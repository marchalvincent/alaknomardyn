package fr.upmc.ta.aladyn.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.upmc.ta.aladyn.InjectionException;
import fr.upmc.ta.aladyn.injection.MethodeCouranteManager;

public class MethodeCouranteManagerTest {

    @Test
    public void testStack() throws InjectionException {
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	assertTrue(true);
    }

    @Test
    public void testStack2() throws InjectionException {
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	assertTrue(true);
    }

    @Test(expected=InjectionException.class)
    public void testStack3() throws InjectionException {
	MethodeCouranteManager.instance.newTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
	MethodeCouranteManager.instance.endOfTransactionnableMethod();
    }

    public void test() {
	
    }
    
}
