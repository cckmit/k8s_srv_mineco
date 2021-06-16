package com.egoveris.vucfront.base.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public abstract class AbstractMb implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(AbstractMb.class);

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Redirects to the selected Url. Please use pretty-config.xml and ConstantsUrl
	 * to define Urls.
	 * 
	 * @param url
	 */
	public void redirect(String url) {
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/" + url);
		} catch (IOException e) {
			logger.error("Can't redirect to the selected url: ".concat(url), e);
		}
	}

	/**
	 * Print's a info message in the selected <p:message id.
	 * 
	 * @param id
	 * @param msg
	 * @param msgTyp
	 */
	public void addMessageById(String id, String msg, MessageType msgTyp) {
		Severity sv = null;
		switch (msgTyp) {
		case ERROR:
			sv = FacesMessage.SEVERITY_ERROR;
			break;
		case WARNING:
			sv = FacesMessage.SEVERITY_WARN;
			break;
		case INFO:
			sv = FacesMessage.SEVERITY_INFO;
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(id, new FacesMessage(sv, "", msg));
	}

	/**
	 * Execute a JS in the requesContext. Command example: "PF('dialogId').show()"
	 * 
	 * @param command
	 */
	public void execute(String command) {
		RequestContext.getCurrentInstance().execute(command);
	}

	/**
	 * Update an id in the requesContext. For refreshing JSF components from the
	 * backing bean.
	 * 
	 * @param id
	 */
	public void update(String id) {
		RequestContext.getCurrentInstance().update(id);
	}

	/**
	 * 
	 * @param titulo
	 * @param msg
	 * @param msgTyp
	 */
	@SuppressWarnings("unchecked")
	public void showDialogMessage(Object msg, MessageType msgTyp) {
		// title
		List<String> tituloParam = new ArrayList<>();
		tituloParam.add(msgTyp.getTitle());
		// messages
		List<String> msgParam = new ArrayList<>();
		// message type
		List<String> msgtypeParam = new ArrayList<>();
		msgtypeParam.add(msgTyp.toString());

		if (msg instanceof List) {
			StringBuilder mergedMessages = new StringBuilder("");
			for (String aux : (List<String>) msg) {
				mergedMessages.append(aux.concat("_"));
			}
			String correctedMergedMessages = mergedMessages.toString().substring(0, mergedMessages.length() - 1);
			msgParam.add(correctedMergedMessages);
		} else {
			// TSF agrega un caracter '[' en el mensaje de error,
			// que no se quiere mostrar por pantalla.
			msgParam.add(((String) msg).replaceFirst("\\[", ""));
		}

		/* Messages */
		Map<String, List<String>> params = new HashMap<>();
		params.put("title", tituloParam);
		params.put("msgs", msgParam);
		params.put("type", msgtypeParam);

		/* Options */
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("widgetVar", "dialogErrores");
		options.put("resizable", true);
		// options.put("responsive", true);

		RequestContext.getCurrentInstance().openDialog("/jsf/errorDialog.jsf", options, params);
	}

}