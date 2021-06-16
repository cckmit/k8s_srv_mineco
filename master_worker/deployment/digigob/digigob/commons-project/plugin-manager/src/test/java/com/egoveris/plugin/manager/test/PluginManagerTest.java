/**
 * 
 */
package com.egoveris.plugin.manager.test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.IDeployHandler;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.plugins.manager.deployers.FileDeployer;
import com.egoveris.plugins.manager.deployers.WebDavDeployer;
import com.egoveris.plugins.manager.deployers.strategies.ProgressiveDeploy;
import com.egoveris.plugins.manager.deployers.strategies.RawDeploy;
import com.egoveris.plugins.manager.model.ExecutableInfo;
import com.egoveris.plugins.manager.plugins.exceptions.ExecutableException;
import com.egoveris.plugins.manager.plugins.exceptions.UnauthorizedException;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;

/**
 * @author difarias
 *
 */
public class PluginManagerTest {
	final static Logger logger = LoggerFactory.getLogger(PluginManagerTest.class);

	private PluginManager pm;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pm = PluginManager.getInstance();
		pm.setDeployStrategy(new ProgressiveDeploy());
		pm.setWorkingDirectory("C:\\");
		//pm.setDeployStrategy(new RawDeploy());
	}

	@Test
	public void deployTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.deployTest()");
		pm.deploy("C:\\Users\\ggefaell\\Documents\\egoveris-codigo\\workflow-designer\\target\\Designer-2.0.0-SNAPSHOT.jar");
	}

	@Test
	public void deployMultipleTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.deployMultipleTest()");
		
		List<String> listFiles = new ArrayList<String>();
		listFiles.add("C:\\Desarrollo\\test\\pluginA-0.0.1-SNAPSHOT.jar");
		listFiles.add("C:\\Desarrollo\\test\\jasper-compiler.jar");
		
		pm.deploy(listFiles);
	}

	@Test
	public void redeployTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.redeployTest()");
		pm.deploy("C:\\Desarrollo\\workspace\\pluginA\\target\\pluginA-0.0.1-SNAPSHOT.jar");
		pm.redeploy("C:\\Desarrollo\\workspace\\pluginA\\target\\pluginA-0.0.1-SNAPSHOT.jar");
	}
	
	@Test
	public void demo1Test() {
		/*
		logger.info("-----------------------");
		logger.info("PluginManagerTest.demo1Test()");
		pm.deploy("C:\\Desarrollo\\workspace\\pluginA\\target\\pluginA-0.0.1-SNAPSHOT.jar");
		List<IState> lstObj = pm.searchClasses("com.everis.plugin");
		System.out.printf("total class founded (%d) \n",lstObj.size());
		int counter=0;
		for (IState state: lstObj) {
			System.out.printf("%d -> %s %s \n",++counter,state.getName(),state.aceptReject());
		}
		
		assertTrue(true);
		*/
	}
	
	@Test
	public void printTest(){
		logger.info("Printing...");
		FileDeployer fd = new FileDeployer();
		
		fd.setTimeout(10);
				
		//fd.setDirectoryToMonitoring("c:\\Desarrollo\\test");
		
		fd.start();
		int counter=20;

		while(--counter>0) {
			try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

		counter=200;
		while(--counter>0) {logger.info("--> "+counter);}
		fd.pause();
		counter=20000;
		while(--counter>0) {}
		fd.stop();

		counter=5;
		while(--counter>0) {
			try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	@Test
	public void fileExplorerTest(){
		FileDeployer fd = new FileDeployer();
		
		fd.setTimeout(10);
		//fd.setDirectoryToMonitoring("c:\\Desarrollo\\test\\");
		fd.setUrlToMonitoring("file:///c:/Desarrollo/test");
		
		fd.setDeployHandler(new IDeployHandler() {
			private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			public void deploy(String... pluginJar) {
				this.deploy(Arrays.asList(pluginJar));
			}

			public void deploy(List<String> pluginJar) {
				for (String jarname: pluginJar) {
					System.out.printf("(%s) Deploying %s \n", sdf.format(new Date()),jarname);
				}
			}

			public void redeploy(String... pluginJar) {
				this.redeploy(Arrays.asList(pluginJar));
			}

			public void redeploy(List<String> pluginJar) {
				for (String jarname: pluginJar) {
					System.out.printf("(%s) Redeploying %s \n", sdf.format(new Date()),jarname);
				}
			}
		});
		
		fd.start();

//		while(true) {
//			try {
//	            Thread.sleep(1000);
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	    }
	}
	
	@Test
	public void activateDeployersTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.deployTest()");
		
		final String PLUGIN_MANAGER_DIRECTORY = "file:///c:/Desarrollo/PluginManager2";
		
		
		pm.setWorkingDirectory(PLUGIN_MANAGER_DIRECTORY);
		
		FileDeployer fd = new FileDeployer();
		fd.setUrlToMonitoring(PLUGIN_MANAGER_DIRECTORY+"/deploy");
		pm.getDeployers().clear();
		pm.getDeployers().add(fd);
		
		pm.startDeployers();

		Scanner s = new Scanner(System.in);
		String input="";
		while (!input.equals("exit")) {
			input = s.nextLine();
			
			if (input.equalsIgnoreCase("start"))
				pm.startDeployers();
			
			if (input.equalsIgnoreCase("stop"))
				pm.stopDeployers();

			if (input.equalsIgnoreCase("versions")) {
				pm.printVersions();
				continue;
			}
			
			if (input.equalsIgnoreCase("doit")) {
				try {
					pm.execute("RuleEngine");
				} catch (ExecutableException e) {
					e.printStackTrace();
				} catch (UnauthorizedException e) {
					e.printStackTrace();
				}
			}
			
			if (input.equalsIgnoreCase("script")) {
				ScriptUtils su = ScriptUtils.getInstance();
				
				URL urlFile =  this.getClass().getResource("/scriptRelationTest.js");
				File file = new File(urlFile.getFile());
				String scriptContent="";
				try {
					scriptContent = su.getStringFromReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Map<String, Object> vars = new HashMap<String,Object>();
				Map<String, Object> data = new HashMap<String,Object>();
				
				data.put("VARS", vars);
				
				try {
					Object resultado  = su.executeScript(scriptContent, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			
			
			if (input.equalsIgnoreCase("lists")) {
				List lstObj = pm.searchClasses("com.everis.plugin");
				
				if (lstObj!=null && !lstObj.isEmpty()) {
					System.out.printf("total class founded (%d) \n",lstObj.size());
					int counter=0;
					for (Object obj: lstObj) {
						logger.info("--> "+obj.getClass().getName());
					}
					/*
					for (IState state: lstObj) {
						System.out.printf("%d -> %s %s \n",++counter,state.getName(),state.aceptReject());
					}
					*/
				}
			}
			
			if (input.startsWith("version")) {
				String[] data = input.split(" ");
				
				if (data.length>1) {
					pm.switchVersion(data[1]);
				} else {
					logger.info("VERSION : "+pm.getActiveVersion().getName());
				}
			}
			
			try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
		
		pm.stopDeployers();
	}
	
	@Test
	public void executableTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.deployTest()");
		
		final String PLUGIN_MANAGER_DIRECTORY = "file:///c:/Desarrollo/PluginManager2";
		
		
		pm.setWorkingDirectory(PLUGIN_MANAGER_DIRECTORY);
		
		FileDeployer fd = new FileDeployer();
		fd.setUrlToMonitoring(PLUGIN_MANAGER_DIRECTORY+"/deploy");
		pm.getDeployers().clear();
		pm.getDeployers().add(fd);
		
		pm.startDeployers();
		
		int counter=0;
		
		while(true) {
			try {
	            Thread.sleep(1000);
	            counter++;
	            
	            if (counter==7) {
	            	logger.info("calling executable...");
	            	try {
						pm.execute("hola2");
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            
	            if (counter==15) {
	            	logger.info("....Executable info....");
	            	for (ExecutableInfo info : pm.getExecsInfo()) {
	            		logger.info(info.toString());
	            	}
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	@Test
	public void activateDeployersWEBDAVTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.deployTest()");
		
		String PLUGIN_MANAGER_DIRECTORY = "file://c:/Desarrollo/PluginManager2";
		String WEBDAV_ROOT = "/PluginManager/ee";
		
		
		pm.setWorkingDirectory(PLUGIN_MANAGER_DIRECTORY);
		
		
		WebDavDeployer fd = new WebDavDeployer();
		
		fd.setDirectoryDestiny(PLUGIN_MANAGER_DIRECTORY+"/deploy");
		//fd.setUrlToMonitoring(WEBDAV_ROOT+"/deploy");
		fd.setUrlToMonitoring("Workflow_Designer/Workflows/SRC/");
		fd.setHostname("webdav.gcaba.everis.int");
		fd.setPort(80);
		fd.setDocumentRoot("/guarda-documental-prod");
		fd.setDefaultUsername("admin");		
		//fd.setDefaultPassword("<<passwd>>k2+ka5qAf3xBiiu58W9jNuQQK7cwxcLr7yBzeyYdYKA=");
		fd.setDefaultPassword("admin");
		fd.setDefaultConnectionTimeout(15000);
		
		pm.getDeployers().clear();
		pm.getDeployers().add(fd);
		
		pm.startDeployers();

		Scanner s = new Scanner(System.in);
		String input="";
		while (!input.equals("exit")) {
			input = s.nextLine();
			
			if (input.equalsIgnoreCase("start"))
				pm.startDeployers();
			
			if (input.equalsIgnoreCase("stop"))
				pm.stopDeployers();

			if (input.equalsIgnoreCase("versions")) {
				pm.printVersions();
				continue;
			}
			
			if (input.equalsIgnoreCase("script")) {
				ScriptUtils su = ScriptUtils.getInstance();
				
				URL urlFile =  this.getClass().getResource("/scriptRequireTest.js");
				File file = new File(urlFile.getFile());
				String scriptContent="";
				try {
					scriptContent = su.getStringFromReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Map<String, Object> vars = new HashMap<String,Object>();
				Map<String, Object> data = new HashMap<String,Object>();
				
				data.put("VARS", vars);
				
				try {
					Object resultado  = su.executeScript(scriptContent, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			
			
			if (input.equalsIgnoreCase("lists")) {
				List lstObj = pm.searchClasses("com.everis.plugin");
				
				if (lstObj!=null && !lstObj.isEmpty()) {
					System.out.printf("total class founded (%d) \n",lstObj.size());
					int counter=0;
					for (Object obj: lstObj) {
						logger.info("--> "+obj.getClass().getName());
					}
					/*
					for (IState state: lstObj) {
						System.out.printf("%d -> %s %s \n",++counter,state.getName(),state.aceptReject());
					}
					*/
				}
			}
			
			if (input.startsWith("version")) {
				String[] data = input.split(" ");
				
				if (data.length>1) {
					pm.switchVersion(data[1]);
				} else {
					logger.info("VERSION : "+pm.getActiveVersion().getName());
				}
			}
			
			try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
		
		pm.stopDeployers();
	}
	
	@Test
	public void scriptableTest() {
		logger.info("-----------------------");
		logger.info("PluginManagerTest.scriptableTest()");
		
		String PLUGIN_MANAGER_DIRECTORY = "file:///c:/Desarrollo/PluginManager2";
		
		pm.setWorkingDirectory(PLUGIN_MANAGER_DIRECTORY);
		
		FileDeployer fDeployer = new FileDeployer();
		fDeployer.setUrlToMonitoring(PLUGIN_MANAGER_DIRECTORY+"/deploy");		
		pm.getDeployers().clear();
		pm.getDeployers().add(fDeployer);
		
		pm.startDeployers();

		Scanner s = new Scanner(System.in);
		String input="";
		while (!input.equals("exit")) {
			input = s.nextLine();
			if (input.equalsIgnoreCase("start"))
				pm.startDeployers();
			
			if (input.equalsIgnoreCase("stop"))
				pm.stopDeployers();

			if (input.equalsIgnoreCase("versions")) {
				pm.printVersions();
				continue;
			}
			if (input.equalsIgnoreCase("script")) {
				ScriptUtils su = ScriptUtils.getInstance();
				
//				URL urlFile =  this.getClass().getResource("/scriptRequireWsInvokerTest.js");
//				File file = new File(urlFile.getFile());
				File file = new File("C:\\llevar\\RuleEngineTEST\\scriptRuleEngine.js");
				String scriptContent="";
				try {
					scriptContent = su.getStringFromReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Map<String, Object> vars = new HashMap<String,Object>();
				Map<String, Object> data = new HashMap<String,Object>();
				
				vars.put("reparticion","MGEYA");
				vars.put("sector","AA2000");
				data.put("CV", vars);
				
				try {
					Object resultado  = su.executeScript(scriptContent, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			if (input.equalsIgnoreCase("lists")) {
				List lstObj = pm.searchClasses("com.everis.plugin");
				
				if (lstObj!=null && !lstObj.isEmpty()) {
					System.out.printf("total class founded (%d) \n",lstObj.size());
					int counter=0;
					for (Object obj: lstObj) {
						logger.info("--> "+obj.getClass().getName());
					}
					/*
					for (IState state: lstObj) {
						System.out.printf("%d -> %s %s \n",++counter,state.getName(),state.aceptReject());
					}
					*/
				}
			}
			if (input.startsWith("version")) {
				String[] data = input.split(" ");
				
				if (data.length>1) {
					pm.switchVersion(data[1]);
				} else {
					logger.info("VERSION : "+pm.getActiveVersion().getName());
				}
			}
			
			try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
		
		pm.stopDeployers();
	}
	
	private <T> List<T> substract(List<T> oldList, List<T> newlist) {
		List<T> result = new ArrayList<T>();
		
		for(T actualData: oldList) {
			if (!newlist.contains(actualData)) {
				result.add(actualData);
			}
		}
		
		return result;
	}
	
	private List<String> checkRemoved(List<String> lastDeployed, List<String> pluginJar) {
		logger.info("Checking for removed...");
		
		List<String> result = new ArrayList<String>();
		// --- normalize names ---
		if (pluginJar!=null && !pluginJar.isEmpty()) {
			for(String aux: lastDeployed) {
				boolean toRemove=true;
				aux = FilenameUtils.getName(aux);
				for(String newDeploy: pluginJar) {
					newDeploy = FilenameUtils.getName(newDeploy);
					if (aux.equals(newDeploy)) toRemove=false;
				}
				
				if (toRemove) result.add(aux);
			}
		} else {
			result.addAll(pluginJar);
		}
		
		return result;
	}
	
	

	@Test
	public void excludeTest() {
		List<String> oldList = new ArrayList<String>();
		List<String> newLst = new ArrayList<String>();
		
		oldList.add("ola.txt");
		oldList.add("k.txt");
		oldList.add("/kkc/ase.txt");
		oldList.add("pepe.txt");
		
		//newLst.add("ola");
		//newLst.add("pepe.txt");
		newLst.add("/pepe/ase.txt");
		newLst.add("k.txt");
		newLst.add("ki.txt");
		
		List<String> toRemove = checkRemoved(oldList, newLst);
		
		if (toRemove!=null && toRemove!=null) {
			for (String str : toRemove) {
				logger.info("toRemove -> "+str);
			}
		} else {
			logger.info("nothing to remove");
		}
	}
	
}
