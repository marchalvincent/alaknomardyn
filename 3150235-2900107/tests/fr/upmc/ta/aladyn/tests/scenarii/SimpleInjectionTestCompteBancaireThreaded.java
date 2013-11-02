package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.tests.objects.SiteAchatException;
import fr.upmc.ta.aladyn.tests.objects.SiteAchatThreaded;

public class SimpleInjectionTestCompteBancaireThreaded {

    public static void main(String[] args) throws SiteAchatException {

	Thread t1 = new CompteBancaireTestThreaded();
	Thread t2 = new CompteBancaireTestThreaded();
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
	
	if (t1.isInterrupted() || t2.isInterrupted())
	    throw new SiteAchatException();
    }
}
