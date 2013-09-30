package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import org.junit.Test;
import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.backup.BackupManager;
import fr.upmc.ta.aladyn.tests.objects.MyTransactionnable;
import fr.upmc.ta.aladyn.tests.objects.Titi;


public class BackupManagerTest {

	/**
	 * Teste la méthode isTransactionnable de la classe BackupManager.
	 * @throws Exception
	 */
	@Test
	public final void isTransactionnableTest() throws Exception {

		MyTransactionnable mt = new MyTransactionnable();
		Field[] fields = mt.getClass().getDeclaredFields();
		BackupManager<MyTransactionnable> bm = new BackupManager<>();
		
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().contains("transactionnable"))
				assertTrue(bm.isTransactionnable(field, mt));
			else 
				assertFalse(bm.isTransactionnable(field, mt));
		}
	}
	
	/**
	 * Teste les points suivants sur les variables d'un objet transactionnel (lors d'une restauration) :
	 * <ul>
	 * <li>Si la valeur d'une variable primitive est changée, elle est rétablie.</li>
	 * </ul>
	 * @throws Exception
	 */
	@Test
	public final void primitifRestoreTest() throws Exception {
		
		MyTransactionnable mt = new MyTransactionnable();
		assertTrue(mt.primitif_y == 0);
		assertTrue(mt.primitif_b == true);
		
		BackupManager<MyTransactionnable> bm = new BackupManager<>();
		bm.save(mt);
		
		mt.modifyPrimitif();
		assertTrue(mt.primitif_y != 0);
		assertTrue(mt.primitif_b != true);
		
		try {
			mt.failMethod();
		} catch (MethodException e) {
			// on check que le type primitif a changé
			assertTrue(mt.primitif_y != 0);
			assertTrue(mt.primitif_b != true);
			
			System.err.print(e.getMessage());
			// et on tente le restore
			try {
				bm.restore(mt);
				System.out.println(" But restored.");
			} catch (Exception e2) {
				// ici, le restore a aussi raté
				System.out.println();
				throw new BackupException("Backup fail. " + e2.getMessage());
			}
		}
		assertTrue(mt.primitif_y == 0);
		assertTrue(mt.primitif_b == true);
	}
	
	/**
	 * Teste les points suivants sur les variables d'un objet transactionnel (lors d'une restauration) :
	 * <ul>
	 * <li>Si une référence vers un objet non transactionnable est changée, elle est rétablie ;</li>
	 * <li>Si la valeur d'un objet non transactionnable est changée, la valeur n'est pas rétabli.</li>
	 * </ul>
	 * @throws Exception
	 */
	@Test
	public final void objectNonTransactionnableTest() throws Exception {
		
		MyTransactionnable mt = new MyTransactionnable();
		
		// on enregistre les références
		Titi titi = mt.object_titi;
		Integer integer = mt.object_x;

		assertTrue(mt.object_x == integer);
		assertTrue(mt.object_x.equals(0));

		assertTrue(mt.object_titi == titi);
		assertTrue(mt.object_titi.x.equals(0));
		
		BackupManager<MyTransactionnable> bm = new BackupManager<>();
		bm.save(mt);
		
		mt.modifyObjectNonTransactionnable();
		// object_x à changé de référence et de valeur
		assertTrue(mt.object_x != integer);
		assertTrue(mt.object_x.equals(2));

		// object_titi n'a pas changé de référence mais de valeur
		assertTrue(mt.object_titi == titi);
		assertTrue(!mt.object_titi.x.equals(0));
		
		try {
			mt.failMethod();
		} catch (MethodException e) {
			
			System.err.print(e.getMessage());
			// on tente le restore
			try {
				bm.restore(mt);
				System.out.println(" But restored.");
			} catch (Exception e2) {
				// ici, le restore a aussi raté
				System.out.println();
				throw new BackupException("Backup fail. " + e2.getMessage());
			}
		}

		// la référence de object_x a été rétablie, et donc la valeur est redevenue la même
		assertTrue(mt.object_x == integer);
		assertTrue(mt.object_x.equals(0));

		/*
		 * la référence de object_titi est toujours la même, mais la valeur reste changée 
		 * car l'objet est non transactionnel
		 */
		assertTrue(mt.object_titi == titi);
		assertTrue(!mt.object_titi.x.equals(0));
	}

	@Test
	public final void objectTransactionnableTest() {
		
	}
}
