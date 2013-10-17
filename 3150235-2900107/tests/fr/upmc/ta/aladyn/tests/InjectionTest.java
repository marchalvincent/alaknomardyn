package fr.upmc.ta.aladyn.tests;

import fr.upmc.ta.aladyn.injection.BackupTranslator;
import javassist.ClassPool;
import javassist.Loader;

public class InjectionTest {

    public static void main(String[] args) {
	try
	{
	    ClassPool pool = ClassPool.getDefault();
	    Loader loader = new Loader(pool); 
	    BackupTranslator backupTranslator = new BackupTranslator();
	    loader.addTranslator(pool, backupTranslator);

	    loader.run("fr.upmc.ta.aladyn.tests.classMain.PersonTest", args);
	    
	}catch(Throwable e){
	    System.err.println(e);
	}
    }
}
