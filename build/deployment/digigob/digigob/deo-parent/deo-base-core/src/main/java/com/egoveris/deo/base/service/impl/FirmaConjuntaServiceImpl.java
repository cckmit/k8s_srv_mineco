package com.egoveris.deo.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.exception.SinPersistirException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.model.Firmante;
import com.egoveris.deo.base.repository.FirmanteRepository;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service
public class FirmaConjuntaServiceImpl implements FirmaConjuntaService {

	private static final Logger LOGGER= LoggerFactory
			.getLogger(FirmaConjuntaServiceImpl.class);

	@Autowired
	private FirmanteRepository firmanteRepository;
	@Autowired
	private IUsuarioService usuariosService;
	@Autowired
	@Qualifier("deoCoreMapper")
	private Mapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public void guardarFirmantes(List<Usuario> usuariosFirmantes, String workflowId)
			throws SinPersistirException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarFirmantes(List<Usuario>, String) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> firmantes = new ArrayList<>();
		Integer orden = 1;
		for (Usuario usuarioFirmante : usuariosFirmantes) {
			FirmanteDTO firmante = new FirmanteDTO();
			firmante.setUsuarioFirmante(usuarioFirmante.getUsername());
			firmante.setOrden(orden);
			firmante.setWorkflowId(workflowId);
			firmante.setEstadoFirma(false);
			firmante.setUsuarioRevisor(usuarioFirmante.getUsuarioRevisor() != null && !usuarioFirmante.getUsuarioRevisor().isEmpty()
										? usuarioFirmante.getUsuarioRevisor() : null);
			firmante.setEstadoRevision(false);
			orden++;
			firmantes.add(firmante);
		}
		try {
			this.firmanteRepository.save(ListMapper.mapList(firmantes, mapper, Firmante.class));
		} catch (SinPersistirException spe) {
			LOGGER.error("No se puede persistir el objeto," + firmantes, spe);
			throw new SinPersistirException("No se han podido guardar los usuarios firmantes");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarFirmantes(List<Usuario>, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarFirmantesPorProceso(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorProceso(String) - start"); //$NON-NLS-1$
		}

		List<Usuario> usuarios = new ArrayList<>();
		List<FirmanteDTO> firmantes = (ListMapper.mapList(
				this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId), mapper, FirmanteDTO.class));
		for (FirmanteDTO firmante : firmantes) {
			Usuario usuario;
			try {
				usuario = this.usuariosService.obtenerUsuario(firmante.getUsuarioFirmante());
				if (firmante.getUsuarioRevisor() != null) {
					usuarios.add(armarDatosUsuarioBeanConUsuarioRevisor(usuario, firmante));
				} else {
					usuarios.add(usuario);
				}
			} catch (SecurityNegocioException e) {
				LOGGER.error("Mensaje de error", e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorProceso(String) - end"); //$NON-NLS-1$
		}
		return usuarios;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FirmanteDTO> buscarFirmantesPorTarea(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorTarea(String) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> returnList = ListMapper.mapList(
				this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId), mapper, FirmanteDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorTarea(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<Usuario> buscarRevisoresPorProceso(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisoresPorProceso(String) - start"); //$NON-NLS-1$
		}

		List<Usuario> usuarios = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<FirmanteDTO> firmantes = ListMapper.mapList(
				this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId), mapper, FirmanteDTO.class);
		for (FirmanteDTO firmante : firmantes) {
			if (firmante.getUsuarioRevisor() != null) {
				Usuario usuario;
				try {
					if (firmante.getUsuarioRevisor() != null
							&& !firmante.getUsuarioRevisor().trim().isEmpty()) {
						usuario = this.usuariosService.obtenerUsuario(firmante.getUsuarioRevisor());
						usuarios.add(usuario);
					}
				} catch (SecurityNegocioException e) {
					LOGGER.error("Mensaje de error", e);
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisoresPorProceso(String) - end"); //$NON-NLS-1$
		}
		return usuarios;
	}

	private Usuario armarDatosUsuarioBeanConUsuarioRevisor(Usuario usuarioFirmante,
			FirmanteDTO usuarioRevisor) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("armarDatosUsuarioBeanConUsuarioRevisor(Usuario, FirmanteDTO) - start"); //$NON-NLS-1$
		}

		Usuario nuevoUsuarioFirmante = new Usuario();

		nuevoUsuarioFirmante.setAceptacionTYC(usuarioFirmante.getAceptacionTYC());
		nuevoUsuarioFirmante.setNombreApellido(usuarioFirmante.getNombreApellido());
		nuevoUsuarioFirmante.setOcupacion(usuarioFirmante.getOcupacion());
		nuevoUsuarioFirmante.setCargo(usuarioFirmante.getCargo());
		nuevoUsuarioFirmante.setExternalizarFirmaGEDO(usuarioFirmante.getExternalizarFirmaGEDO());
		nuevoUsuarioFirmante.setCuit(usuarioFirmante.getCuit());
		nuevoUsuarioFirmante.setEmail(usuarioFirmante.getEmail());
		nuevoUsuarioFirmante.setCodigoReparticion(usuarioFirmante.getCodigoReparticion());
		nuevoUsuarioFirmante.setUsername(usuarioFirmante.getUsername());
		nuevoUsuarioFirmante.setUsuarioRevisor(usuarioRevisor.getUsuarioRevisor());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("armarDatosUsuarioBeanConUsuarioRevisor(Usuario, FirmanteDTO) - end"); //$NON-NLS-1$
		}
		return nuevoUsuarioFirmante;
	}

	@Override
	public List<Usuario> buscarFirmantesPorEstado(String workflowId, boolean estadoFirma) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorEstado(String, boolean) - start"); //$NON-NLS-1$
		}

		List<Usuario> usuarios = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<FirmanteDTO> firmantes = ListMapper.mapList(this.firmanteRepository
				.findByWorkflowIdAndEstadoFirmaOrderByOrden(workflowId, estadoFirma), mapper,
				FirmanteDTO.class);
		for (FirmanteDTO firmante : firmantes) {
			Usuario usuario;
			try {
				usuario = this.usuariosService.obtenerUsuario(firmante.getUsuarioFirmante());
				usuarios.add(usuario);
			} catch (SecurityNegocioException e) {
				LOGGER.error("Mensaje de error", e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmantesPorEstado(String, boolean) - end"); //$NON-NLS-1$
		}
		return usuarios;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> buscarTareasPorFirmante(String usuario) throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTareasPorFirmante(String) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> listaFirmante = ListMapper.mapList(
				this.firmanteRepository.findByUsuarioFirmanteAndEstadoFirmaIsNull(usuario),
				mapper, FirmanteDTO.class);

		List<String> workflowidFirmante = new ArrayList<>();

		if (null != listaFirmante && !listaFirmante.isEmpty()) {
			for (FirmanteDTO h : listaFirmante) {
				if (h.getWorkflowId() != null) {
					workflowidFirmante.add(h.getWorkflowId());
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTareasPorFirmante(String) - end"); //$NON-NLS-1$
		}
		return workflowidFirmante;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FirmanteDTO> obtenerFirmantesPorEstado(String workflowId, boolean estadoFirma) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerFirmantesPorEstado(String, boolean) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> firmantes = ListMapper.mapList(this.firmanteRepository
				.findByWorkflowIdAndEstadoFirmaOrderByOrden(workflowId, estadoFirma), mapper,
				FirmanteDTO.class);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerFirmantesPorEstado(String, boolean) - end"); //$NON-NLS-1$
		}
		return firmantes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarRevisoresPorEstado(String workflowId, boolean estadoRevision) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisoresPorEstado(String, boolean) - start"); //$NON-NLS-1$
		}

		List<Usuario> usuarios = new ArrayList<>();
		List<FirmanteDTO> revisores = ListMapper.mapList(this.firmanteRepository
				.findByWorkflowIdAndEstadoRevisionOrderByOrden(workflowId, estadoRevision), mapper,
				FirmanteDTO.class);

		for (FirmanteDTO revisor : revisores) {
			Usuario usuario;
			try {
				if (revisor.getUsuarioRevisor() != null && !revisor.getUsuarioRevisor().isEmpty()) {
					usuario = this.usuariosService.obtenerUsuario(revisor.getUsuarioRevisor());					
					usuarios.add(usuario);
				}
			} catch (SecurityNegocioException e) {
				LOGGER.error("Mensaje de error", e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisoresPorEstado(String, boolean) - end"); //$NON-NLS-1$
		}
		return usuarios;
	}

	@Override
	public void actualizarFirmante(String usuario, boolean estadoFirma, String workflowId,
			String apoderado) throws ValidacionCampoFirmaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmante(String, boolean, String, String) - start"); //$NON-NLS-1$
		}

//		Firmante firmante = this.firmanteRepository.
//				findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
//						usuario, workflowId, estadoFirma);
		Firmante firmante = this.firmanteRepository.
		findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
		usuario, workflowId, false);
		if(firmante != null) {
			firmante.setApoderado(apoderado);
			firmante.setEstadoFirma(estadoFirma);
			this.firmanteRepository.save(firmante);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmante(String, boolean, String, String) - end"); //$NON-NLS-1$
		}
	}
	
	@Override
	public void actualizaFirmante(String usuario, boolean estadoFirma, String workflowId) throws ValidacionCampoFirmaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmante(String, boolean, String, String) - start"); //$NON-NLS-1$
		}

		Firmante firmante = this.firmanteRepository.
				findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
						usuario, workflowId, false);
		if(firmante != null) {
			firmante.setEstadoFirma(estadoFirma);
			this.firmanteRepository.save(firmante);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmante(String, boolean, String, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void actualizarRevisor(String usuario, boolean estadoRevision, String workflowId)
			throws ApplicationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarRevisor(String, boolean, String) - start"); //$NON-NLS-1$
		}

		List<Firmante> firmantes = this.firmanteRepository.
				findByUsuarioRevisorAndWorkflowIdAndEstadoRevisionOrderByOrden(
						usuario, workflowId, false);
		if(firmantes != null && firmantes.size() > 0) {
			Firmante firmante = firmantes.get(0);
			firmante.setEstadoRevision(estadoRevision);	
			this.firmanteRepository.save(firmante);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarRevisor(String, boolean, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public FirmanteDTO buscarFirmante(String usuario, String workflowId, boolean estadoFirma) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmante(String, String, boolean) - start"); //$NON-NLS-1$
		}

		FirmanteDTO firmantes = mapper
				.map(this.firmanteRepository.findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
						usuario, workflowId, estadoFirma), FirmanteDTO.class);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFirmante(String, String, boolean) - end"); //$NON-NLS-1$
		}
		return firmantes;

	}

	@Override
	@SuppressWarnings("unchecked")
	public FirmanteDTO buscarRevisorPorFirmante(String usuario, String workflowId,
			boolean estadoRevision) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> firmantes = ListMapper.mapList(
				this.firmanteRepository.findByUsuarioRevisorAndWorkflowIdAndEstadoRevisionOrderByOrden(
						usuario, workflowId, estadoRevision),
				mapper, FirmanteDTO.class);
		if (CollectionUtils.isEmpty(firmantes)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return null;
		} else {
			FirmanteDTO returnFirmanteDTO = firmantes.get(0);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return returnFirmanteDTO;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FirmanteDTO> buscarRevisorFirmante(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> firmantes = (ListMapper.mapList(
						this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId), mapper, FirmanteDTO.class));
		if (CollectionUtils.isEmpty(firmantes)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return null;
		} else {
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisorPorFirmante(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return firmantes;
		}
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	public FirmanteDTO buscarRevisor(String usuario, String workflowId, boolean estadoRevision) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarRevisor(String, String, boolean) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> firmantes = ListMapper.mapList(
				this.firmanteRepository.findByUsuarioRevisorAndWorkflowIdAndEstadoRevisionOrderByOrden(
						usuario, workflowId, estadoRevision),
				mapper, FirmanteDTO.class);
		if (CollectionUtils.isEmpty(firmantes)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisor(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return null;
		} else {
			FirmanteDTO returnFirmanteDTO = firmantes.get(0);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("buscarRevisor(String, String, boolean) - end"); //$NON-NLS-1$
			}
			return returnFirmanteDTO;
		}
	}

	@Override
	public void actualizarFirmantes(List<Usuario> usuariosFirmantes, String workflowId)
			throws ApplicationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmantes(List<Usuario>, String) - start"); //$NON-NLS-1$
		}

		try {
			 List<Firmante> firmantes = this.firmanteRepository.
					 findByWorkflowIdOrderByOrden(workflowId);
			if(firmantes != null) {
				for (Firmante firmante : firmantes) {
					firmanteRepository.delete(firmante);
				}
			}
			if (usuariosFirmantes != null && !usuariosFirmantes.isEmpty())
				this.guardarFirmantes(usuariosFirmantes, workflowId);
		} catch (Exception e) {
			LOGGER.error("Error actualizando lista de firmantes en base de datos ", e);
			throw e;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarFirmantes(List<Usuario>, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void reemplazarFirmante(String usuarioFirmanteActual, String usuarioFirmanteNuevo,
			String workflowId) throws SinPersistirException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("reemplazarFirmante(String, String, String) - start"); //$NON-NLS-1$
		}

		try {
			Firmante firmante =this.firmanteRepository.findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
								usuarioFirmanteActual, workflowId, false);
				if(firmante != null) {
					firmante.setUsuarioFirmante(usuarioFirmanteNuevo);
					this.firmanteRepository.save(firmante);
				}
		} catch (SinPersistirException spe) {
			LOGGER.error("No se puede persistir el objeto,", spe);
			throw new SinPersistirException("No se han podido guardar los usuarios firmantes", spe);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("reemplazarFirmante(String, String, String) - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean esUltimoFirmante(String usuarioFirmante, String workflowId, boolean estadoFirma) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("esUltimoFirmante(String, String, boolean) - start"); //$NON-NLS-1$
		}

		boolean ultimoFirmante = false;
	List<FirmanteDTO> firmantes = ListMapper.mapList(this.firmanteRepository
				.findByWorkflowIdAndEstadoFirmaOrderByOrdenDesc(workflowId, estadoFirma), mapper, 
				FirmanteDTO.class);
				
		if(firmantes != null && firmantes.size() > 0
				&& usuarioFirmante != null){
				 Integer numeroFirmantes = this.firmanteRepository.findByWorkflowId(workflowId).size();
				for(FirmanteDTO firmante : firmantes){
						if (usuarioFirmante.equalsIgnoreCase(firmante.getUsuarioFirmante())
								&& firmante.getOrden().equals(numeroFirmantes)) {
							ultimoFirmante = true;
							break;
						}
				}
		}		
	 

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("esUltimoFirmante(String, String, boolean) - end"); //$NON-NLS-1$
		}
		return ultimoFirmante;
	}

	@Override
	public boolean esUltimoRevisor(String usuarioRevisor, String workflowId,
			boolean estadoRevision) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("esUltimoRevisor(String, String, boolean) - start"); //$NON-NLS-1$
		}

		boolean ultimoRevisor = false;
		Firmante revisor = this.firmanteRepository
				.findByWorkflowIdAndEstadoRevisionOrderByOrdenDesc(workflowId, estadoRevision);
		if(revisor != null) {
			Integer numeroFirmantes = 0;
			List<Firmante> firmantesList =	this.firmanteRepository.findByWorkflowId(workflowId);
			if(CollectionUtils.isNotEmpty(firmantesList)) {
				numeroFirmantes = firmantesList.size();
			}
			
				if (revisor != null && revisor.getUsuarioRevisor() != null 
					&&	revisor.getUsuarioRevisor().compareTo(usuarioRevisor) == 0
						&& revisor.getOrden().equals(numeroFirmantes)) {
					ultimoRevisor = true;
				}
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("esUltimoRevisor(String, String, boolean) - end"); //$NON-NLS-1$
		}
		return ultimoRevisor;
	}

	@Override
	public void actualizarEstadoFirmantes(String workflowId, boolean estadoFirma)
			throws SinPersistirException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarEstadoFirmantes(String, boolean) - start"); //$NON-NLS-1$
		}
		try {
			List<Firmante> firmantes = this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId);
			if(firmantes != null) {
				for (Firmante firmante : firmantes) {
					firmante.setEstadoFirma(estadoFirma);
					this.firmanteRepository.save(firmante);
				}
			}
		} catch (SinPersistirException spe) {
			LOGGER.error("No se puede persistir Firmarntes,", spe);
			throw new SinPersistirException("No se han podido actualizar el estado de los firmantes",spe);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarEstadoFirmantes(String, boolean) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void actualizarEstadoRevisores(String workflowId, boolean estadoRevision)
			throws SinPersistirException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarEstadoRevisores(String, boolean) - start"); //$NON-NLS-1$
		}
		
		try {
			List<Firmante> firmantes = this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId);
				if(firmantes != null) {
					for (Firmante firmante : firmantes) {
						firmante.setEstadoRevision(estadoRevision);
						this.firmanteRepository.save(firmante);
					}
				}
		} catch (SinPersistirException spe) {
			LOGGER.error("No se puede persistir el objeto,", spe);
			throw new SinPersistirException("No se han podido actualizar el estado de los firmantes",
					spe);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarEstadoRevisores(String, boolean) - end"); //$NON-NLS-1$
		}
	}
	

	@Override
	public Integer nroFirmaFirmante(String usuario, String workflowId, boolean estadoFirma) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("nroFirmaFirmante(String, String, boolean) - start"); //$NON-NLS-1$
		}
		Integer returnInteger = 0;
		Firmante firmante = this.firmanteRepository.
				findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(
						usuario, workflowId, estadoFirma);
		if(firmante != null && firmante.getOrden() != null) {
			returnInteger = firmante.getOrden() - 1;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("nroFirmaFirmante(String, String, boolean) - end"); //$NON-NLS-1$
		}
		return returnInteger;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<FirmanteDTO> buscarUsuarioFirmantesPorProceso(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioFirmantesPorProceso(String) - start"); //$NON-NLS-1$
		}

		List<Firmante> firmantesList = this.firmanteRepository.findByWorkflowIdOrderByOrden(workflowId);
		if(CollectionUtils.isNotEmpty(firmantesList)) {
				return ListMapper.mapList(
					 firmantesList , mapper, FirmanteDTO.class);			
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioFirmantesPorProceso(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Metodo no se usa
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Deprecated
	public String buscarUsuarioFirmante(String workflowId, boolean estadoFirma)
			throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioFirmante(String, boolean) - start"); //$NON-NLS-1$
		}

		List<FirmanteDTO> listaFirmantes = ListMapper.mapList(this.firmanteRepository
				.findByWorkflowIdAndEstadoFirmaOrderByOrden(workflowId, estadoFirma), mapper,
				FirmanteDTO.class);
		String usuriosFirmante = "";

		for (FirmanteDTO firmante : listaFirmantes) {
			usuriosFirmante = firmante.getUsuarioFirmante();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarUsuarioFirmante(String, boolean) - end"); //$NON-NLS-1$
		}
		return usuriosFirmante;

	}

	/**
	 * Metodo no se usa
	 */
	@Override
	@Deprecated
	public void eliminarRevisor(String usuario) throws EjecucionSiglaException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarRevisor(String) - start"); //$NON-NLS-1$
		}
		
	 // this.firmanteRepository.delete(mapper.map(usuario, Firmante.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarRevisor(String) - end"); //$NON-NLS-1$
		}
	}
}