package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.backup.BackupManager;
import fr.upmc.ta.aladyn.backup.MethodeCouranteManager;
import fr.upmc.ta.aladyn.tests.objects.TransactionnableClass;

/**
 * Classe de tests pour le {@link MethodeCouranteManager} qui se charge de garder en mémoire la stack des méthodes transactionnables
 * en cours et d'attribuer/restorer les backup de sauvegarde en interagissant avec cette stack.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class MethodeCouranteManagerTest {

    @Test
    public void testStack() throws BackupException {
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	assertTrue(true);
    }

    @Test(expected = BackupException.class)
    public void testStack3() throws BackupException {
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
    }

    @Test
    public void addBackupToEmptyStack() throws Exception {
	TransactionnableClass tata = new TransactionnableClass(10);
	BackupManager backup = new BackupManager(tata);

	// comme la stack est vide, le backup n'est pas gardé
	MethodeCouranteManager.instance.addBackupToCurrentMethod(backup);
	tata.x = 11;

	// le restore ne doit rien restore
	MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
	assertTrue(tata.x == 11);
    }

    /**
     * Teste le restore d'un objet par le {@link MethodeCouranteManager}
     * @throws Exception
     */
    @Test
    public void testRestore() throws Exception {

	TransactionnableClass tata = new TransactionnableClass(10);
	BackupManager bm = new BackupManager(tata);

	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.addBackupToCurrentMethod(bm);
	// modification de tata
	tata.x = 11;
	// puis restore, tata.x devrait être égal a 10
	MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
	assertTrue(tata.x == 10);
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
    }

    /**
     * teste qu'un restore appelé dans une méthode transactionnable imbriquée (m2) ne restore pas les objets de la méthode m1.
     * @throws Exception
     */
    @Test
    public void testRestore2() throws Exception {

	TransactionnableClass tata = new TransactionnableClass(10);
	BackupManager bm = new BackupManager(tata);

	// ajout de tata dans une première méthode 
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.addBackupToCurrentMethod(bm);

	// modification de tata 
	tata.x = 11;

	// on va dans une méthode imbriquée dans laquelle on restore
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.restoreBackupsOfLastMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();

	assertTrue(tata.x == 11);

	MethodeCouranteManager.instance.endOfTransactionnableMethod();
    }

    @Test
    public void testMultithreads() throws Exception {
	// TODO
    }
}
