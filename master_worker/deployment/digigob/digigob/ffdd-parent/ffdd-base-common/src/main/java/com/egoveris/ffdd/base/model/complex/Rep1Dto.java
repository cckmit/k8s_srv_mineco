
package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.model.model.TransaccionDTO;

public class Rep1Dto extends ComplexComponentDTO {
  /**
  * 
  */
  private String cltId;
  private String cltKey;
  private String stFlg;
  
  private static final long serialVersionUID = 1L;

  public Rep1Dto() {
    //constructor
  }


  public Rep1Dto(TransaccionDTO transaccion, String partentName, Integer i) {
    super(transaccion, partentName, i);
  }

  public String getCltId() {
    return cltId;
  }

  public void setCltId(String cltId) {
    this.cltId = cltId;
  }

  public String getCltKey() {
    return cltKey;
  }

  public void setCltKey(String cltKey) {
    this.cltKey = cltKey;
  }

  public String getStFlg() {
    return stFlg;
  }

  public void setStFlg(String stFlg) {
    this.stFlg = stFlg;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Rep1Dto [cltId=").append(cltId).append(", cltKey=").append(cltKey)
        .append(", stFlg=").append(stFlg).append("]");
    return builder.toString();
  }
}