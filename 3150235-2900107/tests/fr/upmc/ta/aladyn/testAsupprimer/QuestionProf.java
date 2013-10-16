package fr.upmc.ta.aladyn.testAsupprimer;

import fr.upmc.ta.aladyn.Transactionnable;

public class QuestionProf {

    @Transactionnable
    public void m2() {
	try {
	    // code + exception
	} catch (Exception e) {
	    // INJECT : restore? oui je pense...
	    // remise sur les rails
	}
	// code normal
	//
    }

    @Transactionnable
    public void m0() {
	try {
	    // code + exception
	} catch (Exception e) {
	    // traitement perso
	}
    }

    @Transactionnable
    public void m4() {
	try {
	    try {
		// code + exception
	    } catch (Exception e) {

	    }
	    // code
	} catch (Exception e) {

	}
    }
}
