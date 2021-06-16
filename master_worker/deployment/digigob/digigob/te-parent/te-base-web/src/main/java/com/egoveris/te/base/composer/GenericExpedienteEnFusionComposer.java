/**
 * @author cearagon
 */
package com.egoveris.te.base.composer;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.ValidacionDeFuisionException;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.TramitacionTabsConditional;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


/**
 * @author cearagon
 *
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericExpedienteEnFusionComposer extends GenericForwardComposer {

  private final static Logger logger = LoggerFactory
      .getLogger(GenericExpedienteEnFusionComposer.class);

  private static final String MOTIVO_VINCULACION_FUSION = "Carátula Fusión";
  private static final String MOTIVO_DESVINCULACION_FUSION = "Desvinculación en Fusión";
  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static final String REPARTICION_SECTOR_ARCHIVADO = "ARCHIVO";
  public static final String PASE = "Pase";
  private static final String MOTIVO_VINCULACION_FUSION_DOCUMENTO = "Carátula Fusión";

  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  private static final String ESTADO_TRAMITACION = "Tramitacion";
  private static final String ESTADO_EJECUCION = "Ejecucion";
  private static final String ESTADO_SUBSANACION = "Subsanacion";

  // Servicios
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private Window expedienteEnFusionWindow;
  @Autowired
  public Tab expedienteTramitacionConjunta;
  @Autowired
  public Tab expedienteFusion;
  private Grid busquedaExpedienteEnFusionGrid;
  public List<ExpedienteAsociadoEntDTO> listaExpedienteEnFusion = new ArrayList<>();
  public ExpedienteAsociadoEntDTO selectedExpedienteEnFusion;
  private Listbox expedienteEnFusionComponent;
  private String codigoExpedienteElectronico;
  protected Task workingTask = null;
  private ExpedienteElectronicoDTO expedienteElectronico;
  private ExpedienteAsociadoEntDTO expFusion;
  private ExpedienteElectronicoDTO expElectronico; // es un expediente que va a
  // fusion
  private String loggedUsername;
  @Autowired
  private Bandbox reparticionBusquedaUsuario;
  @Autowired
  private Combobox tipoExpediente;
  @Autowired
  private Intbox anioSADE;
  @Autowired
  private Intbox numeroSADE;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private Grid gridConsultaExpedientes;
  @Autowired
  private Button buscarPorNumeroSadeButton;
  @Autowired
  private Button confirmarFusionButton;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private Button tramitacionParalelo;
  @Autowired
  private Button enviar;
  @Autowired
  private Button reservar;
  
  @WireVariable(ConstantesServicios.CONSTANTESDB)
  private ConstantesDB constantesDB;
  @Autowired
  private Textbox reparticionActuacion;

  private List<String> actuaciones;

  @Autowired
  private Window tramitacionWindow;

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.FUSION_SERVICE)
  private FusionService fusionService;
  
  @WireVariable(ConstantesServicios.EXP_ASOCIADO_SERVICE)
  private ExpedienteAsociadoService expedienteAsociadoService;
  private String asignadoAnterior;
  private Listfooter totalExpedientesEnfusionSize;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_NOTIFY, new FusionOnNotifyWindowListener(this));
    comp.addEventListener(Events.ON_USER, new FusionOnNotifyWindowListener(this));

    loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
        .toString();
    this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));

    reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());

    try {
      this.expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
          .getAttribute("expedienteElectronico");

      this.tramitacionParalelo = (Button) Executions.getCurrent().getDesktop()
          .getAttribute("tramitacionParaleloButton");
      this.reservar = (Button) Executions.getCurrent().getDesktop().getAttribute("reservarButton");

      this.codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
          .getAttribute("codigoExpedienteElectronico");
    } catch (Exception e) {
      logger.debug(e.getMessage());
      throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
    }

    if (expedienteElectronico == null) {
      String tipoActaucion = BusinessFormatHelper.obtenerActuacion(codigoExpedienteElectronico);
      Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpedienteElectronico);
      Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteElectronico);
      String reparticion = BusinessFormatHelper
          .obtenerReparticionUsuario(codigoExpedienteElectronico);
      this.expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronico(tipoActaucion, anio, numero, reparticion);
    }

    cargarExpedientesAsociadosAFusion();

    /**
     * Si es cabecera y la lista tiene datos = es expediente en fusion.
     */
    if ((this.expedienteElectronico.getEsCabeceraTC() != null)
        && this.expedienteElectronico.getEsCabeceraTC()) {
      if (this.listaExpedienteEnFusion.size() > 0) {
        this.gridConsultaExpedientes.setVisible(false);
        this.buscarPorNumeroSadeButton.setVisible(false);
        this.confirmarFusionButton.setVisible(false);
        this.confirmarFusionButton.setDisabled(false);

        if (this.listaExpedienteEnFusion.get(0).getDefinitivo()) {
          this.enviar.setDisabled(false);
        } else {
          this.enviar.setDisabled(true);
        }

        this.tramitacionParalelo.setDisabled(true);

      }

    } else if (this.listaExpedienteEnFusion.size() > 0) {
      // Sino es cabecera pero la lista tiene datos es por que aún no se
      // confirmo la tramitacion y debe estar el botón habilitado
      this.confirmarFusionButton.setDisabled(false);
      this.enviar.setDisabled(true);
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);
    }

    if (!confirmarFusionButton.isVisible()) {
      busquedaExpedienteEnFusionGrid.detach();
    }
    loadComboActuaciones();
    this.binder.loadAll();
    Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
  }

  private void cargarExpedientesAsociadosAFusion() {
    listaExpedienteEnFusion = new ArrayList<>();

    for (ExpedienteAsociadoEntDTO expFusion : this.expedienteElectronico
        .getListaExpedientesAsociados()) {
      if ((expFusion.getEsExpedienteAsociadoFusion() != null)
          && expFusion.getEsExpedienteAsociadoFusion()) {
        this.listaExpedienteEnFusion.add(expFusion);
      }
    }

    expedienteEnFusionComponent
        .setModel(new BindingListModelList(this.listaExpedienteEnFusion, true));
    this.totalExpedientesEnfusionSize
        .setLabel((new Integer(this.listaExpedienteEnFusion.size())).toString());
  }

  public void onBuscarExpediente() throws InterruptedException {
    if (expedienteElectronicoService == null) {
      expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
          .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    }

    ExpedienteAsociadoEntDTO expedienteFusion = new ExpedienteAsociadoEntDTO();

    validarDatosFormulario();

    String tipoExpediente = (String) this.tipoExpediente.getSelectedItem().getValue();
    Integer anio = this.anioSADE.getValue();
    Integer numero = this.numeroSADE.getValue();
    String reparticionUsuario = (String) this.reparticionBusquedaUsuario.getValue().trim();

    if (validarConExpedienteActual(this.expedienteElectronico, anio, numero)) {
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteActualNoSePuedeAsociar"),
          Labels.getLabel("ee.tramitacion.expedienteActualNoSePuedeAsociar.titulo"), Messagebox.OK,
          Messagebox.EXCLAMATION);
      limpiarFormulario();
    } else {
      // Primero busco el expediente en la tabla de expedientes asociados
      // para corroborar de que no se utilice en otra fusión
      ExpedienteAsociadoEntDTO expedienteAsociado = expedienteAsociadoService
          .obtenerExpedienteAsociadoPorTC(numero, anio, true);

      // Si es nulo significa que el expediente no se esta usando en
      // ninguna fusión.
      if (expedienteAsociado == null) {
        // busca el expediente electronico en la tabla de expedientes.
        ExpedienteElectronicoDTO expParaAsociar = expedienteElectronicoService
            .obtenerExpedienteElectronico(tipoExpediente, anio, numero, reparticionUsuario);
        // EN CASO DE NAULAR EL REQUERIMIENTO DE EVOLUCION DE MODELO DE
        // INTEGRACION.. COMENTAR EL IF
        if (expParaAsociar != null && (expParaAsociar.getTrata().getIntegracionSisExt()
            || expParaAsociar.getTrata().getIntegracionAFJG())) {
          Messagebox.show(
        		  Labels.getLabel("ee.tramitacion.expedienteSistemaExterno"),
              Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
              Messagebox.EXCLAMATION);
          limpiarFormulario();
          return;
        }
        // Si no esta significa que es no existe el expediente.
        if (expParaAsociar != null) {
          // if (!expParaAsociar.getEsReservado()) {
          if (fusionService.esExpedienteEnProcesoDeFusion(expParaAsociar.getCodigoCaratula())) {
            Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteYaAsociadoFusion"),
                Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
                Messagebox.EXCLAMATION);
            limpiarFormulario();

            return;
          }

          // Si esta, debo preguntar si es cabecera de otra fusión/TC.
          if ((expParaAsociar.getEsCabeceraTC() == null) || !expParaAsociar.getEsCabeceraTC()) {
            try {
              // si es tipoExpediente reservado debe tener el
              // mismo tipo de reserva
              if (!validarTipoReservasIguales(expParaAsociar)) {

                Messagebox.show(
                    Labels.getLabel("ee.tramitacion.expedienteAsociadoFusion.error.tipoReserva"),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
                limpiarFormulario();
                return;
              }
              // Si el expediente existe y no es cabecera,
              // entonces llamo al servicio para que me devuelva
              // el expediente que voy a
              // asociar. Debe ser de tipo ExpedienteAsociado
              expedienteFusion = fusionService.obtenerExpedienteFusion(expParaAsociar,
                  loggedUsername, expedienteElectronico.getEstado());

              // Puede que el expediente vuelva nulo por que el
              // expediente a vincular no se encuentra en el mismo
              // estado que el cabecera
              // if (expedienteFusion != null) {

              // Si me trae el expediente para asociar, debo
              // corroborar que ya no lo haya asociado, osea que
              // no este en la lista.
              boolean estaEnLista = false;

              for (ExpedienteAsociadoEntDTO expAsoc : this.expedienteElectronico
                  .getListaExpedientesAsociados()) {
                if (expAsoc.getNumero().equals(expedienteFusion.getNumero())
                    && expAsoc.getAnio().equals(expedienteFusion.getAnio())) {
                  if ((expAsoc.getEsExpedienteAsociadoFusion() != null)
                      && expAsoc.getEsExpedienteAsociadoFusion()) {
                    estaEnLista = true;

                    break;
                  }
                }
              }

              // Si no esta en la lista lo agrego
              if (!estaEnLista) {
                expedienteFusion.setIdExpCabeceraTC(this.expedienteElectronico.getId());

                // Se actualiza la fecha de modificacion
                this.expedienteElectronico.setFechaModificacion(new Date());
                expParaAsociar.setFechaModificacion(new Date());

                this.expedienteElectronico.getListaExpedientesAsociados().add(expedienteFusion);
                limpiarFormulario();

                this.confirmarFusionButton.setDisabled(false);
                this.enviar.setDisabled(true);
                this.tramitacionParalelo.setDisabled(true);
                this.reservar.setDisabled(true);
                this.reservar.setVisible(false);

                cargarExpedientesAsociadosAFusion();
                this.binder.loadAll();
                Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
              } else {
                Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoFusion"),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
                limpiarFormulario();

                return;
              }

              // }
            } catch (ValidacionDeFuisionException validacionDeFusionError) {
              Messagebox.show(validacionDeFusionError.getMessage(), Labels.getLabel("ee.tramitacion.titulo.documentoAsociado"), Messagebox.OK,
                  Messagebox.EXCLAMATION);

              limpiarFormulario();
            } catch (RuntimeException re) {
              Messagebox.show(
                  Labels.getLabel("ee.tramitacion.expedienteAsociadoFusion.Estado",
                      new String[] { this.expedienteElectronico.getEstado() }),
                  Labels.getLabel("ee.tramitacion.titulo.documentoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
              limpiarFormulario();
            }
          } else { // Es cabecera de una TC o bien de una Fusión o
            // tiene Expedientes Asociados

            ExpedienteAsociadoEntDTO expAsocAux = expParaAsociar.getListaExpedientesAsociados()
                .get(0);

            if (expAsocAux.getEsExpedienteAsociadoFusion() != null) { // es
              // cabecera
              // de
              // Fusion
              Messagebox.show(
                  Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeFusion",
                      new String[] { expParaAsociar.getEstado() }),
                  Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
                  Messagebox.EXCLAMATION);
            } else {
              if (expAsocAux.getEsExpedienteAsociadoTC() != null) { // es
                // cabecera
                // de
                // TC
                Messagebox.show(
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeTC",
                        new String[] { expParaAsociar.getEstado() }),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
              } else { // tiene expedientes asociados
                Messagebox.show(
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.tieneExpAsociados",
                        new String[] { expParaAsociar.getEstado() }),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
              }
            }

            limpiarFormulario();

            return;
          }

        } else {
          Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoFusionNoExiste"),
              Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
              Messagebox.EXCLAMATION);
          limpiarFormulario();

          return;
        }
      } else {
        Messagebox.show(
        		Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.utilizado"),
            Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
            Messagebox.EXCLAMATION);
        limpiarFormulario();

        return;
      }
    }
  }

  /**
   * Si expedienteElectronico es reservado, entonces expParaAsociar debe tener
   * el mismo tipo de reserva
   * 
   * @param expParaAsociar
   * @return
   */
  private boolean validarTipoReservasIguales(ExpedienteElectronicoDTO expParaAsociar) {

    if (expedienteElectronico.getEsReservado()) {
      if (expedienteElectronico.getEsReservado() && expParaAsociar.getEsReservado()) {

        return expedienteElectronico.getTrata().getTipoReserva().getId()
            .equals(expParaAsociar.getTrata().getTipoReserva().getId());
      }
      return false;
    }
    return !expParaAsociar.getEsReservado();
  }

  public void onConfirmarFusion() {

    for (ExpedienteAsociadoEntDTO ea : listaExpedienteEnFusion) {
      ExpedienteElectronicoDTO expParaAsociar = null;
      try {
        expParaAsociar = expedienteElectronicoService
            .obtenerExpedienteElectronicoPorCodigo(ea.getAsNumeroSade());
      } catch (ParametroIncorrectoException e) {

      }
      if (expParaAsociar != null && (expParaAsociar.getTrata().getIntegracionSisExt()
          || expParaAsociar.getTrata().getIntegracionAFJG())) {
        Messagebox.show(
        		Labels.getLabel("ee.tramitacion.expedienteSistemaExterno"),
            Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
            Messagebox.EXCLAMATION);
        return;
      }
    }

    Messagebox.show(Labels.getLabel("ee.fusion.question.Fusionar"),
        Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              Clients.showBusy(Labels.getLabel("ee.tramitacion.confirmarFusion.value"));
              Events.echoEvent("onUser", self, "confirmarFusion");

              break;

            case Messagebox.NO:
              break;
            }
          }
        });
  }

  public void confirmarFusion() throws InterruptedException {
    // expedienteElectronico es el expediente
    // cabecera.(expedienteElectronico)
    // expElectronico es un expediente ingresado para fusionarse al
    // cabecera.(expedienteElectronico)
    // expFusion es un expediente ingresado para fusionarse al
    // cabecera.(expedienteAsociado)
    try {
      // Seteo como definitivos a los expedientes que estaran adjuntos.
      // Coloco al expediente Electronico como cabecera
      this.expedienteElectronico.setEsCabeceraTC(true);

      // Se actualiza la fecha de modificacion
      this.expedienteElectronico.setFechaModificacion(new Date());

      String motivo = MOTIVO_VINCULACION_FUSION;

      DocumentoDTO documentoFusion = fusionService.generarDocumentoDeVinculacionEnFusion(
          expedienteElectronico, motivo, loggedUsername, workingTask);
      List<String> listaDocumentos = new ArrayList<String>();
      // TODO: hacer el servicio de VINCULACION del for que sigue!
      for (int i = 0; i < listaExpedienteEnFusion.size(); i++) {
        expFusion = listaExpedienteEnFusion.get(i);
        expFusion.setDefinitivo(true);
        expFusion.setFechaModificacion(new Date());
        expFusion.setUsuarioModificacion(loggedUsername);

        // debo traerme cada task y cambiarle el assignee para que no se
        // vea mas en el inbox
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(expFusion.getIdTask());
        Task task = taskQuery.uniqueResult();

        // Obtengo el expediente de la tabla expedienteElectronico
        expElectronico = this.expedienteElectronicoService.obtenerExpedienteElectronico(
            expFusion.getTipoDocumento(), expFusion.getAnio(), expFusion.getNumero(),
            expFusion.getCodigoReparticionUsuario());

        String tipoActaucion = BusinessFormatHelper.obtenerActuacion(expFusion.getAsNumeroSade());
        Integer anio = BusinessFormatHelper.obtenerAnio(expFusion.getAsNumeroSade());
        Integer numero = BusinessFormatHelper.obtenerNumeroSade(expFusion.getAsNumeroSade());
        String reparticion = BusinessFormatHelper
            .obtenerReparticionUsuario(expFusion.getAsNumeroSade());

        ExpedienteElectronicoDTO expFusionado = expedienteElectronicoService
            .obtenerExpedienteElectronico(tipoActaucion, anio, numero, reparticion);

        documentoFusion.setIdTask(task.getId());
        int posicion = expedienteElectronico.getDocumentos().size();
        documentoFusion.setPosicion(posicion);
        expElectronico.getDocumentos().add(documentoFusion);

        List<DocumentoDTO> listaDocumentosAFusionar = new ArrayList<>();

        for (DocumentoDTO documento : expFusionado.getDocumentos()) {
          if (!expedienteElectronico.getDocumentos().contains(documento)) {
            // Se agrega validacion no debe copiarse el documento si
            // ya existe en el exp cabecera
            listaDocumentosAFusionar
                .add(copiarDocumentoAFusionar(documento, loggedUsername, ++posicion));
            listaDocumentos.add(documento.getNumeroSade());
          }

        }
        expedienteElectronico.getDocumentos().addAll(listaDocumentosAFusionar);
        this.asignadoAnterior = task.getAssignee();

        String asignacion = task.getAssignee() + ".fusion";
        this.processEngine.getTaskService().assignTask(task.getId(), asignacion);

      } // fin FOR recorrido por los fusionados

      fusionService.actualizarEstadoVisualizacionCabecera(expedienteElectronico, loggedUsername,
          listaDocumentos);
      // Se quitan los ceros para guardar el expediente
      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      // Se devuelven los ceros.
      this.confirmarFusionButton.setDisabled(true);
      this.enviar.setDisabled(false);
      this.buscarPorNumeroSadeButton.setDisabled(true);
      this.buscarPorNumeroSadeButton.setVisible(false);
      this.confirmarFusionButton.setVisible(false);
      this.gridConsultaExpedientes.setVisible(false);
      enviarAGuardaTemporalSusFucionados(expedienteElectronico.getId());
      cargarExpedientesAsociadosAFusion();
      habilitarTCvsFusion();

      Clients.clearBusy();
      busquedaExpedienteEnFusionGrid.detach();
      this.enviar.setDisabled(false);
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);

      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.confirmado"), Labels.getLabel("ee.tramitacion.titulo.Tramitadorasociado"), Messagebox.OK,
          Messagebox.INFORMATION);

      Events.sendEvent(
          new Event(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender"));

    } catch (RemoteAccessException e) {
      Clients.clearBusy();
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Se produjo un error al confirmar la fusión. ", e.getMessage());
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.errorConfirmar"), "Error", Messagebox.OK,
          Messagebox.ERROR);
      /**
       * ROLLBACK A MANO DE CADA PASO REALIZADO ARRIBA. TODO: FALTARIA PEDIR LA
       * BAJA DEL DOCUMENTO MEDIANTE UN SERVICIO DE GEDO (faltante)
       */

      // Seteo como definitivos a los expedientes que estaran adjuntos.
      // Coloco al expediente Electronico como cabecera
      this.expedienteElectronico.setEsCabeceraTC(false);

      // String motivo = MOTIVO_VINCULACION_FUSION;
      //
      // Documento documentoFusion =
      // fusionService.generarDocumentoDeVinculacionEnFusion(expedienteElectronico,
      // motivo,
      // loggedUsername, workingTask);

      // TODO: hacer el servicio de VINCULACION del for que sigue!
      for (int i = 0; i < listaExpedienteEnFusion.size(); i++) {
        // OBTENGO CADA EXPEDIENTE FUISIONADO DE LA LISTA
        expFusion = listaExpedienteEnFusion.get(i);
        expFusion.setDefinitivo(false);
        expFusion.setFechaModificacion(new Date());
        expFusion.setUsuarioModificacion(loggedUsername);

        // debo traerme cada task y cambiarle el assignee para que no
        // desaparezcan del inbox
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(expFusion.getIdTask());
        Task task = taskQuery.uniqueResult();

        if (this.asignadoAnterior != null) {
          this.processEngine.getTaskService().assignTask(task.getId(), this.asignadoAnterior);
        }

        // Obtengo el expediente de la tabla expedienteElectronico
        expElectronico = this.expedienteElectronicoService.obtenerExpedienteElectronico(
            expFusion.getTipoDocumento(), expFusion.getAnio(), expFusion.getNumero(),
            expFusion.getCodigoReparticionUsuario());

        if (expElectronico != null) {
          // Obtengo los documentos del expediente fusionado
          List<DocumentoDTO> documentos = expElectronico.getDocumentos();
          List<DocumentoDTO> documentosAux = new ArrayList<>();

          for (DocumentoDTO documentoFusion : documentos) {
            if ((documentoFusion.getIdTask() != null)
                && (documentoFusion.getIdTask().equals(task.getExecutionId())
                    || documentoFusion.getIdTask().equals(task.getId()))
                && (documentoFusion.getMotivo()
                    .equalsIgnoreCase(MOTIVO_VINCULACION_FUSION_DOCUMENTO))) {
              documentosAux.add(documentoFusion);
            }
          }

          // Obtener y eliminar del cabecera los "documentos" del
          // fusionado
          for (DocumentoDTO documento : expElectronico.getDocumentos()) {
            expedienteElectronico.eliminarDocumento(documento);
          }

          for (DocumentoDTO documentoAux : documentosAux) {
            documentoAux.setIdTask(null);
            documentos.remove(documentoAux);
          }

          // Quito los ceros del numero de actuacion
          this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        }

        // Quito los ceros del numero de actuacion
        this.expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      } // fin FOR recorrido por los fusionados

      // Se quitan los ceros para guardar el expediente
      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

      this.confirmarFusionButton.setDisabled(false);
      this.enviar.setDisabled(true);

      this.buscarPorNumeroSadeButton.setDisabled(false);
      this.buscarPorNumeroSadeButton.setVisible(true);
      this.confirmarFusionButton.setVisible(true);

      this.gridConsultaExpedientes.setVisible(true);

      // TODO: si falla acá como hacemos rollback??

      habilitarTCvsFusion();

      this.enviar.setDisabled(true);
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);

      Clients.clearBusy();
      this.closeAssociatedWindow();
    } finally {
      this.binder.loadComponent(this.expedienteEnFusionWindow.getParent());
    }
  }

  private DocumentoDTO copiarDocumentoAFusionar(DocumentoDTO documento, String nombreUsuario,
      int posicion) {
    DocumentoDTO documentoFusionado = new DocumentoDTO();

    documentoFusionado.setNumeroSade(documento.getNumeroSade());
    documentoFusionado.setNumeroEspecial(documento.getNumeroEspecial());
    documentoFusionado.setTipoDocAcronimo(documento.getTipoDocAcronimo());
    documentoFusionado.setNombreUsuarioGenerador(documento.getNombreUsuarioGenerador());
    documentoFusionado.setMotivo(documento.getMotivo());
    documentoFusionado.setData(documento.getData());
    documentoFusionado.setNombreArchivo(documento.getNombreArchivo());
    documentoFusionado.setNumeroFoliado(documento.getNumeroFoliado());
    documentoFusionado.setDefinitivo(true);
    documentoFusionado.setFechaAsociacion(new Date());
    documentoFusionado.setFechaCreacion(new Date());
    documentoFusionado.setUsuarioAsociador(nombreUsuario);
    documentoFusionado.setIdTask(documento.getIdTask());
    documentoFusionado.setTipoDocGedo(documento.getTipoDocGedo());
    documentoFusionado.setTipoDocGenerado(documento.getTipoDocGenerado());
    documentoFusionado.setArchivosDeTrabajo(documento.getArchivosDeTrabajo());
    documentoFusionado.setAclaracion(documento.getAclaracion());
    documentoFusionado.setIdTransaccionFC(documento.getIdTransaccionFC());
    documentoFusionado.setPosicion(posicion);

    return documentoFusionado;
  }

  private void enviarAGuardaTemporalSusFucionados(Long idExpedienteElectronico)
      throws InterruptedException {
    // Llevar los expedientes que fueron fusionados, a
    // "Guarda Temporal"
    listaExpedienteEnFusion = new ArrayList<>();

    // Obtengo los expedientes fusionados al cabecera
    for (ExpedienteAsociadoEntDTO expFusion : this.expedienteElectronico
        .getListaExpedientesAsociados()) {
      if ((expFusion.getEsExpedienteAsociadoFusion() != null)
          && expFusion.getEsExpedienteAsociadoFusion()) {
        this.listaExpedienteEnFusion.add(expFusion);
      }
    }

    for (int i = 0; i < listaExpedienteEnFusion.size(); i++) {
      expFusion = listaExpedienteEnFusion.get(i);
      expElectronico = this.expedienteElectronicoService
          .buscarExpedienteElectronico(expFusion.getIdCodigoCaratula());

      expElectronico.setEstado(ESTADO_GUARDA_TEMPORAL);
      archivar();
    }
  }

  private void archivar() throws InterruptedException {
    setVariableWorkFlow("usuarioSeleccionado", null);
    setVariableWorkFlow("grupoSeleccionado", REPARTICION_SECTOR_ARCHIVADO);

    Map<String, String> detalles = new HashMap<>();
    detalles.put("destinatario", ESTADO_GUARDA_TEMPORAL);

    String motivo = "Fusión con " + expedienteElectronico.getCodigoCaratula();

    TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
        .executionId(expElectronico.getIdWorkflow());
    Task fusionWorkingTask = taskQuery.uniqueResult();

    expedienteElectronicoService.generarPaseExpedienteElectronico(fusionWorkingTask,
        expElectronico, loggedUsername, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

    // Avanza la tarea en el workflow
    signalExecution(ESTADO_GUARDA_TEMPORAL, expElectronico.getIdWorkflow());
    // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
    
    // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
    //processEngine.getExecutionService().signalExecutionById(expElectronico.getIdWorkflow(),
    //    "Cierre");
  }

  /**
   * Closes the associated window and sent a notify event to parent component
   */
  protected final void closeAssociatedWindow() {
    if (this.self == null) {
      throw new IllegalAccessError("The self associated component is not present");
    }

    Events.sendEvent(this.self.getParent(), new Event(Events.ON_NOTIFY));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
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

  /**
   *
   * @param name
   *          : nombre de la variable del WF que quiero encontrar
   * @return objeto guardado en la variable
   */
  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);

    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.workingTask.getExecutionId() + ", " + name, null);
    }

    return obj;
  }

  /**
   *
   * @param nameTransition
   *          : nombre de la transición que voy a usar para la proxima tarea
   * @param usernameDerivador
   *          : usuario que va a tener asignada la tarea
   */
  public void signalExecution(String nameTransition, String idExecutionWorkingTask) {
    processEngine.getExecutionService().signalExecutionById(idExecutionWorkingTask,
        nameTransition);
  }

  public void onDesvincularTodo() {
    Clients.showBusy(Labels.getLabel("ee.tramitacion.desvincularFusion.value"));
    Events.echoEvent("onUser", this.self, "desvincularFusion");
  }

  public void desvincularTodo() throws InterruptedException {
    try {
      // Asigno todas las task de los expedientes hijos a mi mismo.

      // Saco al expediente Electronico como cabecera
      this.expedienteElectronico.setEsCabeceraTC(false);

      // Se actualiza la fecha de modificacion
      this.expedienteElectronico.setFechaModificacion(new Date());

      String motivo = MOTIVO_DESVINCULACION_FUSION;

      DocumentoDTO documentoFusion = fusionService.generarDocumentoDeDesvinculacionEnFusion(
          expedienteElectronico, motivo, loggedUsername, workingTask);

      this.expedienteElectronico = this.fusionService
          .desvincularExpedientesFusion(expedienteElectronico, loggedUsername, documentoFusion);

      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

      // Elimino los expedientes de la tabla.
      for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpedienteEnFusion) {
        this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
      }

      // Vacio la lista por que desvinculo a todos de la fusión.
      listaExpedienteEnFusion = new ArrayList<ExpedienteAsociadoEntDTO>();
      cargarExpedientesAsociadosAFusion();

      this.confirmarFusionButton.setDisabled(true);
      this.enviar.setDisabled(false);

      habilitarBotonesReservaExpediente();

      this.buscarPorNumeroSadeButton.setDisabled(false);
      this.buscarPorNumeroSadeButton.setVisible(true);
      this.confirmarFusionButton.setVisible(true);

      this.gridConsultaExpedientes.setVisible(true);

      this.binder.loadAll();
      Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");

      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.desvinculacion"), Labels.getLabel("ee.tramitacion.titulo.Tramitadorasociado"),
          Messagebox.OK, Messagebox.INFORMATION);

    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Se produjo un error al desvincular los expedientes adjuntos.", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.errorDesvinculacion"), "Error",
          Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
    }
  }

  private void validarDatosFormulario() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    String fechaActual = sdf.format(new Date());
    String anioFormateado = fechaActual.substring(6, 10);

    int anioActual = Integer.parseInt(anioFormateado);

    Integer anioValido = new Integer(anioActual);

    if ((this.anioSADE.getValue() == null) || (this.anioSADE.getValue().equals(""))) {
      throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.tramitacion.faltaAnio"));
    }

    if (this.anioSADE.getValue() > anioValido) {
      throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.tramitacion.anioInvalido"));
    }

    if ((this.numeroSADE.getValue() == null) || (this.numeroSADE.getValue().equals(""))) {
      throw new WrongValueException(this.numeroSADE,
          Labels.getLabel("ee.tramitacion.faltaNumeroDeExpediente"));
    }

    if ((this.reparticionBusquedaUsuario.getValue() == null)
        || (this.reparticionBusquedaUsuario.getValue().equals(""))) {
      throw new WrongValueException(reparticionBusquedaUsuario,
          Labels.getLabel("ee.tramitacion.faltaReparticion"));
    }
  }

  private boolean validarConExpedienteActual(ExpedienteElectronicoDTO expedienteElectronico,
      Integer anio, Integer numero) {
    Integer auxNumero = expedienteElectronico.getNumero();

    return expedienteElectronico.getAnio().equals(anio) && auxNumero.equals(numero);
  }

  public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
    DetalleExpedienteElectronicoComposer.openInModal(this.self,
        this.selectedExpedienteEnFusion.getAsNumeroSade());
  }

  /**
   * Limpia el formulario
   */
  private void limpiarFormulario() {
    this.anioSADE.setText("");
    this.numeroSADE.setText("");
    this.reparticionBusquedaUsuario.setText("");
  }

  public void refreshInbox() {
    this.binder.loadComponent(this.expedienteEnFusionComponent);
    Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
  }

  public void onExecuteDesasociar() throws InterruptedException {
    this.listaExpedienteEnFusion.remove(this.selectedExpedienteEnFusion);

    this.expedienteElectronico.getListaExpedientesAsociados()
        .remove(this.selectedExpedienteEnFusion);

    // Actulizo el expediente electronico
    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

    // Elimino el expediente de la tabla
    this.expedienteAsociadoService.deleteExpedienteAsociado(this.selectedExpedienteEnFusion);

    cargarExpedientesAsociadosAFusion();

    if (listaExpedienteEnFusion.size() == 0) {
      this.confirmarFusionButton.setDisabled(true);
      this.enviar.setDisabled(false);
      habilitarBotonesReservaExpediente();
      this.expedienteFusion.setDisabled(false);
      this.expedienteTramitacionConjunta.setDisabled(false);
    } else {
      habilitarTCvsFusion();
    }

    this.binder.loadComponent(expedienteEnFusionComponent);

  }

  private void habilitarBotonesReservaExpediente() {
    // valida los tres estados posibles
    if (estadoValidoParaReservar()// valida que no sea cabecera confirmada
        // de una TC o una fusion
        && (!((this.expedienteElectronico.getEsCabeceraTC() != null)
            && this.expedienteElectronico.getEsCabeceraTC())
            && ((this.expedienteElectronico.getListaExpedientesAsociados() == null)
                || this.expedienteElectronico.getListaExpedientesAsociados().isEmpty()))
        && !workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
      Boolean tienePermisoGedo = usuariosSADEService.usuarioTieneRol(loggedUsername,
          ConstantesWeb.ROL_EE_CONFIDENCIAL);

      if (usuarioHabilitadoParaReservar() && tienePermisoGedo) {
        this.reservar.setDisabled(false);
        this.reservar.setVisible(true);
      }
    }
  }

  private boolean estadoValidoParaReservar() {
    return getWorkingTask().getActivityName().equals(ESTADO_EJECUCION)
        || getWorkingTask().getActivityName().equals(ESTADO_TRAMITACION)
        || getWorkingTask().getActivityName().equals(ESTADO_SUBSANACION);
  }

  private Boolean usuarioHabilitadoParaReservar() {
    List<TrataReparticionDTO> reparticionesRestoras = obtenerReparticionesRectora();

    Boolean usuarioPerteneceAReparticion = usuarioPerteneceAReparticionRectora(loggedUsername,
        reparticionesRestoras);

    if (usuarioPerteneceAReparticion) {
      return true;
    } else {
      return false;
    }
  }

  private List<TrataReparticionDTO> obtenerReparticionesRectora() {
    List<TrataReparticionDTO> reparticionesRestoras = new ArrayList<>();

    for (int i = 0; i < this.expedienteElectronico.getTrata().getReparticionesTrata()
        .size(); i++) {
      if (this.expedienteElectronico.getTrata().getReparticionesTrata().get(i)
          .getReserva() == true) {
        reparticionesRestoras
            .add(this.expedienteElectronico.getTrata().getReparticionesTrata().get(i));
      }
    }

    return reparticionesRestoras;
  }

  @SuppressWarnings("unused")
  private Boolean usuarioPerteneceAReparticionRectora(String loggedUsername,
      List<TrataReparticionDTO> reparticionesRestoras) {
    if (reparticionesRestoras.size() != 0) {
      Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

      if (!((datosUsuario == null) || (datosUsuario.getCodigoReparticion() == null))) {
        for (int i = 0; i < reparticionesRestoras.size(); i++) {
          if (reparticionesRestoras.get(i).getCodigoReparticion().trim()
              .equals(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS)
              || reparticionesRestoras.get(i).getCodigoReparticion().trim()
                  .equals(datosUsuario.getCodigoReparticion())) {
            return true;
          }

          break;
        }
      }
    }

    return false;
  }

  /**
   * GETTERS & SETTERS
   */
  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public String getCodigoExpedienteElectronico() {
    return codigoExpedienteElectronico;
  }

  public void setCodigoExpedienteElectronico(String codigoExpedienteElectronico) {
    this.codigoExpedienteElectronico = codigoExpedienteElectronico;
  }

  public List<ExpedienteAsociadoEntDTO> getListaExpedienteEnFusion() {
    return listaExpedienteEnFusion;
  }

  public void setListaExpedienteEnFusion(List<ExpedienteAsociadoEntDTO> listaExpedienteEnFusion) {
    this.listaExpedienteEnFusion = listaExpedienteEnFusion;
  }

  public ExpedienteAsociadoEntDTO getSelectedExpedienteEnFusion() {
    return selectedExpedienteEnFusion;
  }

  public void setSelectedExpedienteEnFusion(ExpedienteAsociadoEntDTO selectedExpedienteEnFusion) {
    this.selectedExpedienteEnFusion = selectedExpedienteEnFusion;
  }

  public FusionService getFusionService() {
    return fusionService;
  }

  public void setFusionService(FusionService FusionService) {
    this.fusionService = FusionService;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  /**
   *
   * @throws InterruptedException
   * @Deprecated
   */
  private void habilitarTCvsFusion() throws InterruptedException {
    TramitacionTabsConditional tramitacionTabsConditional = new TramitacionTabsConditional(
        this.workingTask);

    // ************************************************************************
    // **
    // ** Feature: Habilitacion de funcionalidad tratamiento en
    // paralelo/tratamiento en conjunto.
    // ** Se modifica la pantalla de visualización de expediente para que:
    // ** Sumadas a las validaciones actuales, para habilitar el botón "pase
    // múltiple", se controle que esta operación se encuentre habilitada
    // para la trata del expediente
    // ** Sumadas a las validaciones actuales, para habilitar la solapa
    // "tramitación Conjunta", se controle que esta operación se encuentre
    // habilitada para la trata del expediente
    // ************************************************************************
    this.expedienteFusion.setDisabled(!tramitacionTabsConditional
        .condition(this.expedienteElectronico).getIsHabilitarTabFusion());
    this.expedienteTramitacionConjunta.setDisabled(
        !tramitacionTabsConditional.condition(this.expedienteElectronico).getIsHabilitarTabTC());
  }

  /**
   * @return the actuaciones
   */
  public List<String> getActuaciones() {
    if (actuaciones == null) {
      this.actuaciones = TramitacionHelper.findActuaciones();
    }
    return actuaciones;
  }

  private void loadComboActuaciones() {
    tipoExpediente.setModel(new ListModelArray(this.getActuaciones()));
    tipoExpediente.setItemRenderer(new ComboitemRenderer() {

      @Override
      public void render(Comboitem item, Object data, int arg1) throws Exception {
        String actuacion = (String) data;
        item.setLabel(actuacion);
        item.setValue(actuacion);

        if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
          tipoExpediente.setSelectedItem(item);
        }
      }
    });
  }

  final class FusionOnNotifyWindowListener implements EventListener {
    private GenericExpedienteEnFusionComposer composer;

    public FusionOnNotifyWindowListener(GenericExpedienteEnFusionComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData().equals("actualizarSolapasTCvsFusion")) {
          habilitarTCvsFusion();
        } else {
          this.composer.refreshInbox();
        }
      }

      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("confirmarFusion")) {
          this.composer.confirmarFusion();
        }

        if (event.getData().equals("desvincularFusion")) {
          this.composer.desvincularTodo();
        }

        if (event.getData().equals("archivar")) {
          this.composer.archivar();
        }
      }
    }
  }
}
