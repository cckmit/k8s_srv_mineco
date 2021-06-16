package com.egoveris.deo.web.satra.monitor;

import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;

public class ReprogramarSolicitudComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private Datebox fechaLanzamiento;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public void onClick$reprogramar() throws WrongValueException, ParseException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fecha", fechaLanzamiento.getValue().getTime());
		
		this.closeAndNotifyAssociatedWindow(map);
	}
}
