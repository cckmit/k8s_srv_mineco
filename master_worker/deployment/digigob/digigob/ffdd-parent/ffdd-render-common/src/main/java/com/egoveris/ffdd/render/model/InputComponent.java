package com.egoveris.ffdd.render.model;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.EventListener;

public interface InputComponent extends ComponentZkExt {
	
	/**
	 * Obtiene el valor del componente
	 */
	public Object getRawValue();

	/**
	 * Setea el valor del componente
	 */
	public void setRawValue(Object obj);

	/**
	 * Obtiene el texto con previa validacion de constraint
	 */
	public String getText();
	
	/**
	 * Devuelve true si el componente se encuentra en modo solo lectura
	 * 
	 * @return
	 */
	public boolean isReadonly();

	/**
	 * Establece el valor del atributo readonly "deshabilitado"
	 * 
	 * @param disable
	 */
	public void setReadonly(boolean disable);
	
	//-- event listener --//
	/** Adds an event listener to specified event name for this component.
	 * The second registration is ignored and false is returned.
	 *
	 * <p>You could register listener to all components in the same page
	 * by use of {@link Page#addEventListener}.
	 *
	 * @param evtnm what event to listen (never null)
	 * @return whether the listener is added; false if it was added before
	 * @see Page#addEventListener
	 */
	public boolean addEventListener(String evtnm, EventListener listener);
	
	/** Clears the error message.
	 *
	 * <p>The error message is cleared automatically, so you rarely need
	 * to call this method.
	 * However, if a constraint depends on multiple input fields and
	 * the error can be corrected by changing one of these fields,
	 * then you may have to clear the error message manullay by invoking
	 * this method.
	 *
	 * <p>For example, assume you have two {@link org.zkoss.zul.Intbox}
	 * and want the value of the first one to be smaller than that of the
	 * second one. Then, you have to call this method for the second intbox
	 * once the validation of the first intbox succeeds, and vice versa.
	 * Otherwise, the error message for the seoncd intbox remains if
	 * the user fixed the error by lowering down the value of the first one
	 * Why? The second intbox got no idea to clear the error message
	 * (since its content doesn't change).
	 *
	 * @param revalidateRequired whether to re-validate the current value
	 * when {@link #getText} or others (such as {@link org.zkoss.zul.Intbox#getValue})
	 * is called.
	 * If false, the current value is assumed to be correct and
	 * the following invocation to {@link #getText} or others (such as {@link org.zkoss.zul.Intbox#getValue})
	 * won't check the value again.
	 * Note: when an input element is constrcuted, the initial value
	 * is assumed to be "not-validated-yet".
	 * @since 3.0.1
	 */
	public void clearErrorMessage(boolean revalidateRequired);

}
