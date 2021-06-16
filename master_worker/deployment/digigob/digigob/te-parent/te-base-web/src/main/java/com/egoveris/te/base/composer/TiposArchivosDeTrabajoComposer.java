package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoArchivoTrabajoDTO;
import com.egoveris.te.base.service.ArchivoDeTrabajoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TiposArchivosDeTrabajoComposer extends EEGenericForwardComposer {
	private Window tiposArchivosDeTrabajoWindow;
	
	@WireVariable(ConstantesServicios.ARCHIVOS_TRABAJO_SERVICE)
	private ArchivoDeTrabajoService archivoDeTrabajoService;
	
	private List<TipoArchivoTrabajoDTO> listaTiposDeArchivo;
	
	@Autowired
	private Combobox tipoArchivoDeTrabajo;
	
	private TipoArchivoTrabajoDTO  selectedTipoArchivo;
	
	private ArchivoDeTrabajoDTO archivoDeTrabajo;
	
	private ExpedienteElectronicoDTO ee;
	
	@Autowired
	private AnnotateDataBinder binder;
	
	public AnnotateDataBinder getBinder() {
		return binder;
	}
	
	@Autowired
	private Window tramitacionWindow;
	
	private boolean esModificacion= false;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		listaTiposDeArchivo = this.archivoDeTrabajoService.mostrarTodosTipoArchivoTrabajo();
		
		this.ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg().get("expediente");
		this.archivoDeTrabajo = (ArchivoDeTrabajoDTO) Executions.getCurrent().getArg().get("archivoDeTrabajo");
		
		if(this.archivoDeTrabajo.getTipoArchivoTrabajo()!=null && this.archivoDeTrabajo.getTipoArchivoTrabajo().getId()!=null){
			int i = 0;
			for(TipoArchivoTrabajoDTO tipoArch:listaTiposDeArchivo){
				if(tipoArch.getId().equals(this.archivoDeTrabajo.getTipoArchivoTrabajo().getId())){
					this.selectedTipoArchivo = listaTiposDeArchivo.get(i);
					esModificacion=true;
					break;
				}
				i++;
			}
		}else{
			int i = 0;
			for(TipoArchivoTrabajoDTO tipoArch:listaTiposDeArchivo){
				if("Otros".equals(tipoArch.getNombre())) {
					this.selectedTipoArchivo = listaTiposDeArchivo.get(i);
					break;
				}
				i++;
			}
		}
		
		binder = new AnnotateDataBinder(comp);
		
		comp.addEventListener(Events.ON_USER , new TiposArchivosDeTrabajoComposerListener(this));
		
		this.binder.loadComponent(this.tipoArchivoDeTrabajo);
	}


	public void setSelectedTipoArchivo(TipoArchivoTrabajoDTO selectedTipoArchivo) {
		this.selectedTipoArchivo = selectedTipoArchivo;
	}


	public TipoArchivoTrabajoDTO getSelectedTipoArchivo() {
		return selectedTipoArchivo;
	}


	public void setListaTiposDeArchivo(List<TipoArchivoTrabajoDTO> listaTiposDeArchivo) {
		this.listaTiposDeArchivo = listaTiposDeArchivo;
	}


	public List<TipoArchivoTrabajoDTO> getListaTiposDeArchivo() {
		return listaTiposDeArchivo;
	}


	public void setArchivoDeTrabajoService(ArchivoDeTrabajoService archivoDeTrabajoService) {
		this.archivoDeTrabajoService = archivoDeTrabajoService;
	}


	public ArchivoDeTrabajoService getArchivoDeTrabajoService() {
		return archivoDeTrabajoService;
	}


	public void setTiposArchivosDeTrabajoWindow(
			Window tiposArchivosDeTrabajoWindow) {
		this.tiposArchivosDeTrabajoWindow = tiposArchivosDeTrabajoWindow;
	}


	public Window getTiposArchivosDeTrabajoWindow() {
		return tiposArchivosDeTrabajoWindow;
	}
	
	public void onGuardarTipoArchivo() throws InterruptedException {
		TipoArchivoTrabajoDTO selectTipoArchivoDeTrabajo = (TipoArchivoTrabajoDTO)this.tipoArchivoDeTrabajo.getSelectedItem().getValue();
		
		if(!selectTipoArchivoDeTrabajo.isRepetible()){
			for(ArchivoDeTrabajoDTO archivo: ee.getArchivosDeTrabajo()){
				if(archivo.getTipoArchivoTrabajo().getNombre()!=null && archivo.getTipoArchivoTrabajo().getNombre().equals(selectTipoArchivoDeTrabajo.getNombre())){
					throw new WrongValueException(this.tipoArchivoDeTrabajo,"No se permite repetir el tipo de archivo ingresado.");
				}
			}
		}
		
		archivoDeTrabajo.setTipoArchivoTrabajo(selectTipoArchivoDeTrabajo);
		if(!esModificacion){
			enviarBloqueoPantalla();
		}else{
			Events.echoEvent("onUser", this.self, null);
		}
    }
	
	public void onCancelar() throws InterruptedException {
		((Window) this.self).onClose();
    }
	
	protected void enviarBloqueoPantalla() {
		Clients.showBusy(Labels.getLabel("ee.tramitacion.subirarchivo"));
		Events.echoEvent("onUser", this.self, null);
	}
	
	final class TiposArchivosDeTrabajoComposerListener implements EventListener {
		private TiposArchivosDeTrabajoComposer composer;

		public TiposArchivosDeTrabajoComposerListener(TiposArchivosDeTrabajoComposer comp) {
			this.composer = comp;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {

			if (event.getName().equals(Events.ON_USER))
			{
				if(!esModificacion){
					Events.echoEvent("onUser",(Component) tramitacionWindow , "uploadArchivoDeTrabajo");
					Events.sendEvent(this.composer.getTiposArchivosDeTrabajoWindow(),new Event(Events.ON_CLOSE));
				}else{
					Events.sendEvent(this.composer.getTiposArchivosDeTrabajoWindow(),new Event(Events.ON_CLOSE));
					Events.echoEvent("onUser",(Component) tramitacionWindow , "actualizarArchivoDeTrabajo");
				}
			}
			if (event.getName().equals(Events.ON_CLOSE)) {
				Events.sendEvent(this.composer.getTiposArchivosDeTrabajoWindow(),new Event(Events.ON_CLOSE));
			}	
		}
	}
}


