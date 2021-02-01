package com.egoveris.te.base.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;

@Service
@Transactional
public class DocumentoComunicacionesServiceImpl implements DocumentoComunicacionesService {
  @Autowired
  @Qualifier("dataSource")
  private DataSource datasource;
  private final static Logger logger = LoggerFactory
      .getLogger(DocumentoComunicacionesServiceImpl.class);

  /**
   * Consulta a la base de CCOO por un documento asociado a un determinado
   * numero estandar. Si lo encuentra devuelve sus datos y sino retorna null.
   * 
   */
  public DocumentoDTO buscarDocumento(String numeroSADE) {
	  
	  throw new NotImplementedException();
//    if (logger.isDebugEnabled()) {
//      logger.debug("buscarDocumento(numeroSADE={}) - start", numeroSADE);
//    }
//
//    Connection c = null;
//    ResultSet rs = null;
//    PreparedStatement ps = null;
//    try {
//      c = datasource.getConnection();
//      DocumentoDTO documento = null;
//      ps = c.prepareStatement(OBTENER_DOCUMENTO);
//      ps.setString(1, numeroSADE);
//      rs = ps.executeQuery();
//      while (rs.next()) {
//        documento = new DocumentoDTO();
//        documento.setNumeroSade(numeroSADE);
//        documento.setNumeroEspecial(null);
//        documento.setMotivo(rs.getString("referencia"));
//        documento.setNombreUsuarioGenerador(rs.getString("usuarioFrom"));
//        documento.setFechaCreacion(rs.getTimestamp("fecha"));
//
//        if (logger.isDebugEnabled()) {
//          logger.debug("buscarDocumento(String) - end - return value={}", documento);
//        }
//        return documento;
//      }
//
//      if (logger.isDebugEnabled()) {
//        logger.debug("buscarDocumento(String) - end - return value={null}");
//      }
//      return null;
//    } catch (SQLException sqle) {
//      logger.error("Error al obtener datos de GEDO", sqle);
//      throw new TeRuntimeException("Error al obtener datos de GEDO", sqle);
//    } finally {
//      this.cerrarPreparedStatement(ps);
//      this.cerrarConexionABase(c);
//    }
  }

  /**
   * Cierra un PreparedStatement previamente abierto, si la referencia al mismo
   * no es nula.
   * 
   * @param ps
   * @throws SQLException
   *           al producirse una excepcion en el cierre
   */
  private void cerrarPreparedStatement(PreparedStatement ps) {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarPreparedStatement(ps={}) - start", ps);
    }

    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException sqle) {
        logger.error("Error al cerrar connection al obtener datos de SADE", sqle);
        throw new TeRuntimeException("Error al cerrar connection al obtener datos de SADE", sqle);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cerrarPreparedStatement(PreparedStatement) - end");
    }
  }

  /**
   * Cierra una Conexion a la base de datos previamente abierta, si la
   * referencia al mismo no es nula.
   * 
   * @param ps
   * @throws SQLException
   *           al producirse una excepcion en el cierre
   */
  private void cerrarConexionABase(Connection c) {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarConexionABase(c={}) - start", c);
    }

    if (c != null) {
      try {
        c.close();
      } catch (SQLException sqle) {
        logger.error("Error al cerrar connection al obtener datos de SADE", sqle);
        throw new TeRuntimeException("Error al cerrar connection al obtener datos de SADE", sqle);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cerrarConexionABase(Connection) - end");
    }
  }



  private static String OBTENER_DOCUMENTO = "select id_ComunicacionOficialBean, codigoCaratula, referencia, usuarioFrom, fecha from ComunicacionOficialBean where codigoCaratula = ?";

}
