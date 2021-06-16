/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.exception.DocumentoOArchivoSinPermisoDeVisualizacionException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.service.ArchivoDeTrabajoService;
import com.egoveris.te.base.service.PermisoVisualizacionService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;



/**
 * @author jnorvert
 *
 */
@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericDocumentoTrabajoComposer extends GenericForwardComposer {

	static final Logger logger = LoggerFactory.getLogger(GenericDocumentoTrabajoComposer.class);

	public List<ArchivoDeTrabajoDTO> listaDocumentoTrabajo = new ArrayList<>();
	public ArchivoDeTrabajoDTO selectedArchivoDeTrabajo;
	private Listbox documentoTrabajoComponent;

	private String codigoExpedienteElectronico;
	protected Task workingTask = null;
	private ExpedienteElectronicoDTO expedienteElectronico;

	@Autowired
	private ArchivoDeTrabajoService archivoDeTrabajoService;

	/**
	 * Defino los servicios que voy a utilizar
	 */

	@WireVariable(ConstantesServicios.WEBDAV_SERVICE)
	private IWebDavService webDavService;
	@WireVariable(ConstantesServicios.ACCESO_WEBDAV_SERVICE)
	private IAccesoWebDavService visualizaDocumentoService;

	private String loggedUsername;
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;

	private PermisoVisualizacionService permisoVisualizacionService;

	private ProcessEngine processEngine;

	private Boolean soloLectura;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
				.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);  
		loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString(); 
		this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));
		try {
			this.codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
					.getAttribute("codigoExpedienteElectronico");
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
		}
		this.expedienteElectronico = expedienteElectronicoService
				.obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);

		List<ArchivoDeTrabajoDTO> archivosDeTrabajo = new ArrayList<>();

		archivosDeTrabajo.addAll(expedienteElectronico.getArchivosDeTrabajo());

		this.listaDocumentoTrabajo = validarListaDeDocumentoDeTrabajo(archivosDeTrabajo);
		documentoTrabajoComponent.setModel(new BindingListModelList(this.listaDocumentoTrabajo, true));
	}

	private List<TrataReparticionDTO> obtenerReparticionesRectora() {
		List<TrataReparticionDTO> reparticionesRestoras = new ArrayList<TrataReparticionDTO>();
		for (int i = 0; i < expedienteElectronico.getTrata().getReparticionesTrata().size(); i++) {
			if (expedienteElectronico.getTrata().getReparticionesTrata().get(i).getReserva() == true) {
				reparticionesRestoras.add(expedienteElectronico.getTrata().getReparticionesTrata().get(i));
			}
		}
		return reparticionesRestoras;
	}

	@SuppressWarnings("unused")
	private Boolean usuarioPerteneceAReparticionRectora(String loggedUsername,
			List<TrataReparticionDTO> reparticionesRestoras) {
		if (reparticionesRestoras.size() != 0) {
			Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

			if (!(datosUsuario == null || datosUsuario.getCodigoReparticion() == null)) {
				for (int i = 0; i < reparticionesRestoras.size(); i++) {
					if (reparticionesRestoras.get(i).getCodigoReparticion().trim()
							.equals(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS)
							|| reparticionesRestoras.get(i).getCodigoReparticion().trim()
									.equals(datosUsuario.getCodigoReparticion()))
						return true;
					break;
				}

			}
		}
		return false;
	}

	private List<ArchivoDeTrabajoDTO> validarListaDeDocumentoDeTrabajo(List<ArchivoDeTrabajoDTO> archivosDeTrabajo) {
		List<ArchivoDeTrabajoDTO> archvosDeTrabajoDefinitivo = new ArrayList<>();

		for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {
			if (archivoDeTrabajo.isDefinitivo()) {
				archvosDeTrabajoDefinitivo.add(archivoDeTrabajo);
			}
		}
		return archvosDeTrabajoDefinitivo;
	}

	/**
	 * Metodo que permite visualizar un archivo de trabajo en el repositorio
	 * 
	 * @autor IES
	 * 
	 *        Servicios utilizados:IAccesoWebDavService
	 *        visualizaDocumentoService Métodos utilizados del
	 *        servicio:visualizarDocumento(String path)
	 * 
	 *        Variables importantes utilizadas:
	 * 
	 * @param String
	 *            path :Cadena que se usa como parámetro en el método
	 *            BufferedInputStream visualizarDocumento(String path) para
	 *            busqueda del Archivo de trabajo, la cual se completa con:
	 * 
	 *            ° pathDocumentoDeTrabajo .- Ubicación del Documento de
	 *            Trabajo. ° nombreSpaceWebDav .- Nombre del Espacio WebDav. °
	 *            archivoDeTrabajo.getNombreArchivo() .- Nombre del Archivo.
	 * 
	 * @param File
	 *            fihero :Fichero utilizado para obtener el tipo de
	 *            fichero(MimeType).
	 * @param String
	 *            tipoFichero : Tipo de fichero del Archivo.
	 * @param BufferedInputStream
	 *            file : Variable que recibe el resultado tipo Inputstream del
	 *            Servicio IAccesoWebDavService visualizaDocumentoService.
	 * 
	 * 
	 * 
	 */
	public void onVisualizarArchivosDeTrabajo() throws Exception {
		try {

			ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;
			File fichero = new File(archivoDeTrabajo.getNombreArchivo());
			String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
			String nombreArchivo = archivoDeTrabajo.getNombreArchivo();

			if (archivoDeTrabajo.getTipoReserva() != null)
				this.puedeDescargarArchivo(archivoDeTrabajo, loggedUsername);

			String nombreSpace = BusinessFormatHelper
					.formarPathWebDavApache(this.expedienteElectronico.getCodigoCaratula());
			String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";
			String fileName = pathDocumentoDeTrabajo + "/" + nombreSpace + "/" + archivoDeTrabajo.getNombreArchivo();

			BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(fileName);
			Filedownload.save(file, tipoFichero, nombreArchivo);
		} catch (DocumentoOArchivoNoEncontradoException e) {

			Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
					Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		} catch (DocumentoOArchivoSinPermisoDeVisualizacionException a) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoDescargados"),
					Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

	}

	private void puedeDescargarArchivo(ArchivoDeTrabajoDTO archivoDeTrabajo, String loggedUsername)
			throws SinPrivilegiosException, ParametroInvalidoConsultaException,
			DocumentoOArchivoSinPermisoDeVisualizacionException {

		ExpedienteElectronicoDTO ee = this.getExpedienteElectronico();

		Task selectedTask = processEngine.getTaskService().createTaskQuery().executionId(ee.getIdWorkflow())
				.uniqueResult();

		boolean consultaArchivo = false;

		if (selectedTask != null) {
			// valida si viene de la consulta o de la tramitacion
			if (this.soloLectura != null) {
				// Si el usuario consulta sin tener en cuenta los permisos de
				// reserva
				if (selectedTask.getAssignee() != null && selectedTask.getAssignee().equals(loggedUsername)) {
					consultaArchivo = false;
				} else {
					consultaArchivo = true;
				}
			} else {
				if (selectedTask.getAssignee() != null && selectedTask.getAssignee().equals(loggedUsername)) {
					consultaArchivo = false;
				} else {
					consultaArchivo = true;
				}
			}
		} else {
			consultaArchivo = true;
		}

		if (consultaArchivo) {
			Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);
			boolean result = permisoVisualizacionService.tienePermisoVisualizacion(user, archivoDeTrabajo);
			if (!result)
				throw new DocumentoOArchivoSinPermisoDeVisualizacionException(null);
		}
	}

	public Listbox getDocumentoTrabajoComponent() {
		return documentoTrabajoComponent;
	}

	public void setDocumentoTrabajoComponent(Listbox documentoTrabajoComponent) {
		this.documentoTrabajoComponent = documentoTrabajoComponent;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public List<ArchivoDeTrabajoDTO> getListaDocumentoTrabajo() {
		return listaDocumentoTrabajo;
	}

	public void setListaDocumentoTrabajo(List<ArchivoDeTrabajoDTO> listaDocumentoTrabajo) {
		this.listaDocumentoTrabajo = listaDocumentoTrabajo;
	}

	public ArchivoDeTrabajoDTO getSelectedArchivoDeTrabajo() {
		return selectedArchivoDeTrabajo;
	}

	public void setSelectedArchivoDeTrabajo(ArchivoDeTrabajoDTO selectedArchivoDeTrabajo) {
		this.selectedArchivoDeTrabajo = selectedArchivoDeTrabajo;
	}

	public String getCodigoExpedienteElectronico() {
		return codigoExpedienteElectronico;
	}

	public void setCodigoExpedienteElectronico(String codigoExpedienteElectronico) {
		this.codigoExpedienteElectronico = codigoExpedienteElectronico;
	}

	public ArchivoDeTrabajoService getArchivoDeTrabajoService() {
		return archivoDeTrabajoService;
	}

	public void setArchivoDeTrabajoService(ArchivoDeTrabajoService archivoDeTrabajoService) {
		this.archivoDeTrabajoService = archivoDeTrabajoService;
	}

}
