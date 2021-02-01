package com.egoveris.vucfront.base.interceptor;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class PersonaInterceptor implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 677508387395267688L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		System.out.println(arg0);
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		System.out.println(arg0);
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
