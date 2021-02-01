package com.egoveris.vucfront.ws.service.impl;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.DocumentoEstadoDTO;
import com.egoveris.vucfront.model.model.EstadoTareaDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.TareaDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.egoveris.vucfront.model.service.TareaService;
import com.egoveris.vucfront.model.util.DocumentoEstadoEnum;
import com.egoveris.vucfront.model.util.EstadoTareaEnum;
import com.egoveris.vucfront.ws.model.NuevaTareaRequest;
import com.egoveris.vucfront.ws.service.ExternalTareaService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalTareaServiceImpl implements ExternalTareaService {

  @Autowired
  private DocumentoService documentoService;
  @Autowired
  private ExpedienteService expedienteService;
  @Autowired
  private TareaService tareaService;

  @Override
  public void nuevaTareaSubsanacionRequest(NuevaTareaRequest request) {
    // Prepare all the needed data for the DTO
    Date fechaActual = new Date();
    // Estado Tarea
    EstadoTareaDTO estado = new EstadoTareaDTO(EstadoTareaEnum.PENDIENTE);
    // ExpedienteBase
    ExpedienteBaseDTO expediente = expedienteService
        .getExpedienteBaseByCodigo(request.getCodigoExpediente());
    // Tipos Documento a subsanar
    List<TipoDocumentoDTO> tiposDoc = new ArrayList<>();
    for (String acronimoGedo : request.getAcronimosTadDocumentosASubir()) {
      TipoDocumentoDTO result = documentoService.getTipoDocumentoByAcronimoGedo(acronimoGedo);
      if (result != null) {
        tiposDoc.add(result);
        setEstadoDocumento(expediente, result);
      }
    }

    // Let's fill the TareaDTO!
    TareaDTO nuevaTarea = new TareaDTO();
    nuevaTarea.setFecha(fechaActual);
    nuevaTarea.setEstado(estado);
    nuevaTarea.setExpedienteBase(expediente);
    nuevaTarea.setMotivo(request.getMotivo());
    nuevaTarea.setEnviadoPor(request.getReparticionSectorGenerador());
    nuevaTarea.setBajaLogica(false);
    nuevaTarea.setIdInterviniente(null);
    nuevaTarea.setTipo(request.getTipoTarea());
    nuevaTarea.setCuitDestino(null);
    nuevaTarea.setTipoDocumentos(tiposDoc);
    
    
    /* TOD
     * Agregar el registro del documentoEstado pendiente de subsanacion
     * 
     * */
    
    //

    tareaService.saveTarea(nuevaTarea);

  }
  private void setEstadoDocumento (ExpedienteBaseDTO expediente, TipoDocumentoDTO tipoDocumento) {
	  for (DocumentoDTO documentoDTO : expediente.getDocumentosList()) {
		if (documentoDTO.getTipoDocumento().getId()==tipoDocumento.getId()) {
			if (documentoDTO.getDocumentoEstados() == null) {
				documentoDTO.setDocumentoEstados(new ArrayList<DocumentoEstadoDTO>());
			}
			DocumentoEstadoDTO estado = new DocumentoEstadoDTO();
			estado.setDocumento(documentoDTO);
			estado.setFecha(new Date());
			estado.setEstado(DocumentoEstadoEnum.PENDIENTESUBSANACION.getEstado());
			documentoDTO.getDocumentoEstados().add(estado);
			documentoService.saveDocument(documentoDTO);
		}
	}
  }

}