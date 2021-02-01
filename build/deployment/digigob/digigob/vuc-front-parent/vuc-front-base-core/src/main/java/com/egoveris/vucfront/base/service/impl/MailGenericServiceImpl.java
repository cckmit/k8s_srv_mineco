package com.egoveris.vucfront.base.service.impl;

import java.io.ByteArrayInputStream;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.vucfront.base.exception.NegocioException;
import com.egoveris.vucfront.base.service.IMailGenericService;
import com.egoveris.vucfront.base.service.TemplateService;

import freemarker.template.Template;

@Service("mailGenericVUCService")
public class MailGenericServiceImpl implements IMailGenericService {

	private final String charSet = "utf-8";

	@Value("${resource.base.url}/logo.jpg")
	private Resource imagenRes;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private AppProperty appProperty;

	private static final Logger logger = LoggerFactory.getLogger(MailGenericServiceImpl.class);

	@Override
	public Template obtenerTemplate(String nombreTemplate) throws NegocioException {

		try {
			return this.templateService.getTemplate(nombreTemplate);
		} catch (IOException e) {
			logger.error("Error al obtener el template: " + nombreTemplate, e);
			throw new NegocioException("Error al obtener el template: " + nombreTemplate, e);
		}
	}

	@Override
	public void enviarMail(Template template, String mailDestinatario, Map<String, String> variablesCorreo,
			String asunto, byte[] attachment) throws EmailNoEnviadoException {

		try {
			if (StringUtils.isNotEmpty(mailDestinatario)) {
				sendMailGoogle(template, mailDestinatario, variablesCorreo, asunto, attachment);
			}
			// Se cambia la exception capturada (antes era MailException) por el cambio a
			// TSF
		} catch (Exception ex) {
			logger.error("Error al enviar el mail. El mismo no se enviará.", ex);
			// Se creo EmailNoEnviadoException en lugar de NegocioException
			throw new EmailNoEnviadoException("Error al enviar el mail. El mismo no se enviará.", ex);
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
	private String armarCorreo(Map<String, String> variablesCorreo, Template template) throws NegocioException {

		String textoDelMail = "";
		try {
			StringWriter out = new StringWriter();

			this.escaparCaracteresHTML(variablesCorreo);

			template.process(variablesCorreo, out);
			out.flush();
			out.close();
			textoDelMail = out.toString();

		} catch (Exception ex) {
			logger.error("Error al confeccionar el mail. El mismo no se enviará.", ex);
			throw new NegocioException("Error al confeccionar el mail. El mismo no se enviará.", ex);
		}
		return textoDelMail;
	}

	/**
	 * Dado un mapa de strings, convierte los valores en cadenas con formato html
	 * 
	 * @param variablesCorreo
	 */
	private void escaparCaracteresHTML(Map<String, String> variablesCorreo) {
		Iterator<?> itr = variablesCorreo.entrySet().iterator();
		while (itr.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> e = (Map.Entry<String, String>) itr.next();
			String s = StringEscapeUtils.escapeHtml((String) e.getValue());
			e.setValue(s);
		}
	}

	private void sendMailGoogle(Template template, String mailDestinatario, Map<String, String> variablesCorreo,
			String asunto, byte[] attachment) {
		final String username = appProperty.getString("app.fromEmail");
		final String pp = appProperty.getString("app.mail.password");

		String auth = appProperty.getString("mail.smtp.auth") != null ? appProperty.getString("mail.smtp.auth")
				: "true";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", appProperty.getString("mail.smtp.host"));
		prop.put("mail.smtp.port", appProperty.getString("mail.smtp.port"));
		prop.put("mail.smtp.auth", auth);
		prop.put("mail.smtp.starttls.enable", appProperty.getString("mail.smtp.starttls.enable"));
		prop.put("mail.mime.charset", "utf-8");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pp);
			}
		});
		session.getProperties().put("mail.smtp.ssl.trust", appProperty.getString("mail.smtp.ssl.trust"));
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(appProperty.getString("app.fromEmail")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestinatario));
			message.setSubject(asunto);
			// agregar cuerpo del mensaje
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(armarCorreo(variablesCorreo, template), "text/html");

			multipart.addBodyPart(messageBodyPart);

			// agregar logo
			messageBodyPart = new MimeBodyPart();

			DataSource ds = new ByteArrayDataSource(this.imagenRes.getInputStream(), "image/jpeg");

			messageBodyPart.setDataHandler(new DataHandler(ds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			if (attachment != null) {
				logger.info("Se adjunta el documento de pago al mail");
				// adjuntar documento
				messageBodyPart = new MimeBodyPart();
				DataSource docPago = new ByteArrayDataSource(new ByteArrayInputStream(attachment), "application/pdf");
				messageBodyPart.setDataHandler(new DataHandler(docPago));
				messageBodyPart.setFileName("comprobante_pago.pdf");
				multipart.addBodyPart(messageBodyPart);
			}

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
