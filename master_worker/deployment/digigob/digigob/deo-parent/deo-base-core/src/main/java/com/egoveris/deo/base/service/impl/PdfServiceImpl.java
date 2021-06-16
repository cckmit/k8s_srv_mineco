
package com.egoveris.deo.base.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;
import org.zkoss.util.resource.Labels;

import com.egoveris.deo.base.exception.ModificacionPDFException;
import com.egoveris.deo.base.exception.PDFConversionException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.base.service.ConversorPdf;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.TemplateService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.tica.base.model.ResponseDocumento;
import com.egoveris.tica.base.service.ExternalTicaPdfService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service 
public class PdfServiceImpl implements PdfService {

  private static final String DOCUMENTO_SOLO_LOGO = "DocumentoSoloLogo";
  private static final String DOCUMENTO_BASE_TEMPLATE_SIN_CONTENIDO = "DocumentoBaseTemplateSinContenido";
  private static final String DOCUMENTO_BASE_TEMPLATE = "DocumentoBaseTemplate";
  private static final String DOCUMENTO_IMPORTAR_TEMPLATE = "DocumentoImportarTemplate";
  private static final String DOCUMENTO_BASE_TEMPLATE_NOTA = "DocumentoBaseTemplateNota";
  private static final String DOCUMENTO_BASE_TEMPLATE_MEMO = "DocumentoBaseTemplateMemo";
  private static final String DOCUMENTO_IMPORTAR_TEMPLATE_MEMO = "DocumentoImportarTemplateMemo";
  private static final String DOCUMENTO_IMPORTAR_TEMPLATE_NOTA = "DocumentoImportarTemplateNota";
  
  
  
  /* configuraciones traidas de ticaSign*/
  private static final String FECHA_ASIGNAR = "<Fecha a asignar>";
  private static final String NUMERO_DOCUMENTO = "numero_documento";
  private static final String NUMERO_ASIGNAR = "<Número a asignar>";
  private static final String FECHA = "fecha";
  private static final String LOCALIDAD = "localidad";

  private static final String FIRMADO_DIGITAL_DEL_DOCUMENTO = "Firmado digital del documento";
  private static final Integer COORD_X1_NOMB_FIRMA_CERTIF = 30;
  private static final Integer COORD_Y1_NOMB_FIRMA_CERTIF = 5;
  private static final Integer COORD_X2_NOMB_FIRMA_CERTIF = 200;
  private static final Integer COORD_Y2_NOMB_FIRMA_CERTIF = 15;
  private static final Integer MARGEN_ADICIONAL_DERECHA = 120;
  private static final Integer MARGEN_ADICIONAL_IZQUIERDA = 200;  
 

  private static transient Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

  /**
   * Constantes para creacion de campos.
   */
  private final static Integer ESPACIO_SELLOS = Integer.valueOf(20);
 
//  private final static Integer ALTO_SELLO = Integer.valueOf(60);
 
  private final static Integer ALTO_SELLO = Integer.valueOf(10);

//  private final static Integer ALTO_CAMPO_FIRMA = Integer.valueOf(20);
  private final static Integer ALTO_CAMPO_FIRMA = Integer.valueOf(60);

//  private final static Integer ALTO_CAMPO_SELLO = Integer.valueOf(10);
  
  private final static Integer ALTO_CAMPO_SELLO = Integer.valueOf(10);
  
  private final static Integer ANCHO_SELLO = Integer.valueOf(250);
  private final static Integer ESPACION_ENTRE_LINEAS = Integer.valueOf(20);
  private final static Integer ALTO_FOOTER = Integer.valueOf(50);
  private final static Integer X1_FIRMA_CIERRE = Integer.valueOf(450);
  private final static Integer Y1_FIRMA_CIERRE = Integer.valueOf(50);
  private final static Integer ANCHO_FIRMA_CIERRE = Integer.valueOf(133);
  private final static Integer ALTO_FIRMA_CIERRE = Integer.valueOf(40);
  private final static String PAGINA_ = "Pagina_";
  private final static String NUMERO_ = "Numero_";
  private final static String MEMO_ = "ME";
  private final static String ALIAS_CERTIFICADO = "egoveris";

  private static final String NOMBRE_GENERICO_ARCHIVO_PDF = "previsualizacion.pdf";
  

  @Value("${gedo.leyendaConmemorativa}")
  private String leyendaConmemorativa;

  @Value("${gedo.localidad}")
  private String localidad;
  
  @Autowired
  private ConversorPdf conversor2Pdf;

  @Autowired
  private TipoDocumentoService tipoDocumentoService;

  @Value("${app.name:SADE}")
  private String modulo_origen;

  private Configuration configFreeMarker;
  @Autowired
  public IUsuarioService usuarioService;

  @Autowired
  private ComunicacionService comunicacionService;

  // tica
  @Autowired
  ExternalTicaPdfService externalTicaPdf;

  @Autowired
  ExternalFormularioService formularioService;
  
  @Autowired
  private TemplateService templateService;
  
  /**
   * Se llama luego de inicializar el bean. "init-method"
   */
  @PostConstruct
  public void initPdfService() {
    if (logger.isDebugEnabled()) {
      logger.debug("initPdfService() - start"); //$NON-NLS-1$
    }

    inicializarTemplates();

    if (logger.isDebugEnabled()) {
      logger.debug("initPdfService() - end"); //$NON-NLS-1$
    }
  }

  public FormularioDTO obtenerFormularioControlado(String nombreForm) {
		return formularioService.buscarFormularioPorNombre(nombreForm);
  }

  
  
  /*
  * 
  */
//  private void inicializarTemplates() {
//    if (logger.isDebugEnabled()) {
//      logger.debug("inicializarTemplates() - start"); //$NON-NLS-1$
//    }
//
//    configFreeMarker = new Configuration();
//    StringTemplateLoader stringLoader = new StringTemplateLoader();
//
//    StringBuffer cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoBaseTemplate.html");
//
//    StringBuffer cadenaLogoImportar = new StringBuffer();
//    cadenaLogoImportar.append("/templates/html");
//    cadenaLogoImportar.append("/DocumentoImportarTemplate.html");
//    InputStream template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoImportarTemplate.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoImportarTemplateNota.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_NOTA, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoImportarTemplateMemo.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_MEMO, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoBaseTemplateNota.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_NOTA, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoBaseTemplateMemo.html");
//
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_MEMO, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoSoloLogo.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_SOLO_LOGO, transformarInToString(template));
//
//    cadenaLogo = new StringBuffer();
//    cadenaLogo.append("/templates/html");
//    cadenaLogo.append("/DocumentoBaseTemplateSinContenido.html");
//    template = PdfServiceImpl.class.getResourceAsStream(cadenaLogo.toString());
//    stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_SIN_CONTENIDO,
//        transformarInToString(template));
//
//    configFreeMarker.setTemplateLoader(stringLoader);
//
//    if (logger.isDebugEnabled()) {
//      logger.debug("inicializarTemplates() - end"); //$NON-NLS-1$
//    }
//  }

	private void inicializarTemplates() {
		if (logger.isDebugEnabled()) {
			logger.debug("inicializarTemplates() - start"); //$NON-NLS-1$
		}

		configFreeMarker = new Configuration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();

		stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE, this.templateService.getByCodigo(DOCUMENTO_BASE_TEMPLATE));
		stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE, this.templateService.getByCodigo(DOCUMENTO_IMPORTAR_TEMPLATE));
		stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_NOTA, this.templateService.getByCodigo(DOCUMENTO_IMPORTAR_TEMPLATE_NOTA));
		stringLoader.putTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_MEMO, this.templateService.getByCodigo(DOCUMENTO_IMPORTAR_TEMPLATE_MEMO));
		stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_NOTA, this.templateService.getByCodigo(DOCUMENTO_BASE_TEMPLATE_NOTA));
		stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_MEMO, this.templateService.getByCodigo(DOCUMENTO_BASE_TEMPLATE_MEMO));
		stringLoader.putTemplate(DOCUMENTO_SOLO_LOGO, this.templateService.getByCodigo(DOCUMENTO_SOLO_LOGO));
		stringLoader.putTemplate(DOCUMENTO_BASE_TEMPLATE_SIN_CONTENIDO, this.templateService.getByCodigo(DOCUMENTO_BASE_TEMPLATE_SIN_CONTENIDO));

		configFreeMarker.setTemplateLoader(stringLoader);

		if (logger.isDebugEnabled()) {
			logger.debug("inicializarTemplates() - end"); //$NON-NLS-1$
		}
	}

  private byte[] agregarWatermarkPDF(byte[] contenido) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarWatermarkPDF(byte[]) - start"); //$NON-NLS-1$
    }

    ByteArrayOutputStream contenidoWatermark = new ByteArrayOutputStream();
    byte[] contenidoPrevisualizacion = null;
    PdfReader reader = null;
    PdfStamper stamp = null;
    try {
      reader = new PdfReader(contenido);
      stamp = new PdfStamper(reader, contenidoWatermark, '\0', true);
      PdfContentByte over;
      Image img = Image.getInstance(PdfService.class.getResource("/images/wm_gedo_preview.jpg"));
      // Alto y ancho de la STAMPA
      float altoStampa = img.getPlainHeight();// 1044
      float anchoStampa = img.getPlainWidth();// 699
      for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        Rectangle rectangle = reader.getBoxSize(i, "media");
        // Alto y ancho de la IMAGEN en importar o incorporar
        float altoImagen = rectangle.getHeight();// 768
        float anchoImagen = rectangle.getWidth();// 1366
        // Calculo del porcetanje a escalar
        float porcentajeAEscalar = porcetajeAEscalar(altoImagen, anchoImagen, altoStampa,
            anchoStampa);
        img.scalePercent(porcentajeAEscalar);
        setearPosicionStampa(img, altoImagen, anchoImagen);
        over = stamp.getOverContent(i);
        over.addImage(img);
      }
    } catch (Exception de) {
      logger.error("Error al agregar marca de agua", de);
      throw new Exception(de.getMessage(), de);
    } finally {
      if (stamp != null) {
        stamp.close(); // el stamper hace el cierre del pdfreader
      }
      contenidoPrevisualizacion = contenidoWatermark.toByteArray();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("agregarWatermarkPDF(byte[]) - end"); //$NON-NLS-1$
    }
    return contenidoPrevisualizacion;
  }

  private void setearPosicionStampa(Image img, float altoImagen, float anchoImagen) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearPosicionStampa(Image, float, float) - start"); //$NON-NLS-1$
    }

    float posicionX = (anchoImagen / 2);
    float posicionY = (altoImagen / 2);
    float anchoStampaEscalada = img.getScaledWidth();
    float altoStampaEscalada = img.getScaledHeight();
    posicionX = posicionX - (anchoStampaEscalada / 2);
    posicionY = posicionY - (altoStampaEscalada / 2);
    img.setAbsolutePosition(posicionX, posicionY);

    if (logger.isDebugEnabled()) {
      logger.debug("setearPosicionStampa(Image, float, float) - end"); //$NON-NLS-1$
    }
  }

  private float porcetajeAEscalar(float altoImagen, float anchoImagen, float altoStampa,
      float anchoStampa) {
    if (logger.isDebugEnabled()) {
      logger.debug("porcetajeAEscalar(float, float, float, float) - start"); //$NON-NLS-1$
    }

    float porcentajeAEscalar = 0f;
    float difAlto = Math.abs(altoImagen - altoStampa);
    float difAncho = Math.abs(anchoImagen - anchoStampa);
    if (altoImagen > altoStampa && anchoImagen < anchoStampa) {
      porcentajeAEscalar = calculoPorcentaje(anchoImagen, anchoStampa);
    }
    if (altoImagen < altoStampa && anchoImagen > anchoStampa) {
      porcentajeAEscalar = calculoPorcentaje(altoImagen, altoStampa);
    }

    if (esImagenMasGrande(altoImagen, anchoImagen, altoStampa, anchoStampa)) {
      if (difAncho < difAlto) {
        porcentajeAEscalar = calculoPorcentaje(anchoImagen, anchoStampa);
      } else {
        porcentajeAEscalar = calculoPorcentaje(altoImagen, altoStampa);
      }
    } else if (esEstampaMasGrande(altoImagen, anchoImagen, altoStampa, anchoStampa)) {
      if (difAncho < difAlto) {
        porcentajeAEscalar = calculoPorcentaje(altoImagen, altoStampa);
      } else {
        porcentajeAEscalar = calculoPorcentaje(anchoImagen, anchoImagen);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("porcetajeAEscalar(float, float, float, float) - end"); //$NON-NLS-1$
    }
    return porcentajeAEscalar;
  }

  private float calculoPorcentaje(float valorNuevo, float valorAnterior) {
    if (logger.isDebugEnabled()) {
      logger.debug("calculoPorcentaje(float, float) - start"); //$NON-NLS-1$
    }

    float porcentajeAEscalar;
    porcentajeAEscalar = (valorNuevo * 100f) / valorAnterior;

    if (logger.isDebugEnabled()) {
      logger.debug("calculoPorcentaje(float, float) - end"); //$NON-NLS-1$
    }
    return porcentajeAEscalar;
  }

  private boolean esImagenMasGrande(float altoImagen, float anchoImagen, float altoStampa,
      float anchoStampa) {
    if (logger.isDebugEnabled()) {
      logger.debug("esImagenMasGrande(float, float, float, float) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = (altoImagen > altoStampa && anchoImagen > anchoStampa);
    if (logger.isDebugEnabled()) {
      logger.debug("esImagenMasGrande(float, float, float, float) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  private boolean esEstampaMasGrande(float altoImagen, float anchoImagen, float altoStampa,
      float anchoStampa) {
    if (logger.isDebugEnabled()) {
      logger.debug("esEstampaMasGrande(float, float, float, float) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = (altoImagen < altoStampa && anchoImagen < anchoStampa);
    if (logger.isDebugEnabled()) {
      logger.debug("esEstampaMasGrande(float, float, float, float) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  @Override
  public byte[] generarPDF(String contenido, String motivo, TipoDocumentoDTO tipoDocumento)
      throws ApplicationException, IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPDF(String, String, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    Template template = this.getTemplate(tipoDocumento);
    template.setEncoding("UTF-8");
    String nombreDoc;
    if (tipoDocumento.getNombre() != null) {
      nombreDoc = tipoDocumento.getNombre();
    } else {
      nombreDoc = "";
    }
    Map<String, String> propiedadesTemplate = this.generarMapPropTempl(motivo, nombreDoc,
        leyendaConmemorativa, contenido);
    byte[] docHtml;
    byte[] returnbyteArray = new byte[0];
    try {
      docHtml = this.generarTemplate(template, propiedadesTemplate);
      returnbyteArray = this.conversor2Pdf.convertirHTMLaPDF(docHtml);
    } catch (TemplateException e) {
      e.printStackTrace();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarPDF(String, String, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  @Override
  public byte[] generateEncabezado(String contenido, String motivo, TipoDocumentoDTO tipoDocumento,
      byte[] cont) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("generateEncabezado(String, String, TipoDocumentoDTO, byte[]) - start"); //$NON-NLS-1$
    }

    Template template;
    byte[] returnbyteArray = new byte[0];
    try {
      template = configFreeMarker.getTemplate(DOCUMENTO_BASE_TEMPLATE_SIN_CONTENIDO);
      template.setEncoding("UTF-8");
      String nombreDoc;
      if (tipoDocumento.getNombre() != null) {
        nombreDoc = tipoDocumento.getNombre();
      } else {
        nombreDoc = "";
      }
      Map<String, String> propiedadesTemplate = new HashMap<String, String>();
      propiedadesTemplate.put("motivo", motivo);
      propiedadesTemplate.put("leyenda", leyendaConmemorativa);
      propiedadesTemplate.put("tipoDocumento", nombreDoc);
      propiedadesTemplate.put("localidad", localidad);
      // Map<String, String> propiedadesTemplate =
      // this.generarMapPropTempl(motivo, nombreDoc, leyendaConmemorativa,
      // contenido);
      byte[] docHtml;

      try {
        docHtml = this.generarTemplate(template, propiedadesTemplate);
        returnbyteArray = this.conversor2Pdf.convertirHTMLaPDF(docHtml);
      } catch (IOException | TemplateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      if (logger.isDebugEnabled()) {
        logger.debug("generateEncabezado(String, String, TipoDocumentoDTO, byte[]) - end"); //$NON-NLS-1$
      }
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return returnbyteArray;
  }

  @Override
  public byte[] generateLogo() throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("generateLogo() - start"); //$NON-NLS-1$
    }
    byte[] returnbyteArray = new byte[0];
    Template template;
    try {
      template = configFreeMarker.getTemplate(DOCUMENTO_SOLO_LOGO);
      template.setEncoding("UTF-8");
      Map<String, String> propiedadesTemplate = this
          .generarMapPropTempLeyendaConm(leyendaConmemorativa);
      byte[] docHtml = this.generarTemplate(template, propiedadesTemplate);
      returnbyteArray = conversor2Pdf.convertirHTMLaPDF(docHtml);
    } catch (IOException e) {

    } catch (TemplateException e) {

    }
    if (logger.isDebugEnabled()) {
      logger.debug("generateLogo() - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  @Override
  public byte[] generarPDFCCOO(String contenido, String motivo, TipoDocumentoDTO tipoDocumento,
      RequestGenerarDocumento request) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPDFCCOO(String, String, TipoDocumentoDTO, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    Template template;
    byte[] returnbyteArray = new byte[0];
    try {
      template = this.getTemplate(tipoDocumento);
      template.setEncoding("UTF-8");
      Map<String, String> propiedadesTemplate = this.generarMapPropTemplCO(request, tipoDocumento,
          leyendaConmemorativa, contenido);
      byte[] docHtml = this.generarTemplate(template, propiedadesTemplate);

      returnbyteArray = this.conversor2Pdf.convertirHTMLaPDF(docHtml);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarPDFCCOO(String, String, TipoDocumentoDTO, RequestGenerarDocumento) - end"); //$NON-NLS-1$
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SecurityNegocioException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
    return returnbyteArray;
  }

  private Map<String, String> generarMapPropTempLeyendaConm(String leyendaConmemorativa) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarMapPropTempLeyendaConm(String) - start"); //$NON-NLS-1$
    }

    Map<String, String> data = new HashMap<String, String>();
    data.put("leyenda", leyendaConmemorativa);

    if (logger.isDebugEnabled()) {
      logger.debug("generarMapPropTempLeyendaConm(String) - end"); //$NON-NLS-1$
    }
    return data;
  }

  private Map<String, String> generarMapPropTemplCO(RequestGenerarDocumento request,
      TipoDocumentoDTO tipoDocumento, String leyendaConmemorativa, String cont)
      throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarMapPropTemplCO(RequestGenerarDocumento, TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    Map<String, String> data = new HashMap<String, String>();
    data.put("motivo", request.getMotivo());
    data.put("leyenda", leyendaConmemorativa);
    data.put("contenidoHtml", cont);
    data.put("localidad", localidad);
    if (tipoDocumento.getCodigoTipoDocumentoSade().equals(MEMO_)) {
      data.put("reparticion", request.getCodigoReparticion());
    }

    if (request.getIdComunicacionRespondida() != null
        && request.getIdComunicacionRespondida() != 0) {
      data.put("responde",
          "En respuesta a: " + this.comunicacionService
              .buscarComunicacionPorId(request.getIdComunicacionRespondida()).getDocumento()
              .getNumero());
    } else {
      data.put("responde", " ");
    }
    String destinatarios = cargarUsuariosDestinatarios(request.getListaUsuariosDestinatarios());

    for (UsuarioExternoDTO externo : request.getListaUsuariosDestinatariosExternos()) {
      destinatarios = destinatarios
          .concat(externo.getNombre() + " (" + externo.getDestino() + "), ");
    }
    data.put("destinatarios", destinatarios);
    data.put("destinatariosCopia",
        cargarUsuariosDestinatarios(request.getListaUsuariosDestinatariosCopia()));

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarMapPropTemplCO(RequestGenerarDocumento, TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return data;
  }

  private String cargarUsuariosDestinatarios(List<String> listaUsuariosDestinatarios)
      throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarUsuariosDestinatarios(List<String>) - start"); //$NON-NLS-1$
    }

    String usuariosDestinarios = "  ";
    for (String usuarioDestino : listaUsuariosDestinatarios) {
      Usuario usuario = this.usuarioService.obtenerUsuario(usuarioDestino);
      usuariosDestinarios = usuariosDestinarios.concat(
          usuario.getNombreApellido() + " " + "(" + usuario.getCodigoReparticion() + ")" + ", ");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarUsuariosDestinatarios(List<String>) - end"); //$NON-NLS-1$
    }
    return usuariosDestinarios;
  }

  private Map<String, String> generarMapPropTempl(String motivo, String nombreTipoDocumento,
      String leyendaConmemorativa, String cont) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarMapPropTempl(String, String, String, String) - start"); //$NON-NLS-1$
    }

    Map<String, String> data = new HashMap<String, String>();
    data.put("motivo", motivo);
    data.put("leyenda", leyendaConmemorativa);
    data.put("tipoDocumento", nombreTipoDocumento);
    data.put("contenidoHtml", cont);
    data.put("localidad", localidad);
    if (logger.isDebugEnabled()) {
      logger.debug("generarMapPropTempl(String, String, String, String) - end"); //$NON-NLS-1$
    }
    return data;
  }

  private String transformarInToString(InputStream co) {
    if (logger.isDebugEnabled()) {
      logger.debug("transformarInToString(InputStream) - start"); //$NON-NLS-1$
    }

    StringBuilder sb = new StringBuilder();
    String line;

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(co, "UTF-8"));
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (UnsupportedEncodingException uee) {
      logger.error("Encoding no soportado", uee);
    } catch (IOException ioe) {
      logger.error("No se pudo cerrar el archivo", ioe);
    } finally {
      try {
        co.close();
      } catch (IOException ioe) {
        logger.error("No se pudo cerrar el archivo", ioe);
      }
    }

    String returnString = sb.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("transformarInToString(InputStream) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Procesa el template FreeMarket. Reemplazando en la "template" los campos
   * preseteados en "propiedadesTemplate"
   * 
   * @param template
   * @param porpiedadesTemplate
   *          mapa con los nombres
   * @return byte[]
   * @throws IOException
   * @throws TemplateException
   */
  private byte[] generarTemplate(Template template, Map<String, String> propiedadesTemplate)
      throws ApplicationException, IOException, TemplateException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTemplate(Template, Map<String,String>) - start"); //$NON-NLS-1$
    }

    StringWriter processedTemplateWriter = new StringWriter();
    try {
      template.process(propiedadesTemplate, processedTemplateWriter);
      byte[] returnbyteArray = String.valueOf(processedTemplateWriter.getBuffer()).getBytes();
      if (logger.isDebugEnabled()) {
        logger.debug("generarTemplate(Template, Map<String,String>) - end"); //$NON-NLS-1$
      }
      return returnbyteArray;
    } catch (ApplicationException e) {
      logger.error("Error al generar el template", e);
      throw e;
    } finally {
      try {
        processedTemplateWriter.close();
      } catch (IOException ioe) {
        logger.debug(ioe.toString());
      }
    }
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List<String> transformPDFToPNG(final byte[] contenidoPdf) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("transformPDFToPNG(byte[]) - start"); //$NON-NLS-1$
    }

    List<String> pageFiles = new ArrayList<String>();

    if (logger.isDebugEnabled()) {
      logger.debug("transformPDFToPNG(byte[]) - end"); //$NON-NLS-1$
    }
    return pageFiles;
  }

  /*
   * Tiene por objetivo validar ciertos tipos de archivo cuya conversi�n a PDF,
   * o a imagen PNG,no es soportada por LiveCycle.
   */
  public boolean validacionTipoArchivo(String nombreArchivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("validacionTipoArchivo(String) - start"); //$NON-NLS-1$
    }

    String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase();
    if (StringUtils.isNotEmpty(extension)
        && Constantes.extensionesPermitidas.contains(extension)) {
      if (logger.isDebugEnabled()) {
        logger.debug("validacionTipoArchivo(String) - end"); //$NON-NLS-1$
      }
      return true;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("validacionTipoArchivo(String) - end"); //$NON-NLS-1$
      }
      return false;
    }
  }

  /**
   * Permite crear un campo de datos, en el documento pdf.
   * 
   * @param pdfStamper
   * @param ubicacion
   *          : Ubicación inicial del campo.
   * @param nombreCampo
   *          : Nombre del campo.
   * @param baseFont
   *          : Definición de la fuente
   * @return El objeto de tipo TextField creado.
   * @throws Exception
   */
  private TextField crearCampoSello(PdfStamper pdfStamper, float[] ubicacion, String nombreCampo,
      BaseFont baseFont, boolean encabezado) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoSello(PdfStamper, float[], String, BaseFont, boolean) - start"); //$NON-NLS-1$
    }

    float x1 = ubicacion[1];
    float y1 = ubicacion[2];
    int numeroPagina = (int) ubicacion[0];
    Rectangle rectangle = new Rectangle(x1, y1, x1 + ANCHO_SELLO, y1 - ALTO_CAMPO_SELLO);
    TextField textSello;
    textSello = new TextField(pdfStamper.getWriter(), rectangle, nombreCampo);
    textSello.setFont(baseFont);
    textSello.setFontSize(8);
    if (encabezado == false)
      pdfStamper.addAnnotation(textSello.getTextField(), numeroPagina);

    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoSello(PdfStamper, float[], String, BaseFont, boolean) - end"); //$NON-NLS-1$
    }
    return textSello;
  }

  /**
   * Crea el campo de firma, y los campos correspondientes al sello, en el
   * documento pdf.
   * 
   * @param pdfStamper
   * @param ubicacion
   *          : Posición inicial del campo de firma
   * @param numeroFirma
   *          : Número que identifica los campos a crear para la firma actual.
   * @return Las coordenadas x,y del último campo, derecha-abajo.
   * @throws Exception
   */
  private float[] crearCamposSello(PdfStamper pdfStamper, float[] ubicacion, int numeroFirma,
      int cantFirmas, boolean encabezado) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("crearCamposSello(PdfStamper, float[], int, int, boolean) - start"); //$NON-NLS-1$
    }

    BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI,
        BaseFont.NOT_EMBEDDED);
    float[] nuevasCoordenadas = ubicacion.clone();
    if(logger.isDebugEnabled()) {
    	logger.debug("ubicacion de x1 y y1: "  + ubicacion[1] + " " +ubicacion[2]);    	
    }
    float[] coordenadasFirma = { ubicacion[0], ubicacion[1], ubicacion[2],
        ubicacion[1] + ANCHO_SELLO, ubicacion[2] - ALTO_CAMPO_FIRMA };
    // Creación campos de firma funcionario
    crearCampoFirma(pdfStamper, coordenadasFirma, SIGNATURE_ + numeroFirma);
    // Creación de los campos de sello
    // Actualización de coordenada y1, para la adición de los campos
    // verticalmente.
    ubicacion[2] = ubicacion[2] - ALTO_CAMPO_FIRMA - 10;
    if (encabezado == true && cantFirmas - numeroFirma == 2) {
      encabezado = true;
    } else {
      encabezado = false;
    }
		TextField textSello;
    
		textSello = crearCampoSello(pdfStamper, ubicacion, USUARIO_ + numeroFirma, baseFont, encabezado);
		ubicacion[2] = textSello.getBox().getTop() - 10;
		textSello = crearCampoSello(pdfStamper, ubicacion, CARGO_ + numeroFirma, baseFont, encabezado);
		ubicacion[2] = textSello.getBox().getTop() - 10;
		textSello = crearCampoSello(pdfStamper, ubicacion, SECTOR_ + numeroFirma, baseFont, encabezado);
		ubicacion[2] = textSello.getBox().getTop() - 10;
		textSello = crearCampoSello(pdfStamper, ubicacion, REPARTICION_ + numeroFirma, baseFont, encabezado);
    
	  
    // Corresponde a la coordenada x1.
    nuevasCoordenadas[1] = ubicacion[1] + ANCHO_SELLO;
    

    
    if (logger.isDebugEnabled()) {
      logger.debug("crearCamposSello(PdfStamper, float[], int, int, boolean) - end"); //$NON-NLS-1$
      logger.debug("ubicacion salida de x1 y y1: "  + nuevasCoordenadas[1] + " " +nuevasCoordenadas[2]);
    }
    return nuevasCoordenadas;
  }

  public byte[] quitarCamposSelloEncabezado(byte[] contenidoArchivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("quitarCamposSelloEncabezado(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfStamper pdfStamper = null;
    byte[] contenidoConCampos = null;
    ByteArrayOutputStream contenidoCampos = null;

    try {
      pdfReader = new PdfReader(contenidoArchivo);
      contenidoCampos = new ByteArrayOutputStream();
      pdfStamper = new PdfStamper(pdfReader, contenidoCampos, '\0', false);
      // Crear campos correspondientes a número del documento y fecha.
      AcroFields fields = pdfStamper.getAcroFields();
      Map<?, ?> hm = fields.getFields();
      Object[] ob = hm.keySet().toArray();
      // Cargar la ubicacion del primer sello.
      for (int i = 0; i < ob.length; i++) {
        String campoSello = (String) ob[i];
        if (campoSello.contains("reparticion_0") || campoSello.contains("usuario_0")
            || campoSello.contains("cargo_0") || campoSello.contains("sector_0")) {
          fields.removeField((String) ob[i]);
        }
      }
      pdfStamper.close();

    } catch (Exception e) {
      logger.error("quitarCamposSelloEncabezado(byte[])", e); //$NON-NLS-1$

      e.printStackTrace();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("quitarCamposSelloEncabezado(byte[]) - end"); //$NON-NLS-1$
    }
    return contenidoConCampos;
  }
  
	public byte[] adicionarCampos(byte[] contenidoArchivo, Integer numeroFirmas, boolean encabezado) {
		PdfReader pdfReader = null;
		PdfStamper pdfStamper = null;
		byte[] contenidoConCampos = null;
		ByteArrayOutputStream contenidoCampos = null;
		
	
		try {
				pdfReader = new PdfReader(contenidoArchivo);
				contenidoCampos = new ByteArrayOutputStream();
				pdfStamper = new PdfStamper(pdfReader, contenidoCampos, '\0', false);
			// Crear campos correspondientes a número del documento y fecha.
			crearAcroFields(pdfStamper);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<?, ?> hm = fields.getFields();
			Object[] ob = hm.keySet().toArray();
			List<FieldPosition> ubicacion = null;
			// Cargar la ubicacion del primer sello.
			for (int i = 0; i < ob.length; i++) {
				String campoSello = (String) ob[i];
				if (campoSello.contains(SELLO_USUARIO)) {
					ubicacion = fields.getFieldPositions((String) ob[i]);
					fields.removeField((String) ob[i]);
					break;
				}
			}
			int numeroPagina = ubicacion.get(0).page;
			// Se inicializa copia de trabajo con ubicación original, con el
			// objetivo de mantener esta última sin alterar.
			float[] nuevasCoordenadas = { ubicacion.get(0).page, (float) ubicacion.get(0).position.getLeft(),
					(float) ubicacion.get(0).position.getBottom() };
			for (int j = 0; j < numeroFirmas; j++) {
				// Validación cambio de linea, espacio horizontal.
				if (nuevasCoordenadas[1] + ANCHO_SELLO > PageSize.A4.getRight()) {
					nuevasCoordenadas[1] = (float) ubicacion.get(0).position.getLeft();// Ubicación
																						// original,
																						// margen
																						// derecho
																						// coordenada
																						// X
					nuevasCoordenadas[2]=  nuevasCoordenadas[2] - ALTO_CAMPO_FIRMA - 10;
					nuevasCoordenadas[2] = nuevasCoordenadas[2] - ALTO_SELLO - ESPACION_ENTRE_LINEAS; // Nueva
																										// ubicación
																										// vertical
																										// coordenada
																										// Y.
				}
				// Validación cambio de página, espacio vertical.
				if ((nuevasCoordenadas[2] - ALTO_CAMPO_FIRMA - ALTO_CAMPO_SELLO - 10) <= (PageSize.A4.getBottom() + ALTO_FOOTER)) {
					pdfStamper.insertPage(pdfReader.getNumberOfPages() + 1, PageSize.A4);
					numeroPagina = pdfReader.getNumberOfPages();
					// Definir coordenadas iniciales, para nuevas páginas,
					// acorde al A4.
					nuevasCoordenadas[1] = (float) ubicacion.get(0).position.getLeft(); // Coordenada
																						// X
																						// original.
					nuevasCoordenadas[2] = 800;// Coordenada Y
					nuevasCoordenadas[0] = numeroPagina;
				}
				nuevasCoordenadas = crearCamposSello(pdfStamper, nuevasCoordenadas, j, numeroFirmas, encabezado);
				if (j < numeroFirmas) {
					// Corresponde a la coordenada x1.
					nuevasCoordenadas[1] = nuevasCoordenadas[1] + ESPACIO_SELLOS;
				}
			}
			// Crear campo firma cierre.
			crearCampoFirmaCierre(pdfStamper, X1_FIRMA_CIERRE, Y1_FIRMA_CIERRE, ANCHO_FIRMA_CIERRE, ALTO_FIRMA_CIERRE,false);
					
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}finally {
				if (pdfStamper!=null) {
					try {
						pdfStamper.close();
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
				if (pdfReader!=null) {
					pdfReader.close();
				}
			}
		  if (contenidoCampos != null) {
		  	contenidoConCampos = contenidoCampos.toByteArray();
		  }
		return contenidoConCampos;
	}
	
	/*
	 * Crear campo de firma de cierre.
	 */
	private void crearCampoFirmaCierre(PdfStamper pdfStamper, Integer X1, Integer Y1, Integer ANCHO, Integer ALTO, boolean rotarFirma)
			throws ModificacionPDFException {
		try {
			// Se crea el campo de firma de cierre en la última página del
			// documento
			int numeroPagina = (int) pdfStamper.getReader().getNumberOfPages();
			float ubicacion[] = { numeroPagina, X1, Y1, X1 + ANCHO,
					Y1 - ALTO };
			crearCampoFirma(pdfStamper, ubicacion, SIGNATURE_CIERRE,rotarFirma);
		} catch (Exception e) {
			logger.debug("Error creando campo de firma en cierre de documento", e);
			throw new ModificacionPDFException(
					"Error creando campo de firma en cierre de documento: " + e.getMessage());
		}
	}
	
	/**
	 * Crea un campo de firma, a partir de un PdfStamper ya instanciado
	 * 
	 * @param pdfStamper
	 * @param ubicacion
	 * @param nombreCampo
	 */
	private void crearCampoFirma(PdfStamper pdfStamper, float ubicacion[], String nombreCampo, boolean rotarFirma) {
		// Creación del campo de firma.
		PdfFormField fieldSignature = PdfFormField.createSignature(pdfStamper.getWriter());
		Rectangle rectangle = new Rectangle(ubicacion[1], ubicacion[2], ubicacion[3], ubicacion[4]);
		fieldSignature.setWidget(rectangle, null);
		fieldSignature.setFlags(PdfAnnotation.FLAGS_PRINT);
		fieldSignature.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));
		fieldSignature.setFieldName(nombreCampo);
		if(rotarFirma){
			int rotation = pdfStamper.getReader().getPageRotation(pdfStamper.getReader().getNumberOfPages());
			fieldSignature.setMKRotation(rotation);
		}
		// El número de página corresponde al primer elemento del arreglo
		// ubicación.
		fieldSignature.setPage((int) ubicacion[0]);
		pdfStamper.addAnnotation(fieldSignature, (int) ubicacion[0]);
	}

  
  
	private PdfStamper addField(final PdfStamper pdfStamper, final PdfReader pdfReader) throws Exception {
	

		try {
			final Rectangle cropBox = pdfReader.getCropBox(1);
			float x1 = cropBox.getLeft(325);
			float y1 = cropBox.getTop(234);
			float x2 = cropBox.getLeft(505);
			float y2 = cropBox.getTop(164);

			final Rectangle recFecha = new Rectangle(x1, y1, x2, y2);
			final TextField txtFecha = new TextField(pdfStamper.getWriter(), recFecha, FECHA);
			txtFecha.setText(FECHA_ASIGNAR);
			txtFecha.setFontSize(11);

			x1 = cropBox.getLeft(87);
			y1 = cropBox.getTop(218);
			x2 = cropBox.getLeft(500);
			y2 = cropBox.getTop(147);

			final Rectangle recNumero = new Rectangle(x1, y1, x2, y2);
			final TextField txtNumero = new TextField(pdfStamper.getWriter(), recNumero, NUMERO_DOCUMENTO);
			txtNumero.setText(NUMERO_ASIGNAR);
			txtNumero.setFontSize(11);


			pdfStamper.addAnnotation(txtFecha.getTextField(),  1);
			pdfStamper.addAnnotation(txtNumero.getTextField(), 1);

			if (logger.isDebugEnabled()) {
				logger.debug("addField(PdfStamper, PdfReader, String, String, String) - end - return value={}", pdfStamper);
			}
			return pdfStamper;
		} catch (IOException | DocumentException e) {
			logger.error("addField(PdfStamper, PdfReader, String, String, String)", e);

			throw new Exception("Error adding field for signing", e);
		}
	}

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#adicionarCamposNuevaPagina(
   * java .io.File, int)
   */
  public byte[] adicionarCamposNuevaPagina(byte[] contenidoArchivo, Integer numeroFirmas,
      String acronimoTipoDocumento, String motivo) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("adicionarCamposNuevaPagina(byte[], Integer, String, String) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporalConSello = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    int cantPagArchImport;
    try {
      pdfReader = new PdfReader(contenidoArchivo);
      temporalConSello = new ByteArrayOutputStream();
      byte[] templateConFooter = this.adicionarCamposNumeroPaginaNumeroSADE(pdfReader);
      cantPagArchImport = pdfReader.getNumberOfPages();
      String contenido = "El documento fue importado por el sistema DEO con un total de "
          + cantPagArchImport + " p&aacute;gina/s.";

      logger.info("buscando tipo de documento por acronimo = " + acronimoTipoDocumento);
      TipoDocumentoDTO objetoTipoDocumento = tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
      contenidoCampos = this.generarPDF(contenido, motivo, objetoTipoDocumento);
      contenidoCampos = this.adicionarCampos(contenidoCampos, numeroFirmas, false);
      reader1 = new PdfReader(templateConFooter);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporalConSello);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (pdfReader != null) {
        pdfReader.close();
      }
      if (temporalConSello != null) {
        contenidoNuevaPagina = temporalConSello.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("adicionarCamposNuevaPagina(byte[], Integer, String, String) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] adicionarCamposNuevaPagina(byte[] contenidoArchivo, Integer numeroFirmas,
      String acronimoTipoDocumento, String motivo, RequestGenerarDocumento request)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPagina(byte[], Integer, String, String, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporalConSello = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    int cantPagArchImport;
    try {
      pdfReader = new PdfReader(contenidoArchivo);
      temporalConSello = new ByteArrayOutputStream();
      byte[] templateConFooter = this.adicionarCamposNumeroPaginaNumeroSADE(pdfReader);
      cantPagArchImport = pdfReader.getNumberOfPages();
      String contenido = "El documento fue importado por el sistema DEO con un total de "
          + cantPagArchImport + " página/s.";

      logger.info("buscando tipo de documento por acronimo = " + acronimoTipoDocumento);
      TipoDocumentoDTO objetoTipoDocumento = tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
      contenidoCampos = this.generarPDFCCOO(contenido, motivo, objetoTipoDocumento, request);
      contenidoCampos = this.adicionarCampos(contenidoCampos, numeroFirmas, false);
      reader1 = new PdfReader(templateConFooter);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporalConSello);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (pdfReader != null) {
        pdfReader.close();
      }
      if (temporalConSello != null) {
        contenidoNuevaPagina = temporalConSello.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPagina(byte[], Integer, String, String, RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] adicionarCamposNuevaPaginaImpTemp(byte[] contenidoArchivo, Integer numeroFirmas,
      String acronimoTipoDocumento, String motivo, String contenidoTemplate)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPaginaImpTemp(byte[], Integer, String, String, String) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporalConSello = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    try {
      pdfReader = new PdfReader(contenidoArchivo);
      temporalConSello = new ByteArrayOutputStream();
      byte[] templateConFooter = this.adicionarCamposNumeroPaginaNumeroSADE(pdfReader);

      logger.info("buscando tipo de documento por acronimo = " + acronimoTipoDocumento);
      TipoDocumentoDTO objetoTipoDocumento = tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
      contenidoCampos = this.generarPDF(contenidoTemplate, motivo, objetoTipoDocumento);
      contenidoCampos = this.adicionarCampos(contenidoCampos, numeroFirmas, false);
      reader1 = new PdfReader(templateConFooter);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporalConSello);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (pdfReader != null) {
        pdfReader.close();
      }
      if (temporalConSello != null) {
        contenidoNuevaPagina = temporalConSello.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPaginaImpTemp(byte[], Integer, String, String, String) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] adicionarCamposNuevaPaginaImpTemp(byte[] contenidoArchivo, Integer numeroFirmas,
      String acronimoTipoDocumento, String motivo, String contenidoTemplate,
      RequestGenerarDocumento request) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPaginaImpTemp(byte[], Integer, String, String, String, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporalConSello = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    try {
      pdfReader = new PdfReader(contenidoArchivo);
      temporalConSello = new ByteArrayOutputStream();
      byte[] templateConFooter = this.adicionarCamposNumeroPaginaNumeroSADE(pdfReader);

      logger.info("buscando tipo de documento por acronimo = " + acronimoTipoDocumento);
      TipoDocumentoDTO objetoTipoDocumento = tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
      contenidoCampos = this.generarPDFCCOO(contenidoTemplate, motivo, objetoTipoDocumento,
          request);
      contenidoCampos = this.adicionarCampos(contenidoCampos, numeroFirmas, false);
      reader1 = new PdfReader(templateConFooter);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporalConSello);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (pdfReader != null) {
        pdfReader.close();
      }
      if (temporalConSello != null) {
        contenidoNuevaPagina = temporalConSello.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarCamposNuevaPaginaImpTemp(byte[], Integer, String, String, String, RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] adicionarNuevaPaginaVisualizacionImpTemp(byte[] contenidoImportado,
      byte[] contenidoTemplate, String tipoArchivoContenidoImp, TipoDocumentoDTO tipoDocumento,
      String motivo) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPaginaVisualizacionImpTemp(byte[], byte[], String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    try {
      String textoTemplate = new String(contenidoTemplate);
      contenidoImportado = adicionarNuevaPagina(contenidoImportado, textoTemplate, tipoDocumento,
          motivo);
    } catch (PDFConversionException pdfce) {
      logger.error("Error al convertir el archivo importado a pdf. " + pdfce.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPaginaVisualizacionImpTemp(byte[], byte[], String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return contenidoImportado;
  }

  private byte[] adicionarNuevaPagina(byte[] contenidoImportado, String contenidoTemplate,
      TipoDocumentoDTO tipoDocumento, String motivo) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("adicionarNuevaPagina(byte[], String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporal = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    try {
      temporal = new ByteArrayOutputStream();
      contenidoCampos = this.generarPDF(contenidoTemplate, motivo, tipoDocumento);
      contenidoCampos = this.agregarWatermarkPDF(contenidoCampos);
      reader1 = new PdfReader(contenidoImportado);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporal);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página. ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (temporal != null) {
        contenidoNuevaPagina = temporal.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("adicionarNuevaPagina(byte[], String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] adicionarNuevaPaginaVisualizacionImpTemp(byte[] contenidoImportado,
      byte[] contenidoTemplate, String tipoArchivoContenidoImp, TipoDocumentoDTO tipoDocumento,
      String motivo, RequestGenerarDocumento request) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPaginaVisualizacionImpTemp(byte[], byte[], String, TipoDocumentoDTO, String, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    try {
      String textoTemplate = new String(contenidoTemplate);
      contenidoImportado = adicionarNuevaPagina(contenidoImportado, textoTemplate, tipoDocumento,
          motivo, request);
    } catch (PDFConversionException pdfce) {
      logger.error("Error al convertir el archivo importado a pdf. " + pdfce.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPaginaVisualizacionImpTemp(byte[], byte[], String, TipoDocumentoDTO, String, RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return contenidoImportado;
  }

  private byte[] adicionarNuevaPagina(byte[] contenidoImportado, String contenidoTemplate,
      TipoDocumentoDTO tipoDocumento, String motivo, RequestGenerarDocumento request)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPagina(byte[], String, TipoDocumentoDTO, String, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    PdfReader reader1 = null;
    PdfReader reader2 = null;
    PdfCopyFields copy = null;

    ByteArrayOutputStream temporal = null;
    byte[] contenidoNuevaPagina = null;
    byte[] contenidoCampos = null;
    try {
      temporal = new ByteArrayOutputStream();
      contenidoCampos = this.generarPDFCCOO(contenidoTemplate, motivo, tipoDocumento, request);
      contenidoCampos = this.agregarWatermarkPDF(contenidoCampos);
      reader1 = new PdfReader(contenidoImportado);
      reader2 = new PdfReader(contenidoCampos);
      copy = new PdfCopyFields(temporal);
      copy.addDocument(reader1);
      copy.addDocument(reader2);
    } catch (Exception e) {
      logger.error("Error en adición de campos en nueva página. ", e);
    } finally {
      if (copy != null) {
        copy.close();
      }
      if (reader1 != null) {
        reader1.close();
      }
      if (reader2 != null) {
        reader2.close();
      }
      if (temporal != null) {
        contenidoNuevaPagina = temporal.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adicionarNuevaPagina(byte[], String, TipoDocumentoDTO, String, RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return contenidoNuevaPagina;
  }

  public byte[] quitarUltimaPagina(byte[] contenidoArchivo) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("quitarUltimaPagina(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfReader pdfReader2 = null;
    PdfCopyFields copy = null;
    ByteArrayOutputStream archivoTemporal = null;
    Integer cantPagArchImport;

    byte[] contenidoNuevoDocumento = null;

    try {
      archivoTemporal = new ByteArrayOutputStream();
      pdfReader = new PdfReader(contenidoArchivo);
      cantPagArchImport = (pdfReader.getNumberOfPages()) - 1;
      pdfReader.selectPages("1-" + cantPagArchImport.toString());
      byte[] archivoSinNumeracion = this.eliminarCamposNumeroPaginaNumeroSADE(pdfReader);
      pdfReader2 = new PdfReader(archivoSinNumeracion);
      copy = new PdfCopyFields(archivoTemporal);
      copy.addDocument(pdfReader2);
    } catch (Exception e) {
      logger.error("Error en eliminación de la última pagina ", e);
    } finally {

      if (pdfReader2 != null) {
        pdfReader2.close();
      }

      if (pdfReader != null) {
        pdfReader.close();
      }

      if (copy != null) {
        copy.close();
      }

      if (archivoTemporal != null) {
        contenidoNuevoDocumento = archivoTemporal.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("quitarUltimaPagina(byte[]) - end"); //$NON-NLS-1$
    }
    return contenidoNuevoDocumento;
  }

  /**
   * A partir de un pdfReader ya creado, adiciona los campos para la página y el
   * número SADE, creados en los documentos importados.
   * 
   * @param pdfReader
   * @return Arreglo de bytes con el contenido del documento importado más los
   *         campos mencionados
   * @throws Exception
   */
  private byte[] eliminarCamposNumeroPaginaNumeroSADE(PdfReader pdfReader) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarCamposNumeroPaginaNumeroSADE(PdfReader) - start"); //$NON-NLS-1$
    }

    byte[] contenidoConCampos = null;
    ByteArrayOutputStream contenidoCampos = new ByteArrayOutputStream();
    PdfStamper pdfStamper = null;

    try {
      pdfStamper = new PdfStamper(pdfReader, contenidoCampos);

      AcroFields fields = pdfStamper.getAcroFields();
      Map<?, ?> hm = fields.getFields();
      Object[] ob = hm.keySet().toArray();
      // Cargar la ubicacion del primer sello.
      for (int j = 0; j < ob.length; j++) {
        String campoNumero = (String) ob[j];
        if (campoNumero.contains(NUMERO_)) {
          fields.removeField((String) ob[j]);
        }

        if (campoNumero.contains(PAGINA_)) {
          fields.removeField((String) ob[j]);
        }
      }
    } catch (Exception e) {
      logger.error("Error en adición de campos ", e);
      throw e;
    } finally {
      if (pdfStamper != null) {
        pdfStamper.close();
      }
      contenidoConCampos = contenidoCampos.toByteArray();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarCamposNumeroPaginaNumeroSADE(PdfReader) - end"); //$NON-NLS-1$
    }
    return contenidoConCampos;
  }

  /**
   * Crear campos del tipo AcroField, para reemplazar los campos generados a
   * partir del template html, correspondientes a: 1. Número del documento. 2.
   * Fecha.
   * 
   * @param pdfStamper
   * @throws Exception
   */
//  private void crearAcroFields(PdfStamper pdfStamper) throws Exception {
//    if (logger.isDebugEnabled()) {
//      logger.debug("crearAcroFields(PdfStamper) - start"); //$NON-NLS-1$
//    }
//
//    BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI,
//        BaseFont.NOT_EMBEDDED);
//    AcroFields fields = pdfStamper.getAcroFields();
//    Map<String, Item> hm = fields.getFields();
//    Object[] ob = hm.keySet().toArray();
//    // Adicionar primer campo para sello.
//    for (int i = 0; i < ob.length; i++) {
//      String campo = (String) ob[i];
//      if (!campo.contains(SELLO_USUARIO)) {
//
//        List<FieldPosition> f = fields.getFieldPositions((String) ob[i]);
//        Rectangle rectangle = new Rectangle((float) f.get(0).position.getLeft(),
//            (float) f.get(0).position.getBottom() + 3, (float) f.get(0).position.getLeft() + 200,
//            (float) f.get(0).position.getBottom() + 13);
//        TextField textField = null;
//        if (campo.contains(FECHA)) {
//          textField = new TextField(pdfStamper.getWriter(), rectangle, FECHA);
//          textField.setText(FECHA_ASIGNAR);
//        } else {
//          textField = new TextField(pdfStamper.getWriter(), rectangle, NUMERO_DOCUMENTO);
//          textField.setText(NUMERO_ASIGNAR);
//        }
//        textField.setFont(baseFont);
//        textField.setFontSize(11);
//        pdfStamper.addAnnotation(textField.getTextField(), f.get(0).page);
//        fields.removeField((String) ob[i]);
//      }
//    }
//
//    if (logger.isDebugEnabled()) {
//      logger.debug("crearAcroFields(PdfStamper) - end"); //$NON-NLS-1$
//    }
//  }
  
  
  
	private void crearAcroFields(PdfStamper pdfStamper) throws Exception {
		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		AcroFields fields = pdfStamper.getAcroFields();
		Map<String, Item> hm = fields.getFields();
		Object[] ob = hm.keySet().toArray();
		// Adicionar primer campo para sello.
		for (int i = 0; i < ob.length; i++) {
			String campo = (String) ob[i];
			if (!campo.contains(SELLO_USUARIO)) {

				List<FieldPosition> f = fields.getFieldPositions((String) ob[i]);

				TextField textField = null;
				if (campo.contains(FECHA)) {
					Rectangle rectangle = f.get(0).position;
					textField = new TextField(pdfStamper.getWriter(), rectangle, FECHA);
					textField.setText(FECHA_ASIGNAR);
					textField.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				} else if (campo.contains(LOCALIDAD)) {
					//TODO modificar aca
					Rectangle rectangle = f.get(0).position;
					textField = new TextField(pdfStamper.getWriter(), rectangle, LOCALIDAD);
					textField.setText(LOCALIDAD);
					textField.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				} else if (campo.contains(NUMERO_DOCUMENTO)){
					Rectangle rectangle = establecerSizeField(450, 13, f, 3);
	
					textField = new TextField(pdfStamper.getWriter(), rectangle, NUMERO_DOCUMENTO);
					textField.setText(NUMERO_ASIGNAR);	

				}
				if(textField != null) {
					textField.setFont(baseFont);
					textField.setFontSize(11);
					pdfStamper.addAnnotation(textField.getTextField(), f.get(0).page);
				}				
				fields.removeField((String) ob[i]);
			}
		}
	}
	
	private Rectangle establecerSizeField(int anchoCampo, int altoCampo ,List<FieldPosition> f, int posicion) {

		return new Rectangle((float) f.get(0).position.getLeft(), (float) f.get(0).position.getBottom() + posicion,
				(float) f.get(0).position.getLeft() + anchoCampo, (float) f.get(0).position.getBottom() + altoCampo);
	}

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#actualizarCampoPdf(java.util.
   * Map, java.lang.String, java.lang.String)
   */
  public byte[] actualizarCampoPdf(Map<String, String> campos, byte[] contenido)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarCampoPdf(Map<String,String>, byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfStamper pdfStamper = null;
    byte[] contenidoActual = null;
    ByteArrayOutputStream contenidoCampo = null;
    try {
      pdfReader = new PdfReader(contenido);
      contenidoCampo = new ByteArrayOutputStream();
      pdfStamper = new PdfStamper(pdfReader, contenidoCampo, '\0', true);
            
      AcroFields acroFields = pdfStamper.getAcroFields();
      Set<String> acroKeys = acroFields.getFields().keySet();
      Set<String> keys = campos.keySet();
      for (String nombreCampo : keys) {
        for (String acroCampo : acroKeys) {
          // Adicionar comentario explicativo
          if (acroCampo.equals(nombreCampo) || StringUtils.endsWith(acroCampo, nombreCampo)) {
            acroFields.setField(acroCampo, campos.get(nombreCampo));
            acroFields.setFieldProperty(acroCampo, "setfflags", PdfFormField.FF_READ_ONLY, null);
            break;
          }
        }
      }
    } catch (Exception e) {
      logger.error("Error en actualización de campos en el archivo pdf" + e);
    } finally {
      if (pdfStamper != null) {
        try {
          pdfStamper.close();
        } catch (DocumentException | IOException e) {
          e.printStackTrace();
        }
      }
      if (contenidoCampo != null) {
        contenidoActual = contenidoCampo.toByteArray();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarCampoPdf(Map<String,String>, byte[]) - end"); //$NON-NLS-1$
    }
    return contenidoActual;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#obtenerCampoPdf(java.lang.
   * String)
   */
  public String obtenerCampoPdf(byte[] contenido) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCampoPdf(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    String numeroCampo = null;
    try {
      pdfReader = new PdfReader(contenido);
      AcroFields acroFields = pdfReader.getAcroFields();
      List<String> camposFirma = acroFields.getBlankSignatureNames();
      Collections.sort(camposFirma);
      String campo = camposFirma.get(0);
      numeroCampo = campo.substring(campo.lastIndexOf("_") + 1, campo.length());
    } catch (IOException e) {
      logger.error("Error obteniendo campos de firma, en blanco", e);
    } finally {
      if (pdfReader != null) {
        pdfReader.close();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCampoPdf(byte[]) - end"); //$NON-NLS-1$
    }
    return numeroCampo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#pdfNoInteractivo(java.lang.
   * String)
   */
  @Deprecated
  public void pdfNoInteractivo(String name) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("pdfNoInteractivo(String) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfStamper pdfStamper = null;
    try {
      pdfReader = new PdfReader(new FileInputStream(name));
      pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(name), '\0', false);
      pdfStamper.setFormFlattening(true);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    } finally {
      if (pdfStamper != null) {
        try {
          pdfStamper.close();
        } catch (DocumentException | IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("pdfNoInteractivo(String) - end"); //$NON-NLS-1$
    }
  }

  public String getLeyendaConmemorativa() {
    return leyendaConmemorativa;
  }

  public void setLeyendaConmemorativa(String leyendaConmemorativa) {
    this.leyendaConmemorativa = leyendaConmemorativa;
  }

  private Template getTemplate(TipoDocumentoDTO tipoDocumento) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("getTemplate(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {

      if (tipoDocumento.getEsComunicable()) {
        if (tipoDocumento.getCodigoTipoDocumentoSade().equals(MEMO_)) {
          Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_MEMO);
          if (logger.isDebugEnabled()) {
            logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
          }
          return returnTemplate;
        } else {
          Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_IMPORTAR_TEMPLATE_NOTA);
          if (logger.isDebugEnabled()) {
            logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
          }
          return returnTemplate;
        }
      } else {
        Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_IMPORTAR_TEMPLATE);
        if (logger.isDebugEnabled()) {
          logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
        }
        return returnTemplate;
      }

    } else {
      if (tipoDocumento.getCodigoTipoDocumentoSade() == null) {
        Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_BASE_TEMPLATE);
        if (logger.isDebugEnabled()) {
          logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
        }
        return returnTemplate;
      } else {
        if (tipoDocumento.getEsComunicable()) {
          if (tipoDocumento.getCodigoTipoDocumentoSade().equals(MEMO_)) {
            Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_BASE_TEMPLATE_MEMO);
            if (logger.isDebugEnabled()) {
              logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
            }
            return returnTemplate;
          } else {
            Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_BASE_TEMPLATE_NOTA);
            if (logger.isDebugEnabled()) {
              logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
            }
            return returnTemplate;
          }
        } else {
          Template returnTemplate = configFreeMarker.getTemplate(DOCUMENTO_BASE_TEMPLATE);
          if (logger.isDebugEnabled()) {
            logger.debug("getTemplate(TipoDocumentoDTO) - end"); //$NON-NLS-1$
          }
          return returnTemplate;
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#agregarNumeroPaginaNumeroSADE
   * (byte [], java.lang.String)
   */
  public byte[] agregarNumeroPaginaNumeroSADE(byte[] contenido, String numeroSade)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarNumeroPaginaNumeroSADE(byte[], String) - start"); //$NON-NLS-1$
    }

    HashMap<String, String> campos = new HashMap<String, String>();
    PdfReader pdfReader = null;
    try {
      pdfReader = new PdfReader(contenido);
      for (int i = 1; i < pdfReader.getNumberOfPages(); i++) {
        campos.put("Pagina_" + i, " " + i + " de "
            + String.valueOf(pdfReader.getNumberOfPages() - 1));
        campos.put("Numero_" + i, numeroSade);
      }
    } catch (IOException e) {
      logger.error("Error al actualizar número de página: " + numeroSade, e);
    } finally {
      if (pdfReader != null) {
        pdfReader.close();
      }
    }
    byte[] returnbyteArray = this.actualizarCampoPdf(campos, contenido);
    if (logger.isDebugEnabled()) {
      logger.debug("agregarNumeroPaginaNumeroSADE(byte[], String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  /**
   * A partir de un pdfReader ya creado, adiciona los campos para la página y el
   * número SADE, creados en los documentos importados.
   * 
   * @param pdfReader
   * @return Arreglo de bytes con el contenido del documento importado más los
   *         campos mencionados
   * @throws Exception
   */
  private byte[] adicionarCamposNumeroPaginaNumeroSADE(PdfReader pdfReader) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("adicionarCamposNumeroPaginaNumeroSADE(PdfReader) - start"); //$NON-NLS-1$
    }

    byte[] contenidoConCampos = null;
    ByteArrayOutputStream contenidoCampos = new ByteArrayOutputStream();
    PdfStamper pdfStamper = null;
    Rectangle rectangle = null;
    Rectangle rectangleNumeroSadeEspecial = null;
    Rectangle rectanglePageXofY = null;
    try {
      // BISADE-11777 // Se agrega esta porcion de codigo para solucionar
      // el error del mantis-76589
      if (pdfReader.isRebuilt()) {
        pdfReader.setTampered(false);
        pdfStamper = new PdfStamper(pdfReader, contenidoCampos);
        pdfStamper.close();
        pdfReader = new PdfReader(contenidoCampos.toByteArray());

      }
      pdfStamper = new PdfStamper(pdfReader, contenidoCampos, '\0', true);

      BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI,
          BaseFont.NOT_EMBEDDED);
      // Adicionar primer campo para numero sade y numero de pagina.
      for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
        String footerPageXofY = "Pagina_" + i;
        String footerSade = "Numero_" + i;
        rectangle = pdfReader.getBoxSize(i, "media");

        float anchoPagina = rectangle.getWidth();
        // Este es el ejemplo mas largo para determinat el largo del
        // campo
        // numero sade --> 'Número: MARIO-2001-00009190-132-DGPIANADJ'
        // Para calcular el largo son 42 caracteres * 5px ancho + 10 px
        // de margen
        float anchoLeyenda = ((44 * 5) + 10);
        float posicionX = (anchoPagina - anchoLeyenda);

        float posicionXSinImportar = (rectangle.getHeight()) - anchoLeyenda;
        // cambiar la rotacion desde el stamper o reader ya que por mas
        // que cambiemos el eje x sigue escribiendo con la rotacion del
        // documento.

        if (pdfReader.getPageRotation(i) > 0 && pdfReader.getPageRotation(i) != 180) {
          rectangleNumeroSadeEspecial = new Rectangle(posicionXSinImportar, ALTO_FOOTER,
              posicionXSinImportar + anchoLeyenda, ALTO_FOOTER + ALTO_CAMPO_FIRMA);
          rectanglePageXofY = new Rectangle(posicionXSinImportar, ALTO_CAMPO_SELLO,
              posicionXSinImportar + anchoLeyenda, ALTO_CAMPO_FIRMA + ALTO_CAMPO_SELLO);
        } else {
          rectangleNumeroSadeEspecial = new Rectangle(posicionX, ALTO_FOOTER,
              posicionX + anchoLeyenda, ALTO_FOOTER + ALTO_CAMPO_FIRMA);
          rectanglePageXofY = new Rectangle(posicionX, ALTO_CAMPO_SELLO, posicionX + anchoLeyenda,
              ALTO_CAMPO_FIRMA + ALTO_CAMPO_SELLO);
        }
        PdfWriter writer = pdfStamper.getWriter();

        TextField textFieldSade = new TextField(writer, rectangleNumeroSadeEspecial, footerSade);
        TextField textFieldPage = new TextField(writer, rectanglePageXofY, footerPageXofY);
        textFieldSade.setFont(baseFont);
        textFieldSade.setFontSize(11);
        textFieldPage.setFont(baseFont);
        textFieldPage.setFontSize(11);
        textFieldSade.setText("XXX");
        textFieldPage.setText("XXX");

        textFieldPage.setRotation(pdfReader.getPageRotation(i));
        textFieldSade.setRotation(pdfReader.getPageRotation(i));

        pdfStamper.addAnnotation(textFieldSade.getTextField(), (int) i);
        pdfStamper.addAnnotation(textFieldPage.getTextField(), (int) i);
      }

    } catch (Exception e) {
      logger.error("Error en adición de campos ", e);
      throw e;
    } finally {
      if (pdfStamper != null) {
        pdfStamper.close();
      }
      contenidoConCampos = contenidoCampos.toByteArray();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("adicionarCamposNumeroPaginaNumeroSADE(PdfReader) - end"); //$NON-NLS-1$
    }
    return contenidoConCampos;
  }

  /**
   * Valida si un pdf esta firmado. No valida si la firma es valida.
   * 
   * @param data
   *          (byte[])
   * @return true si esta firmado / false si no esta firmado.
   * @throws ValidacionCampoFirmaException
   */
  public boolean estaFirmado(byte[] data) throws ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("estaFirmado(byte[]) - start"); //$NON-NLS-1$
    }

    boolean salida = true;
    PdfReader pdfReader = null;
    try {
      pdfReader = new PdfReader(data);
      List<String> camposFirma = pdfReader.getAcroFields().getSignatureNames();
      if (camposFirma.size() == 0) {
        salida = false;
      }
    } catch (IOException e) {
      logger.error("Error: Validando firmas del documento" + e.getMessage(), e);
      throw new ValidacionCampoFirmaException(
          "Error: Validando firmas del documento" + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("estaFirmado(byte[]) - end"); //$NON-NLS-1$
    }
    return salida;
  }

  /**
   * Valida si un pdf esta firmado o posee campos de firma. No valida si la
   * firma es valida.
   * 
   * @param data
   *          (byte[])
   * @return true si esta firmado o posee campos para firmar / false si no esta
   *         firmado o no posee campos de firma para firmar.
   * @throws ValidacionCampoFirmaException
   */
  public boolean estaFirmadoOConEspaciosDeFirma(byte[] data) throws ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("estaFirmadoOConEspaciosDeFirma(byte[]) - start"); //$NON-NLS-1$
    }

    boolean salida = true;
    PdfReader pdfReader = null;
    try {
      pdfReader = new PdfReader(data);
      List<String> camposFirmaEnBlanco = pdfReader.getAcroFields().getBlankSignatureNames();
      List<String> camposFirma = pdfReader.getAcroFields().getSignatureNames();
      if (camposFirmaEnBlanco.size() == 0 && camposFirma.size() == 0) {
        salida = false;
      }
    } catch (IOException e) {
      logger.error("Error: Validando firmas del documento" + e.getMessage(), e);
      throw new ValidacionCampoFirmaException(
          "Error: Validando firmas del documento" + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("estaFirmadoOConEspaciosDeFirma(byte[]) - end"); //$NON-NLS-1$
    }
    return salida;

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.deo.core.api.services.PdfService#validarCamposFirma(byte[])
   */
  public void validarCamposFirma(byte[] data) throws ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposFirma(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    try {
      pdfReader = new PdfReader(data);
      List<String> camposFirmaEnBlanco = pdfReader.getAcroFields().getBlankSignatureNames();
      if (camposFirmaEnBlanco.size() >= 1)
        throw new ValidacionCampoFirmaException(
            "Error: Existe al menos un campo de firma en blanco");
      List<String> camposFirma = pdfReader.getAcroFields().getSignatureNames();
      if (camposFirma.size() == 0)
        throw new ValidacionCampoFirmaException(
            "Error: El documento no tiene campos de firma llenos");
    } catch (IOException e) {
      logger.error("Error: Validando firmas del documento" + e.getMessage(), e);
      throw new ValidacionCampoFirmaException(
          "Error: Validando firmas del documento" + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposFirma(byte[]) - end"); //$NON-NLS-1$
    }
  }

  public byte[] crearDocumentoPDFPrevisualizacion(byte[] datos, TipoDocumentoDTO tipoDocumento)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearDocumentoPDFPrevisualizacion(byte[], TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = this.crearDocumentoPDFPrevisualizacion(NOMBRE_GENERICO_ARCHIVO_PDF,
        datos, tipoDocumento);
    if (logger.isDebugEnabled()) {
      logger.debug("crearDocumentoPDFPrevisualizacion(byte[], TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.GenerarDocumentoService#
   * crearDocumentoPDFPrevisualizacion(java.lang.String, byte[])
   */
  public byte[] crearDocumentoPDFPrevisualizacion(String nombreArchivo, final byte[] datos,
      TipoDocumentoDTO tipoDocumento) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearDocumentoPDFPrevisualizacion(String, byte[], TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }
    byte[] contenido = datos;
    try {

      contenido = this.agregarWatermarkPDF(contenido);

      if (logger.isDebugEnabled()) {
        logger.debug("crearDocumentoPDFPrevisualizacion(String, byte[], TipoDocumentoDTO) - end"); //$NON-NLS-1$
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return contenido;
  }

  public boolean borrarImagenesTemporales(String directorio) {
    if (logger.isDebugEnabled()) {
      logger.debug("borrarImagenesTemporales(String) - start"); //$NON-NLS-1$
    }

    File toBeDeleted = new File(directorio);
    boolean returnboolean = new FileUtilities().recursiveDelete(toBeDeleted);
    if (logger.isDebugEnabled()) {
      logger.debug("borrarImagenesTemporales(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }


  /*
   * Crear campo de firma de cierre.
   */
  private void crearCampoFirmaCierre(PdfStamper pdfStamper) throws ModificacionPDFException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirmaCierre(PdfStamper) - start"); //$NON-NLS-1$
    }

    try {
      // Se crea el campo de firma de cierre en la última página del
      // documento
      int numeroPagina = (int) pdfStamper.getReader().getNumberOfPages();
      float ubicacion[] = { numeroPagina, X1_FIRMA_CIERRE, Y1_FIRMA_CIERRE,
          X1_FIRMA_CIERRE + ANCHO_FIRMA_CIERRE, Y1_FIRMA_CIERRE - ALTO_FIRMA_CIERRE };
      crearCampoFirma(pdfStamper, ubicacion, SIGNATURE_CIERRE);
    } catch (Exception e) {
      logger.error("Error creando campo de firma en cierre de documento", e);
      throw new ModificacionPDFException(
          "Error creando campo de firma en cierre de documento: " + e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirmaCierre(PdfStamper) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Crea un campo de firma, a partir de un PdfStamper ya instanciado
   * 
   * @param pdfStamper
   * @param ubicacion
   * @param nombreCampo
   */
  private void crearCampoFirma(PdfStamper pdfStamper, float[] ubicacion, String nombreCampo) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirma(PdfStamper, float[], String) - start"); //$NON-NLS-1$
    }

    // Creación del campo de firma.
    PdfFormField fieldSignature = PdfFormField.createSignature(pdfStamper.getWriter());
    Rectangle rectangle = new Rectangle(ubicacion[1], ubicacion[2], ubicacion[3], ubicacion[4]);
    fieldSignature.setWidget(rectangle, null);
    fieldSignature.setFlags(PdfAnnotation.FLAGS_PRINT);
    fieldSignature.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));
    fieldSignature.setFieldName(nombreCampo);
    // El número de página corresponde al primer elemento del arreglo
    // ubicación.
    fieldSignature.setPage((int) ubicacion[0]);
    pdfStamper.addAnnotation(fieldSignature, (int) ubicacion[0]);

    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirma(PdfStamper, float[], String) - end"); //$NON-NLS-1$
    }
  }

  @Deprecated
  public byte[] crearCampoFirmaCierre(byte[] contenido) throws ModificacionPDFException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirmaCierre(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader pdfReader = null;
    PdfStamper pdfStamper = null;
    byte[] resultado = null;
    ByteArrayOutputStream contenidoCampos = null;
    try {
      pdfReader = new PdfReader(contenido);
      Item campoFirmaCierre = pdfReader.getAcroFields().getFieldItem(SIGNATURE_CIERRE);
      if (campoFirmaCierre == null) {
        contenidoCampos = new ByteArrayOutputStream();
        pdfStamper = new PdfStamper(pdfReader, contenidoCampos, '\0', true);
        crearCampoFirmaCierre(pdfStamper);
        pdfStamper.close();
        resultado = contenidoCampos.toByteArray();
      } else {
        resultado = contenido;
      }
    } catch (Exception e) {
      logger.error("Error creando campo de firma en cierre de documento", e);
      throw new ModificacionPDFException(
          "Error creando campo de firma en cierre de documento: " + e.getMessage());
    } finally {
      if (pdfStamper != null) {
        try {
          pdfStamper.close();
        } catch (Exception e) {
          logger.error("Error cerrando stamper en cierre de documento", e);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearCampoFirmaCierre(byte[]) - end"); //$NON-NLS-1$
    }
    return resultado;
  }

  public byte[] firmarConCertificadoServidor(RequestGenerarDocumento requestGenerarDocumento,
      byte[] contenido, String campoFirma, boolean importado) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger
          .debug("firmarConCertificadoServidor(RequestGenerarDocumento, byte[], String) - start"); //$NON-NLS-1$
    }

    long start = System.currentTimeMillis();
    byte[] documentoFirmado = null;
    try {
      ResponseDocumento responseDocumento = new ResponseDocumento();
      responseDocumento.setCampoFirma(campoFirma);
      responseDocumento.setData(contenido);
      responseDocumento.setLocation(localidad);
      responseDocumento.setOrganismo(requestGenerarDocumento.getReparticion());
      responseDocumento.setSector(requestGenerarDocumento.getSector());
      responseDocumento.setCargo(requestGenerarDocumento.getCargo());
      responseDocumento.setUsuario(requestGenerarDocumento.getUsuario());

      documentoFirmado = this.externalTicaPdf.firmarDocumento(responseDocumento, importado, llenaLabelFirma());

      logger.info("Firma con servidor Tica: " + (System.currentTimeMillis() - start) + " ms.");

    } catch (Exception e) {
      logger.error("Error firmando documento con certificado con Tica" + e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("firmarConCertificadoServidor(RequestGenerarDocumento, byte[], String) - end"); //$NON-NLS-1$
    }
    return documentoFirmado;
  }

  private Map<String, String> llenaLabelFirma(){
	  
	  Map<String, String> labelsFirma = new HashMap<>();
	  labelsFirma.put("tica.firma.FirmadoDigitalmentePor", Labels.getLabel("tica.firma.FirmadoDigitalmentePor"));
	  labelsFirma.put("tica.firma.Fecha", Labels.getLabel("tica.firma.Fecha"));
	  labelsFirma.put("tica.firma.Razon", Labels.getLabel("tica.firma.Razon"));
	  labelsFirma.put("tica.firma.Localidad", Labels.getLabel("tica.firma.Localidad"));
	  
	  return labelsFirma;
	  
  }
  
  public ByteArrayOutputStream generarPDFImportadoTemplate(InputStream contenidoImportado,
      InputStream contenidoTemplate, String motivo, TipoDocumentoDTO tipoDocumento)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPDFImportadoTemplate(InputStream, InputStream, String, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    ByteArrayOutputStream archivoTemporal = new ByteArrayOutputStream();
    PdfReader pdfReaderTemplate;
    try {
      pdfReaderTemplate = new PdfReader(contenidoTemplate);
      PdfReader pdfReaderImportado = new PdfReader(contenidoImportado);
      PdfCopyFields copy = new PdfCopyFields(archivoTemporal);
      copy.addDocument(pdfReaderImportado);
      copy.addDocument(pdfReaderTemplate);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPDFImportadoTemplate(InputStream, InputStream, String, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return archivoTemporal;
  }

@Override
public Map<String, Object> crearDocumentoParaAutoFirma(byte[] contenido,Map<String, String> map ,boolean importado) throws ModificacionPDFException {

	PdfStamper stp = null;
	PdfReader reader = null;
	
	Map<String, Object> mapResult = new HashMap<>();
	


	try(ByteArrayOutputStream fout = new ByteArrayOutputStream()) {
	
		reader = new PdfReader(new ByteArrayInputStream(contenido));
		stp = new PdfStamper(reader, fout, '\0', true);
		addField(stp, reader, map.get("usuario"), map.get("cargo"), 
						map.get("organismo"), importado, map.get("fieldSign"));
	
		stp.close();
		mapResult.put("data",fout.toByteArray());
		mapResult.put("page", importado ? reader.getNumberOfPages(): 1);
		mapResult.put("field", map.get("fieldSign"));
		return mapResult;		
		
	} catch (Exception e) {
		logger.error("Error al crear el doc pdf para autofirma: {}",e.getMessage(),e);
		throw new ModificacionPDFException(e.getMessage(),e);
	}finally {
		if(reader!=null) {
			reader.close();
		}
		if(stp!=null) {
			try {
				stp.close();
			} catch (DocumentException | IOException e) {
				logger.error("Error al cerrar el pdfStamper: {}", e.getMessage(),e);
			}
		}
	}
	
}


private static void addField(final PdfStamper pdfStamper, final PdfReader pdfReader, final String usuario,
		final String cargo, final String organismo, boolean importado, String fieldSign) throws Exception {
	if (logger.isDebugEnabled()) {
		logger.debug("addField(pdfStamper={}, pdfReader={}, usuario={}, cargo={}, organismo={}) - start", pdfStamper, pdfReader, usuario, cargo, organismo);
	}

	try {
		final int numPags = pdfReader.getNumberOfPages();
		final int numFirmas = pdfReader.getAcroFields().getSignatureNames().size();
		final int numUsuario = numFirmas + 1;
		final Rectangle cropBox = pdfReader.getCropBox(1);
		float x1 = cropBox.getLeft(325);
		float y1 = cropBox.getTop(234);
		float x2 = cropBox.getLeft(505);
		float y2 = cropBox.getTop(164);

		final Rectangle recFecha = new Rectangle(x1, y1, x2, y2);
		final TextField txtFecha = new TextField(pdfStamper.getWriter(), recFecha, FECHA);
		txtFecha.setText(FECHA_ASIGNAR);
		txtFecha.setFontSize(11);

		x1 = cropBox.getLeft(87);
		y1 = cropBox.getTop(218);
		x2 = cropBox.getLeft(500);
		y2 = cropBox.getTop(147);

		final Rectangle recNumero = new Rectangle(x1, y1, x2, y2);
		final TextField txtNumero = new TextField(pdfStamper.getWriter(), recNumero, NUMERO_DOCUMENTO);
		txtNumero.setText(NUMERO_ASIGNAR);
		txtNumero.setFontSize(11);
		
		// Creo el campo de firma
	    PdfFormField fieldSignature = PdfFormField.createSignature(pdfStamper.getWriter());
	    Rectangle rectangle = getRectangleFirma(pdfReader, null);
	    fieldSignature.setWidget(rectangle, null);
	    fieldSignature.setFlags(PdfAnnotation.FLAGS_PRINT);
	    fieldSignature.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));
	    fieldSignature.setFieldName(fieldSign);
	    // El número de página corresponde al primer elemento del arreglo
	    // ubicación.
	    fieldSignature.setPage(importado ? numPags: 1);
		
		
		final Rectangle recUsuario = getRectangleFirma(pdfReader, "usuario_" + numUsuario);
		final TextField txtUsuario = new TextField(pdfStamper.getWriter(), recUsuario, "usuario_" + numUsuario);
		txtUsuario.setText(usuario + " " + cargo + " " + organismo);
		txtUsuario.setFontSize(8);

		pdfStamper.addAnnotation(txtFecha.getTextField(), importado ? numPags: 1);
		pdfStamper.addAnnotation(txtNumero.getTextField(), importado ? numPags: 1);
		
		
		pdfStamper.addAnnotation(fieldSignature,importado ? numPags: 1);
		
		pdfStamper.addAnnotation(txtUsuario.getTextField(), numPags);

		if (logger.isDebugEnabled()) {
			logger.debug("addField(PdfStamper, PdfReader, String, String, String) - end - return value={}", pdfStamper);
		}
		pdfStamper.close();
		
	} catch (IOException | DocumentException e) {
		logger.error("addField(PdfStamper, PdfReader, String, String, String)", e);

		throw new Exception("Error adding field for signing", e);
	}
}


private static Rectangle getRectangleFirma(final PdfReader reader, final String campo) {
	if (logger.isDebugEnabled()) {
		logger.debug("getRectangleFirma(reader={}, campo={}) - start", reader, campo);
	}

	Rectangle rectangle;
	if (campo != null) {
		//nombre firmante
		rectangle = new Rectangle(COORD_X1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, COORD_Y1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_IZQUIERDA
								, COORD_X2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, COORD_Y2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_IZQUIERDA);
		
	}else {
		//firma
		rectangle = new Rectangle(
				COORD_X1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, 
				COORD_Y2_NOMB_FIRMA_CERTIF*2+MARGEN_ADICIONAL_IZQUIERDA, 
				COORD_X2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, 
				COORD_Y2_NOMB_FIRMA_CERTIF*6+MARGEN_ADICIONAL_IZQUIERDA);
	}


	if (logger.isDebugEnabled()) {
		logger.debug("getRectangleFirma(PdfReader, String) - end - return value={}", rectangle);
	}
	return rectangle;

}

}

final class FileUtilities {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(FileUtilities.class);

  /* configuraciones traidas de ticaSign*/
  private static final String FECHA_ASIGNAR = "<Fecha a asignar>";
  private static final String NUMERO_DOCUMENTO = "numero_documento";
  private static final String NUMERO_ASIGNAR = "<Número a asignar>";
  private static final String FECHA = "fecha";
  private static final String FIRMADO_DIGITAL_DEL_DOCUMENTO = "Firmado digital del documento";


  private static final Integer COORD_X1_NOMB_FIRMA_CERTIF = 30;
  private static final Integer COORD_Y1_NOMB_FIRMA_CERTIF = 5;
  private static final Integer COORD_X2_NOMB_FIRMA_CERTIF = 200;
  private static final Integer COORD_Y2_NOMB_FIRMA_CERTIF = 15;
	
  private static final Integer MARGEN_ADICIONAL_DERECHA = 120;
  private static final Integer MARGEN_ADICIONAL_IZQUIERDA = 200;  
  
  /**
   * Create a new temporary directory. Use something like
   * {@link #recursiveDelete(File)} to clean this directory up since it isn't
   * deleted automatically
   * 
   * @return the new directory
   * @throws IOException
   *           if there is an error creating the temporary directory
   */
  public File createTempDir() throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("createTempDir() - start"); //$NON-NLS-1$
    }

    final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
    File newTempDir;
    final int maxAttempts = 9;
    int attemptCount = 0;
    do {
      attemptCount++;
      if (attemptCount > maxAttempts) {
        throw new IOException("The highly improbable has occurred! Failed to "
            + "create a unique temporary directory after " + maxAttempts + " attempts.");
      }
      String dirName = UUID.randomUUID().toString();
      newTempDir = new File(sysTempDir, dirName);
    } while (newTempDir.exists());

    if (newTempDir.mkdirs()) {
      if (logger.isDebugEnabled()) {
        logger.debug("createTempDir() - end"); //$NON-NLS-1$
      }
      return newTempDir;
    } else {
      throw new IOException("Failed to create temp dir named " + newTempDir.getAbsolutePath());
    }
  }

  /**
   * Recursively delete file or directory
   * 
   * @param fileOrDir
   *          the file or dir to delete
   * @return true iff all files are successfully deleted clase especifica
   *         reutilizable
   */
  public boolean recursiveDelete(File fileOrDir) {
    if (logger.isDebugEnabled()) {
      logger.debug("recursiveDelete(File) - start"); //$NON-NLS-1$
    }

    if (fileOrDir.isDirectory()) {
      // recursively delete contents
      for (File innerFile : fileOrDir.listFiles()) {
        if (!this.recursiveDelete(innerFile)) {
          if (logger.isDebugEnabled()) {
            logger.debug("recursiveDelete(File) - end"); //$NON-NLS-1$
          }
          return false;
        }
      }
    }

    boolean returnboolean = fileOrDir.delete();
    if (logger.isDebugEnabled()) {
      logger.debug("recursiveDelete(File) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }
  
  public byte[] crearDocumentoParaAutoFirma(byte[] contenido, String campoFirma, 
		  String usuario,String cargo, String organismo, boolean importado, String location) throws Exception {
	  
		PdfStamper stp = null;
		PdfReader reader = null;

		final ByteArrayOutputStream fout = new ByteArrayOutputStream();

		try {
		
			reader = new PdfReader(new ByteArrayInputStream(contenido));
			//stp = PdfStamper.createSignature(reader, fout, '\0', null, true);
			  stp = new PdfStamper(reader, fout, '\0', true);
			
			final PdfSignatureAppearance sap = stp.getSignatureAppearance();

			if (campoFirma.contains("cierre")) {
				sap.setVisibleSignature(campoFirma);
			} else {
				stp = addField(stp, reader, usuario, cargo, organismo, importado);
				sap.setVisibleSignature(getRectangleFirma(reader, null), reader.getNumberOfPages(), campoFirma);
				sap.setRenderingMode(RenderingMode.DESCRIPTION);
//				sap.setContact(firmaInput.getUsuario() + " " + firmaInput.getCargo() + " " + firmaInput.getOrganismo());
				sap.setReason(FIRMADO_DIGITAL_DEL_DOCUMENTO);
				sap.setLocation(location);
				
				return fout.toByteArray();

			}

			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage(),e);
		}finally {
			if(reader!=null) {
				reader.close();
			}
			if(stp!=null) {
				stp.close();
			}
			if(fout!=null) {
				fout.close();
			}
		}
		return contenido;
		
	  
  }
  
  
	private String readPDFContent(PdfReader pdfReader) throws IOException {
	
	    //Get the number of pages in pdf.
	    int pages = pdfReader.getNumberOfPages(); 
	    String pageContent = null;
	    
	    //Iterate the pdf through pages.
	    for(int i=1; i<=pages; i++) { 
	      //Extract the page content using PdfTextExtractor.
	      pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);

	      //Print the page content on console.
	      System.out.println("Content on Page "
	                          + i + ": " + pageContent);
	      }
	    
	    
		return pageContent;
	}

	private static PdfStamper addField(final PdfStamper pdfStamper, final PdfReader pdfReader, final String usuario,
			final String cargo, final String organismo, boolean importado) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addField(pdfStamper={}, pdfReader={}, usuario={}, cargo={}, organismo={}) - start", pdfStamper, pdfReader, usuario, cargo, organismo);
		}

		try {
			final int numPags = pdfReader.getNumberOfPages();
			final int numFirmas = pdfReader.getAcroFields().getSignatureNames().size();
			final int numUsuario = numFirmas + 1;
			final Rectangle cropBox = pdfReader.getCropBox(1);
			float x1 = cropBox.getLeft(325);
			float y1 = cropBox.getTop(234);
			float x2 = cropBox.getLeft(505);
			float y2 = cropBox.getTop(164);

			final Rectangle recFecha = new Rectangle(x1, y1, x2, y2);
			final TextField txtFecha = new TextField(pdfStamper.getWriter(), recFecha, FECHA);
			txtFecha.setText(FECHA_ASIGNAR);
			txtFecha.setFontSize(11);

			x1 = cropBox.getLeft(87);
			y1 = cropBox.getTop(218);
			x2 = cropBox.getLeft(500);
			y2 = cropBox.getTop(147);

			final Rectangle recNumero = new Rectangle(x1, y1, x2, y2);
			final TextField txtNumero = new TextField(pdfStamper.getWriter(), recNumero, NUMERO_DOCUMENTO);
			txtNumero.setText(NUMERO_ASIGNAR);
			txtNumero.setFontSize(11);

			final Rectangle recUsuario = getRectangleFirma(pdfReader, "usuario_" + numUsuario);
			final TextField txtUsuario = new TextField(pdfStamper.getWriter(), recUsuario, "usuario_" + numUsuario);
			txtUsuario.setText(usuario + " " + cargo + " " + organismo);
			txtUsuario.setFontSize(8);

			pdfStamper.addAnnotation(txtFecha.getTextField(), importado ? numPags: 1);
			pdfStamper.addAnnotation(txtNumero.getTextField(), importado ? numPags: 1);
			pdfStamper.addAnnotation(txtUsuario.getTextField(), numPags);

			if (logger.isDebugEnabled()) {
				logger.debug("addField(PdfStamper, PdfReader, String, String, String) - end - return value={}", pdfStamper);
			}
			return pdfStamper;
		} catch (IOException | DocumentException e) {
			logger.error("addField(PdfStamper, PdfReader, String, String, String)", e);

			throw new Exception("Error adding field for signing", e);
		}
	}
	
	
	private static Rectangle getRectangleFirma(final PdfReader reader, final String campo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getRectangleFirma(reader={}, campo={}) - start", reader, campo);
		}

		Rectangle rectangle;
		final int numFirmas = reader.getAcroFields().getSignatureNames().size();
		if (campo != null) {
			//nombre firmante
			rectangle = new Rectangle(COORD_X1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, COORD_Y1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_IZQUIERDA
									, COORD_X2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, COORD_Y2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_IZQUIERDA);
			
		}else {
			//firma
			rectangle = new Rectangle(
					COORD_X1_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, 
					COORD_Y2_NOMB_FIRMA_CERTIF*2+MARGEN_ADICIONAL_IZQUIERDA, 
					COORD_X2_NOMB_FIRMA_CERTIF+MARGEN_ADICIONAL_DERECHA, 
					COORD_Y2_NOMB_FIRMA_CERTIF*6+MARGEN_ADICIONAL_IZQUIERDA);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("getRectangleFirma(PdfReader, String) - end - return value={}", rectangle);
		}
		return rectangle;

	}
  
  
}



