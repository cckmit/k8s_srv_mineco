package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;

public interface ITipoOperacionOrganismoService {
	
	/**
	 * Devuelve todos los posibles organismos a escoger, 
	 * para relacionarlos con el tipo de operacion, siempre y
	 * cuando esten vigentes
	 * 
	 * @param tipoOperacion Tipo de operacion (se acepta null)
	 * @return Lista con los posibles organismos
	 * @throws ServiceException
	 */
	public List<TipoOperacionOrganismoDTO> getPosiblesOrganismos(TipoOperacionDTO tipoOperacion) throws ServiceException;
	
	/**
	 * Devuelve los organismos correspondientes
	 * al tipo de operacion
	 * 
	 * @return Lista con los organismos correspondientes
	 * @throws ServiceException
	 */
	public List<TipoOperacionOrganismoDTO> getOrganismosByTipoOperacion(Long idTipoOperacion) throws ServiceException;
	
	/**
	 * Guarda los organismos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @param organismosOper Lista de organismos a guardar
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void saveOrganismosTipoOperacion(Long idTipoOperacion, List<TipoOperacionOrganismoDTO> organismosOper) throws ServiceException;
	
	/**
	 * Elimina los organismos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void deleteOrganismosTipoOper(Long idTipoOperacion) throws ServiceException;
	
}
