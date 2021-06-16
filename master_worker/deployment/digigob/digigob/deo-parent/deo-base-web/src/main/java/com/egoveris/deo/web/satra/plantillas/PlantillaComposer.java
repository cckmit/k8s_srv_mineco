package com.egoveris.deo.web.satra.plantillas;

import com.egoveris.deo.base.service.IPlantillaFacade;
import com.egoveris.deo.model.model.PlantillaDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PlantillaComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -552398098524032598L;

  private static Logger logger = LoggerFactory.getLogger(PlantillaComposer.class);

  private PlantillaDTO plantilla;

  @WireVariable("plantillaFacadeImpl")
  private IPlantillaFacade plantillaFacade;

  private Window plantillaWindow;

  private CKeditor cKeditor;

  private String accion;

  private List<PlantillaDTO> listadoPlantillas;

  private Textbox txtNombrePlantilla;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.plantilla = ((PlantillaDTO) Executions.getCurrent().getArg().get("selectedPlantilla"));
    this.accion = ((String) Executions.getCurrent().getArg().get("accion"));
    this.listadoPlantillas = ((List<PlantillaDTO>) Executions.getCurrent().getArg()
        .get("listadoPlantillas"));

    if (this.plantilla == null) {
      this.plantilla = new PlantillaDTO();
    } else {
      byte[] bdata = this.plantilla.getContenido().getBytes();
      String text = new String(bdata);
      this.cKeditor.setValue(text);
    }

    if (!StringUtils.isEmpty(this.accion)) {
      if (this.accion.equals("editar")) {
        this.txtNombrePlantilla.setDisabled(true);
      }
    }
  }

  private boolean existeNombrePlantilla(String nombre) {
    boolean result = false;

    if (nombre != null && !nombre.isEmpty()) {
      for (PlantillaDTO plantilla : listadoPlantillas) {
        if (nombre.trim().equals(plantilla.getNombre())) {
          result = true;
          break;
        }
      }
    }

    return result;
  }

  public void setPlantilla(PlantillaDTO plantilla) {
    this.plantilla = plantilla;
  }

  public PlantillaDTO getPlantilla() {
    return plantilla;
  }

  public void setPlantillaWindow(Window plantillaWindow) {
    this.plantillaWindow = plantillaWindow;
  }

  public Textbox getTxtNombrePlantilla() {
    return txtNombrePlantilla;
  }

  public void setTxtNombrePlantilla(Textbox txtNombrePlantilla) {
    this.txtNombrePlantilla = txtNombrePlantilla;
  }

  public Window getPlantillaWindow() {
    return plantillaWindow;
  }

  public void onClick$cancelar() {
    Messagebox.show(Labels.getLabel("gedo.general.cerrarVentanaPlantilla"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
            Clients.evalJavaScript("parent.closeIframe();");
              closeWindow();
              break;
            case Messagebox.NO:
              return;
            }
          }
        });
  }

  private void closeWindow() {
    this.plantillaWindow.detach();
  }

  public void onClick$guardar() throws InterruptedException {
    if (!StringUtils.isEmpty(this.plantilla.getNombre())) {
      if (this.cKeditor.getValue() != "") {
        this.plantilla.setContenido(this.cKeditor.getValue());
      } else {
        throw new WrongValueException(this.cKeditor,
            Labels.getLabel("gedo.plantillaComposer.exception.debeIngresarContenido"));
      }
      if (!StringUtils.isEmpty(this.accion)) {
        if (this.accion.equals("crear")) {
          this.plantilla.setNombre(this.plantilla.getNombre().trim());
          if (!this.existeNombrePlantilla(plantilla.getNombre())) {
            this.plantillaFacade.crear((String) Executions.getCurrent().getSession()
                .getAttribute(Constantes.SESSION_USERNAME), plantilla);
            Messagebox.show(Labels.getLabel("gedo.plantillas.plantillaCreada"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK,
                Messagebox.INFORMATION);
            this.closeAndNotifyAssociatedWindow(plantilla);
          } else {
            Messagebox.show(Labels.getLabel("gedo.plantillas.plantillaNoCreada"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
          }
        } else {
          if (this.accion.equals("editar")) {
            this.plantillaFacade.actualizar(plantilla);
            Messagebox.show(Labels.getLabel("gedo.plantillas.plantillaCreada"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK,
                Messagebox.INFORMATION);
            this.closeAndNotifyAssociatedWindow(null);
          }
        }
      }
    } else {
      throw new WrongValueException(this.txtNombrePlantilla,
          Labels.getLabel("gedo.plantillaComposer.exception.debeIngresarNombre"));
    }
  }

}
