package fr.upmc.ta.aladyn.interception;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaire;
import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

/**
 * Cette classe teste l'interception d'appel de méthodes avec l'exemple de transfert d'argent sur des comptes bancaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class SimpleInterceptionTestCompteBancaire {

    public static void main(String[] args) throws Exception {

	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100)
	    throw new CompteBancaireException();

	try {
	    michel.wrongTransfertTransactionnable(vincent, 50);
	} catch (CompteBancaireException e) {
	    // la méthode est transactionnable, le solde doit avoir été rétabli
	    if (vincent.getSolde() != 0 || michel.getSolde() != 100)
		throw new CompteBancaireException();
	}

	try {
	    michel.wrongTransfert(vincent, 50);
	} catch (CompteBancaireException e) {
	    // la méthode n'est pas transactionnable, le solde de michel a été débité mais celui de vincent non crédité !
	    if (vincent.getSolde() != 0 || michel.getSolde() != 50)
		throw new CompteBancaireException();
	}

    }

}
