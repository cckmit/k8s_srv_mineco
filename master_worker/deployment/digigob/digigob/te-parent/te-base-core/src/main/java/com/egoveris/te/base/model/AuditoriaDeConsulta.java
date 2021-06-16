package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EE_AUDITORIA_DE_CONSULTA")
public class AuditoriaDeConsulta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_AUDITORIA") 
	private Long idAuditoria;
	
	@Column(name="USUARIO") 
	private String usuario;
	
	@Column(name="TIPO_ACTUACION") 
	private String tipoActuacion;
	
	@Column(name="ANIO_ACTUACION") 
	private Integer anioActuacion;
	
	@Column(name="NUMERO_ACTUACION") 
	private Integer numeroActuacion;
	
	@Column(name="REPARTICION_ACTUACION") 
	private String reparticionActuacion;
	
	@Column(name="REPARTICION_USUARIO") 
	private String reparticionUsuario;
	
	@Column(name="TRATA") 
	private String trata;
	
	@Column(name="METADATO_1") 
	private String metadato1;
	
	@Column(name="VALOR_METADATO_1") 
	private String valorMetadato1;
	
	@Column(name="METADATO_2") 
	private String metadato2;
	
	@Column(name="VALOR_METADATO_2") 
	private String valorMetadato2;
	
	@Column(name="METADATO_3") 
	private String metadato3;
	
	@Column(name="VALOR_METADATO_3") 
	private String valorMetadato3;
	
	@Column(name="FECHA_DESDE") 
	private Date fechaDesde;
	
	@Column(name="FECHA_HASTA") 
	private Date fechaHasta;
	 
	@Column(name="FECHA_CONSULTA") 
	private Date fechaConsulta;
	 
	@Column(name="TIPODOCUMENTO") 
	private String tipoDocumento;
	
	@Column(name="NUMERODOCUMENTO") 
	private String numeroDocumento;
	
	@Column(name="CUIT_CUIL") 
	private String cuitCuil;
	
	@Column(name="DOMICILIO") 
	private String domicilio;
	
	@Column(name="PISO") 
	private String piso;
	
	@Column(name="DEPARTAMENTO") 
	private String departamento;
	
	@Column(name="CODIGO_POSTAL") 
	private String codigoPostal;

	/**
	 * @return the idAuditoria
	 */
	public Long getIdAuditoria() {
		return idAuditoria;
	}

	/**
	 * @param idAuditoria the idAuditoria to set
	 */
	public void setIdAuditoria(Long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the tipoActuacion
	 */
	public String getTipoActuacion() {
		return tipoActuacion;
	}

	/**
	 * @param tipoActuacion the tipoActuacion to set
	 */
	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	/**
	 * @return the anioActuacion
	 */
	public Integer getAnioActuacion() {
		return anioActuacion;
	}

	/**
	 * @param anioActuacion the anioActuacion to set
	 */
	public void setAnioActuacion(Integer anioActuacion) {
		this.anioActuacion = anioActuacion;
	}

	/**
	 * @return the numeroActuacion
	 */
	public Integer getNumeroActuacion() {
		return numeroActuacion;
	}

	/**
	 * @param numeroActuacion the numeroActuacion to set
	 */
	public void setNumeroActuacion(Integer numeroActuacion) {
		this.numeroActuacion = numeroActuacion;
	}

	/**
	 * @return the reparticionActuacion
	 */
	public String getReparticionActuacion() {
		return reparticionActuacion;
	}

	/**
	 * @param reparticionActuacion the reparticionActuacion to set
	 */
	public void setReparticionActuacion(String reparticionActuacion) {
		this.reparticionActuacion = reparticionActuacion;
	}

	/**
	 * @return the reparticionUsuario
	 */
	public String getReparticionUsuario() {
		return reparticionUsuario;
	}

	/**
	 * @param reparticionUsuario the reparticionUsuario to set
	 */
	public void setReparticionUsuario(String reparticionUsuario) {
		this.reparticionUsuario = reparticionUsuario;
	}

	/**
	 * @return the trata
	 */
	public String getTrata() {
		return trata;
	}

	/**
	 * @param trata the trata to set
	 */
	public void setTrata(String trata) {
		this.trata = trata;
	}

	/**
	 * @return the metadato1
	 */
	public String getMetadato1() {
		return metadato1;
	}

	/**
	 * @param metadato1 the metadato1 to set
	 */
	public void setMetadato1(String metadato1) {
		this.metadato1 = metadato1;
	}

	/**
	 * @return the valorMetadato1
	 */
	public String getValorMetadato1() {
		return valorMetadato1;
	}

	/**
	 * @param valorMetadato1 the valorMetadato1 to set
	 */
	public void setValorMetadato1(String valorMetadato1) {
		this.valorMetadato1 = valorMetadato1;
	}

	/**
	 * @return the metadato2
	 */
	public String getMetadato2() {
		return metadato2;
	}

	/**
	 * @param metadato2 the metadato2 to set
	 */
	public void setMetadato2(String metadato2) {
		this.metadato2 = metadato2;
	}

	/**
	 * @return the valorMetadato2
	 */
	public String getValorMetadato2() {
		return valorMetadato2;
	}

	/**
	 * @param valorMetadato2 the valorMetadato2 to set
	 */
	public void setValorMetadato2(String valorMetadato2) {
		this.valorMetadato2 = valorMetadato2;
	}

	/**
	 * @return the metadato3
	 */
	public String getMetadato3() {
		return metadato3;
	}

	/**
	 * @param metadato3 the metadato3 to set
	 */
	public void setMetadato3(String metadato3) {
		this.metadato3 = metadato3;
	}

	/**
	 * @return the valorMetadato3
	 */
	public String getValorMetadato3() {
		return valorMetadato3;
	}

	/**
	 * @param valorMetadato3 the valorMetadato3 to set
	 */
	public void setValorMetadato3(String valorMetadato3) {
		this.valorMetadato3 = valorMetadato3;
	}

	/**
	 * @return the fechaDesde
	 */
	public Date getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * @return the fechaHasta
	 */
	public Date getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	/**
	 * @return the fechaConsulta
	 */
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * @param fechaConsulta the fechaConsulta to set
	 */
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the cuitCuil
	 */
	public String getCuitCuil() {
		return cuitCuil;
	}

	/**
	 * @param cuitCuil the cuitCuil to set
	 */
	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the piso
	 */
	public String getPiso() {
		return piso;
	}

	/**
	 * @param piso the piso to set
	 */
	public void setPiso(String piso) {
		this.piso = piso;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	
}
