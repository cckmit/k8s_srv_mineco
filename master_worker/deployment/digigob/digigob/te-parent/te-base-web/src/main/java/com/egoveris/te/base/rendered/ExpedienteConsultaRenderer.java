package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IExpediente;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Space;


public class ExpedienteConsultaRenderer implements ListitemRenderer {
	private ConstantesDB constantesDB;

	@Override
	public void render(Listitem item, Object data, int arg1) throws Exception {
		IExpediente expediente = (IExpediente) data;
		constantesDB = (ConstantesDB) SpringUtil.getBean(ConstantesServicios.CONSTANTESDB);

		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String date = " ";
		
		if (expediente.getFechaCreacion() != null) {
			date = dateformat.format(expediente.getFechaCreacion());
		}

		item.appendChild(new Listcell(expediente.getCodigoCaratula()));
		item.appendChild(new Listcell(date));

		Hbox hbox = new Hbox();
		hbox.setAlign("center");

		Space separator = new Space();
		separator.setSpacing("10px");
		separator.setBar(true);
		Listcell listcell = new Listcell();

		if (expediente instanceof ExpedienteElectronicoDTO) {
			item.appendChild(new Listcell(expediente.getUsuarioCreador()));
			if (!StringUtils.isEmpty(expediente.getSolicitudIniciadora().getMotivo())) {
				item.appendChild(new Listcell(expediente.getSolicitudIniciadora().getMotivo()));
			} else if (!StringUtils.isEmpty(expediente.getSolicitudIniciadora().getMotivoExterno())) {
				item.appendChild(new Listcell(expediente.getSolicitudIniciadora().getMotivoExterno()));
			} else {
				item.appendChild(new Listcell("SIN MOTIVO"));
			}

			// visualizar expediente
			Image img = new Image("/imagenes/ver_expediente.png");
			img.setTooltiptext("Ver expediente");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(img, "onClick=composer.onVerExpediente");

			Label label = new Label("Visualizar");
			label.setTooltiptext("Ver expediente");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(label, "onClick=composer.onVerExpediente");

			if (expediente.getEstado() != null && (expediente.getEstado().equals(ConstantesWeb.ESTADO_ARCHIVO)
					|| expediente.getEstado().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO))) {
				listcell.appendChild(img);
				listcell.appendChild(label);
				item.appendChild(listcell);
			} else {
				// tramitar expediente
				construirTramitar(item, hbox, separator, listcell, img, label, (ExpedienteElectronicoDTO) expediente);
			}
		} else {
			item.appendChild(new Listcell(""));
			item.appendChild(new Listcell(""));

			Image imgDetalle = new Image("/imagenes/egovInspect.gif");
			imgDetalle.setTooltiptext("Ver Detalle");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imgDetalle, "onClick=composer.onVerDetalleExpedienteTrack");

			Label labelVerDetalle = new Label("Detalle");
			labelVerDetalle.setTooltiptext("Ver Detalle");
			labelVerDetalle.setStyle("color: blue; text-decoration:underline;");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelVerDetalle,
					"onClick=composer.onVerDetalleExpedienteTrack");

			Label labelVerHistorial = new Label("Historial");
			labelVerHistorial.setTooltiptext("Ver Historial");
			labelVerHistorial.setStyle("color: blue; text-decoration:underline;");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelVerHistorial,
					"onClick=composer.onVerHistorialExpedienteTrack");

			Image imgHistorial = new Image("/imagenes/egovInspect.gif");
			imgHistorial.setTooltiptext("Ver Historial");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imgHistorial,
					"onClick=composer.onVerHistorialExpedienteTrack");

			hbox.appendChild(imgDetalle);
			hbox.appendChild(labelVerDetalle);
			hbox.appendChild(separator);
			hbox.appendChild(imgHistorial);
			hbox.appendChild(labelVerHistorial);

			listcell.appendChild(hbox);
			item.appendChild(listcell);
		}
	}

	private void construirTramitar(Listitem item, Hbox hbox, Space separator, Listcell listcell, Image img, Label label,
			ExpedienteElectronicoDTO exp) {
		Image imgTramitar = new Image();
		Label labelTramitar = new Label();

		if (!exp.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
			imgTramitar.setSrc("/imagenes/btn-menu-hor-over.gif");
			imgTramitar.setTooltiptext("Tramitar Expediente");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imgTramitar, "onClick=composer.onVerPopUp");

			labelTramitar.setValue("Tramitar");
			labelTramitar.setTooltiptext("Tramitar Expediente");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelTramitar, "onClick=composer.onVerPopUp");

		} else if (exp.getSolicitarArchivo()) {
			if (constantesDB.getNombreEntorno().equalsIgnoreCase(ConstantesWeb.MODULO_EE)) {
				imgTramitar.setTooltiptext("Solicitar Archivo");
				imgTramitar.setSrc("/imagenes/arrowright16x16.png");
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imgTramitar, "onClick=composer.onSolicitarArchivo");

				labelTramitar.setValue("Solicitar Archivo");
				labelTramitar.setTooltiptext("Solicitar Archivo");
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(labelTramitar, "onClick=composer.onSolicitarArchivo");
			}
		}

		// agregacion al hbox
		hbox.appendChild(img);
		hbox.appendChild(label);

		if (constantesDB.getNombreEntorno().toUpperCase().equals(ConstantesWeb.MODULO_EE.toUpperCase())) {
			hbox.appendChild(separator);
		}

		hbox.appendChild(imgTramitar);
		hbox.appendChild(labelTramitar);

		listcell.appendChild(hbox);
		item.appendChild(listcell);
	}
}
