package com.egoveris.cdeo.web.visualizarDocumento;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

public class VisualizacionDocumentoPopup {

  public static void show(Component component, String numeroSade,
      Map<String, Boolean> parametrosVisualizacion)
      throws SuspendNotAllowedException, InterruptedException {
    show(component, numeroSade, null, parametrosVisualizacion, null);
  }

  public static void show(Component component, String numeroSade, 
		  Boolean tieneTemplate, Map<String, Boolean> parametrosVisualizacion, String usuario)
      throws SuspendNotAllowedException, InterruptedException {

    /**
     * Estas variables se inicializan en el Desktop de ZK
     * "Executions.getCurrent().getDesktop().setAttribute(Parametro1,
     * Parametro2)" Luego si declaro una variable con el mismo nombre del
     * atributo, la variable declarada apunta al espacio de memoria del Desktop
     * de ZK, o sea tiene el valor previamente seteado.
     * 
     */
    if (usuario == null) {
      usuario = Executions.getCurrent().getSession().getAttribute("userName").toString();
    }
    if(tieneTemplate != null) {
        Executions.getCurrent().getDesktop().setAttribute(Constantes.TIENE_TEMPLATE, tieneTemplate);    	
    }
    
    Executions.getCurrent().getDesktop().setAttribute(Constantes.NUMERO_DOCUMENTO, numeroSade);
    Executions.getCurrent().getDesktop().setAttribute(Constantes.USER, usuario);
    if (parametrosVisualizacion != null) {
      Executions.getCurrent().getDesktop().setAttribute(Constantes.PARAMETROS_VISUALIZACION,
          parametrosVisualizacion);
    } else {
      Executions.getCurrent().getDesktop().setAttribute(Constantes.PARAMETROS_VISUALIZACION,
          new HashMap<String, Boolean>());
    }
    Window popupDocumentoWindow = (Window) Executions.createComponents(Constantes._PARENT, null,
        null);
    popupDocumentoWindow.setTitle("Documento");
    popupDocumentoWindow.setWidth("70%");
    popupDocumentoWindow.doModal();
  }
}