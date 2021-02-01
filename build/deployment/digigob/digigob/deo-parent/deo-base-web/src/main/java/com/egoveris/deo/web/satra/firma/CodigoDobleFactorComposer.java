package com.egoveris.deo.web.satra.firma;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.deo.web.utils.Utilitarios;

public class CodigoDobleFactorComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -3002494150001454188L;
	private static final Logger logger = LoggerFactory.getLogger(CodigoDobleFactorComposer.class);

	private Textbox codigoText;
	private String codigo;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
	    Map<String, String> datos = (HashMap<String, String>) Executions.getCurrent().getArg();
	    this.codigo = datos.get("codigo");
	}

	public void onClick$validarCodigo() throws InterruptedException {
		try {
			if (this.codigo.equals(this.codigoText.getValue())) {
				Clients.showBusy(Labels.getLabel("Procesando..."));
				Map<String, Object> datos = new HashMap<>();
				datos.put("funcion", "validarCodigo");
				Events.sendEvent(new Event(Events.ON_USER, this.self.getParent(), datos));
			} else {
				Messagebox.show(Labels.getLabel("gedo.error.codigoDobleFactor"),
						Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			logger.error("Error al verificar el código", e);
			Messagebox.show(Labels.getLabel("gedo.error.dobleFactor"), Labels.getLabel("gedo.general.error"),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onClick$reenviarMail() {
		try {
			Map<String, Object> datos = new HashMap<>();
			this.codigo = RandomStringUtils.randomAlphanumeric(5);
			String codEnc = Utilitarios.encriptar(this.codigo);
			datos.put("funcion", "reenviarMail");
			datos.put("codigo", codEnc);
			Events.sendEvent(new Event(Events.ON_USER, this.self.getParent(), datos));
		} catch (Exception e) {
			logger.error("Error al verificar el código", e);
		}
	}
}
