package com.egoveris.te.base.model.tipo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_DOCUMENTO")
public class TipoDocumento {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "ACRONIMO")
  private String acronimo;

  @Column(name = "ESESPECIAL")
  private Boolean esEspecial = false;

  @Column(name = "ID_TIPO_DOCUMENTOSADE")
  private Long idTipoDocumentoSade;

  @Column(name = "CODIGO_TIPO_DOCUMENTOSADE")
  private String codigoTipoDocumentoSade;

  @Column(name = "uso_ee")
  private String usoEnEE;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre
   *          the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the acronimo
   */
  public String getAcronimo() {
    return acronimo;
  }

  /**
   * @param acronimo
   *          the acronimo to set
   */
  public void setAcronimo(String acronimo) {
    this.acronimo = acronimo;
  }

  /**
   * @return the esEspecial
   */
  public Boolean getEsEspecial() {
    return esEspecial;
  }

  /**
   * @param esEspecial
   *          the esEspecial to set
   */
  public void setEsEspecial(Boolean esEspecial) {
    this.esEspecial = esEspecial;
  }

  /**
   * @return the idTipoDocumentoSade
   */
  public Long getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  /**
   * @param idTipoDocumentoSade
   *          the idTipoDocumentoSade to set
   */
  public void setIdTipoDocumentoSade(Long idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
  }

  /**
   * @return the codigoTipoDocumentoSade
   */
  public String getCodigoTipoDocumentoSade() {
    return codigoTipoDocumentoSade;
  }

  /**
   * @param codigoTipoDocumentoSade
   *          the codigoTipoDocumentoSade to set
   */
  public void setCodigoTipoDocumentoSade(String codigoTipoDocumentoSade) {
    this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
  }

  /**
   * @return the usoEnEE
   */
  public String getUsoEnEE() {
    return usoEnEE;
  }

  /**
   * @param usoEnEE
   *          the usoEnEE to set
   */
  public void setUsoEnEE(String usoEnEE) {
    this.usoEnEE = usoEnEE;
  }

}
