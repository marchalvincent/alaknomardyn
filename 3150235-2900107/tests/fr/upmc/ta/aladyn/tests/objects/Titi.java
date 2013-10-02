package fr.upmc.ta.aladyn.tests.objects;


public class Titi {
	
	public Integer x;
	
	public Titi() {
		this(0);
	}
	
	public Titi(int a) {
		super();
		x = new Integer(a);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
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
		Titi other = (Titi) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		return true;
	}
}
