package com.egoveris.sharedorganismo.base.service;

import com.egoveris.sharedorganismo.base.model.SectorInternoBean;

import java.util.List;

public interface SectorInternoServ {

  /**
   * Ingresando como parámetro el id de la repartición devuelve el listado de
   * sectores que le pertenecen
   * 
   * @param idReparticion
   *          Id de la repartición
   * 
   * @return Listado de sectores pertenecientes a la repartición
   */

  public List<SectorInternoBean> buscarSectoresPorReparticion(Long idReparticion);

  /**
   * Devuelve el listado de todos los sectores
   * 
   * @return Listado de todos los sectores
   */

  public List<SectorInternoBean> buscarTodosSectores();

  /**
   * Busca el sector por repartición y se lo ordena por sector mesa S, N, vacio
   * y null para mostrarse en el envío de la solicitud.
   * 
   * @param idReparticion
   *          = id de reparticion
   * @return lista de sectores pertenecientes a la repartición ordenados por
   *         sector_mesa.
   */

  public List<SectorInternoBean> buscarSectoresPorReparticionOrderByMesa(Long idReparticion);

  /**
   * Se ingresa como parametro el nombre de usuario del sector interno buscado.
   * 
   * @param username
   *          usuario del sector interno buscado.
   * 
   * @return Sector interno al cual el usuario pertenece.
   */

  public SectorInternoBean buscarSectorInternoUsername(String username);

  /**
   * Valida que el código del sector ingresado pertenezca a la repartición.
   * 
   * @param value
   *          Codigo del sector interno.
   * 
   * @param idReparticion
   *          Id de la repartición.
   * 
   * @return True si el sector pertenece a la repartición, False en caso
   *         contrario.
   */

  public boolean validarCodigoSector(String value, Long idReparticion);

  /**
   * Valida que el código del sector ingresado pertenezca a la repartición.
   * 
   * @param value
   *          Codigo del sector interno.
   * 
   * @param idReparticion
   *          Id de la repartición.
   * 
   * @return True si el sector pertenece a la repartición, False en caso
   *         contrario.
   */

  public boolean validarCodigoSectorMesa(String value, Long idReparticion);

  public String buscarNombreSectorPorCodigo(String codigoSector, String codigoReparticion);

}
