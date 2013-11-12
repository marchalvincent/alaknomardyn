package fr.upmc.ta.aladyn;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.injection.InjectionMain;

/**
 * Cette classe permet de tester les mains des injections de code à l'aide de programme décrivant des scénarii plus ou moins
 * complexes.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InjectionScenariiTests {

    @Test
    public void injectionTestCompteBancaire() {
	String[] args = { "fr.upmc.ta.aladyn.injection.InjectionTestCompteBancaire" };
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void injectionTestCompteBancaire2() {
	String[] args = { "fr.upmc.ta.aladyn.injection.InjectionTestCompteBancaire2" };
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void injectionTestCompteBancaire3() {
	String[] args = { "fr.upmc.ta.aladyn.injection.InjectionTestCompteBancaire3" };
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void injectionTestSiteWeb() {
	String[] args = { "fr.upmc.ta.aladyn.injection.InjectionTestSiteWeb" };
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void injectionTestThreaded() {
	String[] args = { "fr.upmc.ta.aladyn.injection.InjectionTestThreaded" };
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
}
