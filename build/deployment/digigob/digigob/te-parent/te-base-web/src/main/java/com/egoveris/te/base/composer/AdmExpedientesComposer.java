package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AdmExpedientesComposer extends GenericForwardComposer {

  private static Logger logger = LoggerFactory.getLogger(AdmExpedientesComposer.class);

  private static final long serialVersionUID = 5695877602254767673L;

  // protected Task workingTask = null;

  private Window parametrosConsultaWindow;
  private Window admexpedientesWindow;

  private List<ExpedienteElectronicoDTO> expedientes = new ArrayList<>();
  private ExpedienteElectronicoDTO selectedExpediente;
  private Combobox tipoActuacion;
  private Intbox anioSADE;
  private Intbox numeroSADE;
  private Textbox reparticionActuacion;
  private Bandbox reparticionBusquedaUsuario;
  private Listbox consultaExpedienteList;
  private AnnotateDataBinder binder;

  @Autowired
  private Window envio;
  private Map<String, ExpedienteElectronicoDTO> mapCheck = new HashMap<>();
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  @WireVariable(ConstantesServicios.EXP_ASOCIADO_SERVICE)
  private ExpedienteAsociadoService expedienteAsociadoService;
  @WireVariable(ConstantesServicios.CONSTANTESDB)
  private ConstantesDB constantesDB;

  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";

  private List<String> actuaciones;

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(c);
     
    reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());
    loadComboActuaciones();
  }

  /**
   * Busca y devuelve un expediente que se encuentre en estado de archivo.
   * 
   * @throws InterruptedException
   */
  public void onBuscarActuacion() throws InterruptedException {
    validarDatosBuscarExpediente();
    limpiarGrilla();

    this.expedientes = new ArrayList<>();

    String stTipoActuacion = tipoActuacion.getText();
    Integer stAnioSADE = anioSADE.getValue();
    Integer stNumeroSADE = numeroSADE.getValue();
    String stReparticionUsuario = reparticionBusquedaUsuario.getText();

    limpiarCampos();

    ExpedienteAsociadoEntDTO expedienteAsociadoPorFusion = expedienteAsociadoService
        .obtenerExpedienteAsociadoPorFusion(stNumeroSADE, stAnioSADE, true);

    if (expedienteAsociadoPorFusion != null
        && null != expedienteAsociadoPorFusion.getEsExpedienteAsociadoFusion() && !expedienteAsociadoPorFusion.getEsExpedienteAsociadoFusion()) {

      ExpedienteElectronicoDTO expediente = expedienteElectronicoService
          .buscarExpedienteElectronico(expedienteAsociadoPorFusion.getIdExpCabeceraTC());
      String labelExpediente = expediente == null ? null : expediente.getCodigoCaratula();
      Messagebox.show(
          Labels.getLabel("ee.desarchivar.expediente.archivadoPorFusion",
              new String[] { labelExpediente }),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } else {

      if (stNumeroSADE != null) {
        ExpedienteElectronicoDTO expediente = expedienteElectronicoService
            .obtenerExpedienteElectronico(stTipoActuacion, stAnioSADE, stNumeroSADE,
                stReparticionUsuario);

        if (expediente != null && StringUtils.isNotEmpty(expediente.getIdWorkflow())
            && ConstantesWeb.NOMBRE_APLICACION.equals(expediente.getSistemaCreador())) {
          if (expediente.getEstado() != null) {
            this.expedientes.add(expediente);
          } else {
            if (expediente.getEstado().equals(ESTADO_GUARDA_TEMPORAL)) {
              Messagebox.show(Labels.getLabel("ee.desarchivar.expediente.sinarchivar"),
                  Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
            }
          }
        } else {
          Messagebox.show(Labels.getLabel("ee.general.noHayResultados"),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        }
      } else {
        this.expedientes = expedienteElectronicoService
            .buscarExpedientesElectronicosPorAnioReparticion(stAnioSADE, stReparticionUsuario);

        if (this.expedientes == null || this.expedientes.isEmpty()) {
          Messagebox.show(Labels.getLabel("ee.general.noHayResultados"),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        }
      }

      Executions.getCurrent().getDesktop().setAttribute("mapCheck", mapCheck);

      BindingListModel expedientesModel = new BindingListModelList(this.expedientes, true);

      this.consultaExpedienteList.setModel(expedientesModel);
      this.binder.loadComponent(this.consultaExpedienteList);
    }
  }

  public void onClick$administrar() {
    onAnular();
  }

  private void validarDatosBuscarExpediente() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String fechaActual = sdf.format(new Date());
    String anioFormateado = fechaActual.substring(6, 10);

    int anioActual = Integer.parseInt(anioFormateado);
    Integer anioValido = Integer.valueOf(anioActual);
    if (StringUtils.isEmpty(anioSADE.getText())) {
      throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.historialpase.faltaanio"));
    }
    if (this.anioSADE.getValue() > anioValido) {
      throw new WrongValueException(this.anioSADE,
          Labels.getLabel("ee.historialpase.anioInvalido"));
    }
    
//    if (this.numeroSADE.getValue() == null) {
//      throw new WrongValueException(this.numeroSADE,
//          Labels.getLabel("ee.historialpase.faltatnumero"));
//    }
    
    if (StringUtils.isEmpty(reparticionBusquedaUsuario.getText())) {
      throw new WrongValueException(this.reparticionBusquedaUsuario,
          Labels.getLabel("ee.historialpase.faltaReparcicion"));
    }
  }

  private void limpiarCampos() {
    anioSADE.setText("");
    numeroSADE.setText("");
    reparticionBusquedaUsuario.setText("");

  }

  /**
   * 
   * 
   * @throws InterruptedException
   */
  public void onVerExpediente() throws InterruptedException {
    Long idExpedienteElectronico;
    idExpedienteElectronico = this.selectedExpediente.getId();
    Executions.getCurrent().getDesktop().setAttribute("idExpedienteElectronico",
        idExpedienteElectronico);
    Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
        this.selectedExpediente.getCodigoCaratula());
    this.openModalWindow("/expediente/detalleExpedienteElectronico.zul");
  }
  
  public void onRehabilitarExp() {
		// TODO usar la parte de rehabilitar

		HashMap<String, Object>  hma = new HashMap<>();
		hma.put("expedienteSelected", this.selectedExpediente);

		this.envio = (Window) Executions.createComponents("/pase/envioAdministracion.zul", null, hma);
		this.envio.setParent(this.admexpedientesWindow);
		this.envio.setPosition("center");
		this.envio.setClosable(true);

		try {
			this.envio.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("error en onEnviarTramitacion() - SuspendNotAllowedException", e);
		}
		// onAnular();

	}

  public void openModalWindow(String zulFile) {
    if (this.parametrosConsultaWindow != null) {
      this.parametrosConsultaWindow.detach();
      // Le pasamos el mismo composer a la ventana modal para poder
      // comunicarla con la ventana principal
      Map<String, Object> values = new HashMap<>();
      values.put("parentComposer", this);
      this.parametrosConsultaWindow = (Window) Executions
          .createComponents(Utils.formatZulPath(zulFile), this.self, values);
      this.parametrosConsultaWindow.setParent(this.admexpedientesWindow);
      this.parametrosConsultaWindow.setPosition("center");
      this.parametrosConsultaWindow.setVisible(true);
      try {
        this.parametrosConsultaWindow.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class).error("Error al abrir GUI", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("ee.consultaExpedienteComposer.msgbox.noPosibleInicializarVista"),
          Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onEnviarTramitacion() {

    if (this.mapCheck.isEmpty()) {
      Messagebox.show(Labels.getLabel("ee.administrarexpediente.buttonEnviar"),
          Labels.getLabel("ee.administrarexpediente.titulo"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } else {

      HashMap<String, Object> hma = new HashMap<>();
      hma.put("expedientesElectronicos", this.expedientes);

      this.envio = (Window) Executions.createComponents("/pase/envioAdministracion.zul", null,
          hma);
      this.envio.setParent(this.admexpedientesWindow);
      this.envio.setPosition("center");
      this.envio.setClosable(true);

      try {
        this.envio.doModal();
      } catch (SuspendNotAllowedException e) {
        logger.error("error en onEnviarTramitacion() - SuspendNotAllowedException", e);
      }
      onAnular();
    }
  }

  public void onAnular() {
    mapCheck.clear();
    limpiarCampos();
    limpiarGrilla();
    this.binder.loadComponent(this.consultaExpedienteList);
  }

  /**
   * Limpia la grilla del expediente encontrado.
   */
  private void limpiarGrilla() {
    expedientes.clear();
  }

  public List<ExpedienteElectronicoDTO> getExpedientes() {
    return expedientes;
  }

  public void setExpedientes(List<ExpedienteElectronicoDTO> expedientes) {
    this.expedientes = expedientes;
  }

  public ExpedienteElectronicoDTO getSelectedExpediente() {
    return selectedExpediente;
  }

  public void setSelectedExpediente(ExpedienteElectronicoDTO selectedExpediente) {
    this.selectedExpediente = selectedExpediente;
  }

  public Intbox getAnioSADE() {
    return anioSADE;
  }

  public void setAnioSADE(Intbox anioSADE) {
    this.anioSADE = anioSADE;
  }

  public Intbox getNumeroSADE() {
    return numeroSADE;
  }

  public void setNumeroSADE(Intbox numeroSADE) {
    this.numeroSADE = numeroSADE;
  }

  public Textbox getReparticionActuacion() {
    return reparticionActuacion;
  }

  public void setReparticionActuacion(Textbox reparticionActuacion) {
    this.reparticionActuacion = reparticionActuacion;
  }

  public Bandbox getReparticionBusquedaUsuario() {
    return reparticionBusquedaUsuario;
  }

  public void setReparticionBusquedaUsuario(Bandbox reparticionBusquedaSADE) {
    this.reparticionBusquedaUsuario = reparticionBusquedaSADE;
  }

  public Listbox getConsultaExpedienteList() {
    return consultaExpedienteList;
  }

  public void setConsultaExpedienteList(Listbox consultaExpedienteList) {
    this.consultaExpedienteList = consultaExpedienteList;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
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
  
	public void onChanging$reparticionBusquedaUsuario(InputEvent e) {
		Events.sendEvent(Events.ON_CHANGING,reparticionBusquedaUsuario.getChildren().get(0), e);
	}

  private void loadComboActuaciones() {
    tipoActuacion.setModel(new ListModelArray(this.getActuaciones()));
    tipoActuacion.setItemRenderer(new ComboitemRenderer() {

      @Override
      public void render(Comboitem item, Object data, int arg1) throws Exception {
        String actuacion = (String) data;
        item.setLabel(actuacion);
        item.setValue(actuacion);

        if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
          tipoActuacion.setSelectedItem(item);
        }
      }
    });
  }
}
