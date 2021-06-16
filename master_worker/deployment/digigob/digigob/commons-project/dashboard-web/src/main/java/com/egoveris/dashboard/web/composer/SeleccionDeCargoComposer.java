package com.egoveris.dashboard.web.composer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
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

public class SeleccionDeCargoComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 7664205572787858194L;
  protected static final Logger logger = LoggerFactory.getLogger(SeleccionDeCargoComposer.class);
  
  private Window win_seleccionDeReparticion;

  private IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService;

  @Autowired
  private IReparticionSeleccionadaService reparticionSeleccionadaService;

  private ReparticionSeleccionadaDTO reparticionSeleccionadaParaPersistir;

  private IUsuarioService usuarioService;

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

  @Autowired
  CustomAuthoritiesPopulator authoritiesPopulator;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    csession = Executions.getCurrent().getDesktop().getSession();

    usuarioReparticionHabilitadaService = (IUsuarioReparticionHabilitadaService) SpringUtil
        .getBean("usuarioReparticionHabilitadaService");
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    reparticionSeleccionadaService = (IReparticionSeleccionadaService) SpringUtil
        .getBean("reparticionSeleccionaService");
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");
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
        .obtenerReparticionesHabilitadasByUsername( Utilitarios.obtenerUsuarioActual().getUsername()));
    resaltarReparticionPropia();
    this.binder.loadComponent(this.listboxReparticiones);
  }

  private void resaltarReparticionPropia() {
    this.reparticionHabSeleccionada = usuarioReparticionHabilitadaService
        .obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario( Utilitarios.obtenerUsuarioActual().getUsername());
  }

  public void onSeleccionarReparticion() throws InterruptedException, SecurityNegocioException {
	  Executions.getCurrent().getDesktop().getSession().setAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN,
        reparticionHabSeleccionada.getReparticion());
    reparticionSeleccionadaParaPersistir	
        .setReparticion(reparticionHabSeleccionada.getReparticion());
    reparticionSeleccionadaParaPersistir.setSector(reparticionHabSeleccionada.getSector());
    this.reparticionSeleccionadaParaPersistir.setUsuario( Utilitarios.obtenerUsuarioActual().getUsername());
    Usuario user = Utilitarios.obtenerUsuarioActual();
    user.setCargo(reparticionHabSeleccionada.getCargo().getCargoNombre());
    reparticionSeleccionadaService
        .establecerReparticionSeleccionada(reparticionSeleccionadaParaPersistir, user);


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

    Executions.sendRedirect("");
  }


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
