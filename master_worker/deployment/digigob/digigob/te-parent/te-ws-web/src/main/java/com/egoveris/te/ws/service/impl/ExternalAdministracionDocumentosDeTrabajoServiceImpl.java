package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.ArchivoDeTrabajoResponse;
import com.egoveris.te.model.model.DocumentoTrabajo;
import com.egoveris.te.ws.service.IAdministracionDocumentosDeTrabajoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalAdministracionDocumentosDeTrabajoServiceImpl implements IAdministracionDocumentosDeTrabajoService {

  @Autowired
  private com.egoveris.te.base.service.iface.IAdministracionDocumentosDeTrabajoService baseService;

  @Override
  public void adjuntarDocumentosTrabajo(String sistemaUsuario, String usuario, String codigoEE,
      List<DocumentoTrabajo> listaDocumentos) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.adjuntarDocumentosTrabajo(sistemaUsuario, usuario, codigoEE, listaDocumentos);
  }

  @Override
  public void desadjuntarDocumentosDeTrabajo(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.desadjuntarDocumentosDeTrabajo(sistemaUsuario, usuario, codigoEE, listaDocumentos);
  }

  @Override
  public ArchivoDeTrabajoResponse obtenerArchivoDeTrabajo(String codigoEE, String usuario,
      String nombre) throws ProcesoFallidoException, ParametroIncorrectoException {
    return baseService.obtenerArchivoDeTrabajo(codigoEE, usuario, nombre);
  }
}
