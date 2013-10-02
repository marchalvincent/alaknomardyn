package fr.upmc.ta.aladyn.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import fr.upmc.ta.aladyn.Transactionnable;

public class Utils {

	/**
	 * Parcours les annotations de la classe du champ passé en paramètre.
	 * @param field
	 * @return true si le champ est un objet transactionnable, false sinon.
	 * @throws Exception si une erreur survient. Typiquement, l'objet du champ n'est pas initialisé.
	 */
	public static boolean isClassTransactionnable(Field field, Object objectToSave) throws Exception {
		boolean isTransac = false;
		Annotation[] annotations = field.getType().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Transactionnable) {
				isTransac = true;
				break;
			}
		}
		return isTransac;
	}
}
