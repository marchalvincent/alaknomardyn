package fr.upmc.ta.aladyn;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.upmc.ta.aladyn.interception.InterceptionMain;

/**
 * Cette classe permet de tester les mains des interceptions d'appels de méthode à l'aide de programme décrivant des scénarii plus
 * ou moins complexes.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InterceptionScenariiTests {

    @Test
    public void inteceptionTestCompteBancaire() {
	String[] args = { "fr.upmc.ta.aladyn.interception.InterceptionTestCompteBancaire" };
	try {
	    InterceptionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void inteceptionTestSiteWeb() {
	String[] args = { "fr.upmc.ta.aladyn.interception.InterceptionTestSiteWeb" };
	try {
	    InterceptionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void inteceptionSimpleTestThreaded() {
	String[] args = { "fr.upmc.ta.aladyn.interception.InterceptionTestThreaded" };
	try {
	    InterceptionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

}
