package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.Reparticion;

@Entity
@Table(name = "TE_TIPO_OPER_ORGANISMO")
public class TipoOperacionOrganismo implements Serializable {

	private static final long serialVersionUID = 5983624031968297331L;

	@EmbeddedId
	private TipoOperacionOrganismoPK pk;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_OPERACION", insertable = false, updatable = false)
	private TipoOperacion tipoOperacion;

	@ManyToOne
	@JoinColumn(name = "ID_ORGANISMO", insertable = false, updatable = false)
	private Reparticion reparticion;

	public TipoOperacionOrganismoPK getPk() {
		return pk;
	}

	public void setPk(TipoOperacionOrganismoPK pk) {
		this.pk = pk;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Reparticion getReparticion() {
		return reparticion;
	}

	public void setReparticion(Reparticion reparticion) {
		this.reparticion = reparticion;
	}

}
