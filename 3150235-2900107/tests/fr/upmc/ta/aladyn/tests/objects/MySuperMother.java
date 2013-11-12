package fr.upmc.ta.aladyn.tests.objects;

/**
 * Une classe de test permettant de vérifier la bonne sauvegarde des champs hérités.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
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
