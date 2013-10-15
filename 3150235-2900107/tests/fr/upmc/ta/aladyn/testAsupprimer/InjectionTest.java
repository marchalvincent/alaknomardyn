package fr.upmc.ta.aladyn.testAsupprimer;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;
import fr.upmc.ta.aladyn.injection.BackupTranslator;

public class InjectionTest {

    public static void main(String[] args) throws Throwable {
	ClassPool pool = ClassPool.getDefault();
	Loader loader = new Loader(pool);

	Translator t = new BackupTranslator();
	loader.addTranslator(pool, t);
	loader.run("fr.upmc.ta.aladyn.testAsupprimer.MainTestTranslator", args);
    }
}
