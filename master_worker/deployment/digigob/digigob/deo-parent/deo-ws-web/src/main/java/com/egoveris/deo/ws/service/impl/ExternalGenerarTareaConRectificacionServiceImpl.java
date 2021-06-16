package com.egoveris.deo.ws.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.model.model.RequestExternalGenerarTareaConRectificacion;
import com.egoveris.deo.model.model.RequestExternalGenerarTareaConRectificacion.NombreImagen;
import com.egoveris.deo.model.model.RequestExternalGenerarTareaConRectificacion.PagContexto;
import com.egoveris.deo.model.model.ResponseExternalGenerarTarea;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.ErrorGeneracionTareaException;
import com.egoveris.deo.ws.exception.ParametroInvalidoTareaException;
import com.egoveris.deo.ws.service.IExternalGenerarTareaConRectificacionService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseField;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.TextField;

@Service
public class ExternalGenerarTareaConRectificacionServiceImpl extends ExternalGenerarTareaServiceImpl
		implements IExternalGenerarTareaConRectificacionService {

	private static final String ULTIMA = "ultima";
	private static final String PRIMERA = "primera";
	private static final String TODAS = "todas";
	private static final Integer X1_FIRMA_CIERRE = Integer.valueOf(220);
	private static final Integer Y1_FIRMA_CIERRE = Integer.valueOf(80);
	private static final Integer ANCHO_FIRMA_CIERRE = Integer.valueOf(120);
	private static final Integer ALTO_FIRMA_CIERRE = Integer.valueOf(40);

	private static final Integer ANCHO_TEXTO_RECTIF = Integer.valueOf(200);
	private static final Integer ALTO_TEXTO_RECTIF = Integer.valueOf(70);

	private static final Integer X1_TEXTO_RECTIF_NUEVA_PAGINA = Integer.valueOf(30);
	private static final Integer Y1_TEXTO_RECTIF_NUEVA_PAGINA = Integer.valueOf(600);
	private static final Integer ANCHO_TEXTO_RECTIF_NUEVA_PAGINA = Integer.valueOf(535);
	private static final Integer ALTO_TEXTO_RECTIF_NUEVA_PAGINA = Integer.valueOf(700);

	// TITULO HOJA MARGINACION

	private static final Integer X1_TEXTO_TITULO_NUEVA_PAGINA = Integer.valueOf(150);
	private static final Integer Y1_TEXTO_TITULO_NUEVA_PAGINA = Integer.valueOf(640);
	private static final Integer ANCHO_TEXTO_TITULO_NUEVA_PAGINA = Integer.valueOf(380);
	private static final Integer ALTO_TEXTO_TITULO_NUEVA_PAGINA = Integer.valueOf(70);

	private static final Logger logger = LoggerFactory.getLogger(ExternalGenerarTareaConRectificacionServiceImpl.class);

	@Autowired
	@Qualifier("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;
	@Autowired
	private BuscarDocumentosGedoService buscarDocumentosGedoService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private PdfService pdfService;

	@Value("${app.sistema.gestor.documental}")
	private String gestorDocumental;

	private static final String USUARIOA = "usuario_";
	private static final String CARGOA = "cargo_";
	private final static Integer ALTO_CAMPO_FIRMA = 20;
	private final static Integer ANCHO_SELLO = 210;
	private static final float ALTO_CAMPO_SELLO = 10;

	public ResponseExternalGenerarTarea generarTareaConRectificacion(
			RequestExternalGenerarTareaConRectificacion request) {
		if (logger.isDebugEnabled()) {
			logger.debug("generarTareaConRectificacion(RequestExternalGenerarTareaConRectificacion) - start"); //$NON-NLS-1$
		}

		String textoRectificacion = request.getTextoRectificacion();

		if (textoRectificacion.length() > Constantes.LONGITUD_MAXIMA_TEXTO_RECTIFICACION) {
			throw new ParametroInvalidoTareaException("El Servicio de Rectificación no acepta un texto mayor a "
					+ Constantes.LONGITUD_MAXIMA_TEXTO_RECTIFICACION + " caracteres de longitud maxima");
		}

		ResponseExternalGenerarTarea response = null;
		String nombreArchivoTemp = "";
		byte[] contenidoTemporal = "".getBytes();
		byte[] pdfOriginalDoc1 = "".getBytes();
		String nombreArchivoOriginalDoc1;

		// WEBDAV
		nombreArchivoOriginalDoc1 = gestionArchivosWebDavService
				.obtenerUbicacionDescarga(request.getNroSadeRectificacion());

		try {
			pdfOriginalDoc1 = gestionArchivosWebDavService.obtenerArchivoDeTrabajoWebDav(nombreArchivoOriginalDoc1);
		} catch (Exception e) {
			logger.error("Error al obtener el archivo de trabajo" + e, e.getMessage());
			throw new ErrorGeneracionTareaException("Error al obtener el archivo de trabajo" + e, e);
		}

		if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
			// WEBDAV
			if (yaFueRectificadoElDocumento(pdfOriginalDoc1)) {
				gestionArchivosWebDavService.subirArchivoRevisionRectificacion(request.getNroSadeRectificacion(),
						pdfOriginalDoc1);
			} else {
				gestionArchivosWebDavService.subirArchivoDeTrabajoWebDavOriginal(request.getNroSadeRectificacion(),
						pdfOriginalDoc1, request.getNroSadeRectificacion() + ".pdf");
			}
		}

		if (request.getPagContexto() != null) {
			contenidoTemporal = generarCampoRectificacion(pdfOriginalDoc1, request.getTextoRectificacion(),
					request.getPagContexto());
		}

		if (request.getMarcaAgua() && request.getNombreImagen() != null) {
			String nombreImagen = obtenerPathImagen(request.getNombreImagen());
			contenidoTemporal = this.agregarMarcaDeAgua(contenidoTemporal, nombreImagen);
		}
		contenidoTemporal = generarCampoMarginacion(contenidoTemporal, request.getTextoMarginacion(),
				request.getTextoTitulo(), request);

		// WEBDAV
		nombreArchivoTemp = gestionArchivosWebDavService.crearNombreArchivoTemporal();

		try {

			// WEBDAV
			gestionArchivosWebDavService.subirArchivoTemporalWebDav(nombreArchivoTemp, contenidoTemporal);

		} catch (Exception e) {
			logger.error("Error al subir el archivo temporal", e.getMessage());
			throw new ErrorGeneracionTareaException("Error al subir el archivo temporal", e);
		}

		response = super.generarTareaGEDO(request);

		this.processEngine.getExecutionService().setVariable(response.getProcessId(),
				Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC, nombreArchivoTemp);

		this.processEngine.getExecutionService().setVariable(response.getProcessId(),
				Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL, request.getNroSadeRectificacion());

		if (logger.isDebugEnabled()) {
			logger.debug("generarTareaConRectificacion(RequestExternalGenerarTareaConRectificacion) - end"); //$NON-NLS-1$
		}
		return response;
	}

	private boolean verificoSiYaFueMarginado(byte[] pdf) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificoSiYaFueMarginado(byte[]) - start"); //$NON-NLS-1$
		}

		try {
			ByteArrayOutputStream pdfConCampoMarg = new ByteArrayOutputStream();
			PdfReader pdfReader = new PdfReader(pdf);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, pdfConCampoMarg, '\0', true);
			AcroFields acroField = pdfStamper.getAcroFields();

			if (acroField.getField(Constantes.TEXTO_MARGINACION) != null) {
				pdfStamper.close();

				if (logger.isDebugEnabled()) {
					logger.debug("verificoSiYaFueMarginado(byte[]) - end"); //$NON-NLS-1$
				}
				return true;
			} else {
				pdfStamper.close();

				if (logger.isDebugEnabled()) {
					logger.debug("verificoSiYaFueMarginado(byte[]) - end"); //$NON-NLS-1$
				}
				return false;
			}

		} catch (Exception e) {
			logger.error("Error al verificar marginacion. " + e.getMessage(), e);
			throw new ErrorGeneracionTareaException("Error al verificar si ya fue marginado");
		}
	}

	@SuppressWarnings("deprecation")
	private byte[] generarCampoMarginacion(byte[] pdf, String textoMarginacion, String textoTitulo,
			RequestExternalGenerarTareaConRectificacion request) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarCampoMarginacion(byte[], String, String, RequestExternalGenerarTareaConRectificacion) - start"); //$NON-NLS-1$
		}

		ByteArrayOutputStream pdfConCampoMarg = new ByteArrayOutputStream();
		try {
			byte[] previewLogo = pdfService.generateLogo();
			byte[] contenidoNuevaPagina = null;
			if (!verificoSiYaFueMarginado(pdf)) {

				PdfReader pdfReader = new PdfReader(pdf);
				PdfReader pdfReader2daHoja = new PdfReader(previewLogo);

				ByteArrayOutputStream temporalCon2daHoja = new ByteArrayOutputStream();
				PdfCopyFields copy = null;

				copy = new PdfCopyFields(temporalCon2daHoja);
				copy.addDocument(pdfReader);
				copy.addDocument(pdfReader2daHoja);
				copy.close();
				pdfReader.close();
				pdfReader2daHoja.close();
				contenidoNuevaPagina = temporalCon2daHoja.toByteArray();

			} else {
				// si ya fue marginado no agrego otra hoja
				contenidoNuevaPagina = pdf;
			}
			PdfReader pdfReaderCon2daHoja = new PdfReader(contenidoNuevaPagina);

			PdfStamper pdfStamper = new PdfStamper(pdfReaderCon2daHoja, pdfConCampoMarg, '\0', true);
			int numeroPagina = pdfStamper.getReader().getNumberOfPages();
			AcroFields acroField = pdfStamper.getAcroFields();
			if (acroField.getField(Constantes.TEXTO_MARGINACION) != null) {
				acroField.removeField(Constantes.TEXTO_MARGINACION);
				acroField.removeField(Constantes.TEXTO_TITULO);
				generarCampoTxtRectifNuevaPagina(numeroPagina, pdfStamper, Constantes.TEXTO_MARGINACION,
						textoMarginacion, Constantes.TEXTO_TITULO, textoTitulo);
			} else {
				generarCampoTxtRectifNuevaPagina(numeroPagina, pdfStamper, Constantes.TEXTO_MARGINACION,
						textoMarginacion, Constantes.TEXTO_TITULO, textoTitulo);
			}
			if (acroField.getBlankSignatureNames().isEmpty()) {
				acroField.removeField(Constantes.SIGNATURE_RECTIFICACION);
			}
			crearSellosDelFirmanteRectificacion(pdfStamper, numeroPagina);
			PdfFormField campo = PdfFormField.createSignature(pdfStamper.getWriter());
			campo.setWidget(new Rectangle(X1_FIRMA_CIERRE, Y1_FIRMA_CIERRE, X1_FIRMA_CIERRE + ANCHO_FIRMA_CIERRE,
					Y1_FIRMA_CIERRE - ALTO_FIRMA_CIERRE), new PdfName(""));
			campo.setFlags(PdfAnnotation.FLAGS_PRINT);
			campo.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));
			campo.setFieldName(Constantes.SIGNATURE_RECTIFICACION);
			campo.setPage(numeroPagina);

			pdfStamper.addAnnotation(campo, numeroPagina);

			pdfStamper.close();

		} catch (Exception e) {
			logger.error("generarCampoMarginacion(byte[], String, String, RequestExternalGenerarTareaConRectificacion)", //$NON-NLS-1$
					e.getMessage());

			throw new ErrorGeneracionTareaException("Error al generar el campo de Marginacion", e);
		}
		byte[] returnbyteArray = pdfConCampoMarg.toByteArray();
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarCampoMarginacion(byte[], String, String, RequestExternalGenerarTareaConRectificacion) - end"); //$NON-NLS-1$
		}
		return returnbyteArray;
	}

	private byte[] generarCampoRectificacion(byte[] pdf, String textoRectificacion, PagContexto pagContexto) {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoRectificacion(byte[], String, PagContexto) - start"); //$NON-NLS-1$
		}

		ByteArrayOutputStream pdfConCampoRec = new ByteArrayOutputStream();

		try {
			PdfReader pdfReader = new PdfReader(pdf);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, pdfConCampoRec, '\0', true);
			int numeroPagina = pdfStamper.getReader().getNumberOfPages();
			AcroFields acroField = pdfStamper.getAcroFields();

			if (acroField.getBlankSignatureNames().isEmpty()) {
				for (int i = 0; i <= numeroPagina; i++) {
					acroField.removeField(Constantes.TEXTO_RECTIFICACION + i);
				}
			}

			if (pdfReader.getAcroFields().getField(Constantes.TEXTO_MARGINACION) != null) {
				numeroPagina = numeroPagina - 1;
			}

			if (pagContexto.toString().equalsIgnoreCase(TODAS)) {

				for (int i = 1; i <= numeroPagina; i++) {

					Rectangle rectangleTxtRectificacion = pdfReader.getPageSize(i);
					float altoPagina = rectangleTxtRectificacion.getHeight();
					float anchoPagina = rectangleTxtRectificacion.getWidth();
					float posicionY = altoPagina * new Float("0.95");
					float posicionX = (anchoPagina / 20);

					generarCampoTxtRectif(i, pdfStamper, Constantes.TEXTO_RECTIFICACION + i, textoRectificacion,
							posicionY, posicionX);
				}
			}
			if (pagContexto.toString().equalsIgnoreCase(PRIMERA)) {

				Rectangle rectangleTxtRectificacion = pdfReader.getPageSize(1);
				float altoPagina = rectangleTxtRectificacion.getHeight();
				float anchoPagina = rectangleTxtRectificacion.getWidth();
				float posicionY = altoPagina * new Float("0.95");
				float posicionX = (anchoPagina / 20);

				generarCampoTxtRectif(1, pdfStamper, Constantes.TEXTO_RECTIFICACION + "1", textoRectificacion,
						posicionY, posicionX);
			}
			if (pagContexto.toString().equalsIgnoreCase(ULTIMA)) {
				Rectangle rectangleTxtRectificacion = pdfReader.getPageSize(numeroPagina);
				float altoPagina = rectangleTxtRectificacion.getHeight();
				float anchoPagina = rectangleTxtRectificacion.getWidth();
				float posicionY = altoPagina * new Float("0.95");
				float posicionX = (anchoPagina / 20);

				generarCampoTxtRectif(numeroPagina, pdfStamper, Constantes.TEXTO_RECTIFICACION + numeroPagina,
						textoRectificacion, posicionY, posicionX);
			}
			pdfStamper.close();
		} catch (Exception e) {
			logger.error("generarCampoRectificacion(byte[], String, PagContexto)", e.getMessage()); //$NON-NLS-1$

			throw new ErrorGeneracionTareaException("Error al generar el campo de Rectificación", e);
		}
		byte[] returnbyteArray = pdfConCampoRec.toByteArray();
		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoRectificacion(byte[], String, PagContexto) - end"); //$NON-NLS-1$
		}
		return returnbyteArray;
	}

	private void generarCampoTxtRectifNuevaPagina(int numeroPagina, PdfStamper pdfStamper, String campoTextoMarg,
			String textoMarginacion, String campoTextoTitulo, String textoTitulo)
			throws IOException, DocumentException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoTxtRectifNuevaPagina(int, PdfStamper, String, String, String, String) - start"); //$NON-NLS-1$
		}

		Font font = new Font();
		font.setSize(10);
		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		Rectangle rectangle = new Rectangle(X1_TEXTO_RECTIF_NUEVA_PAGINA, Y1_TEXTO_RECTIF_NUEVA_PAGINA,
				X1_TEXTO_RECTIF_NUEVA_PAGINA + ANCHO_TEXTO_RECTIF_NUEVA_PAGINA,
				Y1_TEXTO_RECTIF_NUEVA_PAGINA - ALTO_TEXTO_RECTIF_NUEVA_PAGINA);
		TextField textSello;
		textSello = new TextField(pdfStamper.getWriter(), rectangle, campoTextoMarg);
		textSello.setFont(baseFont);
		textSello.setFontSize(11);
		textSello.setText(textoMarginacion);
		textSello.setOptions(BaseField.READ_ONLY | BaseField.MULTILINE);
		pdfStamper.addAnnotation(textSello.getTextField(), numeroPagina);

		// creo el titulo
		BaseFont baseFontTitle = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		Rectangle rectangleTitle = new Rectangle(X1_TEXTO_TITULO_NUEVA_PAGINA, Y1_TEXTO_TITULO_NUEVA_PAGINA,
				X1_TEXTO_TITULO_NUEVA_PAGINA + ANCHO_TEXTO_TITULO_NUEVA_PAGINA,
				Y1_TEXTO_TITULO_NUEVA_PAGINA - ALTO_TEXTO_TITULO_NUEVA_PAGINA);

		TextField textTitle;
		textTitle = new TextField(pdfStamper.getWriter(), rectangleTitle, campoTextoTitulo);
		textTitle.setFont(baseFontTitle);
		textTitle.setFontSize(14);
		textTitle.setText(textoTitulo);
		textTitle.setAlignment(Element.ALIGN_CENTER);
		textTitle.setOptions(BaseField.READ_ONLY | BaseField.MULTILINE);
		pdfStamper.addAnnotation(textTitle.getTextField(), numeroPagina);

		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoTxtRectifNuevaPagina(int, PdfStamper, String, String, String, String) - end"); //$NON-NLS-1$
		}
	}

	private void generarCampoTxtRectif(int numeroPagina, PdfStamper pdfStamper, String campoTextoRect,
			String textoRectif, float posicionY1, float posicionX1) throws DocumentException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoTxtRectif(int, PdfStamper, String, String, float, float) - start"); //$NON-NLS-1$
		}

		Font font = new Font();
		font.setSize(11);
		BaseFont baseFont;
		try {
			baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			Rectangle rectangle = new Rectangle(posicionX1, posicionY1, posicionX1 + ANCHO_TEXTO_RECTIF,
					posicionY1 - ALTO_TEXTO_RECTIF);
			TextField textSello;
			textSello = new TextField(pdfStamper.getWriter(), rectangle, campoTextoRect);
			textSello.setFont(baseFont);
			textSello.setFontSize(11);
			textSello.setText(textoRectif);
			textSello.setOptions(BaseField.READ_ONLY | BaseField.MULTILINE);
			pdfStamper.addAnnotation(textSello.getTextField(), numeroPagina);
		} catch (DocumentException e) {
			logger.error("Error generarCampoTxtRectif - ", e.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("generarCampoTxtRectif(int, PdfStamper, String, String, float, float) - end"); //$NON-NLS-1$
		}
	}

	// METODO QUE AGREGA MARCA DE AGUA
	private byte[] agregarMarcaDeAgua(byte[] contenido, String nombreImagen) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarMarcaDeAgua(byte[], String) - start"); //$NON-NLS-1$
		}

		ByteArrayOutputStream contenidoWatermark = new ByteArrayOutputStream();
		byte[] contenidoPrevisualizacion = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		try {
			reader = new PdfReader(contenido);
			stamp = new PdfStamper(reader, contenidoWatermark, '\0', true);
			PdfContentByte over;
			Image img = Image.getInstance(PdfService.class.getResource(nombreImagen));
			// Alto y ancho de la STAMPA
			float altoStampa = img.getPlainHeight();// 1044
			float anchoStampa = img.getPlainWidth();// 699
			int numeroPagina = reader.getNumberOfPages();
			if (reader.getAcroFields().getField(Constantes.TEXTO_MARGINACION) != null) {
				numeroPagina = numeroPagina - 1;
			}
			for (int i = 1; i <= numeroPagina; i++) {
				Rectangle rectangle = reader.getBoxSize(i, "media");
				// Alto y ancho de la IMAGEN en importar o incorporar
				float altoImagen = rectangle.getHeight();// 768
				float anchoImagen = rectangle.getWidth();// 1366
				// Calculo del porcetanje a escalar
				float porcentajeAEscalar = porcetajeAEscalar(altoImagen, anchoImagen, altoStampa, anchoStampa);
				img.scalePercent(porcentajeAEscalar);
				setearPosicionStampa(img, altoImagen, anchoImagen);
				over = stamp.getOverContent(i);
				over.addImage(img);
			}
		} catch (Exception de) {
			logger.error("Error al agregar marca de agua", de.getMessage());
			throw new ErrorGeneracionTareaException("Error al agregar marca de agua", de);
		} finally {
			if (stamp != null) {
				try {
					stamp.close();
				} catch (Exception e) {
					logger.error("Error al cerrar el pdfStamper", e);
				}
			}
			contenidoPrevisualizacion = contenidoWatermark.toByteArray();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("agregarMarcaDeAgua(byte[], String) - end"); //$NON-NLS-1$
		}
		return contenidoPrevisualizacion;
	}

	// submetodos de marca de agua
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

	private float porcetajeAEscalar(float altoImagen, float anchoImagen, float altoStampa, float anchoStampa) {
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

	private boolean esImagenMasGrande(float altoImagen, float anchoImagen, float altoStampa, float anchoStampa) {
		if (logger.isDebugEnabled()) {
			logger.debug("esImagenMasGrande(float, float, float, float) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = (altoImagen > altoStampa && anchoImagen > anchoStampa);
		if (logger.isDebugEnabled()) {
			logger.debug("esImagenMasGrande(float, float, float, float) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	private boolean esEstampaMasGrande(float altoImagen, float anchoImagen, float altoStampa, float anchoStampa) {
		if (logger.isDebugEnabled()) {
			logger.debug("esEstampaMasGrande(float, float, float, float) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = (altoImagen < altoStampa && anchoImagen < anchoStampa);
		if (logger.isDebugEnabled()) {
			logger.debug("esEstampaMasGrande(float, float, float, float) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	// fin -->submetodos de marca de agua

	private String obtenerPathImagen(NombreImagen nombreImagen) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPathImagen(NombreImagen) - start"); //$NON-NLS-1$
		}

		switch (nombreImagen) {
		case PREVISUALIZACION:
			return Constantes.PREVISUALIZACION;
		case RECTIFICACION_RC:
			return Constantes.RECTIFICACION_RC;
		case ANULACION:
			return Constantes.ANULACION;
		default:
			return Constantes.PREVISUALIZACION;
		}
	}

	/**
	 * Metodo que permite agregar al pdf los campos de sello del firmante
	 */
	public void crearSellosDelFirmanteRectificacion(PdfStamper pdfStamper, int numeroPagina) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearSellosDelFirmanteRectificacion(PdfStamper, int) - start"); //$NON-NLS-1$
		}

		try {
			TextField textCargosFirmanteRec;
			BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			float[] ubicacion = { numeroPagina, 220, 70 };
			ubicacion[2] = ubicacion[2] - ALTO_CAMPO_FIRMA - 10;
			textCargosFirmanteRec = crearCampoSello(pdfStamper, ubicacion, USUARIOA, baseFont);
			ubicacion[2] = textCargosFirmanteRec.getBox().getTop() - 10;
			textCargosFirmanteRec = crearCampoSello(pdfStamper, ubicacion, CARGOA, baseFont);
			ubicacion[2] = textCargosFirmanteRec.getBox().getTop() - 10;
		} catch (Exception e) {
			logger.error("Error al crear los campos sello del firmante de rectificación." + e.getMessage(), e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearSellosDelFirmanteRectificacion(PdfStamper, int) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * SubMetodo de crearSellosDelFirmanteRectificacion.
	 */
	public TextField crearCampoSello(PdfStamper pdfStamper, float[] ubicacion, String nombreCampo, BaseFont baseFont)
			throws DocumentException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCampoSello(PdfStamper, float[], String, BaseFont) - start"); //$NON-NLS-1$
		}

		float x1 = ubicacion[1];
		float y1 = ubicacion[2];
		int numeroPagina = (int) ubicacion[0];
		Rectangle rectangle = new Rectangle(x1, y1, x1 + ANCHO_SELLO, y1 - ALTO_CAMPO_SELLO);
		TextField textSello;
		textSello = new TextField(pdfStamper.getWriter(), rectangle, nombreCampo);
		textSello.setFont(baseFont);
		textSello.setFontSize(6);
		textSello.setOptions(BaseField.READ_ONLY);
		pdfStamper.addAnnotation(textSello.getTextField(), numeroPagina);

		if (logger.isDebugEnabled()) {
			logger.debug("crearCampoSello(PdfStamper, float[], String, BaseFont) - end"); //$NON-NLS-1$
		}
		return textSello;
	}

	/**
	 * Permite saber si ya se firmo el campo signature_rectificacion del pdf de
	 * rectificación.
	 */
	public boolean yaFueRectificadoElDocumento(byte[] data) {
		if (logger.isDebugEnabled()) {
			logger.debug("yaFueRectificadoElDocumento(byte[]) - start"); //$NON-NLS-1$
		}

		boolean salida = false;
		PdfReader pdfReader = null;
		try {
			pdfReader = new PdfReader(data);
			List<String> camposFirma = pdfReader.getAcroFields().getSignatureNames();
			if (camposFirma.contains(Constantes.SIGNATURE_RECTIFICACION)) {
				salida = true;
			}
		} catch (IOException e) {
			logger.error("Error verificando si el documento ya fue rectificado alguna vez." + e.getMessage(), e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("yaFueRectificadoElDocumento(byte[]) - end"); //$NON-NLS-1$
		}
		return salida;
	}

}
