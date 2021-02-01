package com.egoveris.edt.base.service;

import java.util.Map;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;

import freemarker.template.Template;

public interface IMailGenericService {

  /**
   * Este metodo obtiene el template almacenado correspondiente al nombre dado.
   * 
   * @param nombreTemplate
   *          que se solicita, debe incluir la extension del archivo
   * @return El <b>Template</b> que corresponde al nombre dado
   * @throws NegocioException
   *           en caso de ocurrir un error al cargar el template dado
   */
  public Template obtenerTemplate(String nombreTemplate) throws NegocioException;

  /**
   * Este metodo se encarga de enviar el mail del tipo template y con las
   * variables dadas al destinatario seleccionado
   * 
   * @param template
   *          del tipo de mail que se desea enviar
   * @param mailDestinatario
   *          direccion de mail del usuario al que se le desea enviar el mail
   * @param variablesCorreo
   *          son los datos a insertar en el template que luego se enviarian en
   *          el mail
   * @param asunto
   *          es el titulo del tema del mail
   * @throws NegocioException
   *           en caso de ocurrir un error durante el armado del mail.
   */
  public void enviarMail(Template template, String mailDestinatario,
      Map<String, String> variablesCorreo, String asunto) throws EmailNoEnviadoException;
}
