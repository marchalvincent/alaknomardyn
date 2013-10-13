package fr.upmc.ta.aladyn.injection;

import java.util.List;
import java.util.Stack;

import fr.upmc.ta.aladyn.backup.BackupManager;

/**
 * Cette méthode se charge de garder en mémoire la méthode transactionnable
 * en cours à un instant t de l'éxecution d'un programme. De plus, elle 
 * possède une liste d'objet transactionnable à restaurer en cas d'erreur.
 * 
 * Pour chaque méthode transactionnable, une liste lui est associée.
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
	 * Créé une nouvelle {@link CtMethodExecuted} et la push dans la Stack des méthodes en cours.
	 * Cette méthode est à appeler en début de méthode transactionnable.
	 */
	public void newTransactionnableMethod() {
		stackMethods.push(new CtMethodExecuted());
	}
	
	/**
	 * Dépile la stack des méthodes en cours. Cette méthode est à appeler en fin de méthode 
	 * transactionnable.
	 */
	public void endOfTransactionnableMethod() {
		stackMethods.pop();
	}
	
	/**
	 * Ajoute un backup d'objet dans la liste de la méthode transactionnable courrante (celle 
	 * qui est au dessus de la stack).
	 * 
	 * @param backup le backup de l'objet enregistré
	 */
	public void addBackupToCurrentMethod (BackupManager<?> backup) {
		stackMethods.peek().addBackupManager(backup);
	}
	
	/**
	 * Renvoie la liste des objets à restaurer pour la méthode courrante (celle qui est au 
	 * dessus de la stack).
	 * 
	 * @return une List de {@link BackupManager}.
	 */
	public List<BackupManager<?>> getBackupsToRestore() {
		return stackMethods.peek().getBackupsList();
	}
	
}
