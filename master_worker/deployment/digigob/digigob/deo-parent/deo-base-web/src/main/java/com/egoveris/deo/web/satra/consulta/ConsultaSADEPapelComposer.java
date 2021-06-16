package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.model.model.ActuacionSADEBean;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaSADEPapelComposer extends GEDOGenericForwardComposer {
  private static final long serialVersionUID = -496912070973965987L;
  private List<ActuacionSADEBean> tiposDocumentoSADE;
  private ActuacionSADEBean selectedTipoDocumentoSADE;
  @WireVariable("tipoActuacionServiceImpl")
  private TipoActuacionService tipoActuacionService;
  private Intbox numeroSADE;
  private Combobox tipoDocumento;
  private Bandbox reparticionBusquedaSADE;
  private Bandbox reparticionBusquedaSADEPapel;
  private Intbox anioSADE;
  private Window consultaPorNumeroSADEWindow;

  @WireVariable("ecosistemaServiceImpl")
  private IEcosistemaService ecosistemaService;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.reparticionBusquedaSADEPapel.setDisabled(true);
    this.reparticionBusquedaSADE.setText("");
    this.reparticionBusquedaSADE.setDisabled(false);
    if (this.ecosistemaService.obtenerEcosistema().trim().isEmpty()) {
      this.consultaPorNumeroSADEWindow.setTitle(Labels
          .getLabel("gedo.consultaDocumentos.busquedaPorSadePapel", new String[] { "SADE" }));
    } else {
      this.consultaPorNumeroSADEWindow
          .setTitle(Labels.getLabel("gedo.consultaDocumentos.busquedaPorSadePapel",
              new String[] { this.ecosistemaService.obtenerEcosistema() }));
    }

  }

  public void onClick$buscar() throws InterruptedException {
    this.checkConstraints();
    ConsultaSolrRequest consulta = new ConsultaSolrRequest();
    String nroSadePapel = armarNumeroPapel(selectedTipoDocumentoSADE, this.anioSADE.getValue(),
        this.numeroSADE.getValue(), this.reparticionBusquedaSADE.getValue(),
        this.reparticionBusquedaSADEPapel.getValue());
    consulta.setNroSadePapel(nroSadePapel);
    this.closeAndNotifyAssociatedWindow(consulta);
  }

  public String armarNumeroPapel(ActuacionSADEBean actuacion, Integer anio, Integer numero,
      String reparticion, String reparticionUsuario) {

    if ((actuacion == null) || (numero == null) || (anio == null)
        || ((StringUtils.isEmpty(reparticion)) || (reparticion == null))
        || ((actuacion.getDescripcion().equals("EXPEDIENTES")
            && StringUtils.isEmpty(reparticionUsuario)) || (reparticionUsuario == null))) {
      throw new IllegalArgumentException(
          Labels.getLabel("gedo.consSadeComposer.exception.camposCompletados"));
    }

    String numeroSADE = actuacion.getCodigo() + anio.toString() + formatearNumero(numero) + "   "
        + reparticion.trim();
    if (actuacion.getDescripcion().equals("EXPEDIENTES")) {
      numeroSADE += " " + reparticionUsuario.trim();
    }
    return numeroSADE;
  }

  private String formatearNumero(Integer numero) {
    DecimalFormat format = new DecimalFormat("00000000");
    return format.format(numero);
  }

  protected void checkConstraints() {

    if (this.tipoDocumento.getSelectedItem() == null) {
      throw new WrongValueException(this.tipoDocumento,
          Labels.getLabel("gedo.consultaDocumentos.actuacionIncorrecta"));
    }
    if (this.anioSADE.getValue() != null) {
      if (this.anioSADE.getValue() <= 1854) {
        throw new WrongValueException(this.anioSADE,
            Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto",
                new Object[] { String.valueOf(this.anioSADE.intValue()) }));
      }
    } else {
      throw new WrongValueException(this.anioSADE,
          Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto", new Object[] { "" }));
    }
  }

  public void onClick$cerrar() {
    ((Window) this.self).onClose();
  }

  public List<ActuacionSADEBean> getTiposDocumentoSADE() {
    if (this.tiposDocumentoSADE == null) {
      this.tiposDocumentoSADE = this.tipoActuacionService.obtenerListaTodasLasActuaciones();

    }
    return tiposDocumentoSADE;
  }

  public void onChange$tipoDocumento() {
    if (this.selectedTipoDocumentoSADE.getDescripcion().equals("EXPEDIENTES")) {
      this.reparticionBusquedaSADE.setText("MGEYA");
      this.reparticionBusquedaSADE.setDisabled(true);
      this.reparticionBusquedaSADEPapel.setDisabled(false);
    } else {
      this.reparticionBusquedaSADEPapel.setText("");
      this.reparticionBusquedaSADEPapel.setDisabled(true);
      this.reparticionBusquedaSADE.setText("");
      this.reparticionBusquedaSADE.setDisabled(false);
    }

  }

  public ActuacionSADEBean getSelectedTipoDocumentoSADE() {
    return this.selectedTipoDocumentoSADE;
  }

  public void setSelectedTipoDocumentoSADE(ActuacionSADEBean selectedTipoDocumentoSADE) {
    this.selectedTipoDocumentoSADE = selectedTipoDocumentoSADE;
  }

  public Intbox getAnioSADE() {
    return anioSADE;
  }

  public void setAnioSADE(Intbox anioSADE) {
    this.anioSADE = anioSADE;
  }

  public TipoActuacionService getTipoActuacionService() {
    return tipoActuacionService;
  }
}