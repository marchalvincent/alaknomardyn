package fr.upmc.ta.aladyn.testAsupprimer;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class Person {

    private int value ;
    public int test = 10;
    public int f ( int i ) {
	value = i +1;
	return value; 
    }

    public int getValue() throws Exception {
	throw new Exception("mon exc");
//        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) throws Exception{
	System.out.println("Main de la classe person");
	Person p = new Person();
	int value = 5;
	System.out.println("Incrementation de " + value + " = " + String.valueOf(p.f(value)));
	System.out.println("get value : " + p.getValue());
	
	System.out.println("lecture public value test : " + p.test);


    }
}
