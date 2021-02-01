package com.egoveris.dashboard.web.vm;

import java.util.List;
import java.util.Locale;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.Locales;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.dashboard.web.util.DashboardConstants;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ControlUsuarioVM {
	
	@Wire
	private Hlayout controlUsuarioLayout;
	
	private AppProperty appProperty;
	
	private String usuarioLogeado;
	private List<Listitem> idiomas;
	private Listitem idiomaSel;
		
	/**
	 * Inicializa el panel de control usuario. Carga el usuario logeado
	 * y los idiomas disponibles (en caso de querer cambiarlo)
	 * 
	 * @param view
	 */
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		if (SpringUtil.getBean(DashboardConstants.BEAN_DBPROPERTY) != null) {
			appProperty = (AppProperty) SpringUtil.getBean(DashboardConstants.BEAN_DBPROPERTY);
		}
		
		loadInfoUser();
		
		String lenguaje = appProperty.getString(DashboardConstants.APP_LANG);

		Locale preferedLocale = Locales.getLocale(lenguaje);
		Sessions.getCurrent().setAttribute(Attributes.PREFERRED_LOCALE, preferedLocale);
	}
	
	/**
	 * En el panel de usuario, a traves de las variables de sesion
	 * muestra el siguiente texto: USUARIO - REPARTICION - SECTOR
	 */
	private void loadInfoUser() {
		String user = "";
		String reparticion = null;
		String sector = null;
		
		if (Executions.getCurrent().getDesktop().getSession().hasAttribute(DashboardConstants.SESSION_USERNAME)) {
			user = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(DashboardConstants.SESSION_USERNAME);
		}
		
		if (Executions.getCurrent().getDesktop().getSession().hasAttribute(DashboardConstants.SESSION_USER_REPARTICION)) {
			reparticion = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(DashboardConstants.SESSION_USER_REPARTICION);
		}
		
		if (Executions.getCurrent().getDesktop().getSession().hasAttribute(DashboardConstants.SESSION_USER_SECTOR)) {
			sector = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(DashboardConstants.SESSION_USER_SECTOR);
		}
		
		String infoUser = user + " " + (reparticion == null ? " " : reparticion) + " "
				+ (sector == null ? " " : sector);
		
		setUsuarioLogeado(infoUser);
	}
	
	@Command
	public void irEscritorio() {
		if (appProperty != null) {
			Executions.sendRedirect(DashboardConstants.getHostEDT());
		}
	}
	
	@Command
	public void miInformacion() {
		if (appProperty != null) {
			Executions.sendRedirect(appProperty.getString(DashboardConstants.PROPERTY_URL_MIINFORMACION));
		}
	}
	
	@Command
	public void cambiarCargo() {
		Utilitarios.closePopUps("win_seleccionDeReparticion");
		Window cambiarIdiomaWindow = (Window) Executions.createComponents(DashboardConstants.WINDOW_CAMBIO_SECTOR,
				controlUsuarioLayout, null);
		cambiarIdiomaWindow.doModal();
	}
	
	
	/**
	 * Comando que abre la ventana de cambio de idioma
	 */
	@Command
	public void openCambiarIdiomaWindow() {
		Utilitarios.closePopUps("cambiarIdiomaWindow");
		Window cambiarIdiomaWindow = (Window) Executions.createComponents(DashboardConstants.WINDOW_CAMBIO_IDIOMA,
				controlUsuarioLayout, null);
		cambiarIdiomaWindow.doModal();
	}
	
	/**
	 * Comando que cambia el idioma de sesion
	 */
	@Command
	public void cambiarIdioma() {
		if (idiomaSel != null) {
			Locale preferedLocale = Locales.getLocale(idiomaSel.getValue().toString());
			Sessions.getCurrent().setAttribute(Attributes.PREFERRED_LOCALE, preferedLocale);
			Executions.sendRedirect(null);
		}
	}
	
	/**
	 * Comando que realiza el logout
	 */
	@Command
	public void logout() {
		Clients.evalJavaScript("egoverisLogout()");
		Clients.evalJavaScript("localStorage.removeItem('egoverisLogged')");
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect(DashboardConstants.URL_LOGOUT);
	}
	
	public String getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(String usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public List<Listitem> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Listitem> idiomas) {
		this.idiomas = idiomas;
	}

	public Listitem getIdiomaSel() {
		return idiomaSel;
	}

	public void setIdiomaSel(Listitem idiomaSel) {
		this.idiomaSel = idiomaSel;
	}
	
}
