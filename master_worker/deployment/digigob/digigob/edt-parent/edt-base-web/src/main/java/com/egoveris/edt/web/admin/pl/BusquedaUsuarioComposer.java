package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BusquedaUsuarioComposer extends BaseComposer {

  private static final long serialVersionUID = -3653535674201948178L;

  @Autowired
  protected AnnotateDataBinder binder;

  private List<UsuarioBaseDTO> listaResultadoUsuarios;
  private List<ReparticionDTO> listaRepartcionesDelUsuario;
  private Integer resultados;
  private UsuarioBaseDTO selectedUsuario;
  private Textbox txbx_usuarioBuscado;
  
  @WireVariable("ldapServiceImpl")
  private ILdapService iLdapService;
  
  @WireVariable("adminReparticionService")
  private IAdminReparticionService adminReparticionService;

  @WireVariable("usuarioServiceImpl")
  private IUsuarioService usuarioService;

  private Toolbarbutton tbbtn_altaUsuario;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    selectedUsuario = new UsuarioBaseDTO();
    listaResultadoUsuarios = new ArrayList<>();
    resultados = listaResultadoUsuarios.size();
    
    comp.addEventListener(Events.ON_USER, new BusquedaUsuarioComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new BusquedaUsuarioComposerListener(this));


    // Visibilidad de campos si cuenta con el rol de PL
    if (Utilitarios.isProjectLeader() && !Utilitarios.isAdministradorCentral()) {
        tbbtn_altaUsuario.setVisible(false);
    }
  }

  /**
   * On click$tbbtn alta usuario.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onClick$tbbtn_altaUsuario() throws InterruptedException {
    try {
      String entorno = (String) SpringUtil.getBean("ldapEntorno");
			if (!"SADE".equalsIgnoreCase(entorno)) {
				Utilitarios.closePopUps("win_altaUsuario");
				Window window = (Window) Executions.createComponents("/administrator/altaUsuario.zul", this.self, null);
				window.setPosition("center");
				window.setVisible(true);
				window.doModal();
			} else {
				Utilitarios.closePopUps("win_altaUsuarioAD");
				Window window = (Window) Executions.createComponents("/administrator/altaUsuarioAD.zul", this.self, null);
				window.setPosition("center");
				window.setVisible(true);
				window.doModal();
			}
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onClick$btn_buscar() throws InterruptedException {
    busquedaUsuarios();
  }

  private void busquedaUsuarios() throws InterruptedException {
    if (validarTextboxDeBusqueda()) {
      buscarUsuarios();
    } else {
      throw new WrongValueException(txbx_usuarioBuscado,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
  }

  /**
   * Buscar usuarios.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void buscarUsuarios() throws InterruptedException {
    try {
    	if(!txbx_usuarioBuscado.getValue().isEmpty() 
    			&& txbx_usuarioBuscado.getValue().trim().equals("*")) {
        listaResultadoUsuarios = iLdapService
            .obtenerTodosLosUsuarios();    		
    	}
    	
    	else if (!txbx_usuarioBuscado.getValue().isEmpty()) {
        listaResultadoUsuarios = iLdapService
            .obtenerUsuarioPorNombreYUid(txbx_usuarioBuscado.getValue());
        
      }
    	
    	if(listaResultadoUsuarios!=null 
    			&& !listaResultadoUsuarios.isEmpty()) {
    	
    		if (!Utilitarios.isAdministradorCentral()
            && Utilitarios.isAdministradorLocalReparticion()) {
          listaRepartcionesDelUsuario = adminReparticionService
              .obtenerReparticionesRelacionadasByUsername(getUsername());
          listaResultadoUsuarios = this
              .filtrarListaUsuariosPorReparticionesDeUsuarios(listaResultadoUsuarios);
        }
        
        resultados = listaResultadoUsuarios.size();
        this.binder.loadAll();
    	}
      
    	
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  private List<UsuarioBaseDTO> filtrarListaUsuariosPorReparticionesDeUsuarios(
      List<UsuarioBaseDTO> listaResultadoUsuariosAfiltrar) {

    List<UsuarioBaseDTO> listaFiltrada = new ArrayList<>();
    for (UsuarioBaseDTO usuario : listaResultadoUsuariosAfiltrar) {
      Boolean stat = false;

      try {
        stat = adminReparticionService.usuarioPerteneceAlasReparticiones(usuario.getUid(),
            listaRepartcionesDelUsuario);
      } catch (Exception e) {
        logger.info(e.getMessage(), e);
      }

      if (stat) {
        listaFiltrada.add(usuario);
      }
    }
    
    return listaFiltrada;
  }

  private boolean validarTextboxDeBusqueda() {
//    if (!Utilitarios.isAdministradorCentral()) {
//      txbx_usuarioBuscado.setValue(txbx_usuarioBuscado.getValue().trim());
//      
//      if (this.txbx_usuarioBuscado.getValue().length() < 3)
//        return false;
//    }
  	
  	  return this.txbx_usuarioBuscado.getValue().trim().equals("*")
  			||
  			this.txbx_usuarioBuscado.getValue().length() > 1;  	
    
  }

  public void onVisualizarUsuario() throws InterruptedException {
    try {
			Utilitarios.closePopUps("win_datosPersonales");
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_USUARIO, selectedUsuario);
      parametros.put(ConstantesSesion.KEY_MODIFICAR, false);
      parametros.put(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA, true);
      parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
      Window win = (Window) Executions.createComponents("/administrator/datosPersonales.zul",
          this.self, parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.busquedaUsuarioComposer.winTitle.visualizarPerfil"));
      win.setWidth("500px");
      win.setPosition("center");
      win.setVisible(true);
      win.setBorder("normal");
      win.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onModificarUsuario() throws InterruptedException {
    try {
      if (Utilitarios.isAdministradorCentral() || (!Utilitarios.isAdministradorCentral ()
          && !Utilitarios.isAdministradorCentral(selectedUsuario))) {
				Utilitarios.closePopUps("win_datosPersonales");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ConstantesSesion.KEY_USUARIO, selectedUsuario);
        parametros.put(ConstantesSesion.KEY_MODIFICAR, true);
        parametros.put(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA, false);
        Window win = (Window) Executions.createComponents("/administrator/datosPersonales.zul",
            this.self, parametros);
        win.setMode("modal");
        win.setClosable(true);
        win.setTitle(Labels.getLabel("eu.busquedaUsuarioComposer.winTitle.modPerfil"));
        win.setWidth("700px");
        win.setPosition("center");
        win.setVisible(true);
        win.setBorder("normal");
        win.doModal();
      } else {
        Messagebox.show(
            Labels.getLabel("eu.busquedaUsuarioComposer.msgbox.usuarioNoEditaInformacion"),
            Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
            Messagebox.EXCLAMATION);
      }
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onAuditarUsuario() throws InterruptedException {
    try {
      if (Utilitarios.isAdministradorCentral() || (!Utilitarios.isAdministradorCentral()
          && !Utilitarios.isAdministradorCentral(selectedUsuario))) {
				Utilitarios.closePopUps("auditoriaUsuarioTab");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ConstantesSesion.KEY_USUARIO, selectedUsuario);
        parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
        Executions.getCurrent().setAttribute(ConstantesSesion.KEY_USUARIO, selectedUsuario);
        Window win = (Window) Executions.createComponents(
            "administrator/tabsAuditoria/auditoriaUsuarioTab.zul", this.self, parametros);
        win.setMode("modal");
        win.setClosable(true);
        win.setTitle(Labels.getLabel("eu.busquedaUsuarioComposer.winTitle.datosHistoricos"));
        win.setWidth("80%");
        win.setPosition("center");
        win.setVisible(true);
        win.setBorder("normal");
        win.doModal();
      } else {
        Messagebox.show(
            Labels.getLabel("eu.busquedaUsuarioComposer.msgbox.usuarioNoAuditarInformacion"),
            Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
            Messagebox.EXCLAMATION);
      }
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onGestionarReparticionesDeUsuario() throws InterruptedException {
    try {
      if (Utilitarios.isAdministradorCentral() || (!Utilitarios.isAdministradorCentral()
          && !Utilitarios.isAdministradorCentral(selectedUsuario))) {
				Utilitarios.closePopUps("win_datosReparticionesUsuario");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ConstantesSesion.KEY_USUARIO, selectedUsuario);

        Window win = (Window) Executions.createComponents(
            "/administrator/datosReparticionesUsuario.zul", this.self, parametros);
        win.setMode("modal");
        win.setClosable(true);
        win.setTitle(Labels.getLabel("eu.busquedaUsuarioComposer.winTitle.gestionOrganismos") + " "
            + selectedUsuario.getUid());
        win.setWidth("800px");
        win.setPosition("center");
        win.setVisible(true);
        win.setBorder("normal");
        win.doModal();
      } else {
        Messagebox.show(
            Labels.getLabel("eu.busquedaUsuarioComposer.msgbox.usuarioNoEditaInformacion"),
            Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
            Messagebox.EXCLAMATION);
      }
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void actUsuario(UsuarioBaseDTO usuarioSade) {
    this.listaResultadoUsuarios.remove(usuarioIndex(this.selectedUsuario));
    this.listaResultadoUsuarios.add(usuarioSade);
  }

  private UsuarioBaseDTO usuarioIndex(UsuarioBaseDTO selectedUsuario2) {
    for (UsuarioBaseDTO usuario : this.listaResultadoUsuarios) {
      if (usuario.getNombre().trim().equals(selectedUsuario2.getNombre().trim())) {
        return usuario;
      }
    }
    return null;
  }

  public void onOK$txbx_usuarioBuscado() throws InterruptedException {
    busquedaUsuarios();
  }

  public List<UsuarioBaseDTO> getListaResultadoUsuarios() {
    return listaResultadoUsuarios;
  }

  public void setListaResultadoUsuarios(List<UsuarioBaseDTO> listaResultadoUsuarios) {
    this.listaResultadoUsuarios = listaResultadoUsuarios;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public Integer getResultados() {
    return resultados;
  }

  public UsuarioBaseDTO getSelectedUsuario() {
    return selectedUsuario;
  }

  public void setSelectedUsuario(UsuarioBaseDTO selectedUsuario) {
    this.selectedUsuario = selectedUsuario;
  }
 
  public void setiLdapService(ILdapService iLdapService) {
    this.iLdapService = iLdapService;
  }

  public ILdapService getiLdapService() {
    return iLdapService;
  }

}

@SuppressWarnings("rawtypes")
final class BusquedaUsuarioComposerListener implements EventListener {
  private BusquedaUsuarioComposer composer;

  public BusquedaUsuarioComposerListener(BusquedaUsuarioComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      this.composer.buscarUsuarios();
      if (event.getData() instanceof UsuarioBaseDTO) {
        this.composer.actUsuario((UsuarioBaseDTO) event.getData());
      }
    }
    
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.binder.loadAll();
    }
  }
}
