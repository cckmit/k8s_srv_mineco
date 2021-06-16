package com.egoveris.te.base.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TrataTipoDocumentoDTO implements Serializable, Comparable<Object> {
	private static final Logger logger = LoggerFactory.getLogger(TrataTipoDocumentoDTO.class);

    /**
     *
     */
    private static final long serialVersionUID = 7092906296182130485L;

    public static final String ESTADO_ACTIVO = "ALTA";
    public static final String ESTADO_INACTIVO = "BAJA";
    private Long idTrataTipoDocumento;
    private Long idTrata;
    private TrataDTO trata;
    private String acronimoGEDO;
    private transient Boolean estaHabilitado;
    private transient String nombre;
    private transient String actuacion;

    public TrataTipoDocumentoDTO() {
        super();
    }

    public TrataTipoDocumentoDTO(String acronimoGEDO) {
        super();
        this.acronimoGEDO = acronimoGEDO;
    }

    public TrataTipoDocumentoDTO(String acronimoGEDO, TrataDTO trata) {
        super();
        this.acronimoGEDO = acronimoGEDO;
        this.trata = trata;
    }

    public Boolean getEstaHabilitado() {
        return estaHabilitado;
    }

    public void setEstaHabilitado(Boolean estaHabilitado) {
        this.estaHabilitado = estaHabilitado;
    }


    public String getAcronimoGEDO() {
        return acronimoGEDO;
    }

    public void setAcronimoGEDO(String acronimoGEDO) {
        this.acronimoGEDO = acronimoGEDO;
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

    public Long getIdTrataTipoDocumento() {
        return idTrataTipoDocumento;
    }

    public void setIdTrataTipoDocumento(Long idTrataTipoDocumento) {
        this.idTrataTipoDocumento = idTrataTipoDocumento;
    }
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActuacion() {
		return actuacion;
	}

	public void setActuacion(String actuacion) {
		this.actuacion = actuacion;
	}

	@Override
    public int compareTo(Object o) {
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(o={}) - start", o);
		}
		
		int returnint = 0;
		
		if (o != null && o instanceof TrataTipoDocumentoDTO) {
			TrataTipoDocumentoDTO trataTipoDocumentoDTO = (TrataTipoDocumentoDTO) o;
			
			if (this.getAcronimoGEDO() != null && trataTipoDocumentoDTO.getAcronimoGEDO() != null) {
				returnint = this.getAcronimoGEDO().compareTo(trataTipoDocumentoDTO.getAcronimoGEDO());
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(Object) - end - return value={}", returnint);
		}
		
        return returnint;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acronimoGEDO == null) ? 0 : acronimoGEDO.hashCode());
		result = prime * result + ((idTrata == null) ? 0 : idTrata.hashCode());
		result = prime * result + ((idTrataTipoDocumento == null) ? 0 : idTrataTipoDocumento.hashCode());
		result = prime * result + ((trata == null) ? 0 : trata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrataTipoDocumentoDTO other = (TrataTipoDocumentoDTO) obj;
		if (acronimoGEDO == null) {
			if (other.acronimoGEDO != null)
				return false;
		} else if (!acronimoGEDO.equals(other.acronimoGEDO))
			return false;
		if (idTrata == null) {
			if (other.idTrata != null)
				return false;
		} else if (!idTrata.equals(other.idTrata))
			return false;
		if (idTrataTipoDocumento == null) {
			if (other.idTrataTipoDocumento != null)
				return false;
		} else if (!idTrataTipoDocumento.equals(other.idTrataTipoDocumento))
			return false;
		if (trata == null) {
			if (other.trata != null)
				return false;
		} else if (!trata.equals(other.trata))
			return false;
		return true;
	}
}
