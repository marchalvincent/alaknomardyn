package fr.upmc.ta.aladyn.interception;

import javassist.ClassPool;
import javassist.tools.reflect.Loader;

public class TesterMeta {

    public static void main(String[] args) {
	try {
	    ClassPool pool = ClassPool.getDefault();
	    Loader loader = new Loader();
	    InterceptionTranslator backupTrans = new InterceptionTranslator(loader);
	    loader.addTranslator(pool, backupTrans);
	    loader.run("fr.upmc.ta.aladyn.tests.objects.Person", args);
	} catch (Throwable ex) {
	    ex.printStackTrace();
	}
    }

}
