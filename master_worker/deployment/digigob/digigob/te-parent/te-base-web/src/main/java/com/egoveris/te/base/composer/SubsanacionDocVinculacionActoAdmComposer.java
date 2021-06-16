package com.egoveris.te.base.composer;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoSubsanableDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoDAO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IMailService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;


/**
 * @author dpadua
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SubsanacionDocVinculacionActoAdmComposer extends EEGenericForwardComposer {

  private static Logger logger = LoggerFactory
      .getLogger(SubsanacionDocVinculacionActoAdmComposer.class);
  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  @Autowired
  private IMailService iMailService;

  @Autowired
  private Component acordeon;

  protected ProcessEngine processEngine;

  public static String MEMORANDUM = "ME";
  public static String NOTA = "NO";
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";

  private TipoDocumentoDAO tipoDocumentoDAO;
  private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;

  private String loggedUsername;

  // windows
  private Window subsanacionDocVinculacionActoAdmWindow;
  @Autowired
  private Window subsanacionDeDocumentosWindow;

  // button
  @Autowired
  private Button vincularDocumento;
  @Autowired
  private Button cancelar;

  // bandbox
  @Autowired
  protected Bandbox tiposDocumentoActoAdministrativoBandbox;
  @Autowired
  protected Bandbox reparticionBusquedaDocumento;

  // intbox
  @Autowired
  protected Intbox anioSADEDocumentoActAdm;
  @Autowired
  protected Intbox numeroSADEDocumentoActAdm;

  // Servicios
  @WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
  private DocumentoManagerService documentoManagerService;

  protected TipoDocumentoService tipoDocumentoService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  protected ExpedienteElectronicoDTO ee;

  @Autowired
  private Button tramitacionParalelo;

  @Autowired
  public Tab expedienteTramitacionConjunta;

  @Autowired
  public Tab expedienteFusion;

  @Autowired
  private Window tramitacionWindow;

  protected Task workingTask;

  private DocumentoDTO documentoEstandard;

  private Map<String, DocumentoSubsanableDTO> mapSubsanar;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

    this.mapSubsanar = ((Map<String, DocumentoSubsanableDTO>) Executions.getCurrent().getDesktop()
        .getAttribute("mapSubsanacion"));

    this.ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
        .getAttribute("expedienteElectronico");
    this.tramitacionParalelo = (Button) Executions.getCurrent().getDesktop()
        .getAttribute("tramitacionParaleloButton");
    this.tramitacionWindow = (Window) Executions.getCurrent().getDesktop()
        .getAttribute("tramitacionWindow");
    this.acordeon = (Component) Executions.getCurrent().getDesktop()
        .getAttribute("acordeonWindow");
    this.expedienteTramitacionConjunta = (Tab) Executions.getCurrent().getDesktop()
        .getAttribute("expedienteTramitacionConjuntaTab");
    this.expedienteFusion = (Tab) Executions.getCurrent().getDesktop()
        .getAttribute("expedienteFusionTab");

    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

  }

  public void onCancelar() {
    this.subsanacionDocVinculacionActoAdmWindow.detach();
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  /**
   * 
   * @param name
   *          : nombre de la variable que quiero setear
   * @param value
   *          : valor de la variable
   */
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void onBuscarDocumento() throws InterruptedException {
    validarDatosBuscarDocumento();
    String tipoDocumento = (String) this.tiposDocumentoActoAdministrativoBandbox.getValue();
    tipoDocumento = (tipoDocumento != null) ? tipoDocumento.toUpperCase() : null;
    Integer anioDocumento = this.anioSADEDocumentoActAdm.getValue();
    Integer numeroDocumento = this.numeroSADEDocumentoActAdm.getValue();
    String reparticionDocumento = (String) this.reparticionBusquedaDocumento.getValue().trim();
    DocumentoDTO documentoEstandard = documentoManagerService.buscarDocumentoEstandar(
        tipoDocumento, anioDocumento, numeroDocumento, reparticionDocumento);
    if (documentoEstandard != null) {
      if (!this.ee.getDocumentos().contains(documentoEstandard)) {
        // TODO FALLAR SI NO SE ENCUENTRA
        String tipoDocAcronimo = null;
        if (tipoDocumento != null
            && ((tipoDocumento.equals(NOTA)) || (tipoDocumento.equals(MEMORANDUM)))) {
          tipoDocAcronimo = tipoDocumento;
        } else {
          tipoDocAcronimo = tipoDocumentoService
              .obtenerTiposDocumentoGEDO(documentoEstandard.getTipoDocGedo()).getAcronimo();
        }

        if (!estaHabilitado(tipoDocAcronimo)) {
          throw new WrongValueException(this.tiposDocumentoActoAdministrativoBandbox,
              "Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
        }

        documentoEstandard.setTipoDocAcronimo(tipoDocAcronimo);
        documentoEstandard.setFechaAsociacion(new Date());
        documentoEstandard.setUsuarioAsociador(loggedUsername);
        documentoEstandard.setIdTask(workingTask.getExecutionId());

        if (this.ee.getEsCabeceraTC() != null && this.ee.getEsCabeceraTC()) {
          documentoEstandard.setIdExpCabeceraTC(this.ee.getId());
        }

        documentoEstandard.setDefinitivo(true);
        documentoEstandard.setSubsanado(false);
        documentoEstandard.setSubsanadoLimitado(false);

        this.documentoEstandard = documentoEstandard;

        Messagebox.show(Labels.getLabel("ee.tramitacion.subsanacion.question.actoAdministrativo"),
            Labels.getLabel("ee.tramitacion.subsanacion.titulo"), Messagebox.YES | Messagebox.NO,
            Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
              public void onEvent(Event evt) throws InterruptedException {
                switch (((Integer) evt.getData()).intValue()) {
                case Messagebox.YES:
                  subsanarDocumentos();
                  break;
                case Messagebox.NO:
                  break;
                }
              }
            });

      } else {
        if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
          Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
              Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
              Messagebox.EXCLAMATION);
        } else {
          Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
              Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
              Messagebox.EXCLAMATION);
        }
      }
    } else {
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }
  }

  public void subsanarDocumentos() {

    this.ee.getDocumentos().add(this.documentoEstandard);

    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    for (DocumentoDTO doc : this.ee.getDocumentos()) {
      if (getMapSubsanar().containsKey(doc.getNumeroSade())) {
        if (doc.isSubsanado() != getMapSubsanar().get(doc.getNumeroSade()).isSubsanado()
            || doc.isSubsanadoLimitado() != getMapSubsanar().get(doc.getNumeroSade())
                .isSubsanadoLimitado()) {
          doc.setSubsanado(getMapSubsanar().get(doc.getNumeroSade()).isSubsanado());
          doc.setSubsanadoLimitado(
              getMapSubsanar().get(doc.getNumeroSade()).isSubsanadoLimitado());
          doc.setUsuarioSubsanador(loggedUsername);
          doc.setFechaSubsanacion(new Date());
          if (!doc.getDefinitivo()) {
            doc.setDefinitivo(true);
          }
        }
      }
    }

    this.ee.setFechaModificacion(new Date());
    this.ee.setUsuarioModificacion(loggedUsername);

    expedienteElectronicoService.modificarExpedienteElectronico(this.ee);

    this.subsanacionDocVinculacionActoAdmWindow.detach();
    this.subsanacionDeDocumentosWindow.detach();

    try {
      iMailService.enviarMailSubsanacionDeDocumentos(this.ee);
    } catch (NegocioException e) {
      logger.error(e.getMessage());
    }

    Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarListaDocumentos");
  }

  private void validarDatosBuscarDocumento() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String fechaActual = sdf.format(new Date());
    String anioFormateado = fechaActual.substring(6, 10);
    int anioActual = Integer.parseInt(anioFormateado);
    Integer anioValido = Integer.valueOf(anioActual);
    if ((this.tiposDocumentoActoAdministrativoBandbox.getValue() == null)
        || (this.tiposDocumentoActoAdministrativoBandbox.getValue().equals(""))) {
      throw new WrongValueException(this.tiposDocumentoActoAdministrativoBandbox,
          Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
    }
    if ((this.anioSADEDocumentoActAdm.getValue() == null)) {
      throw new WrongValueException(this.anioSADEDocumentoActAdm,
          Labels.getLabel("ee.tramitacion.faltaAnio"));
    }
    if (this.anioSADEDocumentoActAdm.getValue() > anioValido) {
      throw new WrongValueException(this.anioSADEDocumentoActAdm,
          Labels.getLabel("ee.tramitacion.anioInvalido"));
    }
    if ((this.numeroSADEDocumentoActAdm.getValue() == null)
        || (this.numeroSADEDocumentoActAdm.getValue().equals(""))) {
      throw new WrongValueException(this.numeroSADEDocumentoActAdm,
          Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
    }
    if ((this.reparticionBusquedaDocumento.getValue() == null)
        || (this.reparticionBusquedaDocumento.getValue().equals(""))) {
      throw new WrongValueException(this.reparticionBusquedaDocumento,
          Labels.getLabel("ee.tramitacion.faltaReparticion"));
    }
  }

  private boolean estaHabilitado(String acronimo) {
    this.tiposDocumentosGEDOBD = new ArrayList<>();
    this.tiposDocumentosGEDOBD = tipoDocumentoDAO.buscarTrataTipoDocumento(this.ee.getTrata());
    if (this.tiposDocumentosGEDOBD.size() > 0 && !this.tiposDocumentosGEDOBD.get(0)
        .getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
      for (TrataTipoDocumentoDTO documentoBD : this.tiposDocumentosGEDOBD) {
        if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
          return true;
        }
      }
    } else {
      if (this.tiposDocumentosGEDOBD.size() > 0 && this.tiposDocumentosGEDOBD.get(0)
          .getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
        return true;
      }
    }
    return false;
  }

  public Map<String, DocumentoSubsanableDTO> getMapSubsanar() {
    return mapSubsanar;
  }

  public void setMapSubsanar(Map<String, DocumentoSubsanableDTO> mapSubsanar) {
    this.mapSubsanar = mapSubsanar;
  }
}