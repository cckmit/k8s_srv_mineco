package com.egoveris.deo.base.model;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_DOCUMENTO")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NUMERO")
	private String numero;

	@Column(name = "NUMEROESPECIAL")
	private String numeroEspecial;

	@Column(name = "REPARTICION")
	private String reparticion;

	@Column(name = "REPARTICION_ACTUAL")
	private String reparticionActual;

	@Column(name = "ANIO")
	private String anio;

	@Column(name = "USUARIOGENERADOR")
	private String usuarioGenerador;

	@Column(name = "MOTIVO")
	private String motivo;

	@ManyToOne
	@JoinColumn(name = "TIPO")
	private TipoDocumento tipo;
	
	@ManyToOne
	@JoinColumn(name = "TIPORESERVA")
	private TipoReserva tipoReserva;

	@Column(name = "FECHACREACION")
	private Date fechaCreacion;

	@Column(name = "FECHA_MODIFICACION")
	private Date fechaModificacion;
 
	@Column(name = "WORKFLOWORIGEN")
	private String workflowOrigen;

	@Column(name = "SISTEMAORIGEN")
	private String sistemaOrigen;

	@Column(name = "SISTEMAINICIADOR")
	private String sistemaIniciador;

	@Column(name = "USUARIOINICIADOR")
	private String usuarioIniciador;

	@Column(name = "numero_sade_papel")
	private String numeroSadePapel;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DOCUMENTO")
	private Set<UsuarioReserva> usuariosReservados;

	@Column(name = "apoderado")
	private String apoderado;

	@Column(name = "motivo_depuracion")
	private String motivoDepuracion;

	@Column(name = "fecha_depuracion")
	private Date fechaDepuracion;

	@Column(name = "id_guarda_documental")
	private String idGuardaDocumental;

	@Column(name = "peso")
	private Integer peso;
	
	@Column(name = "id_publicable")
	private String idPublicable;

	public Documento() {
		this.fechaCreacion = new Date();
	}

	public String getNumeroSadePapel() {
		return numeroSadePapel;
	}

	public void setNumeroSadePapel(String numeroSadePapel) {
		this.numeroSadePapel = numeroSadePapel;
	}
 
	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public String getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getReparticion() {
		return reparticion;
	}

	public String getReparticionActual() {
		return reparticionActual;
	}

	public void setReparticionActual(String reparticionActual) {
		this.reparticionActual = reparticionActual;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setUsuarioGenerador(String usuarioGenerador) {
		this.usuarioGenerador = usuarioGenerador;
	}

	public String getUsuarioGenerador() {
		return usuarioGenerador;
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

	public String getWorkflowOrigen() {
		return workflowOrigen;
	}

	public void setWorkflowOrigen(String workflowOrigen) {
		this.workflowOrigen = workflowOrigen;

	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getSistemaOrigen() {
		return sistemaOrigen;
	}

	public String getUsuarioIniciador() {
		return usuarioIniciador;
	}

	public void setUsuarioIniciador(String usuarioIniciador) {
		this.usuarioIniciador = usuarioIniciador;
	}

	public String getSistemaIniciador() {
		return sistemaIniciador;
	}

	public void setSistemaIniciador(String sistemaIniciador) {
		this.sistemaIniciador = sistemaIniciador;
	}

	public TipoReserva getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReserva tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public Set<UsuarioReserva> getUsuariosReservados() {
		return usuariosReservados;
	}

	public void setUsuariosReservados(Set<UsuarioReserva> usuariosReservados) {
		this.usuariosReservados = usuariosReservados;
	}

	public String getApoderado() {
		return apoderado;
	}

	public void setApoderado(String apoderado) {
		this.apoderado = apoderado;
	}

	public String getMotivoDepuracion() {
		return motivoDepuracion;
	}

	public void setMotivoDepuracion(String motivoDepuracion) {
		this.motivoDepuracion = motivoDepuracion;
	}

	public Date getFechaDepuracion() {
		return fechaDepuracion;
	}

	public void setFechaDepuracion(Date fechaDepuracion) {
		this.fechaDepuracion = fechaDepuracion;
	}

	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}

	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getIdPublicable() {
		return idPublicable;
	}

	public void setIdPublicable(String idPublicable) {
		this.idPublicable = idPublicable;
	}
	
}