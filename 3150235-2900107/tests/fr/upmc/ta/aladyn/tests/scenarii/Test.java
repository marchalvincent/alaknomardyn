package fr.upmc.ta.aladyn.tests.scenarii;

import fr.upmc.ta.aladyn.Transactionnable;

@Transactionnable
public class Test {

    public int x = 0;
    
    public void setX(int x) {
	this.x = x;
    }
    
    public void tata() {
	this.setX(3);
    }
}
