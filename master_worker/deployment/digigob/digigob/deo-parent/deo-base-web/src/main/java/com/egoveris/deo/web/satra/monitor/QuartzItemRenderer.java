package com.egoveris.deo.web.satra.monitor;

import com.egoveris.deo.model.model.SolicitudQuartzDTO;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class QuartzItemRenderer implements ListitemRenderer {

	public void render(Listitem listitem, Object data, int arg2) throws Exception {

		SolicitudQuartzDTO solicitud = (SolicitudQuartzDTO) data;

        listitem.setValue(data);

        addListcell(listitem, solicitud.getNombreJob());
        addListcell(listitem, solicitud.getNombreTrigger());
        addListcell(listitem, solicitud.getEstado());
        
        if (solicitud.getNextFireTime() != null){
        	addListcell(listitem, solicitud.getNextFireTime().toString());
        }else{
        	addListcell(listitem, null);
        }

        addListcell(listitem, solicitud.getGrupo());
        addListcell(listitem, solicitud.getProximoReintento());
        
        if (solicitud.getCronExpression() != null){
        	addListcell(listitem, solicitud.getCronExpression().toString());
        	listitem.setCheckable(false);
        	listitem.setDisabled(false);
        }else{
        	addListcell(listitem, null);
        }
    }

	private void addListcell (Listitem listitem, String value) {
        Listcell lc = new Listcell ();
        Label lb = new Label(value);
        lb.setParent(lc);
        lc.setParent(listitem);
    }
}
