package com.egoveris.ffdd.render.zk.constr;

import com.egoveris.ffdd.render.model.InputComponent;

public interface Condition {
	
	public boolean validar(InputComponent comp, Object value);
}
