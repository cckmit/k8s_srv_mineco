package com.egoveris.ffdd.base.service;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;

import java.util.List;

public interface IConstraintService {

  public String toJSON(Object object);

  public Object toObject(String json, String type);

  public FormTriggerDTO createDynamicConstraintComponent(int idFormulario, Object object)
      throws AccesoDatoException;

  /**
   * Permite guardar el objeto DynamicConstraintComponent , si ya existe lo
   * actualiza , tabla DF_DYNAMIC_CONSTRAINT
   *
   * @param dynamicConstraint
   * @throws AccesoDatoException
   */
  public void guardarDynamicConstraint(FormTriggerDTO dynamicConstraint)
      throws AccesoDatoException;

  /**
   * Permite obtener la lista de constraints por el id del componente, devuelve
   * una lista de DynamicConstraintComponent
   *
   * @param id
   * @return
   * @throws AccesoDatoException
   */
  public List<FormTriggerDTO> obtenerConstraintsPorComponente(Integer id)
      throws AccesoDatoException;

  /**
   * Permite obtener la lista de constraints por el id del componente, devuelve
   * una lista de DynamicConstraintComponent
   *
   * @param id
   * @return
   * @throws AccesoDatoException
   */
  public List<FormTriggerDTO> obtenerDynamicConstraintPorTipo(Integer id, String type)
      throws AccesoDatoException;

  /**
   * Permite eliminar todas las constraints de la lista pasada por parametro ,
   * tabla DF_DYNAMIC_CONSTRAINT
   *
   * @param listDynamicConstraintComponents
   * @throws AccesoDatoException
   */
  public void eliminarConstraints(List<FormTriggerDTO> listDynamicConstraintComponents)
      throws AccesoDatoException;

  /**
   * Permite guardar la lista pasada por parametro, tabla DF_DYNAMIC_CONSTRAINT
   *
   * @param listDynamicConstraintComponents
   * @throws AccesoDatoException
   */
  public void guardarListConstraints(List<FormTriggerDTO> listDynamicConstraintComponents)
      throws AccesoDatoException;

  /**
   * Permite modificar las constraints de cada componente de un formulario, se
   * utiliza si ya existe el id
   *
   * @param componente
   * @param listConstraintDTO
   * @throws AccesoDatoException
   */
  public void modificarConstraintList(List<FormTriggerDTO> listaTriggers, int idFormulario)
      throws AccesoDatoException;

  /**
   * Permite convertir el JSON del objeto DynamicConstraintComponent a byte
   *
   * @param dynamicConstraintComp
   * @return
   * @throws AccesoDatoException
   */
  public byte[] jsonToByte(FormTriggerDTO dynamicConstraintComp) throws AccesoDatoException;

  /**
   * Permite convertir el objeto DynamicConstraintComp a DTO
   * 
   * @param dynamicConstraintComp
   * @return
   * @throws AccesoDatoException
   */
  public Object crearObjectDTO(FormTriggerDTO dynamicConstraintComp) throws AccesoDatoException;

  /**
   * Permite modificar las constraints persistidas de un formulario de acuerdo
   * al tipo de este.
   * 
   * @param formulario
   * @param type
   * @throws AccesoDatoException
   */
  public void modificarRestriccionList(FormularioDTO formulario, String type)
      throws AccesoDatoException;

}
