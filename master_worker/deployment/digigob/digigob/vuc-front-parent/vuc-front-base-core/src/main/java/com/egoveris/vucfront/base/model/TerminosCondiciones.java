package com.egoveris.vucfront.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_TERMINOS_CONDICIONES")
public class TerminosCondiciones {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "CODIGO_CONTENIDO")
  private String codigoContenido;

  @Column(name = "ESTADO")
  private Boolean estado;

  @Column(name = "FECHA")
  private Date fecha;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ID_TIPO_DOCUMENTO")
  private TipoDocumento tipoDoc;

  @Column(name = "VERSION")
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigoContenido() {
    return codigoContenido;
  }

  public void setCodigoContenido(String codigoContenido) {
    this.codigoContenido = codigoContenido;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public TipoDocumento getTipoDoc() {
    return tipoDoc;
  }

  public void setTipoDoc(TipoDocumento tipoDoc) {
    this.tipoDoc = tipoDoc;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

}