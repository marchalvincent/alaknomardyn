package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

import fr.upmc.ta.aladyn.tests.objects.MyTransactionnable;
import fr.upmc.ta.aladyn.utils.Utils;

public class UtilsTests {
	
	/**
	 * Teste la méthode isTransactionnable de la classe BackupManager.
	 * @throws Exception
	 */
	@Test
	public final void isClassTransactionnableTest() throws Exception {

		MyTransactionnable mt = new MyTransactionnable();
		Field[] fields = mt.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().contains("transactionnable"))
				assertTrue(Utils.isClassTransactionnable(field, mt));
			else 
				assertFalse(Utils.isClassTransactionnable(field, mt));
		}
	}
}
