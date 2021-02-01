package com.egoveris.edt.base.service.impl.mail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.IMailGenericService;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Service
public class MailGenericServiceImpl implements IMailGenericService {

  private final String charSet = "utf-8";

  private static final Logger LOGGER = LoggerFactory.getLogger(MailGenericServiceImpl.class);

  @Value("${cas.url}/logo.jpg")
  private Resource imagenRes;
  
  @Autowired
  private AppProperty appProperty;

  @Override
  public Template obtenerTemplate(String nombreTemplate) throws NegocioException {
    StringBuilder cadenaLogo = new StringBuilder();
    cadenaLogo.append("/templates/");
    cadenaLogo.append(appProperty.getString("url.archivo.logo"));
    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(this.getClass(), cadenaLogo.toString());
    cfg.setObjectWrapper(new DefaultObjectWrapper());
    try {
      return cfg.getTemplate(nombreTemplate);
    } catch (IOException ex) {
      LOGGER.error("Error al obtener el template: " + nombreTemplate, ex);
      throw new NegocioException("Error al obtener el template: " + nombreTemplate, ex);
    }
  }

  @Override
  public void enviarMail(Template template, String mailDestinatario,
      Map<String, String> variablesCorreo, String asunto) throws EmailNoEnviadoException {
    try {
      if (StringUtils.isNotEmpty(mailDestinatario)) {
    	  sendMailGoogle(template, mailDestinatario, variablesCorreo, asunto);
      }
    // Se cambia la exception capturada (antes era MailException) por el cambio a TSF
    } catch (Exception ex) {
      LOGGER.error("Error al enviar el mail. El mismo no se enviará.", ex);
      // Se creo EmailNoEnviadoException en lugar de NegocioException
      throw new EmailNoEnviadoException("Error al enviar el mail. El mismo no se enviará.", ex);
    }
  }

  private void sendMailGoogle(Template template, String mailDestinatario,
	      Map<String, String> variablesCorreo, String asunto) {
      final String username = appProperty.getString("app.edt.fromEmail");
      final String pp = appProperty.getString("app.eu.mail.password");

      Properties prop = new Properties();
      prop.put("mail.smtp.host", appProperty.getString("mail.smtp.host"));
      prop.put("mail.smtp.port", appProperty.getString("mail.smtp.port"));
      prop.put("mail.smtp.auth", appProperty.getString("mail.smtp.auth"));
      prop.put("mail.smtp.starttls.enable", appProperty.getString("mail.smtp.starttls.enable"));

      Session session = Session.getInstance(prop,
              new javax.mail.Authenticator() {
                  protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(username, pp);
                  }
              });
      session.getProperties().put("mail.smtp.ssl.trust", appProperty.getString("mail.smtp.ssl.trust"));
      try {
    	  
    	  
    	  
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(appProperty.getString("app.edt.fromEmail")));
          message.setRecipients(
                  Message.RecipientType.TO,
                  InternetAddress.parse(mailDestinatario)
          );
          message.setSubject(asunto);
          
          MimeMultipart multipart = new MimeMultipart("related");
          BodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setContent(armarCorreo(variablesCorreo, template), "text/html");
          
          multipart.addBodyPart(messageBodyPart);
          
          messageBodyPart = new MimeBodyPart();
          
          DataSource ds = new ByteArrayDataSource(this.imagenRes.getInputStream(), "image/jpeg");
          
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
   * @param mailDestinatario
   *          direccion de mail del usuario destinatario
   * @param variablesCorreo
   *          Map que contiene las variables propias de cada mail, ej. nombre
   *          usuario, id usuario, etc
   * @param template
   *          del tipo de correo que se esta armando
   * @param mailSender
   *          instancia en uso del enviador de mail de Java del cual se creara
   *          el correo
   * @param asunto
   *          es el titulo del tema del mail
   * @return Un <b>MimeMessage</b> cargado con los datos dados, listo para ser
   *         enviado al destinatario
   * @throws NegocioException
   *           en caso de ocurrir un error durante el armado del mail.
   */
  private String armarCorreo(Map<String, String> variablesCorreo,
      Template template) throws NegocioException {
	  
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
      throw new NegocioException("Error al confeccionar el mail. El mismo no se enviará.", ex);
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

  public void setAppProperty(AppProperty appProperty) {
    this.appProperty = appProperty;
  }

  public AppProperty getAppProperty() {
    return appProperty;
  }

}