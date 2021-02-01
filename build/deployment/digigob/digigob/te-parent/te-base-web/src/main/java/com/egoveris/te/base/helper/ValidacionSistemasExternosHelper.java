/**
 * 
 */
package com.egoveris.te.base.helper;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;

/**
 *
 */
public class ValidacionSistemasExternosHelper {

	
  private static final String SISTEMA_AFJG = "AFJG";

  private static final String SISTEMAS_EXTERNOS = "Sistemas externos";

  private static Logger logger = LoggerFactory.getLogger(ValidacionSistemasExternosHelper.class);

  private ExpedienteElectronicoDTO expedienteElectronico;

  @Autowired
  private HistorialOperacionService historialOperacionService;
  
  @Autowired
  TramitacionConjuntaService tramitacionConjuntaService;
  
  @Autowired
  TareaParaleloService tareaParaleloService;
  
  @Autowired
  FusionService fusionService;
  
  @Autowired
  IActividadExpedienteService actividadExpedienteService;
  
  @Autowired
  static ExpedienteElectronicoService expedienteElectronicoService;
   
  @Autowired
  IActividadExpedienteService actividadService;
    

  public static Boolean tienePermisoUsuarioAFJG() {

    return (Boolean) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_PERMISO_INTEGRACION_SIS_EXT);

  }

  public void validaExpedienteEnvioAFJG(Tarea task, ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {

    this.setExpedienteElectronico(expedienteElectronico);

    tieneCaratulaVariableSinCompletar(task, SISTEMA_AFJG);
    verificarAsociacionesSinConfirmar();
    verificarTramitacionConjunta(task);
    verificarTramitacionParalelo(task);
    verificarExpedienteFusion(task);
    verificarActividadesPendientesDeResolucion(task);

  }

  private void verificarAsociacionesSinConfirmar() throws InterruptedException {
    String debeConfirmar = " antes de enviar el expediente a AFJG.";
    String tipoAsociacion;
    for (ExpedienteAsociadoEntDTO expAsociado : expedienteElectronico
        .getListaExpedientesAsociados()) {
      if (!expAsociado.getDefinitivo()) {
        tipoAsociacion = getTipoAsociacion(expAsociado);
        throw new InterruptedException(
            "El Expediente posee una " + tipoAsociacion + " sin Confirmar."
                + " Debe confirmar o cancelar esta " + tipoAsociacion + debeConfirmar);
      }
    }
  }

  private void tieneCaratulaVariableSinCompletar(Tarea task, String sistema)
      throws InterruptedException {

    boolean tieneCaratulaVariableSinCompletar = true;

    if (expedienteElectronico.getTrata().getAcronimoDocumento() == null) {

      tieneCaratulaVariableSinCompletar = false;
    }
    if (tieneCaratulaVariableSinCompletar) {

      expedienteElectronico.getTrata().getAcronimoDocumento();
      String estadoExpedienteElectronico = task.getEstado();

      Boolean tieneCaratulaVariable = false;
      for (DocumentoDTO d : expedienteElectronico.getDocumentos()) {
        if (d.getIdTransaccionFC() != null) {
          tieneCaratulaVariable = true;
        }
      }

      int cantDocs = expedienteElectronico.getDocumentos().size();
      if (cantDocs < 2) {
        estadoExpedienteElectronico = ConstantesWeb.ESTADO_PENDIENTE_INICIACION;
        tieneCaratulaVariable = false;
      }

      tieneCaratulaVariableSinCompletar = (estadoExpedienteElectronico
          .equals(ConstantesWeb.ESTADO_PENDIENTE_INICIACION)
          || (expedienteElectronico.getTrata().getAcronimoDocumento() != null
              && !tieneCaratulaVariable && cantDocs < 2))
          || (estadoExpedienteElectronico.equals(ConstantesWeb.ESTADO_INICIACION)
              && expedienteElectronico.getDocumentos().size() == 4
              && fueRehabilitado(expedienteElectronico.getId())
              && expedienteElectronico.getTrata().getAcronimoDocumento() != null
              && !tieneCaratulaVariable);
    }

    if (tieneCaratulaVariableSinCompletar) {
      String mensaje = Labels.getLabel(
          "ee.envio.sistemas.externos.advertencia.formularioControlado",
          new String[] { task.getCodigoExpediente(), sistema });
      logger.info(mensaje);
      throw new InterruptedException(mensaje);
    }
  }

  private boolean fueRehabilitado(Long idExpedienteElectronico) {

    List<HistorialOperacionDTO> hist = historialOperacionService
        .buscarHistorialporExpediente(idExpedienteElectronico);
    if (hist != null) {
      for (HistorialOperacionDTO h : hist) {
        if (ConstantesWeb.ESTADO_GUARDA_TEMPORAL.equals(h.getEstado())) {
          return true;
        }
      }
    }
    return false;
  }

  private void verificarTramitacionConjunta(Tarea task) throws InterruptedException {
 

    if (tramitacionConjuntaService
        .esExpedienteEnProcesoDeTramitacionConjunta(task.getCodigoExpediente())) {
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(task.getCodigoExpediente());
      String mensaje = "El expediente " + task.getCodigoExpediente()
          + " se encuentra en proceso de Tramitación Conjunta. "
          + "Por favor, desvincule el mismo del expediente " + codigoExpedienteElectronicoCabecera
          + " si desea enviarlo a AFJG.";
      logger.info(mensaje);
      throw new InterruptedException(mensaje);
    }

  }

  private void verificarTramitacionParalelo(Tarea task) throws InterruptedException {

    if (ConstantesWeb.ESTADO_PARALELO.equals(task.getEstado()) || tieneTareasEnParalelo(task)) {
      String mensaje = Labels.getLabel("ee.envio.sistemas.externos.advertenciaPendientes",
          new String[] { "Tramitación en Paralelo" });
      logger.info(mensaje);
      throw new InterruptedException(mensaje);
    }

  }

  private boolean tieneTareasEnParalelo(Tarea task) {
 
    TareaParaleloDTO tareaParalelo = tareaParaleloService
        .buscarTareaEnParaleloByIdTask(task.getTask().getExecutionId());
    if (tareaParalelo != null) {
      return true;
    }
    return false;

  }

  private void verificarExpedienteFusion(Tarea task) throws InterruptedException {

    String codigoExpedienteElectronico = task.getCodigoExpediente();
 
 
    if ((codigoExpedienteElectronico != null)
        && fusionService.esExpedienteEnProcesoDeFusion(codigoExpedienteElectronico)) {
      // se usa el servicio de tc porque devuelve la caratula del expediente
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);

      throw new InterruptedException(
          "El expediente " + codigoExpedienteElectronico + " se encuentra en proceso de fusión. "
              + "Por favor, desvincule el mismo del expediente "
              + codigoExpedienteElectronicoCabecera + " si desea enviarlo a AFJG.");
    }
  }

  public void verificarActividadesPendientesDeResolucion(Tarea task) throws InterruptedException {

    List<String> workIds = new ArrayList<>();
    workIds.add(task.getTask().getExecutionId()); 

    if (!actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty()) {
      String error = "Debe resolver las Actividades pendientes del Expediente antes de enviar a AFJG.";
      logger.info(error);
      throw new InterruptedException(error);
    }
  }

  public void resolverDocumentosNoDefinitivos() throws NegocioException {

    String docsSinConfirmar = "";
    boolean tieneDocumentosPendientes = false;
    for (DocumentoDTO doc : expedienteElectronico.getDocumentos()) {
      if (!doc.getDefinitivo()) {
        tieneDocumentosPendientes = true;
        break;
      }
    }
    if (tieneDocumentosPendientes)
      docsSinConfirmar = "documentos";

    for (ArchivoDeTrabajoDTO arch : expedienteElectronico.getArchivosDeTrabajo()) {
      if (!arch.isDefinitivo()) {
        docsSinConfirmar = docsSinConfirmar.equals("") ? "archivos de trabajo"
            : docsSinConfirmar + " y archivos de trabajo";
        tieneDocumentosPendientes = true;
        break;
      }
    }
    if (tieneDocumentosPendientes) {
      throw new NegocioException(docsSinConfirmar, null);
    }
  }

  public static void actualizarDocumentosNoDefinitivos(String codigoExpedienteElectronico)
      throws InterruptedException { 

    ExpedienteElectronicoDTO expedienteElectronico;
    try {
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);
    } catch (ParametroIncorrectoException e) {

      throw new InterruptedException(
          "Ocurrió un error al guardar el Expediente: " + codigoExpedienteElectronico);
    }

    for (DocumentoDTO doc : expedienteElectronico.getDocumentos()) {
      if (!doc.getDefinitivo()) {
        doc.setDefinitivo(true);
      }
    }
    for (ArchivoDeTrabajoDTO arch : expedienteElectronico.getArchivosDeTrabajo()) {
      if (!arch.isDefinitivo()) {
        arch.setDefinitivo(true);
      }
    }

    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public ExpedienteElectronicoDTO validarExpediente(Tarea task) throws InterruptedException {

    String codigoExpedienteElectronico = task.getCodigoExpediente();
    ExpedienteElectronicoDTO expedienteElectronico = null;
    String debeConfirmar = " antes de enviar el expediente al Sistema Externo.";
    try {
      
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);

    } catch (Exception e) {
      logger.error("error en metodo validarExpediente()", e);
      throw new InterruptedException("No se encontro el expediente.");
    }

    this.setExpedienteElectronico(expedienteElectronico);
    tieneCaratulaVariableSinCompletar(task, SISTEMAS_EXTERNOS);

    String tipoAsociacion;
    for (ExpedienteAsociadoEntDTO expAsociado : expedienteElectronico
        .getListaExpedientesAsociados()) {
      if (!expAsociado.getDefinitivo()) {
        tipoAsociacion = getTipoAsociacion(expAsociado);
        throw new InterruptedException(
            "El Expediente posee una " + tipoAsociacion + " sin Confirmar."
                + " Debe confirmar o desvincular esta " + tipoAsociacion + debeConfirmar);
      }
    }

    String codigoExpedienteElectronicoCabecera;
 
    if ((codigoExpedienteElectronico != null) && tramitacionConjuntaService
        .esExpedienteEnProcesoDeTramitacionConjunta(codigoExpedienteElectronico)) {
      codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      throw new InterruptedException(Labels.getLabel("ee.inbox.error.tc.enviarSistemaExterno",
          new String[] { codigoExpedienteElectronico, codigoExpedienteElectronicoCabecera }));
    }
 
    if ((codigoExpedienteElectronico != null)
        && fusionService.esExpedienteEnProcesoDeFusion(codigoExpedienteElectronico)) {
      // se usa el servicio de tc porque devuelve la caratula del expediente
      codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      throw new InterruptedException(Labels.getLabel("ee.inbox.error.fusion.enviarSistemaExterno",
          new String[] { codigoExpedienteElectronico, codigoExpedienteElectronicoCabecera }));
    }

    for (DocumentoDTO doc : expedienteElectronico.getDocumentos()) {
      if (!doc.getDefinitivo()) {
        throw new InterruptedException("Existen Documentos asociados no definitivos. "
            + "Debe cancelarlos o hacer la vinculación definitiva" + debeConfirmar);
      }
    }

    for (ArchivoDeTrabajoDTO archi : expedienteElectronico.getArchivosDeTrabajo()) {
      if (!archi.isDefinitivo()) {
        throw new InterruptedException("Existen Archivos de trabajo asociados no definitivos. "
            + "Debe cancelarlos o hacer la vinculación definitiva" + debeConfirmar);
      }
    } 

    boolean encontroAct = actividadService.tieneActividades(expedienteElectronico);

    if (encontroAct) {
      throw new InterruptedException("El expediente tiene Actividades pendientes."
          + " Debe cancelar o completarlas" + debeConfirmar);
    } 

    List<TareaParaleloDTO> tareasParalelo = tareaParaleloService
        .buscarTareasPendientesByExpediente(expedienteElectronico.getId());
    if (tareasParalelo != null && tareasParalelo.size() > 0) {
      throw new InterruptedException("El expediente posee tareas en paralelo."
          + " No se puede enviar el expediente a Sistemas Externos.");
    }

    return expedienteElectronico;
  }

  private String getTipoAsociacion(ExpedienteAsociadoEntDTO expAsociado) {

    String tipoAsociacion = "Asociación";
    if (expAsociado.getEsExpedienteAsociadoFusion() != null) {
      tipoAsociacion = "Fusión";
    }
    ;
    if (expAsociado.getEsExpedienteAsociadoTC() != null) {
      tipoAsociacion = "Tramitación Conjunta";
    }
    return tipoAsociacion;
  }

}