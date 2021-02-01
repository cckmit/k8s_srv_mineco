package com.egoveris.edt.base.service.actuacion;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.model.model.ActuacionSadeDTO;

import java.util.Date;
import java.util.List;

public interface IActuacionService {

  /**
   * Retrieve active list of Actuaciones.
   * 
   * @return List<ActuacionDTO>
   * @throws NegocioException
   *           exception
   */
   public List<ActuacionDTO> listActuacion();

  /**
   * Retrive the list of Actuaciones by codigo or nombre.
   * 
   * @param codigo
   *          parámetro código
   * @return List<ActuacionDTO>
   * @throws NegocioException
   *           exception
   */
  List<ActuacionDTO> buscarActuacionByCodigoYNombreComodin(String codigo);

  /**
   * Search an Actuacion by codigo.
   * 
   * @param codigo
   *          parámetro codigo
   * @return
   * @throws NegocioException
   *           exception
   */
  ActuacionDTO getActuacionByCodigo(String codigo);

  /**
   * Persist an Actuacion.
   * 
   * @param actuacion
   * @throws NegocioException
   *           exception
   */
  void guardarActuacion(ActuacionDTO actuacion);
  
  
  /**
   * Obtener lista todas las actuaciones activas vigentes Y es documento.
   *
   * @param vigenciaDesde the vigencia desde
   * @param vigenciaHasta the vigencia hasta
   * @return the list
   */
  List<ActuacionSadeDTO> obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(Date vigenciaDesde, Date vigenciaHasta);

  /**
   * Existe documentos asociados.
   *
   * @param idActuacion the id actuacion
   * @return true, if successful
   */
  boolean existeDocumentosAsociados(Integer idActuacion);

	public List<ActuacionSadeDTO> obtenerActuacion();
  
}