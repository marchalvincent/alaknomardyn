package fr.upmc.ta.aladyn.tests;

import fr.upmc.ta.aladyn.meta.BackupTranslator;
import javassist.tools.reflect.Loader;
import javassist.Translator;


public class InterceptionTest extends AbstractTest {
    private Loader loader;
    private BackupTranslator backupTranslator;
    
    public InterceptionTest() throws Exception
    {
	loader = new Loader();
	backupTranslator = new BackupTranslator(loader);
    }
    @Override
    public javassist.Loader getLoaderInjection() {
	return null;
    }

    @Override
    public Loader getLoaderInterception(){
	    return loader;
    }

    @Override
    public Translator getTranslator() {
	return backupTranslator;
    }

}
