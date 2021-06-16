package com.egoveris.te.base.util;

import com.egoveris.te.base.composer.GenericActividadComposer;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.util.ConstantesWeb.VISTA;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class VisualizacionActividadPopup {

  

  public static void show(Component comp, ActividadInbox actividad,
      Map<String, Boolean> parametrosVisualizacion, boolean permisoModificarAct) {

    Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.ACTIVIDAD, actividad);
    Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD,
        determinarVista(actividad, permisoModificarAct));

    if (parametrosVisualizacion != null) {
      Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.PARAMETROS_VISUALIZACION,
          parametrosVisualizacion);
    }

    if (actividad.getForm() != null) {
      actividad.setForm(actividad.getForm().replaceFirst("./", "/"));
    }

    if ((actividad.getUsuarioActual().equals(actividad.getMailCreador()))
        && !"CERRADO".equalsIgnoreCase(actividad.getEstado())) {
     final Component window = findInParentsById(comp, "tramitacionWindow");
      Map<String, Object> mapVars = new HashMap<>();
      mapVars.put("numeroSade", actividad.getNroPedidoGEDO());
      mapVars.put("tramitacionWindow", comp);

      Window producirDocumentoIfr = (Window) Executions.createComponents(
          "/expediente/producirDocumento/producirDocumentoIfr.zul", null, mapVars);
      producirDocumentoIfr.doModal();
    } else {
      Component win = Executions.createComponents(actividad.getForm(), comp, null);
      forzarExtendsGenericActividadComposer(win);
    }
  }

  public static Component findInParentsById(Component component, String searchId) {
	  Component actualComponent = component;
	  Component parentComponent = null;
	  
	  boolean found = false;
	  
	  while (actualComponent != null && !found) {
		  if (actualComponent.getId().equals(searchId)) {
			  parentComponent = actualComponent;
			  found = true;
		  }
		  
		  actualComponent = actualComponent.getParent();
	  }
	  
	  return parentComponent;
  }
  private static void forzarExtendsGenericActividadComposer(Component win) {
    if (!(win.getAttribute(win.getId() + "$composer") instanceof GenericActividadComposer))
      throw new TeRuntimeException(
          "El zul debe usar un composer que extienda de GenericActividadComposer", null);
  }

  private static VISTA determinarVista(ActividadInbox act, boolean permiso) {
    if (act.isModificable() && permiso) {
      return VISTA.EDICION;
    } else {
      return VISTA.LECTURA;
    }
  }
}
