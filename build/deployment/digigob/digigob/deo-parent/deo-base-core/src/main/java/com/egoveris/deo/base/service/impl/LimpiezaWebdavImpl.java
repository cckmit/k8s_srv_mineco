package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoDeTrabajoException;
import com.egoveris.deo.base.service.LimpiezaWebdav;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;
import com.egoveris.shareddocument.base.exception.WebDAVWrongRequestException;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class LimpiezaWebdavImpl implements LimpiezaWebdav {

	@Autowired
	private IWebDavService webDavService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LimpiezaWebdavImpl.class);
	private static final String SLASH = "/";
	private static final String PREFIX_DOCUMENTOS_ADJUNTOS = "Proyectos_temporales/GEDO/Documentos_Adjuntos";
	private static final String PREFIX_DOCUMENTOS_DE_TRABAJO = "Proyectos_temporales/GEDO/Documentos_De_Trabajo";
	private static final String PREFIX_DOCUMENTOS_SADE = "Proyectos_temporales/GEDO/SADE";
	
	public void limpiezaSADE(String filename) throws ApplicationException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaSADE(String) - start"); //$NON-NLS-1$
    }

		try{
			borrarSade(filename);
		}catch(ApplicationException e){
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaSADE(String) - end"); //$NON-NLS-1$
    }
 	}
	
	public void limpiezaDocumentoAdjunto(String filename) throws ApplicationException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaDocumentoAdjunto(String) - start"); //$NON-NLS-1$
    }

		try{
			borrarDocumentoAdjunto(filename);
		}catch(Exception e){
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaDocumentoAdjunto(String) - end"); //$NON-NLS-1$
    }
 	}
	
	public void limpiezaDocumentoDeTrabajo(String filename) throws ApplicationException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaDocumentoDeTrabajo(String) - start"); //$NON-NLS-1$
    }
		try{
			borrarDocumentoDeTrabajo(filename);
		}catch(Exception e){
		  LOGGER.error("Error al limpiar documento adjunto"+e.getMessage(),e);
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaDocumentoDeTrabajo(String) - end"); //$NON-NLS-1$
    }
 	}
	
	public void limpiezaCarpetasVacias() throws ApplicationException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaCarpetasVacias() - start"); //$NON-NLS-1$
    }
		
		try{
			LOGGER.debug("Borrando carpetas temporales de WebDAV - SADE...");
			borrarCarpetasVacias(PREFIX_DOCUMENTOS_SADE);
			
			LOGGER.debug("Borrando carpetas temporales de WebDAV - Documentos Adjuntos...");
			borrarCarpetasVacias(PREFIX_DOCUMENTOS_ADJUNTOS);
			
			LOGGER.debug("Borrando carpetas temporales de WebDAV - Documentos de Trabajo...");
			borrarCarpetasVacias(PREFIX_DOCUMENTOS_DE_TRABAJO);
			
		} catch (Exception e) {
			LOGGER.error("Ha ocurrido un error al borrar las carpetas vacias del repositorio Webdav - " + e.getMessage());
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("limpiezaCarpetasVacias() - end"); //$NON-NLS-1$
    }
 	}
	
	private void borrarDocumentoAdjunto(String filename) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjunto(String) - start"); //$NON-NLS-1$
    }

		String spaceName = null;
		try{
			spaceName = obtenerSpaceName(filename);
			this.webDavService.deleteSpace(PREFIX_DOCUMENTOS_ADJUNTOS, spaceName, "", "");
			LOGGER.debug("Documentos Adjuntos borrados: " + filename);
		}catch(WebDAVResourceNotFoundException e){
			LOGGER.debug("No existen Documentos Adjuntos para: " + filename+e.getMessage(),e);
		}catch(Exception e){
			LOGGER.error("Error al eliminar documento adjunto"+e.getMessage(),e);
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjunto(String) - end"); //$NON-NLS-1$
    }
 	}

	private void borrarDocumentoDeTrabajo(String filename) throws ArchivoDeTrabajoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoDeTrabajo(String) - start"); //$NON-NLS-1$
    }

		String spaceName = null;
		try{
			spaceName = obtenerSpaceName(filename);
			this.webDavService.deleteSpace(PREFIX_DOCUMENTOS_DE_TRABAJO, spaceName, "", "");
			LOGGER.debug("Documentos de Trabajo borrados: " + filename);
		}catch(WebDAVResourceNotFoundException e){
			LOGGER.debug("No existen Documentos de Trabajo para: " + filename+e.getMessage(),e);
		}catch(Exception e){
			LOGGER.error("Error al eliminar documento de trabajo"+e.getMessage(),e);
			throw new ArchivoDeTrabajoException("No se ha podido borrar el archivo ubicado en: " + PREFIX_DOCUMENTOS_DE_TRABAJO + "/" + spaceName);
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoDeTrabajo(String) - end"); //$NON-NLS-1$
    }
 	}
	
	private void borrarSade(String filename) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarSade(String) - start"); //$NON-NLS-1$
    }

		String prefix = null;
		try{
			prefix = obtenerSpaceNameSade(filename);
			this.webDavService.deleteFile(PREFIX_DOCUMENTOS_SADE + "/" + prefix, filename, "", "");
			LOGGER.debug("Documento SADE borrado: " + filename);
		}catch(WebDAVResourceNotFoundException e){
			LOGGER.debug("No existen Documentos SADE para: " + filename+e.getMessage(),e);
		}catch(ApplicationException e){
			LOGGER.error("Error al eliminar documento SADE "+e.getMessage(),e);
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarSade(String) - end"); //$NON-NLS-1$
    }
 	}

	private String obtenerSpaceName(String filename){
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSpaceName(String) - start"); //$NON-NLS-1$
    }
		
		String espacios[] = filename.split("-"); 
		StringBuilder url = new StringBuilder("");
		String espaciosConExtension[] = espacios[3].split("\\.");
		String urlArray[] = {espacios[0], espacios[1], espacios[2]}; 
		for (String espacio : urlArray) 
			url.append(espacio + SLASH);
    String returnString = url.toString() + espaciosConExtension[0];
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSpaceName(String) - end"); //$NON-NLS-1$
    }
  		return returnString;
	}

	private String obtenerSpaceNameSade(String filename){
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSpaceNameSade(String) - start"); //$NON-NLS-1$
    }
		
		String espacios[] = filename.split("-"); 
		StringBuilder url = new StringBuilder("");
		String urlArray[] = {espacios[0], espacios[1], espacios[2]}; 
		for (String espacio : urlArray) {
			url.append(espacio + SLASH);
		}
		if(url.charAt(url.length()-1) == SLASH.charAt(0)){
			url.deleteCharAt(url.length()-1);
		}
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSpaceNameSade(String) - end"); //$NON-NLS-1$
    }
  		return returnString;
	}
	
	private void borrarCarpetasVacias(String prefix) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarCarpetasVacias(String) - start"); //$NON-NLS-1$
    }

		List<WebDAVResourceBean> directoryList = null;
		try{
			directoryList = this.webDavService.listSpace(prefix, "", "");
		}catch(WebDAVResourceNotFoundException e){
			LOGGER.error("Ha ocurrido un error al ingresar a la ruta: " + prefix + " - " + e.getMessage(),e);
			directoryList = null;
		}catch(WebDAVWrongRequestException e){
			LOGGER.error("Ha ocurrido un error al ingresar a la ruta: " + prefix + " - " + e.getMessage(),e);
			directoryList = null;
		}
		if(directoryList != null){
			directoryList.remove(0);
			if (!directoryList.isEmpty()){
				for(WebDAVResourceBean resource : directoryList){
					if (resource.isDirectorio()){
						//Saco el prefijo guarda-documental-XXXX/ del resource.getHref()
						String espacios[] = resource.getHref().split("/"); 
						StringBuilder url = new StringBuilder("");
						for (int i = 2 ; i < espacios.length ; i++) 
							url.append(espacios[i] + SLASH);
						
						borrarCarpetasVacias(url.toString());
					}
				}
			}else{
				try{
					//Separo la direccion dado que el metodo deleteSpace requiere "relativeUri" y "spaceName" y por lo tanto falla si envio null o "".
					String espacios[] = prefix.split("/"); 
					StringBuilder url = new StringBuilder("");
					for (int i = 0 ; i < espacios.length-1 ; i++) 
						url.append(espacios[i] + SLASH);
					this.webDavService.deleteSpace(url.toString().substring(0, url.toString().length()-1), 
							espacios[espacios.length-1], "", "");
					LOGGER.debug("Directorio borrado: " + prefix);
				}catch(Exception e){
					LOGGER.error("Mensaje de error", e);
				}
			}
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarCarpetasVacias(String) - end"); //$NON-NLS-1$
    }
 	}
}
