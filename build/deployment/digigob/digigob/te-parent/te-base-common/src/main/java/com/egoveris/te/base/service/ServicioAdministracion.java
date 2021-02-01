package com.egoveris.te.base.service;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

public interface ServicioAdministracion {

  ExpedienteElectronicoDTO crearEESolicitudArchivoYGTemporalParaAdjuntarDoc(
      String codigoExpediente, String sistemaOrigen, String usuarioOrigen)
      throws ParametroIncorrectoException, ProcesoFallidoException;

  ExpedienteElectronicoDTO crearExpedienteElectronicoAdministrador(
      String codigoExpedienteElectronico, String usuarioOrigen, String usuarioDestino,
      String sistemaOrigen, String motivoDePase, String reparticionDestino, String sectorDestino,
      Boolean isMesaDestino, Boolean isSectorDestino, Boolean isReparticionDestino,
      Boolean isUsuarioDestino, String estado, Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException;

  /**
   *
   * @param codigoExpedienteElectronico
   * @param sistemaUsuario
   * @param usuario
   * @param estadoSeleccionado
   * @param isOperacionBloqueante
   * @throws ParametroIncorrectoException
   * @throws ExpedienteNoDisponibleException
   * @throws ProcesoFallidoException
   */
  ExpedienteElectronicoDTO crearExpedienteElectronicoAsociados(String codigoExpedienteElectronico,
      String sistemaUsuario, String usuario, Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException;

  /**
   *
   * @param codigoExpedienteElectronico
   * @param sistemaUsuario
   * @param usuario
   * @param isOperacionBloqueante
   * @throws ParametroIncorrectoException
   * @throws ExpedienteNoDisponibleException
   * @throws ProcesoFallidoException
   */
  ExpedienteElectronicoDTO crearExpedienteElectronicoConDocumentos(
      String codigoExpedienteElectronico, String sistemaUsuario, String usuario,
      Boolean isOperacionBloqueante) throws ParametroIncorrectoException,
      ExpedienteNoDisponibleException, ProcesoFallidoException;

  /**
   * Este método crearExpedienteElectronicoConDocumentosNoDefinitivos
   *
   * @param <code>java.lang.String</code>codigoExpedienteElectronico
   * @param <code>java.lang.String</code>sistemaUsuario
   * @param <code>java.lang.String</code>usuario
   * @param <code>java.lang.String</code>estadoSeleccionado
   * @param <code>java.lang.Boolean</code>isOperacionBloqueante
   */
  ExpedienteElectronicoDTO crearExpedienteElectronicoConDocumentosNoDefinitivos(
      String codigoExpedienteElectronico, String sistemaUsuario, String usuario,
      String estadoSeleccionado, Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException;

  /**
   *
   * @param codigoExpedienteElectronico
   * @param usuarioOrigen
   * @param usuarioDestino
   * @param sistemaOrigen
   * @param motivoDePase
   * @param reparticionDestino
   * @param sectorDestino
   * @param isMesaDestino
   * @param isSectorDestino
   * @param isReparticionDestino
   * @param isUsuarioDestino
   * @param estado
   * @param isOperacionBloqueante
   * @throws ParametroIncorrectoException
   * @throws ExpedienteNoDisponibleException
   * @throws ProcesoFallidoException
   */
  ExpedienteElectronicoDTO crearExpedienteElectronicoParaDerivacionDeOwner(
      String codigoExpedienteElectronico, String usuarioOrigen, String usuarioDestino,
      String sistemaOrigen, String motivoDePase, String reparticionDestino, String sectorDestino,
      Boolean isMesaDestino, Boolean isSectorDestino, Boolean isReparticionDestino,
      Boolean isUsuarioDestino, String estado, Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException;

  /**
   * Esté método crearExpedienteElectronicoParaDocumentosDeTrabajo ya validada
   * la asignacion
   *
   * @param codigoExpedienteElectronico
   * @param sistemaUsuario
   * @param usuario
   * @param isOperacionBloqueante
   * @throws ParametroIncorrectoException
   * @throws ExpedienteNoDisponibleException
   * @throws ProcesoFallidoException
   */
  ExpedienteElectronicoDTO crearExpedienteElectronicoParaDocumentosDeTrabajo(
      String codigoExpedienteElectronico, String sistemaUsuario, String usuario,
      Boolean isOperacionBloqueante) throws ParametroIncorrectoException,
      ExpedienteNoDisponibleException, ProcesoFallidoException;

  ExpedienteElectronicoDTO crearExpedienteParaEnvioAArchivo(String codigoExpedienteElectronico,
      String usuarioOrigen, String sistemaOrigen, String motivoDePase, String estado,
      Boolean isMesaDestino, Boolean isSectorDestino, Boolean isReparticionDestino,
      Boolean isUsuarioDestino) throws ParametroIncorrectoException, ProcesoFallidoException;

  ExpedienteElectronicoDTO crearExpedienteParaEnvioASolicitudArchivo(
      String codigoExpedienteElectronico, String usuarioOrigen, String sistemaOrigen,
      String motivoDePase, String estado, Boolean isMesaDestino, Boolean isSectorDestino,
      Boolean isReparticionDestino, Boolean isUsuarioDestino)
      throws ParametroIncorrectoException, ProcesoFallidoException;

  /**
   * Obtiene el objeto ExpedienteElectronico a partir del codigo del mismo.
   *
   * @param <code>java.lang.String</code>codigoEE
   * @return <code>ExpedienteElectronico</code> cuyo código fue otorgado por
   *         parámetro. ó Null Object.
   * @throws <code>ParametroIncorrectoException</code>
   *           parametroIncorrectoException
   */
  ExpedienteElectronicoDTO obtenerExpedienteElectronico(String codigoExpedienteElectronico)
      throws ParametroIncorrectoException, ProcesoFallidoException;

  /**
   * Esté método valida que el usuario apoderado del expediente sea el mismo que
   * requiere realizar las operaciones en la administracion de documentos.
   *
   * @param expedienteElectronico
   * @param historialOperacionService
   * @param sistemaUsuario
   * @param usuario
   * @param isOperacionBloqueante
   * @throws ParametroIncorrectoException
   * @throws ExpedienteNoDisponibleException
   * @throws ProcesoFallidoException
   */
  void validarAsignacionClaseExpedienteElectrico(ExpedienteElectronicoDTO expedienteElectronico,
      HistorialOperacionService historialOperacionService, String sistemaUsuario, String usuario,
      Boolean isOperacionBloqueante, boolean validarAsignacion)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException;

  ExpedienteElectronicoDTO obtenerExpedienteParaHacerDocsDefinitivos(String codigoEE,
      String sistemaUsuario, String usuario) throws ParametroIncorrectoException,
      ProcesoFallidoException, ExpedienteNoDisponibleException;

}