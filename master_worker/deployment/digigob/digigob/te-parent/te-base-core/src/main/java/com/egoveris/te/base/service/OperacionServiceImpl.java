package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import com.egoveris.deo.model.exception.NegocioException;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.ExpedientTransaction;
import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Operacion;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpediente;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.SubProceso;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacion;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.SubProcesoOperacionPk;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.ExpedientTransactionRepository;
import com.egoveris.te.base.repository.OperacionRepository;
import com.egoveris.te.base.repository.SolicitudExpedienteRepository;
import com.egoveris.te.base.repository.SubProcesoOperacionRepository;
import com.egoveris.te.base.repository.SubProcesoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlowScriptUtils;

@Service(ConstantesServicios.OPERACION_SERVICE)
@Transactional
public class OperacionServiceImpl implements OperacionService {

  private static final Logger logger = LoggerFactory.getLogger(OperacionServiceImpl.class);
  
  @Autowired
  private OperacionRepository operacionRepository;
  @Autowired
  private WorkFlowService workflowService;
  @Autowired
  private SubProcesoRepository subprocesoRepository;
  @Autowired
  private TrataService trataService;
  @Autowired
  private SolicitudExpedienteRepository solicitudExpedienteRepository;
  @Autowired
  private SubProcesoOperacionRepository subprocesoOpRepository;
  private DozerBeanMapper mapper = new DozerBeanMapper();
  @Autowired
  private ExpedientTransactionRepository expedientTransactionRepository;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoServiceImpl;
  
  @Override
  public OperacionDTO saveOrUpdate(final OperacionDTO operacionDto) throws NegocioException {
    try {
      if (StringUtils.isEmpty(operacionDto.getJbpmExecutionId())) {
        operacionDto.setJbpmExecutionId("0");
      }
      
      if (operacionDto.getIdSectorInterno() == null) {
        operacionDto.setIdSectorInterno(0);
      }
      
      Operacion opEnt = mapper.map(operacionDto, Operacion.class); 
      opEnt.setTipoOperacion(operacionDto.getTipoOperacionOb().getId());
      operacionRepository.saveAndFlush(opEnt);
      
      OperacionDTO opDtoRetorno = mapper.map(opEnt, OperacionDTO.class);
      opDtoRetorno.setTipoOperacion(opEnt.getTipoOperacion());
      return opDtoRetorno;
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.saveOrUpdate(): ", e);
      throw new NegocioException(e.getMessage(), e);
    }
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public List<OperacionDTO> getOperaciones() throws NegocioException {
    try {
      List<Operacion> operacionesEnt = operacionRepository.findAll();
      List<OperacionDTO> operaciones = new ArrayList<>();
      
      operaciones.addAll(ListMapper.mapList(operacionesEnt, mapper, OperacionDTO.class));
      return operaciones;
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.getOperaciones(): ", e);
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @Override
  public List<String> getTransicionesOperacion(final String processId) throws NegocioException {
    try {
      final List<String> actividades = new ArrayList<>();
      
      workflowService.getProcessEngine().execute(new Command<Object>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Object execute(final Environment env) {
          final ExecutionImpl exe = (ExecutionImpl) workflowService.getProcessEngine()
              .getExecutionService().findExecutionById(processId);
          
          if (null != exe) {
            final ActivityImpl act = exe.getActivity();
            
            for (final Transition name : act.getOutgoingTransitions()) {
              actividades.add(name.getName());
            }
          }
          
          return env;
        }
      });

      return actividades;
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.getTransicionesOperacion(): ", e);
      return new ArrayList<>();
    }
  }

  @Override
  public OperacionDTO confirmarOperacion(final OperacionDTO operacion) throws NegocioException {
    try {
      final Long idWorkflow = operacion.getTipoOperacionOb().getWorkflow();
      final ProcessDefinitionQuery query = workflowService.getProcessEngine()
          .getRepositoryService().createProcessDefinitionQuery();
      query.deploymentId(idWorkflow.toString());
      final ProcessDefinition process = query.uniqueResult();
      final String workflow = process.getKey();
      
      final ProcessInstance processInstance = workflowService.startWorkFlowAndReturnInstance(workflowService.getProcessEngine(),
              workflow, null);
      final String processID = processInstance.getId();
      final Execution exe = processInstance.getProcessInstance();
      
      operacion.setEstadoBloq(BloqueoOperacion.NINGUNO.getValue());
      operacion.setEstadoOperacion(exe.findActiveActivityNames().iterator().next());
      operacion.setJbpmExecutionId(processID);
      operacion.setNumOficial(operacion.getCodigoCaratula());
      Operacion op = operacionRepository.save(mapper.map(operacion, Operacion.class));
      return  mapper.map(op, OperacionDTO.class);
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.confirmarOperacion(): ", e);
      throw new NegocioException(e.getMessage(), e);
    }
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public List<SubProcesoDTO> getWorkFlowSubProcess(final Long idWorkFlow, final String executionId,
      final String typeSubProcess, final Integer versionProcedure) throws NegocioException {
    List<SubProcesoDTO> subprocess = new ArrayList<>();
    
    try {
      final ProcessInstance processInstance = workflowService.getProcessEngine().getExecutionService()
          .findProcessInstanceById(executionId);
      
      if (processInstance != null) {
        // Obtengo el processDefinition WorkFlow
        final ProcessDefinitionQuery query = workflowService.getProcessEngine().getRepositoryService()
            .createProcessDefinitionQuery();
        query.processDefinitionId(processInstance.getProcessDefinitionId());
        
        // Get version
        final ProcessDefinition processDefinition = query.uniqueResult();
        final String stateFlow = processDefinition.getKey();
        final int version = processDefinition.getVersion();
        
        // Obtengo el estado actual de la operacion
        String stateName = processInstance.findActiveActivityNames().iterator().next();
        
        subprocess = ListMapper.mapList(
            subprocesoRepository.findSubProcess(stateFlow, stateName, version, typeSubProcess, versionProcedure),
            mapper, SubProcesoDTO.class);
      }
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.getWorkFlowSubProcess(): ", e);
      throw new NegocioException(e.getMessage(), e);
    }
    
    return subprocess;
  }
  
  @Override
  public ExpedienteElectronicoDTO iniciarSubProceso(final SubProcesoDTO subproceso, final OperacionDTO operacion,
      final String username, String motivo) throws NegocioException {
    
	try {
	  final SolicitudExpedienteDTO solicitudExpedienteDto = new SolicitudExpedienteDTO();
	  final SolicitanteDTO solicitanteDto = new SolicitanteDTO();
	  
	  if (motivo == null || StringUtils.isBlank(motivo)) {
	    motivo = "Iniciar Proceso";
	  }
	  
	  final Long idTrata = subproceso.getTramite().getId();
	  solicitudExpedienteDto.setEsSolicitudInterna(true);
	  solicitudExpedienteDto.setFechaCreacion(new Date());
	  solicitudExpedienteDto.setIdTrataSugerida(idTrata);
	  solicitudExpedienteDto.setMotivo(motivo);
	  solicitudExpedienteDto.setMotivoDeRechazo("");
	  solicitudExpedienteDto.setSolicitante(solicitanteDto);
	  solicitudExpedienteDto.setUsuarioCreacion(username);
	 
	  solicitudExpedienteDto.setIdOperacion(operacion.getId());
	  
	  SolicitudExpediente solicitudEnt = mapper.map(solicitudExpedienteDto, SolicitudExpediente.class);
	  solicitudEnt = solicitudExpedienteRepository.save(solicitudEnt);
	  
	  solicitudExpedienteDto.setId(solicitudEnt.getId());
	  
	  final TrataDTO trata = trataService.buscarTrataporId(idTrata);
	  
      final ExpedienteElectronicoDTO ee = ExpedienteElectronicoFactory.getInstance()
          .crearExpedienteElectronico(workflowService.getProcessEngine(), solicitudExpedienteDto,
              trata, "", null, username, motivo);
      
      if (ee != null) {
    	  
        final SubProcesoOperacion subprocesoOp = new SubProcesoOperacion();
        final SubProcesoOperacionPk pk = new SubProcesoOperacionPk();
        pk.setIdOperacion(operacion.getId().longValue());
        pk.setIdExpediente(ee.getId().longValue());
        pk.setIdSubproceso(subproceso.getId());
        subprocesoOp.setPk(pk);
        subprocesoOpRepository.save(subprocesoOp);
        return ee;
      }
      
      return null;
    } catch (final Exception e) {
      logger.error("Error en OperacionServiceImpl.iniciarSubProceso(): ", e);
      throw new NegocioException("Error al iniciar subproceso", e);
    }
	
  }
  
  @Override
  public OperacionDTO getOperacionById(final Long id) {
    Operacion operacionEnt = operacionRepository.findById(id);
    return mapper.map(operacionEnt, OperacionDTO.class);
  }

  @Override
  public OperacionDTO getOperacionByNumOfic(String numOfic) {
    Operacion operacionEnt = operacionRepository.findByNumOficial(numOfic);
    return mapper.map(operacionEnt, OperacionDTO.class);
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public List<SubProcesoOperacionDTO> getSubProcesos(final Long idOperacion) {
	  List<SubProcesoOperacion> subprocesosOp = subprocesoOpRepository.findByPkIdOperacion(idOperacion);
	  return ListMapper.mapList(subprocesosOp, mapper, SubProcesoOperacionDTO.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<OperacionDTO> getOperacionesBySector(Integer idSector) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("getOperacionesBySector(idSector) - start");
    }
    
    List<OperacionDTO> operacionesBySector = new ArrayList<>();
    List<Operacion> operacionesEnt = operacionRepository.findByIdSectorInternoOrderByFechaInicioDesc(idSector);
    
    if (!operacionesEnt.isEmpty()) {
    	operacionesBySector = ListMapper.mapList(operacionesEnt, mapper, OperacionDTO.class);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getOperacionesBySector(OperacionDTO) - end");
    }
    
    return operacionesBySector;
  }
  
  @Override
  public List<SubProcesoOperacionDTO> startAutomaticSubProcess(OperacionDTO operacion) throws NegocioException {
	List<SubProcesoOperacionDTO> listSubprocess = new ArrayList<>();
	try {
		  List<SubProcesoDTO> subProcessWorkFlow = getWorkFlowSubProcess(operacion.
				  getTipoOperacionOb().getWorkflow(), operacion.getJbpmExecutionId(), "Automatico", 
				  operacion.getVersionProcedure());
      if (CollectionUtils.isNotEmpty(subProcessWorkFlow)) {
        List<SubProcesoOperacion> subProcessOperation = operacionRepository.findAllSubProcess(operacion.getId());
        Iterator<SubProcesoDTO> iterator = subProcessWorkFlow.iterator();
        
        while (iterator.hasNext()) {
          SubProcesoDTO s = iterator.next();
          for (SubProcesoOperacion sub : subProcessOperation) {
            if (s.getId().equals(sub.getSubproceso().getId())) {
              iterator.remove();
            }
          }
        }
        
        // Se inician los subprocesos no iniciados anteriormente
        for (SubProcesoDTO s : subProcessWorkFlow) {
          ExpedienteElectronicoDTO ee = iniciarSubProceso(s, operacion, Executions.getCurrent().getRemoteUser(), null);
          
          // Se agrega el subproceso a la lista de retorno
          // para saber cuales se iniciaron
          SubProcesoOperacionDTO subProcesoOperacion = new SubProcesoOperacionDTO();
          subProcesoOperacion.setExpediente(ee);
          subProcesoOperacion.setOperacion(operacion);
          subProcesoOperacion.setSubproceso(s);
          listSubprocess.add(subProcesoOperacion);
        }
      }
    } catch (Exception e) {
      logger.error("Error al iniciar subprocesos automaticos", e);
      throw new NegocioException("Error al iniciar subprocesos automaticos", e);
    }
    
    return listSubprocess;
  }

	@Override
	public void iniciarSubProceso(Long idSubProcess, Long idOperacion, String username, String procedureName)
			throws NegocioException {
		
		 String userNameInit = username;
		 SubProcesoDTO s;
		 if(idSubProcess != null){
			 s = mapper.map(subprocesoRepository.findOne(idSubProcess), SubProcesoDTO.class);
		 } else {
			 Page<SubProceso> sub = subprocesoRepository.findByNameSubprocess(procedureName, new PageRequest(0, 1));
			 if(CollectionUtils.isNotEmpty(sub.getContent())){
				 s = mapper.map(sub.getContent().get(0), SubProcesoDTO.class);
			 } else {
				 throw new NegocioException("not found subprocess " + procedureName);
			 }
		 }
		 OperacionDTO operacion  = mapper.map(operacionRepository.findOne(idOperacion), OperacionDTO.class);
		 if(StringUtils.isBlank(userNameInit)){
			 SubProcesoOperacion subOp = subprocesoOpRepository.getSubProcessByOperation(s.getId(), idOperacion);
			 ExpedienteElectronico ee = subOp.getExpediente();
			 userNameInit = ee.getUsuarioCreador();
		 }
		 iniciarSubProceso(s, operacion, userNameInit, null);
	}

	@Override
	public boolean isValidTransaction(String idTransaction) throws NegocioException {
		try {
			ExpedientTransaction exp = expedientTransactionRepository.findActivTransaction(idTransaction);
			if(exp == null){
				return false;
			}
			ExpedienteElectronicoDTO ee = expedienteElectronicoServiceImpl.buscarExpedienteElectronico(exp.getIdExpedient());
			if(ee == null){
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Error al obtener transaction",e);
		}
		return false;
	}
	
	@Override
	 public ExpedientTransactionDTO findLastActiveTransaction(String idTransaction)  throws NegocioException{
		 try {
			 ExpedientTransaction exp = expedientTransactionRepository.findActivTransaction(idTransaction);
			 return mapper.map(exp, ExpedientTransactionDTO.class);
		} catch (Exception e) {
			logger.error("Error get last transaction", e);
			throw new NegocioException("Error get last transaction", e);
		}
	}
	
	@Override
	 public ExpedientTransactionDTO findLastTransaction(String idTransaction)  throws NegocioException{
		 try {
			 ExpedientTransaction exp = expedientTransactionRepository.findTransaction(idTransaction);
			 return mapper.map(exp, ExpedientTransactionDTO.class);
		} catch (Exception e) {
			logger.error("Error get last transaction", e);
			throw new NegocioException("Error get last transaction", e);
		}
	}
	
	
	/**
	 * metodo temporal hasta que se solucione los transactional
	 */
	@Override
	public void initScriptSubprocess(OperacionDTO operacion)  throws NegocioException {
		try {
			List<SubProcesoOperacion> subprocess = subprocesoOpRepository.findByPkIdOperacion(operacion.getId());
			for(SubProcesoOperacion s : subprocess){
				ExpedienteElectronicoDTO ee = mapper.map(s.getExpediente(), ExpedienteElectronicoDTO.class);
				ExpedientTransaction exp = expedientTransactionRepository.getByIdExpedient(ee.getId());
				if(exp == null){
					WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, ee, null, "JQUINTAU");
				}
			}
		} catch (Exception e) {
			logger.error("Error init subprocess", e);
			throw new NegocioException("error inicialize subprocess",e);
		}
		
	}
	
	@Override
	public Integer getVersionProcedureProject(Long idWorkFlow)  throws NegocioException{
		try {
			final ProcessDefinitionQuery query = workflowService.getProcessEngine()
			          .getRepositoryService().createProcessDefinitionQuery().deploymentId(idWorkFlow.toString());
			final ProcessDefinition processDefinition = query.uniqueResult();
			final String stateFlow = processDefinition.getKey();
			List<Integer> num= subprocesoRepository.findLastVersionOfProcedure(stateFlow);
			if(CollectionUtils.isNotEmpty(num)){
				return num.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("Error get version of procedure", e);
			throw new NegocioException("error to get version of procedure",e);
		}
	}

	@SuppressWarnings("unchecked")
  @Override
	public List<OperacionDTO> getOperacionBySectoAndNumOficAndEstado(Integer id, String numOfic, String estadoOpe) { 
	  List<OperacionDTO> out = new ArrayList<>(); 
	  List<Operacion> operacionEnt; 
	  if (null != numOfic && !numOfic.isEmpty() && null != estadoOpe && !estadoOpe.isEmpty() ) { 
		    operacionEnt = operacionRepository.findByIdSectorInternoAndNumOficialAndEstadoOperacion(id, numOfic, estadoOpe); 
	  } else {
		  if (null == numOfic || numOfic.isEmpty()) {
			  operacionEnt = operacionRepository.findByIdSectorInternoAndEstadoOperacion(id, estadoOpe);
		  } else {
			  operacionEnt = operacionRepository.findByIdSectorInternoAndNumOficial(id, numOfic);
		  }
	  } 
	  if (null != operacionEnt) {
		  out.addAll(ListMapper.mapList(operacionEnt, mapper, OperacionDTO.class)); 
	  } 
      return out;
	}

}
