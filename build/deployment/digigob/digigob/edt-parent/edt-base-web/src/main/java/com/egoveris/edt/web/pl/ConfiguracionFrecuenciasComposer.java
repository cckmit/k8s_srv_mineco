package com.egoveris.edt.web.pl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.usuario.IUsuarioFrecuenciasService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class ConfiguracionFrecuenciasComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static Logger logger = LoggerFactory.getLogger(ConfiguracionAplicacionesComposer.class);
  private static final long serialVersionUID = 5135211429061891529L;
  private Window configuracionFrecuenciasDesktop;
  private Intbox frecuenciaMayor;
  private Intbox frecuenciaMedia;
  private Intbox frecuenciaMenor;
  private Listbox usuriosFrecuenciasId;
  private AnnotateDataBinder binder;
  private Listheader promMayorMax;
  private Listheader promMenorMax;
  private Listheader valorMinimo;
  private Listheader valorMedio;
  private Listheader valorMaximo;
  private Listheader mayorAlMaximo;
  private Vbox boxEjemplo;
  private Session csession = null;

  private Auxheader sistema;
  private Listheader tareasPendientes;

  private IUsuarioFrecuenciasService usuarioFrecuenciasService;

  @Override
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);

    this.usuarioFrecuenciasService = (IUsuarioFrecuenciasService) SpringUtil
        .getBean("usuarioFrecuenciasServiceImpl");
    try {
      csession = Executions.getCurrent().getDesktop().getSession();
      UsuarioFrecuenciaDTO usuarioFrecuencias = (UsuarioFrecuenciaDTO) csession
          .getAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA);

      frecuenciaMayor.setValue(usuarioFrecuencias.getFrecuenciaMayor());
      frecuenciaMedia.setValue(usuarioFrecuencias.getFrecuenciaMedia());
      frecuenciaMenor.setValue(usuarioFrecuencias.getFrecuenciaMenor());

    } catch (Exception ie) {
      logger.error(ie.getMessage(), ie);
      Messagebox.show(Labels.getLabel("eu.configuracionFrecuencias.msgBoxError"),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);
    }

  }

  public Window getConfiguracionFrecuenciasDesktop() {
    return configuracionFrecuenciasDesktop;
  }

  public void setConfiguracionFrecuenciasDesktop(Window configuracionFrecuenciasDesktop) {
    this.configuracionFrecuenciasDesktop = configuracionFrecuenciasDesktop;
  }

  public boolean valida() throws InterruptedException {

    if (frecuenciaMenor.getValue() == null) {
      throw new WrongValueException(this.frecuenciaMenor,
          Labels.getLabel("eu.configuracionFrecuencias.IngresarValor.validacion"));
    }

    if (frecuenciaMedia.getValue() == null) {
      throw new WrongValueException(this.frecuenciaMedia,
          Labels.getLabel("eu.configuracionFrecuencias.IngresarValor.validacion"));
    }

    if (frecuenciaMayor.getValue() == null) {
      throw new WrongValueException(this.frecuenciaMayor,
          Labels.getLabel("eu.configuracionFrecuencias.IngresarValor.validacion"));
    }

    if (frecuenciaMenor.getValue() > Integer
        .valueOf(Labels.getLabel("eu.configuracionFrecuencias.MenorAntiguedad.valor"))) {
      if (frecuenciaMedia.getValue() > frecuenciaMenor.getValue()) {
        if (frecuenciaMayor.getValue() > frecuenciaMedia.getValue()) {
          return true;
        } else {

          throw new WrongValueException(this.frecuenciaMayor,
              Labels.getLabel("eu.configuracionFrecuencias.MayorAntiguedad.validacion"));

        }
      } else {
        throw new WrongValueException(this.frecuenciaMedia,
            Labels.getLabel("eu.configuracionFrecuencias.AntiguedadMedia.validacion"));
      }
    } else {
      throw new WrongValueException(this.frecuenciaMenor,
          Labels.getLabel("eu.configuracionFrecuencias.MenorAntiguedad.validacion"));
    }

  }

  public void onGuardar() throws InterruptedException {

    if (this.valida()) {

      UsuarioFrecuenciaDTO usuarioFrecuencia = (UsuarioFrecuenciaDTO) csession
          .getAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA);
      if (null == usuarioFrecuencia) {
        usuarioFrecuencia = new UsuarioFrecuenciaDTO();
        String userName = Executions.getCurrent().getRemoteUser();
        usuarioFrecuencia.setUsuario(userName);
      }
      usuarioFrecuencia.setFrecuenciaMayor(frecuenciaMayor.getValue());
      usuarioFrecuencia.setFrecuenciaMedia(frecuenciaMedia.getValue());
      usuarioFrecuencia.setFrecuenciaMenor(frecuenciaMenor.getValue());

      this.usuarioFrecuenciasService.insertarUsuarioFrecuencias(usuarioFrecuencia);
      csession.setAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA, usuarioFrecuencia);

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
  }

  public void onPrevisualizar() throws WrongValueException, InterruptedException {

    if (this.valida()) {

      Label labelSistemas = new Label();
      labelSistemas.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.sistema"));
      this.sistema.setLabel(labelSistemas.getValue());
      this.sistema.setStyle("color:#FFFFFF;background: #026290;");

      Label labelTareasPendientes = new Label();
      labelTareasPendientes.setValue(Labels.getLabel("eu.escritorioUnico.misTareas.total"));
      this.tareasPendientes.setLabel(labelTareasPendientes.getValue());
      this.tareasPendientes.setStyle("color:#FFFFFF;background: #026290;");

      if ("promMenorMax".equals(promMenorMax.getId())) {
        Label label = new Label();
        if (frecuenciaMayor.getValue() != null) {
          label.setValue(
              Labels.getLabel("eu.configuracionFrecuencias.misTareas.porcentajeFrecuenciaMenor",
                  new String[] { frecuenciaMayor.getValue().toString() }));
        } else {
          label.setValue(
              Labels.getLabel("eu.configuracionFrecuencias.misTareas.porcentajeFrecuenciaMenor",
                  new String[] { "XX" }));
        }
        promMenorMax.setLabel(label.getValue());
      }

      if ("promMayorMax".equals(promMayorMax.getId())) {
        Label label = new Label();
        if (frecuenciaMayor.getValue() != null) {
          label.setValue(
              Labels.getLabel("eu.configuracionFrecuencias.misTareas.porcentajeFrecuenciaMayor",
                  new String[] { frecuenciaMayor.getValue().toString() }));
        } else {
          label.setValue(
              Labels.getLabel("eu.configuracionFrecuencias.misTareas.porcentajeFrecuenciaMayor",
                  new String[] { "XX" }));
        }
        promMayorMax.setLabel(label.getValue());
      }

      if ("valorMinimo".equals(valorMinimo.getId())) {
        Label label = new Label();
        if (frecuenciaMenor.getValue() != null) {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec1",
              new String[] { frecuenciaMenor.getValue().toString() }));
        } else {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec1",
              new String[] { "XX" }));
        }
        valorMinimo.setLabel(label.getValue());
      }

      if ("valorMedio".equals(valorMedio.getId())) {
        Label label = new Label();
        if (frecuenciaMedia.getValue() != null) {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec2",
              new String[] { frecuenciaMedia.getValue().toString() }));
        } else {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec2",
              new String[] { "XX" }));
        }

        valorMedio.setLabel(label.getValue());
      }

      if ("valorMaximo".equals(valorMaximo.getId())) {
        Label label = new Label();
        if (frecuenciaMayor.getValue() != null) {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec3",
              new String[] { frecuenciaMayor.getValue().toString() }));
        } else {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec3",
              new String[] { "XX" }));
        }

        valorMaximo.setLabel(label.getValue());
      }

      if ("mayorAlMaximo".equals(mayorAlMaximo.getId())) {
        Label label = new Label();
        if (frecuenciaMayor.getValue() != null) {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec4",
              new String[] { frecuenciaMayor.getValue().toString() }));
        } else {
          label.setValue(Labels.getLabel("eu.configuracionFrecuencias.misTareas.frec4",
              new String[] { "XX" }));
        }

        mayorAlMaximo.setLabel(label.getValue());
      }

      boxEjemplo.setVisible(true);

      binder.loadComponent(usuriosFrecuenciasId);
    }

  }

  public Auxheader getSistema() {
    return sistema;
  }

  public void setSistema(Auxheader sistema) {
    this.sistema = sistema;
  }

  public Listheader getTareasPendientes() {
    return tareasPendientes;
  }

  public void setTareasPendientes(Listheader tareasPendientes) {
    this.tareasPendientes = tareasPendientes;
  }

}
