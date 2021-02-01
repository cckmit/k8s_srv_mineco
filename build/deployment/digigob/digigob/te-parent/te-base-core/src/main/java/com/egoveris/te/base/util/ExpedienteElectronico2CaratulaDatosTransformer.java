package com.egoveris.te.base.util;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.model.DTODatosCaratula;

import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;


public class ExpedienteElectronico2CaratulaDatosTransformer implements Transformer {
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteElectronico2CaratulaDatosTransformer.class);
	@Qualifier("consultaDocumento3Service")
	private IExternalConsultaDocumentoService consultaDocumentoService;
	private String msgError;
	public ExpedienteElectronico2CaratulaDatosTransformer(final IExternalConsultaDocumentoService  consultaDocumentoService) {
		this.consultaDocumentoService = consultaDocumentoService;
	}
    public Object transform(Object input) {
		if (logger.isDebugEnabled()) {
			logger.debug("transform(input={}) - start", input);
		}

		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		ExpedienteElectronicoDTO expedienteElectronico = null;
		try {
			DTODatosCaratula datosCaratula = new DTODatosCaratula();
			expedienteElectronico = (ExpedienteElectronicoDTO)input;
    	request.setNumeroDocumento(expedienteElectronico.getDocumentos().get(0).getNumeroSade());
    	request.setUsuarioConsulta(expedienteElectronico.getUsuarioCreador());
    	
    	ResponseExternalConsultaDocumento response = consultaDocumentoService.consultarDocumentoPorNumero(request);    	
    	datosCaratula.setNombreSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getNombreSolicitante());
    	datosCaratula.setApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getApellidoSolicitante());
    	
    	datosCaratula.setSegundoApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getSegundoApellidoSolicitante());
    	datosCaratula.setSegundoNombreSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getSegundoNombreSolicitante());
    	
    	datosCaratula.setTercerApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTercerApellidoSolicitante());
    	datosCaratula.setTercerNombreSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTercerNombreSolicitante());
    	
    	datosCaratula.setTelefono(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTelefono());
    	datosCaratula.setMail(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getEmail());
    	
    	datosCaratula.setCuitCuil(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil());
    	
    	datosCaratula.setDomicilio(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio());
    	datosCaratula.setPiso(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso());
    	datosCaratula.setDepartamento(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDepartamento());
    	datosCaratula.setCodigoPostal(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCodigoPostal());
    	
    	if(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getRazonSocialSolicitante()!=null)
    		datosCaratula.setRazonSocialSolicitante(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getRazonSocialSolicitante());    	
    	if(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento()!=null)
    		datosCaratula.setTipoDocumento(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento().getTipoDocumento());
    	if(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento()!=null)
    		datosCaratula.setNumeroDocumento(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento().getNumeroDocumento());    	
		datosCaratula.setEsPersonaJuridica((expedienteElectronico.getSolicitudIniciadora().getSolicitante().getRazonSocialSolicitante()==null?false:true));    	
    	datosCaratula.setNumeroSadeDocumentoCaratula(response.getNumeroDocumento());

			if (logger.isDebugEnabled()) {
				logger.debug("transform(Object) - end - return value={}", datosCaratula);
			}
	    	return datosCaratula;
    	
		} catch (ParametroInvalidoConsultaException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (DocumentoNoExisteException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (SinPrivilegiosException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (ErrorConsultaDocumentoException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (Exception e) {
			logger.error("transform(Object)", e);

			this.msgError = "Hubo un error al obtener los datos de la caratula para el expediente";
		} 
		
		Object returnObject = new DTODatosCaratula();
		if (logger.isDebugEnabled()) {
			logger.debug("transform(Object) - end - return value={}", returnObject);
		}
		return returnObject;
    }
    
    public String getMsgError() {
    	return this.msgError;
    }


}
