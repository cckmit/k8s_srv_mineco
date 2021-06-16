package com.egoveris.ffdd.base.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DF_COMPONENT")
public class Componente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String nombre;

	@Column(name = "nombreXml")
	private String nombreXml;

	@Column(name = "description")
	private String descripcion;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@Column(name = "modification_date")
	// @Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@Column(name = "creator_user")
	private String usuarioCreador;

	@Column(name = "modifying_user")
	private String usuarioModificador;

	@ManyToOne
	@JoinColumn(name = "id_component_type")
	private TipoComponente tipoComponenteEnt;

	@OneToMany(mappedBy = "componente", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@MapKeyColumn(name = "key_at")
	private Map<String, AtributoComponente> atributos;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "componente")
	@OrderBy("value_order ASC")
	private Set<Item> items;

	@Column(name = "mascara")
	private String mascara;

	@Column(name = "mensaje")
	private String mensaje;

	@Column(name = "abm_component")
	private Boolean abmComponent;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "componente")
	private Set<DynamicComponentField> dynamicFields;

	public Componente() {
		this.setFechaModificacion(Calendar.getInstance().getTime());
	}

	public Map<String, AtributoComponente> getAtributos() {
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

	public Set<Item> getItems() {
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

	public TipoComponente getTipoComponenteEnt() {
		return tipoComponenteEnt;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	public Boolean isAbmComponent() {
		return abmComponent;
	}

	public void setAbmComponent(final Boolean abmComponent) {
		this.abmComponent = abmComponent;
	}

	public void setAtributos(final Map<String, AtributoComponente> atributos) {
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

	public void setItems(final Set<Item> items) {
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

	public void setTipoComponenteEnt(final TipoComponente tipoComponenteEnt) {
		this.tipoComponenteEnt = tipoComponenteEnt;
	}

	public void setUsuarioCreador(final String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public void setUsuarioModificador(final String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	/**
	 * @return the dynamicFields
	 */
	public Set<DynamicComponentField> getDynamicFields() {
		return dynamicFields;
	}

	/**
	 * @param dynamicFields
	 *            the dynamicFields to set
	 */
	public void setDynamicFields(Set<DynamicComponentField> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}

	/**
	 * @return the abmComponent
	 */
	public Boolean getAbmComponent() {
		return abmComponent;
	}
}