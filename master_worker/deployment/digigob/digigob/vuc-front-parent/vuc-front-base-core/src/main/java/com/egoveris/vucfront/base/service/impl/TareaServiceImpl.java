package com.egoveris.vucfront.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.model.model.ResolucionSubsDocRequest;
import com.egoveris.te.model.model.ResolucionSubsRequest;
import com.egoveris.te.ws.service.IActividadExternalService;
import com.egoveris.vucfront.base.model.EstadoTarea;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.model.Tarea;
import com.egoveris.vucfront.base.repository.TareaRepository;
import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.base.util.SistemaEnum;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.EstadoTareaDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TareaDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.TareaService;
import com.egoveris.vucfront.model.util.EstadoTareaEnum;

@Service
@Transactional
public class TareaServiceImpl implements TareaService {
  @Autowired
  @Qualifier("vucMapper")
  private Mapper mapper;

  @Autowired
  private IActividadExternalService actividadExternalService;
  @Autowired
  private TareaRepository tareaRepository;
  
  @Autowired
  private DocumentoService documentoService;
  @Autowired
  private WebDavService webDavService;
  @Autowired
  private IExternalGenerarDocumentoService externalGenerarDocumentoService;

  private static final Logger LOG = LoggerFactory.getLogger(TareaServiceImpl.class);
  @Override
  public void saveTarea(TareaDTO tarea) {
    if (tarea != null) {
      tareaRepository.save(mapper.map(tarea, Tarea.class));
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaDTO> getTareasPendientesByPersona(PersonaDTO persona) {
    List<TareaDTO> retorno = new ArrayList<>();
    EstadoTareaDTO estado = new EstadoTareaDTO(EstadoTareaEnum.PENDIENTE);

    List<Tarea> result = tareaRepository.findByEstadoAndExpedienteBase_PersonaOrderByFechaDesc(
        mapper.map(estado, EstadoTarea.class), mapper.map(persona, Persona.class));
    if (result != null) {
      retorno = ListMapper.mapList(result, mapper, TareaDTO.class);
    }
    return retorno;
  }

  @Override
  public void setTareaAsViewed(TareaDTO tarea) {
    tarea.setNotificado(true);
    Tarea updatedtarea = mapper.map(tarea, Tarea.class);
    tareaRepository.save(updatedtarea);
  }

  @Override
  public Integer getUnseenTareas(PersonaDTO persona) {
    Integer unseenTareas = 0;
    List<TareaDTO> result = this.getTareasPendientesByPersona(persona);
    for (TareaDTO aux : result) {
      if (!aux.getNotificado()) {
        unseenTareas++;
      }
    }
    if (unseenTareas > 0) {
      return unseenTareas;
    }

    return null;
  }

  @Override
  public void confirmarTareaSubsanacion(ExpedienteBaseDTO expediente, List<DocumentoDTO> listDocSubsanados) {

    ResolucionSubsDocRequest docRequest;
    List<ResolucionSubsDocRequest> listaDocRequest = new ArrayList<>();
    List<DocumentoDTO>  listDocumentoGeneradoGedo = new ArrayList<> ();
    
    listDocumentoGeneradoGedo.addAll(generaDocumentosDeoDocumentosSubidos(listDocSubsanados, expediente));
    listDocumentoGeneradoGedo.addAll(addFfddDocuments(expediente, listDocSubsanados));
    for (DocumentoDTO doc : listDocumentoGeneradoGedo) {
      docRequest = new ResolucionSubsDocRequest();
      docRequest.setNumeroDocumento(doc.getCodigoSade());
      docRequest.setTipoDocTad(doc.getTipoDocumento().getAcronimoGedo());
      listaDocRequest.add(docRequest);
    }

    ResolucionSubsRequest request = new ResolucionSubsRequest();
    request.setNumeroExpediente(expediente.getCodigoSade());
    request.setListDocAct(listaDocRequest);

    actividadExternalService.generarActividadSubsanacion(request);
  }
  
  
  /**
	 * Envia a DEO los archivos que fueron subidos a WebDav en el step 2 y los
	 * asocia al expediente en VUC.
	 * 
	 * @param expediente
	 */
	private List<DocumentoDTO> generaDocumentosDeoDocumentosSubidos(List<DocumentoDTO> listDocSubsanados, ExpedienteBaseDTO expediente) {
		LOG.info("==> generaDocumentosDeoArchivosSubidos");

		
		List<DocumentoDTO> addedDocuments = new ArrayList<>();

		for (DocumentoDTO docExpediente : listDocSubsanados) {
			// Los documentos que tienen Url Temporal están en el WebDav
			if (docExpediente.getArchivo() != null && docExpediente.getArchivo().length >0 ) {
				
				// Los envia a GEDO para su creación
				DocumentoDTO deoDocument = documentoService.generateDocumentoGedo(docExpediente,
						expediente.getTipoTramite().getUsuarioIniciador(), expediente.getPersona());

				// Reemplaza el documento del expediente por el de DEO
				addedDocuments.add(deoDocument);
				
			}
		}
		return addedDocuments;
	}

	private List<DocumentoDTO> addFfddDocuments(ExpedienteBaseDTO expediente, List<DocumentoDTO> listDocSubsanados) {
		LOG.info("==> adding FFDD documents");
		if (!listDocSubsanados.isEmpty()) {
			List<DocumentoDTO> newDocumentList = new ArrayList<>();
			for (DocumentoDTO aux : listDocSubsanados) {
				if (aux.getIdTransaccion() != null) {
					DocumentoDTO deoDocument = documentoService.generateDocumentoGedo(aux,
							expediente.getTipoTramite().getUsuarioIniciador(), expediente.getPersona());
					newDocumentList.add(deoDocument);
				}
			}
			return newDocumentList;
		}
		return new ArrayList<>();
	}
	// INI - En documentos con formato template no viene usuario
	private String validaUsuario(ExpedienteBaseDTO expediente, String usuarioIniciador) {
		String usuario;
		if (expediente.getTipoTramite().getUsuarioIniciador() != null) {
			usuario = expediente.getTipoTramite().getUsuarioIniciador();
		} else {
			usuario = usuarioIniciador;
		}
		return usuario;
	}
}