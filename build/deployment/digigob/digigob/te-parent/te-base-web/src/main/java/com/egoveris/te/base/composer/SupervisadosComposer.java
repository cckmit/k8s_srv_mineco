package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TareasUsuario;
import com.egoveris.te.base.service.SupervisadosService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;



@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SupervisadosComposer extends GenericForwardComposer {
	/**
	 *
	 */
	private static final long serialVersionUID = -4436871756631367971L;
	@Autowired
	private AnnotateDataBinder binder;
	@WireVariable(ConstantesServicios.SUPERVISADOS_SERVICE)
	private SupervisadosService supervisadosService;
	private TareasUsuario selectedSupervisado;
	@Autowired
	private Window supervisadosView;
	@Autowired
	private Window misSupervisadosWindow;
	@Autowired
	private Listbox tasksBriefingList;
	@Autowired
	private Label labelPersonalACargo;
	private Paging supervisadosPaginator;

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.binder = new AnnotateDataBinder(component);
		
		component.addEventListener(Events.ON_NOTIFY, new SupervisadosOnNotifyWindowListener(this));
 
		this.self.setAttribute("dontAskBeforeClose", "true");
	}

	public void onCreate$misSupervisadosWindow(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	public void onClick$verTareas() {
		if (this.supervisadosView != null) {
			this.supervisadosView.detach();

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("supervisado", this.selectedSupervisado.getDatosUsuario());
			this.self.getDesktop().setAttribute("supervisado", this.selectedSupervisado.getDatosUsuario());
			this.supervisadosView = (Window) Executions.createComponents("/supervisados/supervisadosInbox.zul",
					this.self, variables);
			this.supervisadosView.setParent(this.misSupervisadosWindow);
			this.supervisadosView.setPosition("center");
			this.supervisadosView.setVisible(true);
			this.supervisadosView.doModal();
		} else {
			Messagebox.show(Labels.getLabel("ee.consultaExpedienteComposer.msgbox.noPosibleInicializarVista"), 
					Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	void updateEstadisticas() {
		if (this.self.isVisible()) {
			this.binder.loadComponent(this.tasksBriefingList);
		}
	}

	public List<TareasUsuario> getTareasSupervisados() {
		String loggedUsername = (String) Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);
		List<TareasUsuario> tareas = new ArrayList<TareasUsuario>();

		if (StringUtils.isNotEmpty(loggedUsername)) {
			tareas = this.supervisadosService.obtenerResumenTareasSupervisados(loggedUsername);

			if (tareas.size() != 0) {
				return tareas;
			}
		}

		supervisadosPaginator.setVisible(false);
		tasksBriefingList.setVisible(false);
		labelPersonalACargo.setVisible(true);

		return tareas;
	}

	public void setSelectedSupervisado(TareasUsuario selectedSupervisado) {
		this.selectedSupervisado = selectedSupervisado;
	}

	public TareasUsuario getSelectedSupervisado() {
		return selectedSupervisado;
	}
}

final class SupervisadosOnNotifyWindowListener implements EventListener {
	private SupervisadosComposer composer;

	public SupervisadosOnNotifyWindowListener(SupervisadosComposer comp) {
		this.composer = comp;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			if (event.getData() == null) {
				this.composer.updateEstadisticas();
			}
		}
	}
}
