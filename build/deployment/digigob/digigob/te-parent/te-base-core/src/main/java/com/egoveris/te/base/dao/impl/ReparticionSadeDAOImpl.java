package com.egoveris.te.base.dao.impl;


import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.te.base.dao.ReparticionSadeDAO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.jbpm.api.identity.Group;
import org.jbpm.pvm.internal.identity.impl.GroupImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Deprecated
@Repository
public class ReparticionSadeDAOImpl implements ReparticionSadeDAO {
	private static final Logger logger = LoggerFactory.getLogger(ReparticionSadeDAOImpl.class);

	@Autowired
	private ReparticionServ reparticionServ;

	public List<Group> buscarReparticionesPorUsuario(String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesPorUsuario(userName={}) - start", userName);
		}

		ReparticionBean reparticion = this.reparticionServ
				.buscarReparticionPorUsuario(userName);
		List<Group> grupos = new ArrayList<>();
		GroupImpl grupo = new GroupImpl();
		grupo.setId(reparticion.getCodigo());
		grupo.setName(reparticion.getCodigo());

		grupos.add(grupo);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesPorUsuario(String) - end - return value={}", grupos);
		}
		return grupos;
	}

	// Por modificacion de PaginSorting Groups
	public Group buscarReparticionesByUsuario(String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesByUsuario(userName={}) - start", userName);
		}

		ReparticionBean reparticion = this.reparticionServ
				.buscarReparticionPorUsuario(userName);
		GroupImpl grupo = new GroupImpl();
		grupo.setId(reparticion.getCodigo().trim());
		grupo.setName(reparticion.getCodigo().trim());

		if (logger.isDebugEnabled()) {
			logger.debug("buscarReparticionesByUsuario(String) - end - return value={}", grupo);
		}
		return grupo;
	}

	class ReparticionComparator implements Comparator<ReparticionBean> {
		public int compare(ReparticionBean o1, ReparticionBean o2) {
			if (logger.isDebugEnabled()) {
				logger.debug("compare(o1={}, o2={}) - start", o1, o2);
			}
			
			String codigoReparticion1 = o1.getCodigo();
			String codigoReparticion2 = o2.getCodigo();
			int returnint = codigoReparticion1.compareTo(codigoReparticion2);
			if (logger.isDebugEnabled()) {
				logger.debug("compare(ReparticionBean, ReparticionBean) - end - return value={}", returnint);
			}
			return returnint;
		}

	}

	public boolean validarCodigoReparticion(String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoReparticion(value={}) - start", value);
		}

		ReparticionBean current;
		Iterator<ReparticionBean> iterator = this.reparticionServ
				.buscarReparticionesVigentesActivas().iterator();
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getCodigo().trim().equalsIgnoreCase(value)) {
				if (logger.isDebugEnabled()) {
					logger.debug("validarCodigoReparticion(String) - end - return value={}", true);
				}
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoReparticion(String) - end - return value={}", false);
		}
		return false;
	}

	public ReparticionServ getReparticionServ() {
		return reparticionServ;
	}

	public void setReparticionServ(ReparticionServ reparticionServ) {
		this.reparticionServ = reparticionServ;
	}

}
