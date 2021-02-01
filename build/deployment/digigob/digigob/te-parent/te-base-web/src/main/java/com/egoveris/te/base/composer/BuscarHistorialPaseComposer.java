package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
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
public class BuscarHistorialPaseComposer extends EEGenericForwardComposer {

  private static final long serialVersionUID = 7099215570026597987L;

  @WireVariable(ConstantesServicios.AUDITORIA_SERVICE)
  private IAuditoriaService auditoriaService;

  private List<HistorialOperacionDTO> pases = new ArrayList<>();
  
  private DozerBeanMapper mapper = new DozerBeanMapper();

  private HistorialOperacionDTO selectedPase;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private Window detalleView;
  @Autowired
  private Textbox reparticionActuacion;
  @Autowired
  private Listbox consultaPasesList;
  @Autowired
  private Intbox anioSADE;
  @Autowired
  private Intbox numeroSADE;
  @Autowired
  private Combobox tipoExpediente;
  @Autowired
  private Bandbox reparticionBusquedaSADE;

  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialOperacionService;

  private ExpedienteElectronicoDTO expedienteElectronico;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.CONSTANTESDB)
  private ConstantesDB constantesDB;

  private List<String> actuaciones;

  public void onBuscarHistorial() throws InterruptedException {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    String fechaActual = sdf.format(new Date());
    String anioFormateado = fechaActual.substring(6, 10);

    int anioActual = Integer.parseInt(anioFormateado);

    Integer anioValido = Integer.valueOf(anioActual);

    if (this.anioSADE.getValue() == null) {
      throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.historialpase.faltaanio"));
    }

    if (this.anioSADE.getValue() > anioValido) {

      throw new WrongValueException(this.anioSADE,
          Labels.getLabel("ee.historialpase.anioInvalido"));
    }

    if (((this.numeroSADE.getValue() == null) || (this.numeroSADE.getValue().equals("")))) {
      throw new WrongValueException(this.numeroSADE,
          Labels.getLabel("ee.historialpase.faltatnumero"));
    }

    if (((this.reparticionBusquedaSADE.getValue() == null)
        || (this.reparticionBusquedaSADE.getValue().equals("")))) {
      throw new WrongValueException(this.reparticionBusquedaSADE,
          Labels.getLabel("ee.historialpase.faltaReparcicion"));
    }

    this.expedienteElectronico = expedienteElectronicoService.obtenerExpedienteElectronico(
        tipoExpediente.getValue().toString(), anioSADE.getValue(), numeroSADE.getValue(),
        this.reparticionBusquedaSADE.getValue().toString().trim());

    this.pases =  historialOperacionService
        .buscarHistorialporExpediente(expedienteElectronico.getId());

    // Auditoria de consulta
    grabarAuditoriaDeConsulta(tipoExpediente.getValue().toString(), anioSADE.getValue(),
        numeroSADE.getValue(), this.reparticionActuacion.getValue().trim(),
        this.reparticionBusquedaSADE.getValue().trim());

    if (this.pases.size() < 1) {

      Messagebox.show(Labels.getLabel("ee.caratulas.historial.expedienteNoExiste"),
          Labels.getLabel("ee.caratulas.historial.informacion.caratulaNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }

    binder.loadComponent(consultaPasesList);
  }

  public void onMostrarDetalle() throws InterruptedException {
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("pase", selectedPase);
    hm.put("ee", expedienteElectronico);
    if (this.detalleView != null) {
      this.detalleView.invalidate();
      this.detalleView = (Window) Executions.createComponents("/inbox/detalleExpediente.zul",
          this.self, hm);
      this.detalleView.setPosition("center");
      this.detalleView.doModal();
    } else {
      Messagebox.show(Labels.getLabel("ee.general.imposibleIniciarVista"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void setTipoExpediente(Combobox tipoExpediente) {
    this.tipoExpediente = tipoExpediente;
  }

  public Combobox getTipoExpediente() {
    return tipoExpediente;
  }

  public List<HistorialOperacionDTO> getPases() {
    return pases;
  }

  public void setPases(List<HistorialOperacionDTO> pases) {
    this.pases = pases;
  }

  public HistorialOperacionDTO getSelectedPase() {
    return selectedPase;
  }

  public void setSelectedPase(HistorialOperacionDTO selectedPase) {
    this.selectedPase = selectedPase;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public Textbox getReparticionActuacion() {
    return reparticionActuacion;
  }

  public void setReparticionActuacion(Textbox reparticionActuacion) {
    this.reparticionActuacion = reparticionActuacion;
  }

  public Listbox getConsultaPasesList() {
    return consultaPasesList;
  }

  public void setConsultaPasesList(Listbox consultaPasesList) {
    this.consultaPasesList = consultaPasesList;
  }

  public void onCreate$historialPasesWindows(Event event) {
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

  }

  public void onLimpiarDatos() {

    limpiarFormulario();
    limpiarGrillaPases();
    this.binder.loadComponent(consultaPasesList);

  }

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

    comp.addEventListener(Events.ON_NOTIFY, new BuscarHistorialPaseOnNotifyWindowListener(this));
    
    reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());

    loadComboActuaciones();

    this.binder.loadAll();
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

  public Bandbox getReparticionBusquedaSADE() {
    return reparticionBusquedaSADE;
  }

  public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
    this.reparticionBusquedaSADE = reparticionBusquedaSADE;
  }

  public void limpiarFormulario() {

    anioSADE.setValue(null);
    numeroSADE.setValue(null);
    reparticionBusquedaSADE.setValue(null);
    anioSADE.setFocus(true);
  }

  public void limpiarGrillaPases() {

    this.pases.removeAll(getPases());
  }

  public void refreshPases() {
    this.binder.loadComponent(this.consultaPasesList);
  }

  final class BuscarHistorialPaseOnNotifyWindowListener implements EventListener {
    private BuscarHistorialPaseComposer composer;

    public BuscarHistorialPaseOnNotifyWindowListener(
        BuscarHistorialPaseComposer buscarHistorialPaseComposer) {
      this.composer = buscarHistorialPaseComposer;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {

        limpiarFormulario();
        limpiarGrillaPases();
        this.composer.refreshPases();

      }
    }

  }

  public void grabarAuditoriaDeConsulta(String tipoActuacion, Integer anioActuacion,
      Integer numeroActuacion, String reparticionActuacion, String reparticionUsuario) {

    AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

    auditoriaDeConsulta.setTipoActuacion(tipoActuacion);
    auditoriaDeConsulta.setAnioActuacion(anioActuacion);
    auditoriaDeConsulta.setNumeroActuacion(numeroActuacion);
    auditoriaDeConsulta.setReparticionActuacion(reparticionActuacion);
    auditoriaDeConsulta.setReparticionUsuario(reparticionUsuario);
    String usuario = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    auditoriaDeConsulta.setUsuario(usuario);
    auditoriaDeConsulta.setFechaConsulta(new Date());

    AuditoriaDeConsultaDTO auditoriDTO = mapper.map(auditoriaDeConsulta, AuditoriaDeConsultaDTO.class);
    
    auditoriaService.grabarAuditoriaDeConsulta(auditoriDTO);
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
