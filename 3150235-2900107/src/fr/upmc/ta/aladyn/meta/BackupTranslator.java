package fr.upmc.ta.aladyn.meta;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.tools.reflect.Loader;
import fr.upmc.ta.aladyn.Transactionnable;

public class BackupTranslator implements Translator {

    private Loader loader;

    public BackupTranslator (Loader loader)
    {
	super();
	this.loader = loader;
    }

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
	// TODO Auto-generated method stub
    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException 
    {
	CtClass ctClass = pool.get(classname);

	// On regarde si la CtClass est transactionnable
	try 
	{
	    if (ctClass.hasAnnotation(Transactionnable.class))
	    {
		loader = new Loader();
		loader.makeReflective( classname,
			"fr.upmc.ta.aladyn.meta.TransMetaObj",
			"javassist.tools.reflect.ClassMetaobject" );
	    }
	} 
	catch (Throwable e) 
	{
	    e.printStackTrace();
	}

    }

}
