package com.egoveris.sharedorganismo.base.service;

import com.egoveris.sharedorganismo.base.exception.ReparticionGenericDAOException;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;

import java.util.List;

/**
 * 
 * @author agambina
 * 
 */

public interface ReparticionServ {

  /**
   * Este método se inicia cuando se inicia cuando se inicia el Bean. Inicializa
   * el thread de carga de reparticiones.
   * 
   * @return listaReparticiones cargadas. Thread inicializado.
   * 
   */
  public void loadReparticiones();

  /**
   * Devuelve el listado de reparticiones.
   * 
   * @return Listado de reparticiones.
   */

  public List<ReparticionBean> buscarTodasLasReparticiones();

  /**
   * Devuelve el listado de reparticiones que contienen la subcadena ingresada
   * como parámetro dentro del código de repartición o del nombre de la
   * repartición.
   * 
   * @param textoBusqueda
   *          Subcadena que contiene el código de repartición o el nombre de la
   *          repartición.
   * @return Listado de reparticiones existentes que contienen como subcadena el
   *         parametro ingresado dentro del código de repartición o del nombre
   *         de la repartición.
   */

  public List<ReparticionBean> buscarTodasLasReparticiones(String textoBusqueda);

  /**
   * Devuelve el listado de reparticiones que contienen la subcadena ingresada
   * como parámetro dentro del nombre de la repartición.
   * 
   * @param textoBusqueda
   *          Subcadena que contiene el nombre de la repartición.
   * @return Listado de reparticiones existentes que contienen como subcadena el
   *         parametro ingresado dentro del nombre de la repartición.
   */

  public List<ReparticionBean> buscarTodasLasReparticionesNombre(String textoBusqueda);

  /**
   * Devuelve el listado de reparticiones que contienen la subcadena ingresada
   * como parámetro dentro del código de repartición.
   * 
   * @param textoBusqueda
   *          Subcadena que contiene el código de repartición.
   * @return Listado de reparticiones existentes que contienen como subcadena el
   *         parametro ingresado dentro del código de repartición.
   */

  public List<ReparticionBean> buscarTodasLasReparticionesCodigo(String textoBusqueda);

  /**
   * Se ingresa como parametro el código de la repartición buscada y devuelve la
   * repartición buscada.
   * 
   * @param codigoReparticion
   *          Código de repartición buscada.
   * 
   * @return Repartición que tiene como código el parámetro ingresado.
   */

  public ReparticionBean buscarReparticionPorCodigo(String codigoReparticion);

  
  public String buscarNombreReparticionPorCodigo(String codigoReparticion);
  
  /**
   * Se ingresa como parametro el id de la repartición buscada y devuelve la
   * repartición buscada.
   * 
   * @param idReparticion
   *          id de la repartición buscada.
   * 
   * @return Repartición que tiene como id el parámetro ingresado.
   */

  public ReparticionBean buscarReparticionPorId(Long idReparticion);

  /**
   * Se ingresa como parametro el nombre de usuario de la repartición buscada.
   * En caso de no existir se retorna null por compatibilidad con el código
   * existente. Todas las otras excepciones se propagan como
   * ReparticionGenericDAOException
   * 
   * @param username
   *          usuario de la repartición buscada o nulo si no se encuentra
   * 
   * @return Repartición a la cual el usuario pertenece.
   */

  public ReparticionBean buscarReparticionPorUsuario(String username)
      throws ReparticionGenericDAOException;

  /**
   * Valida que el código de repartición exista.
   * 
   * @param codigo
   *          es el código de repartición
   * @return True en el caso que el código de repartición exista, False en caso
   *         contrario.
   */

  public boolean validarCodigoReparticion(String codigoReparticion);

  /**
   * Devuelve el listado de reparticiones vigentes y activas.
   * 
   * @return Listado de reparticiones vigentes y activas.
   */

  public List<ReparticionBean> buscarReparticionesVigentesActivas();

  /**
   * Devuelve el listado de las reparticiones que esten vigentes, activas y
   * esDgtal = 1.
   * 
   * @return Listado de las reparticiones que esten vigentes, activas y esDgtal
   *         = 1.
   */

  public List<ReparticionBean> buscarTodasLasReparticionesDGTAL();

  /**
   * 
   * @param codReparticion
   * @return
   */
  public ReparticionBean buscarDGTALByReparticion(String codReparticion);

}
