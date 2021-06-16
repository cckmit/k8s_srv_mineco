package com.egoveris.ffdd.render.zk.form;

import com.egoveris.ffdd.render.model.InputComponent;

public interface Condition {
	
	public boolean validar(InputComponent comp, Object value);

	public boolean validar();
}
