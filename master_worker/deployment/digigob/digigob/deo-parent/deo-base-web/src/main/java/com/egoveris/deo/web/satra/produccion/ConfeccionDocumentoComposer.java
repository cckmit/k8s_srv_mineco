package com.egoveris.deo.web.satra.produccion;

import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;



public class ConfeccionDocumentoComposer extends GEDOGenericForwardComposer {

	private static final String REVISAR_DOCUMENTO_IMPORTADO = Labels.getLabel("gedo.confeccionDocComp.constante.revisarDocImportado");
	private static final String REVISAR_DOCUMENTO_LIBRE = Labels.getLabel("gedo.confeccionDocComp.constante.revisarDocLibre");
	private static final String REVISAR_DOCUMENTO_TEMPLATE = Labels.getLabel("gedo.confeccionDocComp.constante.revisarDocTemplate");
	private static final String RECHAZO_DOCUMENTO_IMPORTADO = Labels.getLabel("gedo.confeccionDocComp.constante.rechazoDocTemplate");
	private static final String RECHAZO_DOCUMENTO_LIBRE = Labels.getLabel("gedo.confeccionDocComp.constante.rechazoDocLibre");
	private static final String RECHAZO_DOCUMENTO_TEMPLATE = Labels.getLabel("gedo.confeccionDocComp.constante.rechazoDocTemplate");
	private static final String PRODUCIR_DOCUMENTO_IMPORTADO = Labels.getLabel("gedo.confeccionDocComp.constante.producirDocImportado");
	private static final String PRODUCIR_DOCUMENTO_LIBRE = Labels.getLabel("gedo.confeccionDocComp.constante.producirDocLibre");
	private static final String PRODUCIR_DOCUMENTO_TEMPLATE = Labels.getLabel("gedo.confeccionDocComp.constante.producirDocTemplate");

	private Include redactarDocumento;
	private static final long serialVersionUID = 1L;
	private Window producirWindow;
	private String estadoExpediente;

	

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);

		redactarDocumento.addEventListener(Events.ON_CLOSE,
				new ConfeccionDocumentoComposerListener(this));
		redactarDocumento.addEventListener(Events.ON_NOTIFY,
				new ConfeccionDocumentoComposerListener(this));
	    

		if (redactarDocumento.getChildren().isEmpty()) {

			producirWindow.setHeight("0px");
			producirWindow.setWidth("0px");
			
			Messagebox.show(Labels.getLabel("gedo.error.documentoSinFormulario"),
					Labels.getLabel("gedo.general.error"),Messagebox.OK,
					Messagebox.ERROR, new EventListener() {						
						public void onEvent(Event paramEvent) throws Exception {
							cerrarVentanaError();							
						}
					});
		}
	}
	
	public void cerrarVentanaError(){
		producirWindow.detach();
	}

	public void cerrarVentanaConNotificacion(boolean estadoExpediente) {
		if (this.self == null) {
			throw new IllegalAccessError(
					Labels.getLabel("gedo.confeccionDocumentoComposer.exception.componenteNoPresente"));
		}

		// Avisa a la ventana padre que debe refrescar su contenido.
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
				null));

		// Avisa a la propia ventana que debe cerrarse. En este caso no se setea
		// el atributo dontAskBeforeClose para que muestre el popup de
		// confirmación de abandono.
		this.self.setAttribute(Constantes.VAR_NUMERO_SA, estadoExpediente);
		this.self.setAttribute("dontAskBeforeClose", false);
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}
	 

	public void cerrarVentanaSinNotificacion(MacroEventData macroEventData) { 
		this.self.setAttribute(Constantes.VAR_NUMERO_SA, estadoExpediente);
		super.closeAndNotifyAssociatedWindow(macroEventData);
	}
	
	public void cerrarVentanaSinNotificacionExpediente(boolean macroEventData) { 
		if (this.self == null) {
			throw new IllegalAccessError(
					Labels.getLabel("gedo.confeccionDocumentoComposer.exception.componenteNoPresente"));
		}

		// Avisa a la ventana padre que debe refrescar su contenido.
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
				null)); 
		// Avisa a la propia ventana que debe cerrarse. En este caso no se setea
		// el atributo dontAskBeforeClose para que muestre el popup de
		// confirmación de abandono.
		this.self.setAttribute(Constantes.VAR_NUMERO_SA, macroEventData);
		this.self.setAttribute("dontAskBeforeClose", true);
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}
	

	public void cambiarTitulo(TipoDocumentoDTO tipoDocumento,
			String nombreActividad) {
		if (nombreActividad.equals(Constantes.TASK_REVISAR_DOCUMENTO)) {
			if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_LIBRE) {
				((Window) this.self).setTitle(REVISAR_DOCUMENTO_LIBRE);
			} else if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO) {
				((Window) this.self).setTitle(REVISAR_DOCUMENTO_IMPORTADO);
			} else {
				((Window) this.self).setTitle(REVISAR_DOCUMENTO_TEMPLATE);
			}
		} else if (nombreActividad.equals(Constantes.TASK_RECHAZADO)) {
			if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_LIBRE) {
				((Window) this.self).setTitle(RECHAZO_DOCUMENTO_LIBRE);
			} else if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO) {
				((Window) this.self).setTitle(RECHAZO_DOCUMENTO_IMPORTADO);
			} else {
				((Window) this.self).setTitle(RECHAZO_DOCUMENTO_TEMPLATE);
			}
		} else {
			if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_LIBRE) {
				((Window) this.self).setTitle(PRODUCIR_DOCUMENTO_LIBRE);
			} else if (tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO) {
				((Window) this.self).setTitle(PRODUCIR_DOCUMENTO_IMPORTADO);
			} else {
				((Window) this.self).setTitle(PRODUCIR_DOCUMENTO_TEMPLATE);
			}
		}
	}

}

final class ConfeccionDocumentoComposerListener implements EventListener {
	private ConfeccionDocumentoComposer composer;

	public ConfeccionDocumentoComposerListener(ConfeccionDocumentoComposer comp) {
		this.composer = comp;
	}

	public void onEvent(Event event) throws Exception {

		if (event.getName().equals(Events.ON_CLOSE)) {
			String estadoExpediente = null; 
			Map<String, Object> datos = (Map<String, Object>) event.getData();
			String notificacion = (String) datos.get("notificacion");
			estadoExpediente = (String) datos.get(Constantes.VAR_NUMERO_SA); 
			
			MacroEventData macroEventData = (MacroEventData) datos
					.get("macroEventData"); 
			Boolean noAbrirNuevamenteVentanaEnBaseAWorkflow = (Boolean) datos
					.get("noAbrirNuevamenteVentanaEnBaseAWorkflow");
			
			if (null == estadoExpediente) {
				notificacionSinExpediente(notificacion, macroEventData, 
						noAbrirNuevamenteVentanaEnBaseAWorkflow);
			} else {
				notificacionConExpediente(notificacion, macroEventData, 
						noAbrirNuevamenteVentanaEnBaseAWorkflow);
			} 
		}

		if (event.getName().equals(Events.ON_NOTIFY)) {
			Map<String, Object> datos = (Map<String, Object>) event.getData();
			TipoDocumentoDTO tipoDocumento = (TipoDocumentoDTO) datos
					.get("tipoDocumento");
			String nombreActividad = (String) datos.get("nombreActividad");

			composer.cambiarTitulo(tipoDocumento, nombreActividad);

		}

	}

	/**
	 * @param notificacion
	 * @param macroEventData
	 * @param noAbrirNuevamenteVentanaEnBaseAWorkflow
	 */
	private void notificacionConExpediente(String notificacion, MacroEventData macroEventData,
			Boolean noAbrirNuevamenteVentanaEnBaseAWorkflow) {
		if (notificacion.equals("activada")) { 
			composer.cerrarVentanaConNotificacion(true); 
		} else {  
				composer.cerrarVentanaSinNotificacionExpediente(true);
			} 
		 
	}

	/**
	 * @param notificacion
	 * @param macroEventData
	 * @param noAbrirNuevamenteVentanaEnBaseAWorkflow
	 */
	private void notificacionSinExpediente(String notificacion, MacroEventData macroEventData,
			Boolean noAbrirNuevamenteVentanaEnBaseAWorkflow) {
		if (notificacion.equals("activada")) { 
			composer.cerrarVentanaConNotificacion(false); 
		} else { 
			 if (noAbrirNuevamenteVentanaEnBaseAWorkflow ) { 
				composer.cerrarVentanaSinNotificacion(null);   
			}  else {
				composer.cerrarVentanaSinNotificacion(macroEventData);
			} 
		}
	}

}
