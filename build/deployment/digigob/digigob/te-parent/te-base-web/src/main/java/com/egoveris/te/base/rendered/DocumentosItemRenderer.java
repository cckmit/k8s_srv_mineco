
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.DocumentoArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.UtilsDate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;

public class DocumentosItemRenderer
    extends GenericListitemRenderer /* implements ListitemRenderer */
{
  @Autowired
  ProcessEngine processEngine;
  Task workingTask;
  String loggedUsername = new String("");
  private static final Logger logger = LoggerFactory.getLogger("DocumentosItemRenderer");
  protected static String MEMORANDUM = "ME";
  protected static String NOTA = "NO";
  @Autowired
  private DocumentoGedoService documentoGedoService;
  @Autowired
  private TareaParaleloService tareaParaleloService;
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  private TareaParaleloDTO tareaParalelo = null;
  private List<DocumentoArchivoDeTrabajoDTO> archDeTrabajo = null;
  private ExpedienteElectronicoDTO expedienteElectronico;

  private Boolean soloLectura;

  public void render(Listitem item, Object data, int arg1) throws Exception {
    this.expedienteElectronico = ((ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
        .getAttribute("eeAcordeon"));
    this.soloLectura = ((Boolean) Executions.getCurrent().getDesktop()
        .getAttribute("soloLectura"));
    this.tipoDocumentoService = ((TipoDocumentoService) Executions.getCurrent().getDesktop()
        .getAttribute("tipoDocumentoService"));
   
    DocumentoDTO documento = (DocumentoDTO) data;
    // comienzo de cambio: se comenta esta linea, porue al utilizar gedo con
    // iframe no es necesario traer los archivos de trabajo del documento

    Listcell currentCell;
    item.setHflex("min");
    // Numero de Folio
    /**
     * Se obtiene la cantidad total de documentos a listar para poder listar la
     * columna "Orden" con numeración descendente.
     */
    Listbox lista2 = (Listbox) item.getParent();
    int cantDocumentos = lista2.getItemCount();

    /**
     * A la cantidad de documentos total a listar se resta el indice de cada
     * item para que quede de manera descendente.
     */
    loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
        .toString();

    // INICIO A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR
     
    if (this.workingTask == null) {
      workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
    }
    if (this.workingTask != null) { 

      if (workingTask.getActivityName().equals(ConstantesWeb.ESTADO_PARALELO)) {
        // Obtengo la tarea en paralelo
        tareaParalelo = this.tareaParaleloService
            .buscarTareaEnParaleloByIdTask(workingTask.getExecutionId());
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
                      || (documento.getFechaAsociacion().getTime()) >= (workingTask.getCreateTime()
                          .getTime())) {
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

    // Tipo de Documento
    String tipoDocStr = null;
    if (documento.getTipoDocumento() != null) {
      tipoDocStr = documento.getTipoDocumento().getNombre();
    } else if (documento.getTipoDocAcronimo() != null) {
      try {
    	tipoDocumentoService = (TipoDocumentoService)SpringUtil.getBean("tipoDocumentoServiceImpl");
        tipoDocStr = tipoDocumentoService.obtenerTipoDocumento(documento.getTipoDocAcronimo())
            .toString();
      } catch (Exception e) {
        logger.error("render - ", e);
        tipoDocStr = documento.getTipoDocAcronimo();
      }
    }

    Listcell tipoDoc = new Listcell(tipoDocStr);
    tipoDoc.setParent(item);

    Listcell numDoc;

    if (documento.isSubsanadoLimitado()) {
      String numeroSade = documento.getNumeroSade().substring(0, 12) + "XXXX"
          + documento.getNumeroSade().substring(16, 22) + "XXXX";
      // Numero Documento
      numDoc = new Listcell(numeroSade);
    } else {
      numDoc = new Listcell(documento.getNumeroSade());
    }

    numDoc.setParent(item);
    // Referencia
    String motivo = documento.getMotivo();
    Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
    listCellMotivo.setTooltiptext(motivo);
    listCellMotivo.setParent(item);
    // FechaDeAsociacioAlExpediente
    Listcell fechaAsociacion = new Listcell(
        UtilsDate.formatearFechaHora(documento.getFechaAsociacion()));
    fechaAsociacion.setParent(item);
    Listcell fechaCreacion = new Listcell(
        UtilsDate.formatearFechaHora(documento.getFechaCreacion()));
    fechaCreacion.setParent(item);

    currentCell = new Listcell();
    currentCell.setParent(item);

    Hbox hbox = new Hbox();
    Image documentoImage;
    Image documentoNoImage;
    if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
        && documento.getUsuarioSubsanador().equals(loggedUsername))) {
      documentoImage = new Image("/imagenes/page_text.png");
      documentoNoImage = new Image("/imagenes/DocumentoNoDisponible.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoNoImage,
          "onClick=onNoVisualizarDocumento");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoImage,
          "onClick=onVisualizarDocumento");
    } else {
      documentoImage = new Image("/imagenes/page_text_deshabilitado.png");
      documentoNoImage = new Image("/imagenes/DocumentoNoDisponible_deshabilitado.png");
    }

    documentoImage.setTooltiptext("Visualizar documento.");
    documentoNoImage.setTooltiptext("Documento no disponible.");
    Separator separarDocumento = new Separator();
    separarDocumento.setParent(hbox);

    documentoImage.setParent(hbox);

    Image visualizarImage;
    if (!documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
        && documento.getUsuarioSubsanador().equals(loggedUsername))) {
      visualizarImage = new Image("/imagenes/download_documento.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
          "onClick=onDescargarDocumento");
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

    // *****
    Separator separar2 = new Separator();
    Image runImage = new Image("/imagenes/decline.png");
    runImage.setTooltiptext("Desvincular documento.");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage, "onClick=onDesasociarDocumentos");
    separar2.setParent(hbox);
    runImage.setParent(hbox);

    if (!documento.getDefinitivo() && (soloLectura == null || soloLectura != null && !soloLectura)
        && !documento.isSubsanado()) {

      runImage.setVisible(true);
    } else {
      runImage.setVisible(false);
    }
    hbox.setParent(currentCell);
  }

  private boolean estasTramitandoExpediente(Task wt, ExpedienteElectronicoDTO ee) {
    if ((wt != null) && (wt.getExecutionId().equals(ee.getIdWorkflow())))
      return true;
    return false;
  }

  private String motivoParseado(String motivo) {
    int cantidadCaracteres = 28;
    if (StringUtils.isBlank(motivo)) {
    	motivo = "";
    }
    String substringMotivo;
    if (motivo.length() > cantidadCaracteres) {
      substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
    } else {
      substringMotivo = motivo.substring(0, motivo.length());
    }
    return substringMotivo;
  }
}
