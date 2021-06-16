package com.egoveris.edt.base.service.mail;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

public interface IMailService {

  /**
   * Este metodo se encarga de enviar el mail del tipo "ALTA" de Usuario SADE.
   *
   * @param usuarioDestino
   *          datos del usuario destinatario
   * @throws NegocioException
   *           en caso de ocurrir un error al cargar el template o durante el
   *           armado del mail
   */
  public void enviarMailAlta(UsuarioBaseDTO usuarioDestino) throws EmailNoEnviadoException;

  /**
   * Envio de mail para notificar al usuario del reseteo de su password.
   *
   * @param usuarioDestino
   *          the usuario destino
   * @throws NegocioException
   *           the negocio exception
   */
  public void enviarMailResetoPassword(UsuarioBaseDTO usuarioDestino) throws EmailNoEnviadoException;
}
