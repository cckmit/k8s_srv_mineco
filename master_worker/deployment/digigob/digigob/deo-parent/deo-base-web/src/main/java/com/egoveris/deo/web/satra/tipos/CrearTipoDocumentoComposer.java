package com.egoveris.deo.web.satra.tipos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.GEDOServicesExceptions;
import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.ActuacionSADEBean;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoAuditoriaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplatePKDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.produccion.ABMTipoDocumentoTemplateComposer;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CrearTipoDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 9160241670607927537L;
  private static final Logger logger = LoggerFactory.getLogger(CrearTipoDocumentoComposer.class);
  private Window crearDocumentoWindow;
  private Window extensionesPermitidasWindow;
  private List<TipoDocumentoDTO> tiposDocumentos = null;
  private TipoDocumentoDTO selectedTipoDocumento = null;

  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  @WireVariable("tipoActuacionServiceImpl")
  private TipoActuacionService tipoActuacionService;
  @WireVariable("familiaTipoDocumentoServiceImpl")
  private FamiliaTipoDocumentoService familiaTipoDocumentoService;

  private TipoDocumentoDTO tipoDocumento = new TipoDocumentoDTO();
  private List<ActuacionSADEBean> actuacionesSADE;
  private ActuacionSADEBean selectedActuacionSADE;
  private List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas;
  private TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos;

  private Row filaTamanio;
  private Radio automatica;
  private Radio ambos;
  private Radio manual;
  private Radio libre;
  private Radio importado;
  private Radio template;
  private Radio importadoTemplate;
  private Checkbox tieneToken;
  private Checkbox esDobleFactor;
  private Checkbox esFirmaConjunta;
  private Checkbox esFirmaExterna;
  private Checkbox esFirmaExternaConEncabezado;
  private Checkbox tieneAviso;
  private Checkbox permiteEmbebidos;
  private Checkbox esOculto;
  private Checkbox esPublicable;
  private Checkbox esComunicable;
  private Checkbox esConfidencial;
  private Checkbox resultado;
  private Button cargarTemplate;
  private Window abmTipoDocumentoTemplateWindow;
  private Intbox tamanioMax;

  private Button tiposDeArchivo;
  @Autowired
  private Combobox familia;
  private List<String> listafamilias;
  @Autowired
  private FamiliaTipoDocumentoDTO selectedfamilia;
  private String selectedNombreFamilia;
  private String valueCkeditor = null;
  private String idFormularioControlado = null;
  private String descripcionTemplate = null;
  private Integer tamanioMaximo;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    tamanioMaximo = Integer.valueOf(appProperty.getString("gedo.maximoArchivos"))
        / Constantes.FACTOR_CONVERSION;

    this.crearDocumentoWindow.addEventListener(Events.ON_NOTIFY,
        new CrearTipoDocumentoComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new CrearTipoDocumentoComposerListener(this));
   
    this.listafamilias = new ArrayList<String>(familiaTipoDocumentoService.traerNombresFamilias());
    if(listafamilias != null) {
    	this.selectedNombreFamilia = this.listafamilias.get(0);
    }
    this.tieneAviso.setChecked(true);
    this.tiposDeArchivo.setDisabled(true);
    this.cargarTemplate.setDisabled(true);
    this.esFirmaExterna.setVisible(false);
    this.esFirmaExternaConEncabezado.setVisible(true);
    this.tamanioMax.setConstraint(new TamanioConstraint());
  }
  
	public void onCheck$tieneToken() {
		if (this.tieneToken.isChecked()) {
			this.tipoDocumento.setEsDobleFactor(false);
			this.tipoDocumento.setTieneToken(true);
		} else {
			this.tipoDocumento.setTieneToken(false);
		}
	}

	public void onCheck$esDobleFactor() {
		if (this.esDobleFactor.isChecked()) {
			this.tipoDocumento.setEsDobleFactor(true);
			this.tipoDocumento.setTieneToken(false);
		} else {
			this.tipoDocumento.setEsDobleFactor(false);
		}
	}
	
	public void onCheck$esPublicable() {
		if (this.esPublicable.isChecked()) {
			this.tipoDocumento.setEsPublicable(true);
		} else {
			this.tipoDocumento.setEsPublicable(false);
		}
	}

  public void onCheck$esFirmaExterna() {
    if (esFirmaExterna.isChecked()) {
      this.tieneToken.setDisabled(true);
      this.esDobleFactor.setDisabled(true);
      this.esFirmaConjunta.setDisabled(true);
      this.tipoDocumento.setTieneToken(false);
      this.tipoDocumento.setEsDobleFactor(false);
      this.tipoDocumento.setEsFirmaConjunta(false);
      this.tipoDocumento.setPermiteEmbebidos(false);
      this.permiteEmbebidos.setChecked(false);
    } else {
      this.tieneToken.setDisabled(false);
      this.esDobleFactor.setDisabled(false);
      this.esFirmaConjunta.setDisabled(false);
    }
  }

  public void onCheck$esFirmaExternaConEncabezado() {
    if (esFirmaExternaConEncabezado.isChecked()) {
      this.tieneToken.setDisabled(true);
      this.esDobleFactor.setDisabled(true);
      this.esFirmaConjunta.setDisabled(true);
      this.tipoDocumento.setTieneToken(false);
      this.tipoDocumento.setEsDobleFactor(false);
      this.tipoDocumento.setEsFirmaConjunta(false);
      this.tipoDocumento.setPermiteEmbebidos(false);
      this.permiteEmbebidos.setChecked(false);
    } else {
      this.tieneToken.setDisabled(false);
      this.esDobleFactor.setDisabled(false);
      this.esFirmaConjunta.setDisabled(false);
    }
  }

  public void onCheck$esComunicable() {
    if (esComunicable.isChecked()) {
      tipoDocumento.setEsConfidencial(false);
      this.esConfidencial.setDisabled(true);
      this.tieneAviso.setDisabled(true);
      tipoDocumento.setTieneAviso(false);
      this.esOculto.setDisabled(true);
      tipoDocumento.setEsOculto(false);
    } else {
      this.esConfidencial.setDisabled(false);
      this.tieneAviso.setDisabled(false);
      this.esOculto.setDisabled(false);
    }
  }

  /**
   * El check del radioButton Importado, dara a visualizar el checkBox
   * esFirmaExterna.
   */
  public void onCheck$importado() {
    if (this.importado.isChecked()) {
      this.filaTamanio.setVisible(true);
      this.esFirmaExterna.setChecked(false);
      this.esFirmaExterna.setVisible(true);
      this.esFirmaExternaConEncabezado.setChecked(false);
      this.esFirmaExternaConEncabezado.setVisible(false);
      this.tiposDeArchivo.setDisabled(true);
      this.cargarTemplate.setDisabled(true);
      this.permiteEmbebidos.setVisible(true);
      this.permiteEmbebidos.setChecked(false);
    }
  }

  /**
   * El check del radioButton Libre, dara a visualizar el checkBox
   * esFirmaExterna.
   */
  public void onCheck$libre() {
    if (this.libre.isChecked()) {
      this.filaTamanio.setVisible(false);
      this.esFirmaExterna.setChecked(false);
      this.esFirmaExterna.setVisible(false);
      this.esFirmaExternaConEncabezado.setChecked(false);
      this.esFirmaExternaConEncabezado.setVisible(true);
      this.permiteEmbebidos.setVisible(true);
      this.permiteEmbebidos.setChecked(false);
      this.cargarTemplate.setDisabled(true);
      this.tiposDeArchivo.setDisabled(true);
    }
  }

  /**
   * El check del radioButton Libre, dara a visualizar el checkBox
   * esFirmaExterna.
   */
  public void onCheck$template() {
    if (this.template.isChecked()) {
      this.filaTamanio.setVisible(false);
      this.esFirmaExterna.setChecked(false);
      this.esFirmaExterna.setVisible(false);
      this.esFirmaExternaConEncabezado.setChecked(false);
      this.esFirmaExternaConEncabezado.setVisible(false);
      this.permiteEmbebidos.setVisible(true);
      this.permiteEmbebidos.setChecked(false);
      this.cargarTemplate.setDisabled(false);
      this.tiposDeArchivo.setDisabled(true);
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_TEMPLATE);
    }
  }

  public void onCheck$importadoTemplate() {
    if (this.importadoTemplate.isChecked()) {
      this.filaTamanio.setVisible(true);
      this.esFirmaExterna.setChecked(false);
      this.esFirmaExterna.setVisible(false);
      this.esFirmaExternaConEncabezado.setChecked(false);
      this.esFirmaExternaConEncabezado.setVisible(false);
      this.permiteEmbebidos.setVisible(true);
      this.permiteEmbebidos.setChecked(false);
      this.cargarTemplate.setDisabled(false);
      this.tiposDeArchivo.setDisabled(true);
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE);
    }
  }

  public void onCheck$permiteEmbebidos() {
    this.tipoDocumento.setPermiteEmbebidos(this.permiteEmbebidos.isChecked());
    if (this.permiteEmbebidos.isChecked()) {
      this.tiposDeArchivo.setDisabled(false);
    } else {
      this.tiposDeArchivo.setDisabled(true);
      if (listaExtensionesPermitidas != null && listaExtensionesPermitidas.size() > 0) {
        this.listaExtensionesPermitidas.clear();
      }
    }
  }

  public void onClick$tiposDeArchivo() {
    try {
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("listaExtensiones", this.listaExtensionesPermitidas);
      map.put("tipoDocumento", this.tipoDocumento);
      this.extensionesPermitidasWindow = (Window) Executions
          .createComponents("/inbox/extensionesPermitdasTipoDoc.zul", this.self, map);
      this.extensionesPermitidasWindow.setClosable(true);
      this.extensionesPermitidasWindow.setMaximizable(true);
      this.extensionesPermitidasWindow.doModal();
    } catch (Exception e) {
      logger.error("Mensaje de error", e);
    }
  }

  public void onGuardarTipoDocumento() throws InterruptedException {

    validarParametros();

    if (this.automatica.isChecked()) {
      this.tipoDocumento.setEsManual(false);
      this.tipoDocumento.setEsAutomatica(true);
    } else if (this.ambos.isChecked()) {
      this.tipoDocumento.setEsManual(true);
      this.tipoDocumento.setEsAutomatica(true);
    } else if (this.manual.isChecked()) {
      this.tipoDocumento.setEsManual(true);
      this.tipoDocumento.setEsAutomatica(false);
    }

    if (this.libre.isChecked()) {
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_LIBRE);
      this.tipoDocumento.setTieneTemplate(false);
      this.tipoDocumento.setListaTemplates(new HashSet<TipoDocumentoTemplateDTO>());
    } else if (this.importado.isChecked()) {
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_IMPORTADO);
      this.tipoDocumento.setTieneTemplate(false);
      this.tipoDocumento.setListaTemplates(new HashSet<TipoDocumentoTemplateDTO>());
      this.tipoDocumento.setTipoDocumentoEmbebidos(new HashSet<TipoDocumentoEmbebidosDTO>());
      this.tipoDocumento.setSizeImportado(tamanioMax.getValue() * Constantes.FACTOR_CONVERSION);
    } else if (this.template.isChecked()) {
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_TEMPLATE);
      this.setTemplateTipoDoc();
    } else if (this.importadoTemplate.isChecked()) {
      this.tipoDocumento.setTipoProduccion(Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE);
      this.setTemplateTipoDoc();
      this.tipoDocumento.setSizeImportado(tamanioMax.getValue() * Constantes.FACTOR_CONVERSION);
    }

    this.tipoDocumento.setEsFirmaExterna(this.esFirmaExterna.isChecked());
    this.tipoDocumento
        .setEsFirmaExternaConEncabezado(this.esFirmaExternaConEncabezado.isChecked());
    this.tipoDocumento.setResultado(this.resultado.isChecked());
    
    this.tipoDocumento.setPermiteEmbebidos(this.permiteEmbebidos.isChecked());
    this.tipoDocumento.setEsComunicable(this.esComunicable.isChecked());
    this.tipoDocumento.setIdTipoDocumentoSade(this.selectedActuacionSADE.getId());
    this.tipoDocumento.setCodigoTipoDocumentoSade(this.selectedActuacionSADE.getCodigo());
    this.tipoDocumento.setFamilia(this.selectedfamilia);
    this.tipoDocumento.setEsOculto(this.esOculto.isChecked());

    String username = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);
    this.tipoDocumento.setUsuarioCreador(username);
    this.tipoDocumento.setFechaCreacion((Date) Calendar.getInstance().getTime());
    if ((listaExtensionesPermitidas == null || listaExtensionesPermitidas.size() == 0)
        || (!this.permiteEmbebidos.isChecked())) {
      this.tipoDocumento.setTipoDocumentoEmbebidos(new HashSet<TipoDocumentoEmbebidosDTO>());
    } else {
      Set<TipoDocumentoEmbebidosDTO> setExtensionesPermitidas = new HashSet<TipoDocumentoEmbebidosDTO>(
          listaExtensionesPermitidas);
      this.tipoDocumento.setTipoDocumentoEmbebidos(setExtensionesPermitidas);
    }
    this.tipoDocumento.setVersion("1.0");

    try {
      if (!tipoDocumentoService.existsAcronymDocumentType(tipoDocumento.getAcronimo())) {
        TipoDocumentoAuditoriaDTO tipoDocumen = tipoDocumentoService.crearTipoDocumento(this.tipoDocumento, username);
        tipoDocumentoService.saveTipoDocumento(tipoDocumen);
        Messagebox.show(
            Labels.getLabel("gedo.nuevoDocumento.nuevoTipoCreado", new String[] { this.tipoDocumento.getNombre() }),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        this.closeAndNotifyAssociatedWindow(null);
      }
      else {
        Messagebox.show(
            Labels.getLabel("gedo.nuevoDocumento.acronimoRepetido", new String[] { this.tipoDocumento.getAcronimo() }),
            Labels.getLabel("gedo.inbox.error.title"), Messagebox.OK, Messagebox.ERROR);
      }
    } catch (GEDOServicesExceptions e) {
      logger.error("Error al guardar tipo de documento " + e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("gedo.abmTipoDocumento.informacion"),
          Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  private void setTemplateTipoDoc() {
    this.tipoDocumento.setTieneTemplate(true);
    this.tipoDocumento.setTipoDocumentoEmbebidos(new HashSet<TipoDocumentoEmbebidosDTO>());

    if (this.tipoDocumento.getListaTemplates().isEmpty()) {
      TipoDocumentoTemplateDTO tipoDocumentoTemplate = new TipoDocumentoTemplateDTO();
      TipoDocumentoTemplatePKDTO tipoDocumentoTemplatePK = new TipoDocumentoTemplatePKDTO();

      tipoDocumentoTemplatePK.setVersion(1);
      tipoDocumentoTemplate.setTipoDocumentoTemplatePK(tipoDocumentoTemplatePK);
      tipoDocumentoTemplate.setDescripcion(this.getDescripcionTemplate());
      tipoDocumentoTemplate.setUsuarioAlta(super.execution.getUserPrincipal().getName());
      tipoDocumentoTemplate.setTemplate(this.getValueCkeditor());
      tipoDocumentoTemplate.setIdFormulario(this.getIdFormularioControlado());
      tipoDocumentoTemplate.setTipoDocumento(this.tipoDocumento);
      tipoDocumentoTemplate.setFechaCreacion(new Date());
      this.tipoDocumento.getListaTemplates().add(tipoDocumentoTemplate);
    }
  }

  public void onSelect$familia(ForwardEvent event) {
	Combobox cmb = (Combobox) event.getOrigin().getTarget();
	if(cmb != null) {
		logger.info("Selected Item for Familia: " + cmb.getValue());
	    this.selectedfamilia = this.familiaTipoDocumentoService
	        .traerFamiliaTipoDocumentoByNombre(cmb.getValue());
	}
  }

  public void onCargarTemplate() {
    Map<String, Object> hm = new HashMap<>();
    if (this.getValueCkeditor() != null && this.getIdFormularioControlado() != null) {
      hm.put("modo", ABMTipoDocumentoTemplateComposer.MODO_MODIFICACION_TEMPLATE);
      hm.put("datos", this.getValueCkeditor());
      hm.put("idFormularioControlado", this.getIdFormularioControlado());
      hm.put("descripcionTemplate", this.getDescripcionTemplate());
    } else {
      hm.put("modo", ABMTipoDocumentoTemplateComposer.MODO_ALTA_TEMPLATE);
      hm.put("tipoDocumento", this.tipoDocumento);
    }
    this.abmTipoDocumentoTemplateWindow = (Window) Executions
        .createComponents("abmTipoDocumentoTemplate.zul", this.self, hm);
    this.abmTipoDocumentoTemplateWindow.setClosable(true);
    this.abmTipoDocumentoTemplateWindow.addEventListener(Events.ON_NOTIFY,
        new CrearTipoDocumentoComposerListener(this));

    this.abmTipoDocumentoTemplateWindow.doModal();
  }

  private void validarParametros() {
    if (this.tipoDocumento.getNombre() == null || this.tipoDocumento.getAcronimo() == null
        || StringUtils.isEmpty(this.tipoDocumento.getDescripcion())) {
      throw new WrongValueException(
          Labels.getLabel("gedo.crearTipoDocComposer.exception.completeRecuadrosVacios"));
    }
    if (this.selectedActuacionSADE == null) {
      throw new WrongValueException(Labels.getLabel("gedo.abmTipoDocumento.errorActuacionSade"));
    }
    if (this.familia.getValue().trim().equals("")) {
      throw new WrongValueException(this.familia,
          Labels.getLabel("gedo.crearTipoDocComposer.exception.ingreseFamilia"));
    }
    if (this.permiteEmbebidos.isChecked()) {
      if (this.listaExtensionesPermitidas == null || this.listaExtensionesPermitidas.size() == 0) {
        throw new WrongValueException(this.tiposDeArchivo,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.ingreseExtencionPermitida"));
      }
    }
    if (this.template.isChecked() && this.getValueCkeditor() == null) {
      throw new WrongValueException(this.cargarTemplate,
          Labels.getLabel("gedo.abmTipoDocumento.errorNoTemplate"));
    }
    if (this.importadoTemplate.isChecked() && this.getValueCkeditor() == null) {
      throw new WrongValueException(this.cargarTemplate,
          Labels.getLabel("gedo.abmTipoDocumento.errorNoTemplate"));
    }
    if (selectedfamilia == null && familia.getValue() != null) {
      selectedfamilia = familiaTipoDocumentoService
          .traerFamiliaTipoDocumentoByNombre(familia.getValue());
    }

  }

  public Window getCrearDocumentoWindow() {
    return crearDocumentoWindow;
  }

  public void setCrearDocumentoWindow(Window crearDocumentoWindow) {
    this.crearDocumentoWindow = crearDocumentoWindow;
  }

  public List<TipoDocumentoEmbebidosDTO> getListaExtensionesPermitidas() {
    return listaExtensionesPermitidas;
  }

  public void setListaExtensionesPermitidas(
      List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas) {
    this.listaExtensionesPermitidas = listaExtensionesPermitidas;
  }

  public List<TipoDocumentoDTO> getTiposDocumentos() {
    return tiposDocumentos;
  }

  public void setTiposDocumentos(List<TipoDocumentoDTO> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
  }

  public TipoDocumentoDTO getSelectedTipoDocumento() {
    return selectedTipoDocumento;
  }

  public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public void onCancelar() throws InterruptedException {
    ((Window) this.self).onClose();
  }

  public void setActuacionesSADE(List<ActuacionSADEBean> actuacionesSADE) {
    this.actuacionesSADE = actuacionesSADE;
  }

  public List<ActuacionSADEBean> getActuacionesSADE() {
    if (this.actuacionesSADE == null) {
      this.actuacionesSADE = this.tipoActuacionService.obtenerListaTodasLasActuaciones();
    }
    return this.actuacionesSADE;
  }

  public void setSelectedActuacionSADE(ActuacionSADEBean selectedActuacionSADE) {
    this.selectedActuacionSADE = selectedActuacionSADE;
  }

  public ActuacionSADEBean getSelectedActuacionSADE() {
    return selectedActuacionSADE;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public TipoActuacionService getTipoActuacionService() {
    return tipoActuacionService;
  }

  public Combobox getFamilia() {
    return familia;
  }

  public void setFamilia(Combobox familia) {
    this.familia = familia;
  }

  public List<String> getListafamilias() {
    return listafamilias;
  }

  public void setListafamilias(List<String> listafamilias) {
    this.listafamilias = listafamilias;
  }

  public FamiliaTipoDocumentoService getFamiliaTipoDocumentoService() {
    return familiaTipoDocumentoService;
  }

  public void setFamiliaTipoDocumentoService(
      FamiliaTipoDocumentoService familiaTipoDocumentoService) {
    this.familiaTipoDocumentoService = familiaTipoDocumentoService;
  }

  public FamiliaTipoDocumentoDTO getSelectedfamilia() {
    return selectedfamilia;
  }

  public void setSelectedfamilia(FamiliaTipoDocumentoDTO selectedfamilia) {
    this.selectedfamilia = selectedfamilia;
  }

  public Radio getLibre() {
    return libre;
  }

  public void setLibre(Radio libre) {
    this.libre = libre;
  }

  public Radio getImportado() {
    return importado;
  }

  public void setImportado(Radio importado) {
    this.importado = importado;
  }

  public Radio getTemplate() {
    return template;
  }

  public void setTemplate(Radio template) {
    this.template = template;
  }

  public Button getCargarTemplate() {
    return cargarTemplate;
  }

  public void setCargarTemplate(Button cargarTemplate) {
    this.cargarTemplate = cargarTemplate;
  }

  public Window getProducirTemplateWindow() {
    return abmTipoDocumentoTemplateWindow;
  }

  public void setProducirTemplateWindow(Window producirTemplateWindow) {
    this.abmTipoDocumentoTemplateWindow = producirTemplateWindow;
  }

  public String getValueCkeditor() {
    return valueCkeditor;
  }

  public void setValueCkeditor(String valueCkeditor) {
    this.valueCkeditor = valueCkeditor;
  }

  public String getIdFormularioControlado() {
    return idFormularioControlado;
  }

  public void setIdFormularioControlado(String idFormularioControlado) {
    this.idFormularioControlado = idFormularioControlado;
  }

  public TipoDocumentoEmbebidosDTO getTipoDocumentoEmbebidos() {
    return tipoDocumentoEmbebidos;
  }

  public void setTipoDocumentoEmbebidos(TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos) {
    this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
  }

  public String getDescripcionTemplate() {
    return descripcionTemplate;
  }

  public void setDescripcionTemplate(String descripcionTemplate) {
    this.descripcionTemplate = descripcionTemplate;
  }

  public String getSelectedNombreFamilia() {
    return selectedNombreFamilia;
  }

  public void setSelectedNombreFamilia(String selectedNombreFamilia) {
    this.selectedNombreFamilia = selectedNombreFamilia;
  }

  public void setEsOculto(Checkbox esOculto) {
    this.esOculto = esOculto;
  }

  public Checkbox getEsOculto() {
    return esOculto;
  }

  public Checkbox getResultado() {
    return resultado;
  }

  public void setResultado(Checkbox resultado) {
    this.resultado = resultado;
  }

  private class TamanioConstraint implements Constraint {
    // Constraint//
    public void validate(Component comp, Object value) {
      if (value == null) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.vacioNoPermitido"));
      } else if (((Integer) value).intValue() <= 0) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.valorMayorCero"));
      } else if (((Integer) value).intValue() > tamanioMaximo) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.valorMenorMaximo") + "("
                + tamanioMaximo + ")");
      }
    }
  }
}

final class CrearTipoDocumentoComposerListener implements EventListener {
  private CrearTipoDocumentoComposer composer;

  public CrearTipoDocumentoComposerListener(CrearTipoDocumentoComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        Map<String, Object> map = (Map<String, Object>) event.getData();
        String origen = (String) map.get("origen");
        if (origen.equals(Constantes.EVENTO_ABM_DOCUMENTO_TEMPLATE)) {
          composer.setValueCkeditor((String) map.get("datos"));
          composer.setIdFormularioControlado((String) map.get("idFormularioControlado"));
          composer.setDescripcionTemplate((String) map.get("descripcionTemplate"));
        } else if (origen.equals(Constantes.EVENTO_TIPO_DOC_EMBEBIDOS)) {
          composer.setListaExtensionesPermitidas(
              (List<TipoDocumentoEmbebidosDTO>) map.get("extensiones"));
          composer.setTipoDocumentoEmbebidos(
              (TipoDocumentoEmbebidosDTO) map.get("tipoDocumentoEmbebidos"));
        }
      }
    }
  }
}
