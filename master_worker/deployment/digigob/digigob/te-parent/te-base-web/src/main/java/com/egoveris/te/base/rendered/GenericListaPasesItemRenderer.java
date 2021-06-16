package com.egoveris.te.base.rendered;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.tarjetausuario.TarjetaDatosUsuario;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.UtilsDate;

public class GenericListaPasesItemRenderer extends GenericListitemRenderer {

	private static final String MGEYA = "MGEYA";

	@Override
	public void render(final Listitem item, final Object data, final int arg1) throws Exception {

		final HistorialOperacionDTO pase = (HistorialOperacionDTO) data;
		/**
		 * Se obtiene la cantidad total de documentos a listar para poder listar
		 * la columna "Orden" con numeración descendente.
		 */
		final Listbox lista2 = (Listbox) item.getParent();
		final int cantDocumentos = lista2.getItemCount();
		/**
		 * A la cantidad de documentos total a listar se resta el indice de cada
		 * item para que quede de manera descendente.
		 */
		final int numFolio = cantDocumentos - item.getIndex();
		final String numeroFolio = Integer.toString(numFolio);
		final Listcell folio = new Listcell(numeroFolio);
		folio.setHflex("min");
		folio.setParent(item);

		// Fecha Operacion
		final Listcell fechaOperacion = new Listcell(UtilsDate.formatearFechaHora(pase.getFechaOperacion()));
		fechaOperacion.setParent(item);

		// Usuario
		final Listcell usuario = new Listcell(pase.getUsuario());

		usuario.setParent(item);
		if (pase.getUsuario() != null) {
			TarjetaDatosUsuario.setPopup(usuario, pase.getUsuario());
		}

		// Destinatario
		final Listcell destinatario = new Listcell();

		if (pase.getDestinatario().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
				|| pase.getDestinatario().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)
				|| pase.getDestinatario().equals(ConstantesWeb.ESTADO_ARCHIVO)) {
			destinatario.setLabel(MGEYA);
		} else {
			destinatario.setLabel(pase.getDestinatario());
			if (pase.getDestinatario() != null) {
				TarjetaDatosUsuario.setPopup(destinatario, pase.getDestinatario());
			}
		}

		destinatario.setParent(item);

		// Estado
		final Listcell estado = new Listcell(pase.getEstado());
		estado.setParent(item);

		// Resultado
		final Listcell resultado = new Listcell();
		String lblResultado = pase.getResultado() != null ? pase.getResultado() : "-";
		resultado.setLabel(lblResultado);
		resultado.setParent(item);

		// Motivo
		final String motivo = pase.getMotivo();
		final Listcell listCellMotivo = new Listcell();
		String motivoParse = "SIN MOTIVO";
		if (motivo != null) {
			motivoParse = motivoParseado(motivo);
			listCellMotivo.setTooltiptext(motivo);
		}
		listCellMotivo.setLabel(motivoParse);
		listCellMotivo.setParent(item);

//		// GeoInfo
//		final Listcell listGeoInfo = new Listcell();
//		if (!StringUtils.isEmpty(pase.getLongitude()) && !StringUtils.isEmpty(pase.getLongitude())) {
//			Hbox hbox = new Hbox();
//			Image visualizarImage;
//			visualizarImage = new Image("/imagenes/egovInspect.gif");
//			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage, "onClick=onVerMapa");
//
//			visualizarImage.setTooltiptext("Ver la ubicación donde se ejecutó la tarea.");
//			visualizarImage.setParent(hbox);
//			hbox.setParent(listGeoInfo);
//		}
//		listGeoInfo.setParent(item);
	}

	private String motivoParseado(final String motivo) {
		final int cantidadCaracteres = 20;
		String substringMotivo;
		if (motivo.length() > cantidadCaracteres) {
			substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
		} else {
			substringMotivo = motivo.substring(0, motivo.length());
		}
		return substringMotivo;
	}

}