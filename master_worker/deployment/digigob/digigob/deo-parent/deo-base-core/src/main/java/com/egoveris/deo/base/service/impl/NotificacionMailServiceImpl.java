package com.egoveris.deo.base.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.NotificacionMailException;
import com.egoveris.deo.base.service.NotificacionMailService;
import com.egoveris.deo.base.util.GetTemplateException;
import com.egoveris.deo.base.util.TemplateUtil;
import com.egoveris.deo.model.exception.NegocioException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
@Transactional
public class NotificacionMailServiceImpl implements NotificacionMailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionMailServiceImpl.class);

	@Value("${cas.url}/logo.jpg")
	private Resource imagenRes;

	@Autowired
	private AppProperty appProperty;
	
	@Autowired
	private TemplateUtil templateUtil;
	
	@Override
	public void componerCorreo(String subject, String mailDestinatario, String template, Map variablesTemplate)
			throws NotificacionMailException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("componerCorreo(String, String, String, Map) - start"); //$NON-NLS-1$
		}

		StringWriter out = new StringWriter();
		Template temp = null;
		
		try {
			temp = this.getTemplate(template);
			temp.process(variablesTemplate, out);
			out.flush();
			out.close();
		} catch (IOException e1) {
			LOGGER.error("error al componer correo", e1);
		} catch (TemplateException | GetTemplateException e) {
			LOGGER.error("error al componer correo", e);
		}

		if (temp != null) {
			try {
				sendMailGoogle(temp, mailDestinatario, variablesTemplate, subject);
			} catch (Exception e) {
				LOGGER.error("Sin éxito al enviar el correo. Reintentar.", e);
				// throw new NotificacionMailException("Sin éxito al enviar el correo. Por favor
				// reintentar.");
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("componerCorreo(String, String, String, Map) - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	private void sendMailGoogle(Template template, String mailDestinatario, Map variablesCorreo,
			String asunto) {
		final String username = appProperty.getString("app.edt.fromEmail");
		final String pp = appProperty.getString("app.eu.mail.password");

		Properties prop = new Properties();
		prop.put("mail.smtp.host", appProperty.getString("mail.smtp.host"));
		prop.put("mail.smtp.port", appProperty.getString("mail.smtp.port"));
		prop.put("mail.smtp.auth", appProperty.getString("mail.smtp.auth"));
		prop.put("mail.smtp.starttls.enable", appProperty.getString("mail.smtp.starttls.enable"));

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pp);
			}
		});

		session.getProperties().put("mail.smtp.ssl.trust", appProperty.getString("mail.smtp.ssl.trust"));

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(appProperty.getString("app.edt.fromEmail")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestinatario));
			message.setSubject(asunto);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(armarCorreo(variablesCorreo, template), "text/html");
			multipart.addBodyPart(messageBodyPart);

			DataSource ds = new ByteArrayDataSource(this.imagenRes.getInputStream(), "image/jpeg");

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(ds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo arma el correo electronico con los datos dados
	 * 
	 * @param mailDestinatario direccion de mail del usuario destinatario
	 * @param variablesCorreo  Map que contiene las variables propias de cada mail,
	 *                         ej. nombre usuario, id usuario, etc
	 * @param template         del tipo de correo que se esta armando
	 * @param mailSender       instancia en uso del enviador de mail de Java del
	 *                         cual se creara el correo
	 * @param asunto           es el titulo del tema del mail
	 * @return Un <b>MimeMessage</b> cargado con los datos dados, listo para ser
	 *         enviado al destinatario
	 * @throws NegocioException en caso de ocurrir un error durante el armado del
	 *                          mail.
	 */
	private String armarCorreo(Map<String, String> variablesCorreo, Template template)
			throws NotificacionMailException {
		String textoDelMail = "";

		try {
			StringWriter out = new StringWriter();
			this.escaparCaracteresHTML(variablesCorreo);

			template.process(variablesCorreo, out);
			out.flush();
			out.close();
			textoDelMail = out.toString();
		} catch (Exception ex) {
			LOGGER.error("Error al confeccionar el mail. El mismo no se enviará.", ex);
			throw new NotificacionMailException("Error al confeccionar el mail. El mismo no se enviará.", ex);
		}

		return textoDelMail;
	}

	/**
	 * Dado un mapa de strings, convierte los valores en cadenas con formato html.
	 * 
	 * @param variablesCorreo
	 */
	private void escaparCaracteresHTML(Map<String, String> variablesCorreo) {
		Iterator<?> itr = variablesCorreo.entrySet().iterator();
		
		while (itr.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
			String str = StringEscapeUtils.escapeHtml((String) entry.getValue());
			entry.setValue(str);
		}
	}

	@SuppressWarnings("deprecation")
    private Template getTemplate(String nombreTemplate) throws GetTemplateException {
		return this.templateUtil.getFreemakerTemplate(nombreTemplate);
    }

}
