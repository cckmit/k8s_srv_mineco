package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Identificacion de un expediente. 
 * La caratula es la primer pagina del expediente y contiene
 * los datos identificatorios de un expediente. 
 * El numero de caratula y la secuencia debe ser provista por SADE, 
 * por tanto, estos datos son actualizados una vez que el expediente
 * es caratulado por SADE.
 * La estructura del codigo de caratula respecto al numero SADE es:
 * EX-2010-000000001-001-MGEYA-MGEYA (Ejemplo)
 * donde:
 * EX es el codigo de actuacion, 2010 su año, 000000001 su numero de 
 * actuacion, MGEYA la reparticion actual y reparticion usuario
 * respectivamente. 
 *  
 * @author Rocco Gallo Citera
 *
 */
public class ExpedienteAsociadoEntDTO implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteAsociadoEntDTO.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 570025178929826533L;
	private Long id;	
	private String tipoDocumento;
	private Integer anio;
	private Integer numero;
	private String secuencia;
	private Boolean definitivo=false;
	private String codigoReparticionActuacion;
	private String codigoReparticionUsuario;
	private Boolean esElectronico;
	private transient String trata;
	private Long idCodigoCaratula;
	private String usuarioAsociador;
	private Date fechaAsociacion;
	private String idTask;
	private Boolean esExpedienteAsociadoTC;
	private Boolean esExpedienteAsociadoFusion;
	private Date fechaModificacion;
	private String usuarioModificacion;
	private Long idExpCabeceraTC;
	private String descrpTrata;
	
  public String getTrata() {
    return trata;
  }

  public void setTrata(String trata) {
    this.trata = trata;
  }

  public Boolean getEsElectronico() {
    return esElectronico;
  }

  public void setEsElectronico(Boolean esElectronico) {
    this.esElectronico = esElectronico;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigoReparticionActuacion() {
    return codigoReparticionActuacion;
  }

  public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
    this.codigoReparticionActuacion = codigoReparticionActuacion;
  }

  public String getCodigoReparticionUsuario() {
    return codigoReparticionUsuario;
  }

  public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
    this.codigoReparticionUsuario = codigoReparticionUsuario;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Integer getAnio() {
    return anio;
  }

  public void setAnio(Integer anio) {
    this.anio = anio;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public String getSecuencia() {
    return secuencia;
  }

  public void setSecuencia(String secuencia) {
    this.secuencia = secuencia;
  }

  public Boolean getDefinitivo() {
    return definitivo;
  }

  public void setDefinitivo(Boolean definitivo) {
    this.definitivo = definitivo;
  }
	
	/**
	 * Devuelve el codigo de caratula formateado.
	 */
	@Override
	public String toString(){
		StringBuilder codigoCaratula = new StringBuilder();
		
    if (this.tipoDocumento != null) {
      codigoCaratula.append(this.tipoDocumento.toUpperCase());
      codigoCaratula.append("-");
      codigoCaratula.append(this.anio);
      codigoCaratula.append("-");
      codigoCaratula.append(this.numero);
      codigoCaratula.append("-");
      codigoCaratula.append(this.secuencia);
    }
    
    if (this.codigoReparticionActuacion != null) {
      codigoCaratula.append("-");
      codigoCaratula.append(this.codigoReparticionActuacion.toUpperCase());
    }
    
    if (this.codigoReparticionUsuario != null) {
      codigoCaratula.append("-");
      codigoCaratula.append(this.codigoReparticionUsuario.toUpperCase());
    }
    
    return codigoCaratula.toString();
	}
	
	public String getAsNumeroSade(){
		if (logger.isDebugEnabled()) {
			logger.debug("getAsNumeroSade() - start");
		}

    StringBuilder codigoCaratula = new StringBuilder();
    
    if (this.tipoDocumento != null) {
      codigoCaratula.append(this.tipoDocumento.toUpperCase());
    }
    
    codigoCaratula.append("-");
    codigoCaratula.append(this.anio);
    codigoCaratula.append("-");
    codigoCaratula.append(this.numero);
    codigoCaratula.append("-");
    codigoCaratula.append(this.secuencia);
    codigoCaratula.append("-");
    
    if (this.codigoReparticionActuacion != null) {
      codigoCaratula.append(this.codigoReparticionActuacion.toUpperCase());
    }
    
    codigoCaratula.append("-");
    
    if (this.codigoReparticionUsuario != null) {
      codigoCaratula.append(this.codigoReparticionUsuario.toUpperCase());
    }
    
    String returnString = codigoCaratula.toString();
    
    if (logger.isDebugEnabled()) {
      logger.debug("getAsNumeroSade() - end - return value={}", returnString);
    }
    
    return returnString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((secuencia == null) ? 0 : secuencia.hashCode());
		result = prime * result
				+ ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ExpedienteAsociadoEntDTO other = (ExpedienteAsociadoEntDTO) obj;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equals(other.anio))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (secuencia == null) {
			if (other.secuencia != null)
				return false;
		} else if (!secuencia.equals(other.secuencia))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

  public Long getIdCodigoCaratula() {
    return idCodigoCaratula;
  }

  public void setIdCodigoCaratula(Long idCodigoCaratula) {
    this.idCodigoCaratula = idCodigoCaratula;
  }

  public String getUsuarioAsociador() {
    return usuarioAsociador;
  }

  public void setUsuarioAsociador(String usuarioAsociador) {
    this.usuarioAsociador = usuarioAsociador;
  }

  public Date getFechaAsociacion() {
    return fechaAsociacion;
  }

  public void setFechaAsociacion(Date fechaAsociacion) {
    this.fechaAsociacion = fechaAsociacion;
  }

  public String getIdTask() {
    return idTask;
  }

  public void setIdTask(String idTask) {
    this.idTask = idTask;

  }

  public Boolean getEsExpedienteAsociadoTC() {
    return esExpedienteAsociadoTC;
  }

  public void setEsExpedienteAsociadoTC(Boolean esExpedienteAsociadoTC) {
    this.esExpedienteAsociadoTC = esExpedienteAsociadoTC;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Boolean getEsExpedienteAsociadoFusion() {
    return esExpedienteAsociadoFusion;
  }

  public void setEsExpedienteAsociadoFusion(Boolean esExpedienteAsociadoFusion) {
    this.esExpedienteAsociadoFusion = esExpedienteAsociadoFusion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public Long getIdExpCabeceraTC() {
    return idExpCabeceraTC;
  }

  public void setIdExpCabeceraTC(Long idExpCabeceraTC) {
    this.idExpCabeceraTC = idExpCabeceraTC;
  }

  public String getDescrpTrata() {
    return descrpTrata;
  }

  public void setDescrpTrata(String descrpTrata) {
    this.descrpTrata = descrpTrata;
  }
	
}
