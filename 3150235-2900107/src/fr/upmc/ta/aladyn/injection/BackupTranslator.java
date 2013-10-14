package fr.upmc.ta.aladyn.injection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import fr.upmc.ta.aladyn.InjectionException;
import fr.upmc.ta.aladyn.Transactionnable;

public class BackupTranslator implements Translator {

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
	// TODO Auto-generated method stub
    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
	CtClass ctClass = pool.get(classname);

	// 1. Première étape, on regarde si la CtClass est transactionnable
	try {
	    if (ctClass.hasAnnotation(Transactionnable.class)) {
		// si oui, il faut injecter ses Setters afin d'enregistrer les états de ses instances
		this.injectSetters(ctClass);
	    }
	} catch (InjectionException e) {
	    e.printStackTrace();
	}

	/*
	 * 2. Deuxième étape, on parcours chaque méthodes transactionnables afin de les enregistrer dans le singleton {@link
	 * MethodeCouranteManager}. Ainsi, les backups créé dans les setters pourront se lier à ces méthodes.
	 * 
	 * De il faut ajouter le traitement des exceptions et faire appels à la restauration de chaque objet transactionnable
	 * modifié.
	 */
	for (CtMethod method : ctClass.getMethods()) {
	    if (method.hasAnnotation(Transactionnable.class)) {
		// injection des méthodes transactionnables
		this.injectMethod(method);
	    }
	}
    }

    /**
     * Injecte du code en début de chaque Setter de la classe. Le code injecté va faire la sauvegarde des objets instance de cette
     * classe afin de les enregistrer dans le singleton {@link MethodeCouranteManager}.
     * 
     * @param ctClass
     *            la classe dont les setters doivent être injectés.
     * @throws InjectionException
     *             si le code injecté ne compile pas.
     */
    private void injectSetters(CtClass ctClass) throws InjectionException {
	// on parcours tout les setters afin de faire un save
	for (CtMethod method : ctClass.getMethods()) {
	    if (method.getName().startsWith("set")) {
		// on insère la sauvegarde de l'objet au début du setter
		try {
		    method.insertBefore("BackupManager bm = new BackupManager(this);"
			    + "MethodeCouranteManager.instance.addBackupToCurrentMethod(bm);");

		} catch (CannotCompileException e) {
		    throw new InjectionException(e.getMessage());
		}
	    }
	}
    }

    /**
     * Injecte du code en début et fin de la méthode de façon à :
     * <ul>
     * <li>Empiler (resp. dépiler) une {@link CtMethodExecuted} dans la stack du {@link MethodeCouranteManager} en début (resp.
     * fin) de la méthode</li>
     * <li>Gérer le traitement des exceptions et des restores.</li>
     * 
     * @param method
     *            la méthode à injecter.
     * @throws CannotCompileException
     *             si le code injecté ne compile pas.
     */
    private void injectMethod(CtMethod method) throws CannotCompileException {
	/*
	 * 1. Première étape, on injecte avant et après la méthode la création et suppression d'une {@link CtMethodExecuted} dans
	 * le singleton {@link MethodeCouranteManager}.
	 */
	method.insertBefore("MethodeCouranteManager.instance.newTransactionnableMethod();");
	method.insertAfter("MethodeCouranteManager.instance.endOfTransactionnableMethod();");

	/*
	 * 2. Deuxième étape, le traitement des exceptions, avec les restores
	 */
	// TODO
    }

}
