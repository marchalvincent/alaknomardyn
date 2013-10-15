package fr.upmc.ta.aladyn.meta;

import javassist.tools.reflect.Loader;

public class TesterMeta {

    public static void main(String[] args){
	try 
	{
	    Loader cl = new Loader();
	    cl.makeReflective( "fr.upmc.ta.aladyn.testAsupprimer.Person",
	    		"fr.upmc.ta.aladyn.meta.TransMetaObj",
	    		"javassist.tools.reflect.ClassMetaobject" );
	    cl.run("fr.upmc.ta.aladyn.testAsupprimer.Person", args );
	}
	catch( Throwable ex)
	{
	    ex.printStackTrace();
	}

    }

}

//public class TesterMeta {
//
//    public static void main(String[] args){
//	try 
//	{
//	    ClassPool pool = ClassPool.getDefault();
//	    Loader loader = new Loader();
//	    BackupTranslator backupTrans = new BackupTranslator(loader);
//	    loader.addTranslator(pool, backupTrans);
//	    loader.run("fr.upmc.ta.aladyn.testAsupprimer.Person", args );
//	}
//	catch( Throwable ex)
//	{
//	    ex.printStackTrace();
//	}
//
//    }
//
//}
