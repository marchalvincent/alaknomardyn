package fr.upmc.ta.aladyn.injection;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaire;
import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

/**
 * Cette classe teste l'injection de code avec l'exemple de transfert d'argent sur des comptes bancaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InjectionTestCompteBancaire3 {

    public static void main(String[] args) throws Exception {

	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100)
	    throw new CompteBancaireException();

	michel.transfertTransactionnableWithSurety(vincent, 50);
	if (vincent.getSolde() != 0 || michel.getSolde() != 100)
	    throw new CompteBancaireException();
	try {
	    michel.imbricatedTransfertTransactionnable(vincent, 50);
	} catch (CompteBancaireException e) {
	    if (vincent.getSolde() != 0 || michel.getSolde() != 100)
		throw new CompteBancaireException();
	}
    }
}
