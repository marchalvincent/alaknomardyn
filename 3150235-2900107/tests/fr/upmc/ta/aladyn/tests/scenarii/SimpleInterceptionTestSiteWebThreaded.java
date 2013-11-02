package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.tests.objects.SiteAchatException;
import fr.upmc.ta.aladyn.tests.objects.SiteAchatThreaded;


/**
 * TODO
 * Cette classe teste l'injection de code avec l'exemple d'achat d'article sur un site en ligne.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */

public class SimpleInterceptionTestSiteWebThreaded {

    public static void main(String[] args) throws SiteAchatException {

	System.out.println("test : " + String.valueOf(Thread.currentThread().getId()));
	Thread t1 = new SiteAchatThreaded();
	Thread t2 = new SiteAchatThreaded();
	t1.start();
	t2.start();
	
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	if (t1.isInterrupted() || t2.isInterrupted())
	    throw new SiteAchatException();
    }

    
}
