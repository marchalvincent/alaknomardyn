package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.MethodException;
import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Cet objet transactionnable sert de test. Il contient tout type de champs.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
@Transactionnable
public class MyTransactionnable extends MyMother {

    // transactionnable
    public TransactionnableClass transactionnable_tata;

    // non transactionnable
    public Integer object_x;
    public NonTransactionnableClass object_titi;

    // primitifs
    public int primitif_y;
    public boolean primitif_b;

    // tableau
    public int[] table_t;

    public MyTransactionnable() {
	super();

	transactionnable_tata = new TransactionnableClass();

	object_x = new Integer(0);
	object_titi = new NonTransactionnableClass();

	primitif_y = 0;
	primitif_b = true;

	table_t = new int[2];
	table_t[0] = 2;
	table_t[1] = 1;
    }

    public void modifyPrimitif() {
	primitif_y++;
	primitif_b = false;
    }

    /**
     * Cette méthode change les valeurs et/ou références des objets de la classe
     */
    public void modifyObjects() {
	object_x = new Integer(2);
	object_titi.x++;
	transactionnable_tata = new TransactionnableClass(2);
    }

    public void modifyTable() {
	table_t = new int[2];
	table_t[0] = 4;
	table_t[1] = 5;
    }

    public void failMethod() throws MethodException {
	throw new MethodException("Method fail.");
    }
}
