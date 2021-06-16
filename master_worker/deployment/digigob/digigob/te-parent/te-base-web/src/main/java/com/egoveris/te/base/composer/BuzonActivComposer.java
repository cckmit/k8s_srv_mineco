package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Paging;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BuzonActivComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -6347056479232360611L;

	private Listbox actividadesListbox;
	private List<ActividadInbox> todasLasActividades;
	private Label listaVacia;
	private Listfooter lisTotalSize;
	@Autowired
	private Combobox estados;
	@Autowired
	private Paging pagingActiv;
	private static final String ESTADO_TODOS = "Todos los estados...";
	private static final String COMPLETADA = "COMPLETADA";
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private static ProcessEngine processEngine;
		
	@WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
	private static IActividadExpedienteService actividadExpedienteService;
	
	
	List<String> tasks;

	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		// el tipo de evento ON_NOTIFY lo envia panelUsuarioComposer
		c.addEventListener(Events.ON_NOTIFY, new LoadActEventListener()); 
		c.addEventListener(Events.ON_USER, new LoadActEventListener());
		this.estados.appendItem(ESTADO_TODOS);
		this.estados.appendItem(ConstEstadoActividad.ESTADO_ABIERTA);
		this.estados.appendItem(COMPLETADA);
		this.estados.appendItem(ConstEstadoActividad.ESTADO_PENDIENTE);
		this.estados.setValue(ESTADO_TODOS);
		loadAct();
	}

	private void loadAct() {
		String username = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		List<String> wIds = buscarWorkflowIds(username);
		List<ActividadInbox> activ = null;
		if (wIds != null && !wIds.isEmpty()) {
			activ = buscarActividades(wIds);
		} else {
			activ = new ArrayList<ActividadInbox>();
		}
		this.todasLasActividades = activ;
		bindearActividades(activ);
	}

	private void bindearActividades(List<ActividadInbox> activ) {
		if (!activ.isEmpty()) {
			actividadesListbox.setModel(new BindingListModelList(activ, true));
			lisTotalSize.setLabel(String.valueOf(activ.size()));
			actividadesListbox.setActivePage(0); 
			actividadesListbox.setVisible(true);
			pagingActiv.setVisible(true);
			// oculto mensaje vacio
			listaVacia.setVisible(false);
		} else {
			// vacio - no muestro ni las columnas
			actividadesListbox.setVisible(false);
			pagingActiv.setVisible(false);
			listaVacia.setVisible(true);
		}
	}

	private String getEstadoSeleccionado() {
		if(this.estados.getValue().equals(COMPLETADA)){
			return ConstEstadoActividad.ESTADO_ABIERTA;
		}
		return this.estados.getValue();
	}


	private static List<Task> buscarTask(String usuario) {
		TaskQuery tq = processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);
		return tq.list();
	}

	private static List<String> buscarWorkflowIds(String usuario) {

		List<String> result = new ArrayList<String>();
		List<Task> tareas = buscarTask(usuario);
		for (Task task : tareas) {
			result.add(task.getExecutionId());
		}
		return result;
	}

	private static List<ActividadInbox> buscarActividades(List<String> workFlowIds) {

		return actividadExpedienteService.buscarActividadesVigentes(workFlowIds);
	}
	
	private  List<ActividadInbox> buscarActividadesPorEstado() {
		
		List<ActividadInbox> result = new ArrayList<ActividadInbox>();

		if(ESTADO_TODOS.equals(getEstadoSeleccionado())){
			return result = this.todasLasActividades;
		}
		for (ActividadInbox actividad : this.todasLasActividades) {
			if(actividad.getEstado().equals(getEstadoSeleccionado())){
				if(ConstEstadoActividad.ESTADO_ABIERTA.equals(getEstadoSeleccionado())){
					if(COMPLETADA.equals(this.estados.getValue())
							&& (ConstantesWeb.TIPO_ACTIVIDIDAD_RESULTADO_TAD.equals(actividad.getTipoActividadDecrip())
									|| ConstantesWeb.TIPO_ACTIVIDAD_APROBACION_SUBSANACION_TAD.equals(actividad.getTipoActividadDecrip()))){
						result.add(actividad);
					} else {
						if (ConstEstadoActividad.ESTADO_ABIERTA.equals(this.estados.getValue())
								&& ! (ConstantesWeb.TIPO_ACTIVIDIDAD_RESULTADO_TAD.equals(actividad.getTipoActividadDecrip())
										|| ConstantesWeb.TIPO_ACTIVIDAD_APROBACION_SUBSANACION_TAD.equals(actividad.getTipoActividadDecrip()))){
							result.add(actividad);
						}
					}
				} else {
					result.add(actividad);
				}
			}
		}
		return result;
	}
	
	private class LoadActEventListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_USER)) {
				if (event.getData().equals("actCerrada")) {
					// Actualizo la tabla de actividades
					loadAct();
				}
			} else if (event.getName().equals(Events.ON_NOTIFY)) {
				// Actualizo la tabla de actividades
				loadAct();
			}
		}
	}
	 
    public void onClick$filtrar() throws InterruptedException {
    	buscarActividadesPorEstado();
    	bindearActividades(buscarActividadesPorEstado());
    }
    
    public void onClick$quitarFiltro() throws InterruptedException {
        this.estados.setValue(ESTADO_TODOS);
        bindearActividades(buscarActividadesPorEstado());
    }

}