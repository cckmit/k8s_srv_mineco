package com.egoveris.te.base.model;

import com.egoveris.te.base.model.rest.FormsWsDTO;
import com.egoveris.te.model.model.ValidateUser;

public class SaveFormRequestAppDTO {

  private FormsWsDTO formInput;
  private TareaAppDTO task;
  private ValidateUser user;

  /**
   * @return the formInput
   */
  public FormsWsDTO getFormInput() {
    return formInput;
  }

  /**
   * @param formInput
   *          the formInput to set
   */
  public void setFormInput(FormsWsDTO formInput) {
    this.formInput = formInput;
  }

  /**
   * @return the task
   */
  public TareaAppDTO getTask() {
    return task;
  }

  /**
   * @param task
   *          the task to set
   */
  public void setTask(TareaAppDTO task) {
    this.task = task;
  }

  /**
   * @return the user
   */
  public ValidateUser getUser() {
    return user;
  }

  /**
   * @param user
   *          the user to set
   */
  public void setUser(ValidateUser user) {
    this.user = user;
  }

}
