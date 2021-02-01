package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class HsCodeMatrizDTO extends AbstractCComplejoDTO implements Serializable{

  private static final long serialVersionUID = 1L;
  
  
  protected Long idHSCodeMatriz;
  protected Long idMatrizVB;
  protected Long idHSCode;
  
  
  public Long getIdHSCodeMatriz() {
    return idHSCodeMatriz;
  }
  public void setIdHSCodeMatriz(Long idHSCodeMatriz) {
    this.idHSCodeMatriz = idHSCodeMatriz;
  }
  public Long getIdMatrizVB() {
    return idMatrizVB;
  }
  public void setIdMatrizVB(Long idMatrizVB) {
    this.idMatrizVB = idMatrizVB;
  }
  public Long getIdHSCode() {
    return idHSCode;
  }
  public void setIdHSCode(Long idHSCode) {
    this.idHSCode = idHSCode;
  }
  
}
