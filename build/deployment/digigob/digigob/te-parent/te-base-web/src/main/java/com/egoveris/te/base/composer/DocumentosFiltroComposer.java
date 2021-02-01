package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.util.FiltroEE;
import com.egoveris.te.base.util.UtilsDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Paging;

/**
 * 
 * @author lfishkel
 *
 */
public class DocumentosFiltroComposer extends GenericDocumentoComposer {

  private static final long serialVersionUID = -7306923832998599988L;

  @Autowired
  private Listbox listboxDocumentosFiltro;
  @Autowired
  private Bandbox reparticionBusquedaDocumento;
  @Autowired
  private Intbox anio;
  @Autowired
  private Datebox fechaDesde;
  @Autowired
  private Label labelPaginaFiltro;
  @Autowired
  private Listfooter listFooter;
  @Autowired
  private Paging pagingDocumentoFiltro;
  @Autowired
  private Datebox fechaHasta;
  private List<DocumentoDTO> documentosFiltradosFiltro;
  private List<String> referenciasCompletas;
  private List<String> tiposDocumentosCompletos;
  private List<String> referenciasFiltradas;
  private List<String> tiposDocumentosFiltrados;
  private List<DocumentoDTO> listaCompleta;
  private AnnotateDataBinder binder;
  private Combobox busquedaReferenciaComboBox;
  private Combobox busquedaTipoDocumentoComboBox;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(this.listboxDocumentosFiltro);
    comp.addEventListener(Events.ON_CLICK, new DocumentosFiltroEventListener(this));
    comp.addEventListener(Events.ON_USER, new DocumentosFiltroEventListener(this));
    super.setearVentanaAbierta(this.self);
    super.setExpedienteElectronico((ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
        .getAttribute("eeAcordeon"));
    super.initializeDocumentsList(false);
    armarFiltroReferenciasYTiposDocumentos();
    this.onCancelarFiltro();
  }

  @SuppressWarnings("unchecked")
  private void armarFiltroReferenciasYTiposDocumentos() {
    this.referenciasCompletas = new ArrayList<String>();
    Set<String> referencias = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    this.tiposDocumentosCompletos = new ArrayList<String>();
    Set<String> tiposDocumentos = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    
    for (DocumentoDTO documento : this.getExpedienteElectronico().getDocumentos()) {
      String motivo = FiltroEE.normalizarString(documento.getMotivo());
      String tipoDocumento = FiltroEE.normalizarString(documento.getTipoDocAcronimo());
      referencias.add(motivo);
      tiposDocumentos.add(tipoDocumento);
    }
    
    this.referenciasCompletas.addAll(referencias);
    this.tiposDocumentosCompletos.addAll(tiposDocumentos);
  }

  public void onAplicarFiltro() throws Exception {
    validarCampos(fechaDesde.getValue(), fechaHasta.getValue());
    
    if (this.listaCompleta == null) {
      this.listaCompleta = super.cargarDocumentosFiltrados();
    }
    
    this.documentosFiltradosFiltro = FiltroEE.filtrarConCamposDeBusqueda(this.listaCompleta,
        reparticionBusquedaDocumento.getValue(), anio.getValue(), fechaDesde.getValue(),
        fechaHasta.getValue(), busquedaTipoDocumentoComboBox.getValue(),
        busquedaReferenciaComboBox.getValue());
    listboxDocumentosFiltro.setModel(new ListModelList(documentosFiltradosFiltro));
    this.setearTamanio();
    paginadoDeDocumentosFiltro();
  }

  private void validarCampos(Date desde, Date hasta) {
    if (desde != null ^ hasta != null) {
      if (desde == null)
        throw new WrongValueException("Debe ingresar fecha desde");
      else
        throw new WrongValueException("Debe ingresar fecha hasta");
    }

    if ((this.busquedaTipoDocumentoComboBox.getValue() == null
        || this.busquedaTipoDocumentoComboBox.getValue().isEmpty())
        && (this.busquedaReferenciaComboBox.getValue() == null
            || this.busquedaReferenciaComboBox.getValue().isEmpty())) {
      throw new WrongValueException("Debe ingresar el criterio Tipo Documento o Referencia");

    }
    
    if (this.anio.getValue() != null && this.anio.getValue() < 2006) {
      throw new WrongValueException("Debe ingresar un año correcto (desde 2006 en adelante)");
    }
    
  }

  public void onCancelarFiltro() throws Exception {
    super.initializeDocumentsList(false);
    this.setearTamanio();
    paginadoDeDocumentosFiltro();
    this.limpiarCamposBusqueda();
  }

  private void setearTamanio() {
    Integer tamanio = documentosFiltradosFiltro.size();
    listFooter.setLabel(tamanio.toString());
    binder.loadComponent(listboxDocumentosFiltro);
  }

  public void onDescargarTodos() throws IOException, InterruptedException {
    this.onDescargarTodos("Documentos-" + super.expedienteElectronico.getCodigoCaratula(),
        documentosFiltradosFiltro, "FILTRO", crearTxtFiltro());
    super.ordenarDocumentos(documentosFiltradosFiltro);
  }

  public byte[] crearTxtFiltro() {
    StringBuilder sb = new StringBuilder();
    sb.append("Documentos vinculados a ");
    sb.append(super.expedienteElectronico.getCodigoCaratula());
    sb.append(" filtrados por el siguiente criterio:  ");
    sb.append(System.getProperty("line.separator"));
    sb.append(System.getProperty("line.separator"));
    sb.append(Labels.getLabel("ee.tramitacion.expediente.txtFiltro.reparticion"));
    sb.append(" ");
    sb.append(reparticionBusquedaDocumento.getValue());
    sb.append(System.getProperty("line.separator"));
    sb.append(Labels.getLabel("ee.tramitacion.expediente.txtFiltro.anio"));
    
    if (anio.getValue() != null) {
      sb.append(anio.getValue().toString());
      sb.append("   ");
    } else
      sb.append(" ");
    sb.append(System.getProperty("line.separator"));
    sb.append("Desde: ");
    
    if (fechaDesde.getValue() != null) {
      sb.append(UtilsDate.formatearFechaHora(fechaDesde.getValue()));
      sb.append(System.getProperty("line.separator"));
      sb.append("Hasta: ");
      sb.append(UtilsDate.formatearFechaHora(fechaHasta.getValue()));
    } else {
      sb.append("   ");
      sb.append(System.getProperty("line.separator"));
      sb.append("Hasta: ");
      sb.append("   ");

    }
    
    sb.append(System.getProperty("line.separator"));
    sb.append("Tipo Documento: ");
    sb.append(busquedaTipoDocumentoComboBox.getValue());
    sb.append(System.getProperty("line.separator"));
    sb.append("Referencia: ");
    sb.append(busquedaReferenciaComboBox.getValue());
    return sb.toString().getBytes();
  }

  private void paginadoDeDocumentosFiltro() {
    labelPaginaFiltro.setVisible(
        (pagingDocumentoFiltro.getPageSize() == 10 && documentosFiltradosFiltro.size() > 10));
  }

  public List<DocumentoDTO> getDocumentosFiltradosFiltro() {
    return documentosFiltradosFiltro;
  }

  public void setDocumentosFiltradosFiltro(List<DocumentoDTO> documentosFiltradosFiltro) {
    this.documentosFiltradosFiltro = documentosFiltradosFiltro;
  }

  private void limpiarCamposBusqueda() {
    this.reparticionBusquedaDocumento.setValue(StringUtils.EMPTY);
    this.anio.setValue(null);
    this.fechaDesde.setValue(null);
    this.fechaHasta.setValue(null);
    this.busquedaReferenciaComboBox.setValue(null);
    this.busquedaTipoDocumentoComboBox.setValue(null);
  }

  @SuppressWarnings("unchecked")
  public void refreshList(List<?> model) {
    this.listaCompleta = (List<DocumentoDTO>) model;
    this.documentosFiltradosFiltro = this.listaCompleta;
    documentosFiltradosFiltro = super.ordenarDocumentos(documentosFiltradosFiltro);
    super.refreshList(this.listboxDocumentosFiltro, this.documentosFiltradosFiltro);
    this.setearTamanio();
  }

  public void onChanging$busquedaReferenciaComboBox(InputEvent e) {
    this.referenciasFiltradas = new ArrayList<String>();
    this.cargarReferencias(e);
  }

  public void cargarReferencias(InputEvent e) {

    String matchingText = FiltroEE.normalizarString(e.getValue());
    this.referenciasFiltradas.clear();
    
    if (!matchingText.equals("") && (matchingText.length() >= 3)) {
      if (this.referenciasFiltradas != null) {
        matchingText = matchingText.toUpperCase();
        
        for (String referencia : this.referenciasCompletas) {
          Pattern pat = Pattern.compile(".*" + matchingText.trim() + ".*");
          Matcher mat = pat.matcher(referencia);

          if (mat.matches()) {
            this.referenciasFiltradas.add(referencia);
          }
        }
      }
    } else if (matchingText.trim().equals("")) {

      this.referenciasFiltradas = new ArrayList<String>();
    }

    this.busquedaReferenciaComboBox.setModel(new ListModelList(referenciasFiltradas));

    this.binder.loadComponent(busquedaReferenciaComboBox);
  }

  public void onChanging$busquedaTipoDocumentoComboBox(InputEvent e) {
    this.tiposDocumentosFiltrados = new ArrayList<String>();
    this.cargarTiposDocumentos(e);
  }

  public void cargarTiposDocumentos(InputEvent e) {

    String matchingText = FiltroEE.normalizarString(e.getValue());
    this.tiposDocumentosFiltrados.clear();
    if (!matchingText.equals("") && (matchingText.length() >= 2)) {
      if (this.tiposDocumentosFiltrados != null) {
        matchingText = matchingText.toUpperCase();
        for (String tipoDocumento : this.tiposDocumentosCompletos) {

          Pattern pat = Pattern.compile(".*" + matchingText.trim() + ".*");
          Matcher mat = pat.matcher(tipoDocumento);

          if (mat.matches()) {
            this.tiposDocumentosFiltrados.add(tipoDocumento);
          }
        }
      }
    } else if (matchingText.trim().equals("")) {

      this.tiposDocumentosFiltrados = new ArrayList<String>();
    }

    this.busquedaTipoDocumentoComboBox.setModel(new ListModelList(tiposDocumentosFiltrados));

    this.binder.loadComponent(busquedaTipoDocumentoComboBox);
  }

}

final class DocumentosFiltroEventListener implements EventListener {

  private DocumentosFiltroComposer comp;

  public DocumentosFiltroEventListener(DocumentosFiltroComposer comp) {
    this.comp = comp;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CLICK)) {
      if (event.getData() != null) {
        List<?> model = (List<?>) ((Map<String, Object>) event.getData()).get("model");
        if (model != null) {
          this.comp.refreshList(model);
        }
      }
    }
    if (event.getName().equals(Events.ON_USER)) {
      comp.onDescargarTodos("Documentos-" + comp.getExpedienteElectronico().getCodigoCaratula(),
          comp.getDocumentosFiltradosFiltro(), "FILTRO", comp.crearTxtFiltro());
    }
  }

}
