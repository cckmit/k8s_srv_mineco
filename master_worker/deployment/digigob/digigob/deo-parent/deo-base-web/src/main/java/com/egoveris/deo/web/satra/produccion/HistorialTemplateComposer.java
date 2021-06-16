package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HistorialTemplateComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 1L;

  private CKeditor ckeditor;
  private Label descripcion;
  private Label formAsociado;
  @WireVariable("procesamientoTemplateImpl")
  private ProcesamientoTemplate procesamientoTemplate;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    TipoDocumentoTemplateDTO tipoDocumentoTemplate = procesamientoTemplate
        .obtenerUltimoTemplatePorTipoDocumento(
            (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento"));

    if (tipoDocumentoTemplate != null) {
      byte[] data = tipoDocumentoTemplate.getTemplate().getBytes();
      String s = new String(data);
      renderDatosPantalla(s, tipoDocumentoTemplate.getDescripcion(),
          tipoDocumentoTemplate.getIdFormulario(), tipoDocumentoTemplate.getIdFormulario());
    }

  }

  private void renderDatosPantalla(String datos, String descripcionTemplate,
      String idFormularioControlado, String nombreFormulario) throws Exception {
    this.ckeditor.setValue(datos);
    this.descripcion.setValue(descripcionTemplate.length() > 100
        ? descripcionTemplate.substring(0, 100) + "..." : descripcionTemplate);
    this.descripcion.setTooltiptext(descripcionTemplate);
    this.formAsociado.setValue(nombreFormulario);
    this.formAsociado.setTooltiptext(nombreFormulario);
  }

}
