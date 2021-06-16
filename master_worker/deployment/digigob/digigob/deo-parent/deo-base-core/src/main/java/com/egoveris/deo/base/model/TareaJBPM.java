package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JBPM4_TASK")
public class TareaJBPM {

  @Id
  @Column(name = "DBID_")
  private Integer dbID;

  @Column(name = "ASSIGNEE_")
  private String assignee;

  @Column(name = "EXECUTION_ID_")
  private String executionID;

  @Column(name = "EXECUTION_")
  private String activityName;

  @Column(name = "ACTIVITY_NAME_")
  private String execution;

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getExecutionID() {
    return executionID;
  }

  public void setExecutionID(String executionID) {
    this.executionID = executionID;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public Integer getDbID() {
    return dbID;
  }

  public void setDbID(Integer dbID) {
    this.dbID = dbID;
  }

  public String getExecution() {
    return execution;
  }

  public void setExecution(String execution) {
    this.execution = execution;
  }

}
