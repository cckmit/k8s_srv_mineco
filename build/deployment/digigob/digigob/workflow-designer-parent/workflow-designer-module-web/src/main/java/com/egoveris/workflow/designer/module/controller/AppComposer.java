package com.egoveris.workflow.designer.module.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.json.JSONObject;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.workflow.designer.module.exception.DesginerException;
import com.egoveris.workflow.designer.module.exception.TransformerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.ProjectDesignerDTO;
import com.egoveris.workflow.designer.module.model.State;
import com.egoveris.workflow.designer.module.model.StateProperties;
import com.egoveris.workflow.designer.module.service.DesignerService;
import com.egoveris.workflow.designer.module.service.SubProcessService;
import com.egoveris.workflow.designer.module.service.TransformerService;
import com.egoveris.workflow.designer.module.service.WebDavIntService;
import com.egoveris.workflow.designer.module.util.DesignerUtil;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;
import com.google.gson.Gson;

public class AppComposer extends GenericForwardComposer<Component> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(AppComposer.class);
	private static final long serialVersionUID = 7709729990479038638L;
	private Window window;
	@Autowired
	private DesignerService designerService;
	@Autowired
	private WebDavIntService webDavService;
	@Autowired
	private TransformerService transformerService;
	private String userLogged;
	private Project project;
	public static final String SESSION_USERNAME = "username";
	private SubProcessService subProcesssService;
	@WireVariable("#chkValidate")
	private Checkbox chkValidate;
	private String projectName;
	private Map<String, String> initialSubProcess;
	private int versionProject;
	private String TYPE_BODY = "body";
	private String TYPE_QUERY = "query";
	private String HTML_BLANK_URL = "%20";

	/**
	 * @return the project
	 */
	public Project getProject() {
		if (logger.isDebugEnabled()) {
			logger.debug("getProject() - start"); //$NON-NLS-1$
		}

		if (project == null) {
			project = new Project();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getProject() - end"); //$NON-NLS-1$
		}
		return project;
	}

	/**
	 * @param project
	 *            the projecto to set
	 */
	public void setProject(Project projecto) {
		this.project = projecto;
	}

	/**
	 * @return the userLogged
	 */
	public String getUserLogged() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserLogged() - start"); //$NON-NLS-1$
		}

		if (userLogged == null) {
			userLogged = Executions.getCurrent().getRemoteUser();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUserLogged() - end"); //$NON-NLS-1$
		}
		return userLogged;
	}

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("doAfterCompose(Component) - start"); //$NON-NLS-1$
		}

		super.doAfterCompose(comp);
		transformerService =   (TransformerService) SpringUtil.getBean("transformerServiceImpl");
		designerService = (DesignerService) SpringUtil.getBean("designerServiceImpl");
		webDavService = (WebDavIntService) SpringUtil.getBean("webDavIntServiceImpl");
		subProcesssService = (SubProcessService) SpringUtil.getBean("subProcessServiceImpl");
		Clients.evalJavaScript("cargarToltip();");
		if (logger.isDebugEnabled()) {
			logger.debug("doAfterCompose(Component) - end"); //$NON-NLS-1$
		}
	}
	


	public void editar(Project project, TypeWorkFlow type) {
		if (logger.isDebugEnabled()) {
			logger.debug("editar(Project, TypeWorkFlow) - start"); //$NON-NLS-1$
		}

		setProject(project);
		try {
			final Window win = ZkUtil.createComponent("pantallas/NuevoProyecto.zul", null, null);
			Button btnAceptar = ZkUtil.findById(win, "btnAceptar");
			Button btnCancelar = ZkUtil.findById(win, "btnCancelar");

			Textbox autor = ZkUtil.findById(win, "autor");

			if (getUserLogged() != null && !getUserLogged().isEmpty()) {
				autor.setValue(getUserLogged());
			}

			btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
					}

					Clients.evalJavaScript("firstProject = false");
					win.detach();

					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});

			btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
					}

					actualizarInfoProyecto(win);

					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});

			win.doModal();
		} catch (UiException uiex) {
			logger.warn("editar(Project, TypeWorkFlow) - exception ignored", uiex); //$NON-NLS-1$
			Messagebox.show(Labels.getLabel("msg.errorCrear"), "Error", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("editar(Project, TypeWorkFlow) - end"); //$NON-NLS-1$
		}
	}


	public void actualizarVersionProyecto(String version) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarVersionProyecto(String) - start"); //$NON-NLS-1$
		}

		Label versionProyecto = ZkUtil.findById(window, "versionProyecto");
		versionProyecto.setValue(version);
		
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarVersionProyecto(String) - end"); //$NON-NLS-1$
		}
	}

	public void actualizarInfoProyecto(Window win) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarInfoProyecto(Window) - start"); //$NON-NLS-1$
		}

		Textbox nombre = ZkUtil.findById(win, "nombre");
		Textbox autor = ZkUtil.findById(win, "autor");
		Textbox descripcion = ZkUtil.findById(win, "descripcion");

		String nombreProyecto = nombre.getValue();
		if (nombreProyecto == null || nombreProyecto.trim().equals("")) {
			Messagebox.show(Labels.getLabel("msg.inidicarNombre"), Labels.getLabel("msg.verificarDatos"),
					Messagebox.OK, Messagebox.EXCLAMATION);
			if (logger.isDebugEnabled()) {
				logger.debug("actualizarInfoProyecto(Window) - end"); //$NON-NLS-1$
			}
			return;
		} else {
			getProject().setName(nombre.getValue());
			getProject().setAuthor(autor.getValue());
			getProject().setDescription(descripcion.getValue());
			final String jsonProject =designerService.toJson(getProject());
			logger.info("Json PROJECT --> " + jsonProject);
			Project existedProject =webDavService.findProjectByName(project.getName());
			if (existedProject != null) {
				Messagebox.show(Labels.getLabel("msg.proyectoExistente1") + getProject().getName() + Labels.getLabel("msg.proyectoExistente2"), 
						Labels.getLabel("msg.atencion"),
						Messagebox.OK + Messagebox.CANCEL, Messagebox.INFORMATION, new EventListener<Event>() {
							@Override
							public void onEvent(Event evt) throws Exception {
								if (logger.isDebugEnabled()) {
									logger.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
								}

								int btn = ((Integer) evt.getData()).intValue();
								if (Messagebox.OK == btn) {
									Clients.evalJavaScript("removeCells()");
									Clients.evalJavaScript("nuevoProyecto(" + jsonProject + ");");
								} else {
									Clients.evalJavaScript("firstProject = false;");
								}

								if (logger.isDebugEnabled()) {
									logger.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
								}
							}
						});
			} else {
				Clients.evalJavaScript("nuevoProyecto(" + jsonProject + ");");
			}
			win.detach();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarInfoProyecto(Window) - end"); //$NON-NLS-1$
		}
	}

	public void onAddState(Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onAddState(Event) - start"); //$NON-NLS-1$
		}

		State state =designerService.fromJson((String) event.getData(), State.class);
		getProject().getStates().add(state);

		if (logger.isDebugEnabled()) {
			logger.debug("onAddState(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onSaveModel(Event event, TypeWorkFlow type) {
		if (logger.isDebugEnabled()) {
			logger.debug("onSaveModel(Event) - start"); //$NON-NLS-1$
		}
		
		Project pr = designerService.fromJson((String) event.getData(), Project.class);
		pr.setTypeWorkFlow(type);
		setProject(pr);
		designerService.saveOrUpdateProject(pr);
		if (logger.isDebugEnabled()) {
			logger.debug("onSaveModel(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onSaveAsModel(final Event eventComposer) {
		if (logger.isDebugEnabled()) {
			logger.debug("onSaveAsModel(Event) - start"); //$NON-NLS-1$
		}

		
		try {
			final Window win = ZkUtil.createComponent("pantallas/NuevoProyecto.zul", null, null);
			win.setTitle("Guardar Como...");
			Button btnGuardar = ZkUtil.findById(win, "btnAceptar");
			btnGuardar.setLabel("Guardar");
			Button btnCancelar = ZkUtil.findById(win, "btnCancelar");
			final Textbox name = ZkUtil.findById(win, "nombre");
			final Textbox description = ZkUtil.findById(win, "descripcion");
			Textbox autor = ZkUtil.findById(win, "autor");

			if (getUserLogged() != null && !getUserLogged().isEmpty()) {
				autor.setValue(getUserLogged());
			}

			btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
					}

					Clients.evalJavaScript("firstProject = false");
					win.detach();

					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});

			btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
					}
					
					if(!StringUtils.isEmpty(name.getValue())
							 && !StringUtils.isEmpty(description.getValue()) ){
						String jsonString = (String) eventComposer.getData();
						Project proj = designerService.fromJson(jsonString, Project.class);
						final String nameOld = proj.getName();
						proj.setVersion(proj.getVersion());
						proj.setName(DesignerUtil.camelName(name.getValue()));
						proj.setDescription(description.getValue());
						setProject(proj);
						actualizarInfoProyecto(win);
						designerService.changeNameProject(proj, nameOld);
						if(proj.getTypeWorkFlow().equals(TypeWorkFlow.STATE)){
							subProcesssService.updateSubProcessProject(name.getValue(), nameOld , proj.getVersion());
						}
						Messagebox.show(Labels.getLabel("msg.wfGuardado"));
					} else {
						Messagebox.show(Labels.getLabel("msg.ingregarNomDesc"), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});
			win.doModal();
		} catch (UiException uiex) {
			logger.warn("onSaveAsModel(Event) - exception ignored", uiex); //$NON-NLS-1$
			Messagebox.show(Labels.getLabel("msg.errorGuardar"), "Error", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onSaveAsModel(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onUpload$importButton(UploadEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onUpload$importButton(UploadEvent) - start"); //$NON-NLS-1$
		}
		String[] parts = event.getMedia().getName().split("\\.");
		String fileExtension = parts[parts.length - 1];
		if (fileExtension.equalsIgnoreCase("json")) {
			try {
				if (event.getMedia().isBinary()) {
					String json = IOUtils.toString(event.getMedia().getStreamData());
					Clients.evalJavaScript("importarProyecto(" + json + ");");
				}
			} catch (IOException e) {
				logger.error("onUpload$importButton(UploadEvent)", e); //$NON-NLS-1$
			}
		} else {
			Messagebox.show(Labels.getLabel("msg.sinJson"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onUpload$importButton(UploadEvent) - end"); //$NON-NLS-1$
		}
	}

	public void onExportJSON(Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onExportJSON(Event) - start"); //$NON-NLS-1$
		}
		try {
			String json = (String) event.getData();
			String tempDir = System.getProperty("java.io.tmpdir");
			String projectName = DesignerUtil.camelName(getProject().getName());
			FileWriter fileWriter = new FileWriter(tempDir + projectName + ".json");
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
			FileInputStream fileInputStream = new FileInputStream(tempDir + projectName + ".json");
			Filedownload.save(fileInputStream, "application/json", projectName + ".json");
		} catch (IOException  e) {
			logger.error("onExportJSON(Event)", e); //$NON-NLS-1$
			Messagebox.show(Labels.getLabel("msg.errorExportar"), "Error", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onExportJSON(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onMakePackageModel(Event event) {
		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageModel(Event) - start"); //$NON-NLS-1$
		}

		Project project =designerService.fromJson((String) event.getData(), Project.class);
		setProject(project);
		updateWorkflowName(project);
		designerService.saveOrUpdateProject(project);
		actualizarVersionProyecto(Integer.toString(project.getVersion()));
		FileInputStream fis  = null;
		try {
			String jarFile = transformerService.createProjectJar(project);
			fis = new FileInputStream(new File(jarFile));
			Filedownload.save(fis, "application/java-archive", DesignerUtil.camelName(project.getName()) + ".jar");
		} catch (IOException | TransformerException  e) {
			logger.error("onMakePackageModel(Event)", e); //$NON-NLS-1$
			Messagebox.show(Labels.getLabel("msg.errorJar"), "Error", Messagebox.OK, Messagebox.ERROR);
		} 
		

		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageModel(Event) - end"); //$NON-NLS-1$
		}
	}

	public void onMakePackageOnWebDav(Project proj, JSONObject jobj, TypeWorkFlow type)
	throws DesginerException{
		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageOnWebDav(Event, TypeWorkFlow) - start"); //$NON-NLS-1$
		}
		try {
			project.setTypeWorkFlow(type);
			setProject(proj);
			updateWorkflowName(proj);
			designerService.saveOrUpdateProject(proj);
			actualizarVersionProyecto(Integer.toString(proj.getVersion()));
			String packageName = DesignerUtil.camelName(proj.getName()) + ".jar";
			designerService.saveJarOnWevDav(proj, packageName, jobj.get("nameSpace").toString());
		} catch (DesginerException e) {
			logger.error("onMakePackageOnWebDav(Event, TypeWorkFlow)", e); //$NON-NLS-1$
			throw new DesginerException("Error al generar archivo jar", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onMakePackageOnWebDav(Event, TypeWorkFlow) - end"); //$NON-NLS-1$
		}
	}

	public <T> T getJsonValue(String json, Class<T> clazz) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJsonValue(String, Class<T>) - start"); //$NON-NLS-1$
		}

		Gson gson = new Gson();
		T returnT = (T) gson.fromJson(json, clazz);
		if (logger.isDebugEnabled()) {
			logger.debug("getJsonValue(String, Class<T>) - end"); //$NON-NLS-1$
		}
		return returnT;
	}

	/**
	 * Metodo para abrir un proyecto
	 */
	public void onAbrirProyecto(TypeWorkFlow  type) {
		if (logger.isDebugEnabled()) {
			logger.debug("onAbrirProyecto(TypeWorkFlow) - start"); //$NON-NLS-1$
		}

		List<ProjectDesignerDTO> lstProject =  designerService.getAllProjects(type);
		if (lstProject == null || lstProject.isEmpty()) {
			Messagebox.show(Labels.getLabel("msg.errorNoExisten"), "Información", Messagebox.OK, Messagebox.INFORMATION);
			if (logger.isDebugEnabled()) {
				logger.debug("onAbrirProyecto(TypeWorkFlow) - end"); //$NON-NLS-1$
			}
			return;
		}
		try {
			final Window win = ZkUtil.createComponent("pantallas/AbrirProyecto.zul", null,null);
			ProjectDesignerDTO inicial = lstProject.get(0);

			final Textbox autor = ZkUtil.findById(win, "autor");
			final Textbox descripcion = ZkUtil.findById(win, "descripcion");
			final Listbox listbox = ZkUtil.findById(win, "lstProject");
			listbox.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
					}
					ProjectDesignerDTO projecto = (ProjectDesignerDTO) listbox.getSelectedItem().getValue();
					autor.setValue(projecto.getAuthor());
					descripcion.setValue(projecto.getDescription());
					setProjectName(projecto.getName());
				
					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});

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

					Listbox listbox = ZkUtil.findById(win, "lstProject");
					ProjectDesignerDTO pr = (ProjectDesignerDTO) listbox.getSelectedItem().getValue();
					Project proj = designerService.fromJson(pr.getJsonModel(), Project.class);
					setInitialSubProcess(getSubProcesssService().getAllSubProcessProject(proj.getName()));
					setVersionProject(proj.getVersion());
					loadProject(proj);
					win.detach();

					if (logger.isDebugEnabled()) {
						logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
					}
				}
			});

			autor.setValue(inicial.getAuthor());
			descripcion.setValue(inicial.getDescription());
			listbox.setModel(new ListModelList<ProjectDesignerDTO>(lstProject));
			listbox.setSelectedIndex(0);
			win.doModal();
		} catch (UiException uiex) {
			logger.warn("onAbrirProyecto(TypeWorkFlow) - exception ignored", uiex); //$NON-NLS-1$
			Messagebox.show(Labels.getLabel("msg.errorObtener"), "Error", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onAbrirProyecto(TypeWorkFlow) - end"); //$NON-NLS-1$
		}

	}

	public void loadProject(Project project) {
		if (logger.isDebugEnabled()) {
			logger.debug("loadProject(Project) - start"); //$NON-NLS-1$
		}

		setProject(project);
		final String jsonProject =designerService.toJson(project);
		Clients.evalJavaScript("cargarProyecto(" + jsonProject + ");");

		if (logger.isDebugEnabled()) {
			logger.debug("loadProject(Project) - end"); //$NON-NLS-1$
		}
	}
	
	/*
	 * Functions copied from TaskFlowComposer.java. Since this
	 * is a superclass, it can be used from both TaskFlowComposer and
	 * StateFlowComposer, for both task and states scripts
	 */
	
	public void onSelectServices(TypeWorkFlow type){
		Map<String,Object>  params =  new HashMap<>();
		params.put("typeWorkflow", type.toString());
		final Window win = ZkUtil.createComponent("pantallas/ServicesESB.zul", null, params, null);
		final Button btnGuardar = ZkUtil.findById(win, "btnAceptar");
		final Button btnCancelar = ZkUtil.findById(win, "btnCancelar");
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
				}
				String fnInsert = null;
				if(type.equals(TypeWorkFlow.TASK)){
					fnInsert = "invokeRemoteService('%s','%s','%s');";
				} else {
					fnInsert = "invokeRemoteServiceOperation('%s','%s','%s');";
				}
				String callFn;
				Combobox cmbCodes = ZkUtil.findById(win, "lstCodes");
				Combobox cmbTypesBlock = ZkUtil.findById(win, "lstTypesBlock");
				Textbox tbxMessage = ZkUtil.findById(win, "message");
				Combobox cmbTypeCall = ZkUtil.findById(win, "typeCall");
				
				if(StringUtils.isBlank(cmbCodes.getValue()) 
						&& StringUtils.isBlank(tbxMessage.getValue())){
					Messagebox.show(Labels.getLabel("msg.seleccioneCodigo"),"Error",Messagebox.OK, Messagebox.ERROR);
					return;
				}
				String typeCall = cmbTypeCall.getSelectedItem().getValue();
				callFn = String.format(fnInsert,
						cmbCodes.getSelectedItem().getValue(), tbxMessage.getValue(), typeCall);
				Clients.evalJavaScript(String.format("insertCmd(\"%s\");",callFn));
				String  typeBlock =  cmbTypesBlock.getValue();
				if("Si".equalsIgnoreCase(typeBlock)){
					callFn = "blockExpedient();";
					Clients.evalJavaScript(String.format("insertCmd(\"%s\");",callFn));
				}
				
				
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
				
				String callFn;
				Combobox cmbxFields = ZkUtil.findById(win, "lstFields");
				Combobox cmbxCondition = ZkUtil.findById(win, "lstCondition");
				Combobox cmbxForms = ZkUtil.findById(win, "lstForms");
				Textbox tbxCompare = ZkUtil.findById(win, "tbxCompare");
				Radiogroup rg = ZkUtil.findById(win, "g1");
				Combobox cmbxTypes = ZkUtil.findById(win, "lstFieldType");
				
				if(rg.getSelectedItem().getId().equalsIgnoreCase("ra1")){
					callFn = String.format("validateStructure('%s');", cmbxForms.getValue());
				} else if (rg.getSelectedItem().getId().equalsIgnoreCase("ra2")){
					String type = cmbxTypes.getValue();
					String format = "%s";
					if("Alfanumerico".equalsIgnoreCase(type)){
						format = "'%s'";
					}
					callFn = String.format("if(FFDD.get('%s') %s " + format + "){}", cmbxFields.getValue()  , 
							cmbxCondition.getValue(), tbxCompare.getValue());
				} else {
					callFn = String.format("FFDD.get('%s')", cmbxFields.getValue());
				}
				
				 
				Clients.evalJavaScript(String.format("insertCmd(\"%s\");",callFn));
				win.detach();

				if (logger.isDebugEnabled()) {
					logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
				}
			}
		});
		win.doModal();
	}

	public void onRealizarPase(Event event) {
	
	}

	public void onShowHistory(Event event) {
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void onSelectServicesEgoveris(final Event event){
		final Window win = ZkUtil.createComponent("pantallas/ServicesEgoveris.zul", null, null, null);
		final Button btnGuardar = ZkUtil.findById(win, "btnAceptar");
		final Button btnCancelar = ZkUtil.findById(win, "btnCancelar");
		
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				String host = ((Combobox) ZkUtil.findById(win, "cbxServices")).getSelectedItem().getValue();
				String endpoint = ((Combobox) ZkUtil.findById(win, "cbxEndPoints")).getValue();
				Grid grid = ((Grid) ZkUtil.findById(win, "gridParameters"));
				String httpType = ((Textbox) ZkUtil.findById(win, "typeRequest")).getValue();
				String typeParameter = ((Textbox) ZkUtil.findById(win, "typeParameter")).getValue();
				String parameters = getParameters(grid, typeParameter);
				String url =  host.concat(endpoint);
				String callFn;
				if(TYPE_QUERY.equalsIgnoreCase(typeParameter)){
					callFn = String.format("httpRequest(\"%s\",\"%s\",\"%s\",\"%s\");",url, parameters, typeParameter ,httpType);
				} else {
					callFn = String.format("httpRequest(\"%s\",%s,\"%s\",\"%s\");",url, parameters, typeParameter,httpType);
				}	
				String cmd = String.format("insertCmd(\'%s\');", callFn);
				Clients.evalJavaScript(cmd);
				win.detach();
			}
		});
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				win.detach();
			}
		});
		win.doModal();
	}
	
	/**
	 * The parameters are retrieved depending on the type of parameter if it is of type QUERY then they will be concatenated to the URL, 
	 * on the contrary if it is of type BODY, then the JSON corresponding to the ENDPOINT will be formed.
	 * @param grid
	 * @param typeQuery
	 * @return
	 */
	private String getParameters(Grid grid, String typeQuery){
		String params;
		if(TYPE_BODY.equalsIgnoreCase(typeQuery)){
			params ="{";
			params += getValueFromRow(grid, typeQuery,"");
			params = params.substring(0, params.length() -1 );
			params += "}";
		} else {
			params = "";
			for(Component comp : grid.getRows().getChildren()){
				Row  row = (Row) comp;
				if(row.getChildren().size() == 2){
					Label label = (Label) row.getChildren().get(0).getFirstChild();
					Textbox tbx = (Textbox) row.getChildren().get(1).getFirstChild();
					String value = tbx.getValue().replace(" ",HTML_BLANK_URL);
					if(StringUtils.isBlank(value)){
						value= HTML_BLANK_URL;
					} else if(value.startsWith("${")){
						value =  "\" + " + value.substring(2, value.length() - 1) + " + \"";
					}
					params += label.getValue() + "=" + value + "&";
				}
			}
			params = params.substring(0, params.length() -1);
		}
		
		return params;
	}
	
	
	/**
	 * The parameters are obtained from the form obtained in 
	 * the {@link ServicesEgoverisVM} class and all the rows are crossed to
	 *  obtain the value and the labels that will be keyed in JSON format
	 * @param grid
	 * @param typeQuery
	 * @param params
	 * @return
	 */
	private String getValueFromRow(Grid grid, String typeQuery, String params){
		for(Component comp : grid.getRows().getChildren()){
			Row row = (Row) comp;
			java.util.Iterator<Component> it = row.getChildren().iterator();
			while(it.hasNext()){
				Component c = it.next();
				Component next = c.getNextSibling();
				boolean isHeader = false;
				boolean isArray = false;
				if(next != null && !(next instanceof Textbox)){
					if(c instanceof Cell){
						Label label = (Label) c.getFirstChild();
						params += "\"" +  label.getValue() + "\"" + ":";
						if(label.getAttribute("header") != null){
							isHeader = true;
							if(label.getAttribute("array") != null){
										params += "[";
										isArray = true;
							}
							params += "{";
						} 
					}
					if(next instanceof Grid){
						params += getValueFromRow((Grid) next, typeQuery, "");
						params = params.substring(0, params.length() - 1);
						params += "}";
						if(isHeader && isArray){
							params += "]";
						}
						params += ",";
						
					} 
				} else if(c instanceof Cell){
					if(c.getFirstChild() instanceof Textbox){
						Textbox tbx = (Textbox) c.getFirstChild();
						String value =  tbx.getValue();
						if(value == null){
							value = "";
						}
						// if true mean the value is a var of execution time
						if(value.startsWith("${")){
							value =  value.substring(2, value.length() - 1);
							params += value;
						} else {
							 if(tbx.getAttribute("numeric") != null
									 || "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)){
								 params += value;
							 } else {
								 params += "\"" + value + "\"";
							 }
						}
						 params +=  ",";
					}
				}
			}
		}
		
		return params;
				
	}
		

	
	/**
	 * Actualiza el workflowName de cada estado
	 */
	public void updateWorkflowName(Project project) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateWorkflowName(Project) - start"); //$NON-NLS-1$
		}
		List<State> lstState = project.getStates();
		for (State state : lstState) {
			StateProperties properties = state.getProperties();
			properties.setWorkflowName(DesignerUtil.camelName(project.getName()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("updateWorkflowName(Project) - end"); //$NON-NLS-1$
		}
	}

	public DesignerService getDesignerService() {
		return designerService;
	}

	public void setDesignerService(DesignerService designerService) {
		this.designerService = designerService;
	}

	public WebDavIntService getWebDavService() {
		return webDavService;
	}

	public void setWebDavService(WebDavIntService webDavService) {
		this.webDavService = webDavService;
	}

	public TransformerService getTransformerService() {
		return transformerService;
	}

	public void setTransformerService(TransformerService transformerService) {
		this.transformerService = transformerService;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public SubProcessService getSubProcesssService() {
		return subProcesssService;
	}

	public void setSubProcesssService(SubProcessService subProcesssService) {
		this.subProcesssService = subProcesssService;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Map<String, String> getInitialSubProcess() {
		return initialSubProcess;
	}

	public void setInitialSubProcess(Map<String,String> initialSubProcess) {
		this.initialSubProcess = initialSubProcess;
	}

	public int getVersionProject() {
		return versionProject;
	}

	public void setVersionProject(int versionProject) {
		this.versionProject = versionProject;
	}

	
}
