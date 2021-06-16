package com.egoveris.ffdd.render.zk.constr;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComplexData;
import com.egoveris.ffdd.render.zk.comp.ext.BandboxExt;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class BandboxExtConstraint implements Constraint {

	private BandboxExt bandbox;
	private static final String BANDBOX_DESCRIPCION = "ComplexBandBox - Reparticiones - Descripcion";
	
	public BandboxExtConstraint(BandboxExt bandbox) {
		this.bandbox = bandbox;
	}
	
	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		String valueSt = value.toString().trim();
		if (!valueSt.isEmpty()) {
			try {
				if (!(this.validarLista(valueSt))) {
					this.bandbox.focus();
					throw new WrongValueException(this.bandbox, valueSt + " inválida");
				}
			} catch (DynFormException e) {
				throw new WrongValueException("error en la validacion del componente" + this.bandbox.getDataTypeName(),e);				
			}
		}
	}

	private boolean validarLista(String value) throws DynFormException {
		
		for (ComplexData data : this.bandbox.getListaDataCompleta()) {						
			if (this.bandbox.getTipoComponente().equals(BANDBOX_DESCRIPCION)) {
				if (data.getNombre().trim().equalsIgnoreCase(value)) {
					return true;
				}
			}else{
				if (data.getCodigo().trim().equalsIgnoreCase(value)) {
					return true;
				}
			}						
		}
		return false;
	}
}
