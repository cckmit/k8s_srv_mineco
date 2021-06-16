package com.egoveris.te.base.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
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
import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.service.iface.IMailGenericService;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.GetTemplateException;
import com.egoveris.te.base.util.TemplateUtil;

import freemarker.template.Template;

@Service
@Transactional
public class MailGenericServiceImpl implements IMailGenericService {

  private final String charSet = "utf-8";
 
  private static final Logger logger = LoggerFactory.getLogger(MailGenericServiceImpl.class);
  private AppProperty appProperty;
  @Autowired
  private ConstantesDB constantesDB;
  
  @Autowired
  private TemplateUtil templateUtil;

  //@Value("${ee.envioCorreoActivo}") TODO
  private String envioActivo ="ee.envioCorreoActivo";

	public Template obtenerTemplate(String nombreTemplate) throws NegocioException {
		try {
			return templateUtil.getFreemakerTemplate(nombreTemplate);
		} catch (GetTemplateException e) {
			logger.error("Error al obtener el template: " + nombreTemplate, e);
			throw new NegocioException("Error al obtener el template: " + nombreTemplate, e);
		}
	}


  public void enviarMail(Template template, String mailDestinatario,
      Map<String, String> variablesCorreo, String asunto) throws NegocioException {
	  
//    if (!"true".equals(envioActivo))
//      return;
    try {
      if (StringUtils.isNotEmpty(mailDestinatario)) {
			sendMailGoogle(template, mailDestinatario, variablesCorreo, asunto);
      }
    } catch (MailException | NegocioException e) {
      logger.error("Error al enviar el mail. El mismo no se enviará. " + e.getMessage(), e);
      throw new NegocioException("Error al enviar el mail. El mismo no se enviará. " + e.getMessage(), e);
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
			String asunto) {
		final String username = appProperty.getString("app.fromEmail");
		final String pp = appProperty.getString("app.mail.password");

		String auth = appProperty.getString("mail.smtp.auth") != null ? appProperty.getString("mail.smtp.auth")
				: "true";
		
		final String host = validarProperty("mail.smtp.host");
		final String port = validarProperty("mail.smtp.port");
		final String tls = validarProperty("mail.smtp.starttls.enable");
		final String trust = validarProperty("mail.smtp.ssl.trust");

		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.auth", auth);
		prop.put("mail.smtp.starttls.enable", tls);
		prop.put("mail.mime.charset", charSet);

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pp);
			}
		});
		session.getProperties().put("mail.smtp.ssl.trust", trust);
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

			String urlogo = appProperty.getString("resource.base.url") + "/logo.jpg";
			InputStream imagenRes = new URL(urlogo).openStream();
			DataSource ds = new ByteArrayDataSource(imagenRes, "image/jpeg");

			messageBodyPart.setDataHandler(new DataHandler(ds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException | IOException e) {
			logger.error("Error al intentar enviar el mail " + e.getMessage(), e);
			throw new NegocioException("Error al intentar enviar el mail " + e.getMessage(), e);
		}
	}
	
	private String validarProperty(String clave) {
		String valorCampo = appProperty.getString(clave);

		if (valorCampo == null) {
			throw new NegocioException("No se encontro valor para la propiedad " + clave);
		}

		return valorCampo;
	}

	  public void setAppProperty(AppProperty appProperty) {
	    this.appProperty = appProperty;
	  }

	  public AppProperty getAppProperty() {
	    return appProperty;
	  }

}
