package fr.upmc.ta.aladyn.utils;

import javassist.CtClass;
import fr.upmc.ta.aladyn.Transactionnable;

public class Utils {
	
	public static boolean isClassTransactionnable(Class<?> clazz) {
		return findTransactionnable(clazz.getAnnotations());
	}
	
	public static boolean isCtClassTransactionnable(CtClass ctClass) throws ClassNotFoundException {
		return findTransactionnable(ctClass.getAnnotations());
	}
	
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
