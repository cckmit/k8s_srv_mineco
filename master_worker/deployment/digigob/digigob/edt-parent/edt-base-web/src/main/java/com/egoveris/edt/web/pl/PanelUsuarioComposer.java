package com.egoveris.edt.web.pl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.AccesoDatosException;
import com.egoveris.edt.base.service.novedad.INovedadHelper;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.IReparticionSeleccionadaService;
import com.egoveris.sharedsecurity.base.service.IUsuarioReparticionHabilitadaService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class PanelUsuarioComposer extends BaseComposer {
	
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 998699609160253400L;

  private Tabbox euTabs;
  private Session csession;
  private Tab abmTab;
  private Tab escritorioTab;
  @Autowired
  private Tab tabSindicatura;
  private Tabpanel tp_perfilUsuario;
  private Tabpanel tp_escritorio;
  private Tab proyectLiderTab;

  private String ldapEntorno;

  private ICargoService cargoService;
  private IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService;
  private IReparticionSeleccionadaService reparticionSeleccionadaService;
  private IUsuarioAplicacionService usuarioMisSistemasService;
  private INovedadHelper novedadHelper;

  @SuppressWarnings("unchecked")
  @Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
		reparticionSeleccionadaService = (IReparticionSeleccionadaService) SpringUtil
				.getBean("reparticionSeleccionaService");

		this.usuarioMisSistemasService = (IUsuarioAplicacionService) SpringUtil.getBean("usuarioMisSistemasService");
		this.novedadHelper = (INovedadHelper) SpringUtil.getBean("novedadHelper");
		this.cargoService = (ICargoService) SpringUtil.getBean("cargoService");
		this.usuarioReparticionHabilitadaService = (IUsuarioReparticionHabilitadaService) SpringUtil
				.getBean("usuarioReparticionHabilitadaService");
		this.reparticionSeleccionadaService = (IReparticionSeleccionadaService) SpringUtil
				.getBean("reparticionSeleccionaService");
    
		csession = Executions.getCurrent().getDesktop().getSession();
		List<Integer> listaIdsAplicacionesMisTareas = (List<Integer>) csession
				.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_TAREAS);
		List<Integer> listaIdsAplicacionesMisSistemas = (List<Integer>) csession
				.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SISTEMAS);
		List<Integer> listaIdsAplicacionesTareasMisSupervisado = (List<Integer>) csession
				.getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS);

		// Valido que existan sus datos personales, Si no, lo primero que hara
		// sera ingresarlos, y no podra operar nada mas
		boolean existeDatosUsuario = (boolean) Executions.getCurrent()
				.getAttribute(ConstantesSesion.KEY_EXISTE_DATOS_USUARIO);

		if (BooleanUtils.isFalse(existeDatosUsuario)) {
			deshabilitarUsuario(null);
		} else {

			// Se muestra un popUp con novedades para todos los usuarios, en
			// caso de que haya
			if (!usuarioMisSistemasService.buscarAplicacionesPorUsuario(getUsername()).isEmpty()) {
				if (!novedadHelper.obtenerTodas().isEmpty()
						&& csession.getAttribute(ConstantesSesion.KEY_MOSTRAR_NOVEDADES) == null) {
					Window novedadWin = (Window) Executions.createComponents("/inicioNovedades.zul", this.tp_escritorio, null);
					novedadWin.doModal();
				}
			}
			
			// Se muestra un popUp para la selección de la reparticion que va a
			// utilizar el usuario en esta sesion, si solo hay una unica repartición se va a cargar esta
			if (getReparticionUsadaEnSesion() == null) {
				if (reparticionesHabilitadas().size() > 1) {
					Map<String, Object> params = new HashMap<>(1);
					params.put("panelComposer", this);
					Window repaWin = (Window) Executions.createComponents("~./db_layout/common/seleccionDeCargo.zul",
							this.tp_escritorio, params);
					repaWin.doModal();
				} else {
					UsuarioReparticionHabilitadaDTO repActual = resaltarReparticionPropia();
					Executions.getCurrent().getDesktop().getSession().setAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN,
							repActual.getReparticion());
				}
			}
			
			// Valido que tenga datos configurados en alguna de las vistas
			// Sino no tiene vistas configuradas se supone que es la primera vez
			// que ingresa al sistema
			// entonces se le pone un cartel indicando los pasos a seguir para
			// operar con EU
			String userName = (String) csession.getAttribute(ConstantesSesion.SESSION_USERNAME);
			if (validarCargoUsuario(userName)) {
				if (CollectionUtils.isEmpty(listaIdsAplicacionesMisTareas)
						&& CollectionUtils.isEmpty(listaIdsAplicacionesMisSistemas)
						&& CollectionUtils.isEmpty(listaIdsAplicacionesTareasMisSupervisado)) {
					Messagebox.show(
							Labels.getLabel("eu.escritorioUnico.disclaimer.primerIngresoSistema", new String[] { ldapEntorno }),
							Labels.getLabel("eu.escritorioUnico.disclaimer.bienvenida"), Messagebox.OK, Messagebox.INFORMATION);
					//abmTab.setSelected(true);
				}
			}
		}
	}

	/**
	 * Reparticiones habilitadas.
	 *
	 * @return the list
	 */
	public List<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas() {
		return usuarioReparticionHabilitadaService
				.obtenerReparticionesHabilitadasByUsername(Utilitarios.obtenerUsuarioActual().getUsername());
	}

	/**
	 * Resaltar reparticion propia.
	 *
	 * @return the usuario reparticion habilitada DTO
	 */
	private UsuarioReparticionHabilitadaDTO resaltarReparticionPropia() {
		return usuarioReparticionHabilitadaService
				.obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(Utilitarios.obtenerUsuarioActual().getUsername());
	}
  
  /**
   * Deshabilitar usuario.
   *
   * @param mensaje the mensaje
   */
  private void deshabilitarUsuario(String mensaje) {
    escritorioTab.setDisabled(true);
    abmTab.setDisabled(true);
    euTabs.setSelectedPanel(tp_escritorio);

    if (mensaje != null) {
      mostrarWarning(mensaje);
    }
  }

  private Boolean validarCargoUsuario(String userName) throws AccesoDatosException {
    CargoDTO cargoUsuario = cargoService.getCargoByUsuario(userName);
    if (null == cargoUsuario || 0 != cargoUsuario.getId()) {
      return true;
    } else {
      if (0 == cargoUsuario.getId()) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ConstantesSesion.SESSION_USERNAME, userName);
        Window cargoWindow = (Window) Executions
            .createComponents("/actualizacionCargoUsuario.zul", this.self.getParent(), params);
        cargoWindow.doModal();

        return false;
      }
    }
    return true;
  }

  public void onSelect$euTabs() {
    if (euTabs.getSelectedPanel().getFirstChild() != null) {
      /*
      Window currentWindow = (Window) euTabs.getSelectedPanel().getFirstChild().getFirstChild();
      Event event = new Event(Events.ON_NOTIFY, currentWindow);
      Events.sendEvent(event);
      */
    }
  }

  public Session getCsession() {
    return csession;
  }

  public void setCsession(Session csession) {
    this.csession = csession;
  }

  public Tab getAbmTab() {
    return abmTab;
  }

  public void setAbmTab(Tab abmTab) {
    this.abmTab = abmTab;
  }

  public String getLdapEntorno() {
    return ldapEntorno;
  }

  public void setLdapEntorno(String ldapEntorno) {
    this.ldapEntorno = ldapEntorno;
  }

  public Tab getProyectLiderTab() {
    return proyectLiderTab;
  }

  public void setProyectLiderTab(Tab proyectLiderTab) {
    this.proyectLiderTab = proyectLiderTab;
  }
}
