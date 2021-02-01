package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HistorialTipoDocumentoComposer extends GEDOGenericForwardComposer {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(HistorialTipoDocumentoComposer.class);

  private Window historialDocumentoWindow;
  private Window historialTemplateWindow;
  private Listbox tiposDocumentoLb;

  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  private List<TipoDocumentoDTO> tiposDocumentos;
  private TipoDocumentoDTO tipoDocumento;
  private TipoDocumentoDTO selectedTipoDocumento;

  public void doAfterCompose(Component comp) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("doAfterCompose(Component) - start"); //$NON-NLS-1$
    }

    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento");
    this.historialDocumentoWindow.setTitle(Labels.getLabel("gedo.historial.documento.titulo") + " "
        + this.tipoDocumento.getAcronimo());
    this.tiposDocumentos = this.tipoDocumentoService
        .buscarDocumentosPorVersiones(this.tipoDocumento.getAcronimo());

    if (logger.isDebugEnabled()) {
      logger.debug("doAfterCompose(Component) - end"); //$NON-NLS-1$
    }
  }

  public void onVerTemplate() {
    if (logger.isDebugEnabled()) {
      logger.debug("onVerTemplate() - start"); //$NON-NLS-1$
    }

    HashMap<String, Object> hm = new HashMap<>();
    hm.put("tipoDocumento", this.selectedTipoDocumento);
    this.historialTemplateWindow = (Window) Executions.createComponents("historialTemplate.zul",
        this.self, hm);
    this.historialTemplateWindow.setClosable(true);
    this.historialTemplateWindow.doModal();

    if (logger.isDebugEnabled()) {
      logger.debug("onVerTemplate() - end"); //$NON-NLS-1$
    }
  }

  public void onVerEmbebidos() {
    if (logger.isDebugEnabled()) {
      logger.debug("onVerEmbebidos() - start"); //$NON-NLS-1$
    }

    HashMap<String, Object> hm = new HashMap<>();
    hm.put("tipoDocumento", this.selectedTipoDocumento);
    this.historialTemplateWindow = (Window) Executions.createComponents("historialEmbebidos.zul",
        this.self, hm);
    this.historialTemplateWindow.setClosable(true);
    this.historialTemplateWindow.doModal();

    if (logger.isDebugEnabled()) {
      logger.debug("onVerEmbebidos() - end"); //$NON-NLS-1$
    }
  }

  public Window getHistorialDocumentoWindow() {
    return historialDocumentoWindow;
  }

  public void setHistorialDocumentoWindow(Window historialDocumentoWindow) {
    this.historialDocumentoWindow = historialDocumentoWindow;
  }

  public List<TipoDocumentoDTO> getTiposDocumentos() {
    return tiposDocumentos;
  }

  public void setTiposDocumentos(List<TipoDocumentoDTO> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
  }

  public TipoDocumentoDTO getSelectedTipoDocumento() {
    return selectedTipoDocumento;
  }

  public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  public Listbox getTiposDocumentoLb() {
    return tiposDocumentoLb;
  }

  public void setTiposDocumentoLb(Listbox tiposDocumentoLb) {
    this.tiposDocumentoLb = tiposDocumentoLb;
  }

}
