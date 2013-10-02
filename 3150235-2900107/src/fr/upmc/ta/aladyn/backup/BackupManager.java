package fr.upmc.ta.aladyn.backup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import fr.upmc.ta.aladyn.BackupException;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 * @param <T>
 *            le type de la classe à enregistrer.
 */
public class BackupManager<T> {

	private Map<Field, Object> savedFields;

	public BackupManager() {

		super();
		savedFields = new HashMap<>();
	}

	/**
	 * Méthode permettant de sauvegarder un objet à un instant T
	 * 
	 * @param objectToSave
	 *            : Objet à sauvegarder
	 * @throws
	 * @throws InstantiationException
	 * @throws Exception
	 */
	public void save(T objectToSave) throws Exception {

		// on récupère les Field de l'objet
		Field[] fields = objectToSave.getClass().getDeclaredFields();
		// parcours de tous les attributs de l'objet afin de récupérer les valeurs des attributs et les sauvegarder
		
		for (Field field : fields) {
			field.setAccessible(true);
			
			// on ne peut pas "set" une variable final, il ne sert a rien de l'avoir en mémoire
			if (!Modifier.isFinal(field.getModifiers()))
				savedFields.put(field, field.get(objectToSave));
			
			field.setAccessible(false);
		}
	}

	/**
	 * Méthode permettant de restaurer un objet préalablement sauvegardé
	 * 
	 * @param objectToRestore
	 *            : Object à restaurer
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws BackupException 
	 * @throws Exception
	 */
	public void restore(T objectToRestore) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalAccessException, BackupException {

		// on récupère les Field de l'objet
		Field[] fields = objectToRestore.getClass().getDeclaredFields();
		// parcours de tous les attributs de backup pour récupérer les valeurs et les restaurer dans l'objet
		for (Field field : fields) {
			field.setAccessible(true);
			
			// on ne peut pas "set" une variable final
			if (!Modifier.isFinal(field.getModifiers())) {
				if (!savedFields.containsKey(field))
					throw new BackupException("The field is not contained in the backup.");
				
				field.set(objectToRestore, savedFields.get(field));
			}
			
			field.setAccessible(false);
		}
	}
}
