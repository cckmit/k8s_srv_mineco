
package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HISTORIALOPERACION")
public class HistorialOperacion implements Serializable {

	/**  
	 * 
	 */
	private static final long serialVersionUID = -3762107384789954953L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="TIPO_OPERACION")
	private String tipoOperacion; 
	
	@Column(name="FECHA_OPERACION")
	private Date fechaOperacion; 

	@Column(name="USUARIO")
	private String usuario;
 
	@Column(name="EXPEDIENTE")
	private String expediente;

	@Column(name="ID_EXPEDIENTE") 
	private Long idExpediente;
	
	@Column(name="ID_SOLICITUD")
	private Integer idSolicitud;

	@Column(name="ID_EXPEDIENTE_ELECTRONICO")
	private Integer idExpedienteDetalle;

	@Column(name="FECHA")
	private Date fechaDetalle;

	@Column(name="GRUPO_SELECCIONADO")
	private String grupoSeleccionado;

	@Column(name="ES_SECTOR_DESTINO")
	private String esSectorDestino;

	@Column(name="USUARIO_DESTINO")
	private String usuarioDestino;

	@Column(name="ID_LIST_DESTINATARIOS")
	private String idListDestinatarios;

	@Column(name="DESTINATARIO")
	private String destinatarioDetalle;

	@Column(name="USUARIO_ANTERIOR")
	private String usuarioAnterior;

	@Column(name="ESTADO_SELECCIONADO")
	private String estadoSeleccionado;

	@Column(name="ES_USUARIODESTINO")
	private String esUsuarioDestino;

	@Column(name="GRUPO_ANTERIOR")
	private String grupoAnterior;

	@Column(name="USUARIO_PRODUCTOR_INFO")
	private String usuarioProductorInfo;

	@Column(name="SECTOR_DESTINO")
	private String sectorDestino;

	@Column(name="USUARIO_ORIGEN")
	private String usuarioOrigen;

	@Column(name="SISTEMA_APODERADO")
	private String sistemaApoderado;

	@Column(name="REPARTICION_USUARIO")
	private String reparticionUsuario;

	@Column(name="REPARTICION_DESTINO")
	private String reparticionDestino;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="TIPO_OPERACION_DETALLE")
	private String tipoOperacionDetalle;
 
	@Column(name="DESTINO")
	private String destino;

	@Column(name="MOTIVO")
	private String motivo;

	@Column(name="ESTADO_ANTERIOR")
	private String estadoAnterior;

	@Column(name="TAREA_GRUPAL")
	private String tareaGrupal;

	@Column(name="LOGGEDUSERNAME")
	private String loggedUsername;

	@Column(name="ES_REPARTICIONDESTINO")
	private String esReparticionDestino;

	@Column(name="ESTADO")
	private String estado;

  @Column(name="USUARIO_SELECCIONADO")
	private String usuarioSeleccionado;
 
	@Column(name="SECTOR_USUARIO_ORIGEN")
	private String sectorUsuario;

	@Column(name="RESULTADO")
	private String resultado;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the fechaOperacion
	 */
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion the fechaOperacion to set
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
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
	 * @return the expediente
	 */
	public String getExpediente() {
		return expediente;
	}

	/**
	 * @param expediente the expediente to set
	 */
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	/**
	 * @return the idExpediente
	 */
	public Long getIdExpediente() {
		return idExpediente;
	}

	/**
	 * @param idExpediente the idExpediente to set
	 */
	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	/**
	 * @return the idSolicitud
	 */
	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the idExpedienteDetalle
	 */
	public Integer getIdExpedienteDetalle() {
		return idExpedienteDetalle;
	}

	/**
	 * @param idExpedienteDetalle the idExpedienteDetalle to set
	 */
	public void setIdExpedienteDetalle(Integer idExpedienteDetalle) {
		this.idExpedienteDetalle = idExpedienteDetalle;
	}

	/**
	 * @return the fechaDetalle
	 */
	public Date getFechaDetalle() {
		return fechaDetalle;
	}

	/**
	 * @param fechaDetalle the fechaDetalle to set
	 */
	public void setFechaDetalle(Date fechaDetalle) {
		this.fechaDetalle = fechaDetalle;
	}

	/**
	 * @return the grupoSeleccionado
	 */
	public String getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	/**
	 * @param grupoSeleccionado the grupoSeleccionado to set
	 */
	public void setGrupoSeleccionado(String grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	/**
	 * @return the esSectorDestino
	 */
	public String getEsSectorDestino() {
		return esSectorDestino;
	}

	/**
	 * @param esSectorDestino the esSectorDestino to set
	 */
	public void setEsSectorDestino(String esSectorDestino) {
		this.esSectorDestino = esSectorDestino;
	}

	/**
	 * @return the usuarioDestino
	 */
	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	/**
	 * @param usuarioDestino the usuarioDestino to set
	 */
	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	/**
	 * @return the idListDestinatarios
	 */
	public String getIdListDestinatarios() {
		return idListDestinatarios;
	}

	/**
	 * @param idListDestinatarios the idListDestinatarios to set
	 */
	public void setIdListDestinatarios(String idListDestinatarios) {
		this.idListDestinatarios = idListDestinatarios;
	}

	/**
	 * @return the destinatarioDetalle
	 */
	public String getDestinatarioDetalle() {
		return destinatarioDetalle;
	}

	/**
	 * @param destinatarioDetalle the destinatarioDetalle to set
	 */
	public void setDestinatarioDetalle(String destinatarioDetalle) {
		this.destinatarioDetalle = destinatarioDetalle;
	}

	/**
	 * @return the usuarioAnterior
	 */
	public String getUsuarioAnterior() {
		return usuarioAnterior;
	}

	/**
	 * @param usuarioAnterior the usuarioAnterior to set
	 */
	public void setUsuarioAnterior(String usuarioAnterior) {
		this.usuarioAnterior = usuarioAnterior;
	}

	/**
	 * @return the estadoSeleccionado
	 */
	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	/**
	 * @param estadoSeleccionado the estadoSeleccionado to set
	 */
	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	/**
	 * @return the esUsuarioDestino
	 */
	public String getEsUsuarioDestino() {
		return esUsuarioDestino;
	}

	/**
	 * @param esUsuarioDestino the esUsuarioDestino to set
	 */
	public void setEsUsuarioDestino(String esUsuarioDestino) {
		this.esUsuarioDestino = esUsuarioDestino;
	}

	/**
	 * @return the grupoAnterior
	 */
	public String getGrupoAnterior() {
		return grupoAnterior;
	}

	/**
	 * @param grupoAnterior the grupoAnterior to set
	 */
	public void setGrupoAnterior(String grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	/**
	 * @return the usuarioProductorInfo
	 */
	public String getUsuarioProductorInfo() {
		return usuarioProductorInfo;
	}

	/**
	 * @param usuarioProductorInfo the usuarioProductorInfo to set
	 */
	public void setUsuarioProductorInfo(String usuarioProductorInfo) {
		this.usuarioProductorInfo = usuarioProductorInfo;
	}

	/**
	 * @return the sectorDestino
	 */
	public String getSectorDestino() {
		return sectorDestino;
	}

	/**
	 * @param sectorDestino the sectorDestino to set
	 */
	public void setSectorDestino(String sectorDestino) {
		this.sectorDestino = sectorDestino;
	}

	/**
	 * @return the usuarioOrigen
	 */
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	/**
	 * @param usuarioOrigen the usuarioOrigen to set
	 */
	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	/**
	 * @return the sistemaApoderado
	 */
	public String getSistemaApoderado() {
		return sistemaApoderado;
	}

	/**
	 * @param sistemaApoderado the sistemaApoderado to set
	 */
	public void setSistemaApoderado(String sistemaApoderado) {
		this.sistemaApoderado = sistemaApoderado;
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
	 * @return the reparticionDestino
	 */
	public String getReparticionDestino() {
		return reparticionDestino;
	}

	/**
	 * @param reparticionDestino the reparticionDestino to set
	 */
	public void setReparticionDestino(String reparticionDestino) {
		this.reparticionDestino = reparticionDestino;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoOperacionDetalle
	 */
	public String getTipoOperacionDetalle() {
		return tipoOperacionDetalle;
	}

	/**
	 * @param tipoOperacionDetalle the tipoOperacionDetalle to set
	 */
	public void setTipoOperacionDetalle(String tipoOperacionDetalle) {
		this.tipoOperacionDetalle = tipoOperacionDetalle;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
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
	 * @return the estadoAnterior
	 */
	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	/**
	 * @param estadoAnterior the estadoAnterior to set
	 */
	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	/**
	 * @return the tareaGrupal
	 */
	public String getTareaGrupal() {
		return tareaGrupal;
	}

	/**
	 * @param tareaGrupal the tareaGrupal to set
	 */
	public void setTareaGrupal(String tareaGrupal) {
		this.tareaGrupal = tareaGrupal;
	}

	/**
	 * @return the loggedUsername
	 */
	public String getLoggedUsername() {
		return loggedUsername;
	}

	/**
	 * @param loggedUsername the loggedUsername to set
	 */
	public void setLoggedUsername(String loggedUsername) {
		this.loggedUsername = loggedUsername;
	}

	/**
	 * @return the esReparticionDestino
	 */
	public String getEsReparticionDestino() {
		return esReparticionDestino;
	}

	/**
	 * @param esReparticionDestino the esReparticionDestino to set
	 */
	public void setEsReparticionDestino(String esReparticionDestino) {
		this.esReparticionDestino = esReparticionDestino;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the usuarioSeleccionado
	 */
	public String getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	/**
	 * @param usuarioSeleccionado the usuarioSeleccionado to set
	 */
	public void setUsuarioSeleccionado(String usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	/**
	 * @return the sectorUsuario
	 */
	public String getSectorUsuario() {
		return sectorUsuario;
	}

	/**
	 * @param sectorUsuario the sectorUsuario to set
	 */
	public void setSectorUsuario(String sectorUsuario) {
		this.sectorUsuario = sectorUsuario;
	}

  /**
   * @return the resultado
   */
  public String getResultado() {
    return resultado;
  }

  /**
   * @param resultado the resultado to set
   */
  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

public String getLatitude() {
	return latitude;
}

public void setLatitude(String latitude) {
	this.latitude = latitude;
}

public String getLongitude() {
	return longitude;
}

public void setLongitude(String longitude) {
	this.longitude = longitude;
}

}
