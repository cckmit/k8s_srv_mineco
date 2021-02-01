package com.egoveris.te.base.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.BuzonAsignacionBean;
import com.egoveris.te.base.service.iface.IMailDepuracionService;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.GetTemplateException;
import com.egoveris.te.base.util.TemplateUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Service
@Transactional
public class DepuracionMailServiceImpl implements IMailDepuracionService {

	private final String charSet = "utf-8";
	private final Logger logger = LoggerFactory.getLogger(DepuracionMailServiceImpl.class);
	private JavaMailSender mailSender; // TODO revisar

	private String asuntoDepuracionSolicitud = "Mantenimiento de Expediente Electrónico - Depuración de Solicitudes";

	@Autowired
	private ConstantesDB constantesDB;
	
	private TemplateUtil templateUtil;

	@Override
	public void enviarMailProcesoDepuracionAsignacion(List<BuzonAsignacionBean> solicitudes, Usuario usuarioDestino,
			Integer cantidad, long diasVencimiento) throws NegocioException {

		Map<String, String> variablesCorreo = new HashMap<>();

		variablesCorreo.put("estado", "Iniciar Expediente");

		variablesCorreo.put("cantidadDeSolicitudesSinCaratular", " " + cantidad);

		variablesCorreo.put("tablaSolicitudes", crearTablaDinamica(usuarioDestino, solicitudes));

		variablesCorreo.put("nombreCompletoDestinatario", " " + usuarioDestino.getNombreApellido());

		variablesCorreo.put("diasVencimiento", " " + diasVencimiento);

		Template template = this.obtenerTemplate("templateMailDepuracionAsignados");

		this.enviarMail(template, usuarioDestino.getEmail(), variablesCorreo, this.asuntoDepuracionSolicitud);
	}

	public void enviarMail(Template template, String mailDestinatario, Map<String, String> variablesCorreo,
			String asunto) throws NegocioException {
		try {
			MimeMessage message;
			if (StringUtils.isNotEmpty(mailDestinatario)) {
				message = this.armarCorreo(mailDestinatario, variablesCorreo, template, this.mailSender, asunto);
				if (message != null) {
					this.mailSender.send(message);
				}
			}
		} catch (MailException e) {
			logger.error("Error al enviar el mail. El mismo no se enviará.", e);
			throw new NegocioException("Error al enviar el mail. El mismo no se enviará.", e);
		}
	}

	/**
	 * Este metodo arma el correo electronico con los datos dados
	 * 
	 * @param mailDestinatario
	 *            direccion de mail del usuario destinatario
	 * @param variablesCorreo
	 *            Map que contiene las variables propias de cada mail, ej.
	 *            nombre usuario, id usuario, etc
	 * @param template
	 *            del tipo de correo que se esta armando
	 * @param mailSender
	 *            instancia en uso del enviador de mail de Java del cual se
	 *            creara el correo
	 * @param asunto
	 *            es el titulo del tema del mail
	 * @return Un <b>MimeMessage</b> cargado con los datos dados, listo para ser
	 *         enviado al destinatario
	 * @throws NegocioException
	 *             en caso de ocurrir un error durante el armado del mail.
	 */
	private MimeMessage armarCorreo(String mailDestinatario, Map<String, String> variablesCorreo, Template template,
			JavaMailSender mailSender, String asunto) throws NegocioException {

	 StringBuilder pathLogoMail = new StringBuilder();

		pathLogoMail.append("/templates/");

		pathLogoMail.append(constantesDB.getNombreEntorno());

		pathLogoMail.append("/logo2.jpg");

		ClassPathResource dataSourceLogo = new ClassPathResource(pathLogoMail.toString());

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			message.setSubject(MimeUtility.encodeText(asunto, this.charSet, "B"));
			MimeMessageHelper mimeHelper = new MimeMessageHelper(message, true, this.charSet);
			mimeHelper.setTo(mailDestinatario);
			StringWriter out = new StringWriter();

			// Se convierten los caracteres especiales a formato HTML, dado que
			// van a
			// insertarse estos valores en un template
			// en formato ftl
			this.escaparCaracteresHTML(variablesCorreo);

			template.process(variablesCorreo, out);
			/* Genera el mail en formato HTML - USo de Prueba */
			// template.process(variablesCorreoNotificacion, new
			// FileWriter("C:\\Mail.html"));

			out.flush();
			out.close();
			String textoDelMail = out.toString();

			mimeHelper.setText(textoDelMail, true);
			mimeHelper.addInline("logoGCABA1", dataSourceLogo);
		} catch (Exception e) {
			// logger.error("Error al confeccionar el mail. El mismo no se
			// enviará.",
			// e);
			throw new NegocioException("Error al confeccionar el mail. El mismo no se enviará.", e);
		}
		return message;
	}

	/**
	 * Dado un mapa de strings, convierte los valores en cadenas con formato
	 * html
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

	private String crearTablaDinamica(Usuario user, List<BuzonAsignacionBean> solicitudes) {

	  StringBuilder tabla = new StringBuilder();
		tabla.append(crearParteSuperiorDeTabla());

		for (BuzonAsignacionBean s : solicitudes) {
			if (user.getUsername().equals(s.getUsuario())) {
				tabla.append(crearFilaHTML(s));
			}
		}
		tabla.append(crearParteInferiorDeTabla());
		return tabla.toString();
	}

	private String crearParteSuperiorDeTabla() {
		return "<table style=" + '"' + "width: 100%; text-align: center; margin-left: auto;"
				+ "margin-right: auto; width: 966px; height: 85px; font-family: Arial;" + '"' + "cellpadding=" + '"'
				+ "2" + '"' + "cellspacing=" + '"' + "2" + '"' + ">" + "<tbody>" + "<tr style=" + '"'
				+ "border: solid; background: gray;" + '"' + ">" + "<td><font color=" + '"' + "white" + '"'
				+ ">Fecha Creaci&#243n</font></td>" + "<td><font color=" + '"' + "white" + '"' + ">Motivo</font></td>"
				+ "<td><font color=" + '"' + "white" + '"' + ">Trata</font></td>" + "</tr>";
	}

	private String crearParteInferiorDeTabla() {
		return "</tbody></table>";
	}

	private String crearFilaHTML(BuzonAsignacionBean buzonSolcitudes) {
	 StringBuilder r = new StringBuilder();

		List<Map<String, Object>> listaVariables = buzonSolcitudes.getDetalles();

		for (Map<String, Object> p : listaVariables) {
			r.append("<tr>");
			r.append("<td>" + buzonSolcitudes.getFecha() + "</td>");
			r.append("<td>" + p.get("motivo") + "</td>");

			if (p.get("trata") != null) {
				r.append("<td>" + p.get("trata") + "</td>");
			} else {
				r.append("<td>Sin Trata Registrada</td>");
			}
			r.append("</tr>");
		}
		return r.toString();
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public Template obtenerTemplate(String nombreTemplate) throws NegocioException {

//		try {
//			return this.templateUtil.getFreemakerTemplate(nombreTemplate);
//		} catch (GetTemplateException e) {
//			throw new NegocioException("Erro al obtener template '" + nombreTemplate + "'.");
//		}
		
	 StringBuilder pathEntorno = new StringBuilder();
		pathEntorno.append("/templates/");

		pathEntorno.append(constantesDB.getNombreEntorno());

		pathEntorno.append("/");

		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), pathEntorno.toString());
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		try {
			return cfg.getTemplate(nombreTemplate);
		} catch (IOException e) {
			logger.error("Error al obtener el template: " + nombreTemplate, e);
			throw new NegocioException("Error al obtener el template: " + nombreTemplate, e);
		}
	}
}
