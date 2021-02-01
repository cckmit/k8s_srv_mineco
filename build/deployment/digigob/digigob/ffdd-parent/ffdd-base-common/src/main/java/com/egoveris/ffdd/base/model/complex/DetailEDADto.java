
package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.model.model.TransaccionDTO;

public class DetailEDADto extends ComplexComponentDTO {

  String cmd;

  String cm2;

  String cmn;

  String or;

  String qn1;

  String qt1;

  String qn2;

  String qt2;

  String cmp;

  String bpc;

  String bpk;

  String ect;

  String eac;

  public DetailEDADto() {
    //constructor
  }

  public DetailEDADto(TransaccionDTO transaccion, String partentName, Integer repetition) {
    super(transaccion, partentName, repetition);
  }

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public String getCm2() {
    return cm2;
  }

  public void setCm2(String cm2) {
    this.cm2 = cm2;
  }

  public String getCmn() {
    return cmn;
  }

  public void setCmn(String cmn) {
    this.cmn = cmn;
  }

  public String getOr() {
    return or;
  }

  public void setOr(String or) {
    this.or = or;
  }

  public String getQn1() {
    return qn1;
  }

  public void setQn1(String qn1) {
    this.qn1 = qn1;
  }

  public String getQt1() {
    return qt1;
  }

  public void setQt1(String qt1) {
    this.qt1 = qt1;
  }

  public String getQn2() {
    return qn2;
  }

  public void setQn2(String qn2) {
    this.qn2 = qn2;
  }

  public String getQt2() {
    return qt2;
  }

  public void setQt2(String qt2) {
    this.qt2 = qt2;
  }

  public String getCmp() {
    return cmp;
  }

  public void setCmp(String cmp) {
    this.cmp = cmp;
  }

  public String getBpc() {
    return bpc;
  }

  public void setBpc(String bpc) {
    this.bpc = bpc;
  }

  public String getBpk() {
    return bpk;
  }

  public void setBpk(String bpk) {
    this.bpk = bpk;
  }

  public String getEct() {
    return ect;
  }

  public void setEct(String ect) {
    this.ect = ect;
  }

  public String getEac() {
    return eac;
  }

  public void setEac(String eac) {
    this.eac = eac;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DetailEDADto [cmd=").append(cmd).append(", cm2=").append(cm2).append(", cmn=")
        .append(cmn).append(", or=").append(or).append(", qn1=").append(qn1).append(", qt1=")
        .append(qt1).append(", qn2=").append(qn2).append(", qt2=").append(qt2).append(", cmp=")
        .append(cmp).append(", bpc=").append(bpc).append(", bpk=").append(bpk).append(", ect=")
        .append(ect).append(", eac=").append(eac).append("]");
    return builder.toString();
  }
}