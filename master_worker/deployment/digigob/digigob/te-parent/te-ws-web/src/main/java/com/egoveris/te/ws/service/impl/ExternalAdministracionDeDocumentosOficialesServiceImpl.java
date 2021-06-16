package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RequestRelacionDocumentoOficialEE;
import com.egoveris.te.model.model.VinculacionDefinitivaDeDocsRequest;
import com.egoveris.te.ws.service.IAdministracionDeDocumentosOficialesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExternalAdministracionDeDocumentosOficialesServiceImpl
    implements IAdministracionDeDocumentosOficialesService {

  @Autowired
  @Qualifier("administracionDeDocumentosOficialesServiceImpl")
  private com.egoveris.te.base.service.iface.IAdministracionDeDocumentosOficialesService baseService;

  @Override
  public void vincularDocumentosOficiales(String sistemaUsuario, String usuario, String codigoEE,
      List<String> listaDocumentos) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.vincularDocumentosOficiales(sistemaUsuario, usuario, codigoEE, listaDocumentos);
  }

  @Override
  public void desvincularDocumentosOficiales(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.desvincularDocumentosOficiales(sistemaUsuario, usuario, codigoEE, listaDocumentos);
  }

  @Override
  public void eliminarDocumentosNoDefinitivos(String sistemaUsuario, String usuario,
      String codigoEE) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.eliminarDocumentosNoDefinitivos(sistemaUsuario, usuario, codigoEE);
  }

  @Override
  public void vincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE request)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.vincularDocumentosOficialesNumeroEspecial(request);
  }

  @Override
  public void desvincularDocumentosOficialesNumeroEspecial(
      RequestRelacionDocumentoOficialEE request) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.desvincularDocumentosOficialesNumeroEspecial(request);
  }

  @Override
  public void vincularDocumentosOficialesConTransaccionFC(String sistemaUsuario, String usuario,
      String codigoEE, String documento, Long idTransaccionFC) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.vincularDocumentosOficialesConTransaccionFC(sistemaUsuario, usuario, codigoEE, documento, idTransaccionFC);
  }

  @Override
  public void vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(String sistemaUsuario,
      String usuario, String codigoEE, List<String> listaDocumentos)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(sistemaUsuario, usuario, codigoEE, listaDocumentos);
  }

  @Override
  public void hacerDefinitivosDocsDeEE(VinculacionDefinitivaDeDocsRequest request)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.hacerDefinitivosDocsDeEE(request);
  };



}