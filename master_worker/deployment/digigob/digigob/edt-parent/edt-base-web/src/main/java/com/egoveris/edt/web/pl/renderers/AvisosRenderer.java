package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacionDTO;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AvisosRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    NotificacionesPorAplicacionDTO notifacacionPorAplicacion = (NotificacionesPorAplicacionDTO) data;
    AplicacionDTO aplicacion = notifacacionPorAplicacion.getAplicacion();
    String nombreAplicacion = aplicacion.getNombreAplicacion();

    Listcell modulo = new Listcell(nombreAplicacion);
    modulo.setParent(item);
    modulo.setTooltiptext(aplicacion.getDescripcionAplicacion());
    ComponentsCtrl.applyForward(modulo, "onClick=onSeleccionarModulo()");

    Listcell avisos = new Listcell(
        notifacacionPorAplicacion.getCantidadDeNotificaciones().toString());
    avisos.setParent(item);
    ComponentsCtrl.applyForward(avisos, "onClick=onSeleccionarModulo()");
  }

}
