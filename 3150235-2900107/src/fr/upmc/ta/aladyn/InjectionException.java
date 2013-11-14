package fr.upmc.ta.aladyn;

/**
 * Exception levée lorsque une injection de code échoue.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InjectionException extends Exception {

    private static final long serialVersionUID = 1L;

    public InjectionException(String message) {
	super(message);
    }
}
