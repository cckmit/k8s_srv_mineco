package com.egoveris.te.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VersionInfo {
	
  private final static Logger logger = LoggerFactory.getLogger(VersionInfo.class);
  private static final VersionInfo INSTANCE = new VersionInfo();

	private String build = null;
	private String version = null;
	private String fullVersion = null;


	private VersionInfo() {
		this.version = "N/A";
		this.build = "N/A";
		Properties prop = new Properties();
		InputStream is = VersionInfo.class.getResourceAsStream("/build.properties");
		if (is != null) {
			try {
				prop.load(is);
				this.build = prop.getProperty("build.number");
				this.version = prop.getProperty("version");
				this.fullVersion = "- version "+this.version+" - build." + this.build +" -";
				logger.info(this.fullVersion);
			} catch (IOException e) {
				logger.error("Error al obtener las propiedades de build.properties",e);
			}
		} else {
			logger.error("Error grave: No es posible leer archivo build.properties!");
		}
	}
	
	public static VersionInfo getInstance() {
		return INSTANCE;
	}
	
	public String getVersion() {
		return version;
	}

	public String getBuild() {
		return build;
	}
	
	public String getFullVersion() {
		return fullVersion;
	}	

}
