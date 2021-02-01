package com.egoveris.te.ws.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zkoss.zk.ui.Component;

import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.service.rmi.RemoteRestService;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.ws.model.RemoteResponse;
import com.egoveris.te.ws.util.RemoteTeUtils;
import com.egoveris.trade.common.model.AcknowledgeDTO;
import com.egoveris.trade.common.model.ResponseMessageDTO;

@RestController
@RequestMapping("/api")
public class RemoteEndPoint {
	@Autowired
	private RemoteRestService remoteRestServiceImpl;
	@Autowired
	private IExternalTipoDocumentoService tipoDocumentoService;
	@Autowired
	private IFormManagerFactory<IFormManager<Component>> zkFormManagerFactory;
	private Log logger =  LogFactory.getLog(RemoteEndPoint.class);
	
	
	/**
	 * Endpoint expose to process the messages of ESB
	 * @param responseMessage
	 * @return
	 */
	@RequestMapping(value="/operation/message", method = RequestMethod.POST)
	public  ResponseEntity<RemoteResponse> confirm(@RequestBody ResponseMessageDTO responseMessage){
		RemoteResponse response = new RemoteResponse();
		ResponseEntity<RemoteResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			RemoteTeUtils.getInstance().validateEntry(responseMessage);
			String idTransaction = RemoteTeUtils.getInstance().getIdTransaction(responseMessage);
			ExpedientTransactionDTO exp = RemoteTeUtils.getInstance().getOperacionService().findLastActiveTransaction(idTransaction);
			Map<String, Object> params = new HashMap<>();
			params.put(ConstantesCommon.SCRIPT_TYPE_REQUEST, response.getMessage());
			params.put(ConstantesCommon.SCRIPT_WINDOW_PASE, null);
			params.put(ConstantesCommon.SCRIPT_FORM, responseMessage.getForm());
			params.put(ConstantesCommon.SCRIPT_FORM_VALUES, responseMessage.getFormValues());
			WorkFlowScriptUtils.getInstance().executeScript(ScriptType.REMOTE_TASK, exp.getIdExpedient(), params, null);
			WorkFlowScriptUtils.getInstance().executeScript(ScriptType.REMOTE_GENERIC, exp.getIdExpedient(), params, null);
			remoteRestServiceImpl.updateTransaction(idTransaction);
			
			response.setHttpStatus(200);
			response.setMessage("OK");
			response.setStatusApp(1);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			logger.error("Error",e);
			response.setHttpStatus(500);
			response.setMessage("Error");
			response.setStatusApp(0);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			
		}
		return responseEntity;
		
	}

	/**
	 * EndPoint that init a  subprocess of operation
	 * @param operation
	 * @return
	 */
	@RequestMapping(value="/operation/initSubProcess", method = RequestMethod.POST)	
	public  ResponseEntity<RemoteResponse> initSubProcess(@RequestBody ResponseMessageDTO operation){
		RemoteResponse response = new RemoteResponse();
		ResponseEntity<RemoteResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			RemoteTeUtils.getInstance().validateEntry(operation);
			String idTransaction = RemoteTeUtils.getInstance().getIdTransaction(operation);
			ExpedientTransactionDTO exp = RemoteTeUtils.getInstance().getOperacionService().findLastActiveTransaction(idTransaction);
			RemoteTeUtils.getInstance().getOperacionService().
				iniciarSubProceso(1L, exp.getIdOperation() , null, "WorkFlowURL");
			
			remoteRestServiceImpl.updateTransaction(idTransaction);
			response.setHttpStatus(200);
			response.setMessage("OK");
			response.setStatusApp(1);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (RemoteException e) {
			logger.error(e);
			response.setHttpStatus(500);
			response.setMessage("Error");
			response.setStatusApp(0);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return responseEntity;
		
	}
	
	
	/**
	 * Ednpoint that return a form (FFDD) 
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/operation/form/{name}", method = RequestMethod.GET)	
	public  ResponseEntity<FormularioDTO> getForm(@PathVariable(value="name") String name){
		ResponseEntity<FormularioDTO> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		FormularioDTO form = new FormularioDTO();
		try {
			ResponseTipoDocumento doc = tipoDocumentoService.buscarTipoDocumentoByAcronimo(name);
			if(doc != null){
				form = zkFormManagerFactory.buscarFormulario(doc.getIdFormulario());
			}
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(form);
		} catch (Exception e) {
			logger.error(e);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(form);
		}
		return responseEntity; 
	}
	
	
	/**
	 * Endpoint that process the notifications
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/operation/notification", method = RequestMethod.POST)	
	public  ResponseEntity<AcknowledgeDTO> notification(@RequestBody ResponseMessageDTO response){
		ResponseEntity<AcknowledgeDTO> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		AcknowledgeDTO ack  = new AcknowledgeDTO();
		try {
			RemoteTeUtils.getInstance().validateEntry(response);
			String idTransaction = RemoteTeUtils.getInstance().getIdTransaction(response);
			ExpedientTransactionDTO exp = RemoteTeUtils.getInstance().getOperacionService().findLastTransaction(idTransaction);
			Map<String, Object> params = new HashMap<>();
			params.put(ConstantesCommon.SCRIPT_TYPE_REQUEST, response.getIdMessage());
			params.put(ConstantesCommon.SCRIPT_WINDOW_PASE, null);
			params.put(ConstantesCommon.SCRIPT_FORM, response.getForm());
			params.put(ConstantesCommon.SCRIPT_FORM_VALUES, response.getFormValues());
			WorkFlowScriptUtils.getInstance().executeScript(ScriptType.REMOTE_GENERIC, exp.getIdExpedient(), params, "JQUINTAU");
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(ack);
		} catch (Exception e) {
			logger.error(e);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ack);
		}
		return responseEntity;
	}
	
}
