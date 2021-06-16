package com.egoveris.te.base.model.rest;

import java.util.List;

public class FormsWsDTO {

  // title
  private String title;
  // FormWsDTO
  private List<FormWsDTO> form;

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the form
   */
  public List<FormWsDTO> getForm() {
    return form;
  }

  /**
   * @param form
   *          the form to set
   */
  public void setForm(List<FormWsDTO> form) {
    this.form = form;
  }

}
