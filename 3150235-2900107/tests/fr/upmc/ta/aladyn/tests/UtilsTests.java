package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javassist.ClassPool;

import org.junit.Test;

import fr.upmc.ta.aladyn.tests.objects.MyMother;
import fr.upmc.ta.aladyn.tests.objects.MySuperMother;
import fr.upmc.ta.aladyn.tests.objects.MyTransactionnable;
import fr.upmc.ta.aladyn.tests.objects.Tata;
import fr.upmc.ta.aladyn.tests.objects.Titi;
import fr.upmc.ta.aladyn.utils.Utils;

/**
 * Classe de tests pour les méthodes Utiles 
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class UtilsTests {
	
	/**
	 * Teste la méthode Utils.isClassTransactionnable(Class clazz) qui renvoie
	 * true si une classe est transactionnable.
	 */
	@Test
	public void isClassTransactionnableTest() {
		assertTrue(Utils.isClassTransactionnable(Tata.class));
		assertTrue(Utils.isClassTransactionnable(MyTransactionnable.class));
		assertFalse(Utils.isClassTransactionnable(MyMother.class));
		assertFalse(Utils.isClassTransactionnable(MySuperMother.class));
		assertFalse(Utils.isClassTransactionnable(Titi.class));
	}
	
	/**
	 * Teste la méthode Utils.isCtClassTransactionnable(CtClass clazz) qui renvoie
	 * true si une "ct classe" javassist est transactionnable.
	 */
	@Test
	public void isCtClassTransactionnableTest() throws Throwable {

		ClassPool pool = ClassPool.getDefault();

		assertTrue(Utils.isCtClassTransactionnable(pool.getCtClass("fr.upmc.ta.aladyn.tests.objects.Tata")));
		assertTrue(Utils.isCtClassTransactionnable(pool.getCtClass("fr.upmc.ta.aladyn.tests.objects.MyTransactionnable")));
		assertFalse(Utils.isCtClassTransactionnable(pool.getCtClass("fr.upmc.ta.aladyn.tests.objects.MyMother")));
		assertFalse(Utils.isCtClassTransactionnable(pool.getCtClass("fr.upmc.ta.aladyn.tests.objects.MySuperMother")));
		assertFalse(Utils.isCtClassTransactionnable(pool.getCtClass("fr.upmc.ta.aladyn.tests.objects.Titi")));
	}
}
