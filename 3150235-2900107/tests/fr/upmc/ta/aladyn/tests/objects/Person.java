package fr.upmc.ta.aladyn.tests.objects;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class Person {

    private int value;
    public int test = 10;

    public int f(int i) {
	value = i + 1;
	return value;
    }

    public int getValue() {
	return value;
    }

    @Transactionnable
    public void setValue(int value) throws Exception {
	this.value = 2;
	throw new Exception("mon exc");
    }

    public static void main(String[] args) throws Exception {
	System.out.println("Main de la classe person");
	Person p = new Person();
	int value = 5;
	System.out.println("Incrementation de " + value + " = " + String.valueOf(p.f(value)));
	System.out.println("get value : " + p.getValue());
	System.out.println("Appel de la méthode setValue(15) : ");
	p.setValue(15);
	System.out.println("La valeur de p : " + p.getValue());

	System.out.println("lecture public value test : " + p.test);

    }
}
