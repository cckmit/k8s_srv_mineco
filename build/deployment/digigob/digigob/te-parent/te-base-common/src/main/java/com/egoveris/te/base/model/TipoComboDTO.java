
package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class TipoComboDTO  implements Serializable
{	
	private static final long serialVersionUID = 7358136192913860115L;

	private Long id;
	private int grupo;
	private String valorOpcion;
	private Date fechaBaja;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValorOpcion() {
		return valorOpcion;
	}

	public void setValorOpcion(String valorOpcion) {
		this.valorOpcion = valorOpcion;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
}
