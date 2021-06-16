package com.egoveris.te.base.dao.impl;

import com.egoveris.te.base.dao.IBuzonDAO;
import com.egoveris.te.base.model.BuzonBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class BuzonDAOHbn extends HibernateDaoSupport  implements IBuzonDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuzonDAOHbn.class);
	
	@Autowired
	private SessionFactory session;

	@PostConstruct
	public void init() {
		setSessionFactory(session);
	}

	/**
	 * Obtiene la lista de todas los tramites de SADE definidas, las mismas
	 * deberan ser transformadas a un Tipo de trata de Expedientes Electronicos.
	 */
	public List<BuzonBean> obtenerListaTaskSinAsignacion() {
	
		try {
			
			this.getHibernateTemplate().setCacheQueries(true);

	        
	       
			
			StringBuilder strBuffer = new StringBuilder();		
			
			strBuffer.append("SELECT p.GROUPID_ groupId, ");
			strBuffer.append("  t.EXECUTION_ID_ workflowId, ");
			strBuffer.append("			  e.ID expedienteId, ");
			strBuffer.append("			  e.fecha_modificacion fechaModificacion, ");
			strBuffer.append("			  t.activity_name_ activity ");
			strBuffer.append("			FROM EE_EXPEDIENTE_ELECTRONICO e, ");
			strBuffer.append("			  JBPM4_TASK t, ");
			strBuffer.append("			  JBPM4_PARTICIPATION p ");
			strBuffer.append("			WHERE e.ID_WORKFLOW = t.EXECUTION_ID_ ");
			strBuffer.append("			AND t.DBID_         = p.TASK_ ");
			strBuffer.append("			AND p.GROUPID_ NOT LIKE '%.bloqueado%' ");
			strBuffer.append("			AND p.GROUPID_ NOT LIKE '%.conjunta%' ");
			strBuffer.append("			AND t.ASSIGNEE_ IS NULL ");
			strBuffer.append("			AND e.id in (1454879,1454875,1454845,1454835,1454815) ");
			
			SQLQuery q = ((SharedSessionContract) session).createSQLQuery(strBuffer.toString());
			
			List list = q.list();
			
			List<BuzonBean> result2 = new ArrayList();
			
			if (list!=null && !list.isEmpty()) {
		        for (int i = 0; i < list.size(); i++) {
		        	BuzonBean buz = new BuzonBean();
		        	buz.setGrupoId((String)((Object[]) list.get(i))[0]);
		        	
		        	buz.setWorkflowId((String)((Object[]) list.get(i))[1]);
		        	
		        	buz.setExpedienteId((BigDecimal)((Object[]) list.get(i))[2]);
		        	
		        	buz.setFechaModificacion((Timestamp)((Object[]) list.get(i))[3]);
		        	
		        	buz.setNombreActividad((String)((Object[]) list.get(i))[4]);
		        	
		        	String grupo = buz.getGrupoId();
		        	
					String[] repartionSector = grupo.split("-");
					if(repartionSector[0]!=null){
						buz.setReparticionId(repartionSector[0]);
					}
					
					if(repartionSector.length>1 && repartionSector[1]!=null){
						buz.setSectorId(repartionSector[1]);
					}
		        	
		        	result2.add(buz);
		        }
			}
			return result2;
		} finally {
			session.close();
		}
	}

}
