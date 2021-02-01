package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.dao.DocumentoTemporalDao;
import com.egoveris.deo.base.model.ProcesoLog;
import com.egoveris.deo.base.repository.ProcesoLogRepository;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.BorradoTemporal;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.LimpiezaWebdav;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.DocumentoTemporalDTO;
import com.egoveris.deo.model.model.ProcesoLogDTO;
import com.egoveris.deo.model.model.RegistroTemporalDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dozer.Mapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class BorradoTemporalImpl implements BorradoTemporal {

	private static final Logger LOGGER = LoggerFactory.getLogger(BorradoTemporalImpl.class);
	private static final String FFNET = "Se ha obtenido la ruta del documento alojado en filenet";

	@Autowired
	private DocumentoTemporalDao documentoTemporalDao;

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ArchivoDeTrabajoService archivoDeTrabajoService;
	@Autowired
	private DocumentoAdjuntoService documentoAdjuntoService;
	@Autowired
	@Qualifier("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;
	@Autowired
	private LimpiezaWebdav limpiezaWebdav;
	@Autowired
	private ProcesoLogRepository procesoLogRepo;
	@Value("${app.sistema.gestor.documental}")
	private String gestorDocumental;
	@Autowired
	@Qualifier("deoCoreMapper")
	private Mapper mapper;

	@Override
	public synchronized void ejecutarBorrado(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ejecutarBorrado(String) - start"); //$NON-NLS-1$
		}

		DocumentoTemporalDTO documentoTemporal;

		try {
			// 5.1- Se obtienen los documentos que seran borrados de Webdav
			documentoTemporal = this.obtenerDocumentosABorrar(workflowId);
			LOGGER.info("Se han obtenido los datos necesarios para el borrado");

			// 5.2- Borrar registros de la BBDD
			this.borrarRegistrosTemporales(workflowId);
			LOGGER.info("Se han borrado los registros de las tablas de GEDO");

			// 5.3- Borrado de Archivos Temporales del repositorio
			this.borrarDocumentosTemporales(documentoTemporal);
			LOGGER.info("Se han borrado los archivos de Webdav");

		} catch (ApplicationException e) {
			LOGGER.error("Ha ocurrido un error al procesar el documento: " + workflowId + " - " + e.getMessage(), e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ejecutarBorrado(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Persistencia de un determinado proceso en la tabla de logs para OK o para
	 * ERROR.
	 * 
	 * @param workflowId
	 * @param numeroSade
	 * @param descripcion
	 * @param proceso
	 * @param estado
	 * @param reintento
	 */
	@Override
	public ProcesoLogDTO guardarLog(String workflowId, String descripcion, String proceso, String estado,
			String sistemaOrigen) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarLog(String, String, String, String, String) - start"); //$NON-NLS-1$
		}

		ProcesoLogDTO log = new ProcesoLogDTO();

		log.setDescripcion(descripcion);
		log.setEstado(estado);
		log.setFechaCreacion(new Date());
		log.setProceso(proceso);
		log.setSistemaOrigen(sistemaOrigen);
		log.setWorkflowId(workflowId);

		try {
			procesoLogRepo.save(this.mapper.map(log, ProcesoLog.class));
		} catch (ApplicationException e) {
			LOGGER.error("Ha ocurrido un error al persistir el log del proceso: " + proceso + " - " + e.getMessage(),
					e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("guardarLog(String, String, String, String, String) - end"); //$NON-NLS-1$
		}
		return log;
	}

	@Override
	public void cambiarEstadoLog(ProcesoLogDTO log, String estado) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("cambiarEstadoLog(ProcesoLogDTO, String) - start"); //$NON-NLS-1$
		}

		try {
			log.setEstado(estado);
			procesoLogRepo.save(this.mapper.map(log, ProcesoLog.class));
		} catch (ApplicationException e) {
			LOGGER.error("Ha ocurrido un error al cambiar el estado del log del proceso" + log.getProceso() + " - "
					+ e.getMessage(), e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("cambiarEstadoLog(ProcesoLogDTO, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public List<RegistroTemporalDTO> obtenerRegistrosABorrar(Date fechaLimiteDocumentosTemporales) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerRegistrosABorrar(Date) - start"); //$NON-NLS-1$
		}

		List<RegistroTemporalDTO> listDocumentoTemporal = null;
		try {
			listDocumentoTemporal = documentoTemporalDao.obtenerRegistrosABorrar(fechaLimiteDocumentosTemporales);
		} catch (ApplicationException e) {
			LOGGER.error(
					"Ha ocurrido un error al obtener los archivos temporales que deberan borrarse del repositorio - ",
					e.getMessage());
			throw e;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerRegistrosABorrar(Date) - end"); //$NON-NLS-1$
		}
		return listDocumentoTemporal;
	}

	@Override
	public DocumentoTemporalDTO obtenerDocumentosABorrar(String workflowId) {
		DocumentoTemporalDTO documentoTemporal = new DocumentoTemporalDTO();
		try {

			// 1- Obtener ruta SADE
			String rutaSade = (String) processEngine.getExecutionService().getVariable(workflowId,
					"archivoTemporalFirma");
			String idGuardaDocumental = (String) processEngine.getExecutionService().getVariable(workflowId,
					"idGuardaDocumental");
			if (rutaSade != null && !"".equals(rutaSade) && !rutaSade.contains("temporal")) {
				documentoTemporal.setRutaDocumentoSade(rutaSade);
				LOGGER.debug("Se ha obtenido la ruta del documento alojado en la carpeta SADE");
			} else {
				if (idGuardaDocumental != null) {
					documentoTemporal.setIdGuardaDocumentalSade(idGuardaDocumental);
					LOGGER.debug(FFNET);
				} else {
					LOGGER.debug("No existen documentos alojados en el repositorio");
				}
			}

			// 2- Obtener ruta Archivo De Trabajo
			// Se trae solo el path del primer elemento dado que la direccion
			// donde
			// deberian estar alojados
			// los documentros deberia ser la misma para todos los Archivos de
			// Trabajo
			List<ArchivoDeTrabajoDTO> listArchivosDeTrabajo = archivoDeTrabajoService
					.buscarArchivosDeTrabajoPorProceso(workflowId);

			if (listArchivosDeTrabajo != null && !listArchivosDeTrabajo.isEmpty()) {
				String rutaArchivoDeTrabajo = listArchivosDeTrabajo.get(0).getPathRelativo();
				documentoTemporal.setListaDocumentosDeTrabajo(new ArrayList<String>());

				for (ArchivoDeTrabajoDTO archivo : listArchivosDeTrabajo) {
					if (archivo.getIdGuardaDocumental() != null) {
						documentoTemporal.getListaDocumentosDeTrabajo().add(idGuardaDocumental);
						LOGGER.debug(FFNET);
					} else {
						if (rutaArchivoDeTrabajo != null && !"".equals(rutaArchivoDeTrabajo)) {
							documentoTemporal.setRutaDocumentoDeTrabajo(rutaArchivoDeTrabajo);
							LOGGER.debug(
									"Se ha obtenido la ruta del documento alojado en la carpeta ARCHIVOS_DE_TRABAJO");
						} else {
							documentoTemporal.setRutaDocumentoDeTrabajo(null);
							LOGGER.debug("No existen documentos alojados en la carpeta ARCHIVOS_DE_TRABAJO de Webdav");
						}
					}
				}
			} else {
				documentoTemporal.setRutaDocumentoDeTrabajo(null);
				LOGGER.debug("No existen archivos de trabajo asociados");
			}

			// 3- Obtener ruta Documentos Adjuntos
			List<DocumentoAdjuntoDTO> listDocumentoAdjunto = documentoAdjuntoService
					.buscarArchivosDeTrabajoPorProceso(workflowId);

			if (listDocumentoAdjunto != null && !listDocumentoAdjunto.isEmpty()) {
				String rutaDocumentoAdjunto = listDocumentoAdjunto.get(0).getPathRelativo();
				String idGuardaDocumentalAdjunto = listDocumentoAdjunto.get(0).getIdGuardaDocumental();
				if (rutaDocumentoAdjunto != null && !"".equals(rutaDocumentoAdjunto)
						&& !rutaDocumentoAdjunto.contains("temporal")) {
					documentoTemporal.setRutaDocumentoAdjunto(rutaDocumentoAdjunto);
					LOGGER.debug(
							"Se ha obtenido la ruta del documento alojado en la carpeta DOCUMENTO_ADJUNTO de Webdav");
				} else {
					documentoTemporal.setRutaDocumentoAdjunto(null);
				}

				if (idGuardaDocumentalAdjunto != null) {
					documentoTemporal.setIdGuardaDocumentalAdjunto(idGuardaDocumentalAdjunto);
					LOGGER.debug(FFNET);
				}
			} else {
				documentoTemporal.setRutaDocumentoAdjunto(null);
				LOGGER.debug("No existen documentos adjuntos");
			}

		} catch (ApplicationException e) {
			LOGGER.error("Ha ocurrido un error al obtener las rutas de los documentos - ", e.getMessage());
			throw e;
		}

		return documentoTemporal;
	}

	@Override
	public String subirRegistrosABorrarLimpiezaTemporales(List<RegistroTemporalDTO> listRegistrosTemporalesABorrar) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subirRegistrosABorrarLimpiezaTemporales(List<RegistroTemporalDTO>) - start"); //$NON-NLS-1$
		}

		File file = null;
		String nameFile = null;
		FileWriter w = null;
		try {
			Long millisecondsTime = new Date().getTime();
			nameFile = "Registros_A_Borrar_Limpieza_Temporales-" + millisecondsTime.toString() + ".csv";
			file = new File(nameFile);
			w = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.write(
					"jbpm4_execution.dbid_|jbpm4_execution.id_|jbpm4_execution.instance_|jbpm4_execution.activityname_|"
							+ "jbpm4_task.create_|jbpm4_task.assignee_|jbpm4_execution.MOTIVO\n");
			for (RegistroTemporalDTO documentoTemporal : listRegistrosTemporalesABorrar) {
				wr.append(documentoTemporal.getDbid() + "|" + documentoTemporal.getWorkflowid() + "|"
						+ documentoTemporal.getInstance() + "|" + documentoTemporal.getActivityName() + "|"
						+ documentoTemporal.getFechaCreacionTask() + "|" + documentoTemporal.getAssignee() + "|"
						+ documentoTemporal.getMotivo() + "\n");
			}
			subirALimpiezaTemporales(IOUtils.toByteArray(new FileInputStream(file)), nameFile);
			wr.close();
			bw.close();

		} catch (IOException e) {
			file = null;
			LOGGER.error(
					"Se ha producido un error al momento de generar el archivo Registros_A_Borrar_Limpieza_Temporales-XXX.csv",
					e);
		} finally {
			closeStatement(w);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subirRegistrosABorrarLimpiezaTemporales(List<RegistroTemporalDTO>) - end"); //$NON-NLS-1$
		}
		return nameFile;
	}

	private void closeStatement(FileWriter w) {
		if (w != null) {
			try {
				w.close();
			} catch (IOException e) {
				LOGGER.error("Error al cerrar connection", e);

			}
		}

	}

	@Override
	public void subirALimpiezaTemporales(byte[] file, String nameFile) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subirALimpiezaTemporales(byte[], String) - start"); //$NON-NLS-1$
		}

		try {

			// WEBDAV
			gestionArchivosWebDavService.subirDocumentoLimpiezaTemporalesWebDav(nameFile, file);

		} catch (ApplicationException e) {
			LOGGER.error(
					"Se ha producido un error al subir el archivo Registros_A_Borrar_Limpieza_Temporales-XXX.csv al repositorio",
					e.getMessage());
			throw e;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subirALimpiezaTemporales(byte[], String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void borrarRegistrosTemporales(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarRegistrosTemporales(String) - start"); //$NON-NLS-1$
		}

		try {
			documentoTemporalDao.borrarDocumentosTemporales(workflowId);
			processEngine.getExecutionService().endProcessInstance(workflowId, ProcessInstance.STATE_ENDED);
		} catch (ApplicationException e) {
			LOGGER.error("Error borrando documentos temporales", e.getMessage());
			throw e;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarRegistrosTemporales(String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void borrarDocumentosTemporales(DocumentoTemporalDTO documentoTemporal) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarDocumentosTemporales(DocumentoTemporalDTO) - start"); //$NON-NLS-1$
		}

		try {

			// WEBDAV
			if (documentoTemporal.getRutaDocumentoSade() != null) {
				limpiezaWebdav.limpiezaSADE(documentoTemporal.getRutaDocumentoSade());
			}

			// WEBDAV
			if (documentoTemporal.getRutaDocumentoAdjunto() != null) {
				limpiezaWebdav.limpiezaDocumentoAdjunto(documentoTemporal.getRutaDocumentoAdjunto());
			}

			if (documentoTemporal.getRutaDocumentoDeTrabajo() != null) {
				limpiezaWebdav.limpiezaDocumentoDeTrabajo(documentoTemporal.getRutaDocumentoDeTrabajo());
			}

		} catch (ApplicationException e) {
			LOGGER.error("Error borrando documentos temporales", e.getMessage());
			throw e;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarDocumentosTemporales(DocumentoTemporalDTO) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void limpiarCarpetasTemporalesWebdav() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("limpiarCarpetasTemporalesWebdav() - start"); //$NON-NLS-1$
		}

		try {
			limpiezaWebdav.limpiezaCarpetasVacias();
		} catch (ApplicationException e) {
			LOGGER.error("Error al limpiar carpetas temp", e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("limpiarCarpetasTemporalesWebdav() - end"); //$NON-NLS-1$
		}
	}

	@Override
	public List<RegistroTemporalDTO> messageRequestARegistroTemporal(Date fechaLimiteDocumentosTemporales) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("messageRequestARegistroTemporal(Date) - start"); //$NON-NLS-1$
		}

		List<RegistroTemporalDTO> returnList = documentoTemporalDao
				.messageRequestARegistroTemporal(fechaLimiteDocumentosTemporales);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("messageRequestARegistroTemporal(Date) - end"); //$NON-NLS-1$
		}
		return returnList;
	}
}
