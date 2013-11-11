package fr.upmc.ta.aladyn.interception;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.tools.reflect.Loader;
import fr.upmc.ta.aladyn.Transactionnable;
import fr.upmc.ta.aladyn.interception.InterceptionTranslator;

/**
 * Main qui permet d'intercepter les appels de méthodes dans un programme utilisant les annotations {@link Transactionnable}.
 * Les interceptions permettent la restauration des objets transactionnables en cas de problèmes dans une méthode transactionnable.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 *
 */
public class InterceptionMain {


    private static boolean initialized = false;

    private static ClassPool pool;
    private static Loader loader;
    private static InterceptionTranslator backupTrans;

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

    public static void initialize() throws CannotCompileException, NotFoundException {
	pool = ClassPool.getDefault();
	loader = new Loader();
	backupTrans = new InterceptionTranslator(loader);
	loader.addTranslator(pool, backupTrans);
	initialized = true;
    }

}
