package com.egoveris.te.base.model;

import java.io.Serializable;

public class TrataTipoResultadoDTO implements Serializable {

  private static final long serialVersionUID = -2480652985893691838L;

  private Long idTrata;
  private String clave;
  private PropertyConfigurationDTO property;

  public TrataTipoResultadoDTO() {
    //
  }
  
  public TrataTipoResultadoDTO(Long idTrata, String clave) {
    this.idTrata = idTrata;
    this.clave = clave;
  }
  
  public Long getIdTrata() {
    return idTrata;
  }

  public void setIdTrata(Long idTrata) {
    this.idTrata = idTrata;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public PropertyConfigurationDTO getProperty() {
    return property;
  }

  public void setProperty(PropertyConfigurationDTO property) {
    this.property = property;
  }

}
