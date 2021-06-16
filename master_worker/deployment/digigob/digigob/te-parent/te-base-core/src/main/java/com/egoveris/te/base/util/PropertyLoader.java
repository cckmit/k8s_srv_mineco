package com.egoveris.te.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Carga un conjunto de propiedades en formato key value desde una ubicacion dentro 
 * del classpath. 
 * 
 * @author Vladimir Roubtsov
 * @author Rocco Gallo Citera
 *
 */
public class PropertyLoader{
    /**
     * Obtiene un recurso llamado 'name'en el classpath. El recurso debe mapearse
     * a un archivo con extension .properties. El nombre se asume absoluto y puede 
     * usarse "/" o "." para separacion de segmentos de paquetes, con un 
     * sufijo opcional "/" o ".properties". Ejemplo:
     * <pre> some.pkg.Resource
     * some.pkg.Resource.properties
     * some/pkg/Resource
     * some/pkg/Resource.properties
     * /some/pkg/Resource
     * /some/pkg/Resource.properties </pre> 
     * @param name nombre del recurso del classpath [no null]
     * @param loader classloader a travez del cual se cargara el recurso [null
     * indica el loader de la aplicacion]
     * 
     * @return recurso convertido a java.util.Properties [puede ser null si el recurso 
     * no fue encontrado y THROW_ON_LOAD_FAILURE es false]
     * @throws IllegalArgumentException si el recurso no fue encontrado y 
     * THROW_ON_LOAD_FAILURE es true
     */
    public static Properties loadProperties (String name, ClassLoader loader){
        name = validarParametroNombre(name);        
        Properties result;      
        if (loader == null) loader = ClassLoader.getSystemClassLoader ();            
        if (LOAD_AS_RESOURCE_BUNDLE){        	
        	log.info("Load as resource bundle: true");
        	result = cargarComoResourceBundle(name, loader); 
        }else{
        	result = cargarResourceDesdeStream(name, loader);
        }
        if (THROW_ON_LOAD_FAILURE && (result == null)){
	    	String msg = "could not load [" + name + "] as a classloader resource";
	    	log.info(msg);
            throw new IllegalArgumentException (msg);
        }
        return result;
    }

    /**
     * Realiza la carga de las propiedades desde un stream de entrada. 
     * Si hay un error en el lookup el result se devuelve como null. 
     * 
     * @param name nombre del recurso donde estan las propiedades
     * @param loader classloader asociado al recurso
     * @return propiedades cargadas
     */
	private static Properties cargarResourceDesdeStream(String name, ClassLoader loader) {
		Properties result = null;
		InputStream in = null;
		name = name.replace ('.', '/');                
	    if (! name.endsWith (SUFFIX))
	        name = name.concat (SUFFIX);                               
	    in = loader.getResourceAsStream (name);	    
	    try{		
	    	if (in != null){
	    		result = new Properties ();
	    		result.load (in); 
			}            
	    }catch (IOException e){
	    	log.error("cargarResourceDesdeStream IOException ", e);
			result = null;
	    }finally{
	    	try {
	    		if(in != null){
				in.close();
	    		}else{
	    			log.info("No se pudo cargar el recurso desde el Stream");
	    		}
			} catch (IOException e) {
				log.info("cargarResourceDesdeStream IOException finally ", e);
			} 
	    }
		return result;
		
	}

    /**
     * Realiza la carga de las propiedades desde un resource bundle.
     * Si el archivo no puede cargarse adecuadamente por un error de lookup, se 
     * produce un MissingResourceException.
     * 
     * @param name nombre del archivo de propiedades
     * @param loader classloader asociado al paquete
     * @return propiedades cargadas
     */
	
	private static Properties cargarComoResourceBundle(String name, ClassLoader loader) {
		Properties result;
		name = name.replace ('/', '.');
		final ResourceBundle rb = ResourceBundle.getBundle (name, Locale.getDefault(), loader);                
		result = new Properties ();
		for (Enumeration<String> keys = rb.getKeys (); keys.hasMoreElements ();){
		    final String key = (String) keys.nextElement ();
		    final String value = rb.getString (key);                    
		    result.put (key, value);
		}
		if (THROW_ON_LOAD_FAILURE){
			String msg = "could not load [" + name + "] as a classloader resource";
	    	log.info(msg);
            throw new IllegalArgumentException ("could not load [" + name + "] as a resource bundle");
        }
		return result;
	}

    /**
     * Valida que el parametro del nombre del archivo de propiedades tenga el formato adecuado.
     * 
     * @param name nombre a validar para obtener el nombre puro del archivo a levantar
     * @return
     */
	private static String validarParametroNombre(String name) {
		if (name == null){
			String msg = "null input: name";
			log.info(msg);
            throw new IllegalArgumentException (msg);
		}    
        if (name.startsWith ("/"))
            name = name.substring (1);            
        if (name.endsWith (SUFFIX))
            name = name.substring (0, name.length () - SUFFIX.length ());
		return name;
	}
    
    /**
     * Sobrecarga usada para abstraerse del classloader a usar.
     */
    public static Properties loadProperties (final String name){
        return loadProperties (name, Thread.currentThread ().getContextClassLoader ());
    }
    
    public static boolean isTHROW_ON_LOAD_FAILURE() {
		return THROW_ON_LOAD_FAILURE;
	}

	public static void setTHROW_ON_LOAD_FAILURE(boolean tHROWONLOADFAILURE) {
		THROW_ON_LOAD_FAILURE = tHROWONLOADFAILURE;
	}

	public static boolean isLoadAsResourceBundle() {
		return LOAD_AS_RESOURCE_BUNDLE;
	}
    
	public static void setLoadAsResourceBundle(boolean lOAD_AS_RESOURCE_BUNDLE) {
		LOAD_AS_RESOURCE_BUNDLE = lOAD_AS_RESOURCE_BUNDLE;
	}
	
    private static final Logger log = LoggerFactory
			.getLogger(PropertyLoader.class);
    private static boolean THROW_ON_LOAD_FAILURE = true;
	private static boolean LOAD_AS_RESOURCE_BUNDLE = false;
    private static final String SUFFIX = ".properties";
} 
