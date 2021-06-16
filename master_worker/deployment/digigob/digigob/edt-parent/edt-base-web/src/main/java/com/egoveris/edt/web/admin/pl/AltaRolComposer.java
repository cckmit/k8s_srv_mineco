package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.base.service.IPermisoService;
import com.egoveris.edt.base.service.IRolService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.edt.web.admin.pl.renderers.RolAltaItemRenderer;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;

public class AltaRolComposer extends BaseComposer {

	/**
	*
	*/
	private static final long serialVersionUID = -8633730204071165283L;

	private static final Logger logger = LoggerFactory.getLogger(AltaRolComposer.class);

	protected AnnotateDataBinder binder;

	private IRolService rolService;

	@Autowired
	private Textbox txbx_nombre;

	@Autowired
	private Checkbox chkbx_vigente;

	private List<PermisoDTO> listaPermisos;

	// @Autowired
	// private Combobox cbx_permiso;

	private RolDTO rol;

	private PermisoDTO selectedPermiso;

	private Listbox lstbx_permisos;

	private Map<?, ?> parametros;

	private boolean esAlta;

	private IPermisoService permisoService;
  private ICargoService cargoService;
  private ILdapService iLdapService;
  private IDatosUsuarioService datosUsuarioService;
  private IUsuarioHelper usuarioHelper;
  
	private Combobox cbbx_sistema;
	private RolAltaItemRenderer rolAltaItemRenderer;
	private String selectedAplicacion;
	private List<String> listaAplicaciones;
	private List<PermisoDTO> permisosIniciales = new ArrayList<>();
	private List<PermisoDTO> permisosGenericos = new ArrayList<>();
	private List<PermisoDTO> allPermisos = new ArrayList<>();
	
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);

		parametros = Executions.getCurrent().getArg();
		permisoService = (IPermisoService) SpringUtil.getBean("permisoService");
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");
		esAlta = (Boolean) parametros.get("alta");
		rolService = (IRolService) SpringUtil.getBean("rolService");
		iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
		datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
		usuarioHelper= (IUsuarioHelper) SpringUtil.getBean("usuarioHelper");
		if (!esAlta) {
			rol = (RolDTO) parametros.get("rol");
		}
		cargarCompontentes();

		this.binder = new AnnotateDataBinder(comp);
		this.binder.loadAll();
	}

	private void cargarCompontentes() throws NegocioException {
		this.rol = (RolDTO) Executions.getCurrent().getArg().get("rol");
		this.listaPermisos = new ArrayList<>();
		// Remover permisos genericos, estos se asignarán siempre al guardar
		if (null != getTipoUsuario()) {
			allPermisos = permisoService.filtrarListaPermisosPorGrupoUsuario(getTipoUsuario().getCodigo());
			allPermisos.stream().forEach(per -> {
				String permiso = per.getPermiso().toUpperCase();
				String[] priv = ConstantesSesion.getPrincipales();
	      for (int i = 0; i < priv.length; i++) {
	          String privilege = priv[i].trim();
	          if (permiso.contains(privilege)) {
	    				permisosGenericos.add(per);
	    			}
	      }
			});
			
			// Se remueven los genericos
			allPermisos.removeAll(permisosGenericos);
		}
		
		this.listaPermisos.addAll(allPermisos);
		this.listaAplicaciones = new ArrayList<>(permisoService.filtrarListaAplicacionesGrupos());
		this.listaAplicaciones.add(0, ConstantesSesion.TODOS_SISTEMAS);
		this.rolAltaItemRenderer = new RolAltaItemRenderer(this);
		this.lstbx_permisos.setItemRenderer(this.rolAltaItemRenderer);
		this.selectedAplicacion = this.listaAplicaciones.get(0);

		if (this.rol != null) {
			txbx_nombre.setValue(this.rol.getRolNombre());
			chkbx_vigente.setChecked(this.rol.getEsVigente());
			permisosIniciales.addAll(this.rol.getListaPermisos());
		} else {
			this.rol = new RolDTO();
			this.rol.setListaPermisos(new ArrayList<PermisoDTO>());
		}
	}

	public void onSelect$cbbx_sistema() throws InterruptedException {
		if (null != this.selectedAplicacion) {
			filterPermisos();
		}
	}

	public void onChange$cbbx_sistema() {
		if (null != this.selectedAplicacion) {
			filterPermisos();
		}
	}
	
	/**
	 * Filter permisos.
	 */
	private void filterPermisos() {
		try {
			this.rolAltaItemRenderer = new RolAltaItemRenderer(this);
			this.listaPermisos.clear();
			if ("Todos".equals(this.selectedAplicacion)) {
				this.listaPermisos.addAll(allPermisos);
			} else {
				this.listaPermisos.addAll(allPermisos.stream()
						.filter(per -> per.getSistema().equalsIgnoreCase(this.selectedAplicacion)).collect(Collectors.toList()));
			}
		} catch (final NegocioException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(Labels.getLabel("eu.adminSade.usuario.generales.errorPermisosSistema"),
					Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK, Messagebox.ERROR);
		}
		this.binder.loadAll();
	}

	public void onClick$btn_altaRol() throws InterruptedException {

		if (validarCampos()) {

			Messagebox.show(Labels.getLabel("eu.adminSade.usuario.mensajes.consultaOperacion"),
					Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {

						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								try {
									guardarRol();
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
	}

	/**
	 * Guardar rol.
	 *
	 * @throws NegocioException the negocio exception
	 * @throws InterruptedException the interrupted exception
	 */
	private void guardarRol() throws NegocioException, InterruptedException {
		// Asignar datos genericos si es nuevo
		if (null == rol.getId()) {
			rol.setFechaCreacion(new Date());
			rol.setUsuarioCreacion((String) Executions.getCurrent().getSession().getAttribute(ConstantesSesion.SESSION_USERNAME));
			rol.getListaPermisos().addAll(permisosGenericos);
		}
		rol.setRolNombre(this.txbx_nombre.getValue());
		rol.setEsVigente(this.chkbx_vigente.isChecked());
		rol.setUsuarioModificacion(
				(String) Executions.getCurrent().getSession().getAttribute(ConstantesSesion.SESSION_USERNAME));
		rolService.save(rol);
		
		// sincronizar el cambio del rol con los usuarios, si existe cambio de algun
		// permiso
//		if (CollectionUtils.isNotEmpty(permisosIniciales) && this.hayCambio(permisosIniciales, rol.getListaPermisos())) {
//			this.synchronizedLdap();
//		}

		Messagebox.show(Labels.getLabel("eu.adminSade.rol.mensajes.altaExitosa"),
				Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK, Messagebox.INFORMATION);

		Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	/**
	 * Synchronized ldap.
	 */
//	private void synchronizedLdap() {
//		// Obtener todos los cargos asociados al Rol
//		List<CargoDTO> cargos = cargoService.getCargosByRolId(rol.getId());
//		if (CollectionUtils.isNotEmpty(cargos)) {
//			Map<CargoDTO, List<DatosUsuarioDTO>> mapUser = new HashMap<>();
//			for (CargoDTO cargo : cargos) {
//				// Obtener usuarios asociados a ese cargo
//				List<DatosUsuarioDTO> datosUsuario = datosUsuarioService.getDatosUsuariosByCargo(cargo);
//				if (CollectionUtils.isNotEmpty(datosUsuario)) {
//					mapUser.put(cargo, datosUsuario);
//				}
//			}

			// Cargar los usuarios de ldap y sincronizar con los nuevos permisos de cada
			// cargo
//			for (Map.Entry<CargoDTO, List<DatosUsuarioDTO>> entry : mapUser.entrySet()) {
//				List<String> permisosList = cargoService.buscarPermisosPorCargo(entry.getKey());
//				entry.getValue().stream().forEach(dto -> this.updateUser(permisosList, dto));
//			}
//		}
//	}

	/**
	 * Update user.
	 *
	 * @param permisosList the permisos list
	 * @param dto the dto
	 */
	private void updateUser(List<String> permisosList, DatosUsuarioDTO dto) {
		UsuarioBaseDTO dtoBase = this.iLdapService.obtenerUsuarioPorUid(dto.getUsuario());
		if (null != dtoBase) {
			if (null == dtoBase.getPermisos()) {
				dtoBase.setPermisos(new ArrayList<>());
			} else {
				dtoBase.getPermisos().clear();
			}
			dtoBase.getPermisos().addAll(permisosList);
			// valido si el sector destino contiene un usuario
			// asignador, sino me autoasigno
			PermisoDTO permiso = permisoService.obtenerPermisoAsignador();
			if (CollectionUtils.isEmpty(usuarioHelper.obtenerUsuariosAsignadoresPorSector(dto.getIdSectorInterno()))
					&& !dtoBase.getPermisos().contains(permiso.getPermiso())) {
				dtoBase.getPermisos().add(permiso.getPermiso());
			}
			// Sincronizar con Ldap
			iLdapService.modificarUsuario(dtoBase);
		}
	}

	/**
	 * Hay cambio.
	 *
	 * @param permisosIniciales the permisos iniciales
	 * @param listaPermisosNuevos the lista permisos nuevos
	 * @return true, if successful
	 */
	boolean hayCambio(List<PermisoDTO> permisosIniciales, List<PermisoDTO> listaPermisosNuevos) {
		List<Integer> listOne = permisosIniciales.stream().map(PermisoDTO::getIdPermiso).collect(Collectors.toList());
		List<Integer> listTwo = listaPermisosNuevos.stream().map(PermisoDTO::getIdPermiso).collect(Collectors.toList());
		Collections.sort(listOne);
		Collections.sort(listTwo);
		return !listOne.equals(listTwo);
	}

	public boolean validarCampos() throws InterruptedException {
		if (txbx_nombre.getValue().isEmpty()) {
			throw new WrongValueException(this.txbx_nombre, Labels.getLabel("eu.adminSade.validacion.nombre"));
		}

		if (esAlta && !validarNombreRol(txbx_nombre.getValue())) {
			throw new WrongValueException(this.txbx_nombre, Labels.getLabel("eu.adminSade.validacion.existeRol"));
		}

		return true;
	}

	public boolean validarNombreRol(final String nombre) {

		final Collection<RolDTO> lista = rolService.getRolesActivos();

		final RolDTO value = CollectionUtils.find(lista, new Predicate() {

			@Override
			public boolean evaluate(final Object rol) {
				return ((RolDTO) rol).getRolNombre().equals(nombre);
			}
		});

		if (value != null) {
			return false;
		}

		return true;
	}

	public void onClick$btn_cancelar() {
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

	public List<PermisoDTO> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(final List<PermisoDTO> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	// public Combobox getCbx_permiso() {
	// return cbx_permiso;
	// }
	//
	// public void setCbx_permiso(Combobox cbx_permiso) {
	// this.cbx_permiso = cbx_permiso;
	// }

	public PermisoDTO getSelectedPermiso() {
		return selectedPermiso;
	}

	public void setSelectedPermiso(final PermisoDTO selectedPermiso) {
		this.selectedPermiso = selectedPermiso;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(final RolDTO rol) {
		this.rol = rol;
	}

	public Map<?, ?> getParametros() {
		return parametros;
	}

	public void setParametros(final Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public boolean isEsAlta() {
		return esAlta;
	}

	public void setEsAlta(final boolean esAlta) {
		this.esAlta = esAlta;
	}

	public Combobox getCbbx_sistema() {
		return cbbx_sistema;
	}

	public void setCbbx_sistema(final Combobox cbbx_sistema) {
		this.cbbx_sistema = cbbx_sistema;
	}

	public RolAltaItemRenderer getRolAltaItemRenderer() {
		return rolAltaItemRenderer;
	}

	public void setRolAltaItemRenderer(final RolAltaItemRenderer rolAltaItemRenderer) {
		this.rolAltaItemRenderer = rolAltaItemRenderer;
	}

	public String getSelectedAplicacion() {
		return selectedAplicacion;
	}

	public void setSelectedAplicacion(final String selectedAplicacion) {
		this.selectedAplicacion = selectedAplicacion;
	}

	public List<String> getListaAplicaciones() {
		return listaAplicaciones;
	}

	public void setListaAplicaciones(final List<String> listaAplicacionesMisTareas) {
		this.listaAplicaciones = listaAplicacionesMisTareas;
	}

	public Listbox getLstbx_permisos() {
		return lstbx_permisos;
	}

	public void setLstbx_permisos(final Listbox lstbx_permisos) {
		this.lstbx_permisos = lstbx_permisos;
	}

}
