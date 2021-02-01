package com.egoveris.vucfront.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;

public class ViewPDFServlet extends HttpServlet {
	private static final long serialVersionUID = 7538008759679379051L;

	private static final Logger LOG = LoggerFactory.getLogger(ViewPDFServlet.class);

	private static final String PARAM_FILENAME = "nombreArchivo";
	private static final String PARAM_IDDOC = "idDocumento";
	private static final String PARAM_FILE = "archivo";
	private static final String PARAM_IDNOTIF = "idNotif";
	private static final String PARAM_CODDEO = "codDeo";

	private static final String ERROR_DOC_NOT_FOUND = "El documento solicitado no se encuentra disponible en el sistema: ";

	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private NotificacionService notificacionService;
	@Autowired
	private WebDavService webDavService;

	// This code allows the servlet to use spring autowired
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean actionOk = true;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] archivo = null;
		String nombreArchivo = null;
		boolean downloadFile = false;

		// Recently uploaded Document
		if (receivedStringParamExists(request, PARAM_FILENAME)
				&& request.getSession().getAttribute(PARAM_FILE) != null) {
			nombreArchivo = request.getParameter(PARAM_FILENAME);
			archivo = (byte[]) request.getSession().getAttribute(PARAM_FILE);
		}
		// Stored Document
		else if (receivedStringParamExists(request, PARAM_IDDOC)) {
			Encryption encrypt = Encryption.getInstance();
			String documentId = encrypt.decryptParameter(request.getParameter(PARAM_IDDOC));

			DocumentoDTO documento = documentoService.getDocumentById(Long.valueOf(documentId));
			nombreArchivo = documento.getNombreOriginal().substring(0, documento.getNombreOriginal().length() - 4);
			try {
				// In WebDav
				if (documento.getUrlTemporal() != null) {
					archivo = webDavService.getDocumentoByteArray(documento.getUrlTemporal());
				}
				// In DEO
				else {
					archivo = documentoService.getDocumentoGedoArrayBytes(documento.getCodigoSade(),
							documento.getUsuarioCreacion());
				}
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(ERROR_DOC_NOT_FOUND.concat(documento.getCodigoSade()));
				actionOk = false;
			}
		}
		// Notificación
		else if (receivedStringParamExists(request, PARAM_IDNOTIF)) {
			Encryption encrypt = Encryption.getInstance();
			String notificacionId = encrypt.decryptParameter(request.getParameter(PARAM_IDNOTIF));

			NotificacionDTO result = notificacionService.getNotificacionById(Long.valueOf(notificacionId));
			String codigoDocumento = result.getCodSade();

			try {
				archivo = documentoService.getDocumentoGedoArrayBytes(result.getCodSade(), result.getUsuarioCreacion());
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(ERROR_DOC_NOT_FOUND.concat(codigoDocumento));
				actionOk = false;
			}
			nombreArchivo = codigoDocumento;
		}
		// Documento DEO (Doc notificado)
		else if (receivedStringParamExists(request, PARAM_CODDEO)) {
			String[] codigoDeoArray = request.getParameterValues(PARAM_CODDEO)[0].split("-");
			String codDeo = BusinessFormatHelper.armarNumeracionSADE(codigoDeoArray[0],
					Integer.valueOf(codigoDeoArray[1]), Integer.valueOf(codigoDeoArray[2]), codigoDeoArray[3], codigoDeoArray[4],
					false);

			nombreArchivo = "Documento notificado";
			archivo = documentoService.getDocumentoGedoArrayBytes(codDeo, "JQUINTAU");

		} else {
			// parametros inválidos
		}

		if (actionOk) {
			// View or download the Document?
			downloadFile = receivedStringParamExists(request, "dwn") ? true : false;
			String contentDispotion = downloadFile ? "attachment; filename=\"" + nombreArchivo + ".pdf\""
					: "inline; filename=\"" + nombreArchivo + "\"";

			int length = 0;
			if (archivo != null && archivo.length > 0) {
				length = archivo.length;
			}

			try {
				baos.write(archivo);
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(length));
				response.setHeader("Content-Disposition", contentDispotion);
				response.getOutputStream().write(baos.toByteArray(), 0, length);
				response.getOutputStream().flush();
				baos.close();
			} catch (IOException e) {
				LOG.error("Error para visualizar PDF.", e);
			}
		}
	}

	/**
	 * Checks if the String Param exists in the request.
	 * 
	 * @param request
	 * @return True if the param exists in the request, False if not.
	 */
	private boolean receivedStringParamExists(HttpServletRequest request, String stringParam) {
		boolean result = false;
		if (request.getParameter(stringParam) != null && !request.getParameter(stringParam).isEmpty()
				&& !"null".equals(request.getParameter(stringParam))) {
			result = true;
		}
		return result;
	}
}
