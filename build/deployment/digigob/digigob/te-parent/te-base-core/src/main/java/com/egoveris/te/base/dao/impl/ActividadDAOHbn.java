package com.egoveris.te.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.dao.ActividadDAO;
import com.egoveris.te.base.exception.SinPersistirException;
import com.egoveris.te.base.model.Actividad;
import com.egoveris.te.base.model.TipoActividad;
import com.egoveris.te.base.util.ListUtil;

@Deprecated
@Repository
public class ActividadDAOHbn extends HibernateDaoSupport implements ActividadDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
		setSessionFactory(sessionFactory);
	}

	public Long crearActividad(Actividad obj) throws DataAccessException {
		try {
			this.getHibernateTemplate().persist(obj);
			this.getHibernateTemplate().flush();
		} catch (HibernateException he) {
			throw new SinPersistirException("No se pudo persistir la actividad: " + obj, he);
		}
		return obj.getId();
	}

	public Actividad findActividad(Long id) {
		return this.getHibernateTemplate().get(Actividad.class, id);
	}

	public void deleteActividad(Actividad obj) throws DataAccessException {
		try {
			this.getHibernateTemplate().delete(obj);
		} catch (HibernateException he) {
			throw new SinPersistirException("No se pudo eliminar la actividad: " + obj, he);
		}
	}

	public void actualizarActividad(Actividad obj) throws DataAccessException {
		try {
			this.getHibernateTemplate().update(obj);
		} catch (HibernateException he) {
			throw new SinPersistirException("No se pudo actualizar la actividad: " + obj, he);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarActividadesVigentes(String idObj) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
		criteria.add(Restrictions.and(Restrictions.isNull("fechaCierre"),
				Restrictions.and(Restrictions.isNull("parentId"), Restrictions.eq("idObjetivo", idObj))));
		criteria.addOrder(Order.desc("fechaAlta"));
	 return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarHistoricoActividades(String idObj) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
		criteria.add(Restrictions.and(Restrictions.isNull("parentId"), Restrictions.eq("idObjetivo", idObj)));
		criteria.addOrder(Order.desc("fechaAlta"));
		return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarActividadesVigentes(List<String> idObj) {

		List<List<String>> listaPart = ListUtil.partition(idObj, 999);
		List<Actividad> result = new ArrayList<Actividad>();
		for (List<String> list : listaPart) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
			criteria.add(Restrictions.and(Restrictions.isNull("fechaCierre"),
					Restrictions.and(Restrictions.isNull("parentId"), Restrictions.in("idObjetivo", list))));
			criteria.addOrder(Order.desc("fechaAlta"));
		//	criteria.setProjection(Projections.distinct(Projections.id()));
			result.addAll((List<Actividad>) getHibernateTemplate().findByCriteria(criteria));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarSubActividades(Long activId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
		criteria.add(Restrictions.eq("parentId", activId));
		criteria.addOrder(Order.desc("fechaAlta"));
		return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarSubActividadesPorTipo(Long activId, String tipo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
		criteria.createAlias("tipoActividad", "tipoAct");
		criteria.add(Restrictions.and(Restrictions.eq("tipoAct.nombre", tipo), Restrictions.eq("parentId", activId)));
		criteria.addOrder(Order.desc("fechaAlta"));
		return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarActividadPorEstado(String estado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
		criteria.add(Restrictions.eq("estado", estado));
		return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarActividadesPendientes(TipoActividad tipo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);

		if (tipo != null) {
			criteria.add(Restrictions.and(Restrictions.isNull("fechaCierre"),
					Restrictions.and(Restrictions.isNull("parentId"), Restrictions.eq("tipoActividad", tipo))));
		} else {
			criteria.add(Restrictions.and(Restrictions.isNull("fechaCierre"), Restrictions.isNull("parentId")));
		}
		return (List<Actividad>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> buscarActividadesVigentesPorEstado(List<String> idObj, List<String> estados) {

		List<List<String>> listaPart = ListUtil.partition(idObj, 999);
		List<Actividad> result = new ArrayList<Actividad>();
		for (List<String> list : listaPart) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Actividad.class);
			criteria.add(Restrictions.and(Restrictions.isNull("fechaCierre"), Restrictions.in("idObjetivo", list)));
			criteria.add(Restrictions.in("estado", estados));
			result.addAll((List<Actividad>) getHibernateTemplate().findByCriteria(criteria));
		}
		return result;
	}
}