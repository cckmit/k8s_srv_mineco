package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

public class ConsultaDatosPropiosDocumentoComposer extends GEDOGenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = 137471927248462093L;
  /**
  * 
  */
  private AnnotateDataBinder binder;
  private Window datosPropiosDelDocumentoWindow;
  private List<DocumentoMetadataDTO> datos;
  public static final String METADATA = "metadata";
  private Label idSinMetadatos;
  private Listbox consultaDocumentosList;
  private DocumentoMetadataDTO selectedDocumento;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    datos = (List<DocumentoMetadataDTO>) Executions.getCurrent().getArg().get(METADATA);
    if (datos == null || datos.isEmpty()) {
      this.consultaDocumentosList.setVisible(false);
      this.idSinMetadatos.setVisible(true);
    }
  }

  public void onClick$cerrar() {

    this.datosPropiosDelDocumentoWindow.detach();
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Window getDatosPropiosDelDocumentoWindow() {
    return datosPropiosDelDocumentoWindow;
  }

  public void setDatosPropiosDelDocumentoWindow(Window datosPropiosDelDocumentoWindow) {
    this.datosPropiosDelDocumentoWindow = datosPropiosDelDocumentoWindow;
  }

  public List<DocumentoMetadataDTO> getDatos() {
    return datos;
  }

  public void setDatos(List<DocumentoMetadataDTO> datos) {
    this.datos = datos;
  }

  public Label getIdSinMetadatos() {
    return idSinMetadatos;
  }

  public void setIdSinMetadatos(Label idSinMetadatos) {
    this.idSinMetadatos = idSinMetadatos;
  }

  public Listbox getConsultaDocumentosList() {
    return consultaDocumentosList;
  }

  public void setConsultaDocumentosList(Listbox consultaDocumentosList) {
    this.consultaDocumentosList = consultaDocumentosList;
  }

  public DocumentoMetadataDTO getSelectedDocumento() {
    return selectedDocumento;
  }

  public void setSelectedDocumento(DocumentoMetadataDTO selectedDocumento) {
    this.selectedDocumento = selectedDocumento;
  }
}
