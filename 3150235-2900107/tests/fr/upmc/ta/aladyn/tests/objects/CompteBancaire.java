package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Un compte bancaire qui est {@link Transactionnable} et donc les transferts d'argent se doivent d'être cohérents.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
@Transactionnable
public class CompteBancaire {

    private double solde = 0.0;
    
    public CompteBancaire() {
	this(0.0);
    }
    
    public CompteBancaire(double montant) {
	super();
	this.solde = montant;
    }
    
    public double getSolde() {
	return solde;
    }
    
    private void setSolde(double nouveauSolde) {
	this.solde = nouveauSolde;
    }
    
    /**
     * Methode private pour solder le compte faisant appel au Setter pour que l'injection de code marche
     * @param credit
     */
    private void crediter(double credit) {
	this.setSolde(this.getSolde() + credit);
    }
    
    /**
     * Methode private pour débiter le compte faisant appel au Setter pour que l'injection de code marche
     * @param debit
     */
    private void debiter(double debit) {
	this.setSolde(this.getSolde() - debit);
    }
    
    /**
     * Transfert qui rate et qui fausse les données.
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    public void wrongTransfert(CompteBancaire compteASolder, double montant) throws CompteBancaireException {
	this.debiter(montant);
	throw new CompteBancaireException();
	// ici un problème est survenu, le crédit n'a pas été effectué !
//	compteASolder.crediter(montant);
    }

    /**
     * Transfert qui rate mais garde les données cohérentes.
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    @Transactionnable
    public void wrongTransfertTransactionnable(CompteBancaire compteASolder, double montant) throws CompteBancaireException {
	this.debiter(montant);
	throw new CompteBancaireException();
	// ici un problème est survenu, le crédit n'a pas été effectué !
//	compteASolder.crediter(montant);
    }

    /**
     * Transfert d'argent qui rate, qui retente sa chance et réussi mais les données sont fausses.
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    public void wrongTransfertWithInternalException(CompteBancaire compteASolder, double montant) {
	this.debiter(montant);
	try {
	    // imaginons une erreur de réseau !
	    throw new CompteBancaireException();
	} catch (CompteBancaireException e) {
	    /*
	     * comme la méthode n'est pas transactionnable, le débit est faussé, imaginons aucun traitement. Du coup, le compte "this"
	     * n'a plus du tout le bon montant...
	     */
	}
	
	// puis on retente le transfert, qui réussit malgré le montant de "this" incorrect
	this.debiter(montant);
	compteASolder.crediter(montant);
    }

    /**
     * Transfert d'argent qui rate, qui retente sa chance et réussi et garde les données cohérentes.
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    @Transactionnable
    public void transfertTransactionnableWithInternalException(CompteBancaire compteASolder, double montant) {
	this.debiter(montant);
	try {
	    // imaginons une erreur de réseau !
	    throw new CompteBancaireException();
	} catch (CompteBancaireException e) {
	    // comme la méthode est transactionnable, le débit est automatiquement annulé, on imagine qu'il n'y a pas de traitement manuel
	}
	
	// puis on retente le transfert
	this.debiter(montant);
	compteASolder.crediter(montant);
    }
    
    /**
     * Transaction (m1) qui en appelle une autre (m2). m2 échoue mais est correctement rétablie puis m1 se fini normalement.
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    @Transactionnable
    public void transfertTransactionnableWithSurety(CompteBancaire compteASolder, double montant) throws CompteBancaireException {
	
	// la caution de 10 euros
	this.debiter(10);

	double thisSoldeAfterSurety = this.getSolde();
	double compteSolde = compteASolder.getSolde();
	// le vrai transfert
	try {
	    this.wrongTransfertTransactionnable(compteASolder, montant);
	} catch (CompteBancaireException e) {
	    // le transfert a raté ! les montant sont normalement rétablis car la méthode appelée est Transactionnable
	    if (this.getSolde() != (thisSoldeAfterSurety) || compteASolder.getSolde() != compteSolde) {
		throw e;
	    }
	}
	
	// on rétablit quand même la caution sinon on va avoir encore moins de client
	this.crediter(10);
    }
    
    /**
     * Transaction (m1) qui en appelle une autre (m2). m2 réussit mais m1 échoue et est rétablie.
     * 
     * @param compteASolder
     * @param montant
     * @throws CompteBancaireException Une exception est levée pendant le transfert d'argent
     */
    @Transactionnable
    public void imbricatedTransfertTransactionnable(CompteBancaire compteASolder, double montant) throws CompteBancaireException {
	
	this.debiter(montant);
	
	// le vrai transfert qui réussit (la méthode imbriquée)
	this.transfertTransactionnableWithInternalException(compteASolder, montant);

	// puis un morceau de code qui lève une exception, seul le débit fait dans la méthode actuelle doit être rétablie
	throw new CompteBancaireException();
    }
}
