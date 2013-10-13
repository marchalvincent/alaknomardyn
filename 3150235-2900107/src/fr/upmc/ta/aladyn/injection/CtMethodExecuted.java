package fr.upmc.ta.aladyn.injection;

import java.util.ArrayList;
import java.util.List;

import fr.upmc.ta.aladyn.backup.BackupManager;

public class CtMethodExecuted {

	private List<BackupManager<?>> objectsToRestore;
	
	public CtMethodExecuted() {
		super();
		objectsToRestore = new ArrayList<>();
	}
	
	public void addBackupManager(BackupManager<?> backup) {
		objectsToRestore.add(backup);
	}

	public List<BackupManager<?>> getBackupsList() {
		return objectsToRestore;
	}
}
