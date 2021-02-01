package com.egoveris.te.base.model.trata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrataTipoResultadoPK implements Serializable {

  private static final long serialVersionUID = 2509980859551318661L;

  @Column(name = "ID_TRATA")
  private Long idTrata;

  @Column(name = "CLAVE_TIPO_RESULTADO")
  private String claveTipoResultado;
  
  public TrataTipoResultadoPK () {
    //
  }
  
  public TrataTipoResultadoPK (Long idTrata, String claveTipoResultado) {
    this.idTrata = idTrata;
    this.claveTipoResultado = claveTipoResultado;
  }

  public Long getIdTrata() {
    return idTrata;
  }

  public void setIdTrata(Long idTrata) {
    this.idTrata = idTrata;
  }

  public String getClaveTipoResultado() {
    return claveTipoResultado;
  }

  public void setClaveTipoResultado(String claveTipoResultado) {
    this.claveTipoResultado = claveTipoResultado;
  }

}
