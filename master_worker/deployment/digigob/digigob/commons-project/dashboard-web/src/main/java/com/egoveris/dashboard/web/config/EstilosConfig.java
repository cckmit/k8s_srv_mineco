package com.egoveris.dashboard.web.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

public class EstilosConfig {

	private static final Logger logger = LoggerFactory.getLogger(EstilosConfig.class);
	
	private DataSource datasource;
	
	private String logo;
	private String colorHeader;
	private String colorHeaderBorder;
	
	public EstilosConfig() {
		init();
	}

	private void init() {
		this.datasource = this.dataSource();
		try (
				Connection conn = this.datasource.getConnection();
				CallableStatement stm = conn.prepareCall("SELECT LOGO, COLOR_HEADER, COLOR_HEADER_BORDER FROM EDT_ESTILOS WHERE CODIGO = 'EGOV'");
				ResultSet rs = stm.executeQuery();
			) {
			
			if (rs.next()) {
				this.logo = rs.getString(1);
				this.colorHeader = rs.getString(2);
				this.colorHeaderBorder = rs.getString(3);
			} else {
				logger.info("No se encontraron estilos.");
			}
			
		} catch (Exception e) {
			logger.error("Ocurrió un error al leer los estilos: " + e.getMessage());
		}
	}
	
	public DataSource dataSource() {
		if (logger.isDebugEnabled()) {
			logger.debug("Estilos dataSource() - start"); //$NON-NLS-1$
		}

		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		final DataSource returnDataSource = dsLookup.getDataSource("java:comp/env/jdbc/edtDS");

		if (logger.isDebugEnabled()) {
			logger.debug("Estilos dataSource() - end"); //$NON-NLS-1$
		}

		return returnDataSource;
	}

	public String getLogo() {
		return logo;
	}

	public String getColorHeader() {
		return colorHeader;
	}

	public String getColorHeaderBorder() {
		return colorHeaderBorder;
	}
	

}
