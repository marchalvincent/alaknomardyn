package fr.upmc.ta.aladyn.tests.objects;

public class MyMother extends MySuperMother {

    public int x;
    protected Tata tata;
    private Double myDouble;

    public MyMother() {
	super();
	x = 2;
	tata = new Tata(1);
	myDouble = 5.2;
    }

    public void modifyMotherField() {
	super.modifyDouble();
	x = 4;
	tata = new Tata(10);
	myDouble = 6.6;
    }

    public Tata getTata() {
	return tata;
    }

    public Double getDouble() {
	return myDouble;
    }
}
