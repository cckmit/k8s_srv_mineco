package com.egoveris.edt.web.admin.pl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NumberUtils;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionAudiDTO;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.base.service.actuacion.IActuacionAudiService;
import com.egoveris.edt.base.service.actuacion.IActuacionHelper;
import com.egoveris.edt.base.service.actuacion.IActuacionService;
import com.egoveris.edt.base.util.zk.UtilsDate;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class ABMActuacionComposer extends BaseComposer {

  private static final long serialVersionUID = -159758911077524788L;

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ABMEstructuraComposer.class);
  
  /** The Constant AGREGAR_ACTUACION_ZUL. */
  public static final String AGREGAR_ACTUACION_ZUL = "/administrator/agregarActuacion.zul";

  // componentes
  @Autowired
  protected AnnotateDataBinder binder;
  private Vbox vbox_headerAlta;
  private Vbox vbox_headerVer;
  private Vbox vbox_headerModificar;
  private Grid gr_altaActuacion;
  private Textbox txbx_codigo;
  private Textbox txbx_nombre;
  private Datebox dbx_vigenciaDesde;
  private Datebox dbx_vigenciaHasta;

  // private Checkbox chkbx_deshabilitado_papel;
  // private Checkbox chkbx_es_documento;
  // private Textbox txbx_version;
  // private Textbox txbx_jerarquia;
  private Combobox cmbBox_desglosado;
  private Combobox cmbBox_anulado;
  private Combobox cmbBox_agregado;
  private Combobox cmbBox_incorporado;
  private Combobox cmbBox_iniciaActuacion;
  private Row row_usuarioCreacion;
  private Row row_fechaCreacion;
  private Row row_usuarioModificacion;
  private Row row_fechaModificacion;
  private Textbox txbx_usuarioCreacion;
  private Datebox dbx_fechaCreacion;
  private Textbox txbx_usuarioModificacion;
  private Datebox dbx_fechaModificacion;

  // botones
  private Hbox hbox_visu;
  private Hbox hbox_modi;
  private Hbox hbox_borrar;
  private Hbox hbox_botones;

  // etiquetas
  private Label mensaje_confirmacion;
  private IActuacionAudiService actuacionAudiService;
  private IActuacionService actuacionService;
  private List<ActuacionDTO> listaResultadoActuaciones;

  private ActuacionAudiDTO actuacionAudi;
  private ActuacionDTO actuacion;
  private IActuacionHelper actuacionHelper;
  private ActuacionDTO actuacionSeleccionada = null;
  private ActuacionDTO actuacionSeleccionada_modificar = null;
  private ActuacionDTO actuacionSeleccionada_borrar = null;
  private Boolean edicion = false;
  private Boolean visualizacion = false;
  private Boolean borrar = false;
  private Map<?, ?> parametros;
  //lista de actuaciones que es traida desde el otro composer (GestionActuacionComposer) para ser seteada desde aca
  private Listbox lstbx_actuacion = new Listbox(); 

  // Version 3.5.0
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    edicion = false;
    visualizacion = false;
    borrar = false;
    actuacionSeleccionada = null;
    actuacionSeleccionada_modificar = null;
    actuacionSeleccionada_borrar = null;

    parametros = Executions.getCurrent().getArg();
    this.actuacion = new ActuacionDTO();
    dbx_vigenciaDesde.setValue(new Date());
    dbx_vigenciaHasta.setValue(new Date());
    traerServicios();
		// obtengo el componente que muestra la lista en la pantalla principal
		lstbx_actuacion = (Listbox) parametros.get(ConstantesSesion.COMPONENTE_ACTUACION);
		// obtengo actuacion seleccionada para visualizar
		actuacionSeleccionada = (ActuacionDTO) parametros.get(ConstantesSesion.KEY_ACTUACION);
		// obtengo actuacion seleccionada para modificar
		actuacionSeleccionada_modificar = (ActuacionDTO) parametros.get(ConstantesSesion.KEY_ACTUACION_MODIFICAR);
		// obtengo actuacion seleccionada para borrar
		actuacionSeleccionada_borrar = (ActuacionDTO) parametros.get(ConstantesSesion.KEY_ACTUACION_BORRAR);
    visualizacion = (Boolean) parametros.get(ConstantesSesion.KEY_VISUALIZAR_ACTUACION);
    edicion = (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR_ACTUACION);
    borrar = (Boolean) parametros.get(ConstantesSesion.KEY_BORRAR_ACTUACION);

    if (actuacionSeleccionada != null && Boolean.TRUE.equals(visualizacion)) {
      cargarComponentesVisualizacion(visualizacion);
    }

    if (actuacionSeleccionada_modificar != null && Boolean.TRUE.equals(edicion)) {
      cargarComponentesModificacion(edicion);
    }

    if (actuacionSeleccionada_borrar != null && Boolean.TRUE.equals(borrar)) {
      cargarComponentesBorrado();
    }

  }

  private void traerServicios() {
    actuacionService = (IActuacionService) SpringUtil.getBean("actuacionService");
    actuacionHelper = (IActuacionHelper) SpringUtil.getBean("actuacionHelper");
    actuacionAudiService = (IActuacionAudiService) SpringUtil.getBean("actuacionAudiService");
  }

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos(false)) {

      try {
        guardarActuacion();
        // refresco la lista de actuaciones ya que se agrego una nueva
        actuacionHelper.cachearListaActuaciones();
        listaResultadoActuaciones = actuacionHelper.obtenerTodasActuaciones();
        lstbx_actuacion.setModel(new BindingListModelList(listaResultadoActuaciones, true));
        
        Messagebox.show(Labels.getLabel("eu.adminSade.actuacion.savedOk"),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    }

  }

  public void onClick$btn_modificar() throws InterruptedException {
    if (validarCampos(true)) {

      try {
        guardarModificar();
        
        // refresco la lista de actuaciones ya que se agrego una nueva
        actuacionHelper.cachearListaActuaciones();
        listaResultadoActuaciones = actuacionHelper.obtenerTodasActuaciones();
        lstbx_actuacion.setModel(new BindingListModelList(listaResultadoActuaciones, true));
        
        Messagebox.show(Labels.getLabel("eu.adminSade.actuacion.modifyOk"),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    }

  }

  public void onClick$btn_borrar() throws InterruptedException {

    try {
      guardarBorrar();
      // refresco la lista de actuacion ya que se agrego una nueva
      actuacionHelper.cachearListaActuaciones();
      listaResultadoActuaciones = actuacionHelper.obtenerTodasActuaciones();
      lstbx_actuacion.setModel(new BindingListModelList(listaResultadoActuaciones, true));
      
      Messagebox.show(Labels.getLabel("eu.adminSade.actuacion.bajaExitosa"),
          Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }

  }

  private void guardarActuacion() throws NegocioException, InterruptedException {
    armarObjetoActuacionParaPersistir();
    actuacionService.guardarActuacion(actuacion);
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  private void guardarModificar() throws NegocioException, InterruptedException {
    armarObjetoActuacionParaPersistirModificar();
    actuacionService.guardarActuacion(actuacion);
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  private void guardarBorrar() throws NegocioException, InterruptedException {
    actuacionSeleccionada_borrar.setEstadoRegistro(false);
    actuacionService.guardarActuacion(actuacionSeleccionada_borrar);
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  // armar objeto para guardar
  private void armarObjetoActuacionParaPersistir() {
    char iniciaActuacion;
    char incorporado;
    char agregado;
    char anulado;
    char desglosado;
    Date fechaActual = new Date();
    int esdocumento;

    if (null != cmbBox_iniciaActuacion.getSelectedItem()
        && cmbBox_iniciaActuacion.getSelectedItem().getLabel().contains("S")) {
      iniciaActuacion = 'S';
    } else {
      iniciaActuacion = 'N';
    }

    if (null != cmbBox_incorporado.getSelectedItem()
        && cmbBox_incorporado.getSelectedItem().getLabel().contains("S")) {
      incorporado = 'S';
    } else {
      incorporado = 'N';
    }
    if (null != cmbBox_agregado.getSelectedItem()
        && cmbBox_agregado.getSelectedItem().getLabel().contains("S")) {
      agregado = 'S';
    } else {
      agregado = 'N';
    }
    if (null != cmbBox_anulado.getSelectedItem()
        && cmbBox_anulado.getSelectedItem().getLabel().contains("S")) {
      anulado = 'S';
    } else {
      anulado = 'N';
    }
    if (null != cmbBox_desglosado.getSelectedItem()
        && cmbBox_desglosado.getSelectedItem().getLabel().contains("S")) {
      desglosado = 'S';
    } else {
      desglosado = 'N';
    }

  //  if (chkbx_es_documento.isChecked()) {
      esdocumento = 1;
  //  } else {
      esdocumento = 0;
  //  }

    actuacion.setCodigoActuacion(txbx_codigo.getValue().trim());
    actuacion.setNombreActuacion(txbx_nombre.getValue().trim());
    actuacion.setVigenciaDesde(UtilsDate.formatoFechaInicio(dbx_vigenciaDesde.getValue()));
    actuacion.setVigenciaHasta(UtilsDate.formatoFechaFin(dbx_vigenciaHasta.getValue()));
    actuacion.setIniciaActuacion(iniciaActuacion);
   // actuacion.setJerarquia(Integer.parseInt(txbx_jerarquia.getValue().trim()));
    actuacion.setIncorporado(incorporado);
    actuacion.setAgregado(agregado);
    actuacion.setAnulado(anulado);
    actuacion.setDesaglosado(desglosado);
    //actuacion.setVersion(Integer.parseInt(txbx_version.getValue().trim()));
    actuacion.setFechaCreacion(fechaActual);
    actuacion.setUsuarioCreacion(getCurrentUser().getUsername());
    actuacion.setUsuarioModificacion(null);
    actuacion.setFechaModificacion(null);
    actuacion.setEstadoRegistro(true);
    actuacion.setEsDocumento(esdocumento);
  //  actuacion.setDeshabilitadoPapel(chkbx_deshabilitado_papel.isChecked());
  }

  private void armarObjetoActuacionParaPersistirModificar() {
    char iniciaActuacion;
    char incorporado;
    char agregado;
    char anulado;
    char desglosado;
    Date fechaActual = new Date();
    int esdocumento;

    if (cmbBox_iniciaActuacion.getSelectedItem().getLabel().contains("S")) {
      iniciaActuacion = 'S';
    } else {
      iniciaActuacion = 'N';
    }

    if (cmbBox_incorporado.getSelectedItem().getLabel().contains("S")) {
      incorporado = 'S';
    } else {
      incorporado = 'N';
    }
    if (cmbBox_agregado.getSelectedItem().getLabel().contains("S")) {
      agregado = 'S';
    } else {
      agregado = 'N';
    }
    if (cmbBox_anulado.getSelectedItem().getLabel().contains("S")) {
      anulado = 'S';
    } else {
      anulado = 'N';
    }
    if (cmbBox_desglosado.getSelectedItem() != null && cmbBox_desglosado.getSelectedItem().getLabel().contains("S")) {
      desglosado = 'S';
    } else {
      desglosado = 'N';
    }

   // if (chkbx_es_documento.isChecked()) {
   //   esdocumento = 1;
   // } else {
      esdocumento = 0;
   // }

    actuacion.setId(actuacionSeleccionada_modificar.getId());
    actuacion.setCodigoActuacion(txbx_codigo.getValue().trim());
    actuacion.setNombreActuacion(txbx_nombre.getValue().trim());
    actuacion.setVigenciaDesde(UtilsDate.formatoFechaInicio(dbx_vigenciaDesde.getValue()));
    actuacion.setVigenciaHasta(UtilsDate.formatoFechaFin(dbx_vigenciaHasta.getValue()));
    actuacion.setIniciaActuacion(iniciaActuacion);
//    actuacion.setJerarquia(Integer.parseInt(txbx_jerarquia.getValue().trim()));
    actuacion.setIncorporado(incorporado);
    actuacion.setAgregado(agregado);
    actuacion.setAnulado(anulado);
    actuacion.setDesaglosado(desglosado);
 //   actuacion.setVersion(Integer.parseInt(txbx_version.getValue().trim()));
    actuacion.setUsuarioCreacion(actuacionSeleccionada_modificar.getUsuarioCreacion());
    actuacion.setFechaCreacion(actuacionSeleccionada_modificar.getFechaCreacion());
    actuacion.setUsuarioModificacion(getCurrentUser().getUsername());
    actuacion.setFechaModificacion(fechaActual);
    actuacion.setEstadoRegistro(true);
    actuacion.setEsDocumento(esdocumento);
   // actuacion.setDeshabilitadoPapel(chkbx_deshabilitado_papel.isChecked());

    actuacionAudi = new ActuacionAudiDTO();
    // llamar a servicio para crear una tupla en SADE_ACTUACION_AUDI
    actuacionAudi.setIdActuacion(actuacion.getId());
    actuacionAudi.setCodigoActuacion(actuacion.getCodigoActuacion());
    actuacionAudi.setNombreActuacion(actuacion.getNombreActuacion());
    actuacionAudi.setVigenciaDesde(actuacion.getVigenciaDesde());
    actuacionAudi.setVigenciaHasta(actuacion.getVigenciaHasta());
    actuacionAudi.setIniciaActuacion(actuacion.getIniciaActuacion());
    actuacionAudi.setJerarquia(actuacion.getJerarquia());
    actuacionAudi.setIncorporado(actuacion.getIncorporado());
    actuacionAudi.setAgregado(actuacion.getAgregado());
    actuacionAudi.setAnulado(actuacion.getAnulado());
    actuacionAudi.setDesaglosado(actuacion.getDesaglosado());
    actuacionAudi.setVersion(actuacion.getVersion());
    actuacionAudi.setFechaActualizacion(actuacion.getFechaModificacion());
    actuacionAudi.setFuncion_a('M');
    actuacionAudi.setUsuarioActualizacion(actuacion.getUsuarioModificacion());
    actuacionAudi.setIdSectorInternoA(200002077);
    actuacionAudiService.guardarActuacionAudi(actuacionAudi);
  }

  public boolean validarCampos(boolean modificar) {
    if (!modificar) {
      ActuacionDTO actuacionValidador = actuacionService
          .getActuacionByCodigo(txbx_codigo.getValue().trim());
      if (actuacionValidador != null) {
        throw new WrongValueException(this.txbx_codigo,
            Labels.getLabel("eu.adminSade.validacion.altaActuacion.codigoExistente"));
      }
    }

    if (txbx_codigo.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.actuacion.codigo"));
    }

    if (dbx_vigenciaDesde.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaDesde,
          Labels.getLabel("eu.adminSade.validacion.actuacion.fecha"));
    }
    if (dbx_vigenciaHasta.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaHasta,
          Labels.getLabel("eu.adminSade.validacion.actuacion.fecha"));
    }

    if (txbx_nombre.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_nombre,
          Labels.getLabel("eu.adminSade.validacion.actuacion.nombre"));
    }

//    if (!NumberUtils.isNumber(txbx_version.getValue().trim())) {
//      throw new WrongValueException(this.txbx_codigo,
//          Labels.getLabel("eu.adminSade.validacion.isNumber.actuacion.version"));
//    }

//    if (!NumberUtils.isNumber(txbx_jerarquia.getValue().trim())) {
//      throw new WrongValueException(this.txbx_jerarquia,
//          Labels.getLabel("eu.adminSade.validacion.isNumber.actuacion.jerarquia"));
//    }

    // validaciones para las fechas de vigencia
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date fechaDesde = dbx_vigenciaDesde.getValue();
      Date fechahasta = dbx_vigenciaHasta.getValue();
      String fechaHoyText = sdf.format(new Date());
      Date fechaHoy = sdf.parse(fechaHoyText);

      int diferencia = fechaDesde.compareTo(fechaHoy);

      // comparo la fecha de vigencia que sea mayor o igual a hoy
      if (diferencia == -1) {
        throw new WrongValueException(this.dbx_vigenciaDesde,
            Labels.getLabel("eu.adminSade.validacion.actuacion.fechaDesdeMenorAActual"));
      }
      // comparo si las dos fechas distintas, para realizar las demás
      // validaciones
      diferencia = fechaDesde.compareTo(fechahasta);
      if (diferencia == 1) {
        // valido que la fecha de inicio sea menor a la de finalización
        throw new WrongValueException(this.dbx_vigenciaDesde,
            Labels.getLabel("eu.adminSade.validacion.actuacion.fechaDesdeMayorAFechaHasta"));
      }
    } catch (ParseException e) {
      logger.error("error en fecha vigencia" + e.toString());
      return false;
    }

    return true;
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_salir_modi() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_salir_borrar() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onBlur$txbx_version() throws InterruptedException {

//    if (!NumberUtils.isNumber(txbx_version.getValue().trim())) {
//
//      throw new WrongValueException(this.txbx_version,
//          Labels.getLabel("eu.adminSade.validacion.isNumber.actuacion.version"));
//    }

//    txbx_version.setValue(txbx_version.getValue().toUpperCase());
//    Integer version = new Integer(Integer.parseInt(txbx_version.getValue().trim()));
//    this.actuacion.setVersion(version);
//    this.binder.loadComponent(txbx_version);
  }

//  public void onBlur$txbx_jerarquia() throws InterruptedException {
//
//    if (!NumberUtils.isNumber(txbx_jerarquia.getValue().trim())) {
//
//      throw new WrongValueException(this.txbx_jerarquia,
//          Labels.getLabel("eu.adminSade.validacion.isNumber.actuacion.jerarquia"));
//    }
//
//    txbx_jerarquia.setValue(txbx_jerarquia.getValue().toUpperCase());
//    Integer jerarquia = new Integer(Integer.parseInt(txbx_jerarquia.getValue().trim()));
//    this.actuacion.setJerarquia(jerarquia);
//    this.binder.loadComponent(txbx_jerarquia);
//  }

  /* configuracion de pantalla agregarEstructura.zul en modo Visualizacion */
  private void cargarComponentesVisualizacion(boolean dato) {

    boolean esDocumento;
    if (actuacionSeleccionada.getEsDocumento() == 1) {
      esDocumento = true;
    } else {
      esDocumento = false;
    }

    mensaje_confirmacion.setVisible(!dato);
    txbx_codigo.setValue(actuacionSeleccionada.getCodigoActuacion().toString());
    txbx_nombre.setValue(actuacionSeleccionada.getNombreActuacion());
    dbx_vigenciaDesde.setValue(actuacionSeleccionada.getVigenciaDesde());
    dbx_vigenciaHasta.setValue(actuacionSeleccionada.getVigenciaHasta());
    cmbBox_iniciaActuacion
        .setValue(Character.toString(actuacionSeleccionada.getIniciaActuacion()));
//    txbx_jerarquia.setValue(Integer.toString(actuacionSeleccionada.getJerarquia()));
    cmbBox_incorporado.setValue(Character.toString(actuacionSeleccionada.getIncorporado()));
    cmbBox_agregado.setValue(Character.toString(actuacionSeleccionada.getAgregado()));
    cmbBox_anulado.setValue(Character.toString(actuacionSeleccionada.getAnulado()));
    cmbBox_desglosado.setValue(Character.toString(actuacionSeleccionada.getDesaglosado()));
//    txbx_version.setValue(Integer.toString(actuacionSeleccionada.getVersion()));
//    chkbx_deshabilitado_papel.setChecked(actuacionSeleccionada.isDeshabilitadoPapel());
//    chkbx_es_documento.setChecked(esDocumento);
    txbx_usuarioCreacion.setValue(actuacionSeleccionada.getUsuarioCreacion());
    dbx_fechaCreacion.setValue(actuacionSeleccionada.getFechaCreacion());
    txbx_usuarioModificacion.setValue(actuacionSeleccionada.getUsuarioModificacion());
    dbx_fechaModificacion.setValue(actuacionSeleccionada.getFechaModificacion());

    binder.loadAll();
    txbx_codigo.setDisabled(dato);
    txbx_nombre.setDisabled(dato);
    dbx_vigenciaDesde.setDisabled(dato);
    dbx_vigenciaHasta.setDisabled(dato);
    cmbBox_iniciaActuacion.setDisabled(dato);
//    txbx_jerarquia.setDisabled(dato);
    cmbBox_incorporado.setDisabled(dato);
    cmbBox_agregado.setDisabled(dato);
    cmbBox_anulado.setDisabled(dato);
    cmbBox_desglosado.setDisabled(dato);
//    txbx_version.setDisabled(dato);
//    chkbx_deshabilitado_papel.setDisabled(dato);
//    chkbx_es_documento.setDisabled(dato);
    txbx_usuarioCreacion.setDisabled(dato);
    dbx_fechaCreacion.setDisabled(dato);
    txbx_usuarioModificacion.setReadonly(dato);
    dbx_fechaModificacion.setDisabled(dato);

    row_usuarioCreacion.setVisible(dato);
    row_fechaCreacion.setVisible(dato);
    row_usuarioModificacion.setVisible(dato);
    row_fechaModificacion.setVisible(dato);

    hbox_botones.setVisible(!dato);
    hbox_visu.setVisible(dato);
    hbox_modi.setVisible(!dato);

  }

  private void cargarComponentesModificacion(boolean dato) {
    mensaje_confirmacion.setVisible(!dato);
    boolean esDocumento;
    if (actuacionSeleccionada_modificar.getEsDocumento() == 1) {
      esDocumento = true;
    } else {
      esDocumento = false;
    }

    txbx_codigo.setValue(actuacionSeleccionada_modificar.getCodigoActuacion());
    txbx_nombre.setValue(actuacionSeleccionada_modificar.getNombreActuacion());
    dbx_vigenciaDesde.setValue(actuacionSeleccionada_modificar.getVigenciaDesde());
    dbx_vigenciaHasta.setValue(actuacionSeleccionada_modificar.getVigenciaHasta());
    cmbBox_iniciaActuacion
        .setValue(Character.toString(actuacionSeleccionada_modificar.getIniciaActuacion()));
//    txbx_jerarquia.setValue(Integer.toString(actuacionSeleccionada_modificar.getJerarquia()));
    cmbBox_incorporado
        .setValue(Character.toString(actuacionSeleccionada_modificar.getIncorporado()));
    cmbBox_agregado.setValue(Character.toString(actuacionSeleccionada_modificar.getAgregado()));
    cmbBox_anulado.setValue(Character.toString(actuacionSeleccionada_modificar.getAnulado()));
    cmbBox_desglosado
        .setValue(Character.toString(actuacionSeleccionada_modificar.getDesaglosado()));
//    txbx_version.setValue(Integer.toString(actuacionSeleccionada_modificar.getVersion()));

//    chkbx_deshabilitado_papel.setChecked(actuacionSeleccionada_modificar.isDeshabilitadoPapel());
//    chkbx_es_documento.setChecked(esDocumento);

    txbx_usuarioCreacion.setValue(actuacionSeleccionada_modificar.getUsuarioCreacion());
    dbx_fechaCreacion.setValue(actuacionSeleccionada_modificar.getFechaCreacion());

    binder.loadAll();

    txbx_codigo.setReadonly(dato);
    txbx_nombre.setReadonly(!dato);
    dbx_vigenciaDesde.setDisabled(!dato);
    dbx_vigenciaHasta.setDisabled(!dato);
    cmbBox_iniciaActuacion.setDisabled(!dato);
//    txbx_jerarquia.setReadonly(!dato);
    cmbBox_incorporado.setDisabled(!dato);
    cmbBox_agregado.setDisabled(!dato);
    cmbBox_anulado.setDisabled(!dato);
    cmbBox_desglosado.setDisabled(!dato);
//    txbx_version.setReadonly(!dato);
//    chkbx_deshabilitado_papel.setDisabled(!dato);
//    chkbx_es_documento.setDisabled(!dato);
    dbx_fechaCreacion.setDisabled(dato);
    txbx_usuarioCreacion.setReadonly(dato);

    row_usuarioCreacion.setVisible(!dato);
    row_fechaCreacion.setVisible(!dato);
    row_usuarioModificacion.setVisible(!dato);
    row_fechaModificacion.setVisible(!dato);

    hbox_botones.setVisible(!dato);
    hbox_visu.setVisible(!dato);
    hbox_modi.setVisible(dato);

  }

  /* configuracion de pantalla para borrar */
  private void cargarComponentesBorrado() {
    gr_altaActuacion.setVisible(false);
    hbox_botones.setVisible(false);
    hbox_visu.setVisible(false);
    hbox_modi.setVisible(false);
    hbox_borrar.setVisible(true);
    mensaje_confirmacion.setVisible(true);
    binder.loadAll();

  }
  
  public void onBlur$txbx_codigo(){
  	if(txbx_codigo.getValue()!=null) {
  		txbx_codigo.setValue(txbx_codigo.getValue().toUpperCase());
  	}
  }

  public Textbox getTxbx_codigo() {
    return txbx_codigo;
  }

  public void setTxbx_codigo(Textbox txbx_codigo) {
    this.txbx_codigo = txbx_codigo;
  }

  public Textbox getTxbx_descripcion() {
    return txbx_nombre;
  }

  public void setTxbx_descripcion(Textbox txbx_descripcion) {
    this.txbx_nombre = txbx_descripcion;
  }

  public Datebox getDbx_vigenciaHasta() {
    return dbx_vigenciaHasta;
  }

  public void setDbx_vigenciaHasta(Datebox dbx_vigenciaHasta) {
    this.dbx_vigenciaHasta = dbx_vigenciaHasta;
  }

  public Datebox getDbx_vigenciaDesde() {
    return dbx_vigenciaDesde;
  }

  public void setDbx_vigenciaDesde(Datebox dbx_vigenciaDesde) {
    this.dbx_vigenciaDesde = dbx_vigenciaDesde;
  }

  public Hbox getHbox_visu() {
    return hbox_visu;
  }

  public void setHbox_visu(Hbox hbox_visu) {
    this.hbox_visu = hbox_visu;
  }

  public Hbox getHbox_botones() {
    return hbox_botones;
  }

  public void setHbox_botones(Hbox hbox_botones) {
    this.hbox_botones = hbox_botones;
  }

  public Vbox getVbox_headerVer() {
    return vbox_headerVer;
  }

  public void setVbox_headerVer(Vbox vbox_headerVer) {
    this.vbox_headerVer = vbox_headerVer;
  }

  public Vbox getVbox_headerModificar() {
    return vbox_headerModificar;
  }

  public void setVbox_headerModificar(Vbox vbox_headerModificar) {
    this.vbox_headerModificar = vbox_headerModificar;
  }

  public Vbox getVbox_headerAlta() {
    return vbox_headerAlta;
  }

  public void setVbox_headerAlta(Vbox vbox_headerAlta) {
    this.vbox_headerAlta = vbox_headerAlta;
  }

  public Grid getGr_altaActuacion() {
    return gr_altaActuacion;
  }

  public void setGr_altaActuacion(Grid gr_altaActuacion) {
    this.gr_altaActuacion = gr_altaActuacion;
  }
}