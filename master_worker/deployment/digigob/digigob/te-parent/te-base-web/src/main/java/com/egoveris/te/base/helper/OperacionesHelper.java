package com.egoveris.te.base.helper;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class OperacionesHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(OperacionesHelper.class);
	
	private OperacionesHelper() {
		
	}
	
	/**
	 * Redirecciona hacia la pantalla de resumen operacion
	 * 
	 * Nota: el comando global 'changeContainerContent' esta
	 * dentro de dashboard-web y lo que hace es cambiar la zona de contenido
	 * segun el zul enviado como argumento
	 * 
	 * @param operacion Dto con los datos de la operacion
	 */
	public static void redirectResumenOperacion(OperacionDTO operacion) {
		if (operacion != null) {
			Map<String, Object> cmdArgs = new HashMap<>();
			cmdArgs.put("targetZul", "/operaciones/resumenOperacion.zul");
			
			Map<String, Object> zulArgs = new HashMap<>();
			zulArgs.put("operacion", operacion);
			cmdArgs.put("args", zulArgs);
			BindUtils.postGlobalCommand(null, null, "changeContainerContent", cmdArgs);
		}
	}
	
	/**
	 * Obtiene el sector actual del usuario logeado.
	 * Este sector corresponde al seleccionado en edt-web
	 * 
	 * @return
	 * @throws SecurityNegocioException
	 */
	public static Integer getSectorActualUsuario() throws SecurityNegocioException {
		Integer sector = null;
		String loggedUsername = null;
		
		if (Executions.getCurrent().getSession().hasAttribute(ConstantesWeb.SESSION_USERNAME)) {
			loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		}
		
		IUsuarioService usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
		SectorInternoServ sectorInternoServ = (SectorInternoServ) SpringUtil.getBean(ConstantesServicios.SECTOR_INTERNO_SERVICE);
		
		if (loggedUsername != null && usuarioService != null && sectorInternoServ != null) {
			List<Usuario> usuario = usuarioService.obtenerUsuariosPorNombre(loggedUsername);
			
			if (usuario != null && !usuario.isEmpty()) {
				sector = searchSectorListByCodigo(sectorInternoServ.buscarTodosSectores(), usuario.get(0).getCodigoSectorInterno());
			}
		}
		
		return sector;
	}
	
	/**
	 * Busca en la lista de sectores el sector
	 * cuyo codigo de sector interno coincida con el 
	 * parametro dado
	 * 
	 * @param sectores Lista de sectores
	 * @param codigoSectorInterno Codigo de sector interno
	 * @return
	 */
	private static Integer searchSectorListByCodigo(List<SectorInternoBean> sectores, String codigoSectorInterno) {
		Integer sector = null;
		
		if (sectores != null && codigoSectorInterno != null) {
			for (SectorInternoBean sectorInternoBean : sectores) {
				if (sectorInternoBean.getCodigo() != null && sectorInternoBean.getCodigo().equals(codigoSectorInterno)) {
					sector = sectorInternoBean.getId();
					break;
				}
			}
		}
		
		return sector;
	}
	
	/**
	 * Obtiene la reparticion actual del usuario logeado.
	 * Esta reparticion corresponde al seleccionado en edt-web
	 * 
	 * @return
	 * @throws SecurityNegocioException
	 */
	public static Long getReparticionActualUsuario() throws SecurityNegocioException {
		Long idReparticion = null;
		String loggedUsername = null;
		
		if (Executions.getCurrent().getSession().hasAttribute(ConstantesWeb.SESSION_USERNAME)) {
			loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		}
		
		IUsuarioService usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
		ReparticionServ reparticionServ = (ReparticionServ) SpringUtil.getBean(ConstantesServicios.REPARTICION_SERVICE);
		
		if (loggedUsername != null && usuarioService != null && reparticionServ != null) {
			List<Usuario> usuario = usuarioService.obtenerUsuariosPorNombre(loggedUsername);
			
			if (usuario != null && !usuario.isEmpty()) {
				String codigoReparticion = usuario.get(0).getCodigoReparticion();
				ReparticionBean reparticion = reparticionServ.buscarReparticionPorCodigo(codigoReparticion);
				
				if (reparticion != null) {
					idReparticion = reparticion.getId();
				}
			}
		}
		
		return idReparticion;
	}
	
	/**
	 * Metodo que actualiza el bloqueo de la operacion
	 * segun el estado en que se encuentren los subprocesos
	 * 
	 * @param operacion
	 * @param listaSubprocesos
	 * @throws ServiceException
	 */
	public static void actualizaBloqueoOp(OperacionDTO operacion, List<SubProcesoOperacionDTO> listaSubprocesos) {
		if (operacion != null && operacion.getEstadoBloq() != null && listaSubprocesos != null) {
			Set<String> estadosSubprocesos = bloqueosOperacion(listaSubprocesos);
			String estadoBloqueo = BloqueoOperacion.NINGUNO.getValue();
			
			if (estadosSubprocesos.contains(BloqueoOperacion.TOTAL.getValue())) {
				estadoBloqueo = BloqueoOperacion.TOTAL.getValue();
			}
			else if (estadosSubprocesos.contains(BloqueoOperacion.PARCIAL.getValue())) {
				estadoBloqueo = BloqueoOperacion.PARCIAL.getValue();
			}
			
			// Se debe actualizar el estado
			if (!estadoBloqueo.equalsIgnoreCase(operacion.getEstadoBloq())) {
				operacion.setEstadoBloq(estadoBloqueo);
				
				try {
					OperacionService operacionService = (OperacionService) SpringUtil.getBean(ConstantesServicios.OPERACION_SERVICE);
					operacionService.saveOrUpdate(operacion);
				}
				catch (ServiceException e) {
					logger.error("Error en OperacionesHelper.actualizaBloqueoOp(): ", e);
					Messagebox.show(Labels.getLabel("te.opearcionesHerlper.error"), "Error", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}
	
	/**
	 * Metodo que recorre la lista de subprocesos 
	 * y devuelve los bloqueos de operacion vigentes
	 * 
	 * @param listaSubprocesos
	 * @return
	 */
	private static Set<String> bloqueosOperacion(List<SubProcesoOperacionDTO> listaSubprocesos) {
		Set<String> estadosSubprocesos = new HashSet<>();
		estadosSubprocesos.add(BloqueoOperacion.NINGUNO.getValue());
		
		for (SubProcesoOperacionDTO subprocesoOp : listaSubprocesos) {
			if (subprocesoOp.getSubproceso() != null && subprocesoOp.getSubproceso().getLockType() != null
					&& !subprocesoOp.getSubproceso().getLockType().equalsIgnoreCase(BloqueoOperacion.NINGUNO.getValue())) {
				// Si la suboperacion tiene bloqueo total o parcial, hay bloqueo de operacion
				estadosSubprocesos.add(subprocesoOp.getSubproceso().getLockType());
				
				if (subprocesoOp.getExpediente() != null && subprocesoOp.getExpediente().getEstado() != null &&
						subprocesoOp.getExpediente().getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
					// Pero si la suboperacion esta en guarda temporal, el bloqueo se levanta
					estadosSubprocesos.remove(subprocesoOp.getSubproceso().getLockType());
				}
			}
		}
		
		return estadosSubprocesos;
	}
	
}
