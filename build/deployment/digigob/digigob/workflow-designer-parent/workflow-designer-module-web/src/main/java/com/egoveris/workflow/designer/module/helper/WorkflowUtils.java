/**
 * 
 */
package com.egoveris.workflow.designer.module.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.validator.routines.UrlValidator;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * @author difarias
 *
 */
public final class WorkflowUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WorkflowUtils.class);

	/**
	 * Method to know what the last version of a determined workflow by name
	 * @param processEngine
	 * @param workflowName
	 * @return Integer
	 */
	public static Integer findLastVersion(ProcessEngine processEngine, String workflowName) {
		if (logger.isDebugEnabled()) {
			logger.debug("findLastVersion(ProcessEngine, String) - start"); //$NON-NLS-1$
		}
 
		Integer result=-1;
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> lst = query.list();
		
		for (ProcessDefinition pd : lst) {
			if (pd.getName().equalsIgnoreCase(workflowName) && !pd.isSuspended() && pd.getVersion()>result) {
				result=pd.getVersion();
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("findLastVersion(ProcessEngine, String) - end"); //$NON-NLS-1$
		}
		return result;
	}
	
	
	/**
	 * Method to search and install a workflow (jpdl file) through ProcessEngine 
	 * @param workflowResource URL to a resource
	 * @param processEngine ProcessEngine
	 */
	public static void searchAndDeploy(URL workflowResource, ProcessEngine processEngine) {
		if (logger.isDebugEnabled()) {
			logger.debug("searchAndDeploy(URL, ProcessEngine) - start"); //$NON-NLS-1$
		}

		try {
			InputStream is = null;
				
			if (workflowResource.getProtocol().equalsIgnoreCase("jar")) {
			    JarURLConnection uc = (JarURLConnection) workflowResource.openConnection();
			    is = uc.getInputStream();
			} else {
				is = new FileInputStream(new File(workflowResource.getFile()));
			}
			
		    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    documentBuilderFactory.setNamespaceAware(true);
		    DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
		    
		    InputSource source = new InputSource(is);
		    Document doc = builder.parse(source);
		    
		    XPathFactory xPathFactory = XPathFactory.newInstance();
		    XPath xpath = xPathFactory.newXPath();
	 
		    XPathExpression expr = xpath.compile("//node()");
		    NodeList nodeSetResult = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		    
		    String workflowName = "";
		    Integer version = 0;
		    for (int i = 0; i < nodeSetResult.getLength(); i++) {
		    	Node node = nodeSetResult.item(i);
		    	if (node.getNodeName().equalsIgnoreCase("process")) {
		    		workflowName = node.getAttributes().getNamedItem("name").getTextContent();
		    		version = new Integer(node.getAttributes().getNamedItem("version").getTextContent());
		    		break;
		    	}
		    }
		    
			if (processEngine!=null && workflowResource!=null) {
	    		System.out.printf("Trying to deploy workflowname: %s - Version: %s \n",workflowName,version);
				Integer workflowVersion = findLastVersion(processEngine, workflowName);

				if (workflowVersion<0 || (workflowVersion>0 && workflowVersion<version.intValue())) {
					if (processEngine!=null) {
						try {
							NewDeployment nDep = processEngine.getRepositoryService().createDeployment().addResourceFromUrl(workflowResource);
							String deploy = nDep.deploy();
							System.out.printf("Version %s of %s was deployed [%s] \n", version,workflowName,deploy);
						} catch (Exception e) {
							logger.error("searchAndDeploy(URL, ProcessEngine)", e); //$NON-NLS-1$

							e.printStackTrace();
						}
					}
				} else {
					System.out.println("Don't need to deploy workflow.");
				}
			}
		} catch (Exception e) {
			logger.error("searchAndDeploy(URL, ProcessEngine)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("searchAndDeploy(URL, ProcessEngine) - end"); //$NON-NLS-1$
		}
	}
	
	public static boolean validateURL(String url){
		String[] schemes = {"http","https","rmi","ftp"}; // DEFAULT schemes = "http", "https", "ftp"
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(url)) {
		   return true;
		}
		 
		return false;
	}
}
