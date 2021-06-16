package com.egoveris.te.base.model.tipo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lfishkel
 *
 */
@Entity
@Table(name = "TIPO_DOCUMENTO_VINCULADO")
public class TipoDocumentoGenerador {

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "NOMBRE")
  private String tipoDocGenerador;

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
   * @return the tipoDocGenerador
   */
  public String getTipoDocGenerador() {
    return tipoDocGenerador;
  }

  /**
   * @param tipoDocGenerador
   *          the tipoDocGenerador to set
   */
  public void setTipoDocGenerador(String tipoDocGenerador) {
    this.tipoDocGenerador = tipoDocGenerador;
  }

}