package com.egoveris.te.base.model.trata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRATA_TIPO_DOCUMENTO_AUDITORIA")
public class TrataTipoDocumentoAuditoria {

  @Id
  @Column(name = "ID_TRATA_TIPO_DOCUMENTO")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idTrataTipoDocumento;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModif;

  @Column(name = "ACRONIMO_GEDO")
  private String acronimoGEDO;

  @Column(name = "ESTADO")
  private String estado;
  
  @ManyToOne
  @JoinColumn(name = "TRATA")
  private Trata trata;

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

  public Date getFechaModif() {
    return fechaModif;
  }

  public void setFechaModif(Date fechaModif) {
    this.fechaModif = fechaModif;
  }

  /**
   * @return the trata
   */
  public Trata getTrata() {
    return trata;
  }

  /**
   * @param trata
   *          the trata to set
   */
  public void setTrata(Trata trata) {
    this.trata = trata;
  }

  public String getAcronimoGEDO() {
    return acronimoGEDO;
  }

  public void setAcronimoGEDO(String acronimoGEDO) {
    this.acronimoGEDO = acronimoGEDO;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}