package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InboxGeneralComposer extends ValidarApoderamientoComposer {
	
	private static Logger logger = LoggerFactory.getLogger(InboxGeneralComposer.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7101020335303241894L;
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	@WireVariable("ExecutionServiceImpl")
	private ExecutionService executionService;
	private String username;
	
	public void doAfterCompose(Component c) throws Exception {
        super.doAfterCompose(c);
        Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));  
        this.username = (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);
    }

	
	 public void generarTareaHistorico(String codigoExpediente, String motivo, String origen, String destino,Task task){
	    	
	    	 

	    	try {
				ExpedienteElectronicoDTO	expedienteElectronico = this.expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codigoExpediente);
				
				Map<String, String> detalles = new HashMap<String, String>();
				if(task != null){
					detalles.put(ConstantesWeb.ESTADO, task.getActivityName());					
				}else{
					detalles.put(ConstantesWeb.ESTADO, expedienteElectronico.getEstado());
				}
				detalles.put(ConstantesWeb.MOTIVO, motivo);
				
				
				
				detalles.put(ConstantesWeb.REPARTICION_USUARIO,origen);
				detalles.put(ConstantesWeb.DESTINATARIO,destino);
				detalles.put(ConstantesWeb.TIPO_OPERACION,motivo);
				Usuario datosUsuario = usuariosSADEService.getDatosUsuario(username);
				detalles.put(ConstantesWeb.SECTOR_USUARIO,datosUsuario.getCodigoSectorInterno());
				
				
				
				
				this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronico,username, detalles);
				
			} catch (ParametroIncorrectoException e) {
				logger.error("Error al registrar Pase sin documento:" + e.getMessage());
			} 
	    }


	@Override
	protected void asignarTarea() throws Exception {
	}


	@Override
	protected void enviarBloqueoPantalla(Object data) {
	}
}
