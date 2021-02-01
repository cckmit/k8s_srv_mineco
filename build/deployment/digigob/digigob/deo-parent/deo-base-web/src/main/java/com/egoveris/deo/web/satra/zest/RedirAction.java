package com.egoveris.deo.web.satra.zest;

import com.egoveris.deo.util.Constantes;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zest.ActionContext;


/**
 * 
 * @author lfishkel
 *
 */
public class RedirAction {

	private static final String MATCHES = "matches";
	
	private static final int POSICION_PALABRA_CLAVE = 2;
	private static final int POSICION_PRIMER_ID = 3;
	
	
	private String keyWord;
	private String path;
	
	private final static Logger logger = LoggerFactory.getLogger(RedirAction.class);
	/**
	 * retorna un Map, en una entrada la palabra clave ej: "tareas" y
	 * las demas entradas son los ID (ID1 , ID2 ,.., IDn) n se define en la constante CANTIDAD_ID_PERMITIDOS
	 * @param 
	 * @return Map keys: keyWord, ID1, ID2,.., IDn
	 */
	public Map<String,Object> obtenerVariablesRedireccion(ActionContext ac){
		
		HashMap<String,Object> hm = null; 
		try{
			
			HttpServletRequest hsr = ac.getServletRequest();
			this.path =((String[]) hsr.getAttribute(MATCHES))[0];
			String[] fullPathSplited = path.split("/");
			if(fullPathSplited.length<=3+Constantes.CANTIDAD_ID_PERMITIDOS){
				hm= new HashMap<String,Object>();
				this.keyWord = fullPathSplited[POSICION_PALABRA_CLAVE];
				hm.put(Constantes.KEYWORD,this.keyWord);
				for(int i=POSICION_PRIMER_ID; i<fullPathSplited.length;i++){
					String id = fullPathSplited[i];
					hm.put("ID"+(i-POSICION_PALABRA_CLAVE),id);
				}
			}
		}catch (Exception e) {
			if(this.keyWord !=null){
				logger.error("no se pudo redireccionar a "+this.keyWord,e);
			}else
			if(this.path == null){
				logger.error("no se pudo obtener la url ingresada",e);
			}else{
				logger.error("no se pudo redirigir",e);
			}
		}
		return hm;
	}

}
