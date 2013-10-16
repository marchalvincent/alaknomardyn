package fr.upmc.ta.aladyn.meta;

import javassist.ClassPool;
import javassist.tools.reflect.Loader;

public class TesterMeta {

    public static void main(String[] args){
	try 
	{
	    ClassPool pool = ClassPool.getDefault();
	    Loader loader = new Loader();
	    BackupTranslator backupTrans = new BackupTranslator(loader);
	    loader.addTranslator(pool, backupTrans);
	    loader.run("fr.upmc.ta.aladyn.tests.objects.Person", args );
	}
	catch( Throwable ex)
	{
	    ex.printStackTrace();
	}
    }

}
