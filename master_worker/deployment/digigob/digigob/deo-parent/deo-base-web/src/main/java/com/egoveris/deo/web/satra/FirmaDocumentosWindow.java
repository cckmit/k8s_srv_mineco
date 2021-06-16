package com.egoveris.deo.web.satra;

import com.egoveris.deo.model.model.FirmaEvent;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public final class FirmaDocumentosWindow extends AskBeforeCloseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108636307231376915L;

	@Override
	public void service(AuRequest request, boolean everError) {
		super.service(request, everError);
		if (request.getCommand().contains("onFirma")
				|| request.getCommand().equals("onAutoFirma")
				|| request.getCommand().equals("onAutoFirmaError")) {
			enviarNensajeFirma(request);
		
		}
		
		
	}

	private void enviarNensajeFirma(AuRequest request) {
		FirmaEvent eventFirma = new FirmaEvent();
		eventFirma.setEventName(request.getCommand());
		eventFirma.setData(request.getData());
		Event event = new Event(Events.ON_NOTIFY, this, eventFirma);
		Events.sendEvent(event);
	}
	


}
