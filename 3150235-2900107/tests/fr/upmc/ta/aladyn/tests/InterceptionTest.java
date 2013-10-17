package fr.upmc.ta.aladyn.tests;

import fr.upmc.ta.aladyn.interception.BackupTranslator;
import javassist.tools.reflect.Loader;
import javassist.ClassPool;


public class InterceptionTest {

    public static void main(String[] args) {
	try
	{
	    ClassPool pool = ClassPool.getDefault();
	    Loader loader = new Loader();
	    BackupTranslator backupTrans = new BackupTranslator(loader);
	    loader.addTranslator(pool, backupTrans);

	    loader.run("fr.upmc.ta.aladyn.tests.classMain.PersonTest", args);
	}catch(Throwable e){
	    System.err.println(e);
	}
    }
}
