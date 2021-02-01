package com.egoveris.te.base.helper;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.vm.TipoOperacionesVM;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox; 

public class TipoOperacionesHelper {

  private static final Logger logger = LoggerFactory.getLogger(TipoOperacionesHelper.class);
  private static final String KEY_LITERAL_ERROR_SERVICIO_CABECERA = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicioCabecera";

  private TipoOperacionesHelper() {

  }

  /**
   * Copia de TramitacionHelper.findActiveWorkflows(), pero devuelve tambien el
   * Id [Stateflows]
   * 
   * @return
   */
  public static List<Listitem> findActiveWorkflows() {
    List<Listitem> result = new ArrayList<>(); 
    ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
    
    // Order by name asc and latest version
    ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
    query.orderAsc(ProcessDefinitionQuery.PROPERTY_NAME);
    query.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION);
    
    List<ProcessDefinition> lst = query.list();

    for (ProcessDefinition pd : lst) {
      if (!pd.isSuspended() && pd.getName().startsWith("state") && !containsWorkflow(pd.getKey(), result)) {
        result.add(new Listitem(pd.getKey(), pd.getDeploymentId()));
      }
    }
    
    return result;
  }
  
  /**
   * Checks if actual workflow list contains specified workflowName
   * 
   * @param workflowName
   * @param workflowList
   * @return
   */
  private static boolean containsWorkflow(String workflowName, List<Listitem> workflowList) {
    boolean contains = false;
    
    if (workflowList != null && !workflowList.isEmpty()) {
      for (Listitem listitem : workflowList) {
        if (listitem.getLabel().equalsIgnoreCase(workflowName)) {
          contains = true;
          break;
        }
      }
    }
    
    return contains;
  }

  /**
   * Busca y retorna el workflow seleccionado segun value
   * 
   * @param collection
   *          Lista de workflows
   * @param value
   *          Id. del workflow
   * @return Workflow encontrado, puede ser null si no lo encuentra
   */
  public static Listitem getSelectedItem(List<Listitem> collection, String value) {
    Listitem selectedItem = null;

    if (collection != null && !collection.isEmpty()) {
      for (Listitem item : collection) {
        if (item.getValue().equals(value)) {
          selectedItem = item;
          break;
        }
      }
    }

    return selectedItem;
  }

  /**
   * Obtiene todos los formularios dinamicos que existan
   * 
   * @param formularioService
   *          Instancia de IExternalFormsService
   * @return Lista con formularios dinamicos, puede ser null en caso de error
   */
  public static List<FormularioDTO> loadFormulariosDinamicos(
		  IExternalFormsService formularioService) {
    List<FormularioDTO> formulariosDinamicos = null;

    try {
      formulariosDinamicos = formularioService.buscarFormulariosFFDD();
    } catch (Exception e) {
      logger.error("Error en TipoOperacionesHelper.loadFormulariosDinamicos(): ", e);
      Messagebox.show(Labels.getLabel(KEY_LITERAL_ERROR_SERVICIO_CABECERA),
          Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK,
          Messagebox.ERROR);
    }

    return formulariosDinamicos;
  }

  /**
   * Obtiene todos los formularios asociados a un tipo de operacion
   * 
   * @param tipoOperacionService
   *          Instancia de ITipoOperacionService
   * @param formulariosDinamicos
   *          Lista con todos los formularios dinamicos, previa llamada a
   *          loadFormulariosDinamicos()
   * @param idTipoOperacion
   *          Id de tipo operacion
   * @return Lista con formularios dinamicos asociados al tipo de operacion,
   *         puede venir vacia si no hay
   */
  public static List<FormularioDTO> getFormulariosTipoOperacion(
      ITipoOperacionService tipoOperacionService, List<FormularioDTO> formulariosDinamicos,
      Long idTipoOperacion) {
    List<FormularioDTO> formulariosTipoOperacion = new ArrayList<>();
    List<FormularioDTO> idFormulariosTipoOperacion = new ArrayList<>();

    try {
      idFormulariosTipoOperacion = tipoOperacionService
          .getIdFormulariosTipoOperacion(idTipoOperacion);
    } catch (ServiceException e) {
      logger.error("Error en TipoOperacionesHelper.getFormulariosTipoOperacion(): ", e);
      Messagebox.show(Labels.getLabel(KEY_LITERAL_ERROR_SERVICIO_CABECERA),
          Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK,
          Messagebox.ERROR);
    }

    // Arma una lista con los formularios dinamicos que correspondan al tipo de
    // operacion
    for (FormularioDTO idFormularioTipoOperacion : idFormulariosTipoOperacion) {
      Integer searchId = idFormularioTipoOperacion.getId();

      for (FormularioDTO formularioDTO : formulariosDinamicos) {
        if (formularioDTO.getId().equals(searchId)) {
          formulariosTipoOperacion.add(formularioDTO);
          break;
        }
      }
    }

    return formulariosTipoOperacion;
  }

  /**
   * Evento capturado cuando el usuario pulsa una opcion en el popup de
   * habilitar o deshabilitar un tipo de operacion
   * 
   * @param event
   *          Evento
   * @param tipoOperacionService
   *          Servicio TipoOperacion
   * @param tipoOperacion
   *          Dto TipoOperacion
   * @param checked
   *          habilitado o deshabilitado (true o false)
   */
  public static void onCheckHabilitarEvent(Event event, ITipoOperacionService tipoOperacionService,
      TipoOperacionDTO tipoOperacion, boolean checked) {
    if (event.getName().equals(Messagebox.ON_YES)) {
      tipoOperacion.setEstado(checked);

      if (tipoOperacionService.saveOrUpdateTipoOperacion(tipoOperacion, null, null, null)) {
        BindUtils.postGlobalCommand(null, EventQueues.APPLICATION, "recargarGrid", null);
      } else {
        Messagebox.show(Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_ERROR_SERVICIO),
            Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK,
            Messagebox.ERROR);

        tipoOperacion.setEstado(!checked);
        BindUtils.postGlobalCommand(null, EventQueues.APPLICATION, "notifyChangeHabilitar", null);
      }
    } else {
      // Si pulso "no" revierte el cambio
      tipoOperacion.setEstado(!checked);

      BindUtils.postGlobalCommand(null, EventQueues.APPLICATION, "notifyChangeHabilitar", null);
    }
  }

}
