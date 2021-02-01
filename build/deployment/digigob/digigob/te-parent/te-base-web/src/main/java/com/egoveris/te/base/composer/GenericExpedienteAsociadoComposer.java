/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;

/**
 * @author jnorvert
 *
 */
@SuppressWarnings("serial")
public class GenericExpedienteAsociadoComposer extends GenericForwardComposer {

  final static Logger logger = LoggerFactory.getLogger(GenericExpedienteAsociadoComposer.class);

  public List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado = new ArrayList<>();
  public ExpedienteAsociadoEntDTO selectedExpedienteAsociado;
  private Listbox expedienteAsociadoComponent;

  private String codigoExpedienteElectronico;
  protected Task workingTask = null;
  private ExpedienteElectronicoDTO expedienteElectronico;

  /**
   * Defino los servicios que voy a utilizar
   */

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);

    this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));
    try {
      this.codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
          .getAttribute("codigoExpedienteElectronico");
    } catch (Exception e) {
      logger.debug(e.getMessage());
      throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
    }
    codigoExpedienteElectronico = BusinessFormatHelper
        .quitaCerosCodigoExpediente(codigoExpedienteElectronico);

    String tipoActaucion = BusinessFormatHelper.obtenerActuacion(codigoExpedienteElectronico);
    Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpedienteElectronico);
    Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteElectronico);
    String reparticion = BusinessFormatHelper
        .obtenerReparticionUsuario(codigoExpedienteElectronico);
    this.expedienteElectronico = expedienteElectronicoService
        .obtenerExpedienteElectronico(tipoActaucion, anio, numero, reparticion);

    for (int i = 0; i < this.expedienteElectronico.getListaExpedientesAsociados().size(); i++) {
      if (((this.expedienteElectronico.getListaExpedientesAsociados().get(i)
          .getEsExpedienteAsociadoTC() == null)
          || !this.expedienteElectronico.getListaExpedientesAsociados().get(i)
              .getEsExpedienteAsociadoTC())
          && ((this.expedienteElectronico.getListaExpedientesAsociados().get(i)
              .getEsExpedienteAsociadoFusion() == null)
              || !this.expedienteElectronico.getListaExpedientesAsociados().get(i)
                  .getEsExpedienteAsociadoFusion())) {
        this.listaExpedienteAsociado
            .add(this.expedienteElectronico.getListaExpedientesAsociados().get(i));
      }
    }
    expedienteAsociadoComponent
        .setModel(new BindingListModelList(this.listaExpedienteAsociado, true));
  }

  public List<ExpedienteAsociadoEntDTO> getListaExpedienteAsociado() {
    return listaExpedienteAsociado;
  }

  public void setListaExpedienteAsociado(List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado) {
    this.listaExpedienteAsociado = listaExpedienteAsociado;
  }

  public ExpedienteAsociadoEntDTO getSelectedExpedienteAsociado() {
    return selectedExpedienteAsociado;
  }

  public void setSelectedExpedienteAsociado(ExpedienteAsociadoEntDTO selectedExpedienteAsociado) {
    this.selectedExpedienteAsociado = selectedExpedienteAsociado;
  }
  
  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public String getCodigoExpedienteElectronico() {
    return codigoExpedienteElectronico;
  }

  public void setCodigoExpedienteElectronico(String codigoExpedienteElectronico) {
    this.codigoExpedienteElectronico = codigoExpedienteElectronico;
  }

}
