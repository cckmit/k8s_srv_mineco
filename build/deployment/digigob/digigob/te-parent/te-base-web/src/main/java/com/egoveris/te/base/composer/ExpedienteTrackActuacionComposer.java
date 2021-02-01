package com.egoveris.te.base.composer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;

abstract class ExpedienteTrackActuacionComposer extends EEGenericForwardComposer {
	
	protected static final long serialVersionUID = -3323971693138968227L;

	@Autowired
	Textbox ex;
	@Autowired
	Longbox anio;
	@Autowired
	Longbox numero;
	@Autowired
	Textbox codigoReparticion;
	@Autowired
	Textbox codigoRepUsuario;
	@Autowired
	Textbox sec;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

}