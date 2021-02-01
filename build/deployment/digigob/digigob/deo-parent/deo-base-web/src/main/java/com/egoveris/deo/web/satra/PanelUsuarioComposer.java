package com.egoveris.deo.web.satra;

import com.egoveris.deo.base.service.ISupervisadosService;
import com.egoveris.deo.base.service.IValidaUsuarioGedoService;
import com.egoveris.deo.base.service.LdapAccessor;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.encryption.URLEncryptor;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PanelUsuarioComposer extends GenericForwardComposer {
	/**
	* 
	*/
	private static final long serialVersionUID = 998699609160253400L;
	@WireVariable("ldapAccessorImpl")
	private LdapAccessor ldapAccessor;
	private Tabbox gedoTabs;

	@WireVariable("supervisadosServiceImpl")
	private ISupervisadosService supervisadosService;
	@WireVariable("validaUsuarioGedoServiceImpl")
	private IValidaUsuarioGedoService validaUsuarioGedoService;
	@WireVariable("usuarioServiceImpl")
	private IUsuarioService usuarioService;

	private static final Logger logger = LoggerFactory.getLogger(PanelUsuarioComposer.class);
	private Tab inBoxTab;
	private Tab supervisadosTab;
	private Tab consultarDocumentosTab;
	private Tab perfilTab;
	private String username;
	private HashMap<String, Object> pathMap;

	public void onSelect$gedoTabs() {
		if (gedoTabs.getSelectedPanel().getFirstChild() != null) {
			Window currentWindow = (Window) gedoTabs.getSelectedPanel().getFirstChild().getFirstChild();
			Event event = new Event(Events.ON_NOTIFY, currentWindow);
			Events.sendEvent(event);
		}
	}

	@Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.username = (String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		boolean usuarioValido = this.validaUsuarioGedoService.validaUsuarioGedo(this.username);
		if (!usuarioValido) {
			usuarioInvalidoRedirect();
			this.gedoTabs.setVisible(false);
		} else {
			// SPRING SECURITY
			// Si el usuario esta con el período de licencia activo
			// solo puede operar la pantalla de consultas
			if (this.usuarioService.licenciaActiva(this.username, new Date())) {
				this.inBoxTab.setDisabled(true);
				this.supervisadosTab.setDisabled(true);
				this.perfilTab.setDisabled(true);
				this.consultarDocumentosTab.setSelected(true);
			}
		}

	}

	public void usuarioInvalidoRedirect() {
		Executions.sendRedirect("/usuarioInvalido.zul");
	}

	@SuppressWarnings("unchecked")
	public void onCreate$panelUsuario() throws InterruptedException {
		// Si no es null o empty significa que se recibiÃ³ como parÃ¡metro el
		// nombre de un usuario subordinado para trabajar con su buzÃ³n
		String keyWord = null;
		try {
			if (this.usuarioService.licenciaActiva(this.username, new Date())) {
				this.consultarDocumentosTab.setSelected(true);
				this.gedoTabs.setSelectedTab(this.consultarDocumentosTab);
				Events.sendEvent(new Event("onClick", this.gedoTabs.getSelectedTab()));
			} else {
				String encryptedUsername = (String) Executions.getCurrent().getParameter("idUS");
				if (StringUtils.isNotEmpty(encryptedUsername)) {

					URLEncryptor codec = URLEncryptor.getInstance();

					try {
						String usernameSupervisado = codec.decrypt(encryptedUsername);
						String username = (String) Executions.getCurrent().getSession()
								.getAttribute(Constantes.SESSION_USERNAME);
						Usuario supervisado = this.supervisadosService.getSupervisado(username, usernameSupervisado);
						if (supervisado != null) {
							if (ldapAccessor.getNombreYApellido(supervisado.getUsername()) != null) {
								supervisado
										.setNombreApellido(ldapAccessor.getNombreYApellido(supervisado.getUsername()));
							} else {
								supervisado.setNombreApellido("");
							}
							Executions.getCurrent().getDesktop().setAttribute("supervisado", supervisado);
							Window supervisadosInbox = (Window) Executions.createComponents("/supervisadosInbox.zul",
									this.self.getParent(), new HashMap<String, Object>());
							this.self.getParent().appendChild(supervisadosInbox);
							supervisadosInbox.setPosition("center");
							supervisadosInbox.doModal();
						} else {
							String errorLabel = Labels.getLabel("gedo.error.errorObteniendoDatosSupervisado",
									new String[] { usernameSupervisado, username });
							this.showError(errorLabel);
						}
					} catch (SecurityException se) {
						logger.error("Error decoding URL parameter", se);
						this.showError(Labels.getLabel("gedo.error.parametroIncorrecto"));
					}
				} else {
					this.pathMap = (HashMap<String, Object>) Executions.getCurrent().getSession()
							.getAttribute(Constantes.PATHMAP);
					if (!CollectionUtils.isEmpty(this.pathMap)) {
						if ((Boolean) this.pathMap.get(Constantes.REDIRECT) != null
								&& (Boolean) this.pathMap.get(Constantes.REDIRECT)) {
							pathMap.put(Constantes.REDIRECT, false);
							Executions.sendRedirect("/");
						}
						keyWord = (String) this.pathMap.get(Constantes.KEYWORD);
						if (StringUtils.isNotEmpty(keyWord) && keyWord.equals(Constantes.KEYWORD_DOCUMENTOS)) {
							this.redirigirATab(this.consultarDocumentosTab);

						}
					}
				}
			}
		} catch (SuspendNotAllowedException e) {
			logger.error("je de error", e);
		} catch (SecurityNegocioException e) {
			logger.error("Mensaje de error", e);
		}
	}

	private void redirigirATab(Tab tab) {
		Events.sendEvent(tab, new Event(Events.ON_CLICK));
		this.gedoTabs.setSelectedTab(this.consultarDocumentosTab);
	}

	private void showError(String message) throws InterruptedException {
		Messagebox.show(message, Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						return;
					}
				});
	}

	public Tabbox getGedoTabs() {
		return gedoTabs;
	}

	public Tab getInBoxTab() {
		return inBoxTab;
	}

	public Tab getSupervisadosTab() {
		return supervisadosTab;
	}

	public Tab getConsultarDocumentosTab() {
		return consultarDocumentosTab;
	}

	public IValidaUsuarioGedoService getValidaUsuarioGedoService() {
		return validaUsuarioGedoService;
	}

	public void setValidaUsuarioGedoService(IValidaUsuarioGedoService validaUsuarioGedoService) {
		this.validaUsuarioGedoService = validaUsuarioGedoService;
	}

	public Tab getPerfilTab() {
		return perfilTab;
	}

	public void setPerfilTab(Tab perfilTab) {
		this.perfilTab = perfilTab;
	}

}