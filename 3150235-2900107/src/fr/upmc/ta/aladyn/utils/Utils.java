package fr.upmc.ta.aladyn.utils;

import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Cette classe rassemble quelques méthodes utilitaires de manière à simplifier la manipulation des classes et méthodes
 * transactionnables.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class Utils {

    /**
     * Renvoie true si la Class passée en paramètre est {@link Transactionnable}.
     * 
     * @param clazz
     *            la classe à tester.
     * @return boolean.
     */
    public static boolean isClassTransactionnable(Class<?> clazz) {
	return findTransactionnable(clazz.getAnnotations());
    }

    /**
     * Renvoie true si dans les objets passés en paramètre il y a au moins une instance de {@link Transactionnable}.
     * 
     * @param annotations
     *            un tableau d'objets.
     * @return boolean.
     */
    private static boolean findTransactionnable(Object[] annotations) {
	boolean isTransac = false;
	for (Object annotation : annotations) {
	    if (annotation instanceof Transactionnable) {
		isTransac = true;
		break;
	    }
	}
	return isTransac;
    }
}
