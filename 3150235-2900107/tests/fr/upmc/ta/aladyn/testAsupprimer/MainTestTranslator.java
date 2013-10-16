package fr.upmc.ta.aladyn.testAsupprimer;

public class MainTestTranslator {

    public static void main(String[] args) throws Exception {
	MyPoint point = new MyPoint(2, 3);
	point.getX();
	try {
	    point.transactionnableMethod();
	} catch (Exception e) {
	    System.out.flush();
	    System.err.println("exception levee");
	    System.err.flush();
	}
	
    }
}
