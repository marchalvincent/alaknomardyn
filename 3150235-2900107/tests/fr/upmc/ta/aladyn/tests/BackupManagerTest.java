package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.backup.BackupManager;
import fr.upmc.ta.aladyn.tests.objects.MyTransactionnable;
import fr.upmc.ta.aladyn.tests.objects.Tata;
import fr.upmc.ta.aladyn.tests.objects.Titi;

/**
 * Classe de tests pour le BackupManager qui se charge de sauvegarder et restaurer l'état d'un objet.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public final class BackupManagerTest {

    /**
     * Test la méthode equals du backupManager
     * 
     * @throws Exception
     */
    @Test
    public void backupManagerEqualsTest() throws Exception {
	Tata tata = new Tata();
	BackupManager bm1 = new BackupManager(tata);

	tata.x = 11;
	BackupManager bm2 = new BackupManager(tata);

	assertTrue(bm1.equals(bm2));

	Tata tata2 = new Tata(tata);
	BackupManager bm3 = new BackupManager(tata2);

	assertTrue(!bm2.equals(bm3));
    }

    /**
     * Test que l'on ne peut pas sauvegarder un objet non transactionnable
     * 
     * @throws Exception
     */
    @Test(expected = BackupException.class)
    public void saveNonTransactionnableObject() throws Exception {
	new BackupManager(new Titi());
    }

    /**
     * Teste les points suivants sur les variables d'un objet transactionnel (lors d'une restauration) :
     * <ul>
     * <li>Si la valeur d'une variable primitive est changée, elle est rétablie.</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void primitifRestoreTest() throws Exception {

	MyTransactionnable mt = new MyTransactionnable();
	assertTrue(mt.primitif_y == 0);
	assertTrue(mt.primitif_b == true);

	BackupManager bm = new BackupManager(mt);

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
	    System.err.flush();
	    // et on tente le restore
	    try {
		bm.restore();
		System.out.println(" But restored.");
		System.out.flush();
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
     * 
     * @throws Exception
     */
    @Test
    public void objectNonTransactionnableTest() throws Exception {

	MyTransactionnable mt = new MyTransactionnable();

	// on enregistre les références
	Titi titi = mt.object_titi;
	Integer integer = mt.object_x;
	Tata tata = mt.transactionnable_tata;

	assertTrue(mt.object_x == integer);
	assertTrue(mt.object_x.equals(0));

	assertTrue(mt.object_titi == titi);
	assertTrue(mt.object_titi.x.equals(0));

	assertTrue(mt.transactionnable_tata == tata);
	assertTrue(mt.transactionnable_tata.equals(tata));

	BackupManager bm = new BackupManager(mt);

	mt.modifyObjects();
	// object_x à changé de référence et de valeur
	assertTrue(mt.object_x != integer);
	assertTrue(mt.object_x.equals(2));

	// object_titi n'a pas changé de référence mais de valeur
	assertTrue(mt.object_titi == titi);
	assertTrue(!mt.object_titi.x.equals(0));

	assertTrue(mt.transactionnable_tata != tata);
	assertTrue(!mt.transactionnable_tata.equals(tata));

	try {
	    mt.failMethod();
	} catch (MethodException e) {

	    System.err.print(e.getMessage());
	    System.err.flush();
	    // on tente le restore
	    try {
		bm.restore();
		System.out.println(" But restored.");
		System.out.flush();
	    } catch (Exception e2) {
		// ici, le restore a aussi raté
		System.out.println();
		throw new BackupException("Backup fail. " + e2.getMessage());
	    }
	}

	// la référence de object_x a été rétablie, et donc la valeur est
	// redevenue la même
	assertTrue(mt.object_x == integer);
	assertTrue(mt.object_x.equals(0));

	/*
	 * la référence de object_titi est toujours la même, mais la valeur reste changée car l'objet est non transactionnel
	 */
	assertTrue(mt.object_titi == titi);
	assertTrue(!mt.object_titi.x.equals(0));

	assertTrue(mt.transactionnable_tata == tata);
	assertTrue(mt.transactionnable_tata.equals(tata));
    }

    /**
     * Teste les points suivants sur les variables d'un objet transactionnel (lors d'une restauration) :
     * <ul>
     * <li>Si la référence vers un tableau est changée, alors elle est rétablie</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void tableTest() throws Exception {

	MyTransactionnable mt = new MyTransactionnable();
	int[] table = mt.table_t;
	assertTrue(mt.table_t == table);
	assertTrue(mt.table_t[0] == 2);
	assertTrue(mt.table_t[1] == 1);

	BackupManager bm = new BackupManager(mt);

	mt.modifyTable();
	assertTrue(mt.table_t != table);
	assertTrue(mt.table_t[0] != 2);
	assertTrue(mt.table_t[1] != 1);

	try {
	    mt.failMethod();
	} catch (MethodException e) {

	    System.err.print(e.getMessage());
	    System.err.flush();
	    // et on tente le restore
	    try {
		bm.restore();
		System.out.println(" But restored.");
		System.out.flush();
	    } catch (Exception e2) {
		// ici, le restore a aussi raté
		System.out.println();
		throw new BackupException("Backup fail. " + e2.getMessage());
	    }
	}

	assertTrue(mt.table_t == table);
	assertTrue(mt.table_t[0] == 2);
	assertTrue(mt.table_t[1] == 1);
    }

    /**
     * Teste les points suivants sur les variables d'un objet transactionnel (lors d'une restauration) :
     * <ul>
     * <li>Si les valeurs ou références d'une classe mère sont modifiés, alors elles sont rétablies</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void heritageTest() throws Exception {

	MyTransactionnable mt = new MyTransactionnable();

	int x = mt.x;
	Tata tata = mt.getTata();
	Double myDouble = mt.getDouble();
	Double mySuperDouble = mt.getSuperDouble();

	assertTrue(mt.x == x);
	assertTrue(mt.getTata() == tata);
	assertTrue(mt.getDouble() == myDouble);
	assertTrue(mt.getSuperDouble() == mySuperDouble);

	BackupManager bm = new BackupManager(mt);

	mt.modifyMotherField();

	assertTrue(mt.x != x);
	assertTrue(!mt.getTata().equals(tata));
	assertTrue(mt.getDouble() != myDouble);
	assertTrue(mt.getSuperDouble() != mySuperDouble);

	try {
	    mt.failMethod();
	} catch (MethodException e) {

	    System.err.print(e.getMessage());
	    System.err.flush();
	    // et on tente le restore
	    try {
		bm.restore();
		System.out.println(" But restored.");
		System.out.flush();
	    } catch (Exception e2) {
		// ici, le restore a aussi raté
		System.out.println();
		throw new BackupException("Backup fail. " + e2.getMessage());
	    }
	}

	assertTrue(mt.x == x);
	assertTrue(mt.getTata() == tata);
	assertTrue(mt.getDouble() == myDouble);
	assertTrue(mt.getSuperDouble() == mySuperDouble);
    }
}
