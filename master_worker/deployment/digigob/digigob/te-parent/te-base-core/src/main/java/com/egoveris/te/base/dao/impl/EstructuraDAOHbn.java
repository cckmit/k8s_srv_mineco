package com.egoveris.te.base.dao.impl;

import com.egoveris.te.base.dao.IEstructuraDAO;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.EstructuraBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Deprecated
@Repository
public class EstructuraDAOHbn  implements IEstructuraDAO{
	
	private DataSource datasource;
	private static final  Logger logger = LoggerFactory.getLogger(EstructuraDAOHbn.class);
	
	private static final  String  MSG_ERROR_BBDD = "Error al cerrar connection al obtener datos de las estructuras";
 
	
	public List<EstructuraBean> buscarEstructurasVigentesActivas() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarEstructurasVigentesActivas() - start");
		}
		
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<EstructuraBean> listaEstructuras = new ArrayList<EstructuraBean>();
		String query = "SELECT nombre_estructura, id_estructura " + " FROM sade_estructura " +
						" WHERE estado_registro = 1 " + " AND vigencia_desde <= sysdate() " + " AND vigencia_hasta >= sysdate() "
						+ " ORDER BY id_estructura";
		
		try {
			connection = datasource.getConnection();
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			EstructuraBean aux = null;
			while(rs.next()){
				aux = new EstructuraBean();
				aux.setId(rs.getInt("id_estructura"));
				aux.setNombre(rs.getString("nombre_estructura"));
				listaEstructuras.add(aux);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("buscarEstructurasVigentesActivas() - end - return value={}", listaEstructuras);
			}
			return listaEstructuras;
		}
		catch(SQLException e){
			logger.error("Error al obtener los datos de las estructuras", e);
			throw new TeRuntimeException("Error al obtener los datos de las estructuras", e);
		}
		catch(Exception e) {
			logger.error("Error desconocido al obtener datos", e);
			throw new TeRuntimeException("Error desconocido al obtener datos", e);
		}
		finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error(MSG_ERROR_BBDD, e);
				}
			
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(MSG_ERROR_BBDD, e);
				}
			
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error(MSG_ERROR_BBDD, e);
				}
			
		}
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	
	
}
