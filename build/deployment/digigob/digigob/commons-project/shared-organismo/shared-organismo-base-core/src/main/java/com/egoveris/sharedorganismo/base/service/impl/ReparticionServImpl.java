package com.egoveris.sharedorganismo.base.service.impl;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedorganismo.base.exception.ReparticionGenericDAOException;
import com.egoveris.sharedorganismo.base.model.Reparticion;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.repository.ReparticionRepository;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de AbstractReparticionServImpl que además de la lista con
 * todas las reparticiones provee dos listas: reparticiones vigentes y
 * reparticiones vigentes + "es DGTAL"
 * 
 * @author alelarre
 *
 */
@Service
@Transactional(value = "jpaTransactionManagerOrg", readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class ReparticionServImpl implements ReparticionServ {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ReparticionServImpl.class);

	private List<ReparticionBean> listaReparticionesVigentesActivas = new ArrayList<>();
	private List<ReparticionBean> listaReparticionesDGTAL = new ArrayList<>();

	@Autowired
	private ReparticionRepository reparticionRepo;
	@Autowired
	@Qualifier("organismoMapper")
	private Mapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReparticionBean> buscarReparticionesVigentesActivas() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesVigentesActivas() - start"); //$NON-NLS-1$
		}
		List<Reparticion> listaRepar = reparticionRepo.findAll();
		List<ReparticionBean> reparticionesTemp = ListMapper.mapList(listaRepar, this.mapper, ReparticionBean.class);
		List<ReparticionBean> reparticionesDto = new ArrayList<>();
		for (ReparticionBean rep : reparticionesTemp) {
			if (rep.isVigenteActiva()) {
				reparticionesDto.add(rep);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesVigentesActivas() - end"); //$NON-NLS-1$
		}
		return reparticionesDto;
		
	}

	@Override
	public List<ReparticionBean> buscarTodasLasReparticionesDGTAL() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticionesDGTAL() - start"); //$NON-NLS-1$
		}

		synchronized (this.listaReparticionesDGTAL) {
			if (logger.isDebugEnabled()) {
				logger.debug("buscarTodasLasReparticionesDGTAL() - end"); //$NON-NLS-1$
			}
			return this.listaReparticionesDGTAL;
		}
	}

	private void filtrarReparticionesActivasVigentes() {
		if (logger.isDebugEnabled()) {
			logger.debug("filtrarReparticionesActivasVigentes() - start"); //$NON-NLS-1$
		}

		List<ReparticionBean> listaReparticionesVigentesActivasTmp = new ArrayList<>();

		for (ReparticionBean rep : this.listaReparticiones) {
			if (rep.isVigenteActiva()) {
				listaReparticionesVigentesActivasTmp.add(rep);
			}
		}

		// Intercambio las listas bloqueando el uso de la lista no temporal
		synchronized (this.listaReparticionesVigentesActivas) {
			listaReparticionesVigentesActivas = listaReparticionesVigentesActivasTmp;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("filtrarReparticionesActivasVigentes() - end"); //$NON-NLS-1$
		}
	}

	private ReparticionBean devolverDGTALByReparticionEncontrada(String codReparticionConDGTAL) {
		if (logger.isDebugEnabled()) {
			logger.debug("devolverDGTALByReparticionEncontrada(String) - start"); //$NON-NLS-1$
		}

		if (codReparticionConDGTAL == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("devolverDGTALByReparticionEncontrada(String) - end"); //$NON-NLS-1$
			}
			return null;
		}
		for (ReparticionBean r : listaReparticionesDGTAL) {
			if (r.getCodigo().equals(codReparticionConDGTAL.trim())) {
				if (logger.isDebugEnabled()) {
					logger.debug("devolverDGTALByReparticionEncontrada(String) - end"); //$NON-NLS-1$
				}
				return r;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("devolverDGTALByReparticionEncontrada(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public ReparticionBean buscarDGTALByReparticion(String codReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarDGTALByReparticion(String) - start"); //$NON-NLS-1$
		}

		for (ReparticionBean r : listaReparticionesVigentesActivas) {
			if (r.getCodigo().equals(codReparticion)) {
				ReparticionBean returnReparticionBean = this.devolverDGTALByReparticionEncontrada(r.getCodDgtal());
				if (logger.isDebugEnabled()) {
					logger.debug("buscarDGTALByReparticion(String) - end"); //$NON-NLS-1$
				}
				return returnReparticionBean;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDGTALByReparticion(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	private void filtrarReparticionesDGTAL() {
		if (logger.isDebugEnabled()) {
			logger.debug("filtrarReparticionesDGTAL() - start"); //$NON-NLS-1$
		}

		List<ReparticionBean> listaReparticionesDGTALTmp = new ArrayList<>();
		for (ReparticionBean rep : this.listaReparticionesVigentesActivas) {
			if (null != rep.getEsDigital() && rep.getEsDigital() == 1) {
				listaReparticionesDGTALTmp.add(rep);
			}
		}

		// Intercambio las listas bloqueando el uso de la lista no temporal
		synchronized (this.listaReparticionesDGTAL) {
			listaReparticionesDGTAL = listaReparticionesDGTALTmp;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("filtrarReparticionesDGTAL() - end"); //$NON-NLS-1$
		}
	}

	protected void cargarListasAdicionales() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarListasAdicionales() - start"); //$NON-NLS-1$
		}

		filtrarReparticionesActivasVigentes();
		filtrarReparticionesDGTAL();

		if (logger.isDebugEnabled()) {
			logger.debug("cargarListasAdicionales() - end"); //$NON-NLS-1$
		}
	}

	protected List<ReparticionBean> listaReparticiones = new ArrayList<>();
	private String tiempo;
	private boolean stopThread = false;

	@Override
	public void loadReparticiones() {
		if (logger.isDebugEnabled()) {
			logger.debug("loadReparticiones() - start"); //$NON-NLS-1$
		}
		this.cargarListas();

		if (logger.isDebugEnabled()) {
			logger.debug("loadReparticiones() - end"); //$NON-NLS-1$
		}
	}

	private void cargarListas() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarListas() - start"); //$NON-NLS-1$
		} 
		List<Reparticion> listaRepar = reparticionRepo.findAll();
				 
		List<ReparticionBean> listaReparticionesTmp = ListMapper.mapList(listaRepar, this.mapper,
				ReparticionBean.class);
		
		this.listaReparticiones = listaReparticionesTmp;

		this.cargarListasAdicionales();

		if (logger.isDebugEnabled()) {
			logger.debug("cargarListas() - end"); //$NON-NLS-1$
		}
	}

	public List<ReparticionBean> buscarTodasLasReparticiones() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticiones() - start"); //$NON-NLS-1$
		}
		cargarListas(); 
		
		return this.listaReparticiones;
		 
	}

	@Override
	public List<ReparticionBean> buscarTodasLasReparticiones(String textoBusqueda) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticiones(String) - start"); //$NON-NLS-1$
		}

		ReparticionBean current;
		List<ReparticionBean> listaReparticionesRtn = new ArrayList<>();

		Iterator<ReparticionBean> iterator = this.buscarTodasLasReparticiones().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if ((current.getCodigo() != null && current.getCodigo().contains(textoBusqueda))
					|| (current.getNombre() != null && current.getNombre().contains(textoBusqueda))) {
				listaReparticionesRtn.add(current);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticiones(String) - end"); //$NON-NLS-1$
		}
		return listaReparticionesRtn;
	}

	@Override
	public List<ReparticionBean> buscarTodasLasReparticionesNombre(String textoBusqueda) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticionesNombre(String) - start"); //$NON-NLS-1$
		}

		ReparticionBean current;
		List<ReparticionBean> listaReparticionesRtn;
		listaReparticionesRtn = new ArrayList<>();

		Iterator<ReparticionBean> iterator = this.buscarTodasLasReparticiones().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getNombre() != null && current.getNombre().contains(textoBusqueda))
				listaReparticionesRtn.add(current);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticionesNombre(String) - end"); //$NON-NLS-1$
		}
		return listaReparticionesRtn;
	}

	@Override
	public List<ReparticionBean> buscarTodasLasReparticionesCodigo(String textoBusqueda) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticionesCodigo(String) - start"); //$NON-NLS-1$
		}

		ReparticionBean current;
		List<ReparticionBean> listaReparticionesRtn;
		listaReparticionesRtn = new ArrayList<>();

		Iterator<ReparticionBean> iterator = this.buscarTodasLasReparticiones().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getCodigo() != null && current.getCodigo().contains(textoBusqueda))
				listaReparticionesRtn.add(current);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodasLasReparticionesCodigo(String) - end"); //$NON-NLS-1$
		}
		return listaReparticionesRtn;
	}

	@Override
	public ReparticionBean buscarReparticionPorCodigo(String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorCodigo(String) - start"); //$NON-NLS-1$
		}

		ReparticionBean current;

		Iterator<ReparticionBean> iterator = this.buscarTodasLasReparticiones().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getCodigo() != null && current.getCodigo().trim().equalsIgnoreCase(codigoReparticion.trim()))
				return current;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorCodigo(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public ReparticionBean buscarReparticionPorId(Long idReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorId(int) - start"); //$NON-NLS-1$
		}

		ReparticionBean current;

		Iterator<ReparticionBean> iterator = this.buscarTodasLasReparticiones().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getId().compareTo(idReparticion) == 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarReparticionPorId(int) - end"); //$NON-NLS-1$
				}
				return current;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorId(int) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public boolean validarCodigoReparticion(String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoReparticion(String) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = buscarReparticionPorCodigo(codigoReparticion) != null;
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoReparticion(String) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	@Override
	public ReparticionBean buscarReparticionPorUsuario(String username) throws ReparticionGenericDAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorUsuario(String) - start"); //$NON-NLS-1$
		}

		ReparticionBean returnReparticionBean = null;
		Reparticion result = this.reparticionRepo.findByUserName(username);
		if (result != null) {
			returnReparticionBean = this.mapper.map(result, ReparticionBean.class);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionPorUsuario(String) - end"); //$NON-NLS-1$
		}
		return returnReparticionBean;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getTiempo() {
		return tiempo;
	}

	@Override
	public String buscarNombreReparticionPorCodigo(String codigoReparticion) {
		return reparticionRepo.buscarNombreReparticionPorCodigo(codigoReparticion);
	}

}
