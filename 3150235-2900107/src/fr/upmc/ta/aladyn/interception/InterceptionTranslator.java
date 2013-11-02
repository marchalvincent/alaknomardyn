package fr.upmc.ta.aladyn.interception;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.tools.reflect.Loader;
import fr.upmc.ta.aladyn.Transactionnable;

public class InterceptionTranslator implements Translator {

    private Loader loader;

    public InterceptionTranslator(Loader loader) {
	super();
	this.loader = loader;
    }

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
	// TODO Auto-generated method stub
    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
	System.out.println("chargement de " + classname);
	CtClass ctClass = pool.get(classname);

	// On regarde si la CtClass est transactionnable
	try {
	    if (ctClass.hasAnnotation(Transactionnable.class)) {
		loader = new Loader();
		loader.makeReflective(classname, "fr.upmc.ta.aladyn.interception.TransMetaObj", "javassist.tools.reflect.ClassMetaobject");
	    }
	} catch (Throwable e) {
	    e.printStackTrace();
	}

    }

}
