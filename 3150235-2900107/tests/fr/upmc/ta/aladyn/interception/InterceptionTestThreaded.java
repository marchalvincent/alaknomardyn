package fr.upmc.ta.aladyn.interception;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaireTestThreaded;
import fr.upmc.ta.aladyn.tests.objects.SiteAchatException;
import fr.upmc.ta.aladyn.tests.objects.SiteAchatThreaded;

/**
 * Cette classe teste l'interception d'appel de méthodes avec avec plusieurs file d'éxecution. Pour chaque thread un main manipule
 * des objets transactionnables distincts.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InterceptionTestThreaded {

    public static void main(String[] args) throws SiteAchatException {

	SiteAchatThreaded t1 = new SiteAchatThreaded();
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

	if (t1.hasFailed() || t2.hasFailed())
	    throw new SiteAchatException();
    }
}
