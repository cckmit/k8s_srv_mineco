package com.egoveris.edt.web.admin.pl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.egoveris.edt.base.model.eu.CalleDTO;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.service.reparticion.IReparticionHelper;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.base.util.zk.UtilsDate;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMSectorComposer extends BaseComposer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7528531508532117656L;
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ABMSectorComposer.class);

  /** The Constant ABM_SECTOR_ZUL. */
  public static final String ABM_SECTOR_ZUL = "/administrator/abmSector.zul";
  
  @Autowired
  protected AnnotateDataBinder binder;

  @Autowired
  private ISectorService sectorService;

  private Textbox txbx_sector;
  private Textbox txbx_descripcion;
  private Textbox txbx_nroCalle;
  private Datebox datebox_vigenciaDesde;
  private Datebox datebox_vigenciaHasta;
  private Textbox txbx_telefono;
  private Textbox txbx_piso;
  private Textbox txbx_fax;
  private Textbox txbx_oficina;
  private Textbox txbx_email;
  private Textbox txbx_calle;
  private Checkbox chckBox_esMesaVirtualEE;
  private Bandbox bandBoxAsignador;
  private Hbox hbox_visu;
  private Hbox hbox_botones;
  private Vbox vbox_headerVerSector;
  private Vbox vbox_headerModificarSector;
  private Vbox vbox_headerAlta;
  private Vbox vbox_modificar;
  private Component comp;

  private List<CalleDTO> listaCalles;
  private SectorDTO sector;
  private List<UsuarioReducido> listaAsignadores;
  private UsuarioReducido asignadorSeleccionado;
  private Listfooter totalAsignadores;
  private Listbox asignadoresListbox;

  private Boolean alta;
  private Boolean edicion;
  private Boolean visualizacion;
  private Map<?, ?> parametros;

  // Version 3.6.6
  private Bandbox bbx_reparticion;
  private Textbox txt_busquedaReparticion;
  private List<ReparticionDTO> listaReparticion;
  private List<ReparticionDTO> reparticiones;
  private IReparticionHelper reparticionHelper;
  private IAdminReparticionService adminReparticionService;
  private Listbox lbx_reparticion;

  @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    traerServicios();
    crearListas();
    configurarPantalla();

    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();

    // Valido el estado del sector, para poder habilitar la modificacion de
    // los campos
    if (edicion && BooleanUtils.isFalse(sector.getEstadoRegistro())) {
      // Mando un aviso notificando que no se puede modificar ese sector
      Messagebox.show(Labels.getLabel("eu.adminSade.sector.mensajes.sectorInactivo"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION, new EventListener() {
            @Override
            public void onEvent(Event evt) throws InterruptedException {
              onClick$btn_cancelar();
            }
          });
    }
  }

  /**
   * Configurar pantalla.
   *
   * @throws SecurityNegocioException the security negocio exception
   */
  public void configurarPantalla() throws SecurityNegocioException {
    parametros = Executions.getCurrent().getArg();
    edicion = parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR);
    visualizacion = parametros.get(ConstantesSesion.KEY_VISUALIZAR) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_VISUALIZAR);
    alta = parametros.get(ConstantesSesion.KEY_ALTA) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_ALTA);

    if (!alta) {
      sector = (SectorDTO) parametros.get(ConstantesSesion.KEY_SECTOR);
      cargarComponentes();
      if (visualizacion) {
        // pantalla visualizacion
        vbox_headerVerSector.setVisible(true);
        vbox_headerModificarSector.setVisible(false);
        habilitarCampos(false);
      } else if (edicion) {
        // pantalla edicion
        vbox_headerVerSector.setVisible(false);
        vbox_headerModificarSector.setVisible(true);
        habilitarCampos(true);
      }
    } else {
      this.sector = new SectorDTO();
      sector.setSectorMesa(ConstantesSesion.NO);
      vbox_headerAlta.setVisible(true);
      datebox_vigenciaDesde.setValue(new Date());
      datebox_vigenciaHasta.setValue(new Date());
    }
  }

  // ------ Bandbox ReparticionDTO
  public void onOpen$bbx_reparticion() {
    if (bbx_reparticion.getValue() != null) {
      txt_busquedaReparticion.setText(bbx_reparticion.getValue().toUpperCase().trim());
      filtrarReparticiones(bbx_reparticion.getValue());
      this.binder.loadComponent(bbx_reparticion);
    }
    txt_busquedaReparticion.focus();
  }

  public void onChanging$txt_busquedaReparticion(InputEvent e) {
    String value = e.getValue().toUpperCase();
    bbx_reparticion.setText(value);
    bbx_reparticion.setValue(value);

    filtrarReparticiones(value);
    binder.loadComponent(lbx_reparticion);
  }

  public void onSelect$lbx_reparticion() {
    bbx_reparticion.setText(sector.getReparticion().getCodigo());
    bbx_reparticion.setValue(sector.getReparticion().getCodigo());
    this.listaReparticion = new ArrayList<ReparticionDTO>();
    binder.loadComponent(bbx_reparticion);
    bbx_reparticion.close();
  }

  /**
   * Busca dentro de la lista de reparticiones por codigoReparticion o por
   * nombre
   * 
   * @param value
   */
  private void filtrarReparticiones(String value) {
    this.listaReparticion.clear();
    if ("*".equals(value.trim())) {
      this.listaReparticion = new ArrayList<>(this.reparticiones);
    } else if (value.length() >= 2) {
      if (listaReparticion != null) {
        for (ReparticionDTO reparticion : reparticiones) {
          if (reparticion != null && reparticion.getNombre() != null) {
            String normalizadoValorA = Normalizer.normalize(value, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String normalizadoValorB = Normalizer
                .normalize(reparticion.getNombre(), Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            if (StringUtils.containsIgnoreCase(reparticion.getCodigo(), value)
                || StringUtils.containsIgnoreCase(normalizadoValorB, normalizadoValorA)) {
              listaReparticion.add(reparticion);
            }
          }
        }
      }
    }else {
    	sector.setReparticion(null);
    	this.listaReparticion.clear();
    }
  }

  // ------ Fin Bandbox ReparticionDTO

  /**
   * Validaciones para la obligatoriedad de los campos
   * 
   * @return
   */
  public boolean validarCampos() {
    if (sector.getReparticion() == null || StringUtils.isEmpty(bbx_reparticion.getValue())) {
      throw new WrongValueException(this.bbx_reparticion,
          Labels.getLabel("eu.adminSade.validacion.sector.codigo"));
    }

    if (StringUtils.isEmpty(txbx_sector.getValue())) {
      throw new WrongValueException(this.txbx_sector,
          Labels.getLabel("eu.adminSade.validacion.sector.codigo"));
    }

    if (alta) {
      // Valido la duplicidad de la clave reparticion-sector
      if (sectorService.buscarSectorPorRepaYSector(txbx_sector.getValue(),
          sector.getReparticion()) != null) {
        throw new WrongValueException(this.txbx_sector,
            Labels.getLabel("eu.altaSector.validacion.sector.existente"));
      }
      // Valido si es el unico sector mesa virtual de la reparticion
      if (chckBox_esMesaVirtualEE.isChecked()) {
        List<SectorDTO> sectoresVirtuales = sectorService
            .buscarSectoresMesaVirtualPorReparticion(sector.getReparticion());
        if (sectoresVirtuales == null || !sectoresVirtuales.isEmpty()) {
          throw new WrongValueException(this.chckBox_esMesaVirtualEE,
              Labels.getLabel("eu.altaSector.validacion.sectorMesaVirtual")
                  .concat(sectoresVirtuales != null ? sectoresVirtuales.get(0).getCodigo() : ""));
        }
      }
    } else if (chckBox_esMesaVirtualEE.isChecked()) {
      // Valido que el sector mesa virtual sea el mismo que estoy
      // modificando
      List<SectorDTO> sectoresVirtuales = sectorService
          .buscarSectoresMesaVirtualPorReparticion(sector.getReparticion());
      if (sectoresVirtuales != null && !sectoresVirtuales.isEmpty()) {
        if (!sectoresVirtuales.get(0).getCodigo().equals(sector.getCodigo())) {
          throw new WrongValueException(this.chckBox_esMesaVirtualEE,
              Labels.getLabel("eu.altaSector.validacion.sectorMesaVirtual")
                  .concat(sectoresVirtuales.get(0).getCodigo()));
        }
      }
    }

    if (txbx_descripcion.getValue().trim().isEmpty())

    {
      throw new WrongValueException(this.txbx_descripcion,
          Labels.getLabel("eu.adminSade.validacion.nombre"));
    }

    if (txbx_calle.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.txbx_calle,
          Labels.getLabel("eu.adminSade.validacion.reparticion.calle"));
    }

    if (datebox_vigenciaDesde.getValue() == (null)) {
      throw new WrongValueException(this.datebox_vigenciaDesde,
          Labels.getLabel("eu.adminSade.validacion.sector.fecha"));
    }
    if (datebox_vigenciaHasta.getValue() == (null) && alta) {
      throw new WrongValueException(this.datebox_vigenciaHasta,
          Labels.getLabel("eu.adminSade.validacion.sector.fecha"));
    }
    if (datebox_vigenciaDesde.getValue() != (null) && datebox_vigenciaHasta.getValue() != (null)
        && alta) {
      if (datebox_vigenciaDesde.getValue().after(datebox_vigenciaHasta.getValue())) {
        throw new WrongValueException(this.datebox_vigenciaDesde,
            Labels.getLabel("eu.adminSade.validacion.sector.fechaDesdeMayorAFechaHasta"));
      }
    }

    Date fechaActual = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(fechaActual);
    cal.add(Calendar.DATE, -1);

    if (datebox_vigenciaDesde.getValue().before(cal.getTime()) && alta) {
      throw new WrongValueException(this.datebox_vigenciaDesde,
          Labels.getLabel("eu.adminSade.validacion.sector.fechaDesdeMenorAActual"));
    }

    Utilitarios.validarNumeros(txbx_nroCalle);
    Utilitarios.validarNumerosTelefono(txbx_fax);
    Utilitarios.validarNumerosTelefono(txbx_telefono);
    Utilitarios.validarMail(txbx_email);

    return true;
  }

  public void onClick$btn_cancelar() {
    limpiarDeSessionLaReparticionSeleccionada();
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_salir() {
    limpiarDeSessionLaReparticionSeleccionada();
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos())
      guardarSector();
  }

  private void guardarSector() throws InterruptedException {

    armarObjetoSectorParaPersistir();
    try {
      if (alta) {
        sectorService.guardarSector(sector);

        Messagebox.show(Labels.getLabel("eu.adminSade.sector.mensajes.altaExitosa"),
            Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);
      } else if (edicion) {
        sectorService.modificarSector(sector);
        Messagebox.show(Labels.getLabel("eu.adminSade.sector.mensajes.modificacionExitosa"),
            Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);
      }

      Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      Messagebox.show(ex.getMessage(),
          Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  /**
   * Armar objeto sector para persistir.
   */
  private void armarObjetoSectorParaPersistir() {
    this.sector.setCodigo(txbx_sector.getValue().trim());
    this.sector.setNombre(txbx_descripcion.getValue());
    this.sector.setNumeroCalle(txbx_nroCalle.getValue());
    this.sector.setCalle(txbx_calle.getValue());
    this.sector.setVigenciaDesde(UtilsDate.formatoFechaInicio(datebox_vigenciaDesde.getValue()));
    this.sector.setVigenciaHasta(UtilsDate.formatoFechaFin(datebox_vigenciaHasta.getValue()));
    this.sector.setTelefono(txbx_telefono.getValue());
    this.sector.setPiso(txbx_piso.getValue());
    this.sector.setFax(txbx_fax.getValue());
    this.sector.setOficina(txbx_oficina.getValue());
    this.sector.setEmail(txbx_email.getValue());
    this.sector.setSectorMesa("S");
    this.sector.setEsArchivo(false);
    this.sector.setMesaVirtual(chckBox_esMesaVirtualEE.isChecked());
    this.sector.setEstadoRegistro(true);
    this.sector.setUsuarioCreacion(getUsername());
    this.sector.setFechaCreacion(alta ? new Date() : sector.getFechaCreacion());
  }

  public void onBlur$txbx_sector() throws InterruptedException {
    txbx_sector.setValue(txbx_sector.getValue().toUpperCase());
    this.sector.setCodigo(txbx_sector.getValue());
    this.binder.loadComponent(txbx_sector);
  }

  /**
   * Cargar componentes.
   *
   * @throws SecurityNegocioException the security negocio exception
   */
  public void cargarComponentes() throws SecurityNegocioException {
    bbx_reparticion.setText(sector.getReparticion().getCodigo());
    bbx_reparticion.setValue(sector.getReparticion().getCodigo());
    txbx_sector.setValue(sector.getCodigo());
    txbx_descripcion.setValue(sector.getNombre());
    txbx_calle.setValue(sector.getCalle());
    txbx_nroCalle.setValue(sector.getNumeroCalle());
    datebox_vigenciaDesde.setValue(sector.getVigenciaDesde());
    datebox_vigenciaHasta.setValue(sector.getVigenciaHasta());
    txbx_telefono.setValue(
        sector.getTelefono() != null ? sector.getTelefono().trim() : sector.getTelefono());
    txbx_piso.setValue(sector.getPiso() != null ? sector.getPiso().trim() : sector.getPiso());
    txbx_fax.setValue(sector.getFax() != null ? sector.getFax().trim() : sector.getFax());
    txbx_oficina
        .setValue(sector.getOficina() != null ? sector.getOficina().trim() : sector.getOficina());
    txbx_email.setValue(sector.getEmail() != null ? sector.getEmail().trim() : sector.getEmail());
    chckBox_esMesaVirtualEE.setChecked(sector.getMesaVirtual());
    this.binder.loadAll();
  }

  private void habilitarCampos(boolean habilitado) {

    bbx_reparticion.setDisabled(!habilitado || edicion);
    txbx_sector.setDisabled(!habilitado || edicion);
    txbx_descripcion.setDisabled(!habilitado);
    txbx_telefono.setReadonly(!habilitado);
    txbx_nroCalle.setDisabled(!habilitado);
    txbx_piso.setDisabled(!habilitado);
    txbx_fax.setDisabled(!habilitado);
    txbx_oficina.setDisabled(!habilitado);
    txbx_email.setDisabled(!habilitado);
    txbx_calle.setDisabled(!habilitado);
    datebox_vigenciaDesde.setDisabled(!habilitado || edicion);
    datebox_vigenciaHasta.setDisabled(!habilitado || edicion);
    hbox_botones.setVisible(habilitado);
    hbox_visu.setVisible(!habilitado);
    chckBox_esMesaVirtualEE.setDisabled(!habilitado);
  }

  private void traerServicios() {
    sectorService = (ISectorService) SpringUtil.getBean("sectorService");
    reparticionHelper = (IReparticionHelper) SpringUtil.getBean("reparticionHelper");
    adminReparticionService = (IAdminReparticionService) SpringUtil
        .getBean("adminReparticionService");
  }

  private void crearListas() {
    listaAsignadores = new ArrayList<UsuarioReducido>();
    listaReparticion = new ArrayList<ReparticionDTO>();
    if (Utilitarios.isAdministradorCentral()) {
      this.reparticiones = reparticionHelper.obtenerTodosReparticiones();
    } else if (Utilitarios.isAdministradorLocalReparticion()) {
      reparticiones = adminReparticionService
          .obtenerReparticionesRelacionadasByUsername(getUsername());
    }
  }

  private void limpiarDeSessionLaReparticionSeleccionada() {
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

  public List<CalleDTO> getListaCalles() {
    return listaCalles;
  }

  public void setListaCalles(List<CalleDTO> listaCalles) {
    this.listaCalles = listaCalles;
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

  public Textbox getTxbx_fax() {
    return txbx_fax;
  }

  public void setTxbx_fax(Textbox txbx_fax) {
    this.txbx_fax = txbx_fax;
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

  public Checkbox getChckBox_esMesaVirtualEE() {
    return chckBox_esMesaVirtualEE;
  }

  public void setChckBox_esMesaVirtualEE(Checkbox chckBox_esMesaVirtualEE) {
    this.chckBox_esMesaVirtualEE = chckBox_esMesaVirtualEE;
  }

  public Bandbox getBandBoxAsignador() {
    return bandBoxAsignador;
  }

  public void setBandBoxAsignador(Bandbox bandBoxAsignador) {
    this.bandBoxAsignador = bandBoxAsignador;
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

  public Vbox getVbox_headerVerSector() {
    return vbox_headerVerSector;
  }

  public void setVbox_headerVerSector(Vbox vbox_headerVerSector) {
    this.vbox_headerVerSector = vbox_headerVerSector;
  }

  public Vbox getVbox_headerModificarSector() {
    return vbox_headerModificarSector;
  }

  public void setVbox_headerModificarSector(Vbox vbox_headerModificarSector) {
    this.vbox_headerModificarSector = vbox_headerModificarSector;
  }

  public Listfooter getTotalAsignadores() {
    return totalAsignadores;
  }

  public void setTotalAsignadores(Listfooter totalAsignadores) {
    this.totalAsignadores = totalAsignadores;
  }

  public Listbox getAsignadoresListbox() {
    return asignadoresListbox;
  }

  public void setAsignadoresListbox(Listbox asignadoresListbox) {
    this.asignadoresListbox = asignadoresListbox;
  }

  public Vbox getVbox_headerAlta() {
    return vbox_headerAlta;
  }

  public void setVbox_headerAlta(Vbox vbox_headerAlta) {
    this.vbox_headerAlta = vbox_headerAlta;
  }

  public Vbox getVbox_modificar() {
    return vbox_modificar;
  }

  public void setVbox_modificar(Vbox vbox_modificar) {
    this.vbox_modificar = vbox_modificar;
  }

  public Bandbox getBbx_reparticion() {
    return bbx_reparticion;
  }

  public void setBbx_reparticion(Bandbox bbx_reparticion) {
    this.bbx_reparticion = bbx_reparticion;
  }

  public List<ReparticionDTO> getListaReparticion() {
    return listaReparticion;
  }

  public void setListaReparticion(List<ReparticionDTO> listaReparticion) {
    this.listaReparticion = listaReparticion;
  }

  public List<ReparticionDTO> getReparticiones() {
    return reparticiones;
  }

  public void setReparticiones(List<ReparticionDTO> reparticiones) {
    this.reparticiones = reparticiones;
  }

  public IReparticionHelper getReparticionHelper() {
    return reparticionHelper;
  }

  public void setReparticionHelper(IReparticionHelper reparticionHelper) {
    this.reparticionHelper = reparticionHelper;
  }

  public IAdminReparticionService getAdminReparticionService() {
    return adminReparticionService;
  }

  public void setAdminReparticionService(IAdminReparticionService adminReparticionService) {
    this.adminReparticionService = adminReparticionService;
  }

  public static Logger getLogger() {
    return logger;
  }

  public Textbox getTxt_busquedaReparticion() {
    return txt_busquedaReparticion;
  }

  public void setTxt_busquedaReparticion(Textbox txt_busquedaReparticion) {
    this.txt_busquedaReparticion = txt_busquedaReparticion;
  }

  public Listbox getLbx_reparticion() {
    return lbx_reparticion;
  }

  public void setLbx_reparticion(Listbox lbx_reparticion) {
    this.lbx_reparticion = lbx_reparticion;
  }

  public Textbox getTxbx_calle() {
    return txbx_calle;
  }

  public void setTxbx_calle(Textbox txbx_calle) {
    this.txbx_calle = txbx_calle;
  }

}
