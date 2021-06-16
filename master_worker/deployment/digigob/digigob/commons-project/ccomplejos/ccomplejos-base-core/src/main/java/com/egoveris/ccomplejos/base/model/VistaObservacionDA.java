package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_OBSERVACION_DA")
public class VistaObservacionDA extends AbstractCComplejoJPA {


	@Column(name = "VALOR_OBSERVACION")
	protected String valorObservacion;
	@Column(name = "CODIGO_OBSERVACION")
	protected String codigoObservacion;
	@Column(name = "GLOSA_OBSERVACION")
	protected String glosaObservacion;
	@ManyToOne
	@JoinColumn(name = "ID_OPERACION", referencedColumnName = "id", insertable = false, updatable = false)
	VistaItemDA vistaOb;

	/**
	 * @return the idOperacion
	 */
	public Integer getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion
	 *            the idOperacion to set
	 */
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the valorObservacion
	 */
	public String getValorObservacion() {
		return valorObservacion;
	}

	/**
	 * @param valorObservacion
	 *            the valorObservacion to set
	 */
	public void setValorObservacion(String valorObservacion) {
		this.valorObservacion = valorObservacion;
	}

	/**
	 * @return the codigoObservacion
	 */
	public String getCodigoObservacion() {
		return codigoObservacion;
	}

	/**
	 * @param codigoObservacion
	 *            the codigoObservacion to set
	 */
	public void setCodigoObservacion(String codigoObservacion) {
		this.codigoObservacion = codigoObservacion;
	}

	/**
	 * @return the glosaObservacion
	 */
	public String getGlosaObservacion() {
		return glosaObservacion;
	}

	/**
	 * @param glosaObservacion
	 *            the glosaObservacion to set
	 */
	public void setGlosaObservacion(String glosaObservacion) {
		this.glosaObservacion = glosaObservacion;
	}

	/**
	 * @return the vistaOb
	 */
	public VistaItemDA getVistaOb() {
		return vistaOb;
	}

	/**
	 * @param vistaOb
	 *            the vistaOb to set
	 */
	public void setVistaOb(VistaItemDA vistaOb) {
		this.vistaOb = vistaOb;
	}

}
