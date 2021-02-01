package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import com.egoveris.deo.model.model.RequestExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.model.DocumentoArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoGedoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.te.base.model.TipoDocumentoDTO;



/**
 * Interfaz del servicio que realiza operaciones sobre los documentos de GEDO.
 *
 * @author Rocco Gallo Citera
 */
public interface DocumentoGedoService {

	/**
	 * Obtiene de Gedo un los datos asociados a un documento, identificado
	 * por su numero especial.
	 *
	 * @param numeroEspecial the numero especial
	 * @return the documento
	 */
	public DocumentoDTO obtenerDocumentoPorNumeroEspecial(String numeroEspecial);

	/**
	 * Obtiene de Gedo un los datos asociados a un numero estantar.
	 *
	 * @param numeroSade the numero Sade
	 * @return DocumentoGedoDTO
	 */ 
	 public DocumentoGedoDTO obtenerDocumentoGedoPorNumeroEstandar(String numeroSade);

	
	
	/**
	 * Obtiene de Gedo un los datos asociados a un documento, identificado
	 * por su numero standard.
	 *
	 * @param numeroEstandar the numero estandar
	 * @return the documento
	 */
	public DocumentoDTO obtenerDocumentoPorNumeroEstandar(String numeroEstandar);
	
	/**
	 * Genera, en GEDO, el documento electrónico del PASE generado en EE.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivoExpediente the motivo expediente
	 * @param aclaracion the aclaracion
	 * @param username the username
	 * @return Documento para agregar en la lista de documentos del expediente.
	 */
	public DocumentoDTO generarDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronico, String motivoExpediente, String aclaracion, String username);
	
	
	/**
	 * Metodo para generar un documento de pase generico, indicando el acronimo correspondiente.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivoExpediente the motivo expediente
	 * @param username the username
	 * @param acronimoPase the acronimo pase
	 * @param referenciaPase the referencia pase
	 * @param camposTemplate the campos template
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoGEDOPase(ExpedienteElectronicoDTO expedienteElectronico, String motivoExpediente, String username, String acronimoPase, String referenciaPase, Map<String,String> camposTemplate);	
	
	/**
	 *  Genera, en GEDO, el documento electrónico de la Vinculación o Desvinculación en Tramitación Conjunta generado en EE.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivo the motivo
	 * @param username the username
	 * @return Documento para agregar en la lista de documentos del expediente.
	 */
	public DocumentoDTO generarDocumentoDeTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico, String motivo, String username);

	/**
	 *  Genera, en GEDO, el documento electrónico de la Vinculación en Fusion generado en EE.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivo the motivo
	 * @param username the username
	 * @return Documento para agregar en la lista de documentos del expediente.
	 */
	public DocumentoDTO generarDocumentoDeFusion(ExpedienteElectronicoDTO expedienteElectronico, String motivo, String username);
	
	
	/**
	 * Obtiene de gedo, los archivos de trabajo, que tiene el documento enviado como parametro.
	 *
	 * @param documento the documento
	 * @return Una lista con los archivos de trabajo.
	 */
	public List<DocumentoArchivoDeTrabajoDTO> obtenerArchivosDeTrabajo (DocumentoDTO documento);

	/**
	 *  Genera, en GEDO, el documento electrónico que regstra la eliminación de la reserva del EE.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivo the motivo
	 * @param username the username
	 * @return Documento para agregar en la lista de documentos del expediente.
	 */
	public DocumentoDTO generarDocumentoQuitarReserva(ExpedienteElectronicoDTO expedienteElectronico, String motivo,
			String username);
	

	/**
	 * Genera en GEDO el documento de subsanacion.
	 *
	 * @param solicitudSubs the solicitud subs
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoSubsanacion(SolicitudSubs solicitudSubs);
	
	/**
	 * Consultar documento por numero.
	 *
	 * @param nroDocumento the nro documento
	 * @param usuarioConsulta the usuario consulta
	 * @return the response external consulta documento
	 * @throws ParametroInvalidoConsultaException the parametro invalido consulta exception
	 * @throws DocumentoNoExisteException the documento no existe exception
	 * @throws SinPrivilegiosException the sin privilegios exception
	 * @throws ErrorConsultaDocumentoException the error consulta documento exception
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ParametroInvalidoConsultaException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.DocumentoNoExisteException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.SinPrivilegiosException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaDocumentoException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ParametroInvalidoConsultaException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.DocumentoNoExisteException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.SinPrivilegiosException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaDocumentoException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ParametroInvalidoConsultaException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.DocumentoNoExisteException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.SinPrivilegiosException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaDocumentoException 
	 */
	public ResponseExternalConsultaDocumento consultarDocumentoPorNumero(String nroDocumento, String usuarioConsulta) throws  ParametroInvalidoConsultaException, DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException;
	
	/**
	 *  Genera, en GEDO, una tarea.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param userProductor the user productor
	 * @param userReceptor the user receptor
	 * @param motivo the motivo
	 * @param tipoDoc the tipo doc
	 * @return Documento con el process id (ubicado en el campo numero sade).
	 */
	public DocumentoDTO generarPeticionGeneracionDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronico,String userProductor, Usuario userReceptor, String motivo , TipoDocumentoDTO tipoDoc);
	
	
	/**
	 * Generar documento GEDO con comunicacion.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @param pieDePaginaDeDestinatarios the pie de pagina de destinatarios
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoGEDOConComunicacion(ExpedienteElectronicoDTO expedienteElectronico, String username, String motivoExpediente,String pieDePaginaDeDestinatarios);

	public String getPathTareaExterna();

	/**
	 * Buscar documento GEDO por datos propios.
	 *
	 * @param requestExternalBuscarDocumentos the request external buscar documentos
	 * @return the response external buscar documentos
	 * @throws ErrorConsultaNumeroSadeException the error consulta numero sade exception
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaNumeroSadeException 
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaNumeroSadeException 
	 */
	public ResponseExternalBuscarDocumentos buscarDocumentoGEDOPorDatosPropios(RequestExternalBuscarDocumentos requestExternalBuscarDocumentos) throws ErrorConsultaNumeroSadeException;

	/**
	 * Armar doc de notificacion.
	 *
	 * @param ee the ee
	 * @param username the username
	 * @param referencia the referencia
	 * @param motivo the motivo
	 * @return the documento
	 */
	public DocumentoDTO armarDocDeNotificacion(ExpedienteElectronicoDTO ee,String username, String referencia, String motivo);

	/**
	 * Actualiza en Gedo los permisos de visualizacion de documentos (reserva).
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param usuario the usuario
	 * @param reparticionesRectora the reparticiones rectora
	 * @throws RuntimeException the runtime exception
	 */
	public void asignarPermisosVisualizacionGEDO(ExpedienteElectronicoDTO expedienteElectronico,Usuario usuario, List<ReparticionParticipanteDTO> reparticionesRectora) throws AsignacionException;

 
	
	 
}