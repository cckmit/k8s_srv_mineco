package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentosExcelDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.TareaBusquedaDTO;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.text.DateFormats;

public class ExcelConverter {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelConverter.class);
	
	public static byte[] convert(List<NumerosUsadosDTO> list) {
		if (list!=null) {
			StringBuilder builder=new StringBuilder();
			builder.append("<TABLE border=\"1\">");
			builder.append("<tr>");
			builder.append("<TD>");
			builder.append("Nro. SADE");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Nro. Especial");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Acrónimo Tipo Doc.");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Nombre Tipo Doc.");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Estado del número");
			builder.append("</TD>");

			for (NumerosUsadosDTO nro:list) {
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append(nro.getNumeroSADE());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getAnio()+"-"+nro.getNumeroEspecial()+"-"+nro.getCodigoReparticion());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getTipoDocumento().getAcronimo());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getTipoDocumento().getNombre());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getEstado());
				builder.append("</TD>");
				builder.append("</TR>");
			}
			builder.append("</TABLE>");
			try {
				return builder.toString().getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.error("Error de conversion "+e.getMessage(),e);
				return null;
			}
		}
		return null;
	}
	public static byte[] convert2(List<DocumentosExcelDTO> list, ConsultaSolrRequest consulta, String username) {
		if (list!=null) {
			StringBuilder builder=new StringBuilder();
			builder.append("<TABLE border=\"1\">");
			builder.append("<tr>");
			builder.append("<TD>");
			builder.append("Nro. SADE");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Fecha creación");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Usuario generador");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Referencia");
			builder.append("</TD>");
			builder.append("</tr>");
			

			for (DocumentosExcelDTO nro:list) {
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append(nro.getCodigoSade());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(DateFormats.format(nro.getFechaCreacion(),true));
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getUsuarioGenerador());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(nro.getReferencia());
				builder.append("</TD>");
				builder.append("</TR>");
			}
			builder.append("</TABLE>");
			builder.append("<TABLE border=\"1\">");
			builder.append("<TR>");
			builder.append("</TR>");
			builder.append("<TR>");
			builder.append("<TD>");
			builder.append("Consulta:");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append(consulta.getTipoBusqueda());
			builder.append("</TD>");
			builder.append("</TR>");
			builder.append("<TR>");
			builder.append("<TD>");
			builder.append("Usuario:");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append(username);
			builder.append("</TD>");
			builder.append("</TR>");
			builder.append("<TR>");
			builder.append("<TD>");
			builder.append("Tipo Documento:");
			builder.append("</TD>");
			builder.append("<TD>");
			if(consulta.getTipoDocAcr()!=null)
				builder.append(consulta.getTipoDocAcr() + " - " + consulta.getTipoDocDescr());
			else
				builder.append("Todos");
			builder.append("</TD>");
			builder.append("</TR>");
			builder.append("<TR>");
			builder.append("<TD>");
			builder.append("Desde");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Hasta");
			builder.append("</TD>");
			builder.append("</TR>");
			if(consulta.getFechaDesde()!=null && consulta.getFechaHasta()!=null){
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append(DateFormats.format(consulta.getFechaDesde(),true));
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(DateFormats.format(consulta.getFechaHasta(),true));
				builder.append("</TD>");
				builder.append("</TR>");
			}else{
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append("siempre");
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(DateFormats.format(new Date(), true));
				builder.append("</TD>");
				builder.append("</TR>");
			}
			if(consulta.getUsuarioFirmante()!=null){
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append("Usuario Firmante:");
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(consulta.getUsuarioFirmante());
				builder.append("</TD>");
				builder.append("</TR>");
			}
			
			
			try {
				return builder.toString().getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.error("Error de conversion "+e.getMessage(),e);
				return null;
			}
		}
		return null;
	}
	public static byte[] convert3(List<TareaBusquedaDTO> list) {
		if (list!=null) {
			StringBuilder builder=new StringBuilder();
			builder.append("<TABLE border=\"1\">");
			builder.append("<tr>");
			builder.append("<TD>");
			builder.append("Usuario Ini.");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Fecha creacion");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Acrónimo Tipo Doc.");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Motivo");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Tipo tarea");
			builder.append("</TD>");
			builder.append("<TD>");
			builder.append("Usuario Dest.");
			builder.append("</TD>");

			for (TareaBusquedaDTO task:list) {
				builder.append("<TR>");
				builder.append("<TD>");
				builder.append(task.getUsuarioIniciador());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(task.getFechaAlta());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(task.getTipoDocumento());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(task.getReferencia() != null?task.getReferencia():"");
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(task.getTipoTarea());
				builder.append("</TD>");
				builder.append("<TD>");
				builder.append(task.getUsuarioDestino());
				builder.append("</TD>");
				builder.append("</TR>");
			}
			builder.append("</TABLE>");
			try {
				return builder.toString().getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.error("Error de conversion "+e.getMessage(),e);
				return null;
			}
		}
		return null;
	}
}
