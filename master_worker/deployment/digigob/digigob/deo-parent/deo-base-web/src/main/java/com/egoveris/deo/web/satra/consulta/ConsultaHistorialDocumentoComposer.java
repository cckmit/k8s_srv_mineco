package com.egoveris.deo.web.satra.consulta;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.IHistorialProcesosService;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaHistorialDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -4500798310900948116L;

  private AnnotateDataBinder binder;
  private Window historialDelDocumentoWindow;

  private Listbox actividadesDocumento;

  private List<HistorialDTO> historial;

  private DocumentoDTO selectedDocumento;

  @WireVariable("historialProcesosServiceImpl")
  private IHistorialProcesosService historialProcesosService;
  private String executionId = null;

  private String id = null;
  private Label textDoc;

  public Label getTextDoc() {
    return textDoc;
  }

  public void setTextDoc(Label textDoc) {
    this.textDoc = textDoc;
  }

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
    selectedDocumento = (DocumentoDTO) Executions.getCurrent().getArg().get("selectedDocumento");
    
    executionId = (String) Executions.getCurrent().getArg().get("executionId");
    if (this.executionId != null) {
      id = this.executionId;
      this.textDoc.setVisible(false);
    } else {
      id = this.getSelectedDocumento().getWorkflowOrigen();
      this.textDoc.setVisible(true);
    }
    this.historial = historialProcesosService.getHistorial(this.id);
    

    
  }

  public void onClick$cerrar() {

    this.historialDelDocumentoWindow.detach();
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Listbox getActividadesDocumento() {
    return actividadesDocumento;
  }

  public void setActividadesDocumento(Listbox actividadesDocumento) {
    this.actividadesDocumento = actividadesDocumento;
  }

  public DocumentoDTO getSelectedDocumento() {
    return selectedDocumento;
  }

  public void setSelectedDocumento(DocumentoDTO selectedDocumento) {
    this.selectedDocumento = selectedDocumento;
  }

  public List<HistorialDTO> getHistorial() {
    return historial;
  }

  public void setHistorial(List<HistorialDTO> historial) {
    this.historial = historial;
  }

}
