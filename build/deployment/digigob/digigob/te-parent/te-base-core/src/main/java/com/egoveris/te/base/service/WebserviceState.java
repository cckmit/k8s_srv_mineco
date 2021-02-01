/**
 * 
 */
package com.egoveris.te.base.service;
 
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;
import javax.transaction.Transactional;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;
import com.egoveris.plugins.tools.ReflectionUtils;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.states.IState;
import com.egoveris.te.base.util.ConstantesWeb;


@Service
@Transactional
public class WebserviceState implements IState {
  private final static Logger logger = LoggerFactory.getLogger(WebserviceState.class);

  private final String ALL_WORKFLOW = "*";
  private String workflowName;
  private String name;
  private ExpedienteElectronicoDTO ee;
  private Task workingTask;

  @Autowired
  private ProcessEngine processEngine;
  private Usuario userLogged;
  private String forwardDesicion = "";
  private String forwardValidation = "";
  private String startScript;
  private Map<String, String> variablesEE;

  @Autowired
  private DocumentoGedoService documentacionGedoService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private ExternalTransaccionService transactionFFCCService;
  
  private WebserviceState(){
	  //Default Constructor
  }
  
  public WebserviceState(Object originState) {
    setForwardDesicion((String) getValue("getForwardDesicion", originState));
    setForwardValidation((String) getValue("getForwardValidation", originState));
    setName((String) getValue("getName", originState));
    setWorkflowName((String) getValue("getWorkflowName", originState));
    setStartScript((String) getValue("getStartScript", originState));
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#setExpedienteElectronico(com.
   * egoveris.te.core.api.expedientes.dominio.ExpedienteElectronico)
   */
  public void setExpedienteElectronico(ExpedienteElectronicoDTO ee) {
    this.ee = ee;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * ar.gob.gcaba.ee.common.workflow.IState#setWorkingTask(org.jbpm.api.task.
   * Task)
   */
  @Override
  public void setWorkingTask(Task task) {
    this.workingTask = task;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#getWorkflowName()
   */
  @Override
  public String getWorkflowName() {
    return this.workflowName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#toApply(ar.gob.gcaba.ee.common.
   * workflow.IState)
   */
  @Override
  public boolean toApply(IState state) {
    if (logger.isDebugEnabled()) {
      logger.debug("toApply(state={}) - start", state);
    }

    boolean returnboolean = state.getName().equals(getName());
    if (logger.isDebugEnabled()) {
      logger.debug("toApply(IState) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#is(java.lang.String,
   * java.lang.String)
   */
  @Override
  public boolean is(String workflowName, String stateName) {
    if (logger.isDebugEnabled()) {
      logger.debug("is(workflowName={}, stateName={}) - start", workflowName, stateName);
    }

    boolean returnboolean = isWorkflow(workflowName) && getName().equals(stateName);
    if (logger.isDebugEnabled()) {
      logger.debug("is(String, String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#isWorkflow(java.lang.String)
   */
  @Override
  public boolean isWorkflow(String workflowName) {
    if (logger.isDebugEnabled()) {
      logger.debug("isWorkflow(workflowName={}) - start", workflowName);
    }

    boolean returnboolean = (getWorkflowName().equals(ALL_WORKFLOW)
        || getWorkflowName().equalsIgnoreCase(workflowName));
    if (logger.isDebugEnabled()) {
      logger.debug("isWorkflow(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * ar.gob.gcaba.ee.common.workflow.IState#isWorkflowEqual(java.lang.String)
   */
  @Override
  public boolean isWorkflowEqual(String workflowName) {
    if (logger.isDebugEnabled()) {
      logger.debug("isWorkflowEqual(workflowName={}) - start", workflowName);
    }

    boolean returnboolean = getWorkflowName().equals(workflowName);
    if (logger.isDebugEnabled()) {
      logger.debug("isWorkflowEqual(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  /**
   * @return the forwardDesicion
   */
  public String getForwardDesicion() {
    return forwardDesicion;
  }

  /**
   * @param forwardDesicion
   *          the forwardDesicion to set
   */
  public void setForwardDesicion(String forwardDesicion) {
    this.forwardDesicion = forwardDesicion;
  }

  /**
   * @return the forwardValidation
   */
  public String getForwardValidation() {
    return forwardValidation;
  }

  /**
   * @param forwardValidation
   *          the forwardValidation to set
   */
  public void setForwardValidation(String forwardValidation) {
    this.forwardValidation = forwardValidation;
  }

  /**
   * @return the userLogged
   */
  public Usuario getUserLogged() {
    return userLogged;
  }

  /**
   * @param userLogged
   *          the userLogged to set
   */
  public void setUserLogged(Usuario userLogged) {
    this.userLogged = userLogged;
  }

  /**
   * @return the processEngine
   */
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  /**
   * @param processEngine
   *          the processEngine to set
   */
  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  /**
   * @return the variablesEE
   */
  public Map<String, String> getVariablesEE() {
    if (logger.isDebugEnabled()) {
      logger.debug("getVariablesEE() - start");
    }

    if (variablesEE == null) {
      variablesEE = new HashMap<>();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getVariablesEE() - end - return value={}", variablesEE);
    }
    return variablesEE;
  }

  /**
   * @param variablesEE
   *          the variablesEE to set
   */
  public void setVariablesEE(Map<String, String> variablesEE) {
    this.variablesEE = variablesEE;
  }

  // ##############################################################
  // ##############################################################
  private void clearDestiny() {
    if (logger.isDebugEnabled()) {
      logger.debug("clearDestiny() - start");
    }

    getVariablesEE().put(ConstantesWeb.ES_USUARIO_DESTINO, "0");
    getVariablesEE().put(ConstantesWeb.ES_SECTOR_DESTINO, "0");
    getVariablesEE().put(ConstantesWeb.ES_REPARTICION_DESTINO, "0");
    getVariablesEE().put(ConstantesWeb.ES_MESA_DESTINO, "0");

    if (logger.isDebugEnabled()) {
      logger.debug("clearDestiny() - end");
    }
  }

  public Object selectUsuario(Object container, String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("selectUsuario(container={}, usuario={}) - start", container, usuario);
    }

    clearDestiny();
    getVariablesEE().put(ConstantesWeb.USUARIO_SELECCIONADO, usuario);

    if (logger.isDebugEnabled()) {
      logger.debug("selectUsuario(Object, String) - end - return value={null}");
    }
    return null;
  }

  public Object selectSector(Object container, String reparticion, String sector) {
    if (logger.isDebugEnabled()) {
      logger.debug("selectSector(container={}, reparticion={}, sector={}) - start", container,
          reparticion, sector);
    }

    clearDestiny();
    getVariablesEE().put(ConstantesWeb.ES_SECTOR_DESTINO, "1");
    getVariablesEE().put(ConstantesWeb.REPARTICION_DESTINO, reparticion);
    getVariablesEE().put(ConstantesWeb.SECTOR_DESTINO, sector);

    if (logger.isDebugEnabled()) {
      logger.debug("selectSector(Object, String, String) - end - return value={null}");
    }
    return null;
  }

  public Object selectReparticion(Object container, String reparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("selectReparticion(container={}, reparticion={}) - start", container,
          reparticion);
    }

    clearDestiny();
    getVariablesEE().put(ConstantesWeb.ES_REPARTICION_DESTINO, "1");
    getVariablesEE().put(ConstantesWeb.REPARTICION_DESTINO, reparticion);

    if (logger.isDebugEnabled()) {
      logger.debug("selectReparticion(Object, String) - end - return value={null}");
    }
    return null;
  }
  // --------------------------------------------------------------
  // --------------------------------------------------------------

  public Map<String, Object> getVariables(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("getVariables(executionId={}) - start", executionId);
    }

    try {
      Set<String> keys = getProcessEngine().getExecutionService().getVariableNames(executionId);

      if (keys != null && !keys.isEmpty()) {
        keys.remove("workingTask");
        Map<String, Object> data = getProcessEngine().getExecutionService()
            .getVariables(executionId, keys);

        if (logger.isDebugEnabled()) {
          logger.debug("getVariables(String) - end - return value={}", data);
        }
        return data;
      }
    } catch (Exception e) {
      logger.error("Error", e);
    }

    Map<String, Object> returnMap = new HashMap<>();
    if (logger.isDebugEnabled()) {
      logger.debug("getVariables(String) - end - return value={}", returnMap);
    }
    return returnMap;
  }

  public void addVariables(Task task, Map<String, Object> values) {
    if (logger.isDebugEnabled()) {
      logger.debug("addVariables(task={}, values={}) - start", task, values);
    }

    if (values != null) {
      Map<String, Object> vars = getVariables(task.getExecutionId());
      vars.putAll(values);
      getProcessEngine().getTaskService().setVariables(task.getId(), vars);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("addVariables(Task, Map<String,Object>) - end");
    }
  }

  /**
   * @return the documentacionGedoService
   */
  public DocumentoGedoService getDocumentacionGedoService() {
    return documentacionGedoService;
  }

  /**
   * @param documentacionGedoService
   *          the documentacionGedoService to set
   */
  public void setDocumentacionGedoService(DocumentoGedoService documentacionGedoService) {
    this.documentacionGedoService = documentacionGedoService;
  }

  /**
   * @return the tipoDocumentoService
   */
  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  /**
   * @param tipoDocumentoService
   *          the tipoDocumentoService to set
   */
  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  /**
   * @return the transactionFFCCService
   */
  public ExternalTransaccionService getTransactionFFCCService() {
    return transactionFFCCService;
  }

  /**
   * @param transactionFFCCService
   *          the transactionFFCCService to set
   */
  public void setTransactionFFCCService(ExternalTransaccionService transactionFFCCService) {
    this.transactionFFCCService = transactionFFCCService;
  }

  public Map<String, Object> getDatosCaratulaVariable(List<DocumentoDTO> documentos) {
    if (logger.isDebugEnabled()) {
      logger.debug("getDatosCaratulaVariable(documentos={}) - start", documentos);
    }

    for (DocumentoDTO doc : documentos) {
      if (doc.getMotivo().equalsIgnoreCase("carátula") && doc.getIdTransaccionFC() != null) {
        ResponseExternalConsultaDocumento response = null;
        try {
          response = getDocumentacionGedoService().consultarDocumentoPorNumero(doc.getNumeroSade(),
              getUserLogged().getUsername());

          if (response.getIdTransaccion() != null) {
            TipoDocumentoDTO tp = getTipoDocumentoService()
                .obtenerTipoDocumento(response.getTipoDocumento());

            if (tp != null && tp.getIdFormulario() != null) {

              try {
                Map<String, Object> data = new HashMap<>();
                TransaccionDTO dto = getTransactionFFCCService()
                    .buscarTransaccionPorUUID(response.getIdTransaccion());
                for (ValorFormCompDTO campo : dto.getValorFormComps()) {
                  data.put(campo.getInputName(), campo.getValor());
                }

                if (logger.isDebugEnabled()) {
                  logger.debug("getDatosCaratulaVariable(List<Documento>) - end - return value={}",
                      data);
                }
                return data;
              } catch (Exception e) {
                logger.warn("getDatosCaratulaVariable(List<Documento>) - exception ignored", e);
              }

            }
          }
        } catch (Exception e) {
          logger.error("error al buscar transaccion por UUID ", e);
        }
        // getEe().getDocumentos().get(1).getIdTransaccionFC()
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getDatosCaratulaVariable(List<Documento>) - end - return value={null}");
    }
    return null;
  }

  protected Map<String, Object> getSharedObject(ExpedienteElectronicoDTO ee2) {
    if (logger.isDebugEnabled()) {
      logger.debug("getSharedObject(ee={}) - start", ee2);
    }

    Map<String, Object> data = new HashMap<>();
    data.put("ee", ee2);
    data.put("state", this);
    data.put("VARS", new HashMap<String, Object>());
    data.put("__isWebService__", new Boolean(true));

    if (logger.isDebugEnabled()) {
      logger.debug("getSharedObject(ExpedienteElectronico) - end - return value={}", data);
    }
    return data;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#initState()
   */
  @Override
  public void initState() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#isValid()
   */
  @Override
  public boolean isValid() {
    if (logger.isDebugEnabled()) {
      logger.debug("isValid() - start");
    }

    Map<String, Object> data = getSharedObject(ee);

    data.put("BPM", getVariables(workingTask.getExecutionId()));
    data.put("usuario", getUserLogged());

    Map<String, Object> CV = getDatosCaratulaVariable(ee.getDocumentos()); // caratula
                                                                           // variable
    if (CV != null && !CV.isEmpty()) {
      data.put("CV", CV);
    }

    Boolean result = true;

    try {
      if (getForwardValidation() != null && !getForwardValidation().isEmpty()) {
        Map<String, Object> returnResult = ScriptUtils.executeScript(getForwardValidation(),
            getSharedObject(ee), "validationBP");
        result = (Boolean) returnResult.get(ScriptUtils.RESULT_KEY);
      }
    } catch (ScriptException e) {
      logger.error("Error", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isValid() - end - return value={}", result);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#takeDecision()
   */
  /*
   * (non-Javadoc)
   * 
   * @see ar.gob.gcaba.ee.common.workflow.IState#takeDecision()
   */
  @Override
  public Object takeDecision() {

    return null;
  }

  public Object takeDecision(String nextState) {
    if (logger.isDebugEnabled()) {
      logger.debug("takeDecision(nextState={}) - start", nextState);
    }

    String siguienteEstado = nextState;
    Map<String, Object> data = getSharedObject(ee);

    Map<String, Object> CV = getDatosCaratulaVariable(ee.getDocumentos()); // caratula
                                                                           // variable
    if (CV != null && !CV.isEmpty()) {
      data.put("CV", CV);
    }

    data.put("windowPase", "none");

    Map<String, Object> bpm = getVariables(workingTask.getExecutionId());
    Map<String, String> vars = getVariablesEE();

    data.put("BPM", bpm);
    data.put("VARS", vars);

    try {
      Map<String, Object> returnResult = ScriptUtils.executeScript(getForwardDesicion(), data,
          "forwardDesicion");

      data = (Map<String, Object>) returnResult.get(ScriptUtils.DATA_KEY);
      siguienteEstado = (String) returnResult.get(ScriptUtils.RESULT_KEY);

    } catch (ScriptException e) {
      logger.error("error takeDecision", e);
    }

    if (data != null) {
      bpm = (Map<String, Object>) data.get("BPM");
      addVariables(workingTask, bpm);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("takeDecision(String) - end - return value={}", siguienteEstado);
    }
    return siguienteEstado;
  }

  private <V> V getValue(String methodName, Object obj) {
    if (logger.isDebugEnabled()) {
      logger.debug("getValue(methodName={}, obj={}) - start", methodName, obj);
    }

    // Field field = ReflectionUtils.getField(obj.getClass(), fieldName);
    // return (V) ReflectionUtils.getFieldValue(field, obj);

    Method method = null;

    try {
      method = obj.getClass().getMethod(methodName);
    } catch (SecurityException e) {
      logger.error("getValue(String, Object)", e);

    } catch (NoSuchMethodException e) {
      logger.error("getValue(String, Object)", e);

    }

    V returnV = (V) (method != null ? ReflectionUtils.invokeMethod(method, obj) : null);
    if (logger.isDebugEnabled()) {
      logger.debug("getValue(String, Object) - end - return value={}", returnV);
    }
    return returnV;
  }

	public String getStartScript() {
		return startScript;
	}
	
	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}
	  
  
  

}
