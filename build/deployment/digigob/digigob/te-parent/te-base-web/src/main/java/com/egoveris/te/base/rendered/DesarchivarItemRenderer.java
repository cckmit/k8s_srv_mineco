package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.TrataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class DesarchivarItemRenderer implements ListitemRenderer {
  @Autowired
  private TrataService trataService;

  public void render(Listitem item, Object data, int arg1) throws Exception {

    ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) data;
    
    // TipoDocumento (EX) Expediente
    String tipoDocumento = expedienteElectronico.getTipoDocumento();
    new Listcell(tipoDocumento).setParent(item);

    // Anio Expediente
    Integer anioSADE = expedienteElectronico.getAnio();
    new Listcell(anioSADE.toString()).setParent(item);

    // Número Expediente
    Integer numeroSADE = expedienteElectronico.getNumero();
    new Listcell(numeroSADE.toString()).setParent(item);

    // Repartición Usuario
    String reparticionUsuario = expedienteElectronico.getCodigoReparticionUsuario();
    new Listcell(reparticionUsuario).setParent(item);

    // Trata
    String Trata = expedienteElectronico.getTrata().getCodigoTrata() + " - " + this.trataService
        .obtenerDescripcionTrataByCodigo(expedienteElectronico.getTrata().getCodigoTrata());
    new Listcell(Trata).setParent(item);

    // Descripción
    String descripcion = expedienteElectronico.getDescripcion();
    new Listcell(descripcion).setParent(item);

    // Accion a realizar
    Listcell currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();
    Image runImage = new Image("/imagenes/play.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
        "onClick=desarchivarWindow$DesarchivarComposer.onDesarchivar");
    Label ejecutar = new Label(Labels.getLabel("ee.desarchivar.expediente.acciondesarchivar"));
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
        "onClick=desarchivarWindow$DesarchivarComposer.onDesarchivar");
    runImage.setParent(hbox);
    ejecutar.setParent(hbox);
    hbox.setParent(currentCell);
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

}
