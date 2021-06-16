package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;

public class NotificableTadRenderer implements ListitemRenderer {
	@Autowired
	protected TipoDocumentoService tipoDocumentoService;
	String loggedUsername = new String("");

	public void render(Listitem item, Object data, int arg1) throws Exception {

		DocumentoDTO documento = (DocumentoDTO) data;
		Listcell currentCell;
		item.setHflex("min");

		Listbox lista2 = (Listbox) item.getParent();
		int cantDocumentos = lista2.getItemCount();

		int numFolio = cantDocumentos - (item.getIndex());
		String numeroFolio = Integer.toString(numFolio);

		TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService.obtenerTipoDocumento(documento.getTipoDocAcronimo());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		String fechaAsociacion = sdf.format(documento.getFechaAsociacion());
		String fechaCreacion = sdf.format(documento.getFechaCreacion());

		Listcell folio = new Listcell(numeroFolio);
		folio.setHflex("min");
		folio.setParent(item);
		new Listcell(tipoDeDocumento.getDescripcionTipoDocumentoSade()).setParent(item);
		new Listcell(documento.getNumeroSade()).setParent(item);
		new Listcell(documento.getMotivo()).setParent(item);
		new Listcell(fechaAsociacion).setParent(item);
		new Listcell(fechaCreacion).setParent(item);

		final Checkbox notificableCheckbox = new Checkbox();
		notificableCheckbox.setId(documento.getNumeroSade());
		notificableCheckbox.setChecked(documento.isSubsanado());
		if (documento.isSubsanado()) {
			notificableCheckbox.setDisabled(true);
		} else {
			notificableCheckbox.setDisabled(false);
		}

		Listcell notificable = new Listcell();
		notificableCheckbox.setParent(notificable);
		notificable.setParent(item);

		currentCell = new Listcell();
		currentCell.setParent(item);

		Hbox hbox = new Hbox();

		Image documentoImage;
		Image documentoNoImage;
		if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
				&& documento.getUsuarioSubsanador().equals(loggedUsername))) {
			documentoImage = new Image("/imagenes/page_text.png");
			documentoNoImage = new Image("/imagenes/DocumentoNoDisponible.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoNoImage, "onClick=onNoVisualizarDocumento");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoImage, "onClick=onVisualizarDocumento");
		} else {
			documentoImage = new Image("/imagenes/page_text_deshabilitado.png");
			documentoNoImage = new Image("/imagenes/DocumentoNoDisponible_deshabilitado.png");
		}

		documentoImage.setTooltiptext("Visualizar documento.");
		documentoNoImage.setTooltiptext("Documento no disponible.");
		Separator separarDocumento = new Separator();
		separarDocumento.setParent(hbox);

		documentoImage.setParent(hbox);

		Image visualizarImage;
		if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
				&& documento.getUsuarioSubsanador().equals(loggedUsername))) {
			visualizarImage = new Image("/imagenes/download_documento.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage, "onClick=onDescargarDocumento");
		} else {
			visualizarImage = new Image("/imagenes/download_documento_deshabilitado.png");
		}

		visualizarImage.setTooltiptext("Descargar el documento a su disco local.");
		visualizarImage.setParent(hbox);

		// Popup
		Popup popup = new Popup();
		popup.setId("id_" + documento.getNumeroSade());

		Listbox lista = new Listbox();
		lista.setWidth("400px");
		Listhead listHead = new Listhead();

		Listheader listheader = new Listheader("Usuario Generador");
		Listheader listheader1 = new Listheader("Número Especial");
		Listheader listheader2 = new Listheader("Usuario Subsanador");
		listheader.setParent(listHead);
		listheader1.setParent(listHead);
		listheader2.setParent(listHead);
		listHead.setParent(lista);
		Listitem itemPopup = new Listitem();
		Listcell celdaUsuarioGenerador = new Listcell(documento.getNombreUsuarioGenerador());
		celdaUsuarioGenerador.setParent(itemPopup);
		Listcell celdaNumeroEspecial = new Listcell(documento.getNumeroEspecial());
		celdaNumeroEspecial.setParent(itemPopup);
		Listcell celdaUsuarioLimitador = new Listcell(documento.getUsuarioSubsanador());
		celdaUsuarioLimitador.setParent(itemPopup);
		itemPopup.setParent(lista);
		lista.setParent(popup);
		popup.setParent(hbox);

		Image imagen = new Image("/imagenes/edit-find.png");
		imagen.setPopup(popup.getId());
		imagen.setTooltiptext("Mas Datos");
		Separator separar = new Separator();
		separar.setParent(hbox);
		imagen.setParent(hbox);

		hbox.setParent(currentCell);
	}
}
