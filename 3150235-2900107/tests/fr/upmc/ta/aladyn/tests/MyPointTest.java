package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

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
					System.out.println(" But corrected.");
				} catch (Exception e2) {
					// ici, le restore a aussi raté
					System.out.println();
					throw new BackupException("Backup fail. " + e2.getMessage());
				}
			}
			
			assertTrue(point.getX() == 10);
			assertTrue(point.getY() == 5);
			System.out.println(point.getTab0());
			assertTrue(point.getTab0() == 3);
			assertTrue(point.getTab1() == 4);
	}
	
	
	public void test() {
		MyPoint p = new MyPoint();
		Field[] fields1 = p.getClass().getDeclaredFields();
		Field[] fields2 = p.getClass().getDeclaredFields();

		//TODO
		for (int i = 0; i < fields1.length; i++) {
			assertTrue(fields1[i].equals(fields2[i]));
		}

		for (int i = 0; i < fields1.length; i++) {
			assertTrue(fields1[i] == fields2[i]);
		}
	}
}
