package fr.upmc.ta.aladyn.tests.objects;

/**
 * Cette classe définit un scénario d'achat en ligne. Elle étend la classe {@link Thread} et donc peut être utilisée pour des
 * tests multi-threadés.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
public class SiteAchatThreaded extends Thread {

    private boolean hasFail = false;

    public boolean hasFailed() {
	return hasFail;
    }

    @Override
    public void run() {

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
	if (amazon.getNbArticle() != 2) {
	    hasFail = true;
	    return;
	}

	try {
	    amazon.achatTransactionnable("chat roux");
	} catch (SiteAchatException e) {
	    // la méthode est transactionnable, l'ajout de l'achat du "chat roux" dans le panier ne c'est pas effectué
	    if (amazon.getNbArticle() != 2) {
		hasFail = true;
		return;
	    }
	}

	try {
	    amazon.addAchatFail("chat violet");
	    System.out.println(amazon.getNbArticle());
	} catch (SiteAchatException e) {
	    // la méthode n'est transactionnable pas, l'ajout de l'achat du "chat violet" dans le panier c'est effectué
	    if (amazon.getNbArticle() != 3) {
		hasFail = true;
		return;
	    }

	}

	try {
	    amazon.stockFail();
	} catch (SiteAchatException e) {
	    //
	    if (amazon.getNbArticle() != 3) {
		hasFail = true;
		return;
	    }

	}

	amazon.setStock();
	if (amazon.getNbArticle() != 0) {
	    hasFail = true;
	    return;
	}

    }
}
