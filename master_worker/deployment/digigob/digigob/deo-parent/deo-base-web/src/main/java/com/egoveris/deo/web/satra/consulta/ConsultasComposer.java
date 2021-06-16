package com.egoveris.deo.web.satra.consulta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Constrainted;

import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.MetaDocumentoDTO;
import com.egoveris.deo.model.model.MetaDocumentoDTO.TipoConsulta;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.Utilitarios;
import com.egoveris.deo.web.satra.renderers.ValoresMetadatosRenderer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.comp.ext.TextboxExt;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ConsultasComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -4218306124772951269L;

  private Datebox fechaDesde;
  private Datebox fechaHasta;
  protected Combobox tipoDocumento;
  protected Combobox nombreMetadato;
  protected Combobox usuarioFirmante;
  protected Combobox comboMetaFc;
  private Textbox nuevo_valor;
  private Grid grillaValoresAgregados;
  private Button agregar;
  private Button agregarFc;
  private Checkbox mostrar;
  private Checkbox mostrarTodos;
  private Bandbox familiaEstructuraTree;
  private Bandpopup familia;
  private Hbox agregaMetaDataHbox;
  private Hbox agregaDataFcHlayout;
  private Div divFc;

  protected List<MetadataDTO> listaMetadata = new ArrayList<>();
  protected List<MetaDocumentoDTO> listaDocMetadata = new ArrayList<>();
  protected List<CampoBusqueda> listaMetaFc = new ArrayList<>();

  protected TipoDocumentoDTO selectedTipoDocumento;
  protected MetadataDTO selectedMetadata;
  protected CampoBusqueda selectedMetaFc;
  protected AnnotateDataBinder binder;
  @WireVariable("usuarioServiceImpl")
  public IUsuarioService usuarioService;
  @WireVariable("procesamientoTemplateImpl")
  private ProcesamientoTemplate procesamientoTemplate;

  @WireVariable("zkFormManagerFactory")
  protected IFormManagerFactory<IFormManager<Component>> formManagerFact;

  @WireVariable("zkFormManager")
  protected IFormManager<Component> manager;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    familiaEstructuraTree.addEventListener(Events.ON_NOTIFY, new ConsultasComposerListener(this));
    usuarioFirmante.setModel(
        ListModels.toListSubModel(new ListModelList(this.getUsuarioService().obtenerUsuarios()),
            new UsuariosComparator(), 30));
    this.binder = new AnnotateDataBinder(comp);
  }

  public void onClick$buscar() throws InterruptedException, SecurityNegocioException {

    if (this.fechaDesde.getValue() == null && this.fechaHasta.getValue() != null) {
      throw new WrongValueException(this.fechaDesde, "Debe ingresar fecha desde");
    }
    if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() == null) {
      throw new WrongValueException(this.fechaHasta, "Debe ingresar fecha hasta");
    }
    if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() != null
        && this.fechaDesde.getValue().after(this.fechaHasta.getValue())) {
      throw new WrongValueException(this.fechaDesde, "La fecha desde debe ser anterior a la fecha hasta");
    }
    if ((this.usuarioFirmante.getSelectedItem() == null)
        && (!this.usuarioFirmante.getValue().isEmpty())) {
      throw new WrongValueException(this.usuarioFirmante,
          Labels.getLabel("gedo.firmaConjunta.errores.faltaUsuario"));
    }
    if (this.selectedTipoDocumento == null && !familiaEstructuraTree.getValue().equals("")) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
    }

    if (this.mostrarTodos.isChecked()) {
      this.selectedTipoDocumento = null;
    }

    if (this.fechaHasta.getValue() != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(fechaHasta.getValue());
      c.add(Calendar.DATE, 1);
      fechaHasta.setValue(c.getTime());
    }

    ConsultaSolrRequest requestSolr = armarConsultaSolr();
    this.closeAndNotifyAssociatedWindow(requestSolr);
  }

  private ConsultaSolrRequest armarConsultaSolr() throws SecurityNegocioException {
    String userName = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);

    ConsultaSolrRequest requestSolr = new ConsultaSolrRequest();
    requestSolr.setFechaDesde(this.fechaDesde.getValue());
    requestSolr.setFechaHasta(this.fechaHasta.getValue());
    if (this.selectedTipoDocumento != null) {
      requestSolr.setTipoDocAcr(this.selectedTipoDocumento.getAcronimo());
      requestSolr.setTipoDocDescr(this.selectedTipoDocumento.getNombre());
    }
    if (this.seleccionarMetodo().equals("buscarDocumentoPorMetadatosPorUsuario")) {
      requestSolr.setUsuarioGenerador(userName);
    } else {
      Usuario datosUsuario = null;
      // cacheUser
      datosUsuario = Utilitarios.obtenerUsuarioActual();
      requestSolr.setReparticion(datosUsuario.getCodigoReparticion());
    }

    if (this.usuarioFirmante.getSelectedItem() != null) {
      // cacheUser
      Usuario usuario = this.usuarioService.obtenerUsuario(
          ((Usuario) this.usuarioFirmante.getSelectedItem().getValue()).getUsername());
      requestSolr.setUsuarioFirmante(usuario.getUsername());

    }

    for (MetaDocumentoDTO metaData : this.listaDocMetadata) {
      switch (metaData.getTipoConsulta()) {
      case LEGACY_CONSULTA:
        requestSolr.addLegacyField(metaData.getNombre(), (String) metaData.getValor());
        break;
      case CONTAIN_CONSULTA:
        requestSolr.addContainsDynamicField(metaData.getNombre(), (String) metaData.getValor());
        break;
      default:
        requestSolr.addDynamicField(metaData.getNombre(), metaData.getValor());
        break;
      }
    }
    requestSolr.setTipoBusqueda(this.seleccionarMetodo());
    return requestSolr;
  }

  protected abstract String seleccionarMetodo();

  public void onClick$cancelar() {
    ((Window) this.self).onClose();
  }

  public void onCheck$mostrar() {
    if (this.mostrar.isChecked()) {
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.familia, false));
    } else {
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.familia, true));
    }
  }

  public void onSelect$comboMetaFc() {
    Component comp = valueFcComponent(this.selectedMetaFc.getNombre());
    divFc.getChildren().clear();
    divFc.appendChild(comp);
    agregarFc.setVisible(true);
  }

  private Component valueFcComponent(String nombre) {

    Component comp = this.manager.getComponent(nombre);
    if (comp instanceof HtmlBasedComponent) {
      HtmlBasedComponent htmlComp = (HtmlBasedComponent) comp;
      htmlComp.setWidth("200px");
    }
    if (comp instanceof Constrainted) {
      Constrainted constrComp = (Constrainted) comp;
      constrComp.setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));
    }
    return comp;
  }

  public void onCheck$mostrarTodos() {
    if (this.mostrarTodos.isChecked()) {
      familiaEstructuraTree.setDisabled(true);
      familiaEstructuraTree.invalidate();
    } else {
      familiaEstructuraTree.setDisabled(false);
    }
  }

  @SuppressWarnings("unchecked")
  public void onSelectTipoDocumento() {
    List<MetadataDTO> modelCombo = (List<MetadataDTO>) this.nombreMetadato.getModel();
    this.listaMetadata = selectedTipoDocumento.getListaDatosVariables();
    modelCombo.clear();
    modelCombo.addAll(listaMetadata);
    this.agregaMetaDataHbox.setVisible(listaMetadata.size() > 0);
    this.binder.loadComponent(this.nombreMetadato);

    this.listaDocMetadata = (List<MetaDocumentoDTO>) this.grillaValoresAgregados.getModel();
    this.listaDocMetadata.clear();
    this.grillaValoresAgregados.setRowRenderer(new ValoresMetadatosRenderer());
    this.grillaValoresAgregados.setVisible(false);
    this.binder.loadComponent(this.grillaValoresAgregados);

    cargarComboFc();
  }

  @SuppressWarnings("unchecked")
  private void cargarComboFc() {
    List<CampoBusqueda> modelComboFc = (List<CampoBusqueda>) this.comboMetaFc.getModel();
    modelComboFc.clear();
    List<CampoBusqueda> campoBusq = obtenerCamposBusquedaFC();
    if (!campoBusq.isEmpty()) {
      this.listaMetaFc = campoBusq;
      modelComboFc.addAll(listaMetaFc);
      this.agregarFc.setDisabled(false);
      this.comboMetaFc.setDisabled(false);
      this.agregaDataFcHlayout.setVisible(true);
    } else {
      this.agregaDataFcHlayout.setVisible(false);
    }
    this.binder.loadComponent(this.comboMetaFc);
  }

  private String obtenerIdFormulario() {
    TipoDocumentoTemplateDTO ultimoTemplatePorTipoDocumento = this.procesamientoTemplate
        .obtenerUltimoTemplatePorTipoDocumento(this.selectedTipoDocumento);
    if (ultimoTemplatePorTipoDocumento != null) {
      return ultimoTemplatePorTipoDocumento.getIdFormulario();
    } else {
      return null;
    }
  }

  private List<CampoBusqueda> obtenerCamposBusquedaFC() {
    List<CampoBusqueda> campoBusqs = new ArrayList<CampoBusqueda>();
    String formName = obtenerIdFormulario();
    if (formName != null) {
      this.manager = formManagerFact.create(formName);
      for (CampoBusqueda campoBusqueda : manager.searchFields()) {
        if (campoBusqueda.getRelevanciaBusqueda() > 0) {
          campoBusqs.add(campoBusqueda);
        }
      }
    }
    return campoBusqs;
  }

  @SuppressWarnings("unchecked")
  public void onClick$agregar() {

    if (selectedMetadata != null) {
      String nuevo_valor = this.nuevo_valor.getValue();
      String nombre = this.selectedMetadata.getNombre();

      if (nuevo_valor == null || nuevo_valor.trim().equals("")) {
        throw new WrongValueException(this.nuevo_valor, "El nombre no puede ser vacío.");
      }

      filtroRepetido(nombre);

      this.grillaValoresAgregados.setVisible(true);
      this.listaDocMetadata = (List<MetaDocumentoDTO>) grillaValoresAgregados.getModel();
      MetaDocumentoDTO newMetadata = new MetaDocumentoDTO();
      newMetadata.setNombre(nombre);
      newMetadata.setValor(nuevo_valor);
      newMetadata.setTipoConsulta(TipoConsulta.LEGACY_CONSULTA);

      this.nuevo_valor.setValue(null);

      listaDocMetadata.add(newMetadata);
      refreshGrid();
    }
  }

  private void filtroRepetido(String nombre) {
    for (MetaDocumentoDTO metadata : this.listaDocMetadata) {
      if (metadata.getNombre().equals(nombre)) {
        throw new WrongValueException("Los nombres de los valores no pueden repetirse.");
      }
    }
  }

  public void onClick$agregarFc() {

    if (selectedMetaFc != null) {

      String nombre = this.selectedMetaFc.getNombre();
      InputComponent comp = (InputComponent) divFc.getFirstChild();

      // Validaciones
      comp.getText();
      filtroRepetido(nombre);

      MetaDocumentoDTO newMetadata = new MetaDocumentoDTO();
      newMetadata.setNombre(nombre);
      newMetadata.setValor(comp.getRawValue());
      if (comp instanceof TextboxExt) {
        newMetadata.setTipoConsulta(TipoConsulta.CONTAIN_CONSULTA);
      } else {
        newMetadata.setTipoConsulta(TipoConsulta.IS_CONSULTA);
      }
      // Reinicio el valor del componente
      comp.setRawValue(null);

      listaDocMetadata.add(newMetadata);
      refreshGrid();
    }
  }

  public void refreshGrid() {
    if (this.listaDocMetadata.size() >= 5) {
      this.agregar.setDisabled(true);
      this.agregarFc.setDisabled(true);
      this.comboMetaFc.setDisabled(true);
    }

    this.grillaValoresAgregados.setVisible(true);

    this.binder.loadComponent(this.grillaValoresAgregados);
  }

  public List<MetaDocumentoDTO> getListaDocMetadata() {
    return listaDocMetadata;
  }

  public void setListaDocMetadata(List<MetaDocumentoDTO> listaDocMetadata) {
    this.listaDocMetadata = listaDocMetadata;
  }

  public void setListaMetadata(List<MetadataDTO> listaMetadata) {
    this.listaMetadata = listaMetadata;
  }

  public List<MetadataDTO> getListaMetadata() {
    return listaMetadata;
  }

  public TipoDocumentoDTO getSelectedTipoDocumento() {
    return selectedTipoDocumento;
  }

  public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public MetadataDTO getSelectedMetadata() {
    return selectedMetadata;
  }

  public void setSelectedMetadata(MetadataDTO selectedMetadata) {
    this.selectedMetadata = selectedMetadata;
  }

  public List<CampoBusqueda> getListaMetaFc() {
    return listaMetaFc;
  }

  public void setListaMetaFc(List<CampoBusqueda> listaMetaFc) {
    this.listaMetaFc = listaMetaFc;
  }

  public CampoBusqueda getSelectedMetaFc() {
    return selectedMetaFc;
  }

  public void setSelectedMetaFc(CampoBusqueda selectedMetaFc) {
    this.selectedMetaFc = selectedMetaFc;
  }

  public void cargarTipoDocumento(TipoDocumentoDTO data) {
    this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
    this.familiaEstructuraTree.close();
    this.selectedTipoDocumento = data;
    onSelectTipoDocumento();
  }
}

final class ConsultasComposerListener implements EventListener {
  private ConsultasComposer composer;

  public ConsultasComposerListener(ConsultasComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {

    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
        this.composer.cargarTipoDocumento(data);
      }
    }
  }
}