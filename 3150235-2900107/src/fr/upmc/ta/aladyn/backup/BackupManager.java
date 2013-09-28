package fr.upmc.ta.aladyn.backup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
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

		// parcours de tous les attributs de l'objet afin de récupérer les
		// valeurs des attributs et les sauvegarder
		for (Field field : fields) {
			field.setAccessible(true);

			// on ne peut pas "set" une variable final, il ne sert a rien de
			// l'avoir en mémoire
			if (!Modifier.isFinal(field.getModifiers())) {

				// dans le cas où on a un tableau, il faut faire la copie
				if (field.getType().isArray()) {

					// TODO tableau
					// Class<?> componentType =
					// field.get(objectToSave).getClass().getComponentType();
					// int[] array = (int[]) Array.newInstance(componentType,
					// 2);
					//
					// Array.set(array, 0, Array.get(field.get(objectToSave),
					// 0));
					// Array.set(array, 1, Array.get(field.get(objectToSave),
					// 1));
					//
					// values.put(field, array);
					//
					// System.out.println("0. " +
					// Array.get(field.get(objectToSave), 0));
					// System.out.println("1. " +
					// Array.get(field.get(objectToSave), 1));
					//
				} else {
					
					if (this.isCopy(field, objectToSave)) {
						copies.put(field, field.get(objectToSave));
					} else {
						// TODO
					}
					 
				}
			}
		}
	}
	
	private boolean isCopy(Field field, Object objectToSave) {
		
		return false;
	}

	/**
	 * Cette méthode regarde le type du champ à sauvegarder.
	 * <ul>
	 * <li>Soit le type est primitif, alors la méthode renvoie une copie de
	 * l'objet ;</li>
	 * <li>Soit le type est un objet transactionnable, alors la méthode
	 * enregistre par copie l'objet ;</li>
	 * <li>Soit le type est un objet <b>non</b> transactionnable, alors on ne
	 * sauvegarde que la référence.</li>
	 * </ul>
	 * 
	 * @param field
	 * @param objectToSave
	 * @return
	 * @throws Exception 
	 */
	private Object saveObject(Field field, Object objectToSave) throws Exception {

		if (field.getType().isPrimitive()) {
			// on fait une copie du type primitif, le get le fait automatiquement
			return field.get(objectToSave);

		} else {
			// ici, on a un objet. Il faut savoir s'il est annoté transactionnable
			Annotation[] annotations = field.getAnnotations();
			boolean isTransac = false;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Transactionnable) {
					isTransac = true;
					break;
				}
			}

			if (isTransac) {
				// on fait la sauvegarde par copie de l'objet
				// TODO
				Class<?> type = field.get(objectToSave).getClass();
				
				// TODO michel putain c'est la merde !
				//TODO vincent
				BackupManager<Object> bm = new BackupManager<>();
				bm.save(field.get(objectToSave));
				
				

			} else {
				// on fait la sauvegarde par référence, le get le fait automatiquement
				return field.get(objectToSave);
			}
		}
		return null;
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
	public void restore(T objectToRestore)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			IllegalAccessException {

		// on récupère les Field de l'objet
		Field[] fields = objectToRestore.getClass().getDeclaredFields();

		// parcours de tous les attributs de backup pour récupérer les valeurs
		// et les restaurer dans l'objet
		for (Field field : fields) {
			field.setAccessible(true);

			// on ne peut pas "set" une variable final
			if (!Modifier.isFinal(field.getModifiers())) {

				if (field.getType().isArray()) {

					// int[] array = (int[]) values.get(field);

					// Array.set(field.get(objectToRestore), 0, array[0]);
					// Array.set(field.get(objectToRestore), 1, array[1]);

				} else {
					// field.set(objectToRestore, values.get(field));
				}
			}
		}

		// System.out.println("after restore");
		// Set<Field> set = values.keySet();
		// for (Field field : set) {
		// System.out.println(field.getType().isArray() + " | map : " +
		// values.get(field) + " | object : " + field.get(objectToRestore));
		// }
	}

	/**
	 * Méthode permettant d'afficher les informations d'une class (le nombre, le
	 * type et le nom des attributs présent dans la classe)
	 * 
	 * @param classObject
	 *            : Class de l'objet
	 */
	public void showInformation(Class<?> classObject) {
		Field[] fields = classObject.getDeclaredFields();

		System.out.printf("%d fields:%n", fields.length);

		for (Field field : fields) {
			System.out.printf("%s %s %s%n", Modifier.toString(field
					.getModifiers()), field.getType().getSimpleName(), field
					.getName());
		}
	}

}
