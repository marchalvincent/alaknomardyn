package fr.upmc.ta.aladyn.testAsupprimer;

import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class MyPoint {

    private Double x, y;
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

    public MyPoint(int x, int y) {
	this(x, y, 0, 0);
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
	this.x++;
	this.y--;
	this.tab[0] += 5;
	this.tab[1] -= 5;
	throw new MethodException("Method fail.");
    }
    
    @Transactionnable
    public void transactionnableMethod() throws Exception {
	System.out.println("hello you");
	try {
	    System.out.println("contenu du try normal");
	    throw new Exception();
	} catch (Exception e) {
	    System.out.println("ici mon catch perso");
	}
	throw new Exception("MyException");
    }
}
