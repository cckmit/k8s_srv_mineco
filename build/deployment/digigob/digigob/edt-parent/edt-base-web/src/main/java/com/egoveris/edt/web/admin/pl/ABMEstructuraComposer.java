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
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.estructura.IEstructuraHelper;
import com.egoveris.edt.base.service.estructura.IEstructuraService;
import com.egoveris.edt.base.util.zk.UtilsDate;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMEstructuraComposer extends BaseComposer {

  private static final long serialVersionUID = -159758911077524788L;

  private static final Logger logger = LoggerFactory.getLogger(ABMEstructuraComposer.class);

  @Autowired
  protected AnnotateDataBinder binder;
  private Vbox vbox_headerAlta;
  private Vbox vbox_headerVer;
  private Vbox vbox_headerModificar;
  private Textbox txbx_codigo;
  private Textbox txbx_descripcion;
  private Datebox dbx_vigenciaDesde;
  private Datebox dbx_vigenciaHasta;
  private Hbox hbox_visu;
  private Hbox hbox_modi;

  private Hbox hbox_borrar;
  private Hbox hbox_botones;
  private Label mensaje_confirmacion;

  private Label label_codigo;
  private Label label_nombre;
  private Label label_videnciaDesde;
  private Label label_vigenciaHasta;

  private IEstructuraService estructuraService;
  private List<EstructuraDTO> listaResultadoEstructuras;

  private EstructuraDTO estructura;
  private IEstructuraHelper estructuraHelper;
  private EstructuraDTO estructuraSeleccionada = null;
  private EstructuraDTO estructuraSeleccionada_modificar = null;
  private Boolean edicion = false;
  private Boolean visualizacion = false;
  private Boolean borrar = false;
  private Map<?, ?> parametros;
  private Listbox lstbx_estructura = new Listbox();
  
  private String userName;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    edicion = false;
    visualizacion = false;
    borrar = false;
    estructuraSeleccionada = null;
    estructuraSeleccionada_modificar = null;

    parametros = Executions.getCurrent().getArg();
    this.estructura = new EstructuraDTO();
    dbx_vigenciaDesde.setValue(new Date());
    dbx_vigenciaHasta.setValue(new Date());
    traerServicios();
    lstbx_estructura = (Listbox) parametros.get(ConstantesSesion.COMPONENTE);
    estructuraSeleccionada = (EstructuraDTO) parametros.get(ConstantesSesion.KEY_ESTRUCTURA);
    estructuraSeleccionada_modificar = (EstructuraDTO) parametros
        .get(ConstantesSesion.KEY_ESTRUCTURA_MODIFICAR);

    visualizacion = (Boolean) parametros.get(ConstantesSesion.KEY_VISUALIZAR);
    edicion = (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR);
    borrar = (Boolean) parametros.get(ConstantesSesion.KEY_BORRAR);

    if (estructuraSeleccionada != null && Boolean.TRUE.equals(visualizacion)) {
      cargarComponentesVisualizacion(visualizacion);
    }

    if (estructuraSeleccionada_modificar != null && Boolean.TRUE.equals(edicion)) {
      cargarComponentesModificacion(edicion);
    }

    userName = (String)getSession().getAttribute(ConstantesSesion.SESSION_USERNAME);
  }

  private void traerServicios() {
    estructuraService = (IEstructuraService) SpringUtil.getBean("estructuraService");
    estructuraHelper = (IEstructuraHelper) SpringUtil.getBean("estructuraHelper");
  }

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos(true)) {

      try {
        guardarEstructura();
        // refresco la lista de estructuras ya que se agrego una nueva
        listaResultadoEstructuras = estructuraHelper.obtenerTodasEstructuras();
        estructuraHelper.cachearListaEstructuras();
        lstbx_estructura.setModel(new BindingListModelList(listaResultadoEstructuras, true));
        Messagebox.show(Labels.getLabel("eu.abmEstructura.label.savedOk"),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    }

  }

  public void onClick$btn_modificar() throws InterruptedException {
    if (validarCampos(false)) {

      try {
        guardarModificar();
        Messagebox.show(Labels.getLabel("eu.abmEstructura.label.modifyOk"),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    }

  }

  private void guardarEstructura() throws NegocioException, InterruptedException {

    armarObjetoEstructuraParaPersistir();

    estructuraService.guardarEstructura(estructura);
    listaResultadoEstructuras = estructuraService.listEstructuras();
    GestionEstructuraComposer composerVisualizar = new GestionEstructuraComposer();
    composerVisualizar.setListaResultadoEstructuras(listaResultadoEstructuras);

    estructuraHelper.cachearListaEstructuras();

    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  private void guardarModificar() throws NegocioException, InterruptedException {

    armarObjetoEstructuraParaPersistirModificar();

    estructuraService.guardarEstructura(estructura);
    GestionEstructuraComposer composerVisualizar = (GestionEstructuraComposer) Components
        .getComposer(this.self.getParent());
    composerVisualizar.refrescarListado();
    estructuraHelper.cachearListaEstructuras();

    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  /**
   * Armar objeto estructura para persistir.
   */
  private void armarObjetoEstructuraParaPersistir() {
    Integer valorcodigo = new Integer(Integer.parseInt(txbx_codigo.getValue().trim()));
    estructura.setCodigoEstructura(valorcodigo);
    estructura.setVigenciaDesde(UtilsDate.formatoFechaInicio(dbx_vigenciaDesde.getValue()));
    estructura.setVigenciaHasta(UtilsDate.formatoFechaFin(dbx_vigenciaHasta.getValue()));
    estructura.setNombreEstructura(txbx_descripcion.getValue());
    estructura.setEstadoRegistro(true);
    estructura.setEstructuraPoderEjecutivo(null);
    estructura.setUsuarioModificacion(null);
    estructura.setFechaModificacion(null);
    estructura.setUsuarioCreacion(userName);
    estructura.setFechaCreacion(new Date());
    estructura.setVersion(1);
    estructura.setGeneraAls("N");
  }

	/**
	 * Armar objeto estructura para persistir modificar.
	 */
	private void armarObjetoEstructuraParaPersistirModificar() {
		Integer valorcodigo = new Integer(Integer.parseInt(txbx_codigo.getValue().trim()));
		estructura.setId(estructuraSeleccionada_modificar.getId());
		estructura.setCodigoEstructura(valorcodigo);
		estructura.setNombreEstructura(txbx_descripcion.getValue());
    estructura.setVigenciaDesde(UtilsDate.formatoFechaInicio(dbx_vigenciaDesde.getValue()));
    estructura.setVigenciaHasta(UtilsDate.formatoFechaFin(dbx_vigenciaHasta.getValue()));
		estructura.setGeneraAls("N");
		estructura.setVersion(estructuraSeleccionada_modificar.getVersion() + 1);
		estructura.setFechaModificacion(new Date());
    estructura.setFechaCreacion(estructuraSeleccionada_modificar.getFechaCreacion());
		estructura.setUsuarioModificacion(userName);
		estructura.setUsuarioCreacion(estructuraSeleccionada_modificar.getUsuarioCreacion());
		estructura.setEstadoRegistro(estructuraSeleccionada_modificar.getEstadoRegistro());
		estructura.setEstructuraPoderEjecutivo(estructuraSeleccionada_modificar.getEstructuraPoderEjecutivo());
	}

  public boolean validarCampos(Boolean esAlta) {
    if (txbx_codigo.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.estructura.codigo"));
    }

    if (!NumberUtils.isNumber(txbx_codigo.getValue().trim())) {

      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.isNumber.estructura.codigo"));
    }
    if (esAlta) {
      EstructuraDTO estructuraValidador = estructuraService
          .getEstructuraByCodigo(Integer.parseInt(txbx_codigo.getValue().trim()));
      if (estructuraValidador != null) {
        throw new WrongValueException(this.txbx_codigo,
            Labels.getLabel("eu.adminSade.validacion.altaEstructura.codigoExistente"));
      }
    }
    if (dbx_vigenciaDesde.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaDesde,
          Labels.getLabel("eu.adminSade.validacion.estructura.fecha"));
    }
    if (dbx_vigenciaHasta.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaHasta,
          Labels.getLabel("eu.adminSade.validacion.estructura.fecha"));
    }

    if (txbx_descripcion.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_descripcion,
          Labels.getLabel("eu.adminSade.validacion.estructura.nombre"));
    }

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
            Labels.getLabel("eu.adminSade.validacion.estructura.fechaDesdeMenorAActual"));
      }
      // comparo si las dos fechas distintas, para realizar las demás
      // validaciones
      diferencia = fechaDesde.compareTo(fechahasta);
      if (diferencia != 0) {
        // valido que la fecha de inicio sea menor a la de finalización
        if (diferencia == 1) {
          throw new WrongValueException(this.dbx_vigenciaDesde,
              Labels.getLabel("eu.adminSade.validacion.estructura.fechaDesdeMayorAFechaHasta"));
        }
      }
    } catch (ParseException e) {
      logger.error("error en fecha vigencia metodo validar " + e.toString());
      return false;
    }

    return true;
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_salir_modi() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_salir_borrar() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onBlur$txbx_codigo() throws InterruptedException {

    if (!NumberUtils.isNumber(txbx_codigo.getValue().trim())) {

      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.isNumber.estructura.codigo"));
    }

    txbx_codigo.setValue(txbx_codigo.getValue().toUpperCase());
    Integer valorcodigo = new Integer(Integer.parseInt(txbx_codigo.getValue().trim()));
    this.estructura.setCodigoEstructura(valorcodigo);
    this.binder.loadComponent(txbx_codigo);
  }

  /* configuracion de pantalla agregarEstructura.zul en modo Visualizacion */
  private void cargarComponentesVisualizacion(boolean dato) {
    txbx_codigo.setValue(estructuraSeleccionada.getCodigoEstructura().toString());
    txbx_descripcion.setValue(estructuraSeleccionada.getNombreEstructura());
    dbx_vigenciaDesde.setValue(estructuraSeleccionada.getVigenciaDesde());
    dbx_vigenciaHasta.setValue(estructuraSeleccionada.getVigenciaHasta());

    txbx_codigo.setDisabled(dato);
    txbx_descripcion.setDisabled(dato);
    dbx_vigenciaDesde.setDisabled(dato);
    dbx_vigenciaHasta.setDisabled(dato);
    hbox_botones.setVisible(!dato);
    hbox_visu.setVisible(dato);
    hbox_modi.setVisible(!dato);

    binder.loadAll();
  }

  private void cargarComponentesModificacion(boolean dato) {

    txbx_codigo.setValue(estructuraSeleccionada_modificar.getCodigoEstructura().toString());
    txbx_descripcion.setValue(estructuraSeleccionada_modificar.getNombreEstructura());
    dbx_vigenciaDesde.setValue(estructuraSeleccionada_modificar.getVigenciaDesde());
    dbx_vigenciaHasta.setValue(estructuraSeleccionada_modificar.getVigenciaHasta());

    txbx_codigo.setVisible(dato);
    txbx_codigo.setReadonly(dato);
    txbx_descripcion.setVisible(dato);
    dbx_vigenciaDesde.setVisible(dato);
    dbx_vigenciaHasta.setVisible(dato);
    hbox_botones.setVisible(!dato);
    hbox_visu.setVisible(!dato);
    hbox_modi.setVisible(dato);

    binder.loadAll();
  }

  public Textbox getTxbx_codigo() {
    return txbx_codigo;
  }

  public void setTxbx_codigo(Textbox txbx_codigo) {
    this.txbx_codigo = txbx_codigo;
  }

  public Textbox getTxbx_descripcion() {
    return txbx_descripcion;
  }

  public void setTxbx_descripcion(Textbox txbx_descripcion) {
    this.txbx_descripcion = txbx_descripcion;
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

}
