package com.egoveris.edt.base.service.impl.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.SupervisadosBean;
import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.vista.IVistaMisSupervisadosService;
import com.egoveris.edt.base.util.Utilidades;

@Service("vistaMisSupervisadosService")
public class VistaMisSupervisadosServiceImpl implements IVistaMisSupervisadosService {

  private static Logger logger = LoggerFactory.getLogger(VistaMisSupervisadosServiceImpl.class);

  @Autowired
  private IAplicacionService aplicacionService;

  @Override
  public List<SupervisadosBean> obtenerVistaMisSistemas(List<Integer> listaIdsAplicaciones,
      String userName, UsuarioFrecuenciaDTO usuarioFrecuencia, List<String> usuariosSubordinados) {

    logger.debug("{} - Iniciando", userName);
    List<SupervisadosBean> result = new ArrayList<SupervisadosBean>();
    List<AplicacionDTO> listaAplicacionesMisTareas = null;
    SupervisadosBean supervisado = null;
    List<TareasPorSistemaBean> tareasPorSistSup = null;

    if (usuariosSubordinados != null && !usuariosSubordinados.isEmpty()) {
      // Segundo: con la lista de id's de las aplicaciones configuradas
      // para el usuario para la vista de mis tareas busco la aplicaciones
      if (listaIdsAplicaciones != null && !listaIdsAplicaciones.isEmpty()) {
        logger.debug("{} -  aplicacionService.buscarAplicacionesEnMemoria", userName);
        listaAplicacionesMisTareas = this.aplicacionService
            .buscarAplicaciones(listaIdsAplicaciones);

        logger.debug("{} -  Cantidad aplicaciones encontradas: {}", userName,
            listaAplicacionesMisTareas.size());
      }

      // Tercero; con la lista de subordinados y la lista de aplicaciones
      // para
      // esta vista busco las tareas de cada supervisado en todas las
      // aplicaciones
      // configuradas
      if (listaAplicacionesMisTareas != null && !listaAplicacionesMisTareas.isEmpty()) {
        List<Date> listaTareasPendientes = null;
        TareasPorSistemaBean tareaPorSistema = null;
        for (String superv : usuariosSubordinados) {
          tareasPorSistSup = new ArrayList<TareasPorSistemaBean>();
          supervisado = new SupervisadosBean();
          supervisado.setNombreSupervisado(superv);

          for (AplicacionDTO aplicacion : listaAplicacionesMisTareas) {
            tareaPorSistema = new TareasPorSistemaBean();
            try {
              logger.debug(
                  "{} - Invocando tareaService.buscarTareasPorUsuario para aplicacion '{}'",
                  userName, aplicacion.getNombreAplicacion());
              listaTareasPendientes = new ArrayList<>(); 
              // Con las lista de fechas de las tareas pendientes
              // del
              // usuario las proceso para mostrarlas en el
              // frontend
              logger.debug("{} - Cantidad tareas obtenidas para aplicacion '{}': {}",
                  new Object[] { userName, aplicacion.getNombreAplicacion(),
                      listaTareasPendientes.size() });
              tareaPorSistema = Utilidades.procesarTareasPendientes(listaTareasPendientes,
                  usuarioFrecuencia);
            } catch (Exception ex) {
              logger.error(
                  "Error al obtener tareas pendientes de " + userName + " para la aplicación "
                      + aplicacion.getNombreAplicacion() + " - Error: " + ex.getMessage(),
                  ex);
            } finally {
              tareaPorSistema.setAplicacion(aplicacion);
              tareasPorSistSup.add(tareaPorSistema);

              logger.debug("{} - tareaPorSistema obtenida: {}", userName,
                  tareaPorSistema.toString());
            }
          }
          supervisado.setListaTareasPorSistema(tareasPorSistSup);
          result.add(supervisado);
        }
      }
    }
    logger.debug("{} - Fin", userName);
    return result;
  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }
}