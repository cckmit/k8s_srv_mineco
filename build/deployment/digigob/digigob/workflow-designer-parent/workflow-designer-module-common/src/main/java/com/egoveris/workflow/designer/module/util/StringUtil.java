/**
 * 
 */
package com.egoveris.workflow.designer.module.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author difarias
 *
 */
public class StringUtil {
	
	/**
	 * Recursive method to convert text to CamelCase name for classes
	 * @param name
	 * @return Camel Case text
	 */
	public static String camelName(String name) {
		String separator=null;
		@SuppressWarnings("unused")
		int counter= 0;
		boolean transformToCamel=false;
		
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(name).find();
		
		if(hasSpecialChar){
			name = name.replaceAll("[^a-zA-Z0-9]","");
		}
		
		if (name.contains("-")) {separator="-"; counter++; transformToCamel=true;};		
		if (name.contains("_")) {separator="_"; counter++; transformToCamel=true;};
		if (name.contains(" ")) {separator=" "; counter++; transformToCamel=true;};
		
		if (!transformToCamel) return name;
		
		if (separator==null) return StringUtils.capitalize(name);
		
		String[] data = name.split(separator);
		String retorno = "";
		for (String str: data) {
			retorno += camelName(str);
		}
				
		return StringUtils.capitalize(retorno);
	}
}
