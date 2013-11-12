package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

/**
 * Une classe transactionnable contenant un champ de type primitif.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
@Transactionnable
public class TransactionnableClass {

    public int x;

    public TransactionnableClass() {
	this(0);
    }

    public TransactionnableClass(TransactionnableClass t) {
	this(t.x);
    }

    public TransactionnableClass(int i) {
	super();
	x = i;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + x;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TransactionnableClass other = (TransactionnableClass) obj;
	if (x != other.x)
	    return false;
	return true;
    }
}
