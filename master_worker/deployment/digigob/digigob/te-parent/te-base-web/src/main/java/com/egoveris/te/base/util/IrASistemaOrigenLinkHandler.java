package com.egoveris.te.base.util;

import com.egoveris.te.base.component.ComponenteLink;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.helper.ValidacionSistemasExternosHelper;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Hbox;

/**
 * Link que sabe su comportamiento, si puede ser usado/visualizado en pantalla.
 */
public class IrASistemaOrigenLinkHandler extends ComponenteLink {

  private static Logger logger = LoggerFactory.getLogger(IrASistemaOrigenLinkHandler.class);

  private static final long serialVersionUID = 1L;
  private static IrASistemaOrigenLinkHandler instance;
  @Autowired
  ExpedienteElectronicoService expedienteElectronicoService;
  private Tarea selectedTask;
  private Tarea task;
  private Hbox hbox;

  public static IrASistemaOrigenLinkHandler getInstance() {
    if (instance == null) {
      instance = new IrASistemaOrigenLinkHandler();
    }

    return instance;
  }

  /**
   * Inicializa el componente.
   * 
   * @param <code>Tarea</code>task
   * @param <code>Hbox</code>hbox
   */
  public void initComponentLink(Tarea task, Hbox hbox) {
    ExpedienteElectronicoDTO ee = getExpedienteElectronico(task);
    if (ee != null && ee.getSistemaCreador().equalsIgnoreCase((ConstantesWeb.SISTEMA_BAC))) {
      super.initComponentLink(ee, task, hbox, "inboxWindow$InboxComposer.onIrASistemaExterno");
    }
  }

  private ExpedienteElectronicoDTO getExpedienteElectronico(Tarea task) {
    ExpedienteElectronicoDTO expedienteElectronico = null; 
    try {
      // Verifico que tenga codigo de expediente y no sea una solicitud
      if (!"".equals(task.getCodigoExpediente())) {
        expedienteElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronicoPorCodigo(task.getCodigoExpediente());
      }
    } catch (Exception e) {
      logger.error(Labels.getLabel("ee.envio.sistemas.externos.errorBusquedaExpediente"));
    }

    return expedienteElectronico;
  }

  /**
   * Realiza la acción
   * 
   * @throws <code>InterruptedException</code>
   *           si ocurre una excepción en los mecanismos de la vista.
   */
  public boolean onClick(Tarea selectedTask) throws InterruptedException, NegocioException {

    String username = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
    ParametrosSistemaExternoDTO parametrosSistemaExterno = null;

    ExpedienteElectronicoDTO expedienteElectronico = obtenerExpediente(
        this.selectedTask.getCodigoExpediente());

    parametrosSistemaExterno = ( this.getConfiguracionInicialModuloEEFactory())
        .obtenerParametrosPorTrata(expedienteElectronico.getTrata().getId());
    ;
    ValidacionSistemasExternosHelper helper = new ValidacionSistemasExternosHelper();
    helper.validarExpediente(selectedTask);

    if (parametrosSistemaExterno != null) {
      parametrosSistemaExterno
          .setParametros("?usr=" + username + "&exp=" + selectedTask.getCodigoExpediente());
    }

    try {

      URL url;
      if (parametrosSistemaExterno != null) {
        url = new URL(parametrosSistemaExterno.getURLFull());
        URLConnection con = url.openConnection();
        con.connect();
      }

    } catch (MalformedURLException e) {
      throw new InterruptedException("Se produjo un error al comunicarse con el Sistema BAC");
    } catch (IOException e) {
      throw new InterruptedException("Se produjo un error al comunicarse con el Sistema BAC");
    }
    if (parametrosSistemaExterno != null) {
      Executions.getCurrent().sendRedirect(parametrosSistemaExterno.getURLFull(), "_blank");
    }
    return true;
  }

  protected void actualizarDocumentosNoDefinitivos() throws InterruptedException {

    ValidacionSistemasExternosHelper
        .actualizarDocumentosNoDefinitivos(this.selectedTask.getCodigoExpediente());

  }

  public void envioExpedienteAFJG() throws InterruptedException {
  }

  private ExpedienteElectronicoDTO obtenerExpediente(String codigoExpedienteElectronico)
      throws InterruptedException {
    try { 

      return expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);
    } catch (Exception e) {
      throw new InterruptedException(
          Labels.getLabel("ee.envio.sistemas.externos.errorBusquedaExpediente"));
    }
  }

  public static boolean hostAvailabilityCheck(String host) {

    try {
      return InetAddress.getByName(host).isReachable(1000);
    } catch (IOException ex) {
      logger.debug(Labels.getLabel("ee.envio.sistemas.externos.errorAlEnviarEnServicio",
          new String[] { host }));
    }
    return false;
  }

  public static String normalizarhost(String host) {
    String result = host.replace("http://", "");
    result = result.replace("https://", "");
    int indice = result.indexOf("/");
    if (indice != -1) {
      result = result.substring(0, indice);
    }
    return result;
  }

  /**
   * Link.setDisabled(<code>Component</code> component,<code>Boolean</code>) :
   * Se chequea los permisos de la accion para habilitar o deshabilitar el link.
   */
  public void setComponentDisabled(Component componentRef) {
    try {
      super.setComponentDisabled("inboxWindow$InboxComposer.onIrASistemaExterno", componentRef);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  // ***************************************************************
  // ** SETTER & GETTER
  // ***************************************************************
  public Hbox getHbox() {
    return hbox;
  }

  public void setHbox(Hbox hbox) {
    this.hbox = hbox;
  }

  public Tarea getTask() {
    return task;
  }

  public void setTask(Tarea task) {
    this.task = task;
  }

  public Tarea getSelectedTask() {
    return this.selectedTask;
  }

  public void setSelectedTask(Tarea selectedTask) {
    this.selectedTask = selectedTask;
  }

}
