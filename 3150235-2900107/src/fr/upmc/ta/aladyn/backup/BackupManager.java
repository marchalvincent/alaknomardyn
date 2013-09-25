package fr.upmc.ta.aladyn.backup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import fr.upmc.ta.aladyn.tests.MyPoint;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 * @param <T> le type de la classe à enregistrer.
 */
public class BackupManager<T> {

	private T backup;

	/***
	 * Méthode permettant de sauvegarder un objet à un instant T
	 * 
	 * @param objectToSave
	 *            : Objet à sauvegarder
	 * @throws  
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	public void backup(T objectToSave) throws Exception {

		// on récupère la classe de l'objet, ainsi que ses fields
		Class<?> classObject = objectToSave.getClass();
		Field[] fields = classObject.getDeclaredFields();

		// Instanciacion de l'objet backup
		/*** ATTENTION LA CLASSE DOIT POSSEDER UN CONSTRUCTEUR SANS PARAMETRES ***/
		// TODO gérer...
		backup = (T) classObject.newInstance();

		// parcours de tous les attributs de l'objet afin de récupérer les valeurs des attributs et les sauvegarder
		for (Field field : fields) {
			field.setAccessible(true);

			// on ne peut pas "set" une variable final
			if (!Modifier.isFinal(field.getModifiers())) {
				field.set(backup, field.get(objectToSave));
			}
		}

		System.out.println("SAVE");

		System.out.print("tab0 ");
		System.out.println(((MyPoint) backup).getTab0());
		System.out.print("tab1 ");
		System.out.println(((MyPoint) backup).getTab1());
	}

	/***
	 * Méthode permettant de restaurer un objet préalablement sauvegardé
	 * 
	 * @param objectToRestore
	 *            : Object à restaurer
	 * @throws Exception
	 */
	public void restore(T objectToRestore) throws Exception {

		Class<?> c = objectToRestore.getClass();

		// Récupération de l'ensembe des attributs de l'objet
		// sous le type de Field
		Field[] fields = c.getDeclaredFields();

		// parcours de tous les attributs de backup
		// pour récupérer les valeurs et les restaurer dans l'objet
		for (Field field : fields) {
			field.setAccessible(true);

			// on ne peut pas "set" une variable final
			if (!Modifier.isFinal(field.getModifiers())) {
				field.set(objectToRestore, field.get(backup));
			}
		}

		System.out.println("RESTORE");

		System.out.print("tab0 ");
		System.out.println(((MyPoint) backup).getTab0());
		System.out.print("tab1 ");
		System.out.println(((MyPoint) backup).getTab1());
		
	}

	public T getBackup() {
		return backup;
	}

	/***
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
