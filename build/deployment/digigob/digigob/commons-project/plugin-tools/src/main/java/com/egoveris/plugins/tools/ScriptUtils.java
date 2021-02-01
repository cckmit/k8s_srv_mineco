/**
 * 
 */
package com.egoveris.plugins.tools;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import javax.script.ScriptException;

/**
 * 
 * Wrapper class for ScriptUtils in PluginManager
 * 
 * @see com.egoveris.plugins.manager.tools.scripts.ScriptUtils 
 * @author difarias
 *
 */
@Deprecated
@SuppressWarnings("restriction")
public final class ScriptUtils {
	
	public static final String DATA_KEY = "DATA";
	public static final String RESULT_KEY = "RESULT";
	
	public static final com.egoveris.plugins.manager.tools.scripts.ScriptUtils SCRIPT_UTILS = com.egoveris.plugins.manager.tools.scripts.ScriptUtils.getInstance(); 

	public static String getStringFromReader (Reader reader) throws IOException {
		return com.egoveris.plugins.manager.tools.scripts.ScriptUtils.getStringFromReader(reader);
	}
	
	/**
	 * Method to execute particular javascript code
	 * @param expression
	 * @param props
	 * @return
	 * @throws ScriptException
	 */
	public static Map<String,Object> eval(String expression, Map<String,Object> props, Writer writer) throws ScriptException {
		return SCRIPT_UTILS.eval(expression, props, writer);
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
		return eval(expression, props,null);
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
		return com.egoveris.plugins.manager.tools.scripts.ScriptUtils.executeScript(script, context, useTmpFileName);
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
