package com.egoveris.cdeo.web.visualizarDocumento;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.IVisualizarDocumentosService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.VisualizarDocumentoDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosDocumentoComposer extends GenericForwardComposer<Component> {

  /**
  * 
  */
  private static final long serialVersionUID = 6507460147096943801L;

  public static final String NO_POSEE_DATOS_PROPIOS = "Este documento no posee datos propios.";
  public static final String NO_POSEE_FIRMANTES = "Este documento no posee firmantes.";
  public static final String NO_POSEE_ARCHIVOS_DE_TRABAJO = "Este documento no posee archivos de trabajo.";
  public static final String NO_POSEE_HISTORIAL = "Este documento no posee historial.";

  // Componenentes de la ventana
  private Window datosDocumentoWindow;
  private Label txtNumeroSade;
  private Label txtNumeroEspecial;
  private Listbox lstFirmantes;
  private Label txtFechaCreacion;
  private Label txtTipoDocumento;
  private Label txtDatosPropios;
  private Listbox lstArchivosTrabajo;
  private Listbox lstHistorial;
  private Row rowNumeroSade;
  private Row rowNumeroEspecial;
  private Row rowReferencia;
  private Row rowFechaCreacion;
  private Row rowTipoDocumento;
  private Row rowListaFirmantes;
  private Row rowListaUsuariosReservados;
  private Row rowDatosPropios;
  private Row rowArchivosTrabajo;
  private Row rowHistorial;

  private DocumentoMetadataDTO selectedDocumento;
  private ArchivoDeTrabajoDTO selectedArchivoDeTrabajo;

  private Textbox txtReferencia;

  @WireVariable("visualizarDocumentosServiceImpl")
  private IVisualizarDocumentosService visualizarDocumentosService;

  @WireVariable
  private Desktop desktop;
  
  /*
   * Estas variables están seteadas en el Desktop de ZK.
   */
  private String popupUserDocumento;
  private String popupNumeroSade;
  private VisualizarDocumentoDTO popupDocumentoDTO;
  private Map<String, Boolean> popupParametrosVisualizacion;
  
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    if (this.popupDocumentoDTO == null) {
      this.popupDocumentoDTO = (VisualizarDocumentoDTO) desktop.getAttribute(Constantes.DOCUMENTODTO);
    }
    
    if (this.popupDocumentoDTO != null) {
      this.filtrarFilasAcceso();
      this.filtrarFilasVacias();
      this.filtrarArchivo();
    }
  }
  
  public Row getRowNumeroEspecial() {
    return rowNumeroEspecial;
  }

  public void setRowNumeroEspecial(Row rowNumeroEspecial) {
    this.rowNumeroEspecial = rowNumeroEspecial;
  }

  public Row getRowReferencia() {
    return rowReferencia;
  }

  public void setRowReferencia(Row rowReferencia) {
    this.rowReferencia = rowReferencia;
  }

  public Row getRowFechaCreacion() {
    return rowFechaCreacion;
  }

  public void setRowFechaCreacion(Row rowFechaCreacion) {
    this.rowFechaCreacion = rowFechaCreacion;
  }

  public Row getRowTipoDocumento() {
    return rowTipoDocumento;
  }

  public void setRowTipoDocumento(Row rowTipoDocumento) {
    this.rowTipoDocumento = rowTipoDocumento;
  }

  public Row getRowListaFirmantes() {
    return rowListaFirmantes;
  }

  public void setRowListaFirmantes(Row rowListaFirmantes) {
    this.rowListaFirmantes = rowListaFirmantes;
  }

  public Row getRowDatosPropios() {
    return rowDatosPropios;
  }

  public void setRowDatosPropios(Row rowDatosPropios) {
    this.rowDatosPropios = rowDatosPropios;
  }

  public Row getRowArchivosTrabajo() {
    return rowArchivosTrabajo;
  }

  public void setRowArchivosTrabajo(Row rowArchivosTrabajo) {
    this.rowArchivosTrabajo = rowArchivosTrabajo;
  }

  public Row getRowHistorial() {
    return rowHistorial;
  }

  public void setRowHistorial(Row rowHistorial) {
    this.rowHistorial = rowHistorial;
  }

  public void onVisualizarArchivosDeTrabajo() throws InterruptedException {

    try {
      if (popupDocumentoDTO.getMotivoDepuracion() != null) {
        Messagebox.show(
        		Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      } else {
        Filedownload.save(
            this.visualizarDocumentosService
                .descargaArchivoDeTrabajo(this.selectedArchivoDeTrabajo),
            null, this.selectedArchivoDeTrabajo.getNombreArchivo());
      }

    } catch (Exception e) {
      Messagebox.show(Labels.getLabel("Error: " + e.getMessage()), Labels.getLabel("gedo.general.information"),
          Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  private void filtrarFilasVacias() {

    if (StringUtils.isEmpty(this.popupDocumentoDTO.getNumeroSade())
    		&& txtNumeroSade != null) {
      this.txtNumeroSade.setValue("Este documento no posee número SADE");
    }

    if (StringUtils.isEmpty(this.popupDocumentoDTO.getNumeroEspecial()) 
    		&& txtNumeroEspecial != null) {
      this.txtNumeroEspecial.setValue("Este documento no posee número especial");
    }

    if (StringUtils.isEmpty(this.popupDocumentoDTO.getReferencia()) 
    		&& txtReferencia != null) {
      this.txtReferencia.setValue("Este documento no posee referencia");
    }

    if (StringUtils.isEmpty(this.popupDocumentoDTO.getTipoDocumento()) 
    		&& txtTipoDocumento != null) {
      this.txtTipoDocumento.setValue("Este documento no posee Tipo");
    }

    if (this.popupDocumentoDTO.getListaFirmantes() == null
        || this.popupDocumentoDTO.getListaFirmantes().isEmpty()) {
      this.rowListaFirmantes.setVisible(false);
    }

    if (this.popupDocumentoDTO.getDatosPropios() == null
        || this.popupDocumentoDTO.getDatosPropios().isEmpty()) {
      this.rowDatosPropios.setVisible(false);
    }

    if (this.popupDocumentoDTO.getListaHistorialDTO() == null
        || this.popupDocumentoDTO.getListaHistorialDTO().isEmpty()) {
      this.rowHistorial.setVisible(false);
    }
    if (this.popupDocumentoDTO.getListaUsuariosReservados() == null
        || this.popupDocumentoDTO.getListaUsuariosReservados().isEmpty()) {
      this.getRowListaUsuariosReservados().setVisible(false);
    }

    if (this.popupDocumentoDTO.getListaArchivosDeTrabajo() == null
        || this.popupDocumentoDTO.getListaArchivosDeTrabajo().isEmpty()) {
      this.rowArchivosTrabajo.setVisible(false);
    }

  }

  private void filtrarFilasAcceso() {
    this.rowNumeroSade.setVisible(true);
    this.rowNumeroEspecial.setVisible(true);
    this.rowReferencia.setVisible(true);
    this.rowFechaCreacion.setVisible(true);
    this.rowTipoDocumento.setVisible(true);

    if (popupDocumentoDTO.getPuedeVerDocumento()) {
      this.rowListaFirmantes.setVisible(true);
      this.rowDatosPropios.setVisible(true);
      this.rowArchivosTrabajo.setVisible(true);
      this.rowHistorial.setVisible(true);
      this.rowListaUsuariosReservados.setVisible(true);
    } else {
      this.rowListaFirmantes.setVisible(false);
      this.rowDatosPropios.setVisible(false);
      this.rowArchivosTrabajo.setVisible(false);
      this.rowHistorial.setVisible(false);
      this.rowListaUsuariosReservados.setVisible(false);
    }

  }

  private void filtrarArchivo() {
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_NUMERO_SADE) != null) {
      this.rowNumeroSade.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_NUMERO_SADE));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_NUMERO_ESPECIAL) != null) {
      this.rowNumeroEspecial.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_NUMERO_ESPECIAL));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_NUMERO_ESPECIAL) != null) {
      this.rowReferencia.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_REFERENCIA));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_FECHA_CREACION) != null) {
      this.rowFechaCreacion.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_FECHA_CREACION));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_TIPO_DOCUMENTO) != null) {
      this.rowTipoDocumento.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_TIPO_DOCUMENTO));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_FIRMANTES) != null) {
      this.rowListaFirmantes.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_FIRMANTES));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_DATOS_PROPIOS) != null) {
      this.rowDatosPropios.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_DATOS_PROPIOS));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_ARCHIVOS_DE_TRABAJO) != null) {
      this.rowArchivosTrabajo.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_ARCHIVOS_DE_TRABAJO));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_HISTORIAL) != null) {
      this.rowHistorial.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_HISTORIAL));
    }
    if (this.popupParametrosVisualizacion
        .get(ParametrosVisualizacionDocumento.PARAMETRO_USUARIOS_RESERVADOS) != null) {
      this.rowListaUsuariosReservados.setVisible(this.popupParametrosVisualizacion
          .get(ParametrosVisualizacionDocumento.PARAMETRO_USUARIOS_RESERVADOS));
    }
  }

  // Componentes de la ventana
  public Label getTxtNumeroSade() {
    return txtNumeroSade;
  }

  public void setTxtNumeroSade(Label txtNumeroSade) {
    this.txtNumeroSade = txtNumeroSade;
  }

  public Label getTxtNumeroEspecial() {
    return txtNumeroEspecial;
  }

  public void setTxtNumeroEspecial(Label txtNumeroEspecial) {
    this.txtNumeroEspecial = txtNumeroEspecial;
  }

  public Listbox getLstFirmantes() {
    return lstFirmantes;
  }

  public void setLstFirmantes(Listbox lstFirmantes) {
    this.lstFirmantes = lstFirmantes;
  }

  public Label getTxtTipoDocumento() {
    return txtTipoDocumento;
  }

  public void setTxtTipoDocumento(Label txtTipoDocumento) {
    this.txtTipoDocumento = txtTipoDocumento;
  }

  public Label getTxtDatosPropios() {
    return txtDatosPropios;
  }

  public void setTxtDatosPropios(Label txtDatosPropios) {
    this.txtDatosPropios = txtDatosPropios;
  }

  public Listbox getLstArchivosTrabajo() {
    return lstArchivosTrabajo;
  }

  public void setLstArchivosTrabajo(Listbox lstArchivosTrabajo) {
    this.lstArchivosTrabajo = lstArchivosTrabajo;
  }

  public Listbox getLstHistorial() {
    return lstHistorial;
  }

  public void setLstHistorial(Listbox lstHistorial) {
    this.lstHistorial = lstHistorial;
  }

  public void setSelectedDocumento(DocumentoMetadataDTO selectedDocumento) {
    this.selectedDocumento = selectedDocumento;
  }

  public DocumentoMetadataDTO getSelectedDocumento() {
    return selectedDocumento;
  }

  public void setSelectedArchivoDeTrabajo(ArchivoDeTrabajoDTO selectedArchivoDeTrabajo) {
    this.selectedArchivoDeTrabajo = selectedArchivoDeTrabajo;
  }

  public ArchivoDeTrabajoDTO getSelectedArchivoDeTrabajo() {
    return selectedArchivoDeTrabajo;
  }

  public void setRowNumeroSade(Row rowNumeroSade) {
    this.rowNumeroSade = rowNumeroSade;
  }

  public Row getRowNumeroSade() {
    return rowNumeroSade;
  }

  public void setDatosDocumentoWindow(Window datosDocumentoWindow) {
    this.datosDocumentoWindow = datosDocumentoWindow;
  }

  public Window getDatosDocumentoWindow() {
    return datosDocumentoWindow;
  }

  public void setTxtReferencia(Textbox txtReferencia) {
    this.txtReferencia = txtReferencia;
  }

  public Textbox getTxtReferencia() {
    return txtReferencia;
  }

  public void setTxtFechaCreacion(Label txtFechaCreacion) {
    this.txtFechaCreacion = txtFechaCreacion;
  }

  public Label getTxtFechaCreacion() {
    return txtFechaCreacion;
  }

  public void setPopupUserDocumento(String popupUserDocumento) {
    this.popupUserDocumento = popupUserDocumento;
  }

  public String getPopupUserDocumento() {
    return popupUserDocumento;
  }

  public void setPopupNumeroSade(String popupNumeroSade) {
    this.popupNumeroSade = popupNumeroSade;
  }

  public String getPopupNumeroSade() {
    return popupNumeroSade;
  }

  public VisualizarDocumentoDTO getPopupDocumentoDTO() {
    return popupDocumentoDTO;
  }

  public void setPopupDocumentoDTO(VisualizarDocumentoDTO popupDocumentoDTO) {
    this.popupDocumentoDTO = popupDocumentoDTO;
  }

  public Map<String, Boolean> getPopupParametrosVisualizacion() {
    return popupParametrosVisualizacion;
  }

  public void setPopupParametrosVisualizacion(Map<String, Boolean> popupParametrosVisualizacion) {
    this.popupParametrosVisualizacion = popupParametrosVisualizacion;
  }

  public Row getRowListaUsuariosReservados() {
    return rowListaUsuariosReservados;
  }

  public void setRowListaUsuariosReservados(Row rowListaUsuariosReservados) {
    this.rowListaUsuariosReservados = rowListaUsuariosReservados;
  }

}
