/**
 * 
 */
package com.egoveris.plugin.manager.test.tools;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;

import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;

/**
 * @author difarias
 *
 */
public class ScriptUtilsTest {
	public ScriptUtils su;

	@Before
	public void initialize() {
		su = ScriptUtils.getInstance();
	}
	
//	@Test
//	public void registrationTest() throws Exception {
//		Dummy pepe = new Dummy();
//		su.registerObject("pepe", pepe);
//		ScriptEngine se = su.getStandaloneEngine();
//		assertTrue(su.getEngine().getBindings(ScriptContext.GLOBAL_SCOPE).containsKey("pepe"));
//	}
//	
//	@Test
//	@SuppressWarnings("static-access")
//	public void scriptTest() throws Exception {
//		Dummy pepe = new Dummy();
//		su.registerObject("dummyPlugin", pepe);
//
//		URL urlFile =  this.getClass().getResource("/scriptRequireTest.js");
//		File file = new File(urlFile.getFile());
//		String scriptContent = su.getStringFromReader(new FileReader(file));
//		
//		Map<String, Object> vars = new HashMap<String,Object>();
//		Map<String, Object> data = new HashMap<String,Object>();
//		
//		data.put("VARS", vars);
//		
//		try {
//			Object resultado  = su.executeScript(scriptContent, data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	public String getContentJS(String resource) throws FileNotFoundException, IOException { 
		URL urlFile =  this.getClass().getResource(resource);
		File file = new File(urlFile.getFile());
		String scriptContent = su.getStringFromReader(new FileReader(file));
		
		return scriptContent;
	}
//	
//	@Test
//	@SuppressWarnings("static-access")
//	public void scriptRegisterTest() throws Exception {
//		Dummy pepe = new Dummy();
//		su.registerObject("droolsPlugin", pepe);
//		
//		String envContent = getContentJS("/env.js");
//		
//		//String scriptContent = getContentJS("/scriptRegisterTest.js");
//		String scriptContentRq = getContentJS("/scriptRequireTestB.js");
//		String scriptContentA = getContentJS("/scriptRequireTest.js");
//		
//		Map<String, Object> vars = new HashMap<String,Object>();
//		Map<String, Object> data = new HashMap<String,Object>();
//		
//		data.put("VARS", vars);
//		
//		try {
//			//su.registerJS(envContent);
//			//su.registerJS(scriptContent);
//			
//			//su.executeScript(scriptContentA, data);
//			su.executeScript(scriptContentRq, data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	@SuppressWarnings("static-access")
//	public void scriptRelationTest() throws Exception {
//		Dummy pepe = new Dummy();
//		su.registerObject("dummy", pepe);
//		
//		String script = getContentJS("/scriptRelationTest.js");
//		
//		Map<String, Object> vars = new HashMap<String,Object>();
//		Map<String, Object> data = new HashMap<String,Object>();
//		
//		data.put("VARS", vars);
//		
//		try {
//			
//			su.executeScript(script, data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	@SuppressWarnings("static-access")
	public void scriptRelationTest() throws Exception {
		Dummy pepe = new Dummy();
		su.registerObject("dummy", pepe);
		
		String script = getContentJS("/scriptExceptions.js");
		
		Map<String, Object> vars = new HashMap<String,Object>();
		Map<String, Object> data = new HashMap<String,Object>();
		
		data.put("VARS", vars);
		
		try {
			
			su.executeScript(script, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
