package com.egoveris.te.base.model.rest;

public class FormWsDTO {

  // type
  private String type;
  // readonly
  private boolean readonly;
  // key
  private String key;
  // label
  private String label;
  // returnDate
  private String returnData;

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the readonly
   */
  public boolean getReadonly() {
    return readonly;
  }

  /**
   * @param readonly
   *          the readonly to set
   */
  public void setReadonly(boolean readonly) {
    this.readonly = readonly;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @param key
   *          the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label
   *          the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the returnDate
   */
  public String getReturnData() {
    return returnData;
  }

  /**
   * @param returnDate
   *          the returnDate to set
   */
  public void setReturnData(String returnData) {
    this.returnData = returnData;
  }

}
