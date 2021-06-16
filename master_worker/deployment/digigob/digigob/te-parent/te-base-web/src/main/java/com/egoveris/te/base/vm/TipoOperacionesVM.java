package com.egoveris.te.base.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.helper.TipoOperacionesHelper;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ActionModeEnum;
import com.egoveris.te.base.util.ConstantesServicios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TipoOperacionesVM {

  private static final Logger logger = LoggerFactory.getLogger(TipoOperacionesVM.class);

  private static final String LITERAL_KEY_ELIMINAR_TITLE = "ee.tipoOperacion.admTipoOperaciones.popup.eliminar.title";
  private static final String LITERAL_KEY_ELIMINAR_MSG = "ee.tipoOperacion.admTipoOperaciones.popup.eliminar.msg";

  private static final String LITERAL_KEY_HABILITAR_TITLE = "ee.tipoOperacion.admTipoOperaciones.popup.habilitar.title";
  private static final String LITERAL_KEY_HABILITAR_MSG = "ee.tipoOperacion.admTipoOperaciones.popup.habilitar.msg";
  private static final String LITERAL_KEY_DESHABILITAR_MSG = "ee.tipoOperacion.admTipoOperaciones.popup.deshabilitar.msg";

  public static final String LITERAL_KEY_TITULO_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicio.title";
  public static final String LITERAL_KEY_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicio";
  private static final String LITERAL_KEY_ERROR_SERVICIO_ELIMINAR = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicioEliminar";

  public static final String LITERAL_KEY_TITULO_ERROR_PREVIEW = "ee.tipoOperacion.admTipoOperaciones.formulario.preview.error.title";
  public static final String LITERAL_KEY_ERROR_PREVIEW = "ee.tipoOperacion.admTipoOperaciones.formulario.preview.error";

  private static final String COMPARE_CODIGO = "codigo";
  private static final String COMPARE_DESC = "desc";

  // Pantalla
  @Wire("#tipoOperacionesWindow")
  private Window window;

  private Window formulario;
  private ActionModeEnum actionMode;

  // Servicio
  @WireVariable(ConstantesServicios.TIPO_OPERACION_SERVICE)
  private ITipoOperacionService tipoOperacionService;

  @WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
  private IExternalFormsService formularioService;

  @WireVariable(ConstantesServicios.FORM_MANAGER_FACTORY)
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;

  // "Beans"
  private TipoOperacionDTO tipoOperacion;
  private List<TipoOperacionDTO> tipoOperaciones;
  private List<TipoOperacionDTO> tipoOperacionesAux;
  private Listitem workflowSel;
  private List<Listitem> listWorkflows;
  private List<FormularioDTO> formulariosDinamicos;
  private List<FormularioDTO> formulariosDinamicosAux;
  private List<FormularioDTO> formulariosSel;
  private String inputBusquedaForm;
  private String inputBusquedaGrid;
  private List<String> foundBusquedaGrid;

  private List<TipoOperacionDocDTO> tiposDocumentosSel;
  private List<TipoOperacionOrganismoDTO> organismosSel;

  // Interaccion

  /**
   * Init de TipoOperacionesVM Inicializa los beans de servicio y carga la
   * grilla de tipo operaciones
   * 
   * @param view
   */
  @Init
  public void init(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);
    listWorkflows = TipoOperacionesHelper.findActiveWorkflows();

    tipoOperaciones = new ArrayList<>();

    try {
      tipoOperaciones.addAll(tipoOperacionService.getAllTiposOperacion());
    } catch (ServiceException e) {
      logger.error("Error en TipoOperacionesVM.init(): ", e);
      Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Abre una ventana de formulario tipo operacion (nuevo registro)
   */
  @Command
  @NotifyChange({ "tipoOperacion", "workflowSel", "formulariosSel" })
  public void newTipoOperacionWindow() {
    actionMode = ActionModeEnum.NEW;

    tipoOperacion = new TipoOperacionDTO();
    workflowSel = new Listitem();

    if (formulariosDinamicos == null) {
      formulariosDinamicos = TipoOperacionesHelper.loadFormulariosDinamicos(formularioService);
    } else {
      limpiarBusquedaFormulario();
    }

    if (formulariosDinamicos != null) {
      formulariosSel = new ArrayList<>();

      formulario = (Window) Executions.createComponents("/tipoOperaciones/formTipoOperacion.zul", window, null);
      formulario.doModal();
    }
  }

  /**
   * Abre una ventana de formulario tipo operacion (edicion registro)
   * 
   * @param tipoOperacion
   *          Dto con los datos del registro a modificar
   */
  @Command
  @NotifyChange({ "workflowSel", "formulariosSel" })
  public void editTipoOperacionWindow(@BindingParam("tipoOperacion") TipoOperacionDTO tipoOperacion) {
    actionMode = ActionModeEnum.EDIT;

    this.tipoOperacion = tipoOperacion;
    workflowSel = TipoOperacionesHelper.getSelectedItem(listWorkflows, tipoOperacion.getWorkflow().toString());

    if (formulariosDinamicos == null) {
      formulariosDinamicos = TipoOperacionesHelper.loadFormulariosDinamicos(formularioService);
    } else {
      limpiarBusquedaFormulario();
    }

    if (formulariosDinamicos != null) {
      formulariosSel = TipoOperacionesHelper.getFormulariosTipoOperacion(tipoOperacionService, formulariosDinamicos,
          tipoOperacion.getId());

      formulario = (Window) Executions.createComponents("/tipoOperaciones/formTipoOperacion.zul", window, null);
      formulario.doModal();
    }
  }

  /**
   * Cierra la ventana de formulario tipo operacion
   */
  @Command
  public void closeTipoOperacion() {
    if (formulario != null) {
      formulario.onClose();
    }
  }

  /**
   * Guarda un registro de tipo operacion Para ello el formulario debe estar
   * previamente validado y luego llama al servicio TipoOperacionService
   */
  @Command
  @NotifyChange({ "tipoOperacion", "tipoOperaciones", "tipoOperacionesAux" })
  public void saveTipoOperacion() {
    if (actionMode.equals(ActionModeEnum.NEW)) {
      // Estado habilitado para nuevo registro
      tipoOperacion.setEstado(true);
    }

    tipoOperacion.setWorkflow(Long.valueOf(workflowSel.getValue().toString()));

    if (tipoOperacionService.saveOrUpdateTipoOperacion(tipoOperacion, formulariosSel, tiposDocumentosSel,
        organismosSel)) {
      tipoOperacion = new TipoOperacionDTO();

      recargarGrid();
      closeTipoOperacion();
    } else {
      // Problema en la operacion
      Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Abre un dialog (YES/NO) para eliminar un tipo de operacion
   * 
   * @param tipoOperacion
   *          Dto con el registro a eliminar
   */
  @Command
  public void deleteTipoOperacion(@BindingParam("tipoOperacion") final TipoOperacionDTO tipoOperacion) {
    String confirmationMsg = Labels.getLabel(LITERAL_KEY_ELIMINAR_MSG, new Object[] { tipoOperacion.getCodigo() });

    Messagebox.show(confirmationMsg, Labels.getLabel(LITERAL_KEY_ELIMINAR_TITLE), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener<Event>() {

          @Override
          public void onEvent(Event event) throws Exception {
            if (event.getName().equals(Messagebox.ON_YES)) {
              if (!tipoOperacionService.deleteTipoOperacion(tipoOperacion)) {
                // Problema al eliminar
                Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO_ELIMINAR),
                    Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK, Messagebox.ERROR);
              } else {
                BindUtils.postGlobalCommand(null, null, "recargarGrid", null);
              }
            }
          }
        });
  }

  /**
   * Abre un dialog para habilitar (o deshabilitar) un tipo de operacion
   * 
   * @param tipoOperacion
   *          Dto con el registro a habilitar/deshabilitar
   * @param checked
   *          Valor checked del componente
   */
  @Command
  public void onCheckHabilitar(@BindingParam("tipoOperacion") final TipoOperacionDTO tipoOperacion,
      @BindingParam("checked") final boolean checked) {
    String confirmationMsg;

    if (checked) {
      // Habilitar
      confirmationMsg = Labels.getLabel(LITERAL_KEY_HABILITAR_MSG, new Object[] { tipoOperacion.getCodigo() });
    } else {
      // Deshabilitar
      confirmationMsg = Labels.getLabel(LITERAL_KEY_DESHABILITAR_MSG, new Object[] { tipoOperacion.getCodigo() });
    }

    Messagebox.show(confirmationMsg, Labels.getLabel(LITERAL_KEY_HABILITAR_TITLE), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener<Event>() {
          @Override
          public void onEvent(Event event) throws Exception {
            TipoOperacionesHelper.onCheckHabilitarEvent(event, tipoOperacionService, tipoOperacion, checked);
          }
        });
  }

  /**
   * Comando que recarga la grilla de tipo operaciones
   */
  @GlobalCommand
  @NotifyChange({ "tipoOperaciones", "tipoOperacionesAux" })
  public void recargarGrid() {
    tipoOperaciones = new ArrayList<>();
    tipoOperacionesAux = new ArrayList<>();

    try {
      tipoOperaciones.addAll(tipoOperacionService.getAllTiposOperacion());
      tipoOperacionesAux.addAll(tipoOperaciones);
    } catch (ServiceException e) {
      logger.error("Error en TipoOperacionesVM.recargarGrid(): ", e);
      Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Comando exclusivamente para hacer reRender de tipoOperaciones Utilizado al
   * eliminar un registro de tipo operacion
   */
  @GlobalCommand
  @NotifyChange({ "tipoOperacion", "tipoOperaciones" })
  public void notifyChangeHabilitar() {
    // No hace nada mas que el reRender (NotifyChange)
  }

  /**
   * Busca formularios dinamicos por filtro Accion que ocurre al pulsar el boton
   * 'Buscar' dentro de la seccion cabecera de la ventana formulario tipo
   * operacion
   */
  @Command
  @NotifyChange({ "formulariosDinamicos", "inputBusquedaForm" })
  public void busquedaFormulario() {
    if (inputBusquedaForm != null && "".equals(StringUtils.trim(inputBusquedaForm))) {
      limpiarBusquedaFormulario();
    } else if (inputBusquedaForm != null && formulariosDinamicos != null) {
      if (formulariosDinamicosAux == null) {
        formulariosDinamicosAux = new ArrayList<>();
        formulariosDinamicosAux.addAll(formulariosDinamicos);
      }

      List<FormularioDTO> formEncontrados = new ArrayList<>();

      Pattern pattern = Pattern.compile(".*" + inputBusquedaForm.toLowerCase() + ".*");

      for (FormularioDTO formularioDTO : formulariosDinamicosAux) {
        if (formularioDTO.getNombre() != null && pattern.matcher(formularioDTO.getNombre().toLowerCase()).matches()) {
          formEncontrados.add(formularioDTO);
        }
      }

      formulariosDinamicos.clear();
      formulariosDinamicos.addAll(formEncontrados);
    }
  }

  /**
   * Limpia los filtros del buscador de formularios dinamicos
   */
  @Command
  @NotifyChange({ "formulariosDinamicos", "inputBusquedaForm" })
  public void limpiarBusquedaFormulario() {
    inputBusquedaForm = "";

    if (formulariosDinamicosAux != null) {
      formulariosDinamicos.clear();
      formulariosDinamicos.addAll(formulariosDinamicosAux);
    }
  }

  @Command
  @NotifyChange({ "foundBusquedaGrid" })
  public void onChangingBusquedaGrid() {
    foundBusquedaGrid = new ArrayList<>();

    if (inputBusquedaGrid != null && inputBusquedaGrid.length() > 3) {
      Pattern pattern = Pattern.compile(".*" + inputBusquedaGrid.toLowerCase() + ".*");

      for (TipoOperacionDTO tipoOperacionDTO : tipoOperaciones) {
        StringBuilder codigoDescripcion = new StringBuilder(tipoOperacionDTO.getCodigo()).append(" - ");

        if (tipoOperacionDTO.getDescripcion() != null) {
          codigoDescripcion.append(tipoOperacionDTO.getDescripcion());
        }

        if (pattern.matcher(codigoDescripcion.toString().toLowerCase()).matches()) {
          foundBusquedaGrid.add(codigoDescripcion.toString());
        }
      }
    }
  }

  /**
   * Accion desencadenada al presionar buscar por codigo o descripcion
   * 
   * @param tipoBusqueda
   *          String que indica el tipo de busqueda. Puede ser "codigo" o "desc"
   */
  @Command
  @NotifyChange({ "tipoOperaciones", "inputBusquedaGrid" })
  public void busquedaGrid(@BindingParam("tipoBusqueda") String tipoBusqueda) {
    if (inputBusquedaGrid != null && !StringUtils.isBlank(inputBusquedaGrid) && tipoOperaciones != null) {

      if (tipoOperacionesAux == null) {
        tipoOperacionesAux = new ArrayList<>();
        tipoOperacionesAux.addAll(tipoOperaciones);
      }

      tipoOperaciones.clear();
      tipoOperaciones.addAll(busquedaTipoOperPorTipo(tipoBusqueda, inputBusquedaGrid, tipoOperacionesAux));
    } else if (inputBusquedaGrid != null) {
      limpiarBusquedaGrid();
    }
  }

  /**
   * Busca dentro de la lista de tipo operaciones un texto ya sea por codigo o
   * descripcion
   * 
   * @param tipoBusqueda
   *          puede ser "codigo" o "desc"
   * @param textoBusqueda
   *          texto a buscar
   * @param tipoOperaciones
   *          lista de tipo operaciones a buscar
   * @return lista con los tipos de operaciones que coinciden
   */
  private List<TipoOperacionDTO> busquedaTipoOperPorTipo(String tipoBusqueda, String inputBusqueda,
      List<TipoOperacionDTO> tipoOperaciones) {
    List<TipoOperacionDTO> tipoOperacionesEncontrados = new ArrayList<>();

    if (tipoBusqueda != null && inputBusqueda != null) {
      Pattern pattern = patternBusquedaTipoOper(tipoBusqueda, inputBusqueda);

      for (TipoOperacionDTO tipoOperacionDTO : tipoOperaciones) {
        if (COMPARE_CODIGO.equals(tipoBusqueda) && tipoOperacionDTO.getCodigo() != null
            && pattern.matcher(tipoOperacionDTO.getCodigo().toLowerCase()).matches()) {
          tipoOperacionesEncontrados.add(tipoOperacionDTO);
        }

        if (COMPARE_DESC.equals(tipoBusqueda) && tipoOperacionDTO.getDescripcion() != null
            && pattern.matcher(tipoOperacionDTO.getDescripcion().toLowerCase()).matches()) {
          tipoOperacionesEncontrados.add(tipoOperacionDTO);
        }
      }
    }

    return tipoOperacionesEncontrados;
  }

  /**
   * Retorna el patron de busqueda. Este metodo es invocado desde
   * busquedaTipoOperPorTipo
   * 
   * @param tipoBusqueda
   * @param inputBusqueda
   * @return
   */
  private Pattern patternBusquedaTipoOper(String tipoBusqueda, String inputBusqueda) {
    String textoBusqueda = inputBusqueda.toLowerCase();

    if (COMPARE_CODIGO.equals(tipoBusqueda) && textoBusqueda.contains("-")) {
      textoBusqueda = StringUtils.substringBefore(textoBusqueda, "-");
    } else if (COMPARE_DESC.equals(tipoBusqueda) && textoBusqueda.contains("-")) {
      textoBusqueda = StringUtils.substringAfter(textoBusqueda, "-");
    }

    return Pattern.compile(".*" + textoBusqueda + ".*");
  }

  /**
   * Accion que limpia los filtros de busqueda de la grilla tipo operaciones
   */
  @Command
  @NotifyChange({ "tipoOperaciones", "inputBusquedaGrid", "foundBusquedaGrid" })
  public void limpiarBusquedaGrid() {
    inputBusquedaGrid = "";
    foundBusquedaGrid = new ArrayList<>();

    if (tipoOperacionesAux != null) {
      tipoOperaciones.clear();
      tipoOperaciones.addAll(tipoOperacionesAux);
    }
  }

  /**
   * Previsualiza un formulario dinamico
   * 
   * @param dynamicForm
   *          Dto con el objeto de formulario dinamico
   */
  @Command
  public void previewDynamicForm(@BindingParam("dynamicForm") FormularioDTO dynamicForm) {
    IFormManager<Component> formManager = null;

    try {
      formManager = formManagerFact.create(dynamicForm.getNombre());
    } catch (Exception e) {
      logger.error("Error en TipoOperacionesVM.previewDynamicForm(): ", e);
      Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_PREVIEW), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_PREVIEW),
          Messagebox.OK, Messagebox.ERROR);
    }

    if (formManager != null) {
      Window previewFormulario = (Window) Executions.createComponents("/tipoOperaciones/tabs/previewDynamicForm.zul",
          window, null);

      Div fcDiv = (Div) previewFormulario.getFirstChild();
      Hbox fcHbox = (Hbox) fcDiv.getFirstChild();

      fcDiv.setVisible(true);
      fcHbox.appendChild(formManager.getFormComponent());

      previewFormulario.setTitle(dynamicForm.getNombre());
      previewFormulario.doModal();
    }
  }

  /**
   * Muestra el texto correspondiente a un Id. Workflow
   * 
   * @param value
   * @return
   */
  public String getWorkflowLabel(Long value) {
    String label = "";
    Listitem listitem = TipoOperacionesHelper.getSelectedItem(listWorkflows, value.toString());

    if (listitem != null) {
      label = listitem.getLabel();
    }

    return label;
  }

  /**
   * Comando global que actualiza la lista de tipo documentos seleccionada desde
   * TipoOperacionDocsVM
   * 
   * @param tiposDocumentosSel
   */
  @GlobalCommand
  public void updTipoDocsSeleccionados(
      @BindingParam("tiposDocumentosSel") List<TipoOperacionDocDTO> tiposDocumentosSel) {
    setTiposDocumentosSel(tiposDocumentosSel);
  }

  /**
   * Comando global que actualiza la lista de organismos seleccionada desde
   * TipoOperacionOrganismosVM
   * 
   * @param organismosSel
   */
  @GlobalCommand
  public void updOrganismosSeleccionados(@BindingParam("organismosSel") List<TipoOperacionOrganismoDTO> organismosSel) {
    setOrganismosSel(organismosSel);
  }

  // Getters - setters

  public ActionModeEnum getActionMode() {
    return actionMode;
  }

  public void setActionMode(ActionModeEnum actionMode) {
    this.actionMode = actionMode;
  }

  public TipoOperacionDTO getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public List<TipoOperacionDTO> getTipoOperaciones() {
    return tipoOperaciones;
  }

  public void setTipoOperaciones(List<TipoOperacionDTO> tipoOperaciones) {
    this.tipoOperaciones = tipoOperaciones;
  }

  public List<TipoOperacionDTO> getTipoOperacionesAux() {
    return tipoOperacionesAux;
  }

  public void setTipoOperacionesAux(List<TipoOperacionDTO> tipoOperacionesAux) {
    this.tipoOperacionesAux = tipoOperacionesAux;
  }

  public Listitem getWorkflowSel() {
    return workflowSel;
  }

  public void setWorkflowSel(Listitem workflowSel) {
    this.workflowSel = workflowSel;
  }

  public List<Listitem> getListWorkflows() {
    return listWorkflows;
  }

  public void setListWorkflows(List<Listitem> listWorkflows) {
    this.listWorkflows = listWorkflows;
  }

  public List<FormularioDTO> getFormulariosDinamicos() {
    return formulariosDinamicos;
  }

  public void setFormulariosDinamicos(List<FormularioDTO> formulariosDinamicos) {
    this.formulariosDinamicos = formulariosDinamicos;
  }

  public List<FormularioDTO> getFormulariosDinamicosAux() {
    return formulariosDinamicosAux;
  }

  public void setFormulariosDinamicosAux(List<FormularioDTO> formulariosDinamicosAux) {
    this.formulariosDinamicosAux = formulariosDinamicosAux;
  }

  public List<FormularioDTO> getFormulariosSel() {
    return formulariosSel;
  }

  public void setFormulariosSel(List<FormularioDTO> formulariosSel) {
    this.formulariosSel = formulariosSel;
  }

  public String getInputBusquedaForm() {
    return inputBusquedaForm;
  }

  public void setInputBusquedaForm(String inputBusquedaForm) {
    this.inputBusquedaForm = inputBusquedaForm;
  }

  public String getInputBusquedaGrid() {
    return inputBusquedaGrid;
  }

  public void setInputBusquedaGrid(String inputBusquedaGrid) {
    this.inputBusquedaGrid = inputBusquedaGrid;
  }

  public List<String> getFoundBusquedaGrid() {
    return foundBusquedaGrid;
  }

  public void setFoundBusquedaGrid(List<String> foundBusquedaGrid) {
    this.foundBusquedaGrid = foundBusquedaGrid;
  }

  public List<TipoOperacionDocDTO> getTiposDocumentosSel() {
    return tiposDocumentosSel;
  }

  public void setTiposDocumentosSel(List<TipoOperacionDocDTO> tiposDocumentosSel) {
    this.tiposDocumentosSel = tiposDocumentosSel;
  }

  public List<TipoOperacionOrganismoDTO> getOrganismosSel() {
    return organismosSel;
  }

  public void setOrganismosSel(List<TipoOperacionOrganismoDTO> organismosSel) {
    this.organismosSel = organismosSel;
  }

}