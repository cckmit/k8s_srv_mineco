package com.egoveris.te.base.rendered;


import com.egoveris.te.base.composer.HistorialTrataComposer;
import com.egoveris.te.base.model.TrataAuditoriaDTO;
import com.egoveris.te.base.util.UtilsDate;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;




public class TrataHistoricotemRenderer implements ListitemRenderer{
	
  private HistorialTrataComposer composer;
	
	public TrataHistoricotemRenderer() {
	}
	
	public TrataHistoricotemRenderer(HistorialTrataComposer composer) {
		super();
		this.setComposer(composer);
	}
	
	@Override
	public void render(Listitem item, Object data,int arg1) throws Exception {
		
	
		TrataAuditoriaDTO trata = (TrataAuditoriaDTO)data;
		
		//codigo trata
		Listcell codigoTrata = new Listcell(trata.getCodigoTrata());
		codigoTrata.setTooltiptext(trata.getCodigoTrata());
		codigoTrata.setParent(item);

		//DESC TRATA
		String desc= trata.getDescripcionTrata();
		if(desc != null  && desc.trim().length() > 9){
			desc = desc.substring(0, 7) + "...";
		}
		Listcell descripcion = new Listcell(desc);
		descripcion.setTooltiptext(trata.getDescripcionTrata());
		descripcion.setParent(item);
		
		//tipoDoc
		Listcell tipoDoc = new Listcell(trata.getTipoDocumento());
		tipoDoc.setParent(item);
		
		//workflow
		Listcell workflow = new Listcell(trata.getWorkflow());
		workflow.setTooltiptext(trata.getWorkflow());
		workflow.setParent(item);
		
		//manual
		final Checkbox esManual = new Checkbox();
		if(trata.getEsManual()!=null){
			esManual.setChecked(trata.getEsManual());
		}else{
			esManual.setChecked(false);
		}
		esManual.setDisabled(true);
		
		Listcell seleccionar = new Listcell();
		esManual.setParent(seleccionar);
		seleccionar.setParent(item);

		//automatica
		Checkbox esAutomatica = new Checkbox();
		if(trata.getEsAutomatica()!=null)
			esAutomatica.setChecked(trata.getEsAutomatica());
		else
			esAutomatica.setChecked(false);
		
		esAutomatica.setDisabled(true);
		
		Listcell esAutomaticaCell = new Listcell();
		esAutomatica.setParent(esAutomaticaCell);
		esAutomaticaCell.setParent(item);

		//interno
		Checkbox esInterno= new Checkbox();
		if(trata.getEsInterno()!=null){
			esInterno.setChecked(trata.getEsInterno());
		} else {
			esInterno.setChecked(false);
		}
		esInterno.setDisabled(true);
		Listcell esInternoCell = new Listcell();
		esInterno.setParent(esInternoCell);
		esInternoCell.setParent(item);
		//Externo
		Checkbox esExterno= new Checkbox();
		if(trata.getEsExterno()!=null)
			esExterno.setChecked(trata.getEsExterno());
		else
			esExterno.setChecked(false);
		
		esExterno.setDisabled(true);
		Listcell esExternoCell = new Listcell();
		esExterno.setParent(esExternoCell);
		esExternoCell.setParent(item);
		
		
		//Envio Automatico a Guarda Temporal
		Checkbox esEnvioAutomaticoGT= new Checkbox();
		if(trata.getEsEnvioAutomaticoGT()!=null){
			esEnvioAutomaticoGT.setChecked(trata.getEsEnvioAutomaticoGT());
		} else {
			esEnvioAutomaticoGT.setChecked(false);
		}
		esEnvioAutomaticoGT.setDisabled(true);
		Listcell esEnvioAutomaticoGTCell = new Listcell();
		esEnvioAutomaticoGT.setParent(esEnvioAutomaticoGTCell);
		esEnvioAutomaticoGTCell.setParent(item);	
		
		// fecha modificacion
		Listcell fechaModificacion = new Listcell(UtilsDate.formatearFechaHora(trata.getFechaModificacion()));
		fechaModificacion.setParent(item);
		
		//Username
		Listcell usuario = new Listcell(trata.getUserName());
		usuario.setParent(item);
		
		//Clave Tad
		Listcell claveTad = new Listcell(trata.getClaveTad());
		claveTad.setTooltiptext(trata.getClaveTad());
		claveTad.setParent(item);
		
		//tiempo estimado
		Integer t = trata.getTiempoEstimado();
		
		Listcell tiempoEstimado;
		if(t!=null){
			tiempoEstimado = new Listcell(t.toString());
		}else{
			tiempoEstimado = new Listcell(null);
		}
		tiempoEstimado.setParent(item);
		
		//tipoAct
		Listcell tipoAct = new Listcell(trata.getTipoActuacion());
		tipoAct.setParent(item);
		
		//Operacion
		Listcell tipoReserva = new Listcell();
		if(trata.getTipoReserva().equals(new Integer("1"))){
			String tipo = "Sin Reserva";
			tipoReserva = new Listcell(tipo);
			tipoReserva.setTooltiptext(tipo);
		}
		if(trata.getTipoReserva().equals(new Integer("2"))){
			tipoReserva = new Listcell("Parcial");
		}
		if(trata.getTipoReserva().equals(new Integer("3"))){
			tipoReserva = new Listcell("Total");
		}
		tipoReserva.setParent(item);
		
		//estado
		Listcell operacion = new Listcell(trata.getTipoOperacion());
		operacion.setTooltiptext(trata.getTipoOperacion());
		operacion.setParent(item);
	}

	public void setComposer(HistorialTrataComposer composer) {
		this.composer = composer;
	}

	public HistorialTrataComposer getComposer() {
		return composer;
	}
}


