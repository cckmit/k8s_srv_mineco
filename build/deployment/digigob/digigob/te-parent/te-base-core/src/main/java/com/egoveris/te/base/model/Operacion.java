package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.tipo.TipoOperacion;

@Entity
@Table(name = "TE_OPERACION")
public class Operacion {
	
	@GeneratedValue
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TIPO_OPERACION_ID")
	private Long tipoOperacion;
	
	@Column(name = "EXECUTION_DBID_")
	private String jbpmExecutionId;
	 
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Column(name = "NUM_OFICIAL")
	private String numOficial;

	@Column(name = "ESTADO_BLOQ")
	private String estadoBloq; 

	@Column(name = "ESTADO_OPERACION")
	private String estadoOperacion;
 
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name = "TIPO_OPERACION_ID", insertable = false, updatable = false)
	private TipoOperacion tipoOperacionOb;
	
	@Column(name="ID_SECTOR_INTERNO")
	private Integer idSectorInterno;
	
	@Column(name="VERSION_PROCEDURE")
	private Integer versionProcedure;
	
	@Column(name="USUARIO_CREADOR")
  private String usuarioCreador;
  
  @Column(name="ID_REPARTICION")
  private Integer idReparticion;
  
  
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getJbpmExecutionId() {
		return jbpmExecutionId;
	}

	public void setJbpmExecutionId(String jbpmExecutionId) {
		this.jbpmExecutionId = jbpmExecutionId;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNumOficial() {
		return numOficial;
	}

	public void setNumOficial(String numOficial) {
		this.numOficial = numOficial;
	}

	public String getEstadoBloq() {
		return estadoBloq;
	}

	public void setEstadoBloq(String estadoBloq) {
		this.estadoBloq = estadoBloq;
	}
 
	public String getEstadoOperacion() {
		return estadoOperacion;
	}

	public void setEstadoOperacion(String estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	/**
	 * @return the tipoOperacionOb
	 */
	public TipoOperacion getTipoOperacionOb() {
		return tipoOperacionOb;
	}

	/**
	 * @param tipoOperacionOb the tipoOperacionOb to set
	 */
	public void setTipoOperacionOb(TipoOperacion tipoOperacionOb) {
		this.tipoOperacionOb = tipoOperacionOb;
	}

	public Integer getIdSectorInterno() {
		return idSectorInterno;
	}

	public void setIdSectorInterno(Integer idSectorInterno) {
		this.idSectorInterno = idSectorInterno;
	}

	public Integer getVersionProcedure() {
		return versionProcedure;
	}

	public void setVersionProcedure(Integer versionProcedure) {
		this.versionProcedure = versionProcedure;
	}

  public String getUsuarioCreador() {
    return usuarioCreador;
  }

  public void setUsuarioCreador(String usuarioCreador) {
    this.usuarioCreador = usuarioCreador;
  }

  public Integer getIdReparticion() {
    return idReparticion;
  }

  public void setIdReparticion(Integer idReparticion) {
    this.idReparticion = idReparticion;
  }
  
}
