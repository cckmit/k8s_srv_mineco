package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.IPlantillaFacade;
import com.egoveris.deo.model.model.PlantillaDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.sql.SQLException;
import java.util.List;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProducirTemplateComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 5373594361915672253L;
  private static final Logger logger = LoggerFactory.getLogger(ProducirTemplateComposer.class);
  protected Window producirTemplateWindow;
  protected Task workingTask = null;

  protected CKeditor ckeditor;
  protected PlantillaDTO selectedPlantilla;
  protected List<PlantillaDTO> listaPlantilla;
  @WireVariable("plantillaFacadeImpl")
  protected IPlantillaFacade plantillaFacade;
  @WireVariable("usuarioServiceImpl")
  public IUsuarioService usuarioService;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.inicializarPlantillas();

  }

  public Window getProducirTemplateWindow() {
    return producirTemplateWindow;
  }

  public void setProducirTemplateWindow(Window producirTemplateWindow) {
    this.producirTemplateWindow = producirTemplateWindow;
  }

  public void onSelect$comboboxPlantillas() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.plantillas.aplicarPlantilla"),
        Labels.getLabel("gedo.plantillas.aplicarPlantilla.titulo"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event e) throws InterruptedException {
            if ("onYes".equals(e.getName())) {
              try {
                aplicarPlantilla();
              } catch (SQLException e1) {
                logger.error("Mensaje de error", e1);
              }
            }
          }

        });
  }

  private void inicializarPlantillas() throws SQLException {
    this.setListaPlantilla(this.plantillaFacade.buscarPlantillaPorUsuarioPlantilla(
        (String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME)));
  }

  public void aplicarPlantilla() throws SQLException {
    if (this.selectedPlantilla != null) {
      this.selectedPlantilla = this.plantillaFacade
          .buscarPlantillaPorId(this.selectedPlantilla.getId());
      this.ckeditor.setValue(this.selectedPlantilla.getContenido());
    }
  }

  public PlantillaDTO getSelectedPlantilla() {
    return selectedPlantilla;
  }

  public void setSelectedPlantilla(PlantillaDTO selectedPlantilla) {
    this.selectedPlantilla = selectedPlantilla;
  }

  public void setListaPlantilla(List<PlantillaDTO> listaPlantilla) {
    this.listaPlantilla = listaPlantilla;
  }

  public List<PlantillaDTO> getListaPlantilla() {
    return listaPlantilla;
  }

}
