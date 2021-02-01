package com.egoveris.deo.web.satra;

import com.egoveris.deo.base.exception.DocumentoDigitalExistenteException;
import com.egoveris.deo.base.exception.DocumentoExistenteGEDOCaratulaExcepcion;
import com.egoveris.deo.base.exception.NoEsDocumentoSadePapelException;
import com.egoveris.deo.base.exception.NoExisteDocumentoSadeExcepcion;
import com.egoveris.deo.base.service.BuscarDocumentosSadeService;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.model.model.ActuacionSADEBean;
import com.egoveris.deo.util.Constantes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IncorporarSadeComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 1811351826032197174L;
  
  @WireVariable("buscarDocumentosSadeServiceImpl")
  private BuscarDocumentosSadeService buscarDocumentosSadeService;
  @WireVariable("tipoActuacionServiceImpl")
  private TipoActuacionService tipoActuacionService;
  protected AnnotateDataBinder importarBinder;

  private Textbox trataExpediente;
  private Combobox actuacionSADE;
  private List<ActuacionSADEBean> actuacionesSADE;
  private ActuacionSADEBean selectedActuacionSADE;
  private Intbox anioSADE;
  private Intbox numeroSADE;
  private Bandbox reparticionImportarDocumentoSADE;
  private Bandbox reparticionUsuarioImportarDocumentoSADE;
  private Window incorporarSade;
  private String trata;
  private String codigo;

  private static final Logger logger = LoggerFactory.getLogger(IncorporarSadeComposer.class);

  public void doAfterCompose(Component comp) throws Exception {

    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.self.addEventListener(Events.ON_OPEN, new IncorporarSadeComposerListener(this));

    this.reparticionImportarDocumentoSADE.setText("");
    this.reparticionImportarDocumentoSADE.setDisabled(false);
    this.reparticionUsuarioImportarDocumentoSADE.setText("");
    this.reparticionUsuarioImportarDocumentoSADE.setDisabled(true);
    
    this.importarBinder = new AnnotateDataBinder(comp);
    this.importarBinder.loadAll();

  }

  public void onChange$actuacionSADE() {
    /**
     * Se cambia onSelect por onChange, porque cuando se selecciona el tipo de
     * actuación desde teclado, el item seleccionado llega en null.
     */
    if (this.selectedActuacionSADE == null) {
      throw new WrongValueException(this.actuacionSADE,
          Labels.getLabel("gedo.incorporarDocumento.faltaActuacion"));
    }

    resetControles();
    armadoPantalla();

  }

  private void armadoPantalla() {
    if (this.selectedActuacionSADE.getDescripcion().equals("EXPEDIENTES")) {
      this.reparticionImportarDocumentoSADE.setText("MGEYA");
      this.reparticionImportarDocumentoSADE.setDisabled(true);
      this.reparticionUsuarioImportarDocumentoSADE.setDisabled(false);
    } else {
      this.reparticionImportarDocumentoSADE.setText("");
      this.reparticionImportarDocumentoSADE.setDisabled(false);
      this.reparticionUsuarioImportarDocumentoSADE.setText("");
      this.reparticionUsuarioImportarDocumentoSADE.setDisabled(true);
    }
  }

  public void onClick$buscarCaratulaButton() throws InterruptedException {

    String numeroDocumento = null;
    String codigoSade = null;

    validarSade();

    numeroDocumento = armarNumeracionEstandar();
    codigoSade = StringUtils.remove(numeroDocumento, "-");

    if (this.selectedActuacionSADE.getDescripcion().equals("EXPEDIENTES")) {
      codigoSade = codigoSade + " " + this.reparticionUsuarioImportarDocumentoSADE.getValue();
    }
    this.codigo = codigoSade;
    buscarYCompletarPantalla(numeroDocumento, codigoSade);

  }

  private void validarSade() {

    if (this.selectedActuacionSADE == null) {
      notificarPadre(null);
      trataExpediente.setText(null);
      throw new WrongValueException(actuacionSADE,
          Labels.getLabel("gedo.incorporarSadeComp.exception.selecioneActuacion"));
    }
    if (this.anioSADE.getValue() == null) {
      notificarPadre(null);
      trataExpediente.setText(null);
      throw new WrongValueException(anioSADE,
          Labels.getLabel("gedo.incorporarSadeComp.exception.ingreseValor"));
    }
    if (this.numeroSADE.getValue() == null) {
      notificarPadre(null);
      trataExpediente.setText(null);
      throw new WrongValueException(numeroSADE,
          Labels.getLabel("gedo.incorporarSadeComp.exception.ingreseValor"));
    }
    if (this.selectedActuacionSADE.getDescripcion().equals("EXPEDIENTES")
        && StringUtils.isEmpty(this.reparticionUsuarioImportarDocumentoSADE.getValue())) {
      trataExpediente.setText(null);
      notificarPadre(null);
      throw new WrongValueException(reparticionUsuarioImportarDocumentoSADE,
          Labels.getLabel("gedo.incorporarSadeComp.exception.seleccioneOrganismoUsuario"));
    }
    if (StringUtils.isEmpty(this.reparticionImportarDocumentoSADE.getValue())) {
      trataExpediente.setText(null);
      notificarPadre(null);
      throw new WrongValueException(reparticionImportarDocumentoSADE,
          Labels.getLabel("gedo.incorporarSadeComp.exception.seleccioneOrganismo"));
    }
  }

  private void buscarYCompletarPantalla(String numeroDocumento, String codigoSade)
      throws InterruptedException {
    String descripcionTrata = null;

    try {

      descripcionTrata = this.buscarDocumentosSadeService
          .consultaCaratulaSadeExiste(numeroDocumento, codigoSade);
      if (StringUtils.isNotEmpty(descripcionTrata)) {
        trataExpediente.setText(descripcionTrata);
        this.setTrata(descripcionTrata);
      } else {
        this.setTrata(descripcionTrata);
        trataExpediente
            .setText(Labels.getLabel("gedo.incorporarSadeComp.exception.avtuacionEncontrada"));
      }

    } catch (NoExisteDocumentoSadeExcepcion nedse) {
      logger.error("Error al armar pantalla para incorporar numero sade " + nedse.getMessage(),
          nedse);
      actualizarActuacionNoEncontrada();
      Messagebox.show(
          Labels.getLabel("gedo.importarDocumentos.noExisteDocumentoSade",
              new String[] { codigoSade }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (DocumentoExistenteGEDOCaratulaExcepcion e) {
      logger.error("Error al armar pantalla para incorporar numero sade " + e.getMessage(), e);
      actualizarActuacionNoEncontrada();
      Messagebox.show(
          Labels.getLabel("gedo.importarDocumentos.yaExisteDocumento",
              new String[] { codigoSade }),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    } catch (DocumentoDigitalExistenteException e) {
      logger.error("Error al armar pantalla para incorporar numero sade " + e.getMessage(), e);
      actualizarActuacionNoEncontrada();
      Messagebox.show(
          Labels.getLabel("gedo.importarDocumentos.existenteCCOO", new String[] { codigoSade }),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    } catch (NoEsDocumentoSadePapelException e) {
      logger.error("Error al armar pantalla para incorporar numero sade " + e.getMessage(), e);
      actualizarActuacionNoEncontrada();
      Messagebox.show(
          Labels.getLabel("gedo.importarDocumentos.noEsDocumentoSadePapel",
              new String[] { codigoSade }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.EXCLAMATION);
    }

  }

  private void actualizarActuacionNoEncontrada() {
    this.codigo = "";
  }

  public void obtenerTrata(String codigoSade) {

  }

  private void notificarPadre(String codigoSade) {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("origen", Constantes.EVENTO_DOCUMENTO_SADE);
    map.put("codigoSade", codigoSade);

    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
  }

  /**
   * Arma la numeracion estandar de los documentos SADE.
   * 
   * @param codigoActuacionSade
   * @param anio
   * @param numero
   * @param codigoReparticion
   * @return
   */
  private String armarNumeracionEstandar() {
    String numeroFormateado = formatearNumero(this.numeroSADE.getValue().toString());

    return selectedActuacionSADE.getCodigo() + this.anioSADE.getValue().toString() + "-"
        + numeroFormateado + "-" + "   -"
        + this.reparticionImportarDocumentoSADE.getValue().trim();

  }

  private String formatearNumero(String numero) {
    DecimalFormat format = new DecimalFormat("00000000");
    Integer numeroAformatear = Integer.valueOf(numero);
    String numeroFormateado = format.format(numeroAformatear);
    return numeroFormateado;
  }

  public List<ActuacionSADEBean> getActuacionesSADE() {
    if (this.actuacionesSADE == null) {
      this.actuacionesSADE = this.tipoActuacionService.obtenerListaTodasLasActuaciones();
    }
    return this.actuacionesSADE;
  }

  public void setSelectedActuacionSADE(ActuacionSADEBean selectedActuacionSADE) {
    this.selectedActuacionSADE = selectedActuacionSADE;
  }

  public ActuacionSADEBean getSelectedActuacionSADE() {
    return selectedActuacionSADE;
  }

  /**
   * Carga estado inicial de componentes gráficos.
   */
  protected final void resetControles() {
    Clients.clearBusy();
    this.anioSADE.setValue(null);
    this.numeroSADE.setValue(null);
    this.reparticionImportarDocumentoSADE.setValue(null);
    this.trataExpediente.setValue(null);
    this.reparticionUsuarioImportarDocumentoSADE.setValue(null);
    this.reparticionUsuarioImportarDocumentoSADE.setDisabled(true);
    this.reparticionImportarDocumentoSADE.setValue(null);
    this.reparticionImportarDocumentoSADE.setDisabled(false);
  }

  public void completarDatosDocumentoSade(String codigoSade) throws InterruptedException {

    int contadorCaracteres = 0;
    boolean armado = false;
    StringBuffer actuacion = new StringBuffer();
    String anio;
    StringBuffer numero = new StringBuffer();
    StringBuffer reparticion = new StringBuffer();
    StringBuffer reparticionUsuario = new StringBuffer();

    // Armo Actuacion
    while (!armado) {
      if (Character.isLetter(codigoSade.charAt(contadorCaracteres))) {
        actuacion.append(String.valueOf(codigoSade.charAt(contadorCaracteres)));
        contadorCaracteres++;
      } else {
        armado = true;
      }
    }
    armado = false;

    // Armo Año
    anio = StringUtils.substring(codigoSade, contadorCaracteres, contadorCaracteres + 4);
    contadorCaracteres = contadorCaracteres + 4;

    // Armo Numero
    while (!armado) {
      if (!Character.isSpaceChar(codigoSade.charAt(contadorCaracteres))) {
        numero.append(String.valueOf(codigoSade.charAt(contadorCaracteres)));
        contadorCaracteres++;
      } else {
        armado = true;
      }
    }
    armado = false;

    // Salteo los espacios en blanco
    while (!armado) {
      if (Character.isSpaceChar(codigoSade.charAt(contadorCaracteres))) {
        contadorCaracteres++;
      } else {
        armado = true;
      }
    }
    armado = false;

    // Armo Reparticion
    while (!armado) {
      if (contadorCaracteres < codigoSade.length()
          && !Character.isSpaceChar(codigoSade.charAt(contadorCaracteres))) {
        reparticion.append(String.valueOf(codigoSade.charAt(contadorCaracteres)));
        contadorCaracteres++;
      } else {
        armado = true;
      }
    }
    armado = false;
    contadorCaracteres++;

    // Armo Reparticion Usuario
    while (!armado) {
      if (contadorCaracteres < codigoSade.length()) {
        reparticionUsuario.append(String.valueOf(codigoSade.charAt(contadorCaracteres)));
        contadorCaracteres++;
      } else {
        armado = true;
      }
    }
    armado = false;

    try {

      this.setSelectedActuacionSADE(obtenerActuacion(actuacion.toString()));
      armadoPantalla();
      this.anioSADE.setValue(Integer.valueOf(anio.toString()));
      this.numeroSADE.setValue(Integer.valueOf(numero.toString()));
      this.reparticionImportarDocumentoSADE.setValue(reparticion.toString());
      this.reparticionUsuarioImportarDocumentoSADE.setValue(reparticionUsuario.toString());

      buscarYCompletarPantalla(armarNumeracionEstandar(), codigoSade);

    } catch (Exception e) {
      logger.error("Mensaje de error", e);
      Messagebox.show(
          Labels.getLabel(Labels.getLabel("gedo.redactarDocumentoComposer.msgbox.errorCargarDoc"),
              new String[] { codigoSade }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
      resetControles();
    }
  }

  private ActuacionSADEBean obtenerActuacion(String codigoActuacion) {
    for (ActuacionSADEBean actuacion : actuacionesSADE) {
      if (actuacion.getCodigo().equals(codigoActuacion)) {
        return actuacion;
      }
    }
    return null;
  }

  public void onClick$aceptar() {
    validarSade();
    if (codigo == null) {
      Messagebox.show(Labels.getLabel("gedo.incorporarSadeComp.msgbox.presioneObtenerDescripcion"),
          Labels.getLabel("ccoo.title.atencion"), Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
      Map<String, Object> maps = new HashMap<String, Object>();
      maps.put("origen", Constantes.EVENTO_INCORPORAR_SADE);
      maps.put("sade", codigo);
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), maps));
      notificarPadre(codigo);
      this.incorporarSade.onClose();
    }

  }

  public void onClick$cancelar() {

    this.incorporarSade.onClose();
  }

  public String getTrata() {
    return trata;
  }

  public void setTrata(String trata) {
    this.trata = trata;
  }

}

final class IncorporarSadeComposerListener implements EventListener {
  private IncorporarSadeComposer composer;

  public IncorporarSadeComposerListener(IncorporarSadeComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_OPEN)) {
      if (event.getData() != null) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) event.getData();
        String codigoSade = (String) map.get("codigoSade");

        if (codigoSade != null && !codigoSade.equals(""))
          composer.completarDatosDocumentoSade(codigoSade);
      }
    }
  }

}
