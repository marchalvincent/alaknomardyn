package fr.upmc.ta.aladyn.backup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 * @param <T>
 *            le type de la classe à enregistrer.
 */
public class BackupManager<T> {

	private Map<Field, Object> copies;
	private Map<Field, Object> references;

	public BackupManager() {

		super();
		copies = new HashMap<>();
		references = new HashMap<>();
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
			if (!Modifier.isFinal(field.getModifiers())) {
				
				// dans le cas où on a un tableau, il faut faire la copie
				if (field.getType().isArray()) {
					// TODO tableau
//					 Class<?> componentType = field.getType().getComponentType();
//					 int[] array = (int[]) Array.newInstance(componentType, 2);
//					
//					 Array.set(array, 0, Array.get(field.get(objectToSave), 0));
//					 Array.set(array, 1, Array.get(field.get(objectToSave), 1));
//					
//					 values.put(field, array);
//					
//					 System.out.println("0. " + Array.get(field.get(objectToSave), 0));
//					 System.out.println("1. " + Array.get(field.get(objectToSave), 1));
					
				} else {
					this.saveField(field, objectToSave);
				}
			}
			field.setAccessible(false);
		}
	}

	/**
	 * Cette méthode regarde le type du champ à sauvegarder.
	 * <ul>
	 * <li>Soit le type est primitif, alors la méthode renvoie une copie de l'objet ;</li>
	 * <li>Soit le type est un objet transactionnable, alors la méthode enregistre par copie l'objet ;</li>
	 * <li>Soit le type est un objet <b>non</b> transactionnable, alors on ne sauvegarde que la référence.</li>
	 * </ul>
	 * 
	 * @param field
	 * @param objectToSave
	 * @return
	 * @throws Exception
	 */
	private void saveField(Field field, Object objectToSave) throws Exception {

		if (field.getType().isPrimitive()) {
			// on fait une copie du type primitif, le get le fait automatiquement
			copies.put(field, field.get(objectToSave));
		} else {
			// ici, on a un objet. Il faut savoir s'il est annoté transactionnable
			if (this.isTransactionnable(field, objectToSave)) {
				
				// on fait la sauvegarde par copie de l'objet
				// TODO faire la sauvegarde par copie
//				Class<?> type = field.getType();
//				BackupManager<Object> bm = new BackupManager<>();
//				bm.save(field.get(objectToSave));
//				
//				copies.put(field, null);
				references.put(field, field.get(objectToSave));
				
			} else {
				// on fait la sauvegarde par référence de l'objet, le get le fait automatiquement
				references.put(field, field.get(objectToSave));
			}
		}
	}
	
	/**
	 * Parcours les annotations de la classe du champ passé en paramètre.
	 * @param field
	 * @return true si le champ est un objet transactionnable, false sinon.
	 * @throws Exception si une erreur survient. Typiquement, l'objet du champ n'est pas initialisé.
	 */
	public boolean isTransactionnable(Field field, Object objectToSave) throws Exception {
		boolean isTransac = false;
		Annotation[] annotations = field.getType().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Transactionnable) {
				isTransac = true;
				break;
			}
		}
		return isTransac;
	}

	/**
	 * Méthode permettant de restaurer un objet préalablement sauvegardé
	 * 
	 * @param objectToRestore
	 *            : Object à restaurer
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws Exception
	 */
	public void restore(T objectToRestore) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalAccessException {

		// on récupère les Field de l'objet
		Field[] fields = objectToRestore.getClass().getDeclaredFields();
		// parcours de tous les attributs de backup pour récupérer les valeurs et les restaurer dans l'objet
		for (Field field : fields) {
			field.setAccessible(true);
			
			// on ne peut pas "set" une variable final
			if (!Modifier.isFinal(field.getModifiers())) {
				if (field.getType().isArray()) {
					// int[] array = (int[]) values.get(field);
					// Array.set(field.get(objectToRestore), 0, array[0]);
					// Array.set(field.get(objectToRestore), 1, array[1]);
				} else {
					// on regarde si ce field est une copie ou une référence
					Object copy = copies.get(field);
					Object reference = references.get(field);
					
					if (copy == null && reference != null) {
						// si le field est une référence, on set l'ancienne référence
						field.set(objectToRestore, reference);
						
					} else if (copy != null && reference == null) {
						
						if (field.getType().isPrimitive()) {
							// si le field à été copié par copie, et que c'est un type primitif
							field.set(objectToRestore, copy);
						} else {
							// si le field à été copié par copie, et que c'est un objet transactionnable
							// TODO gérer...
						}
					} else {
						System.err.println("Error... BackupManager method restore. Field " + field.getName());
					}
				}
			}
			field.setAccessible(false);
		}
	}
}
