
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.SubsanacionDeDocumentosComposer;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.DocumentoArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoSubsanableDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.UtilsDate;

import java.awt.Button;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;

public class SubsanacionDeDocumentosItemRenderer implements ListitemRenderer {
	@Autowired
	ProcessEngine processEngine;
	Task workingTask;
	String loggedUsername = new String("");

	protected static String MEMORANDUM = "ME";
	protected static String NOTA = "NO";
	@Autowired
	private DocumentoGedoService documentoGedoService;
	@Autowired
	private TareaParaleloService tareaParaleloService;
	private TipoDocumentoService tipoDocumentoService;
	private TareaParaleloDTO tareaParalelo = null;
	private List<DocumentoArchivoDeTrabajoDTO> archDeTrabajo = null;

	@Autowired
	private SubsanacionDeDocumentosComposer composer;

	@Autowired
	private Button aceptar;

	public SubsanacionDeDocumentosItemRenderer(SubsanacionDeDocumentosComposer composer) {
		super();
		this.composer = composer;
	}

	public SubsanacionDeDocumentosItemRenderer() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(Listitem item, Object data, int arg1) throws Exception {

		@SuppressWarnings("unchecked")
		final Map<String, DocumentoSubsanableDTO> mapSubsanar = ((Map<String, DocumentoSubsanableDTO>) Executions.getCurrent()
				.getDesktop().getAttribute("mapSubsanacion"));

		this.tipoDocumentoService = ((TipoDocumentoService) Executions.getCurrent().getDesktop()
				.getAttribute("tipoDocumentoService"));

		archDeTrabajo = null;
		final DocumentoDTO documento = (DocumentoDTO) data;
		// comienzo de cambio: se comenta esta linea, porue al utilizar gedo con
		// iframe no es necesario traer los archivos de trabajo del documento

		Listcell currentCell;
		item.setHflex("min");
		/**
		 * Se obtiene la cantidad total de documentos a listar para poder listar
		 * la columna "Orden" con numeración descendente.
		 */
		Listbox lista2 = (Listbox) item.getParent();
		int cantDocumentos = lista2.getItemCount();

		/**
		 * A la cantidad de documentos total a listar se resta el indice de cada
		 * item para que quede de manera descendente.
		 */
		loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

		// INICIO A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR
		 
		if (this.workingTask == null) {
			workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
		}
		if (this.workingTask != null) {
			 
			if (workingTask.getActivityName().equals(ConstantesWeb.ESTADO_PARALELO)) {
				// Obtengo la tarea en paralelo
				tareaParalelo = this.tareaParaleloService.buscarTareaEnParaleloByIdTask(workingTask.getExecutionId());
				if (tareaParalelo != null) {

					if (!loggedUsername.equals("")) {
						if (tareaParalelo.getUsuarioOrigen().equals(loggedUsername)) {// ¿soy
																						// el
																						// usuario
																						// propietario?
							if (!documento.getDefinitivo()) {
								if (!documento.getNombreUsuarioGenerador().equals(loggedUsername)) {
									if ((documento.getFechaCreacion().getTime()) >= (workingTask.getCreateTime()
											.getTime())
											|| (documento.getFechaAsociacion().getTime()) >= (workingTask
													.getCreateTime().getTime())) {
										item.setStyle("background-color:" + ConstantesWeb.COLOR_ILUMINACION_FILA);
									}
								}
							}
						}
					} else {
						throw new TeException("No se ha podido recuperar el usuario loggeado.", null);
					}
				} else {
					throw new TeException("No se ha podido recuperar la tarea.", null);
				}
			}
		}
		// FIN A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR

		if (documento.isSubsanado() || documento.isSubsanadoLimitado()) {
			item.setStyle("background-color:" + "#C0C0C0");
		}

		int numFolio = cantDocumentos - (item.getIndex());
		String numeroFolio = Integer.toString(numFolio);
		Listcell folio = new Listcell(numeroFolio);
		folio.setHflex("min");
		folio.setParent(item);

		String tipoDocStr = null;
		if (documento.getTipoDocumento() != null) {
			tipoDocStr = documento.getTipoDocumento().getNombre();
		} else if (documento.getTipoDocAcronimo() != null) {
			tipoDocStr = tipoDocumentoService.obtenerTipoDocumento(documento.getTipoDocAcronimo()).getNombre();
		}

		Listcell tipoDoc = new Listcell(tipoDocStr);
		tipoDoc.setParent(item);
		Listcell numDoc;

		if (documento.isSubsanadoLimitado()) {
			String numeroSade = documento.getNumeroSade().substring(0, 12) + "XXXX"
					+ documento.getNumeroSade().substring(16, 22) + "XXXX";
			numDoc = new Listcell(numeroSade);
		} else {
			numDoc = new Listcell(documento.getNumeroSade());
		}

		numDoc.setParent(item);
		String motivo = documento.getMotivo();
		Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
		listCellMotivo.setTooltiptext(motivo);
		listCellMotivo.setParent(item);
		Listcell fechaAsociacion = new Listcell(UtilsDate.formatearFechaHora(documento.getFechaAsociacion()));
		fechaAsociacion.setParent(item);
		Listcell fechaCreacion = new Listcell(UtilsDate.formatearFechaHora(documento.getFechaCreacion()));
		fechaCreacion.setParent(item);

		final Checkbox subsanarCheckbox = new Checkbox();
		subsanarCheckbox.setChecked(documento.isSubsanado());
		if (documento.isSubsanado()) {
			subsanarCheckbox.setDisabled(true);
		} else {
			subsanarCheckbox.setDisabled(false);
		}

		Listcell subsanar = new Listcell();
		subsanarCheckbox.setParent(subsanar);
		subsanar.setParent(item);

		subsanarCheckbox.addEventListener("onCheck", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				Checkbox checkBox = (Checkbox) event.getTarget();
				mapSubsanar.get(documento.getNumeroSade()).setSubsanado(checkBox.isChecked());
			}
		});

		currentCell = new Listcell();
		currentCell.setParent(item);

		Hbox hbox = new Hbox();

		Image documentoImage;
		Image documentoNoImage;
		if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
				&& documento.getUsuarioSubsanador().equals(loggedUsername))) {
			documentoImage = new Image("/imagenes/page_text.png");
			documentoNoImage = new Image("/imagenes/DocumentoNoDisponible.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoNoImage, "onClick=onNoVisualizarDocumento");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoImage, "onClick=onVisualizarDocumento");
		} else {
			documentoImage = new Image("/imagenes/page_text_deshabilitado.png");
			documentoNoImage = new Image("/imagenes/DocumentoNoDisponible_deshabilitado.png");
		}

		documentoImage.setTooltiptext("Visualizar documento.");
		documentoNoImage.setTooltiptext("Documento no disponible.");
		Separator separarDocumento = new Separator();
		separarDocumento.setParent(hbox);
		if (MEMORANDUM.equals(documento.getNumeroSade().substring(0, 2))
				|| NOTA.equals(documento.getNumeroSade().substring(0, 2))) {
			documentoNoImage.setParent(hbox);
		} else {
			documentoImage.setParent(hbox);
		}

		Image visualizarImage;
		if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
				&& documento.getUsuarioSubsanador().equals(loggedUsername))) {
			visualizarImage = new Image("/imagenes/download_documento.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage, "onClick=onDescargarDocumento");
		} else {
			visualizarImage = new Image("/imagenes/download_documento_deshabilitado.png");
		}

		visualizarImage.setTooltiptext("Descargar el documento a su disco local.");
		visualizarImage.setParent(hbox);

		// Popup
		Popup popup = new Popup();
		popup.setId("id_" + documento.getNumeroSade());

		Listbox lista = new Listbox();
		lista.setWidth("400px");
		Listhead listHead = new Listhead();

		Listheader listheader = new Listheader("Usuario Generador");
		Listheader listheader1 = new Listheader("Número Especial");
		Listheader listheader2 = new Listheader("Usuario Subsanador");
		listheader.setParent(listHead);
		listheader1.setParent(listHead);
		listheader2.setParent(listHead);
		listHead.setParent(lista);
		Listitem itemPopup = new Listitem();
		Listcell celdaUsuarioGenerador = new Listcell(documento.getNombreUsuarioGenerador());
		celdaUsuarioGenerador.setParent(itemPopup);
		Listcell celdaNumeroEspecial = new Listcell(documento.getNumeroEspecial());
		celdaNumeroEspecial.setParent(itemPopup);
		Listcell celdaUsuarioLimitador = new Listcell(documento.getUsuarioSubsanador());
		celdaUsuarioLimitador.setParent(itemPopup);
		itemPopup.setParent(lista);
		lista.setParent(popup);
		popup.setParent(hbox);

		Image imagen = new Image("/imagenes/edit-find.png");
		imagen.setPopup(popup.getId());
		imagen.setTooltiptext("Mas Datos");
		Separator separar = new Separator();
		separar.setParent(hbox);
		imagen.setParent(hbox);

		hbox.setParent(currentCell);
	}

	private String motivoParseado(String motivo) {
		int cantidadCaracteres = 28;
		String substringMotivo;
		if (motivo.length() > cantidadCaracteres) {
			substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
		} else {
			substringMotivo = motivo.substring(0, motivo.length());
		}
		return substringMotivo;
	}
}
