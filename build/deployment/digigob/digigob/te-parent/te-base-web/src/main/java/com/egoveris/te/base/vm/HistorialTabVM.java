package com.egoveris.te.base.vm;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HistorialTabVM {

  private static final String MGEYA = "MGEYA";

  private ExpedienteElectronicoDTO expediente;
  private List<HistorialOperacionDTO> listaHistorial;

  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  HistorialOperacionService historialOperacionService;
  
  /**
   * Inicializa el Tab de Historial. Obtiene la lista de historial a traves del
   * expediente
   * 
   * @param expediente
   *          Expediente
   */
  @Init
  public void init(@ExecutionArgParam("expediente") ExpedienteElectronicoDTO expediente) {
    if (expediente != null) {
      setExpediente(expediente);
      setListaHistorial(
          historialOperacionService.buscarHistorialporExpediente(expediente.getId()));

      if (getListaHistorial().isEmpty()) {
        Messagebox.show(Labels.getLabel("ee.caratulas.historial.expedienteNoExiste"),
            Labels.getLabel("ee.caratulas.historial.informacion.caratulaNoExiste"), Messagebox.OK,
            Messagebox.EXCLAMATION);
      }
    }
  }

  // - LOGICA PERSONALIZADA GRILLA -

  /**
   * Obtiene el valor de la etiqueta de destino para un registro de historial
   * 
   * @param historial
   * @return Label destino
   */
  public String labelDestino(HistorialOperacionDTO historial) {
    String destino = "";

    if (historial.getDestinatario() != null) {
      destino = historial.getDestinatario();

      if (historial.getDestinatario().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
          || historial.getDestinatario().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)
          || historial.getDestinatario().equals(ConstantesWeb.ESTADO_ARCHIVO)) {
        destino = MGEYA;
      }
    }

    return destino;
  }

  // Getters - setters

  public ExpedienteElectronicoDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteElectronicoDTO expediente) {
    this.expediente = expediente;
  }

  public List<HistorialOperacionDTO> getListaHistorial() {
    return listaHistorial;
  }

  public void setListaHistorial(List<HistorialOperacionDTO> listaHistorial) {
    this.listaHistorial = listaHistorial;
  }

}
