package fr.upmc.ta.aladyn;

/**
 * Cette exception est utilisée pour simuler une méthode qui échoue. On s'en sert pour tester la sauvegarde des objets
 * {@link Transactionnable}.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class MethodException extends Exception {

    private static final long serialVersionUID = 1L;

    public MethodException(String message) {

	super(message);
    }
}
