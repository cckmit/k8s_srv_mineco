package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametroActividadDTO;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.te.base.model.TipoActividadDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActivSubsanacionService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.vucfront.model.exception.ValidacionException;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.NuevaTareaRequest;
import com.egoveris.vucfront.ws.service.DocumentosClient;
import com.egoveris.vucfront.ws.service.ExternalDocumentoService;
import com.egoveris.vucfront.ws.service.ExternalTareaService;
import com.egoveris.vucfront.ws.service.TipoDocumentosClient;

@Service
@Transactional
public class ActivSubsanacionServiceImpl implements IActivSubsanacionService {

  private static final String TIPADO_LIBRE = "LIBRE";
  @Autowired
  private IActividadService actividadService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private DocumentoGedoService documentoGedoService;
  @Autowired
  private ExternalTareaService externalTareaService;
  @Autowired
  private INotificacionEEService notificacionService;
  @Autowired
  private IActividadExpedienteService actividadExpedienteService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private ExternalDocumentoService externalDocumentoService;
  @Autowired
  private DocumentosClient documentoVucService;
  @Autowired
  private TipoDocumentosClient tipoDocumentoVucService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  private DozerBeanMapper mapper = new DozerBeanMapper();
  private List<TipoDocumentoDTO> tipoDocumentos;

  private final static Logger logger = LoggerFactory.getLogger(ActivSubsanacionServiceImpl.class);

  // init-method del bean
  public void init() {
    if (logger.isDebugEnabled()) {
      logger.debug("init() - start");
    }

    try {
      setTipoDocumentos();
    } catch (Exception e) {
      logger.error("Error al inicializar la lista de todos los tipos de documento TAD", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("init() - end");
    }
  }

	@Override
	public List<ExternalDocumentoVucDTO> getAllTipoDocumentosTad() throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllTipoDocumentosTad() - start");
		}

		if (this.tipoDocumentos == null) {
			try {
				setTipoDocumentos();
			} catch (NegocioException e) {
				logger.error("getAllTipoDocumentosTad()", e);

				throw new ServiceException("Error al obtener tipos de documento TAD", e);
			}
		}
		synchronized (this.tipoDocumentos) {
			List<ExternalDocumentoVucDTO> returnList = convertList(tipoDocumentos);
			if (logger.isDebugEnabled()) {
				logger.debug("getAllTipoDocumentosTad() - end - return value={}", returnList);
			}
			return returnList;
		}
	}

	public List<ExternalDocumentoVucDTO> getTipoDocumentosTadSoportados() throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentosTadSoportados() - start");
		}

		if (this.tipoDocumentos == null) {
			try {
				setTipoDocumentos();
			} catch (NegocioException e) {
				logger.error("getTipoDocumentosTadSoportados()", e);

				throw new ServiceException("Error al obtener tipos de documento TAD", e);
			}
		}
		
		List<ExternalDocumentoVucDTO> returnList = convertList(this.tipoDocumentos);
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentosTadSoportados() - end - return value={}", returnList);
		}
		return returnList;
	}

  @Override
  public ExternalTipoDocumentoVucDTO getTipoDocVucByCodVuc(String acronimoVuc)
      throws ServiceException {

    return documentoVucService.getTipoDocumentoByAcronimoVuc(acronimoVuc);
  }

  @Override
  public List<ExternalDocumentoVucDTO> getDocumentosVucPorCodigoTrata(String codigoTrata)
      throws ServiceException, NoResultException {
    return documentoVucService.getDocumentosByCodigoTramite(codigoTrata);
  }

  @Override
  public List<ExternalDocumentoVucDTO> getTipoDocumentosVucParaExp(String codigoExp)
      throws ServiceException, NoResultException {
    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentosTadParaExp(codigoExp={}) - start", codigoExp);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    try {
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codigoExp);
    } catch (ParametroIncorrectoException e) {
      logger.error("getTipoDocumentosTadParaExp(String)", e);

      throw new ServiceException("Error al obtener tipos de documento TAD", e);
    }
    List<ExternalDocumentoVucDTO> returnList = getDocumentosVucPorCodigoTrata(
        expedienteElectronico.getTrata().getCodigoTrata());
    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentosTadParaExp(String) - end - return value={}", returnList);
    }
    return returnList;
  }

	private void setTipoDocumentos() throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("setTipoDocumentos() - start");
		}

		this.tipoDocumentos = tipoDocumentoVucService.getAllTipoDocumento();

		if (logger.isDebugEnabled()) {
			logger.debug("setTipoDocumentos() - end");
		}
	}

  @Override
  public void enviarSubsanacionExp(SolicitudSubs solicitudSubs) throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("enviarSubsanacionExp(solicitudSubs={}) - start", solicitudSubs);
    }

    ExpedienteElectronicoDTO ee = expedienteElectronicoService
        .buscarExpedienteElectronicoByIdTask(solicitudSubs.getWorkFlowId());

    logger.info("Inicio de subsanacion del expediente : " + ee.getCodigoCaratula());
    try {

      logger.debug("obteniendo datos del usuario");

      logger.debug("generando documento de subsanacion en gedo (invocacion remota)");
      // generar documento subsanacion -- SI FALLA DE ACA EN ADELANTE
      // QUEDA UN DOCUMENTO HUERFANO!
      DocumentoDTO documentoNuevo = documentoGedoService
          .generarDocumentoSubsanacion(solicitudSubs);
      documentoNuevo.setIdTask(ee.getIdWorkflow());

      ee.getDocumentos().add(documentoNuevo);
      ee.setEstado(ConstantesWeb.ESTADO_SUBSANACION);
      ee.setFechaModificacion(getActualDate());
      logger.debug("agregar el documento al expediente");

      logger.debug("generando tarea de subsanacion en tad (invocacion remota)");

      logger.debug("generando actividad solicitud de subs");
      generarActividadSolicitudSubs(solicitudSubs);
      
      // Aumentar num subsanacion
      if (ee.getCantidadSubsanar() == null) {
    	  ee.setCantidadSubsanar(1);
      } else {
    	  ee.setCantidadSubsanar((ee.getCantidadSubsanar())+1);
      }

      expedienteElectronicoService.modificarExpedienteElectronico(ee);

      // Para que no puedan devolver al buzon grupal
      processEngine.getExecutionService().setVariable(solicitudSubs.getWorkFlowId(), "tareaGrupal",
          "noEsTareaGrupal");
      processEngine.getExecutionService().setVariable(solicitudSubs.getWorkFlowId(),
          ConstantesWeb.USUARIO_SELECCIONADO, solicitudSubs.getUsuarioAlta());
      processEngine.getExecutionService().signalExecutionById(solicitudSubs.getWorkFlowId(),
          ConstantesWeb.ESTADO_SUBSANACION);

    } catch (Exception e) {
      logger.error("enviarSubsanacionExp(SolicitudSubs)", e);

      throw new ServiceException(e.getMessage(), e);
    }

    logger.info(
        "Finalizacion del proceso de subsanacion del expediente : " + ee.getCodigoCaratula());
  }

  // @Override
  public void enviarSubsanacionDocsConNombreExp(SolicitudSubs solicitudSubs)
      throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("0", solicitudSubs);
    }

    ExpedienteElectronicoDTO ee = expedienteElectronicoService
        .buscarExpedienteElectronicoByIdTask(solicitudSubs.getWorkFlowId());

    logger.info("Inicio de subsanacion del expediente : " + ee.getCodigoCaratula());
    try {

      logger.debug("obteniendo datos del usuario");

      logger.debug("generando documento de subsanacion en gedo (invocacion remota)");
      // generar documento subsanacion -- SI FALLA DE ACA EN ADELANTE
      // QUEDA UN DOCUMENTO HUERFANO!
      DocumentoDTO documentoNuevo = documentoGedoService
          .generarDocumentoSubsanacion(solicitudSubs);
      documentoNuevo.setIdTask(ee.getIdWorkflow());

      ee.getDocumentos().add(documentoNuevo);
      ee.setEstado(ConstantesWeb.ESTADO_SUBSANACION);
      ee.setFechaModificacion(getActualDate());
      logger.debug("agregar el documento al expediente");

      NuevaTareaRequest request = new NuevaTareaRequest();
      request.setCodigoExpediente(solicitudSubs.getNroExpediente());
      request.setMotivo(solicitudSubs.getMotivo());
      request.setTipoTarea(solicitudSubs.getTipo());
      request.setReparticionSectorGenerador(
          ee.getCodigoReparticionUsuario() + "-" + ee.getCodigoReparticionActuacion());
      request.setEsSubsanarFormulario(solicitudSubs.isFormulario());
      request.setEsSubsanarDocumentacion(solicitudSubs.isSubsDoc());
      request.setEsAgregarDocumentacion(solicitudSubs.isPedidoDoc());
      request.setAcronimosTadDocumentosASubir(solicitudSubs.getListaPedidoDocs());
      request.setEsSubsanarFormulario(true);

      if (solicitudSubs.getDestino() != null
          && solicitudSubs.getDestino().equals(ConstantesWeb.DESTINO_INTERVINIENTE)) {
        request.setEsSubsanacionInterviniente(true);
      } else {
        request.setEsSubsanacionInterviniente(false);
      }

      logger.debug("generando tarea de subsanacion en tad (invocacion remota)");
      logger.info("generando tarea de subsanacion en tad (invocacion remota)");
      actividadExpedienteService.nuevaTareaSubsanacionRequest(request);  
      
      logger.debug("generando actividad solicitud de subs");
      
      /*Se envia la notificaión a VUC*/
      List<DocumentoDTO> docNot = new ArrayList<>();
      docNot.add(documentoNuevo);
      this.enviarNotificacionVUC(ee.getUsuarioCreador(),ee, docNot,solicitudSubs.getMotivo());
      
      generarActividadSolicitudSubs(solicitudSubs);

      expedienteElectronicoService.modificarExpedienteElectronico(ee);

      // Para que no puedan devolver al buzon grupal
      // processEngine.getExecutionService().setVariable(solicitudSubs.getWorkFlowId(),
      // "tareaGrupal",
      // "noEsTareaGrupal");
      // processEngine.getExecutionService().setVariable(solicitudSubs.getWorkFlowId(),
      // Constantes.USUARIO_SELECCIONADO, solicitudSubs.getUsuarioAlta());
      // processEngine.getExecutionService().signalExecutionById(solicitudSubs.getWorkFlowId(),
      // Constantes.ESTADO_SUBSANACION);

    } catch (Exception e) {
      logger.error("enviarSubsanacionDocsConNombreExp(SolicitudSubs)", e);

      throw new ServiceException(e.getMessage(), e);
    }

    logger.info(
        "Finalizacion del proceso de subsanacion del expediente : " + ee.getCodigoCaratula());
  }

  private void generarActividadSolicitudSubs(SolicitudSubs request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSolicitudSubs(request={}) - start", request);
    }

    TipoActividadDTO activSub = actividadService
        .buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION);

    ActividadDTO act = new ActividadDTO();

    // Fecha alta
    Date date = getActualDate();

    // Estado
    String oculta = ConstEstadoActividad.ESTADO_PENDIENTE;

    act.setIdObjetivo(request.getWorkFlowId());
    act.setFechaAlta(date);
    act.setTipoActividad(activSub);
    act.setParametros(new HashMap<String, ParametroActividadDTO>());
    act.getParametros().put(ConstParamActividad.PARAM_TIPO_SUBS,
        new ParametroActividadDTO(request.getTipo()));
    act.getParametros().put(ConstParamActividad.PARAM_NRO_EXP,
        new ParametroActividadDTO(request.getNroExpediente()));
    act.getParametros().put(ConstParamActividad.PARAM_MOTIVO_EE,
        new ParametroActividadDTO(request.getMotivo()));
    act.getParametros().put(ConstParamActividad.PARAM_FORMULARIO,
        new ParametroActividadDTO(String.valueOf(request.isFormulario())));
    act.getParametros().put(ConstParamActividad.PARAM_SUBS_DOC,
        new ParametroActividadDTO(String.valueOf(request.isSubsDoc())));
    act.getParametros().put(ConstParamActividad.PARAM_PEDIDO_DOC,
        new ParametroActividadDTO(String.valueOf(request.isPedidoDoc())));
    act.getParametros().put(ConstParamActividad.PARAM_TRATA,
        new ParametroActividadDTO(request.getCodigoTrata()));
    if (!request.getListaPedidoDocs().isEmpty()) {
      act.getParametros().put(ConstParamActividad.PARAM_LISTA_SUBS_DOC,
          new ParametroActividadDTO(StringUtils.join(request.getListaPedidoDocs(), ',')));
    }

    if (request.getDestino() != null) {
      act.getParametros().put(ConstParamActividad.PARAM_DESTINO,
          new ParametroActividadDTO(request.getDestino()));
    }
    act.setUsuarioAlta(request.getUsuarioAlta());
    act.setEstado(oculta);

    actividadService.generarActividad(act);

    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSolicitudSubs(SolicitudSubs) - end");
    }
  }

  @Override
  public SolicitudSubs buscarActividadSolicitudSubs(Long idAct) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadSolicitudSubs(idAct={}) - start", idAct);
    }

    SolicitudSubs result = new SolicitudSubs();
    ActividadDTO act = actividadService.buscarActividad(idAct);

    if (act.getParametros().get(ConstParamActividad.PARAM_TIPO_SUBS) != null)
      result.setTipo(act.getParametros().get(ConstParamActividad.PARAM_TIPO_SUBS).toString());
    if (act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP) != null)
      result
          .setNroExpediente(act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).toString());
    if (act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_EE) != null)
      result.setMotivo(act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_EE).toString());
    if (act.getParametros().get(ConstParamActividad.PARAM_FORMULARIO) != null)
      result.setFormulario(Boolean
          .valueOf(act.getParametros().get(ConstParamActividad.PARAM_FORMULARIO).toString()));
    if (act.getParametros().get(ConstParamActividad.PARAM_SUBS_DOC) != null)
      result.setSubsDoc(
          Boolean.valueOf(act.getParametros().get(ConstParamActividad.PARAM_SUBS_DOC).toString()));
    if (act.getParametros().get(ConstParamActividad.PARAM_PEDIDO_DOC) != null)
      result.setPedidoDoc(Boolean
          .valueOf(act.getParametros().get(ConstParamActividad.PARAM_PEDIDO_DOC).toString()));
    if (act.getParametros().get(ConstParamActividad.PARAM_LISTA_SUBS_DOC) != null) {
      List<String> list1 = new ArrayList<>(Arrays.asList(act.getParametros()
          .get(ConstParamActividad.PARAM_LISTA_SUBS_DOC).toString().split(",")));
      result.setListaSubsDocs(list1);
    }
    if (act.getParametros().get(ConstParamActividad.PARAM_LISTA_PEDIDO_DOC) != null) {
      List<String> list2 = new ArrayList<String>(Arrays.asList(act.getParametros()
          .get(ConstParamActividad.PARAM_LISTA_PEDIDO_DOC).toString().split(",")));
      result.setListaPedidoDocs(list2);
    }
    if (act.getParametros().get(ConstParamActividad.PARAM_DESTINO) != null)
      result.setDestino(act.getParametros().get(ConstParamActividad.PARAM_DESTINO).toString());

    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadSolicitudSubs(int) - end - return value={}", result);
    }
    return result;
  }

  private Date getActualDate() {
    if (logger.isDebugEnabled()) {
      logger.debug("getActualDate() - start");
    }

    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();

    if (logger.isDebugEnabled()) {
      logger.debug("getActualDate() - end - return value={}", date);
    }
    return date;
  }

  private List<String> borrarDocDuplicados(List<String> lista1, List<String> lista2) {
    if (logger.isDebugEnabled()) {
      logger.debug("borrarDocDuplicados(lista1={}, lista2={}) - start", lista1, lista2);
    }

    List<String> result = new ArrayList<String>();
    result.addAll(lista1);

    for (String string2 : lista2) {
      boolean duplicado = false;
      for (String string1 : lista1) {
        if (string1.equals(string2)) {
          duplicado = true;
          break;
        }
      }
      if (!duplicado) {
        result.add(string2);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("borrarDocDuplicados(List<String>, List<String>) - end - return value={}",
          result);
    }
    return result;
  }
  
  private void enviarNotificacionVUC(String usuario, ExpedienteElectronicoDTO ee,List<DocumentoDTO>listaDocumento ,String motivo) {
	  
	  logger.info("generando notificacion en VUC (invocacion remota)");
	  try {
//		  com.egoveris.vucfront.model.model.DocumentoDTO doc = new com.egoveris.vucfront.model.model.DocumentoDTO();
//		  List <String>codSade = this.obtenerDesgloseCodigoSADE(codigoExpedinte);
//		  doc.setActuacion(codSade.get(0));
//		  doc.setAnio(Long.valueOf(codSade.get(1)));
//		  doc.setNumero(Long.valueOf(codSade.get(2)));
//		  doc.setReparticion(codSade.get(4));
		  
		  /*
		   * Se manda el usuario creador del expediente, queda revisar este tema, porque en el metodo altaNotificacionVUC, 
		   * llamado desde la pantalla se envia el usuario logeado de TE
		   * 
		   * */
		  getNotificacionService().altaNotificacionVUC(usuario, ee, listaDocumento, motivo);
		  logger.info("notificacion enviada a VUC (invocacion remota)");
	  }catch (Exception e) {
		  logger.error("error al enviar la notificación a VUC (invoción remota)",e);
		  throw new ServiceException(e.getMessage(), e);
	  }
	  
  }
  private List<String> obtenerDesgloseCodigoSADE(String strCodigoSADE)
	      throws ValidacionException {

	    List<String> listDesgloseCodigoRegistro = new ArrayList<>();

	    if (StringUtils.isBlank(strCodigoSADE)) {
	      throw new ValidacionException("El código de registro es nulo.");
	    }
	    StringTokenizer strTokenizer = new StringTokenizer(strCodigoSADE, "-");

	    int i = 0;
	    while (strTokenizer.hasMoreElements()) {
	      if (i != 3) {
	        listDesgloseCodigoRegistro.add(i, (String) strTokenizer.nextElement());
	      } else {
	        String posibleSecuencia = (String) strTokenizer.nextElement();
	        if ("".equals(posibleSecuencia.trim())) {
	          i--;
	        } else {
	          listDesgloseCodigoRegistro.add(i, posibleSecuencia);
	        }
	      }
	      i++;
	    }
	    return listDesgloseCodigoRegistro;
	  }

	private List<ExternalDocumentoVucDTO> convertList(List<TipoDocumentoDTO> lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertList(lista={}) - start", lista);
		}

		List<ExternalDocumentoVucDTO> docs = new ArrayList<>();

		for (TipoDocumentoDTO tipoDocumentoDTO : lista) {
			ExternalDocumentoVucDTO tad = convToDocTad(tipoDocumentoDTO);
			docs.add(tad);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertList(List<TipoDocumentoDTO>) - end - return value={}", docs);
		}
		return docs;
	}

	private ExternalDocumentoVucDTO convToDocTad(TipoDocumentoDTO tipoDocumentoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("convToDocTad(tipoDocumentoDTO={}) - start", tipoDocumentoDTO);
		}
		ExternalTipoDocumentoVucDTO tadTipoDoc = new ExternalTipoDocumentoVucDTO();
		ExternalDocumentoVucDTO tad = new ExternalDocumentoVucDTO();
		tadTipoDoc.setAcronimoTad(tipoDocumentoDTO.getAcronimoTad());
		tadTipoDoc.setDescripcion(tipoDocumentoDTO.getDescripcion());
		tadTipoDoc.setAcronimoGedo(tipoDocumentoDTO.getAcronimoGedo());
		tadTipoDoc.setNombre(tipoDocumentoDTO.getNombre());
		tad.setTipoDocumento(tadTipoDoc);

		if (logger.isDebugEnabled()) {
			logger.debug("convToDocTad(TipoDocumentoDTO) - end - return value={}", tad);
		}
		return tad;
	}

  // public IDocumentoService getDocumentoService() {
  // return documentoService;
  // }
  //
  // public void setDocumentoService(IDocumentoService documentoService) {
  // this.documentoService = documentoService;
  // }
  //
  // public ITareaService getTareaService() {
  // return tareaService;
  // }
  //
  // public void setTareaService(ITareaService tareaService) {
  // this.tareaService = tareaService;
  // }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public DocumentoGedoService getDocumentoGedoService() {
    return documentoGedoService;
  }

  public void setDocumentoGedoService(DocumentoGedoService documentoGedoService) {
    this.documentoGedoService = documentoGedoService;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public IActividadService getActividadService() {
    return actividadService;
  }

  public void setActividadService(IActividadService actividadService) {
    this.actividadService = actividadService;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

public INotificacionEEService getNotificacionService() {
	return notificacionService;
}

public void setNotificacionService(INotificacionEEService notificacionService) {
	this.notificacionService = notificacionService;
}
  

  
}
