package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.novedad.NovedadHistDTO;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class NovedadesHistItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    NovedadHistDTO novedad = (NovedadHistDTO) data;

    item.setValue(novedad);

    String textoNovedad;
    if (novedad.getNovedad().trim().length() <= 50) {
      textoNovedad = novedad.getNovedad();
    } else {
      textoNovedad = novedad.getNovedad().trim().substring(0, 50);
    }
    Listcell lblNovedad = new Listcell(textoNovedad);
    lblNovedad.setTooltiptext(novedad.getNovedad());
    lblNovedad.setParent(item);

    Listcell lblModulo = new Listcell(novedad.getAplicaciones());
    lblModulo.setParent(item);

    Listcell lblCategoria = new Listcell(novedad.getCategoria().getCategoriaNombre());
    lblCategoria.setParent(item);

    Listcell lblFechaInicio = new Listcell(sdf.format(novedad.getFechaInicio()));
    lblFechaInicio.setParent(item);

    Listcell lblFechaFin = new Listcell(sdf.format(novedad.getFechaFin()));
    lblFechaFin.setParent(item);

    Listcell lblFechaModificacion = new Listcell(sdf.format(novedad.getFechaModificacion()));
    lblFechaModificacion.setParent(item);

    Listcell lblUsuario = new Listcell(novedad.getUsuario());
    lblUsuario.setParent(item);

    // detalle de una novedad
    Listcell lblEstado = new Listcell(novedad.getEstado());
    lblEstado.setParent(item);
  }
}
