package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.Transactionnable;

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
	 * Cette méthode change la référence de la variable object_x et le contenu de la variable object_titi
	 */
	public void modifyObjectNonTransactionnable() {
		object_x = new Integer(2);
		object_titi.x ++;
	}
	
	public void modifyObjectTransactionnable() {
		object_x = new Integer(2);
		object_titi = new Titi();
	}
	
	public void failMethod() throws MethodException {
		throw new MethodException("Method fail.");
	}
}
