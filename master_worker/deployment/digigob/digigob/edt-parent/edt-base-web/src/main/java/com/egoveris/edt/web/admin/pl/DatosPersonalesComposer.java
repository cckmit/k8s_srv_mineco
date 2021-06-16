package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.generico.BandBoxRolComposer;
import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.IPermisoService;
import com.egoveris.edt.base.service.mail.IMailService;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.edt.web.admin.pl.renderers.PermisosAltaItemRenderer;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.edt.web.pl.renderers.RolAsignadoRenderer;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;
import com.egoveris.sharedsecurity.base.model.TipoUsuarioEnum;
import com.egoveris.sharedsecurity.base.model.UserConverter;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.IPasswordService;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioSolrService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.base.util.ConstanstesAdminSade;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosPersonalesComposer extends BaseComposer {

  private static final long serialVersionUID = -5362549948387867821L;

  private static final Logger logger = LoggerFactory.getLogger(DatosPersonalesComposer.class);

  @WireVariable("ldapServiceImpl")
  private ILdapService iLdapService;
  
  @WireVariable("datosUsuarioService")
  private IDatosUsuarioService datosUsuarioService;

  @WireVariable("iPasswordService")
  private IPasswordService iPasswordService;
  
  @WireVariable("iMailService")
  private IMailService iMailService;
  
  @WireVariable("permisoService")
  private IPermisoService permisoService;
  
  @WireVariable("aplicacionesService")
  private IAplicacionService aplicacionService;
  
  @WireVariable("sectorUsuarioService")
  private ISectorUsuarioService sectorUsuarioService;
  
  @Wire
  private Include inc_reparticionSectorSelector;
  
  @WireVariable("reparticionEDTService")
  private IReparticionEDTService reparticionService;

  @WireVariable("sectorService")
  private ISectorService sectorService;

  private UsuarioBaseDTO usuario;

  @WireVariable("usuarioSolrService")
  private IUsuarioSolrService usuarioSolrDao;
  
  @WireVariable("userConverter")
  private UserConverter userConverter;
  
  @WireVariable("usuarioServiceImpl")
  private IUsuarioService usuarioService;
  
  @WireVariable("usuarioHelper")
  private IUsuarioHelper usuarioHelper;

  // Version 3.2.8
  private DatosUsuarioDTO datosUsuario;
  private List<CargoDTO> cargos;
  private CargoDTO cargo;
  
  @WireVariable("cargoService")
  private ICargoService cargoService;

  @Autowired
  protected AnnotateDataBinder binder;
  private Window win_datosPersonales;
  
  private Textbox txbx_nombre;
  
  protected Textbox txbx_primer_nombre;
  protected Textbox txbx_segundo_nombre;
  protected Textbox txbx_tercer_nombre;

  protected Textbox txbx_primer_apellido;
  protected Textbox txbx_segundo_apellido;
  protected Textbox txbx_tercer_apellido;
  
  private Textbox txbx_uid;
  private Textbox txbx_mail;
  private Textbox txbx_legajo;
  private Textbox txbx_cuit;
  private Textbox txbx_passwordNueva;
  private Textbox txbx_passwordRepetirNueva;
  private Row row_resetearPassword;
  private Row row_eliminarUser;

  private Vbox vbox_passwordNueva;
  private Vbox vbox_permisosPersonales;
//  private Vbox vbox_permisosTotales;
  private Hbox hbox_visu;
  private Listbox lstbx_permisosTotales;
  private Listbox lstbx_rolesAsignados;
  private String selectedAplicacion;
  private Button btn_resetearPassword;
  private Combobox cbx_cargo;
  private Include incRol;

  private Hbox hbox_botones;
  private Vbox vbox_headerVer;
  private Vbox vbox_headerModificar;
  private Vbox vbox_modificar;
  private Map<?, ?> parametros;
  private Boolean passwordOk = false;
  private Window win_busquedaUsuario;
  private List<PermisoDTO> listaPermisosPersonales;
  private List<PermisoDTO> listaPermisos;
  
  private List<RolDTO> listaRolAsignados = new ArrayList<RolDTO>();
  
  private List<String> listaAplicaciones;
  private PermisosAltaItemRenderer permisosAltaItemRenderer;
  private ReparticionDTO reparticionSeleccionada;
  private SectorDTO sectorSeleccionado;
  private SectorUsuarioDTO sectorUsuario;
  private String ldapEntorno;
  private Boolean edicion;
  private Boolean esSoloLectura;
  
  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    comp.addEventListener(Events.ON_NOTIFY, new DatosPersonalesOnNotifyWindowListener(this));
    comp.addEventListener(Events.ON_OK, new DatosPersonalesOnNotifyWindowListener(this));
    ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
    cargos = new ArrayList<>();
    cargo = new CargoDTO();
    this.configurarPantalla(comp);
    inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);
    binder = new AnnotateDataBinder(comp);
    binder.loadAll();
  }

  /**
   * Configurar pantalla.
   * @throws InterruptedException 
   */
	private void configurarPantalla(Component comp) throws Exception {
		parametros = Executions.getCurrent().getArg();
		if (!parametros.isEmpty()) {
			edicion = parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
					&& (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR);
			usuario = (UsuarioBaseDTO) parametros.get(ConstantesSesion.KEY_USUARIO);
			esSoloLectura = (Boolean) parametros.get(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA);

			// Carga propiedades de reparticion
			datosUsuario = datosUsuarioService.getDatosUsuarioByUsername(usuario.getUid());
			getSession().setAttribute(ConstantesSesion.KEY_USUARIO_MODIFICAR, usuario.getUid());

			// Levanto sector y reparticion del Usuario
			sectorUsuario = sectorUsuarioService.getByUsername(usuario.getUid());

			if (sectorUsuario == null) {
				sectorUsuario = new SectorUsuarioDTO(sectorSeleccionado, usuario.getUid(),
						ConstantesSesion.PROCESO_SECTOR_USUARIO);
			} else {
				CargoDTO cargoTemp = cargoService.obtenerById(sectorUsuario.getCargoId());
				cargo = cargoTemp;
				cargarComboCargoReparticion();
			}

  		configurarBandboxRol(comp, !edicion);

			
			// Visualizacion desde Administrador
			if (edicion) {
				loadEdition();
			} else {
				if (Utilitarios.isProjectLeader() && !Utilitarios.isAdministradorCentral()) {
					inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_BANDBOXS, false);
				}
				loadReadOnly();
			}

		} else {
			obtenerDatosUsuarioLogueado();
		}
		
		// Carga de reparticion y sectores
		this.cargarComboSectorReparticion();
	}
	
	@SuppressWarnings("unchecked")
	private void configurarBandboxRol(Component component, boolean deshabilitado) {
		incRol.setDynamicProperty(BandBoxRolComposer.DISABLED_BANDBOX, deshabilitado);
		incRol.setDynamicProperty(BandBoxRolComposer.COMPONENTE_PADRE, component);
		incRol.setSrc(BandBoxRolComposer.SRC);
    
		component.addEventListener(BandBoxRolComposer.ON_SELECT_ROL,
				new DatosPersonalesOnNotifyWindowListener(this));
		
		listaRolAsignados.addAll(datosUsuario.getSoloRoles());
    
	}
	
	private void cleanBandboxRol() {
		Events.sendEvent(BandBoxRolComposer.EVENT_CLEAN, incRol.getChildren().get(0), null);
	}
	
	private void mensajeValidadorBandboxRol(String mensaje) {
		Events.sendEvent(BandBoxRolComposer.EVENT_VALIDAR, incRol.getChildren().get(0), mensaje);
	}
	
//	private void cargarBandboxRol(String usuario) {
//		Events.sendEvent(BandBoxRolComposer.EVENT_CARGAR_DATOS, incRol.getChildren().get(0), usuario);
//	}
	
	public void onQuitarRol(ForwardEvent event) {
		RolDTO quitarRol = (RolDTO) event.getData();
		listaRolAsignados.remove(quitarRol);
		datosUsuario.removeRol(quitarRol);
		binder.loadComponent(lstbx_rolesAsignados);
	}

	/**
	 * Load edition.
	 */
	private void loadEdition() {
		this.listaPermisos = new ArrayList<>();
		this.listaPermisos = permisoService.filtrarListaPermisosPorGrupoUsuario(getTipoUsuario().getCodigo());

    this.lstbx_rolesAsignados.setItemRenderer(new RolAsignadoRenderer(true));

		
		// Si el usuario logueado es PL, deshabilito la edicion de
		// datos
		if (Utilitarios.isAdministradorLocalReparticion()) {
			if (datosUsuario != null && datosUsuario.getCargoAsignado() != null
					&& datosUsuario.getCargoAsignado().isEsTipoBaja()) {
				setearRowBajaUsuario();
			}
			if (datosUsuario != null && datosUsuario.getCargoAsignado() == null) {
				setearRowBajaUsuario();
			} else {
				if (datosUsuario == null) {
					setearRowBajaUsuario();
				}
			}
		}

		if (Utilitarios.isProjectLeader() && !Utilitarios.isAdministradorCentral()) {
			row_resetearPassword.setVisible(false);
			vbox_permisosPersonales.setVisible(false);
			row_eliminarUser.setVisible(false);
	//		vbox_permisosTotales.setVisible(true);
			vbox_headerVer.setVisible(false);
			vbox_headerModificar.setVisible(true);
			vbox_modificar.setVisible(false);
			configurarComboSectorReparticion(true);
			cbx_cargo.setDisabled(true);
	//		txbx_nombre.setDisabled(true);
			
			txbx_primer_nombre.setDisabled(true);
//			onBlur$txbx_primer_nombre();
//			onBlur$txbx_segundo_nombre();
			txbx_segundo_nombre.setDisabled(true);
			txbx_tercer_nombre.setDisabled(true);
			txbx_primer_apellido.setDisabled(true);
//			onBlur$txbx_primer_apellido();
//			onBlur$txbx_segundo_apellido();
			txbx_segundo_apellido.setDisabled(true);
			txbx_tercer_apellido.setDisabled(true);
			
			
			txbx_mail.setDisabled(true);
			txbx_legajo.setDisabled(true);
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_BANDBOXS, false);
		} else {
			habilitarCampos(true);
			row_resetearPassword.setVisible(true);
			vbox_permisosPersonales.setVisible(true);
//			vbox_permisosTotales.setVisible(true);
			vbox_headerVer.setVisible(false);
			vbox_headerModificar.setVisible(true);
			vbox_modificar.setVisible(false);
			configurarComboSectorReparticion(true);

			if (Utilitarios.isAdministradorCentral()) {
				setearRowBajaUsuario();
			}

			if (datosUsuario != null) {
				if (Utilitarios.isAdministradorCentral()) {

					List<CargoDTO> cargosTotales = new ArrayList<>();
					cargosTotales.addAll(cargoService.getCargosActivosVigentes());
					cargos.addAll(cargosTotales);
					txbx_cuit.setValue(datosUsuario.getNumeroCuit());
				} else {
					cbx_cargo.setDisabled(true);
				}
			}
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_BANDBOXS, true);
		}

		if (null != getTipoUsuario() && getTipoUsuario().getCodigo().equals(TipoUsuarioEnum.AC.getCodigo())) {
			listaPermisos.addAll(permisoService.filtrarListaPermisosPorGrupoUsuario(TipoUsuarioEnum.PL.getCodigo()));
		}

		// Si el usuario logueado es PL, solo puede modificar
		// permisos dentro de los sistemas este habilitado
		if (Utilitarios.isProjectLeader() && !Utilitarios.isAdministradorCentral()) {
			listaAplicaciones = super.getAplicaionesAdministradas();
			Collections.sort(listaAplicaciones, new ComparatorApli());
		}
		// Se pueden modificar los permisos para todos los sistemas
		else {
			this.listaPermisosPersonales = new ArrayList<PermisoDTO>(cargarUsuario(usuario.getPermisos()));
			Collections.sort(listaPermisosPersonales, new ComparatorSistema());
			this.listaAplicaciones = permisoService.filtrarListaAplicacionesGrupos();
			Collections.sort(listaAplicaciones, new ComparatorApli());
			this.listaAplicaciones.add(0, ConstantesSesion.TODOS_SISTEMAS);
		}
	}

	/**
	 * Load edition.
	 */
	private void loadReadOnly() {
    this.lstbx_rolesAsignados.setItemRenderer(new RolAsignadoRenderer(false));

		vbox_headerVer.setVisible(true);
		vbox_headerModificar.setVisible(false);
		vbox_modificar.setVisible(false);
		vbox_permisosPersonales.setVisible(true);
		habilitarCampos(false);
		HabilitarCamposPassword(false);
		configurarComboSectorReparticion(false);
		txbx_legajo.setDisabled(true);
		if (datosUsuario != null && Utilitarios.isAdministradorCentral()) {
			txbx_cuit.setValue(datosUsuario.getNumeroCuit());
		}
		this.listaPermisos = new ArrayList<>();
		this.listaPermisos = permisoService.filtrarListaPermisosPorGrupoUsuario(TipoUsuarioEnum.AL.getCodigo());
		this.listaPermisos.addAll(permisoService.filtrarListaPermisosPorGrupoUsuario(TipoUsuarioEnum.PL.getCodigo()));
		this.listaPermisosPersonales = new ArrayList<>(cargarUsuario(usuario.getPermisos()));
		Collections.sort(listaPermisosPersonales, new ComparatorSistema());
		this.listaAplicaciones = permisoService.filtrarListaAplicacionesGrupos();
		Collections.sort(listaAplicaciones, new ComparatorApli());
	}

	/**
	 * Setear row baja usuario.
	 */
	private void setearRowBajaUsuario() {
    if (sectorUsuario != null && sectorUsuario.getId() != null) {

      row_eliminarUser.setVisible(true);
    } else {
      row_eliminarUser.setVisible(false);
    }
  }

  private void configurarComboSectorReparticion(boolean datosModificables) {
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION,
        datosModificables);
  }

	/**
	 * Cargar combo sector reparticion.
	 */
	private void cargarComboSectorReparticion() {
		if (sectorUsuario != null && sectorUsuario.getSector() != null
				&& sectorUsuario.getSector().getReparticion() != null) {
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.REPARTICION_SELECCIONADA,
					sectorUsuario.getSector().getReparticion());
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.SECTOR_SELECCIONADO, sectorUsuario.getSector());
		}
		if (esSoloLectura != null) {
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA, esSoloLectura);
		}
		if (parametros.get(ConstantesSesion.KEY_VISUALIZAR) != null) {
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA_SECTOR, true);
			inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA_CARGO, true);
		}
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR, true);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO, true);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION, true);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_SECTOR, true);
	}

  /**
   * Cargar combo cargo reparticion.
   */
  private void cargarComboCargoReparticion() {
    if (cargo != null) {
      inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.CARGO_SELECCIONADO, cargo);
    }
  }

  private Collection<? extends PermisoDTO> cargarUsuario(List<String> permisos) {
    List<PermisoDTO> salida = new ArrayList<PermisoDTO>();
    PermisoDTO permiso;

    if (permisos != null) {
      for (String aux : permisos) {
        permiso = new PermisoDTO();
        permiso.setPermiso(aux);
        permiso.setSistema(devuelveIdDelSistema(aux));
        salida.add(permiso);
      }
    }

    return salida;
  }

  private String devuelveIdDelSistema(String data) {
    String salida = "";
    for (PermisoDTO permiso : this.listaPermisos) {
      if (permiso.getPermiso().trim().equals(data.trim())) {
        salida = permiso.getSistema();
      }
    }
    return salida;
  }

  /**
   * Obtener datos usuario logueado.
   *
   * @throws InterruptedException the interrupted exception
   */
  private void obtenerDatosUsuarioLogueado() throws InterruptedException {
    try {
      usuario = new UsuarioBaseDTO();
      usuario.setUid(getUsername());
      usuario = this.iLdapService.obtenerUsuarioPorUid(usuario.getUid());
      getSession().setAttribute("usuarioModificar", usuario.getUid());
      vbox_permisosPersonales.setVisible(true);
      datosUsuario = datosUsuarioService.getDatosUsuarioByUsername(usuario.getUid());
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos()) {
    	usuario.setNombre(datosUsuario.getApellidoYNombre());
      verificarPasswordPopup();
    }
  }

  /**
   * carga los permisos de acuerdo al grupo que se haya seleccionado en el combo
   * de módulos
   * 
   * @throws InterruptedException
   */
  public void onSelect$cbbx_sistema() throws InterruptedException {

    try {
      String nombre = this.selectedAplicacion;
      this.listaPermisos = new ArrayList<PermisoDTO>();
      if (Utilitarios.isProjectLeader() && !Utilitarios.isAdministradorCentral()) {
        this.listaPermisos.addAll(permisoService.filtrarListaPermisosPorSistema(nombre));
      } else {
        if (Utilitarios.isAdministradorCentral()) {
          this.listaPermisos
              .addAll(permisoService.filtrarListaPermisosPorSistemaYGrupoUsuario("PL", nombre));
        }
        this.listaPermisos.addAll(permisoService
            .filtrarListaPermisosPorSistemaYGrupoUsuario(getTipoUsuario().getCodigo(), nombre));
      }
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(Labels.getLabel("eu.datosPersonalesComposer.msgbox.errorFiltrarPermisos"),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);
    }

    this.binder.loadAll();
  }

  /**
   * Se modifico cargo.
   *
   * @return true, if successful
   */
  private boolean seModificoCargo() {
    // solo entro al metodo si estoy dentro de una edicion, o sea si hay una
    // reparticion y sector origen
		if (edicion && sectorUsuario.getCargoId() != null) {
			Integer codigoCargoOrigen = sectorUsuario.getCargoId();
			Integer codigoCargoDestino = cargo.getId();
			return !codigoCargoOrigen.equals(codigoCargoDestino);
		}
		return false;
	}
  
  /**
   * Se modifico sector.
   *
   * @return the boolean
   */
  private Boolean seModificoSector() {
    // solo entro al metodo si estoy dentro de una edicion, o sea si hay una
    // reparticion y sector origen
		if (edicion && sectorUsuario.getSector() != null) {
			String codigoReparticionOrigen = sectorUsuario.getSector().getReparticion().getCodigo();
			String codigoSectorOrigen = sectorUsuario.getSector().getCodigo();
			String codigoReparticionDestino = reparticionSeleccionada.getCodigo();
			String codigoSectorDestino = sectorSeleccionado.getCodigo();
			Boolean seModificoSector = (!codigoReparticionOrigen.equals(codigoReparticionDestino)
					|| !codigoSectorOrigen.equals(codigoSectorDestino));
			if (seModificoSector && codigoSectorOrigen != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

  /**
   * Guardar.
   *
   * @param passwordOk the password ok
   * @throws InterruptedException the interrupted exception
   */
  public void guardar(boolean passwordOk) throws InterruptedException {
    setReparticionSeleccionada(
        (ReparticionDTO) getSession().getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA));
    setSectorSeleccionado((SectorDTO) getSession().getAttribute(ConstantesSesion.SECTOR_SELECCIONADO));
    setCargo((CargoDTO) getSession().getAttribute(ConstantesSesion.CARGO_SELECCIONADO));

    if (!passwordOk) {

      // muestro error de password
      blanquearCamposPassword();
      Messagebox.show(Labels.getLabel("eu.adminSade.validacion.passActual"),
          Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    } else if (null != datosUsuario) {
      try {
        if (!StringUtils.isBlank(txbx_passwordNueva.getValue())) {
        	this.usuario.setPassword(txbx_passwordNueva.getValue());
        }

        datosUsuario.setCargoAsignado(cargo);
        sectorUsuario.setCargoId(cargo.getId());

					
        // valido si hay usuarios asignadores para el sector
        // seleccionado
        if (!existeUsuarioAsignador()) {
           mostrarError(Labels.getLabel("eu.datosPersonales.datosUsuario.avisoSinAsignador"));
           return;
        }
				sectorUsuarioService.modificarSectorUsuario(sectorUsuario);
        
        
        // Se modifico el sector
        if (this.seModificoSector()) {
          if (!permitirBorrarUnicoAsignador()) {
            mostrarError(
                Labels.getLabel("eu.datosPersonales.datosUsuario.avisoSinAsignadorOrigen"));
            return;
          }
          // valido si el sector destino contiene un usuario
          // asignador, sino me autoasigno
          PermisoDTO permiso = permisoService.obtenerPermisoAsignador();
          if (CollectionUtils.isEmpty(
              usuarioHelper.obtenerUsuariosAsignadoresPorSector(sectorSeleccionado.getId()))) {
            if (this.usuario.getPermisos() == null) {
            	this.usuario.setPermisos(new ArrayList<String>());
            }
            if (!this.usuario.getPermisos().contains(permiso.getPermiso())) {
            	this.usuario.getPermisos().add(permiso.getPermiso());
            }
            mostrarWarning(
                Labels.getLabel("eu.datosPersonales.datosUsuario.avisoPrimerAsignador"));
          }

          // Como cambio el sector-reparticion, hago que el usuario
          // elija un nuevo sector-mesa
          datosUsuario.setCambiarMesa(Boolean.TRUE);
          
          // Asignar sector y cargo
					sectorUsuario.setSector(sectorSeleccionado);
					sectorUsuarioService.modificarSectorUsuario(sectorUsuario);
					datosUsuario.setIdSectorInterno(sectorSeleccionado.getId());
        }
        
        if (edicion) {
          if (this.self == null) {
            throw new IllegalAccessError("The self associated component is not present");
          }
          Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
        }

        // Guardar datos usuario
      	datosUsuario.setApellidoYNombre(this.usuario.getNombre());
        datosUsuario.setMail(this.usuario.getMail());
        datosUsuarioService.guardarDatosUsuario(datosUsuario);
        //agregar roles con el usuario
        agregarRoles(datosUsuario);
        datosUsuarioService.guardarDatosUsuarioRol(datosUsuario);
        
        // asignar nuevos permisos si hay cargo nuevo
        this.usuario.getPermisos().clear();
        this.usuario.getPermisos().addAll(datosUsuario.obtenerLosPermisos());
        
        this.usuario.setNombre(this.datosUsuario.getApellidoYNombre());
        
        // Sincronizar con solr
        Usuario usuarioSolr = usuarioService.obtenerUsuario(usuario.getUid());
        if (usuarioSolr != null) {
          usuarioSolr.setNombreApellido(usuario.getNombre());
          usuarioSolr.setEmail(usuario.getMail());
          if(sectorSeleccionado != null){
              usuarioSolr.setCodigoSectorInterno(sectorSeleccionado.getCodigo());
              if(sectorSeleccionado.getReparticion() != null){
                usuarioSolr.setCodigoReparticion(sectorSeleccionado.getReparticion().getCodigo());
              }
          }
          if (cargo != null) {
          	usuarioSolr.setCargo(cargo.getCargoNombre());
          }
          usuarioSolrDao.addToIndex(userConverter.cargarDTO(usuarioSolr));
        }
        
        // Sincronizar con Ldap
        iLdapService.modificarUsuario(usuario);

        Events.sendEvent(new Event(Events.ON_USER, win_busquedaUsuario, usuario));
        Messagebox.show(Labels.getLabel("eu.adminSade.usuario.mensajes.modificacionExitosa"),
            Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);

        blanquearCamposPassword();
        cargos = new ArrayList<CargoDTO>();
        cargo = null;
        onClick$btn_salir();
      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      } catch (SecurityNegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(),
            Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.noActualizarUsuario"), Messagebox.OK,
            Messagebox.ERROR);
      }
    }
  }
  
  private void agregarRoles(DatosUsuarioDTO datosUsuario) {
  	
  	for(RolDTO rol : listaRolAsignados) {
  		datosUsuario.addRol(rol, getUsername());
  	}
	}

	/**
	 * Permitir borrar asignador.
	 *
	 * @return the boolean
	 * @throws NegocioException the negocio exception
	 */
	private Boolean existeUsuarioAsignador() throws NegocioException {
		PermisoDTO permiso = permisoService.obtenerPermisoAsignador();

		List<String> usuariosAsignadores = null;

		if (sectorUsuario.getSector() != null) {
			usuariosAsignadores = usuarioHelper.obtenerUsuariosAsignadoresPorSector(sectorSeleccionado.getId());
		}

		// valido que haya asignadores en el sector
		if ((null != usuariosAsignadores) && !CollectionUtils.isEmpty(usuariosAsignadores)) {
			if (usuariosAsignadores.size() > 1) {
				return Boolean.TRUE;
			} else {
				// valido si el asignador soy yo
				if (usuariosAsignadores.contains(usuario.getUid())) {
					// valido que el usuario mantenga el rol de asignador
					for (String user : usuariosAsignadores) {
						if (user.equals(this.usuario.getUid()) && !this.usuario.getPermisos().contains(permiso.getPermiso())) {
							return Boolean.FALSE;
						}
					}
				}
			}
		} else if (!usuario.getPermisos().contains(permiso.getPermiso())) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * Permitir borrar unico asignador.
	 *
	 * @return the boolean
	 * @throws NegocioException the negocio exception
	 */
	private Boolean permitirBorrarUnicoAsignador() throws NegocioException {
		List<String> usuariosAsignadores = usuarioHelper.obtenerUsuariosAsignadoresPorSector(sectorSeleccionado.getId());

		// valido que haya asignadores en el sector
		if (!CollectionUtils.isEmpty(usuariosAsignadores)) {
			if (usuariosAsignadores.size() == 1 && usuariosAsignadores.contains(usuario.getUid())) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		}
		return Boolean.TRUE;
	}

  public void onClick$btn_salir() {
    limpiarDeSessionLaReparticionSeleccionada();
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_NOTIFY));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    habilitarCampos(false);
    HabilitarCamposPassword(false);
    blanquearCamposPassword();
    if (edicion) {
      if (this.self == null) {
        throw new IllegalAccessError("The self associated component is not present");
      }
      Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
    }
    limpiarDeSessionLaReparticionSeleccionada();
  }

  public void onClick$tbbtn_ModificarPerfil() {
    habilitarCampos(true);
    HabilitarCamposPassword(true);
  }

  @SuppressWarnings("unchecked")
	public void onClick$btn_resetearPassword() throws InterruptedException {
    if (usuario.getMail() != null && !usuario.getMail().isEmpty()) {
      Messagebox.show(
          Labels.getLabel("eu.adminSade.usuario.mensajes.resetearPassword",
              new String[] { usuario.getMail(), "\n" }),
          Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
          Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {

            @Override
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                usuario.setPassword(iPasswordService.generarPasswordAleatoria(
                    ConstanstesAdminSade.CANTIDAD_CARACTERES_PASSWORD_ALEATORIA));
                try {
                  iLdapService.modificarUsuario(usuario);
                  iMailService.enviarMailResetoPassword(usuario);
                  Messagebox.show(
                      Labels.getLabel("eu.adminSade.usuario.mensajes.modificacionExitosa"),
                      Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
                      Messagebox.INFORMATION);
                  Events.sendEvent(win_datosPersonales.getParent(), new Event(Events.ON_USER));
                } catch (NegocioException | EmailNoEnviadoException e) {
                  logger.error(e.getMessage(), e);
                  Messagebox.show(e.getMessage(),
                      Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
                      Messagebox.ERROR);
                }
                break;
              case Messagebox.NO:
                break;
              }
            }
          });
    } else {
      Messagebox.show(
          Labels.getLabel("eu.adminSade.usuario.mensajes.mailVacio", new String[] { "\n" }),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  // Esta logica es la del boton de la eliminacion del user
  public void onClick$btn_EliminarUser() throws InterruptedException, SecurityNegocioException {
    if (datosUsuario != null && datosUsuario.getAceptacionTyC()) {

      Usuario u = usuarioService.obtenerUsuario(datosUsuario.getUsuario());
      ReparticionDTO reparticion = reparticionService
          .getReparticionByCodigo(u.getCodigoReparticionOriginal());
      SectorDTO sector = sectorService
          .buscarSectorPorRepaYSector(u.getCodigoSectorInternoOriginal(), reparticion);

      if (reparticion != null && sector != null) {
        {
          Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.avisoDeleteUser"),
              Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
              Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
                @Override
                public void onEvent(Event evt)
                    throws InterruptedException, SecurityNegocioException, NegocioException {
                  if ("onYes".equals(evt.getName())) {
                    Usuario u = usuarioService.obtenerUsuario(datosUsuario.getUsuario());
                    ReparticionDTO reparticion = reparticionService
                        .getReparticionByCodigo(u.getCodigoReparticionOriginal());
                    SectorDTO sector = sectorService.buscarSectorPorRepaYSector(
                        u.getCodigoSectorInternoOriginal(), reparticion);
                    if(sector != null){
                    List<String> usuariosAsignadores = usuarioHelper
                        .obtenerUsuariosAsignadoresPorSector(sector.getId());
                    Boolean tieneAsignadores = !CollectionUtils.isEmpty(usuariosAsignadores)
                        ? Boolean.TRUE : Boolean.FALSE;
                    Boolean esAsignador = Boolean.FALSE;

                    if (tieneAsignadores) {
                      for (String usuario : usuariosAsignadores) {
                        if (usuario.contains(u.getUsername())) {
                          esAsignador = Boolean.TRUE;
                          break;
                        }
                      }
                    }
                    if (esAsignador && usuariosAsignadores.size() <= 1) {
                      logger.error(
                          "Error, el usuario es el unico asignador de su sector, por favor verifique los permisos."
                              + datosUsuario.getUsuario());
                      Messagebox.show(
                          "Error, " + Labels
                              .getLabel("eu.datosPersonalesComposer.msgbox.usuarioUnicoAsignado"),
                          Labels.getLabel("eu.adminSade.usuario.generales.informacion"),
                          Messagebox.OK, Messagebox.ERROR);
                      return;
                    } else {

                      if (reparticion != null) {
                          if (!usuarioHelper.tieneUsuarioAsignador(sector.getId())) {
                            Messagebox.show(
                                "Error, " + Labels.getLabel(
                                    "eu.datosPersonalesComposer.msgbox.sectorNoPoseeUsuario"),
                                Labels.getLabel("eu.adminSade.usuario.generales.informacion"),
                                Messagebox.OK, Messagebox.ERROR);
                            return;
                          }
                      }
                    }
                  }
                    datosUsuarioService.eliminarDatosUsuario(datosUsuario);
                    sectorUsuarioService.desvincularSectorUsuario(datosUsuario.getUsuario());
                    onClick$btn_salir();
                    Messagebox.show(
                        Labels.getLabel("eu.datosPersonales.datosUsuario.eliminacionExitosa"),
                        Labels.getLabel("eu.adminSade.usuario.generales.informacion"),
                        Messagebox.OK, Messagebox.INFORMATION);
                  }
                }
              });

        }
      }
    } else {
      ReparticionDTO reparticion = reparticionService.getReparticionByUserName(usuario.getUid());
      SectorUsuarioDTO sector = sectorUsuarioService.getByUsername(usuario.getUid());
      if (sector != null && reparticion != null) {
        Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.avisoDeleteUser"),
            Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
            Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
              @Override
              public void onEvent(Event evt)
                  throws InterruptedException, SecurityNegocioException, NegocioException {
                if ("onYes".equals(evt.getName())) {
                  sectorUsuarioService.desvincularSectorUsuario(usuario.getUid());
                  onClick$btn_salir();
                  Messagebox.show(
                      Labels.getLabel(
                          "eu.datosPersonales.datosUsuario.avisoDeleteOkUserRepa.Sector"),
                      Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
                      Messagebox.INFORMATION);

                }
              }
            });

      } else {
        Messagebox.show(
            Labels.getLabel("eu.datosPersonales.datosUsuario.usuarioNotieneReparticion"),
            Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION, new EventListener() {
              @Override
              public void onEvent(Event evt) throws InterruptedException {
                if ("onOK".equals(evt.getName())) {
                  onClick$btn_salir();
                }
              }
            });
      }
    }

  }

  private void habilitarCampos(boolean habilitado) {
  	
    //txbx_nombre.setReadonly(!habilitado);
    txbx_primer_nombre.setReadonly(!habilitado);
    txbx_segundo_nombre.setReadonly(!habilitado);
    txbx_tercer_nombre.setReadonly(!habilitado);
    
  	txbx_primer_apellido.setReadonly(!habilitado);
  	txbx_segundo_apellido.setReadonly(!habilitado);
  	txbx_tercer_apellido.setReadonly(!habilitado);

  	
    txbx_mail.setReadonly(!habilitado);
    txbx_legajo.setReadonly(!habilitado);
    hbox_botones.setVisible(habilitado);
    hbox_visu.setVisible(!habilitado);
  }

  private void HabilitarCamposPassword(boolean habilitado) {
    vbox_passwordNueva.setVisible(habilitado);
  }

  private void blanquearCamposPassword() {
    txbx_passwordNueva.setValue("");
    txbx_passwordRepetirNueva.setValue("");
  }

  public boolean validarCampos() {
//    if (txbx_nombre.getValue().isEmpty()) {
//      throw new WrongValueException(this.txbx_nombre,
//          Labels.getLabel("eu.adminSade.validacion.nombre"));
//    }
  	
  	if(txbx_primer_nombre.getValue().isEmpty()) {
  		throw new WrongValueException(this.txbx_primer_nombre, 
  				Labels.getLabel("eu.adminSade.validacion.primer.nombre"));
  	}
  	
  	if(txbx_primer_apellido.getValue().isEmpty()) {
  		throw new WrongValueException(this.txbx_primer_apellido, 
  				Labels.getLabel("eu.adminSade.validacion.primer.apellido"));
  	}
  	
    if (txbx_uid.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_uid, Labels.getLabel("eu.adminSade.validacion.uid"));
    }
    if (txbx_mail.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_mail,
          Labels.getLabel("eu.adminSade.validacion.mail"));
    }
    if (!txbx_passwordNueva.getValue().equals(txbx_passwordRepetirNueva.getValue())) {
      throw new WrongValueException(this.txbx_passwordRepetirNueva,
          Labels.getLabel("eu.adminSade.validacion.passConfirmarPass"));
    }

    Utilitarios.validarMail(txbx_mail);
    validarCombosSectorYReparticion();

    return true;
  }

  private void validarCombosSectorYReparticion() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHECK, comp, null);
  }

  public boolean verificarPasswordPopup() throws InterruptedException {
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

  private void limpiarDeSessionLaReparticionSeleccionada() {
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
    getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, null);
  }
  
//  public void onBlur$txbx_primer_nombre() {
//  	if(StringUtils.isNotBlank(txbx_primer_nombre.getValue())) {
//  		txbx_segundo_nombre.setDisabled(false);
//  	}else {
//  		txbx_segundo_nombre.setDisabled(true);
//  	  txbx_segundo_nombre.focus();
//  	}
//  }
  
//  public void onBlur$txbx_segundo_nombre() {
//  	if(StringUtils.isNotBlank(txbx_segundo_nombre.getValue())) {
//  		txbx_tercer_nombre.setDisabled(false);
//  	}else {
//  		txbx_tercer_nombre.setDisabled(true);
//  		
//  	}
//  }
  
//  public void onBlur$txbx_primer_apellido() {
//  	if(StringUtils.isNotBlank(txbx_primer_apellido.getValue())) {
//  		txbx_segundo_apellido.setDisabled(false);
//  	}
//  }
//  
//  public void onBlur$txbx_segundo_apellido() {
//  	if(StringUtils.isNotBlank(txbx_segundo_apellido.getValue())) {
//  		txbx_tercer_apellido.setDisabled(false);
//  	}
//  }

  /**
   * Refresca los datos al viajar entre las pestañas
   * 
   */
  final class DatosPersonalesOnNotifyWindowListener implements EventListener {
    private DatosPersonalesComposer composer;

    public DatosPersonalesOnNotifyWindowListener(DatosPersonalesComposer comp) {
      this.composer = comp;
    }

    @Override
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          this.composer.obtenerDatosUsuarioLogueado();
        }
      }
      if (event.getName().equals(Events.ON_OK)) {
        if (event.getData() != null) {
          Map<String, Boolean> map = (Map<String, Boolean>) event.getData();
          passwordOk = map.get("isOk");
          this.composer.guardar(passwordOk);
          if (passwordOk) {
            // onClick$btn_salir();
          }
        }
      }
      if(event.getName().equals(BandBoxRolComposer.ON_SELECT_ROL)) {
				if(event.getData()==null) {
					return;
				}
				RolDTO agregarRol = (RolDTO) event.getData();
				
				if(!this.composer.getListaRolAsignados().contains(agregarRol)) {	
					
					this.composer.agregarRolAsignados(agregarRol);
					this.composer.cleanBandboxRol();
					this.composer.getBinder().loadComponent(lstbx_rolesAsignados);
				}else {
					Messagebox.show(Labels.getLabel("eu.altaUsuarioAdComposer.error.rolAsignado"), Labels.getLabel("eu.altaUsuarioAdComposer.asignarRol"), 
							Messagebox.OK, Messagebox.INFORMATION);
				}
				
      }
    }
  }

  public void setUsuario(UsuarioBaseDTO usuario) {
    this.usuario = usuario;
  }

  public UsuarioBaseDTO getUsuario() {
    return usuario;
  }

  public void setPermisoService(IPermisoService permisoService) {
    this.permisoService = permisoService;
  }

  public IPermisoService getPermisoService() {
    return permisoService;
  }

  public List<PermisoDTO> getListaPermisos() {
    return listaPermisos;
  }

  public void setListaPermisos(List<PermisoDTO> listaPermisos) {
    this.listaPermisos = listaPermisos;
  }

  public List<String> getListaAplicaciones() {
    return listaAplicaciones;
  }

  public void setListaAplicaciones(List<String> listaAplicaciones) {
    this.listaAplicaciones = listaAplicaciones;
  }

  public PermisosAltaItemRenderer getPermisosAltaItemRenderer() {
    return permisosAltaItemRenderer;
  }

  public void setPermisosAltaItemRenderer(PermisosAltaItemRenderer permisosAltaItemRenderer) {
    this.permisosAltaItemRenderer = permisosAltaItemRenderer;
  }

  public String getSelectedAplicacion() {
    return selectedAplicacion;
  }

  public void setSelectedAplicacion(String selectedAplicacion) {
    this.selectedAplicacion = selectedAplicacion;
  }

  public IAplicacionService getAplicacionService() {
    return aplicacionService;
  }

  public IReparticionEDTService getReparticionService() {
    return reparticionService;
  }

  public void setReparticionService(IReparticionEDTService reparticionService) {
    this.reparticionService = reparticionService;
  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }

  public Row getRow_eliminarUser() {
    return row_eliminarUser;
  }

  public void setRow_eliminarUser(Row row_eliminarUser) {
    this.row_eliminarUser = row_eliminarUser;
  }

  public List<PermisoDTO> getListaPermisosPersonales() {
    return listaPermisosPersonales;
  }

  public void setListaPermisosPersonales(List<PermisoDTO> listaPermisosPersonales) {
    this.listaPermisosPersonales = listaPermisosPersonales;
  }

  public SectorDTO getSectorSeleccionado() {
    return sectorSeleccionado;
  }

  public void setSectorSeleccionado(SectorDTO sectorSeleccionado) {
    this.sectorSeleccionado = sectorSeleccionado;
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public Include getInc_reparticionSectorSelector() {
    return inc_reparticionSectorSelector;
  }

  public void setInc_reparticionSectorSelector(Include inc_reparticionSectorSelector) {
    this.inc_reparticionSectorSelector = inc_reparticionSectorSelector;
  }

  public List<CargoDTO> getCargos() {
    return cargos;
  }

  public void setCargos(List<CargoDTO> cargos) {
    this.cargos = cargos;
  }

  public CargoDTO getCargo() {
    return cargo;
  }

  public void setCargo(CargoDTO cargo) {
    this.cargo = cargo;
  }

  public Combobox getCbx_cargo() {
    return cbx_cargo;
  }

  public void setCbx_cargo(Combobox cbx_cargo) {
    this.cbx_cargo = cbx_cargo;
  }

  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

	public List<RolDTO> getListaRolAsignados() {
		return listaRolAsignados;
	}

	public void setListaRolAsignados(List<RolDTO> listaRolAsignados) {
		this.listaRolAsignados = listaRolAsignados;
	}
	
	public void agregarRolAsignados(RolDTO rol) {
		this.listaRolAsignados.add(rol);
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public DatosUsuarioDTO getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuarioDTO datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

}

class ComparatorSistema implements Comparator<PermisoDTO> {

  @Override
  public int compare(PermisoDTO o1, PermisoDTO o2) {
    String sistema1 = o1.getSistema();
    String sistema2 = o2.getSistema();
    int resultado = sistema1.compareTo(sistema2);
    if (resultado != 0) {
      return resultado;
    }
    sistema1 = o1.getPermiso();
    sistema2 = o2.getPermiso();
    resultado = sistema1.compareTo(sistema2);
    if (resultado != 0) {
      return resultado;
    }

    return resultado;
  }

}

class ComparatorApli implements Comparator<String> {

  @Override
  public int compare(String o1, String o2) {

    String sistema1 = o1;
    String sistema2 = o2;

    return sistema1.compareTo(sistema2);
  }

}
