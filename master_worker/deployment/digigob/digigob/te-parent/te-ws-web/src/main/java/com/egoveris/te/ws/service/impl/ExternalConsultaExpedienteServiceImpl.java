package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CaratulaVariableResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponseDetallado;
import com.egoveris.te.model.model.ExpedienteElectronicoResponse;
import com.egoveris.te.model.model.HistorialDePasesResponse;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEERequest;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEEResponse;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;
import com.egoveris.te.ws.service.IConsultaExpedienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalConsultaExpedienteServiceImpl implements IConsultaExpedienteService {

  @Autowired
  private com.egoveris.te.base.service.iface.IConsultaExpedienteService baseService;

  @Override
  public boolean validarEE(String codigoEE) {
    return baseService.validarEE(codigoEE);
  }

  @Override
  public ConsultaExpedienteResponse consultaEE(String codigoEE) throws ProcesoFallidoException,
      ExpedienteInexistenteException, ParametroIncorrectoException {
    return baseService.consultaEE(codigoEE);
  }

  @Override
  public ConsultaExpedienteResponseDetallado consultaEEDetallado(String codigoEE)
      throws ProcesoFallidoException, ExpedienteInexistenteException,
      ParametroIncorrectoException {
    return baseService.consultaEEDetallado(codigoEE);
  }

  @Override
  public ConsultaExpedienteResponse consultarDatosExpedientePorCodigosDeTrata(
      List<String> listaDeCodigosTrata, String expedienteEstado, String expedienteUsuarioAsignado)
      throws ProcesoFallidoException {
    return baseService.consultarDatosExpedientePorCodigosDeTrata(listaDeCodigosTrata,
        expedienteEstado, expedienteUsuarioAsignado);
  }

  @Override
  public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalMayorA24Meses(
      Integer cantidadDeDias) {
    return baseService.obtenerExpedientesEnGuardaTemporalMayorA24Meses(cantidadDeDias);
  }

  @Override
  public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(
      String codExpediente) {
    return baseService.obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(codExpediente);
  }

  @Override
  public ConsultaExpedienteResponse obtenerCaratulaPorNumeroExpediente(String expedienteCodigo)
      throws ProcesoFallidoException {
    return baseService.obtenerCaratulaPorNumeroExpediente(expedienteCodigo);
  }

  @Override
  public ObtenerCaratulaPorCodigoEEResponse obtenerCaratulaPorCodigoEE(
      ObtenerCaratulaPorCodigoEERequest request) throws ProcesoFallidoException {
    return baseService.obtenerCaratulaPorCodigoEE(request);
  }

  @Override
  public HistorialDePasesResponse obtenerHistorialDePasesDeExpediente(String codigoEE)
      throws ParametroIncorrectoException {
    return baseService.obtenerHistorialDePasesDeExpediente(codigoEE);
  }

  @Override
  public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen,
      String reparticion) throws ParametroIncorrectoException {
    return baseService.consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen, reparticion);
  }

  @Override
  public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen,
      String usuario) throws ParametroIncorrectoException {
    return baseService.consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen, usuario);
  }

  @Override
  public int consultaIdFCPorExpediente(String codigoEE)
      throws ParametroIncorrectoException, ExpedienteInexistenteException {
    return baseService.consultaIdFCPorExpediente(codigoEE).intValue();
  }

  @Override
  public CaratulaVariableResponse obtenerDatosCaratulaVariable(String codigoEE, String usuario)
      throws ParametroIncorrectoException {
    return baseService.obtenerDatosCaratulaVariable(codigoEE, usuario);
  }

  @Override
  public byte[] obtenerDocumentoCaratulaVariable(String codigoEE, String usuario)
      throws ParametroIncorrectoException {
    return baseService.obtenerDocumentoCaratulaVariable(codigoEE, usuario);
  }
}