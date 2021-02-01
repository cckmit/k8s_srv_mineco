	package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TareaParaleloInbox;
import com.egoveris.te.base.util.BusinessFormatHelper;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class TareasEnParaleloItemRenderer implements ListitemRenderer {
	
	private static final String TAREA_TERMINADA = "Terminado";
	private static final String TAREA_PENDIENTE = "Pendiente";
	
	public void render(Listitem item, Object data,int arg1) throws Exception {
		TareaParaleloInbox tarea=(TareaParaleloInbox)data;
		
		Listcell currentCell;

		//Nombre Tarea
		new Listcell(tarea.getEstado()).setParent(item);
		
		
		//Codigo Expediente
		
		Integer numero= tarea.getNumeroExp();
		String numeroExp = BusinessFormatHelper.completarConCerosNumActuacion(numero);
		
		
		String codigoExpediente = tarea.getTipoDocumentoExp()+"-"
		+ tarea.getAnioExp()+"-"
		+ numeroExp+"- -"
		+ tarea.getCodigoReparticionActuacion()
		+"-"+ tarea.getCodigoReparticionUsuario();
				
		new Listcell(codigoExpediente).setParent(item);		
		
		//Fecha
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		new Listcell(sdf.format(tarea.getFechaPase())).setParent(item);
		
		//Motivo y Destinatario
		if (!tarea.getEstado().equals(TAREA_TERMINADA)) {
			String motivo = tarea.getMotivo();
			new Listcell(formatoMotivo(motivo)).setParent(item);
			new Listcell(tarea.getUsuarioGrupoDestinoAsignado())
			.setParent(item);
		} else {
			String motivo = tarea.getMotivoRespuesta();
			new Listcell(formatoMotivo(motivo)).setParent(item);
			new Listcell(tarea.getUsuarioGrupoDestinoAnterior())
			.setParent(item);
		}
		
		//Accion a realizar
		currentCell=new Listcell();
		currentCell.setParent(item);
		Hbox hbox=new Hbox();
		
		if (tarea.getEstado().equals(TAREA_PENDIENTE) && (!tarea.getUsuarioGrupoDestinoAsignado().equals(tarea.getUsuarioOrigen()))) {
			Image runImage = new Image("/imagenes/avocarexpedientes.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(runImage,
							"onClick=tareasEnParaleloWindow$TareasEnParaleloComposer.onAdquirirTask");
			Label adquirir = new Label(Labels.getLabel("ee.inbox.adquirir"));
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(adquirir,
							"onClick=tareasEnParalelo$TareasEnParaleloComposer.onAdquirirTask");

			runImage.setParent(hbox);
			adquirir.setParent(hbox);
		}
		
		
		hbox.setParent(currentCell);
		currentCell = new Listcell();
		currentCell.setParent(item);
		}

	private String formatoMotivo(String motivo) {
		String salida ;
			if(motivo.length()>70){
				salida=(String) motivo.substring(0, 70)+"...";
			}else{
				salida = motivo+".";
			}
		
		return salida;
	}
	
	
	
	
	
	
	}