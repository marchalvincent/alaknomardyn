package fr.upmc.ta.aladyn.testAsupprimer;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

public class MainTestSsist {

    public static void main(String[] args) throws Throwable {
	ClassPool pool = ClassPool.getDefault();
	Loader loader = new Loader(pool);

	Translator t = new MyTranslator();
	loader.addTranslator(pool, t);
	loader.run("fr.upmc.ta.aladyn.testAsupprimer.MainTestTranslator", args);
    }

}
