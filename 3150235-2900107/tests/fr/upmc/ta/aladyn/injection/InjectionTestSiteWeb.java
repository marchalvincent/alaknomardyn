package fr.upmc.ta.aladyn.injection;

import fr.upmc.ta.aladyn.tests.objects.SiteAchat;
import fr.upmc.ta.aladyn.tests.objects.SiteAchatException;

/**
 * Cette classe teste l'injection de code avec l'exemple d'achat d'article sur un site en ligne.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class InjectionTestSiteWeb {

    public static void main(String[] args) throws Exception {

	// Création du site web
	SiteAchat amazon = new SiteAchat();

	// Remplissage du site web
	amazon.setStock("velo jaune");
	amazon.setStock("une voiture noir");
	amazon.setStock("chat roux");
	amazon.setStock("chat violet");
	amazon.setStock("un gros chien noir");

	// Préremplissage du panier
	amazon.setAchat("velo jaune");
	amazon.setAchat("une voiture noir");

	// test de vérification
	if (amazon.getNbArticle() != 2)
	    throw new SiteAchatException();

	try {
	    amazon.achatTransactionnable("chat roux");
	} catch (SiteAchatException e) {
	    // la méthode est transactionnable, l'ajout de l'achat du "chat roux" dans le panier ne c'est pas effectué
	    if (amazon.getNbArticle() != 2)
		throw new SiteAchatException();
	}

	try {
	    amazon.addAchatFail("chat violet");
	} catch (SiteAchatException e) {
	    // la méthode n'est transactionnable pas, l'ajout de l'achat du "chat violet" dans le panier c'est effectué
	    if (amazon.getNbArticle() != 3)
		throw new SiteAchatException();
	}

	try {
	    amazon.stockFail();
	} catch (SiteAchatException e) {
	    if (amazon.getNbArticle() != 3)
		throw new SiteAchatException();
	}

	amazon.setStock();
	if (amazon.getNbArticle() != 0)
	    throw new SiteAchatException();
    }
}
