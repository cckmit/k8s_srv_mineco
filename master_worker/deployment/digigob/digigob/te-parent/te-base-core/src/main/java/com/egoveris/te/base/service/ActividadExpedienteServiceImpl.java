package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.util.resource.Labels;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.GenerarCopiaBeanDTO;
import com.egoveris.te.base.model.ParametroActividadDTO;
import com.egoveris.te.base.model.TipoActividadDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ApplicationContextProvider;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.ConstantesCore;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.NuevaTareaRequest;
import com.egoveris.vucfront.ws.service.DocumentosClient;
import com.egoveris.vucfront.ws.service.TareasClient;

@Service
@Transactional
public class ActividadExpedienteServiceImpl implements IActividadExpedienteService {

	private static final Logger logger = LoggerFactory.getLogger(ActividadExpedienteServiceImpl.class);
	@Autowired
	private IActividadService actividadService;
	@Autowired
	private DocumentoGedoService documentoGedoService;
	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	@Autowired
	private TareasClient tareaVucService;
	@Autowired
	private DocumentosClient documentoVucService;

	@Autowired
	private GenerarCopiaService generarCopiaService;

	private String codigoExpediente;
	private String userName;
	private String idWorkflowEE;
	private String resultadoEjecucionLlamadaAuditoria;
	private Integer resultado;
	private ActividadDTO actividadAuxParaPasarAThread;

	@Override
	public List<ActividadInbox> buscarActividadesVigentes(List<String> workFlowIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarActividadesVigentes(workFlowIds={}) - start", workFlowIds);
		}

		List<ActividadInbox> result = new ArrayList<>();
		List<ActividadDTO> actList = actividadService.buscarActividadesVigentes(workFlowIds);

		if (actList != null) {
			for (ActividadDTO actividad : actList) {
				result.add(mapToActInbox(actividad));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarActividadesVigentes(List<String>) - end - return value={}", result);
		}
		return result;
	}

	private ActividadInbox mapToActInbox(ActividadDTO actividad) {
		if (logger.isDebugEnabled()) {
			logger.debug("mapToActInbox(actividad={}) - start", actividad);
		}
		ActividadInbox actIn = new ActividadInbox();
		actIn.setId(actividad.getId());
		actIn.setTipoActividadDecrip(actividad.getTipoActividad().getDescripcion());
		String numPedido = null;
		String mailCreador = null;
		if (null != actividad.getParametros()) {
			for (Map.Entry<String, ParametroActividadDTO> entry : actividad.getParametros().entrySet()) {
				if (ConstParamActividad.PARAM_PEDIDO_GEDO.equalsIgnoreCase(entry.getValue().getCampo())) {
					numPedido = entry.getValue().getValor();
				}
				if (ConstParamActividad.PARAM_USER_MAIL.equalsIgnoreCase(entry.getValue().getCampo())) {
					mailCreador = entry.getValue().getValor();
				}
			}
		}
		actIn.setMailCreador(mailCreador);
		actIn.setNroPedidoGEDO(numPedido);
		actIn.setForm(actividad.getTipoActividad().getForm());
		actIn.setFechaAlta(actividad.getFechaAlta());
		actIn.setFechaBaja(actividad.getFechaCierre());
		actIn.setIdObjetivo(actividad.getIdObjetivo());
		actIn.setEstado(actividad.getEstado());
		actIn.setUsuarioCierre(actividad.getUsuarioCierre());
		if (null != actividad.getParametros()
				&& null != actividad.getParametros().get(ConstParamActividad.PARAM_NRO_EXP)) {
			actIn.setNroExpediente(actividad.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).toString());
		}
		if (null != actividad.getParametros()
				&& actividad.getParametros().get(ConstParamActividad.PARAM_TRATA) != null) {
			actIn.setTrata(actividad.getParametros().get(ConstParamActividad.PARAM_TRATA).toString());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mapToActInbox(Actividad) - end - return value={}", actIn);
		}
		return actIn;
	}

	public IActividadService getActividadService() {
		return actividadService;
	}

	public void setActividadService(IActividadService actividadService) {
		this.actividadService = actividadService;
	}

	@Override
	public List<ActividadInbox> buscarHistoricoActividades(String workFlowId) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarHistoricoActividades(workFlowId={}) - start", workFlowId);
		}

		List<ActividadInbox> result = new ArrayList<>();
		List<ActividadDTO> actList = actividadService.buscarHistoricoActividades(workFlowId);

		if (actList != null) {
			for (ActividadDTO actividad : actList) {
				result.add(mapToActInbox(actividad));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarHistoricoActividades(String) - end - return value={}", result);
		}
		return result;
	}

	public void eliminarActividad(String numeroExpediente, List<ActividadDTO> listActividades) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividad(numeroExpediente={}, listActividades={}) - start", numeroExpediente,
					listActividades);
		}

		Map<String, ParametroActividadDTO> parametros;

		for (ActividadDTO actividad : listActividades) {
			parametros = actividad.getParametros();
			ParametroActividadDTO parametroNumExp = parametros.get(ConstParamActividad.PARAM_NRO_EXP);

			if (parametroNumExp != null && numeroExpediente.equals(parametroNumExp.getValor())
					&& actividad.getEstado().equals("PENDIENTE")) {
				actividadService.eliminarActividad(actividad);
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividad(String, List<Actividad>) - end");
		}
	}

	public void rechazarActividades(String numeroExpediente, List<ActividadDTO> listActividades, String nombreUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("rechazarActividades(numeroExpediente={}, listActividades={}, nombreUsuario={}) - start",
					numeroExpediente, listActividades, nombreUsuario);
		}

		Map<String, ParametroActividadDTO> parametros;

		for (ActividadDTO actividad : listActividades) {
			parametros = actividad.getParametros();
			ParametroActividadDTO parametroNumExp = parametros.get(ConstParamActividad.PARAM_NRO_EXP);

			if (parametroNumExp != null && numeroExpediente.equals(parametroNumExp.getValor())
					&& actividad.getEstado().equals("PENDIENTE")) {
				actividad.setEstado(ConstEstadoActividad.ESTADO_CANCELADA);
				actividad.setFechaCierre(new Date());
				actividad.setUsuarioCierre(nombreUsuario);
				actividadService.actualizarActividad(actividad);
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rechazarActividades(String, List<Actividad>, String) - end");
		}
	}

	public boolean tieneActividades(ExpedienteElectronicoDTO e) {
		logger.info("Buscando las actividades pendientes del expediente: " + e.getCodigoCaratula());
		List<ActividadInbox> activs = buscarHistoricoActividades(e.getIdWorkflow());
		boolean tieneActividades = false;
		for (ActividadInbox act : activs) {
			if (act.getEstado().equals("PENDIENTE") || act.getEstado().equals("ABIERTA")) {
				tieneActividades = true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tieneActividades(ExpedienteElectronico) - end - return value={}", tieneActividades);
		}
		return tieneActividades;
	}

	public void eliminarActividadSubsanacion(ExpedienteElectronicoDTO ee, String userName) {

		logger.info("Eliminando la actividad de subsanacion del expediente: " + ee.getCodigoCaratula());
		TipoActividadDTO tipoActividadSub = actividadService
				.buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION);
		List<ActividadDTO> actividadesSub = actividadService.buscarActividadesPendientes(tipoActividadSub);

		StringBuilder sb = new StringBuilder();
		sb.append(ConstantesCore.MOTIVO_CANCELACION);
		sb.append(ee.getCodigoCaratula());
		String motivoDeCancelacion = sb.toString();
		String referenciaDeCancelacion = ConstantesCore.REFERENCIADECANCELACION;
		List<ActividadDTO> list = buscarActividadesSubsPendienteDeEE(actividadesSub, ee.getCodigoCaratula());
		if (!list.isEmpty()) {
			DocumentoDTO d = documentoGedoService.armarDocDeNotificacion(ee, userName, referenciaDeCancelacion,
					motivoDeCancelacion);
			cancelarTarea(ee.getCodigoCaratula());
			rechazarActividades(ee.getCodigoCaratula(), list, userName);
			notificar(userName, motivoDeCancelacion, ee, d);
			d.setPosicion(ee.getDocumentos().size());
			ee.agregarDocumento(d);
			expedienteElectronicoService.modificarExpedienteElectronico(ee);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividadSubsanacion(ExpedienteElectronico, String) - end");
		}
	}

	private List<ActividadDTO> buscarActividadesSubsPendienteDeEE(List<ActividadDTO> actividades,
			String numeroExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarActividadesSubsPendienteDeEE(actividades={}, numeroExpediente={}) - start", actividades,
					numeroExpediente);
		}

		List<ActividadDTO> resultado = new ArrayList<>();
		for (ActividadDTO act : actividades) {
			if (act.getEstado().equals("PENDIENTE")
					&& act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).getValor().equals(numeroExpediente)) {
				resultado.add(act);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarActividadesSubsPendienteDeEE(List<Actividad>, String) - end - return value={}",
					resultado);
		}
		return resultado;
	}

	private void notificar(String userName, String motivo, ExpedienteElectronicoDTO ee, DocumentoDTO... documentos) {
		if (logger.isDebugEnabled()) {
			logger.debug("notificar(userName={}, motivo={}, ee={}, documentos={}) - start", userName, motivo, ee,
					documentos);
		}

		List<DocumentoDTO> docs = new ArrayList<>();
		for (DocumentoDTO d : documentos) {
			docs.add(d);
		}
		try {
		//	notificacionEEService.altaNotificacionVUC(userName, ee, docs, motivo);
		} catch (Exception e) {
			logger.error("error al notificar documentos del expediente:", e);
			logger.error("ERROR al notificar documentos del expediente: " + ee.getCodigoCaratula() + e.getMessage());
			throw new TeRuntimeException(Labels.getLabel("ee.subsanacion.notificacion.tad.error"), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("notificar(String, String, ExpedienteElectronico, Documento) - end");
		}
	}

	private void cancelarTarea(String nroExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarTarea(nroExpediente={}) - start", nroExpediente);
		}
		// TODO Se elimina en espera de refactorizar VUC
		// try {
		// tareaService.cancelarTarea(nroExpediente);
		// } catch
		// (com.egoveris.vuc.external.service.client.service.external.exception.NegocioException
		// e) {
		// logger.error("ERROR al cancelar la solicitud de subsanacion del
		// expediente: "+nroExpediente + e.getMessage());
		// throw new
		// TeRuntimeException(Labels.getLabel("ee.subsanacion.cancelacion.actividad.error"),
		// e);
		// }
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarTarea(String) - end");
		}
	}

	/**
	 * Eliminacion actividad VUC.
	 *
	 * @param ee
	 *            the ee
	 * @param actividad
	 *            the actividad
	 * @param userName
	 *            the user name
	 */
	public void eliminacionActividadVUC(ExpedienteElectronicoDTO ee, ActividadDTO actividad, String userName) {
		logger.info("Eliminando la actividad de subsanacion del expediente: " + ee.getCodigoCaratula());

		StringBuilder sb = new StringBuilder();
		sb.append(ConstantesCore.MOTIVO_CANCELACION);
		sb.append(ee.getCodigoCaratula());
		String motivoDeCancelacion = sb.toString();
		String referenciaDeCancelacion = ConstantesCore.REFERENCIADECANCELACION;

		DocumentoDTO d = documentoGedoService.armarDocDeNotificacion(ee, userName, referenciaDeCancelacion,
				motivoDeCancelacion);
		notificar(userName, motivoDeCancelacion, ee, d);
		d.setPosicion(ee.getDocumentos().size());
		ee.agregarDocumento(d);
		expedienteElectronicoService.modificarExpedienteElectronico(ee);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminacionActividadVUC(ExpedienteElectronico, Actividad, String) - end");
		}
	}

	public void eliminarActividad(Long idActividad, String numeroExpediente, String tipoActividad) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividad(idActividad={}, numeroExpediente={}, tipoActividad={}) - start",
					idActividad, numeroExpediente, tipoActividad);
		}

		TipoActividadDTO tipo = actividadService.buscarTipoActividad(tipoActividad);

		List<ActividadDTO> listActividades = actividadService.buscarActividadesPendientes(tipo);

		for (ActividadDTO act : listActividades) {
			if (act.getEstado().equals("PENDIENTE")
					&& act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).getValor().equals(numeroExpediente)
					&& act.getId().equals(idActividad)) {
				actividadService.eliminarActividad(act);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividad(Integer, String, String) - end");
		}
	}

	public void eliminarActividadGedo(String nroExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividadGedo(nroExpediente={}) - start", nroExpediente);
		}

		TipoActividadDTO tipoActividad = actividadService.buscarTipoActividad(ConstantesWeb.PETICION_PENDIENTE_GEDO);
		List<ActividadDTO> listActividades = actividadService.buscarActividadesPendientes(tipoActividad);

		eliminarActividad(nroExpediente, listActividades);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarActividadGedo(String) - end");
		}
	}

	public void rechazarActividad(Integer idActividad, String numeroExpediente, String tipoActividad, String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"rechazarActividad(idActividad={}, numeroExpediente={}, tipoActividad={}, userName={}) - start",
					idActividad, numeroExpediente, tipoActividad, userName);
		}

		TipoActividadDTO tipo = actividadService.buscarTipoActividad(tipoActividad);

		List<ActividadDTO> listActividades = actividadService.buscarActividadesPendientes(tipo);

		for (ActividadDTO act : listActividades) {
			if (act.getEstado().equals("PENDIENTE")
					&& act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).getValor().equals(numeroExpediente)
					&& act.getId().equals(idActividad)) {
				act.setEstado(ConstEstadoActividad.ESTADO_CANCELADA);
				act.setFechaCierre(new Date());
				act.setUsuarioCierre(userName);
				actividadService.actualizarActividad(act);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rechazarActividad(Integer, String, String, String) - end");
		}
	}

	public void rechazarActividad(ExpedienteElectronicoDTO ee, String tipoActividad, String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug("rechazarActividad(ee={}, tipoActividad={}, userName={}) - start", ee, tipoActividad,
					userName);
		}

		TipoActividadDTO tipoActividadSub = null;

		if (tipoActividad != null) {
			tipoActividadSub = actividadService.buscarTipoActividad(tipoActividad);
		}

		List<ActividadDTO> actividadesSub = actividadService.buscarActividadesPendientes(tipoActividadSub);

		String[] numeroExpediente = new String[1];
		numeroExpediente[0] = ee.getCodigoCaratula();
		String motivoDeCancelacion = Labels.getLabel("ee.act.cancelacion.motivo", numeroExpediente);
		String referenciaDeCancelacion = Labels.getLabel("ee.subsanacion.cancelacion.actividad.referencia");
		List<ActividadDTO> list = buscarActividadesSubsPendienteDeEE(actividadesSub, ee.getCodigoCaratula());
		if (list != null && list.size() > 0) {
			DocumentoDTO d = documentoGedoService.armarDocDeNotificacion(ee, userName, referenciaDeCancelacion,
					motivoDeCancelacion);
			cancelarTarea(ee.getCodigoCaratula());
			rechazarActividades(ee.getCodigoCaratula(), list, userName);
			notificar(userName, motivoDeCancelacion, ee, d);
			d.setPosicion(ee.getDocumentos().size());
			ee.agregarDocumento(d);
			expedienteElectronicoService.modificarExpedienteElectronico(ee);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rechazarActividad(ExpedienteElectronico, String, String) - end");
		}
	}

	@Override
	public synchronized int generarActividadCrearPaqueteArch(String numeroExpediente, String nombreUsuario)
			throws ParametroIncorrectoException, NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarActividadCrearPaqueteArch(numeroExpediente={}, nombreUsuario={}) - start",
					numeroExpediente, nombreUsuario);
		}

		ExpedienteElectronicoDTO expediente = expedienteElectronicoService
				.obtenerExpedienteElectronicoPorCodigo(numeroExpediente);

		this.codigoExpediente = numeroExpediente;
		this.userName = nombreUsuario;
		this.idWorkflowEE = expediente.getIdWorkflow();
		this.actividadService = (IActividadService) ApplicationContextProvider.getApplicationContext()
				.getBean("actividadService");

		// Id objetivo
		String idObj = expediente.getIdWorkflow();

		// Fecha actual
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		TipoActividadDTO actividadPaqueteArchivo = actividadService
				.buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_PAQUETE_ARCHIVO);

		ActividadDTO act = new ActividadDTO();

		// Estado
		String abierta = ConstEstadoActividad.ESTADO_ABIERTA;

		act.setIdObjetivo(idObj);
		act.setFechaAlta(date);
		act.setTipoActividad(actividadPaqueteArchivo);
		act.setParametros(new HashMap<String, ParametroActividadDTO>());
		if (expediente.getTrata() != null) {
			act.getParametros().put(ConstParamActividad.PARAM_TRATA,
					new ParametroActividadDTO(expediente.getTrata().getCodigoTrata()));
		}

		act.getParametros().put(ConstParamActividad.PARAM_NRO_EXP, new ParametroActividadDTO(numeroExpediente));

		StringBuilder strBuffer = new StringBuilder();
		strBuffer.append("Petición de creación de paquete a Archivo");
		strBuffer.append(", con número de solicitud ");
		strBuffer.append(idObj);

		act.getParametros().put(ConstParamActividad.PARAM_MOTIVO_EE, new ParametroActividadDTO(strBuffer.toString()));
		act.setUsuarioAlta(nombreUsuario);
		act.setEstado(abierta);
		this.actividadAuxParaPasarAThread = act;
		this.resultado = 1;

		new Thread() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("$Thread.run() - start");
				}

				synchronized (this) {
					actividadService = (IActividadService) ApplicationContextProvider.getApplicationContext()
							.getBean("actividadService");
					resultadoEjecucionLlamadaAuditoria = "OK";
					try {
						GenerarCopiaBeanDTO bean = new GenerarCopiaBeanDTO(userName, idWorkflowEE, codigoExpediente,
								actividadAuxParaPasarAThread.getId());
						generarCopiaService.save(bean);

					} catch (Exception e) {
						logger.error("$Thread.run()", e);

						GenerarCopiaBeanDTO bean = new GenerarCopiaBeanDTO(userName, idWorkflowEE, codigoExpediente,
								actividadAuxParaPasarAThread.getId());
						generarCopiaService.save(bean);
					}

					if (logger.isDebugEnabled()) {
						logger.debug("$Thread.run() - end");
					}
				}
			}
		}.start();

		if (logger.isDebugEnabled()) {
			logger.debug("generarActividadCrearPaqueteArch(String, String) - end - return value={}", resultado);
		}
		return resultado;
	}

	/**
	 * Dado un codigo de expediente, idObj y su tipoActividad, cierra su
	 * actividad
	 * 
	 * @param ee
	 * @param tipoActividad
	 * @param userName
	 */
	public void cerrarActividad(String codigoExpediente, String tipoActividad, Long idObjetivo, String nombreUsuario,
			String estado) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"cerrarActividad(codigoExpediente={}, tipoActividad={}, idObjetivo={}, nombreUsuario={}, estado={}) - start",
					codigoExpediente, tipoActividad, idObjetivo, nombreUsuario, estado);
		}

		TipoActividadDTO tipoAct;

		if (tipoActividad != null) {
			tipoAct = actividadService.buscarTipoActividad(tipoActividad);
		} else {
			throw new ParametroIncorrectoException("Tipo de actividad inválido" + tipoActividad, null);
		}

		if (tipoAct == null)
			throw new ParametroIncorrectoException("No se encontro tipo de actividad: " + tipoActividad, null);

		if (idObjetivo == null)
			throw new ParametroIncorrectoException("Id de objetivo invalido ", null);

		ActividadDTO actividad = actividadService.buscarActividad(idObjetivo);

		if (actividad == null)
			throw new ParametroIncorrectoException("No se encontro actividad solicitada: " + idObjetivo, null);

		Map<String, ParametroActividadDTO> parametros;

		parametros = actividad.getParametros();

		ParametroActividadDTO parametroNumExp = parametros.get(ConstParamActividad.PARAM_NRO_EXP);

		ParametroActividadDTO nombreUsuarioParam = parametros.get(ConstParamActividad.PARAM_NOMBRE_USUARIO);

		if (!nombreUsuario.toUpperCase().equals(nombreUsuarioParam.getValor().toUpperCase()))
			throw new ParametroIncorrectoException("No se encontro usuario solicitado: " + nombreUsuario, null);

		if (parametroNumExp != null && codigoExpediente.equals(parametroNumExp.getValor())
				&& actividad.getEstado().equals(ConstEstadoActividad.ESTADO_ABIERTA)) {
			actividad.setEstado(estado);
			actividad.setFechaCierre(new Date());
			actividad.setUsuarioCierre(nombreUsuario);
			actividadService.actualizarActividad(actividad);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarActividad(String, String, Integer, String, String) - end");
		}
	}

	@Override
	public List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente, String codigoTramite) {
		List<ExternalDocumentoVucDTO> docsVuc = documentoVucService.getDocumentosByCodigoExpediente(codigoExpediente, codigoTramite);
		List<ExternalDocumentoVucDTO> docsSubsanar = new ArrayList<>();
		List<String> acronimosVuc = new ArrayList<>();

		for (ExternalDocumentoVucDTO doc : docsVuc) {
			if (!acronimosVuc.contains(doc.getTipoDocumento().getAcronimoTad())) {
				acronimosVuc.add(doc.getTipoDocumento().getAcronimoTad());
				docsSubsanar.add(doc);
			} else {
				ExternalDocumentoVucDTO docAgregar = null;
				ExternalDocumentoVucDTO docEliminar = null;
				for (ExternalDocumentoVucDTO docAux : docsSubsanar) {
					if (doc.getTipoDocumento().getAcronimoTad().equals(docAux.getTipoDocumento().getAcronimoTad())
							&& doc.getFechaCreacion().after(docAux.getFechaCreacion())) {
						docAgregar = doc;
						docEliminar = docAux;
					}
				}
				docsSubsanar.remove(docEliminar);
				docsSubsanar.add(docAgregar);
			}
		}
		return docsSubsanar;
	}
	
	@Override
	public void nuevaTareaSubsanacionRequest(NuevaTareaRequest request) {
		tareaVucService.nuevaTareaSubsanacionRequest(request);
	}
	  
	public String getIdWorkflowEE() {
		return idWorkflowEE;
	}

	public void setIdWorkflowEE(String idWorkflowEE) {
		this.idWorkflowEE = idWorkflowEE;
	}

	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}

	public String getCodigoExpediente() {
		return codigoExpediente;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setResultadoEjecucionLlamadaAuditoria(String resultadoEjecucionLlamadaAuditoria) {
		this.resultadoEjecucionLlamadaAuditoria = resultadoEjecucionLlamadaAuditoria;
	}

	public String getResultadoEjecucionLlamadaAuditoria() {
		return resultadoEjecucionLlamadaAuditoria;
	}

	public ActividadDTO getActividadAuxParaPasarAThread() {
		return actividadAuxParaPasarAThread;
	}

	public GenerarCopiaService getGenerarCopiaService() {
		return generarCopiaService;
	}

	public void setGenerarCopiaService(GenerarCopiaService generarCopiaService) {
		this.generarCopiaService = generarCopiaService;
	}

	public void setActividadAuxParaPasarAThread(ActividadDTO actividadAuxParaPasarAThread) {
		this.actividadAuxParaPasarAThread = actividadAuxParaPasarAThread;
	}

	public Integer getResultado() {
		return resultado;
	}

	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

}
