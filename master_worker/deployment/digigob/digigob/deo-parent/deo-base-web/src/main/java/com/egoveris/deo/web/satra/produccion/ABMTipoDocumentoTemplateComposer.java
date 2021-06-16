package com.egoveris.deo.web.satra.produccion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelArray;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.egoveris.deo.base.exception.TemplateMalArmadoException;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.macros.PrevisualizacionDocumento;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.DelimitadoresFaltantesException;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMTipoDocumentoTemplateComposer extends GEDOGenericForwardComposer {

  private static final String ESPACIO_ETIQUETA_NOMBRE = ": ";
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ABMTipoDocumentoTemplateComposer.class);
  public static final String MODO_CONSULTA_TEMPLATE = "modoConsultaTemplate";
  public static final String MODO_ALTA_TEMPLATE = "modoAltaTemplate";
  public static final String MODO_MODIFICACION_TEMPLATE = "modoModificacionTemplate";
  public static final String CAMPO_OBLIGATORIO = "obligatorio";
  public static final String CAMPO_NO_OBLIGATORIO = "noObligatorio";
  public static final String ORIGEN_CLIPBOARD = "copyClipboard";
  public static final String SEPARATOR = "SEPARATOR";
  public static final String SEPARATOR_GENERICO = "Separator - Generico";
  public static final String SEPARATOR_REPETIDOR = "Separator - Repetidor";
  public static final String SEPARATOR_INTERNO = "Separator - Interno";
  public static final String MULTILINEA = "MULTILINEA";
  public static final String MULTILINEA_EDITABLE = "MULTILINEA EDITABLE";

  @WireVariable("externalFormularioService")
  private ExternalFormularioService formularioService;
  @WireVariable("procesamientoTemplateImpl")
  private ProcesamientoTemplate procesamientoTemplate;

  private Combobox comboboxFormulariosControlados;
  public CKeditor ckeditor;
  private Listbox camposFormularioControladoListBox;
  private Textbox descripcionTemplate;
  private Label labelArmarTemplate;
  private Toolbarbutton guardar;
  private Button copiarTodos;
  private AnnotateDataBinder binder;
  private TipoDocumentoDTO tipoDocumento;
  @WireVariable("pdfServiceImpl")
  private PdfService pdfService;

  private String modo = null;
  private FormularioDTO selectedFormularioControlado = null;
  private List<FormularioDTO> listaFormulariosControlados = new ArrayList<>();
  private Map<String, InputComponent> mapNombreFCCamposFormulario;
  @WireVariable("zkFormManagerFactory")
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;
  @WireVariable("zkFormManager")
  private IFormManager<Component> manager;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    inicializarValores(comp);
    this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento");

    if (this.modo.equals(MODO_ALTA_TEMPLATE)) {
      this.renderAltaTemplate();
      this.inicializarFormulariosControlados();
    } else if (this.modo.equals(MODO_MODIFICACION_TEMPLATE)) {
      this.inicializarFormulariosControlados();
      this.renderModificacionTemplate();
    } else if (this.modo.equals(MODO_CONSULTA_TEMPLATE)) {
      inicializarFormulariosControlados();
      this.renderConsultaTemplate();
    } else {
      throw new Exception(
          Labels.getLabel("gedo.abmTipoDocTemplateComposer.exception.errorSistema"));
    }
  }

  private void leyendasInformativas() {
    labelArmarTemplate
        .setValue(Labels.getLabel("gedo.abmTipoDocTemplateComposer.label.botonesCombo"));
  }

  private void renderDatosPantalla(String datos, String descripcionTemplate,
      String idFormularioControlado) throws Exception {
    this.ckeditor.setValue(datos);
    this.descripcionTemplate.setValue(descripcionTemplate);

    for (FormularioDTO f : listaFormulariosControlados) {
      if (f.getNombre().equals(idFormularioControlado)) {
        this.setSelectedFormularioControlado(f);
        break;
      } else {
        this.setSelectedFormularioControlado(null);
      }
    }
    if (this.getSelectedFormularioControlado() != null) {
      this.onSeleccionarComboboxFormulariosControlados();
    }
  }

  private void renderAltaTemplate() {
    leyendasInformativas();
    this.copiarTodos.setDisabled(true);
  }

  private void renderConsultaTemplate() throws Exception {
    TipoDocumentoTemplateDTO tipoDocumentoTemplate = procesamientoTemplate
        .obtenerUltimoTemplatePorTipoDocumento(
            (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento"));

    if (tipoDocumentoTemplate != null) {

      InputStream template = new ByteArrayInputStream(
          tipoDocumentoTemplate.getTemplate().getBytes());

      renderDatosPantalla(this.transformarInToString(template),
          tipoDocumentoTemplate.getDescripcion(), tipoDocumentoTemplate.getIdFormulario());
    }
    this.guardar.setDisabled(true);
    this.comboboxFormulariosControlados.setDisabled(true);
    this.descripcionTemplate.setDisabled(true);
    this.copiarTodos.setDisabled(true);
  }

  private void renderModificacionTemplate() throws Exception {
    leyendasInformativas();
    renderDatosPantalla((String) Executions.getCurrent().getArg().get("datos"),
        (String) Executions.getCurrent().getArg().get("descripcionTemplate"),
        (String) Executions.getCurrent().getArg().get("idFormularioControlado"));
    this.copiarTodos.setDisabled(false);
  }

  private void inicializarValores(Component comp) {
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
    this.modo = (String) Executions.getCurrent().getArg().get("modo");
  }

  public void onSeleccionarComboboxFormulariosControlados() throws Exception {

    if (this.selectedFormularioControlado != null) {
      this.mapNombreFCCamposFormulario = new TreeMap<>();
      CampoBusqueda separadorRepetitivo = null;

      manager = formManagerFact.create(this.selectedFormularioControlado.getNombre());

      // ordeno listCampoBusqueda segun el orden de Campo Busqueda
      List<CampoBusqueda> listCampoBusqueda = manager.searchFields();
      Collections.sort(listCampoBusqueda, new Comparator<CampoBusqueda>() {
        @Override
        public int compare(CampoBusqueda campo1, CampoBusqueda campo2) {
          return campo1.getOrden().compareTo(campo2.getOrden());
        }
      });

      for (CampoBusqueda campoBusqueda : listCampoBusqueda) {
        if (campoBusqueda.getNombre().equals(SEPARATOR_REPETIDOR)) {
          this.mapNombreFCCamposFormulario.put(campoBusqueda.getNombre(), null);
          separadorRepetitivo = campoBusqueda;
        } else if (campoBusqueda.getNombre().contains(MULTILINEA)) {
          this.mapNombreFCCamposFormulario.put(campoBusqueda.getNombre(), null);
        } else {
          Component comp = manager.getComponent(campoBusqueda.getNombre());
          if (comp instanceof InputComponent) {
            this.mapNombreFCCamposFormulario.put(campoBusqueda.getNombre(), (InputComponent) comp);
          }
        }
      }
      if (separadorRepetitivo != null) {
        listCampoBusqueda.remove(separadorRepetitivo);
        listCampoBusqueda.add(0, separadorRepetitivo);
      }

      if (this.mapNombreFCCamposFormulario.isEmpty()) {
        throw new Exception(
            Labels.getLabel("gedo.abmTipoDocTemplateComposer.exception.errorFormControlado"));
      } else {
        renderListBoxFormulariosControlados(listCampoBusqueda);
        this.binder.loadComponent(this.camposFormularioControladoListBox);
        this.copiarTodos.setDisabled(false);
      }
    }
  }

  private void renderListBoxFormulariosControlados(List<CampoBusqueda> listCampoBusqueda) {

    Listhead lh = new org.zkoss.zul.Listhead();

    Components.removeAllChildren(this.camposFormularioControladoListBox);

    lh.appendChild(new org.zkoss.zul.Listheader("Campos", null, "85%"));
    lh.appendChild(new org.zkoss.zul.Listheader("", null, "14%"));
    this.camposFormularioControladoListBox.appendChild(lh);

    for (CampoBusqueda cb : listCampoBusqueda) {
      this.camposFormularioControladoListBox.appendChild(this.crearListItemFC(cb));
    }
  }

  private Listitem crearListItemFC(CampoBusqueda cb) {
	Toolbarbutton button = new Toolbarbutton();
    Listitem li = new Listitem();
    Listcell lc = new Listcell();
    Listcell lcButton = new Listcell();
    String valueButton;

    button.setImage(Constantes.IMG_COPIAR_NO_OBLIGATORIO);
    valueButton = obtenerEtiquetaMasCampo(cb);

    button.setWidth("26px");
    button.setAttribute("value", valueButton);
    button.setAttribute("origen", ORIGEN_CLIPBOARD);
    button.setTooltiptext(Labels.getLabel("gedo.abmTipDocTempCom.msgbox.copiar ") + valueButton
        + Labels.getLabel(" gedo.abmTipDocTempCom.msgbox.editor"));
    button.addEventListener(Events.ON_CLICK, new ABMTipoDocumentoTemplateComposerListener(this));
    lc.appendChild(new Label(cb.getNombre()));
    lcButton.appendChild(button);

    li.appendChild(lc);
    li.appendChild(lcButton);
    return li;
  }

  private String obtenerTextoMultilinea(String nombreFormularioComponente) {
    String nombreFormulario = nombreFormularioComponente.replaceFirst(" " + MULTILINEA, "");
//    nombreFormulario = nombreFormulario.replaceFirst(" " + MULTILINEA_EDITABLE, "");
    Set<FormularioComponenteDTO> listaComponentes = this.selectedFormularioControlado
        .getFormularioComponentes();
    try {
      for (FormularioComponenteDTO componente : listaComponentes) {
        if (componente.isMultilinea() && componente.getNombre().equals(nombreFormulario)) {
          return this.formularioService.buscarMultilinea(componente.getId()).replaceAll("\\n",
              "<br/>");
        }
      }
    } catch (DynFormException e) {
      LOGGER.error("Error al obtener contenido de componente multilinea" + e.getMessage(), e);
    }
    return "";
  }

  private String obtenerEtiquetaMasCampo(CampoBusqueda cb) {

    StringBuilder campoFormato = new StringBuilder();

    String nombreFormularioComponente = cb.getNombre();

    if (nombreFormularioComponente.equals(SEPARATOR_REPETIDOR)) {
      campoFormato.append(cb.getEtiqueta() + " ¬R ¬R");
    } else if (nombreFormularioComponente.contains(MULTILINEA) && !nombreFormularioComponente.contains(MULTILINEA_EDITABLE)) {
      campoFormato.append(obtenerTextoMultilinea(nombreFormularioComponente));
    } else if (nombreFormularioComponente.equals(SEPARATOR_GENERICO)
        || nombreFormularioComponente.equals(SEPARATOR_INTERNO)) {
      campoFormato.append(cb.getEtiqueta());
    } else {
    	if (nombreFormularioComponente.contains(MULTILINEA_EDITABLE)){
    		nombreFormularioComponente = nombreFormularioComponente.replaceFirst(" " + MULTILINEA_EDITABLE, "");
    	} 
      campoFormato.append(cb.getEtiqueta());
      campoFormato.append(ESPACIO_ETIQUETA_NOMBRE);

      boolean esDate = this.mapNombreFCCamposFormulario
          .get(nombreFormularioComponente) instanceof Datebox;
      boolean esLong = this.mapNombreFCCamposFormulario
          .get(nombreFormularioComponente) instanceof Longbox;
      boolean esDouble = this.mapNombreFCCamposFormulario
          .get(nombreFormularioComponente) instanceof Doublebox;
      boolean esTime = this.mapNombreFCCamposFormulario
              .get(nombreFormularioComponente) instanceof Timebox;
      campoFormato.append("${");

      if (esDate || esLong || esDouble || esTime) {
        campoFormato.append("(");

      }
      campoFormato.append(nombreFormularioComponente);

      if (esDate) {
        campoFormato.append("?string(\"dd/MM/yyyy\"))");
      } else if (esLong) {
        campoFormato.append("?c)");
      } else if (esDouble) {
        campoFormato.append("?string(\",##0.00\"))");
      } else if (esTime) {
          campoFormato.append("?string(\"HH:mm:ss\"))");
      }

      campoFormato.append("!}");
    }
    return campoFormato.toString();
  }

  private void inicializarFormulariosControlados() throws DynFormException {
    List<FormularioDTO> listFormulario = this.formularioService.obtenerTodosLosFormularios();

    Collections.sort(listFormulario, new Comparator<FormularioDTO>() {
      @Override
      public int compare(FormularioDTO s1, FormularioDTO s2) {
        return s1.getNombre().compareToIgnoreCase(s2.getNombre());
      }
    });

    this.setListaFormulariosControlados(listFormulario);
  }

  public void onCancelar() {
    this.closeAndNotifyAssociatedWindow(null);
  }

  public void onPrevisualizar() throws InterruptedException {
    PrevisualizarLibreTemplate();
  }

  private void PrevisualizarLibreTemplate() throws InterruptedException {

    String sdata;
    try {
      sdata = this.ckeditor.getValue();
      byte[] contenidoPDF;

      sdata = sdata.replaceAll("¬R", "");

      if (this.tipoDocumento.getEsComunicable()) {
        contenidoPDF = this.pdfService.generarPDFCCOO(sdata, "", tipoDocumento,
            cargarDatosComunicable());
      } else {
        contenidoPDF = this.pdfService.generarPDF(sdata, "", tipoDocumento);
      }

      byte[] contenidoPreviewPdf = pdfService.crearDocumentoPDFPrevisualizacion(contenidoPDF,
          tipoDocumento);
      PrevisualizacionDocumento.window(contenidoPreviewPdf, Boolean.FALSE, this.self);

    } catch (WrongValueException e) {
      LOGGER.error("Error al previsualizar - ", e);
      throw e;
    } catch (ClavesFaltantesException e) {
      LOGGER.error("Error al previsualizar - ", e);
      Messagebox.show(Labels.getLabel("gedo.producirDocumento.error.clavesFaltantes"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      LOGGER.error("Error en creación de archivo pdf ", e);
      Messagebox.show(Labels.getLabel("gedo.previsualizar.error"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
    }
  }

  private RequestGenerarDocumento cargarDatosComunicable() {
    RequestGenerarDocumento request = new RequestGenerarDocumento();
    request.setReparticion("");
    ComunicacionDTO comunicacion = new ComunicacionDTO();
    request.setComunicacionRespondida(comunicacion);
    List<String> listaDestinos = new ArrayList<>();
    List<String> listaCopias = new ArrayList<>();
    List<UsuarioExternoDTO> listaExternos = new ArrayList<>();
    request.setMotivo("");
    request.setListaUsuariosDestinatarios(listaDestinos);
    request.setListaUsuariosDestinatariosCopia(listaCopias);
    request.setListaUsuariosDestinatariosExternos(listaExternos);
    if (tipoDocumento.getCodigoTipoDocumentoSade() == null) {
      tipoDocumento.setCodigoTipoDocumentoSade("NO");
    }
    return request;
  }

  @SuppressWarnings("deprecation")
  public void onGuardar() throws Exception {

    validacionABMTemplate();

    try {
      procesamientoTemplate.validarCamposTemplatePorArrayByteYMap(
          IOUtils.toByteArray(this.ckeditor.getValue()), this.crearMapaDePrueba());

      if (this.ckeditor.getValue().contains("¬R")) {
        String[] lista = this.ckeditor.getValue().split("¬R");
        if (lista.length % 2 != 1) {
          throw new DelimitadoresFaltantesException(
              Labels.getLabel("gedo.abmTipoDocTemplateComposer.exception.errorGenerarTemplate"));
        }
      }

      Map<String, Object> map = new HashMap<>();
      map.put("origen", Constantes.EVENTO_ABM_DOCUMENTO_TEMPLATE);
      map.put("datos", ckeditor.getValue());
      map.put("idFormularioControlado", selectedFormularioControlado.getNombre());
      map.put("descripcionTemplate", descripcionTemplate.getValue());
      this.closeAndNotifyAssociatedWindow(map);

    } catch (DelimitadoresFaltantesException e) {
      LOGGER.error("Error al guardar template para tipo de documento " + e.getMessage(), e);
      throw new WrongValueException(e.getMessage());
    } catch (TemplateMalArmadoException tma) {
      LOGGER.error("Error al guardar template para tipo de documento " + tma.getMessage(), tma);
      throw new WrongValueException(this.ckeditor,
          Labels.getLabel("gedo.producirDocumento.error.parseoTemplate"));
    } catch (ClavesFaltantesException vie) {
      LOGGER.error("Error al guardar template para tipo de documento " + vie.getMessage(), vie);
      throw new WrongValueException(this.comboboxFormulariosControlados,
          Labels.getLabel("gedo.producirDocumento.error.contenidoTemplate"));
    } catch (Exception e) {
      LOGGER.error("Error al guardar template para tipo de documento " + e.getMessage(), e);
      throw new WrongValueException(
          Labels.getLabel("gedo.abmTipoDocTemplateComposer.exception.errorIntenteMinutos"));
    }
  }

  public void onCopiarTodos() {

    String stringTodos;
    StringBuilder stringTodosAux = new StringBuilder();

    List<CampoBusqueda> listCampoBusqueda = manager.searchAllFields();

    for (CampoBusqueda campoBusqueda : listCampoBusqueda) {
      stringTodosAux.append(this.obtenerEtiquetaMasCampo(campoBusqueda)).append("</br>");
    }

    stringTodos = stringTodosAux.toString();
    if (!stringTodos.isEmpty())
      this.putStringInCK(stringTodos);
  }

  private Map<String, Object> crearMapaDePrueba() {
    Map<String, Object> mapss = new HashMap<>();

    for (String campo : this.mapNombreFCCamposFormulario.keySet()) {
      if (this.mapNombreFCCamposFormulario.get(campo) instanceof Datebox) {
        mapss.put(campo, new Date());
      } else if (this.mapNombreFCCamposFormulario.get(campo) instanceof Longbox) {
        mapss.put(campo, Long.valueOf("123456789"));
      } else {
        if (this.mapNombreFCCamposFormulario.get(campo) instanceof Doublebox) {
          mapss.put(campo, Double.valueOf(100000));
        } else {
          mapss.put(campo, "CAMPO_EJEMPLO");
        }
      }
    }
    return mapss;
  }

  private void validacionABMTemplate() throws Exception {

    if (this.descripcionTemplate.getValue() == null || (this.descripcionTemplate.getValue() != null
        && "".equals(this.descripcionTemplate.getValue()))) {
      Clients.clearBusy();
      throw new WrongValueException(this.descripcionTemplate,
          Labels.getLabel("gedo.nuevoDocumento.error.descripcion.produccion.template"));
    }

    if (this.selectedFormularioControlado == null) {
      Clients.clearBusy();
      throw new WrongValueException(this.comboboxFormulariosControlados,
          Labels.getLabel("gedo.nuevoDocumento.error.ckeditor.noSeleccion.produccion.template"));
    }

    if (this.ckeditor.getValue().isEmpty()) {
      Clients.clearBusy();
      throw new WrongValueException(this.ckeditor,
          Labels.getLabel("gedo.producirDocumento.error.textoVacio"));
    }
  }

  private String transformarInToString(InputStream co) {

    StringBuilder sb = new StringBuilder();
    String line;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(co, "UTF-8"))) {

      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (UnsupportedEncodingException uee) {
      LOGGER.error("Encoding no soportado", uee);
    } catch (IOException ioe) {
      LOGGER.error("No se pudo cerrar el archivo", ioe);
    } finally {
      try {
        co.close();
      } catch (IOException ioe) {
        LOGGER.error("No se pudo cerrar el archivo", ioe);
      }
    }

    return sb.toString();
  }

  public void onChanging$comboboxFormulariosControlados(InputEvent e) {
    List<FormularioDTO> listaFormulario = new ArrayList<>();
    ListModel model = this.comboboxFormulariosControlados.getModel();
    for (int i = 0; i < model.getSize(); i++) {
      FormularioDTO formularioDto = (FormularioDTO) model.getElementAt(i);
      if (formularioDto.getNombre().toLowerCase().contains(e.getValue().toLowerCase())) {
        listaFormulario.add(formularioDto);
      }
    }
    if (!"".equals(e.getValue())) {
      this.comboboxFormulariosControlados
          .setModel(new BindingListModelArray(listaFormulario.toArray(), true));
    } else {
      this.comboboxFormulariosControlados
          .setModel(new BindingListModelArray(this.listaFormulariosControlados.toArray(), true));
    }
  }

  public CKeditor getCkeditor() {
    return ckeditor;
  }

  public void setCkeditor(CKeditor ckeditor) {
    this.ckeditor = ckeditor;
  }

  public void putStringInCK(String stringCopy) {
    String command = "copyToCk('" + stringCopy + "');";
    Clients.evalJavaScript(command);
  }

  public Combobox getComboboxFormulariosControlados() {
    return comboboxFormulariosControlados;
  }

  public void setComboboxFormulariosControlados(Combobox comboboxFormulariosControlados) {
    this.comboboxFormulariosControlados = comboboxFormulariosControlados;
  }

  public FormularioDTO getSelectedFormularioControlado() {
    return selectedFormularioControlado;
  }

  public void setSelectedFormularioControlado(FormularioDTO selectedFormularioControlado) {
    this.selectedFormularioControlado = selectedFormularioControlado;
  }

  public List<FormularioDTO> getListaFormulariosControlados() {
    return listaFormulariosControlados;
  }

  public void setListaFormulariosControlados(List<FormularioDTO> listaFormulariosControlados) {
    this.listaFormulariosControlados = listaFormulariosControlados;
  }

  public Listbox getCamposFormularioControladoListBox() {
    return camposFormularioControladoListBox;
  }

  public void setCamposFormularioControladoListBox(Listbox camposFormularioControladoListBox) {
    this.camposFormularioControladoListBox = camposFormularioControladoListBox;
  }

  public Textbox getDescripcionTemplate() {
    return descripcionTemplate;
  }

  public void setDescripcionTemplate(Textbox descripcionTemplate) {
    this.descripcionTemplate = descripcionTemplate;
  }

}

final class ABMTipoDocumentoTemplateComposerListener implements EventListener {
  private ABMTipoDocumentoTemplateComposer composer;

  public ABMTipoDocumentoTemplateComposerListener(ABMTipoDocumentoTemplateComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CLICK)) {
      String origen = (String) event.getTarget().getAttribute("origen");
      if (origen != null && "copyClipboard".equalsIgnoreCase(origen)) {
        // Habria que replantearlo, dado que lo que hago es:
        // Parado sobre una component, voy al cell y luego al item.
        // Una vez alli pido el primer hijo, del primer hijo y obtengo
        // SIEMPRE el label que esta en el item.
        String stringCopy = (String) event.getTarget().getAttribute("value");
        if (stringCopy != null && !"".equalsIgnoreCase(stringCopy)) {
          this.composer.putStringInCK(stringCopy);
        }
      }
    }
  }
}
