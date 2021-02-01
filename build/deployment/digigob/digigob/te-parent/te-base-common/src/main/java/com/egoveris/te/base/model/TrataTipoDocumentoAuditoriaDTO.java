package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrataTipoDocumentoAuditoriaDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(TrataTipoDocumentoAuditoriaDTO.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3648915372327006252L;
	private Long idTrataTipoDocumento;
	private Long idTrata;
	private String  nombre;
	private Date fechaModif;
	private TrataDTO trata;
	private String nombreTipoDocumento;
	private String acronimoGEDO;
	private String actuacionSADE;
	private String estado;
	
	public static final String ESTADO_ACTIVO = "ALTA";
	public static final String ESTADO_INACTIVO = "BAJA";
	public static final String POR_SISTEMA = "SistemaGedo";
	
	private Boolean estaHabilitado; 
	private Boolean estaDeshabilitado; 
	
	public Boolean getEstaHabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstaHabilitado() - start");
		}

    if (getEstado() != null) {
      setEstaHabilitado(getEstado().compareTo(ESTADO_ACTIVO) == 0);
    } else {
      setEstaHabilitado(false);
    }

		if (logger.isDebugEnabled()) {
			logger.debug("getEstaHabilitado() - end - return value={}", estaHabilitado);
		}
		
		return estaHabilitado;
	}
	
	public void setEstaHabilitado(Boolean estaHabilitado) {
		this.estaHabilitado = estaHabilitado;
	}
	
	public Boolean getEstaDeshabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstaDeshabilitado() - start");
		}

    if (getEstado() != null) {
      setEstaDeshabilitado(getEstado().compareTo(ESTADO_INACTIVO) == 0);
    } else {
      setEstaDeshabilitado(true);
    }
		
		if (logger.isDebugEnabled()) {
			logger.debug("getEstaDeshabilitado() - end - return value={}", estaDeshabilitado);
		}
		
		return estaDeshabilitado;
	}

	public void setEstaDeshabilitado(Boolean estaDeshabilitado) {
		this.estaDeshabilitado = estaDeshabilitado;
	}
	
  public String getAcronimoGEDO() {
    return acronimoGEDO;
  }

  public void setAcronimoGEDO(String acronimoGEDO) {
    this.acronimoGEDO = acronimoGEDO;
  }

  public String getActuacionSADE() {
    return actuacionSADE;
  }

  public void setActuacionSADE(String actuacionSADE) {
    this.actuacionSADE = actuacionSADE;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public TrataDTO getTrata() {
    return trata;
  }

  public void setTrata(TrataDTO trata) {
    this.trata = trata;
  }
	
	public Long getIdTrata() {
		if (logger.isDebugEnabled()) {
			logger.debug("getIdTrata() - start");
		}

		idTrata = trata.getId();

		if (logger.isDebugEnabled()) {
			logger.debug("getIdTrata() - end - return value={}", idTrata);
		}
		
		return idTrata;
	}

	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaModif() {
		return fechaModif;
	}

	public void setFechaModif(Date fechaModif) {
		this.fechaModif = fechaModif;
	}

	public Long getIdTrataTipoDocumento() {
		return idTrataTipoDocumento;
	}

	public void setIdTrataTipoDocumento(Long idTrataTipoDocumento) {
		this.idTrataTipoDocumento = idTrataTipoDocumento;
	}

	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	
}
