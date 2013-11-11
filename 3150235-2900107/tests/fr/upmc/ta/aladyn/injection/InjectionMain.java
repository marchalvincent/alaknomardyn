package fr.upmc.ta.aladyn.injection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import fr.upmc.ta.aladyn.Transactionnable;
import fr.upmc.ta.aladyn.injection.InjectionTranslator;

/**
 * Main qui permet d'injecter du code dans un programme utilisant les annotations {@link Transactionnable}.
 * Les injections permettent la restauration des objets transactionnables en cas de problèmes dans une méthode transactionnable.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class InjectionMain {

    private static boolean initialized = false;

    private static ClassPool pool;
    private static Loader loader;
    private static InjectionTranslator backupTranslator;

    public static void main(String[] args) throws Throwable {
	try {
	    if (!initialized)
		initialize();
	    loader.run(args[0], args);
	} catch (Throwable e) {
	    e.printStackTrace();
	    throw e;
	}
    }

    public static void initialize() throws NotFoundException, CannotCompileException {
	pool = ClassPool.getDefault();
	loader = new Loader(pool);
	backupTranslator = new InjectionTranslator();
	loader.addTranslator(pool, backupTranslator);
	initialized = true;
    }
}
