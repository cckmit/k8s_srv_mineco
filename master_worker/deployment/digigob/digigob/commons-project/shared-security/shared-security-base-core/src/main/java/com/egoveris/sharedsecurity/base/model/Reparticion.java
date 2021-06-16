package com.egoveris.sharedsecurity.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_reparticion")
public class Reparticion {

	@Id
	@Column(name = "ID_REPARTICION")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CODIGO_REPARTICION")
	private String codigo;

	@Column(name = "nombre_reparticion")
	private String nombre;
	@Column(name = "vigencia_desde")
	private Date vigenciaDesde;
	@Column(name = "vigencia_hasta")
	private Date vigenciaHasta;
	@Column(name = "calle")
	private String calleReparticion;
	@Column(name = "numero")
	private String numero;
	@Column(name = "piso")
	private String piso;
	@Column(name = "oficina")
	private String oficina;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "fax")
	private String fax;
	@Column(name = "email")
	private String email;
	@ManyToOne
	@JoinColumn(name = "id_estructura")
	private Estructura estructura;
	@Column(name = "en_red")
	private String enRed;
	@Column(name = "sector_mesa")
	private String sectorMesa;
	@Column(name = "version")
	private Integer version;
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	@Column(name = "usuario_creacion")
	private String usuarioCreacion;
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	@Column(name = "usuario_modificacion")
	private String usuarioModificacion;
	@Column(name = "estado_registro")
	private Boolean estadoRegistro;
	@Column(name = "es_dgtal")
	private Boolean esDgtal;
	@ManyToOne
	@JoinColumn(name = "rep_padre")
	private Reparticion repPadre;
	
	@Column(name = "es_padre")
	private Boolean esPadre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getVigenciaDesde() {
		return vigenciaDesde;
	}

	public void setVigenciaDesde(Date vigenciaDesde) {
		this.vigenciaDesde = vigenciaDesde;
	}

	public Date getVigenciaHasta() {
		return vigenciaHasta;
	}

	public void setVigenciaHasta(Date vigenciaHasta) {
		this.vigenciaHasta = vigenciaHasta;
	}

	public String getCalleReparticion() {
		return calleReparticion;
	}

	public void setCalleReparticion(String calleReparticion) {
		this.calleReparticion = calleReparticion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Estructura getEstructura() {
		return estructura;
	}

	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}

	public String getEnRed() {
		return enRed;
	}

	public void setEnRed(String enRed) {
		this.enRed = enRed;
	}

	public String getSectorMesa() {
		return sectorMesa;
	}

	public void setSectorMesa(String sectorMesa) {
		this.sectorMesa = sectorMesa;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Boolean getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(Boolean estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public Boolean getEsDgtal() {
		return esDgtal;
	}

	public void setEsDgtal(Boolean esDgtal) {
		this.esDgtal = esDgtal;
	}

	public Reparticion getRepPadre() {
		return repPadre;
	}

	public void setRepPadre(Reparticion repPadre) {
		this.repPadre = repPadre;
	}

	public Boolean getEsPadre() {
		return esPadre;
	}

	public void setEsPadre(Boolean esPadre) {
		this.esPadre = esPadre;
	}


}
