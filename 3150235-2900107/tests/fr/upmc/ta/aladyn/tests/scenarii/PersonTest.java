package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.tests.objects.Person;

/**
 * Cette classe teste l'interception de code avec l'exemple site d'achat en ligne.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */

public class PersonTest {

    public static void main(String[] args) throws Exception {
	System.out.println("** Main de la classe Person, Classe Transactionnable**");
	System.out.println("Création d'une instance de l'objet Person");
	Person p = new Person("Marchal", "Vincent", 22, "mar.vin@gmail.com");
	System.out.println(p);
	//Test de sauvegarde et de restoration
	System.out.println("Appel d'une méthode transactionnable qui lance une exception");
	System.out.println("Appel de la méthode setAgeFail(10)");
	try{
	p.setAgeFail(10);
	}catch(Exception ex){
	    System.err.println(ex.getMessage());
	}
	System.out.println("Vérification de la restoration :");
	System.out.println(p);


    }
}
