package com.egoveris.vucfront.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.vucfront.base.mail.CaratulacionMail;
import com.egoveris.vucfront.base.service.IMailGenericService;
import com.egoveris.vucfront.base.service.MailService;

import freemarker.template.Template;

@Service("mailServiceVUC")
public class MailServiceImpl implements MailService {

	
	private static final String ASUNTO_CARATULACION = "caratulación de trámite VUC";
	
	@Autowired
	@Qualifier("mailGenericVUCService")
	private IMailGenericService mailGenericService;
	
	@Value("${resource.base.url}")
	private String logoBaseUrl;
	
  @Autowired
  private AppProperty appProperty;

	
	@Override
	public void enviarMailCaraturalacion(CaratulacionMail caratulacion, String mailDestino) {

		Map<String, String> infoMail = new HashMap<>();
		
		String nombreEntorno = appProperty.getString("nombre.entorno")!=null 
				? appProperty.getString("nombre.entorno") : "egoeveris";
		    
    infoMail.put("logo", logoBaseUrl + "/logo.jpg");
    infoMail.put("nombreCompletoDestinatario", caratulacion.getNombreCompleto());
    infoMail.put("referenciadocumento", caratulacion.getNumeroCaratula());
    infoMail.put("fechamodi", caratulacion.getFechaString());
    infoMail.put("codigo", caratulacion.getCodigoTramite());
    infoMail.put("descripcion", caratulacion.getNombreTramite());
    infoMail.put("motivo", caratulacion.getNombreDocumento());
    
    Template template;
    
	  template = this.mailGenericService.obtenerTemplate("caratulacionVUC");
	  this.mailGenericService.enviarMail(template, mailDestino, infoMail,
			  ASUNTO_CARATULACION, caratulacion.getDocPDF());
		
	}

}
