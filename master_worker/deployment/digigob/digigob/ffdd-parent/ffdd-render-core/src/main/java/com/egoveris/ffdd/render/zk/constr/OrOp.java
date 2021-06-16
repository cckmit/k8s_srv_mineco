package com.egoveris.ffdd.render.zk.constr;

import com.egoveris.ffdd.render.model.InputComponent;

public class OrOp extends Operator implements Condition {

	public OrOp(Condition condA, Condition condB) {
		super(condA, condB);
	}

	@Override
	public boolean validar(InputComponent comp, Object value) {
		return condA.validar(comp, value) || condB.validar(comp, value);
	}
}
