package com.egoveris.deo.web.satra.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;

import com.egoveris.deo.model.model.TipoDocumentoReducidoDTO;

public class ABMTipoDocumentoRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {
		TipoDocumentoReducidoDTO tipo = (TipoDocumentoReducidoDTO) data;

		new Listcell(tipo.getNombre()).setParent(item);
		new Listcell(tipo.getAcronimo()).setParent(item);
		Checkbox checkEspecial = new Checkbox();
		Checkbox checkFirmaConjunta = new Checkbox();
		Checkbox checkFirmaExterna = new Checkbox();
		Checkbox checkAvisoFirma = new Checkbox();
		Checkbox checkEmbeberArchivos = new Checkbox();
		Checkbox checkReservado = new Checkbox();
		Checkbox checkResultado = new Checkbox();
		
		checkEspecial.setChecked(tipo.getEsEspecial());
		checkEspecial.setDisabled(true);
		checkFirmaConjunta.setChecked(tipo.getEsFirmaConjunta());
		checkFirmaConjunta.setDisabled(true);
		checkFirmaExterna.setChecked(tipo.getEsFirmaExterna());
		checkFirmaExterna.setDisabled(true);
		checkAvisoFirma.setChecked(tipo.getTieneAviso());
		checkAvisoFirma.setDisabled(true);
		checkEmbeberArchivos.setChecked(tipo.getPermiteEmbebidos());
		checkEmbeberArchivos.setDisabled(true);
		checkReservado.setChecked(tipo.getEsConfidencial());
		checkReservado.setDisabled(true);
		checkResultado.setChecked(tipo.getResultado());
		checkResultado.setDisabled(true);
		
		Listcell celdaProduccion = new Listcell();
		Integer tipoProduccion;
		tipoProduccion = tipo.getTipoProduccion();

		if (tipoProduccion == 1) {
			Image produccion = new Image("/imagenes/IconoLibre.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.libre"));
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
		}else if(tipoProduccion == 4){
			Image produccion = new Image("/imagenes/IconoImportadoTemplate.png");
			produccion.setTooltiptext(Labels.getLabel("gedo.nuevoDocumento.radiogroup.radio.produccion.template"));
			celdaProduccion.appendChild(produccion);
			item.appendChild(celdaProduccion);
		}

		Listcell celdaEspecial = new Listcell();
		celdaEspecial.appendChild(checkEspecial);
		item.appendChild(celdaEspecial);
		
		Listcell celdaConjunta = new Listcell();
		celdaConjunta.appendChild(checkFirmaConjunta);
		item.appendChild(celdaConjunta);

		Listcell celdaExterna = new Listcell();
		celdaExterna.appendChild(checkFirmaExterna);
		item.appendChild(celdaExterna);

		Listcell celdaAvisoFirma = new Listcell();
		celdaAvisoFirma.appendChild(checkAvisoFirma);
		item.appendChild(celdaAvisoFirma);

		Listcell celdaEmbeberArchivo = new Listcell();
		celdaEmbeberArchivo.appendChild(checkEmbeberArchivos);
		item.appendChild(celdaEmbeberArchivo);

		Listcell celdaReservado = new Listcell();
		celdaReservado.appendChild(checkReservado);
		item.appendChild(celdaReservado);

		Listcell celdaResultado = new Listcell();
		celdaResultado.appendChild(checkResultado);
    item.appendChild(celdaResultado);
    
		Listcell celdaHabilitado = new Listcell();
		
		if (tipo.getEstaHabilitado()) {
			Image habilitado = new Image("/imagenes/checked.png");
			celdaHabilitado.appendChild(habilitado);
			org.zkoss.zk.ui.sys.ComponentsCtrl
			.applyForward(habilitado,
					"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onClick$unChecked");
			item.appendChild(celdaHabilitado);

		} else {
			Image noHabilitado = new Image("/imagenes/unchecked.png");
			celdaHabilitado.appendChild(noHabilitado);
			org.zkoss.zk.ui.sys.ComponentsCtrl
			.applyForward(noHabilitado,
					"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onClick$checked");
			item.appendChild(celdaHabilitado);
		}

		new Listcell(tipo.getCodigoTipoDocumentoSade()).setParent(item);

		Listcell celdaAcciones = new Listcell();
		Hbox hbox = new Hbox();
		Image datosDocumento = new Image("/imagenes/page_text.png");
		datosDocumento
				.setTooltiptext(Labels
						.getLabel("gedo.abmTipoDocumento.hbox.image.tooltiptext.detalleDelTipoDeDocumento"));
		Label labelDatos = new Label();
		labelDatos.setValue(Labels
				.getLabel("gedo.abmTipoDocumento.hbox.label.datosDocumentos"));
		labelDatos
				.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
		hbox.appendChild(datosDocumento);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(datosDocumento,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onDetalle");
		hbox.appendChild(labelDatos);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(labelDatos,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onDetalle");
		celdaAcciones.appendChild(hbox);

		Separator separator1 = new Separator("horizontal");
		separator1.setWidth("2px");
		separator1.setParent(hbox);

		Image reparticionesHabilitadas = new Image("/imagenes/building.png");
		reparticionesHabilitadas
				.setTooltiptext(Labels
						.getLabel("gedo.abmTipoDocumento.hbox.image.tooltiptext.editarReparticionesDelDocumento"));
		Label labelreparticiones = new Label();
		labelreparticiones
				.setValue(Labels
						.getLabel("gedo.abmTipoDocumento.hbox.image.label.reparticionesHabilitadas"));
		labelreparticiones
				.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
		hbox.appendChild(reparticionesHabilitadas);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(
						reparticionesHabilitadas,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onReparticionesHabilitadas");
		hbox.appendChild(labelreparticiones);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(
						labelreparticiones,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onReparticionesHabilitadas");
		celdaAcciones.appendChild(hbox);

		Image eliminar = new Image("/imagenes/decline.png");
		eliminar.setTooltiptext(Labels
				.getLabel("gedo.abmTipoDocumento.hbox.image.tooltiptext.eliminarTipoDeDocumento"));
		Label labelEliminar = new Label();
		labelEliminar.setValue(Labels
				.getLabel("gedo.abmTipoDocumento.label.eliminar"));
		labelEliminar
				.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
		hbox.appendChild(eliminar);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(eliminar,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onEliminar");
		hbox.appendChild(labelEliminar);
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(labelEliminar,
						"onClick=abmTipoDocumentoWindow$ABMTipoDocumentoComposer.onEliminar");
		celdaAcciones.appendChild(hbox);
		item.appendChild(celdaAcciones);
	}

}

