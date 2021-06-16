package com.egoveris.deo.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.Historial;
import com.egoveris.deo.base.repository.HistorialRepository;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service
@Transactional
public class HistorialServiceImpl implements HistorialService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HistorialServiceImpl.class);

	@Autowired
	private HistorialRepository historialRepo;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	@Qualifier("deoCoreMapper")
	private Mapper mapper;

	public void guardarHistorial(HistorialDTO historial) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarHistorial(HistorialDTO) - start"); //$NON-NLS-1$
		}

		historialRepo.save(mapper.map(historial, Historial.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarHistorial(HistorialDTO) - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	public void actualizarHistorial(String workflowOrigen) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(String) - start"); //$NON-NLS-1$
		}

		List<HistorialDTO> listaHistorial = ListMapper.mapList(
				historialRepo.findByWorkflowOrigenOrderByFechaFinAsc(workflowOrigen), mapper, HistorialDTO.class);

		if (listaHistorial != null && !listaHistorial.isEmpty()) {
			for (HistorialDTO h : listaHistorial) {
				if (h.getFechaFin() == null) {
					actualizarHistorial(h);
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(String) - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	public void actualizarHistorial(String workflowOrigen, String mnjsEnvRevision) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(String) - start"); //$NON-NLS-1$
		}

		List<HistorialDTO> listaHistorial = ListMapper.mapList(
				historialRepo.findByWorkflowOrigenOrderByFechaFinAsc(workflowOrigen), mapper, HistorialDTO.class);

		if (listaHistorial != null && !listaHistorial.isEmpty()) {
			for (HistorialDTO h : listaHistorial) {
				if (h.getFechaFin() == null) {
					h.setMensaje(mnjsEnvRevision);
					actualizarHistorial(h);
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(String) - end"); //$NON-NLS-1$
		}
	}

	public HistorialDTO buscarUltimaHistorial(String workflowOrigen, String actividad) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUltimaHistorial(String, String) - start"); //$NON-NLS-1$
		}

		HistorialDTO returnHistorialDTO = mapper.map(
				this.historialRepo.findByWorkflowOrigenAndActividadAndFechaFinIsNull(workflowOrigen, actividad),
				HistorialDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUltimaHistorial(String, String) - end"); //$NON-NLS-1$
		}
		return returnHistorialDTO;
	}

	private void actualizarHistorial(HistorialDTO historial) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(HistorialDTO) - start"); //$NON-NLS-1$
		}

		historial.setFechaFin(new Date());
		this.guardarHistorial(historial);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorial(HistorialDTO) - end"); //$NON-NLS-1$
		}
	}

	public void nuevoHistorial(String usuario, ProcessInstance pIns) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("nuevoHistorial(String, ProcessInstance) - start"); //$NON-NLS-1$
		}

		Set<String> acts = pIns.findActiveActivityNames();
		String actividad = acts.iterator().next();
		if (actividad.equals(Constantes.ENVIAR_PORTA_FIRMA)) {
			actividad = Constantes.ENVIO_PORTA_FIRMA;
		}
		HistorialDTO historial = new HistorialDTO(usuario, actividad, pIns.getId());
		historial.setFechaInicio(new Date());

		this.guardarHistorial(historial);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("nuevoHistorial(String, ProcessInstance) - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	public List<HistorialDTO> buscarTodosHistoriales(String workflowOrigen) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodosHistoriales(String) - start"); //$NON-NLS-1$
		}

		List<HistorialDTO> returnList = new ArrayList<>();

		List<Historial> listaHistorial = historialRepo.findByWorkflowOrigenOrderByFechaFinAsc(workflowOrigen);

		if (listaHistorial != null && !listaHistorial.isEmpty()) {
			// Ordena la lista de resultados dejando los fechaFin == null al final.
			List<Historial> listaHistorialNullsFirst = new ArrayList<>();
			listaHistorial.stream().forEach(h -> {
				if (h.getFechaFin() != null) {
					listaHistorialNullsFirst.add(h);
				}
			});
			listaHistorial.stream().forEach(h -> {
				if (h.getFechaFin() == null) {
					listaHistorialNullsFirst.add(h);
				}
			});

			returnList.addAll(ListMapper.mapList(listaHistorialNullsFirst, mapper, Historial.class));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodosHistoriales(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public String buscarUsuarioEnHistorial(String workflowOrigen) throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioEnHistorial(String) - start"); //$NON-NLS-1$
		}

		String usuarioReceptor = "";
		List<HistorialDTO> listaHistorial = ListMapper.mapList(
				this.historialRepo.findByWorkflowOrigenAndFechaFinIsNotNullOrderByFechaFinDesc(workflowOrigen), mapper,
				Historial.class);
		List<String> usuariosHistorial = new ArrayList<>();

		if (listaHistorial != null && (!listaHistorial.isEmpty())) {
			for (HistorialDTO h : listaHistorial) {
				if (h.getUsuario() != null) {
					usuariosHistorial.add(h.getUsuario());
				}
			}
		}
		while (("").equals(usuarioReceptor) && !usuariosHistorial.isEmpty()) {
			usuarioReceptor = validarUsuario(usuariosHistorial);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioEnHistorial(String) - end"); //$NON-NLS-1$
		}
		return usuarioReceptor;
	}

	private String validarUsuario(List<String> listaUsuarios) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("validarUsuario(List<String>) - start"); //$NON-NLS-1$
		}

		String usuarioReceptor = "";
		try {
			for (String usuarioAnterior : listaUsuarios) {
				Usuario user = this.usuarioService.obtenerUsuario(usuarioAnterior);
				if (user.getAceptacionTYC() != null && user.getAceptacionTYC()) {
					usuarioReceptor = user.getUsername();
					break;
				} else {
					break;
				}
			}
		} catch (SecurityNegocioException ex) {
			LOGGER.error("Error al buscar usuario en historial. " + ex.getMessage(), ex);
			listaUsuarios.remove(0);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("validarUsuario(List<String>) - end"); //$NON-NLS-1$
			}
			return usuarioReceptor;
		} catch (UsernameNotFoundException ex) {
			LOGGER.error("Error al buscar usuario en historial. " + ex.getMessage(), ex);
			listaUsuarios.remove(0);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("validarUsuario(List<String>) - end"); //$NON-NLS-1$
			}
			return usuarioReceptor;
		}
		listaUsuarios.remove(0);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("validarUsuario(List<String>) - end"); //$NON-NLS-1$
		}
		return usuarioReceptor;
	}

	public void actualizarHistorialUsuarioBaja(Date fechaActual, String executionID, String usuarioBaja,
			String activityName, String mensaje) throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioBaja(Date, String, String, String, String) - start"); //$NON-NLS-1$
		}

		this.historialRepo.actualizarHistorialUsuarioBaja(fechaActual, executionID, activityName, mensaje);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioBaja(Date, String, String, String, String) - end"); //$NON-NLS-1$
		}
	}

	public void actualizarActividadHistorialUsuarioBajaFirmante(String executionID, String activityName)
			throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarActividadHistorialUsuarioBajaFirmante(String, String) - start"); //$NON-NLS-1$
		}

		this.historialRepo.actualizarActividadHistorialUsuarioBajaFirmante(executionID, activityName);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarActividadHistorialUsuarioBajaFirmante(String, String) - end"); //$NON-NLS-1$
		}
	}

	// Usuario baja eliminado por que no se utiliza en la query.
	public void actualizarHistorialUsuarioBajaFirmante(String mensaje, String executionID, String usuarioBaja,
			String activityName) throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioBajaFirmante(String, String, String, String) - start"); //$NON-NLS-1$
		}

		this.historialRepo.actualizarHistorialUsuarioBajaFirmante(mensaje, executionID, activityName);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioBajaFirmante(String, String, String, String) - end"); //$NON-NLS-1$
		}
	}

	public void actualizarHistorialUsuarioRevisorBaja(String mensaje, String executionID, String usuarioBaja)
			throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioRevisorBaja(String, String, String) - start"); //$NON-NLS-1$
		}

		this.historialRepo.actualizarHistorialUsuarioRevisorBaja(mensaje, executionID, usuarioBaja);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarHistorialUsuarioRevisorBaja(String, String, String) - end"); //$NON-NLS-1$
		}
	}

}
