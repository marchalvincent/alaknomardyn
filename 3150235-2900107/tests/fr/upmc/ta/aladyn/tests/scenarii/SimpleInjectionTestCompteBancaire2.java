package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaire;
import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

/**
 * Cette classe teste l'injection de code avec l'exemple de transfert d'argent sur des comptes bancaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class SimpleInjectionTestCompteBancaire2 {

    public static void main(String[] args) throws Exception {
	
	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100)
	    throw new CompteBancaireException();

	michel.transfertTransactionnableWithInternalException(vincent, 50);
	if (vincent.getSolde() != 50 || michel.getSolde() != 50)
		throw new CompteBancaireException();
	
	// cette méthode est volontairement fausse pour montrer que sans l'injection cela pose un problème
	michel.wrongTransfertWithInternalException(vincent, 50);
	if (vincent.getSolde() != 100 || michel.getSolde() != -50)
	    throw new CompteBancaireException();
    }

}
