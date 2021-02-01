package com.egoveris.te.base.composer;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.rendered.HistActivExpItemRender;
import com.egoveris.te.base.service.IActividadService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HistoricoActivComposer extends GenericForwardComposer {
	
	private static Logger logger = LoggerFactory.getLogger(HistoricoActivComposer.class);

	private static final long serialVersionUID = -6347056479232360611L;

	private Listbox actividadesListbox;
	private Label listaVacia;
	private ExpedienteElectronicoDTO expedienteElectronico; 
	@Autowired
	private Window histActWindow;
	@WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
	private IActividadExpedienteService actividadExpedienteService;
	@WireVariable("actividadServiceImpl")
	private IActividadService actividadService;
	
	@Autowired
    private Toolbarbutton botonFiltrar;
	
	@Autowired
	private Window tramitacionWindow;
	
	
	@Autowired
	private AnnotateDataBinder binder;
	
	@Autowired
    private Paginal pagingActiv;
	
	private String loggedUsername = null; 
	
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		 Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null)); 

		// Determino el renderizado de los items
		// el atributo modificacion se setea en el include de tramitacion.zul
		// si el atributo es null es porque invocaron la ventana desde una pantalla de visualizacion
		String attrib = (String) Executions.getCurrent().getAttribute("modificacion");
		this.loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

		if(attrib != null && attrib.equals("false")){
			botonFiltrar.setDisabled(true);
			Executions.getCurrent().getSession().setAttribute("habilitarCancelacion",false);
		}
		actividadesListbox.setItemRenderer(new HistActivExpItemRender((attrib != null && attrib.equals("true"))));

		c.addEventListener(Events.ON_USER, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals(Events.ON_USER)) {
					if (event.getData() != null) {
						if (event.getData().equals("aprobGuardaTemp") || event.getData().equals("subsRealizada")
								|| event.getData().equals("aprobDocCerrado")) {
							// FIXME si hay alguna manera menos sucia CAMBIARLA!
							if (histActWindow.getParent() instanceof Include
									&& histActWindow.getParent().getParent() instanceof Tabpanel) {
								Events.sendEvent(histActWindow.getParent().getParent().getParent().getParent().getParent(), event);
							}
						} else if (event.getData().equals("actCerrada") || event.getData().equals("reload") ) {
							// Actualizo la tabla de historico de actividades
							loadAct();
						} else if(event.getData().equals("actulizarBotonPase")) {
							habilitarBotonPaseTramitacion();
						}
					}
				}
			}
		});
		
		binder = new AnnotateDataBinder(c);

		loadAct();
	}

	public void loadAct() {
		List<ActividadInbox> activs = buscarActividades(this.expedienteElectronico.getIdWorkflow());
		if (!activs.isEmpty()) {
			actividadesListbox.setModel(new BindingListModelList(activs, true));
			botonFiltrar.setVisible(true);
			actividadesListbox.setVisible(true);
			// oculto mensaje vacio
			listaVacia.setVisible(false);
		} else {
			// vacio - no muestro ni las columnas
			listaVacia.setVisible(true);
			actividadesListbox.setVisible(false);
			botonFiltrar.setVisible(false);
		}
	}
	
	public Paginal getTaskPaginator() {
		int vigentes = buscarActividades(this.expedienteElectronico.getIdWorkflow()).size();
        this.pagingActiv.setTotalSize(vigentes);
        return pagingActiv;
    }

    public void setTaskPaginator(Paginal pagingActiv) {
        this.pagingActiv = pagingActiv;
    }
	
	public void refreshWindows(){ 
	 

		// Determino el renderizado de los items
		// el atributo modificacion se setea en el include de tramitacion.zul
		String attrib = (String) Executions.getCurrent().getAttribute("modificacion");
		actividadesListbox.setItemRenderer(new HistActivExpItemRender((attrib != null && attrib.equals("true"))));
		
		loadAct();
	}

	private static List<ActividadInbox> buscarActividades(String workFlowId) {
		IActividadExpedienteService actividadExpedienteService = (IActividadExpedienteService) SpringUtil
				.getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
		return actividadExpedienteService.buscarHistoricoActividades(workFlowId);
	}
	
	public void onCancelarActividades() throws InterruptedException{
		 Messagebox.show(Labels.getLabel("ee.act.label.pregunta"), Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
	                new EventListener() {
	                    public void onEvent(Event evt) {
	                        switch (((Integer) evt.getData()).intValue()) {
	                        case Messagebox.YES:
	                        	 try {
	                        		 cancelarActividades();
	 	                        } catch (InterruptedException e) {
	 	                            logger.error(e.getMessage());
	 	                        }
	 	                        loadAct();

	                            break;

	                        case Messagebox.NO:
	                            break;
	                        }
	                    }
	                });

	}
	
	void eliminarActividadesPendienteGEDO() throws InterruptedException {
		List<ActividadInbox> activs = buscarActividades(this.expedienteElectronico.getIdWorkflow());
		for(ActividadInbox act:activs){
			ActividadDTO actividad  = actividadService.buscarActividad(act.getId());
			if(actividad.getTipoActividad().equals(ConstantesWeb.PETICION_PENDIENTE_GEDO)){
				actividadService.eliminarActividad(actividad);
			}
		}
		refreshWindows();
		Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
	}
	
	/**
	 * Las actividades no se eliminan, se dan de baja logica pasando su estado a "CANCELADA"
	 * @throws InterruptedException
	 */
	public void cancelarActividades() throws InterruptedException {
		
		String userName = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		
		
		
		List<ActividadInbox> activs = buscarActividades(this.expedienteElectronico.getIdWorkflow());
		
		for(ActividadInbox act:activs){
			ActividadDTO actividad  = actividadService.buscarActividad(act.getId());
				if(act.getEstado().equals(ConstantesWeb.ESTADO_PENDIENTE)|| act.getEstado().equals(ConstantesWeb.ESTADO_ABIERTA)){
					if(actividad.getTipoActividad().getNombre().equalsIgnoreCase(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION)){
						actividadExpedienteService.eliminarActividadSubsanacion(this.expedienteElectronico,userName);
						break;
					}
					actividad.setEstado(ConstEstadoActividad.ESTADO_CANCELADA);
					actividad.setFechaCierre(new Date());
					actividad.setUsuarioCierre(userName);
					actividadService.actualizarActividad(actividad);
				}
		}
		
		refreshWindows();
		Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
		Events.sendEvent( (Component) tramitacionWindow ,new Event(Events.ON_CHANGE));
	}
	
	public void habilitarBotonPaseTramitacion(){
		Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
	}
	
	public void onClickTab() {
		this.binder.loadAll();
	}
	

}