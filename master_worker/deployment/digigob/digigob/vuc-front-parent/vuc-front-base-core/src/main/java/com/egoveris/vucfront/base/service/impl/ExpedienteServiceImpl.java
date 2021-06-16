package com.egoveris.vucfront.base.service.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumentoUsuarioExterno;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.egoveris.te.model.model.PaseExpedienteRequest;
import com.egoveris.te.ws.service.IAdministracionDeDocumentosOficialesService;
import com.egoveris.te.ws.service.IGenerarExpedienteService;
import com.egoveris.te.ws.service.IGenerarPaseExpedienteService;
import com.egoveris.vucfront.base.exception.EnviarSolicitudException;
import com.egoveris.vucfront.base.mail.CaratulacionMail;
import com.egoveris.vucfront.base.model.ExpedienteBase;
import com.egoveris.vucfront.base.model.ExpedienteFamiliaSolicitud;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.repository.ExpedienteBaseRepository;
import com.egoveris.vucfront.base.repository.ExpedienteFamiliaSolicitudRepository;
import com.egoveris.vucfront.base.service.MailService;
import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.base.util.SistemaEnum;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.DomicilioDTO;
import com.egoveris.vucfront.model.model.EstadoTramiteDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.ExpedienteBaseService;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;
import com.egoveris.vucfront.model.util.EstadoTramiteEnum;

@Service
public class ExpedienteServiceImpl implements ExpedienteService {
	private static final Logger LOG = LoggerFactory.getLogger(ExpedienteServiceImpl.class);

	@Autowired
	@Qualifier("vucMapper")
	private Mapper mapper;

	private static final String HTML_BEGINTABLE = "<table>";
	private static final String HTML_ENDTABLE = "</table><br />";
	private static final String HTML_BEGINITALICBOLD = "<b><u>";
	private static final String HTML_ENDITALICBOLDBREAKLINE = "</u></b><br>";
	private static final String SELLO_REPA = "Ventanilla Única de Ciudadano";

	@Autowired
	private ExpedienteBaseService expedienteBaseService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private WebDavService webDavService;
	@Autowired
	private IAdministracionDeDocumentosOficialesService externalAdminDocumentOficialService;
	@Autowired
	private IExternalGenerarDocumentoService externalGenerarDocumentoService;
	@Autowired
	private IGenerarExpedienteService externalGenerarExpedienteService;
	@Autowired
	private IGenerarPaseExpedienteService externalGenerarPaseExpService;
	@Autowired
	private ExpedienteFamiliaSolicitudRepository expedienteFamiliaSolicitudRepository;
	@Autowired
	private ExpedienteBaseRepository expedienteBaseRepository;
	
	@Autowired
	@Qualifier("mailServiceVUC")
	private MailService mailService;

	@Value("${ley104.motivoPase}")
	private String motivoPaseFuncionario;
	
	@Autowired
	private AppProperty appProperty;
	

	@Override
	@Transactional
	public ExpedienteFamiliaSolicitudDTO getExpedienteFamiliaSolicitudById(Long idExpediente) {
		ExpedienteFamiliaSolicitud result = expedienteFamiliaSolicitudRepository.findOne(idExpediente);

		return mapper.map(result, ExpedienteFamiliaSolicitudDTO.class);

	}

	@Override
	public void sendApplication(ExpedienteFamiliaSolicitudDTO expediente, Map<String, String> infoPago) {
		LOG.info("==> Finalizando el Trámite en VUC, expediente: {}", expediente.getId());

		try {
			// Adding step1 info to the Expediente
			expediente.setSeVaAReintentar(false); // ???
//			generaSolicitanteDocumentoDeo(expediente);

			// Adding step2 documents to the Expediente
			generaDocumentosDeoDocumentosSubidos(expediente);

			// Adding step2 ffdd documents to the Expediente
			addFfddDocuments(expediente);

			// agregar documento de pago
			if(BooleanUtils.isTrue(expediente.getTipoTramite().getPago())) {				
				generarDocuemntoPago(expediente, infoPago);
			}


			// Genera el expediente en TE
			generaExpedienteElectronico(expediente);
			

			// Vincular documentos al expediente
			vinculateDocuments(expediente);

			// Generar pase
			generarPase(expediente);

			// Cambia el estado del trámite
			expediente.setEstadoTramite(new EstadoTramiteDTO(EstadoTramiteEnum.ENTRAMITE));
			// Guardar expediente en BD VUC
			expedienteBaseService.saveExpedienteFamiliaSolicitud(expediente);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new EnviarSolicitudException("Se ha producido un error al enviar la solicitud.");
		}

		LOG.info("==> Expediente generado");
	}

	private void generarDocuemntoPago(ExpedienteFamiliaSolicitudDTO expediente, Map<String, String> infoPago) throws Exception {
		
		if (expediente.getNumeroAutorizacion() == null || expediente.getNumeroAutorizacion().isEmpty()
				|| expediente.getTransaccionPago() == null || expediente.getTransaccionPago().isEmpty()) {
			throw new EnviarSolicitudException("Hubo en error en el servicio de pago. Vuelva a intentarlo");
		}
		
		expediente.setMonto(new BigDecimal(expediente.getTipoTramite().getMonto()));
		expediente.setApiKeyTransaccion(expediente.getTipoTramite().getApiKey());
		expediente.setNombreTitutarTarjeta(infoPago.get("titular"));
		
		DocumentoDTO adjuntarDocPago =
				documentoService.generateDocumentoGedoLibre(expediente,
				expediente.getTipoTramite().getUsuarioIniciador(), expediente.getPersona(),
				infoPago);
  
		try {
			expediente.getDocumentosList().add(adjuntarDocPago);
			
			expedienteBaseService.saveExpedienteFamiliaSolicitud(expediente);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new Exception("Hubo en error al guardar el documento de pago");
		}
	}
	
	/**
	 * Genera un documento DEO para la información del solicitante y lo anexa al
	 * expediente en VUC.
	 * 
	 * @param expediente
	 */
	private void generaSolicitanteDocumentoDeo(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> generaDocumentoDeoDatosSolicitante");

		// Verifica que no se haya generado este documento previamente
		Optional<DocumentoDTO> match = expediente.getDocumentosList().stream()
				.filter(d -> d.getTipoDocumento().equals(expediente.getTipoTramite().getTipoDocumentoFormulario()))
				.findFirst();

		if (!match.isPresent()) {
			DocumentoDTO solicitanteDoc = new DocumentoDTO();
			solicitanteDoc.setArchivo(generarHtmlSolicitante(expediente).getBytes(StandardCharsets.UTF_8));
			solicitanteDoc.setTipoDocumento(expediente.getTipoTramite().getTipoDocumentoFormulario());

			DocumentoDTO solicitanteDocGedo = documentoService.generateDocumentoGedo(solicitanteDoc,
					expediente.getTipoTramite().getUsuarioIniciador(), expediente.getPersona());

			// Lo anexa el expediente en VUC
			expediente.getDocumentosList().add(solicitanteDocGedo);
			expedienteBaseService.saveExpedienteFamiliaSolicitud(expediente);
		} else {
			LOG.info("\t==> Ya se encuentra generado el documento. id: {}", match.get().getId());
		}
	}

	/**
	 * Envia a DEO los archivos que fueron subidos a WebDav en el step 2 y los
	 * asocia al expediente en VUC.
	 * 
	 * @param expediente
	 */
	private void generaDocumentosDeoDocumentosSubidos(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> generaDocumentosDeoArchivosSubidos");

		List<DocumentoDTO> removedDocuments = null;
		List<DocumentoDTO> addedDocuments = null;

		for (DocumentoDTO docExpediente : expediente.getDocumentosList()) {
			// Los documentos que tienen Url Temporal están en el WebDav
			if (StringUtils.isNotBlank(docExpediente.getUrlTemporal())) {
				// Recupera los bytes desde WebDav
				docExpediente.setArchivo(webDavService.getDocumentoByteArray(docExpediente.getUrlTemporal()));
				// Los envia a GEDO para su creación
				DocumentoDTO deoDocument = documentoService.generateDocumentoGedo(docExpediente,
						expediente.getTipoTramite().getUsuarioIniciador(), expediente.getPersona());

				// Borra el documento del WebDav
				webDavService.deleteDocument(docExpediente);

				// Instancia las listas para modificaciones de documentos
				if (removedDocuments == null && addedDocuments == null) {
					removedDocuments = new ArrayList<>();
					addedDocuments = new ArrayList<>();
				}
				// Reemplaza el documento del expediente por el de DEO
				addedDocuments.add(deoDocument);
				removedDocuments.add(docExpediente);
			}
		}

		// Si se reemplazaron los documentos del WebDav por los de DEO,
		// se remplazan en el expediente,
		if (removedDocuments != null && addedDocuments != null) {
			expediente.getDocumentosList().removeAll(removedDocuments);
			expediente.getDocumentosList().addAll(addedDocuments);

			expedienteBaseService.saveExpedienteFamiliaSolicitud(expediente);
		} else {
			LOG.info("\t==> Los documentos del expediente ya se encuentran registrados en DEO.");
		}

	}

	private void addFfddDocuments(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> adding FFDD documents");
		// TODO similar a documentoService.generateDocumentoGedo: Unificar
		if (!expediente.getDocumentosList().isEmpty()) {
			List<DocumentoDTO> newDocumentList = new ArrayList<>();

			for (DocumentoDTO aux : expediente.getDocumentosList()) {
				if (aux.getIdTransaccion() != null) {
					RequestExternalGenerarDocumentoUsuarioExterno request = new RequestExternalGenerarDocumentoUsuarioExterno();
					request.setReferencia(aux.getTipoDocumento().getNombre());
					request.setAcronimoTipoDocumento(aux.getTipoDocumento().getAcronimoGedo());
					request.setIdTransaccion(aux.getIdTransaccion().intValue());
					request.setSistemaOrigen(SistemaEnum.VUC.getNombre());
					// INI - En documentos con formato template no viene usuario
					request.setUsuario(validaUsuario(expediente, aux.getTipoDocumento().getUsuarioIniciador()));
					// FIN - En documentos con formato template no viene usuario
					
					StringBuilder cargo = new StringBuilder();
					cargo.append(expediente.getPersona().getTipoDocumentoIdentidad()).append(" ").append(expediente.getPersona().getCuit());
					request.setNombreYApellido(expediente.getPersona().getNombreApellido());
					request.setCargo(cargo.toString());
					request.setReparticion("-");
					request.setSector(SELLO_REPA);
					ResponseExternalGenerarDocumento documentoGenerado = externalGenerarDocumentoService
							.generarDocumentoUsuarioExterno(request);

					aux.setNumeroDocumento(documentoGenerado.getNumero());
					aux.setFechaCreacion(new Date());
				}
				newDocumentList.add(aux);
			}

			// Limpia la lista de documentos antigua
			expediente.getDocumentosList().clear();
			// Se cargan los documentos FFDD al expediente
			expediente.getDocumentosList().addAll(newDocumentList);

		}
	}

	/**
	 * Generates the Caratulación of the Expediente Electrónico.
	 * 
	 * @param expediente
	 */
	private void generaExpedienteElectronico(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> generaExpedienteElectronico");
		if (expediente.getNumeroExpediente() != null && !expediente.getNumeroExpediente().isEmpty()) {
			LOG.info("\t==> El expediente ya se encuentra en TE: {}", expediente.getCodigoSade());
		} else {
			CaratulacionExpedienteRequest request = new CaratulacionExpedienteRequest();

			request.setLoggeduser(expediente.getTipoTramite().getUsuarioIniciador());
			request.setSistema(SistemaEnum.VUC.getNombre());
			request.setSelectTrataCod(expediente.getTipoTramite().getTrata());
			request.setExterno(true);
			request.setInterno(false);
			request.setMotivoExterno(expediente.getMotivo()); // dónde se setea?
			request.setDescripcion("Tramite-VUC");

			// validacion por personas juridicas
			if (expediente.getPersona().getRazonSocial() != null) {
				request.setEmpresa(true);
				request.setPersona(false);
				request.setRazonSocial(expediente.getPersona().getRazonSocial());
			} else {
				// personas fisicas
				request.setPersona(true);
				request.setEmpresa(false);
				request.setTipoDoc(expediente.getPersona().getTipoDocumentoIdentidad());
				request.setNroDoc(expediente.getPersona().getDocumentoIdentidad());
				request.setNombre(expediente.getPersona().getNombre1());
				request.setSegundoNombre(expediente.getPersona().getNombre2());
				request.setTercerNombre(expediente.getPersona().getNombre3());
				request.setApellido(expediente.getPersona().getApellido1());
				request.setSegundoApellido(expediente.getPersona().getApellido2());
				request.setTercerApellido(expediente.getPersona().getApellido3());
			}

			request.setPiso(expediente.getPersona().getDomicilioConstituido().getPiso());
			request.setDepartamento(expediente.getPersona().getDomicilioConstituido().getDepto());
			request.setTieneCuitCuil(true);
			request.setCuitCuil(expediente.getPersona().getCuit());
			request.setDomicilio(expediente.getPersona().getDomicilioConstituido().getDireccion().concat(" - ")
					.concat(expediente.getPersona().getDomicilioConstituido().getAltura()));
			request.setCodigoPostal(expediente.getPersona().getDomicilioConstituido().getCodPostal());
			request.setEmail(expediente.getPersona().getEmail());
			request.setTelefono(expediente.getPersona().getTelefonoContacto().replace(" ", ""));

			String nroExpediente = externalGenerarExpedienteService.generarExpedienteElectronicoCaratulacion(request);
			LOG.info("\t==> Expediente generado, nro: ".concat(nroExpediente));

			enviarMail(expediente, nroExpediente);
			
			expediente.setFechaCreacion(new Date());
			expediente.setNumeroExpediente(nroExpediente);
			expedienteBaseService.saveExpedienteFamiliaSolicitud(expediente);
		}
	}

	private void enviarMail(ExpedienteFamiliaSolicitudDTO expediente, String nroExpediente) {
		String destinatario = expediente.getPersona().getEmail();
		try {
			
			DocumentoDTO docPago = expediente.getDocumentosList()
					.stream().filter(doc->doc.getTipoDocumento()
							.getAcronimoTad().equals(appProperty.getString("acronimo.pago.documento")))
					.findFirst().orElse(null);
			
			byte[] docPagoPDF = null;
			
			if(docPago!=null) {
				try {
					docPagoPDF = IOUtils.toByteArray(documentoService.downloadDocument(docPago));					
				} catch (Exception e) {
					LOG.error("Error en la descarga del documento DEO:  " + e.getMessage(),e);
				}
			}
			
			CaratulacionMail caratulacion = new CaratulacionMail();
			caratulacion.setNumeroTramite(expediente.getId().toString());
			caratulacion.setNombreCompleto(expediente.getPersona().getNombreApellido());
			caratulacion.setDocPDF(docPagoPDF);
			caratulacion.setNumeroCaratula(nroExpediente);
			caratulacion.setFecha(new Date());
			caratulacion.setNombreTramite(expediente.getTipoTramite().getNombre());
			caratulacion.setCodigoTramite(expediente.getTipoTramite().getTrata());
			if(docPago!=null) {				
				caratulacion.setNombreDocumento(docPago.getNombreOriginal());
			}
			
			mailService.enviarMailCaraturalacion(caratulacion, destinatario);
			
		} catch (Exception e) {
			LOG.error("Hubo un error al enviar el mail al destinatario {}: {}", destinatario,e.getMessage(),e);
		}
	}

	// INI - En documentos con formato template no viene usuario
	private String validaUsuario(ExpedienteFamiliaSolicitudDTO expediente, String usuarioIniciador) {
		String usuario;
		if (expediente.getTipoTramite().getUsuarioIniciador() != null) {
			usuario = expediente.getTipoTramite().getUsuarioIniciador();
		} else {
			usuario = usuarioIniciador;
		}
		return usuario;
	}
	// FIN - En documentos con formato template no viene usuario

	/**
	 * Generates an HTML with data of the Solicitante: Personal Info, Domicilio and
	 * Detalle Solicitud.
	 */
	private String generarHtmlSolicitante(ExpedienteFamiliaSolicitudDTO expediente) {
		StringBuilder dataformulario = new StringBuilder();
		dataformulario.append(generarHtmlTablePersona(expediente.getPersona(), "Datos Solicitante"));
		dataformulario.append(
				generarHtmlTableDomicilio(expediente.getPersona().getDomicilioConstituido(), "Domicilio Constituido"));
		dataformulario
				.append(generarHtmlTableDetalleSolicitud(expediente.getDetalleSolicitud(), "Detalle de la Solicitud"));

		return dataformulario.toString();
	}

	private String generarHtmlTablePersona(PersonaDTO persona, String titulo) {
		if (persona == null) {
			return StringUtils.EMPTY;
		}

		StringBuilder data = new StringBuilder(HTML_BEGINITALICBOLD + titulo + HTML_ENDITALICBOLDBREAKLINE);
		data.append(HTML_BEGINTABLE);
		// Datos de persona física
		if (persona.getNombreApellido() != null) {
			data.append(getHtmlRowWithData("Nombre y Apellidos", persona.getNombreApellido()));
			data.append(getHtmlRowWithData("Primer Nombre", persona.getNombre1()));
			data.append(getHtmlRowWithData("Segundo Nombre", persona.getNombre2()));
			data.append(getHtmlRowWithData("Tercer Nombre", persona.getNombre3()));
			data.append(getHtmlRowWithData("Primer Apellido", persona.getApellido1()));
			data.append(getHtmlRowWithData("Segundo Apellido", persona.getApellido2()));
			data.append(getHtmlRowWithData("Tercer Apellido", persona.getApellido3()));
			data.append(getHtmlRowWithData("RUT", persona.getCuit()));
			data.append(getHtmlRowWithData("Email de Contacto", persona.getEmail()));
			data.append(getHtmlRowWithData("Teléfono de Contacto", persona.getTelefonoContacto()));
			data.append(getHtmlRowWithData("Sexo", persona.getSexo()));
			data.append(getHtmlRowWithData("Tipo de Documento", persona.getTipoDocumentoIdentidad()));
		} else {
			// Datos de persona jurídica
			data.append(getHtmlRowWithData("Razón Social", persona.getRazonSocial()));
			data.append(getHtmlRowWithData("CUIT/CUIL/CDI", persona.getCuit()));
			data.append(getHtmlRowWithData("Email de Contacto", persona.getEmail()));
			data.append(getHtmlRowWithData("Teléfono de Contacto", persona.getTelefonoContacto()));
		}
		// fin datos
		data.append(HTML_ENDTABLE);

		return data.toString();
	}

	private String generarHtmlTableDomicilio(DomicilioDTO domicilio, String titulo) {
		if (domicilio == null) {
			return StringUtils.EMPTY;
		}

		StringBuilder data = new StringBuilder(HTML_BEGINITALICBOLD + titulo + HTML_ENDITALICBOLDBREAKLINE);
		data.append(HTML_BEGINTABLE);
		data.append(getHtmlRowWithData("Dirección", domicilio.getDireccionAltura()));
		data.append(getHtmlRowWithData("Piso/Depto", domicilio.getPisoDepto()));
		data.append(getHtmlRowWithData("Local", domicilio.getLocal()));
		data.append(getHtmlRowWithData("Código Postal", domicilio.getCodPostal()));
		data.append(getHtmlRowWithData("Teléfono", domicilio.getTelefono()));
		data.append(HTML_ENDTABLE);
		return data.toString();
	}

	private String generarHtmlTableDetalleSolicitud(String detalleSolicitud, String titulo) {
		if (detalleSolicitud == null) {
			return StringUtils.EMPTY;
		}

		StringBuilder data = new StringBuilder();
		data.append(HTML_BEGINITALICBOLD + titulo + HTML_ENDITALICBOLDBREAKLINE);
		data.append(HTML_BEGINTABLE);
		data.append("<tr><td>" + detalleSolicitud + "<td></tr>");
		data.append(HTML_ENDTABLE);

		return data.toString();
	}

	/**
	 * Return a HTML table row with data.
	 * 
	 * @param title
	 * @param value
	 * @return
	 */
	private String getHtmlRowWithData(String title, String value) {
		StringBuilder retorno = new StringBuilder(StringUtils.EMPTY);
		if (title != null && !title.isEmpty()) {
			retorno.append("<tr><td><b>");
			retorno.append(title);
			retorno.append("</b><td>");
			// Validates if the value is null or empty
			if (value == null || value.isEmpty()) {
				retorno.append(StringUtils.EMPTY);
			} else {
				retorno.append(value);
			}
			retorno.append("<td></tr>");
		}
		return retorno.toString();
	}

	/**
	 * 
	 * @param expediente
	 */
	private void vinculateDocuments(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> Vinculando los documentos al expediente");
		List<String> codigoDocumentos = new ArrayList<>();
		for (DocumentoDTO aux : expediente.getDocumentosList()) {
			codigoDocumentos.add(aux.getCodigoSade());
		}
		externalAdminDocumentOficialService.vincularDocumentosOficiales(SistemaEnum.VUC.getNombre(),
				expediente.getTipoTramite().getUsuarioIniciador(), expediente.getCodigoSade(), codigoDocumentos);
	}

	/**
	 * 
	 * @param expediente
	 */
	private void generarPase(ExpedienteFamiliaSolicitudDTO expediente) {
		LOG.info("==> Generando pase");
		PaseExpedienteRequest request = new PaseExpedienteRequest();
		request.setCodigoEE(expediente.getCodigoSade());
		request.setUsuarioOrigen(expediente.getTipoTramite().getUsuarioIniciador());
		request.setEstadoSeleccionado(null);
		request.setMotivoPase(motivoPaseFuncionario);
		if (expediente.getTipoTramite().getSectorIniciador() != null
				&& !expediente.getTipoTramite().getSectorIniciador().isEmpty()) {
			request.setEsSectorDestino(true);
		} else {
			request.setEsReparticionDestino(true);
		}
		request.setSectorDestino(expediente.getTipoTramite().getSectorIniciador());
		request.setReparticionDestino(expediente.getTipoTramite().getReparticionIniciadora());
		request.setSistemaOrigen(SistemaEnum.VUC.getNombre());
		externalGenerarPaseExpService.generarPaseEEConDesbloqueo(request);
	}

	@Override
	@Transactional
	public ExpedienteBaseDTO getExpedienteBaseByCodigo(String codSadeExpediente) {

		ExpedienteBase result = expedienteBaseRepository.findByNumeroExpediente(codSadeExpediente);

		if (result != null) {
			return mapper.map(result, ExpedienteBaseDTO.class);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ExpedienteBaseDTO> getExpedientesBaseByPersona(PersonaDTO persona) {
		List<ExpedienteBaseDTO> retorno = new ArrayList<>();

		List<ExpedienteBase> result = expedienteBaseRepository
				.findByPersonaOrderByFechaCreacionDesc(mapper.map(persona, Persona.class));
		if (result != null) {
			retorno = ListMapper.mapList(result, mapper, ExpedienteBaseDTO.class);
		}
		return retorno;
	}
}