package com.egoveris.te.base.model;

import com.egoveris.te.model.model.ValidateUser;

public class TaskUserAppDTO {
	
	private TareaAppDTO task;
	private ValidateUser user;
	
  public TareaAppDTO getTask() {
    return task;
  }

  public void setTask(TareaAppDTO task) {
    this.task = task;
  }

  public ValidateUser getUser() {
    return user;
  }

  public void setUser(ValidateUser user) {
    this.user = user;
  }
	
} 