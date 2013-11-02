package fr.upmc.ta.aladyn.tests.objects;

import java.util.ArrayList;
import java.util.List;

import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Un site d'achat qui est {@link Transactionnable} et donc les achats se doivent d'être cohérents.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
@Transactionnable
public class siteAchat {

    private List<String> stock;
    private List<String> panier;
    
    /**
     * Constructeur
     */
    public siteAchat() {
	this.panier = new ArrayList<String>();
	this.stock = new ArrayList<String>();
    }
    
    /**
     * Méthode permettant de compter le nombre d'article présent dans votre panier
     * @return le nombre d'article
     */
    public int getNbArticle(){
	return getPanier().size();
    }
    
    /**
     * Getter
     * @return le panier sous la forme d'une list de String
     */
    private List<String> getPanier() {
	return panier;
    }
    
    /**
     * Getter
     * @return le panier sous la forme d'une list de String
     */
    public List<String> getStock() {
        return stock;
    }
    
    /**
     * Methode public permettant d'ajouter un achat à votre panier
     * @param article à ajouter au panier
     */
    public void setAchat(String achat) {
	List<String> tmp = new ArrayList<String>();
	for (String article : panier) {
	    tmp.add(article);
	}
	tmp.add(achat);
	this.panier = tmp;
    }
    
    /**
     * Methode public permettant d'ajouter un article au stock du site web
     * @param article à ajouter stock
     */
    public void setStock(String article) {
	List<String> tmp = new ArrayList<String>();
	for (String art : stock) {
	    tmp.add(art);
	}
	tmp.add(article);
	this.stock = tmp;
	
    }
    
    /**
     * Methode privé pemettant de retirer un achat du panier
     * @param achat à retirer du panier
     */
    public void setRetirerAchatPanier(String achat) {
	List<String> tmp = new ArrayList<String>();
	for (String art : panier) {
	    if (achat != art)
		tmp.add(art);
	}
	this.panier = tmp;
    }
    
    /**
     * Methode privé pemettant de retirer un article des stocks
     * @param article à retirer des stocks
     */
    public void setRetirerAchatStock(String article) {
	List<String> tmp = new ArrayList<String>();
	for (String achat : stock) {
	    if (article != achat)
		tmp.add(achat);
	}
	this.stock = tmp;
    }
  
   
    /**
     * Achat qui rate et qui fausse les données.
     * @param achat
     * @throws siteAchatException Une exception est levée pendant l'achat sur le site
     */
    public void setAchatFail(String achat) throws SiteAchatException {
	setAchat("un gros chien noir");
	throw new SiteAchatException();
	// ici une exception est lancé, l'achat d'un mauvais article est survenu
    }

    /**
     * Achat qui rate mais garde les données cohérentes.
     * @param achat à ajouter au panier
     * @throws SiteAchatException
     */
    @Transactionnable
    public void AchatTransactionnable(String achat) throws SiteAchatException {
	setAchat("un gros chien blanc");
	throw new SiteAchatException();
	// ici l'achat d'un mauvais article est survenu, le panier n'a pas ajouté le mauvais article !
    }
    
    /**
     * Méthode qui permet de vider le panier
     */
    public void viderPanier(){
	for (String achat : panier) {
	    setRetirerAchatPanier(achat);
	}
    }
    
    
    /**
     * Permet d'effctuer une commande et donc retirer les articles des stocks du site internet
     */
    public void setStock(){
	//On vide les stocks commandés
	for (String achat : panier) {
	    setRetirerAchatPanier(achat);
	}
	//On vide le panier
	viderPanier();
    }

    
     //Partie permettant d'effectuer un test Transactionnale :
     //m1 call m2, m2 fail => restore de m1
    
    /**
     * Méthode qui vide le panier avec une erreur
     */
    public void viderPanierFail() throws SiteAchatException{
	for (String achat : panier) {
	    setRetirerAchatPanier(achat);
	    throw new SiteAchatException();
	}
    }
    
    /**
     * Permet d'effctuer une commande et donc retirer les articles des stocks du site internet
     * Cette méthode lance un exception dans l'appel de la suppression du panier
     */
    @Transactionnable
    public void stockFail()throws SiteAchatException{
	//On vide les stocks commandés
	for (String achat : panier) {
	    setRetirerAchatStock(achat);
	}
	//On vide le panier
	viderPanierFail();
    }
    

    public String toString()
    {
	StringBuilder result = new StringBuilder();
	result.append("Votre list d'achat ce compose de : \n");
	for (String article : getPanier()) {
	    result.append(article + "\n");
	}
	return result.toString();
    }

}
