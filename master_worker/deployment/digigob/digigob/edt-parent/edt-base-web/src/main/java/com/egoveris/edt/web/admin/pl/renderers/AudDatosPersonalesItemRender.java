package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AudDatosPersonalesItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    DatosUsuarioDTO dato = (DatosUsuarioDTO) data;

    // dato
    item.setValue(dato);

    // mail
    Listcell lblMail = new Listcell(dato.getMail());
    lblMail.setTooltiptext(dato.getMail());
    lblMail.setParent(item);

    // cuit/cuil
    Listcell lblCuit = new Listcell(dato.getNumeroCuit());
    lblCuit.setParent(item);

    // jerarquia superior
    Listcell lblUsuariosuperior = new Listcell(dato.getUserSuperior());
    lblUsuariosuperior.setParent(item);

    // usuario asesor
    Listcell lblUsuarioAsesor = new Listcell(dato.getUsuarioAsesor());
    lblUsuarioAsesor.setParent(item);

    // usuario secretaria
    Listcell lblUsuarioSecretaria = new Listcell(dato.getSecretario());
    lblUsuarioSecretaria.setParent(item);

    // sector
    Listcell lblSectorMesa = new Listcell("");
    if (dato.getSector() != null) {
      lblSectorMesa = new Listcell(dato.getSector().getCodigo());
    }
    lblSectorMesa.setParent(item);

    // cargo
    Listcell lblCargo = new Listcell("");
    if (dato.getCargoAsignado() != null) {
      lblCargo = new Listcell(dato.getCargoAsignado().getCargoNombre());
    }
    lblCargo.setParent(item);

    // fecha revision
    Listcell lblFechaRevision = new Listcell(sdf.format(dato.getFechaRevision()));
    lblFechaRevision.setParent(item);

    // tipo revision
    Listcell lblOperacion = new Listcell(dato.getTipoRevision());
    lblOperacion.setParent(item);

  }
}
