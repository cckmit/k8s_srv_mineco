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
@Table(name = "edt_sade_sector_interno")
public class Sector{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_SECTOR_INTERNO")
	private Integer id;

	@Column(name = "NOMBRE_SECTOR_INTERNO")
	private String nombre;

	@Column(name = "CODIGO_SECTOR_INTERNO")
	private String codigo;

	@Column(name = "calle")
	private String calle;
	@Column(name = "numero")
	private String numeroCalle;
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
	@Column(name = "en_red")
	private String enRed;
	@Column(name = "sector_mesa")
	private String sectorMesa;
	@Column(name = "vigencia_desde")
	private Date vigenciaDesde;
	@Column(name = "vigencia_hasta")
	private Date vigenciaHasta;
	@Column(name = "es_archivo")
	private Boolean esArchivo;
	@Column(name = "mesa_virtual")
	private Boolean mesaVirtual;
	
	@ManyToOne
	@JoinColumn(name = "codigo_reparticion")
	private Reparticion reparticion;
	
	@Column(name = "estado_registro")
	private Boolean estadoRegistro;
	@Column(name = "usuario_asignador")
	private String usuarioAsignador;
	@Column(name = "usuario_creacion")
	private String usuarioCreacion;
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumeroCalle() {
		return numeroCalle;
	}
	public void setNumeroCalle(String numeroCalle) {
		this.numeroCalle = numeroCalle;
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
	public Boolean getEsArchivo() {
		return esArchivo;
	}
	public void setEsArchivo(Boolean esArchivo) {
		this.esArchivo = esArchivo;
	}
	public Boolean getMesaVirtual() {
		return mesaVirtual;
	}
	public void setMesaVirtual(Boolean mesaVirtual) {
		this.mesaVirtual = mesaVirtual;
	}
	public Reparticion getReparticion() {
		return reparticion;
	}
	public void setReparticion(Reparticion reparticion) {
		this.reparticion = reparticion;
	}
	public Boolean getEstadoRegistro() {
		return estadoRegistro;
	}
	public void setEstadoRegistro(Boolean estado) {
		this.estadoRegistro = estado;
	}
	public String getUsuarioAsignador() {
		return usuarioAsignador;
	}
	public void setUsuarioAsignador(String usuarioAsignador) {
		this.usuarioAsignador = usuarioAsignador;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
