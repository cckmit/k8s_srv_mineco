package com.egoveris.ffdd.render.zk.comp.ext;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Constraint;

public class CheckboxExt extends Checkbox implements ConstrInputComponent {

	private static final long serialVersionUID = -3957958607146719943L;

	private Integer idComponentForm;
	private Constraint constraint;
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
	public Object getRawValue() {
		return this.isChecked();
	}

	@Override
	public void setRawValue(Object obj) {
		Boolean valor;
		if(obj instanceof String){
			String valorS = (String)obj;
			valor = new Boolean(valorS);
		}else {
			valor = (Boolean)obj;
		}
		this.setChecked( obj != null && valor);
	}

	@Override
	public boolean isReadonly() {
		return isDisabled();
	}

	@Override
	public void setReadonly(boolean disable) {
		setDisabled(disable);
	}

	@Override
	public Constraint getConstraint() {
		return this.constraint;
	}

	@Override
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
		
	}

	@Override
	public MultiConstrData getMultiConstrData() {
		return this.multiConstrData;
	}

	@Override
	public void setMultiConstrData(MultiConstrData multiConstrStruct) {
		this.multiConstrData = multiConstrStruct;
	}

	@Override
	public String getText() {
	 if(this.constraint != null){
	   this.constraint.validate(this, this.isChecked());
	 }
		return String.valueOf(this.isChecked());
	}


	@Override
	public void clearErrorMessage(boolean bool) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}
}
