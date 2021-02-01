package com.egoveris.ffdd.render.zk.comp.ext;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Timebox;

import com.egoveris.ffdd.render.model.SelectableComponent;

public class TimeboxExt extends Timebox implements ConstrInputComponent, SelectableComponent{

	private static final long serialVersionUID = 4258659188446322065L;
	
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
	public void setMultiConstrData(MultiConstrData multiConstrData) {
		this.multiConstrData = multiConstrData;
	}

	@Override
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}
}
