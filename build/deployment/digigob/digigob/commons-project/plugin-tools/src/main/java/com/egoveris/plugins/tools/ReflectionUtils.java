/**
 * 
 */
package com.egoveris.plugins.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Loader;

/**
 * @author difarias
 *
 */
public final class ReflectionUtils {
	public static Method findMethodByName(Class clazz, String methodName){
		for (Method method : clazz.getMethods()) {
			if (method.getName().equalsIgnoreCase(methodName)) return method;
		}
		return null;
	}
	
	public static <V> V createClass(String className, Class<V> parentClass) throws Exception {

		ClassPool pool = ClassPool.getDefault();
		CtClass evalClass = pool.makeClass(className);
		evalClass.defrost();
		
		Loader cl = new Loader(parentClass.getClassLoader(),pool);
		Class parent = cl.loadClass(parentClass.getName());
		
		if (evalClass!=null) {
			pool.appendClassPath(new ClassClassPath(parent));
			
			evalClass.setSuperclass(pool.get(parentClass.getName()));
			Class<V> classGenerated = (Class<V>) evalClass.toClass(parentClass.getClassLoader(), parentClass.getProtectionDomain());
			evalClass.detach();
			if (classGenerated!=null) {
				return classGenerated.newInstance(); 
			}
		}
		
		return null;
	}

	public static <V> List<V> createClassInstances(Class<V> parentClass, List<String> lstClasses) {
		List<V> lstClassesInstances = new ArrayList<V>();
		if (lstClasses!=null) {
			for (String className : lstClasses) {
				V obj;
				try {
					obj = createClass(className, parentClass);
					if (obj!=null) {
						lstClassesInstances.add(obj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lstClassesInstances;
	}
	
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		if (clazz==null) throw new IllegalArgumentException("Class must not be null");
		if (name==null) throw new IllegalArgumentException("Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())
						&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	

	public static Object invokeMethod(Method method, Object target, Object... args)  {
		return invoke(target, method.getName(),args);
	}
	
	public static void invokeVoid(Object obj, String methodName) {
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName, new Class[]{});
			if (method!=null) {
					method.invoke(obj, new Object[]{});
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("rawtypes")
	public static Object invoke(Object obj, String methodName, Object... params) {
		try {
			Method[] metodos = obj.getClass().getMethods();
			for (Method method : metodos) {
				if (methodName.equalsIgnoreCase(method.getName())){
					try {
						Object result = method.invoke(obj, params);
						return result;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object invokeStatic(Class clazz, String methodName, Object... args) {
		try {
			Method[] metodos = clazz.getMethods();
			for (Method method : metodos) {
				if (methodName.equalsIgnoreCase(method.getName())){
					try {
						Object result = method.invoke(null, args);
						return result;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean hasInterface(Class<?> clazz, Class<?> interfaceToCheck) {
		List<Class<?>> interfaces = Arrays.asList(clazz.getInterfaces());
		boolean containInterface=interfaces.contains(interfaceToCheck);
		
		if (!containInterface) {// me fijo de las superclases
			Class<?> parent = clazz.getSuperclass();
			while(parent!=null) { 
				interfaces = Arrays.asList(parent.getInterfaces());
				containInterface=interfaces.contains(interfaceToCheck);
				if (containInterface) break;
				parent = parent.getSuperclass();
			}
		}
		return containInterface;
	}
	
	
}
