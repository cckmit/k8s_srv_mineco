package com.egoveris.ffdd.render.zk.comp.ext;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Longbox;

public class LongboxExt extends Longbox implements ConstrInputComponent {

	private static final long serialVersionUID = -40865293572586743L;
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
