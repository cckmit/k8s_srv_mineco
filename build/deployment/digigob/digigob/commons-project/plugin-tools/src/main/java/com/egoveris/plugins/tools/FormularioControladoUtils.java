/**
 * 
 */
package com.egoveris.plugins.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author difarias
 *
 */
public final class FormularioControladoUtils {
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getFFCCValues(Object ffccManager) {
		Map<String,Object> data = new HashMap<String,Object>();
		Field field;
		try {
			field = ffccManager.getClass().getDeclaredField("inputCompZkMap");
			if (field!=null) {
				field.setAccessible(true);
				//Map<String, InputComponent> comps = (Map<String, InputComponent>) field.get(ffccManager);
				Map comps = (Map) field.get(ffccManager);
				
				for (String key : (Set<String>) comps.keySet()) {
					Object ic = comps.get(key);
					Method metodo = ReflectionUtils.findMethodByName(ic.getClass(), "getRawValue");
					if (ic!=null && metodo!=null) {
						Object rawValue;
						try {
							rawValue = metodo.invoke(ic);
							data.put(key, rawValue);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
					//System.out.printf("	campo (%d) -> %s: %s \n", counter++, key , ic.getRawValue());
				}
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public static void setValuesFFCC(Object comp, Map<String,Object> data) {
		if (comp!=null) {
			try {
				Field field = comp.getClass().getDeclaredField("inputCompZkMap");
				if (field!=null) {
					field.setAccessible(true);
					Map comps = (Map) field.get(comp);
					
					for (String key : (Set<String>) comps.keySet()) {
						Object ic = comps.get(key);
						Object rawValue = data.get(key); 
						Method metodo = ReflectionUtils.findMethodByName(ic.getClass(), "setRawValue");
						if (ic!=null && metodo!=null) {
							try {
								metodo.invoke(ic,rawValue);
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
