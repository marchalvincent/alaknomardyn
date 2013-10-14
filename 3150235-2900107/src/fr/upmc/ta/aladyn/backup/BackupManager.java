package fr.upmc.ta.aladyn.backup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.utils.Utils;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 * @param <T>
 *            le type de la classe à enregistrer.
 */
public class BackupManager {

    private Object objectToRestore;
    private Map<Field, Object> savedFields;

    public BackupManager(Object objectToSave) {
	super();
	savedFields = new HashMap<>();
	try {
	    this.save(objectToSave);
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	    objectToRestore = null;
	}
    }

    /**
     * Méthode permettant de sauvegarder un objet à un instant T
     * 
     * @param objectToSave
     *            : Objet à sauvegarder
     * 
     * @throws Exception
     *             En cas d'erreur lors de la sauvegarde. Soit l'objet à sauvegarder n'est pas transactionnable, soit c'est une
     *             erreur lors de la manipulation de la reflexion java.
     */
    public void save(Object objectToSave) throws Exception {

	// petite vérification
	if (!Utils.isClassTransactionnable(objectToSave.getClass())) {
	    throw new BackupException("The object to save is not transactionnable.");
	}

	objectToRestore = objectToSave;
	Class<?> clazz = objectToSave.getClass();
	// on boucle pour sauvegarder les fields des classes mères
	while (!clazz.equals(Object.class)) {

	    // on récupère les Field de l'objet
	    Field[] fields = clazz.getDeclaredFields();
	    // parcours de tous les attributs de l'objet afin de récupérer les valeurs des attributs et les sauvegarder
	    for (Field field : fields) {
		field.setAccessible(true);

		// on ne peut pas "set" une variable final, il ne sert a rien de l'avoir en mémoire
		if (!Modifier.isFinal(field.getModifiers()))
		    savedFields.put(field, field.get(objectToSave));

		field.setAccessible(false);
	    }
	    clazz = clazz.getSuperclass();
	}
    }

    /**
     * Méthode permettant de restaurer l'objet préalablement sauvegardé
     * 
     * @throws Exception
     */
    public void restore() throws Exception {

	if (objectToRestore == null)
	    throw new BackupException("The object must be saved before to be restored.");

	Class<?> clazz = objectToRestore.getClass();
	// on boucle pour restorer les fields des classes mères
	while (!clazz.equals(Object.class)) {

	    // on récupère les Field de l'objet
	    Field[] fields = clazz.getDeclaredFields();
	    // parcours de tous les attributs de backup pour récupérer les valeurs et les restaurer dans l'objet
	    for (Field field : fields) {
		field.setAccessible(true);

		// on ne peut pas "set" une variable final
		if (!Modifier.isFinal(field.getModifiers())) {
		    // petit test qui ne devrait jamais renvoyer l'exception
		    if (!savedFields.containsKey(field))
			throw new BackupException("The field is not contained in the backup.");

		    field.set(objectToRestore, savedFields.get(field));
		}
		field.setAccessible(false);
	    }
	    clazz = clazz.getSuperclass();
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((objectToRestore == null) ? 0 : objectToRestore.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BackupManager other = (BackupManager) obj;
	if (objectToRestore == null) {
	    if (other.objectToRestore != null)
		return false;
	} else if (objectToRestore == other.objectToRestore) {
	    // deux instances de BackupManager sont égales seulement si leurs objets a restaurer sont les mêmes
	    return true;
	}
	return false;
    }
}
