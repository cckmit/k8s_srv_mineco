package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;

public class TipoDocumentoDTO implements Serializable {

	private static final long serialVersionUID = -2279199784341631481L;

	private Long id;
	private String acronimoGedo;
	private String acronimoTad;
	private String descripcion;
	private String detalleFc;
	private Boolean estado;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Boolean firmaConToken;
	private TipadoDocumentoDTO tipadoDcto;
	private Boolean incluidoEnSupertrata;
	private String nombre;
	private String nombreFormularioControlado;
	private String usuarioIniciador;
	private Long version;
	private String nombreArchivoSubido;
	private byte[] archivo;

	public TipoDocumentoDTO() {

	}

	public TipoDocumentoDTO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcronimoGedo() {
		return acronimoGedo;
	}

	public void setAcronimoGedo(String acronimoGedo) {
		this.acronimoGedo = acronimoGedo;
	}

	public String getAcronimoTad() {
		return acronimoTad;
	}

	public void setAcronimoTad(String acronimoTad) {
		this.acronimoTad = acronimoTad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDetalleFc() {
		return detalleFc;
	}

	public void setDetalleFc(String detalleFc) {
		this.detalleFc = detalleFc;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Boolean getFirmaConToken() {
		return firmaConToken;
	}

	public void setFirmaConToken(Boolean firmaConToken) {
		this.firmaConToken = firmaConToken;
	}

	public TipadoDocumentoDTO getTipadoDcto() {
		return tipadoDcto;
	}

	public void setTipadoDcto(TipadoDocumentoDTO tipadoDcto) {
		this.tipadoDcto = tipadoDcto;
	}

	public Boolean getIncluidoEnSupertrata() {
		return incluidoEnSupertrata;
	}

	public void setIncluidoEnSupertrata(Boolean incluidoEnSupertrata) {
		this.incluidoEnSupertrata = incluidoEnSupertrata;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreFormularioControlado() {
		return nombreFormularioControlado;
	}

	public void setNombreFormularioControlado(String nombreFormularioControlado) {
		this.nombreFormularioControlado = nombreFormularioControlado;
	}

	public String getUsuarioIniciador() {
		return usuarioIniciador;
	}

	public void setUsuarioIniciador(String usuarioIniciador) {
		this.usuarioIniciador = usuarioIniciador;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getNombreArchivoSubido() {
		return nombreArchivoSubido;
	}

	public void setNombreArchivoSubido(String nombreArchivoSubido) {
		this.nombreArchivoSubido = nombreArchivoSubido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoDocumentoDTO other = (TipoDocumentoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoDocumentoDTO [id=").append(id).append(", acronimoGedo=").append(acronimoGedo)
				.append(", acronimoTad=").append(acronimoTad).append(", descripcion=").append(descripcion)
				.append(", detalleFc=").append(detalleFc).append(", estado=").append(estado).append(", fechaCreacion=")
				.append(fechaCreacion).append(", fechaModificacion=").append(fechaModificacion)
				.append(", firmaConToken=").append(firmaConToken).append(", tipadoDcto=").append(tipadoDcto)
				.append(", incluidoEnSupertrata=").append(incluidoEnSupertrata).append(", nombre=").append(nombre)
				.append(", nombreFormularioControlado=").append(nombreFormularioControlado)
				.append(", usuarioIniciador=").append(usuarioIniciador).append(", version=").append(version)
				.append("]");
		return builder.toString();
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

}