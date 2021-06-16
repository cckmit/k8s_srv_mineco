package com.egoveris.deo.base.task;

import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.springframework.beans.factory.annotation.Autowired;

public class RechazarDocumento implements ExternalActivityBehaviour {

  private static final long serialVersionUID = 1L;

  @Autowired
  private transient ProcessEngine processEngine;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private AvisoService avisoService;
  @Autowired
  private IUsuarioService usuarioService;

  public void signal(ActivityExecution execution, String signalName, Map<String, ?> parameters)
      throws Exception {
    execution.take(signalName);
  }

  @SuppressWarnings("unchecked")
  public void execute(ActivityExecution execution) throws Exception {
    String executionId = execution.getId();
    int idTipoDocumento = Integer.parseInt((String) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_TIPO_DOCUMENTO));
    TipoDocumentoDTO tipoDocumentoGedo = tipoDocumentoService
        .buscarTipoDocumentoPorId(idTipoDocumento);
    String usuarioDerivador = (String) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_USUARIO_DERIVADOR);
    String usuarioRechazo = (String) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_USUARIO_FIRMANTE);
    this.processEngine.getExecutionService().setVariable(executionId,
        Constantes.VAR_USUARIO_REVISOR, usuarioDerivador);
    this.processEngine.getExecutionService().setVariable(executionId,
        Constantes.VAR_USUARIO_DERIVADOR, usuarioRechazo);
    this.processEngine.getExecutionService().setVariable(executionId,
        Constantes.VAR_TAREA_RECHAZO_DOCUMENTO, Constantes.TRANSICION_RECHAZADO);

    List<String> receptoresAviso = (List<String>) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_RECEPTORES_AVISO_FIRMA);

    if (!receptoresAviso.contains(usuarioDerivador))
      receptoresAviso.add(usuarioDerivador);

    String usuarioApoderador;
    // Adicionar al listado de avisos el usuario apoderador, si éste existiera.
    if (this.usuarioService.licenciaActiva(usuarioDerivador, new Date())) {
      usuarioApoderador = this.usuarioService.obtenerUsuario(usuarioDerivador).getApoderado();
    } else {
      usuarioApoderador = usuarioDerivador;
    }
    if (usuarioApoderador != null && !receptoresAviso.contains(usuarioApoderador))
      receptoresAviso.add(usuarioApoderador);

    // Si el tipo de documento exige firma conjunta se adicionan todos los
    // firmantes para que reciban el aviso
    // de RECHAZO.
    if (tipoDocumentoGedo.getEsFirmaConjunta()) {
      List<Usuario> usuarios = this.firmaConjuntaService.buscarFirmantesPorEstado(executionId,
          true);
      for (Usuario usuario : usuarios) {
        if (!receptoresAviso.contains(usuario.getUsername()))
          receptoresAviso.add(usuario.getUsername());
      }

      List<Usuario> usuariosRevisores = this.firmaConjuntaService
          .buscarRevisoresPorEstado(executionId, true);

      for (Usuario usuarioRevisor : usuariosRevisores) {
        if (!receptoresAviso.contains(usuarioRevisor.getUsername()))
          receptoresAviso.add(usuarioRevisor.getUsername());
      }
      // Actualizar estado de la lista de firmantes, solo si es template.
      this.firmaConjuntaService.actualizarEstadoFirmantes(executionId, false);
      this.firmaConjuntaService.actualizarEstadoRevisores(executionId, false);
    }
    Map<String, String> datos = new HashMap<String, String>();
    datos.put("motivo", (String) this.processEngine.getExecutionService().getVariable(executionId,
        Constantes.VAR_MOTIVO_RECHAZO));
    datos.put("referencia", (String) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_MOTIVO));
    this.avisoService.guardarAvisosRechazo(receptoresAviso, datos, usuarioRechazo);
    this.processEngine.getExecutionService().signalExecutionById(executionId,
        Constantes.TRANSICION_RECHAZO_TEMPLATE);
  }
}
