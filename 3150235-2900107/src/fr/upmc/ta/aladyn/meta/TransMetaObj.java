package fr.upmc.ta.aladyn.meta;

import javassist.tools.reflect.*;

public class TransMetaObj extends Metaobject 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TransMetaObj ( Object self , Object [ ] args ) 
    {
	super(self, args);
	System.out.println("** constructed : " + self.getClass( ).getName());
    }
    public Object trapFieldRead (String name)
    {
	System.out.println( "** fieldread : " + name) ;
	return super.trapFieldRead(name) ;
    }

    public void trapFieldWrite ( String name, Object value ) 
    {
	System.out.println( "** fieldwrite : " + name) ;
	super.trapFieldWrite(name, value) ;
    }
    
    public Object trapMethodcall ( int identifier , Object [ ] args )throws Throwable 
    {
	System.out.println( "** trap : " + getMethodName( identifier ) + " () in " + getClassMetaobject().getName());
	Object o = new Object();
	try {
	    o = super.trapMethodcall(identifier , args ) ;
	} catch (Throwable e) {
	    // restore
	    System.out.println("restore");
	}
	return o;
    }
}