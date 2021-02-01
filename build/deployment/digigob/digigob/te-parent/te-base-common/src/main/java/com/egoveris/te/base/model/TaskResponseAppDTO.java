package com.egoveris.te.base.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.model.model.Status;

public class TaskResponseAppDTO {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskResponseAppDTO.class);

	// listAppDTO
	private List<TareaAppDTO> task; 
	// status
	private Status status;

  /**
   * @return the task
   */
  public List<TareaAppDTO> getTask() {
    return task;
  }

  /**
   * @param task
   *          the task to set
   */
  public void setTask(List<TareaAppDTO> task) {
    this.task = task;
  }

  /**
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * @return the logger
   */
  public static Logger getLogger() {
    return logger;
  }
	
} 