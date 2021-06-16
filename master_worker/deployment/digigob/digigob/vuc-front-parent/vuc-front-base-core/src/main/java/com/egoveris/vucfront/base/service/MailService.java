package com.egoveris.vucfront.base.service;

import com.egoveris.vucfront.base.mail.CaratulacionMail;

public interface MailService {

	public void enviarMailCaraturalacion(CaratulacionMail caratulacion, String mailDestino);
}
