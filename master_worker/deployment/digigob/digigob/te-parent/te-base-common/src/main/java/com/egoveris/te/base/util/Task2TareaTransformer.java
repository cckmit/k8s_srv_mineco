package com.egoveris.te.base.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaAppDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoResultadoDTO;
import com.egoveris.te.base.model.rest.DocumentImportedTypesWsDTO;
import com.egoveris.te.base.model.rest.HistoryWsDTO;
import com.egoveris.te.base.model.rest.PossibleStatesWsDTO;
import com.egoveris.te.base.model.rest.ResultTypeWsDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;

/**
 * Está clase hace una transformación del objecto <code>org.jbpm.api.task.Task</code> en el objecto <code>Tarea</code>.
 */
public class Task2TareaTransformer implements Transformer {
	
	private static Logger logger = LoggerFactory.getLogger(Task2TareaTransformer.class);
	
    private static final String TRAMITACION_EN_PARALELO = "Paralelo";
    TrataService trataService;
    TareaParaleloService tareaParaleloService;
    String msgError;
    HistorialOperacionService historialOperacionService;
	ProcessEngine processEngine;
	TipoDocumentoService tipoDocumentoService;


    
    /**
     * constructor con parámetros
     * @param <code>TrataService</code>trataService
     * @param <code>TareaParaleloService</code>tareaParaleloService
     * @param <code>ExecutionService</code>executionService
     * @param <code>HistorialOperacionService</code>historialOperacionService
     */
	public Task2TareaTransformer(TrataService trataService, TareaParaleloService tareaParaleloService,
			ProcessEngine processEngine,
			HistorialOperacionService historialOperacionService,
			TipoDocumentoService tipoDocumentoService) {
        this.trataService = trataService;
        this.tareaParaleloService = tareaParaleloService;
		this.processEngine = processEngine;
        this.historialOperacionService = historialOperacionService;
		this.tipoDocumentoService = tipoDocumentoService;
    }

    public Object transform(Object input) {
		if (logger.isDebugEnabled()) {
			logger.debug("transform(input={}) - start", input);
		}

        try {
            org.jbpm.api.task.Task task = (Task) input;
            Set<String> vars = new HashSet<>(Arrays.asList(
                        new String[] {
                             ConstantesCommon.CODIGO_EXPEDIENTE, ConstantesCommon.ID_SOLUCITUD, ConstantesCommon.MOTIVO,
                             ConstantesCommon.USUARIO_ANTERIOR, ConstantesCommon.USUARIO_PRODUCTOR, ConstantesCommon.TAREA_GRUPAL,
                             ConstantesCommon.CODIGO_TRATA, ConstantesCommon.ULTIMA_MODIFICACION
                        }));

			Map<String, Object> mapVars = processEngine.getExecutionService().getVariables(task.getExecutionId(), vars);

            String nombreTarea = task.getName();
            String codigoExpedienteAux = (String) mapVars.get(ConstantesCommon.CODIGO_EXPEDIENTE);
            String codigoExpediente = ((codigoExpedienteAux == null) ? "" : codigoExpedienteAux);
            //Long idSolicitud = (Long) mapVars.get(ConstantesCommon.ID_SOLUCITUD);
            
            Number idSolicitudNumber = (Number) mapVars.get(ConstantesCommon.ID_SOLUCITUD);
            Long idSolicitud = 0l;
            if(idSolicitudNumber != null)
            	idSolicitud = idSolicitudNumber.longValue();
            
            String motivo = (String) mapVars.get(ConstantesCommon.MOTIVO);

            String usuarioAnteriorAux = (String) mapVars.get(ConstantesCommon.USUARIO_ANTERIOR);
            String usuarioAnterior = ((usuarioAnteriorAux == null) ? "" : usuarioAnteriorAux);
            String tareaGrupal = (String) mapVars.get(ConstantesCommon.TAREA_GRUPAL);
            String codigoTrataAux = (String) mapVars.get(ConstantesCommon.CODIGO_TRATA);
            String codigoTrata = ((codigoTrataAux == null) ? "" : codigoTrataAux);
            String descTrata = ((codigoTrataAux == null) ? "" : trataService.obtenerDescripcionTrataByCodigo(((TrataDTO) this.trataService.buscarTrataByCodigo(codigoTrata)).getCodigoTrata()));

            if (nombreTarea.equals(TRAMITACION_EN_PARALELO)) {
                TareaParaleloDTO tareaParalelo = tareaParaleloService.buscarTareaEnParaleloByIdTask(task.getExecutionId());
                motivo = tareaParalelo.getMotivo();
                usuarioAnterior = tareaParalelo.getUsuarioGrupoAnterior();
                tareaGrupal = (tareaParalelo.getTareaGrupal() ? "esTareaGrupal" : "noEsTareaGrupal");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaCreacion = sdf.format(task.getCreateTime());

            // Fecha donde se modificó por última vez.
            Date lastDate = (Date) mapVars.get(ConstantesCommon.ULTIMA_MODIFICACION);
            String fechaUltimaModificacion = ((lastDate != null) ? sdf.format(lastDate) : fechaCreacion);

            if ((fechaUltimaModificacion != null) && StringUtils.isNotEmpty((String) fechaUltimaModificacion) && (fechaUltimaModificacion.compareTo(fechaCreacion) == 1)) {
                fechaUltimaModificacion = fechaCreacion;
            }
            
            Object returnObject = new Tarea(nombreTarea, codigoExpediente, idSolicitud, task.getName(), usuarioAnterior.toUpperCase(), motivo, task, tareaGrupal, codigoTrata, descTrata, fechaCreacion, fechaUltimaModificacion);
			if (logger.isDebugEnabled()) {
				logger.debug("transform(Object) - end - return value={}", returnObject);
			}
            return returnObject;
        } catch (Exception e) {
            logger.error("error jbpm task", e);
            this.msgError = e.getMessage();
        }

		if (logger.isDebugEnabled()) {
			logger.debug("transform(Object) - end - return value={null}");
		}
        return null;
    }
    
    
    public Object transformTask(Object input) {
		if (logger.isDebugEnabled()) {
			logger.debug("transform(input={}) - start", input);
		}

        try {
            org.jbpm.api.task.Task task = (Task) input;
            Set<String> vars = new HashSet<>(Arrays.asList(
                        new String[] {
                             ConstantesCommon.CODIGO_EXPEDIENTE, ConstantesCommon.ID_SOLUCITUD, ConstantesCommon.MOTIVO,
                             ConstantesCommon.USUARIO_ANTERIOR, ConstantesCommon.USUARIO_PRODUCTOR, ConstantesCommon.TAREA_GRUPAL,
                             ConstantesCommon.CODIGO_TRATA, ConstantesCommon.ULTIMA_MODIFICACION, ConstantesCommon.ESTADO_ANTERIOR,
                             ConstantesCommon.DESCRIPCION
                        }));

			Map<String, Object> mapVars = processEngine.getExecutionService().getVariables(task.getExecutionId(), vars);

            String nombreTarea = task.getName();
            String codigoExpedienteAux = (String) mapVars.get(ConstantesCommon.CODIGO_EXPEDIENTE);
            String codigoExpediente = ((codigoExpedienteAux == null) ? "" : codigoExpedienteAux);
            String statusHi = (String) mapVars.get(ConstantesCommon.ESTADO_ANTERIOR);
            //Long idSolicitud = (Long) mapVars.get(ConstantesCommon.ID_SOLUCITUD);
            
            Number idSolicitudNumber = (Number) mapVars.get(ConstantesCommon.ID_SOLUCITUD);
            Long idSolicitud = 0l;
            if(idSolicitudNumber != null)
            	idSolicitud = idSolicitudNumber.longValue();
            
            String motivo = (String) mapVars.get(ConstantesCommon.MOTIVO);

            String usuarioAnteriorAux = (String) mapVars.get(ConstantesCommon.USUARIO_ANTERIOR);
            String usuarioAnterior = ((usuarioAnteriorAux == null) ? "" : usuarioAnteriorAux);
            String tareaGrupal = (String) mapVars.get(ConstantesCommon.TAREA_GRUPAL);
            String codigoTrataAux = (String) mapVars.get(ConstantesCommon.CODIGO_TRATA);
            String codigoTrata = ((codigoTrataAux == null) ? "" : codigoTrataAux);
            String descTrata = ((codigoTrataAux == null) ? "" : trataService.obtenerDescripcionTrataByCodigo(((TrataDTO) this.trataService.buscarTrataByCodigo(codigoTrata)).getCodigoTrata()));

//            TrataDTO trataSe =   trataService.buscarTrataByCodigo(codigoTrata);
            
            if (nombreTarea.equals(TRAMITACION_EN_PARALELO)) {
                TareaParaleloDTO tareaParalelo = tareaParaleloService.buscarTareaEnParaleloByIdTask(task.getExecutionId());
                motivo = tareaParalelo.getMotivo();
                usuarioAnterior = tareaParalelo.getUsuarioGrupoAnterior();
                tareaGrupal = (tareaParalelo.getTareaGrupal() ? "esTareaGrupal" : "noEsTareaGrupal");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaCreacion = sdf.format(task.getCreateTime());

            // Fecha donde se modificó por última vez.
            Date lastDate = (Date) mapVars.get(ConstantesCommon.ULTIMA_MODIFICACION);
            String fechaUltimaModificacion = ((lastDate != null) ? sdf.format(lastDate) : fechaCreacion);

            if ((fechaUltimaModificacion != null) && StringUtils.isNotEmpty((String) fechaUltimaModificacion) && (fechaUltimaModificacion.compareTo(fechaCreacion) == 1)) {
                fechaUltimaModificacion = fechaCreacion;
            }
            // return
            TareaAppDTO returnObject = new TareaAppDTO();
			// List<DocumentImportedTypesWsDTO> documentImportedTypeResponse =
			// new ArrayList<>();
			// DocumentImportedTypesWsDTO documentImportedType = new
			// DocumentImportedTypesWsDTO();


			// for (TipoDocumentoDTO tipoDocumentoDTO : listaDocumentos) {
			// if (null != tipoDocumentoDTO.getTipoProduccion()
			// && tipoDocumentoDTO.getTipoProduccion().equals("2")) {
			// documentImportedType
			// .setAcronym(tipoDocumentoDTO.getAcronimo());
			// documentImportedType
			// .setDesc(tipoDocumentoDTO.getDescripcion());
			// documentImportedTypeResponse.add(documentImportedType);
			// }
			// }

//			returnObject.setDocumentImportedTypes(listaDocumentosImportados);
            // procedureDesc
            returnObject.setProcedureCode(codigoExpediente);
            // ProcedureDesc
            returnObject.setProcedureDesc(descTrata);
            // taskCode
            returnObject.setTaskCode(task.getExecutionId());
            // lastMoficationDate
            returnObject.setLastModificationDate(fechaUltimaModificacion);
            // reason
            returnObject.setReason(motivo);
            // lasUser
            returnObject.setLastUser(usuarioAnterior.toUpperCase());
            
            returnObject.setStatusTask(nombreTarea);
            
            //Object returnObject = new TareaAppDTO(codigoExpediente, task.getExecutionId(), task.getId(), fechaUltimaModificacion, motivo, task.getActivityName());
            //Object returnObject = new Tarea(nombreTarea, codigoExpediente, idSolicitud, task.getName(), usuarioAnterior.toUpperCase(), motivo, task, tareaGrupal, codigoTrata, descTrata, fechaCreacion, fechaUltimaModificacion);

			// Estados
				returnObject.setPossibleStates(new ArrayList<>());
				Set<String> outcomes = this.processEngine.getTaskService().getOutcomes(task.getId());
				outcomes.remove(ConstantesCommon.ESTADO_SUBSANACION);
				
				for (String aux : outcomes) {
					PossibleStatesWsDTO possibleStatesWs = new PossibleStatesWsDTO();
					possibleStatesWs.setValor(aux);
					returnObject.getPossibleStates().add(possibleStatesWs);
				}
			

			// Resultados
			TrataDTO trata = trataService.buscarTrataByCodigo(codigoTrata);
			returnObject.setResultType(new ArrayList<>());
			for (TrataTipoResultadoDTO trataTipo : trata.getTipoResultadosTrata()) {
				ResultTypeWsDTO resultTypeWs = new ResultTypeWsDTO();
				resultTypeWs.setClave(trataTipo.getProperty().getClave());
				resultTypeWs.setValor(trataTipo.getProperty().getValor());
				returnObject.getResultType().add(resultTypeWs);
			}
			
			// Se envia el taskResult y nextStatus nulos para
			returnObject.setTaskResult(null);
			returnObject.setNextStatus(null);

			if (logger.isDebugEnabled()) {
				logger.debug("transform(Object) - end - return value={}", returnObject);
			}
            return returnObject;
        } catch (Exception e) {
            logger.error("error jbpm task", e);
            this.msgError = e.getMessage();
        }

		if (logger.isDebugEnabled()) {
			logger.debug("transform(Object) - end - return value={null}");
		}
        return null;
    }

    public void llenaListadePases(Long idExpediente, TareaAppDTO tareaActual) {
    	try {
    	List<HistorialOperacionDTO> pases = new ArrayList<>(); 
		pases = (List<HistorialOperacionDTO>) historialOperacionService.buscarHistorialporExpediente(idExpediente);
		
		if (null != pases && !pases.isEmpty()) {
			 
		// dateFormat
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaOperacion = sdf.format(pases.get(pases.size()-1).getFechaOperacion());
		// histo
        HistoryWsDTO histo = new HistoryWsDTO();
        // status
        histo.setStatus(pases.get(pases.size()-1).getEstado());
        // data
        histo.setDate(fechaOperacion);
        // desc
        histo.setDesc(null != pases.get(pases.size()-1).getDescripcion() ? pases.get(pases.size()-1).getDescripcion() : "Sin descripcion");
		// set histo
        tareaActual.setHistory(histo);
        } else {
        	tareaActual.getHistory().setDesc("Sin pase");
        }
		 }catch(Exception e ) {
			 e.getMessage();
			 } 
	 
    }
    
    public String getMsgError() {
        return this.msgError;
    }
}
