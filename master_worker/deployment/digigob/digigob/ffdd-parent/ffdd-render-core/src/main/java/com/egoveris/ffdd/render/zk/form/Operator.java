package com.egoveris.ffdd.render.zk.form;

public abstract class Operator implements Condition{

	protected Condition condA;
	protected Condition condB;
	
	public boolean validar() {
		return validar(null, null);
	}

	protected Operator(Condition condA, Condition condB) {
		this.condA = condA;
		this.condB = condB;
	}
}
