package fr.upmc.ta.aladyn.injection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;
import javassist.expr.MethodCall;
import fr.upmc.ta.aladyn.InjectionException;
import fr.upmc.ta.aladyn.Transactionnable;
import fr.upmc.ta.aladyn.backup.CtMethodExecuted;
import fr.upmc.ta.aladyn.backup.MethodeCouranteManager;

/**
 * Ce translator se charge d'injecter du code au chargement des classes afin de gérer les sauvegardes
 * et restaurations des objets transactionables lors des appels de méthode transacionnables.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class InjectionTranslator implements Translator {

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {}

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
	CtClass ctClass = pool.get(classname);

	// 1. Première étape, on regarde si la CtClass est transactionnable
	if (ctClass.hasAnnotation(Transactionnable.class)) {
	    // si oui, il faut injecter ses Setters afin d'enregistrer les états de ses instances
	    try {
		this.injectSetters(pool, ctClass);

	    } catch (InjectionException e) {
		e.printStackTrace();
	    }
	}

	/*
	 * 2. Deuxième étape, on parcours chaque méthodes transactionnables afin de les enregistrer dans le singleton {@link
	 * MethodeCouranteManager}. Ainsi, les backups créé dans les setters pourront se lier à ces méthodes transactionnables.
	 * 
	 * De il faut ajouter le traitement des exceptions et faire appels à la restauration de chaque objet transactionnable
	 * modifié.
	 */
	for (CtMethod method : ctClass.getMethods()) {
	    if (method.hasAnnotation(Transactionnable.class)) {
		// injection des méthodes transactionnables
		this.injectMethod(method, pool);
	    }
	}
    }

    /**
     * Injecte du code en début de chaque Setter de la classe. Le code injecté va faire la sauvegarde des objets instance de cette
     * classe afin de les enregistrer dans le singleton {@link MethodeCouranteManager}.
     * @param pool 
     * 		le ClassPool
     * @param ctClass
     *            la classe dont les setters doivent être injectés.
     * @throws InjectionException
     *             si le code injecté ne compile pas.
     * @throws CannotCompileException 
     */
    private void injectSetters(final ClassPool pool, final CtClass ctClass) throws InjectionException, CannotCompileException {

	ctClass.instrument(new ExprEditor() {
	    // on redéfinit les appels de méthodes
	    public void edit(MethodCall mc) throws CannotCompileException {
		try {
		    // seulement les méthode commencant par "set"
		    CtMethod methodCalled = mc.getMethod();
		    if (methodCalled.getName().startsWith("set")) {
			methodCalled.insertBefore("fr.upmc.ta.aladyn.backup.BackupManager bm = new fr.upmc.ta.aladyn.backup.BackupManager(this);"
				+ "fr.upmc.ta.aladyn.backup.MethodeCouranteManager.instance.addBackupToCurrentMethod(bm);");
		    }
		} catch (NotFoundException e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Injecte du code dans la méthode de façon à :
     * <ul>
     * <li>Gérer le traitement des exceptions et des restores. Il faut non seulement ajouter un restore dans les clauses catch
     * déjà existantes mais aussi gérer les exceptions sortantes de la méthode en restorant les objets transactionnables tout en
     * relevant l'exception qui était levée à la base.</li>
     * <li>Empiler (resp. dépiler) une {@link CtMethodExecuted} dans la stack du {@link MethodeCouranteManager} en début (resp.
     * fin) de la méthode.</li>
     * 
     * @param method
     *            la méthode à injecter.
     * @param pool
     *            la ClassPool
     * @throws CannotCompileException
     *             si le code injecté ne compile pas.
     * @throws NotFoundException
     *             si la récupération de la classe Throwable ne fonctionne pas.
     */
    private void injectMethod(CtMethod method, ClassPool pool) throws CannotCompileException, NotFoundException {

	final String restoreMethod = "fr.upmc.ta.aladyn.backup.MethodeCouranteManager.instance.restoreBackupsOfLastMethod();";

	/*
	 * 1. On ajoute le restore des objets dans les clauses catch déjà existantes
	 */
	method.instrument(new ExprEditor() {
	    // on ne redéfinit que la méthode redéfinissant les clauses catchs
	    public void edit(Handler catchClause) throws CannotCompileException {
		// et on insère juste le restore des objets
		catchClause.insertBefore(restoreMethod);
	    }
	});

	/*
	 * 2. On traite les exceptions sortantes à la méthode en restorant
	 */
	CtClass throwableCtClass = pool.get("java.lang.Throwable");
	method.addCatch(restoreMethod + "throw $e;", throwableCtClass);

	/*
	 * 3. On injecte avant et après la méthode le push et le pop d'une {@link CtMethodExecuted} dans le singleton
	 * {@link MethodeCouranteManager}.
	 */
	method.insertBefore("fr.upmc.ta.aladyn.backup.MethodeCouranteManager.instance.newTransactionnableMethod();");
	method.insertAfter("fr.upmc.ta.aladyn.backup.MethodeCouranteManager.instance.endOfTransactionnableMethod();");
    }
}
