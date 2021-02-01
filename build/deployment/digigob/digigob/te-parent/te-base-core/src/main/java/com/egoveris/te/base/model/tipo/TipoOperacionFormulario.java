package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "TE_TIPO_OPER_FORM")
@IdClass(TipoOperacionFormularioPK.class)
public class TipoOperacionFormulario implements Serializable {

	private static final long serialVersionUID = 5983684031998297331L;
	
	@Id
	@Column(name = "ID_FORMULARIO")
	private Long idFormulario;
	
	@Id
	@Column(name = "ID_TIPO_OPERACION")
	private Long idTipoOperacion;

	public Long getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(Long idFormulario) {
		this.idFormulario = idFormulario;
	}

	public Long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(Long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

}
