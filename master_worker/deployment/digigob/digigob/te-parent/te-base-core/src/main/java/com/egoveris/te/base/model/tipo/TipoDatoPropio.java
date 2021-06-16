
package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPOS_DATOS_PROPIOS")
public class TipoDatoPropio implements Serializable {

	private static final long serialVersionUID = 7358136192913860265L;
	
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="abreviacion")
	private String abreviacion;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAbreviacion() {
		return abreviacion;
	}
	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}
}
