package com.egoveris.te.base.model.trata;

import java.util.ArrayList;
import java.util.List;

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

import com.egoveris.te.base.model.tipo.TipoReserva;

@Entity
@Table(name = "TRATA")
public class Trata {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODIGO_TRATA")
	private String codigoTrata;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "ACRONIMO_DOC_GEDO")
	private String acronimoDocumento;

	@ManyToOne
	@JoinColumn(name = "ID_RESERVA")
	private TipoReserva tipoReserva;

	@OneToMany(mappedBy = "idTrata", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrataReparticion> reparticionesTrata;

	@Column(name = "ES_AUTOMATICA")
	private Boolean esAutomatica;

	@Column(name = "ES_MANUAL")
	private Boolean esManual;

	@Column(name = "ES_INTERNO")
	private Boolean esInterno;

	@Column(name = "ES_EXTERNO")
	private Boolean esExterno;

	@Column(name = "ES_NOTIFICABLE_TAD")
	private Boolean esNotificableTad;

	@Column(name = "ENVIO_AUTOMATICO_GT")
	private Boolean esEnvioAutomaticoGT;

	@Column(name = "INTEGRA_SIS_EXT")
	private Boolean integracionSisExt;

	@Column(name = "INTEGRACION_AFJG")
	private Boolean integracionAFJG;

	@Column(name = "CLAVE_TAD")
	private String claveTad;

	@Column(name = "NOTIFICABLE_JMS")
	private Boolean notificableJMS;

	@OneToMany(mappedBy = "trata", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrataTipoDocumento> listaTrataTipoDocumento;

	@Column(name = "TIEMPO_RESOLUCION")
	private Integer tiempoResolucion;

	@Column(name = "NOMBRE_WORKFLOW")
	private String workflow;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@OneToMany(mappedBy = "idTrata", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrataTipoResultado> tipoResultadosTrata;

	@Column(name = "TIPO_CARGA")
	private Integer tipoCarga;

	// TODO CAMPO POR REVISAR
	// private List<MetadataDTO> datoVariable = new ArrayList<MetadataDTO>();

	public Long getId() {
		return id;
	}

	/**
	 * @return the tipoCarga
	 */
	public Integer getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga
	 *            the tipoCarga to set
	 */
	public void setTipoCarga(Integer tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAcronimoDocumento() {
		return acronimoDocumento;
	}

	public void setAcronimoDocumento(String acronimoDocumento) {
		this.acronimoDocumento = acronimoDocumento;
	}

	public TipoReserva getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReserva tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public List<TrataReparticion> getReparticionesTrata() {
		return reparticionesTrata;
	}

	public void setReparticionesTrata(List<TrataReparticion> reparticionesTrata) {
		this.reparticionesTrata = reparticionesTrata;
	}

	public Boolean getEsAutomatica() {
		return esAutomatica;
	}

	public void setEsAutomatica(Boolean esAutomatica) {
		this.esAutomatica = esAutomatica;
	}

	public Boolean getEsManual() {
		return esManual;
	}

	public void setEsManual(Boolean esManual) {
		this.esManual = esManual;
	}

	public Boolean getEsInterno() {
		return esInterno;
	}

	public void setEsInterno(Boolean esInterno) {
		this.esInterno = esInterno;
	}

	public Boolean getEsExterno() {
		return esExterno;
	}

	public void setEsExterno(Boolean esExterno) {
		this.esExterno = esExterno;
	}

	public Boolean getEsNotificableTad() {
		return esNotificableTad;
	}

	public void setEsNotificableTad(Boolean esNotificableTad) {
		this.esNotificableTad = esNotificableTad;
	}

	public Boolean getEsEnvioAutomaticoGT() {
		return esEnvioAutomaticoGT;
	}

	public void setEsEnvioAutomaticoGT(Boolean esEnvioAutomaticoGT) {
		this.esEnvioAutomaticoGT = esEnvioAutomaticoGT;
	}

	public Boolean getIntegracionSisExt() {
		return integracionSisExt;
	}

	public void setIntegracionSisExt(Boolean integracionSisExt) {
		this.integracionSisExt = integracionSisExt;
	}

	public Boolean getIntegracionAFJG() {
		return integracionAFJG;
	}

	public void setIntegracionAFJG(Boolean integracionAFJG) {
		this.integracionAFJG = integracionAFJG;
	}

	public String getClaveTad() {
		return claveTad;
	}

	public void setClaveTad(String claveTad) {
		this.claveTad = claveTad;
	}

	public Boolean getNotificableJMS() {
		return notificableJMS;
	}

	public void setNotificableJMS(Boolean notificableJMS) {
		this.notificableJMS = notificableJMS;
	}

	public Integer getTiempoResolucion() {
		return tiempoResolucion;
	}

	public void setTiempoResolucion(Integer tiempoResolucion) {
		this.tiempoResolucion = tiempoResolucion;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the listaTrataTipoDocumento
	 */
	public List<TrataTipoDocumento> getListaTrataTipoDocumento() {
		return listaTrataTipoDocumento;
	}

	/**
	 * @param listaTrataTipoDocumento
	 *            the listaTrataTipoDocumento to set
	 */
	public void setListaTrataTipoDocumento(List<TrataTipoDocumento> listaTrataTipoDocumento) {
		this.listaTrataTipoDocumento = listaTrataTipoDocumento;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public List<TrataTipoResultado> getTipoResultadosTrata() {
		if (tipoResultadosTrata == null) {
			tipoResultadosTrata = new ArrayList<>();
		}

		return tipoResultadosTrata;
	}

	public void setTipoResultadosTrata(List<TrataTipoResultado> tipoResultadosTrata) {
		this.tipoResultadosTrata = tipoResultadosTrata;
	}

}
