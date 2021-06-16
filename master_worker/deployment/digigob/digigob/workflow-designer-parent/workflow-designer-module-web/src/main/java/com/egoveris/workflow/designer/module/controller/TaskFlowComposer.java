/**
 *
 */
package com.egoveris.workflow.designer.module.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;
import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class TaskFlowComposer extends AppComposer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TaskFlowComposer.class);

	private static final long serialVersionUID = 7709729990479038638L;
	private List<ResponseTipoDocumento> tiposDocumentos;
	private IExternalTipoDocumentoService tipoDocumentoService;
	private ExternalFormularioService formularioService;
	private IFormManager<Component> manager;
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	private TramitacionHelper tramitacionHelper;
	@Autowired
	private Combobox stateTipoDocumentoFFCC;
	@Autowired
	private Window info;


	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("doAfterCompose(Component) - start"); //$NON-NLS-1$
		}
		super.doAfterCompose(comp);
		super.setWindow(info);
		tipoDocumentoService = (IExternalTipoDocumentoService) SpringUtil.getBean("tipoDocumentoService");
		formularioService = (ExternalFormularioService) SpringUtil.getBean("externalFormularioService");
		stateTipoDocumentoFFCC = ZkUtil.findById(info, "stateTipoDocumentoFFCC");
		onOpenCombo();
		if (logger.isDebugEnabled()) {
			logger.debug("doAfterCompose(Component) - end"); //$NON-NLS-1$
		}
	
	}
	


	public void onSaveModel(final Event event) {
		super.onSaveModel(event, TypeWorkFlow.TASK);
		Messagebox.show(Labels.getLabel("msg.wfTaskGuardado"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
		event.stopPropagation();
	}


	public void onOpenCombo() {
		if (logger.isDebugEnabled()) {
			logger.debug("onOpenCombo() - start"); //$NON-NLS-1$
		}

		if (this.stateTipoDocumentoFFCC.getChildren().isEmpty()) {
			Clients.showBusy(this.info, "Obteniendo tipos de documentos");

			final AnnotateDataBinder binder = new AnnotateDataBinder();
			setTiposDocumentos(getTipoDocumentos());

			Comboitem item = new Comboitem();
			item.setValue(null);
			item.setLabel("Seleccione...");
			item.setDescription("Debe seleccionar un tipo de documento.");
			this.stateTipoDocumentoFFCC.appendChild(item);

			for (final ResponseTipoDocumento tdoc : getTipoDocumentos()) {
				item = new Comboitem();
				item.setValue(tdoc);
				item.setLabel(tdoc.getAcronimo());
				item.setDescription(tdoc.getDescripcion().trim());

				this.stateTipoDocumentoFFCC.appendChild(item);
			}

			binder.loadComponent(this.stateTipoDocumentoFFCC);
			Clients.clearBusy(this.info);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onOpenCombo() - end"); //$NON-NLS-1$
		}
	}

	public List<ResponseTipoDocumento> getTipoDocumentos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentos() - start"); //$NON-NLS-1$
		}

		if (tiposDocumentos == null) {
			try {
				tiposDocumentos = new ArrayList<>();
				List<ResponseTipoDocumento> tdocs;
				tdocs = getTipoDocumentoService().getDocumentTypeByProduction(ProductionEnum.TEMPLATE);
				if (tdocs != null && !tdocs.isEmpty()) {
					tiposDocumentos.addAll(tdocs);
				}
			} catch (final Exception e) {
				logger.warn("getTipoDocumentos() - exception ignored", e); //$NON-NLS-1$

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentos() - end"); //$NON-NLS-1$
		}
		return tiposDocumentos;
	}

	public ResponseTipoDocumento findTipoDocumentoByIdFormulario(final String idFormulario) {
		if (logger.isDebugEnabled()) {
			logger.debug("findTipoDocumentoByIdFormulario(String) - start"); //$NON-NLS-1$
		}

		for (final ResponseTipoDocumento tdoc : getTipoDocumentos()) {
			if (tdoc.getIdFormulario().equalsIgnoreCase(idFormulario)) {
				return tdoc;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("findTipoDocumentoByIdFormulario(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	public void onCargarCamposFFCC(Event e) {
		if (logger.isDebugEnabled()) {
			logger.debug("onCargarCamposFFCC() - start"); //$NON-NLS-1$
		}

		if (getStateTipoDocumentoFFCC().getValue() != null && !getStateTipoDocumentoFFCC().getValue().isEmpty()
				&& getStateTipoDocumentoFFCC().getSelectedItem() != null
				&& getStateTipoDocumentoFFCC().getSelectedItem().getValue() != null) {
			String ffccname = "";
			try {
				final ResponseTipoDocumento tipoDocumento = (ResponseTipoDocumento) getStateTipoDocumentoFFCC()
						.getSelectedItem().getValue();
				ffccname = tipoDocumento.getIdFormulario();
				final FormularioWDDTO formulario = getFormularioService().buscarFormularioPorNombreWD(ffccname);
				Gson gson = new Gson();
				final String jsonFFCC = gson.toJson(formulario);
				Clients.evalJavaScript("agregarCamposFFCC(" + jsonFFCC + ");");
			} catch (final Exception ex) {
				logger.error("onCargarCamposFFCC()", ex); //$NON-NLS-1$
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onCargarCamposFFCC() - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void onRealizarPase(final Event event) {
		final List<String> destinies = getDesignerService().fromJson((String) event.getData(), List.class);
		RealizarPase.show(null, destinies, null);
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$previsualizarButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("onClick$previsualizarButton() - start"); //$NON-NLS-1$
		}

		if (getStateTipoDocumentoFFCC().getValue() != null && !getStateTipoDocumentoFFCC().getValue().isEmpty()
				&& getStateTipoDocumentoFFCC().getSelectedItem() != null
				&& getStateTipoDocumentoFFCC().getSelectedItem().getValue() != null) {

			String ffccname = "";
			try {
				final ResponseTipoDocumento tipoDocumento = (ResponseTipoDocumento) getStateTipoDocumentoFFCC()
						.getSelectedItem().getValue();
				try {
					ffccname = tipoDocumento.getIdFormulario();
					formManagerFact = (IFormManagerFactory<IFormManager<Component>>) SpringUtil
							.getBean("zkFormManagerFactory");
					setManager(getFormManagerFact().create(ffccname));
				} catch (final Exception e) {
					logger.error("onClick$previsualizarButton()", e); //$NON-NLS-1$
					Messagebox.show(Labels.getLabel("msg.errorFFCC") + ffccname + "]", Labels.getLabel("msg.atencion"),
							Messagebox.OK, Messagebox.ERROR, new EventListener<Event>() {
								@Override
								public void onEvent(final Event event) throws Exception {
									if (logger.isDebugEnabled()) {
										logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
									}

									logger.error("Formulario Controlado no disponible");

									if (logger.isDebugEnabled()) {
										logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
									}
								}
							});
				}

				if (getManager() == null) {
					Messagebox.show(Labels.getLabel("msg.errorFFCC") + ffccname + "]", Labels.getLabel("msg.atencion"),
							Messagebox.OK, Messagebox.ERROR);

					if (logger.isDebugEnabled()) {
						logger.debug("onClick$previsualizarButton() - end"); //$NON-NLS-1$
					}
					return;
				}

				final Component comp = getManager().getFormComponent();

				final Window win = ZkUtil.createComponent("/pantallas/PrevisualizarFFCC.zul", null, null);
				final Div ffccContainer = ZkUtil.findById(win, "previsualizarFFCCDiv");
				comp.setParent(ffccContainer);
				win.setTitle(String.format("Previsualización del Formulario Controlado (%s)", ffccname));
				win.setVisible(true);
				win.doModal();
			} catch (final Exception e) {
				logger.error("onClick$previsualizarButton()", e); //$NON-NLS-1$
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onClick$previsualizarButton() - end"); //$NON-NLS-1$
		}
	}

	public void onNewProject(final Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onNewProject(Event) - start"); //$NON-NLS-1$
		}

		editar(null);
		event.stopPropagation();

		if (logger.isDebugEnabled()) {
			logger.debug("onNewProject(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onNuevoProyecto(final Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onNuevoProyecto(Event) - start"); //$NON-NLS-1$
		}

		editar(null);
		event.stopPropagation();

		if (logger.isDebugEnabled()) {
			logger.debug("onNuevoProyecto(Event) - end"); //$NON-NLS-1$
		}
	}

	public void editar(final Project project) {
		if (logger.isDebugEnabled()) {
			logger.debug("editar(Project) - start"); //$NON-NLS-1$
		}

		super.editar(project, TypeWorkFlow.TASK);

		if (logger.isDebugEnabled()) {
			logger.debug("editar(Project) - end"); //$NON-NLS-1$
		}
	}

	public void onShow() {
		if (logger.isDebugEnabled()) {
			logger.debug("onShow() - start"); //$NON-NLS-1$
		}

		final Window win = ZkUtil.createComponent("/pantallas/ConfirmacionGuardar.zul", null, null);
		final Button btnGuardar = ZkUtil.findById(win, "btnGuardar");
		final Button btnCancelar = ZkUtil.findById(win, "btnCancelar");
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
				}

				Clients.evalJavaScript("saveModel();");
				Clients.evalJavaScript("firstProject = false");
				editar(null, TypeWorkFlow.TASK);
				win.detach();

				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
				}
			}
		});
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
				}

				win.detach();

				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
				}
			}
		});
		win.doModal();

		if (logger.isDebugEnabled()) {
			logger.debug("onShow() - end"); //$NON-NLS-1$
		}
	}

	public void onMakePackageOnWebDav(final Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageOnWebDav(Event) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject o = (JSONObject) new JSONParser().parse((String) event.getData());
			Project proj = getDesignerService().fromJson(o.get("project").toString(), Project.class);
			
			super.onMakePackageOnWebDav(proj, o, TypeWorkFlow.TASK);
			Messagebox.show(Labels.getLabel("msg.wfTaskGenerado"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
			
		} catch (Exception e) {
			logger.error("Error generate jar", e);
			Messagebox.show(Labels.getLabel("msg.errorJar"), "Error", Messagebox.OK, Messagebox.ERROR);
		}
		event.stopPropagation();
		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageOnWebDav(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onAbrirProyecto(final Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onAbrirProyecto(Event) - start"); //$NON-NLS-1$
		}

		super.onAbrirProyecto(TypeWorkFlow.TASK);
		event.stopPropagation();

		if (logger.isDebugEnabled()) {
			logger.debug("onAbrirProyecto(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onSetIndex(final Event event) {
		final String ffcc = (String) event.getData();
		if (!StringUtils.isEmpty(ffcc)) {
			getStateTipoDocumentoFFCC().setValue(ffcc);
			getStateTipoDocumentoFFCC().setSelectedIndex(2);

		}
	}

	public void onAllComponentsForms(final Event event) {
		final Window win = ZkUtil.createComponent("pantallas/FormsFFCC.zul", null, null);
		Button btnAceptar = ZkUtil.findById(win, "btnAceptar");
		Button btnCancelar = ZkUtil.findById(win, "btnCancelar");

		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
				}
				win.detach();
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
				}
			}
		});

		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
				}
				
				String callFn = null;
				Combobox cmbxFields = ZkUtil.findById(win, "lstFields");
				Combobox cmbxCondition = ZkUtil.findById(win, "lstCondition");
				Combobox cmbxForms = ZkUtil.findById(win, "lstForms");
				Textbox tbxCompare = ZkUtil.findById(win, "tbxCompare");
				Radiogroup rg = ZkUtil.findById(win, "g1");
				Combobox cmbxTypes = ZkUtil.findById(win, "lstFieldType");
				if(rg.getSelectedItem() == null){
					Messagebox.show(Labels.getLabel("msg.seleccionarOpcion"), "Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				if(rg.getSelectedItem().getId().equalsIgnoreCase("ra1")){
					if(StringUtils.isNotBlank(cmbxForms.getValue())){
						callFn = String.format("validateStructure('%s');", cmbxForms.getValue());
					}
				} else if (rg.getSelectedItem().getId().equalsIgnoreCase("ra2")){
					String type = cmbxTypes.getValue();
					if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(cmbxFields.getValue())
							&& StringUtils.isNotBlank(cmbxCondition.getValue())
							&& StringUtils.isNotBlank(tbxCompare.getValue())){
							
							String format = "%s";
							if("Alfanumerico".equalsIgnoreCase(type)){
								format = "'%s'";
							}
							callFn = String.format("if(FFDD.get('%s') %s " + format + "){}", cmbxFields.getValue()  , 
								cmbxCondition.getValue(), tbxCompare.getValue());	
					}
					
				} else if(rg.getSelectedItem().getId().equalsIgnoreCase("ra3")){
					if(StringUtils.isNotBlank(cmbxFields.getValue()))
						callFn = String.format("FFDD.get('%s')", cmbxFields.getValue());
				}
				
				if(StringUtils.isNotBlank(callFn)){ 
					Clients.evalJavaScript(String.format("insertCmd(\"%s\");",callFn));
					win.detach();
				}  else {
					Messagebox.show(Labels.getLabel("msg.ingresarValores"), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				

				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
				}
			}
		});
		win.doModal();
	}

	public void onSelectServices(final Event event){
		super.onSelectServices(TypeWorkFlow.TASK);
		event.stopPropagation();
	}
	
	// GETTERS & SETTERS
	
	public List<ResponseTipoDocumento> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(final List<ResponseTipoDocumento> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public IExternalTipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	public void setTipoDocumentoService(final IExternalTipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}

	public Combobox getStateTipoDocumentoFFCC() {
		return stateTipoDocumentoFFCC;
	}

	public void setStateTipoDocumentoFFCC(final Combobox $stateTipoDocumentoFFCC) {
		this.stateTipoDocumentoFFCC = $stateTipoDocumentoFFCC;
	}

	public ExternalFormularioService getFormularioService() {
		return formularioService;
	}

	public void setFormularioService(final ExternalFormularioService formularioService) {
		this.formularioService = formularioService;
	}

	public IFormManager<Component> getManager() {
		return manager;
	}

	public void setManager(final IFormManager<Component> manager) {
		this.manager = manager;
	}

	public IFormManagerFactory<IFormManager<Component>> getFormManagerFact() {
		return formManagerFact;
	}

	public void setFormManagerFact(final IFormManagerFactory<IFormManager<Component>> formManagerFact) {
		this.formManagerFact = formManagerFact;
	}

	public TramitacionHelper getTramitacionHelper() {
		return tramitacionHelper;
	}

	public void setTramitacionHelper(final TramitacionHelper tramitacionHelper) {
		this.tramitacionHelper = tramitacionHelper;
	}

}
