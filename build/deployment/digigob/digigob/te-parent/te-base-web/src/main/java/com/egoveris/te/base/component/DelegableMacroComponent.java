/**
 * 
 */
package com.egoveris.te.base.component;

import com.egoveris.te.base.service.iface.IDelegable;
import com.egoveris.te.base.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;

/**
 * @author difarias
 *
 */
@SuppressWarnings("serial")
public abstract class DelegableMacroComponent extends HtmlMacroComponent implements IDelegable {
	
	private static Logger logger = LoggerFactory.getLogger(DelegableMacroComponent.class);
	
	private Object callbackObject;

	/* (non-Javadoc)
	 * @see com.egoveris.te.web.common.componentes.documents.IDelegable#setCallbackObject(java.lang.Object)
	 */

	public final void setCallbackObject(Object callbackObject) {
		this.callbackObject=callbackObject;
	}
	
	/**
	 * @return the callbackObject
	 */
	public final Object getCallbackObject() {
		return callbackObject;
	}

	/**
	 * Method to invoke a determinated Method of the callbackObject
	 * @param paramMethodName
	 * @param defaultMethod
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T callMethodFromParameter(String paramMethodName, String defaultMethod) 
	{
		String methodName=(String) getDynamicProperty(paramMethodName); 
		boolean withComponentParam;
		if (methodName!=null) {
			Method method = ReflectionUtil.findMethod(getCallbackObject().getClass(), methodName.replace("()", ""), new Class[]{Component.class},true);
			withComponentParam=true;
			
			if (method==null) {
				method = ReflectionUtil.findMethod(getCallbackObject().getClass(), methodName.replace("()", ""), true);
				withComponentParam=false;
			}
			
			if (defaultMethod!=null && !defaultMethod.isEmpty()) {
				if (method==null) { // if method was not found, then search the default method init(Component comp)
					method = ReflectionUtil.findMethod(getCallbackObject().getClass(), defaultMethod,new Class[]{Component.class},true);
					withComponentParam=true;
				}
				if (method==null) { // if method was not found, then search the default method init()
					method = ReflectionUtil.findMethod(getCallbackObject().getClass(), defaultMethod, true);
					withComponentParam=false;
				}
			}

			if (method!=null) {
				try {
					if (withComponentParam) {
						return (T) method.invoke(getCallbackObject(),this);
					} else {
						return (T) method.invoke(getCallbackObject());
					}
				} catch (IllegalArgumentException e) {
					logger.error("error en los parametros", e);
				} catch (IllegalAccessException e) {
					logger.error("error callMethodFromParameter()", e);
				} catch (InvocationTargetException e) {
					logger.error("error en los parametros: callMethodFromParameter()", e);
				}
			}
		}
		return null;
	}

	/**
	 * Wrapper method with defualtMethod in null
	 * @param paramMethodName
	 * @return <T>
	 */
	@SuppressWarnings("unchecked")
	protected <T> T callMethodFromParameter(String paramMethodName) {
		return (T) callMethodFromParameter(paramMethodName,null);
	}

	/*
	 * Call to init method if exist
	 */
	private void callInitMethod() {
		callMethodFromParameter("init");
	}
	
	
	/* (non-Javadoc)
	 * @see com.egoveris.te.web.common.componentes.documents.IDelegable#initialize()
	 */

	public void initialize() {
		if (getCallbackObject()!=null) {
			callInitMethod();
		}
	}
	
	
	public final <T> void derivateEvent(Object obj, String methodName, T data){

		Method method = ReflectionUtil.findMethod(obj.getClass(), methodName, new Class[]{data.getClass()});
		boolean withParameter = true;

		if (method==null) {

			List<Class<?>> lstIf = Arrays.asList(data.getClass().getInterfaces());
			
			for (Class<?> interfz : lstIf) {

				method = ReflectionUtil.findMethod(obj.getClass(), methodName, new Class[]{interfz});
				if (method!=null) {
					break;
				} 
			}
		}
		
		if (method ==null) {

			method = ReflectionUtil.findMethod(obj.getClass(), methodName);
			withParameter=false;
		}
		
		if (method!=null) {
			try {
				if (withParameter) {
					method.invoke(obj, data);
				} else {
					method.invoke(obj);
				}
			} catch (IllegalArgumentException e) {
				logger.error("error derivateEvent()", e);
			} catch (IllegalAccessException e) {
				logger.error("derivateEvent", e);
			} catch (InvocationTargetException e) {
				logger.error("error en derivateEvent()", e);
			}
		}
	}
}
