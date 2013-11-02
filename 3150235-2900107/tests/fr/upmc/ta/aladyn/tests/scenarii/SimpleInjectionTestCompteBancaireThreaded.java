package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

public class SimpleInjectionTestCompteBancaireThreaded {

    public static void main(String[] args) throws CompteBancaireException {

	CompteBancaireTestThreaded t1 = new CompteBancaireTestThreaded();
	CompteBancaireTestThreaded t2 = new CompteBancaireTestThreaded();
	t1.start();
	t2.start();
	
	try {
	    t1.join();
	} catch (InterruptedException e1) {
	    e1.printStackTrace();
	}
	try {
	    t2.join();
	} catch (InterruptedException e1) {
	    e1.printStackTrace();
	}
	
	if (t1.hasFail() || t2.hasFail()) {
	    throw new CompteBancaireException();
	}
    }
}
