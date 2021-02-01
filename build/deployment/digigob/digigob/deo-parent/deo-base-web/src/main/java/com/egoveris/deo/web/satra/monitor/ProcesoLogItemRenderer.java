package com.egoveris.deo.web.satra.monitor;


import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.deo.model.model.ProcesoLogDTO;

public class ProcesoLogItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {
	
		ProcesoLogDTO procesoLog = (ProcesoLogDTO) data;
		item.setValue(data);
		addListcell(item, procesoLog.getId().toString());
		addListcell(item, procesoLog.getProceso());
		addListcell(item, procesoLog.getWorkflowId());
		addListcell(item, procesoLog.getSistemaOrigen());
		addListcell(item, procesoLog.getEstado());
		addListcell(item, procesoLog.getDescripcion());
		addListcell(item, procesoLog.getFechaCreacion().toString());
	}
	private void addListcell (Listitem listitem, String value) {
        Listcell lc = new Listcell ();
        Label lb = new Label(value);
        lb.setParent(lc);
        lc.setParent(listitem);
    }

}
