/**
 * 
 */
package com.egoveris.plugins.manager.tools.scripts.functions;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.plugins.interfaces.IScriptable;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;

/**
 * @author difarias
 * 
 */
@SuppressWarnings("serial")
public class General extends ScriptableObject {
  private static final Logger logger = LoggerFactory.getLogger(General.class);
	private final String DEFAULT_GROUP = "none";

	private static General ourInstance=null;
	private ScriptEngine engine;
	private Map<String, Map<String, Class>> scriptGroup;
	private Map<String, Map<String, Object>> scriptGroupInstances;

	/**
	 * @return the scriptGroup
	 */
	public Map<String, Map<String, Class>> getScriptGroup() {
		return scriptGroup;
	}

	/**
	 * @param scriptGroup
	 *            the scriptGroup to set
	 */
	public void setScriptGroup(Map<String, Map<String, Class>> scriptGroup) {
		this.scriptGroup = scriptGroup;
	}

	/**
	 * @return the scriptGroupInstances
	 */
	public Map<String, Map<String, Object>> getScriptGroupInstances() {
		if (scriptGroupInstances == null) {
			scriptGroupInstances = new HashMap<>();
			scriptGroupInstances.put(DEFAULT_GROUP, new HashMap<String, Object>());
		}
		return scriptGroupInstances;
	}

	/**
	 * Default Constructor
	 * 
	 * @param engine
	 */
	public General() {
		System.out.println("CONSTRUCTOR General.General()");

		engine = ScriptUtils.getInstance().getEngine();
	}
	
	public static General getInstance(ScriptEngine engine) {
		if (ourInstance==null) {
			ourInstance = new General(engine);
		}
		return ourInstance;
	}

	public static General getInstance() {
		return General.getInstance(null);
	}

	/**
	 * Constructor with engineParameter
	 * 
	 * @param engine
	 */
	public General(ScriptEngine engine) {
		this.engine = engine;
	}

	/**
	 * @return the engine
	 */
	public ScriptEngine getEngine() {
		return engine;
	}

	/**
	 * @param engine
	 *            the engine to set
	 */
	public void setEngine(ScriptEngine engine) {
		this.engine = engine;
	}

	private Object createInstance(String groupName, String name) throws InstantiationException, IllegalAccessException {
		Object obj = null;

		if (scriptGroup != null && scriptGroup.containsKey(groupName)) {
			Map<String, Class> mapClass = scriptGroup.get(groupName);
			if (mapClass != null && mapClass.containsKey(name)) {
				Class cls = mapClass.get(name);
				try {
					obj = cls.newInstance();
				} catch (Exception e) {
				  logger.error("error createInstance()", e);
				}
			}
		}

		return obj;
	}

	/**
	 * Method to find and object by group and alias
	 * 
	 * @param groupName
	 * @param name
	 * @return
	 */
	private Object retrieveObject(String groupName, String name) {
		Object obj = null;

		try {
			return createInstance(groupName, name);
		} catch (InstantiationException e) {
		  logger.error("error retrieveObject - InstantiationException", e);
		} catch (IllegalAccessException e) {
		  logger.error("error retrieveObject - IllegalAccessException", e);
		}

		return obj;
	}

	/**
	 * Method to registry an object by alias
	 * 
	 * @param objAlias
	 * @param obj
	 * @throws ScriptException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public void registerObject(String objAlias, Object obj) throws ScriptException, SecurityException, NoSuchMethodException {
		getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).put(objAlias, obj); // adding
																				// object
																				// to
																				// scope
	}

	/**
	 * Method to eval scriptable functions if object is an instance of
	 * IScriptable
	 * 
	 * @param obj
	 */
	private void evalScriptableFunctions(Object obj, String refName) {
		if (obj != null && obj instanceof IScriptable) {
			IScriptable scriptable = (IScriptable) obj;
			try {
				getEngine().eval(scriptable.getScriptFunctions(refName));
			} catch (ScriptException e) {
			  logger.error("error evalScriptableFunctions() - ScriptException", e);
			}
		}
	}

	public String camelCase(String param) {
		return param.substring(0, 1).toUpperCase() + param.toLowerCase().substring(1, param.length());
	}

	/**
	 * Metodo para obtener los componentes que fueron registrados
	 * 
	 * @param name
	 * @return
	 */
	public Object requireByGroup(String groupName, String name) {
		String alias = name; // camelCase(name);

		Object so = getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).get(alias);

		if (so == null) {
			alias = groupName + "_" + alias;
			so = getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).get(alias);
		}

		if (so == null) {
			so = retrieveObject(groupName, name);
			getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).put(alias, so);
		}

		if (so != null) { // if object exists
			evalScriptableFunctions(so, alias);
			
			if ("InterpretedFunction".equals(so.getClass().getSimpleName())) {
				try {
					so = getEngine().eval(String.format("new %s();",name),getEngine().getBindings(ScriptContext.ENGINE_SCOPE));
				} catch (ScriptException e) {
				  logger.error("error ScriptException - requireByGroup", e);
				}
			}
		}

		return so;
	}

	/**
	 * Metodo para obtener los componentes que fueron registrados
	 * 
	 * @param name
	 * @return
	 */
	public Object require(String name) {
		return requireByGroup(DEFAULT_GROUP, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sun.org.mozilla.javascript.internal.ScriptableObject#getClassName()
	 */
	@Override
	public String getClassName() {
		return General.class.getName();
	}
	
	public void contentRegisterJS(String expression) throws ScriptException {
	    StringBuilder sb = new StringBuilder();
    	sb.append("%s").append("\n");
    	
    	expression=String.format(sb.toString(),expression);
    	
    	System.out.println("---------------------- registrando JS ----------------------");
    	System.out.println(expression);
    	System.out.println("---------------------- registrando JS ----------------------");
    	
    	Bindings bindings = getEngine().getBindings(ScriptContext.GLOBAL_SCOPE);
    	try {
    		getEngine().eval(expression, bindings);
		} catch (ScriptException e) {
			throw e;
		}
	}
	
	private void loadJS(String scriptName, boolean durable) throws ScriptException {
		//logger.info("Loading : "+scriptName);
		URL urlResource = ScriptUtils.getInstance().getActiveClassLoader().getResource(scriptName);
		
		if (urlResource==null) { 
			ScriptUtils.getInstance().getLogger().debug(String.format("Resource %s not founded.",scriptName));
		}
		
		String script;
		try {
		  if(urlResource != null){
    			script = IOUtils.toString(urlResource.openStream());
    			Bindings bindings = getEngine().getBindings((durable?ScriptContext.GLOBAL_SCOPE:ScriptContext.ENGINE_SCOPE));			
    			getEngine().eval(script,bindings);
		  }
		} catch (Exception e) {
			ScriptUtils.getInstance().getLogger().debug(String.format("Reading error (%s)",scriptName));
			ScriptUtils.getInstance().getLogger().error("", e);
		}
	}
	
	public void registerJS(String scriptName) throws ScriptException {
		loadJS(scriptName,true);
	}
	
	
	public void loadJS(String scriptName) throws ScriptException {
		loadJS(scriptName,false);
	}
	
}
