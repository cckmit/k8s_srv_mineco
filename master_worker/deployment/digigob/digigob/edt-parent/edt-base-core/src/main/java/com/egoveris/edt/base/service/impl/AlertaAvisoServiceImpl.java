package com.egoveris.edt.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.TipoEstadoAlertaAviso;
import com.egoveris.edt.base.model.eu.AlertaAviso;
import com.egoveris.edt.base.model.eu.AlertaAvisoDTO;
import com.egoveris.edt.base.model.eu.Aplicacion;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacion;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacionDTO;
import com.egoveris.edt.base.repository.eu.AlertaAvisoRepository;
import com.egoveris.edt.base.repository.eu.AplicacionRepository;
import com.egoveris.edt.base.service.IAlertaAvisoService;
import com.egoveris.shared.map.ListMapper;

@Service("alertaAvisoService")
@Transactional
public class AlertaAvisoServiceImpl implements IAlertaAvisoService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private AlertaAvisoRepository alertaAvisoRepository;
  @Autowired
  private AplicacionRepository aplicacionRepository;

  @Override
  public List<NotificacionesPorAplicacionDTO> obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(
      String userName) {

    List<Aplicacion> apps = aplicacionRepository.findAllByOrderByNombreAplicacionAsc();
    List<NotificacionesPorAplicacion> resultByUser = alertaAvisoRepository
        .obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(userName,
            TipoEstadoAlertaAviso.NO_LEIDO.getEstado());

    /*
     * Si la aplicación no se encuentra en resultByUser, entonces la aplicación
     * tiene 0 notificaciones. Se agrega a la lista de retorno, ya que la query
     * no enuentra las aplicaciones sin notificaciones.
     * 
     */
    List<NotificacionesPorAplicacion> sinNotif = new ArrayList<>();
    for (Aplicacion app : apps) {
      boolean existe = false;
      for (NotificacionesPorAplicacion notif : resultByUser) {
        if (app.equals(notif.getAplicacion())) {
          existe = true;
          break;
        }
      }
      if (!existe) {
        NotificacionesPorAplicacion not = new NotificacionesPorAplicacion(app, Long.valueOf(0));
        sinNotif.add(not);
      }
    }

    if (resultByUser.isEmpty()) {
      resultByUser = new ArrayList<>();
    }
    resultByUser.addAll(sinNotif);

    @SuppressWarnings("unchecked")
    List<NotificacionesPorAplicacionDTO> retorno = ListMapper.mapList(resultByUser, mapper,
        NotificacionesPorAplicacionDTO.class);
    Collections.sort(retorno);

    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AlertaAvisoDTO> obtenerAlertasAvisosPorModuloYUserName(AplicacionDTO aplicacion,
      String userName) {
    return ListMapper.mapList(alertaAvisoRepository.findByAplicacionAndUserName(
        mapper.map(aplicacion, Aplicacion.class), userName), mapper, AlertaAvisoDTO.class);
  }

  @Override
  public void eliminarAlerta(AlertaAvisoDTO aviso) {
    alertaAvisoRepository.delete(mapper.map(aviso, AlertaAviso.class));
  }

  @Override
  public void guardarAviso(AlertaAvisoDTO aviso) {
    alertaAvisoRepository.save(mapper.map(aviso, AlertaAviso.class));
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AlertaAvisoDTO> filtrarAlertasAvisosPorMotivoYReferenciaYFecha(String filtro,
      AplicacionDTO aplicacion, String userName, Date fechaDesde, Date fechaHasta) {

    List<AlertaAviso> resultado = null;

    if (fechaDesde == null && fechaHasta == null) {
      resultado = alertaAvisoRepository.filtrarAlertasAvisosPorMotivoYReferencia(userName,
          aplicacion.getIdAplicacion(), filtro);
    } else if (fechaDesde != null && fechaHasta == null) {
      resultado = alertaAvisoRepository.filtrarAlertasAvisosPorMotivoYReferenciaYFechaDesde(
          userName, aplicacion.getIdAplicacion(), filtro, fechaDesde);
    } else if (fechaDesde == null) {
      resultado = alertaAvisoRepository.filtrarAlertasAvisosPorMotivoYReferenciaYFechaHasta(
         userName, mapper.map(aplicacion, Aplicacion.class), filtro, fechaHasta);

    }
    return ListMapper.mapList(resultado, mapper, AlertaAvisoDTO.class);
  }

}