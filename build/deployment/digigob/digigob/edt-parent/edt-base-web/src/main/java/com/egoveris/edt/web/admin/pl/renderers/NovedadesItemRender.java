package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class NovedadesItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    NovedadDTO novedad = (NovedadDTO) data;

    item.setValue(novedad);
    if(novedad != null){
        String textoNovedad;
        if (novedad.getNovedad().trim().length() <= 50) {
          textoNovedad = novedad.getNovedad();
        } else {
          textoNovedad = novedad.getNovedad().trim().substring(0, 50);
        }
        Listcell lblNovedad = new Listcell(textoNovedad);
        lblNovedad.setTooltiptext(novedad.getNovedad());
        lblNovedad.setParent(item);
        String cadena = "";
        
          for (AplicacionDTO app : novedad.getAplicaciones()) {
            cadena += app.getNombreAplicacion() + " ";
          }
        
    
        Listcell lblModulo = new Listcell(cadena);
        lblModulo.setParent(item);
    
        Listcell lblCategoria = new Listcell(novedad.getCategoria().getCategoriaNombre());
        lblCategoria.setParent(item);
    
        Listcell lblFechaInicio = new Listcell(sdf.format(novedad.getFechaInicio()));
        lblFechaInicio.setParent(item);
    
        Listcell lblFechaFin = new Listcell(sdf.format(novedad.getFechaFin()));
        lblFechaFin.setParent(item);
    }
    // validacion para la visibilidad de los botones de acciones de la
    // grilla de novedades
    String estado = String.valueOf(item.getParent().getAttribute("visibilidad"));
    if ("activa".equals(estado)) {
      if(novedad != null){
        Listcell lblEstadoActual = new Listcell(novedad.getEstado());
        lblEstadoActual.setParent(item);
      }
      Listcell lcAccion = new Listcell();
      lcAccion.setParent(item);
      Hbox hb = new Hbox();
      hb.setParent(lcAccion);

      // modificar
      Image modificar = new Image("/imagenes/pencil.png");
      modificar.setParent(hb);
      ComponentsCtrl.applyForward(modificar, "onClick=onModificarNovedad()");
      Label modificarLabel = new Label(Labels.getLabel("eu.abmNovedades.acciones.modificar"));
      ComponentsCtrl.applyForward(modificarLabel, "onClick=onModificarNovedad()");
      modificarLabel.setParent(hb);

      // eliminar
      Image eliminar = new Image("/imagenes/Eliminar.png");
      eliminar.setParent(hb);
      ComponentsCtrl.applyForward(eliminar, "onClick=onEliminarNovedad()");
      Label eliminarLabel = new Label(Labels.getLabel("eu.abmNovedades.acciones.eliminar"));
      ComponentsCtrl.applyForward(eliminarLabel, "onClick=onEliminarNovedad()");
      eliminarLabel.setParent(hb);
    } else if ("historial".equals(estado)) {
      Listcell lcAccion = new Listcell();
      lcAccion.setParent(item);
      Hbox hb = new Hbox();
      hb.setParent(lcAccion);

      // historial
      Image modificar = new Image("/imagenes/preview.png");
      modificar.setParent(hb);
      ComponentsCtrl.applyForward(modificar, "onClick=onVerHistorialNovedad()");
      Label modificarLabel = new Label(Labels.getLabel("eu.abmNovedades.acciones.historial"));
      ComponentsCtrl.applyForward(modificarLabel, "onClick=onVerHistorialNovedad()");
      modificarLabel.setParent(hb);
    } else if (("detalleNovedad".equals(estado)) && novedad != null) {
      // detalle de una novedad
         Listcell lblEstado = new Listcell(sdf.format(novedad.getEstado()));
         lblEstado.setParent(item);
      }
  }
}
