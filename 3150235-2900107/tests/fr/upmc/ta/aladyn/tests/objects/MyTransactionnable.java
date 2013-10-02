package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.Transactionnable;

/**
 * 
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
@Transactionnable
public class MyTransactionnable {
	
	// transactionnable
	public Tata transactionnable_tata;
	
	// non transactionnable
	public Integer object_x;
	public Titi object_titi;
	
	// primitifs
	public int primitif_y;
	public boolean primitif_b;
	
	public MyTransactionnable() {
		super();
		
		transactionnable_tata = new Tata();
		
		object_x = new Integer(0);
		object_titi = new Titi();
		
		primitif_y = 0;
		primitif_b = true;
	}
	
	public void modifyPrimitif() {
		primitif_y ++;
		primitif_b = false;
	}
	
	/**
	 * Cette méthode change les valeurs et/ou références des objets de la classe
	 */
	public void modifyObjects() {
		object_x = new Integer(2);
		object_titi.x ++;
		transactionnable_tata = new Tata(2);
	}
	
	public void failMethod() throws MethodException {
		throw new MethodException("Method fail.");
	}
}
