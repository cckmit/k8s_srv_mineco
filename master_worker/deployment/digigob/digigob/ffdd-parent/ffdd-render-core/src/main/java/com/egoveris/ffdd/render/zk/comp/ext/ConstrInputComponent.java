package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.render.model.InputComponent;

import org.zkoss.zul.Constraint;

public interface ConstrInputComponent extends InputComponent{

	public Constraint getConstraint();

	public void setConstraint(Constraint constraint);
	
	/**
	 * Obtiene objeto con todas la informacion para construir la constraint
	 */
	public MultiConstrData getMultiConstrData();
	
	/**
	 * Setea objeto con todas la informacion para construir la constraint
	 */
	public void setMultiConstrData(MultiConstrData multiConstrStruct);
	
}
