package com.egoveris.deo.base.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.egoveris.deo.base.dao.impl.DatosUsuarioDAOHbn;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseConfig.class);

	@Autowired
	@Qualifier("jpaDataSource")
	private DataSource dataSource;

	@Value("classpath:/liquibase/changelog.xml")
	private Resource liquibaseResource;

	@Bean
	public SpringLiquibase liquibase() {

		LOGGER.info("Inicializando liquibase...");
		
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:/liquibase/changelog.xml");
		liquibase.setDataSource(dataSource);
		liquibase.setDropFirst(false);
		liquibase.setShouldRun(true);

		Map<String, String> params = new HashMap<>();
		params.put("verbose", "true");
		liquibase.setChangeLogParameters(params);
		
		LOGGER.info("Liquibase iniciado.");

		return liquibase;

	}
}
