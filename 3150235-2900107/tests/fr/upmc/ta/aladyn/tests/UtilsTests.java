package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

import org.junit.Test;

import fr.upmc.ta.aladyn.testAsupprimer.MyTranslator;
import fr.upmc.ta.aladyn.tests.objects.MyMother;
import fr.upmc.ta.aladyn.tests.objects.MySuperMother;
import fr.upmc.ta.aladyn.tests.objects.MyTransactionnable;
import fr.upmc.ta.aladyn.tests.objects.Tata;
import fr.upmc.ta.aladyn.tests.objects.Titi;
import fr.upmc.ta.aladyn.utils.Utils;

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
	 * true si une classe javassist est transactionnable.
	 */
	@Test
	public void isCtClassTransactionnableTest() throws Throwable {

		// on initialise le pool
		ClassPool pool = ClassPool.getDefault();
		Loader loader = new Loader(pool);

		// TODO
		// initialisation du Translator
		Translator t = new MyTranslator();
		loader.addTranslator(pool, t);
		loader.run("fr.upmc.ta.aladyn.testAsupprimer.MainTestTranslator", null);
	
	}
}
