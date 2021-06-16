package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ComunicacionDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ComunicacionService {

  /**
   * Inserta uno comunicacion en la bd.
   * 
   * @param comunicacion
   */

  public void guardarComunicacion(ComunicacionDTO comunicacion);

  /**
   * Busca las comunicaciones enviadas por el usuario dado
   * 
   * @param usuario:
   *          Usuario creador
   * @return lista de comunicaciones
   */
  public List<ComunicacionDTO> buscarComunicacionesEnviadasPorUsuario(
      Map<String, Object> parametrosConsulta);

  /**
   * Retorna verdadero o falso si la comunicacion fue continuada por el usuario
   * receptor
   * 
   * @param id
   *          : identificador de la comunicacion
   * @param usuario
   *          : usuario receptor de la comunicacion
   * @return boolean
   */
  public Boolean validarComunicacionContinuada(Integer id, String usuario);

  /**
   * Consulta el número de comunicaciones registradas para este usuario como
   * creador.
   * 
   * @param usuario:
   *          Usuario creador de comunicaciones.
   * @return Número de comunicaciones
   */
  public Integer numeroComunicacionesEnviadasPorUsuario(String usuario);

  public Integer numeroComunicacionesEnviadasPorFecha(Date fechaDesde, Date fechaHasta,
      String usuarioActual);

  public Integer numeroComunicacionesEnviadasPorReferencia(String referencia,
      String usuarioActual);

  /**
   * Busca las comunicaciones por el id dado
   * 
   * @param id
   *          : identificador de una comunicacion
   * @return Comunicacion
   */
  public ComunicacionDTO buscarComunicacionPorId(Integer id);

  /**
   * Borra de la base de datos las comunicaciones enviadas por el usuario dado.
   * 
   * @param usuario:
   *          Usuario creador.
   */

  public void eliminarComunicacionesEnviadas(String usuario);

  /**
   * Actualiza datos de una comunicacion
   * 
   * @param comunicacion
   *          : comunicacion
   */
  public void actualizarComunicacion(ComunicacionDTO comunicacion);

  public List<ComunicacionDTO> buscarComunicacionPorFechaEnviados(Date fechaDesde, Date fechaHasta,
      String usuarioActual, Map<String, Object> parametrosConsulta);

  public ComunicacionDTO buscarComunicacionPorCaratula(String numeroDocumento);

  public List<ComunicacionDTO> buscarComunicacionPorReferenciaEnviados(String referencia,
      String usuario, Map<String, Object> parametrosConsulta);

}