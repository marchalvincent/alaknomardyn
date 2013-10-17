package fr.upmc.ta.aladyn.tests;

import javassist.ClassPool;
import javassist.Loader;
import fr.upmc.ta.aladyn.injection.InjectionTranslator;

public class InjectionTest {

    public static void main(String[] args) {
	try
	{
	    ClassPool pool = ClassPool.getDefault();
	    Loader loader = new Loader(pool); 
	    InjectionTranslator backupTranslator = new InjectionTranslator();
	    loader.addTranslator(pool, backupTranslator);

	    loader.run("fr.upmc.ta.aladyn.tests.classMain.PersonTest", args);
	    
	}catch(Throwable e){
	    e.printStackTrace();
	}
    }
}
