/**
 * 
 */
package com.egoveris.plugin.manager.test.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.egoveris.plugins.manager.plugins.annotations.Scriptable;
import com.egoveris.plugins.manager.plugins.interfaces.IScriptable;

/**
 * @author difarias
 *
 */
@Scriptable(refName="dummy")
public class Dummy  implements IScriptable  {
	@Override
	public String getScriptFunctions(String refName) {
		StringBuilder builder = new StringBuilder();
		builder.append("var _dummy_ = dummy");
		builder.append("");
		builder.append("function archivo(filename) { ");
		builder.append("	x.show(x.getData(filename));"); // 'c:\\setup.log'
		builder.append("}");
		
		return builder.toString();
	}
	
	public String saludo(String nombre) {
		return String.format("hola k ase %s !",nombre);
	}

	/**
	 * este metodo es para saludo
	 * @return
	 */
	public String saludo() {
		return String.format("hola k ase %s !","PEPE");
	}
	
	public String getWSDL(String param) { 
		System.out.println("Obteniendo wsdl: "+param);
		
		return "prueba";
	}
	
	public InputStream getData(String fileName) throws Exception {
		return new FileInputStream(new File(fileName));
	}
	
	public void show(InputStream input) throws Exception {
		String data = IOUtils.toString(input);
		System.out.println("Archivo: "+data);
	}
	
}
