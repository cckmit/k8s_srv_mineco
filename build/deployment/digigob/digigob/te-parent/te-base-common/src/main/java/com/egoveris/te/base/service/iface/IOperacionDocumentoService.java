package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.OperacionDocumentoDTO;

public interface IOperacionDocumentoService {
	
	/**
	 * Obtiene todos los documentos ingresados que 
	 * pertenezcan a la operacion dada
	 * 
	 * @param idOperacion Identificador operacion
	 * @return Lista con documentos de la operacion
	 * @throws ServiceException Excepcion en caso de error de bdd/servicio
	 */
	public List<OperacionDocumentoDTO> getDocumentosOperacion(Long idOperacion) throws ServiceException;
	
	/**
	 * Metodo que ingresa o actualiza un documento (segun tipo) asociado
	 * a la operacion
	 * 
	 * @param operacionDocumentoDTO
	 * @throws ServiceException
	 */
	public void saveDocumentoOperacion(OperacionDocumentoDTO operacionDocumentoDTO) throws ServiceException;
	
	/**
	 * Metodo que elimina un documento asociado a la operacion
	 * 
	 * @param operacionDocumentoDTO
	 */
	public void deleteDocumentoOperacion(OperacionDocumentoDTO operacionDocumentoDTO);
	
	/**
	 * Metodo que copia los documentos de un expediente dado
	 * que tengan el atributo "Resultado" a una operacion dada
	 * 
	 * @param idEE - Id del Expediente Electronico
	 * @param idOperacion - Id de la Operacion
	 */
	public void copiarDocsResultadoFromEE(Long idEE, Long idOperacion);
	
}
