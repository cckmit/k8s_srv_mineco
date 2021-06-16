package com.egoveris.vucfront.base.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TAD_EXPEDIENTE_BASE")
public class ExpedienteBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "BAJA_LOGICA")
  private Long bajaLogica;

  @Column(name = "DESCRIPCION_ADICIONAL")
  private String descripcionAdicional;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_INICIO")
  private Date fechaInicio;

  @OneToOne
  @JoinColumn(name = "ID_APODERADO")
  private Apoderado apoderado;

  @OneToOne
  @JoinColumn(name = "ID_ESTADO_TRAMITE")
  private EstadoTramite estadoTramite;

  @Column(name = "ID_EXP_PADRE")
  private Long idExpPadre;

  @Column(name = "ID_INTERVINIENTE")
  private Long idInterviniente;

  @ManyToOne
  @JoinColumn(name = "ID_PERSONA")
  private Persona persona;

  @Column(name = "MOTIVO")
  private String motivo;


  @Column(name = "PASO_ACTUAL")
  private Long pasoActual;

  @Column(name = "SE_VA_A_REINTENTAR")
  private Boolean seVaAReintentar;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "TAD_EXPEDIENTE_DOCUMENTO", joinColumns = {
      @JoinColumn(name = "ID_EXPEDIENTE") }, inverseJoinColumns = {
          @JoinColumn(name = "ID_DOCUMENTO") })
  private List<Documento> documentosList;


  @ManyToOne
  @JoinColumn(name = "TIPO_TRAMITE_ID")
  private TipoTramite tipoTramite;

  @Column(name = "TOMANDO_VISTA")
  private Long tomandoVista;

  @Column(name = "TURNO")
  private String turno;

  @Column(name = "ultimo_ingreso")
  private Date ultimoIngreso;

  @Column(name = "VERSION")
  private Long version;
  
  @Column(name = "TRANSACCION_PAGO")
  private String transaccionPago;
  
  @Column(name = "FECHA_PAGO")
  private Date fechaPago;
  
  @Column(name= "NUMERO_AUTORIZACION")
  private String numeroAutorizacion;
  
  @Column(name = "APIKEY_TRANSACCION")
  private String apiKeyTransaccion;
  
  @Column(name = "TITULAR_TARJETA")
  private String nombreTitutarTarjeta;
  
  @Column(name = "MONTO", precision = 10, scale = 2)
  private BigDecimal monto;

  @Column(name = "NUMERO_EXPEDIENTE")
  private String numeroExpediente;
  
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

  public Apoderado getApoderado() {
    return apoderado;
  }

  public void setApoderado(Apoderado apoderado) {
    this.apoderado = apoderado;
  }

  public EstadoTramite getEstadoTramite() {
    return estadoTramite;
  }

  public void setEstadoTramite(EstadoTramite estadoTramite) {
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

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
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

  public List<Documento> getDocumentosList() {
    return documentosList;
  }

  public void setDocumentosList(List<Documento> documentosList) {
    this.documentosList = documentosList;
  }

  public TipoTramite getTipoTramite() {
    return tipoTramite;
  }

  public void setTipoTramite(TipoTramite tipoTramite) {
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

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}


}