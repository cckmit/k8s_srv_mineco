/**
 * 
 */
package com.egoveris.plugins.tools;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author difarias
 *
 */
public final class ConfigurationUtils {
	private static boolean configureClass(Object obj, XMLConfiguration config) {
		System.out.println("trying to configure : "+obj.getClass().getSimpleName());
        XMLBeanDeclaration bean = new XMLBeanDeclaration(config ,obj.getClass().getSimpleName()); 
        if (bean!=null) {
        	for (String key : bean.getNestedBeanDeclarations().keySet()) {
        		Object objValue = bean.getNestedBeanDeclarations().get(key);
        		
        		if (objValue instanceof XMLBeanDeclaration) {
            		XMLBeanDeclaration xbean = (XMLBeanDeclaration) bean.getNestedBeanDeclarations().get(key);
            		BeanHelper.setProperty(obj, xbean.getNode().getName(), xbean.getNode().getValue());
        		} else {
        			for (XMLBeanDeclaration dec: ((List<XMLBeanDeclaration>) objValue)) {
                		BeanHelper.setProperty(obj, dec.getNode().getName(), dec.getNode().getValue());
        			}
        		}
        	}
        	System.out.println(obj.getClass().getSimpleName()+" was configurated.");
        	return true;
        }
        
        return false;
	}


	/**
	 * Method to configure a list of clases via XML file resource;
	 * @param xmlConfiguration
	 * @param classInstances
	 */
	public static void configure(URL xmlConfiguration, List<Object> classInstances) {
		if (xmlConfiguration!=null) {
			try {
				XMLConfiguration config = new XMLConfiguration(xmlConfiguration);
				config.setDefaultListDelimiter((char)0);
				config.setDelimiterParsingDisabled(false);
				
				for (Object obj : classInstances){
					configureClass(obj,config);
				}
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to configure a list of clases via XML file resource;
	 * @param xmlConfiguration
	 * @param classInstances
	 */
	public static List<String> getConfigureClassNames(URL xmlConfiguration, List<Object> classInstances) {
		List<String> lstClasses = new ArrayList<String>();
		if (xmlConfiguration!=null) {
			try {
				XMLConfiguration config = new XMLConfiguration(xmlConfiguration);
				config.setDefaultListDelimiter((char)0);
				config.setDelimiterParsingDisabled(false);
				
				XPathFactory xPathFactory = XPathFactory.newInstance();
			    XPath xpath = xPathFactory.newXPath();
		 
			    XPathExpression expr = xpath.compile("/config/*");
			    NodeList nodeSetResult = (NodeList) expr.evaluate(config.getDocument(), XPathConstants.NODESET);
			    
			    for (int i = 0; i < nodeSetResult.getLength(); i++) {
			    	Node node = nodeSetResult.item(i);
			    	boolean toAdd=true;
			    	if (node.getAttributes()!=null) {
			    		String configClass=node.getAttributes().getNamedItem("config-class").getNodeValue();
			    		if (classInstances!=null) {
				    		for (Object obj: classInstances) { // check if class is always instantiated
				    			toAdd=(!obj.getClass().getName().equalsIgnoreCase(configClass)); 
				    		}
			    		}
			    		if (toAdd) {
			    			lstClasses.add(configClass);
			    		}
			    	}
			    }
			} catch (ConfigurationException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		
		return lstClasses;
	}

}
