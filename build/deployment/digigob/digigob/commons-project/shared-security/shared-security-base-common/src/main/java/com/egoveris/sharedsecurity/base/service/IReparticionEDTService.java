package com.egoveris.sharedsecurity.base.service;

import java.util.List;
import java.util.Map;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

/**
 * The Interface IReparticionService.
 */
public interface IReparticionEDTService {

  /**
   * Obtener reparticiones.
   *
   * @return the list
   */
  List<ReparticionDTO> listReparticiones();

  public List<String> obtenerReparticiones();
  
  /**
   * Guardar reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @throws NegocioException
   *           the negocio exception
   */
  ReparticionDTO guardarReparticion(ReparticionDTO reparticion) throws SecurityNegocioException;

  /**
   * Gets the reparticion by id.
   *
   * @param id
   *          the id
   * @return the reparticion by id
   */
  ReparticionDTO getReparticionById(Integer id);

  /**
   * Modificar reparticion.
   *
   * @param reparticion
   *          the reparticion
   */
  void modificarReparticion(ReparticionDTO reparticion);

  /**
   * Gets the reparticion by user name.
   *
   * @param userName
   *          the user name
   * @return the reparticion by user name
   */
  ReparticionDTO getReparticionByUserName(String userName);

  /**
   * Gets the reparticion by codigo.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @return the reparticion by codigo
   */
  ReparticionDTO getReparticionByCodigo(String codigoReparticion);

  /**
   * Gets the reparticion activa Y inactiva by codigo.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @return the reparticion activa Y inactiva by codigo
   */
  ReparticionDTO getReparticionActivaYInactivaByCodigo(String codigoReparticion);

  /**
   * Buscar reparticion by codigo Y nombre comodin.
   *
   * @param codigo
   *          the codigo
   * @return the list
   */
  List<ReparticionDTO> buscarReparticionByCodigoYNombreComodin(String codigo);

  /**
   * Buscar reparticion by codigo comodin.
   *
   * @param codigo
   *          the codigo
   * @return the list
   */
  List<ReparticionDTO> buscarReparticionByCodigoComodin(String codigo);
  
  /**
   * Busca todas las reparticiones asociadas al userName.
   * @param user
   *          the user
   * @return the list
   */
  List<Map> getAllReparticionByUserName(String user);

  /**
   * Gets all reparticion by its estructure, by the ID of the structure.
   * @param i
   * @return
   */
  List<ReparticionDTO> getOrganismoByEstructura(int idEstructure);
}