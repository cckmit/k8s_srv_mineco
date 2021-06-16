package com.egoveris.ffdd.base.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.service.IConstraintService;
import com.egoveris.ffdd.base.service.IFormDynamicService;
import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.google.gson.Gson;

@Service("constraintService")
@Transactional
public class ConstraintServiceImpl implements IConstraintService {


  @Autowired
  private IFormDynamicService formDynamicService;

  @Override
  public String toJSON(final Object object) {
    final Gson gson = new Gson();
    final String json = gson.toJson(object); 
    return StringEscapeUtils.escapeJson(json);
  }

  @Override
  public Object toObject(final String json, final String type) {
    final Gson gson = new Gson();
    Object object = null;
    if (type.equals(ConstantesConstraint.TYPE_CONSTRAINTDTO)) {
      object = gson.fromJson(StringEscapeUtils.unescapeJson(json), ConstraintDTO.class);
    }
    if (type.equals(ConstantesConstraint.TYPE_RESTRICCIONDTO)) {
      object = gson.fromJson(StringEscapeUtils.unescapeJson(json), VisibilidadComponenteDTO.class);
    }
    return object;
  }

  @Override
  public FormTriggerDTO createDynamicConstraintComponent(final int idFormulario,
      final Object object) throws AccesoDatoException {
    final FormTriggerDTO formTrigger = new FormTriggerDTO();
    formTrigger.setJson(crearBlobConstraint(object));
    formTrigger.setIdForm(idFormulario);
    formTrigger.setFechaCreacion(new Timestamp(new Date().getTime()));
    setearID(object, formTrigger);
    return formTrigger;
  }

  private void setearID(final Object object, final FormTriggerDTO dynamicConstraintComponent) {
    if (object instanceof ConstraintDTO) {
      final ConstraintDTO constraintDTO = (ConstraintDTO) object;
      dynamicConstraintComponent.setId(constraintDTO.getId());
    }
    if (object instanceof VisibilidadComponenteDTO) {
      final VisibilidadComponenteDTO restriccionDTO = (VisibilidadComponenteDTO) object;
      dynamicConstraintComponent.setId(restriccionDTO.getId());
    }
  }

  /**
   * Permite crear el objeto blob para guardar en la tabla DF_DYNAMIC_CONSTRAINT
   * (el objeto blob, contiene el json)
   *
   * @param constraintDTO
   * @return
   * @throws AccesoDatoException
   */
  private String crearBlobConstraint(final Object object) throws AccesoDatoException {
    return toJSON(object);
  }

  @Override
  public void guardarDynamicConstraint(final FormTriggerDTO dynamicConstraint)
      throws AccesoDatoException {
    this.getFormDynamicService().guardar(dynamicConstraint);
  }

  @Override
  public List<FormTriggerDTO> obtenerConstraintsPorComponente(final Integer id)
      throws AccesoDatoException {
    return getFormDynamicService().obtenerConstraintsPorComponente(id);
  }

  @Override
  public void eliminarConstraints(final List<FormTriggerDTO> listDynamicConstraintComponents)
      throws AccesoDatoException {
    for (final FormTriggerDTO dynamicConstraintComponent : listDynamicConstraintComponents) {
      getFormDynamicService().eliminarDynamicConstraintComponent(dynamicConstraintComponent);
    }
  }

  @Override
  public void guardarListConstraints(final List<FormTriggerDTO> listDynamicConstraintComponents)
      throws AccesoDatoException {
    for (final FormTriggerDTO dynamicConstraintComponent : listDynamicConstraintComponents) {
      getFormDynamicService().guardar(dynamicConstraintComponent);
    }
  }

  @Override
  public void modificarConstraintList(final List<FormTriggerDTO> listaTriggers,
      final int idFormulario) throws AccesoDatoException {
     List<FormTriggerDTO> listaConstraintsPersisistidas = obtenerDynamicConstraintPorTipo(idFormulario,
        ConstantesConstraint.TYPE_CONSTRAINTDTO);
    eliminarConstraints(listaConstraintsPersisistidas);
    guardarListConstraints(listaTriggers);
  }

  @Override
  public void modificarRestriccionList(final FormularioDTO formulario, final String type)
      throws AccesoDatoException {
     List<FormTriggerDTO>listaConstraintsPersisistidas = obtenerDynamicConstraintPorTipo(formulario.getId(), type);
    final List<FormTriggerDTO> listaTrigger = new ArrayList<>();
    for (final VisibilidadComponenteDTO restriccion : formulario.getListaComponentesOcultos()) {
      final FormTriggerDTO trigger = createDynamicConstraintComponent(formulario.getId(),
          restriccion);
      trigger.setType(ConstantesConstraint.TYPE_RESTRICCIONDTO);
      listaTrigger.add(trigger);
    }
    eliminarConstraints(listaConstraintsPersisistidas);
    guardarListConstraints(listaTrigger);
  }

  @Override
  public byte[] jsonToByte(final FormTriggerDTO dynamicConstraintComp) throws AccesoDatoException {
    return dynamicConstraintComp.getJson().getBytes();
  }

  @Override
  public Object crearObjectDTO(final FormTriggerDTO dynamicConstraintComp)
      throws AccesoDatoException {
    final String blobString = new String(jsonToByte(dynamicConstraintComp));
    if (dynamicConstraintComp.getType().equals(ConstantesConstraint.TYPE_CONSTRAINTDTO)) {
      final ConstraintDTO constraintDTO = (ConstraintDTO) toObject(blobString,
          ConstantesConstraint.TYPE_CONSTRAINTDTO);
      constraintDTO.setId(dynamicConstraintComp.getId());
      return constraintDTO;
    } else {
      final VisibilidadComponenteDTO restriccionDTO = (VisibilidadComponenteDTO) toObject(
          blobString, ConstantesConstraint.TYPE_RESTRICCIONDTO);
      restriccionDTO.setId(dynamicConstraintComp.getId());
      return restriccionDTO;
    }
  }

  @Override
  public List<FormTriggerDTO> obtenerDynamicConstraintPorTipo(final Integer id, final String type)
      throws AccesoDatoException {
    return getFormDynamicService().obtenerDynamicComponentPorTipo(id, type);
  }

  public IFormDynamicService getFormDynamicService() {
    return formDynamicService;
  }

  public void setFormDynamicService(final IFormDynamicService formDynamicService) {
    this.formDynamicService = formDynamicService;
  }

}
