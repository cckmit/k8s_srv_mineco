package com.egoveris.te.base.model;

import com.egoveris.te.model.model.Status;

public class AdvanceTaskAppDTO {
	
	private TareaAppDTO task; 
	private Status status;
	
	private String destinySector;
	private String destinyUser;
	private String destinyOrganism;
	
  public TareaAppDTO getTask() {
    return task;
  }

  public void setTask(TareaAppDTO task) {
    this.task = task;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getDestinySector() {
    return destinySector;
  }

  public void setDestinySector(String destinySector) {
    this.destinySector = destinySector;
  }

  public String getDestinyUser() {
    return destinyUser;
  }

  public void setDestinyUser(String destinyUser) {
    this.destinyUser = destinyUser;
  }

  public String getDestinyOrganism() {
    return destinyOrganism;
  }

  public void setDestinyOrganism(String destinyOrganism) {
    this.destinyOrganism = destinyOrganism;
  }
	
} 