package fr.upmc.ta.aladyn.backup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Cette classe permet d'enregistrer l'état d'un objet puis de le restorer.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class BackupManager {

    /**
     * Référence vers l'objet à sauvegarder
     */
    private Object objectToRestore;
    
    /**
     * Les champs de l'objet sauvegardé
     */
    private Map<Field, Object> savedFields;

    public BackupManager(Object objectToSave) throws Exception {
	super();
	savedFields = new HashMap<>();
	try {
	    this.save(objectToSave);
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	    objectToRestore = null;
	    throw e;
	}
    }

    /**
     * Méthode permettant de sauvegarder un objet à un instant t.
     * 
     * @param objectToSave
     *            : Objet à sauvegarder
     * 
     * @throws BackupException
     *             En cas d'erreur lors de la sauvegarde car l'objet à sauvegarder n'est pas transactionnable.
     * @throws Exception
     * 			En cas d'erreur lors de la sauvegarde car la manipulation de la reflexion java a échouée.
     */
    public void save(Object objectToSave) throws BackupException, Exception {

	// petite vérification, si l'objet n'est pas transactionnable
	if (objectToSave.getClass().getAnnotation(Transactionnable.class) == null) {
	    throw new BackupException("BackupManager : The object to save is not transactionnable : " + objectToSave.getClass().getSimpleName() + ".");
	}

	objectToRestore = objectToSave;
	Class<?> clazz = objectToSave.getClass();
	// on boucle pour sauvegarder les fields des classes mères
	while (!clazz.equals(Object.class)) {

	    // on récupère les Field de l'objet
	    Field[] fields = clazz.getDeclaredFields();
	    // parcours de tous les attributs de l'objet afin de récupérer les valeurs des attributs et les sauvegarder
	    for (Field field : fields) {
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);

		// on ne peut pas "set" une variable final, il ne sert a rien de l'avoir en mémoire
		if (!Modifier.isFinal(field.getModifiers()))
		    savedFields.put(field, field.get(objectToSave));

		field.setAccessible(isAccessible);
	    }
	    clazz = clazz.getSuperclass();
	}
    }

    /**
     * Méthode permettant de restaurer l'objet préalablement sauvegardé.
     * 
     * @throws BackupException si l'objet à restaurer est inconnu.
     * @throws Exception lorsque la reflexion java échoue.
     */
    public void restore() throws BackupException, Exception {

	if (objectToRestore == null)
	    throw new BackupException("The object must be saved before to be restored.");

	Class<?> clazz = objectToRestore.getClass();
	// on boucle pour restorer les fields des classes mères
	while (!clazz.equals(Object.class)) {

	    // on récupère les Field de l'objet
	    Field[] fields = clazz.getDeclaredFields();
	    // parcours de tous les attributs de backup pour récupérer les valeurs et les restaurer dans l'objet
	    for (Field field : fields) {
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);

		// on ne peut pas "set" une variable final
		if (!Modifier.isFinal(field.getModifiers())) {
		    // petit test qui ne devrait jamais renvoyer l'exception
		    if (!savedFields.containsKey(field))
			throw new BackupException("The field is not contained in the backup.");

		    field.set(objectToRestore, savedFields.get(field));
		}
		field.setAccessible(isAccessible);
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
