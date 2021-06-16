
package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.model.model.TransaccionDTO;

public class CommonEDCDto extends ComplexComponentDTO {
  
  private String jyo;

  public CommonEDCDto() {
    //Constructor
  }

  
  public CommonEDCDto(TransaccionDTO transaccion, String partentName, Integer i) {
    super(transaccion, partentName, i);
  }

  public String getJyo() {
    return jyo;
  }

  public void setJyo(String jyo) {
    this.jyo = jyo;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CommonEDCDto [jyo=").append(jyo).append("]");
    return builder.toString();
  }
}