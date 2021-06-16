package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.NotificacionMailException;

import java.util.Map;
import org.terasoluna.plus.common.exception.ApplicationException;

public interface NotificacionMailService {

  public void componerCorreo(String subject, String mailDestinatario, String template,
      Map variablesTemplate) throws ApplicationException, NotificacionMailException;
  

}
