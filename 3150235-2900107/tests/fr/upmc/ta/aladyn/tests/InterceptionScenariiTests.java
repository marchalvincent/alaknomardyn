package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.upmc.ta.aladyn.InterceptionMain;

public class InterceptionScenariiTests {

    @Test
    public void inteceptionTestSiteWeb() {
	String[] args = {"fr.upmc.ta.aladyn.tests.scenarii.SimpleInterceptionTestSiteWeb"};
	try {
	    InterceptionMain.main(args);
	} catch (Throwable e) {
	    // si une exception est sortie du main, ce n'est pas normal
	    assertTrue(false);
	}
    }
    

}
