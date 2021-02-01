package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.IPermisoService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;

@SuppressWarnings("deprecation")
public class AltaCargoComposer extends BaseComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4166174331310154516L;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AltaCargoComposer.class);

	/** The Constant CARGOS_ALTA_CARGO_ZUL. */
	public static final String CARGOS_ALTA_CARGO_ZUL = "/administrator/tabsCargos/altaCargo.zul";
	
	protected AnnotateDataBinder binder;
	
	private ICargoService cargoService;
	private IPermisoService permisoService;
	private IDatosUsuarioService datosUsuarioService;
//	private IRolService rolService;
  private ILdapService iLdapService;
  private IUsuarioHelper usuarioHelper;
  
	private Textbox txbx_nombre;
	private Checkbox chkbx_vigente;
	private Checkbox chkbx_restringido;
//	private Listbox lstbx_roles;

	private CargoDTO cargo;
	private Map<?, ?> parametros;
	private List<String> listaTipo;
	private String selectedTipo;
//	private List<RolDTO> rolesIniciales = new ArrayList<>();
//	private List<RolDTO> listaRoles;

	Boolean esAlta;

	private Include inc_reparticionSectorSelector;

	private String title;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		parametros = Executions.getCurrent().getArg();
		cargoService = (ICargoService) SpringUtil.getBean("cargoService");
		permisoService = (IPermisoService) SpringUtil.getBean("permisoService");
//		rolService = (IRolService) SpringUtil.getBean("rolService");
		datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
		esAlta = (Boolean) parametros.get("alta");
		iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
		usuarioHelper= (IUsuarioHelper) SpringUtil.getBean("usuarioHelper");
		if (!esAlta) {
			this.cargo = (CargoDTO) parametros.get("cargo");
			this.title = Labels.getLabel("eu.adminSade.modificarCargo");
		} else {
			this.cargo = new CargoDTO();
			//this.cargo.setListaRoles(new ArrayList<RolDTO>());
			this.title = Labels.getLabel("eu.adminSade.altaCargo");
		}
		cargarCompontentes();
		configurarCombosReparticionSector();
		this.binder = new AnnotateDataBinder(comp);
		this.binder.loadAll();
	}

	private void cargarCompontentes() {
		this.cargo = (CargoDTO) Executions.getCurrent().getArg().get("cargo");
		this.listaTipo = new ArrayList<String>();
		listaTipo.add(ConstantesSesion.CARGO_TIPO_BAJA);
		listaTipo.add(ConstantesSesion.CARGO_TIPO_ALTO);
 
//	listaRoles = rolService.getRolesVigentes();

//	lstbx_roles.setItemRenderer(new CargoAltaItemRenderer(this));

		this.selectedTipo = this.listaTipo.get(0);

		if (this.cargo != null) {
			txbx_nombre.setValue(this.cargo.getCargoNombre());
			chkbx_restringido.setChecked(this.cargo.isEsTipoBaja());
			chkbx_vigente.setChecked(this.cargo.getVigente());
			if (this.cargo.isEsTipoBaja()) {
				this.selectedTipo = this.listaTipo.get(0);
			} else {
				this.selectedTipo = this.listaTipo.get(1);
			}
			//rolesIniciales.addAll(this.cargo.getListaRoles());
		} else {
			this.cargo = new CargoDTO();
			//this.cargo.setListaRoles(new ArrayList<RolDTO>());
		}
	}

	private void configurarCombosReparticionSector() {
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR, false);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO, false);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION, false);
		inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION, true);
		inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);
	}

	/**
	 * On click$btn alta cargo.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$btn_altaCargo() throws InterruptedException {
		if (validarCampos()) {
			confirmarAlta();
		}
	}
	
	/**
	 * Confirmar alta.
	 */
	@SuppressWarnings("unchecked")
	private void confirmarAlta() {
		Messagebox.show(Labels.getLabel("eu.adminSade.usuario.mensajes.consultaOperacion"),
				Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener() {
					@Override
					public void onEvent(final Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							try {
								guardarCargo();
								if (esAlta) {
									guardarHistorialCargo(0);
								} else {
									guardarHistorialCargo(1);
								}
								break;
							} catch (final Exception e) {
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
	}

	/**
	 * Guardar cargo.
	 *
	 * @throws NegocioException the negocio exception
	 * @throws InterruptedException the interrupted exception
	 */
	private void guardarCargo() throws NegocioException, InterruptedException {
		// Asignar datos genericos si es nuevo
		if (null == cargo.getId()) {
			cargo.setFechaCreacion(new Date());
			cargo.setUsuarioCreacion((String) Executions.getCurrent().getSession().getAttribute(ConstantesSesion.SESSION_USERNAME));
		}
		cargo.setCargoNombre(this.txbx_nombre.getValue());
		cargo.setEsTipoBaja(this.chkbx_restringido.isChecked());
		cargo.setVigente(this.chkbx_vigente.isChecked());
		cargo.setUsuarioModificacion(
				(String) Executions.getCurrent().getSession().getAttribute(ConstantesSesion.SESSION_USERNAME));
		cargoService.save(cargo);

		// sincronizar el cambio del rol con los usuarios, si existe cambio de algun
		// permiso
//		if (CollectionUtils.isNotEmpty(rolesIniciales) && this.hayCambio(rolesIniciales, cargo.getListaRoles())) {
//			this.synchronizedLdap();
//		}
		
		if (esAlta) {
			Messagebox.show(Labels.getLabel("eu.adminSade.cargo.mensajes.altaExitosa"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			Messagebox.show(Labels.getLabel("eu.adminSade.cargo.mensajes.modificacionExitosa"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
					Messagebox.INFORMATION);
		}
		Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	/**
	 * Synchronized ldap.
	 */
//	private void synchronizedLdap() {
//		List<DatosUsuarioDTO> listUser = new ArrayList<>();
		// Obtener usuarios asociados a ese cargo
//		List<DatosUsuarioDTO> datosUsuario = datosUsuarioService.getDatosUsuariosByCargo(cargo);
//		if (CollectionUtils.isNotEmpty(datosUsuario)) {
//			listUser.addAll(datosUsuario);
//		}

		// Cargar los usuarios de ldap y sincronizar con los nuevos permisos de cada
		// cargo
//		List<String> permisosList = cargoService.buscarPermisosPorCargo(cargo);
//		listUser.stream().forEach(dto -> this.updateUser(permisosList, dto));
//	}
	
	/**
	 * Update user.
	 *
	 * @param permisosList the permisos list
	 * @param dto the dto
	 */
//	private void updateUser(List<String> permisosList, DatosUsuarioDTO dto) {
//		UsuarioBaseDTO dtoBase = this.iLdapService.obtenerUsuarioPorUid(dto.getUsuario());
//		if (null != dtoBase) {
//			if (null == dtoBase.getPermisos()) {
//				dtoBase.setPermisos(new ArrayList<>());
//			} else {
//				dtoBase.getPermisos().clear();
//			}
//			dtoBase.getPermisos().addAll(permisosList);
//			// valido si el sector destino contiene un usuario
//			// asignador, sino me autoasigno
//			PermisoDTO permiso = permisoService.obtenerPermisoAsignador();
//			if (CollectionUtils.isEmpty(usuarioHelper.obtenerUsuariosAsignadoresPorSector(dto.getIdSectorInterno()))
//					&& !dtoBase.getPermisos().contains(permiso.getPermiso())) {
//				dtoBase.getPermisos().add(permiso.getPermiso());
//			}
//			// Sincronizar con Ldap
//			iLdapService.modificarUsuario(dtoBase);
//		}
//	}
	
	/**
	 * Hay cambio.
	 *
	 * @param rolesIniciales the roles iniciales
	 * @param listaRolesNuevos the lista roles nuevos
	 * @return true, if successful
	 */
//	private boolean hayCambio(List<RolDTO> rolesIniciales, List<RolDTO> listaRolesNuevos) {
//		List<Integer> listOne = rolesIniciales.stream().map(RolDTO::getId).collect(Collectors.toList());
//		List<Integer> listTwo = listaRolesNuevos.stream().map(RolDTO::getId).collect(Collectors.toList());
//		Collections.sort(listOne);
//		Collections.sort(listTwo);
//		return !listOne.equals(listTwo);
//	}

	private void guardarHistorialCargo(final Integer tipo) throws NegocioException, InterruptedException {
	}

	public boolean validarNombreCargo(final String nombreCargo) {

		Collection<CargoDTO> lista = cargoService.getCargosActivos();

		@SuppressWarnings("unchecked")
		CargoDTO value = CollectionUtils.find(lista, new Predicate() {

			@Override
			public boolean evaluate(final Object cargo) {
				return ((CargoDTO) cargo).getCargoNombre().equals(nombreCargo);
			}
		});

		if (value != null) {
			return false;
		}

		return true;
	}

	public boolean validarCampos() throws InterruptedException {
		if (txbx_nombre.getValue().isEmpty()) {
			throw new WrongValueException(this.txbx_nombre, Labels.getLabel("eu.adminSade.validacion.nombre"));
		}

		if (esAlta && !validarNombreCargo(txbx_nombre.getValue())) {
			throw new WrongValueException(this.txbx_nombre, Labels.getLabel("eu.adminSade.validacion.existeCargo"));
		}
		return true;
	}

	public void onClick$btn_cancelar() {
		getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	public Textbox getTxbx_nombre() {
		return txbx_nombre;
	}

	public void setTxbx_nombre(final Textbox txbx_nombre) {
		this.txbx_nombre = txbx_nombre;
	}

	public Checkbox getChkbx_vigente() {
		return chkbx_vigente;
	}

	public void setChkbx_vigente(final Checkbox chkbx_vigente) {
		this.chkbx_vigente = chkbx_vigente;
	}

	public Checkbox getChkbx_restringido() {
		return chkbx_restringido;
	}

	public void setChkbx_restringido(final Checkbox chkbx_restringido) {
		this.chkbx_restringido = chkbx_restringido;
	}

	public CargoDTO getCargo() {
		return cargo;
	}

	public void setCargo(final CargoDTO cargo) {
		this.cargo = cargo;
	}

	public Map<?, ?> getParametros() {
		return parametros;
	}

	public void setParametros(final Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public List<String> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(final List<String> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public String getSelectedTipo() {
		return selectedTipo;
	}

	public void setSelectedTipo(final String selectedTipo) {
		this.selectedTipo = selectedTipo;
	}

//	public List<RolDTO> getListaRoles() {
//		return listaRoles;
//	}
//
//	public void setListaRoles(final List<RolDTO> listaRoles) {
//		this.listaRoles = listaRoles;
//	}
//
//	public Listbox getLstbx_roles() {
//		return lstbx_roles;
//	}
//
//	public void setLstbx_roles(final Listbox lstbx_roles) {
//		this.lstbx_roles = lstbx_roles;
//	}
//
//	public IRolService getRolServices() {
//		return rolService;
//	}
//
//	public void setRolServices(final IRolService rolServices) {
//		this.rolService = rolServices;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}