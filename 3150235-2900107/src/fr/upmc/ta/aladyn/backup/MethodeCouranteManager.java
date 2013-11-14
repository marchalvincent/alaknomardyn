package fr.upmc.ta.aladyn.backup;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import fr.upmc.ta.aladyn.BackupException;

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
	stackMethodsOfThreads = new HashMap<Long, Stack<CtMethodExecuted>>();
    }

    /**
     * Cette Map associe une LIFO (représentant la pile des méthodes transactionnables exécutées) à chaque thread.
     */
    Map<Long, Stack<CtMethodExecuted>> stackMethodsOfThreads;

    /**
     * Créé une nouvelle {@link CtMethodExecuted} et la push dans la Stack des méthodes du thread en cours. Cette méthode est à
     * appeler en début de méthode transactionnable.
     */
    public void newTransactionnableMethod() {
	Long threadId = Thread.currentThread().getId();

	Stack<CtMethodExecuted> stack = stackMethodsOfThreads.get(threadId);
	if (stack == null)
	    stackMethodsOfThreads.put(threadId, new Stack<CtMethodExecuted>());

	stackMethodsOfThreads.get(threadId).push(new CtMethodExecuted());
    }

    /**
     * Dépile la stack des méthodes du thread en cours. Cette méthode est à appeler en fin de méthode transactionnable.
     * 
     * @throws BackupException
     *             Cette exception est levée si aucune méthode transactionnable n'est enregistrée.
     */
    public void endOfTransactionnableMethod() throws BackupException {
	if (stackMethodsOfThreads == null || stackMethodsOfThreads.isEmpty())
	    throw new BackupException(
		    "The Map of transactionnables methods is empty. You must call the newTransactionnableMethod.");

	Stack<CtMethodExecuted> stack = stackMethodsOfThreads.get(Thread.currentThread().getId());
	if (stack == null || stack.isEmpty())
	    throw new BackupException(
		    "The stack of transactionnables methods is empty for this thread. You must call the newTransactionnableMethod before to pop the stack.");

	CtMethodExecuted ctMethodExecuted = stack.pop();
	ctMethodExecuted.getBackupsList().clear();
    }

    /**
     * Ajoute un backup d'objet dans la liste de la méthode transactionnable courrante (celle qui est au dessus de la stack) pour
     * le thread en cours.
     * 
     * Il faut également renvoyer ce backup dans les autres méthodes transactionnable de la pile du thread, car si un problème
     * intervient dans celles-ci, les modifications apportée dans la méthode courrante (celle du haut de la pile) doivent être
     * défaites.
     * 
     * @param backup
     *            le backup de l'objet enregistré
     * @throws BackupException
     */
    public void addBackupToCurrentMethod(BackupManager backup) throws BackupException {
	// si la map est vide, on ne save pas le backup manager
	if (stackMethodsOfThreads == null || stackMethodsOfThreads.isEmpty())
	    return;

	// si la stack du thread n'existe pas ou est vide, on ne save pas le backup manager
	Stack<CtMethodExecuted> stack = stackMethodsOfThreads.get(Thread.currentThread().getId());
	if (stack == null || stack.isEmpty())
	    return;

	// si la stack des méthodes transactionnable n'est pas vide, il faut alors sauvegarder le backup
	for (CtMethodExecuted method : stack) {
	    method.addBackupManager(backup);
	}
    }

    /**
     * Fait appel à la méthode restore de l'ensemble des backupManager de la méthode transactionnable courrante pour le thread en
     * cours.
     * 
     * @return une List de {@link BackupManager}.
     */
    public void restoreBackupsOfLastMethod() {
	if (stackMethodsOfThreads == null || stackMethodsOfThreads.isEmpty())
	    return;

	Stack<CtMethodExecuted> stack = stackMethodsOfThreads.get(Thread.currentThread().getId());
	if (stack == null || stack.isEmpty())
	    return;

	// pour chaque backup, on lance le restore
	for (BackupManager backupManager : stack.peek().getBackupsList()) {
	    try {
		backupManager.restore();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
