package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;

public interface ITipoOperacionService {
	
	/**
	 * Devuelve todos los tipos de operacion que esten ingresados
	 * 
	 * @return Lista con todos los tipos de operacion ingresados
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public List<TipoOperacionDTO> getAllTiposOperacion() throws ServiceException;
	
	/**
   * Devuelve todos los tipos de operacion asociados a un organismo dado
   * 
   * @param idOrganismo
   * @return Lista con todos los tipos de operacion segun organismo
   * @throws ServiceException Excepcion en caso de error de bdd
   */
  public List<TipoOperacionDTO> getTiposOperacionOrganismoVigentes(Integer idOrganismo) throws ServiceException;
  
	/**
	 * Metodo que valida si un registro de tipo operacion esta duplicado
	 * Valida que el codigo de tipo operacion no se repita
	 * 
	 * @param tipoOperacionDto Dto con el tipo de operacion
	 * @return true, en caso de exito y false en caso de error
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public Boolean isCodigoDuplicado(TipoOperacionDTO tipoOperacionDto) throws ServiceException;
	
	/**
   * Metodo que ingresa nuevo registro o actualiza registro existente de tipo operacion
   * 
   * @param tipoOperacionDto Dto con el tipo de operacion a ingresar o actualizar
   * @param formulariosTipoOperacionDto Lista con los formularios dinamicos relacionados con el tipo de operacion
   * @param tipoDocumentosDto Lista con los tipos de documentos relacionados con el tipo de operacion
   * @param organismosDto Lista con los organismos relacionados con el tipo de operacion
   * @return true, en caso de exito y false en caso de error
   */
  public Boolean saveOrUpdateTipoOperacion(TipoOperacionDTO tipoOperacionDto, List<FormularioDTO> formulariosTipoOperacionDto, 
      List<TipoOperacionDocDTO> tipoDocumentosDto, List<TipoOperacionOrganismoDTO> organismosDto);
	
	/**
	 * Metodo que elimina un registro de tipo operacion y tambien elimina los formularios dinamicos relacionados
	 * 
	 * @param tipoOperacionDto Dto con el tipo de operacion a eliminar
	 * @return true, en caso de exito y false en caso de error
	 */
	public Boolean deleteTipoOperacion(TipoOperacionDTO tipoOperacionDto);
	
	/**
	 * Obtiene los ids de formularios dinamicos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @return Lista de FormularioDTO (con ids) de formularios dinamicos
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public List<FormularioDTO> getIdFormulariosTipoOperacion(Long idTipoOperacion) throws ServiceException;
	
	/**
	 * Guarda los formularios dinamicos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @param formulariosTipoOperacionDto Lista de formularios tipo operacion a guardar
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void saveFormulariosTipoOperacion(Long idTipoOperacion, List<FormularioDTO> formulariosTipoOperacionDto) throws ServiceException;
	
	/**
	 * Elimina los formularios dinamicos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @throws ServiceException Excepcion en caso de error de bdd
	 */
	public void deleteFormulariosTipoOperacion(Long idTipoOperacion) throws ServiceException;
	
	
}
