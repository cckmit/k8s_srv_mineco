package com.egoveris.deo.web.satra.consulta;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.service.TareaJBPMService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaTareasComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;
  private Window consultaTareasWindow;
  private Datebox fechaDesde;
  private Datebox fechaHasta;
  private Combobox taskBusqueda;
  private Combobox usuarioDestBusqueda;
  private Bandbox familiaEstructuraTree;

  private HashMap<String, Object> parametrosConsulta;
  private Integer cantidadMeses;
  private TipoDocumentoDTO tipoDocumentoSeleccionado;

  @WireVariable("tareaJBPMServiceImpl")
  private TareaJBPMService tareaJBPMService;
  private AppProperty appProperty;
  @WireVariable("usuarioServiceImpl")
  private IUsuarioService usuarioService;

  public enum Tarea {
    CONFECCIONAR(Constantes.ACT_CONFECCIONAR), FIRMAR_DOC(Constantes.ACT_FIRMAR), REVISAR_DOC(
        Constantes.ACT_REVISAR), REVISAR_FIRMA_CONJUNTA(
            Constantes.ACT_REVISAR_FIRMA_CONJUNTA), FIRMAR_PORTAFIRMA(
                Constantes.ACT_FIRMAR_PORTA_FIRMA), RECHAZADO(Constantes.TASK_RECHAZADO);

    final String value;

    Tarea(String value) {
      this.value = value;
    }
  };

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    this.cantidadMeses = appProperty.getInt("tareas.busqueda.tiempo");
    this.parametrosConsulta = new HashMap<String, Object>();
    this.parametrosConsulta = (HashMap<String, Object>) Executions.getCurrent().getArg();
    Executions.getCurrent().getDesktop().setAttribute("porTarea", true);
    this.taskBusqueda.setModel(new ListModelList(cargarComboTareas()));
    usuarioDestBusqueda.setModel(
        ListModels.toListSubModel(new ListModelList(this.usuarioService.obtenerUsuarios()),
            new ConsultaTareasUserComparator(), 30));
    familiaEstructuraTree.addEventListener(Events.ON_NOTIFY,
        new ConsultaTareasComposerListener(this));
  }

  public void onClick$buscar() {

    Date fecha1 = new Date();
    Date fecha2 = new Date();
    Date fechaLimite = new Date();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fechaLimite);
    calendar.set(Calendar.MONTH, calendar.get(2) - this.cantidadMeses);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    fechaLimite = calendar.getTime();

    if (this.fechaDesde.getValue() == null && this.fechaHasta.getValue() != null) {
      throw new WrongValueException(fechaDesde,
          Labels.getLabel("gedo.consultaTareas.completar.fechaDesde"));
    }
    if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() == null) {
      throw new WrongValueException(fechaHasta,
          Labels.getLabel("gedo.consultaTareas.completar.fechaHasta"));
    }
    if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() != null
        && this.fechaDesde.getValue().after(this.fechaHasta.getValue())) {
      throw new WrongValueException(fechaDesde,
          Labels.getLabel("gedo.consultaTareas.fechas.erroneas"));
    }

    if (this.fechaDesde.getValue() != null && fechaDesde.getValue().before(fechaLimite)) {
      throw new WrongValueException(fechaDesde,
          Labels.getLabel("gedo.consultaTareas.fechaDesde.invalida"));
    }

    if (fechaDesde.getValue() != null && fechaHasta.getValue() != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(fechaHasta.getValue());
      c.add(Calendar.DATE, 1);
      fechaHasta.setValue(c.getTime());
      fecha1 = (Date) fechaDesde.getValue();
      fecha2 = (Date) fechaHasta.getValue();
    } else {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(fecha1);
      c1.set(Calendar.MONTH, c1.get(2) - this.cantidadMeses);
      fecha1 = c1.getTime();

      Calendar c2 = Calendar.getInstance();
      c2.setTime(fecha2);
      c2.add(Calendar.DATE, 1);
      fecha2 = c2.getTime();
    }

    parametrosConsulta.put("fechaDesde", fecha1);
    parametrosConsulta.put("fechaHasta", fecha2);
    if (usuarioDestBusqueda.getSelectedItem() != null) {
      Usuario userDestino = (Usuario) usuarioDestBusqueda.getSelectedItem().getValue();
      parametrosConsulta.put("usuarioDestino", userDestino.getUsername());
    } else {
      parametrosConsulta.put("usuarioDestino", null);
    }
    parametrosConsulta.put("tipoTarea",
        taskBusqueda.getSelectedItem() != null ? taskBusqueda.getSelectedItem().getValue() : null);
    parametrosConsulta.put("tipoDocumento", getTipoDocumentoSeleccionado() != null
        ? getTipoDocumentoSeleccionado().getNombre() : null);
    parametrosConsulta.put("listaTareas",
        this.tareaJBPMService.buscarTareasPorUsuarioInvolucrado(parametrosConsulta));
    parametrosConsulta.put("totalTareas",
        this.tareaJBPMService.cantidadTotalTareasPorUsuarioInvolucrado(parametrosConsulta));
    parametrosConsulta.put("tipoBusqueda", "porTarea");
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), parametrosConsulta));
    this.consultaTareasWindow.detach();
  }

  private List<String> cargarComboTareas() {
    List<String> listaTareas = new ArrayList<String>();
    for (Tarea tarea : Tarea.values()) {
      listaTareas.add(tarea.value);
    }
    return listaTareas;
  }

  public void cargarTipoDocumento(TipoDocumentoDTO data) {
    this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
    this.familiaEstructuraTree.close();
    setTipoDocumentoSeleccionado(data);
  }

  public Combobox getTaskBusqueda() {
    return taskBusqueda;
  }

  public void setTaskBusqueda(Combobox taskBusqueda) {
    this.taskBusqueda = taskBusqueda;
  }

  public TipoDocumentoDTO getTipoDocumentoSeleccionado() {
    return tipoDocumentoSeleccionado;
  }

  public void setTipoDocumentoSeleccionado(TipoDocumentoDTO tipoDocumentoSeleccionado) {
    this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
  }

}

final class ConsultaTareasComposerListener implements EventListener {
  private ConsultaTareasComposer composer;

  public ConsultaTareasComposerListener(ConsultaTareasComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        if ((event.getData() instanceof TipoDocumentoDTO)) {
          TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
          this.composer.cargarTipoDocumento(data);

        }
      }
    }
  }
}
