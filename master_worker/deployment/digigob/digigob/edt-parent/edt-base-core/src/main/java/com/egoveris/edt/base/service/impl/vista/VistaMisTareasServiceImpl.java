package com.egoveris.edt.base.service.impl.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ITareasServiceFactory;
import com.egoveris.edt.base.service.vista.IVistaMisTareasService;
import com.egoveris.edt.base.util.Utilidades;

public class VistaMisTareasServiceImpl implements IVistaMisTareasService {

  private static Logger logger = LoggerFactory.getLogger(VistaMisTareasServiceImpl.class);

  private IAplicacionService aplicacionService;
  private ITareasServiceFactory tareaServiceFactory;

  @Override
  public List<TareasPorSistemaBean> obtenerVistaMisTareas(List<Integer> listaIdAplicaciones,
      String userName, UsuarioFrecuenciaDTO usuarioFrecuencia) {
    List<TareasPorSistemaBean> listaTareasPorSistema = new ArrayList<TareasPorSistemaBean>();

    logger.debug("{} - Iniciando - listaIdAplicaciones: {}", userName, listaIdAplicaciones);

    // Primero: con la lista de id's de las aplicaciones configuradas para
    // el
    // usuario para la vista de mis tareas busco la aplicaciones
    if (listaIdAplicaciones != null && !listaIdAplicaciones.isEmpty()) {
      List<AplicacionDTO> listaAplicacionesMisTareas = this.aplicacionService
          .buscarAplicaciones(listaIdAplicaciones);

      // Segundo: busco las tareas pendientes para las aplicaciones del
      // usuario
      if (listaAplicacionesMisTareas != null && !listaAplicacionesMisTareas.isEmpty()) {
        logger.debug("{} - Cantidad aplicaciones obtenidas en memoria: {}", userName,
            listaAplicacionesMisTareas.size());

        List<Date> listaTareasPendientes = null;
        TareasPorSistemaBean tareaPorSistema = null;
        for (AplicacionDTO aplicacion : listaAplicacionesMisTareas) {
          tareaPorSistema = new TareasPorSistemaBean();
          try {
            logger.debug("{} - Invocando tareaService.buscarTareasPorUsuario para aplicacion '{}'",
                userName, aplicacion.getNombreAplicacion());
            listaTareasPendientes = this.tareaServiceFactory.get(aplicacion.getNombreAplicacion())
                .buscarTareasPorUsuario(userName);
            // Con las lista de fechas de las tareas pendientes del
            // usuario las proceso para mostrarlas en el frontend

            logger.debug("{} - Cantidad tareas obtenidas para aplicacion '{}': {}", new Object[] {
                userName, aplicacion.getNombreAplicacion(), listaTareasPendientes.size() });
            tareaPorSistema = Utilidades.procesarTareasPendientes(listaTareasPendientes,
                usuarioFrecuencia);

          } catch (Exception ex) {
            logger
                .error("Error al obtener tareas pendientes de " + userName + " para la aplicación "
                    + aplicacion.getNombreAplicacion() + " - Error: " + ex.getMessage(), ex);
          } finally {
            tareaPorSistema.setAplicacion(aplicacion);
            listaTareasPorSistema.add(tareaPorSistema);
            logger.debug("{} - tareaPorSistema obtenida: {}", userName,
                tareaPorSistema.toString());
          }
          logger.debug("{} - Fin", userName);
        }
      }
    }
    return listaTareasPorSistema;
  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }

  public void setTareaServiceFactory(ITareasServiceFactory tareaServiceFactory) {
    this.tareaServiceFactory = tareaServiceFactory;
  }

}
