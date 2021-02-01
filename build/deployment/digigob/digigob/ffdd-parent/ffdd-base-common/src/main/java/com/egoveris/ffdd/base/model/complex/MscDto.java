
package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;

import java.util.Arrays;

public class MscDto extends ComplexComponentDTO {
  /**
  * 
  */
  private String tmp;
  private String sno;
  private byte[] data;
  
  private static final long serialVersionUID = 1L;

  public MscDto() {
    //constructor 
  }

  public MscDto(TransaccionDTO transaccion, String partentName, Integer i) {
    super(transaccion, partentName, i);
    for (ValorFormCompDTO value : transaccion.getValorFormComps()) {
      if (value.getArchivo() != null)
        this.data = value.getArchivo().getData();
    }
  }

  public String getTmp() {
    return tmp;
  }

  public void setTmp(String tmp) {
    this.tmp = tmp;
  }

  public String getSno() {
    return sno;
  }

  public void setSno(String sno) {
    this.sno = sno;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("MscDto [tmp=").append(tmp).append(", sno=").append(sno).append(", data=")
        .append(Arrays.toString(data)).append("]");
    return builder.toString();
  }
}