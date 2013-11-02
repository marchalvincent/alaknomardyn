package fr.upmc.ta.aladyn.tests.objects;

public class SiteAchatThreaded extends Thread {

    private boolean hasFail = true;
    public boolean hasFailed() {
	return hasFail;
    }

    @Override
    public void run()
    {
	System.out.println("test : " + String.valueOf(Thread.currentThread().getId()));

	//Création du site web
	siteAchat amazon = new siteAchat();

	//Remplissage du site web
	amazon.setStock("velo jaune");
	amazon.setStock("une voiture noir");
	amazon.setStock("chat roux");
	amazon.setStock("chat violet");
	amazon.setStock("un gros chien noir");

	//Préremplissage du panier
	amazon.setAchat("velo jaune");
	amazon.setAchat("une voiture noir");


	// test de vérification
	if (amazon.getNbArticle() != 2){
	    hasFail = false;
	    return;
	}

	try {
	    amazon.AchatTransactionnable("chat roux");
	} catch (SiteAchatException e) {
	    // la méthode est transactionnable, l'ajout de l'achat du "chat roux" dans le panier ne c'est pas effectué 
	    if (amazon.getNbArticle() != 2){
		hasFail = false;
		return;
	    }
	}


	try {
	    amazon.setAchatFail("chat violet");
	} catch (SiteAchatException e) {
	    // la méthode n'est transactionnable pas, l'ajout de l'achat du "chat violet" dans le panier c'est effectué 
	    if (amazon.getNbArticle() != 3){
		hasFail = false;
		return;
	    }

	}

	try {
	    amazon.stockFail();
	} catch (SiteAchatException e) {
	    //
	    if (amazon.getNbArticle() != 3){
		hasFail = false;
		return;
	    }

	}

	amazon.setStock();
	if (amazon.getNbArticle() != 0){
		hasFail = false;
		return;
	    }

    }
}
