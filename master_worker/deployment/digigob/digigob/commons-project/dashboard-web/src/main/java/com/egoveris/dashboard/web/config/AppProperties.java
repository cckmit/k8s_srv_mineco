package com.egoveris.dashboard.web.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.egoveris.commons.databaseconfiguration.exceptions.DatabaseConfigurationException;
import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.commons.databaseconfiguration.propiedades.impl.DBAppPropertyImpl;

public class AppProperties {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);
	private static AppProperties appConfig;
	private AppProperty properties;

	public AppProperties() {
		init();
	}

	public static AppProperties getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("Instance() - start"); //$NON-NLS-1$
		}

		if (appConfig == null) {
			appConfig = new AppProperties();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Instance() - end"); //$NON-NLS-1$
		}

		return appConfig;
	}

	private void init() {
		try {
			properties = new DBAppPropertyImpl(dataSource(), "PROPERTY_CONFIGURATION", "SISTEMA", null);
		} catch (final DatabaseConfigurationException e) {
			logger.warn("AppProperties() - exception ignored", e); //$NON-NLS-1$
		}
	}

	public DataSource dataSource() {
		if (logger.isDebugEnabled()) {
			logger.debug("dataSource() - start"); //$NON-NLS-1$
		}

		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		final DataSource returnDataSource = dsLookup.getDataSource("java:comp/env/jdbc/edtDS");

		if (logger.isDebugEnabled()) {
			logger.debug("dataSource() - end"); //$NON-NLS-1$
		}

		return returnDataSource;
	}

	public AppProperty getProperties() {
		return properties;
	}

	public void setProperties(final AppProperty properties) {
		this.properties = properties;
	}
}