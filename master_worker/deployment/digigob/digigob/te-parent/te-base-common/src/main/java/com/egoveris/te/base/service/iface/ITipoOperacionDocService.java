package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;

public interface ITipoOperacionDocService {
	
	/**
	 * Devuelve todos los posibles tipos de documentos a escoger
	 * que posean el tipo de produccion Importado, para relacionarlos con el 
	 * tipo de operacion
	 * 
	 * @param tipoOperacion Tipo de operacion (se acepta null)
	 * @return Lista con los posibles tipos de documento
	 * @throws ServiceException
	 */
	public List<TipoOperacionDocDTO> getPosiblesTiposDocumentos(TipoOperacionDTO tipoOperacion) throws ServiceException;
	
	/**
	 * Devuelve los tipos de documentos correspondientes
	 * al tipo de operacion
	 * 
	 * @return Lista con los tipos de documentos correspondientes
	 * @throws ServiceException
	 */
	public List<TipoOperacionDocDTO> getTiposDocumentosByTipoOperacion(Long idTipoOperacion) throws ServiceException;
	
	/**
	 * Guarda los tipos de documentos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @param tipoDocsOper Lista de tipos documentos a guardar
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void saveDocumentosTipoOperacion(Long idTipoOperacion, List<TipoOperacionDocDTO> tipoDocsOper) throws ServiceException;
	
	/**
	 * Elimina los tipos de documentos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void deleteTiposDocsTipoOper(Long idTipoOperacion) throws ServiceException;
	
}
