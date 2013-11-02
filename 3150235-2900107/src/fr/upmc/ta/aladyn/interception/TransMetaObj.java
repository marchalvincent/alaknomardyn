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
	// On vérifie que la méthode est une méthode de type SET
	if (getMethodName(identifier).startsWith("set") && getClassMetaobject().getMethod(identifier).isAnnotationPresent(Transactionnable.class)) {
	    System.out.println("** trap : " + getMethodName(identifier) + " () in " + getClassMetaobject().getName());
	    /**
	     * Appel de la sauvegarde
	     */
	    // Ajout de la méthode courante dans la pile
	    MethodeCouranteManager.instance.newTransactionnableMethod();
	    // Création du {@link BackupManager} de l'objet applant
	    MethodeCouranteManager.instance.addBackupToCurrentMethod(new BackupManager(getObject()));

	    Object result;
	    try {
		result = super.trapMethodcall(identifier, args);
	    } catch (Throwable e) {
		/**
		 * Appel de la restoration
		 */
		System.out.println("Appel de la méthode restore");
		MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
		throw e;
	    }
	    // Dépilement de la méthode
	    MethodeCouranteManager.instance.endOfTransactionnableMethod();
	    return result;
	} else {
	    return super.trapMethodcall(identifier, args);
	}
    }
}