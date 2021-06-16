package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.CalleDTO;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class DatosSectorComposer extends BaseComposer {

  private static Logger logger = LoggerFactory.getLogger(DatosSectorComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = 3210439740488389982L;

  @Autowired
  protected AnnotateDataBinder binder;

  private ISectorService sectorService;

  private ILdapService iLdapService;

  private IUsuarioService usuarioService;

  @Autowired
  private List<CalleDTO> listasCallesSeleccionada;
  @Autowired
  private ReparticionDTO reparticionSeleccionada;
  @Autowired
  private CalleDTO calleSeleccionada;
  @Autowired
  private Textbox txbx_sector;
  @Autowired
  private Textbox txbx_descripcion;
  @Autowired
  private Textbox txbx_calle;
  @Autowired
  private Textbox txbx_nroCalle;
  @Autowired
  private Datebox datebox_vigenciaDesde;
  @Autowired
  private Datebox datebox_vigenciaHasta;
  @Autowired
  private Textbox txbx_telefono;
  @Autowired
  private Textbox txbx_piso;
  @Autowired
  private Textbox txbx_fax;
  @Autowired
  private Textbox txbx_oficina;
  @Autowired
  private Textbox txbx_email;
  @Autowired
  private Combobox cmbox_sectorMesa;
  @Autowired
  private Toolbarbutton btn_guardarSector;
  @Autowired
  private Toolbarbutton btn_salir;
  @Autowired
  private Toolbarbutton btn_cancelar;

  private SectorDTO sector;

  private Map<?, ?> parametros;

  private Boolean passwordOk = false;

  private Vbox vbox_headerVerSector;

  private Vbox vbox_headerModificarSector;

  private Vbox vbox_modificarSector;

  private List<CalleDTO> listaCalles;

  private Include inc_reparticionSectorSelector;

  private List<UsuarioReducido> listaAsignadores;

  private List<UsuarioReducido> listaTodosUsuarios;

  private UsuarioReducido asignadorSeleccionado;

  private Listfooter totalAsignadores;

  private Listbox asignadoresListbox;

  private Bandbox bandBoxAsignador;

  public void onChanging$bandBoxAsignador(InputEvent e) {
    this.cargarUsuarios(e, getListaTodosUsuarios(), asignadoresListbox, listaAsignadores,
        totalAsignadores);
  }

  private void cargarUsuarios(InputEvent e, List<UsuarioReducido> listaTotalUsuarios,
      Listbox listbox, List<UsuarioReducido> listaReducida, Listfooter totalUsuarios) {
    String matchingText = e.getValue();
    if (matchingText.trim().length() > 2 && (!"*".equals(matchingText.trim()))) {
      listaReducida.clear();
      if (listaTotalUsuarios != null) {
        matchingText = matchingText.toUpperCase();
        for (UsuarioReducido usuarioReducido : listaTotalUsuarios) {
          if ((usuarioReducido != null) && (usuarioReducido.getUsername() != null)) {
            if ((usuarioReducido.toString().contains(matchingText))) {
              listaReducida.add(usuarioReducido);
            }
          }
        }
      }
    } else if ("*".equals(matchingText.trim())) {
      listaReducida.addAll(listaTotalUsuarios);
    } else if (matchingText.trim().length() < 2) {
      listaReducida.clear();
    }
    listbox.setActivePage(0);
    totalUsuarios.setLabel(Labels.getLabel("eu.datosSectorComposer.setLabel.totalUsuarios") + ": "
        + listaReducida.size());
    this.binder.loadComponent(listbox);
  }

  public void onSelect$asignadoresListbox() {
    bandBoxAsignador.setText(asignadorSeleccionado.getUsername());
    sector.setUsuarioAsignador(asignadorSeleccionado.getUsername());
    bandBoxAsignador.close();
  }

  private List<UsuarioReducido> getListaTodosUsuarios() {
    if (listaTodosUsuarios == null || listaTodosUsuarios.isEmpty()) {
      try {
        listaTodosUsuarios = usuarioService.obtenerUsuariosDeSolr();
      } catch (SecurityNegocioException e) {
        logger.error(e.getMessage(), e);
        mostrarError(Labels.getLabel("eu.datosSectorComposer.mostrarError.noListaUsuarios"));
      }
    }
    return listaTodosUsuarios;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    sectorService = (ISectorService) SpringUtil.getBean("sectorService");
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");

    listaAsignadores = new ArrayList<>();

    comp.addEventListener(Events.ON_NOTIFY, new DatosSectorOnNotifyWindowListener(this));
    comp.addEventListener(Events.ON_OK, new DatosSectorOnNotifyWindowListener(this));

    this.sector = new SectorDTO();

    parametros = Executions.getCurrent().getArg();
    if (!parametros.isEmpty()) {
      if (parametros.get(ConstantesSesion.KEY_SECTOR) != null) {
        sector = (SectorDTO) parametros.get(ConstantesSesion.KEY_SECTOR);
        getSession().setAttribute(ConstantesSesion.KEY_REPARTICION_MODIFICAR, sector.getCodigo());
        // Visualizacion desde Administrador
        if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && !(Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR)) {
          vbox_headerVerSector.setVisible(true);
          vbox_headerModificarSector.setVisible(false);
          vbox_modificarSector.setVisible(false);
          habilitarCampos(false);
          inicializarComponentes((Boolean) (parametros.get(ConstantesSesion.KEY_MODIFICAR)));

          // Modificar desde Administrador
        } else if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR)) {
          habilitarCampos(true);
          vbox_headerVerSector.setVisible(false);
          vbox_headerModificarSector.setVisible(true);
          vbox_modificarSector.setVisible(false);
          inicializarComponentes((Boolean) (parametros.get(ConstantesSesion.KEY_MODIFICAR)));

        }
      }
    } else {
      obtenerDatosUsuarioLogueado();
    }

    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();

  }

  private void inicializarComponentes(boolean camposModificables) {

    cargarCompontentes();
    configurarCombosReparticionSector(camposModificables);
    txbx_sector.setDisabled(!camposModificables);
    txbx_descripcion.setDisabled(!camposModificables);
    txbx_calle.setDisabled(!camposModificables);
    txbx_nroCalle.setDisabled(!camposModificables);
    datebox_vigenciaDesde.setDisabled(!camposModificables);
    datebox_vigenciaHasta.setDisabled(!camposModificables);
    txbx_piso.setDisabled(!camposModificables);
    txbx_fax.setDisabled(!camposModificables);
    txbx_oficina.setDisabled(!camposModificables);
    cmbox_sectorMesa.setDisabled(!camposModificables);
    txbx_oficina.setDisabled(!camposModificables);
    txbx_oficina.setDisabled(!camposModificables);
    txbx_oficina.setDisabled(!camposModificables);
    txbx_telefono.setDisabled(!camposModificables);
    txbx_email.setDisabled(!camposModificables);
    bandBoxAsignador.setDisabled(!camposModificables);
  }

  private void configurarCombosReparticionSector(Boolean camposModificables) {
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR,
        false);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION,
        camposModificables);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION,
        true);
    inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);
  }

  private void cargarCompontentes() {

    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.REPARTICION_SELECCIONADA,
        sector.getReparticion());
    txbx_calle.setValue(sector.getCalle());
    datebox_vigenciaDesde.setValue(sector.getVigenciaDesde());
    datebox_vigenciaHasta.setValue(sector.getVigenciaHasta());
    if (sector.getUsuarioAsignador() != null) {
      bandBoxAsignador.setValue(sector.getUsuarioAsignador());
    }

    if (sector.getSectorMesa() != null) {
      if ("S".equals(sector.getSectorMesa())) {
        cmbox_sectorMesa.setValue("SI");
      }
      if ("N".equals(sector.getSectorMesa())) {
        cmbox_sectorMesa.setValue("NO");
      }
    }
  }

  private void habilitarCampos(boolean habilitado) {
    txbx_sector.setReadonly(!habilitado);
    txbx_nroCalle.setReadonly(!habilitado);
    txbx_telefono.setReadonly(!habilitado);
    txbx_descripcion.setReadonly(!habilitado);
    txbx_piso.setReadonly(!habilitado);
    txbx_fax.setReadonly(!habilitado);
    txbx_oficina.setReadonly(!habilitado);
    txbx_email.setReadonly(!habilitado);
    cmbox_sectorMesa.setReadonly(!habilitado);
    datebox_vigenciaDesde.setDisabled(!habilitado);
    datebox_vigenciaHasta.setDisabled(!habilitado);
    cmbox_sectorMesa.setDisabled(!habilitado);
    txbx_calle.setDisabled(!habilitado);
    btn_guardarSector.setVisible(habilitado);
    btn_cancelar.setVisible(habilitado);
    btn_salir.setVisible(!habilitado);
  }

  public boolean verificarPasswordPopup() throws InterruptedException {
    // LLama a la verificacion del Password y esta verificacion llama al
    // evento guardar
		Utilitarios.closePopUps("win_verificarPassword");
    Window win = (Window) Executions.createComponents("/administrator/verificarPassword.zul",
        this.self, parametros);
    win.setMode("modal");
    win.setClosable(true);
    win.setTitle(Labels.getLabel("eu.datosPersonalesCompose.winTitle.verificarPass"));
    win.setWidth("350px");
    win.setHeight("100px");
    win.setPosition("center");
    win.setVisible(true);
    win.setBorder("normal");
    win.doModal();

    return false;
  }

  private boolean validarComboReparticion() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHECK, comp, null);
    return true;
  }

  public boolean validarCampos() {
    validarComboReparticion();
      if (txbx_sector.getValue().isEmpty()) {
        throw new WrongValueException(this.txbx_sector,
            Labels.getLabel("eu.adminSade.validacion.sector.codigo"));
      }
      if (txbx_descripcion.getValue().isEmpty()) {
        throw new WrongValueException(this.txbx_descripcion,
            Labels.getLabel("eu.adminSade.validacion.nombre"));
      }
      if (txbx_calle.getValue().isEmpty()) {
        throw new WrongValueException(this.txbx_calle,
            Labels.getLabel("eu.adminSade.validacion.sector.calle"));
      }
      if (datebox_vigenciaDesde.getValue() == (null)) {
        throw new WrongValueException(this.datebox_vigenciaDesde,
            Labels.getLabel("eu.adminSade.validacion.sector.fecha"));
      }
      if (datebox_vigenciaHasta.getValue() == (null)) {
        throw new WrongValueException(this.datebox_vigenciaHasta,
            Labels.getLabel("eu.adminSade.validacion.sector.fecha"));
      }
      if (datebox_vigenciaDesde.getValue() != (null)
          && datebox_vigenciaHasta.getValue() != (null)) {
        if (datebox_vigenciaDesde.getValue().after(datebox_vigenciaHasta.getValue())) {
          throw new WrongValueException(this.datebox_vigenciaDesde,
              Labels.getLabel("eu.datosSectorComposer.WrongValueException.fechaMenorFechaHasta"));
        }
      }
      if (bandBoxAsignador.getValue().isEmpty()) {
        throw new WrongValueException(this.bandBoxAsignador,
            Labels.getLabel("eu.datosSectorComposer.WrongValueException.seleccioneAsignador"));
      }

      if (cmbox_sectorMesa.getValue() == (null)) {
        throw new WrongValueException(this.cmbox_sectorMesa,
            Labels.getLabel("eu.adminSade.validacion.sector.fecha"));
      }
    
    return true;
  }

  public void guardar(Boolean passwordOk) throws InterruptedException {

    if (passwordOk) {
      try {
        //
        habilitarCampos(false);
        configurarCombosReparticionSector(false);

        if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR)) {
          if (this.self == null) {
            throw new IllegalAccessError(Labels
                .getLabel("eu.datosSectorComposer.WrongValueException.componenteNoPresente"));
          }
        }
        if (cmbox_sectorMesa.getValue() != null)
          if ("SI".equals(cmbox_sectorMesa.getValue()))
            this.sector.setSectorMesa("S");
          else
            this.sector.setSectorMesa("N");
        sectorService.modificarSector(this.sector);
        Messagebox.show(Labels.getLabel("eu.adminSade.sector.mensajes.modificacionExitosa"),
            Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);
        btn_salir.setVisible(true);
        inc_reparticionSectorSelector.afterCompose();

      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    } else {
      Messagebox.show(Labels.getLabel("eu.adminSade.validacion.passActual"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    }
  }

  public void onClick$btn_guardarSector() throws InterruptedException {
    if (validarCampos()) {
      verificarPasswordPopup();
    }
  }

  private UsuarioBaseDTO obtenerDatosUsuarioLogueado() throws InterruptedException {
    try {
      UsuarioBaseDTO usuarioLoggeado = new UsuarioBaseDTO();
      usuarioLoggeado.setUid((String) Executions.getCurrent().getDesktop().getSession()
          .getAttribute(ConstantesSesion.SESSION_USERNAME));
      usuarioLoggeado = this.iLdapService.obtenerUsuarioPorUid(usuarioLoggeado.getUid());
      getSession().setAttribute("usuarioModificar", usuarioLoggeado.getUid());

      return usuarioLoggeado;
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
    return null;
  }

  /**
   * Refresca los datos al viajar entre las pestañas
   * 
   */
  final class DatosSectorOnNotifyWindowListener implements EventListener {
    private DatosSectorComposer composer;

    public DatosSectorOnNotifyWindowListener(DatosSectorComposer comp) {
      this.composer = comp;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          this.composer.obtenerDatosUsuarioLogueado();
        }
      }
      if (event.getName().equals(Events.ON_OK)) {
        if (event.getData() != null) {
          Map<String, Boolean> map = (Map<String, Boolean>) event.getData();
          passwordOk = (Boolean) map.get(ConstantesSesion.KEY_IS_OK);
          this.composer.guardar(passwordOk);
        }
      }
    }
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

  public List<CalleDTO> getListasCallesSeleccionada() {
    return listasCallesSeleccionada;
  }

  public void setListasCallesSeleccionada(List<CalleDTO> listasCallesSeleccionada) {
    this.listasCallesSeleccionada = listasCallesSeleccionada;
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public CalleDTO getCalleSeleccionada() {
    return calleSeleccionada;
  }

  public void setCalleSeleccionada(CalleDTO calleSeleccionada) {
    this.calleSeleccionada = calleSeleccionada;
  }

  public Textbox getTxbx_sector() {
    return txbx_sector;
  }

  public void setTxbx_sector(Textbox txbx_sector) {
    this.txbx_sector = txbx_sector;
  }

  public Textbox getTxbx_descripcion() {
    return txbx_descripcion;
  }

  public void setTxbx_descripcion(Textbox txbx_descripcion) {
    this.txbx_descripcion = txbx_descripcion;
  }

  public Textbox getTxbx_nroCalle() {
    return txbx_nroCalle;
  }

  public void setTxbx_nroCalle(Textbox txbx_nroCalle) {
    this.txbx_nroCalle = txbx_nroCalle;
  }

  public Datebox getDatebox_vigenciaDesde() {
    return datebox_vigenciaDesde;
  }

  public void setDatebox_vigenciaDesde(Datebox datebox_vigenciaDesde) {
    this.datebox_vigenciaDesde = datebox_vigenciaDesde;
  }

  public Datebox getDatebox_vigenciaHasta() {
    return datebox_vigenciaHasta;
  }

  public void setDatebox_vigenciaHasta(Datebox datebox_vigenciaHasta) {
    this.datebox_vigenciaHasta = datebox_vigenciaHasta;
  }

  public Textbox getTxbx_telefono() {
    return txbx_telefono;
  }

  public void setTxbx_telefono(Textbox txbx_telefono) {
    this.txbx_telefono = txbx_telefono;
  }

  public Textbox getTxbx_piso() {
    return txbx_piso;
  }

  public void setTxbx_piso(Textbox txbx_piso) {
    this.txbx_piso = txbx_piso;
  }

  public Textbox getTxbx_fax() {
    return txbx_fax;
  }

  public void setTxbx_fax(Textbox txbx_fax) {
    this.txbx_fax = txbx_fax;
  }

  public Textbox getTxbx_oficina() {
    return txbx_oficina;
  }

  public void setTxbx_oficina(Textbox txbx_oficina) {
    this.txbx_oficina = txbx_oficina;
  }

  public Textbox getTxbx_email() {
    return txbx_email;
  }

  public void setTxbx_email(Textbox txbx_email) {
    this.txbx_email = txbx_email;
  }

  public Combobox getCmbox_sectorMesa() {
    return cmbox_sectorMesa;
  }

  public void setCmbox_sectorMesa(Combobox cmbox_sectorMesa) {
    this.cmbox_sectorMesa = cmbox_sectorMesa;
  }

  public Include getInc_reparticionSectorSelector() {
    return inc_reparticionSectorSelector;
  }

  public void setInc_reparticionSectorSelector(Include inc_reparticionSectorSelector) {
    this.inc_reparticionSectorSelector = inc_reparticionSectorSelector;
  }

  public List<CalleDTO> getListaCalles() {
    return listaCalles;
  }

  public void setListaCalles(List<CalleDTO> listaCalles) {
    this.listaCalles = listaCalles;
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  public void setListaAsignadores(List<UsuarioReducido> listaAsignadores) {
    this.listaAsignadores = listaAsignadores;
  }

  public List<UsuarioReducido> getListaAsignadores() {
    return listaAsignadores;
  }

  public void setAsignadorSeleccionado(UsuarioReducido asignadorSeleccionado) {
    this.asignadorSeleccionado = asignadorSeleccionado;
  }

  public UsuarioReducido getAsignadorSeleccionado() {
    return asignadorSeleccionado;
  }

  public Textbox getTxbx_calle() {
    return txbx_calle;
  }

  public void setTxbx_calle(Textbox txbx_calle) {
    this.txbx_calle = txbx_calle;
  }
}
