package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteMetadataDTO;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

public class ConsultaDatosPropiosExpedienteComposer extends EEGenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = 137471927248462093L;
  /**
  * 
  */
  private AnnotateDataBinder binder;
  private Window datosPropiosDelExpedienteWindow;
  private List<ExpedienteMetadataDTO> datos;
  public static final String METADATA = "metadata";
  private Label idSinMetadatos;
  private Listbox consultaExpedienteList;
  private ExpedienteMetadataDTO selectedExpediente;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    datos = (List<ExpedienteMetadataDTO>) Executions.getCurrent().getArg().get(METADATA);
    if (datos == null || datos.size() == 0) {
      this.consultaExpedienteList.setVisible(false);
      this.idSinMetadatos.setVisible(true);
    }
  }

  public void onClick$cerrar() {

    this.datosPropiosDelExpedienteWindow.detach();
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Window getDatosPropiosDelDocumentoWindow() {
    return datosPropiosDelExpedienteWindow;
  }

  public void setDatosPropiosDelDocumentoWindow(Window datosPropiosDelDocumentoWindow) {
    this.datosPropiosDelExpedienteWindow = datosPropiosDelDocumentoWindow;
  }

  public List<ExpedienteMetadataDTO> getDatos() {
    return datos;
  }

  public void setDatos(List<ExpedienteMetadataDTO> datos) {
    this.datos = datos;
  }

  public Label getIdSinMetadatos() {
    return idSinMetadatos;
  }

  public void setIdSinMetadatos(Label idSinMetadatos) {
    this.idSinMetadatos = idSinMetadatos;
  }

  public Listbox getConsultaExpedienteList() {
    return consultaExpedienteList;
  }

  public void setConsultaExpedienteList(Listbox consultaExpedienteList) {
    this.consultaExpedienteList = consultaExpedienteList;
  }

  public ExpedienteMetadataDTO getSelectedExpediente() {
    return selectedExpediente;
  }

  public void setSelectedExpediente(ExpedienteMetadataDTO selectedExpediente) {
    this.selectedExpediente = selectedExpediente;
  }
}
