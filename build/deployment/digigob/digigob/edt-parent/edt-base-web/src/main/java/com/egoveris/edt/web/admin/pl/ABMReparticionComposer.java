package com.egoveris.edt.web.admin.pl;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.CalleDTO;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.service.estructura.IEstructuraService;
import com.egoveris.edt.base.service.reparticion.IReparticionHelper;
import com.egoveris.edt.base.util.zk.UtilsDate;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class ABMReparticionComposer extends BaseComposer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -159758911077524788L;

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ABMReparticionComposer.class);

  /** The Constant ABM_REPARTICION_ZUL. */
  public static final String ABM_REPARTICION_ZUL = "/administrator/abmReparticion.zul";
  
  @Autowired
  protected AnnotateDataBinder binder;

  private IReparticionEDTService reparticionService;
  private IEstructuraService estructuraService;
  private IAdminReparticionService adminReparticionService;
  private Textbox txbx_codigo;
  private Textbox txbx_numCalle;
  private Textbox txbx_telefono;
  private Checkbox ck_repa_padre;
  private Textbox txbx_descripcion;
  private Textbox txbx_piso;
  private Textbox txbx_fax;
  private Textbox txbx_oficina;
  private Datebox dbx_vigenciaHasta;
  private Datebox dbx_vigenciaDesde;
  private Textbox txbx_email;
  private Combobox cbbx_sistema;
  private Textbox txbx_calle;

  private Listbox lbx_reparticionesSADEListbox;
  private Bandbox bbx_estructura;
  private Listbox lbx_estructurasSADEListbox;
  private Listbox lbx_callesSADEListbox;
  
  private Hbox hbox_visu;
  private Hbox hbox_botones;
  private Vbox vbox_headerVer;
  private Vbox vbox_headerModificar;
  private Vbox vbox_headerAlta;
  private Vbox vbox_modificar;

  private List<ReparticionDTO> listaReparticionSeleccionada;
  private ReparticionDTO reparticionPadreSeleccionada;
  private List<ReparticionDTO> listaReparticiones;
  private List<EstructuraDTO> listaEstructurasSeleccionada;
  private EstructuraDTO estructuraSeleccionada;
  private List<EstructuraDTO> listaTodasEstructuras;
  private List<CalleDTO> listaCallesSeleccionada;
  private CalleDTO calleSeleccionada;
  private List<CalleDTO> listaTodasCalles;
  private ReparticionDTO reparticion;
  private IReparticionHelper reparticionHelper;

  private Boolean alta;
  private Boolean edicion;
  private Boolean visualizacion;
  private Map<?, ?> parametros;

  // Version 3.5.0
  private Bandbox bbx_reparticionPadre;

  private Textbox txtBusquedaReparticionPadreBusquedaSADE;
  private Textbox txtBusquedaEstructuraBusquedaSADE;

  private String selectedEnRed = new String();

  @SuppressWarnings("unchecked")
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
    
    if (edicion && BooleanUtils.isFalse(reparticion.getEstadoRegistro())) {
      // Valido el estado de la reparticion, para poder habilitar la
      // modificacion de los campos
      // Mando un aviso notificando que no se puede modificar esa reparticion
      Messagebox.show(Labels.getLabel("eu.adminSade.reparticion.mensajes.reparticionInactiva"),
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
   * @throws InterruptedException the interrupted exception
   */
  private void configurarPantalla() throws InterruptedException {

    parametros = Executions.getCurrent().getArg();
    edicion = parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR);
    visualizacion = parametros.get(ConstantesSesion.KEY_VISUALIZAR) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_VISUALIZAR);
    alta = parametros.get(ConstantesSesion.KEY_ALTA) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_ALTA);

    // si no es un alta, obtengo la reparticion para completar los datos en los
    // componentes
    if (!alta) {
      reparticion = (ReparticionDTO) parametros.get(ConstantesSesion.KEY_REPARTICION);
      reparticion = obtenerObjetoReparticionCompleto(reparticion.getId());
      cargarComponentes();
      // pantalla visualizacion
      if (visualizacion) {
        vbox_headerVer.setVisible(true);
        vbox_headerModificar.setVisible(false);
        habilitarCampos(false);
        // pantalla edicion
      } else if (edicion) {
        habilitarCampos(true);
        vbox_headerVer.setVisible(false);
        vbox_headerModificar.setVisible(true);
      }
    } else {
      this.reparticion = new ReparticionDTO();
      reparticion.setEstadoRegistro(true);
      this.reparticionPadreSeleccionada = new ReparticionDTO();
      vbox_headerAlta.setVisible(true);
      dbx_vigenciaDesde.setValue(new Date());
      dbx_vigenciaHasta.setValue(new Date());
    }
  }

  /**
   * Traer servicios.
   */
  private void traerServicios() {
    adminReparticionService = (IAdminReparticionService) SpringUtil
        .getBean("adminReparticionService");
    reparticionService = (IReparticionEDTService) SpringUtil.getBean("reparticionEDTService");
    estructuraService = (IEstructuraService) SpringUtil.getBean("estructuraService");
    reparticionHelper = (IReparticionHelper) SpringUtil.getBean("reparticionHelper");
  }

  private void crearListas() {
    if (Utilitarios.isAdministradorCentral()) {
      this.listaReparticiones = reparticionHelper.obtenerTodosReparticiones();
    } else if (Utilitarios.isAdministradorLocalReparticion()) {
      listaReparticiones = adminReparticionService
          .obtenerReparticionesRelacionadasByUsername(getUsername());
    }
    this.listaReparticionSeleccionada = new ArrayList<>(listaReparticiones);
    listaTodasEstructuras = estructuraService.listEstructuras();
    listaEstructurasSeleccionada = new ArrayList<>(listaTodasEstructuras);

  }

  public void onBlur$reparticion() {
    this.bbx_reparticionPadre.setText(this.bbx_reparticionPadre.getValue().toUpperCase());
  }

  public void onSelect$lbx_reparticionesSADEListbox() {
    bbx_reparticionPadre.setText(reparticionPadreSeleccionada.getCodigo());
    this.listaReparticionSeleccionada = new ArrayList<>();
    this.binder.loadAll();
    bbx_reparticionPadre.close();
  }

  public void onOpen$bbx_reparticionPadre() {
    if (bbx_reparticionPadre.getValue() != null) {
      String value = bbx_reparticionPadre.getValue().toUpperCase().trim();
      txtBusquedaReparticionPadreBusquedaSADE.setText(value);
      this.cargarReparticiones(value);
      this.cargarReparticionPadre(value);
    }
    txtBusquedaReparticionPadreBusquedaSADE.focus();
  }

  public void onChanging$txtBusquedaReparticionPadreBusquedaSADE(InputEvent e) {
    String value = e.getValue().toUpperCase().trim();
    bbx_reparticionPadre.setValue(value);
    bbx_reparticionPadre.setText(value);
    if (StringUtils.isEmpty(value) || (value.length() < 2 && !"*".equals(value.trim()))) {
      this.listaReparticionSeleccionada = new ArrayList<>();
      reparticionPadreSeleccionada = null;
    } else {
      this.cargarReparticiones(value);
      this.cargarReparticionPadre(value);
    }
    this.binder.loadComponent(bbx_reparticionPadre);
  }

  public void onChanging$bbx_calle(InputEvent e) {
    this.cargarCalles(e);
  }

  /**
   * Busca dentro de la lista de reparticiones por codigoReparticion y por
   * nombre
   * 
   * @param value
   */
  private void filtrarReparticiones(String value) {
    this.listaReparticionSeleccionada.clear();
    if ("*".equals(value.trim())) {
      this.listaReparticionSeleccionada = new ArrayList<>(this.listaReparticiones);
    } else if (value.length() >= 2) {
      if (listaReparticiones != null) {
        for (ReparticionDTO reparticion : listaReparticiones) {
          if (reparticion != null && reparticion.getNombre() != null) {
            String normalizadoValorA = Normalizer.normalize(value, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String normalizadoValorB = Normalizer
                .normalize(reparticion.getNombre(), Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            if (StringUtils.containsIgnoreCase(reparticion.getCodigo(), value)
                || StringUtils.containsIgnoreCase(normalizadoValorB, normalizadoValorA)) {
              listaReparticionSeleccionada.add(reparticion);
            }
          }
        }
      }
    }
  }

  public void cargarReparticionPadre(String matchingText) {
    if (StringUtils.isEmpty(matchingText)) {
      reparticionPadreSeleccionada = null;
    }
  }

  public void cargarReparticiones(String value) {
    filtrarReparticiones(value);
    this.binder.loadComponent(lbx_reparticionesSADEListbox);
  }

  public void onCheck$ck_repa_padre() {
  	if(ck_repa_padre.isChecked()) {
  		bbx_reparticionPadre.setDisabled(true);
  		bbx_reparticionPadre.setValue(null);
  		reparticionPadreSeleccionada = null;
  	}else {
  		bbx_reparticionPadre.setDisabled(false);
  	}
  }
  
  public void cargarEstructuras(String matchingText) {
    if (StringUtils.isEmpty(matchingText)) {
      this.listaEstructurasSeleccionada.clear();
      this.listaEstructurasSeleccionada.addAll(listaTodasEstructuras);
    } else if (!"*".equals(matchingText) && (matchingText.length() >= 2)) {
      this.listaEstructurasSeleccionada.clear();
      if (listaTodasEstructuras != null) {
        String matchingTextTemp = matchingText.toUpperCase();
        for (EstructuraDTO estr : listaTodasEstructuras) {
          if ((estr != null) && (estr.getNombreEstructura() != null)) {
            String normalizadoValorA = Normalizer.normalize(matchingTextTemp, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String normalizadoValorB = Normalizer
                .normalize(estr.getNombreEstructura(), Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            if (StringUtils.containsIgnoreCase(normalizadoValorB, normalizadoValorA)) {
              listaEstructurasSeleccionada.add(estr);
            }
          }
        }
      }
    } else if ("*".equals(matchingText)) {
      this.listaEstructurasSeleccionada = new ArrayList<>(this.listaTodasEstructuras);
    }
    this.binder.loadComponent(lbx_estructurasSADEListbox);
  }

  public void cargarCalles(InputEvent e) {
    String matchingText = e.getValue();
    if (!"*".equals(matchingText) && (matchingText.length() >= 3)) {
      listaCallesSeleccionada.clear();
      if (listaTodasCalles != null) {
        matchingText = matchingText.toUpperCase();

        for (CalleDTO c : listaTodasCalles) {
          if ((c != null) && (c.getNombreCalle() != null)) {
            if (StringUtils.containsIgnoreCase(c.getNombreCalle(), matchingText)) {
              listaCallesSeleccionada.add(c);
            }
          }
        }
      }
      this.binder.loadComponent(lbx_callesSADEListbox);
    } else if ("*".equals(matchingText)) {
      listaCallesSeleccionada = new ArrayList<>(listaTodasCalles);
      this.binder.loadComponent(lbx_callesSADEListbox);
    }
  }

  // --------------------------- EVENTOS PARA BANDBOXS
  // ------ ESTRUCTURA
  public void onOpen$bbx_estructura() {
    if (bbx_estructura.getValue() != null) {
      txtBusquedaEstructuraBusquedaSADE.setText(bbx_estructura.getValue().toUpperCase().trim());
      this.cargarEstructuras(bbx_estructura.getValue().toUpperCase().trim());
    }
    txtBusquedaEstructuraBusquedaSADE.focus();
  }

  public void onChanging$txtBusquedaEstructuraBusquedaSADE(InputEvent e) {
    String value = e.getValue().trim().toUpperCase();
    bbx_estructura.setText(value);
    bbx_estructura.setValue(value);
    if (StringUtils.isEmpty(value) || (value.length() < 2 && !"*".equals(value.trim()))) {
      reparticion.setEstructura(null);
      this.listaEstructurasSeleccionada.clear();
    } else {
      this.cargarEstructuras(value);
    }
    this.binder.loadComponent(lbx_estructurasSADEListbox);
  }

  public void onSelect$lbx_estructurasSADEListbox() {
    bbx_estructura.setText(estructuraSeleccionada.getNombreEstructura().toString());
    bbx_estructura.setValue(estructuraSeleccionada.getNombreEstructura().toString());
    txtBusquedaEstructuraBusquedaSADE.setText(bbx_estructura.getValue().toUpperCase().trim());
    reparticion.setEstructura(estructuraSeleccionada);
    bbx_estructura.close();
  }

  private List<ReparticionDTO> listaReparticionesPorEstructura(
      List<ReparticionDTO> listaReparticiones, String estructuras) {

    if (estructuras != null && estructuras.length() > 0) {
      String d = "[,]";
      String[] vector = estructuras.split(d);

      List<ReparticionDTO> lR = new ArrayList<>();
      for (ReparticionDTO r : listaReparticiones) {
        for (int i = 0; i < vector.length; i++) {
          if (r.getEstructura() != null && r.getEstructura().getId() != null) {
            if (r.getEstructura().getId().equals(Integer.valueOf(vector[i]))) {

              lR.add(r);
            }
          }
        }

      }
      return lR;
    } else {
      return listaReparticiones;
    }

  }

  // FIN EVENTOS BANDBOXS

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos()) {
      try {
        guardarReparticion();
        // refresco la lista de reparticiones, para que ya cuente con la que
        // acaba de dar de alta
        reparticionHelper.cachearListaReparticiones();
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    }

  }

  /**
   * Guardar reparticion.
   *
   * @throws NegocioException the negocio exception
   * @throws InterruptedException the interrupted exception
   */
  private void guardarReparticion() throws NegocioException, InterruptedException {
    armarObjetoReparticionParaPersistir();
    if (alta) {
      reparticion.setEsDgtal(true); //EsDgtal will be marked as deprecated. ggefaell 20/02/2018
      reparticionService.guardarReparticion(reparticion);
      Messagebox.show(Labels.getLabel("eu.adminSade.reparticion.mensajes.altaExitosa"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    } else if (edicion) {
      reparticionService.modificarReparticion(reparticion);
      Messagebox.show(Labels.getLabel("eu.adminSade.reparticion.mensajes.modificacionExitosa"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    }

    reparticionHelper.cachearListaReparticiones();
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  /**
   * Excluir estructuras.
   *
   * @return the list
   */
  private List<EstructuraDTO> excluirEstructuras() {
    List<String> estructurasNombres = new ArrayList<>();
    estructurasNombres.add(ConstantesSesion.ESTRUCTURA_SOLO_DESTINO);
    estructurasNombres.add(ConstantesSesion.ESTRUCTURA_JUSTICIA_CABA);
    estructurasNombres.add(ConstantesSesion.ESTRUCTURA_JUSTICIA_NAC_Y_PROV);
    return estructuraService.getByExepciones(estructurasNombres);
  }

  /**
   * Armar objeto reparticion para persistir.
   */
  private void armarObjetoReparticionParaPersistir() {
    reparticion.setCodigo(txbx_codigo.getValue().trim());
    reparticion.setNumero(txbx_numCalle.getValue());
    reparticion.setTelefono(txbx_telefono.getValue());
    reparticion.setVigenciaDesde(UtilsDate.formatoFechaInicio(dbx_vigenciaDesde.getValue()));
    reparticion.setVigenciaHasta(UtilsDate.formatoFechaFin(dbx_vigenciaHasta.getValue()));
    reparticion.setNombre(txbx_descripcion.getValue());
    reparticion.setPiso(txbx_piso.getValue());
    reparticion.setFax(txbx_fax.getValue());
    reparticion.setOficina(txbx_oficina.getValue());
    reparticion.setEmail(txbx_email.getValue());
    reparticion.setRepPadre(reparticionPadreSeleccionada);
    reparticion.setCalleReparticion(txbx_calle.getValue());
    reparticion.setEstructura(estructuraSeleccionada);
    reparticion.setVersion(alta ? 0 : reparticion.getVersion() + 1);
    reparticion.setUsuarioCreacion(getUsername());
    reparticion.setUsuarioModificacion(getUsername());
    reparticion.setFechaCreacion(alta ? new Date() : reparticion.getFechaCreacion());
    reparticion.setFechaModificacion(alta ? null : new Date());
    reparticion.setEsDgtal(true);
    reparticion.setEnRed("");
    if(ck_repa_padre.isChecked()) {
    	reparticion.setRepPadre(reparticion);
    }
    reparticion.setEsPadre(ck_repa_padre.isChecked());
  }

  public boolean validarCampos() {
    if (txbx_codigo.getValue().isEmpty() && alta) {
      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.reparticion.codigo"));
    }

    ReparticionDTO reparticionValidador = reparticionService
        .getReparticionActivaYInactivaByCodigo(txbx_codigo.getValue().trim());
    if (reparticionValidador != null && alta)
      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.altaReparticion.codigoExistente"));

    if (txbx_calle.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.txbx_calle,
          Labels.getLabel("eu.adminSade.validacion.reparticion.calle"));
    }

    if (txbx_numCalle.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.txbx_numCalle,
          Labels.getLabel("eu.adminSade.validacion.reparticion.numCalle"));
    }

    Utilitarios.validarNumeros(txbx_numCalle);
    Utilitarios.validarNumerosTelefono(txbx_telefono);
    Utilitarios.validarNumerosTelefono(txbx_fax);
    Utilitarios.validarMail(txbx_email);

    if (estructuraSeleccionada == null || bbx_estructura.getValue().trim().isEmpty()
        || reparticion.getEstructura() == null) {
      throw new WrongValueException(this.bbx_estructura,
          Labels.getLabel("eu.adminSade.validacion.reparticion.estructura"));
    }

    if (!bbx_estructura.getValue().trim()
        .equals(estructuraSeleccionada.getNombreEstructura().trim())) {
      throw new WrongValueException(this.bbx_estructura,
          Labels.getLabel("eu.adminSade.validacion.reparticion.estructuraInvalida"));
    }
    List<EstructuraDTO> estructurasExp = validarEstructuraPresentacion();

    Map<String, EstructuraDTO> auxMap = new HashMap<>();
    for (EstructuraDTO e : estructurasExp) {
      auxMap.put(e.getNombreEstructura().trim(), e);
    }

    if(!ck_repa_padre.isChecked()) {

      if (auxMap.get(estructuraSeleccionada.getNombreEstructura().trim()) == null) {
        if (reparticionPadreSeleccionada == null
            || bbx_reparticionPadre.getValue().trim().isEmpty()) {
          throw new WrongValueException(this.bbx_reparticionPadre,
              Labels.getLabel("eu.adminSade.validacion.reparticion.codigo"));
        }

        if (!bbx_reparticionPadre.getValue()
            .equals(reparticionPadreSeleccionada.getCodigo())) {
          throw new WrongValueException(this.bbx_reparticionPadre,
              Labels.getLabel("eu.panelAdmin.tabMigraciones.validacion.reparticionInvalida"));
        }
      } else {
        if (!StringUtils.isEmpty(bbx_reparticionPadre.getValue())) {
          throw new WrongValueException(this.bbx_reparticionPadre, Labels
              .getLabel("eu.panelAdmin.tabMigraciones.validacion.reparticionPadreSoloDestino"));
        } else {
          reparticionPadreSeleccionada = null;
        }
      }
    	
    }
    


    if (dbx_vigenciaDesde.getValue() == null && alta) {
      throw new WrongValueException(this.dbx_vigenciaDesde,
          Labels.getLabel("eu.adminSade.validacion.reparticion.fecha"));
    }
    if (dbx_vigenciaHasta.getValue() == null && alta) {
      throw new WrongValueException(this.dbx_vigenciaHasta,
          Labels.getLabel("eu.adminSade.validacion.reparticion.fecha"));
    }

    // validaciones para las fechas de vigencia para cuando se da de alta una
    // reparticion
    if (alta) {
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
              Labels.getLabel("eu.adminSade.validacion.sector.fechaDesdeMenorAActual"));
        }
        // comparo si las dos fechas distintas, para realizar las demás
        // validaciones
        diferencia = fechaDesde.compareTo(fechahasta);
        if (diferencia != 0) {
          // valido que la fecha de inicio sea menor a la de finalización
          if (diferencia == 1) {
            throw new WrongValueException(this.dbx_vigenciaDesde,
                Labels.getLabel("eu.adminSade.validacion.sector.fechaDesdeMayorAFechaHasta"));
          }
        }
      } catch (ParseException e) {
        return false;
      }
    }

    if (txbx_descripcion.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_descripcion,
          Labels.getLabel("eu.adminSade.validacion.reparticion.descripcion"));
    }

    return true;
  }
  
  /**
   * Validar estructura presentacion.
   *
   * @return the list
   */
  private List<EstructuraDTO> validarEstructuraPresentacion() {
    List<EstructuraDTO> estructurasExp = excluirEstructuras();
    List<EstructuraDTO> estruc = estructuraService.getByPoderEjecutivo("0");
    List<EstructuraDTO> estructurasUnion = new ArrayList<>();
    estructurasUnion.addAll(estructurasExp);
    estructurasUnion.addAll(estruc);
    return estructurasUnion;
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onBlur$txbx_codigo() throws InterruptedException {
    txbx_codigo.setValue(txbx_codigo.getValue().toUpperCase());
    this.reparticion.setCodigo(txbx_codigo.getValue());
    this.binder.loadComponent(txbx_codigo);
  }

  private void habilitarCampos(boolean habilitado) {
    txbx_codigo.setReadonly(!habilitado || edicion);
    txbx_numCalle.setReadonly(!habilitado);
    txbx_telefono.setReadonly(!habilitado);
    txbx_descripcion.setReadonly(!habilitado);
    txbx_piso.setReadonly(!habilitado);
    txbx_fax.setReadonly(!habilitado);
    txbx_oficina.setReadonly(!habilitado);
    txbx_email.setDisabled(!habilitado);
    txbx_email.setReadonly(!habilitado);
    txbx_calle.setDisabled(!habilitado);
    bbx_estructura.setDisabled(!habilitado);
    if(reparticion.getEsPadre()) {    	
    	bbx_reparticionPadre.setDisabled(true);
    }else {
    	bbx_reparticionPadre.setDisabled(!habilitado);
    	
    }
    ck_repa_padre.setDisabled(!habilitado);
    dbx_vigenciaDesde.setDisabled(!habilitado || edicion);
    dbx_vigenciaHasta.setDisabled(!habilitado || edicion);
    hbox_botones.setVisible(habilitado);
    hbox_visu.setVisible(!habilitado);
  }

  /**
   * Cargar componentes.
   */
  private void cargarComponentes() {
    // esto es para que se cargue la reparticion a modificar/visualizar en el
    // zul de reparticionSectorSelector
    getSession().setAttribute(ConstantesSesion.KEY_REPARTICION_MODIFICAR,
        reparticion.getCodigo());
    txbx_codigo.setValue(reparticion.getCodigo());
    txbx_numCalle.setValue(reparticion.getNumero());
    txbx_telefono.setValue(reparticion.getTelefono());
    txbx_descripcion.setValue(reparticion.getNombre());
    txbx_piso.setValue(reparticion.getPiso());
    txbx_fax.setValue(reparticion.getFax());
    txbx_oficina.setValue(reparticion.getOficina());
    txbx_email.setValue(reparticion.getEmail());

    txbx_calle.setValue(reparticion.getCalleReparticion() != null
        ? reparticion.getCalleReparticion().toString() : "");
    bbx_estructura.setValue(reparticion.getEstructura() != null
        ? reparticion.getEstructura().getNombreEstructura() : "");
    bbx_reparticionPadre.setValue(
        reparticion.getRepPadre() != null ? reparticion.getRepPadre().getCodigo() : "");

    estructuraSeleccionada = obtenerObjetoEstructuraCompleto(reparticion.getEstructura().getId());
    reparticionPadreSeleccionada = obtenerObjetoReparticionCompleto(
        reparticion.getRepPadre() != null ? reparticion.getRepPadre().getId() : null);

    ck_repa_padre.setChecked(reparticion.getEsPadre());
    dbx_vigenciaDesde.setValue(reparticion.getVigenciaDesde());
    dbx_vigenciaHasta.setValue(reparticion.getVigenciaHasta());

    binder.loadAll();
  }

  public EstructuraDTO obtenerObjetoEstructuraCompleto(Integer idEstructura) {
     
    return estructuraService.getById(idEstructura);
  }

  private ReparticionDTO obtenerObjetoReparticionCompleto(Integer id) {
    ReparticionDTO reparticion = reparticionService.getReparticionById(id);
    return reparticion;
  }

  public Textbox getTxbx_codigo() {
    return txbx_codigo;
  }

  public void setTxbx_codigo(Textbox txbx_codigo) {
    this.txbx_codigo = txbx_codigo;
  }

  public Textbox getTxbx_numCalle() {
    return txbx_numCalle;
  }

  public void setTxbx_numCalle(Textbox txbx_numCalle) {
    this.txbx_numCalle = txbx_numCalle;
  }

  public Textbox getTxbx_telefono() {
    return txbx_telefono;
  }

  public void setTxbx_telefono(Textbox txbx_telefono) {
    this.txbx_telefono = txbx_telefono;
  }

  public Textbox getTxbx_descripcion() {
    return txbx_descripcion;
  }

  public void setTxbx_descripcion(Textbox txbx_descripcion) {
    this.txbx_descripcion = txbx_descripcion;
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

  public Textbox getTxbx_email() {
    return txbx_email;
  }

  public void setTxbx_email(Textbox txbx_email) {
    this.txbx_email = txbx_email;
  }

  public Combobox getCbbx_sistema() {
    return cbbx_sistema;
  }

  public void setCbbx_sistema(Combobox cbbx_sistema) {
    this.cbbx_sistema = cbbx_sistema;
  }

  public Bandbox getBbx_reparticionPadre() {
    return bbx_reparticionPadre;
  }

  public void setBbx_reparticionPadre(Bandbox bbx_reparticionPadre) {
    this.bbx_reparticionPadre = bbx_reparticionPadre;
  }

  public Listbox getLbx_reparticionesSADEListbox() {
    return lbx_reparticionesSADEListbox;
  }

  public void setLbx_reparticionesSADEListbox(Listbox lbx_reparticionesSADEListbox) {
    this.lbx_reparticionesSADEListbox = lbx_reparticionesSADEListbox;
  }

  public Bandbox getBbx_estructura() {
    return bbx_estructura;
  }

  public void setBbx_estructura(Bandbox bbx_estructura) {
    this.bbx_estructura = bbx_estructura;
  }

  public Listbox getLbx_estructurasSADEListbox() {
    return lbx_estructurasSADEListbox;
  }

  public void setLbx_estructurasSADEListbox(Listbox lbx_estructurasSADEListbox) {
    this.lbx_estructurasSADEListbox = lbx_estructurasSADEListbox;
  }

  public Listbox getLbx_callesSADEListbox() {
    return lbx_callesSADEListbox;
  }

  public void setLbx_callesSADEListbox(Listbox lbx_callesSADEListbox) {
    this.lbx_callesSADEListbox = lbx_callesSADEListbox;
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

  public Vbox getVbox_modificar() {
    return vbox_modificar;
  }

  public void setVbox_modificar(Vbox vbox_modificar) {
    this.vbox_modificar = vbox_modificar;
  }

  public List<ReparticionDTO> getListaReparticionSeleccionada() {
    return listaReparticionSeleccionada;
  }

  public void setListaReparticionSeleccionada(List<ReparticionDTO> listaReparticionSeleccionada) {
    this.listaReparticionSeleccionada = listaReparticionSeleccionada;
  }

  public ReparticionDTO getReparticionPadreSeleccionada() {
    return reparticionPadreSeleccionada;
  }

  public void setReparticionPadreSeleccionada(ReparticionDTO reparticionPadreSeleccionada) {
    this.reparticionPadreSeleccionada = reparticionPadreSeleccionada;
  }

  public List<EstructuraDTO> getListaEstructurasSeleccionada() {
    return listaEstructurasSeleccionada;
  }

  public void setListaEstructurasSeleccionada(List<EstructuraDTO> listaEstructurasSeleccionada) {
    this.listaEstructurasSeleccionada = listaEstructurasSeleccionada;
  }

  public EstructuraDTO getEstructuraSeleccionada() {
    return estructuraSeleccionada;
  }

  public void setEstructuraSeleccionada(EstructuraDTO estructuraSeleccionada) {
    this.estructuraSeleccionada = estructuraSeleccionada;
  }

  public List<EstructuraDTO> getListaTodasEstructuras() {
    return listaTodasEstructuras;
  }

  public void setListaTodasEstructuras(List<EstructuraDTO> listaTodasEstructuras) {
    this.listaTodasEstructuras = listaTodasEstructuras;
  }

  public List<CalleDTO> getListaCallesSeleccionada() {
    return listaCallesSeleccionada;
  }

  public void setListaCallesSeleccionada(List<CalleDTO> listaCallesSeleccionada) {
    this.listaCallesSeleccionada = listaCallesSeleccionada;
  }

  public CalleDTO getCalleSeleccionada() {
    return calleSeleccionada;
  }

  public void setCalleSeleccionada(CalleDTO calleSeleccionada) {
    this.calleSeleccionada = calleSeleccionada;
  }

  public List<CalleDTO> getListaTodasCalles() {
    return listaTodasCalles;
  }

  public void setListaTodasCalles(List<CalleDTO> listaTodasCalles) {
    this.listaTodasCalles = listaTodasCalles;
  }

  public List<ReparticionDTO> getListaReparticiones() {
    return listaReparticiones;
  }

  public void setListaReparticiones(List<ReparticionDTO> listaReparticiones) {
    this.listaReparticiones = listaReparticiones;
  }

  public ReparticionDTO getReparticion() {
    return reparticion;
  }

  public void setReparticion(ReparticionDTO reparticion) {
    this.reparticion = reparticion;
  }

  public Textbox getTxtBusquedaReparticionPadreBusquedaSADE() {
    return txtBusquedaReparticionPadreBusquedaSADE;
  }

  public void setTxtBusquedaReparticionPadreBusquedaSADE(
      Textbox txtBusquedaReparticionPadreBusquedaSADE) {
    this.txtBusquedaReparticionPadreBusquedaSADE = txtBusquedaReparticionPadreBusquedaSADE;
  }

  public Textbox getTxtBusquedaEstructuraBusquedaSADE() {
    return txtBusquedaEstructuraBusquedaSADE;
  }

  public void setTxtBusquedaEstructuraBusquedaSADE(Textbox txtBusquedaEstructuraBusquedaSADE) {
    this.txtBusquedaEstructuraBusquedaSADE = txtBusquedaEstructuraBusquedaSADE;
  }

  public String getSelectedEnRed() {
    return selectedEnRed;
  }

  public void setSelectedEnRed(String selectedEnRed) {
    this.selectedEnRed = selectedEnRed;
  }

  public Textbox getTxbx_calle() {
    return txbx_calle;
  }

  public void setTxbx_calle(Textbox txbx_calle) {
    this.txbx_calle = txbx_calle;
  }
}
