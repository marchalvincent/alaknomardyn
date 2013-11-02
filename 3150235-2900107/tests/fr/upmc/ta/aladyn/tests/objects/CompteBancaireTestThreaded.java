package fr.upmc.ta.aladyn.tests.objects;


public class CompteBancaireTestThreaded extends Thread {

    private boolean fail;
    
    public CompteBancaireTestThreaded() {
	super();
	this.fail = false;
    }
    
    @Override
    public void run() {

	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100) {
	    this.fail = true;
	    return;
	}

	try {
	    michel.wrongTransfertTransactionnable(vincent, 50);
	} catch (CompteBancaireException e) {
	    // la méthode est transactionnable, le solde doit avoir été rétabli
	    if (vincent.getSolde() != 0 || michel.getSolde() != 100) {
		    this.fail = true;
		    return;
	    }
	}

	try {
	    michel.wrongTransfert(vincent, 50);
	} catch (CompteBancaireException e) {
	    // la méthode n'est pas transactionnable, le solde de michel a été débité mais celui de vincent non crédité !
	    if (vincent.getSolde() != 0 || michel.getSolde() != 50) {
		    this.fail = true;
		    return;
	    }
	}
    }
    
    public boolean hasFailed() {
	return fail;
    }
}
