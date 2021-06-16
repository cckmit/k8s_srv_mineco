package com.egoveris.edt.web.pl;

import com.egoveris.edt.base.exception.SystemException;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioGenericoDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ITareasServiceFactory;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class ConfiguracionAplicacionesComposer extends GenericForwardComposer {

	
  /**
  * 
  */
  private static Logger logger = LoggerFactory.getLogger(ConfiguracionAplicacionesComposer.class);

  private static final long serialVersionUID = 5135211429061891529L;

  private Window configuracionAplicacionesDesktop;
  private List<AplicacionDTO> listaAplicaciones;
  private AplicacionDTO aplicacion;
  private Listbox abmAplicacionesId;
  private Session csession = null;
  private IUsuarioAplicacionService usuarioBuzonGrupalService;
  private IUsuarioAplicacionService usuarioMisSistemasService;
  private IUsuarioAplicacionService usuarioMisTareasService;
  private IUsuarioAplicacionService usuarioMisSupervisadosService;
  private IAplicacionService aplicacionesService;
  private ITareasServiceFactory tareaServiceFactory;

  @Override
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);
    this.usuarioBuzonGrupalService = (IUsuarioAplicacionService) SpringUtil
        .getBean("usuarioBuzonGrupalService");
    this.usuarioMisSistemasService = (IUsuarioAplicacionService) SpringUtil
        .getBean("usuarioMisSistemasService");
    this.usuarioMisTareasService = (IUsuarioAplicacionService) SpringUtil
        .getBean("usuarioMisTareasService");
    this.usuarioMisSupervisadosService = (IUsuarioAplicacionService) SpringUtil
        .getBean("usuarioMisSupervisadosService");
    this.aplicacionesService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");
    this.tareaServiceFactory = (ITareasServiceFactory) SpringUtil.getBean("tareaServiceFactory");

    csession = Executions.getCurrent().getDesktop().getSession();

  }

  public Window getConfiguracionAplicacionesDesktop() {
    return configuracionAplicacionesDesktop;
  }

  public void setConfiguracionAplicacionesDesktop(Window configuracionAplicacionesDesktop) {
    this.configuracionAplicacionesDesktop = configuracionAplicacionesDesktop;
  }

  public AplicacionDTO getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(AplicacionDTO aplicacion) {
    this.aplicacion = aplicacion;
  }

  public void setListaAplicaciones(List<AplicacionDTO> listaAplicaciones) {
    this.listaAplicaciones = listaAplicaciones;
  }

  public boolean esVisibleTareas(String aplicacion) {

    System.out.println(aplicacion);
    return true;

  }

  public List<AplicacionDTO> getListaAplicaciones() {
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
    return listaAplicaciones;
  }

  public void onGuardar() throws InterruptedException {

    String userName = Executions.getCurrent().getRemoteUser();
    List<Integer> listaIdsAplicacionesMisTareas = new ArrayList<Integer>();
    List<Integer> listaIdsAplicacionesMisSistemas = new ArrayList<Integer>();
    List<Integer> listaIdsAplicacionesTareasMisSupervisado = new ArrayList<Integer>();
    Map<Integer, List<String>> listaIdsAplicacionesBuzonGrupal = new HashMap<Integer, List<String>>();
    // List<Integer> listaIdsAplicacionesBuzonGrupal = new
    // ArrayList<Integer>();
    for (Object hijos : abmAplicacionesId.getChildren()) {

      if (hijos.getClass().equals(org.zkoss.zul.Listitem.class)) {
        Listitem a = (Listitem) hijos;
        String nombreAplicacion = ((Listcell) a.getChildren().get(0)).getLabel();
        Checkbox checkMisTareas = (Checkbox) ((Listcell) a.getChildren().get(1)).getFirstChild();
        Checkbox checkMisSistemas = (Checkbox) ((Listcell) a.getChildren().get(2)).getFirstChild();
        Checkbox checkMisSupervisados = (Checkbox) ((Listcell) a.getChildren().get(3))
            .getFirstChild();
        Checkbox checkBuzon = (Checkbox) ((Listcell) a.getChildren().get(4)).getFirstChild();
        // ** Teniendo el nombre de la aplicacion se obtiene el
        // objeto completo, donde se encuentra el ID de la misma.
        AplicacionDTO aplicacionChecked = this.buscarAplicacionPorNombre(nombreAplicacion);

        // ** Se crea un objeto generico, el cual se insertara o se
        // borrara segun corresponda en cada tabla.
        UsuarioGenericoDTO usuarioAplicacion = new UsuarioGenericoDTO();
        usuarioAplicacion.setAplicacionID(aplicacionChecked.getIdAplicacion());
        usuarioAplicacion.setUsuario(userName);

        // ** Si se encuentra chekeado y el registro no esta en la
        // tabla, se inserta.
        if (checkMisTareas.isChecked() && aplicacionChecked.isVisibleMisTareas()) {
          this.usuarioMisTareasService.insertarUsuarioAplicacion(usuarioAplicacion);
          listaIdsAplicacionesMisTareas.add(usuarioAplicacion.getAplicacionID());

        } else if (!checkMisTareas.isChecked() && aplicacionChecked.isVisibleMisTareas()) {
          this.usuarioMisTareasService.borrarUsuarioAplicacion(usuarioAplicacion);
        }

        // ** Para tabla usuarioMisSistemas.
        if (checkMisSistemas.isChecked() && aplicacionChecked.isVisibleMisSistemas()) {
          this.usuarioMisSistemasService.insertarUsuarioAplicacion(usuarioAplicacion);
          listaIdsAplicacionesMisSistemas.add(usuarioAplicacion.getAplicacionID());

        } else if (!checkMisSistemas.isChecked() && aplicacionChecked.isVisibleMisSistemas()) {
          this.usuarioMisSistemasService.borrarUsuarioAplicacion(usuarioAplicacion);
        }

        // ** Para tabla usuarioMisSupervisados.
        if (checkMisSupervisados.isChecked() && aplicacionChecked.isVisibleMisSupervisados()) {
          this.usuarioMisSupervisadosService.insertarUsuarioAplicacion(usuarioAplicacion);
          listaIdsAplicacionesTareasMisSupervisado.add(usuarioAplicacion.getAplicacionID());

        } else if (!checkMisSupervisados.isChecked()
            && aplicacionChecked.isVisibleMisSupervisados()) {
          this.usuarioMisSupervisadosService.borrarUsuarioAplicacion(usuarioAplicacion);
        }

        // ** Para tabla usuarioBuzonGrupal.
        if (checkBuzon.isChecked() && aplicacionChecked.isVisibleBuzonGrupal()) {
          this.usuarioBuzonGrupalService.insertarUsuarioAplicacion(usuarioAplicacion);
          
          List<String> grupos = tareaServiceFactory.get(aplicacionChecked.getNombreAplicacion())
              .buscarGruposUsuarioAplicacion(usuarioAplicacion.getUsuario());
          listaIdsAplicacionesBuzonGrupal.put(usuarioAplicacion.getAplicacionID(), grupos);

        } else if (!checkBuzon.isChecked() && aplicacionChecked.isVisibleBuzonGrupal()) {
          this.usuarioBuzonGrupalService.borrarUsuarioAplicacion(usuarioAplicacion);
        }
      }
    }
    csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SISTEMAS,
        listaIdsAplicacionesMisSistemas);
    csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_TAREAS,
        listaIdsAplicacionesMisTareas);
    csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS,
        listaIdsAplicacionesTareasMisSupervisado);
    csession.setAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS,
        listaIdsAplicacionesBuzonGrupal);

    Messagebox.show(Labels.getLabel("eu.configuracionFrecuencias.msgBoxGuardar"),
        Labels.getLabel("eu.general.information"), Messagebox.OK,
        Messagebox.INFORMATION, new EventListener() {

          @Override
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.OK:
              Executions.sendRedirect("/panelUsuario.zul");
              break;
            }
          }
        });

  }

  public AplicacionDTO buscarAplicacionPorNombre(String nombreAplicacion) {

    for (AplicacionDTO apliSelect : this.listaAplicaciones) {

      if (apliSelect.getNombreAplicacion().equals(nombreAplicacion))
        return apliSelect;

    }
    return null;
  }

  public void setAplicacionesService(IAplicacionService aplicacionesService) {
    this.aplicacionesService = aplicacionesService;
  }

  public void setTareaServiceFactory(ITareasServiceFactory tareaServiceFactory) {
    this.tareaServiceFactory = tareaServiceFactory;
  }

}
