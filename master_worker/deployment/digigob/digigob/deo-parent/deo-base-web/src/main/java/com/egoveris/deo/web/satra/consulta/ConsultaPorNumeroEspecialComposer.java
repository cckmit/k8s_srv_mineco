package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaPorNumeroEspecialComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -1569138975299328259L;
  private Intbox anioDocEspecial;
  private Intbox numeroEspecial;
  private Bandbox reparticionBusquedaEspeciales;
  private TipoDocumentoDTO selectedTipoDocumentoEspecial;
  private Bandbox familiaEstructuraTree;
  @WireVariable("ecosistemaServiceImpl")
  private IEcosistemaService ecosistemaService;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    familiaEstructuraTree.addEventListener(Events.ON_NOTIFY,
        new ConsultaPorNumeroEspecialComposerListener(this));

  }

  public TipoDocumentoDTO getSelectedTipoDocumentoEspecial() {
    return selectedTipoDocumentoEspecial;
  }

  public void setSelectedTipoDocumentoEspecial(TipoDocumentoDTO selectedTipoDocumentoEspecial) {
    this.selectedTipoDocumentoEspecial = selectedTipoDocumentoEspecial;
  }

  public void onClick$buscar() throws InterruptedException {
    this.checkConstraints();
    ConsultaSolrRequest consulta = new ConsultaSolrRequest();
    if (!this.selectedTipoDocumentoEspecial.getEsOculto()) {
      String nroSadeEsp = armarNumeracionEsp(this.selectedTipoDocumentoEspecial.getAcronimo(),
          this.anioDocEspecial.getValue(), this.numeroEspecial.getValue(),
          this.reparticionBusquedaEspeciales.getValue());
      consulta.setNroEspecialSade(nroSadeEsp);
    }
    this.closeAndNotifyAssociatedWindow(consulta);
  }

  // TODO modificar si es multi ecosistema
  public String armarNumeracionEsp(String tipoAcronimo, Integer anio, Integer numero,
      String reparticion) {
    if ((tipoAcronimo == null) || (numero == null) || (anio == null)
        || ((StringUtils.isEmpty(reparticion)) || (reparticion == null))) {
      throw new IllegalArgumentException(
          Labels.getLabel("gedo.consSadeComposer.exception.camposCompletados"));
    }
    if (ecosistemaService.obtenerEcosistema().trim().isEmpty()) {
      return tipoAcronimo + "-" + anio.toString() + "-" + numero.toString() + "-"
          + reparticion.trim();
    } else {
      return tipoAcronimo + "-" + anio.toString() + "-" + numero.toString() + "-"
          + ecosistemaService.obtenerEcosistema() + "-" + reparticion.trim();
    }

  }

  protected void checkConstraints() {

    if (this.selectedTipoDocumentoEspecial == null) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
    }
    if (this.anioDocEspecial.getValue() != null) {
      if (this.anioDocEspecial.getValue() <= 1854) {
        throw new WrongValueException(this.anioDocEspecial,
            Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto",
                new String[] { String.valueOf(this.anioDocEspecial.intValue()) }));
      }
    } else {
      throw new WrongValueException(this.anioDocEspecial,
          Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto", new Object[] { "" }));
    }

  }

  public void onClick$cerrar() {
    ((Window) this.self).onClose();
  }

  public Bandbox getFamiliaEstructuraTree() {
    return familiaEstructuraTree;
  }

  public void setFamiliaEstructuraTree(Bandbox familiaEstructuraTree) {
    this.familiaEstructuraTree = familiaEstructuraTree;
  }

  public void cargarTipoDocumento(TipoDocumentoDTO data) {
    this.familiaEstructuraTree.setText(data.getAcronimo());
    this.familiaEstructuraTree.close();
    setSelectedTipoDocumentoEspecial(data);
  }
}

final class ConsultaPorNumeroEspecialComposerListener implements EventListener {
  private ConsultaPorNumeroEspecialComposer composer;

  public ConsultaPorNumeroEspecialComposerListener(ConsultaPorNumeroEspecialComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {

    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
        this.composer.cargarTipoDocumento(data);
      }
    }
  }
}