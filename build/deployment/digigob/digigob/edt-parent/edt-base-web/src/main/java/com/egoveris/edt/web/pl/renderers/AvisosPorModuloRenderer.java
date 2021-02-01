package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.TipoEstadoAlertaAviso;
import com.egoveris.edt.base.model.eu.AlertaAvisoDTO;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AvisosPorModuloRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    AlertaAvisoDTO aviso = (AlertaAvisoDTO) data;

    Listcell seleccionado = new Listcell();

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    Listcell nombre = new Listcell(aviso.getReferencia());
    Listcell fecha = new Listcell(formatoFecha.format(aviso.getFechaCreacion()));
    Listcell accion = new Listcell("");

    if (aviso.getEstado().equals(TipoEstadoAlertaAviso.NO_LEIDO.getEstado())) {

      nombre.setStyle("font-weight:bold");
      fecha.setStyle("font-weight:bold");
      accion.setStyle("font-weight:bold");
    }

    seleccionado.setParent(item);

    nombre.setParent(item);
    fecha.setParent(item);
    accion.setParent(item);

    Hbox hbox = new Hbox();

    // VER DETALLE
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVerDetalle");
    visualizarImage.setTooltiptext(Labels.getLabel("eu.escritorioUnico.helpDetalle"));
    visualizarImage.setParent(hbox);

    // Redireccionar y Descargar

    if (aviso.getRedirigible() != null) {
      if (aviso.getRedirigible()) {
        Image imagenRedireccion = new Image("/imagenes/redirect.png");
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imagenRedireccion,
            "onClick=composer.onRedireccionarNotifiacion");
        imagenRedireccion
            .setTooltiptext(Labels.getLabel("eu.avisosModuloRender.tooltiptext.alertaAviso"));
        imagenRedireccion.setParent(hbox);
      }
    }

    // A revisar funcionalidad de Avisos
    // if
    // (AplicacionEnum.GEDO.getId().equals(aviso.getAplicacion().getNombreAplicacion())
    // ||
    // AplicacionEnum.CCOO.getId().equals(aviso.getAplicacion().getNombreAplicacion()))
    // {
    // Image imagenDescargar = new Image("/imagenes/document-save-as.png");
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imagenDescargar,
    // "onClick=composer.onDescargarDocumento");
    // imagenDescargar
    // .setTooltiptext(Labels.getLabel("eu.avisosModuloRender.tooltiptext.descDocFinal"));
    // imagenDescargar.setParent(hbox);
    //
    // }

    // Eliminar
    Image eliminarImage = new Image("/imagenes/delete_24px.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,
        "onClick=composer.onEliminarAviso");
    eliminarImage.setTooltiptext(Labels.getLabel("eu.avisosModuloRender.tooltiptex.eliminar"));
    eliminarImage.setParent(hbox);
    hbox.setParent(accion);

    item.setValue(aviso);

  }

}
