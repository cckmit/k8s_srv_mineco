package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.TiposDocumentosComposer;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.util.ConstantesWeb;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TipoDocsGedoItemRenderer implements ListitemRenderer {

  private final static String ESTILO_TODAS = "font-weight: bold; color: black";
  private List<TrataTipoDocumentoDTO> tiposDocumentoEEList;
  private TiposDocumentosComposer composer;
  private boolean todos;

  public TipoDocsGedoItemRenderer(TiposDocumentosComposer tiposDocumentosComposer) {
    super();
    this.composer = tiposDocumentosComposer;
    this.todos = false;
  }

  public TipoDocsGedoItemRenderer() {

  }

  @Override
  public void render(Listitem item, Object data, int arg1) throws Exception {

    TrataTipoDocumentoDTO tipoDocumento = (TrataTipoDocumentoDTO) data;

    if (tipoDocumento.getAcronimoGEDO().compareTo(ConstantesWeb.SELECCIONAR_TODOS) == 0) {
      Listcell tipoDocumentoGEDONombre = new Listcell("TODOS");
      tipoDocumentoGEDONombre.setParent(item);

      Listcell tipoDocumentoGEDOAcro = new Listcell(tipoDocumento.getNombre());
      tipoDocumentoGEDOAcro.setParent(item);

      Listcell ActuacionSADE = new Listcell(tipoDocumento.getActuacion());
      ActuacionSADE.setParent(item);

      Checkbox habilitacion = new Checkbox();
      habilitacion.setChecked(tipoDocumento.getEstaHabilitado());
      Listcell habilitadocell = new Listcell();
      habilitacion.setParent(habilitadocell);
      habilitadocell.setParent(item);
      habilitacion.addForward("onCheck", "this.self", "onSelectTTD", tipoDocumento);
      if (tipoDocumento.getAcronimoGEDO().compareTo(ConstantesWeb.SELECCIONAR_TODOS) == 0) {
        tipoDocumentoGEDONombre.setStyle(ESTILO_TODAS);

      }
      if (this.tiposDocumentoEEList != null && this.tiposDocumentoEEList.size() > 0
          && this.tiposDocumentoEEList.size() < 2 && this.tiposDocumentoEEList.get(0)
              .getAcronimoGEDO().compareTo(ConstantesWeb.SELECCIONAR_TODOS) == 0) {
        todos = true;
      }
    } else {
      // sacando el if y dejando solo esta parte funciona mas que bien .. solo
      // que muestra el todos en la columna del medio y no en la primera,
      // por lo que se agrego este if ya que modificar la columna significaria
      // cambiar todo el codigo y sus validaciones.(Costo , veneficio)
      Listcell tipoDocumentoGEDONombre = new Listcell(tipoDocumento.getNombre());
      tipoDocumentoGEDONombre.setParent(item);

      Listcell tipoDocumentoGEDOAcro = new Listcell(tipoDocumento.getAcronimoGEDO());
      tipoDocumentoGEDOAcro.setParent(item);

      Listcell ActuacionSADE = new Listcell(tipoDocumento.getActuacion());
      ActuacionSADE.setParent(item);

      Checkbox habilitacion = new Checkbox();
      habilitacion.setChecked(tipoDocumento.getEstaHabilitado());
      Listcell habilitadocell = new Listcell();
      habilitacion.setParent(habilitadocell);
      habilitadocell.setParent(item);
      habilitacion.addForward("onCheck", "this.self", "onSelectTTD", tipoDocumento);
      if (tipoDocumento.getAcronimoGEDO().compareTo(ConstantesWeb.SELECCIONAR_TODOS) == 0) {
        tipoDocumentoGEDOAcro.setStyle(ESTILO_TODAS);

      }
      if (this.tiposDocumentoEEList != null && this.tiposDocumentoEEList.size() > 0
          && this.tiposDocumentoEEList.size() < 2 && this.tiposDocumentoEEList.get(0)
              .getAcronimoGEDO().compareTo(ConstantesWeb.SELECCIONAR_TODOS) == 0) {
        todos = true;
      }
    }
  }

  public void onSelectTTD(Event event) throws InterruptedException {
    TrataTipoDocumentoDTO ttd = (TrataTipoDocumentoDTO) event.getData();
    ttd.setEstaHabilitado(!ttd.getEstaHabilitado());
  }

  private boolean habilitado(TipoDocumentoDTO tipoDocumento) {
    boolean esta = todos;
    if (this.tiposDocumentoEEList != null) {
      for (TrataTipoDocumentoDTO documento : this.tiposDocumentoEEList) {
        if (documento.getAcronimoGEDO().trim().equals(tipoDocumento.getAcronimo().trim())) {
          esta = true;
        }
      }
    }
    return esta;
  }

  public TrataTipoDocumentoDTO busqueda(TrataTipoDocumentoDTO tipoDocumentoGEDO) {
    for (TrataTipoDocumentoDTO documento : this.tiposDocumentoEEList) {
      if (documento.getAcronimoGEDO().trim().equals(tipoDocumentoGEDO.getAcronimoGEDO().trim())) {
        return documento;
      }
    }
    return tipoDocumentoGEDO;
  }

  public List<TrataTipoDocumentoDTO> getTiposDocumentoEEList() {
    return tiposDocumentoEEList;
  }

  public void setTiposDocumentoEEList(List<TrataTipoDocumentoDTO> tiposDocumentoEEList) {
    this.tiposDocumentoEEList = tiposDocumentoEEList;
    this.todos = false;
  }

  public TiposDocumentosComposer getComposer() {
    return composer;
  }

  public void setComposer(TiposDocumentosComposer composer) {
    this.composer = composer;
  }

}