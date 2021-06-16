package com.egoveris.deo.base.dao.impl;

import com.egoveris.deo.base.dao.DocumentoTemporalDao;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.RegistroTemporalDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class DocumentoTemporalDAOHbn extends JdbcDaoSupport implements DocumentoTemporalDao {
  private final static Logger logger = LoggerFactory.getLogger(DocumentoTemporalDAOHbn.class);

  @Autowired
  @Qualifier("jpaDataSource")
  private DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  private final static String queryRegistrosABorrarPF = "select * from messagerequest where FECHA_RESPUESTA is null and estado = 'ENVIADO' and execution_id in ("
      + "select id_ from jbpm4_execution where activityname_ = 'Enviar a Portafirma' and id_ in("
      + "select workfloworigen from gedo_historial where workfloworigen in ("
      + "select workfloworigen from gedo_historial where fecha_fin is null ) "
      + "having max(fecha_inicio) < to_date(?,'dd/mm/yyyy')" + "group by workfloworigen))";

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.base.dao.DocumentoTemporalDao#obtenerRegistrosABorrar(java
   * .util.Date)
   */
  @Override
  public List<RegistroTemporalDTO> obtenerRegistrosABorrar(Date fechaLimiteDocumentosTemporales) {
    Connection connection = null;
    try {
      List<RegistroTemporalDTO> listaDocumentoTemporal = new ArrayList<>();

      connection = getDataSource().getConnection();
      connection.setAutoCommit(false);

      Object[] parametros = formatearFecha(fechaLimiteDocumentosTemporales);

      List<Map<String, Object>> results = getJdbcTemplate().queryForList(
          "SELECT E.DBID_, E.ID_, E.INSTANCE_, E.ACTIVITYNAME_, "
              + "T.CREATE_, T.ASSIGNEE_ , (select max(string_value_) from jbpm4_variable "
              + "where execution_ = e.dbid_ AND KEY_ = 'motivo') motivo FROM JBPM4_EXECUTION E,"
              + " JBPM4_TASK T WHERE E.ID_ = T.EXECUTION_ID_ AND T.CREATE_ < to_date(?,'DD/MM/YYYY')",
          parametros);

      addRegTemporal(listaDocumentoTemporal, results);

      return listaDocumentoTemporal;

    } catch (SQLException sqle) {
      logger.error("Error al obtener los documentos temporales a borrar de GEDO - ", sqle);
      throw new ApplicationContextException(
          "Error al obtener los documentos temporales a borrar de GEDO", sqle);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          logger.error("Error al cerrar la conexion", e);
        }
      }
    }
  }

  private void addRegTemporal(List<RegistroTemporalDTO> listaDocumentoTemporal,
      List<Map<String, Object>> results) {
    RegistroTemporalDTO documentoTemporal;
    for (Map<String, Object> result : results) {
      documentoTemporal = new RegistroTemporalDTO();
      documentoTemporal.setDbid(new Integer(result.get("dbid_").toString()));
      documentoTemporal.setWorkflowid((String) result.get("id_"));
      documentoTemporal.setInstance(new Integer(result.get("instance_").toString()));
      documentoTemporal.setActivityName((String) result.get("activityname_"));
      documentoTemporal.setFechaCreacionTask((Date) result.get("create_"));
      documentoTemporal.setAssignee((String) result.get("assignee_"));
      documentoTemporal.setMotivo((String) result.get("string_value_"));
      listaDocumentoTemporal.add(documentoTemporal);
    }
  }

  private Object[] formatearFecha(Date fechaLimiteDocumentosTemporales) {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    Object[] parametros = new Object[1];
    parametros[0] = format.format(fechaLimiteDocumentosTemporales);
    return parametros;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.base.dao.DocumentoTemporalDao#borrarDocumentosTemporales(
   * java.lang.String)
   */
  @Override
  public void borrarDocumentosTemporales(String workflowId) {
    try {
      Object[] parametros = new Object[1];
      parametros[0] = workflowId;

      getJdbcTemplate().update("DELETE FROM gedo_historial where workflowOrigen = ?", workflowId);

      getJdbcTemplate().update("DELETE FROM gedo_archivo_de_trabajo where idTask = ?", parametros);

      getJdbcTemplate().update("DELETE FROM gedo_firmantes where workflowId = ?", parametros);

      getJdbcTemplate().update("DELETE FROM gedo_comentarios where workflowOrigen = ?", parametros);

      getJdbcTemplate().update("DELETE FROM gedo_documento_adjunto where idtask = ?", parametros);
    } catch (Exception e) {
      logger.error("Error al borrar los registros: " + workflowId + " de las tablas de JBPM4 - ",
          e);
      throw e;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.base.dao.DocumentoTemporalDao#
   * messageRequestARegistroTemporal(java.util.Date)
   */
  @Override
  public List<RegistroTemporalDTO> messageRequestARegistroTemporal(
      Date fechaLimiteDocumentosTemporales) {
    Object[] parametros = this.formatearFecha(fechaLimiteDocumentosTemporales);
    Connection connection = null;
    try {
      connection = getDataSource().getConnection();
      connection.setAutoCommit(false);
      List<Map<String, Object>> results = getJdbcTemplate()
          .queryForList("select e.dbid_, e.id_ , e.instance_, e.activityname_ "
              + "from jbpm4_execution e where id_ in("
              + "select execution_id from messagerequest where FECHA_RESPUESTA is null and estado = 'ENVIADO' and execution_id in ("
              + "select id_ from jbpm4_execution where activityname_ = 'Enviar a Portafirma' and id_ in("
              + "select workfloworigen from gedo_historial where workfloworigen in ("
              + "select workfloworigen from gedo_historial where fecha_fin is null ) "
              + "having max(fecha_inicio) < to_date(?,'dd/mm/yyyy')"
              + "group by workfloworigen)))", parametros);
      List<RegistroTemporalDTO> listaDocumentosTemporales = new ArrayList<RegistroTemporalDTO>();
      for (Map<String, Object> result : results) {
        RegistroTemporalDTO documentoTemporal = new RegistroTemporalDTO();
        documentoTemporal.setDbid(new Integer(result.get("dbid_").toString()));
        documentoTemporal.setWorkflowid((String) result.get("id_"));
        documentoTemporal.setInstance(new Integer(result.get("instance_").toString()));
        documentoTemporal.setActivityName((String) result.get("activityname_"));
        listaDocumentosTemporales.add(documentoTemporal);
      }
      return listaDocumentosTemporales;
    } catch (SQLException sqle) {
      logger.error("Error al obtener los registros a borrar de PF - ", sqle);
      throw new DataAccessLayerException("Error al obtener los documentos temporales a borrar de GEDO",
          sqle);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          logger.error("Error al cerrar la conexion", e);
        }
      }
    }
  }

  private void addRegTemporalPF(List<RegistroTemporalDTO> listaDocumentoTemporal,
      List<Map<String, Object>> results) {
    RegistroTemporalDTO documentoTemporal;
    for (Map<String, Object> result : results) {
      documentoTemporal = new RegistroTemporalDTO();
      documentoTemporal.setDbid(new Integer(result.get("dbid_").toString()));
      documentoTemporal.setWorkflowid((String) result.get("id_"));
      documentoTemporal.setInstance(new Integer(result.get("instance_").toString()));
      documentoTemporal.setActivityName((String) result.get("activityname_"));
      documentoTemporal.setFechaCreacionTask((Date) result.get("FECHA_CREACION"));
      documentoTemporal.setAssignee((String) result.get("string_value_"));
      listaDocumentoTemporal.add(documentoTemporal);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.base.dao.DocumentoTemporalDao#obtenerRegistrosTemporalesPF
   * (java.util.Date)
   */
  @Override
  public List<RegistroTemporalDTO> obtenerRegistrosTemporalesPF(
      Date fechaLimiteDocumentosTemporales) {
    Connection connection = null;
    try {
      connection = getDataSource().getConnection();
      connection.setAutoCommit(false);
      Object[] parametros2 = new Object[2];
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      parametros2[0] = format.format(fechaLimiteDocumentosTemporales);
      parametros2[1] = format.format(fechaLimiteDocumentosTemporales);
      // query obtengo PF
      List<Map<String, Object>> resultsPF = getJdbcTemplate().queryForList(
          "SELECT * FROM JBPM4_EXECUTION E, MESSAGEREQUEST M, JBPM4_VARIABLE V WHERE M.EXECUTION_ID = E.ID_ AND V.EXECUTION_ = E.DBID_ AND V.KEY_='usuarioFirmante' AND E.ACTIVITYNAME_ ='Enviar a Portafirma' AND M.FECHA_CREACION < TO_DATE(?,'DD/MM/YYYY') AND E.ID_ IN (SELECT WORKFLOWORIGEN FROM GEDO_HISTORIAL WHERE WORKFLOWORIGEN IN (SELECT WORKFLOWORIGEN FROM GEDO_HISTORIAL WHERE FECHA_FIN IS NULL ) HAVING MAX(FECHA_INICIO) < TO_DATE(?,'dd/mm/yyyy')GROUP BY WORKFLOWORIGEN) ",
          parametros2);
      List<RegistroTemporalDTO> listaDocumentoTemporalPF = new ArrayList<>();

      addRegTemporalPF(listaDocumentoTemporalPF, resultsPF);

      return listaDocumentoTemporalPF;
    } catch (SQLException sqle) {
      logger.error("Error al obtener los registros a borrar de PF - " + sqle.getMessage());
      throw new DataAccessLayerException("Error al obtener los documentos temporales a borrar de GEDO",
          sqle);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          logger.error("Error al cerrar la conexion", e);
        }
      }
    }
  }
  
  public boolean existeJBPMVariable(String value){    
    List<Map<String,Object>> keys = getJdbcTemplate().queryForList("select key_ from JBPM4_VARIABLE where string_value_ = ?", value);
    boolean result = false;
    if(keys != null){
      result = !keys.isEmpty();
    }
    return result;
  }

}
