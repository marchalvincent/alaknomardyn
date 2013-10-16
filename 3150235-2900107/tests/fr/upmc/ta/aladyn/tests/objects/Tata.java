package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class Tata {

    public int x;

    public Tata() {
	this(0);
    }

    public Tata(Tata t) {
	this(t.x);
    }

    public Tata(int i) {
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
	Tata other = (Tata) obj;
	if (x != other.x)
	    return false;
	return true;
    }
}
