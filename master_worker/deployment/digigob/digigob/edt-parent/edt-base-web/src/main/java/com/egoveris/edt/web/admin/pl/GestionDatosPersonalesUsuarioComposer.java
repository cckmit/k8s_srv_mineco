package com.egoveris.edt.web.admin.pl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.exception.SystemException;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioGenericoDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class GestionDatosPersonalesUsuarioComposer extends BaseComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1182252526230524139L;
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(GestionDatosPersonalesUsuarioComposer.class);
	
	/** The binder. */
	private AnnotateDataBinder binder;
	private DatosUsuarioDTO datosUsuario;
	private ICargoService cargoService;
	private IDatosUsuarioService datosUsuarioService;
	private IReparticionEDTService reparticionService;
	private ISectorService sectorService;
	private ISectorUsuarioService sectorUsuarioService;
	private IUsuarioService usuarioService;
	private ILdapService iLdapService;
	private IUsuarioHelper usuarioHelper;
  private IUsuarioAplicacionService usuarioBuzonGrupalService;
  private IUsuarioAplicacionService usuarioMisSistemasService;
  private IUsuarioAplicacionService usuarioMisTareasService;
  private IUsuarioAplicacionService usuarioMisSupervisadosService;
  private IAplicacionService aplicacionesService;
  
	private List<CargoDTO> cargosDisponibles;
	private List<SectorDTO> sectoresMesaDisponibles;
	private CargoDTO selectedCargo;
	private SectorDTO selectedSector;
	private Textbox txbx_email;
	private Textbox txbx_cuit;
	private Textbox txbx_passwordActual;
	private Combobox cbx_cargo;
	private Combobox cbx_sectorMesa;
	private Textbox txbx_nuevoPsswd;
	private Textbox txbx_nuevoPsswdConfirmacion;
	private Checkbox chbx_tyc;
	private List<UsuarioReducido> listaTodosLosUsuarios;
	private List<UsuarioReducido> listaTodosLosUsuariosSupervisados;
	private List<UsuarioReducido> listaSuperioresSeleccionados;
	private UsuarioReducido superiorSeleccionado;
	private Bandbox bandBoxSuperiores;
	private Listbox superioresListbox;
	private Listfooter totalUsuarios;
	private String ldapEntorno;
	private Boolean esEdicion;
	private String terminosYCondiciones;
	private Button btn_eliminar;
  private List<AplicacionDTO> listaAplicaciones;
  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.loadServices();
		this.inicializarListasDeUsuarios();
		this.inicializarListaAplicaciones();
		cargosDisponibles = cargoService.getCargosActivosVigentes();
		datosUsuario = datosUsuarioService.getDatosUsuarioByUsername(getUsername());
		
		final ReparticionDTO repUsuario = reparticionService.getReparticionByUserName(getUsername());
		if (repUsuario != null) {
			sectoresMesaDisponibles = sectorService.buscarSectoresPorReparticion(repUsuario);
		} else {
			Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.noPoseeReparticion"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK, Messagebox.INFORMATION,
					new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onOK".equals(evt.getName())) {
								Utilitarios.doLogout();
							}
						}
					});
		}

    // SI NO HAY DATOS USUARIO, CARGAR info DESDE USUARIO SOLR O LDAP
		if (!datosUsuarioService.existeDatosUsuario(this.getUsername())) {
			esEdicion = false;
			Usuario usuario = super.getCurrentUser();
			if (null == datosUsuario) {
				datosUsuario = new DatosUsuarioDTO(usuario.getUsername());
			}
			datosUsuario.setApellidoYNombre(usuario.getNombreApellido());
			datosUsuario.setMail(usuario.getEmail());
			btn_eliminar.setVisible(false);
			
			// cargar sector unico si existe
			if (CollectionUtils.isNotEmpty(sectoresMesaDisponibles) && sectoresMesaDisponibles.size() == 1) {
				selectedSector = sectoresMesaDisponibles.get(0);
				cbx_sectorMesa.setValue(selectedSector.getCodigo() + " - " + selectedSector.getNombre());
				datosUsuario.setIdSectorInterno(selectedSector.getId());
				datosUsuario.setCodigoSectorInterno(selectedSector.getCodigo());
			}
			
			// Se edita para que el usuario al completar los datos por primera vez, pueda seleccionar el cargo que le corresponda
			SectorUsuarioDTO sectorUsuarioDTO = sectorUsuarioService.getByUsername(getUsername());
			if (sectorUsuarioDTO != null) {
				CargoDTO cargoDTO = cargoService.obtenerById(sectorUsuarioDTO.getCargoId());
				if (cargoDTO != null) {
					cargosDisponibles.clear();
					cargosDisponibles.add(cargoDTO);
					selectedCargo = cargoDTO;
					datosUsuario.setCargoAsignado(selectedCargo);
				}
			}
		} else {
			llenarEdicion();
		}

		this.binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	/**
	 * Load services.
	 */
	private void loadServices() {
		this.ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
		this.datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
		this.usuarioHelper = (IUsuarioHelper) SpringUtil.getBean("usuarioHelper");
		this.cargoService = (ICargoService) SpringUtil.getBean("cargoService");
		this.reparticionService = (IReparticionEDTService) SpringUtil.getBean("reparticionEDTService");
		this.usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
		this.sectorUsuarioService = (ISectorUsuarioService) SpringUtil.getBean("sectorUsuarioService");
		this.terminosYCondiciones = (String) SpringUtil.getBean("tyccontentBean");
		this.iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
		this.sectorService = (ISectorService) SpringUtil.getBean("sectorService");
		this.usuarioBuzonGrupalService = (IUsuarioAplicacionService) SpringUtil.getBean("usuarioBuzonGrupalService");
		this.usuarioMisSistemasService = (IUsuarioAplicacionService) SpringUtil.getBean("usuarioMisSistemasService");
		this.usuarioMisTareasService = (IUsuarioAplicacionService) SpringUtil.getBean("usuarioMisTareasService");
		this.usuarioMisSupervisadosService = (IUsuarioAplicacionService) SpringUtil
				.getBean("usuarioMisSupervisadosService");
		this.aplicacionesService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
	}

	/**
	 * Llenar edicion.
	 */
	private void llenarEdicion() {
		esEdicion = true;
		chbx_tyc.setDisabled(datosUsuario.getAceptacionTyC());

		// validacion para el reseteo del combo de SectorDTO Mesa

		// uso el sector mesa que esta guardado en la base
		selectedSector = sectorService.getSectorbyId(datosUsuario.getIdSectorInterno());
		if (selectedSector != null) {
			cbx_sectorMesa.setValue(selectedSector.getCodigo() + " - " + selectedSector.getNombre());
		}
		cbx_sectorMesa.setDisabled(true);
		selectedCargo = datosUsuario.getCargoAsignado();
		if (datosUsuario.getCargoAsignado() != null) {
			cbx_cargo.setValue(datosUsuario.getCargoAsignado().getCargoNombre());
		}
		cargarBandBoxs();
		chbx_tyc.setDisabled(true);
		cbx_cargo.setDisabled(true);
	}

//	private void llenarYdisablearCampos() throws NamingException {
//		final List<Attributes> att = ldapAccesor.buscarCuit(getCurrentUser().getCuit());
//		Attributes at = null;
//		if (!att.isEmpty()) {
//			at = att.get(0);
//		}
//		String cuit = getCurrentUser().getCuit();
//		String mail = getCurrentUser().getEmail();
//		if (at != null && at.get("mail") != null) {
//			mail = (String) at.get("mail").get();
//			txbx_email.setValue(mail);
//			datosUsuario.setMail(mail);
//		}
//
//		if (at != null && at.get("sAMAccountName") != null) {
//			cuit = (String) at.get("sAMAccountName").get();
//		}
//		labelCuit.setVisible(false);
//		datosUsuario.setMail(mail);
//		datosUsuario.setNumeroCuit(cuit);
//		txbx_cuit.setValue(cuit);
//		esEdicion = true;
//		btn_eliminar.setVisible(false);
//		cbx_cargo.setDisabled(true);
//	}

	/**
 * Cargar band boxs.
 */
private void cargarBandBoxs() {
		bandBoxSuperiores.setText(datosUsuario.getUserSuperior());
	}

	/**
	 * Inicializar listas de usuarios.
	 *
	 * @throws SecurityNegocioException the security negocio exception
	 */
	private void inicializarListasDeUsuarios() throws SecurityNegocioException {
		listaTodosLosUsuarios = usuarioHelper.obtenerTodosUsuarios();
		listaTodosLosUsuariosSupervisados = usuarioService.obtenerUsuariosDeSolrSupervisados(getUsername());
		listaSuperioresSeleccionados = new ArrayList<>();
	}

	/**
	 * Inicializar lista aplicaciones.
	 */
	private void inicializarListaAplicaciones() {
		if (this.listaAplicaciones == null) {
      try {
        this.listaAplicaciones = this.aplicacionesService.getTodasLasAplicaciones();
      } catch (SystemException se) {
        logger.error(se.getMessage(), se);
        Messagebox.show(se.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(Labels.getLabel("eu.configuracionAplicaiones.msgBox"),
            Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
            Messagebox.ERROR);
      }
    }
	}
	
	/**
	 * On click$btn guardar.
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void onClick$btn_guardar() throws Exception {
		validarCampos();
		final Boolean actualizaPassword = !txbx_nuevoPsswd.getValue().isEmpty();
		if (actualizaPassword) {
			cambiarPassword(datosUsuario.getUsuario(), txbx_nuevoPsswd.getValue(), txbx_passwordActual.getValue());
		}
		// validacion para el cambio de mail
		actualizarLdap();
		if (esEdicion) {
			Boolean reload = false;
			if (datosUsuario.getCambiarMesa() != null && datosUsuario.getCambiarMesa()) {
				reload = true;
			}
			datosUsuarioService.modificarDatosUsuario(datosUsuario);
			usuarioService.indexarUsuario(datosUsuario.getUsuario());
			Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.edicionExitosa"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
					Messagebox.INFORMATION, actualizaPassword ? new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onOK".equals(evt.getName())) {
								Utilitarios.doLogout();
							}
						}
					} : null);
			txbx_passwordActual.setValue(StringUtils.EMPTY);

//			if (reload) {
				reloadPagina();
//			}
		} else {// es el primer login de la persona
			if (!actualizaPassword) {
				throw new WrongValueException(txbx_nuevoPsswd,
						Labels.getLabel("eu.gesDatPerssUsuComp.WrongValueException.camPass"));
			}
			// Primer guardado, configurar automaticamente todas las aplicaciones para el usuario
			if (CollectionUtils.isNotEmpty(listaAplicaciones)) {
				this.guardarAplicacionesUsuario();
			}
			datosUsuarioService.modificarDatosUsuario(datosUsuario);
			usuarioService.indexarUsuario(datosUsuario.getUsuario());
			Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.altaExitosa"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
					Messagebox.INFORMATION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onOK".equals(evt.getName())) {
								Utilitarios.doLogout();
							}
						}
					});
		}
	}

	/**
	 * Guardar aplicaciones usuario.
	 */
	private void guardarAplicacionesUsuario() {
		for (AplicacionDTO aplicacion : listaAplicaciones) {
			// ** Se crea un objeto generico, el cual se insertara o se
			// borrara segun corresponda en cada tabla.
			UsuarioGenericoDTO usuarioAplicacion = new UsuarioGenericoDTO();
			usuarioAplicacion.setAplicacionID(aplicacion.getIdAplicacion());
			usuarioAplicacion.setUsuario(this.getUsername());
			if (aplicacion.isVisibleMisTareas()) {
				this.usuarioMisTareasService.insertarUsuarioAplicacion(usuarioAplicacion);
			}
			if (aplicacion.isVisibleMisSistemas()) {
				this.usuarioMisSistemasService.insertarUsuarioAplicacion(usuarioAplicacion);
			}
			if (aplicacion.isVisibleMisSupervisados()) {
				this.usuarioMisSupervisadosService.insertarUsuarioAplicacion(usuarioAplicacion);
			}
			if (aplicacion.isVisibleBuzonGrupal()) {
				this.usuarioBuzonGrupalService.insertarUsuarioAplicacion(usuarioAplicacion);
			}
		}
	}

	private void actualizarLdap() throws NegocioException {

		try {
			final UsuarioBaseDTO usuarioSade = iLdapService.obtenerUsuarioPorUid(datosUsuario.getUsuario());
			if (!usuarioSade.getMail().equals(datosUsuario.getMail())) {
				usuarioSade.setMail(datosUsuario.getMail());
				iLdapService.modificarUsuario(usuarioSade);
			}

		} catch (final NegocioException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	public void onClick$btn_eliminar() throws InterruptedException, SecurityNegocioException {

		Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.avisoEliminar"),
				Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event evt)
							throws InterruptedException, SecurityNegocioException, NegocioException {
						if ("onOK".equals(evt.getName())) {
							final Usuario u = usuarioService.obtenerUsuario(datosUsuario.getUsuario());
							final ReparticionDTO reparticion = reparticionService
									.getReparticionByCodigo(u.getCodigoReparticionOriginal());
							final SectorDTO sector = sectorService
									.buscarSectorPorRepaYSector(u.getCodigoSectorInternoOriginal(), reparticion);
							if (sector != null) {
								final List<String> usuariosAsignadores = usuarioHelper
										.obtenerUsuariosAsignadoresPorSector(sector.getId());
								final Boolean tieneAsignadores = !CollectionUtils.isEmpty(usuariosAsignadores)
										? Boolean.TRUE : Boolean.FALSE;
								Boolean esAsignador = Boolean.FALSE;

								if (tieneAsignadores) {
									for (final String usuario : usuariosAsignadores) {
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
											"Error," + Labels
													.getLabel("eu.gesDatPerssUsuComp.msgbox.usuarioAsignadoSector"),
											Labels.getLabel("eu.adminSade.usuario.generales.informacion"),
											Messagebox.OK, Messagebox.ERROR);
									return;
								} else {

									if (reparticion != null) {
										if (!usuarioHelper.tieneUsuarioAsignador(sector.getId())) {
											Messagebox.show(
													"Error," + Labels.getLabel(
															"eu.gesDatPerssUsuComp.msgbox.sectorUsuarioAsignador"),
													Labels.getLabel("eu.adminSade.usuario.generales.informacion"),
													Messagebox.OK, Messagebox.ERROR);
											return;
										}
									}
								}
							}
							datosUsuarioService.eliminarDatosUsuario(datosUsuario);
							sectorUsuarioService.desvincularSectorUsuario(datosUsuario.getUsuario());
							Messagebox.show(Labels.getLabel("eu.datosPersonales.datosUsuario.eliminacionExitosa"),
									Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
									Messagebox.INFORMATION, new EventListener() {
										@Override
										public void onEvent(final Event evt) throws InterruptedException {
											if ("onOK".equals(evt.getName())) {
												Utilitarios.doLogout();
											}
										}
									});
						}
					}
				});

	}

	private void cambiarPassword(final String username, final String passwordNuevo, final String passwordActual)
			throws InterruptedException {
		try {
			if (!passwordNuevo.equalsIgnoreCase(passwordActual)) {
				usuarioService.cambiarPasswordUsuario(username, passwordNuevo);
			} else {
				throw new WrongValueException(this.txbx_nuevoPsswd,
						Labels.getLabel("eu.datosPersonales.datosUsuario.password.similar"));
			}
		} catch (final SecurityNegocioException e) {
			logger.error("Error al modificar el password de Usuario " + e.getMessage(), e);
			Messagebox.show(Labels.getLabel("eu.gesDatPerssUsuComp.msgbox.errorModPass"),
					Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onSelect$cbx_sectorMesa() {
		if (selectedSector != null) {
			datosUsuario.setIdSectorInterno(selectedSector.getId());
			datosUsuario.setCodigoSectorInterno(selectedSector.getCodigo());
		}
	}

	public void onChange$cbx_sectorMesa() {
		if (selectedSector != null) {
			datosUsuario.setIdSectorInterno(selectedSector.getId());
			datosUsuario.setCodigoSectorInterno(selectedSector.getCodigo());
		}
	}

	public void onSelect$cbx_cargo() {
		datosUsuario.setCargoAsignado(selectedCargo);
	}

	public void onChange$cbx_cargo() {
		datosUsuario.setCargoAsignado(selectedCargo);
	}

	public void onSelect$superioresListbox() {
		bandBoxSuperiores.setText(superiorSeleccionado.getUsername());
		datosUsuario.setUserSuperior(superiorSeleccionado.getUsername());
		bandBoxSuperiores.close();
	}


	public void onChanging$bandBoxSuperiores(final InputEvent e) {
		this.cargarUsuarios(e, this.listaTodosLosUsuarios, superioresListbox, listaSuperioresSeleccionados,
				totalUsuarios);
	}


	// TODO: en un futuro sprint analizar la posibilidad de un refactor de los
	// componentes de busqueda usuarios, para llevar
	// los 3 componentes a uno generico que sea instanciado por periodo licencia
	// y datos usuarios

	private void cargarUsuarios(final InputEvent e, final List<UsuarioReducido> listaTotalUsuarios,
			final Listbox listbox, final List<UsuarioReducido> listaReducida, final Listfooter totalUsuarios) {
		String matchingText = depurarString(e.getValue());
		if (matchingText.trim().length() > 2 && !"*".equals(matchingText.trim())) {
			listaReducida.clear();
			if (listaTotalUsuarios != null) {
				matchingText = matchingText.toUpperCase();
				for (final UsuarioReducido usuarioReducido : listaTotalUsuarios) {
					if (usuarioReducido != null && usuarioReducido.getUsername() != null) {
						String nombreUsuarioCompleto = depurarString(usuarioReducido.toString());
						if (nombreUsuarioCompleto.toUpperCase().contains(matchingText)) {
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
		totalUsuarios.setLabel(
				Labels.getLabel("eu.datosSectorComposer.setLabel.totalUsuarios") + ": " + listaReducida.size());
		this.binder.loadComponent(listbox);

	}
	
	private String depurarString(String matchingText) {
		matchingText = Normalizer.normalize(matchingText, Normalizer.Form.NFKD)
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return matchingText;
	}

	/**
	 * Validar ingreso password.
	 *
	 * @return true, if successful
	 * @throws SecurityNegocioException the security negocio exception
	 */
	private boolean validarIngresoPassword() throws SecurityNegocioException {
		if (txbx_passwordActual.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.txbx_passwordActual,
					Labels.getLabel("eu.gesDatPerssUsuComp.WrongValueException.ingresarPass"));
		}

		if (!"SADE".equalsIgnoreCase(ldapEntorno)) {
			if (!usuarioService.validarPasswordUsuario(datosUsuario.getUsuario(), txbx_passwordActual.getValue())) {
				throw new WrongValueException(this.txbx_passwordActual,
						Labels.getLabel("eu.datosPersonales.datosUsuario.password.noMatch"));
			}
		} else {
//			if (!ldapAccesor.authenticate(datosUsuario.getNumeroCuit(), txbx_passwordActual.getValue())) {
//				throw new WrongValueException(this.txbx_passwordActual,
//						Labels.getLabel("eu.datosPersonales.datosUsuario.password.noMatch"));
//			}
		}
		return true;
	}

	private void validarCampos() throws InterruptedException, SecurityNegocioException {
		if (txbx_email.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.txbx_email,
					Labels.getLabel("eu.datosPersonales.datosUsuario.campoObligatorio"));
		}
		
		if (txbx_cuit.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.txbx_cuit,
					Labels.getLabel("eu.datosPersonales.datosUsuario.campoObligatorio"));
		}

		if (datosUsuario.getUserSuperior() == null || datosUsuario.getUserSuperior().trim().isEmpty()
				|| bandBoxSuperiores.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.bandBoxSuperiores,
					Labels.getLabel("eu.datosPersonales.datosUsuario.tab.validacion.superior"));
		}

		if (!datosUsuario.getUserSuperior().trim().equals(bandBoxSuperiores.getValue().trim())) {
			throw new WrongValueException(this.bandBoxSuperiores,
					Labels.getLabel("eu.datosPersonales.datosUsuario.tab.validacion.superior"));
		}


		if (cbx_sectorMesa.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.cbx_sectorMesa,
					Labels.getLabel("eu.datosPersonales.datosUsuario.campoObligatorio"));
		}

		if (cbx_cargo.getValue().trim().isEmpty()) {
			throw new WrongValueException(this.cbx_cargo,
					Labels.getLabel("eu.datosPersonales.datosUsuario.campoObligatorio"));
		}

		// SI Existe texto en los campos de nuevo password y confirmacion nuevo
		// password, deben ser iguales.
		if (ldapEntorno != null && !"SADE".equals(ldapEntorno)) {
			boolean newPassConfirmEmpty = txbx_nuevoPsswd.getValue().isEmpty() && !txbx_nuevoPsswdConfirmacion.getValue().isEmpty();
			boolean newPassEmptyConfirm = txbx_nuevoPsswdConfirmacion.getValue().isEmpty() && !txbx_nuevoPsswd.getValue().isEmpty();
			boolean newPassNoEquals = null != txbx_nuevoPsswd.getValue() && null != txbx_nuevoPsswdConfirmacion.getValue()
					&& !txbx_nuevoPsswdConfirmacion.getValue().equals(txbx_nuevoPsswd.getValue());
			
			if (newPassConfirmEmpty || newPassEmptyConfirm || newPassNoEquals) {
				throw new WrongValueException(this.txbx_nuevoPsswdConfirmacion,
						Labels.getLabel("eu.datosPersonales.datosUsuario.password.nuevo.noMatch"));
			}
		}
		validarIngresoPassword();
    Utilitarios.validarMail(txbx_email);
    
		if (!chbx_tyc.isChecked()) {
			throw new WrongValueException(this.chbx_tyc,
					Labels.getLabel("eu.datosPersonales.datosUsuario.tab.validacion.tyc.obligatorio"));
		}

	}

	public DatosUsuarioDTO getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(final DatosUsuarioDTO datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public List<CargoDTO> getCargosDisponibles() {
		return cargosDisponibles;
	}

	public void setCargosDisponibles(final List<CargoDTO> cargosDisponibles) {
		this.cargosDisponibles = cargosDisponibles;
	}

	public CargoDTO getSelectedCargo() {
		return selectedCargo;
	}

	public void setSelectedCargo(final CargoDTO selectedCargo) {
		this.selectedCargo = selectedCargo;
	}

	public List<SectorDTO> getSectoresMesaDisponibles() {
		return sectoresMesaDisponibles;
	}

	public void setSectoresMesaDisponibles(final List<SectorDTO> sectoresMesaDisponibles) {
		this.sectoresMesaDisponibles = sectoresMesaDisponibles;
	}

	public SectorDTO getSelectedSector() {
		return selectedSector;
	}

	public void setSelectedSector(final SectorDTO selectedSector) {
		this.selectedSector = selectedSector;
	}

	public List<UsuarioReducido> getListaSuperioresSeleccionados() {
		return listaSuperioresSeleccionados;
	}

	public void setListaSuperioresSeleccionados(final List<UsuarioReducido> listaSuperioresSeleccionados) {
		this.listaSuperioresSeleccionados = listaSuperioresSeleccionados;
	}

	public List<UsuarioReducido> getListaTodosLosUsuarios() {
		return listaTodosLosUsuarios;
	}

	public void setListaTodosLosUsuarios(final List<UsuarioReducido> listaTodosLosUsuarios) {
		this.listaTodosLosUsuarios = listaTodosLosUsuarios;
	}

	public UsuarioReducido getSuperiorSeleccionado() {
		return superiorSeleccionado;
	}

	public void setSuperiorSeleccionado(final UsuarioReducido superiorSeleccionado) {
		this.superiorSeleccionado = superiorSeleccionado;
	}


	public List<UsuarioReducido> getListaTodosLosUsuariosSupervisados() {
		return listaTodosLosUsuariosSupervisados;
	}

	public void setListaTodosLosUsuariosSupervisados(final List<UsuarioReducido> listaTodosLosUsuariosSupervisados) {
		this.listaTodosLosUsuariosSupervisados = listaTodosLosUsuariosSupervisados;
	}

	public Bandbox getBandBoxSuperiores() {
		return bandBoxSuperiores;
	}

	public void setBandBoxSuperiores(final Bandbox bandBoxSuperiores) {
		this.bandBoxSuperiores = bandBoxSuperiores;
	}


	public Listbox getSuperioresListbox() {
		return superioresListbox;
	}

	public void setSuperioresListbox(final Listbox superioresListbox) {
		this.superioresListbox = superioresListbox;
	}

	public Listfooter getTotalUsuarios() {
		return totalUsuarios;
	}

	public void setTotalUsuarios(final Listfooter totalUsuarios) {
		this.totalUsuarios = totalUsuarios;
	}

	public Textbox getTxbx_cuit() {
		return txbx_cuit;
	}

	public String getTerminosYCondiciones() {
		return terminosYCondiciones;
	}

	public void setTxbx_cuit(final Textbox txbx_cuit) {
		this.txbx_cuit = txbx_cuit;
	}

	public void setTerminosYCondiciones(final String terminosYCondiciones) {
		this.terminosYCondiciones = terminosYCondiciones;
	}
}
