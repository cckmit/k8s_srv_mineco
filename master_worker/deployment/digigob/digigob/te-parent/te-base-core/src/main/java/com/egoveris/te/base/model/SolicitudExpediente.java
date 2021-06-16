package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Solicitud de alta de expediente.
 * 
 * @author rgalloci
 *
 */ 
@Entity
@Table(name="SOLICITUD_EXPEDIENTE")
public class SolicitudExpediente implements Serializable{
	
	private static final long serialVersionUID = -7109795626396712896L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="MOTIVO_EXTERNO")
	private String motivoExterno;
	
	@Column(name="MOTIVO_DE_RECHAZO")
	private String motivoDeRechazo; 
	
	@Column(name="SOLICITUD_INTERNA")
	private boolean esSolicitudInterna;
	
	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;
	
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name="ID_TRATA_SUGERIDA")
	private Integer idTrataSugerida;
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SOLICITANTE")
	private Solicitante solicitante;

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}
	 
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the motivoExterno
	 */
	public String getMotivoExterno() {
		return motivoExterno;
	}

	/**
	 * @param motivoExterno the motivoExterno to set
	 */
	public void setMotivoExterno(String motivoExterno) {
		this.motivoExterno = motivoExterno;
	}

	/**
	 * @return the motivoDeRechazo
	 */
	public String getMotivoDeRechazo() {
		return motivoDeRechazo;
	}

	/**
	 * @param motivoDeRechazo the motivoDeRechazo to set
	 */
	public void setMotivoDeRechazo(String motivoDeRechazo) {
		this.motivoDeRechazo = motivoDeRechazo;
	}

	/**
	 * @return the esSolicitudInterna
	 */
	public boolean isEsSolicitudInterna() {
		return esSolicitudInterna;
	}

	/**
	 * @param esSolicitudInterna the esSolicitudInterna to set
	 */
	public void setEsSolicitudInterna(boolean esSolicitudInterna) {
		this.esSolicitudInterna = esSolicitudInterna;
	}

	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the idTrataSugerida
	 */
	public Integer getIdTrataSugerida() {
		return idTrataSugerida;
	}

	/**
	 * @param idTrataSugerida the idTrataSugerida to set
	 */
	public void setIdTrataSugerida(Integer idTrataSugerida) {
		this.idTrataSugerida = idTrataSugerida;
	}

	/**
	 * @return the solicitante
	 */
	public Solicitante getSolicitante() {
		return solicitante;
	}

	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}
}
