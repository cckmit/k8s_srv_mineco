package com.egoveris.dashboard.web.util;

import com.egoveris.dashboard.web.config.AppProperties;

public class DashboardConstants {
	
	// LOGO
	public static final String PARAM_LOGO = "logoUri";
	public static final String DEFAULT_LOGO = "~./db_images/logoSinFondo.png";
	
	// TEXTO CABECERA
	public static final String PARAM_HEADER_TEXT = "headerText";
	public static final String DEFAULT_HEADER_TEXT = "DigiGob";
	
	// MOSTRAR NOTIFICACIONES
	public static final String PARAM_SHOW_NOTIFICACIONES = "showNotificaciones";
	public static final String DEFAULT_SHOW_NOTIFICACIONES = Boolean.TRUE.toString();
	
	// MOSTRAR SHORTCUTS
	public static final String PARAM_SHOW_SHORTCUTS = "showShortcuts";
	public static final String DEFAULT_SHOW_SHORTCUTS = Boolean.TRUE.toString();
	
	// CAMBIO DE IDIOMA
	public static final String WINDOW_CAMBIO_IDIOMA = "~./db_layout/common/cambiarIdioma.zul";
	public static final String IDIOMA_ES = "es_ES";
	public static final String IDIOMA_EN = "en_US";
	
	// CAMBIO De Sector
		public static final String WINDOW_CAMBIO_SECTOR = "~./db_layout/common/seleccionDeCargo.zul";
		
	// MENU COLLAPSED
	public static final String PARAM_MENU_COLLAPSED = "menuCollapsed";
	public static final String DEFAULT_MENU_COLLAPSED = Boolean.TRUE.toString();
	
	// BEANS
	public static final String BEAN_DBPROPERTY = "dBProperty";
	
	// CONSTANTES QUE VIAJAN EN SESSION
	public static final String SESSION_USERNAME = "userName";
	public static final String SESSION_USER_SECTOR = "sector";
	public static final String SESSION_USER_NOMBRE_APELLIDO = "usernameNyA";
	public static final String SESSION_USER_REPARTICION = "usernameReparticion";
	public static final String SESSION_FUSIONADOR = "esFusionador";
	public static final String SESSION_USER_CCOO = "esUsuarioEnCCOO";
	public static final String SESSION_ADMIN_CENTRAL = "esAdministrador";
	public static final String SESSION_DESARCHIVADOR = "esDesarchivador";
	public static final String SESSION_PARAMETRO_SISTEMA_EXTERNO = "parametroSistemaExterno";
	public static final String SESSION_PARAMETRO_SISTEMA_AFJG = "parametroSistemaAFJG";
	public static final String SESSION_PERMISO_INTEGRACION_SIS_EXT = "permisoIntegracion";
	
	// CONTROL USUARIO VM
	public static final String PROPERTY_URL_ESCRITORIO = "edt.url";
	public static final String PROPERTY_URL_MIINFORMACION = "edt.url.miInformacion";
	public static final String URL_LOGOUT = "/logout";
	
	public static final String APP_LANG = "app.system.lang";
	
	private DashboardConstants() {
		
	}

	public static String getHostDEO() {
		String host = AppProperties.getInstance().getProperties().getString("host.deo");
		return host != null ? host : "";
	}
	
	public static String getHostEDT() {
		String host = AppProperties.getInstance().getProperties().getString("host.edt");
		return host != null ? host : "";
	}

	public static String getHostFFDD() {
		String host = AppProperties.getInstance().getProperties().getString("host.ffdd");
		return host != null ? host : "";
	}

	public static String getHostTE() {
		String host = AppProperties.getInstance().getProperties().getString("host.te");
		return host != null ? host : "";
	}

	public static String getHostWD() {
		String host = AppProperties.getInstance().getProperties().getString("host.wd");
		return host != null ? host : "";
	}

}
