package com.egoveris.deo.base.task;

import com.egoveris.deo.base.repository.TipoDocumentoRepository;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.IHistorialProcesosService;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.HistorialTareasGEDO;
import com.egoveris.deo.model.model.MessageDatosExtrasGEDO;
import com.egoveris.deo.util.Constantes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@SuppressWarnings("serial")
public class EnviarPortaFirma implements ExternalActivityBehaviour{

	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	private List<HistorialDTO> historial;
	@Autowired	
	private IHistorialProcesosService historialProcesosService;
	@Autowired
	private HistorialService historialService;
	@Value("${app.sistema.gestor.documental}")
	private String gestorDocumental;
	
	public void signal(ActivityExecution execution, String signalName,
			Map<String, ?> parameters) throws Exception {
		execution.take(signalName);
	}

	public void execute(ActivityExecution execution) throws Exception {
				
	}

	private MessageDatosExtrasGEDO getDatosAdicionales(String executionId) {
		this.historial = historialProcesosService.getHistorial(executionId);
		MessageDatosExtrasGEDO a = new MessageDatosExtrasGEDO();
		List<HistorialTareasGEDO> hist = new ArrayList<>();		
		for (HistorialDTO t: historial){
			HistorialTareasGEDO h1 = new HistorialTareasGEDO();			
			h1.setActividad(t.getActividad());
			h1.setFecha(t.getFechaFin());
		    h1.setUsuario(t.getUsuario());
			h1.setMensaje(t.getMensaje());
			hist.add(h1);
		}		
		a.setHistorial(hist);
		return a;
	}
	
	public void setVariableWorkFlow(String name, Object value,String executionId) {
		this.processEngine.getExecutionService().setVariable( executionId, name, value);
	}

	public void signalExecution(String nameTransition, String usernameDerivador,String executionId) {
		setVariableWorkFlow("usuarioDerivador", usernameDerivador,executionId);
		processEngine.getExecutionService().signalExecutionById(executionId, nameTransition);
	}
	
	private Map<String, Object> obtenerVariablesWorkflow (String executionId) {
		Set<String> names = new HashSet<>();
		names.add(Constantes.VAR_MOTIVO);
		names.add(Constantes.VAR_USUARIO_FIRMANTE);
		names.add(Constantes.VAR_TIPO_DOCUMENTO);
		names.add(Constantes.VAR_USUARIO_PRODUCTOR);
		names.add(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
		names.add(Constantes.VAR_USUARIO_REVISOR);
		names.add(Constantes.VAR_ID_GUARDA_DOCUMENTAL);
		return processEngine.getExecutionService().getVariables(executionId, names);
	}

}
