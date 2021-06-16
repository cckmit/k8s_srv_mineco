package com.egoveris.ffdd.render.zk.constr;

public abstract class Operator {

	protected Condition condA;
	protected Condition condB;

	protected Operator(Condition condA, Condition condB) {
		this.condA = condA;
		this.condB = condB;
	}
}
