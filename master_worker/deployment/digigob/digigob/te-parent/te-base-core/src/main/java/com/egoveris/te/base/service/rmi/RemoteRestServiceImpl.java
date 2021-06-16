package com.egoveris.te.base.service.rmi;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zk.ui.Component;

import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.internal.service.TransactionESBService;
import com.egoveris.te.base.model.ExpedientTransaction;
import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Operacion;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.RegistryTransaction;
import com.egoveris.te.base.model.SubProcesoOperacion;
import com.egoveris.te.base.repository.ExpedientTransactionRepository;
import com.egoveris.te.base.repository.OperacionRepository;
import com.egoveris.te.base.repository.RegistryTransactionRepository;
import com.egoveris.te.base.repository.SubProcesoOperacionRepository;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.trade.common.model.AcknowledgeDTO;
import com.egoveris.trade.common.model.RequestMessageDTO;
import com.egoveris.trade.common.model.ResponseMessageDTO;

@Service
@Transactional
public class RemoteRestServiceImpl implements RemoteRestService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RemoteRestServiceImpl.class);

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoServiceImpl;
	@Autowired
	private WorkFlowService workflowService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ExpedientTransactionRepository expedientTransactionRepository;
	@Autowired
	private RegistryTransactionRepository registryTransactionRepository;
	@Autowired
	private SubProcesoOperacionRepository subProcesoOperacionRepository;
	@Autowired
	private IExternalTipoDocumentoService tipoDocumentoService;
	@Autowired
	private ExternalFormularioService externalFormularioService;
	@Autowired
	private IFormManagerFactory<IFormManager<Component>> zkFormManagerFactory;
	@Autowired
	private OperacionRepository operacionRepository;
	@Autowired
	private OperacionService operacionService;
	@Autowired
	private TransactionESBService transactionESBServiceImpl;
	private IFormManager<Component> manager;
	@Value("${app.fuse.endpoint.url}")
	private String IP_FUSE;
	private DozerBeanMapper mapper = new DozerBeanMapper();
	private final  String SEND_REQUEST = "SEND REQUEST";
	private final  String ESB_RESPONSE = "ESB REQUEST";
	
	@Override
	public Object invokeRemoteService(String code, String message, ExpedienteElectronicoDTO ee, 
				String idTransactionFFCC, String nameFFCC, String typeCall) throws NegocioException, ScriptException {
		if (logger.isDebugEnabled()) {
			logger.debug("invokeRemoteService(String, String, ExpedienteElectronicoDTO) - start"); //$NON-NLS-1$
		}
		ResponseMessageDTO formResponse;
		AcknowledgeDTO response;
		try {
			logger.info("invokeRemoteService(String, String, ExpedienteElectronicoDTO)"); //$NON-NLS-1$
			SubProcesoOperacion subOperation = subProcesoOperacionRepository.findByExpedienteElectronico(ee.getId());
			if("COD02".equalsIgnoreCase(code)){
				ExpedientTransaction exCode = expedientTransactionRepository.getByIdExpedientAndCode(ee.getId(), code);
				if(exCode != null){
					logger.info("The notification of start task  has already been sent");
					return null;
				}
			}
			String idTransaction = getTransaction(code, message, ee, subOperation);
			registryTransaction(idTransaction, SEND_REQUEST, null);
			
			
			//set data to ESB
			RequestMessageDTO request = new RequestMessageDTO();
			if(subOperation != null &&  subOperation.getPk() != null 
					&& subOperation.getPk().getIdOperacion() != null){
				request.setIdOperation(subOperation.getPk().getIdOperacion());
			}
			
			String[] params = {idTransactionFFCC,nameFFCC};
			request.setCode(code);
			request.setIdMessage(message);
			request.setIdTransaction(idTransaction);
			request.setTypeCall(typeCall);
			determineForm(code, request, subOperation, params);
			
			// send Data and get Response
			logger.info("invokeRemoteService(String, String, ExpedienteElectronicoDTO)"); //$NON-NLS-1$
			RestTemplate restTemplate = new RestTemplate();
		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		    ResponseEntity<ResponseMessageDTO> responseBody = restTemplate.postForEntity(IP_FUSE , request , ResponseMessageDTO.class);
			formResponse = responseBody.getBody();
            response = formResponse.getAcknowledge();
            if("SYNC".equalsIgnoreCase(typeCall)){
	            Map<String, Object> paramsScript = new HashMap<>();
	            paramsScript.put(ConstantesCommon.SCRIPT_WINDOW_PASE, null);
	            paramsScript.put(ConstantesCommon.SCRIPT_FORM,  formResponse.getForm());
	            paramsScript.put(ConstantesCommon.SCRIPT_FORM_VALUES,  formResponse.getFormValues());
				WorkFlowScriptUtils.getInstance().executeScript(ScriptType.REMOTE_TASK, ee, paramsScript, ee.getUsuarioCreador());
            }
            registryTransaction(idTransaction, ESB_RESPONSE, response);		
	} catch (ScriptException e) {
		logger.error("Error: "  + e);
		throw new ScriptException(e);
	} catch (Exception e) {
		logger.error("Error: "  + e);
		throw new NegocioException("Error call remote service" , e);
	}
		
	if (logger.isDebugEnabled()) {
		logger.debug("invokeRemoteService(String, String, ExpedienteElectronicoDTO) - end"); //$NON-NLS-1$
	}
	
	return formResponse;
	}

	
	
	@Override
	public Object invokeRemoteServiceOperation(OperacionDTO operation, String code, String message, String typeCall) throws NegocioException, ScriptException {
		if (logger.isDebugEnabled()) {
			logger.debug("invokeRemoteService(String, String, ExpedienteElectronicoDTO) - start"); //$NON-NLS-1$
		}
		ResponseMessageDTO formResponse = new ResponseMessageDTO();
//		AcknowledgeDTO response;
//		try {
//			logger.info("invokeRemoteService(String, String, ExpedienteElectronicoDTO)"); //$NON-NLS-1$
//			String idTransaction = getTransaction(code, message, operation);
//			registryTransaction(idTransaction, SEND_REQUEST, null);
//			
//			//set data to ESB
//			RequestMessageDTO request = new RequestMessageDTO();
//			request.setCode(code);
//			request.setIdMessage(message);
//			request.setIdTransaction(idTransaction);
//			request.setTypeCall(typeCall);
//			
//			// send Data and get Response
//			logger.info("invokeRemoteService(String, String, ExpedienteElectronicoDTO)"); //$NON-NLS-1$
//			RestTemplate restTemplate = new RestTemplate();
//		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//		    ResponseEntity<ResponseMessageDTO> responseBody = restTemplate.postForEntity(IP_FUSE , request , ResponseMessageDTO.class);
//			formResponse = responseBody.getBody();
//            response = formResponse.getAcknowledge();
//            registryTransaction(idTransaction, ESB_RESPONSE, response);
//			
//	} catch (Exception e) {
//		logger.error("Error: "  + e);
//		throw new NegocioException("Error call remote service" , e);
//	}
//		
//	if (logger.isDebugEnabled()) {
//		logger.debug("invokeRemoteService(String, String, ExpedienteElectronicoDTO) - end"); //$NON-NLS-1$
//	}
	
	return formResponse;
	}


	
	@Override
	public void executeDesicion(Task workingTask, ExpedienteElectronicoDTO exp, String user, String estadoSeleccionado,
			String motivoExpediente, Map<String, String> detalles) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("executeDesicion(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>) - start"); //$NON-NLS-1$
		}
		
		try {
			Map<String, String> detallesTemp = detalles;
			if(detallesTemp == null){
				detallesTemp = new HashMap<>();
			}
			expedienteElectronicoServiceImpl.generarPaseExpedienteElectronico(workingTask, exp, user , estadoSeleccionado, motivoExpediente, detallesTemp);
			WorkFlow workFlow = workflowService.createWorkFlow(exp.getId(), estadoSeleccionado);
			detallesTemp.put(ConstantesWeb.USUARIO_SELECCIONADO, user);
	    	workFlow.initParameters(detallesTemp);
	    	workFlow.execute(workingTask, processEngine);
	    } catch (Exception e) {
			logger.error("executeDesicion(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>)", e); //$NON-NLS-1$
	    }
		
		if (logger.isDebugEnabled()) {
			logger.debug("executeDesicion(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void updateTransaction(String idTransaction) throws NegocioException {
		try {
			ExpedientTransaction  e = expedientTransactionRepository.getByIdTransaction(idTransaction);
			if(e != null){
				e.setStatus(0);
				expedientTransactionRepository.save(e);
			}
		} catch (Exception e) {
			logger.error("executeDesicion(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>)", e); //$NON-NLS-1$
	    }
		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ExpedientTransactionDTO> getTransactions() throws NegocioException{
		try {
			return ListMapper.mapList(expedientTransactionRepository.getAllActivesTransactions(), mapper,
					ExpedientTransactionDTO.class); 
		} catch (Exception e) {
			logger.error("",e);
			throw new NegocioException("Error", e);
		}
	}

	@Override
	public boolean validateStructureForm(String nameForm ,FormularioDTO form) throws NegocioException {
		try {
			logger.info("Request FORM " + (null == form ? "NULL"  : form.getNombre()));
			boolean containsFields = true;
			ResponseTipoDocumento doc =tipoDocumentoService.buscarTipoDocumentoByAcronimo(nameForm);
			if(doc == null){
				throw new NegocioException("Type of Document NULL [" + nameForm + "]");
			} else {	
				FormularioDTO formulario = externalFormularioService.buscarFormularioPorNombre(doc.getIdFormulario());
				Set<FormularioComponenteDTO> fields = formulario.getFormularioComponentes();
				Iterator<FormularioComponenteDTO> it = fields.iterator();
				while(it.hasNext()){
					 FormularioComponenteDTO f = it.next();
					 String n1 = f.getComponente().getNombre();
					 if(getComponentName(n1, form) == null){
						 containsFields = false;
						 break;
					 }
				}
				return containsFields;
			}
		} catch (Exception e) {
			logger.error("",e);
			throw new NegocioException("Error", e);
		}
		
	}

	@Override
	public void blockExpedient(ExpedienteElectronicoDTO ee) throws NegocioException {
		try {
			ee.setBlocked(true);
			expedienteElectronicoServiceImpl.grabarExpedienteElectronico(ee);
		} catch (Exception e) {
			logger.error("",e);
			throw new NegocioException("",e);
		}
		
	}
	
	@Override
	public void blockOperation(ExpedienteElectronicoDTO ee) throws NegocioException {
		try {
			 if(ee == null || ee.getIdOperacion() == null){
				 throw new NegocioException("EE null");
			 }
			 Long idOperation = Long.valueOf(ee.getIdOperacion());
			 Operacion operation  = operacionRepository.findOne(idOperation);
			 operation.setEstadoBloq(BloqueoOperacion.TOTAL.toString());
			 operacionRepository.save(operation);
		} catch (Exception e) {
			logger.error("",e);
			throw new NegocioException("",e);
		}
		
	}

	
	private String getComponentName(String name , FormularioDTO form){
		if(form == null)
			return null;
		Set<FormularioComponenteDTO> fields =  form.getFormularioComponentes();
		if(fields != null){
			Iterator<FormularioComponenteDTO> it = fields.iterator();
			while(it.hasNext()){
				 FormularioComponenteDTO f = it.next();
				 if(f.getComponente() != null &&
						 StringUtils.isNotBlank(f.getComponente().getNombre())){
					 String n1 = f.getComponente().getNombre();
					 if(n1.equalsIgnoreCase(name)){
						 return n1;
					 }
				 } else {
					 break;
				 }
			}
		}
		return null;
	}
	
	@Override
	public void initSubprocess(ExpedienteElectronicoDTO ee, String nameSubprocess) {
		try {
			SubProcesoOperacion subOperation = subProcesoOperacionRepository.findByExpedienteElectronico(ee.getId());
			String user = ee.getUsuarioCreador();
			operacionService.iniciarSubProceso(null, subOperation.getPk().getIdOperacion(), user, nameSubprocess);
		} catch (Exception e) {
			logger.error("Error init subprocess script ",e);
		}
		
	}
	
	
	private String getNewTransaction(String code, String message, Long eeId, Long operationId) throws ProcesoFallidoException{
		try {
			String idTransaction = transactionESBServiceImpl.getNewTransaction();
			// save transaction
			ExpedientTransaction transaction = new ExpedientTransaction();
			transaction.setCode(code);
			transaction.setMessage(message);
			transaction.setIdExpedient(eeId);
			transaction.setDateCreation(new Date());
			transaction.setStatus(1);
			transaction.setIdTransaction(idTransaction);
			transaction.setIdOperation(operationId);
			
			expedientTransactionRepository.save(transaction);
			return idTransaction;
		} catch (Exception e) {
			throw new ProcesoFallidoException("",e);
		}
	}
	
	
	private String getTransaction(String code, String message,ExpedienteElectronicoDTO ee, SubProcesoOperacion subOperation){
		String idTransaction;
		if("COD2".equalsIgnoreCase(code)  || "COD4".equalsIgnoreCase(code)){
			  ExpedientTransaction expTransaction = expedientTransactionRepository.getByIdExpedient(ee.getId());
			  if(expTransaction != null && StringUtils.isNoneBlank(expTransaction.getIdTransaction())){
				  idTransaction = expTransaction.getIdTransaction();
			  } else {
				  idTransaction = getNewTransaction(code, message, ee.getId(), subOperation.getPk().getIdOperacion());
			  }
		} else {
			 idTransaction = getNewTransaction(code, message,  ee.getId(), subOperation.getPk().getIdOperacion());
		}
	
		return idTransaction;
	}
	
	
	private String getTransaction(String code, String message, OperacionDTO operation){
		return getNewTransaction(code, message, null, operation.getId());
	}
	
	private void registryTransaction(String idTransaction, String typeMessage,  AcknowledgeDTO response){
		// save request to ESB
		RegistryTransaction registry = new RegistryTransaction();
		registry.setIdTransaction(idTransaction);
		registry.setMessage(typeMessage);
		if(response != null){
			registry.setIdMessage(response.getIdMessage());
			registry.setReceptionCode(response.getReceptionCode());
			registry.setReceptionDescription(response.getReceptionDescription());
		}
		registry.setRequestDate(new Date());
		registry.setResponseDate(null);
		registryTransactionRepository.save(registry);
	}

	private void determineForm(String code, RequestMessageDTO request,SubProcesoOperacion subOperation, String... params){
		FormularioWDDTO form = new FormularioWDDTO();
		if("COD03".equalsIgnoreCase(code)){
			if(StringUtils.isNotBlank(params[0]) && 
					StringUtils.isNotBlank(params[1])){
				ResponseTipoDocumento doc = tipoDocumentoService.buscarTipoDocumentoByAcronimo(params[1]);
				String  formName = doc.getIdFormulario();
				form.setNombre(formName);
				manager = zkFormManagerFactory.create(formName);
				manager.fillCompValues(Integer.valueOf(params[0]));
				request.setFormValues(manager.getValues());
			}
		} else if("COD2".equalsIgnoreCase(code) || "COD4".equalsIgnoreCase(code)){
			ResponseTipoDocumento doc = tipoDocumentoService.buscarTipoDocumentoByAcronimo("NOESB");
			if(doc != null){
				String  formName = doc.getIdFormulario();
				form.setNombre(formName);
			}
			Map<String, Object> values = new HashMap<>();
			values.put("task", subOperation.getSubproceso().getTramite().getCodigoTrata());
			values.put("idee", subOperation.getExpediente().getId());
			values.put("idoperation", subOperation.getOperacion().getId());
			request.setFormValues(values );
			
		}
		request.setForm(form);
	}
}
