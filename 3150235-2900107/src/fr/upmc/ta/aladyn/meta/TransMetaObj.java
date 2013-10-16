package fr.upmc.ta.aladyn.meta;

import fr.upmc.ta.aladyn.backup.BackupManager;
import fr.upmc.ta.aladyn.injection.MethodeCouranteManager;
import javassist.tools.reflect.*;

public class TransMetaObj extends Metaobject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TransMetaObj(Object self, Object[] args) {
	super(self, args);
	// System.out.println("** constructed : " + self.getClass( ).getName());
    }

    public Object trapFieldRead(String name) {
	// System.out.println( "** fieldread : " + name) ;
	return super.trapFieldRead(name);
    }

    public void trapFieldWrite(String name, Object value) {
	// System.out.println( "** fieldwrite : " + name) ;
	super.trapFieldWrite(name, value);
    }

    /**
     * Permet d'intercepter les appels des méthodes débutant par "set". Avant la réalisation de la méthode nous réalisons une
     * sauvegarde les objets passé en parametre via les {@link BackupManager} dans la pile présente dans la class
     * {@link MethodeCouranteManager}. Dans le cas d'une exception nous restaurons les variables sauvegardées.
     */
    public Object trapMethodcall(int identifier, Object[] args) throws Throwable {
	// On vérifie que la méthode est une méthode de type SET
	if (getMethodName(identifier).length() >= 3 && getMethodName(identifier).substring(0, 3).equals("set")) {
	    System.out.println("** trap : " + getMethodName(identifier) + " () in " + getClassMetaobject().getName());
	    /**
	     * Appel de la sauvegarde
	     */
	    // Ajout de la méthode courante dans la pile
	    MethodeCouranteManager.instance.newTransactionnableMethod();
	    // Création du {@link BackupManager} de l'objet applant
	    MethodeCouranteManager.instance.addBackupToCurrentMethod(new BackupManager(getObject()));

	    Object o = new Object();
	    try {
		o = super.trapMethodcall(identifier, args);
	    } catch (Throwable e) {
		/**
		 * Appel de la restoration
		 */
		MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
		System.out.println("Appel de la méthode restore");
	    }
	    MethodeCouranteManager.instance.endOfTransactionnableMethod();
	    return o;
	} else {
	    return super.trapMethodcall(identifier, args);
	}
    }
}