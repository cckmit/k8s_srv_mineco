package com.egoveris.deo.base.decision;

import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.ConfiguracionTimerJBPM;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class DecisionReintentoCierre implements DecisionHandler {

  private static transient Logger logger = LoggerFactory.getLogger(DecisionReintentoCierre.class);

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private AvisoService avisoService;
  @Autowired
  private ConfiguracionTimerJBPM configReintentosCierre;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;

  /**
   * Este método decide, sí después de ocurrido un error en un cierre, se debe
   * reintentar dicha tarea. Si la variable reintentoHabilitado es False, no se
   * realiza ningún intento y se envía el aviso con el error, de lo contrario se
   * tendrán en cuenta los valores de las variables configuradas en el archivo
   * timer-jbpm-config.xml.
   * 
   * 
   */
  public String decide(OpenExecution execution) {
    String transicion;
    String timeoutCierre;
    String executionId = execution.getId();
    Map<String, String> datos = new HashMap<>();
    Integer numeroIntentos = (Integer) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_NUMERO_REINTENTOS_CIERRE);

    logger
        .debug(
            "Proceso  " + executionId
                + " con Motivo: -- " + (String) this.processEngine.getExecutionService()
                    .getVariable(executionId, Constantes.VAR_MOTIVO)
                + "-- En reintento para Cierre");

    Integer cantidadReintentosMaxima = this.configReintentosCierre.getCantidadReintentos();
    Boolean reintentoHabilitado = (Boolean) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_REINTENTO_HABILITADO);

    /*
     * Verifico si se habilitaron reintentos para esta solicitud. Sino, digo que
     * la Maxima es null y eso anula el proceso
     */
    if (reintentoHabilitado != null && !reintentoHabilitado) {
      cantidadReintentosMaxima = null;
    }

    /*
     * Si la cantidad de reintentos hasta el momento es null, entonces lo
     * transformo a numerico con 0
     */
    if (numeroIntentos == null)
      numeroIntentos = 0;
    if ((cantidadReintentosMaxima != null)
        && (cantidadReintentosMaxima > numeroIntentos || cantidadReintentosMaxima == -1)) {

      Long proximaEjecucion = (long) (50 * Math.pow(numeroIntentos, 3));
      Long shift = (long) (proximaEjecucion * (new Random().nextFloat() / 8));
      Integer segundosParaReintento = (int) (proximaEjecucion + shift);

      /*
       * A duedate expression has the following syntax:
       * 
       * [<Base Date> {+|-}] quantity [business] {second | seconds | minute |
       * minutes | hour | hours | day | days | week | weeks | month | months |
       * year | years}
       * 
       * http://docs.jboss.com/jbpm/v4/devguide/html_single/#duedateexpressions
       */

      timeoutCierre = segundosParaReintento + " seconds";

      this.processEngine.getExecutionService().setVariable(executionId,
          Constantes.VAR_TIMEOUT_REINTENTOS_CIERRE, timeoutCierre);
      this.processEngine.getExecutionService().setVariable(executionId,
          Constantes.VAR_NUMERO_REINTENTOS_CIERRE, ++numeroIntentos);
      logger.debug("Va a cierre documento  " + execution.getId());
      transicion = Constantes.TRANSICION_ESPERA_REINTENTO_CIERRE;

    } else {

      datos.put("motivo", (String) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_MENSAJE_USUARIO));
      datos.put("referencia", (String) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_MOTIVO));
      String usuario = (String) this.processEngine.getExecutionService().getVariable(executionId,
          Constantes.VAR_USUARIO_FIRMANTE);
      int idTipoDocumento = Integer.valueOf((String) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_TIPO_DOCUMENTO));
      TipoDocumentoDTO tipoDocumentoGedo = tipoDocumentoService
          .buscarTipoDocumentoPorId(idTipoDocumento);
      List<String> receptoresAviso = (List<String>) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_RECEPTORES_AVISO_FIRMA);
      if (tipoDocumentoGedo.getEsFirmaConjunta()) {
        List<Usuario> usuarios = this.firmaConjuntaService.buscarFirmantesPorProceso(executionId);
        for (Usuario datosUsuarioBean : usuarios) {
          if (!receptoresAviso.contains(datosUsuarioBean.getUsername()))
            receptoresAviso.add(datosUsuarioBean.getUsername());
        }
      }
      receptoresAviso = (List<String>) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_RECEPTORES_AVISO_FIRMA);
      if (receptoresAviso != null && !receptoresAviso.isEmpty())
        avisoService.guardarAvisosFalla(receptoresAviso, datos, usuario);
      transicion = Constantes.TRANSICION_ERROR_CIERRE;
    }
    logger.debug("Reintento número:  " + numeroIntentos);
    return transicion;
  }
}
