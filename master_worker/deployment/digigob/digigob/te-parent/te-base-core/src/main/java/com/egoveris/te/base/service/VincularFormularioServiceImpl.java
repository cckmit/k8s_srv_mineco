package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IVincularFormularioService;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;
import com.egoveris.te.model.model.VincularFormularioRequest;

@Service("vincularFormularioService")
public class VincularFormularioServiceImpl implements IVincularFormularioService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  IExpedienteFormularioService expedienteFormularioService;

  @Transactional
  public void vincularExpediente(VincularFormularioRequest vincularFormularioRequest)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("vincularExpediente(vincularFormularioRequest={}) - start",
          vincularFormularioRequest);
    }

    List<ExpedienteFormularioDTO> listExpedienteFromulario = transformRequestToExpedienteFormulario(
        vincularFormularioRequest);
    for (ExpedienteFormularioDTO expedienteFormularioTemp : listExpedienteFromulario) {
      try {
        expedienteFormularioService.guardarFormulario(expedienteFormularioTemp);
      } catch (DynFormException e) {
        logger.error("Error guardando formulario expediente ", e);
        throw new ProcesoFallidoException("Error guardando formulario del expediente", e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("vincularExpediente(VincularFormularioRequest) - end");
    }
  }

  private List<ExpedienteFormularioDTO> transformRequestToExpedienteFormulario(
      VincularFormularioRequest vincularFormularioRequest) {
    if (logger.isDebugEnabled()) {
      logger.debug("transformRequestToExpedienteFormulario(vincularFormularioRequest={}) - start",
          vincularFormularioRequest);
    }

    List<ExpedienteFormularioDTO> listExpedienteFromulario = new ArrayList<ExpedienteFormularioDTO>();

    for (ExpedienteFormularioDTO expedienteFormularioDTO : vincularFormularioRequest
        .getExpedienteFormularioDTO()) {
      ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
      expedienteFormulario.setDateCration(expedienteFormularioDTO.getDateCration());
      expedienteFormulario.setFormName(expedienteFormularioDTO.getFormName());
      expedienteFormulario.setIdDefForm(expedienteFormularioDTO.getIdDefForm());
      expedienteFormulario.setIdDfTransaction(expedienteFormularioDTO.getIdDfTransaction());
      expedienteFormulario.setIdEeExpedient(expedienteFormularioDTO.getIdEeExpedient());
      expedienteFormulario.setIsDefinitive(expedienteFormularioDTO.getIsDefinitive());
      expedienteFormulario.setOrganism(expedienteFormularioDTO.getOrganism());
      expedienteFormulario.setUserCreation(expedienteFormularioDTO.getUserCreation());
      listExpedienteFromulario.add(expedienteFormulario);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "transformRequestToExpedienteFormulario(VincularFormularioRequest) - end - return value={}",
          listExpedienteFromulario);
    }
    return listExpedienteFromulario;
  }

}
