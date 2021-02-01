/**
 * 
 */
package com.egoveris.plugins.manager.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author difarias
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Executable {
	public String name() default "none";
	public String icon() default "";
	public String tooltip() default "Executable";
	public boolean isVisible() default true; 
}
