package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.DocumentoDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ConsultaCaratulaRender implements ListitemRenderer {

  public void render(Listitem item, Object data, int arg2) throws Exception {
    ComunicacionDTO co = (ComunicacionDTO) data;
    DocumentoDTO doc = co.getDocumento();

    new Listcell(co.getDocumento().getNumero()).setParent(item);
    new Listcell(doc.getTipo().getCodigoTipoDocumentoSade()).setParent(item);

    new Listcell().setParent(item);

    new Listcell(co.getNombreApellidoUsuario() + " (" + co.getUsuarioCreador() + ")")
        .setParent(item);
    new Listcell(co.getFechaCreacion().toString()).setParent(item);
    new Listcell(co.getDocumento().getMotivo()).setParent(item);

    Listcell celdaAcciones = new Listcell();
    Hbox hboxAcciones = new Hbox();
    Hbox hboxDetalle = new Hbox();
    Hbox hboxHistorial = new Hbox();
    Image detalleDocumento = new Image("/imagenes/edit-find.png");
    detalleDocumento.setTooltiptext(Labels.getLabel("ccoo.bandeja.detalle"));
    Image historialDocumento = new Image("/imagenes/edit-find.png");
    historialDocumento.setTooltiptext(Labels.getLabel(""));
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(hboxDetalle,
        "onClick=bandejaCoWindow$BandejaCoComposer.onExecuteDetailConsultaCaratula");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(hboxHistorial,
        "onClick=bandejaCoWindow$BandejaCoComposer.onExecuteHistorialSend");
    hboxDetalle.appendChild(detalleDocumento);
    hboxDetalle.appendChild(new Label(Labels.getLabel("gedo.conCoRecRender.label.detalle")));
    hboxHistorial.appendChild(historialDocumento);
    hboxHistorial.appendChild(new Label(Labels.getLabel("gedo.conCoRecRender.label.historial")));
    hboxAcciones.appendChild(hboxDetalle);
    hboxAcciones.appendChild(hboxHistorial);
    celdaAcciones.appendChild(hboxAcciones);
    item.appendChild(celdaAcciones);
  }
}
