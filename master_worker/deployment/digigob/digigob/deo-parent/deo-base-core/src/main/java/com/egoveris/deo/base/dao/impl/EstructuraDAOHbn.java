package com.egoveris.deo.base.dao.impl;

import com.egoveris.deo.base.dao.IEstructuraDAO;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.EstructuraBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class EstructuraDAOHbn implements IEstructuraDAO {

	@Autowired
	@Qualifier("dataSourceEdt")
	private DataSource datasource;
	private static final Logger logger = LoggerFactory.getLogger(EstructuraDAOHbn.class);
	private static final String ERROR_CERRRAR = "Error al cerrar connection al obtener datos de las estructuras";

	@Override
	public List<EstructuraBean> buscarEstructurasVigentesActivas() {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<EstructuraBean> listaEstructuras = new ArrayList<>();
		String query = "SELECT nombre_estructura, id_estructura " + " FROM edt_sade_estructura "
				+ " WHERE estado_registro = 1 " + " AND vigencia_desde <= sysdate() "
				+ " AND vigencia_hasta >= sysdate() " + " ORDER BY id_estructura";

		try {
			connection = datasource.getConnection();
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			EstructuraBean aux = null;
			while (rs.next()) {
				aux = new EstructuraBean();
				aux.setId(rs.getInt("id_estructura"));
				aux.setNombre(rs.getString("nombre_estructura"));
				listaEstructuras.add(aux);
			}
			return listaEstructuras;
		} catch (SQLException e) {
			logger.error("Error al obtener los datos de las estructuras", e.getMessage());
			throw new DataAccessLayerException("Error al obtener los datos de las estructuras", e);
		} catch (Exception e) {
			logger.error("Error desconocido al obtener datos", e.getMessage());
			throw new DataAccessLayerException("Error desconocido al obtener datos", e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqle) {
					logger.error(ERROR_CERRRAR + sqle.getMessage(), sqle);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					logger.error(ERROR_CERRRAR + sqle.getMessage(), sqle);
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					logger.error(ERROR_CERRRAR + sqle.getMessage(), sqle);
				}
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