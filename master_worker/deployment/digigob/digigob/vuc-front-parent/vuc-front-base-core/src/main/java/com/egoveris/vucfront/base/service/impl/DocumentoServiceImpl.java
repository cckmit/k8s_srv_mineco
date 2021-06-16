package com.egoveris.vucfront.base.service.impl;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumentoUsuarioExterno;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.vucfront.base.exception.DownloadDocumentException;
import com.egoveris.vucfront.base.model.Documento;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.model.TipoDocumento;
import com.egoveris.vucfront.base.model.TipoTramite;
import com.egoveris.vucfront.base.model.TipoTramiteTipoDoc;
import com.egoveris.vucfront.base.repository.DocumentoRepository;
import com.egoveris.vucfront.base.repository.TipoDocumentoRepository;
import com.egoveris.vucfront.base.repository.TipoTramiteRepository;
import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.base.util.SistemaEnum;
import com.egoveris.vucfront.model.exception.ValidacionException;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentoServiceImpl.class);

	@Autowired
	@Qualifier("vucMapper")
	private Mapper mapper;
	@Value("${app.vuc.url}")
	private String appVucUrl;

	@Autowired
	private ExpedienteService expedienteService;
	@Autowired
	private IExternalGenerarDocumentoService externalGenerarDocumentoService;
	@Autowired
	private IExternalTipoDocumentoService externalTipoDocumentoService;
	@Autowired
	@Qualifier("consultaDocumento3Service")
	private IExternalConsultaDocumentoService externalConsultaDocumentoService;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	@Autowired
	private TipoTramiteRepository tipoTramiteRepository;
	@Autowired
	private WebDavService webDavService;
	
	@Autowired
	private IExternalTipoDocumentoService tipoDocumentoService;
	
	@Autowired
	private ResourceBundle mensajeProperties;
	
//	@Value("${comprobante.url}")
	private String urlComprobante;

	private static final String MENSAJE_COMPROBANTE = "Este comprobante fue generado automáticamente por la ventanilla Nacional"
			+ " de Trámites del Gobierno de El Salvador y confirma que el usuario realizó un pago en linea según se detalla a "
			+ "continuación:";
	

	@Autowired
	private AppProperty appProperty;

//	@Value("${acronimo.pago.documento}")
	private String acronimoDocumentoPago;
	
	private static final String PDF_SERVLET_URI = "/viewArchivo";
	private static final String SELLO_REPA = "Ventanilla Única de Ciudadano";

	@PostConstruct
	public void cargarConfiguraciones() throws Exception {
		
		if(this.acronimoDocumentoPago==null) {
			this.acronimoDocumentoPago = appProperty.getString("acronimo.pago.documento");
		
		}
		if(this.urlComprobante==null) {
			this.urlComprobante = appProperty.getString("comprobante.url");		
		}
	}
	
	@Override
	public void downloadNotificacionDocument(String encrytpedNotificacionId) {
		if (StringUtils.isBlank(encrytpedNotificacionId)) {
			throw new ValidacionException("encrytpedNotificacionId IS BLANK");
		}
		if (StringUtils.isEmpty(appVucUrl)) {
			throw new ValidacionException("app.vuc.url property IS EMPTY");
		}

		// GET params
		Map<String, String> parameters = new HashMap<>();
		parameters.put("dwn", "yes");
		parameters.put("idNotif", encrytpedNotificacionId);

		doPdfServletRequest(parameters);
	}

	@Override
	public InputStream downloadDocument(DocumentoDTO document) {
		if (document == null) {
			throw new ValidacionException("document IS NULL");
		} else if (StringUtils.isBlank(document.getCodigoSade())) {
			throw new ValidacionException("codigoSade IS BLANK");
		} else if (StringUtils.isBlank(document.getUsuarioCreacion())) {
			throw new ValidacionException("usuarioCreacion IS BLANK");
		} else if (StringUtils.isBlank(document.getNombreOriginal())) {
			throw new ValidacionException("nombreOriginal IS BLANK");
		}

		String genericErrorMsg = "Se ha producido un error al descargar el documento.";
		InputStream returnInput = null;
		byte[] bytesFile = null;

		// In WebDav
		if (StringUtils.isNotBlank(document.getUrlTemporal())) {
			LOG.info(">>> searching in WebDav");
			try {
				bytesFile = webDavService.getDocumentoByteArray(document.getUrlTemporal());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new DownloadDocumentException(genericErrorMsg);
			}
		}
		// In DEO
		else {
			LOG.info(">>> searching in DEO");
			try {
				bytesFile = getDocumentoGedoArrayBytes(document.getCodigoSade(), document.getUsuarioCreacion());
			} catch (DocumentoNoExisteException e1) {
				throw new DownloadDocumentException(
						"El documento: ".concat(document.getCodigoSade()).concat(" no existe en el sistema."));
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new DownloadDocumentException(genericErrorMsg);
			}
		}

		if (bytesFile != null) {
			returnInput = new ByteArrayInputStream(bytesFile);
		} else {
			throw new DownloadDocumentException("No se ha podido descargar el documento.");
		}

		return returnInput;
	}

	@Override
	public DocumentoDTO getDocumentById(Long id) {
		DocumentoDTO retorno = null;

		Documento resultado = documentoRepository.findOne(id);
		if (resultado != null) {
			retorno = mapper.map(resultado, DocumentoDTO.class);
		}

		return retorno;
	}

	@Override
	public void deleteDocument(DocumentoDTO document) {
		documentoRepository.delete(mapper.map(document, Documento.class));
	}

	@Override
	public DocumentoDTO generateDocumentoGedo(DocumentoDTO document, String usuarioIniciador,
			PersonaDTO ownerExpediente) {
		DocumentoDTO retorno;

		RequestExternalGenerarDocumentoUsuarioExterno request = new RequestExternalGenerarDocumentoUsuarioExterno();
		request.setSistemaOrigen(SistemaEnum.VUC.getNombre());
		request.setAcronimoTipoDocumento(document.getTipoDocumento().getAcronimoGedo());
		request.setReferencia(document.getTipoDocumento().getNombre());
		request.setUsuario(
				usuarioIniciador == null ? document.getTipoDocumento().getUsuarioIniciador() : usuarioIniciador);
		request.setData(document.getArchivo());
		request.setIdTransaccion(document.getIdTransaccion() != null ? document.getIdTransaccion().intValue() : null);
		
		StringBuilder cargo = new StringBuilder();
		cargo.append(ownerExpediente.getTipoDocumentoIdentidad()).append(" ").append(ownerExpediente.getCuit());
		request.setNombreYApellido(ownerExpediente.getNombreApellido());
		request.setCargo(cargo.toString());
		request.setReparticion("-");
		request.setSector(SELLO_REPA);

		LOG.info("Generando documento DEO del tipo: --".concat(document.getTipoDocumento().getNombre()
				.concat("-- ID: --").concat(document.getTipoDocumento().getId().toString())).concat("--"));
		ResponseExternalGenerarDocumento response = this.externalGenerarDocumentoService.generarDocumentoUsuarioExterno(request);
		retorno = guardarDocumento(response, document, usuarioIniciador, ownerExpediente);

		return retorno;
	}
	
	
	@Override
	public DocumentoDTO generateDocumentoGedoLibre(ExpedienteFamiliaSolicitudDTO expediente,  String usuarioIniciador,
			PersonaDTO ownerExpediente, Map<String, String> infoPago) throws Exception {
		DocumentoDTO retorno;

		DocumentoDTO docPago = new DocumentoDTO();
		
		crearDocumentoPago(expediente, docPago);
		
		RequestExternalGenerarDocumento request = 
				this.armarRequestGedoDocumentoPago(expediente, ownerExpediente, 
						usuarioIniciador, infoPago);



		LOG.info("Generando documento DEO del tipo: --".concat(docPago.getTipoDocumento().getNombre()
				.concat("-- ID: --").concat(docPago.getTipoDocumento().getId().toString())).concat("--"));
		ResponseExternalGenerarDocumento response = this.externalGenerarDocumentoService.generarDocumentoGEDO(request);
		retorno = guardarDocumento(response, docPago, usuarioIniciador, ownerExpediente);

		return retorno;
	}

	private void crearDocumentoPago(ExpedienteFamiliaSolicitudDTO expediente, DocumentoDTO docPago) throws Exception {
		TipoDocumentoDTO tipoDocumentoPago = this.getTipoDocumentoByAcronimoGedo(acronimoDocumentoPago);

		if(tipoDocumentoPago == null) {
			throw new Exception(String.format("El documento DEO con el acronimo %s no se encontro", acronimoDocumentoPago));
		}
		
		docPago.setTipoDocumento(tipoDocumentoPago);
		docPago.setReferencia(tipoDocumentoPago.getNombre());
		docPago.setUsuarioCreacion(expediente.getPersona().getNombreApellido());
		docPago.setNombreOriginal(tipoDocumentoPago.getNombre());
		// Se setea "discretamente" el id del expediente al Documento en el campo
		// urlTemporal. Mientras no sea persistido, se mantendrá el Documento sin
		// asociarse al expediente formalmente.
		docPago.setUrlTemporal(expediente.getId().toString());
	}
	
	
  private RequestExternalGenerarDocumento armarRequestGedoDocumentoPago(
  		ExpedienteFamiliaSolicitudDTO expediente, PersonaDTO persona, String usuarioIniciador, Map<String, String> infoPago)
      throws Exception {
   
  	ResponseTipoDocumento documentoPago = tipoDocumentoService
  			.buscarTipoDocumentoByAcronimo(acronimoDocumentoPago);

    if (documentoPago == null) {
      throw new Exception(String.format("El documento con el acronimo %s "
      		+ "no se pudo encontrar", acronimoDocumentoPago));
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setData(generarDataDePago(persona, expediente, infoPago));
    request.setReferencia(documentoPago.getNombre());
    // Se pone el Acronimo para diferenciar los documentos de SADE con los
    // de GEDO.
    request.setAcronimoTipoDocumento(acronimoDocumentoPago);
    request.setUsuario(usuarioIniciador); //VER que usuario envio aca
    request.setSistemaOrigen(SistemaEnum.VUC.getNombre());

    return request;
  }
	
	
	private byte[] generarDataDePago(PersonaDTO persona, ExpedienteFamiliaSolicitudDTO expediente, 
			Map<String, String> infoPago) throws Exception {
		
		SimpleDateFormat fechaPago = new SimpleDateFormat();
		
		fechaPago.applyPattern("dd/MM/yyyy HH:mm:ss");
		
		StringBuilder informacionPagoDoc = new StringBuilder();
		
		String monto = this.formatMontoStr(new Float(expediente.getTipoTramite().getMonto()));
		
		informacionPagoDoc
		.append(getMensajeComprobante())
		.append(" <br >")
		.append(" <div style=\"border-style: solid; padding: 16px; \">")
		.append("<br")
		.append("Trámite: ").append(expediente.getTipoTramite().getNombre())
		.append(" <br> ")
		.append(" Nombre/s y Apellido/s: ").append(persona.getNombreApellido())
		.append(" <br> ")
		.append(" Monto: $ ").append(monto)
		.append(" <br> ")
		.append(" Número de Autorización: ").append(expediente.getNumeroAutorizacion())
		.append(" <br> ")
		.append(" Número de Referencia: ").append(expediente.getTransaccionPago())
		.append(" <br> ")
		.append(" Número de Cuenta: ").append(infoPago.get("nroTarjeta"))
		.append(" <br> ")
		.append(" Titular de cuenta: ").append(infoPago.get("titular"))
		.append(" <br> ")
		.append(" Código de Ventanilla GOES: ").append(expediente.getId())
		.append(" <br> ")		
		.append(" <br> ")
		.append("<img src=\"data:image/png;base64, " 
		+  generateBarcodeImage(expediente.getId().toString())+  
		" \" alt=\"Barra de codigo GOES\" />")
		.append(" <br> ")
		.append(urlComprobante)
		.append(" </div>");
		
		
		
		return informacionPagoDoc.toString().getBytes();
	}
	
	private String getMensajeComprobante() {
		try {
			return mensajeProperties.getString("comprobante.mensaje");
		} catch (Exception e) {
			return MENSAJE_COMPROBANTE;
		}
	}
	
	private  String formatMontoStr(float val) {
		 return String.format(Locale.CANADA, "%,.2f", val);
	}
	
	private String generateBarcodeImage(String barcodeText) throws Exception {

		try {
				BufferedImage barcode = null;
				Code128Writer barcodeWriter = new Code128Writer();
				BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 290, 70);
				barcode = MatrixToImageWriter.toBufferedImage(bitMatrix);
				
			
			return imgToBase64String(barcode, "png");
			
		} catch (Exception e) {
			LOG.error("Error al generar el codigo de barra: " + e.getMessage(),e);
			return StringUtils.EMPTY;
		}
	}
	
	private String imgToBase64String(final RenderedImage img, final String formatName) {
    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
        return os.toString(StandardCharsets.ISO_8859_1.name());
    } catch (final IOException ioe) {
        throw new UncheckedIOException(ioe);
    }
}

	private DocumentoDTO guardarDocumento(ResponseExternalGenerarDocumento response, DocumentoDTO document,
			String usuarioIniciador, PersonaDTO ownerExpediente) {
		DocumentoDTO retorno = new DocumentoDTO();

		retorno.setFechaCreacion(new Date());
		retorno.setUsuarioCreacion(usuarioIniciador);
		retorno.setReferencia(document.getTipoDocumento().getNombre());
		retorno.setTipoDocumento(document.getTipoDocumento());
		retorno.setPersona(ownerExpediente);
		retorno.setNumeroDocumento(response.getNumero());
		retorno.setIdTransaccion(document.getIdTransaccion());
		
		if (document.getNombreOriginal() != null && !document.getNombreOriginal().isEmpty()) {
			retorno.setNombreOriginal(document.getNombreOriginal());
		} else if (document.getTipoDocumento().getNombre() != null && !document.getTipoDocumento().getNombre().isEmpty()) {
			retorno.setNombreOriginal(document.getTipoDocumento().getNombre());
		}

		Documento docToBeSaved = mapper.map(retorno, Documento.class);
		documentoRepository.save(docToBeSaved);
		retorno.setId(docToBeSaved.getId());

		return retorno;
	}

	@Override
	public byte[] getDocumentoGedoArrayBytes(String codigoSade, String usuarioCreador) {
		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		request.setNumeroDocumento(codigoSade);
		request.setUsuarioConsulta(usuarioCreador);
		return this.externalConsultaDocumentoService.consultarDocumentoPdf(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoDTO> getDocumentosDeoByPersona(PersonaDTO persona) {
		List<DocumentoDTO> retorno = new ArrayList<>();

		List<Documento> result = documentoRepository
				.findByPersonaAndUrlTemporalIsNullOrderByFechaCreacionDesc(mapper.map(persona, Persona.class));
		if (result != null) {
			retorno = ListMapper.mapList(result, mapper, DocumentoDTO.class);
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoDTO> getDocumentosDeoByPersonaAndTipodocumento(PersonaDTO persona, TipoDocumentoDTO tipoDoc) {
		List<DocumentoDTO> retorno = new ArrayList<>();

		List<Documento> result = documentoRepository
				.findByPersonaAndTipoDocumentoAndUrlTemporalIsNullOrderByFechaCreacionDesc(
						mapper.map(persona, Persona.class), mapper.map(tipoDoc, TipoDocumento.class));
		if (result != null) {
			retorno = ListMapper.mapList(result, mapper, DocumentoDTO.class);
		}

		return retorno;
	}

	@Override
	public DocumentoDTO getDocumentoDeoByCodigo(String codigoSade, String usuarioConsulta) {
		DocumentoDTO retorno = null;
		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		request.setUsuarioConsulta(usuarioConsulta);
		request.setNumeroDocumento(codigoSade);
		ResponseExternalConsultaDocumento response = this.externalConsultaDocumentoService
				.consultarDocumentoPorNumero(request);

		if (response != null) {
			retorno = new DocumentoDTO();

			retorno.setNumeroDocumento(response.getNumeroDocumento());
			retorno.setFechaCreacion(response.getFechaCreacion());
			TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
			ResponseTipoDocumento responseTipoDoc = externalTipoDocumentoService
					.consultarTipoDocumentoPorAcronimo(response.getTipoDocumento());
			tipoDoc.setAcronimoGedo(responseTipoDoc.getAcronimo());
			tipoDoc.setDescripcion(responseTipoDoc.getDescripcion());
			retorno.setTipoDocumento(tipoDoc);

		}

		return retorno;
	}

	@Override
	public List<DocumentoDTO> getDocumentosByExpedienteCodigo(String codigoExpediente) {
		List<DocumentoDTO> retorno = new ArrayList<>();
		ExpedienteBaseDTO result = expedienteService.getExpedienteBaseByCodigo(codigoExpediente);
		if (result.getDocumentosList() != null && !result.getDocumentosList().isEmpty()) {
			for (DocumentoDTO aux : result.getDocumentosList()) {
				retorno.add(aux);
			}
		}
		return retorno;
	}

	@Override
	public TipoDocumentoDTO getTipoDocumentoById(Long id) {
		TipoDocumento result = tipoDocumentoRepository.findOne(id);
		if (result != null) {
			return mapper.map(result, TipoDocumentoDTO.class);
		}
		return null;
	}

	@Override
	public TipoDocumentoDTO getTipoDocumentoByAcronimoGedo(String acronimoGedo) {
		TipoDocumento result = tipoDocumentoRepository.getByAcronimoGedo(acronimoGedo);
		if (result != null) {
			return mapper.map(result, TipoDocumentoDTO.class);
		}
		return null;
	}

	@Override
	public TipoDocumentoDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc) {
		TipoDocumento result = tipoDocumentoRepository.getByAcronimoTad(acronimoVuc);
		if (result != null) {
			return mapper.map(result, TipoDocumentoDTO.class);
		}
		return null;
	}

	@Override
	public List<TipoDocumentoDTO> getTipoDocumentoByCodigoTramite(String trata) {
		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		TipoTramite result = tipoTramiteRepository.findByTrata(trata);
		if (result != null) {
			for (TipoTramiteTipoDoc aux : result.getTipoTramiteTipoDoc()) {
				retorno.add(mapper.map(aux.getTipoDoc(), TipoDocumentoDTO.class));
			}
		}
		return retorno;
	}

	@Override
	public DocumentoDTO saveDocument(DocumentoDTO document) {
		DocumentoDTO retorno = new DocumentoDTO();
		Documento result = documentoRepository.save(mapper.map(document, Documento.class));
		if (result != null) {
			retorno = mapper.map(result, DocumentoDTO.class);
		}
		return retorno;
	}

	@Override
	public List<DocumentoDTO> getDocumentoDiscretoByIdexpediente(Long idExpediente) {
		List<Documento> result = documentoRepository.findByUrlTemporal(idExpediente.toString());
		List<DocumentoDTO> retorno = new ArrayList<>();
		if (result != null) {
			for (Documento aux : result) {
				retorno.add(mapper.map(aux, DocumentoDTO.class));
			}
		}
		return retorno;
	}

	/**
	 * Realiza una solicitud al Servlet de PDF.
	 * 
	 * @param parameters
	 */
	private void doPdfServletRequest(Map<String, String> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			throw new ValidacionException("parameters is NULL OR EMPTY");
		}

		URL url;
		try {
			StringBuilder pdfServletUrl = new StringBuilder(
					appVucUrl.concat(PDF_SERVLET_URI).concat(getParamsString(parameters)));
			url = new URL(pdfServletUrl.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setDoInput(true);
			con.setDoOutput(false);

			int status = con.getResponseCode();
			StringBuilder content = null;
			if (status != 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
			}

			con.disconnect();

			if (content != null) {
				throw new DownloadDocumentException(content.toString());
			}

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			throw new DownloadDocumentException("Se ha producido un error inesperado al descargar el documento.");
		}
	}

	/**
	 * Retorna los parametros GET de una solicitud
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder("?");

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}
	@Override
	public DocumentoDTO getDocumentoByIdTemporalOrderByIdTransaccion(String idTemporal) {
		Documento documento = documentoRepository.findByUrlTemporalOrderByIdTransaccionDesc(idTemporal).get(0);
		
		if (documento!= null) {
			return mapper.map(documento, DocumentoDTO.class);
		}
		return null;
	}

	@Override
	public DocumentoDTO getDocumentoByIdTransaccion(Long  idTransaccion) {
		Documento documento = documentoRepository.findByIdTransaccion(idTransaccion);
		if (documento!= null) {
			return mapper.map(documento, DocumentoDTO.class);
		}
		return null;
	}
	
	@Override
	public DocumentoDTO getDocumentoByNumero(String numeroDocumento) {
		Documento documento = documentoRepository.findByNumeroDocumento(numeroDocumento);
		if (documento!= null) {
			return mapper.map(documento, DocumentoDTO.class);
		}
		return null;
	}


	@Override
	public List<TipoDocumentoDTO> getAllTipoDocumento() {
		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		 List<TipoDocumento> result = tipoDocumentoRepository.findAll();
		if (result != null) {
			for (TipoDocumento aux : result) {
				retorno.add(mapper.map(aux, TipoDocumentoDTO.class));
			}
		}
		return retorno;
	}
	
}