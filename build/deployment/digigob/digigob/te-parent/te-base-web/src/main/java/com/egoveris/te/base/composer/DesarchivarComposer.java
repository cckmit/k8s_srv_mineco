package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
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

public class DesarchivarComposer extends GenericForwardComposer {

  private static Logger logger = LoggerFactory.getLogger(DesarchivarComposer.class);

  private static final long serialVersionUID = 5695877602254767673L;

  private List<ExpedienteElectronicoDTO> expedientes = new ArrayList<>();
  private ExpedienteElectronicoDTO selectedExpediente;
  @Autowired
  private Combobox tipoActuacion;
  @Autowired
  private Intbox anioSADE;
  @Autowired
  private Intbox numeroSADE;
  @Autowired
  private Textbox reparticionActuacion;
  @Autowired
  private Bandbox reparticionBusquedaUsuario;
  @Autowired
  private Listbox consultaExpedienteList;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private Window desarchivarEnvioWindow;
  @WireVariable(ConstantesServicios.CONSTANTESDB)
  private ConstantesDB constantesDB;
 
  private ExpedienteElectronicoService expedienteElectronicoService;
  private ExpedienteAsociadoService expedienteAsociadoService;

  private List<String> actuaciones;

  /**
   * Metodo que se ejecuta al cargar la vista
   */
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c); 
    this.expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    this.expedienteAsociadoService = (ExpedienteAsociadoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ASOCIADO_SERVICE);

    reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());

    loadComboActuaciones();

    this.binder = new AnnotateDataBinder(c);
    c.addEventListener(Events.ON_NOTIFY, new DesarchivarOnNotifyWindowListener(this));
  }

  /**
   * Busca y devuelve un expediente que se encuentre en estado de archivo.
   * 
   * @throws InterruptedException
   */
  public void onBuscarActuacion() throws InterruptedException {

    validarDatosBuscarExpediente();
    limpiarGrilla();
    String stTipoActuacion = (String) tipoActuacion.getSelectedItem().getValue();
    Integer stAnioSADE = anioSADE.getValue();
    Integer stNumeroSADE = numeroSADE.getValue();
    String stReparticionUsuario = reparticionBusquedaUsuario.getText();
    limpiarCampos();
    ExpedienteAsociadoEntDTO expedienteAsociadoPorFusion = expedienteAsociadoService
        .obtenerExpedienteAsociadoPorFusion(stNumeroSADE, stAnioSADE, true);

    if (expedienteAsociadoPorFusion != null
        && expedienteAsociadoPorFusion.getEsExpedienteAsociadoFusion()) {

      ExpedienteElectronicoDTO expediente = expedienteElectronicoService
          .buscarExpedienteElectronico(expedienteAsociadoPorFusion.getIdExpCabeceraTC());

      Messagebox.show(
          Labels.getLabel("ee.desarchivar.expediente.archivadoPorFusion",
              new String[] { expediente.getCodigoCaratula() }),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } else {

      ExpedienteElectronicoDTO expediente = expedienteElectronicoService
          .obtenerExpedienteElectronico(stTipoActuacion, stAnioSADE, stNumeroSADE,
              stReparticionUsuario);

      if (expediente != null && StringUtils.isNotEmpty(expediente.getIdWorkflow())) {
        if (expediente.getEstado() != null
            && expediente.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
          this.expedientes.add(expediente);
        } else if (expediente.getEstado().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)) {
          String[] param = new String[2];
          param[0] = "El expediente: ";
          param[1] = expediente.getCodigoCaratula();
          Messagebox.show(
              Labels.getLabel("ee.desarchivar.expediente.sinarchivarEESolicitudArchivo", param),
              Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.INFORMATION);
        } else {
          Messagebox.show(Labels.getLabel("ee.desarchivar.expediente.sinarchivar"),
              Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.INFORMATION);
        }
      } else {
        Messagebox.show(Labels.getLabel("ee.caratulas.expedientenoencotrado"),
            Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
      }

      BindingListModel expedientesModel = new BindingListModelList(this.expedientes, true);

      this.consultaExpedienteList.setModel(expedientesModel);
      this.binder.loadComponent(this.consultaExpedienteList);
    }
  }

  private void limpiarCampos() {
    anioSADE.setText("");
    numeroSADE.setText("");
    reparticionBusquedaUsuario.setText("");

  }

  /**
   * Abre el popup de la ventana que desarchiva el expediente.
   * 
   * @throws InterruptedException
   */
  public void onDesarchivar() throws InterruptedException {
    HashMap<String, Object> hm = new HashMap<>();

    hm.put("idExpedienteElectronico", selectedExpediente.getId());
    if (this.desarchivarEnvioWindow != null) {
      this.desarchivarEnvioWindow.invalidate();
      this.desarchivarEnvioWindow = (Window) Executions
          .createComponents("/pase/envioDesarchivar.zul", this.self, hm);
      this.desarchivarEnvioWindow.setPosition("center");
      this.desarchivarEnvioWindow.doModal();
    } else {
      Messagebox.show(Labels.getLabel("ee.general.imposibleIniciarVista"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Limpia la grilla del expediente encontrado.
   */
  private void limpiarGrilla() {
    expedientes.clear();
  }

  /**
   * Valida si los datos ingresados son correctos.
   */
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

    if (StringUtils.isEmpty(numeroSADE.getText())) {
      throw new WrongValueException(this.numeroSADE,
          Labels.getLabel("ee.historialpase.faltatnumero"));
    }

    if (StringUtils.isEmpty(reparticionBusquedaUsuario.getText())) {
      throw new WrongValueException(this.reparticionBusquedaUsuario,
          Labels.getLabel("ee.historialpase.faltaReparcicion"));
    }
  }

  // Metodos setters y getters //

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

  public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
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

  final class DesarchivarOnNotifyWindowListener implements EventListener {
    private DesarchivarComposer composer;

    public DesarchivarOnNotifyWindowListener(DesarchivarComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.refresh();
      }
    }

  }

  public void refresh() {
    limpiarGrilla();
    this.binder.loadComponent(consultaExpedienteList);

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
