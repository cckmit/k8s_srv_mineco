package com.egoveris.deo.web.satra.tipos;

import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.HashMap;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class ValidacionObligatoriedadComposer extends GEDOGenericForwardComposer {

  public Window validacionObligatoriedadWindow;
  public Row aceptarCancelar, rowOk;
  public Toolbarbutton continuar, cancelar;
  public Button buttonOk;
  public Label texto;
  public Div textDiv;
  public boolean bloqueante;
  public List<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidos;
  public HashMap<String, Object> map = new HashMap<String, Object>();

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    map.put("origen", Constantes.EVENTO_OBLIGATORIEDAD_EXTENSIONES);
    this.bloqueante = (Boolean) Executions.getCurrent().getArg().get("bloqueante");
    this.texto = new Label();
    this.texto.setParent(this.textDiv);
    if (bloqueante) {
      this.texto.setValue(
          Labels.getLabel("gedo.validacionObligatoriedadComp.text.docRequeireArchivosEmbebidos"));
      this.aceptarCancelar.setVisible(false);
      this.validacionObligatoriedadWindow.setTitle("Validación");
    } else {
      this.texto.setValue(Labels.getLabel(
          "gedo.validacionObligatoriedadComp.text.docRequeireArchivosEmbebidosDeseaContinuar"));
      this.rowOk.setVisible(false);
      this.validacionObligatoriedadWindow.setTitle("Confirmación");
    }
  }

  public void onClick$buttonOk() {
    map.put("salida", false);
    this.closeAndNotifyAssociatedWindow(map);
  }

  public void onClick$continuar() {
    map.put("salida", true);
    this.closeAndNotifyAssociatedWindow(map);
  }

  public void onClick$cancelar() {
    map.put("salida", false);
    this.closeAndNotifyAssociatedWindow(map);
  }
}
