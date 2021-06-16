package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.AuditoriaPaseDetalleResult;
import com.egoveris.te.base.model.AuditoriaPaseResult;
import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.base.model.PaseDetalleResult;
import com.egoveris.te.base.service.PaseServiceSatra;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class HistorialDePasesTrackComposer extends ExpedienteTrackActuacionComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 7746932099085891172L;

  private ExpedienteTrack expTrack;
  private PaseDetalleResult model;
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  Window historialDePasesExpedienteTrackWindow;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  PaseServiceSatra paseServiceSatra;
  @Autowired
  Radio repOrigenRadioExterno;
  @Autowired
  Radio repOrigenRadioInterno;
  @Autowired
  Radio repDestinoRadioExterno;
  @Autowired
  Radio repDestinoRadioInterno;
  @Autowired
  Label reparticionOrigen;
  @Autowired
  Label reparticionDestino;
  @Autowired
  Longbox fojas;
  @Autowired
  Longbox fojasDetalle;
  @Autowired
  Textbox permanenciaEnElArchivo;
  @Autowired
  Listbox listHistorial;
  @Autowired
  Paging consultaPaginator;
  @Autowired
  Label repDestino;
  @Autowired
  Label repOrigen;
  @Autowired
  Textbox actuacion;
  @Autowired
  Textbox tipo;
  @Autowired
  Textbox trataDetalle;
  @Autowired
  Textbox extraEjecutiva;
  @Autowired
  Textbox cuerposAnexos;
  @Autowired
  Textbox observacionesDePase;
  @Autowired
  Longbox opAnio;
  @Autowired
  Longbox opNumero;
  @Autowired
  Textbox compTipo;
  @Autowired
  Longbox compAnio;
  @Autowired
  Longbox compNro;
  @Autowired
  Longbox compImporte;
  @Autowired
  Longbox ALEAnio;
  @Autowired
  Textbox ALELeg;
  @Autowired
  Longbox ALEEst;
  @Autowired
  Listbox incorporacionesList;
  @Autowired
  Textbox fechaDetalle;
  private List<AuditoriaPaseResult> historiales;
  private AuditoriaPaseResult selectedPase;
  private AuditoriaPaseDetalleResult selectedAuditoria;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    expTrack = (ExpedienteTrack) Executions.getCurrent().getArg().get("expedienteTrack");
    model = (PaseDetalleResult) Executions.getCurrent().getArg().get("paseDetalle");
    this.anio.setValue(Long.valueOf(expTrack.getAnio()));
    String fecha = model.getFechaCreacion();
    fechaDetalle.setValue(fecha.substring(0, 11));
    this.ex.setValue(expTrack.getLetraTrack());
    this.codigoReparticion.setValue(expTrack.getCodigoReparticionActuacion());
    this.codigoRepUsuario.setValue(expTrack.getCodigoReparticionUsuario());
    this.sec.setValue(expTrack.getSecuencia());
    this.numero.setValue(Long.valueOf(expTrack.getNumero()));
    try {
      historiales = paseServiceSatra.consultaPaseAuditoria(model.getIdPase());
    } catch (Exception e) {
      self.detach();
      logger.error(
          "Ha ocurrido un error al consultar los pases de Auditoria del expediente Track numero: "
              + expTrack.getCodigoCaratula());
      alert("Ha ocurrido un error al cargar los datos de la ventana");
    }
    logger
        .info("Cargando Datos de la ventana: " + historialDePasesExpedienteTrackWindow.getTitle());
    binder.loadAll();

  }

  public void onPopularDatosGenerales() {
    if (selectedPase.isInterna()) {
      repOrigenRadioInterno.setChecked(true);
      repDestinoRadioInterno.setChecked(true);
    } else {
      repOrigenRadioExterno.setChecked(true);
      repDestinoRadioExterno.setChecked(true);
    }
    repOrigen.setValue(selectedPase.getCodigoReparticionOrigen());
    repDestino.setValue(selectedPase.getCodigoReparticionDestino());
    reparticionOrigen.setValue(selectedPase.getOrigen());
    reparticionDestino.setValue(selectedPase.getDestino());
    if (selectedPase.getFojas() != null) {
      fojas.setValue(Long.valueOf(selectedPase.getFojas()));
    }
    if (selectedPase.getCodigoPermanencia() != null) {
      permanenciaEnElArchivo.setValue(selectedPase.getCodigoPermanencia());
    }
    binder.loadAll();
  }

  public void limpiarDetalles() {
    actuacion.setValue("");
    tipo.setValue("");
    fojas.setValue(null);
    trataDetalle.setValue("");
    extraEjecutiva.setValue("");
    cuerposAnexos.setValue("");
    observacionesDePase.setValue("");
    opAnio.setValue(null);
    opNumero.setValue(null);
    compTipo.setValue("");
    compNro.setValue(null);
    compAnio.setValue(null);
    compImporte.setValue(null);
    ALEAnio.setValue(null);
    ALEEst.setValue(null);
    ALELeg.setValue("");
  }

  public void onClick$volver() {
    historialDePasesExpedienteTrackWindow.detach();
  }

  public void onMostrarDetalles() {
    if (selectedAuditoria.getActuacion() != null) {
      actuacion.setValue(selectedAuditoria.getActuacion());
    }
    if (selectedAuditoria.getTipo() != null) {
      tipo.setValue(String.valueOf(selectedAuditoria.getTipo()));
    }
    if (selectedAuditoria.getFojas() != null) {
      fojasDetalle.setValue(selectedAuditoria.getFojas().longValue());
    }
    if (selectedAuditoria.getFojas() != null) {
      fojas.setValue(selectedAuditoria.getFojas().longValue());
    }
    if (selectedAuditoria.getCodTrata() != null) {
      trataDetalle.setValue(selectedAuditoria.getCodTrata());
    }
    if (selectedAuditoria.getExtraEjecutiva() != null) {
      extraEjecutiva.setValue(selectedAuditoria.getExtraEjecutiva());
    }
    if (selectedAuditoria.getCuerpoAnexo() != null) {
      cuerposAnexos.setValue(selectedAuditoria.getCuerpoAnexo());
    }
    if (selectedAuditoria.getObsDePase() != null) {
      observacionesDePase.setValue(selectedAuditoria.getObsDePase());
    }
    if (selectedAuditoria.getOpAnio() != null) {
      opAnio.setValue(selectedAuditoria.getOpAnio().longValue());
    }
    if (selectedAuditoria.getOpNumero() != null) {
      opNumero.setValue(selectedAuditoria.getOpNumero().longValue());
    }
    if (selectedAuditoria.getCompTipo() != null) {
      compTipo.setValue(selectedAuditoria.getCompTipo());
    }
    if (selectedAuditoria.getCompNumero() != null) {
      compNro.setValue(selectedAuditoria.getCompNumero().longValue());
    }
    if (selectedAuditoria.getCompAnio() != null) {
      compAnio.setValue(selectedAuditoria.getCompAnio().longValue());
    }
    if (selectedAuditoria.getCompImporte() != null) {
      compImporte.setValue(selectedAuditoria.getCompImporte().longValue());
    }
    if (selectedAuditoria.getAnio() != null) {
      ALEAnio.setValue(selectedAuditoria.getAnio().longValue());
    }
    if (selectedAuditoria.getEstante() != null) {
      ALEEst.setValue(selectedAuditoria.getEstante().longValue());
    }
    if (selectedAuditoria.getLegajo() != null) {
      ALELeg.setValue(selectedAuditoria.getLegajo());
    }

  }

  public List<AuditoriaPaseResult> getHistoriales() {
    return historiales;
  }

  public void setHistoriales(List<AuditoriaPaseResult> historiales) {
    this.historiales = historiales;
  }

  public AuditoriaPaseResult getSelectedPase() {
    return selectedPase;
  }

  public void setSelectedPase(AuditoriaPaseResult selectedPase) {
    this.selectedPase = selectedPase;
  }

  public AuditoriaPaseDetalleResult getSelectedAuditoria() {
    return selectedAuditoria;
  }

  public void setSelectedAuditoria(AuditoriaPaseDetalleResult selectedAuditoria) {
    this.selectedAuditoria = selectedAuditoria;
  }

}
