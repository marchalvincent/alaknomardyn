package fr.upmc.ta.aladyn.tests.objects;

/**
 * Une classe de test permettant de vérifier la bonne sauvegarde des champs hérités.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class MyMother extends MySuperMother {

    public int x;
    protected TransactionnableClass tata;
    private Double myDouble;

    public MyMother() {
	super();
	x = 2;
	tata = new TransactionnableClass(1);
	myDouble = 5.2;
    }

    public void modifyMotherField() {
	super.modifyDouble();
	x = 4;
	tata = new TransactionnableClass(10);
	myDouble = 6.6;
    }

    public TransactionnableClass getTata() {
	return tata;
    }

    public Double getDouble() {
	return myDouble;
    }
}
