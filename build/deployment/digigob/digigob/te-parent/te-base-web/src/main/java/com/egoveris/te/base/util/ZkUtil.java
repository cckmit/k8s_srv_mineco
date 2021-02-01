/**
 * 
 */
package com.egoveris.te.base.util;

import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IFormManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

/**
 * @author difarias
 *
 */
public final class ZkUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZkUtil.class);
	
	@SuppressWarnings("unchecked")
	public static <T> T findById(Component container, String componentId) {
		Component founded = null;
		
		if (container!=null) {
			try {
				founded = container.getFellow(componentId);
			} catch (Exception e) {
			}
			
			if (founded==null) {
				for (Object childComp : container.getChildren()) {
					Component child = (Component) childComp;
					founded = findById(child, componentId);
					if (founded!=null) break;
				}
			}
		}
		
		return (T) founded;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> findByType(Object obj, Class<T> componentType) {
		Component container = (Component) obj;
		List<T> lstFound = new ArrayList<T>();
		List<Class<?>> toSearch = new ArrayList<Class<?>>();
		
		toSearch.add(container.getClass());
		toSearch.addAll(Arrays.asList(container.getClass().getInterfaces()));
		
		for (Class<?> clz : toSearch) {
			if (clz.equals(componentType)) {
				lstFound.add((T) container);
				break;
			}
		}
		
		for (Object childComp : container.getChildren()) {
			Component child = (Component) childComp;
			lstFound.addAll(findByType(child, componentType));
		}
		
		return lstFound;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T findParentByType(Component container, Class<T> containerType) {
		if (container.getClass().equals(containerType)) return (T) container;
		return (T) findParentByType(container.getParent(), containerType);
	}
	
	public static String getContext() {
		return SpringUtil.getApplicationContext().getDisplayName();
	}
	
	public static String getterByType(Component comp) {
		if (comp instanceof Checkbox) return "isChecked";
		if (comp instanceof Combobox) return "getSelectedIndex";
		
		return "getValue";
	}

	public static String setterByType(Component comp) {
		if (comp instanceof Checkbox) return "setChecked";
		if (comp instanceof Combobox) return "setSelectedIndex";
		
		return "setValue";
	}
	
	public static void setValues(Component container, Map<String, String> values) {
		if (values!=null && !values.isEmpty()) {
			for (String key : values.keySet()) {
				Component comp = findById(container, key);
				Object value = values.get(key);
				
				if ( value!=null && ((String)value).isEmpty()) continue;

				if (comp!=null) {
					try {
						Method method = ReflectionUtil.findMethodByName(comp.getClass(), setterByType(comp));
						Class<?> type = method.getParameterTypes()[0];
						//System.out.printf("(%s) %s  --> %s \n",comp.getId(),method.getName(),value);
						ReflectionUtil.invokeMethod(method, comp, convert(type,value));
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}
	
	public static Object convert(Object type, Object value) {
		if (((Class)type).getName().equals(Date.class.getName())) {
			return new Date(Date.parse((String)value)); 
		}

		if (((Class)type).getName().equals(int.class.getName())) {
			return (new Integer((String)value)).intValue();
		}
		
		if (((Class)type).getName().equals(boolean.class.getName())) {
			return new Boolean((String)value);
		}
		
		return new String((String) value);
	}
	
	public <T> T convertTo(String valor) {
		Object result = null;
		
		
		return (T) result;
	}
	
	@SuppressWarnings("unchecked")
	public static void setJsonValues(Component container, String jsonValue) {
		Gson gson = new GsonBuilder()
			.setDateFormat("dd/MM/yyyy")
			.create();
		
		Map<String,String> values;
		Type mapType = new TypeToken<Map<String, String>>() {}.getType();
		
		values = gson.fromJson(jsonValue, mapType);
		if (values!=null && !values.isEmpty()) {
			setValues(container, values);
		}
	}
	
	
	public static Map<String,Object> jsonToMap(String jsonValue) {
		Gson gson = new GsonBuilder()
		.setDateFormat("dd/MM/yyyy")
		.create();
	
		Map<String,Object> values;
		Type mapType = new TypeToken<Map<String, String>>() {}.getType();
		values = gson.fromJson(jsonValue, mapType);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getValuesFFCC(IFormManager<Component> comp) {
		Map<String,Object> data = new HashMap<String,Object>();
		
		if (comp!=null) {
			try {
				Field field = comp.getClass().getDeclaredField("inputCompZkMap");
				if (field!=null) {
					field.setAccessible(true);
					Map<String, InputComponent> comps = (Map<String, InputComponent>) field.get(comp);
					
					for (String key : comps.keySet()) {
						InputComponent ic = comps.get(key);
						data.put(key, ic.getRawValue());
					}
				}
				
			} catch (SecurityException e) {
				logger.error(e.getMessage());
			} catch (NoSuchFieldException e) {
				logger.error(e.getMessage());
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			}
		}
		
		return data;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void setValuesFFCC(IFormManager<Component> comp, Map<String,Object> data) {
		if (comp!=null) {
			try {
				Field field = comp.getClass().getDeclaredField("inputCompZkMap");
				if (field!=null) {
					field.setAccessible(true);
					Map<String, InputComponent> comps = (Map<String, InputComponent>) field.get(comp);
					
					if (data!=null && !data.isEmpty()) {
						for (String key : comps.keySet()) {
							InputComponent ic = comps.get(key);
							// --- search comp name ---
							Object value = data.get(key);
							ic.setRawValue(value);
							// ------------------------
						}
					}
				}
				
			} catch (SecurityException e) {
				logger.error(e.getMessage());
			} catch (NoSuchFieldException e) {
				logger.error(e.getMessage());
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	
	public static Map<String, Object> getValues(Object object) {
		Map<String,Object> result=null;
		
		if (object!=null) {
			Component container = (Component) object;
			
			Object value = null;
			
			try {
				Method method = ReflectionUtil.findMethod(object.getClass(), getterByType(container));
				value = ReflectionUtil.invokeMethod(method, object);
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
			
			if (value!=null && !(container instanceof Label) && !container.getId().isEmpty()) {
				result = new HashMap<String,Object>();
				result.put(container.getId(), value);
			}
			
			if (!container.getChildren().isEmpty()) {
				for (Object childComp : container.getChildren()) {
					Component child = (Component) childComp;
					Map<String,Object> partial = getValues(child);
					if (partial!=null) {
						if (result==null) {
							result = new HashMap<String,Object>();
						}
						result.putAll(partial);
					}
				}
			}
		}
		
		return result;
	}
	
	public static String getJsonValues(Object object,  Map<String, Object> aditionalData) {
		Gson gson = new Gson();
		Map<String, Object> valores = null;
		
		try {
			valores = getValues(object);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		if (aditionalData!=null && !aditionalData.isEmpty()) {
			if (valores==null) {
				valores = new HashMap<String,Object>();
			}
			valores.putAll(aditionalData);
		}
		
		return (valores!=null?gson.toJson(valores):"");
	}
	
	public static <V> V getOne(List<V> list, int index) {
		
		if (list!=null && !list.isEmpty()) {
			return list.get(index);
		}
		
		return null; 
	}
	
	public static void insertFirsEventListener(Component comp, String eventName, EventListener eventListener) {
		List<EventListener> listeners = new LinkedList<EventListener>();
		// --- extract listeners ---
		for (Iterator it = comp.getListenerIterator(eventName); it.hasNext();) {
			listeners.add((EventListener)it.next());
		}
		// --- remove listeners ---
		for (EventListener listener: listeners) {
			comp.removeEventListener(eventName, listener);
		}
		// --- insert first listener ---
		comp.addEventListener(eventName, eventListener);
		// --- add the others ----
		for (EventListener listener: listeners) {
			comp.addEventListener(eventName, listener);
		}
	}
	
	public static List<EventListener> getListeners(Component comp, String eventName) {
		List<EventListener> listeners = new LinkedList<EventListener>();
		// --- extract listeners ---
		for (Iterator it = comp.getListenerIterator(eventName); it.hasNext();) {
			listeners.add((EventListener)it.next());
		}
		
		return listeners;
	}
	
	public static void removeListeners(Component comp, String eventName) {
		List<EventListener> listeners = new LinkedList<EventListener>();
		// --- extract listeners ---
		for (Iterator it = comp.getListenerIterator(eventName); it.hasNext();) {
			listeners.add((EventListener)it.next());
		}
		// --- remove listeners ---
		for (EventListener listener: listeners) {
			comp.removeEventListener(eventName, listener);
		}
	}
	
	public static void raiseEvents(List<EventListener> listeners, Event event) {
		for (EventListener listener: listeners) {
			try {
				
				listener.onEvent(event);
			}catch (WrongValueException e) {
				throw e;
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public static void setRadioGroupValue(Radiogroup radiogrp, String value) {
		List<Radio> lstRadios = findByType(radiogrp, Radio.class);
		for (Radio radio : lstRadios) {
			if (((String) radio.getValue()).equalsIgnoreCase(value)) {
				radiogrp.setSelectedItem(radio);
				return;
			}
		}
	}
	
	/**
	 * Wrapped method to create a component instance
	 * @param <V> Type of return
	 * @param zulXml String path to ZUL File
	 * @param parent Component parent
	 * @param args Map dataContext
	 * @param controllerObject Object Composer (GenericForwardComposer)  
	 * @return <V> Object
	 */
	@SuppressWarnings("unchecked")
	public static <V> V createComponent(String zulXml, Component parent, Map args, Object controllerObject ) {
		Component comp = Executions.createComponents(zulXml, parent, args);
		if (controllerObject!=null) {
			Components.wireController(comp, controllerObject);
			if (controllerObject instanceof GenericForwardComposer) {
				GenericForwardComposer controller = (GenericForwardComposer) controllerObject;
				controller.bindComponent(comp);
				try {
					controller.doAfterCompose(comp);
					AnnotateDataBinder binder = new AnnotateDataBinder();
					binder.loadComponent(comp);
				} catch (Exception e1) {
					logger.error(e1.getMessage());
				}
			}
		}
		
		return (V) comp;
	}

	/**
	 * @see createComponent
	 */
	@SuppressWarnings("unchecked")
	public static <V> V createComponent(String zulXml, Component parent, Map args) {
		return (V) createComponent(zulXml,parent, args, null);
	}
}
