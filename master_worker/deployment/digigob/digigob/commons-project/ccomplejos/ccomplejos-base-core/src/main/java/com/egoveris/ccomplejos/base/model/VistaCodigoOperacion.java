package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_codigooperacion")
public class VistaCodigoOperacion extends AbstractViewCComplejoJPA {

	@Column (name = "COD_OPERACION")
	protected String codigoOperacion;

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}


}
