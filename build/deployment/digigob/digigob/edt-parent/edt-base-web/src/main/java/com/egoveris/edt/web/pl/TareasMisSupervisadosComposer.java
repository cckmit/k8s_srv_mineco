/**
 *
 */
package com.egoveris.edt.web.pl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.exception.SystemException;
import com.egoveris.edt.base.model.SupervisadosBean;
import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.vista.IVistaMisSupervisadosService;
import com.egoveris.edt.base.util.URLEncryptor;
import com.egoveris.edt.base.util.comparators.MisSupervisadosComparator;
import com.egoveris.edt.web.pl.Actualiza.ActualizaEvent;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

/**
 * @author pfolgar
 *
 */
public class TareasMisSupervisadosComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -1378825464355527708L;

	private static Logger logger = LoggerFactory.getLogger(TareasMisSupervisadosComposer.class);

	private Window tareasMisSupervisadosDesktop;
	private Listbox tareasMisSupervisadosId;
	private List<SupervisadosBean> listaTareasMisSupervisadosPorSistema;
	private String userName;
	private AnnotateDataBinder tareasMisSupervisadosDesktopBinder;
	private List<Integer> listaIdsAplicacionesTareasMisSupervisado;
	private Label labelDetalleTareasMisSupervisadosSinDetalle;
	private Column columnaDetalleTareasMisSupervisados;
	private Grid grillaDetalleTareasDetalleMisSupervisados;
	private Listbox detalleTareasMisSupervisados;
	private TareasPorSistemaBean tareaSistemaUsuarioSel;
	private List<TareasPorSistemaBean> tareasSistemaUsuario;
	private Label labelUsuarioSupervisado;
	private Label labelMisTareasSinAplicacionId;
	private Label labelTareasMisSupervisadoSinSupervisadosId;
	private UsuarioFrecuenciaDTO usuarioFrecuencia;
	private Session csession;
	private Listheader porcentajeFrecuenciaMenor;
	private Listheader porcentajeFrecuenciaMayor;
	private Listheader frec1;
	private Listheader frec2;
	private Listheader frec3;
	private Listheader frec4;
	private Label timeoutMisSupervisados;
	private Image imgIrSupervisados;
	private Label labelAnchor;

	private IAplicacionService aplicacionesService;
	private IVistaMisSupervisadosService vistaMisSupervisadosService;
	private ILdapAccessor ldapAccessor;

	public Label getLabelMisTareasSinAplicacionId() {
		return labelMisTareasSinAplicacionId;
	}

	public void setLabelMisTareasSinAplicacionId(final Label labelMisTareasSinAplicacionId) {
		this.labelMisTareasSinAplicacionId = labelMisTareasSinAplicacionId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(final Component c) throws Exception {

		super.doAfterCompose(c);

		this.aplicacionesService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
		this.vistaMisSupervisadosService = (IVistaMisSupervisadosService) SpringUtil
				.getBean("vistaMisSupervisadosService");
		this.ldapAccessor = (ILdapAccessor) SpringUtil.getBean("ldapAccessor");

		csession = Executions.getCurrent().getDesktop().getSession();
		this.userName = (String) csession.getAttribute(ConstantesSesion.SESSION_USERNAME);
		c.addEventListener(Events.ON_NOTIFY, new TareasMisSupervisadosOnNotifyWindowListener(this));
		if (listaIdsAplicacionesTareasMisSupervisado == null) {
			listaIdsAplicacionesTareasMisSupervisado = (List<Integer>) csession
					.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS);
		}

		timeoutMisSupervisados.setValue((String) csession.getAttribute(ConstantesSesion.TIEMPO_REFRESCO));

		this.loadHeader();
		this.loadListBox(c);
		tareasMisSupervisadosDesktopBinder = new AnnotateDataBinder(c);
		tareasMisSupervisadosDesktopBinder.loadAll();
	}

	@SuppressWarnings("unchecked")
	public List<SupervisadosBean> getListaTareasMisSupervisadosPorSistema() {
		logger.debug("{} - Se ingresa al método getListaTareasMisSupervisadosPorSistema()", userName);
		final Date start = new Date();
		if (this.listaTareasMisSupervisadosPorSistema == null) {
			this.listaIdsAplicacionesTareasMisSupervisado = (List<Integer>) csession
					.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS);
			final List<String> listaUsuariosSupervisados = (List<String>) csession
					.getAttribute(ConstantesSesion.SESSION_LISTA_SUPERVISADOS);
			this.usuarioFrecuencia = (UsuarioFrecuenciaDTO) csession
					.getAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA);
			try {

				listaTareasMisSupervisadosPorSistema = this.vistaMisSupervisadosService.obtenerVistaMisSistemas(
						listaIdsAplicacionesTareasMisSupervisado, userName, usuarioFrecuencia,
						listaUsuariosSupervisados);
			} catch (final NegocioException ne) {
				logger.error(ne.getMessage(), ne);
				this.labelTareasMisSupervisadoSinSupervisadosId.setVisible(true);
			} catch (final SystemException se) {
				logger.error(se.getMessage(), se);
				Messagebox.show(se.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			} catch (final Exception e) {
				logger.error(e.getMessage(), e);
				Messagebox.show(Labels.getLabel("eu.verificarPass.label.ingresePass"),
						Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK, Messagebox.ERROR);
			}
		}
		final Date end = new Date();
		final Long dif = end.getTime() - start.getTime();
		logger.debug("{} - Finaliza el metodo getMisSistemas() {} ms", userName, dif.toString());
		return listaTareasMisSupervisadosPorSistema;
	}

	public void refreshInbox() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.listaTareasMisSupervisadosPorSistema = null;
		this.listaTareasMisSupervisadosPorSistema = this.getListaTareasMisSupervisadosPorSistema();
		this.loadListBox(this.self);
		// Cuando hago el refresco pongo el detalle de la tarea
		// del supervisado como esta inicialmente, es decir con la
		// leyenda de default
		this.labelDetalleTareasMisSupervisadosSinDetalle.setVisible(true);
		this.columnaDetalleTareasMisSupervisados.setVisible(false);
		this.tareasMisSupervisadosDesktopBinder.loadComponent(this.tareasMisSupervisadosId);
	}

	public void loadListBox(final Component comp)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.debug("Se ingresa al método loadListBox()");
		final Date start = new Date();

		this.listaTareasMisSupervisadosPorSistema = null;
		if (listaIdsAplicacionesTareasMisSupervisado != null && !listaIdsAplicacionesTareasMisSupervisado.isEmpty()) {

			final List<SupervisadosBean> listaSuper = this.getListaTareasMisSupervisadosPorSistema();
			@SuppressWarnings("rawtypes")
			final List list = this.tareasMisSupervisadosId.getItems();
			list.clear();

			if (CollectionUtils.isNotEmpty(listaSuper)) {

				// ************************ GRILLA DE DATOS
				// *****************************************************
				if (listaSuper != null && !listaSuper.isEmpty()) {
					Listitem listItem;
					Listcell listCell;

					Image imagen;

					// Para cada usuario supervisado creo una fila
					// (ListItem)
					for (final SupervisadosBean supervisadosBean : listaSuper) {

						final String avatar = this
								.getNombreCompuestoSupervisado(supervisadosBean.getNombreSupervisado());
						listItem = new Listitem(avatar);
						listItem.setTooltiptext(avatar);

						// Para cada usuario supervisado y por cada
						// aplicacion
						// que este tenga configurada
						// creo una celda
						for (final TareasPorSistemaBean tareasPorSistema : supervisadosBean
								.getListaTareasPorSistema()) {

							listCell = new Listcell(tareasPorSistema.getTareasPendientes() != null
									? tareasPorSistema.getTareasPendientes().toString() : StringUtils.EMPTY);
							listCell.setValue(tareasPorSistema);

							if (tareasPorSistema.getTareasPendientes() != null
									&& !tareasPorSistema.getTareasPendientes().equals(0)) {
								imagen = new Image("/imagenes/egovInspect.png");
								// imagen.setSrc("/imagenes/play.png");
								imagen.setTooltiptext(Labels.getLabel("eu.escritorioUnico.helpDetalle"));
								imagen.setAlign("center");
								// org.zkoss.zk.ui.sys.ComponentsCtrl
								// .applyForward(imagen,
								// "onClick=tareasMisSupervisadosDesktop$composer.onMostrarDetalleTareasMisSupervisados");
								imagen.addForward("onClick", comp, "onMostrarDetalleTareasMisSupervisados",
										tareasPorSistema);

								imagen.setAttribute("usuario", supervisadosBean.getNombreSupervisado());
								imagen.setHspace("5");
								imagen.setParent(listCell);
							}

							listCell.setParent(listItem);

						}
						// Al listbox tareasMisSupervisadosId le seteo el
						// listitem como hijo
						listItem.setParent(tareasMisSupervisadosId);
					}
				}
			} else {
				this.labelTareasMisSupervisadoSinSupervisadosId.setVisible(true);
				this.grillaDetalleTareasDetalleMisSupervisados.setVisible(false);
				this.tareasMisSupervisadosId.setVisible(false);
				this.labelDetalleTareasMisSupervisadosSinDetalle.setVisible(false);
			}

		} else {
			this.labelMisTareasSinAplicacionId.setVisible(true);
			this.grillaDetalleTareasDetalleMisSupervisados.setVisible(false);
			this.tareasMisSupervisadosId.setVisible(false);
			this.labelDetalleTareasMisSupervisadosSinDetalle.setVisible(false);

		}
		final Date end = new Date();
		final Long dif = end.getTime() - start.getTime();
		logger.debug("Finaliza el metodo loadListBox() " + dif.toString() + "ms");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadHeader() {
		if (listaIdsAplicacionesTareasMisSupervisado != null && !listaIdsAplicacionesTareasMisSupervisado.isEmpty()) {
			// Busco en memoria si cambio la configuracion de aplicaciones para
			// la
			// vista de mis supervisados
			listaIdsAplicacionesTareasMisSupervisado = (List<Integer>) csession
					.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS);
			// Busco las Aplicaciones que tiene configuradas el usuario
			// para la vista de Mis Supervisados.
			// La busqueda la hago entre las dos listas que estan en memoria
			// La lista listaIdsAplicacionesTareasMisSupervisado es con los Id's
			final List<AplicacionDTO> listaAplicaciones = aplicacionesService
					.buscarAplicaciones(this.listaIdsAplicacionesTareasMisSupervisado);

			// ************************ HEADER
			// *****************************************************
			final Listhead head = new Listhead();

			Listheader header;
			header = new Listheader(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.usuario"));
			header.setWidth("450px");
			header.setParent(head);

			// Para cada aplicacion de cual quiero saber cuantas tareas
			// pendientes tiene un supervisado le creo una columna(HEADER
			// del Listbox)
			int cont = 1;
			for (final AplicacionDTO aplicacion : listaAplicaciones) {

				header = new Listheader(aplicacion.getNombreAplicacion());

				final Comparator promMenorAsc = new MisSupervisadosComparator(true, 1, cont);
				final Comparator promMenorDsc = new MisSupervisadosComparator(false, 1, cont);

				header.setSortAscending(promMenorAsc);
				header.setSortDescending(promMenorDsc);

				header.setParent(head);

				cont++;

			}

			// Al listbox tareasMisSupervisadosId le seteo el head como hijo
			// en memoria
			head.setParent(tareasMisSupervisadosId);
		}
	}

	public void onMostrarDetalleTareasMisSupervisados(final Event ev) {
		logger.debug("Se ingresa al método onMostrarDetalleTareasMisSupervisados()");
		final Date start = new Date();
		if (this.tareasMisSupervisadosDesktop != null) {
			this.usuarioFrecuencia = (UsuarioFrecuenciaDTO) csession
					.getAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA);
			this.labelDetalleTareasMisSupervisadosSinDetalle.setVisible(false);
			this.columnaDetalleTareasMisSupervisados.setVisible(true);
			final TareasPorSistemaBean tareaSistemaUsuarioSeleccionado = (TareasPorSistemaBean) ev.getData();
			if (this.tareasSistemaUsuario == null) {
				this.tareasSistemaUsuario = new ArrayList<>();
			}
			this.tareasSistemaUsuario.clear();
			this.tareasSistemaUsuario.add(tareaSistemaUsuarioSeleccionado);
			final ForwardEvent me = (ForwardEvent) ev;
			final Image imagen = (Image) me.getOrigin().getTarget();
			final String userSup = imagen.getAttribute("usuario").toString();
			final String avatarUser = this.getNombreCompuestoSupervisado(userSup);
			this.labelUsuarioSupervisado.setValue(avatarUser);
			this.labelUsuarioSupervisado.setMultiline(true);
			this.labelUsuarioSupervisado.setTooltiptext(avatarUser);
			this.frec1.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.frec1",
					new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMenor()) }));
			this.frec2.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.frec2",
					new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMedia()) }));
			this.frec3.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.frec3",
					new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
			this.frec4.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.frec4",
					new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
			this.porcentajeFrecuenciaMenor
					.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.porcentajeFrecuenciaMenor",
							new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
			this.porcentajeFrecuenciaMayor
					.setLabel(Labels.getLabel("eu.escritorioUnico.tareasMisSupervisados.porcentajeFrecuenciaMayor",
							new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));

			this.imgIrSupervisados.setAttribute("usuario", userSup);

			this.tareasMisSupervisadosDesktopBinder.loadComponent(this.detalleTareasMisSupervisados);
			this.tareasMisSupervisadosDesktopBinder.loadComponent(this.labelUsuarioSupervisado);
			// Mecanismo para ir hacia el detalle del supervisado. El
			// labelAnchor sirve para marcar el lugar donde debe hacerse scroll
			// No se utiliza la grilla directamente porque habría un bug que la
			// hace renderizar incorrectamente
			Clients.scrollIntoView(labelAnchor);
		} else {
			Messagebox.show(Labels.getLabel("eu.misAppComposer.msgbox.noInicializarVista"),
					Labels.getLabel("eu.tareasSupComp.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
		}
		final Date end = new Date();
		final Long dif = end.getTime() - start.getTime();
		logger.debug("Finaliza el metodo onMostrarDetalleTareasMisSupervisados() " + dif.toString() + "ms");

		// Mecanismo para ir hacia el detalle del supervisado. Debe hacerse con
		// el echo porque de otra forma ZK renderiza incorrectamente la grilla
		// Events.echoEvent(Events.ON_USER,this.grillaDetalleTareasDetalleMisSupervisados,
		// null);
	}

	public void onIrMisSupervisadosInbox() {

		final String usuarioSupDestino = this.imgIrSupervisados.getAttribute("usuario").toString();
		logger.debug("onIrMisSupervisadosInbox - buzón destino de usuario: " + usuarioSupDestino);
		if (usuarioSupDestino != null && this.tareasMisSupervisadosDesktop != null) {

			String url = this.tareaSistemaUsuarioSel.getAplicacion().getUrlAplicacionInboxSupervisado();
			final String usernameEncrypted = URLEncryptor.getInstance().encrypt(usuarioSupDestino);
			url = url.concat("idUS=" + usernameEncrypted);
			Executions.getCurrent().sendRedirect(url);

		} else {
			Messagebox.show(Labels.getLabel("eu.misAppComposer.msgbox.noInicializarVista"),
					Labels.getLabel("eu.tareasSupComp.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	final class TareasMisSupervisadosOnNotifyWindowListener implements EventListener {
		private final TareasMisSupervisadosComposer composer;

		public TareasMisSupervisadosOnNotifyWindowListener(final TareasMisSupervisadosComposer comp) {
			this.composer = comp;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				if (event.getData() == null) {
					this.composer.refreshInbox();
				} else if (event.getData() instanceof ActualizaEvent) {
					final ActualizaEvent eventFirma = (ActualizaEvent) event.getData();
					if ("onActualizarTareasMisSupervisados".equals(eventFirma.getEventName())) {
						this.composer.refreshInbox();
					}
				}
			}
		}
	}

	private String getNombreCompuestoSupervisado(final String userName) {
		String result;
		final String supervisadoNombreApellido = this.ldapAccessor.getNombreYApellido(userName);

		// Busco el nombre y el apellido del supervisado para armar
		// el nombre + el userName
		if (StringUtils.isEmpty(supervisadoNombreApellido)) {
			result = StringUtils.join(new String[] { "(", userName, ")" });
		} else {
			result = StringUtils.join(new String[] { supervisadoNombreApellido, "    ", "(", userName, ")" });
		}

		return result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public void setListaTareasMisSupervisadosPorSistema(
			final List<SupervisadosBean> listaTareasMisSupervisadosPorSistema) {
		this.listaTareasMisSupervisadosPorSistema = listaTareasMisSupervisadosPorSistema;
	}

	public Window getTareasMisSupervisadosDesktop() {
		return tareasMisSupervisadosDesktop;
	}

	public void setTareasMisSupervisadosDesktop(final Window tareasMisSupervisadosDesktop) {
		this.tareasMisSupervisadosDesktop = tareasMisSupervisadosDesktop;
	}

	public Listbox getTareasMisSupervisadosId() {
		return tareasMisSupervisadosId;
	}

	public void setTareasMisSupervisadosId(final Listbox tareasMisSupervisadosId) {
		this.tareasMisSupervisadosId = tareasMisSupervisadosId;
	}

	public Label getLabelDetalleTareasMisSupervisadosSinDetalle() {
		return labelDetalleTareasMisSupervisadosSinDetalle;
	}

	public void setLabelDetalleTareasMisSupervisadosSinDetalle(
			final Label labelDetalleTareasMisSupervisadosSinDetalle) {
		this.labelDetalleTareasMisSupervisadosSinDetalle = labelDetalleTareasMisSupervisadosSinDetalle;
	}

	public Column getColumnaDetalleTareasMisSupervisados() {
		return columnaDetalleTareasMisSupervisados;
	}

	public void setColumnaDetalleTareasMisSupervisados(final Column columnaDetalleTareasMisSupervisados) {
		this.columnaDetalleTareasMisSupervisados = columnaDetalleTareasMisSupervisados;
	}

	public Grid getGrillaDetalleTareasDetalleMisSupervisados() {
		return grillaDetalleTareasDetalleMisSupervisados;
	}

	public void setGrillaDetalleTareasDetalleMisSupervisados(final Grid grillaDetalleTareasDetalleMisSupervisados) {
		this.grillaDetalleTareasDetalleMisSupervisados = grillaDetalleTareasDetalleMisSupervisados;
	}

	public Listbox getDetalleTareasMisSupervisados() {
		return detalleTareasMisSupervisados;
	}

	public void setDetalleTareasMisSupervisados(final Listbox detalleTareasMisSupervisados) {
		this.detalleTareasMisSupervisados = detalleTareasMisSupervisados;
	}

	public TareasPorSistemaBean getTareaSistemaUsuarioSel() {
		return tareaSistemaUsuarioSel;
	}

	public void setTareaSistemaUsuarioSel(final TareasPorSistemaBean tareaSistemaUsuarioSel) {
		this.tareaSistemaUsuarioSel = tareaSistemaUsuarioSel;
	}

	public List<TareasPorSistemaBean> getTareasSistemaUsuario() {
		return tareasSistemaUsuario;
	}

	public void setTareasSistemaUsuario(final List<TareasPorSistemaBean> tareasSistemaUsuario) {
		this.tareasSistemaUsuario = tareasSistemaUsuario;
	}

	public Label getLabelUsuarioSupervisado() {
		return labelUsuarioSupervisado;
	}

	public void setLabelUsuarioSupervisado(final Label labelUsuarioSupervisado) {
		this.labelUsuarioSupervisado = labelUsuarioSupervisado;
	}

	public Label getLabelTareasMisSupervisadoSinSupervisadosId() {
		return labelTareasMisSupervisadoSinSupervisadosId;
	}

	public void setLabelTareasMisSupervisadoSinSupervisadosId(final Label labelTareasMisSupervisadoSinSupervisadosId) {
		this.labelTareasMisSupervisadoSinSupervisadosId = labelTareasMisSupervisadoSinSupervisadosId;
	}

	public UsuarioFrecuenciaDTO getUsuarioFrecuencia() {
		return usuarioFrecuencia;
	}

	public void setUsuarioFrecuencia(final UsuarioFrecuenciaDTO usuarioFrecuencia) {
		this.usuarioFrecuencia = usuarioFrecuencia;
	}

	public Listheader getPorcentajeFrecuenciaMenor() {
		return porcentajeFrecuenciaMenor;
	}

	public void setPorcentajeFrecuenciaMenor(final Listheader porcentajeFrecuenciaMenor) {
		this.porcentajeFrecuenciaMenor = porcentajeFrecuenciaMenor;
	}

	public Listheader getPorcentajeFrecuenciaMayor() {
		return porcentajeFrecuenciaMayor;
	}

	public void setPorcentajeFrecuenciaMayor(final Listheader porcentajeFrecuenciaMayor) {
		this.porcentajeFrecuenciaMayor = porcentajeFrecuenciaMayor;
	}

	public Listheader getFrec1() {
		return frec1;
	}

	public void setFrec1(final Listheader frec1) {
		this.frec1 = frec1;
	}

	public Listheader getFrec2() {
		return frec2;
	}

	public void setFrec2(final Listheader frec2) {
		this.frec2 = frec2;
	}

	public Listheader getFrec3() {
		return frec3;
	}

	public void setFrec3(final Listheader frec3) {
		this.frec3 = frec3;
	}

	public Listheader getFrec4() {
		return frec4;
	}

	public void setFrec4(final Listheader frec4) {
		this.frec4 = frec4;
	}

	public AnnotateDataBinder getTareasMisSupervisadosDesktopBinder() {
		return tareasMisSupervisadosDesktopBinder;
	}

	public void setTareasMisSupervisadosDesktopBinder(final AnnotateDataBinder tareasMisSupervisadosDesktopBinder) {
		this.tareasMisSupervisadosDesktopBinder = tareasMisSupervisadosDesktopBinder;
	}

	public void setTimeoutMisSupervisados(final Label timeoutMisSupervisados) {
		this.timeoutMisSupervisados = timeoutMisSupervisados;
	}

	public Label getTimeoutMisSupervisados() {
		return timeoutMisSupervisados;
	}

	public Image getImgIrSupervisados() {
		return imgIrSupervisados;
	}

	public void setImgIrSupervisados(final Image imgIrSupervisados) {
		this.imgIrSupervisados = imgIrSupervisados;
	}

}
