package com.egoveris.ffdd.render.zk.constr;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.SimpleConstraint;

public class ExtendedConstraint extends SimpleConstraint {

	private static final long serialVersionUID = -4132682662291178685L;

	private List<Constraint> extraConstraints = new ArrayList<Constraint>();

	public ExtendedConstraint(int flags) {
		super(flags);
	}

	public ExtendedConstraint(String constraint) {
		super(constraint);
	}

	public ExtendedConstraint(int flags, String errmsg) {
		super(flags, errmsg);
	}

	public ExtendedConstraint(String regex, String errmsg) {
		super(regex, errmsg);
	}

	public ExtendedConstraint(int flags, String regex, String errmsg) {
		super(flags, regex, errmsg);
	}


	public List<Constraint> getExtraConstraints() {
		return extraConstraints;
	}


	public void setFlags(int flags) {
		_flags = flags;
	}

	@Override
	public String getClientConstraint() {
		if (!getExtraConstraints().isEmpty()) {
			return null;
		} else {
			return super.getClientConstraint();
		}
	}

	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		super.validate(comp, value);
		if (!getExtraConstraints().isEmpty()) {
			for (Constraint constraint : getExtraConstraints()) {
				constraint.validate(comp, value);	
			}
		}
	}
}
