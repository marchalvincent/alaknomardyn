package fr.upmc.ta.aladyn.backup;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 * @param <T> le type de la classe à enregistrer.
 */
public class BackupManager<T> {
	
	private Map<Field, Object> values;
	
	public BackupManager() {
		super();
		values = new HashMap<>();
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

					Class<?> componentType = field.get(objectToSave).getClass().getComponentType();
					int[] array = (int[]) Array.newInstance(componentType, 2);
					
					Array.set(array, 0, Array.get(field.get(objectToSave), 0));
					Array.set(array, 1, Array.get(field.get(objectToSave), 1));
					
					values.put(field, array);
					
					System.out.println("0. " + Array.get(field.get(objectToSave), 0));
					System.out.println("1. " + Array.get(field.get(objectToSave), 1));
					
				} else {
					values.put(field, field.get(objectToSave));
				}
			}
		}
		
//		System.out.println("after save");
//		Set<Field> set = values.keySet();
//		for (Field field : set) {
//			System.out.println(field + " | " + values.get(field));
//		}
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
					
					int[] array = (int[]) values.get(field);
					
					Array.set(field.get(objectToRestore), 0, array[0]);
					Array.set(field.get(objectToRestore), 1, array[1]);
					
				} else {
					field.set(objectToRestore, values.get(field));
				}
			}
		}
		
//		System.out.println("after restore");
//		Set<Field> set = values.keySet();
//		for (Field field : set) {
//			System.out.println(field.getType().isArray() + " | map : " + values.get(field) + " | object : " + field.get(objectToRestore));
//		}
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
