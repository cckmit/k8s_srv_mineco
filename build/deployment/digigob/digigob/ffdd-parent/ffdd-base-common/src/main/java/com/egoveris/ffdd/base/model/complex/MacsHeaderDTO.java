package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.model.model.TransaccionDTO;

public class MacsHeaderDTO extends ComplexComponentDTO {

  private static final long serialVersionUID = 1L;
  String controlInfo;
  String serviceCode;
  String userCode;
  String outputMessageCode;
  String messageReceiveTime;
  String idNumber;
  String password;
  String terminalID;
  String spare1;
  String subject;
  String rtpInfo;
  String serverChainInfo;
  String messageInfo;
  String messageControlInfo;
  String specificNumber;
  String indexInfoRtp;
  String indexInfoAp;
  String destinationType;
  String systemID;
  String spare2;
  String businessChainInfo;
  String serverChainInfo2;
  String xmlFlag;
  String spare3;
  String length;

  public MacsHeaderDTO(TransaccionDTO transaccion, String partentName, Integer repeat) {
    super(transaccion, partentName, repeat);
  }

  public MacsHeaderDTO() {
    //constructor
  }

  public String getControlInfo() {
    return controlInfo;
  }

  public void setControlInfo(String controlInfo) {
    this.controlInfo = controlInfo;
  }

  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getOutputMessageCode() {
    return outputMessageCode;
  }

  public void setOutputMessageCode(String outputMessageCode) {
    this.outputMessageCode = outputMessageCode;
  }

  public String getMessageReceiveTime() {
    return messageReceiveTime;
  }

  public void setMessageReceiveTime(String messageReceiveTime) {
    this.messageReceiveTime = messageReceiveTime;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTerminalID() {
    return terminalID;
  }

  public void setTerminalID(String terminalID) {
    this.terminalID = terminalID;
  }

  public String getSpare1() {
    return spare1;
  }

  public void setSpare1(String spare1) {
    this.spare1 = spare1;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getRtpInfo() {
    return rtpInfo;
  }

  public void setRtpInfo(String rtpInfo) {
    this.rtpInfo = rtpInfo;
  }

  public String getServerChainInfo() {
    return serverChainInfo;
  }

  public void setServerChainInfo(String serverChainInfo) {
    this.serverChainInfo = serverChainInfo;
  }

  public String getMessageInfo() {
    return messageInfo;
  }

  public void setMessageInfo(String messageInfo) {
    this.messageInfo = messageInfo;
  }

  public String getMessageControlInfo() {
    return messageControlInfo;
  }

  public void setMessageControlInfo(String messageControlInfo) {
    this.messageControlInfo = messageControlInfo;
  }

  public String getSpecificNumber() {
    return specificNumber;
  }

  public void setSpecificNumber(String specificNumber) {
    this.specificNumber = specificNumber;
  }

  public String getIndexInfoRtp() {
    return indexInfoRtp;
  }

  public void setIndexInfoRtp(String indexInfoRtp) {
    this.indexInfoRtp = indexInfoRtp;
  }

  public String getIndexInfoAp() {
    return indexInfoAp;
  }

  public void setIndexInfoAp(String indexInfoAp) {
    this.indexInfoAp = indexInfoAp;
  }

  public String getDestinationType() {
    return destinationType;
  }

  public void setDestinationType(String destinationType) {
    this.destinationType = destinationType;
  }

  public String getSystemID() {
    return systemID;
  }

  public void setSystemID(String systemID) {
    this.systemID = systemID;
  }

  public String getSpare2() {
    return spare2;
  }

  public void setSpare2(String spare2) {
    this.spare2 = spare2;
  }

  public String getBusinessChainInfo() {
    return businessChainInfo;
  }

  public void setBusinessChainInfo(String businessChainInfo) {
    this.businessChainInfo = businessChainInfo;
  }

  public String getServerChainInfo2() {
    return serverChainInfo2;
  }

  public void setServerChainInfo2(String serverChainInfo2) {
    this.serverChainInfo2 = serverChainInfo2;
  }

  public String getXmlFlag() {
    return xmlFlag;
  }

  public void setXmlFlag(String xmlFlag) {
    this.xmlFlag = xmlFlag;
  }

  public String getSpare3() {
    return spare3;
  }

  public void setSpare3(String spare3) {
    this.spare3 = spare3;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("MacsHeaderDTO [controlInfo=").append(controlInfo).append(", serviceCode=")
        .append(serviceCode).append(", userCode=").append(userCode).append(", outputMessageCode=")
        .append(outputMessageCode).append(", messageReceiveTime=").append(messageReceiveTime)
        .append(", idNumber=").append(idNumber).append(", password=").append(password)
        .append(", terminalID=").append(terminalID).append(", spare1=").append(spare1)
        .append(", subject=").append(subject).append(", rtpInfo=").append(rtpInfo)
        .append(", serverChainInfo=").append(serverChainInfo).append(", messageInfo=")
        .append(messageInfo).append(", messageControlInfo=").append(messageControlInfo)
        .append(", specificNumber=").append(specificNumber).append(", indexInfoRtp=")
        .append(indexInfoRtp).append(", indexInfoAp=").append(indexInfoAp)
        .append(", destinationType=").append(destinationType).append(", systemID=")
        .append(systemID).append(", spare2=").append(spare2).append(", businessChainInfo=")
        .append(businessChainInfo).append(", serverChainInfo2=").append(serverChainInfo2)
        .append(", xmlFlag=").append(xmlFlag).append(", spare3=").append(spare3)
        .append(", length=").append(length).append("]");
    return builder.toString();
  }
}