package fr.upmc.ta.aladyn.tests;

import fr.upmc.ta.aladyn.injection.BackupTranslator;
import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

public class InjectionTest extends AbstractTest {
    private Loader loader;
private Translator backupTranslator;

    public InjectionTest() throws Exception
    {
	ClassPool pool = ClassPool.getDefault();
	loader = new Loader(pool); 
	backupTranslator = new BackupTranslator();
	loader.addTranslator(pool, backupTranslator);
    }

    @Override
    public Loader getLoaderInjection() {
	return loader;
    }

    @Override
    public javassist.tools.reflect.Loader getLoaderInterception() throws Exception {
	return null;
    }

    @Override
    public Translator getTranslator() {
	return backupTranslator;
    }

}
