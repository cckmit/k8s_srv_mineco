/**
 * 
 */
package com.egoveris.workflow.designer.module.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.egoveris.plugins.tools.ZkUtil;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.composer.EnvioExpedienteComposer;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;


/**
 * @author difarias
 * 
 */
@SuppressWarnings("deprecation")
public class RealizarPase extends EnvioExpedienteComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2455700013709101966L;

	private static final String ZUL_REALIZAR_PASE = "/pantallas/RealizarPase.zul";

	private static final Object ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";

	@Autowired
	private Window realizarPaseWindow;
	private AnnotateDataBinder binder;
	@Autowired
	private Combobox estado;
	@Autowired
	private Combobox usuario;
	@Autowired
	private Radio usuarioRadio;
	@Autowired
	private Radio sectorRadio;
	@Autowired
	private Radio reparticionRadio;
	@Autowired
	private Bandbox reparticionBusquedaSADE;
	@Autowired
	private Bandbox sectorReparticionBusquedaSADE;
	@Autowired
	private Bandbox sectorBusquedaSADE;

	
	private List<String> destinies;
	private ExpedienteElectronicoDTO expedienteElectronico;
	
    @Autowired
    private ReparticionServ reparticionServ;
    private SectorInternoServ sectorInternoServ;	
    private SectorInternoBean sectorMesaVirtual;
    
	public static Component show(ExpedienteElectronicoDTO ee, Component parent, List<String> destinies, String ZUL) {
		RealizarPase realizarPase = new RealizarPase();
		
		realizarPase.destinies = destinies;
		realizarPase.expedienteElectronico = ee;
		String zulPage = (ZUL!=null?ZUL:ZUL_REALIZAR_PASE);
		
		
		if (ee!=null) { // si viene un expediente entonces es una simulacion
	        realizarPase.reparticionServ = (ReparticionServ) SpringUtil.getBean("reparticionServ");
	        realizarPase.sectorInternoServ = (SectorInternoServ) SpringUtil.getBean("sectorInternoServ");
		}
		
		final Window win = ZkUtil.createComponent(zulPage, parent, null, realizarPase);
		
			win.doModal();
		
		return win;
	}
	
	public static Component show(Component parent, List<String> destinies, String ZUL) {
		return show(null,parent,destinies,ZUL);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doAfterCompose(Component component) throws Exception {
		try {
			super.doAfterCompose(component);
		} catch (Exception e) {
			
		}
		this.usuarioRadio.setSelected(false);
		this.sectorRadio.setSelected(false);
		this.reparticionRadio.setSelected(false);
		this.usuario.setDisabled(true);
		this.sectorBusquedaSADE.setDisabled(true);
		this.reparticionBusquedaSADE.setDisabled(true);
		this.sectorReparticionBusquedaSADE.setDisabled(true);
		/*
		 * En la ventana de enviar pase, si la tarea esta en estado archivo.
		 * solo debe visualizarce el estado archivo en el combo. Si la tarea
		 * esta en otro estado debe tener disponibles las demas opciones de
		 * estado en el combo. Ademas no de visualizarce la opcion cierre que es
		 * el fin del workflow, no un estado.
		 */
		IUsuarioService usuariosSADEService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
		if (usuariosSADEService!=null) {
			usuario.setModel(ListModels.toListSubModel(new ListModelList(usuariosSADEService.obtenerUsuarios()), getUsuarioComparator(), 30));
			this.binder = new AnnotateDataBinder();
			binder.loadComponent(usuario);
		}
		
		if (this.destinies!=null && !this.destinies.isEmpty()) {
			for (String destiny : destinies) {
				this.estado.appendItem(destiny);
			}
			
			this.estado.setSelectedIndex(0);
		}
	}
	
	/**
	 * User Comparator
	 * @return
	 */
	public Comparator<Object> getUsuarioComparator() {
		return new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				String userText = null;
				if (o1 instanceof String) {
					userText = (String) o1;
				}
				if ((o2 instanceof Usuario)
						&& (StringUtils.isNotEmpty(userText))
						&& (userText.length() > 2)) {
					Usuario dub = (Usuario) o2;
					String NyAp = dub.getNombreApellido();
					if (NyAp != null) {
						if (NyAp.toLowerCase().contains(userText.toLowerCase())) {
							return 0;
						}
					}
				}
				return 1;
			}
		};
	}
	
	
	public void onCancelar() {
		this.realizarPaseWindow.detach();
	}
	
	public void onUsuarioClick() {
		this.usuario.setDisabled(false);
		this.sectorBusquedaSADE.setDisabled(true);
		this.sectorRadio.setChecked(false);
		this.sectorReparticionBusquedaSADE.setDisabled(true);
		this.sectorReparticionBusquedaSADE.setValue(null);
		this.reparticionBusquedaSADE.setValue(null);
		this.reparticionRadio.setChecked(false);
		this.reparticionBusquedaSADE.setDisabled(true);
		this.sectorBusquedaSADE.setValue(null);
	}
	
	/**
	 * Se guarda el estado que se encontraba seleccionado antes de seleccionar
	 * otro y este último y se los setea en variables del WF para poder mantener
	 * una lógica con la pantalla anterior.
	 */
	public void onSelectEstado() {
		this.estado.setValue(this.estado.getSelectedItem().getLabel());

		// Solo se puede habilitar el boton "realizar pase y comunicar"
		// si el estado del expediente es alguno de los siguientes: TRAMITACION,
		// COMUNICACION, EJECUCION

		if (this.estado.getValue().equals(ESTADO_GUARDA_TEMPORAL)) {
			this.usuarioRadio.setDisabled(true);

			if (this.usuarioRadio.isChecked()) {
				this.usuarioRadio.setChecked(false);
				this.usuario.setValue(null);
				this.usuario.setDisabled(true);
			}

			this.sectorRadio.setDisabled(true);

			if (this.sectorRadio.isChecked()) {
				this.sectorRadio.setChecked(false);
				this.sectorReparticionBusquedaSADE.setValue(null);
				this.sectorReparticionBusquedaSADE.setDisabled(true);
				this.sectorBusquedaSADE.setValue(null);
				this.sectorBusquedaSADE.setDisabled(true);
			}

			this.reparticionRadio.setDisabled(true);

			if (this.reparticionRadio.isChecked()) {
				this.reparticionRadio.setChecked(false);
				this.reparticionBusquedaSADE.setValue(null);
				this.reparticionBusquedaSADE.setDisabled(true);
			}
		} else {
			this.usuarioRadio.setDisabled(false);
			this.sectorRadio.setDisabled(false);
			this.reparticionRadio.setDisabled(false);
		}
	}
	
	public void onSectorClick() {
		// se agrega if por incidencia 4488.
		if ((this.sectorRadio.isChecked()) && (this.sectorReparticionBusquedaSADE.getValue() != "") && (this.sectorReparticionBusquedaSADE.getValue() != null)) {
			this.sectorBusquedaSADE.setDisabled(false);
		} else {
			this.sectorBusquedaSADE.setDisabled(true);
		}

		// se comenta por incidencia 4488.
		this.usuario.setDisabled(true);
		this.usuario.setValue(null);
		this.usuarioRadio.setChecked(false);
		this.sectorReparticionBusquedaSADE.setDisabled(false);
		this.reparticionRadio.setChecked(false);
		this.reparticionBusquedaSADE.setDisabled(true);
	}

	public void onReparticionClick() {
		this.reparticionBusquedaSADE.setDisabled(false);
		this.sectorBusquedaSADE.setDisabled(true);
		this.sectorReparticionBusquedaSADE.setValue(null);
		this.sectorRadio.setChecked(false);
		this.sectorReparticionBusquedaSADE.setDisabled(true);
		this.usuario.setValue(null);
		this.usuario.setDisabled(true);
		this.usuarioRadio.setChecked(false);
		this.sectorBusquedaSADE.setValue(null);
	}
	
	
	public void onAceptar(Event e) {
		String estadoStr = estado.getValue();
		String callFn="";
		
		callFn = String.format("irPaso('%s');", estadoStr);
		
		if (usuarioRadio.isChecked()) {
			String usuarioStr = ((Usuario) usuario.getSelectedItem().getValue()).getUsername();
			callFn = String.format("paseUsuario('%s','%s');", estadoStr, usuarioStr);
		}
		if (sectorRadio.isChecked()) {
			callFn = String.format("paseReparticionSector('%s','%s','%s');", estadoStr, this.sectorReparticionBusquedaSADE.getValue().trim(), this.sectorBusquedaSADE.getValue());
		}
		if (reparticionRadio.isChecked()) {
			callFn = String.format("paseReparticion('%s','%s');", estadoStr, this.reparticionBusquedaSADE.getValue());
		}
		
		Clients.evalJavaScript(String.format("insertCmd(\"%s\");",callFn));
		
		if (this.realizarPaseWindow!=null) {
			this.realizarPaseWindow.detach();
		}
		e.stopPropagation();
	}
	
	public void onEnviar() {
		definirMotivo();
		Events.sendEvent(new Event(Events.ON_USER,this.realizarPaseWindow.getParent()));
		
		String caratula = expedienteElectronico.getCodigoCaratula();
        String mensaje = "";
        String asignacionGrupo;
        
        String asignacionUsuario = "";
        String estadoSeleccionado = this.estado.getValue();
        final StringBuffer destino = new StringBuffer();
    	destino.delete(0, destino.length());
    	
        
        if (this.usuario!=null && this.usuario.getSelectedItem()!=null) {
        	asignacionUsuario = ((Usuario)this.usuario.getSelectedItem().getValue()).getUsername();
        	destino.append(asignacionUsuario);
        }
		
        if (this.usuarioRadio.isChecked()) {
            mensaje = "Se generó el pase del expediente: " + caratula + " , se envió al usuario: " + asignacionUsuario;
        } else {
            if (this.sectorRadio.isChecked()) {
                asignacionGrupo = this.sectorReparticionBusquedaSADE.getValue().trim() + "-" + this.sectorBusquedaSADE.getValue();
                mensaje = "Se generó el pase del expediente: " + caratula + " , se envió al sector: " + asignacionGrupo;
            	destino.append(asignacionGrupo);
            } else if (this.reparticionRadio.isChecked()) {

                if ((this.reparticionBusquedaSADE.getValue() == null) || (this.reparticionBusquedaSADE.getValue() == "") || this.reparticionBusquedaSADE.getValue().isEmpty()) {
                    throw new WrongValueException(this.reparticionBusquedaSADE, "Debe seleccionar una Repartición válida.");
                }

                ReparticionBean reparticion = this.reparticionServ.buscarReparticionPorCodigo(this.reparticionBusquedaSADE.getValue().trim());
                List<SectorInternoBean> sectores = sectorInternoServ.buscarSectoresPorReparticion(reparticion.getId());

                for (SectorInternoBean sector : sectores) {
                    if (sector.isMesaVirtual() && sector.isSectorMesa()) {
                        sectorMesaVirtual = sector;
                    }
                }
            	
                asignacionGrupo = this.reparticionBusquedaSADE.getValue().trim() + "-" + sectorMesaVirtual.getCodigo().trim().toUpperCase();
            	destino.append(asignacionGrupo);
                mensaje = "Se generó el pase del expediente: " + caratula + " , se envió a la repartición: " + asignacionGrupo;
            }
        }

        if (estadoSeleccionado.equals(ESTADO_GUARDA_TEMPORAL)) {
            mensaje = "El expediente: " + caratula + " , se ha guardado temporalmente de forma correcta.";
        }

        final Component mainWindow = this.realizarPaseWindow.getParent();
        final String proximoEstado = this.estado.getValue();
        final String motivoPase = formatoMotivo(this.motivoExpedienteStr);
			this.realizarPaseWindow.detach();
			Messagebox.show(mensaje, Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("proximoEstado", proximoEstado);
					data.put("destinatario",destino.toString());
					data.put("motivo",motivoPase);
					Events.sendEvent(new Event(Events.ON_OK,mainWindow, data));
				}
			});
		
	}
	
	public String formatoMotivo(String motivo) {
		return motivo;
	}
	
}
