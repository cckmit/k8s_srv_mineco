/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.ValidacionDeTramitacionConjuntaException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


/**
 * @author jnorvert
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericExpedienteEnTramitacionConjuntaComposer extends GenericForwardComposer {

  final static Logger logger = LoggerFactory
      .getLogger(GenericExpedienteEnTramitacionConjuntaComposer.class);

  @Autowired
  private Window expedienteEnTramitacionConjuntaWindow;
  @Autowired
  private Window expedienteDetalleEnTramitacionConjuntaWindow;
  private TrataService trataDAOHbn;
  @Autowired
  public Tab expedienteTramitacionConjunta;
  @Autowired
  public Tab expedienteFusion;

  public List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta = new ArrayList<>();
  public ExpedienteAsociadoEntDTO selectedExpedienteEnTramitacionConjunta;
  private Listbox expedienteEnTramitacionConjuntaComponent;

  private String codigoExpedienteElectronico;
  protected Task workingTask = null;
  private ExpedienteElectronicoDTO expedienteElectronico;
  private String loggedUsername;
  
  @WireVariable(ConstantesServicios.CONSTANTESDB)
  private ConstantesDB constantesDB;
  
  @Autowired
  private Bandbox reparticionBusquedaUsuario;
  @Autowired
  private Combobox tipoExpediente;

  private List<String> actuaciones;

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
  private Button confirmarTramitacionConjuntaButton;
  @Autowired
  private Button desvincularTodoButton;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private Button tramitacionParalelo;
  @Autowired
  private Button enviar;
  @Autowired
  private Button reservar;
  @Autowired
  private Button quitarReserva;
  @Autowired
  private Button crearPaquete;

  @Autowired
  private Textbox reparticionActuacion;

  // Servicios
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  private static final String MOTIVO_VINCULACION_TRAMITACION_CONJUNTA = "Vinculación en Tramitacion Conjunta";
  private static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación en Tramitacion Conjunta";
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE)
  private TramitacionConjuntaService tramitacionConjuntaService;
  
  @WireVariable(ConstantesServicios.EXP_ASOCIADO_SERVICE)
  private ExpedienteAsociadoService expedienteAsociadoService;

  private String asignadoAnterior;

  private DocumentoDTO documentoTCVinculacion;
  private DocumentoDTO documentoTCDesvinculacion;

  private static final String ESTADO_TRAMITACION = "Tramitacion";
  private static final String ESTADO_EJECUCION = "Ejecucion";
  private static final String ESTADO_SUBSANACION = "Subsanacion";

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    comp.addEventListener(Events.ON_NOTIFY, new TramitacionConjuntaOnNotifyWindowListener(this));
    comp.addEventListener(Events.ON_USER, new TramitacionConjuntaOnNotifyWindowListener(this));

    loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
        .toString();
    this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));

    
    loadComboActuaciones();
    reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());

    Executions.getCurrent().getDesktop().setAttribute("quitarReservaButton", this.quitarReserva);

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

    cargarExpedientesAsociadosATramitacionConjunta();

    /**
     * Si es cabecera y la lista tiene datos = es expediente en tramitación
     * conjunta.
     */
    if (this.expedienteElectronico.getEsCabeceraTC() != null
        && this.expedienteElectronico.getEsCabeceraTC()) {

      // Si entra en este if está en tramitación conjunta, deshabilito el
      // pase múltiple
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);

      if (this.listaExpedienteEnTramitacionConjunta.size() > 0) {
        this.gridConsultaExpedientes.setVisible(false);
        this.buscarPorNumeroSadeButton.setVisible(false);
        this.confirmarTramitacionConjuntaButton.setVisible(false);
        this.confirmarTramitacionConjuntaButton.setDisabled(false);
        this.desvincularTodoButton.setDisabled(false);

        boolean haypendientes = false;
        int cont = 0;
        while ((cont < listaExpedienteEnTramitacionConjunta.size() && !haypendientes)) {
          if ((listaExpedienteEnTramitacionConjunta.get(cont)
              .getEsExpedienteAsociadoFusion() != null
              || listaExpedienteEnTramitacionConjunta.get(cont)
                  .getEsExpedienteAsociadoTC() != null)
              && !listaExpedienteEnTramitacionConjunta.get(cont).getDefinitivo()) {
            haypendientes = true;
          }
          cont++;
        }
        if (haypendientes) {
          this.enviar.setDisabled(true);
        } else {
          this.enviar.setDisabled(false);
        }

      } else if (this.listaExpedienteEnTramitacionConjunta.size() > 0) {
        // Sino es cabecera pero la lista tiene datos es por que aún no se
        // confirmo la tramitacion y debe estar el botón habilitado

        this.confirmarTramitacionConjuntaButton.setDisabled(false);
        this.enviar.setDisabled(true);
        this.tramitacionParalelo.setDisabled(true);
        this.reservar.setDisabled(true);
        this.reservar.setVisible(false);
      }

//      loadComboActuaciones();

      this.binder.loadAll();
    }

  }

  private void cargarExpedientesAsociadosATramitacionConjunta() {

    listaExpedienteEnTramitacionConjunta = new ArrayList<>();

    for (ExpedienteAsociadoEntDTO expTramitacionConjunta : this.expedienteElectronico
        .getListaExpedientesAsociados()) {
      ExpedienteElectronicoDTO ee2 = null;
      if (expTramitacionConjunta.getEsExpedienteAsociadoTC() != null
          && expTramitacionConjunta.getEsExpedienteAsociadoTC()) {
        ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(
            expTramitacionConjunta.getTipoDocumento(), expTramitacionConjunta.getAnio(),
            expTramitacionConjunta.getNumero(),
            expTramitacionConjunta.getCodigoReparticionUsuario());
        expTramitacionConjunta.setDescrpTrata(ee2.getTrata().getDescripcion());
        this.listaExpedienteEnTramitacionConjunta.add(expTramitacionConjunta);
      }
    }

    expedienteEnTramitacionConjuntaComponent
        .setModel(new BindingListModelList(this.listaExpedienteEnTramitacionConjunta, true));
  }

  public void onBuscarExpediente() throws InterruptedException {
    if (expedienteElectronicoService == null) {
      expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
          .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    }
    ExpedienteAsociadoEntDTO expedienteTramitacionConjunta = new ExpedienteAsociadoEntDTO();

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
      // para corroborar de que no se utilice en otra tramitacion conjunta
      ExpedienteAsociadoEntDTO expedienteAsociado = expedienteAsociadoService
          .obtenerExpedienteAsociadoPorTC(numero, anio, true);

      // Si es nulo significa que el expediente no se esta usando en
      // ninguna tramitacion Conjunta.
      if (expedienteAsociado == null) {

        // busca el expediente electronico en la tabla de expedientes.
        ExpedienteElectronicoDTO expParaAsociar = expedienteElectronicoService
            .obtenerExpedienteElectronico(tipoExpediente, anio, numero, reparticionUsuario);

        // Si no esta significa que es no existe el expediente.
        if (expParaAsociar != null) {

          // Si esta, debo preguntar:
          // si es cabecera de otra TC o bien de una fusión.
          if (expParaAsociar.getEsCabeceraTC() == null || !expParaAsociar.getEsCabeceraTC()) {
            try {
              // si es tipoExpediente reservado debe tener el mismo tipo de
              // reserva
              if (!validarTipoReservasIguales(expParaAsociar)) {

                Messagebox.show(
                    Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.error.tipoReserva"),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
                limpiarFormulario();
                return;
              }
              // Si el expediente existe y no es cabecera,
              // entonces llamo al servicio para que me devuelva
              // el expediente que voy a
              // asociar. Debe ser de tipo ExpedienteAsociado
              expedienteTramitacionConjunta = tramitacionConjuntaService
                  .obtenerExpedienteTramitacionConjunta(expParaAsociar, loggedUsername,
                      expedienteElectronico.getEstado());

              // Puede que el expediente vuelva nulo por que el
              // expediente a vincular no se encuentra en el mismo
              // estado que el cabecera
              // if (expedienteTramitacionConjunta != null) {

              // Si me trae el expediente para asociar, debo
              // corroborar que ya no lo haya asociado, osea que
              // no este en la lista.
              boolean estaEnLista = false;
              for (ExpedienteAsociadoEntDTO expAsoc : this.expedienteElectronico
                  .getListaExpedientesAsociados()) {

                if (expAsoc.getEsExpedienteAsociadoTC() != null
                    && expAsoc.getEsExpedienteAsociadoTC()
                    && expAsoc.getNumero().equals(expedienteTramitacionConjunta.getNumero())
                    && expAsoc.getAnio().equals(expedienteTramitacionConjunta.getAnio())) {
                  estaEnLista = true;
                }
              }

              // Si no esta en la lista lo agrego
              if (!estaEnLista) {
                TrataDTO t = expedienteElectronico.getTrata();
                ParametrosSistemaExternoDTO params = ConfiguracionInicialModuloEEFactory
                    .obtenerParametrosPorTrata(t.getId());
                if (expedienteElectronico.getSistemaCreador().equals(ConstantesWeb.SISTEMA_BAC)
                    || ((t.getIntegracionSisExt() || t.getIntegracionAFJG()) && params != null)) {
                  decidirEnvioDeMensaje(t, expedienteTramitacionConjunta, expParaAsociar, params);
                } else {
                  adjuntarEEConjunta(expedienteTramitacionConjunta, expParaAsociar);
                }

              } else {
                Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTC"),
                    Labels
                        .getLabel("ee.tramitacion.expedienteAsociadoEnTramitacionConjunta.titulo"),
                    Messagebox.OK, Messagebox.EXCLAMATION);
                limpiarFormulario();
                return;
              }
            } catch (ValidacionDeTramitacionConjuntaException validacionDeTramitacionConjuntaError) {

              Messagebox.show(validacionDeTramitacionConjuntaError.getMessage(), Labels.getLabel("ee.tramitacion.titulo.documentoAsociado"),
                  Messagebox.OK, Messagebox.EXCLAMATION);

              limpiarFormulario();
            } catch (RuntimeException re) {
              Messagebox.show(
                  Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.Estado",
                      new String[] { this.expedienteElectronico.getEstado() }),
                  Labels.getLabel("ee.tramitacion.titulo.documentoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
              limpiarFormulario();
            }
          } else {// Es cabecera de una TC o bien de una Fusión o
            // tiene Expedientes Asociados
            ExpedienteAsociadoEntDTO expAsocAux = expParaAsociar.getListaExpedientesAsociados()
                .get(0);
            // Es cabecera de Fusión
            if (expAsocAux.getEsExpedienteAsociadoFusion() != null) {
              Messagebox.show(
                  Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeFusion",
                      new String[] { expParaAsociar.getEstado() }),
                  Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
                  Messagebox.EXCLAMATION);
            } else {
              // Es cabecera de TC
              if (expAsocAux.getEsExpedienteAsociadoTC() != null) {
                Messagebox.show(
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeTC",
                        new String[] { expParaAsociar.getEstado() }),
                    Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
              } else {// tiene expedientes asociados
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

          Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTCNoExiste"),
              Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
              Messagebox.EXCLAMATION);
          limpiarFormulario();
          return;
        }
      } else {
        Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTCUtilizado"),
            Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
            Messagebox.EXCLAMATION);
        limpiarFormulario();
        return;
      }
    }
  }

  private void decidirEnvioDeMensaje(TrataDTO t,
      ExpedienteAsociadoEntDTO expedienteTramitacionConjunta,
      ExpedienteElectronicoDTO expParaAsociar, ParametrosSistemaExternoDTO p)
      throws InterruptedException {

    if (expedienteElectronico.getSistemaCreador().equals(ConstantesWeb.SISTEMA_BAC)) {
      mostrarMensajeDeAdvertenciaSistemaExternoBAC(expedienteTramitacionConjunta, expParaAsociar,
          ConstantesWeb.SISTEMA_BAC);
      return;
    }
    if ((t.getIntegracionSisExt() || t.getIntegracionAFJG()) && p != null) {
      mostrarMensajeDeAdvertenciaSistemaExternoBAC(expedienteTramitacionConjunta, expParaAsociar,
          p.getCodigo());
    }
  }

  public void adjuntarEEConjunta(ExpedienteAsociadoEntDTO expedienteTramitacionConjunta,
      ExpedienteElectronicoDTO expParaAsociar) {
    expedienteTramitacionConjunta.setIdExpCabeceraTC(this.expedienteElectronico.getId());

    // Se actualiza la fecha de modificacion
    this.expedienteElectronico.setFechaModificacion(new Date());
    expParaAsociar.setFechaModificacion(new Date());

    this.expedienteElectronico.getListaExpedientesAsociados().add(expedienteTramitacionConjunta);

    limpiarFormulario();

    this.confirmarTramitacionConjuntaButton.setDisabled(false);
    this.enviar.setDisabled(true);
    this.tramitacionParalelo.setDisabled(true);
    this.reservar.setDisabled(true);
    this.reservar.setVisible(false);
    this.quitarReserva.setDisabled(true);
    this.crearPaquete.setDisabled(true);

    cargarExpedientesAsociadosATramitacionConjunta();

    this.binder.loadAll();
    Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
  }

  public void mostrarMensajeDeAdvertenciaSistemaExternoBAC(final ExpedienteAsociadoEntDTO asoc,
      final ExpedienteElectronicoDTO expParaAsociar, String sistemaExterno)
      throws InterruptedException {
    Messagebox.show(
    		Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.finalizarProceso")
            + sistemaExterno.toUpperCase(),
            Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

          @Override
          public void onEvent(Event evt) throws Exception {
            switch ((Integer) evt.getData()) {
            case Messagebox.OK:
              adjuntarEEConjunta(asoc, expParaAsociar);
            default:
              return;
            }

          }

        });
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

  public void onConfirmarTramitacionConjunta() {

    Clients.showBusy(Labels.getLabel("ee.tramitacion.confirmarTramitacionConjunta.value"));
    Events.echoEvent("onUser", this.self, "confirmarTramitacionConjunta");
  }

  public void confirmarTramitacionConjunta() throws InterruptedException {

    try {
      // Seteo como definitivos a los expedientes que estaran adjuntos.
      // Coloco al expediente Electronico como cabecera
      this.expedienteElectronico.setEsCabeceraTC(true);

      // Se actualiza la fecha de modificacion
      this.expedienteElectronico.setFechaModificacion(new Date());

      String motivo = MOTIVO_VINCULACION_TRAMITACION_CONJUNTA;

      documentoTCVinculacion = tramitacionConjuntaService
          .generarDocumentoDeVinculacionEnTramitacionConjunta(expedienteElectronico, motivo,
              loggedUsername, workingTask);

      this.expedienteElectronico = this.tramitacionConjuntaService.vincularExpedientesTramitacionConjunta(
          listaExpedienteEnTramitacionConjunta, loggedUsername, documentoTCVinculacion,
          asignadoAnterior, this.expedienteElectronico);

      this.confirmarTramitacionConjuntaButton.setDisabled(true);
      this.enviar.setDisabled(false);

      this.buscarPorNumeroSadeButton.setDisabled(true);
      this.buscarPorNumeroSadeButton.setVisible(false);
      this.confirmarTramitacionConjuntaButton.setVisible(false);

      this.desvincularTodoButton.setDisabled(false);
      this.desvincularTodoButton.setVisible(true);

      this.gridConsultaExpedientes.setVisible(false);
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);
      this.crearPaquete.setDisabled(false);

      cargarExpedientesAsociadosATramitacionConjunta();
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.confirmacion"), Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK,
          Messagebox.INFORMATION);

    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Se produjo un error al confirmar la tramitación conjunta.", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTC.errorConfirmar"), "Error",
          Messagebox.OK, Messagebox.ERROR);

      /**
       * ROLLBACK A MANO DE CADA PASO REALIZADO ARRIBA. TODO: FALTARIA PEDIR LA
       * BAJA DEL DOCUMENTO MEDIANTE UN SERVICIO DE GEDO (faltante)
       */

      this.expedienteElectronico.setEsCabeceraTC(false);

      // Tiempo para definir si los documentos de tramitación conjunta son
      // los de esta iteración
      Calendar tiempoActual = Calendar.getInstance();
      tiempoActual.add(Calendar.MINUTE, -5);

      for (ExpedienteAsociadoEntDTO expTramitacionConjunta : listaExpedienteEnTramitacionConjunta) {
        expTramitacionConjunta.setDefinitivo(false);
        expTramitacionConjunta.setFechaModificacion((new Date()));
        expTramitacionConjunta.setUsuarioModificacion(loggedUsername);

        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(expTramitacionConjunta.getIdTask());
        Task task = taskQuery.uniqueResult();

        // Obtengo el expediente de la tabla expedienteElectronico
        ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronico(expTramitacionConjunta.getTipoDocumento(),
                expTramitacionConjunta.getAnio(), expTramitacionConjunta.getNumero(),
                expTramitacionConjunta.getCodigoReparticionUsuario());

        if (expElectronico != null) {
          List<DocumentoDTO> documentos = expElectronico.getDocumentos();
          List<DocumentoDTO> documentosAux = new ArrayList<>();
          for (DocumentoDTO documentoTC : documentos) {
            if (documentoTC.getIdTask() != null
                && (documentoTC.getIdTask().equals(task.getExecutionId())
                    || documentoTC.getIdTask().equals(task.getId()))
                && documentoTC.getMotivo().contains(MOTIVO_VINCULACION_TRAMITACION_CONJUNTA)
                && documentoTC.getFechaAsociacion().after(tiempoActual.getTime())) {
              documentosAux.add(documentoTC);
            }
          }
          // Obtener y eliminar del cabecera los "documentos" de la
          // tramitación conjunta
          for (DocumentoDTO documento : expElectronico.getDocumentos()) {
            expedienteElectronico.eliminarDocumento(documento);
          }
          for (DocumentoDTO documentoAux : documentosAux) {
            documentoAux.setIdTask(null);
            expElectronico.getDocumentos().remove(documentoAux);
          }
          // Quito los ceros del
          this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        }
        if (this.asignadoAnterior != null) {
          this.processEngine.getTaskService().assignTask(task.getId(), this.asignadoAnterior);
        }
        List<DocumentoDTO> documentos = expedienteElectronico.getDocumentos();
        List<DocumentoDTO> documentosAux = new ArrayList<>();

        for (DocumentoDTO documentoTC : documentos) {
          if (documentoTC.getIdTask() != null
              && (documentoTC.getIdTask().equals(task.getExecutionId())
                  || documentoTC.getIdTask().equals(task.getId()))
              && documentoTC.getMotivo().contains(MOTIVO_VINCULACION_TRAMITACION_CONJUNTA)
              && documentoTC.getFechaAsociacion().after(tiempoActual.getTime())) {
            documentosAux.add(documentoTC);
          }
        }
        for (DocumentoDTO documentoAux : documentosAux) {
          documentoAux.setIdTask(null);
          expedienteElectronico.getDocumentos().remove(documentoAux);
        }

        // Quito los ceros del
        this.expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      }

    } finally {
      Clients.clearBusy();

    }
  }

  public void onDesvincularTodo() {

    Clients.showBusy(Labels.getLabel("ee.tramitacion.desvincularTramitacionConjunta.value"));
    Events.echoEvent("onUser", this.self, "desvincularTramitacionConjunta");
  }

  public void desvincularTodo() throws InterruptedException {

    try {
      // Asigno todas las task de los expedientes hijos a mi mismo.

      // Guardo la lista de Expedientes Asociados para poder utilizar en
      // el rollback si fuera necesario.

      // Saco al expediente Electronico como cabecera
      this.expedienteElectronico.setEsCabeceraTC(false);

      // Se actualiza la fecha de modificacion
      this.expedienteElectronico.setFechaModificacion(new Date());

      String motivo = MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA;

      documentoTCDesvinculacion = tramitacionConjuntaService
          .generarDocumentoDeDesvinculacionEnTramitacionConjunta(expedienteElectronico, motivo,
              loggedUsername, workingTask);

      this.expedienteElectronico = this.tramitacionConjuntaService
          .desvincularExpedientesTramitacionConjunta(expedienteElectronico, loggedUsername,
              documentoTCDesvinculacion);

      for (DocumentoDTO doc : expedienteElectronico.getDocumentos()) {
        if (doc != null) {
          doc.setDefinitivo(true);
        }
      }
      // Se quitan los ceros para guardar el expediente en la base
      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      // Se devuelven los ceros

      // Elimino los expedientes de la tabla.
      for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpedienteEnTramitacionConjunta) {
        this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
        ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
                expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
                expedienteAsociado.getCodigoReparticionUsuario());
        if (expElectronico != null) {
          expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        }
      }

      // Vacio la lista por que desvinculo a todos de la tramitacion
      // conjunta.
      listaExpedienteEnTramitacionConjunta = new ArrayList<>();

      cargarExpedientesAsociadosATramitacionConjunta();

      this.confirmarTramitacionConjuntaButton.setDisabled(true);
      this.enviar.setDisabled(false);
      this.tramitacionParalelo.setDisabled(false);
//      this.reservar.setDisabled(false);
//      this.reservar.setVisible(true);
      this.quitarReserva.setDisabled(false);

      this.buscarPorNumeroSadeButton.setDisabled(false);
      this.buscarPorNumeroSadeButton.setVisible(true);
      this.confirmarTramitacionConjuntaButton.setVisible(true);

      this.desvincularTodoButton.setDisabled(true);

      this.gridConsultaExpedientes.setVisible(true);

      this.binder.loadAll();
      Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");

      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.desvinculacion"), Labels.getLabel("ee.act.msg.title.ok"),
          Messagebox.OK, Messagebox.INFORMATION);

    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Se produjo un error al desvincular los expedientes adjuntos.", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteFusion.errorDesvinculacion"), "Error",
          Messagebox.OK, Messagebox.ERROR);

      /**
       * ROLLBACK SI HUBO ERROR
       */

      this.expedienteElectronico.setEsCabeceraTC(true);

      // Si existe el documento de desvinculación y este mismo tiene
      // asignado un id de Task, entonces tengo que volver a vincular
      // los expedientes en TC.
      if (documentoTCDesvinculacion != null && documentoTCDesvinculacion.getIdTask() != null) {

        if (this.expedienteElectronico.getDocumentos().contains(documentoTCDesvinculacion)) {
          this.expedienteElectronico.getDocumentos().remove(documentoTCDesvinculacion);
        }

      }

      if (listaExpedienteEnTramitacionConjunta.size() > 0) {
        for (ExpedienteAsociadoEntDTO expedienteAsociadoAnteriormente : listaExpedienteEnTramitacionConjunta) {
          // Obtengo el expediente de la tabla expedienteElectronico
          expedienteAsociadoAnteriormente.setDefinitivo(true);
          expedienteAsociadoAnteriormente.setFechaModificacion((new Date()));
          expedienteAsociadoAnteriormente.setUsuarioModificacion(loggedUsername);

          ExpedienteElectronicoDTO expElectronicoAsociadoAnteriormente = this.expedienteElectronicoService
              .obtenerExpedienteElectronico(expedienteAsociadoAnteriormente.getTipoDocumento(),
                  expedienteAsociadoAnteriormente.getAnio(),
                  expedienteAsociadoAnteriormente.getNumero(),
                  expedienteAsociadoAnteriormente.getCodigoReparticionUsuario());
          if (documentoTCDesvinculacion != null) {
            documentoTCDesvinculacion.setIdTask(null);
          }
          if (expElectronicoAsociadoAnteriormente.getDocumentos()
              .contains(documentoTCDesvinculacion)) {
            expElectronicoAsociadoAnteriormente.getDocumentos().remove(documentoTCDesvinculacion);
          }

          // debo traerme cada task y cambiarle el assignee para que
          // no se
          // vea mas en el inbox y se mantenga como estaba
          // anteriormente
          TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
              .executionId(expedienteAsociadoAnteriormente.getIdTask());
          Task task = taskQuery.uniqueResult();
          if (!(task.getAssignee().contains(".conjunta"))) {
            asignadoAnterior = task.getAssignee();
            String asignacion = task.getAssignee() + ".conjunta";
            this.processEngine.getTaskService().assignTask(task.getId(), asignacion);
          }

          this.expedienteAsociadoService
              .actualizarExpedienteAsociado(expedienteAsociadoAnteriormente);

          this.expedienteElectronicoService
              .modificarExpedienteElectronico(expElectronicoAsociadoAnteriormente);
        }

      }

      this.expedienteElectronico
          .setListaExpedientesAsociados(listaExpedienteEnTramitacionConjunta);
      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

      this.confirmarTramitacionConjuntaButton.setDisabled(true);
      this.enviar.setDisabled(false);

      this.buscarPorNumeroSadeButton.setDisabled(true);
      this.buscarPorNumeroSadeButton.setVisible(false);
      this.confirmarTramitacionConjuntaButton.setVisible(false);

      this.desvincularTodoButton.setDisabled(false);
      this.desvincularTodoButton.setVisible(true);

      this.gridConsultaExpedientes.setVisible(false);
      this.tramitacionParalelo.setDisabled(true);
      this.reservar.setDisabled(true);
      this.reservar.setVisible(false);

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

    String auxNumero = BusinessFormatHelper
        .quitaCerosNumeroActuacion(expedienteElectronico.getNumero());

    if (expedienteElectronico.getAnio().equals(anio) && auxNumero.equals(numero.toString())) {
      return true;
    }

    return false;
  }

  public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
    DetalleExpedienteElectronicoComposer.openInModal(this.self,
        this.selectedExpedienteEnTramitacionConjunta.getAsNumeroSade());
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
    this.binder.loadComponent(this.expedienteEnTramitacionConjuntaComponent);
    Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
  }

  public void onExecuteDesasociar() throws InterruptedException {

    this.listaExpedienteEnTramitacionConjunta.remove(this.selectedExpedienteEnTramitacionConjunta);

    this.expedienteElectronico.getListaExpedientesAsociados()
        .remove(this.selectedExpedienteEnTramitacionConjunta);

    ExpedienteElectronicoDTO ee = expedienteElectronicoService.obtenerExpedienteElectronico(
        this.selectedExpedienteEnTramitacionConjunta.getTipoDocumento(),
        this.selectedExpedienteEnTramitacionConjunta.getAnio(),
        this.selectedExpedienteEnTramitacionConjunta.getNumero(),
        this.selectedExpedienteEnTramitacionConjunta.getCodigoReparticionUsuario());

    // Se actualiza la fecha de modificacion
    ee.setFechaModificacion(new Date());
    expedienteElectronico.setFechaModificacion(new Date());
    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
    expedienteElectronicoService.modificarExpedienteElectronico(ee);

    // Elimino el expediente de la tabla
    this.expedienteAsociadoService
        .deleteExpedienteAsociado(this.selectedExpedienteEnTramitacionConjunta);

    cargarExpedientesAsociadosATramitacionConjunta();

    if (listaExpedienteEnTramitacionConjunta.size() == 0) {
      this.confirmarTramitacionConjuntaButton.setDisabled(true);
      this.enviar.setDisabled(false);
      this.tramitacionParalelo.setDisabled(false);
      if (!this.expedienteElectronico.getEsReservado()) {
        this.reservar.setDisabled(false);
        this.reservar.setVisible(true);
      } else {
        this.quitarReserva.setVisible(true);
        this.quitarReserva.setDisabled(false);
      }
    }
    this.binder.loadComponent(expedienteEnTramitacionConjuntaComponent);
    Events.echoEvent("onNotify", this.self, "actualizarSolapasTCvsFusion");
  }

  private void habilitarBotonesReservaExpediente() {

    if ( // valida los tres estados posibles
    estadoValidoParaReservar()
        // valida que no sea cabecera confirmada de una TC o una fusion
        // Valida que no sea cabera no confirmada de una TC o una fusion
        && (!(this.expedienteElectronico.getEsCabeceraTC() != null
            && this.expedienteElectronico.getEsCabeceraTC())
            && (this.expedienteElectronico.getListaExpedientesAsociados() == null
                || this.expedienteElectronico.getListaExpedientesAsociados().isEmpty()))
        && !workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
      Boolean tienePermisoGedo = usuariosSADEService.usuarioTieneRol(loggedUsername,
          ConstantesWeb.ROL_EE_CONFIDENCIAL);

      if (usuarioHabilitadoParaReservar() && tienePermisoGedo)
        this.reservar.setDisabled(false);
      this.reservar.setVisible(true);
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

    if (usuarioPerteneceAReparticion)
      return true;
    else
      return false;
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
      Usuario datosUsuario = this.usuariosSADEService.getDatosUsuario(loggedUsername);

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

  public List<ExpedienteAsociadoEntDTO> getListaExpedienteEnTramitacionConjunta() {
    return listaExpedienteEnTramitacionConjunta;
  }

  public void setListaExpedienteEnTramitacionConjunta(
      List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta) {
    this.listaExpedienteEnTramitacionConjunta = listaExpedienteEnTramitacionConjunta;
  }

  public ExpedienteAsociadoEntDTO getSelectedExpedienteEnTramitacionConjunta() {
    return selectedExpedienteEnTramitacionConjunta;
  }

  public void setSelectedExpedienteEnTramitacionConjunta(
      ExpedienteAsociadoEntDTO selectedExpedienteEnTramitacionConjunta) {
    this.selectedExpedienteEnTramitacionConjunta = selectedExpedienteEnTramitacionConjunta;
  }

  public TramitacionConjuntaService getTramitacionConjuntaService() {
    return tramitacionConjuntaService;
  }

  public void setTramitacionConjuntaService(
      TramitacionConjuntaService tramitacionConjuntaService) {
    this.tramitacionConjuntaService = tramitacionConjuntaService;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  private void habilitarTCvsFusion() {
    boolean encontradoExpedienteFusionado = false;
    boolean encontradoExpedienteEnTC = false;
    boolean operacionConfirmada = false;
    int cont = 0;
    int cantAsociados = this.expedienteElectronico.getListaExpedientesAsociados().size();
    List<ExpedienteAsociadoEntDTO> listaExpedienteAsociados = new ArrayList<>();

    listaExpedienteAsociados = this.expedienteElectronico.getListaExpedientesAsociados();

    while ((cont < cantAsociados) && (!encontradoExpedienteFusionado)
        && (!encontradoExpedienteEnTC)) {
      if ((listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoTC() == null)
          && ((listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoFusion() != null)
              && (listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoFusion()))) {
        encontradoExpedienteFusionado = true;
      } else {
        if ((listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoFusion() == null)
            && ((listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoTC() != null)
                && (listaExpedienteAsociados.get(cont).getEsExpedienteAsociadoTC()))) {
          encontradoExpedienteEnTC = true;
        }
      }
      if ((listaExpedienteAsociados.get(cont).getDefinitivo() != null)
          && (listaExpedienteAsociados.get(cont).getDefinitivo())) {
        operacionConfirmada = true;
      }
      cont = cont + 1;
    }
    if (encontradoExpedienteFusionado) {
      TramitacionComposer.habilitarTabFusion = true;
      TramitacionComposer.habilitarTabTC = false;
    } else {
      if (encontradoExpedienteEnTC) {
        TramitacionComposer.habilitarTabFusion = false;
        TramitacionComposer.habilitarTabTC = true;
      } else {
        TramitacionComposer.habilitarTabFusion = true;
        TramitacionComposer.habilitarTabTC = true;
      }
    }
    Boolean fusionador = (Boolean) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_FUSIONADOR);

    if (TramitacionComposer.habilitarTabFusion) {
      if (fusionador != null && fusionador) {
        this.expedienteFusion.setDisabled(false);
      } else {// no es usuario fusionador,
        if (operacionConfirmada) {// pero el expedinte ya cuenta con una
          // fusión confirmada.
          this.expedienteFusion.setDisabled(false);
        } else {
          this.expedienteFusion.setDisabled(true);
        }
      }
    } else {
      this.expedienteFusion.setDisabled(true);
    }
    if (TramitacionComposer.habilitarTabTC) {
      this.expedienteTramitacionConjunta.setDisabled(false);
    }

  }

  final class TramitacionConjuntaOnNotifyWindowListener implements EventListener {
    private GenericExpedienteEnTramitacionConjuntaComposer composer;

    public TramitacionConjuntaOnNotifyWindowListener(
        GenericExpedienteEnTramitacionConjuntaComposer comp) {
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
        if (event.getData().equals("confirmarTramitacionConjunta")) {
          this.composer.confirmarTramitacionConjunta();
        }
        if (event.getData().equals("desvincularTramitacionConjunta")) {
          this.composer.desvincularTodo();
        }

      }
    }

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
}
