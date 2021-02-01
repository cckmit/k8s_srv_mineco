/**
 * 
 */
package com.egoveris.edt.web.pl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ISupervisadosService;
import com.egoveris.edt.base.service.ITareasServiceFactory;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;
import com.egoveris.edt.base.service.usuario.IUsuarioFrecuenciasService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

/**
 * @author pfolgar
 * 
 */
public class InitPanelUsuario implements Initiator {

	
  private ILdapAccessor ldapAccessor;
  private Session csession = null;
  private static Logger logger = LoggerFactory.getLogger(InitPanelUsuario.class);
  private IUsuarioFrecuenciasService usuarioFrecuenciaServ;
  String user = StringUtils.EMPTY;
  private IUsuarioAplicacionService usuarioMisTareasServ;
  private IUsuarioAplicacionService usuarioMisSistemasService;
  private IUsuarioAplicacionService usuarioMisSupervisadosService;
  private IUsuarioAplicacionService usuarioBuzonGrupalService;
  private ISupervisadosService supervisadosService;
  private IAplicacionService aplicacionesService;
  private ITareasServiceFactory tareaServiceFactory;
  private IDatosUsuarioService datosUsuarioService;

  @Override
  public void doInit(Page page, Map args) throws Exception {

    csession = Executions.getCurrent().getDesktop().getSession();
    this.user = (String) csession.getAttribute(ConstantesSesion.SESSION_USERNAME);
    
    // Se le agrega validacion de si la sesion tiene la variable de frecuencia
    // se hace esto porque el dashoard previamente ya setea el usuario en la cabecera
    if (StringUtils.isEmpty(this.user) || !csession.hasAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA)) {
      this.doLogin();
      this.cargarListasEnMemoria();
      
      AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
      StringBuilder cadenaLogo = new StringBuilder();
      cadenaLogo.append("/imagenes/");
      cadenaLogo.append(appProperty.getString("url.archivo.logo"));
      cadenaLogo.append("/favicon.ico");
      page.setAttribute("favicon", cadenaLogo.toString());

      // Cargo configuracion de tiempo de refresh de las tareas en los paneles
      // desde un bean de Spring
      // TODO PERMITIR PARAMETRIZAR DESDE BASE E INTERFAZ DE ADMINISTRACION
      // TODO REFACTORIZAR ESTO, QUEDO MAL ESTRUCTURADO
      String tiempoRefrescoTareas = (String) SpringUtil.getBean("tiempoRefrescoTareas");
      logger.debug("El tiempo de refresco de las tareas es de " + tiempoRefrescoTareas + " ms");

      if (tiempoRefrescoTareas == null)
        tiempoRefrescoTareas = ConstantesSesion.TIEMPO_REFRESCO_DEFAULT;
      csession.setAttribute(ConstantesSesion.TIEMPO_REFRESCO, tiempoRefrescoTareas);
    }
    
    // Se deja fuera del if anterior, porque si se refresca la página 
    // no entra ahí.
    this.cargaSiUsuarioExiste();

  }

  private void doLogin() {
    this.ldapAccessor = (ILdapAccessor) SpringUtil.getBean("ldapAccessor");
    this.user = Executions.getCurrent().getRemoteUser();
    logger.debug("Se ingresa al sistema con el usuario {}", this.user);
    csession.setAttribute(ConstantesSesion.SESSION_USERNAME, this.user);
    csession.setAttribute(ConstantesSesion.SESSION_USER_NOMBRE_APELLIDO,
        this.ldapAccessor.getNombreYApellido(this.user));
  }

  /**
   * Este metodo carga en memoria las listas y frecuencias asociadas al usuario:
   * Frecuencias. Lista de aplicaciones para la vista de mis tareas. Lista de
   * aplicaciones para la vista de mis sistemas . Lista de aplicaciones para la
   * vista de mis subordinados. Lista de supervisados
   */
  private void cargarListasEnMemoria() {
    try {
      // Carga de beans de Spring
      this.usuarioFrecuenciaServ = (IUsuarioFrecuenciasService) SpringUtil
          .getBean("usuarioFrecuenciasServiceImpl");
      this.usuarioMisTareasServ = (IUsuarioAplicacionService) SpringUtil
          .getBean("usuarioMisTareasService");
      this.usuarioMisSistemasService = (IUsuarioAplicacionService) SpringUtil
          .getBean("usuarioMisSistemasService");
      this.usuarioMisSupervisadosService = (IUsuarioAplicacionService) SpringUtil
          .getBean("usuarioMisSupervisadosService");
      this.usuarioBuzonGrupalService = (IUsuarioAplicacionService) SpringUtil
          .getBean("usuarioBuzonGrupalService");
      this.supervisadosService = (ISupervisadosService) SpringUtil
          .getBean("supervisadosServiceImpl");
      this.aplicacionesService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
      this.tareaServiceFactory = (ITareasServiceFactory) SpringUtil.getBean("tareaServiceFactory");

      Date start = new Date();
      // ************************ FRECUENCIA**************************
      // Cargo en memoria la frecuencia configurada por el usuario
      logger.debug(
          "{} - Se realiza la carga en memoria de la frecuencia que configuró el usuario.",
          this.user);
      UsuarioFrecuenciaDTO usuarioFrecuencia = this.usuarioFrecuenciaServ
          .buscarFrecuenciasPorUsuario(this.user);

      // Si no cargo frecuencias le seteo valores por defecto
      if (usuarioFrecuencia == null) {
        usuarioFrecuencia = new UsuarioFrecuenciaDTO();
        usuarioFrecuencia.setFrecuenciaMayor(60);
        usuarioFrecuencia.setFrecuenciaMedia(30);
        usuarioFrecuencia.setFrecuenciaMenor(15);

      }
      csession.setAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA, usuarioFrecuencia);
      // ********************* LISTA VISTA MIS TAREAS***************************
      logger.debug(
          "{} - Se realiza la carga en memoria de las aplicaciones que configuro el usuario para la vista de Mis Tareas.",
          this.user);
      List<Integer> listaIdsAplicacionesMisTareas = this.usuarioMisTareasServ
          .buscarAplicacionesPorUsuario(user);
      csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_TAREAS,
          listaIdsAplicacionesMisTareas);

      // ********************* LISTA VISTA
      // BuzonGrupal***************************
      logger.debug(
          "{} - Se realiza la carga en memoria de las aplicaciones que configuro el usuario para la vista las tareas grupales Tareas.",
          this.user);

      // Esta lista a diferencia de las otras, almacena los ID de aplicacion +
      // los grupos a los que pertenece el usuario ***SEGUN CADA APLICACION***
      Map<Integer, List<String>> listaIdsAplicacionesBuzonGrupal = new HashMap<>();

      /*
       * Temporalmente removido por el costo de las consultas JBPM. 17/02/17
       */
      // int c = 0;
      // for(Integer i : tempListIdAplic) {
      // List<String> grupos =
      // this.tareaServiceFactory.get(tempListAplic.get(c).getNombreAplicacion()).buscarGruposUsuarioAplicacion(user);
      // listaIdsAplicacionesBuzonGrupal.put(i, grupos);
      // c++;
      // }

      csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS,
          listaIdsAplicacionesBuzonGrupal);

      // ********************* LISTA VISTA MIS
      // SISTEMAS***************************
      logger.debug(
          "{} - Se realiza la carga en memoria de las aplicaciones que configuro el usuario para la vista de Mis Sistemas.",
          this.user);
      List<Integer> listaIdsAplicacionesMisSistemas = this.usuarioMisSistemasService
          .buscarAplicacionesPorUsuario(user);
      csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SISTEMAS,
          listaIdsAplicacionesMisSistemas);

      // ********************* LISTA VISTA MIS
      // SUPERVISADOS***************************
      logger.debug(
          "{} - Se realiza la carga en memoria de las aplicaciones que configuro el usuario para la vista de Mis Supervisados.",
          this.user);
      List<Integer> listaIdsAplicacionesTareasMisSupervisado = this.usuarioMisSupervisadosService
          .buscarAplicacionesPorUsuario(user);
      csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS,
          listaIdsAplicacionesTareasMisSupervisado);

      // ********************* LISTA SUPERVISADOS***************************
      logger.debug("{} - Se realiza la carga en memoria de los subordinados del usuario.",
          this.user);
      List<String> usuariosSubordinados = this.supervisadosService
          .obtenerSupervisadosParaUsuario(user);
      csession.setAttribute(ConstantesSesion.SESSION_LISTA_SUPERVISADOS, usuariosSubordinados);

      Date end = new Date();
      Long dif = end.getTime() - start.getTime();
      logger.debug("{} - Finaliza la carga en memoria de los listas de aplicaciones. {} ms.",
          this.user, dif);
    } catch (Exception e) {
      logger.error("Error al cargar listas en memoria: " + e.getMessage(), e);
      Messagebox.show(Labels.getLabel("eu.initPanelUsuario.msgbox.errorBuscarDatos"),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }
  
	/**
	 * Carga si el usuario ya modificó sus datos iniciales y carga el dato como
	 * variable en la ejecución actual. Este dato se usa en: PentahoDashboardVM,
	 * PerfilUsuarioComposer y PanelUsuarioComposer
	 */
	private void cargaSiUsuarioExiste() {
		datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
		boolean existeDatosUsuario = datosUsuarioService.existeDatosUsuario(this.user);
		Executions.getCurrent().setAttribute(ConstantesSesion.KEY_EXISTE_DATO_USUARIO, existeDatosUsuario);
	}

  public void setTareaServiceFactory(ITareasServiceFactory tareaServiceFactory) {
    this.tareaServiceFactory = tareaServiceFactory;
  }

}
