package com.egoveris.ffdd.render.zk.comp.ext;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;

public class IntboxExt extends Intbox implements ConstrInputComponent {

	private static final long serialVersionUID = -3209445303425340450L;

	private Integer idComponentForm;
	private MultiConstrData multiConstrData;

	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	@Override
	public void setIdComponentForm(Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}
	
	@Override
	public MultiConstrData getMultiConstrData() {
		return multiConstrData;
		
	}

	@Override
	public void setMultiConstrData(MultiConstrData multiConstrStruct) {
		this.multiConstrData = multiConstrStruct;
	}

	@Override
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}
	
}
