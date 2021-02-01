package com.egoveris.deo.base.dao.impl;

import com.egoveris.deo.base.dao.TipoActuacionDAO;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.ActuacionSADEBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class TipoActuacionDAOHbn extends JdbcDaoSupport implements TipoActuacionDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoActuacionDAOHbn.class);
	private static final String ERRCON = "Error al cerrar connection al obtener datos de SADE";
	private static final String ERRDAT = "Error al obtener datos de SADE";

	@Autowired
	@Qualifier("dataSourceEdt")
	private DataSource datasource;

	@PostConstruct
	public void init() {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}

		setDataSource(datasource);

		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
	}

	@Override
	public List<ActuacionSADEBean> obtenerListaTodasLasActuaciones() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaTodasLasActuaciones() - start"); //$NON-NLS-1$
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			List<ActuacionSADEBean> listaActuaciones = new ArrayList<>();
			connection = datasource.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_sade_actuacion order by codigo_actuacion asc");
			rs = ps.executeQuery();
			ActuacionSADEBean dub = null;
			while (rs.next()) {
				dub = new ActuacionSADEBean();
				dub.setDescripcion(rs.getString("nombre_actuacion"));
				dub.setCodigo(rs.getString("codigo_actuacion"));
				dub.setId(rs.getInt("id_actuacion"));
				listaActuaciones.add(dub);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerListaTodasLasActuaciones() - end"); //$NON-NLS-1$
			}
			return listaActuaciones;
		} catch (SQLException sqle) {
			LOGGER.error(ERRDAT, sqle.getMessage());
			throw new DataAccessLayerException(ERRDAT, sqle);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.deo.base.dao.TipoActuacionDAO#obtenerActuacionPorCodigo(java
	 * .lang.String)
	 */
	@Override
	public ActuacionSADEBean obtenerActuacionPorCodigo(String codigo) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerActuacionPorCodigo(String) - start"); //$NON-NLS-1$
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		ActuacionSADEBean dub = null;
		try {
			connection = datasource.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_sade_actuacion where codigo_actuacion= ?");
			ps.setString(1, codigo);
			rs = ps.executeQuery();
			while (rs.next()) {
				dub = new ActuacionSADEBean();
				dub.setDescripcion(rs.getString("nombre_actuacion"));
				dub.setCodigo(rs.getString("codigo_actuacion"));
				dub.setId(rs.getInt("id_actuacion"));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerActuacionPorCodigo(String) - end"); //$NON-NLS-1$
			}
			return dub;
		} catch (SQLException sqle) {
			LOGGER.error(ERRDAT, sqle.getMessage());
			throw new DataAccessLayerException(ERRDAT, sqle);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					LOGGER.error(ERRCON + sqle.getMessage(), sqle);
				}
			}
		}
	}

}
