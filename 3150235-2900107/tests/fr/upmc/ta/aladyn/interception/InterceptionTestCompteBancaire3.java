package fr.upmc.ta.aladyn.interception;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaire;
import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

/**
 * Cette classe teste l'interception d'appel de méthodes avec l'exemple de transfert d'argent sur des comptes bancaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InterceptionTestCompteBancaire3 {

    public static void main(String[] args) throws Exception {

	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100)
	    throw new CompteBancaireException();

	// Cette méthode contient une méthode qui échoue mais qui doit être rétablie par l'interception
	michel.transfertTransactionnableWithSurety(vincent, 50);
	if (vincent.getSolde() != 0 || michel.getSolde() != 100)
	    throw new CompteBancaireException();
	
	try {
	    // avec cette méthode qui échoue, l'état initial doit être rétabli
	    michel.imbricatedTransfertTransactionnable(vincent, 50);
	} catch (CompteBancaireException e) {
	    if (vincent.getSolde() != 0 || michel.getSolde() != 100)
		throw new CompteBancaireException();
	}
    }
}
