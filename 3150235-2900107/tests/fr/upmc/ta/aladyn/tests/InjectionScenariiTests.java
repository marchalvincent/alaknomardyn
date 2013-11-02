package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.InjectionMain;

/**
 * Cette classe permet de tester les mains des injections (resp. interceptions) de code (resp. d'appel de méthode) à l'aide de programme
 * décrivant des scénarii plus ou moins complexes.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class InjectionScenariiTests {

    @Test
    public void injectionTestCompteBancaire() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestCompteBancaire"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    
    @Test
    public void injectionTestCompteBancaire2() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestCompteBancaire2"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    
    @Test
    public void injectionTestCompteBancaire3() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestCompteBancaire3"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    
    @Test
    public void injectionTestCompteBancaireThreaded() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestCompteBancaireThreaded"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void injectionTestSiteWeb() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestSiteWeb"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
   
    
    
}
