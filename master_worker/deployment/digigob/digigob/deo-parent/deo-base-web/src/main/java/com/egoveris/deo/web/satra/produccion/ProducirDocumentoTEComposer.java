package com.egoveris.deo.web.satra.produccion;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.springframework.security.core.context.SecurityContextImpl;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.sharedsecurity.base.model.Usuario;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProducirDocumentoTEComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = 7301069883281020067L;

	@WireVariable("processEngine")
	private ProcessEngine processEngine;

	@Wire
	Window producirDocumentoTE;

	private Include redactarDocumento;

	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		// redactarDocumento.addEventListener(Events.ON_CLOSE,
		// new ProducirDocumentoTEComposerListener(this));
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		if (Executions.getCurrent().getParameter("numeroSade") != null) {
			String numeroSade = Executions.getCurrent().getParameter("numeroSade");
			Task task = null;

			try {
				TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
				taskQuery.executionId(numeroSade);

				task = taskQuery.uniqueResult();
			} catch (Exception e) {
				// TODO ponerle logger
			}

			if (task != null) {
				Map<String, Object> variables = new HashMap<>();
				variables.put("selectedTask", task);

				Executions.getCurrent().getDesktop().setAttribute("selectedTask", task);

				// Obtiene el usuario logeado del CAS y lo asigna a la session,
				// en el caso de que no este
				if (Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME) == null) {
					for (Map.Entry<String, Object> entry : Executions.getCurrent().getDesktop().getSession()
							.getAttributes().entrySet()) {
						if (entry.getKey() != null && "SPRING_SECURITY_CONTEXT".equalsIgnoreCase(entry.getKey())) {
							SecurityContextImpl securityContextImpl = (SecurityContextImpl) entry.getValue();
							Usuario usuario = (Usuario) securityContextImpl.getAuthentication().getPrincipal();
							Executions.getCurrent().getSession().getAttributes().put(Constantes.SESSION_USERNAME,
									usuario.getUsername());
							break;
						}
					}
				}

				producirDocumentoTE = (Window) Executions.createComponents("/taskViews/producirDocumento.zul",
						this.self, variables);
			}
		}

	}
}
// public void cerrarVentanaSinNotificacion(MacroEventData macroEventData) {
// super.closeAndNotifyAssociatedWindow(macroEventData);
// }
// public void cerrarVentanaConNotificacion() {
// if (this.self == null) {
// throw new IllegalAccessError(
// Labels.getLabel("gedo.confeccionDocumentoComposer.exception.componenteNoPresente"));
// }
//
// // Avisa a la ventana padre que debe refrescar su contenido.
// Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
// null));
//
// // Avisa a la propia ventana que debe cerrarse. En este caso no se setea
// // el atributo dontAskBeforeClose para que muestre el popup de
// // confirmación de abandono.
// Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
// }
//
// }
//

// final class ProducirDocumentoTEComposerListener implements EventListener {
// private ProducirDocumentoTEComposer composer;
//
// public ProducirDocumentoTEComposerListener(ProducirDocumentoTEComposer comp)
// {
// this.composer = comp;
// }
// @SuppressWarnings("unchecked")
// public void onEvent(Event event) throws Exception {
//
// if (event.getName().equals(Events.ON_CLOSE)) {
// Map<String, Object> datos = (Map<String, Object>) event.getData();
// String notificacion = (String) datos.get("notificacion");
// MacroEventData macroEventData = (MacroEventData) datos
// .get("macroEventData");
// Boolean noAbrirNuevamenteVentanaEnBaseAWorkflow = (Boolean) datos
// .get("noAbrirNuevamenteVentanaEnBaseAWorkflow");
//
// if (notificacion.equals("activada")) {
// composer.cerrarVentanaConNotificacion();
// } else {
// if (noAbrirNuevamenteVentanaEnBaseAWorkflow) {
// composer.cerrarVentanaSinNotificacion(null);
// } else {
// composer.cerrarVentanaSinNotificacion(macroEventData);
// }
// }
// }
// }
//
// }
