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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaPorActuacionSadeComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -8735142835952601365L;

  private Combobox comboActuacionesSADE;
  private Intbox numeroSADE;
  private Intbox anioSADE;
  private Bandbox reparticionBusquedaSADE;
  private Window consultaPorActuacionSadeWindow;

  // private Combobox comboEcosistemas;

  private AnnotateDataBinder binder;
  private List<ActuacionSADEBean> listaActuacionesSade;
  private ActuacionSADEBean actuacionSeleccionada;
  @WireVariable("tipoActuacionServiceImpl")
  private TipoActuacionService tipoActuacionService;
  @WireVariable("ecosistemaServiceImpl")
  private IEcosistemaService ecosistemaService;
  private List<String> listaEcosistemas;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.listaActuacionesSade = this.tipoActuacionService.obtenerListaTodasLasActuaciones();
    if (this.ecosistemaService.obtenerEcosistema().trim().isEmpty()) {
      this.consultaPorActuacionSadeWindow.setTitle(Labels
          .getLabel("gedo.consultaDocumentos.actuacionSade.titulo", new String[] { "SADE" }));
    } else {
      this.consultaPorActuacionSadeWindow
          .setTitle(Labels.getLabel("gedo.consultaDocumentos.actuacionSade.titulo",
              new String[] { this.ecosistemaService.obtenerEcosistema() }));
    }

  }

  public void onClick$buscar() throws InterruptedException {
    this.checkConstraints();
    ConsultaSolrRequest consulta = new ConsultaSolrRequest();
    if (this.actuacionSeleccionada != null) {
      String nroSade = armarNumeracion(actuacionSeleccionada.getCodigo(), this.anioSADE.getValue(),
          this.numeroSADE.getValue(), this.reparticionBusquedaSADE.getValue());
      consulta.setNroSade(nroSade);
      consulta.setActuacionAcr(this.actuacionSeleccionada.getCodigo());
      this.closeAndNotifyAssociatedWindow(consulta);
    } else {
      throw new WrongValueException(comboActuacionesSADE,
          Labels.getLabel("gedo.consultaDocumentos.actuacionSade.actuacionErronea"));
    }
  }

  public void onClick$cerrar() {
    ((Window) this.self).onClose();
  }

  private String armarNumeracion(String codigoTipoDocumentoSade, Integer anio, Integer numero,
      String reparticion) {
    if ((codigoTipoDocumentoSade == null) || (numero == null) || (anio == null)
        || ((StringUtils.isEmpty(reparticion)) || (reparticion == null))) {
      throw new IllegalArgumentException(
          Labels.getLabel("gedo.consSadeComposer.exception.camposCompletados"));
    }

    return armarNumeracionEstandar(codigoTipoDocumentoSade, anio.toString(), numero.toString(),
        reparticion);
  }

  // TODO modificar si es multi ecosistema
  private String armarNumeracionEstandar(String codigoActuacionSade, String anio, String numero,
      String codigoReparticion) {
    String numeroFormateado = formatearNumero(numero);
    String codigoEcosistema = ecosistemaService.obtenerEcosistema();
    if (codigoEcosistema.trim().isEmpty()) {
      return codigoActuacionSade + "-" + anio + "-" + numeroFormateado + "-   -"
          + codigoReparticion.trim();
    } else {
      return codigoActuacionSade + "-" + anio + "-" + numeroFormateado + "-" + codigoEcosistema
          + "-" + codigoReparticion.trim();
    }

  }

  private String formatearNumero(String numero) {
    DecimalFormat format = new DecimalFormat("00000000");
    Integer numeroAformatear = Integer.valueOf(numero);
    String numeroFormateado = format.format(numeroAformatear);

    return numeroFormateado;
  }

  protected void checkConstraints() {

    if (this.comboActuacionesSADE.getValue() == null
        || this.comboActuacionesSADE.getValue().isEmpty()) {
      throw new WrongValueException(this.comboActuacionesSADE,
          Labels.getLabel("gedo.consultaDocumentos.actuacionSade.actuacionVacia"));
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

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public List<ActuacionSADEBean> getListaActuacionesSade() {
    return listaActuacionesSade;
  }

  public void setListaActuacionesSade(List<ActuacionSADEBean> listaActuacionesSade) {
    this.listaActuacionesSade = listaActuacionesSade;
  }

  public ActuacionSADEBean getActuacionSeleccionada() {
    return actuacionSeleccionada;
  }

  public void setActuacionSeleccionada(ActuacionSADEBean actuacionSeleccionada) {
    this.actuacionSeleccionada = actuacionSeleccionada;
  }

  public TipoActuacionService getTipoActuacionService() {
    return tipoActuacionService;
  }

  public void setTipoActuacionService(TipoActuacionService tipoActuacionService) {
    this.tipoActuacionService = tipoActuacionService;
  }

  public void setEcosistemaService(IEcosistemaService ecosistemaService) {
    this.ecosistemaService = ecosistemaService;
  }

  public IEcosistemaService getEcosistemaService() {
    return ecosistemaService;
  }

  public void setListaEcosistemas(List<String> listaEcosistemas) {
    this.listaEcosistemas = listaEcosistemas;
  }

  public List<String> getListaEcosistemas() {
    return listaEcosistemas;
  }

}
