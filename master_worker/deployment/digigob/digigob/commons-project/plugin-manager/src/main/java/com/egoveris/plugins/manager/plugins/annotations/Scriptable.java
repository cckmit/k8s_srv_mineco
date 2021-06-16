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
public @interface Scriptable {
	public String groupName() default "none";
	public String refName() default "none";
	public String initJS() default "";
}
