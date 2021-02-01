package com.egoveris.deo.base.dao.impl;

import com.egoveris.deo.base.dao.TaskGedoDAO;
import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.TareaBusqueda;
import com.egoveris.deo.base.model.TareaJBPM;
import com.egoveris.deo.util.Constantes;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.JbpmException;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.query.AbstractQuery;
import org.jbpm.pvm.internal.query.Page;
import org.jbpm.pvm.internal.session.QuerySession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.terasoluna.plus.common.exception.ApplicationException;

@Deprecated
@Repository
public class TaskGedoDAOHbn extends HibernateDaoSupport implements TaskGedoDAO {
  @Autowired
  private SessionFactory sessionFactory;
  
  @PostConstruct
  public void init(){
    setSessionFactory(sessionFactory);
  }

  public List<Task> buscarTasksPorUsuarioYCaractDeTipoDoc(String usuario, String caractTipoDoc,
      String inicioCarga, String tamanoPaginacion, String criterio, String orden) {

    QueryTaskGedo tq = new QueryTaskGedo();
    tq.assignee(usuario);
    tq.caract(caractTipoDoc);

    if (orden != null && criterio != null) {
      if (orden.compareTo("ascending") == 0) {
        tq.orderAsc(criterio);
      } else {
        tq.orderDesc(criterio);
      }
    }

    if (inicioCarga != null && tamanoPaginacion != null) {
      tq.page(Integer.parseInt(inicioCarga), Integer.parseInt(tamanoPaginacion));
    }

    return tq.list();
  }

  public List<TareaBusqueda> buscarTareasPorUsuarioInvolucrado(
      Map<String, Object> parametrosConsulta) {

    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(TareaBusqueda.class);
      String criterio = (String) parametrosConsulta.get("criterio");

      if (((String) parametrosConsulta.get("orden")).compareTo("ascending") == 0) {
        criteria.addOrder(Order.asc(criterio));
      } else {
        criteria.addOrder(Order.desc(criterio));
      }
      if (!criterio.equals("fechaAlta")) {
        criteria.addOrder(Order.desc("fechaAlta"));
      }
      if ((String) parametrosConsulta.get("usuarioDestino") != null) {
        criteria.add(
            Restrictions.eq("usuarioDestino", (String) parametrosConsulta.get("usuarioDestino")));
      }
      if ((String) parametrosConsulta.get("tipoTarea") != null) {
        criteria.add(Restrictions.eq("tipoTarea", (String) parametrosConsulta.get("tipoTarea")));
      }
      if ((String) parametrosConsulta.get("tipoDocumento") != null) {
        criteria.add(
            Restrictions.eq("tipoDocumento", (String) parametrosConsulta.get("tipoDocumento")));
      }
      criteria.add(Restrictions.eq("usuario", (String) parametrosConsulta.get("usuario")));
      criteria.add(Restrictions.eq("esComunicable", parametrosConsulta.get("esComunicable")));
      criteria.setFirstResult((Integer) parametrosConsulta.get("inicioCarga"));
      criteria.setMaxResults((Integer) parametrosConsulta.get("cantidadResultados"));
      criteria.add(Restrictions.between("fechaParticipacion", parametrosConsulta.get("fechaDesde"),
          parametrosConsulta.get("fechaHasta")));
      return (List<TareaBusqueda>) criteria.list();
    } catch (Exception e) {
      logger.error(e);
      throw new IllegalAccessError("Error al buscar las tareas donde se involucra el usuario "
          + parametrosConsulta.get("usuario") + e);
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public List<TareaBusqueda> buscarTareasPorUsuarioInvolucradoSinFiltrar(
      Map<String, Object> parametrosConsulta) {

    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(TareaBusqueda.class);
      String criterio = (String) parametrosConsulta.get("criterio");

      if (((String) parametrosConsulta.get("orden")).compareTo("ascending") == 0) {
        criteria.addOrder(Order.asc(criterio));
      } else {
        criteria.addOrder(Order.desc(criterio));
      }
      if (!criterio.equals("fechaAlta")) {
        criteria.addOrder(Order.desc("fechaAlta"));
      }
      if ((String) parametrosConsulta.get("usuarioDestino") != null) {
        criteria.add(
            Restrictions.eq("usuarioDestino", (String) parametrosConsulta.get("usuarioDestino")));
      }
      if ((String) parametrosConsulta.get("tipoTarea") != null) {
        criteria.add(Restrictions.eq("tipoTarea", (String) parametrosConsulta.get("tipoTarea")));
      }
      if ((String) parametrosConsulta.get("tipoDocumento") != null) {
        criteria.add(
            Restrictions.eq("tipoDocumento", (String) parametrosConsulta.get("tipoDocumento")));
      }
      criteria.add(Restrictions.eq("usuario", (String) parametrosConsulta.get("usuario")));
      criteria.add(Restrictions.eq("esComunicable", parametrosConsulta.get("esComunicable")));
      criteria.add(Restrictions.between("fechaParticipacion", parametrosConsulta.get("fechaDesde"),
          parametrosConsulta.get("fechaHasta")));
      return (List<TareaBusqueda>) criteria.list();
    } catch (Exception e) {
      logger.error(e);
      throw new IllegalAccessError("Error al buscar las tareas donde se involucra el usuario "
          + parametrosConsulta.get("usuario") + e);
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public Integer cantidadTotalTareasPorUsuarioInvolucrado(Map<String, Object> parametrosConsulta) {

    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(TareaBusqueda.class);
      if ((String) parametrosConsulta.get("usuarioDestino") != null) {
        criteria.add(
            Restrictions.eq("usuarioDestino", (String) parametrosConsulta.get("usuarioDestino")));
      }
      if ((String) parametrosConsulta.get("tipoTarea") != null) {
        criteria.add(Restrictions.eq("tipoTarea", (String) parametrosConsulta.get("tipoTarea")));
      }
      if ((String) parametrosConsulta.get("tipoDocumento") != null) {
        criteria.add(
            Restrictions.eq("tipoDocumento", (String) parametrosConsulta.get("tipoDocumento")));
      }
      criteria.add(Restrictions.eq("usuario", parametrosConsulta.get("usuario")));
      criteria.add(Restrictions.eq("esComunicable", parametrosConsulta.get("esComunicable")));
      criteria.add(Restrictions.between("fechaParticipacion", parametrosConsulta.get("fechaDesde"),
          parametrosConsulta.get("fechaHasta")));
      criteria.setProjection(Projections.rowCount());
      return (Integer) criteria.list().get(0);
    } catch (Exception e) {
      logger.error(e);
      throw new IllegalAccessError(
          "Error al buscar la cantidad de tareas donde se involucra el usuario "
              + parametrosConsulta.get("usuario") + e);
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public long countTasksPorUsuarioYCaractDeTipoDoc(String usuario, String caractTipoDoc) {

    QueryTaskGedo tq = new QueryTaskGedo();
    tq.assignee(usuario);
    tq.caract(caractTipoDoc);
    return tq.count();
  }

  class QueryTaskGedo extends AbstractQuery {

    protected String assignee;
    protected String caract;

    public String hql() {
      StringBuilder hql = new StringBuilder();
      hql.append("select ");
      if (count) {
        hql.append("count(task)");
      } else {
        hql.append("task");
      }

      hql.append(" from ");
      hql.append(TaskImpl.class.getName());

      if (caract != null) {
        hql.append(" as task, TipoDocumento tipoDocumento ");
        appendWhereClause(
            " tipoDocumento.id = cast(task.execution.variables['tipoDocumento'].string as integer)",
            hql);
        appendWhereClause(" tipoDocumento." + caract + "= true", hql);

      }

      appendWhereClause("task.assignee = :assignee ", hql);

      appendOrderByClause(hql);
      return hql.toString();
    }

    public List<Task> list() {
      return CollectionUtil.checkList(untypedList(), Task.class);
    }

    public long count() {
      count = true;

      // Page and count cannot be used together, because paging is applied
      // after the query is formed
      if (page != null) {
        throw new JbpmException(
            "page(firstResult, maxResult) and count() cannot be used together");
      }

      return (Long) untypedUniqueResult();
    }

    // @SuppressWarnings("deprecation")
    // public List<?> untypedList() {
    // return (List<?>)
    // execute(getHibernateTemplate().getSessionFactory().getCurrentSession());
    // }
    //
    // @SuppressWarnings("deprecation")
    // protected Object untypedUniqueResult() {
    // uniqueResult = true;
    // return execute(getSession());
    // }

    public QueryTaskGedo orderAsc(String property) {
      orderByClause = "task." + property + " asc ";
      return this;
    }

    public QueryTaskGedo orderDesc(String property) {
      orderByClause = "task." + property + " desc ";
      return this;
    }

    public QueryTaskGedo assignee(String assignee) {
      this.assignee = assignee;
      return this;
    }

    public QueryTaskGedo caract(String caract) {
      this.caract = caract;
      return this;
    }

    // @Override
    // protected void applyParameters(Query query) {
    // if (assignee != null) {
    // query.setString("assignee", assignee);
    // }
    // }

    public QueryTaskGedo page(int firstResult, int maxResults) {
      page = new Page(firstResult, maxResults);
      return this;
    }

    @Override
    protected Object execute(QuerySession arg0) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }
  }

  @SuppressWarnings({ "deprecation", "unchecked" })
  public List<TareaJBPM> buscarTareasDelUsuarioPorActividad(String usuarioBaja, String actividad) {
    Session session = null;
    try {
      session = getSession();
      Query hqlQuery = session.createQuery(
          "from TareaJBPM where assignee_ = :usuarioBaja and activity_name_= :actividad ");
      hqlQuery.setString("usuarioBaja", usuarioBaja);
      hqlQuery.setString("actividad", actividad);
      return (List<TareaJBPM>) hqlQuery.list();
    } catch (Exception e) {
      logger.error(e);
      throw new IllegalAccessError(
          "Ha ocurrido un error al buscar las tareas del usuario: " + usuarioBaja + e);
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public List<TareaJBPM> buscarTareasDelUsuarioPorActividadRevisionRechazo(String usuarioBaja) {
    Session session = null;
    try {
      session = getSession();
      Query hqlQuery = session.createQuery(
          "from TareaJBPM where assignee_ = :usuarioBaja and activity_name_= :actividad "
              + " or assignee_ = :usuarioBaja and activity_name_= :actividadDos ");
      hqlQuery.setString("usuarioBaja", usuarioBaja);
      hqlQuery.setString("actividad", Constantes.TASK_REVISAR_DOCUMENTO);
      hqlQuery.setString("actividadDos", Constantes.TASK_RECHAZADO);
      return (List<TareaJBPM>) hqlQuery.list();
    } catch (Exception e) {
      logger.error(e);
      throw new IllegalAccessError(
          "Ha ocurrido un error al buscar las tareas del usuario: " + usuarioBaja + e);
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public List<TareaJBPM> buscarTareasPorUsuarioFirmante(String usuario)
      throws EjecucionSiglaException {
    Session session = null;
    try {
      session = getSession();
      Query query = session.createQuery("from TareaJBPM tarea "
          + "where tarea.executionID in (select distinct(firmante.workflowId) "
          + "from Firmante firmante " + "where firmante.usuarioFirmante = :usuarioBaja)");
      query.setParameter("usuarioBaja", usuario);
      return (List<TareaJBPM>) query.list();
    } catch (ApplicationException e) {
      logger.error(e);
      throw e;
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

  public List<TareaJBPM> buscarTareasPorUsuarioRevisor(String usuario, String actividad)
      throws EjecucionSiglaException {
    Session session = null;
    try {
      session = getSession();
      Query query = session.createQuery(
          "from TareaJBPM tarea " + "where assignee_ = :usuarioBaja and activity_name_= :actividad"
              + " and tarea.executionID in (select firmante.workflowId "
              + "from Firmante firmante " + "where firmante.usuarioRevisor = :usuarioBaja)");
      query.setParameter("usuarioBaja", usuario);
      query.setString("actividad", actividad);
      return (List<TareaJBPM>) query.list();
    } catch (ApplicationException e) {
      logger.error(e);
      throw e;
    } finally {
      if (session != null) {
        this.releaseSession(session);
        logger.debug("Se cerró la sesion con éxito");
      }
    }
  }

}
