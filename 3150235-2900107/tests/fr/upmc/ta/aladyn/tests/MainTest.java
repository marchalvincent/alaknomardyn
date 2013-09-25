package fr.upmc.ta.aladyn.tests;

import java.awt.Point;

import fr.upmc.ta.aladyn.backup.BackupManager;

public class MainTest {

	public static void main(String[] args) {

		try {
			Point point = new Point(10, 5);
			System.out.println("point à la création : X " + point.getX()
					+ " Y " + point.getY());

			System.out.println("Réalisation du backup");
			BackupManager<Point> backUp = new BackupManager<>();
			backUp.backup(point);
			Point back = (Point) backUp.getBackup();
			System.out.println("Backup : X " + back.getX() + " Y "
					+ back.getY());

			point.setLocation(3, 4);
//			point.setY(4);
			System.out.println("point apres modification : X " + point.getX()
					+ " Y " + point.getY());

			backUp.restore(point);
			System.out.println("point apres le restore : X " + point.getX()
					+ " Y " + point.getY());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
