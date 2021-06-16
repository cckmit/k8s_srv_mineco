package com.egoveris.edt.web.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ITareasServiceFactory;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;
import com.egoveris.edt.web.admin.pl.renderers.SeleccionReparticionItemRenderer;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionSeleccionadaDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.IReparticionSeleccionadaService;
import com.egoveris.sharedsecurity.base.service.IUsuarioReparticionHabilitadaService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.conf.service.CustomAuthoritiesPopulator;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class SeleccionDeReparticionComposer extends BaseComposer {

  /**
   * 
   */
  private static final long serialVersionUID = 7664205572787858194L;

  private Window win_seleccionDeReparticion;

  private IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService;

  @Autowired
  private IReparticionSeleccionadaService reparticionSeleccionadaService;

  private ReparticionSeleccionadaDTO reparticionSeleccionadaParaPersistir;

  private IAplicacionService aplicacionesService;

  private IUsuarioAplicacionService usuarioBuzonGrupalService;

  private IUsuarioService usuarioService;

  private ITareasServiceFactory tareaServiceFactory;

  private Session csession = null;

  private ILdapService iLdapService;

  private ICargoService cargoService;

  @Autowired
  protected AnnotateDataBinder binder;
  @Autowired
  private List<UsuarioReparticionHabilitadaDTO> listaRepartcionesHabilitadas;
  @Autowired
  private UsuarioReparticionHabilitadaDTO reparticionHabSeleccionada;
  @Autowired
  private Listbox listboxReparticiones;

  // private PanelUsuarioComposer panelComposer;

  @Autowired
  CustomAuthoritiesPopulator authoritiesPopulator;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    // panelComposer = (PanelUsuarioComposer)
    // super.arg.get("panelComposer");
    csession = Executions.getCurrent().getDesktop().getSession();

    usuarioReparticionHabilitadaService = (IUsuarioReparticionHabilitadaService) SpringUtil
        .getBean("usuarioReparticionHabilitadaService");
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    reparticionSeleccionadaService = (IReparticionSeleccionadaService) SpringUtil
        .getBean("reparticionSeleccionaService");
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");
    usuarioBuzonGrupalService = (IUsuarioAplicacionService) SpringUtil
        .getBean("usuarioBuzonGrupalService");
    aplicacionesService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
    tareaServiceFactory = (ITareasServiceFactory) SpringUtil.getBean("tareaServiceFactory");
    iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
    authoritiesPopulator = (CustomAuthoritiesPopulator) SpringUtil.getBean("authoritiesPopulator");
    this.listaRepartcionesHabilitadas = new ArrayList<>();
    this.listboxReparticiones.setItemRenderer(new SeleccionReparticionItemRenderer());
    this.binder = new AnnotateDataBinder(comp);
    this.reparticionSeleccionadaParaPersistir = new ReparticionSeleccionadaDTO();
    this.cargarListboxReparticiones();
    this.binder.loadAll();
  }

  public void cargarListboxReparticiones() {
    this.listaRepartcionesHabilitadas.addAll(usuarioReparticionHabilitadaService
        .obtenerReparticionesHabilitadasByUsername(this.getUsername()));
    resaltarReparticionPropia();
    this.binder.loadComponent(this.listboxReparticiones);
  }

  private void resaltarReparticionPropia() {
    this.reparticionHabSeleccionada = usuarioReparticionHabilitadaService
        .obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(getUsername());
  }

  public void onSeleccionarReparticion() throws InterruptedException, NegocioException {
    getSession().setAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN,
        reparticionHabSeleccionada.getReparticion());
    reparticionSeleccionadaParaPersistir
        .setReparticion(reparticionHabSeleccionada.getReparticion());
    reparticionSeleccionadaParaPersistir.setSector(reparticionHabSeleccionada.getSector());
    this.reparticionSeleccionadaParaPersistir.setUsuario(this.getUsername());
    Usuario user = Utilitarios.obtenerUsuarioActual();
    user.setCargo(reparticionHabSeleccionada.getCargo().getCargoNombre());
    reparticionSeleccionadaService
        .establecerReparticionSeleccionada(reparticionSeleccionadaParaPersistir, user);

//    Messagebox.show(Labels.getLabel("eu.adminSade.seleccionReparticion.seSeleccionoReparticion") + " "
//        + this.reparticionSeleccionadaParaPersistir.getReparticion().getCodigoReparticion(), Labels.getLabel("eu.general.information"), new Messagebox.Button[0], Messagebox.INFORMATION, null);

    int c;
    Map<Integer, List<String>> listaIdsAplicacionesBuzonGrupal = new HashMap<>();
 
    /*
     * JBPM sacado temporalmente por consumo excesivo de recursos.
     */
    // for (Integer i : tempListIdAplic) {
    // List<String> grupos =
    // this.tareaServiceFactory.get(tempListAplic.get(c).getNombreAplicacion())
    // .buscarGruposUsuarioAplicacion(user.getUsername());
    // listaIdsAplicacionesBuzonGrupal.put(i, grupos);
    // c++;
    // }

    csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS,
        listaIdsAplicacionesBuzonGrupal);

    try {
      UsuarioBaseDTO usuario = iLdapService.obtenerUsuarioPorUid(user.getUsername());
//      List<String> permisos = cargoService
//          .buscarPermisosPorCargo(reparticionHabSeleccionada.getCargo());
//      usuario.setPermisos(permisos);
      usuario.setUid(user.getUsername());
      iLdapService.modificarUsuario(usuario);
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
//      for (String string : permisos) {
//        updatedAuthorities
//            .add(new SimpleGrantedAuthority(authoritiesPopulator.getSimpleRole(string)));
//      }
      Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
          auth.getCredentials(), updatedAuthorities);
      SecurityContextHolder.getContext().setAuthentication(newAuth);

    } catch (Exception ex) {
      logger.error("Error al seleccionar el organismo al entrar: ", ex);
    }

    // panelComposer.setTabsVisibility();
    // actualizarLabelEnHeader();
//    win_seleccionDeReparticion.onClose();
    reloadPagina();
  }

  // private void actualizarLabelEnHeader(){
  // Component comp = (Component)Path.getComponent("/header");
  // Events.sendEvent(Events.ON_CHANGE, comp, null);
  // }

  public Listbox getListboxReparticiones() {
    return listboxReparticiones;
  }

  public void setListboxReparticiones(Listbox listboxReparticiones) {
    this.listboxReparticiones = listboxReparticiones;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public List<UsuarioReparticionHabilitadaDTO> getListaRepartcionesHabilitadas() {
    return listaRepartcionesHabilitadas;
  }

  public void setListaRepartcionesHabilitadas(
      List<UsuarioReparticionHabilitadaDTO> listaRepartcionesHabilitadas) {
    this.listaRepartcionesHabilitadas = listaRepartcionesHabilitadas;
  }

  public UsuarioReparticionHabilitadaDTO getReparticionHabSeleccionada() {
    return reparticionHabSeleccionada;
  }

  public void setReparticionHabSeleccionada(
      UsuarioReparticionHabilitadaDTO reparticionHabSeleccionada) {
    this.reparticionHabSeleccionada = reparticionHabSeleccionada;
  }

  public void onClick$btn_sincronizar() {
    usuarioService.fullImportUsuarios();
  }

}
