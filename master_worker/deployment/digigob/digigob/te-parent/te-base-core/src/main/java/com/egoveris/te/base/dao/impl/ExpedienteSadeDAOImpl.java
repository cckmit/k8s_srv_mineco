package com.egoveris.te.base.dao.impl;

import com.egoveris.te.base.dao.ExpedienteSadeDAO;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.repository.IReparticionesRepository;
import com.egoveris.te.base.service.ExpedienteSadeServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Deprecated
@Repository
public class ExpedienteSadeDAOImpl implements ExpedienteSadeDAO {
  
  @Autowired
  private IReparticionesRepository reparticionesRepo;
  
  private DozerBeanMapper mapper = new DozerBeanMapper();
  
  private static final Logger logger = LoggerFactory
    .getLogger(ExpedienteSadeServiceImpl.class);
  private DataSource datasource;
  private Connection c = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  private static String OBTENER_ID_ACTUACION ="SELECT id_actuacion,codigo_actuacion FROM sade_actuacion WHERE codigo_actuacion LIKE ?";
  private static String OBTENER_EXPEDIENTE = "SELECT id_actuacion,anio_actuacion,numero_actuacion,id_reparticion_actuacion,id_codigo_tramite,id_reparticion_usuario FROM sade_expediente WHERE id_actuacion = ? AND anio_actuacion = ? AND numero_actuacion = ?";
  private static String OBTENER_EXPEDIENTE_CON_REP_USUARIO = "SELECT id_actuacion,anio_actuacion,numero_actuacion,id_reparticion_actuacion,id_codigo_tramite,id_reparticion_usuario FROM sade_expediente WHERE id_actuacion = ? AND anio_actuacion = ? AND numero_actuacion = ? AND id_reparticion_usuario = ?";
  private static String OBTENER_REPARTICION = "SELECT codigo_reparticion FROM sade_reparticion WHERE id_reparticion = ?";
  private static String OBTENER_TRATA = "SELECT codigo_extracto FROM sade_extracto WHERE id_extracto = ?";
  private static String OBTENER_ID_REPARTICION = "SELECT id_reparticion FROM sade_reparticion WHERE codigo_reparticion = ?";
  private int idActuacion = 0;
  private int idReparticion = 0;
  private Integer id_reparticion = null;
  private Integer id_trata = null;
  
  public DataSource getDatasource() {
   return datasource;
  }

  public void setDatasource(DataSource datasource) {
   this.datasource = datasource;
  }
  
  
  public ExpedienteAsociadoEntDTO obtenerExpedienteSade(String tipoDocumento,
      Integer anio, Integer numero, String reparticionUsuario){
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteSade(tipoDocumento={}, anio={}, numero={}, reparticionUsuario={}) - start", tipoDocumento, anio, numero, reparticionUsuario);
     }
      
     try{
      ExpedienteAsociadoEntDTO expedienteAsociado = new ExpedienteAsociadoEntDTO();
      //Obtengo el id del tipo de expediente mediante el nombre del mismo.
      c = datasource.getConnection();
      ps = c.prepareStatement(OBTENER_ID_ACTUACION);
      ps.setString(1, tipoDocumento);
      
      rs = ps.executeQuery();
      
      while(rs.next()){
       idActuacion = rs.getInt("id_actuacion");
      }
      this.cerrarResultSet(rs);
      this.cerrarPreparedStatement(ps); 
         
      //Obtengo el id de la reparticion mediante el nombre de la misma.
      c = datasource.getConnection();
      ps=c.prepareStatement(OBTENER_ID_REPARTICION);
      ps.setString(1, reparticionUsuario);
      
      rs = ps.executeQuery();
      
      while(rs.next()){
       idReparticion = rs.getInt("id_reparticion");
      }
      this.cerrarResultSet(rs);
      this.cerrarPreparedStatement(ps);
      
      //Obtengo el expediente con el id_actuacion, id_reparticion_usuario, anio_actuacion y numero_actuacion
      if(idActuacion != 0 && idReparticion != 0){
          ps=c.prepareStatement(OBTENER_EXPEDIENTE_CON_REP_USUARIO);
       
       ps.setInt(1,idActuacion);
       ps.setInt(2,anio);
       ps.setInt(3,numero);
       ps.setInt(4,idReparticion);
       
       rs=ps.executeQuery();
       
       while(rs.next()){
        expedienteAsociado.setTipoDocumento(tipoDocumento);
        expedienteAsociado.setAnio(rs.getInt("anio_actuacion"));
        expedienteAsociado.setNumero(rs.getInt("numero_actuacion"));
        expedienteAsociado.setEsElectronico(false);
        expedienteAsociado.setSecuencia("   ");
        expedienteAsociado.setCodigoReparticionUsuario(reparticionUsuario);
        id_reparticion = rs.getInt("id_reparticion_actuacion");
       }
       
       this.cerrarResultSet(rs);
       this.cerrarPreparedStatement(ps); 
       
       //Obtengo el nombre de la reparticion mediante el id de la misma.
       if(id_reparticion != null && expedienteAsociado.getAnio() != null){
        ps = c.prepareStatement(OBTENER_REPARTICION);
        ps.setInt(1, id_reparticion);
        
        rs=ps.executeQuery();
        
        while(rs.next()){
         expedienteAsociado.setCodigoReparticionActuacion(rs.getString("codigo_reparticion").trim());
        }
       } else {
        //Retorna null si no se encontro ningun expediente con los datos ingresados.

        if (logger.isDebugEnabled()) {
         logger.debug("obtenerExpedienteSade(String, Integer, Integer, String) - end - return value=null");
        }
        return null;
       }
       //Retorna el expediente que coincide con los datos ingresados.

       if (logger.isDebugEnabled()) {
        logger.debug("obtenerExpedienteSade(String, Integer, Integer, String) - end - return value={}", expedienteAsociado);
       }
       return expedienteAsociado;
          
         } else {
          //Retorna null si no se pudo obtener el id_actuacion o el id_reparticion.

       if (logger.isDebugEnabled()) {
        logger.debug("obtenerExpedienteSade(String, Integer, Integer, String) - end - return value={null}");
       }
          return null;
         }
     }
     catch(SQLException sqle){
      logger.error("Error al comunicarse con sistemas externos: " + sqle);   
      throw new TeRuntimeException("Error al comunicarse con sistemas externos.", sqle);
     }
     finally{
      this.cerrarResultSet(rs);
      this.cerrarPreparedStatement(ps); 
      this.cerrarConnection(c);
     }
    
    
    
  }
  
  
  private void cerrarResultSet(ResultSet rs){
    if (logger.isDebugEnabled()) {
     logger.debug("cerrarResultSet(rs={}) - start", rs);
    }

    if(rs!=null){
     try{
      rs.close();
     }
     catch(SQLException sqle){
      
      logger.error("Error al cerrar ResultSet a SADE",sqle);
      throw new TeRuntimeException("Error al cerrar ResultSet a SADE",sqle);
     }
    }

    if (logger.isDebugEnabled()) {
     logger.debug("cerrarResultSet(ResultSet) - end");
    }
   }
   
   private void cerrarPreparedStatement(PreparedStatement ps){
    if (logger.isDebugEnabled()) {
     logger.debug("cerrarPreparedStatement(ps={}) - start", ps);
    }

    if(ps!=null){
     try{
      ps.close();
     }
     catch(SQLException sqle){
      logger.error("Error al cerrar PreparedStatement a SADE",sqle);
      throw new TeRuntimeException("Error al cerrar PreparedStatement a SADE",sqle);
      
     }
     
    }

    if (logger.isDebugEnabled()) {
     logger.debug("cerrarPreparedStatement(PreparedStatement) - end");
    }
   }
   
   public void cerrarConnection(Connection c){
    if (logger.isDebugEnabled()) {
     logger.debug("cerrarConnection(c={}) - start", c);
    }
    
    if(c!=null){
     try{
      c.close();
     }
     catch(SQLException sqle){
      logger.error("Error al cerrar Connection a SADE",sqle);
      throw new TeRuntimeException("Error al cerrar Connection a SADE",sqle);
      
      
     }
     
    }
    
    if (logger.isDebugEnabled()) {
     logger.debug("cerrarConnection(Connection) - end");
    }
   }
   
   public String obtenerCodigoTrataSADE(ExpedienteAsociadoEntDTO expedienteAsociado) {
     if (logger.isDebugEnabled()) {
      logger.debug("obtenerCodigoTrataSADE(expedienteAsociado={}) - start", expedienteAsociado);
     }

     String codigoTrataSADE = null;
      
     try {
      c = datasource.getConnection();
      
      ps=c.prepareStatement(OBTENER_ID_ACTUACION);
      ps.setString(1, expedienteAsociado.getTipoDocumento());
      rs = ps.executeQuery();
      while(rs.next()){
       idActuacion = rs.getInt("id_actuacion");
      }
         cerrarPreparedStatement(ps);
      
      
         if(idActuacion!=0){
          ps=c.prepareStatement(OBTENER_EXPEDIENTE);
       ps.setInt(1,idActuacion);
       ps.setInt(2,expedienteAsociado.getAnio());
       ps.setInt(3,expedienteAsociado.getNumero());
       rs=ps.executeQuery();
       while(rs.next()){ 
        id_trata = rs.getInt("id_codigo_tramite");
       }
       cerrarPreparedStatement(ps);
         }
      
      ps=c.prepareStatement(OBTENER_TRATA);
      ps.setInt(1, id_trata);
      rs=ps.executeQuery();
      while(rs.next()){
       codigoTrataSADE = rs.getString("codigo_extracto");
      }
      
     } catch(SQLException sqle){
      logger.error("Se produjo un error en la base de datos: " + sqle);   
      throw new TeRuntimeException("Se produjo un error en la base de datos", sqle);
     }
     finally{
      this.cerrarResultSet(rs);
      this.cerrarPreparedStatement(ps); 
      this.cerrarConnection(c);
     }

     if (logger.isDebugEnabled()) {
      logger.debug("obtenerCodigoTrataSADE(ExpedienteAsociado) - end - return value={}", codigoTrataSADE);
     }
     return codigoTrataSADE;
    }
  
  

}
