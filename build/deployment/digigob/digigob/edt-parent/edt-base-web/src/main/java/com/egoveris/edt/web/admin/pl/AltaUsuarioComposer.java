package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

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
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.edt.web.pl.renderers.RolAsignadoRenderer;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.IPasswordService;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.base.util.ConstanstesAdminSade;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class AltaUsuarioComposer extends BaseComposer {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(AltaUsuarioComposer.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1448760107985222479L;

  protected AnnotateDataBinder binder;
  private ILdapService iLdapService;
  protected IPermisoService permisoService;
  private IAplicacionService aplicacionService;
  private ISectorService sectorService;
  private ISectorUsuarioService sectorUsuarioService;
  private IUsuarioHelper usuarioHelper;
  private ILdapAccessor ldapAccessor;
  private ICargoService cargoService;
  private IMailService iMailService;
	private IPasswordService iPasswordService;
  private IUsuarioService usuarioService;
  private IDatosUsuarioService datosUsuarioService;
  
//  protected Textbox txbx_nombre;
  
  protected Textbox txbx_primer_nombre;
  protected Textbox txbx_segundo_nombre;
  protected Textbox txbx_tercer_nombre;

  protected Textbox txbx_primer_apellido;
  protected Textbox txbx_segundo_apellido;
  protected Textbox txbx_tercer_apellido;

  
  
  protected Textbox txbx_password;
  protected Textbox txbx_confirmPassword;
  private Textbox txbx_uid;
  protected Textbox txbx_mail;
  protected Textbox txbx_legajo;
  private Listbox lstbx_rolesAsignados;
  private Combobox cbbx_sistema;
  
  protected UsuarioBaseDTO usuario;
  private DatosUsuarioDTO datosUsuario;
  private String selectedAplicacion;
  private List<RolDTO> listaRolAsignados;
  private List<String> listaAplicaciones;
  private RolAsignadoRenderer rolAsignadoRenderer;
  private ReparticionDTO reparticionSeleccionada;
  protected SectorDTO sectorSeleccionado;
  protected CargoDTO cargoSeleccionado;
  private Include inc_reparticionSectorSelector;
  private Include incRol;
  protected String ldapEntorno;

  private Groupbox groupboxRoles;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    try {
      this.ldapAccessor = (ILdapAccessor) SpringUtil.getBean("ldapAccessor");
      sectorService = (ISectorService) SpringUtil.getBean("sectorService");
      cargoService = (ICargoService) SpringUtil.getBean("cargoService");
      sectorUsuarioService = (ISectorUsuarioService) SpringUtil.getBean("sectorUsuarioService");
      permisoService = (IPermisoService) SpringUtil.getBean("permisoService");
      aplicacionService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
      usuarioHelper = (IUsuarioHelper) SpringUtil.getBean("usuarioHelper");
      ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
      iMailService = (IMailService) SpringUtil.getBean("iMailService");
      usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
      iPasswordService = (IPasswordService) SpringUtil.getBean("iPasswordService");
      datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
      iLdapService =(ILdapService) SpringUtil.getBean("ldapServiceImpl");
      
      
      this.usuario = new UsuarioBaseDTO();
      this.datosUsuario = new DatosUsuarioDTO();
      this.usuario.setPermisos(new ArrayList<String>());
      this.listaRolAsignados = new ArrayList<>();

//      if (null != getTipoUsuario()) {
//      	this.listaRolAsignados.addAll(
//            permisoService.filtrarListaPermisosPorGrupoUsuario(getTipoUsuario().getCodigo()));
//
//        if (TipoUsuarioEnum.AC.getCodigo().equals(getTipoUsuario().getCodigo())) {
//          // Se agrega permiso lider de proyecto
//          this.listaRolAsignados.addAll(
//              permisoService.filtrarListaPermisosPorGrupoUsuario(TipoUsuarioEnum.PL.getCodigo()));
//        }
//      }

      this.listaAplicaciones = new ArrayList<String>(
          permisoService.filtrarListaAplicacionesGrupos());
      Collections.sort(listaAplicaciones, new ComparatorApli());
      this.listaAplicaciones.add(0, ConstantesSesion.TODOS_SISTEMAS);
      this.rolAsignadoRenderer = new RolAsignadoRenderer(true);
      this.lstbx_rolesAsignados.setItemRenderer(this.rolAsignadoRenderer);
      this.selectedAplicacion = this.listaAplicaciones.get(0);
      configurarCombosReparticionSector();
  		configurarBandboxRol(comp, false);

      mostrarPermisos();
      this.binder = new AnnotateDataBinder(comp);
      this.binder.loadAll();
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }
  
  
	private void configurarBandboxRol(Component component, boolean deshabilitado) {
		incRol.setDynamicProperty(BandBoxRolComposer.DISABLED_BANDBOX, deshabilitado);
		incRol.setDynamicProperty(BandBoxRolComposer.COMPONENTE_PADRE, component);
		incRol.setSrc(BandBoxRolComposer.SRC);
    
		component.addEventListener(BandBoxRolComposer.ON_SELECT_ROL,
				new AltaUsuarioListener());
    
	}
	
	public void onQuitarRol(ForwardEvent event) {
		RolDTO quitarRol = (RolDTO) event.getData();
		listaRolAsignados.remove(quitarRol);
		binder.loadComponent(lstbx_rolesAsignados);
	}
	
	private void cleanBandboxRol() {
		Events.sendEvent(BandBoxRolComposer.EVENT_CLEAN, incRol.getChildren().get(0), null);
	}
	
	private void mensajeValidadorBandboxRol(String mensaje) {
		Events.sendEvent(BandBoxRolComposer.EVENT_VALIDAR, incRol.getChildren().get(0), mensaje);
	}
	
	private void cargarUsuarioRol(String usuario) {
		Events.sendEvent(BandBoxRolComposer.EVENT_CARGAR_DATOS, incRol.getChildren().get(0), usuario);
	}

  /**
   * Configurar combos reparticion sector.
   */
  private void configurarCombosReparticionSector() {
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR,
        true);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION,
        true);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION,
        false);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO, true);
    inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);
  }

	/**
	 * On click$btn alta usuario.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$btn_altaUsuario() throws InterruptedException {
		this.sectorSeleccionado = (SectorDTO) getSession().getAttribute(ConstantesSesion.SECTOR_SELECCIONADO);
		this.cargoSeleccionado = (CargoDTO) getSession().getAttribute(ConstantesSesion.CARGO_SELECCIONADO);
		if (validarCampos()) {
			
			this.usuario.setNombre(datosUsuario.getApellidoYNombre());
			
			try {
				// valido si el sector cuenta con algun usuario, sino pongo el
				// que se quiere dar de alta, con el rol de asignador
				if (!usuarioHelper.tieneUsuarioAsignador(sectorSeleccionado.getId())) {
					if (this.usuario.getPermisos() == null) {
						this.usuario.setPermisos(new ArrayList<String>());
					}
					PermisoDTO permiso = permisoService.obtenerPermisoAsignador();
					if (!usuario.getPermisos().contains(permiso.getPermiso())) {
						usuario.getPermisos().add(permiso.getPermiso());
					}
				}
			} catch (NegocioException e) {
				logger.error(e.getMessage(), e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			} catch (SecurityNegocioException e) {
				logger.error(e.getMessage(), e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.noActualizarUsuario"),
						Messagebox.OK, Messagebox.ERROR);
			}

			try {
				guardarUsuario();
			} catch (Exception e) {
				logger.error("error al modificar Datos del Usuario", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			try {
				usuarioService.indexarUsuario(usuario.getUid());
			} catch (Exception e) {
				logger.error("error al indexar datos del usuario", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			}
			
			boolean emailEnviado = true;
	    try {
	    	 iMailService.enviarMailAlta(usuario);
	    }
	    catch (EmailNoEnviadoException e) {
	    	logger.debug(e.getMessage(), e);
	    	emailEnviado = false;
	    }
			String mensaje = emailEnviado ? Labels.getLabel("eu.adminSade.usuario.mensajes.altaExitosa")
					: Labels.getLabel("eu.adminSade.usuario.mensajes.altaExitosa.emailNoEnviado");
	    
	    Messagebox.show(mensaje,
	        Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
	        Messagebox.INFORMATION);
	    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
	    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
		}
	}

	private void mostrarPermisos() {
    // boolean bool = false;
    // for (PermisoDTO permiso : listaPermisos) {
    // if(permiso.getIdPermiso() == Constantes.ASIGNAR_PERMISO) {
    // bool = true;
    // break;
    // }
    // }
    groupboxRoles.setVisible(true); // por ahora
  }

  /**
   * Guardar usuario.
   *
   * @throws NegocioException the negocio exception
   * @throws InterruptedException the interrupted exception
   */
  protected void guardarUsuario() throws NegocioException, InterruptedException {
    // TODO: AHA Definir que proceso debe registrarse
    SectorDTO sector = sectorSeleccionado;
    CargoDTO cargo = cargoSeleccionado;
    if (this.usuario.getPermisos() == null) {
      this.usuario.setPermisos(new ArrayList<String>());
    }
    datosUsuario.setUsuario(usuario.getUid().toUpperCase());
    agregarRoles(datosUsuario);
    this.usuario.getPermisos().addAll(this.datosUsuario.obtenerLosPermisos());

    usuario.setMail(this.txbx_mail.getValue());
    usuario.setLegajo(this.txbx_legajo.getValue());
    usuario.setUid(usuario.getUid().toUpperCase());
    //usuario.setNombre(this.txbx_nombre.getValue());
    usuario.setPassword(this.iPasswordService
				.generarPasswordAleatoria(ConstanstesAdminSade.CANTIDAD_CARACTERES_PASSWORD_ALEATORIA));
    iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
  	// almacenar datos usuario con los datos basicos
    //datosUsuario.setApellidoYNombre(txbx_nombre.getValue());
    datosUsuario.setMail(txbx_mail.getValue());
    datosUsuario.setSecretario(StringUtils.EMPTY);
    datosUsuario.setUsuarioAsesor(StringUtils.EMPTY);
    datosUsuario.setAceptacionTyC(false);
    
    iLdapService.darAltaUsuario(usuario, ConstanstesAdminSade.DIAS_PARA_LOGIN);
    
    
    datosUsuarioService.modificarDatosUsuario(datosUsuario);
    
    SectorUsuarioDTO sectorUsuario = new SectorUsuarioDTO(sector, usuario.getUid(), "SUME");
    
    if (this.sectorSeleccionado != null) {
      sectorUsuario.setCargoId(cargo.getId());
      sectorUsuarioService.vincularSectorUsuario(sectorUsuario);
    }
  }

  private void agregarRoles(DatosUsuarioDTO datosUsuario) {

  	for(RolDTO rol : listaRolAsignados) {
  		datosUsuario.addRol(rol, getUsername());
  	}
	}


	/**
   * Gets the permisos by cargo.
   *
   * @return the permisos by cargo
   */
//  private List<String> getPermisosByRoles() {
//  	Set<String> permisos = new HashSet<>();
//  	
//  	datosUsuario.getRoles()
//  	
//  	return new ArrayList<String>(permisos);
//  }

  public boolean validarUsername(String userName) {
    Pattern pattern3Seguidos = Pattern.compile("(?si)[^$]*(?:([a-z0-9])\\1{2,})+[^$]*");
    Pattern patternCaracteresValidos = Pattern.compile("(?si)[^a-z|\\d|\\.|_]+");
    Pattern pattern2digits = Pattern.compile("(?si)(\\d)");
    Pattern pattern1erCaracter = Pattern.compile("(?si)^[^a-zA-Z]");

    Matcher m = pattern3Seguidos.matcher(userName);
    if (m.matches()) {
      return false;
    }
    m = pattern2digits.matcher(userName);
    int i = 0;
    while (m.find()) {
      i++;
      if (i > 1) {
        return false;
      }
    }
    m = patternCaracteresValidos.matcher(userName);
    if (m.find()) {
      return false;
    }
    m = pattern1erCaracter.matcher(userName);
    if (m.find()) {
      return false;
    }

    return true;
  }

  private boolean validarUserNameProhibidos(String userName) {
    if(userName != null){
         for (int j = userName.length() - 1; j > 0; j--) {
           String content = this.nombresMap.get(userName.substring(j, userName.length()));
           if (content != null) {
             if (userName.toUpperCase().contains(content)) {
               return false;
             }
           }
         }
     
         for (int j = 0; j < userName.length() - 1; j++) {
           String content = this.nombresMap.get(userName.substring(0, j));
           if (content != null) {
             if (userName.toUpperCase().contains(content)) {
               return false;
             }
           }
         }
     
         List<String> listaNombresProhibidos = new ArrayList<String>(this.nombresMap.values());
     
         for (String s : listaNombresProhibidos) {
           if (userName.contains(s)) {
             return false;
           }
         }
      }
    // Pattern patternProhibidos =
    // Pattern.compile("\\d*(TEST|PRUEBA|CONSULTA|CAPACITACION)\\d*");
    // Matcher m = patternProhibidos.matcher(userName);
    // if (m.matches()) {
    // return false;
    // }
    return true;
  }

  /**
   * On blur$txbx uid.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onBlur$txbx_uid() throws InterruptedException {
    String nick = txbx_uid.getValue();
    if (nick.length() < 3) {
      throw new WrongValueException(this.txbx_uid,
          Labels.getLabel("eu.adminSade.validacion.uid.cant.caracter"));
    }

    usuario.setUid(usuario.getUid().toUpperCase());
    txbx_uid.setValue(usuario.getUid());
    this.binder.loadAll();
  }
  
  /**
   * On blur$txbx nombre.
   *
   * @throws InterruptedException the interrupted exception
   */
//  public void onBlur$txbx_nombre() throws InterruptedException {
//	    String name = txbx_nombre.getValue();
//	    if (name.isEmpty()) {
//	      throw new WrongValueException(this.txbx_nombre,
//	          Labels.getLabel("eu.adminSade.validacion.nombre"));
//	    } 
//	  }

//  public void onBlur$txbx_primer_nombre() {
//  	if(StringUtils.isNotBlank(txbx_primer_nombre.getValue())) {
//  		txbx_segundo_nombre.setDisabled(false);
//  	}
//  }
//  
//  public void onBlur$txbx_segundo_nombre() {
//  	if(StringUtils.isNotBlank(txbx_segundo_nombre.getValue())) {
//  		txbx_tercer_nombre.setDisabled(false);
//  	}
//  }
//  
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
   * Validar campos.
   *
   * @return true, if successful
   * @throws InterruptedException the interrupted exception
   */
  public boolean validarCampos() throws InterruptedException {
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
    if (!validarUsername(txbx_uid.getValue())) {
      throw new WrongValueException(this.txbx_uid,
          Labels.getLabel("eu.adminSade.validacion.uid.formato"));
    }
    if (!validarUserNameProhibidos(txbx_uid.getValue().toUpperCase())) {
      throw new WrongValueException(this.txbx_uid,
          Labels.getLabel("eu.adminSade.validacion.uid.palabras"));
    }
    if(vadlidarNombreUsuarioExistente(txbx_uid.getValue().toUpperCase())) {
      throw new WrongValueException(this.txbx_uid,
          Labels.getLabel("eu.adminSade.validacion.uid.existente"));   	
    }
    
    if (txbx_mail.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_mail,
          Labels.getLabel("eu.adminSade.validacion.mail"));
    }
    if(listaRolAsignados.isEmpty()) {
    	this.mensajeValidadorBandboxRol(
    			Labels.getLabel("edt.webapp.altausuario.validar.roles.empty"));
    }
    

    Utilitarios.validarMail(txbx_mail);
    this.onBlur$txbx_uid();
    this.validarCombosSectorYReparticion();
    return true;
  }

  private boolean vadlidarNombreUsuarioExistente(String uid) {
		return iLdapService.obtenerUsuarioPorUid(uid)!=null;
	}


	/**
   * Validar combos sector Y reparticion.
   */
  private void validarCombosSectorYReparticion() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHECK, comp, null);
  }

  public void onClick$btn_cancelar() {
    limpiarDeSessionLaReparticionSeleccionada();
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  private void limpiarDeSessionLaReparticionSeleccionada() {
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
    getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, null);
  }

  public void setUsuario(UsuarioBaseDTO usuario) {
    this.usuario = usuario;
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public UsuarioBaseDTO getUsuario() {
    return usuario;
  }

  public Include getInc_reparticionSectorSelector() {
    return inc_reparticionSectorSelector;
  }

  public void setInc_reparticionSectorSelector(Include inc_reparticionSectorSelector) {
    this.inc_reparticionSectorSelector = inc_reparticionSectorSelector;
  }

  public void setiLdapService(ILdapService iLdapService) {
    this.iLdapService = iLdapService;
  }

  public ILdapService getiLdapService() {
    return iLdapService;
  }

  public ILdapAccessor getLdapAccessor() {
    return ldapAccessor;
  }

  public void setLdapAccessor(ILdapAccessor ldapAccessor) {
    this.ldapAccessor = ldapAccessor;
  }

  public void setPermisoService(IPermisoService permisoService) {
    this.permisoService = permisoService;
  }

  public IPermisoService getPermisoService() {
    return permisoService;
  }

  public Listbox getLstbx_rolesAsignados() {
		return lstbx_rolesAsignados;
	}


	public void setLstbx_rolesAsignados(Listbox lstbx_rolesAsignados) {
		this.lstbx_rolesAsignados = lstbx_rolesAsignados;
	}


	public List<RolDTO> getListaRolAsignados() {
		return listaRolAsignados;
	}


	public void setListaRolAsignados(List<RolDTO> listaRolAsignados) {
		this.listaRolAsignados = listaRolAsignados;
	}


	public RolAsignadoRenderer getRolAsignadoRenderer() {
		return rolAsignadoRenderer;
	}


	public void setRolAsignadoRenderer(RolAsignadoRenderer rolAsignadoRenderer) {
		this.rolAsignadoRenderer = rolAsignadoRenderer;
	}


	public IAplicacionService getAplicacionService() {
    return aplicacionService;
  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }

  public List<String> getListaAplicaciones() {
    return listaAplicaciones;
  }

  public void setListaAplicaciones(List<String> listaAplicacionesMisTareas) {
    this.listaAplicaciones = listaAplicacionesMisTareas;
  }

  public Combobox getCbbx_sistema() {
    return cbbx_sistema;
  }

  public void setCbbx_sistema(Combobox cbbx_sistema) {
    this.cbbx_sistema = cbbx_sistema;
  }

  public String getSelectedAplicacion() {
    return selectedAplicacion;
  }

  public void setSelectedAplicacion(String selectedAplicacion) {
    this.selectedAplicacion = selectedAplicacion;
  }

//  public Textbox getTxbx_nombre() {
//    return txbx_nombre;
//  }
//
//  public void setTxbx_nombre(Textbox txbx_nombre) {
//    this.txbx_nombre = txbx_nombre;
//  }

  public Textbox getTxbx_uid() {
    return txbx_uid;
  }

  public void setTxbx_uid(Textbox txbx_uid) {
    this.txbx_uid = txbx_uid;
  }

  public Textbox getTxbx_mail() {
    return txbx_mail;
  }

  public void setTxbx_mail(Textbox txbx_mail) {
    this.txbx_mail = txbx_mail;
  }

  public Textbox getTxbx_legajo() {
    return txbx_legajo;
  }

  public void setTxbx_legajo(Textbox txbx_legajo) {
    this.txbx_legajo = txbx_legajo;
  }

  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

  public ISectorUsuarioService getSectorUsuarioService() {
    return sectorUsuarioService;
  }

  public void setSectorUsuarioService(ISectorUsuarioService sectorUsuarioService) {
    this.sectorUsuarioService = sectorUsuarioService;
  }

  public SectorDTO getSectorSeleccionado() {
    return sectorSeleccionado;
  }

  public void setSectorSeleccionado(SectorDTO sectorSeleccionado) {
    this.sectorSeleccionado = sectorSeleccionado;
  }

  public Groupbox getGroupboxPermisos() {
    return groupboxRoles;
  }

  public void setGroupboxPermisos(Groupbox groupboxPermisos) {
    this.groupboxRoles = groupboxPermisos;
  }

  public CargoDTO getCargoSeleccionado() {
    return cargoSeleccionado;
  }

  public void setCargoSeleccionado(CargoDTO cargoSeleccionado) {
    this.cargoSeleccionado = cargoSeleccionado;
  }
  
  
  
  public DatosUsuarioDTO getDatosUsuario() {
		return datosUsuario;
	}


	public void setDatosUsuario(DatosUsuarioDTO datosUsuario) {
		this.datosUsuario = datosUsuario;
	}



	private class AltaUsuarioListener implements EventListener<Event>{

		@SuppressWarnings("deprecation")
		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(BandBoxRolComposer.ON_SELECT_ROL)) {
				if(event.getData()==null) {
					return;
				}
				RolDTO agregarRol = (RolDTO) event.getData();
				
				if(!listaRolAsignados.contains(agregarRol)) {					
					listaRolAsignados.add(agregarRol);
					cleanBandboxRol();
					binder.loadComponent(lstbx_rolesAsignados);
				}else {
					Messagebox.show(Labels.getLabel("eu.altaUsuarioAdComposer.error.rolAsignado"), Labels.getLabel("eu.altaUsuarioAdComposer.asignarRol"), 
							Messagebox.OK, Messagebox.INFORMATION);
				}
				
			}
		}
  	
  }

}
