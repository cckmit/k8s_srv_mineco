package com.egoveris.te.base.model.trata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.WhereJoinTable;

import com.egoveris.te.base.model.PropertyConfiguration;

@Entity
@Table(name = "TRATA_TIPO_RESULTADO")
public class TrataTipoResultado implements Serializable {

  private static final long serialVersionUID = -4211128188912455380L;

  @EmbeddedId
  private TrataTipoResultadoPK pk;

  @Column(name = "ID_TRATA", insertable = false, updatable = false)
  private Long idTrata;
 
  @Column(name = "CLAVE_TIPO_RESULTADO", insertable = false, updatable = false)
  private String clave;
  
  @OneToOne(optional=true)
  @JoinColumn(name="CLAVE_TIPO_RESULTADO", referencedColumnName="clave", insertable = false, updatable = false)
  @WhereJoinTable(clause = "CONFIGURACION = 'SISTEMA'")
  private PropertyConfiguration property;
  
  public TrataTipoResultadoPK getPk() {
    return pk;
  }

  public void setPk(TrataTipoResultadoPK pk) {
    this.pk = pk;
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
  
  public PropertyConfiguration getProperty() {
    return property;
  }

  public void setProperty(PropertyConfiguration property) {
    this.property = property;
  }
}
