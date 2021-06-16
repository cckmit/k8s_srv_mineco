package com.egoveris.deo.web.satra.firma;

import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class FirmaExternaDocumentoComposer extends FirmaDocumentoGenericComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -2354811430559375085L;

  private static transient Logger logger = LoggerFactory
      .getLogger(FirmaExternaDocumentoComposer.class);
  private Window firmaExternaWindow;
  private Map variables;
  protected AnnotateDataBinder firmaExternaDocumentoComposerBinder;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    this.cargarVariablesInstancia();
    super.solicitudAvisoFirma.setChecked(true);
    super.solicitudAvisoFirma.setDisabled(true);
    super.solicitudEnvioCorreo.setDisabled(true);
    this.firmaExternaDocumentoComposerBinder = new AnnotateDataBinder(component);
    this.firmaExternaDocumentoComposerBinder.loadAll();
  }

  public void onClick$importar() throws InterruptedException {
    if (super.tipoDocumento == null) {
      super.restaurarVentanaFirma();
      Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error.tipoDocumentoYaNoExiste"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
      super.closeAndNotifyAssociatedWindow(null);
    } else {
      try {
        ResponseGenerarDocumento datos = null;
        RequestGenerarDocumento request = super.createAndSetRequest();
        datos = super.generarDocumentoService.cerrarDocumento(request,
            (List<String>) this.variables.get(Constantes.VAR_RECEPTORES_AVISO_FIRMA));
        String numeroEspecial = null;
        if (super.tipoDocumento.getEsEspecial())
          numeroEspecial = datos.getNumeroEspecial().toString();
        this.mostrarMensajeImportar(datos.getNumero(), numeroEspecial);
        super.closeAndNotifyAssociatedWindow(null);
      } catch (Exception e) {
        super.restaurarVentanaFirma();
        logger.error("Error en Importacion", e);
        Messagebox.show(Labels.getLabel("gedo.importacionDocumento.error"),
            Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
        super.closeAndNotifyAssociatedWindow(null);
      }
    }
  }

  private void mostrarMensajeImportar(String numero, String numeracionEspecial)
      throws InterruptedException {
    String mensaje = null;
    try {
      if (super.tipoDocumento.getEsEspecial()) {
        mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGeneradoNumeroEspecial",
            new String[] { numero, numeracionEspecial });
      } else {
        mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGenerado",
            new String[] { numero });
      }
      super.mostrarMensajeDescarga(numero, mensaje, this.firmaExternaWindow);
    } catch (Exception e) {
      logger.error("Error al mostrar el mensaje de firma Simple", e);
      Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error.mostrandoMensajeFirma"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onCreate$firmaExternaWindow(Event event) throws InterruptedException {
    this.cargarVariablesFirma();
    if (super.contenidoTemporal == null || !super.generarPreviewInicial()) {
      super.closeAndNotifyAssociatedWindow(null);
    }
  }

  public AnnotateDataBinder getFirmaExternaDocumentoComposerBinder() {
    return firmaExternaDocumentoComposerBinder;
  }

  public void setFirmaExternaDocumentoComposerBinder(
      AnnotateDataBinder firmaExternaDocumentoComposerBinder) {
    this.firmaExternaDocumentoComposerBinder = firmaExternaDocumentoComposerBinder;
  }

  private void cargarVariablesInstancia() {
    this.variables = (Map) Executions.getCurrent().getArg();
    super.acronimo = (String) this.variables.get(Constantes.VAR_TIPO_DOCUMENTO);
    super.tipoDocumento = super.tipoDocumentoService
        .buscarTipoDocumentoPorId(Integer.valueOf(this.acronimo));
  }

  private void cargarVariablesFirma() {
    super.contenidoTemporal = (byte[]) this.variables.get(Constantes.ARCHIVO_CON_FIRMA_EXTERNA);
    super.motivo = (String) this.variables.get(Constantes.VAR_MOTIVO);
    super.usuarioFirmante = getCurrentUser();
    super.nombreArchivoTemporal = (String) this.variables
        .get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    try {
      super.documentoMetadata = (List<DocumentoMetadataDTO>) this.variables
          .get(Constantes.VAR_DOCUMENTO_DATA);
    } catch (VariableWorkFlowNoExisteException e) {
      logger.debug("Este documento no tiene metadatos", e);
      documentoMetadata = new ArrayList<DocumentoMetadataDTO>();
    }
  }

}
