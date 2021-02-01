package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditarFamiliaTipoDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -6749792007557977128L;
  private static final Logger logger = LoggerFactory
      .getLogger(EditarFamiliaTipoDocumentoComposer.class);

  private Window editarFamiliaTipoDocumentoWindow;
  private FamiliaTipoDocumentoDTO selectedFamilia;
  private FamiliaTipoDocumentoDTO itemSelected;
  @WireVariable("familiaTipoDocumentoServiceImpl")
  private FamiliaTipoDocumentoService familiaTipoDocumentoService;

  private Textbox nfamilia;
  private AnnotateDataBinder binder;
  private Textbox dfamilia;
  private String usuario;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    @SuppressWarnings("unchecked")
    Map<Object, Object> map = (Map<Object, Object>) Executions.getCurrent().getArg();
    itemSelected = (FamiliaTipoDocumentoDTO) map.get("ItemSeleted");
    this.nfamilia.setValue(itemSelected.getNombre());
    this.dfamilia.setValue(itemSelected.getDescripcion());
    this.usuario = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute("userName");
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  public void onGuardar() throws InterruptedException {
    validarEntrada();
    try {
      this.itemSelected.setDescripcion(this.dfamilia.getValue());
      this.itemSelected.setNombre(this.nfamilia.getValue().trim());
      this.familiaTipoDocumentoService.updateFamilia(this.itemSelected, usuario);

      Messagebox.show(Labels.getLabel("gedo.familiaTipoDocumento.guardar"),
          Labels.getLabel("ccoo.definirDestinatario.mensaje"), Messagebox.OK,
          Messagebox.INFORMATION);
      this.closeAndNotifyAssociatedWindow(null);
    } catch (Exception e) {
      logger.error("Error al actualizar familia", e);
      Messagebox.show(Labels.getLabel("gedo.familiaTipoDocumento.error.guardar"),
          Labels.getLabel("gedo.inbox.error.title"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  private void validarEntrada() {
    String nombre = this.nfamilia.getValue();
    if (yaExisteNombre(nombre)) {
      throw new WrongValueException(this.nfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.nombreExiste"));
    }
    if (this.nfamilia.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.nfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.nombreRequerido"));
    }
    if (this.dfamilia.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.dfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.descripcionRequerida"));
    }
  }

  private boolean yaExisteNombre(String nombre) {

    String nombreNuevo = this.nfamilia.getValue().trim();
    if (!nombreNuevo.equals(itemSelected.getNombre())) {
      List<String> nombres = this.familiaTipoDocumentoService.traerNombresFamilias();
      for (String nb : nombres) {
        if (nb.equals(nombreNuevo)) {
          return true;
        }
      }
    }
    return false;
  }

  public void onCancelar() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.general.cerrarVentanaPlantilla"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              closeWindow();
              break;
            case Messagebox.NO:
              return;
            }
          }
        });
  }

  private void closeWindow() {
    this.editarFamiliaTipoDocumentoWindow.detach();
  }

  public FamiliaTipoDocumentoDTO getSelectedFamilia() {
    return selectedFamilia;
  }

  public void setSelectedFamilia(FamiliaTipoDocumentoDTO selectedFamilia) {
    this.selectedFamilia = selectedFamilia;
  }
}