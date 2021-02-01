package com.egoveris.te.base.helper;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoGedoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.base.util.Zip;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


/**
 * Helper para documentos en nueva ventana de tramitacion. Partes del codigo
 * original se encuentran en GenericDocumentoComposer.java
 * 
 * @author everis
 *
 */
public class DocumentosHelper {
	
	private static final String NOMBRE_TXT_FILTRO = "Filtro.txt";
	
	private ExpedienteElectronicoDTO expediente;
	
	private DocumentoGedoService documentoGedoDAO;
	private IExternalConsultaDocumentoServiceExt consultaDocumentoService = (IExternalConsultaDocumentoServiceExt) SpringUtil
			.getBean(ConstantesServicios.CONSULTA_DOC_EXTERNAL_SERVICE);
	
	public DocumentosHelper(ExpedienteElectronicoDTO expediente) {
		setExpediente(expediente);
	}
	
	public void visualizarDocumento(DocumentoDTO documento) throws SinPrivilegiosException,
			ParametroInvalidoConsultaException, DocumentoNoExisteException, ErrorConsultaDocumentoException {
		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		
		puedeDescargarDocumento(documento, loggedUsername);
		
		Map<String, Object> args = new HashMap<>();
		args.put("assignee", loggedUsername);
		args.put("nombreArchivo", documento.getNumeroSade());
		
        Window visualizarWindow = (Window) Executions.createComponents("/consultas/ppVisualizarGedo.zul", null, args);
        visualizarWindow.doModal();
	}
	
	public void descargarDocumento(DocumentoDTO documento) throws SinPrivilegiosException,
			ParametroInvalidoConsultaException, DocumentoNoExisteException, ErrorConsultaDocumentoException {
		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);

		puedeDescargarDocumento(documento, loggedUsername);

		byte[] content = buscarContenidoDeDocumento(documento, loggedUsername);
		File fichero = new File(documento.getNombreArchivo());
		String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
		String nombreArchivo = documento.getNombreArchivo();
		
		Filedownload.save(content, tipoFichero, nombreArchivo);
	}
	
	/**
	 * Método descargar para DocumentoSinPaseComposer, DocumentoConPaseComposer,
	 * DocumentosFiltroComposer
	 * 
	 * @param nombre
	 *            Nombre del archivo ZIP
	 * @param listaDocumentos
	 *            listado de documentos que van adentro del archivo zip
	 * @param tipoDescarga
	 *            Tipo de descarga que se va a realizar (ejemplos, Sin pase, con
	 *            pase, filtro, sale de Constantes.java de ee-web)
	 * @param contenido
	 *            se crea un archivo filtro.txt con la información de contenido,
	 *            este archivo se coloca dentro del archivo zip
	 * @throws Exception 
	 */
	public void descargarTodos(String nombre, List<DocumentoDTO> listaDocumentos, String tipoDescarga, byte[] contenido)
			throws Exception {
		Collections.reverse(listaDocumentos);
		
		try {
			Zip zip = new Zip(nombre + " " + tipoDescarga);
			
			if (contenido != null) {
				zip.agregarEntrada(contenido, NOMBRE_TXT_FILTRO);
			}
	
			String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(ConstantesWeb.SESSION_USERNAME);
			
			StringBuilder nrosDocNoDescargados = new StringBuilder("");
			StringBuilder docsNoDisponibles = new StringBuilder("");
			
			int i = 0;
			
			for (DocumentoDTO documento : listaDocumentos) {
				try {
					puedeDescargarDocumento(documento, loggedUsername);
				} catch (SinPrivilegiosException e) {
					if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
							&& documento.getUsuarioSubsanador().equals(loggedUsername))) {
						nrosDocNoDescargados.append(documento.getNumeroSade()).append(", ");
					} else {
						nrosDocNoDescargados.append(documento.getNumeroSade().substring(0, 12)).append("XXXX").append(documento.getNumeroSade().substring(16, 22)).append("XXXX").append(", ");
					}
	
					continue;
				}
				
				String numeroSadeConEspacio = documento.getNumeroSade();
				byte[] content = null;
				
				try {
					content = this.buscarContenidoDeDocumento(documento, loggedUsername);
				} catch (Exception e) {
					docsNoDisponibles.append(numeroSadeConEspacio).append(" ");
				}
				
				if (content != null) {
					i++;
					zip.agregarEntrada(content, Utils.toStringValue(i) + " - " + documento.getNombreArchivo());
				}
			}
			
			Filedownload.save(zip.getZip(), null, nombre + " " + tipoDescarga + ".zip");
			
			if (!"".equals(nrosDocNoDescargados.toString())) {
				Messagebox.show(
						Labels.getLabel("ee.tramitacion.documentosNoDescargados",
								new String[] { nrosDocNoDescargados.substring(0, nrosDocNoDescargados.length() - 2) }),
						Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.INFORMATION);
			}
			if (!"".equals(docsNoDisponibles.toString())) {
				Messagebox.show(
						Labels.getLabel("ee.tramitacion.documentosNoDisponibles",
								new String[] { docsNoDisponibles.substring(0, docsNoDisponibles.length() - 2) }),
						Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			Collections.reverse(listaDocumentos);
		}
	}
	
	// - METODOS PRIVATE -
	
	/**
	 * Valida si es posible descargar el documento GEDO, verifica si es
	 * reservado y si el usuario tienen permisos para ver
	 * documentos confidenciales
	 * 
	 * @param documento
	 * @param loggedUsername
	 * @throws SinPrivilegiosException
	 * @throws ParametroInvalidoConsultaException
	 * @throws DocumentoNoExisteException
	 * @throws ErrorConsultaDocumentoException
	 */
	private void puedeDescargarDocumento(DocumentoDTO documento, String loggedUsername) throws SinPrivilegiosException,
			ParametroInvalidoConsultaException, DocumentoNoExisteException, ErrorConsultaDocumentoException {
		
		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		request.setNumeroDocumento(documento.getNumeroSade());
		request.setUsuarioConsulta(loggedUsername);
		
		if (calcularLlamadoDeReserva(documento)) {
			consultaDocumentoService.consultarDocumentoPorNumero(request);
		}
		else {
			consultaDocumentoService.consultarPorNumeroReservaTipoDoc(request);
		}
		
		verificarDocumentoSubsanado(documento, loggedUsername);
	}
	
	private boolean calcularLlamadoDeReserva(DocumentoDTO documento) {
		ExpedienteElectronicoDTO ee = getExpediente();
		
		boolean retorno = false; 
		
		if (!ee.getEsReservado()) {
			retorno = true;
		}
		else {
			DocumentoGedoDTO docGedo = documentoGedoDAO.obtenerDocumentoGedoPorNumeroEstandar(documento.getNumeroSade());
			
			if (!"PARCIAL".equals(ee.getTrata().getTipoReserva().getTipoReserva())
					&& !"TOTAL".equals(ee.getTrata().getTipoReserva().getTipoReserva())
					&& documento.getDefinitivo() && ee.getEsReservado()
					&& (docGedo != null && docGedo.esReservado())){
				retorno = true;
			}
		}
		
		return retorno;
	}
	
	private void verificarDocumentoSubsanado(DocumentoDTO documento, String loggedUsername)
			throws SinPrivilegiosException {
		
		if (documento.isSubsanado() && !documento.getUsuarioSubsanador().equals(loggedUsername)) {
			throw new SinPrivilegiosException(loggedUsername);
		}
	}
	
	private byte[] buscarContenidoDeDocumento(DocumentoDTO documento, String loggedUsername)
			throws ParametroInvalidoConsultaException, DocumentoNoExisteException, SinPrivilegiosException,
			ErrorConsultaDocumentoException {
		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		
		request.setNumeroDocumento(documento.getNumeroSade());
		request.setUsuarioConsulta(loggedUsername);
		return consultaDocumentoService.consultarDocumentoPdf(request);
	}
	
	// Getters - setters
	
	public ExpedienteElectronicoDTO getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteElectronicoDTO expediente) {
		this.expediente = expediente;
	}
}
