package com.egoveris.edt.base.service.impl.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.edt.base.service.IMailGenericService;
import com.egoveris.edt.base.service.mail.IMailService;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.util.ConstanstesAdminSade;

import freemarker.template.Template;

/**
 * @author paGarcia
 */
public class MailServiceImpl implements IMailService {

	private AppProperty appProperty;

	@Autowired
	private IMailGenericService iMailGenericService;
	@Autowired
	private String ldapEntorno;

	@Value("${mail.asunto.alta}")
	private String asuntoAltaTextoEgoveris;

	@Value("${mail.asunto.reseteo}")
	private String asuntoReseteoTextoEgoveris;
	
	@Autowired
	private TemplateUtil templateUtil;

	@Override
	public void enviarMailAlta(UsuarioBaseDTO usuarioDestino) throws EmailNoEnviadoException {
		Map<String, String> variablesCorreo = new HashMap<>();
		StringBuilder cadenaLogo = new StringBuilder();
//		cadenaLogo.append("/templates/");
		cadenaLogo.append(appProperty.getString("cas.url"));
		cadenaLogo.append("/logo.jpg");

		variablesCorreo.put("usuarioNombre", usuarioDestino.getNombre());
		variablesCorreo.put("usuarioID", usuarioDestino.getUid());
		variablesCorreo.put("usuarioPassword", usuarioDestino.getPassword());
		variablesCorreo.put("diasParaLogin", ConstanstesAdminSade.DIAS_PARA_LOGIN.toString());
		variablesCorreo.put("logo", cadenaLogo.toString());

		Template template;
		try {
			template = this.templateUtil.getFreemakerTemplate("usuarioAlta");
		} catch (GetTemplateException e) {
			throw new EmailNoEnviadoException("Error al obtener template: " + e.getMessage(), e);
		}
		this.iMailGenericService.enviarMail(template, usuarioDestino.getMail(), variablesCorreo,
				this.asuntoAltaTextoEgoveris);
	}

	@Override
	public void enviarMailResetoPassword(UsuarioBaseDTO usuarioDestino) throws EmailNoEnviadoException {
		Map<String, String> variablesCorreo = new HashMap<>();
		variablesCorreo.put("usuarioNombre", usuarioDestino.getNombre());
		variablesCorreo.put("usuarioID", usuarioDestino.getUid());
		variablesCorreo.put("usuarioPassword", usuarioDestino.getPassword());
		variablesCorreo.put("diasParaLogin", ConstanstesAdminSade.DIAS_PARA_LOGIN.toString());

		Template template;
		try {
			template = this.templateUtil.getFreemakerTemplate("resetearPassword");
		} catch (GetTemplateException e) {
			throw new EmailNoEnviadoException("Error al obtener template: " + e.getMessage(), e);
		}
		this.iMailGenericService.enviarMail(template,
				usuarioDestino.getMail(), variablesCorreo, this.asuntoReseteoTextoEgoveris);
	}

	public String getLdapEntorno() {
		return ldapEntorno;
	}

	public void setLdapEntorno(String ldapEntorno) {
		this.ldapEntorno = ldapEntorno;
	}

	public void setAppProperty(AppProperty appProperty) {
		this.appProperty = appProperty;
	}

	public AppProperty getAppProperty() {
		return appProperty;
	}

}