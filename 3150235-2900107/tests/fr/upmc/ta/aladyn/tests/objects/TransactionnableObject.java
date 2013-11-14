package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class TransactionnableObject {

    public int x;

    public TransactionnableObject() {
	this(0);
    }

    public TransactionnableObject(TransactionnableObject t) {
	this(t.x);
    }

    public TransactionnableObject(int i) {
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
	TransactionnableObject other = (TransactionnableObject) obj;
	if (x != other.x)
	    return false;
	return true;
    }
}
