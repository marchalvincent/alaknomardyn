package fr.upmc.ta.aladyn;

/**
 * Exception levée lorsque la sauvegarde d'un objet {@link Transactionnable} échoue.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class BackupException extends Exception {

    private static final long serialVersionUID = 1L;

    public BackupException(String message) {

	super(message);
    }
}
