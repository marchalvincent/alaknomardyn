package fr.upmc.ta.aladyn.tests.objects;

public class MySuperMother {

    public double d;

    public MySuperMother() {
	super();
	d = 1.1;
    }

    protected void modifyDouble() {
	d = 2.2;
    }

    public double getSuperDouble() {
	return d;
    }
}
