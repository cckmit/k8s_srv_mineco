package com.egoveris.edt.base.service.impl.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ITareasServiceFactory;
import com.egoveris.edt.base.service.vista.IVistaBuzonGrupalService;
import com.egoveris.edt.base.util.Utilidades;

public class VistaBuzonGrupalServiceImpl implements IVistaBuzonGrupalService {

  private IAplicacionService aplicacionService;
  private ITareasServiceFactory tareaServiceFactory;

  private static Logger logger = LoggerFactory.getLogger(VistaBuzonGrupalServiceImpl.class);

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<TareasPorSistemaBean> obtenerVistaTareas(
      Map<Integer, List<String>> listaIdsAplicacionesMisTareas, String userName,
      UsuarioFrecuenciaDTO usuarioFrecuencia) {
    logger.debug("{} - Iniciando - listaIdAplicaciones: {}", userName,
        listaIdsAplicacionesMisTareas.keySet());
    List<TareasPorSistemaBean> listaTareasBuzon = new ArrayList<TareasPorSistemaBean>();
    // Primero: con la lista de id's de las aplicaciones configuradas para el
    // usuario para la vista busco la aplicaciones
    if (listaIdsAplicacionesMisTareas.keySet() != null
        && listaIdsAplicacionesMisTareas.size() > 0) {
      List<AplicacionDTO> listaAplicacionesMisTareas = this.aplicacionService
          .buscarAplicaciones(new ArrayList(listaIdsAplicacionesMisTareas.keySet()));
      // Segundo: busco las tareas pendientes para las aplicaciones del
      // usuario
      if (listaAplicacionesMisTareas != null && !listaAplicacionesMisTareas.isEmpty()) {
        logger.debug("{} - Cantidad aplicaciones obtenidas en memoria: {}", userName,
            listaAplicacionesMisTareas.size());
        List<Date> listaTareasPendientes = new ArrayList<Date>();
        TareasPorSistemaBean tareaPorSistema = null;
        for (AplicacionDTO aplicacion : listaAplicacionesMisTareas) {
          tareaPorSistema = new TareasPorSistemaBean();
          try {
            logger.debug(
                "{} - Invocando tareaService.buscarTareasPorUsuarioSistemas para aplicacion '{}'",
                userName, aplicacion.getNombreAplicacion());

            // Obtenengo la lista de grupos del usuario para la aplicación
            // específica.
            List<String> listaGrupos = listaIdsAplicacionesMisTareas
                .get(aplicacion.getIdAplicacion());
            listaTareasPendientes = this.tareaServiceFactory.get(aplicacion.getNombreAplicacion())
                .buscarTareasBuzonGrupal(userName, listaGrupos);
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
            listaTareasBuzon.add(tareaPorSistema);
            logger.debug("{} - tareaPorSistema obtenida: {}", userName,
                tareaPorSistema.toString());
          }
          logger.debug("{} - Fin", userName);
        }
      }
    }
    return listaTareasBuzon;

  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }

  public void setTareaServiceFactory(ITareasServiceFactory tareaServiceFactory) {
    this.tareaServiceFactory = tareaServiceFactory;
  }

}
