package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
