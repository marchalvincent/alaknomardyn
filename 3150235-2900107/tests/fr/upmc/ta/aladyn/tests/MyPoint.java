package fr.upmc.ta.aladyn.tests;

import fr.upmc.ta.aladyn.MethodException;


public class MyPoint {

	private double x, y;
	private int[] tab;

	public MyPoint(double x_value, double y_value, int tab0, int tab1) {
		super();
		x = x_value;
		y = y_value;
		tab = new int[2];
		tab[0] = tab0;
		tab[1] = tab1;
	}

	public MyPoint() {
		this(0, 0, 2, 3);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getTab0() {
		return tab[0];
	}
	
	public int getTab1() {
		return tab[1];
	}

	public void failMethod() throws MethodException {
		this.x ++;
		this.y --;
		this.tab[0] += 5;
		this.tab[1] -= 5;
		throw new MethodException("Method fail.");
	}
}
