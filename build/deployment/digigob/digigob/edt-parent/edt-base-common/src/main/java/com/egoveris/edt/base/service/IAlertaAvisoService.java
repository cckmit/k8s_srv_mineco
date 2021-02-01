package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.eu.AlertaAvisoDTO;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacionDTO;

import java.util.Date;
import java.util.List;

/**
 * The Interface IAlertaAvisoService.
 */
public interface IAlertaAvisoService {

  /**
   * Obtener cantidad de alertas y avisos no leidos por aplicacion.
   *
   * @param userName
   *          the user name
   * @return the list
   */
  public List<NotificacionesPorAplicacionDTO> obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(
      String userName);

  /**
   * Obtener alertas avisos por modulo Y user name.
   *
   * @param aplicacion
   *          the aplicacion
   * @param userName
   *          the user name
   * @return the list
   */
  public List<AlertaAvisoDTO> obtenerAlertasAvisosPorModuloYUserName(AplicacionDTO aplicacion,
      String userName);

  /**
   * Guardar aviso.
   *
   * @param aviso
   *          the aviso
   */
  public void guardarAviso(AlertaAvisoDTO aviso);

  /**
   * Eliminar alerta.
   *
   * @param aviso
   *          the aviso
   */
  public void eliminarAlerta(AlertaAvisoDTO aviso);

  /**
   * Filtrar alertas avisos por motivo, referencia Y fecha.
   *
   * @param filtro
   *          the filtro
   * @param aplicacion
   *          the aplicacion
   * @param userName
   *          the user name
   * @param desde
   *          the desde
   * @param hasta
   *          the hasta
   * @return the list
   */
  public List<AlertaAvisoDTO> filtrarAlertasAvisosPorMotivoYReferenciaYFecha(String filtro,
      AplicacionDTO aplicacion, String userName, Date desde, Date hasta);

}