package com.egoveris.deo.base.dao.impl;

import com.egoveris.deo.base.dao.IDatosUsuarioDAO;
import com.egoveris.deo.model.model.Cargo;
import com.egoveris.sharedsecurity.base.model.Usuario;

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
public class DatosUsuarioDAOHbn extends JdbcDaoSupport implements IDatosUsuarioDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatosUsuarioDAOHbn.class);
	private static final String ERROR_CONN = "Error al cerrar connection al obtener datos de CCOO";
	private static final String ERROR_DAT = "Error al obtener datos de CCOO";

	@Autowired
	@Qualifier("dataSourceEdt")
	private DataSource datSour;

	@PostConstruct
	private void initialize() {
		setDataSource(datSour);
	}

	public List<Usuario> obtenerListaTodosUsuarios() throws SQLException {
		PreparedStatement ps = null;
		PreparedStatement psCargo = null;
		ResultSet rs = null;
		Connection connection = null;
		List<Usuario> listaUsuarios = new ArrayList<>();
		try {
			connection = datSour.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_datos_usuario");
			rs = ps.executeQuery();
			Usuario us = null;
			psCargo = connection.prepareStatement("SELECT * FROM edt_cargos");
			List<Cargo> cargos = new ArrayList<>();
			ResultSet rsCargo = psCargo.executeQuery();
			while (rsCargo.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(rsCargo.getBigDecimal("id").intValue());
				cargo.setCargo(rsCargo.getString("Cargo"));
				cargos.add(cargo);
			}
			while (rs.next()) {
				us = agregarUsuario(rs);
				Integer auxCargo = rs.getBigDecimal("cargo").intValue();

				for (Cargo c : cargos) {
					if (c.getId().equals(auxCargo)) {
						us.setOcupacion(c.toString());
					}
				}
				listaUsuarios.add(us);
			}

		} catch (SQLException sqle) {
			LOGGER.error(ERROR_DAT, sqle.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (psCargo != null) {
				psCargo.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return listaUsuarios;
	}

	public List<Usuario> obtenerUsuariosSupervisados(String username) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			List<Usuario> listaUsuarios = new ArrayList<>();
			connection = datSour.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_datos_usuario where user_superior=?");
			ps.setString(1, username.trim());
			rs = ps.executeQuery();
			Usuario dub = null;
			while (rs.next()) {
				dub = agregarUsuario(rs);
				listaUsuarios.add(dub);
			}
			return listaUsuarios;
		} catch (SQLException sqle) {
			LOGGER.error(ERROR_DAT, sqle.getMessage());
			throw new SQLException(ERROR_DAT, sqle);
		} finally {
			cerrarConexionPsRs(ps, rs, connection);
		}
	}

	public List<Usuario> obtenerUsuariosJefe(String username) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			List<Usuario> listaUsuarios = new ArrayList<>();
			connection = datSour.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_datos_usuario where secretario=?");
			ps.setString(1, username.trim());
			rs = ps.executeQuery();
			Usuario dub = null;
			while (rs.next()) {
				dub = agregarUsuario(rs);
				listaUsuarios.add(dub);
			}
			return listaUsuarios;
		} catch (SQLException sqle) {
			LOGGER.error(ERROR_DAT, sqle.getMessage());
			throw new SQLException(ERROR_DAT, sqle);
		} finally {
			cerrarConexionPsRs(ps, rs, connection);
		}
	}

	private Usuario agregarUsuario(ResultSet rs) throws SQLException {
		Usuario us;
		us = new Usuario();
		us.setNombreApellido(null);
		us.setOcupacion(rs.getString("ocupacion"));
		us.setUsername(rs.getString("usuario"));
		us.setCodigoSectorInterno(rs.getString("codigo_sector_interno"));
		us.setEmail(rs.getString("mail"));
		us.setExternalizarFirmaGEDO(rs.getBoolean("externalizar_firma_en_gedo"));
		if (rs.getString("usuario_asesor") != null && rs.getString("usuario_asesor").trim().equals("")) {
			us.setUsuarioRevisor(null);
		} else {
			us.setUsuarioRevisor(rs.getString("usuario_asesor"));
		}
		return us;
	}

	public Usuario getDatosUsuario(String username) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = datSour.getConnection();
			ps = connection.prepareStatement("SELECT * FROM edt_datos_usuario where datos_usuario.usuario=?");
			if (username == null) {
				throw new IllegalArgumentException("username no puede ser null");
			}
			ps.setString(1, username);
			rs = ps.executeQuery();
			Usuario us = null;
			while (rs.next()) {
				us = agregarUsuario(rs);
				return us;
			}
			return null;
		} catch (SQLException sqle) {
			LOGGER.error(ERROR_DAT, sqle.getMessage());
			throw new SQLException(ERROR_DAT, sqle);
		} finally {
			cerrarConexionPsRs(ps, rs, connection);
		}
	}

	private void cerrarConexionPsRs(PreparedStatement ps, ResultSet rs, Connection connection) throws SQLException {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqle) {
				LOGGER.error(ERROR_CONN, sqle.getMessage());
				throw new SQLException(ERROR_CONN, sqle);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException sqle) {
				LOGGER.error(ERROR_CONN, sqle.getMessage());
				throw new SQLException(ERROR_CONN, sqle);
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				LOGGER.error(ERROR_CONN, sqle.getMessage());
				throw new SQLException(ERROR_CONN, sqle);
			}
		}
	}

	public void setUsuarioPortaFirma(String userName, boolean tienePortafirma) throws SQLException {
		PreparedStatement ps = null;
		Connection connection = null;
		try {
			connection = datSour.getConnection();
			ps = connection.prepareStatement(
					"UPDATE edt_datos_usuario set externalizar_firma_en_gedo=? where edt_datos_usuario.usuario=?");
			if (userName == null) {
				throw new IllegalArgumentException("username no puede ser null");
			}
			ps.setBoolean(1, tienePortafirma);
			ps.setString(2, userName);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			LOGGER.error(ERROR_DAT, sqle.getMessage());
			throw new SQLException(ERROR_DAT, sqle);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public DataSource getDatSour() {
		return datSour;
	}

	public void setDatSour(DataSource datSour) {
		this.datSour = datSour;
	}

}
