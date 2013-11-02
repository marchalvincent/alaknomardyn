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
    public void injectionTest() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestMain"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    
    @Test
    public void injectionTest2() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestMain2"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    
    @Test
    public void injectionTest3() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestMain"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }

    @Test
    public void inteceptionTestSiteWeb() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInjectionTestSiteWeb"};
	try {
	    InjectionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
   
    
    
}
