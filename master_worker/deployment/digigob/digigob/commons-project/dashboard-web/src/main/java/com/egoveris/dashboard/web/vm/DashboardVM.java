package com.egoveris.dashboard.web.vm;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Include;
import org.zkoss.zul.Style;

import com.egoveris.dashboard.web.config.AppProperties;
import com.egoveris.dashboard.web.elements.MenuElement;
import com.egoveris.dashboard.web.elements.RastroMigas;
import com.egoveris.dashboard.web.util.DashboardConstants;
import com.egoveris.dashboard.web.util.DashboardLocator;
import com.egoveris.dashboard.web.util.MenuHelper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DashboardVM {
	
  /**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DashboardVM.class);

	private List<MenuElement> menuNavegacion;
	private MenuElement selectedMenuElement;
	private RastroMigas rastroMigas;
	private Map<String, String> properties;
	private String paramPrueba;
	private Usuario usuarioActive;
	
	@WireVariable("usuarioServiceImpl")
	private IUsuarioService usuarioService;
	
	@Wire
	Div contenido;
	
	@Wire
	Hlayout main;
	
	@Wire("#menuLateral #navegacion #navbar")
  Navbar navbar;
	
	@Wire
	Style navCss;
	
	/**
	 * Init de DashboardVM. Carga literales de idioma, variables de configuracion,
	 * menu dinamico y rastro de migas, entre otros-
	 */
	@Init
	public void init() {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}

		try {
			Labels.register(new DashboardLocator("db_label", Locales.getCurrent(), AppProperties.getInstance().getProperties().getString("cas.url")));
		} catch (MalformedURLException e) {
			logger.error("Ocurrió un error al cargar el idioma.");
		}
		
		setProperties(getDefaultProperties());
		registerSession();

		// Si hay usuario y aceptado condiciones
		if (null != usuarioActive && BooleanUtils.isTrue(usuarioActive.getAceptacionTYC())) {
			setMenuNavegacion(MenuHelper.loadMenuNavegacion());
		} else {
			setMenuNavegacion(Arrays.asList(MenuHelper.getMenuDeskTop()));
		}
		setSelectedMenuElement(
				findSelectedMenuItem(Executions.getCurrent().getDesktop().getRequestPath(), getMenuNavegacion()));
		setRastroMigas(new RastroMigas());
		Clients.evalJavaScript("menuCollapsed = false;");

		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Se utiliza para hacer Wire
	 * 
	 * @param view
	 */
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - start"); //$NON-NLS-1$
		}
		
		Selectors.wireComponents(view, this, false);
		
		// Sets neccesary JS variables
		Clients.evalJavaScript("menuCollapsed = false;");
		
		if (getProperties().get(DashboardConstants.PARAM_MENU_COLLAPSED) != null
		    && getProperties().get(DashboardConstants.PARAM_MENU_COLLAPSED).equals(Boolean.TRUE.toString())) {
		  Clients.evalJavaScript("menuCollapsed = true;");
		  Clients.evalJavaScript("navbarUuid = '" + navbar.getUuid() + "'");
	    Clients.evalJavaScript("cssUuid = '" + navCss.getUuid() + "'");
		}
		
		// Force to resize, to prevent "cutted" window
		Clients.resize(main);
		
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Recoge el usuario autenticado y lo setea en la sesion
	 */
	private void registerSession() {
		if (logger.isDebugEnabled()) {
			logger.debug("registerSession() - start"); //$NON-NLS-1$
		}

		if (Executions.getCurrent().getRemoteUser() != null) {
			String user = Executions.getCurrent().getRemoteUser();
			Executions.getCurrent().getDesktop().getSession().setAttribute(DashboardConstants.SESSION_USERNAME, user);
			
			// Si usuarioService no esta autowired y es una instancia de IUsuarioService
			if (usuarioService == null && SpringUtil.getBean("usuarioServiceImpl") instanceof IUsuarioService) {
				usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
			}
			
			if (usuarioService != null) {
				try {
					usuarioActive = usuarioService.obtenerUsuario(user);
					if (null != usuarioActive) {
						Executions.getCurrent().getDesktop().getSession().setAttribute(DashboardConstants.SESSION_USER_REPARTICION, usuarioActive.getCodigoReparticion());
						Executions.getCurrent().getDesktop().getSession().setAttribute(DashboardConstants.SESSION_USER_SECTOR, usuarioActive.getCodigoSectorInterno());
					}
				} catch (SecurityNegocioException e) {
					logger.debug("Se ha producido el siguiente problema en registerSession(): ", e);
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("registerSession() - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Comando invocado al momento de hacer click en un
	 * elemento del menu
	 * 
	 * @param menuElement Elemento de menu clickeado
	 */
	@Command
	public void doClickMenuElement(@BindingParam("menuElement") final MenuElement menuElement) {
		if (logger.isDebugEnabled()) {
			logger.debug("doClickMenuElement(MenuElement) - start"); //$NON-NLS-1$
		}

		Executions.sendRedirect(menuElement.getTargetZul());

		if (logger.isDebugEnabled()) {
			logger.debug("doClickMenuElement(MenuElement) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Setea las propiedades por default
	 * 
	 * @return Map con las propiedades por default
	 */
	private Map<String, String> getDefaultProperties() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDefaultProperties() - start"); //$NON-NLS-1$
		}

		Map<String, String> mapDefaultProperties = new HashMap<>();
		mapDefaultProperties.put(DashboardConstants.PARAM_LOGO, DashboardConstants.DEFAULT_LOGO);
		mapDefaultProperties.put(DashboardConstants.PARAM_HEADER_TEXT, DashboardConstants.DEFAULT_HEADER_TEXT);
		mapDefaultProperties.put(DashboardConstants.PARAM_SHOW_NOTIFICACIONES, DashboardConstants.DEFAULT_SHOW_NOTIFICACIONES);
		mapDefaultProperties.put(DashboardConstants.PARAM_SHOW_SHORTCUTS, DashboardConstants.DEFAULT_SHOW_SHORTCUTS);
		mapDefaultProperties.put(DashboardConstants.PARAM_MENU_COLLAPSED, DashboardConstants.DEFAULT_MENU_COLLAPSED);
		
		if (logger.isDebugEnabled()) {
			logger.debug("getDefaultProperties() - end"); //$NON-NLS-1$
		}
		
		return mapDefaultProperties;
	}
	
	/**
	 * Verifica a partir del parametro zul dado si corresponde
	 * a una entrada del menu. Si es asi, retorna dicha entrada
	 * 
	 * @param zulPath
	 * @return
	 */
	private MenuElement findSelectedMenuItem(final String zulPath, final List<MenuElement> menuElements) {
		if (logger.isDebugEnabled()) {
			logger.debug("findSelectedMenuItem(String, List<MenuElement>) - start"); //$NON-NLS-1$
		}
		
		MenuElement entradaMenu = null;
		String zul = zulPath;
		
		if (zulPath != null && zulPath.contains("//")) { // En el caso de que contenga doble //
			zul = zulPath.replace("//", "/");
		}
		
		if (zulPath != null && !zulPath.startsWith("/")) { // En el caso de que no empiece con /
			zul = "/" + zul;
		}
		
		// URL BASE
		String port    = ( Executions.getCurrent().getServerPort() == 80 ) ? "" : (":" + Executions.getCurrent().getServerPort());
		String baseUrl = Executions.getCurrent().getScheme() + "://" + Executions.getCurrent().getServerName() + port + Executions.getCurrent().getContextPath();
		
    logger.debug("baseUrl value: " + baseUrl);
    logger.debug("zul value: " + zul);
		
		for (final MenuElement menuElement : menuElements) {
			if (menuElement.getTargetZul() != null && menuElement.getTargetZul().contains(baseUrl + zul)) {
				entradaMenu = menuElement;
				break;
			}
			
			if (entradaMenu == null && !menuElement.getChildren().isEmpty()) {
				entradaMenu = findSelectedMenuItem(zul, menuElement.getChildren());
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("findSelectedMenuItem(String, List<MenuElement>) - end"); //$NON-NLS-1$
		}
		
		return entradaMenu;
	}
	
	/**
	 * Comando global que cambia la seccion de contenido
	 * a partir de los parametros dados
	 * 
	 * @param targetZul Ubicacion zul a mostrar
	 * @param args Argumentos con los cuales cargar el zul
	 */
	@GlobalCommand
	public void changeContainerContent(@BindingParam("targetZul") final String targetZul,
			@BindingParam("args") final Map<String, Object> args) {
		if (logger.isDebugEnabled()) {
			logger.debug("changeContainerContent(String, Map<String,Object>) - start"); //$NON-NLS-1$
		}
		
		Include pageInclude = new Include();
		
		if (args != null) {
			for (Map.Entry<String, Object> entry : args.entrySet()) {
				pageInclude.setDynamicProperty(entry.getKey(), entry.getValue());
			}
		}
		
		Components.removeAllChildren(contenido);
		pageInclude.setParent(contenido);
		pageInclude.setSrc(targetZul);

		if (logger.isDebugEnabled()) {
			logger.debug("changeContainerContent(String, Map<String,Object>) - end"); //$NON-NLS-1$
		}
	}
	
	// Getters - setters
	
	public List<MenuElement> getMenuNavegacion() {
		return menuNavegacion;
	}

	public void setMenuNavegacion(final List<MenuElement> menuNavegacion) {
		this.menuNavegacion = menuNavegacion;
	}

	public MenuElement getSelectedMenuElement() {
		return selectedMenuElement;
	}

	public void setSelectedMenuElement(final MenuElement selectedMenuElement) {
		if (logger.isDebugEnabled()) {
			logger.debug("setSelectedMenuElement(MenuElement) - start"); //$NON-NLS-1$
		}

		if (selectedMenuElement != null) {
			selectedMenuElement.setSelected(true);
		}
		
		this.selectedMenuElement = selectedMenuElement;

		if (logger.isDebugEnabled()) {
			logger.debug("setSelectedMenuElement(MenuElement) - end"); //$NON-NLS-1$
		}
	}

	public RastroMigas getRastroMigas() {
		return rastroMigas;
	}

	public void setRastroMigas(final RastroMigas rastroMigas) {
		this.rastroMigas = rastroMigas;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

	public String getParamPrueba() {
		return paramPrueba;
	}

	public void setParamPrueba(final String paramPrueba) {
		this.paramPrueba = paramPrueba;
	}
	
}
