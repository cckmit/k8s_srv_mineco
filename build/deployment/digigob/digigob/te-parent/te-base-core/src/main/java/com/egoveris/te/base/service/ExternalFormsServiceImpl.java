package com.egoveris.te.base.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

@Service("externalFormsService")
public class ExternalFormsServiceImpl implements IExternalFormsService {

  private static transient Logger logger = LoggerFactory.getLogger(ExternalFormsServiceImpl.class);

  @Autowired
  private ExternalTransaccionService transaccionService;

  @Autowired
  private ExternalFormularioService formularioService;

  @Override
  public FormularioDTO buscarFormularioNombreFFDD(
      final ExpedienteFormularioDTO expedienteFormulario) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarFormularioNombreFFDD(expedienteFormulario={}) - start",
          expedienteFormulario);
    }

    final FormularioDTO form = this.formularioService
        .buscarFormularioPorNombre(expedienteFormulario.getFormName());
    logger.info(form.getDescripcion());
    return form;
  }

  @Override
  public List<FormularioDTO> buscarFormulariosFFDD() throws DynFormException {
    logger.info("ingresamos buscarFormulario");
    final List<FormularioDTO> listFormulario = this.formularioService
        .obtenerTodosLosFormulariosSinRelaciones();

    if (logger.isDebugEnabled()) {
      logger.debug("buscarFormulariosFFDD() - end - return value={}", listFormulario);
    }
    return listFormulario;
  }

  @Override
  public TransaccionDTO buscarTransaccionPorIdFFDD(final Integer idTransaction)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTransaccionPorIdFFDD(idTransaction={}) - start", idTransaction);
    }

    TransaccionDTO transaccionDTO;
    transaccionDTO = this.transaccionService.buscarTransaccionPorUUID(idTransaction);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTransaccionPorIdFFDD(Integer) - end - return value={}", transaccionDTO);
    }
    return transaccionDTO;
  }

  @Override
  public void eliminarFormularioFFDD(final ExpedienteFormularioDTO expedienteFormulario)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormularioFFDD(expedienteFormulario={}) - start",
          expedienteFormulario);
    }

    final TransaccionDTO transaccion = new TransaccionDTO();
    transaccion.setUuid(expedienteFormulario.getIdDfTransaction());
    this.transaccionService.deleteFormWebExpt(transaccion);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormularioFFDD(ExpedienteFormulario) - end");
    }
  }

}
