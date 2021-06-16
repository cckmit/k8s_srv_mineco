package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.egoveris.shared.date.DateUtil;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;

public class ExpedienteBaseDTO implements Serializable {

  private static final long serialVersionUID = 2006740933790410551L;

  private static final String SINCODIGOSADE = "No se ha generado un código para el expediente.";

  private Long id;
  private Long bajaLogica;
  private String descripcionAdicional;
  private Date fechaCreacion;
  private Date fechaInicio;
  private ApoderadoDTO apoderado;
  private EstadoTramiteDTO estadoTramite;
  private Long idExpPadre;
  private Long idInterviniente;
  private PersonaDTO persona;
  private String motivo;
  private Long pasoActual;
  private Boolean seVaAReintentar;
  private List<DocumentoDTO> documentosList;
  private TipoTramiteDTO tipoTramite;
  private Long tomandoVista;
  private String turno;
  private Date ultimoIngreso;
  private Long version;
  
  
  private String apiKeyTransaccion;  
  private BigDecimal monto;
  private String transaccionPago;
  private Date fechaPago;
  private String numeroAutorizacion;
  
  private String nombreTitutarTarjeta;
  
  
  
  private Long fechaPagoTime;
  
  private String numeroExpediente;
  
  public String getCodigoSade() {
    if (this.numeroExpediente != null) {
      return this.numeroExpediente;
    }
    return SINCODIGOSADE;
  }

  public String getFormattedFechaCreacion() {
    if (fechaCreacion != null) {
      return DateUtil.getFormattedDate(fechaCreacion);
    }
    return "";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBajaLogica() {
    return bajaLogica;
  }

  public void setBajaLogica(Long bajaLogica) {
    this.bajaLogica = bajaLogica;
  }

  public String getDescripcionAdicional() {
    return descripcionAdicional;
  }

  public void setDescripcionAdicional(String descripcionAdicional) {
    this.descripcionAdicional = descripcionAdicional;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public ApoderadoDTO getApoderado() {
    return apoderado;
  }

  public void setApoderado(ApoderadoDTO apoderado) {
    this.apoderado = apoderado;
  }

  public EstadoTramiteDTO getEstadoTramite() {
    return estadoTramite;
  }

  public void setEstadoTramite(EstadoTramiteDTO estadoTramite) {
    this.estadoTramite = estadoTramite;
  }

  public Long getIdExpPadre() {
    return idExpPadre;
  }

  public void setIdExpPadre(Long idExpPadre) {
    this.idExpPadre = idExpPadre;
  }

  public Long getIdInterviniente() {
    return idInterviniente;
  }

  public void setIdInterviniente(Long idInterviniente) {
    this.idInterviniente = idInterviniente;
  }

  public PersonaDTO getPersona() {
    return persona;
  }

  public void setPersona(PersonaDTO persona) {
    this.persona = persona;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Long getPasoActual() {
    return pasoActual;
  }

  public void setPasoActual(Long pasoActual) {
    this.pasoActual = pasoActual;
  }

  public Boolean getSeVaAReintentar() {
    return seVaAReintentar;
  }

  public void setSeVaAReintentar(Boolean seVaAReintentar) {
    this.seVaAReintentar = seVaAReintentar;
  }

  public List<DocumentoDTO> getDocumentosList() {
    if (documentosList == null) {
      documentosList = new ArrayList<>();
    }
    return documentosList;
  }

  public void setDocumentosList(List<DocumentoDTO> documentosList) {
    this.documentosList = documentosList;
  }

  public TipoTramiteDTO getTipoTramite() {
    return tipoTramite;
  }

  public void setTipoTramite(TipoTramiteDTO tipoTramite) {
    this.tipoTramite = tipoTramite;
  }

  public Long getTomandoVista() {
    return tomandoVista;
  }

  public void setTomandoVista(Long tomandoVista) {
    this.tomandoVista = tomandoVista;
  }

  public String getTurno() {
    return turno;
  }

  public void setTurno(String turno) {
    this.turno = turno;
  }

  public Date getUltimoIngreso() {
    return ultimoIngreso;
  }

  public void setUltimoIngreso(Date ultimoIngreso) {
    this.ultimoIngreso = ultimoIngreso;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String getTransaccionPago() {
		return transaccionPago;
	}

	public void setTransaccionPago(String transaccionPago) {
		this.transaccionPago = transaccionPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public Long getFechaPagoTime() {
		return fechaPagoTime;
	}

	public void setFechaPagoTime(Long fechaPagoTime) {
		if(fechaPagoTime!=null) {
			this.fechaPago = new Date(fechaPagoTime);
		}
		this.fechaPagoTime = fechaPagoTime;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((numeroExpediente == null) ? 0 : numeroExpediente.hashCode());
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
    ExpedienteBaseDTO other = (ExpedienteBaseDTO) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (numeroExpediente == null) {
      if (other.numeroExpediente != null)
        return false;
    } else if (!numeroExpediente.equals(other.numeroExpediente))
      return false;
    return true;
  }

  public String getApiKeyTransaccion() {
		return apiKeyTransaccion;
	}

	public void setApiKeyTransaccion(String apiKeyTransaccion) {
		this.apiKeyTransaccion = apiKeyTransaccion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNombreTitutarTarjeta() {
		return nombreTitutarTarjeta;
	}

	public void setNombreTitutarTarjeta(String nombreTitutarTarjeta) {
		this.nombreTitutarTarjeta = nombreTitutarTarjeta;
	}

	@Override
	public String toString() {
		return "ExpedienteBaseDTO [id=" + id + ", bajaLogica=" + bajaLogica + ", descripcionAdicional="
				+ descripcionAdicional + ", fechaCreacion=" + fechaCreacion + ", fechaInicio=" + fechaInicio
				+ ", apoderado=" + apoderado + ", estadoTramite=" + estadoTramite + ", idExpPadre=" + idExpPadre
				+ ", idInterviniente=" + idInterviniente + ", persona=" + persona + ", motivo=" + motivo
				+ ", pasoActual=" + pasoActual + ", seVaAReintentar=" + seVaAReintentar + ", documentosList="
				+ documentosList + ", tipoTramite=" + tipoTramite + ", tomandoVista=" + tomandoVista + ", turno="
				+ turno + ", ultimoIngreso=" + ultimoIngreso + ", version=" + version + ", apiKeyTransaccion="
				+ apiKeyTransaccion + ", monto=" + monto + ", transaccionPago=" + transaccionPago + ", fechaPago="
				+ fechaPago + ", numeroAutorizacion=" + numeroAutorizacion + ", nombreTitutarTarjeta="
				+ nombreTitutarTarjeta + ", fechaPagoTime=" + fechaPagoTime + ", numeroExpediente=" + numeroExpediente
				+ "]";
	}




}