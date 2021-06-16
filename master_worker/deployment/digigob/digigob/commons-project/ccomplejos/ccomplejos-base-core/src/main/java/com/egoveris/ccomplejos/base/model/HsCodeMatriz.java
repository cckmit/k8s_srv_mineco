package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_HS_CODE_MATRIZ_CC")
public class HsCodeMatriz extends AbstractCComplejoJPA {

  @Column(name = "ID_HSCODE_MATRIZ")
  Long idcodeMatriz;

  @Column(name = "ID_MATRIZ_VB")
  Long idMatrizVB;
  
  @Column(name = "ID_HS_CODE")
  Long idHsCode;
  
  
  

  public Long getIdcodeMatriz() {
    return idcodeMatriz;
  }

  public void setIdcodeMatriz(Long idcodeMatriz) {
    this.idcodeMatriz = idcodeMatriz;
  }

  public Long getIdMatrizVB() {
    return idMatrizVB;
  }

  public void setIdMatrizVB(Long idMatrizVB) {
    this.idMatrizVB = idMatrizVB;
  }

  public Long getIdHsCode() {
    return idHsCode;
  }

  public void setIdHsCode(Long idHsCode) {
    this.idHsCode = idHsCode;
  }

}
