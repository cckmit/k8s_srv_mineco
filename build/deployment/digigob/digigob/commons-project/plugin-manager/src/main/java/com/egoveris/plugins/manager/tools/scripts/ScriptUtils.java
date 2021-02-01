/**
 * 
 */
package com.egoveris.plugins.manager.tools.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.tools.scripts.functions.General;


/**
 * @author difarias
 *
 */

public final class ScriptUtils  {
	final static Logger logger = LoggerFactory.getLogger(ScriptUtils.class);
	private static final String LIBRARIES_PATH = "/librariesJS";
	private static final String[] LIBRARIES = new String[]{
			"console.js","json2.js","sax.js","dom.js","dom-parser.js",
			"cycle.js","json_parse.js","json_parse_state.js",
			"jsonselect.js","xml2json.js"};
	private static final Integer LIBRARIES_COUNT= LIBRARIES.length;
	public static final String DATA_KEY = "DATA";
	public static final String RESULT_KEY = "RESULT";
	private static ScriptUtils ourInstance = null;
	private ScriptEngineManager scriptManager = null;
	private ScriptEngine jsEngine=null;
	private General fnGeneral; 
	private ClassLoader activeClassLoader=null;
	
	
	
	/**
	 * private constructor
	 */
	private ScriptUtils() {
		scriptManager = new ScriptEngineManager();
		jsEngine = scriptManager.getEngineByExtension("nashorn");
		if (jsEngine == null) {
			jsEngine = scriptManager.getEngineByExtension("js");
		}
		fnGeneral = General.getInstance(jsEngine);
	}
	
	
	/* (non-Javadoc)
	 * @see sun.org.mozilla.javascript.internal.ScriptableObject#getClassName()
	 */
	public String getClassName() {
		return ScriptUtils.class.getName();
	}

	public Logger getLogger(){
		return logger;
	}
	
	/**
	 * Method for get data from a text file
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromReader (Reader reader) throws IOException {
	    BufferedReader bufIn = new BufferedReader(reader);
	    StringWriter   swOut = new StringWriter();
	    PrintWriter    pwOut = new PrintWriter(swOut);
	    String         tempLine;
	
	    while ((tempLine = bufIn.readLine()) != null) {
	      pwOut.println(tempLine);
	    }
	
	    pwOut.flush();
	
	    return swOut.toString();
	}
	
	
	
	
	/**
	 * Method to get singleton instance
	 * @return ScriptUtils
	 */
	public static synchronized ScriptUtils getInstance() {
		if (ourInstance == null) {
			ourInstance = new ScriptUtils();
			//ourInstance.initialize();
		}
		return ourInstance;
	}
	
	/**
	 * @return the activeClassLoader
	 */
	public ClassLoader getActiveClassLoader() {
		if (activeClassLoader==null) {
			activeClassLoader = getClass().getClassLoader(); // Current ClassLoader
		}
		return activeClassLoader;
	}


	/**
	 * @param activeClassLoader the activeClassLoader to set
	 */
	public void setActiveClassLoader(ClassLoader activeClassLoader) {
		this.activeClassLoader = activeClassLoader;
	}


	/**
	 * @param scriptGroup the scriptGroup to set
	 */
	@SuppressWarnings("rawtypes")
	public void setScriptGroup(Map<String, Map<String, Class>> scriptGroup) {	
		initialize();
		fnGeneral.setScriptGroup(scriptGroup);
	}

	/**
	 * Generic instance of ScricptEngine
	 * @return the jsEngine
	 */
	public ScriptEngine getEngine() {
		return jsEngine;
	}

	public ScriptEngine getStandaloneEngine() {
		ScriptEngineManager scriptManagerSA = new ScriptEngineManager();
		return scriptManagerSA.getEngineByExtension("js");
	}
	
	private void initialize() {		
	    getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).clear();
	    fnGeneral.setEngine(getEngine());
	    try {
			registerFunction("require",fnGeneral);
			registerFunction("requireByGroup",fnGeneral);
			//registerFunction("register",fnGeneral);
			registerFunction("loadJS",fnGeneral);
			registerFunction("registerJS",fnGeneral);
			registerFunction("contentRegisterJS",fnGeneral);
			registerFunction("registerObject",fnGeneral);
			loadLibrariesJS();
		} catch (SecurityException  | NoSuchMethodException  e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Load embbeded libraries
	 */
	private void loadLibrariesJS() {
		InputStream is;	
		for (int c=0; c<LIBRARIES_COUNT; c++) {
			is = getClass().getResourceAsStream(String.format("%s/%s",LIBRARIES_PATH,LIBRARIES[c]));
			if (is!=null) {
				logger.info(String.format("Loading JS library %s ...",LIBRARIES[c]));
				String jsContent;
				try {
					jsContent = IOUtils.toString(is);
					contentRegisterJS(jsContent);
				} catch (IOException e) {
					logger.error("Error reading file.",e);
				} catch (ScriptException e) {
					logger.error("Error loading and executing script.",e);
				}
			}
		}
	}

	/**
	 * Method to get funcionObject
	 * @param objClass
	 * @param methodName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private FunctionObject getFunctionObject(Scriptable objClass, String methodName) throws SecurityException, NoSuchMethodException {
		//Class[] parameters = new Class[] { String.class };
		//objClass.getClass().getMet
		for (Method registerMethod : objClass.getClass().getMethods()) {
			//Method registerMethod = objClass.getClass().getMethod(methodName, parameters);
			if (registerMethod.getName().equals(methodName)) {
				return new FunctionObject(methodName, registerMethod, objClass);
			}
		}
		
		return null;
	}
	
	
	public void registerFunction(String functionName, Scriptable obj) throws NoSuchMethodException {
		if (!jsEngine.getBindings(ScriptContext.GLOBAL_SCOPE).containsKey(functionName)) {
			FunctionObject fnObj = getFunctionObject(obj, functionName);
			getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).put(functionName, fnObj ); // adding GLOBAL to scope
		}
	}	

	
	public void registerObject(String objAlias, Object obj) throws ScriptException, SecurityException, NoSuchMethodException {
		fnGeneral.registerObject(objAlias, obj);
	}
	
	// ---- extraido de la parte de PluginTools ----
	/**
	 * Method to execute particular javascript code
	 * @param expression
	 * @param props
	 * @return
	 * @throws ScriptException
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public Map<String,Object> eval(String expression, Map<String,Object> props, Writer writer) throws ScriptException {


		ClassLoader cl = this.getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(cl);
	    Map<String,Object> returnData = new HashMap<>();
	    getEngine().getBindings(ScriptContext.ENGINE_SCOPE).clear();
		Object result = null;
		String expr;
	    StringBuilder sb = new StringBuilder();
	    sb.append("function evaluate(){                       ").append("\n");
    	sb.append("		try {		                          ").append("\n");
    	sb.append("  		%s                                ").append("\n");
    	sb.append("  	} catch (e){                          ").append("\n");
    	sb.append("			throw e;                  		  ").append("\n");
    	sb.append("  	}                                     ").append("\n");
    	sb.append("};                                         ").append("\n");
    	sb.append("evaluate();").append("\n");
    	expr = String.format(sb.toString(), expression);
    	
		NativeObject nobj;
    	if (props!=null) {
    		for (Entry<String, Object> key: props.entrySet()) {
    			Object data = props.get(key.getKey());
    			if (data instanceof Map) {
    				Map<String,Object> dataMap = (Map<String,Object>) data;
//    				nobj = new NativeObject();
//					for (Entry<String,Object> entry : dataMap.entrySet()) {
//					    nobj.defineProperty(entry.getKey(), entry.getValue(), NativeObject.CONST);
//					}			
    				
					HashMap mapTemp = new HashMap();
    				for (Entry<String, Object> objT: dataMap.entrySet()) {
    					mapTemp.put(objT.getKey(), objT.getValue());
    				}
					getEngine().put(key.getKey(), mapTemp);
    			} else {
    				getEngine().put(key.getKey(), data);
    			}
    		}
    	}
    	
    	try {
    		getEngine().eval("load('nashorn:mozilla_compat.js')");
    		getEngine().eval("load('nashorn:parser.js')");
    		result = getEngine().eval(expr); 
		    // --- restore all defines native objects ---
		    returnData.clear();
		    Bindings bindings = getEngine().getBindings(ScriptContext.ENGINE_SCOPE);
			for (Map.Entry<String, Object> scopeEntry : bindings.entrySet()) {
				Object value = scopeEntry.getValue();
				String name = scopeEntry.getKey();
				if (value instanceof NativeObject) {
					nobj = (NativeObject) value;
					Map<String,Object> mapa = new HashMap<>();
					for (Object key: nobj.getAllIds()) {
						Object valor = nobj.get((String)key,nobj.getParentScope());
						if (valor instanceof Wrapper) {
							mapa.put((String)key, ((Wrapper)valor).unwrap());
						} else {
							mapa.put((String)key, valor);
						}
					}
					returnData.put((String)name, mapa);
				} else if(value instanceof HashMap) {
					returnData.put((String)name, value);
				}
			}		  
		    if (writer!=null) {
				writer.flush();
				writer.close();
			}
    	} catch (ScriptException e) {
			throw e;
		} catch (IOException e){
			logger.error("Error",e);
		}
		
		Map<String,Object> scriptResult = new HashMap<>();
		scriptResult.put(RESULT_KEY, result);
		scriptResult.put(DATA_KEY, returnData);
		return scriptResult;
	}
	
	
	public void contentRegisterJS(String expression) throws ScriptException {
	    StringBuilder sb = new StringBuilder();
    	sb.append("%s").append("\n");
    	String expr = String.format(sb.toString(),expression);
    	Bindings bindings = getEngine().getBindings(ScriptContext.GLOBAL_SCOPE);
    	try {
    		getEngine().eval(expr, bindings);
		} catch (ScriptException e) {
			throw e;
		}
	}
	
	private void loadJS(String scriptName, boolean durable) throws ScriptException {
		URL urlResource = getActiveClassLoader().getResource(scriptName);
		if(urlResource != null){
			String script;
			try {
				script = IOUtils.toString(urlResource.openStream());
				Bindings bindings = getEngine().getBindings((durable?ScriptContext.GLOBAL_SCOPE:ScriptContext.ENGINE_SCOPE));			
				getEngine().eval(script,bindings);
			} catch (Exception e) {
				logger.debug(String.format("Reading error (%s)",scriptName));
				logger.error("", e);
			}
		} else {
			logger.debug(String.format("Resource %s not founded.",scriptName));
		}
	}
	
	public void registerJS(String scriptName) throws ScriptException {
		loadJS(scriptName,true);
	}
	
	
	public void loadJS(String scriptName) throws ScriptException {
		loadJS(scriptName,false);
	}
	
	
	/**
	 * Method wrapper for eval
	 * @param expression
	 * @param props
	 * @param writer
	 * @return
	 * @throws ScriptException
	 */
	public static <V> Map<String,Object> eval(String expression, Map<String,Object> props) throws ScriptException {
		return getInstance().eval(expression, props,null);
	}
	
	/**
	 * Method to execute a javascript content, with a tmpfile as mandatory
	 * @param script String content of the script
	 * @param context Map<String,Object>
	 * @param useTmpFileName ScriptFile mandatory if exist
	 * @return Object
	 * @throws ScriptException
	 */
	public static Map<String,Object> executeScript(String script, Map<String,Object> context, String useTmpFileName) throws ScriptException {
		String scriptToExecute = script;
		
		if (useTmpFileName!=null && !useTmpFileName.isEmpty()) {
			try {
				String fileValidation = System.getProperty("java.io.tmpdir")+File.separator+useTmpFileName+".js";
				logger.debug("[initState] Trying to read : "+fileValidation);
				File file = new File(fileValidation);
				scriptToExecute = getStringFromReader(new FileReader(file));
			} catch (IOException e) {
				logger.warn("Not find file", e.getMessage());
			}
		}
		
		Map<String,Object> result = null;
		try {
			result = eval(scriptToExecute,context);
		} catch (ScriptException e) {
			logger.error("",e);
			String message = e.getMessage().substring(e.getMessage().lastIndexOf(':') + 1,
					e.getMessage().indexOf(e.getFileName())-1);	
				message = message.substring(0, message.lastIndexOf("in"));
				 String[] ss = message.split(":");
				 if (ss.length > 0)
					 message = ss[ss.length - 1];
			throw new ScriptException(message);
		}
		return result;
	}
	
	/**
	 * Wrapper of executeScript(...)
	 * @param script String content of the script
	 * @param context Map<String,Object>
	 * @return Object
	 * @throws ScriptException
	 */
	public static Map<String,Object> executeScript(String script, Map<String,Object> context) throws ScriptException {
		return executeScript(script, context, null);
	}
	
	
	
}
