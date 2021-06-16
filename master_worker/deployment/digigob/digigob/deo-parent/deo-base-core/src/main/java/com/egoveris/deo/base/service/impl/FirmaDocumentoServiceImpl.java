package com.egoveris.deo.base.service.impl;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmaNoExisteException;
import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmadoException;
import com.egoveris.commons.firmadigital.itext.excepciones.SinCamposFirmaException;
import com.egoveris.commons.firmadigital.itext.hash.SignTramelecPdfv2;
import com.egoveris.commons.firmadigital.itext.hash.SignTramelecPdfv2.PrepararFirmaDTO;
import com.egoveris.commons.firmadigital.itext.hash.SignTramelecPdfv2.PrepararFirmaOutDTO;
import com.egoveris.deo.base.exception.FirmaDocumentoException;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.FirmaDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.UtilsJBPM;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.FirmaResponse;
import com.egoveris.deo.model.model.FirmaTokenRequest;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.PrepararFirmaRequest;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service
@Transactional
public class FirmaDocumentoServiceImpl implements FirmaDocumentoService {

  private static final String ERROR_STR_LOGGER = "Error: ";
  private static final String EMPTY_STRING = "";
  private static final String POR_PODER = "P/P ";
  private static final String PRE_SIGN_PDF = "_preSign.pdf";
  private static final String SHA1 = "SHA1";
  private static final String RSA = "RSA";

  private static final Logger LOGGER = LoggerFactory.getLogger(FirmaDocumentoServiceImpl.class);

  @Value("${tsa.url:}")
  private static String tsaUrl;
  @Value("${tsa.username:}")
  private static String tsaUsername;
  @Value("${tsa.password:}")
  private static String tsaPassword;
  @Value("${token.location:Ciudad Autónoma de Buenos Aires}")
  private static String location;

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  public TipoDocumentoService tipoDocumentoService;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private HistorialService historialService;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  private ReparticionHabilitadaService reparticionHabilitadaService;
  @Autowired 
  private ReparticionServ reparticionServ;
  @Autowired 
  private SectorInternoServ sectorInternoServ;
  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  protected PdfService pdfService;

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.generardocumento.service.client.external.firmardoc.
   * FirmaDocumentoService #firmaDocumentoConServ(java.lang.String,
   * java.lang.String)
   */
  @Transactional(rollbackFor = FirmaDocumentoException.class)
  public FirmaResponse firmaDocumentoConServ(String currentUser, String executionId)
      throws FirmaDocumentoException {

    try {
      // valida los parametros de entrada
      validarParametros(currentUser, executionId);

      // valida que exista la tarea de firma para el usuario
      validarExisteTareaFirma(executionId, currentUser);

      // Variables de workflow
      Map<String, Object> mapVariable = obtenerVariablesWorkflow(executionId);

      // Usuario a firmar - valida que exista
      Usuario datosUsuario = obtenerUsuario(currentUser);
      String usuarioApoderador = (String) mapVariable.get(Constantes.VAR_USUARIO_APODERADOR);
      String firmanteOriginal = firstNotBlank(usuarioApoderador, currentUser);

      // Tipo de documento
      TipoDocumentoDTO tipoDocumento = obtenerTipoDocumento(
          (String) mapVariable.get(Constantes.VAR_TIPO_DOCUMENTO));

      // Valida los permisos del usuario para firmar el documento
      validarPermisoReparticion(tipoDocumento, firmanteOriginal);

      // Nro de campo a firmar
      Integer nroCampoFirmante = obtenerNroCampoFirmante(tipoDocumento, executionId,
          firmanteOriginal);

      // fieldname
      String fieldName = PdfService.SIGNATURE_ + nroCampoFirmante;
      
      boolean importado = false;
      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
    	  importado = true;
        }
      
      // firmo el documento temporal con servidor
      
      
      
      firmaDocConServidor((String) mapVariable.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA),
          fieldName, datosUsuario, usuarioApoderador, nroCampoFirmante, importado);
      // firmo el doc de rectif - si existe
      firmaDocConServidor(
          (String) mapVariable.get(Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC), fieldName,
          datosUsuario, usuarioApoderador, null, importado);

      // avanza el workflow en jbpm y actualiza historial y firmantes
      return avanzarWorkflow(currentUser, executionId, firmanteOriginal,
          tipoDocumento.getEsFirmaConjunta(),
          (String) mapVariable.get(Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL));

    } catch (FirmaDocumentoException e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage() + " - error code: " + e.getErrorCode());
      throw e;
    } catch (Exception e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage(), e);
      throw new FirmaDocumentoException("Error al inesperado al firmar con servidor", 332, e);
    }
  }
  @Transactional(rollbackFor = FirmaDocumentoException.class)  
  @Override
  public byte[] firmaDocumentoConServExternal(RequestGenerarDocumento request) {

    try {
      // valida los parametros de entrada
//      validarParametros(currentUser, executionId);

      // valida que exista la tarea de firma para el usuario
//      validarExisteTareaFirma(executionId, currentUser);

      // Variables de workflow
//      Map<String, Object> mapVariable = obtenerVariablesWorkflow(executionId);

      // Usuario a firmar - valida que exista
      Usuario datosUsuario = obtenerUsuario(request.getUsuario());
//      String usuarioApoderador = (String) mapVariable.get(Constantes.VAR_USUARIO_APODERADOR);
//      String firmanteOriginal = firstNotBlank(usuarioApoderador, currentUser);

      // Tipo de documento
      TipoDocumentoDTO tipoDocumento = obtenerTipoDocumento(request);

      // Valida los permisos del usuario para firmar el documento
//      validarPermisoReparticion(tipoDocumento, firmanteOriginal);

      // Nro de campo a firmar
//      Integer nroCampoFirmante = obtenerNroCampoFirmante(tipoDocumento, executionId,
//      		currentUser);

      // fieldname
      String fieldName = PdfService.SIGNATURE_ + 0;
      
      boolean importado = false;
      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
    	  importado = true;
       }
      
      // firmo el documento temporal con servidor
      
      
      
//      firmaDocConServidor((String) mapVariable.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA),
//          fieldName, datosUsuario, usuarioApoderador, nroCampoFirmante, importado);
      
			return firmaDocConServidorExternal(request.getData(), fieldName, datosUsuario, request.getUsuario(), 0,
					importado, request.getCargo(), request.getReparticion(), request.getSector(),
					request.getNombreYApellido());
      
      // firmo el doc de rectif - si existe
//      firmaDocConServidor(
//          (String) mapVariable.get(Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC), fieldName,
//          datosUsuario, usuarioApoderador, null, importado);

      // avanza el workflow en jbpm y actualiza historial y firmantes
//      return avanzarWorkflow(currentUser, executionId, firmanteOriginal,
//          tipoDocumento.getEsFirmaConjunta(),
//          (String) mapVariable.get(Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL));

      
    } catch (FirmaDocumentoException e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage() + " - error code: " + e.getErrorCode());
      throw e;
    } catch (Exception e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage(), e);
      throw new FirmaDocumentoException("Error al inesperado al firmar con servidor", 332, e);
    }

  	
  }

  private TipoDocumentoDTO obtenerTipoDocumento(RequestGenerarDocumento request) {

  	TipoDocumentoDTO tipoDocumento =null;
  	try {
  		 		tipoDocumento = this.tipoDocumentoService
  				.buscarTipoDocumentoPorId(Integer.valueOf(request.getMotivo()));

  		    if (tipoDocumento == null) {
  		    	
  		      throw new FirmaDocumentoException(
  		          "El tipo de documento " + request.getMotivo() + " ya no existe en GEDO", 303);
  		    }
  		
		} catch (NumberFormatException e) {
			LOGGER.info("El motivo no tiene el acronimo por id: {}",request.getMotivo());
			String acronimoString = null;
			if(request.getTipoDocumentoGedo()!=null) {
				acronimoString = request.getTipoDocumentoGedo().getAcronimo();
			}else {				
				acronimoString = request.getMotivo();
			}
			tipoDocumento = this.tipoDocumentoService
					.buscarTipoDocumentoByAcronimo(acronimoString);
			
			if(tipoDocumento == null) {				
				throw new FirmaDocumentoException(
						"El tipo de documento " + acronimoString + " ya no existe en GEDO o no se encontro", 303);
			}
			
    }
    return tipoDocumento;
  }
  	


  
	private static void validarParametros(String currentUser, String executionId)
      throws FirmaDocumentoException {
    if (StringUtils.isEmpty(currentUser) || StringUtils.isEmpty(executionId)) {
      throw new FirmaDocumentoException(
          "Los campos currentUser, executionId no pueden ser vacios o nulos", 201);
    }
  }

  private static void validarParametros(PrepararFirmaRequest req) throws FirmaDocumentoException {
    if (StringUtils.isEmpty(req.getCurrentUser()) || StringUtils.isEmpty(req.getExecutionId())
        || req.getCerts() == null) {
      throw new FirmaDocumentoException(
          "Los campos currentUser, executionId, certs no pueden ser vacios o nulos", 201);
    }
  }

  private static void validarParametros(FirmaTokenRequest req) throws FirmaDocumentoException {
    if (StringUtils.isEmpty(req.getCurrentUser()) || StringUtils.isEmpty(req.getExecutionId())
        || StringUtils.isEmpty(req.getFieldName()) || req.getCerts() == null
        || req.getHash() == null || req.getSignedBytes() == null || req.getSignDate() == null) {
      throw new FirmaDocumentoException(
          "Los campos currentUser, executionId, signDate, certs, fieldName, hash, signedBytes no pueden ser vacios o nulos",
          201);
    }
  }

  private Usuario obtenerUsuario(String currentUser) throws FirmaDocumentoException {

    // Datos del usuario firmante
    Usuario datosUsuario;
    try {
      datosUsuario = this.usuarioService.obtenerUsuario(currentUser);

      if (datosUsuario.getCodigoReparticionOriginal() == null
          || datosUsuario.getCodigoReparticion() == null || datosUsuario.getCargo() == null
          || datosUsuario.getNombreReparticionOriginal() == null) {

        throw new FirmaDocumentoException(
            "El usuario contiene inconsistencia en sus datos " + currentUser + "]", 203);
      }
    } catch (SecurityNegocioException e) {
      throw new FirmaDocumentoException("No se encuentra el usuario " + currentUser, 202, e);
    }
    return datosUsuario;
  }

  private void validarExisteTareaFirma(String executionId, String currentUser)
      throws FirmaDocumentoException {
    if (!UtilsJBPM.existeTarea(processEngine, executionId, currentUser, Constantes.ACT_FIRMAR)) {
      throw new FirmaDocumentoException(
          "No se encuentra una tarea de firma activa para el usuario: " + currentUser, 204);
    }
  }

  private void validarPermisoReparticion(TipoDocumentoDTO tipoDocumento, String usuarioFirmante)
      throws FirmaDocumentoException {
    if (!(reparticionHabilitadaService.validarPermisoReparticion(tipoDocumento, usuarioFirmante,
        Constantes.REPARTICION_PERMISO_FIRMAR))) {
      throw new FirmaDocumentoException(
          "No puede firmar el tipo de documento " + tipoDocumento.getAcronimo()
              + " ya que no pertence a un organismo habilitado",
          205, FirmaDocumentoException.INFO);
    }
  }

  /*
   * Obtiene el documento temporal de RUDO (repositorio).
   */
  private byte[] obtenerArchivoRUDO(String nombreDelDocumento) throws FirmaDocumentoException {
    try {
      return gestionArchivosWebDavService.obtenerRecursoTemporalWebDav(nombreDelDocumento);
    } catch (Exception e) {
      throw new FirmaDocumentoException("Error al obtener el documento del repositorio", 301, e);
    }
  }

  /*
   * Sube el documento temporal a RUDO (repositorio).
   */
  private void subirArchivoRUDO(byte[] pdfFinal, String nombreDelDocumento)
      throws FirmaDocumentoException {
    try {
      gestionArchivosWebDavService.subirArchivoTemporalWebDav(nombreDelDocumento, pdfFinal);
    } catch (Exception e) {
      throw new FirmaDocumentoException("Error al subir el documento al repositorio", 302, e);
    }
  }

  /*
   * Elimina documento temporal a RUDO (repositorio).
   */
  private void eliminarArchivoRUDO(String nombreDelDocumento) {
    try {
      gestionArchivosWebDavService.borrarArchivoTemporalWebDav(nombreDelDocumento);
    } catch (Exception e) {
      LOGGER.error("Error al eliminar el archivo temporal (pre-sign) de RUDO", e);
    }
  }

  private TipoDocumentoDTO obtenerTipoDocumento(String acronimo) throws FirmaDocumentoException {
    
  	TipoDocumentoDTO tipoDocumento =null;
  	try {
  		 		tipoDocumento = this.tipoDocumentoService
  				.buscarTipoDocumentoPorId(Integer.valueOf(acronimo));
			
  		
		} catch (NumberFormatException e) {
			
			LOGGER.info("El motivo no tiene el acronimo: {}",acronimo);
			tipoDocumento = this.tipoDocumentoService
					.buscarTipoDocumentoByAcronimo(acronimo);
		}
  	
    if (tipoDocumento == null) {
    	
      throw new FirmaDocumentoException(
          "El tipo de documento " + acronimo + " ya no existe en GEDO", 303);
    }

    return tipoDocumento;
  }

  private PrepararFirmaOutDTO preFirmarDocTempRectificacion(PrepararFirmaRequest req,
      String fileName, Usuario datosUsuario, String usuarioApoderador) throws Exception {

    PrepararFirmaOutDTO result;
    if (StringUtils.isNotEmpty(fileName)) {
      result = preFirmarDocTemp(req, fileName, Constantes.SIGNATURE_RECTIFICACION, datosUsuario,
          usuarioApoderador, null);
    } else {
      result = new PrepararFirmaOutDTO();
    }
    return result;
  }

  private PrepararFirmaOutDTO preFirmarDocTemp(PrepararFirmaRequest req, String fileName,
      String fieldName, Usuario datosUsuario, String usuarioApoderador, Integer nroCampoFirmante)
      throws Exception {

    // Documento de RUDO
    byte[] documento = obtenerArchivoRUDO(fileName);

    // firma externa, el campo sello viene en el request.
    if (req.isFirmaExterna()) {
      datosUsuario.setNombreApellido(req.getNombreYApellido());
      datosUsuario.setCargo(req.getCargo());
      datosUsuario.setNombreReparticionOriginal(req.getReparticion());
    }
    // Campos de sello
    Map<String, String> mapCamposPdf = obtenerCamposSello(datosUsuario, usuarioApoderador,
        nroCampoFirmante);

    // crear certificado
    Certificate[] certs = SignTramelecPdfv2.crearCertificado(req.getCerts());

    // Genero el pdf con un empty signature
    PrepararFirmaDTO param = new PrepararFirmaDTO(documento, SHA1, fieldName, certs, location,
        mapCamposPdf);

    // Documento con campos de sello actualizados
    PrepararFirmaOutDTO out = prepararFirmaTramelec(param);

    // SUBO el archivo temporal pre firmado
    subirArchivoRUDO(out.getPdfWithEmptySignature(), preSignFile(fileName));

    return out;
  }

  private static PrepararFirmaOutDTO prepararFirmaTramelec(PrepararFirmaDTO param)
      throws Exception {
    PrepararFirmaOutDTO out;
    try {
      out = SignTramelecPdfv2.prepararFirma(param);
    } catch (SinCamposFirmaException e) {
      throw new FirmaDocumentoException("No existen campos de firma disponibles en el documento.",
          304, e);
    } catch (CampoFirmadoException e) {
      throw new FirmaDocumentoException("El campo de firma ya se encuentra firmado", 305, e);
    } catch (CampoFirmaNoExisteException e) {
      throw new FirmaDocumentoException("No existe el campo de firma en el documento", 306, e);
    }
    return out;
  }

  private void firmaDocConServidor(String fileName, String fieldName, Usuario datosUsuario,
      String usuarioApoderador, Integer nroCampoFirmante, boolean importado) throws FirmaDocumentoException {
    try {

      if (!StringUtils.isEmpty(fileName)) {
        // Documento de RUDO
        byte[] documento = obtenerArchivoRUDO(fileName);
        // Campos de sello
        Map<String, String> mapCamposPdf = obtenerCamposSello(datosUsuario, usuarioApoderador,
            nroCampoFirmante);

        // agrego campos de sello
        byte[] documentoConSello = SignTramelecPdfv2.actualizarCamposPdf(documento, mapCamposPdf);

        // firmo con certif
        RequestGenerarDocumento request = new RequestGenerarDocumento();
        String repa = datosUsuario.getCodigoReparticion()!=null ? 
        			reparticionServ.buscarNombreReparticionPorCodigo(datosUsuario.getCodigoReparticion()):" ";
        String sector = datosUsuario.getCodigoSectorInterno() != null ? 
        			sectorInternoServ.buscarNombreSectorPorCodigo(datosUsuario.getCodigoSectorInterno(), 
        														  datosUsuario.getCodigoReparticion()):" ";
        
        request.setCargo(datosUsuario.getCargo());
        request.setReparticion(repa);
        request.setSector(sector);
        request.setUsuario(datosUsuario.getNombreApellido());
        byte[] outConFirma = pdfService.firmarConCertificadoServidor(request, documentoConSello,
            fieldName, importado);

        // SUBO el archivo temporal pre firmado
        subirArchivoRUDO(outConFirma, fileName);
      }
    } catch (FirmaDocumentoException e) {
      throw e;
    } catch (Exception e) {
      throw new FirmaDocumentoException("Error firmando documento con certificado", 308, e);
    }
  }
  
  
	private byte[] firmaDocConServidorExternal(byte[] documento, String fieldName, Usuario datosUsuario,
			String usuarioApoderador, Integer nroCampoFirmante, boolean importado, String cargo, String reparticion, String sector,
			String nombreApellido) throws FirmaDocumentoException {
		try {

        // Campos de sello
        Map<String, String> mapCamposPdf = obtenerCamposSello(datosUsuario, usuarioApoderador,
            nroCampoFirmante);

        // agrego campos de sello
        byte[] documentoConSello = SignTramelecPdfv2.actualizarCamposPdf(documento, mapCamposPdf);

        // firmo con certif
        RequestGenerarDocumento request = new RequestGenerarDocumento();
        if (reparticion == null) {
        	reparticion = datosUsuario.getCodigoReparticion()!=null ? 
        			reparticionServ.buscarNombreReparticionPorCodigo(datosUsuario.getCodigoReparticion()):" ";
        }
        
			if (sector == null) {
				sector = datosUsuario.getCodigoSectorInterno() != null
						? sectorInternoServ.buscarNombreSectorPorCodigo(datosUsuario.getCodigoSectorInterno(), 
																		datosUsuario.getCodigoReparticion()): " ";
			}
    			
        request.setCargo(cargo != null ? cargo : datosUsuario.getCargo());
        request.setReparticion(reparticion);
        request.setSector(sector);
        request.setUsuario(nombreApellido != null ? nombreApellido : datosUsuario.getNombreApellido());
        return pdfService.firmarConCertificadoServidor(request, documentoConSello,
            fieldName, importado);

    } catch (FirmaDocumentoException e) {
      throw e;
    } catch (Exception e) {
      throw new FirmaDocumentoException("Error firmando documento con certificado", 308, e);
    }
  }
  
  
  @Override
  public Map<String, Object> obtenerDocumentoParaFirmarConAutoFirma(String currentUser, String executionId) {
	 
	  try {
		  
	      // valida los parametros de entrada
	      validarParametros(currentUser, executionId);

	      // valida que exista la tarea de firma para el usuario
	      validarExisteTareaFirma(executionId, currentUser);
		  
	      // Usuario a firmar - valida que exista
	      Usuario datosUsuario = obtenerUsuario(currentUser);
	      
	      // Variables de workflow
	      Map<String, Object> mapVariable = obtenerVariablesWorkflow(executionId);
	      String fileName = (String) mapVariable.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
	      
	      String usuarioApoderador = (String) mapVariable.get(Constantes.VAR_USUARIO_APODERADOR);
	      
	      // Tipo de documento
	      TipoDocumentoDTO tipoDocumento = obtenerTipoDocumento(
	          (String) mapVariable.get(Constantes.VAR_TIPO_DOCUMENTO));

	      String firmanteOriginal = firstNotBlank(usuarioApoderador, currentUser);

	      boolean importado = false;
	      Map<String, String> map = new HashMap<>();
	      
	      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
	    	  importado = true;
	        }
	      
	      
	      // Nro de campo a firmar
	      Integer nroCampoFirmante = obtenerNroCampoFirmante(tipoDocumento, executionId,
	          firmanteOriginal);

	      
	      String fieldName = PdfService.SIGNATURE_ + nroCampoFirmante;
	      
	      
		  if (!StringUtils.isEmpty(fileName)) {
			
		        // Documento de RUDO
		        byte[] documento = obtenerArchivoRUDO(fileName);
		        // Campos de sello
		        Map<String, String> mapCamposPdf = obtenerCamposSello(datosUsuario, usuarioApoderador,
		            nroCampoFirmante);

		        // agrego campos de sello
		        byte[] documentoConSello = SignTramelecPdfv2.actualizarCamposPdf(documento, mapCamposPdf);
		        
		        map.put("usuario", datosUsuario.getNombreApellido());
		        map.put("campoFirma", fieldName);
		        map.put("cargo", datosUsuario.getCargo());
		        map.put("organismo", datosUsuario.getCodigoReparticion());
		        map.put("fieldSign", fieldName);

		        return pdfService.crearDocumentoParaAutoFirma(documentoConSello, map, importado);
		        
		       
		  }else {
			  throw new FirmaDocumentoException("El parametro fileName es obligatorio", 304);
		  }
		  
	  } catch (FirmaDocumentoException e) {
	      throw e;
	    } catch (Exception e) {
	      throw new FirmaDocumentoException("Error al obtener el documento para firmar con autofirma", 308, e);
	    }
	  
  }

  private static String preSignFile(String string) {
    return string.substring(0, string.indexOf('.')) + PRE_SIGN_PDF;
  }

  private FirmaResponse avanzarWorkflow(String currentUser, String executionId,
      String firmanteOriginal, boolean esFirmaConj, String docOriginalRect) throws Exception {

    // Actualiza historial
    historialService.actualizarHistorial(executionId);
    // si es firma conj actualiza firmantes
    if (esFirmaConj) {
      firmaConjuntaService.actualizarFirmante(firmanteOriginal, true, executionId, currentUser);
      List<FirmanteDTO> firmantes = firmaConjuntaService.buscarFirmantesPorTarea(executionId);
      for (FirmanteDTO firm : firmantes) {
    	  if (!firm.getEstadoFirma()) {
	    	  if (firm.getUsuarioRevisor() != null && !firm.getUsuarioRevisor().isEmpty()) {
	    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.REVISAR_FIRMA,
	    			        false);
	    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA,
	  			        true);
	    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.REVISAR_DOCUMENTO_CON_CERTIFICADO,
	    			        false);
	    	  } else {
	    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.REVISAR_FIRMA,
	  			        false);
	    	  }
    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.VAR_USUARIO_FIRMANTE,
	  			        firm.getUsuarioFirmante());
    		  this.processEngine.getExecutionService().setVariable(executionId, Constantes.VAR_USUARIO_REVISOR,
  			        firm.getUsuarioRevisor());
	    	  break;
    	  }
      }
    }
    processEngine.getExecutionService().setVariable(executionId, Constantes.VAR_USUARIO_DERIVADOR,
        firmanteOriginal);
    ProcessInstance pInstance = processEngine.getExecutionService()
        .signalExecutionById(executionId, Constantes.TRANSICION_FIRMA_PENDIENTE);

    return estadoWorkflow(pInstance, docOriginalRect);
  }

  private Map<String, Object> obtenerVariablesWorkflow(String executionId) {
    Set<String> setVar = new HashSet<>();
    setVar.add(Constantes.VAR_USUARIO_APODERADOR);
    setVar.add(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    setVar.add(Constantes.VAR_TIPO_DOCUMENTO);
    setVar.add(Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL);
    setVar.add(Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC);

    // validar los obligatorios
    return UtilsJBPM.getVariablesWorkFlow(processEngine, executionId, setVar);
  }

  private static Map<String, String> obtenerCamposSello(Usuario datosUsuario,
      String usuarioApoderador, Integer nroCampoFirmante) {

    String usuarioApNomb = StringUtils.EMPTY;
    if (StringUtils.isNotEmpty(usuarioApoderador)) {
      usuarioApNomb += POR_PODER;
    }
    usuarioApNomb += datosUsuario.getNombreApellido();

    String cargo;
    if (StringUtils.isEmpty(datosUsuario.getCargo())) {
      cargo = datosUsuario.getOcupacion();
    } else {
      cargo = datosUsuario.getCargo();
    }

    String nro = nroCampoFirmante != null ? nroCampoFirmante.toString() : EMPTY_STRING;

    Map<String, String> camposSello = new HashMap<>();
    camposSello.put(PdfService.USUARIO_.concat(nro), usuarioApNomb);
    camposSello.put(PdfService.CARGO_.concat(nro), cargo.replace('\n', ' '));
    camposSello.put(PdfService.REPARTICION_.concat(nro),
        datosUsuario.getNombreReparticionOriginal());
    camposSello.put(PdfService.SECTOR_.concat(nro),
            datosUsuario.getCodigoSectorInternoOriginal());

    return camposSello;
  }

  private Integer obtenerNroCampoFirmante(TipoDocumentoDTO tipoDocumento, String executionId,
      String usuarioFirmante) {
    Integer nroCampo;
    if (tipoDocumento.getEsFirmaExternaConEncabezado()) {
      nroCampo = 1;
    } else if (tipoDocumento.getEsFirmaConjunta()) {
      nroCampo = firmaConjuntaService.nroFirmaFirmante(usuarioFirmante, executionId, false);
    } else {
      nroCampo = 0;
    }
    return nroCampo;
  }

  private FirmaResponse estadoWorkflow(ProcessInstance pInstance, String rect)
      throws FirmaDocumentoException {
    FirmaResponse fResponse = new FirmaResponse();
    if (pInstance.isEnded()) {
      DocumentoDTO documento = this.buscarDocumentosGedoService
          .buscarDocumentoPorProceso(pInstance.getId());
      if (documento != null) {
        fResponse.setNroSade(documento.getNumero());
        if (!StringUtils.isEmpty(rect)) {
          fResponse.setNroSadeRectif(rect);
        }
      } else {
        throw new FirmaDocumentoException(
            "Error al obtener el estado final del workflow - No encuentra el documento", 309);
      }
    } else {
      Set<String> activities = pInstance.findActiveActivityNames();
      if (activities.size() == 1) {
        fResponse.setEstado(activities.iterator().next());
      } else {
        throw new FirmaDocumentoException("Error al obtener el estado final del workflow", 309);
      }
    }
    return fResponse;
  }

  private static String firstNotBlank(String first, String second) {
    if (StringUtils.isNotBlank(first)) {
      return first;
    } else {
      return second;
    }
  }

  @Override
  public FirmaResponse firmFaDocumentoConServ(String currentUser, String executionId)
      throws FirmaDocumentoException {
    try {
      // valida los parametros de entrada
      validarParametros(currentUser, executionId);

      // valida que exista la tarea de firma para el usuario
      validarExisteTareaFirma(executionId, currentUser);

      // Variables de workflow
      Map<String, Object> mapVariable = obtenerVariablesWorkflow(executionId);

      // Usuario a firmar - valida que exista
      Usuario datosUsuario = obtenerUsuario(currentUser);
      String usuarioApoderador = (String) mapVariable.get(Constantes.VAR_USUARIO_APODERADOR);
      String firmanteOriginal = firstNotBlank(usuarioApoderador, currentUser);

      // Tipo de documento
      TipoDocumentoDTO tipoDocumento = obtenerTipoDocumento(
          (String) mapVariable.get(Constantes.VAR_TIPO_DOCUMENTO));

      // Valida los permisos del usuario para firmar el documento
      validarPermisoReparticion(tipoDocumento, firmanteOriginal);

      // Nro de campo a firmar
      Integer nroCampoFirmante = obtenerNroCampoFirmante(tipoDocumento, executionId,
          firmanteOriginal);

      // fieldname
      String fieldName = PdfService.SIGNATURE_ + nroCampoFirmante;

      boolean importado = false;
      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
    	  importado = true;
        }
      
      // firmo el documento temporal con servidor
      firmaDocConServidor((String) mapVariable.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA),
          fieldName, datosUsuario, usuarioApoderador, nroCampoFirmante, importado);
      // firmo el doc de rectif - si existe
      firmaDocConServidor(
          (String) mapVariable.get(Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC), fieldName,
          datosUsuario, usuarioApoderador, null, importado);

      // avanza el workflow en jbpm y actualiza historial y firmantes
      return avanzarWorkflow(currentUser, executionId, firmanteOriginal,
          tipoDocumento.getEsFirmaConjunta(),
          (String) mapVariable.get(Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL));

    } catch (FirmaDocumentoException e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage() + " - error code: " + e.getErrorCode());
      throw e;
    } catch (Exception e) {
      LOGGER.error(ERROR_STR_LOGGER + e.getMessage(), e);
      throw new FirmaDocumentoException("Error al inesperado al firmar con servidor", 332, e);
    }
  }
  
  @Override
  public FirmaResponse documentoFirmadoConAutoFirma(byte[] dataAutoFirma, String currentUser, String executionId) {

	  
	 try {
		  	  
	      // valida los parametros de entrada
	      validarParametros(currentUser, executionId);
	
	      // valida que exista la tarea de firma para el usuario
	      validarExisteTareaFirma(executionId, currentUser);
	
	      
	      // Variables de workflow
	      Map<String, Object> mapVariable = obtenerVariablesWorkflow(executionId);
	

	      String usuarioApoderador = (String) mapVariable.get(Constantes.VAR_USUARIO_APODERADOR);
	      String firmanteOriginal = firstNotBlank(usuarioApoderador, currentUser);
	
	      // Tipo de documento
	      TipoDocumentoDTO tipoDocumento = obtenerTipoDocumento(
	          (String) mapVariable.get(Constantes.VAR_TIPO_DOCUMENTO));
	
	        // SUBO el archivo temporal pre firmado con autoFirma
	        subirArchivoRUDO(dataAutoFirma, (String) mapVariable.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA));
	      
	      
	      // avanza el workflow en jbpm y actualiza historial y firmantes
	      return avanzarWorkflow(currentUser, executionId, firmanteOriginal,
	          tipoDocumento.getEsFirmaConjunta(),
	          (String) mapVariable.get(Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL));

	  } catch (FirmaDocumentoException e) {
	      LOGGER.error(ERROR_STR_LOGGER + e.getMessage() + " - error code: " + e.getErrorCode());
	      throw e;
	    } catch (Exception e) {
	      LOGGER.error(ERROR_STR_LOGGER + e.getMessage(), e);
	      throw new FirmaDocumentoException("Error al inesperado al firmar con autofirma", 332, e);
	    }
  }

  
}
