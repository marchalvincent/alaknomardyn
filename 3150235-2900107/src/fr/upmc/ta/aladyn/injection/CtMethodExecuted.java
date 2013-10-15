package fr.upmc.ta.aladyn.injection;

import java.util.ArrayList;
import java.util.List;

import fr.upmc.ta.aladyn.backup.BackupManager;

/**
 * Représente une méthode qui est dans la stack des exécutions. Elle contient une liste d'objets à restaurer 
 * en cas de problème.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class CtMethodExecuted {

    /**
     * Représente la liste des objets à restaurer en cas d'exception levée dans la méthode 
     * transactionnable que cette classe représente.
     */
    private List<BackupManager> objectsToRestore;

    public CtMethodExecuted() {
	super();
	objectsToRestore = new ArrayList<>();
    }

    /**
     * Ajout un {@link BackupManager} à la liste des objets à restaurer s'il n'existe pas.
     * @param backup le nouveau {@link BackupManager}.
     */
    public void addBackupManager(BackupManager backup) {
	/*
	 * on vérifie bien que l'objet n'est pas déjà dans la liste. La méthode contains utilise la méthode equals du 
	 * {@link BackupManager}. Elle a été redéfinie de façon a renvoyer true dans le cas où l'objet sauvegardé est 
	 * le même dans deux instances de BackupManager.
	 */
	if (!objectsToRestore.contains(backup))
	    objectsToRestore.add(backup);
    }

    /**
     * Simple Getter.
     * @return List.
     */
    public List<BackupManager> getBackupsList() {
	return objectsToRestore;
    }
}
