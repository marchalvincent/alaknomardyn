package fr.upmc.ta.aladyn.testAsupprimer;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;

public class MyTranslator implements Translator {

    @Override
    public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {

	CtClass ctPoint = pool.get(className);
	if (ctPoint.getSimpleName().equals("MyPoint")) {
	    CtMethod ctGetX = ctPoint.getDeclaredMethod("getX");
	    ctGetX.insertBefore("System.out.println(\"la valeur : \" + $0.x);");
	    try {
		ctPoint.writeFile();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void start(ClassPool arg0) throws NotFoundException, CannotCompileException {
    }

}
