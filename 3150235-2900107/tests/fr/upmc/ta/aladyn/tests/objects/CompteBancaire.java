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
     * Un transfert d'argent entre compte non sécurisé (non transactionnable) qui n'a pas lieu pour une raison d'exception levée.
     * Du coup les montant des comptes ne sont plus valable à la fin de la méthode.
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
     * Un transfert d'argent entre compte sécurisé (transactionnable) qui n'a pas lieu pour une raison d'exception levée.
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
     * Un transfert d'argent entre compte sécurisé (transactionnable).
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
     * Un transfert d'argent entre compte sécurisé (transactionnable).
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
     * Imaginons un transfert d'argent entre compte qui aurait besoin d'une caution avant d'effectuer le transfert. La banque risque de perdre
     * quelques clients... ce scénario est inventé pour tester le cas de plusieurs transactions imbriquées.
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
	    // le transfert a raté ! les montant sont désormais rétablis
	    if (this.getSolde() != (thisSoldeAfterSurety) || compteASolder.getSolde() != compteSolde) {
		throw e;
	    }
	}
	
	// on rétablit quand même la caution sinon on va avoir encore moins de client
	this.crediter(10);
    }
}
