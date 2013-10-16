package fr.upmc.ta.aladyn.tests;

import javassist.Translator;

public abstract class AbstractTest {


    public abstract javassist.Loader getLoaderInjection();

    public abstract javassist.tools.reflect.Loader getLoaderInterception();

    public abstract Translator getTranslator();

    public static void main(String[] args){

//	if(getLoaderInjection() == null)
//	{
//	    
//	    
//	}
//	Loader loader = getLoad();
//	BackupTranslator backupTrans = new BackupTranslator(loader);
//	loader.addTranslator(pool, backupTrans);
//	loader.run("fr.upmc.ta.aladyn.tests.objects.Person", args);
    }

}
