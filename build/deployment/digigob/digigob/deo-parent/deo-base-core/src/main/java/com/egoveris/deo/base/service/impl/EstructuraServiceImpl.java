package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.dao.IEstructuraDAO;
import com.egoveris.deo.base.service.IEstructuraService;
import com.egoveris.deo.model.model.EstructuraBean;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstructuraServiceImpl implements IEstructuraService {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EstructuraServiceImpl.class);

	@Autowired
	private IEstructuraDAO estructuraDAO;

	private List<EstructuraBean> listaEstructurasVigentesActivas;

	public List<EstructuraBean> buscarEstructurasVigentesActivas() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarEstructurasVigentesActivas() - start"); //$NON-NLS-1$
		}

		synchronized (this) {
			if (listaEstructurasVigentesActivas == null) {
				this.listaEstructurasVigentesActivas = estructuraDAO.buscarEstructurasVigentesActivas();
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarEstructurasVigentesActivas() - end"); //$NON-NLS-1$
		}
		return listaEstructurasVigentesActivas;
	}

	public EstructuraBean buscarEstructuraPorId(int idEstructura) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarEstructuraPorId(int) - start"); //$NON-NLS-1$
		}

		EstructuraBean actual;

		Iterator<EstructuraBean> iterator = this.buscarEstructurasVigentesActivas().iterator();
		while (iterator.hasNext()) {
			actual = iterator.next();
			if (actual.getId().compareTo(idEstructura) == 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("buscarEstructuraPorId(int) - end"); //$NON-NLS-1$
				}
				return actual;
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarEstructuraPorId(int) - end"); //$NON-NLS-1$
		}
		return null;
	}
}
