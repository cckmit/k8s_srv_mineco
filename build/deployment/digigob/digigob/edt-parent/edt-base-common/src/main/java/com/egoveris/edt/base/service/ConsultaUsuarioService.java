package com.egoveris.edt.base.service;

import com.egoveris.edt.base.exception.ServicioWebException;
import com.egoveris.edt.base.model.eu.usuario.ConsultaUsuarioResponse;

import java.rmi.Remote;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "consultaUsuarioSade", portName = "ConsultaUsuarioPort", targetNamespace = "http://com.egoveris.edt.external.service.client.service/")
public interface ConsultaUsuarioService extends Remote {

  /**
   * Servicio que busca los usuarios CCOO que se corresponden al nombre de
   * usuario ingresado como parámetro.
   * 
   * @param nombreUsuario:
   *          Nombre de usuario similar a cualquier usuarioCCOO
   * @return una lista del objeto del tipo ConsultaUsuarioResponse
   */
  @WebMethod(operationName = "consultaUsuarioSade")
  public @WebResult(name = "consultaUsuarioSadeResponse") List<ConsultaUsuarioResponse> consultaUsuarioSade(
      @WebParam(name = "consultaUsuarioSadeRequest") String nombreUsuario)
      throws ServicioWebException;

}
