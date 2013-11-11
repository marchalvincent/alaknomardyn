package fr.upmc.ta.aladyn;

/**
 * Exception levée lorsque l'interception d'un appel de méthode échoue.
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class InterceptionException extends Exception {

    private static final long serialVersionUID = 1L;

    public InterceptionException(String message) {
	super(message);
    }
}
