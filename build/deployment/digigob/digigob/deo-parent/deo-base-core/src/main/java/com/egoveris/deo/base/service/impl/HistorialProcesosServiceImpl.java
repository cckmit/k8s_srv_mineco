package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Historial;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.IHistorialProcesosService;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistorialProcesosServiceImpl implements IHistorialProcesosService {

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private HistorialService historialService;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(HistorialProcesosServiceImpl.class);

  /**
   * Workaround ya que el motor de historial de JBPM 4 no trae el nombre de la
   * actividad relacionada.
   * 
   * @param extendedTask
   *          Tarea a la cual se le rellenará el nombre de la actividad.
   * @return La tarea con el nombre rellenado con el valor obtenido o con null
   *         si no fue posible recuperarlo.
   */

  @Override
  public List<HistorialDTO> getHistorial(String workflowOrigen) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getHistorial(String) - start"); //$NON-NLS-1$
    }

    List<HistorialDTO> hist = this.historialService.buscarTodosHistoriales(workflowOrigen);
    Iterator<HistorialDTO> it = hist.iterator();
    HistorialDTO historial;
    while (it.hasNext()) {
      historial = this.mapper.map(it.next(), HistorialDTO.class);
      Usuario usuario = null;
      try {
        usuario = usuarioService.obtenerUsuario(historial.getUsuario());
      } catch (SecurityNegocioException e) {
        LOGGER.error("Mensaje de error", e);
      }
      if (usuario == null) {
        historial.setUsuario(Constantes.USUARIO_DADO_DE_BAJA_EN_TRACK_LDAP_O_CCOO + "("
            + historial.getUsuario() + ")");
      } else {
        historial.setUsuario(usuario.getNombreApellido());
      }
    }
    
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getHistorial(String) - end"); //$NON-NLS-1$
    }
    return hist;
  }
}