package com.egoveris.ffdd.render.zk.constr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class TextConstraint  implements Constraint{

	private String[] splitData;
	
	public TextConstraint(String[] splitData) {
		this.splitData = splitData;
	}
	
	public TextConstraint() {}
	
	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		if (splitData == null) {
			throw new WrongValueException(comp, "valor null");
		}
		
		Pattern pattern = Pattern.compile(splitData[0]);
		Matcher matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			throw new WrongValueException(comp, splitData[1]);
		}
		
	}

}
