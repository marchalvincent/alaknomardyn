package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class Person {

    private String nom;
    private String prenom;
    private int age;
    public String email;

    public Person(String nom, String prenom, int age, String email){
	this.nom = nom;
	this.prenom = prenom;
	this.age = age;
	this.email = email;
    }
    
    public void setNom(String nom) {
	this.nom = nom;
    }
    public String getNom(){
	return nom;
    }
    
    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }
    public String getPrenom(){
	return prenom;
    }
    
    public void setAge(int age) {
	this.age = age;
    }
    public int getAge(){
	return age;
    }
    
    @Transactionnable
    public void setAgeFail(int age) throws Exception {
	this.age = age;
	throw new Exception("mon exception");
    }
    
    public String toString()
    {
	return "Nom : " + nom + " Prenom :  " + prenom + " Age : " + age + " Email : " + email;
    }

}
