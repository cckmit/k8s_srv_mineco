package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Vbox;

public class HistorialTipoDocumentoItemRender implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {
		TipoDocumentoDTO tipo = (TipoDocumentoDTO) data;
		item.setHeight("30px");
		new Listcell(tipo.getVersion()).setParent(item);
		new Listcell(tipo.getUsuarioCreador() != null ? tipo.getUsuarioCreador() : "-").setParent(item); 
		if (null != tipo.getFechaCreacion()) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			new Listcell(df.format(tipo.getFechaCreacion())).setParent(item);
		} else {
			new Listcell("-").setParent(item);
		}
		Label descripcion = new Label();
		descripcion.setValue(tipo.getDescripcion().length() > 30 ? tipo.getDescripcion().substring(0, 30) + "..."
				: tipo.getDescripcion());
		descripcion.setTooltiptext(tipo.getDescripcion());
		Listcell celdaDescripcion = new Listcell();
		descripcion.setParent(celdaDescripcion);
		celdaDescripcion.setParent(item);

		Listcell celdaProduccion = new Listcell();
		Integer tipoProduccion;
		tipoProduccion = tipo.getTipoProduccion();

		if (tipoProduccion == 1) {
			Image produccion = new Image("/imagenes/IconoLibre.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.nuevoDocumento.radiogroup.radio.produccion.libre"));
			celdaProduccion.appendChild(produccion);
			item.appendChild(celdaProduccion);
		} else if (tipoProduccion == 2) {
			Image produccion = new Image("/imagenes/IconoImportado.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.nuevoDocumento.radiogroup.radio.produccion.importado"));
			celdaProduccion.appendChild(produccion);
			item.appendChild(celdaProduccion);
		} else if (tipoProduccion == 3) {
			Image produccion = new Image("/imagenes/IconoTemplate.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.nuevoDocumento.radiogroup.radio.produccion.template"));
			celdaProduccion.appendChild(produccion);
			item.appendChild(celdaProduccion);
		} else if (tipoProduccion == 4) {
			Image produccion = new Image("/imagenes/IconoImportadoTemplate.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.nuevoDocumento.radiogroup.radio.produccion.template"));
			celdaProduccion.appendChild(produccion);
			item.appendChild(celdaProduccion);
		}
		String generacion;
		if (tipo.getEsAutomatica() && tipo.getEsManual()) {
			generacion = "Ambas";
		} else if (tipo.getEsAutomatica()) {
			generacion = "Automática";
		} else {
			generacion = "Manual";
		}
		new Listcell(generacion).setParent(item);
		String especiales = "";
		if (tipo.getEsEspecial()) {
			especiales += "Especial, ";
		}
		if (tipo.getTieneToken()) {
			especiales += "Firma con Token, ";
		}
		if (tipo.getEsDobleFactor()) {
			especiales += "Firma con Doble Factor, ";
		}
		if (tipo.getEsFirmaExterna()) {
			especiales += "Firma Externa, ";
		}
		if (tipo.getEsFirmaConjunta()) {
			especiales += "Firma Conjunta, ";
		}
		if (tipo.getTieneAviso()) {
			especiales += "Tiene aviso de Firma, ";
		}
		if (tipo.getPermiteEmbebidos()) {
			especiales += "Permite Embebidos, ";
		}
		if (tipo.getEsComunicable()) {
			especiales += "Comunicable, ";
		}
		if (tipo.getEsConfidencial()) {
			especiales += "Reservado, ";
		}
		if (tipo.getEsNotificable()) {
			especiales += "Notificable, ";
		}
		if (tipo.getEsOculto()) {
			especiales += "Oculto";
		}
		if (especiales.endsWith(", ")) {
			especiales = StringUtils.removeEnd(especiales, ", ");
		}
		new Listcell(especiales).setParent(item);
		new Listcell(tipo.getCodigoTipoDocumentoSade()).setParent(item);
		new Listcell(tipo.getFamilia().getNombre()).setParent(item);
		Integer tamanio = null;
		if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			tamanio = tipo.getSizeImportado();
		}
		if (tamanio != null) {
			new Listcell(String.valueOf(tamanio / Constantes.FACTOR_CONVERSION)).setParent(item);
		} else {
			new Listcell("-").setParent(item);
		}
		Hbox hboxTemplate = new Hbox();
		Hbox hboxEmbebidos = new Hbox();
		hboxTemplate.setWidth("82px");
		hboxEmbebidos.setWidth("82px");
		boolean tieneEmbebido = true;
		boolean tieneTemplate = true;
		if (tipo.getPermiteEmbebidos()) {
			Image embebidos = new Image("/imagenes/template.png");
			Label labelEmbebidos = new Label();
			embebidos.setTooltiptext(Labels.getLabel("gedo.historial.documento.ver.embebidos.tooltip"));
			labelEmbebidos.setValue(Labels.getLabel("gedo.historial.documento.ver.embebidos"));
			hboxEmbebidos.appendChild(embebidos);
			hboxEmbebidos.appendChild(labelEmbebidos);
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(embebidos,
					"onClick=historialDocumentoWindow$HistorialTipoDocumentoComposer.onVerEmbebidos");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelEmbebidos,
					"onClick=historialDocumentoWindow$HistorialTipoDocumentoComposer.onVerEmbebidos");
		} else {
			tieneEmbebido = false;
		}

		if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
				|| tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			Image template = new Image("/imagenes/template.png");
			Label labelTemplate = new Label();
			template.setTooltiptext(Labels.getLabel("gedo.historial.documento.ver.template.tooltip"));
			labelTemplate.setValue(Labels.getLabel("gedo.historial.documento.ver.template"));
			hboxTemplate.appendChild(template);
			hboxTemplate.appendChild(labelTemplate);
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(template,
					"onClick=historialDocumentoWindow$HistorialTipoDocumentoComposer.onVerTemplate");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelTemplate,
					"onClick=historialDocumentoWindow$HistorialTipoDocumentoComposer.onVerTemplate");
		} else {
			tieneTemplate = false;
		}

		Listcell celdaAcciones = new Listcell();
		if (!tieneEmbebido && !tieneTemplate) {
			new Listcell("-").setParent(item);
		} else {
			if (tieneEmbebido && !tieneTemplate) {
				celdaAcciones.appendChild(hboxEmbebidos);
				celdaAcciones.setParent(item);
			} else if (!tieneEmbebido && tieneTemplate) {
				celdaAcciones.appendChild(hboxTemplate);
				celdaAcciones.setParent(item);
			} else {
				Vbox vbox = new Vbox();
				vbox.appendChild(hboxTemplate);
				vbox.appendChild(hboxEmbebidos);

				celdaAcciones.appendChild(vbox);
				celdaAcciones.setParent(item);
			}
		}

	}

}
