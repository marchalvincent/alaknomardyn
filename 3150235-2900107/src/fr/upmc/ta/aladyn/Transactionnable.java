package fr.upmc.ta.aladyn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>Transactionable</code> marks classes of objects that 
 * must be made restorables in a previously checkpointed state as well as the 
 * methods that will be considered as transactions when called.
 * 
 * <p><strong>Description</strong></p>
 * 
 * The annotation is retained at run−time in order to be used by a meta−object 
 * based or a Javassist class transformation that will provide the transaction semantics.
 * 
 * <p>Created on : 2013−08−29</p>
 * 
 * @author <a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Transactionnable {}
