package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ReparticionHabilitadaDTO implements Serializable, Comparable<ReparticionHabilitadaDTO> {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private TipoDocumentoDTO tipoDocumento;
	private String codigoReparticion;
	private Boolean permisoIniciar;
	private Boolean permisoFirmar;
	private Boolean estado;
	private transient NumeracionEspecialDTO numeracionEspecial;
	private transient Boolean edicionNumeracionEspecial = false;

	public NumeracionEspecialDTO getNumeracionEspecial() {
		return numeracionEspecial;
	}

	public void setNumeracionEspecial(NumeracionEspecialDTO numeracionEspecial) {
		this.numeracionEspecial = numeracionEspecial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPermisoIniciar() {
		return permisoIniciar;
	}

	public void setPermisoIniciar(Boolean permisoIniciar) {
		this.permisoIniciar = permisoIniciar;
	}

	public Boolean getPermisoFirmar() {
		return permisoFirmar;
	}

	public void setPermisoFirmar(Boolean permisoFirmar) {
		this.permisoFirmar = permisoFirmar;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setEdicionNumeracionEspecial(Boolean edicionNumeracionEspecial) {
		this.edicionNumeracionEspecial = edicionNumeracionEspecial;
	}

	public Boolean getEdicionNumeracionEspecial() {
		return edicionNumeracionEspecial;
	}

	public int compareTo(ReparticionHabilitadaDTO reparticion) {
		int resultadoComparacion = 0;
		if ((id == null)) {
			if (reparticion.getId() == null) {
				resultadoComparacion = codigoReparticion.compareTo(reparticion.getCodigoReparticion());
			} else {
				resultadoComparacion = 1;
			}
		} else {
			if (reparticion.getId() != null) {
				resultadoComparacion = id.compareTo(reparticion.getId());
			} else {
				resultadoComparacion = -1;
			}
		}
		return resultadoComparacion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReparticionHabilitadaDTO) {
			return this.compareTo((ReparticionHabilitadaDTO) obj) == 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoReparticion == null) ? 0 : codigoReparticion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((permisoFirmar == null) ? 0 : permisoFirmar.hashCode());
		result = prime * result + ((permisoIniciar == null) ? 0 : permisoIniciar.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}
}