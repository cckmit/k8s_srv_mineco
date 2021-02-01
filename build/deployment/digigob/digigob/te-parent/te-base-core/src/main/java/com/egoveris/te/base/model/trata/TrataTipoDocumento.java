package com.egoveris.te.base.model.trata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "TRATA_TIPO_DOCUMENTO")
public class TrataTipoDocumento {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_TRATA_TIPO_DOCUMENTO")
  private Long idTrataTipoDocumento;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trata")
  private Trata trata;

  @Column(name = "ACRONIMO_GEDO")
  private String acronimoGEDO;

  public Long getIdTrataTipoDocumento() {
    return idTrataTipoDocumento;
  }

  public void setIdTrataTipoDocumento(Long idTrataTipoDocumento) {
    this.idTrataTipoDocumento = idTrataTipoDocumento;
  }

  public Trata getTrata() {
    return trata;
  }

  public void setTrata(Trata trata) {
    this.trata = trata;
  }

  public String getAcronimoGEDO() {
    return acronimoGEDO;
  }

  public void setAcronimoGEDO(String acronimoGEDO) {
    this.acronimoGEDO = acronimoGEDO;
  }

}
