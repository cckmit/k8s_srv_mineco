package com.egoveris.ffdd.base.service;

import com.egoveris.ffdd.model.model.FormTriggerDTO;

import java.util.List;

public interface IFormDynamicService {

  /**
   * Stores a form trigger object
   * 
   * @param formTrigger
   */
  public void guardar(FormTriggerDTO constraint);

  /**
   * find all constraints by component id
   * 
   * @param id
   * @return list
   */
  public List<FormTriggerDTO> obtenerConstraintsPorComponente(Integer id);

  /**
   * Find all dynamic components by type for a given component id.
   * 
   * @param id
   * @param type
   * @return list
   */
  public List<FormTriggerDTO> obtenerDynamicComponentPorTipo(Integer id, String type);

  /**
   * Delete an specific dynamic component
   * 
   * @param dynamicConstraintComponent
   */
  public void eliminarDynamicConstraintComponent(FormTriggerDTO dynamicConstraintComponent);

  /**
   * update an existing dynamic component.
   * 
   * @param constraint
   */
  public void modificarDynamicConstraintComponente(FormTriggerDTO constraint);

}
