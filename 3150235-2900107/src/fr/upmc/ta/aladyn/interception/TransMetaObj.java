package fr.upmc.ta.aladyn.interception;

import fr.upmc.ta.aladyn.Transactionnable;
import fr.upmc.ta.aladyn.backup.BackupManager;
import fr.upmc.ta.aladyn.backup.MethodeCouranteManager;
import javassist.tools.reflect.*;

public class TransMetaObj extends Metaobject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Méthode non surchargé
     * @param self
     * @param args
     */
    public TransMetaObj(Object self, Object[] args) {
	super(self, args);
    }

    /**
     * Méthode non surchargé
     * @param self
     * @param args
     */
    public Object trapFieldRead(String name) {
	return super.trapFieldRead(name);
    }

    /**
     * Méthode non surchargé
     * @param self
     * @param args
     */
    public void trapFieldWrite(String name, Object value) {
	super.trapFieldWrite(name, value);
    }

    /**
     * Méthode surchargé
     * Permet d'intercepter les appels des méthodes débutant par "set". Avant la réalisation de la méthode nous réalisons une
     * sauvegarde les objets passé en parametre via les {@link BackupManager} dans la pile présente dans la class
     * {@link MethodeCouranteManager}. Dans le cas d'une exception nous restaurons les variables sauvegardées.
     */
    public Object trapMethodcall(int identifier, Object[] args) throws Throwable {
	
	boolean methodStartsWithSet = getMethodName(identifier).startsWith("set");
	boolean methodeIsTransactionnable = getClassMetaobject().getMethod(identifier).isAnnotationPresent(Transactionnable.class);
	@SuppressWarnings("unchecked")
	boolean classIsTransactionnable = getClassMetaobject().getJavaClass().isAnnotationPresent(Transactionnable.class);

	boolean mustSaveObject = methodStartsWithSet && classIsTransactionnable;
	boolean mustEmpileDepileAndCatchException = methodeIsTransactionnable;

	if (mustEmpileDepileAndCatchException) {
	    // si on est dans une méthode transactionnable, on empile la méthode dans la stack
	    MethodeCouranteManager.instance.newTransactionnableMethod();
	}

	if (mustSaveObject) {
	    // si on est dans un set, on sauvegarde l'objet 
	    MethodeCouranteManager.instance.addBackupToCurrentMethod(new BackupManager(getObject()));
	}
	
	Object result;
	try {
	    result = super.trapMethodcall(identifier, args);
	} catch (Throwable e) {
	    if (mustEmpileDepileAndCatchException) {
		// si on est dans une méthode transactionnable, on restore les exception
		MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
	    }
	    throw e;
	}

	if (mustEmpileDepileAndCatchException) {
	    // si on est dans une méthode transactionnable, on dépile la méthode de la stack
	    MethodeCouranteManager.instance.endOfTransactionnableMethod();
	}

	return result;
    }
}