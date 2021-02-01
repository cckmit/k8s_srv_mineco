package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AbstractWorkFlowComposer extends GEDOGenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -1911208049935423635L;
  /**
  * 
  */
  @WireVariable("processEngine")
  protected ProcessEngine processEngine;
  protected Task workingTask = null;
  private org.slf4j.Logger logger = org.slf4j.LoggerFactory
      .getLogger(AbstractWorkFlowComposer.class);

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
    component.getDesktop().removeAttribute("selectedTask");
  }

  protected final String getLongStringFromWorkFlow(String name) {
    byte[] contenidoBytes = (byte[]) this.getVariableWorkFlow(name);
    try {
      return new String(contenidoBytes, "UTF-8");
    } catch (UnsupportedEncodingException uee) {
      logger.error("Error al recuperar contenido del documento", uee);
      return "<EL CONTENIDO DEL DOCUMENTO NO PUDO SER RECUPERADO. CONSULTE A SU ADMINISTRADOR>";
    }
  }

  protected final void setLongStringVariableWorkflow(String name, String value) {
    try {
      this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
          value.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException uee) {
      logger.error("Ocurrio un error al setear la variable LongString " + name, uee);
      this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
          "<OCURRIO UN ERROR AL ESTABLECER EL VALOR DE LA VARIABLE" + name);
    }
  }

  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException(
          Labels.getLabel("gedo.archivosEmbebidosComposer.exception.noVariable")
              + this.workingTask.getExecutionId() + ", " + name);
    }
    return obj;
  }

  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
    setVariableWorkFlow(Constantes.VAR_USUARIO_DERIVADOR, usernameDerivador);
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  /**
   * Cancela la tarea actual.
   */
  protected void cancelarTarea() {
    processEngine.getExecutionService().endProcessInstance(this.workingTask.getExecutionId(),
        ProcessInstance.STATE_ENDED);

  }

  public List<Usuario> getUsuariosGEDO() {
    try {
      return getUsuarioService().obtenerUsuarios("*");
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }
    return null;
  }

  public String getCurrentUser() {
    return (String) this.self.getDesktop().getSession().getAttribute(Constantes.SESSION_USERNAME);
  }

  // Funciones comunes para el sistema de avisos.

  /**
   * Adiciona el usuario actual a la lista de usuarios receptores de un aviso
   * relacionado al documento en proceso. Valida que el usuario se encuentre en
   * la lista, si la variable no existe, la crea.
   */

  protected void adicionarReceptoresAviso() {
    List<String> receptores = new ArrayList<String>();
    String usuarioApoderador = null;
    boolean listaModificada = false;
    boolean variableNoExiste = false;

    try {
      receptores = (List<String>) this.getVariableWorkFlow(Constantes.VAR_RECEPTORES_AVISO_FIRMA);
      if (!receptores.contains(getCurrentUser())) {
        receptores.add(getCurrentUser());
        listaModificada = true;
      }
      usuarioApoderador = (String) this.processEngine.getExecutionService()
          .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
      if (!StringUtils.isEmpty(usuarioApoderador) && !receptores.contains(usuarioApoderador)) {
        receptores.add(usuarioApoderador);
        listaModificada = true;
      }
      if (listaModificada)
        this.setVariableWorkFlow(Constantes.VAR_RECEPTORES_AVISO_FIRMA, receptores);
    } catch (VariableWorkFlowNoExisteException e) {
      variableNoExiste = true;
      logger.error("Error al adicionar receptores de aviso " + e.getMessage(), e);
      
    }
    
  }

  /**
   * Elimina un usuario de la lista de usuarios receptores de un aviso
   * relacionado al documento en proceso. Valida que la variable exista y que el
   * usuario se encuentre en la lista.
   */

  protected void eliminarReceptoresAviso() {
    List<String> receptores = new ArrayList<String>();
    String usuarioApoderador = null;
    boolean listaModificada = false;

    try {
      receptores = (List<String>) this.getVariableWorkFlow(Constantes.VAR_RECEPTORES_AVISO_FIRMA);
      if (receptores.contains(getCurrentUser())) {
        receptores.remove(getCurrentUser());
        listaModificada = true;
      }
      usuarioApoderador = (String) this.processEngine.getExecutionService()
          .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
      if (!StringUtils.isEmpty(usuarioApoderador) && receptores.contains(usuarioApoderador)) {
        receptores.remove(usuarioApoderador);
        listaModificada = true;
      }
      if (listaModificada)
        this.setVariableWorkFlow(Constantes.VAR_RECEPTORES_AVISO_FIRMA, receptores);
    } catch (VariableWorkFlowNoExisteException e) {
      logger.error("Error al eliminar receptores aviso " + e.getMessage(), e);
    }
  }

  /**
   * Carga el estado de la solicitud de aviso.
   */
  protected boolean estadoSolicitudAviso() {
    try {
      List<String> receptores = (List<String>) this
          .getVariableWorkFlow(Constantes.VAR_RECEPTORES_AVISO_FIRMA);
      return receptores.contains(getCurrentUser());
    } catch (VariableWorkFlowNoExisteException vwe) {
      logger.error("Error al cargar el estado de la solicitud de aviso " + vwe.getMessage(), vwe);
      return false;
    }
  }

}