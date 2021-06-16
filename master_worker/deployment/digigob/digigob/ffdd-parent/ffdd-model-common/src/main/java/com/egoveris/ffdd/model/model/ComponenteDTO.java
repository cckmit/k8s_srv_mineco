package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8893527584577306550L;

	private Integer id;
	private String nombre;
	private String nombreXml;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private String usuarioCreador;
	private String usuarioModificador;
	private TipoComponenteDTO tipoComponenteEnt;
	private Map<String, AtributoComponenteDTO> atributos;
	private Set<ItemDTO> items;
	private String mascara;
	private String mensaje;
	private boolean abmComponent;
	private Set<DynamicComponentFieldDTO> dynamicFields;

	public ComponenteDTO() {
		this.setFechaModificacion(Calendar.getInstance().getTime());
	}

	public Map<String, AtributoComponenteDTO> getAtributos() {
		return atributos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public Integer getId() {
		return id;
	}

	public Set<ItemDTO> getItems() {
		return items;
	}

	public String getMascara() {
		return mascara;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreXml() {
		return nombreXml;
	}

	public String getTipoComponente() {
		return tipoComponenteEnt.getNombre();
	}

	public TipoComponenteDTO getTipoComponenteEnt() {
		return tipoComponenteEnt;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	public boolean isAbmComponent() {
		return abmComponent;
	}

	public void setAbmComponent(final boolean abmComponent) {
		this.abmComponent = abmComponent;
	}

	public void setAtributos(final Map<String, AtributoComponenteDTO> atributos) {
		this.atributos = atributos;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setFechaModificacion(final Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setItems(final Set<ItemDTO> items) {
		this.items = items;
	}

	public void setMascara(final String mascara) {
		this.mascara = mascara;
	}

	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public void setNombreXml(final String nombreXml) {
		this.nombreXml = nombreXml;
	}

	public void setTipoComponenteEnt(final TipoComponenteDTO tipoComponenteEnt) {
		this.tipoComponenteEnt = tipoComponenteEnt;
	}

	public void setUsuarioCreador(final String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public void setUsuarioModificador(final String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * @return the dynamicFields
	 */
	public Set<DynamicComponentFieldDTO> getDynamicFields() {
		return dynamicFields;
	}

	/**
	 * @param dynamicFields
	 *            the dynamicFields to set
	 */
	public void setDynamicFields(Set<DynamicComponentFieldDTO> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}
}