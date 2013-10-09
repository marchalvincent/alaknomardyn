package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.BackupException;
import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.backup.BackupManager;

public class MyPointTest {

	@Test
	public void simpleTestFailMethod() throws Exception {

		MyPoint point = new MyPoint(10, 5, 3, 4);
		assertTrue(point.getX() == 10);
		assertTrue(point.getY() == 5);
		assertTrue(point.getTab0() == 3);
		assertTrue(point.getTab1() == 4);
		BackupManager<MyPoint> backUp = new BackupManager<>();
		backUp.save(point);
		try {
			// on fait appel à la méthode critique
			point.failMethod();
		} catch (MethodException e) {
			// on check que le point a bien changé
			assertTrue(point.getX() != 10);
			assertTrue(point.getY() != 5);
			assertTrue(point.getTab0() != 3);
			assertTrue(point.getTab1() != 4);
			// en cas d'erreur, on print le message
			System.err.print(e.getMessage());
			// et on tente le restore
			try {
				backUp.restore(point);
				System.out.println(" But restored.");
			} catch (Exception e2) {
				// ici, le restore a aussi raté
				System.out.println();
				throw new BackupException("Backup fail. " + e2.getMessage());
			}
		}
		// assertTrue(point.getX() == 10);
		// assertTrue(point.getY() == 5);
		// assertTrue(point.getTab0() == 3);
		// assertTrue(point.getTab1() == 4);
	}
}
