package fr.upmc.ta.aladyn.backup;

import java.util.Stack;

import fr.upmc.ta.aladyn.InjectionException;

/**
 * Cette méthode se charge de garder en mémoire les méthodes transactionnables en cours à un instant t de l'éxecution d'un
 * programme. Ces méthodes possèdent une liste d'objets transactionnables à restaurer en cas d'erreur.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class MethodeCouranteManager {

    // le singleton
    public static MethodeCouranteManager instance = new MethodeCouranteManager();

    private MethodeCouranteManager() {
	super();
	stackMethods = new Stack<>();
    }

    /**
     * Cette LIFO représente la pile des méthodes transactionnables exécutées.
     */
    Stack<CtMethodExecuted> stackMethods;

    /**
     * Créé une nouvelle {@link CtMethodExecuted} et la push dans la Stack des méthodes en cours. Cette méthode est à appeler en
     * début de méthode transactionnable.
     */
    public void newTransactionnableMethod() {
	stackMethods.push(new CtMethodExecuted());
    }

    /**
     * Dépile la stack des méthodes en cours. Cette méthode est à appeler en fin de méthode transactionnable.
     * 
     * @throws InjectionException
     *             Cette exception est levée si aucune méthode transactionnable n'est enregistrée.
     */
    public void endOfTransactionnableMethod() throws InjectionException {
	if (stackMethods.isEmpty())
	    throw new InjectionException(
		    "The stack of transactionnables methods is empty. You must call the newTransactionnableMethod before to"
			    + " pop the stack.");
	CtMethodExecuted ctMethodExecuted = stackMethods.pop();
	ctMethodExecuted.getBackupsList().clear();
    }

    /**
     * Ajoute un backup d'objet dans la liste de la méthode transactionnable courrante (celle qui est au dessus de la stack).
     * 
     * @param backup
     *            le backup de l'objet enregistré
     * @throws InjectionException
     */
    public void addBackupToCurrentMethod(BackupManager backup) throws InjectionException {
	if (stackMethods.isEmpty())
	    throw new InjectionException(
		    "The stack of transactionnables methods is empty. You must call the newTransactionnableMethod before to"
			    + " add a backupManager.");
	stackMethods.peek().addBackupManager(backup);
    }

    /**
     * Fait appel à la méthode restore de l'ensemble des backupManager de la méthode transactionnable courrante.
     * 
     * @return une List de {@link BackupManager}.
     */
    public void restoreBackupsOfLastMethod() {
	// pour chaque backup, on lance le restore
	for (BackupManager backupManager : stackMethods.peek().getBackupsList()) {
	    try {
		backupManager.restore();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

}
