package com.egoveris.te.base.util;

import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Configuracion Inicial Modulo EE
 *
 */
public class ConfiguracionInicialModuloEEFactory { 
	static List<ParametrosSistemaExternoDTO> parametros = null;
	
	private static Logger logger = LoggerFactory.getLogger(ConfiguracionInicialModuloEEFactory.class);

	
	public ConfiguracionInicialModuloEEFactory() {}
	
 public static void guardarParametros(ParametrosSistemaExternoDTO si){
   if (logger.isDebugEnabled()) {
    logger.debug("guardarParametros(si={}) - start", si);
   }

   //configuracionInicialDAO.guardarSistemaExterno(si);

   if (logger.isDebugEnabled()) {
    logger.debug("guardarParametros(ParametrosSistemaExterno) - end");
   }
  }
	
	  
	public static ParametrosSistemaExternoDTO getParametrosSistemaExternoMockObject(){
		if (logger.isDebugEnabled()) {
			logger.debug("getParametrosSistemaExternoMockObject() - start");
		}
		
		ParametrosSistemaExternoDTO parametrosSistemaExterno = new ParametrosSistemaExternoDTO();
		
		parametrosSistemaExterno.setUrl("http://bac_sade.cysonline.com.ar/LoginSADE.aspx");
		parametrosSistemaExterno.setEsactivo(true);
		parametrosSistemaExterno.setParametros("?usr=testcys&exp=EX-2013-00122177-%20%20%20-MGEYA-MHGC");

		if (logger.isDebugEnabled()) {
			logger.debug("getParametrosSistemaExternoMockObject() - end - return value={}", parametrosSistemaExterno);
		}
		return parametrosSistemaExterno;
	}
	
 public static ParametrosSistemaExternoDTO obtenerParametrosPorTrata(Long idTrata){
   if (logger.isDebugEnabled()) {
    logger.debug("obtenerParametrosPorTrata(idTrata={}) - start", idTrata);
   }

   ParametrosSistemaExternoDTO ParametrosSistemaExternoDTO = null; 
   if (logger.isDebugEnabled()) {
    logger.debug("obtenerParametrosPorTrata(Integer) - end - return value={}", ParametrosSistemaExternoDTO);
   }
   return ParametrosSistemaExternoDTO;
  }

 
	
}
