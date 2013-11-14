package fr.upmc.ta.aladyn.interception;

import fr.upmc.ta.aladyn.tests.objects.CompteBancaire;
import fr.upmc.ta.aladyn.tests.objects.CompteBancaireException;

/**
 * Cette classe teste l'interception d'appel de méthodes avec l'exemple de transfert d'argent sur des comptes bancaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InterceptionTestCompteBancaire2 {

    public static void main(String[] args) throws Exception {

	CompteBancaire vincent = new CompteBancaire();
	CompteBancaire michel = new CompteBancaire(100);

	// test de vérification
	if (vincent.getSolde() + michel.getSolde() != 100)
	    throw new CompteBancaireException();

	/*
	 * cette méthode fait 2 fois le débit et 1 fois le crédit. Comme une exception interne n'est pas gérée par 
	 * l'interception, le débit est bien effectué 2 fois et donc les données ne sont pas incohérentes
	 */
	michel.transfertTransactionnableWithInternalException(vincent, 50);
	if (vincent.getSolde() != 50 || michel.getSolde() != 0)
	    throw new CompteBancaireException();
    }
}
