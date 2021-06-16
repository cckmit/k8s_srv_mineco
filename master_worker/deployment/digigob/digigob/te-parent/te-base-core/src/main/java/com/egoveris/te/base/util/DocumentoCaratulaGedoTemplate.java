package com.egoveris.te.base.util;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Realiza el template que se va a mandar a GEDO para
 * generar el documento que sera usado como caratula 
 * del expediente.
 * 
 * @author Rocco Gallo Citera
 *
 */
public class DocumentoCaratulaGedoTemplate {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoCaratulaGedoTemplate.class);

	/**
	 * Crea un Array de Bytes con el formato de la caratula.
	 * 
	 * @param caratula
	 * @return
	 */
	@Autowired
	private TrataService trataService;
	@Autowired
	private UsuariosSADEService usuariosSADEService;
	
	public byte[] generarCaratulaComoByteArray(ExpedienteElectronicoDTO expedienteElectronico, SolicitudExpedienteDTO solicitud){
		if (logger.isDebugEnabled()) {
			logger.debug("generarCaratulaComoByteArray(expedienteElectronico={}, solicitud={}) - start", expedienteElectronico, solicitud);
		}

		StringBuilder buffer = new StringBuilder();
		TrataDTO trata = expedienteElectronico.getTrata();
		SimpleDateFormat fechaCaratulacion = new SimpleDateFormat();
		
		String usuarioCaratulacion = usuariosSADEService.getDatosUsuario(expedienteElectronico.getUsuarioCreador()).getNombreApellido();
		String usuarioSolicitante =  usuariosSADEService.getDatosUsuario(solicitud.getUsuarioCreacion()).getNombreApellido();
		String email;
		String telefono;
		String apellido;
		String nombre;
		String cuitCuil;
		
		String razonSocial;
		
		if(solicitud.getSolicitante().getCuitCuil()!=null
				&& !solicitud.getSolicitante().getCuitCuil().equalsIgnoreCase("")){
			cuitCuil = solicitud.getSolicitante().getCuitCuil();
		}else{
			cuitCuil = "---";
		}
		
		if(solicitud.getSolicitante().getEmail()!=null 
				&& !solicitud.getSolicitante().getEmail().equalsIgnoreCase("")){
			email = solicitud.getSolicitante().getEmail();
		}else{
			email = "---";
		}
		if(solicitud.getSolicitante().getTelefono()!=null
				&& !solicitud.getSolicitante().getTelefono().equalsIgnoreCase("")){
			telefono = solicitud.getSolicitante().getTelefono();
		}else{
			telefono = "---";
		}
		if(solicitud.getSolicitante().getApellidoSolicitante()!=null
				&& !solicitud.getSolicitante().getApellidoSolicitante().equalsIgnoreCase("")){
			apellido = solicitud.getSolicitante().getApellidoSolicitante();
		}else{
			apellido = "---";
		}
		
		if(solicitud.getSolicitante().getSegundoApellidoSolicitante()!=null
				&& !solicitud.getSolicitante().getSegundoApellidoSolicitante().equalsIgnoreCase("")){
			apellido += "   " + solicitud.getSolicitante().getSegundoApellidoSolicitante();
		}
		if(solicitud.getSolicitante().getTercerApellidoSolicitante()!=null
				&& !solicitud.getSolicitante().getTercerApellidoSolicitante().equalsIgnoreCase("")){
			apellido += "    " + solicitud.getSolicitante().getTercerApellidoSolicitante();
		}
		
		if(solicitud.getSolicitante().getNombreSolicitante()!=null
				&& !solicitud.getSolicitante().getNombreSolicitante().equalsIgnoreCase("")){
			nombre = solicitud.getSolicitante().getNombreSolicitante();
		}else{
			nombre = "---";
		}
		
		if(solicitud.getSolicitante().getSegundoNombreSolicitante()!=null
				&& !solicitud.getSolicitante().getSegundoNombreSolicitante().equalsIgnoreCase("")){
			nombre += "    " + solicitud.getSolicitante().getSegundoNombreSolicitante();
		}
		if(solicitud.getSolicitante().getTercerNombreSolicitante()!=null
				&& !solicitud.getSolicitante().getTercerNombreSolicitante().equalsIgnoreCase("")){
			nombre += "    " + solicitud.getSolicitante().getTercerNombreSolicitante();
		}
		
		
		if(solicitud.getSolicitante().getRazonSocialSolicitante()!=null
				&& !solicitud.getSolicitante().getRazonSocialSolicitante().equalsIgnoreCase("")){
			razonSocial = solicitud.getSolicitante().getRazonSocialSolicitante();
		}else{
			razonSocial = "---";
		}
		
		String domicilio;
		if(solicitud.getSolicitante().getDomicilio()!=null
				&& !solicitud.getSolicitante().getDomicilio().equalsIgnoreCase("")){
			domicilio = solicitud.getSolicitante().getDomicilio();
		}else{
			domicilio = "---";
		}
		
		String piso;
		if(solicitud.getSolicitante().getPiso()!=null
				&& !solicitud.getSolicitante().getPiso().equalsIgnoreCase("")){
			piso = solicitud.getSolicitante().getPiso();
		}else{
			piso = "---";
		}
		
		String departamento;
		if(solicitud.getSolicitante().getDepartamento()!=null
				&& !solicitud.getSolicitante().getDepartamento().equalsIgnoreCase("")){
			departamento = solicitud.getSolicitante().getDepartamento();
		}else{
			departamento = "---";
		}
		
		String codigoPostal;
		
		if(solicitud.getSolicitante().getCodigoPostal()!=null
				&& !solicitud.getSolicitante().getCodigoPostal().equalsIgnoreCase("")){
			codigoPostal = solicitud.getSolicitante().getCodigoPostal();
		}else{
			codigoPostal = "---";
		}
		
		
		String numeroDocumento = "---";
		String tipoDocumento = "---";
		
		if(solicitud.getSolicitante().getDocumento() != null){
			String numeroDocumentoTemp =solicitud.getSolicitante().getDocumento().getNumeroDocumento();
			String tipoDocumentoTemp = solicitud.getSolicitante().getDocumento().getTipoDocumento();
		
			if(tipoDocumentoTemp != null && !tipoDocumentoTemp.equals("")){
				tipoDocumento = tipoDocumentoTemp;
			}		
			if(numeroDocumentoTemp != null && !numeroDocumentoTemp.equals("")){
				numeroDocumento = numeroDocumentoTemp;
			}
		}
		//
		
		
		fechaCaratulacion.applyPattern("dd/MM/yyyy");
		buffer.append("Expediente: ").append(expedienteElectronico.getCodigoCaratula()).append("<br>");
		buffer.append("Fecha Caratulación: ").append(fechaCaratulacion.format(expedienteElectronico.getFechaCreacion())).append("<br>");
		buffer.append("Usuario Caratulación: ").append(usuarioCaratulacion +" ("+expedienteElectronico.getUsuarioCreador()+")").append("<br>");
		buffer.append("Usuario Solicitante: ").append(usuarioSolicitante + " ("+solicitud.getUsuarioCreacion()+")").append("<br>");
		buffer.append("Trata: ").append(trata.getCodigoTrata()).append(" - ").append(trataService.obtenerDescripcionTrataByCodigo(trata.getCodigoTrata())).append("<br>");
		buffer.append("Descripción: ").append(expedienteElectronico.getDescripcion()).append("<br>");
		
		if (null != trata.getTiempoResolucion()) {
			if (!"0".equalsIgnoreCase(trata.getTiempoResolucion().toString())) {
				buffer.append("Tiempo de resolucion (días habiles): ").append(trata.getTiempoResolucion()).append("<br>");
			}
		}
		
		if(solicitud.isEsSolicitudInterna()){
			buffer.append("Email: ").append(email).append("<br>");
			buffer.append("Teléfono: ").append(telefono).append("<br><br><br>");
		}else{
//			buffer.append("Cuit/Cuil: ").append(cuitCuil).append("<br>");
			// -- modificacion
			buffer.append("Tipo Documento: ").append(tipoDocumento).append("<br>");
			buffer.append("Número Documento: ").append(numeroDocumento).append("<br>");
			//
			buffer.append("Persona Física/Persona Jurídica").append("<br>");
			buffer.append("  Apellidos: ").append(apellido).append("<br>");
			buffer.append("  Nombres: ").append(nombre).append("<br>");
			buffer.append("  Razón Social: ").append(razonSocial).append("<br>");
			buffer.append("  Email: ").append(email).append("<br>");
			buffer.append("  Teléfono: ").append(telefono).append("<br>");
			
			buffer.append("  Domicilio: ").append(domicilio).append("<br>");
			buffer.append("  Piso: ").append(piso).append("<br>");
			buffer.append("  Departamento: ").append(departamento).append("<br>");
			buffer.append("  Código Postal: ").append(codigoPostal).append("<br><br><br>");
			
		}		
		if(!StringUtils.isEmpty(solicitud.getMotivoExterno())){
			buffer.append("Motivo de Solicitud de Caratulación: ").append(solicitud.getMotivoExterno()).append("<br><br><br>");			
		}else if(!StringUtils.isEmpty(solicitud.getMotivo())) {
			buffer.append("Motivo de Solicitud de Caratulación: ").append(solicitud.getMotivo()).append("<br><br><br>");
		}else{
			buffer.append("Motivo de Solicitud de Caratulación: ").append("No posee motivo.").append("<br><br><br>");			
		}
		byte[] returnbyteArray = buffer.toString().getBytes();
		if (logger.isDebugEnabled()) {
			logger.debug("generarCaratulaComoByteArray(ExpedienteElectronico, SolicitudExpediente) - end - return value={}", returnbyteArray);
		}
	    	return returnbyteArray;
	}
	
}
