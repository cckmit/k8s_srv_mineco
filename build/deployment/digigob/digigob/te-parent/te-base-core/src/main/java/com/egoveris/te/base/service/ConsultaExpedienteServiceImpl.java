package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Component;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.plugins.tools.FormularioControladoUtils;
import com.egoveris.te.base.exception.BuscarExpedienteElectronicoException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.SinPersistirException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IConsultaExpedienteService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExpedienteElectronico2CaratulaDatosTransformer;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CampoFFCCDTO;
import com.egoveris.te.model.model.CaratulaVariableResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponseDetallado;
import com.egoveris.te.model.model.DTODatosCaratula;
import com.egoveris.te.model.model.DatosTareaBean;
import com.egoveris.te.model.model.DocumentosVinculadosResponse;
import com.egoveris.te.model.model.ExpedienteAsociadoDTO;
import com.egoveris.te.model.model.ExpedienteAsociadoResponse;
import com.egoveris.te.model.model.ExpedienteElectronicoResponse;
import com.egoveris.te.model.model.ExpedienteMetadataExternal;
import com.egoveris.te.model.model.ExpedientesFusionResponse;
import com.egoveris.te.model.model.ExpedientesVinculadosResponse;
import com.egoveris.te.model.model.HistorialDeOperacionResponse;
import com.egoveris.te.model.model.HistorialDePasesResponse;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEERequest;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEEResponse;
import com.egoveris.te.model.model.SolicitanteResponse;

@Service
@Transactional
public class ConsultaExpedienteServiceImpl extends ExternalServiceAbstract
    implements IConsultaExpedienteService {
  // ExpedienteElectronico expediente;
  private transient static Logger logger = LoggerFactory
      .getLogger(ConsultaExpedienteServiceImpl.class);
  List<String> listDesgloseCodigoEE = new ArrayList<>();
  @Autowired
  private TrataService trataService;
  @Autowired
  private HistorialOperacionService historialOperacionService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ITaskViewService taskViewService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private String reparticionGT;

  @Autowired
  private DocumentoGedoService documentoGedoService;

  @Autowired
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;

  @Autowired
  private TipoDocumentoService tipoDocumentoService;

  @Autowired
  @Qualifier(ConstantesServicios.CONSULTA_DOC_EXTERNAL_SERVICE)
  private IExternalConsultaDocumentoServiceExt consultaDocumentoService;

  @Autowired
  private FusionService fusionService;
  @Autowired
  private TramitacionConjuntaService tramitacionConjuntaService;

  @Transactional
  public ConsultaExpedienteResponse consultaEE(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaEE(codigoEE={}) - start", codigoEE);
    }

    ExpedienteElectronicoDTO expediente = this.validarCodigoEE(codigoEE);

    ConsultaExpedienteResponse consultaResponse = new ConsultaExpedienteResponse();
    this.consultaBasicaExpediente(expediente, consultaResponse);
    consultaResponse.setListDocumentosOficiales(this.listDocumentosOficiales(expediente));
    consultaResponse.setListArchivosAdjuntos(this.listArchTrabajo(expediente));
    consultaResponse.setListExpedientesAsociados(this.listExpedientesAsociados(expediente));

    if (logger.isDebugEnabled()) {
      logger.debug("consultaEE(String) - end - return value={}", consultaResponse);
    }
    return consultaResponse;
  }

  @Transactional(readOnly = true)
  public ConsultaExpedienteResponseDetallado consultaEEDetallado(String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaEEDetallado(codigoEE={}) - start", codigoEE);
    }

    ExpedienteElectronicoDTO expediente = this.validarCodigoEE(codigoEE);

    ConsultaExpedienteResponseDetallado consultaResponse = new ConsultaExpedienteResponseDetallado();
    consultaResponse = (ConsultaExpedienteResponseDetallado) this
        .consultaBasicaExpediente(expediente, consultaResponse);
    consultaResponse.setListDocumentosOficiales(this.listDocumentosOficiales(expediente));
    consultaResponse.setListArchivosAdjuntos(listArchTrabajo(expediente));
    consultaResponse = (ConsultaExpedienteResponseDetallado) listExpedientesAsociados(
        consultaResponse, expediente);
    this.consultaEEDetalle(consultaResponse, expediente);

    if (logger.isDebugEnabled()) {
      logger.debug("consultaEEDetallado(String) - end - return value={}", consultaResponse);
    }
    return consultaResponse;
  }

  private ConsultaExpedienteResponse consultaBasicaExpediente(ExpedienteElectronicoDTO expediente,
      ConsultaExpedienteResponse consultaresponse)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaBasicaExpediente(expediente={}, consultaresponse={}) - start",
          expediente, consultaresponse);
    }

    consultaresponse.setCodigoEE(expediente.getCodigoCaratula());
    consultaresponse.setDescripcionTrata(
        trataService.obtenerDescripcionTrataByCodigo(expediente.getTrata().getCodigoTrata()));
    consultaresponse.setCodigotrata(expediente.getTrata().getCodigoTrata());
    consultaresponse.setEstado(expediente.getEstado());
    consultaresponse.setSistemaOrigen(expediente.getSistemaCreador());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultaBasicaExpediente(ExpedienteElectronico, ConsultaExpedienteResponse) - end - return value={}",
          consultaresponse);
    }
    return consultaresponse;
  }

  private List<String> listDocumentosOficiales(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listDocumentosOficiales(expediente={}) - start", expediente);
    }

    List<String> listDocumentosOficiales = new ArrayList<>();

    for (DocumentoDTO lista : expediente.getDocumentos()) {
      listDocumentosOficiales.add(lista.getNumeroSade());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listDocumentosOficiales(ExpedienteElectronico) - end - return value={}",
          listDocumentosOficiales);
    }
    return listDocumentosOficiales;
  }

  private List<String> listArchTrabajo(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listArchTrabajo(expediente={}) - start", expediente);
    }

    List<String> listArchTrabajo = new ArrayList<>();

    for (ArchivoDeTrabajoDTO lista : expediente.getArchivosDeTrabajo()) {
      listArchTrabajo.add(lista.getNombreArchivo());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listArchTrabajo(ExpedienteElectronico) - end - return value={}",
          listArchTrabajo);
    }
    return listArchTrabajo;
  }

  private List<String> listExpedientesAsociados(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listExpedientesAsociados(expediente={}) - start", expediente);
    }

    List<String> listExpedientesAsociados = new ArrayList<>();

    for (ExpedienteAsociadoEntDTO lista : expediente.getListaExpedientesAsociados()) {
      listExpedientesAsociados.add(lista.getAsNumeroSade());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listExpedientesAsociados(ExpedienteElectronico) - end - return value={}",
          listExpedientesAsociados);
    }
    return listExpedientesAsociados;
  }

  private ConsultaExpedienteResponseDetallado listExpedientesAsociados(
      ConsultaExpedienteResponseDetallado consultaResponse, ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listExpedientesAsociados(consultaResponse={}, expediente={}) - start",
          consultaResponse, expediente);
    }

    List<ExpedienteAsociadoDTO> listExpedientesAsociados = new ArrayList<>();
    List<ExpedienteAsociadoDTO> listExpedientesAsociadosTC = new ArrayList<>();
    List<ExpedienteAsociadoDTO> listExpedientesAsociadosFusion = new ArrayList<>();
    List<String> listaExpedientesAsociados = new ArrayList<>();

    for (ExpedienteAsociadoEntDTO expAsoc : expediente.getListaExpedientesAsociados()) {
      if (expAsoc.getDefinitivo()) {
        ExpedienteAsociadoDTO expAsocDTO = new ExpedienteAsociadoDTO();
        expAsocDTO.setEsCabecera(false);
        expAsocDTO.setNumeroSade(expAsoc.getAsNumeroSade());
        if (expAsoc.getEsExpedienteAsociadoFusion() != null
            && expAsoc.getEsExpedienteAsociadoFusion()) {
          listExpedientesAsociadosFusion.add(expAsocDTO);
        } else if (expAsoc.getEsExpedienteAsociadoTC() != null
            && expAsoc.getEsExpedienteAsociadoTC()) {
          listExpedientesAsociadosTC.add(expAsocDTO);
        } else {
          listExpedientesAsociados.add(expAsocDTO);
        }
        listaExpedientesAsociados.add(expAsoc.getAsNumeroSade());
      }
    }
    String codigoExpedienteElectronico = expediente.getCodigoCaratula();
    if ((codigoExpedienteElectronico != null) && tramitacionConjuntaService
        .esExpedienteEnProcesoDeTramitacionConjunta(codigoExpedienteElectronico)) {
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      if (codigoExpedienteElectronicoCabecera != null) {
        ExpedienteAsociadoDTO expAsocDTO = new ExpedienteAsociadoDTO();
        expAsocDTO.setEsCabecera(true);
        expAsocDTO.setNumeroSade(codigoExpedienteElectronicoCabecera);
        listExpedientesAsociadosTC.add(expAsocDTO);
      }

    }
    if ((codigoExpedienteElectronico != null)
        && fusionService.esExpedienteEnProcesoDeFusion(codigoExpedienteElectronico)) {
      // se usa el servicio de tc porque devuelve la caratula del expediente
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      if (codigoExpedienteElectronicoCabecera != null) {
        ExpedienteAsociadoDTO expAsocDTO = new ExpedienteAsociadoDTO();
        expAsocDTO.setEsCabecera(true);
        expAsocDTO.setNumeroSade(codigoExpedienteElectronicoCabecera);
        listExpedientesAsociadosFusion.add(expAsocDTO);
      }
    }
    consultaResponse.setListaExpedientesAsociados(listExpedientesAsociados);
    consultaResponse.setListaExpedientesAsociadosTC(listExpedientesAsociadosTC);
    consultaResponse.setListaExpedientesAsociadosFusion(listExpedientesAsociadosFusion);
    consultaResponse.setListExpedientesAsociados(listaExpedientesAsociados);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "listExpedientesAsociados(ConsultaExpedienteResponseDetallado, ExpedienteElectronico) - end - return value={}",
          consultaResponse);
    }
    return consultaResponse;
  }

  private void consultaEEDetalle(ConsultaExpedienteResponseDetallado consultaresponse,
      ExpedienteElectronicoDTO expediente) throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaEEDetalle(consultaresponse={}, expediente={}) - start",
          consultaresponse, expediente);
    }

    consultaresponse.setDescripcionTramite(expediente.getDescripcion());
    consultaresponse.setF_caratulacion(expediente.getFechaCreacion());
    consultaresponse.setUsuarioCaratulador(expediente.getUsuarioCreador());
    consultaresponse.setDatoVariable(this.listDatoVariable(expediente));
    destinoActual(consultaresponse, expediente);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultaEEDetalle(ConsultaExpedienteResponseDetallado, ExpedienteElectronico) - end");
    }
  }

  private void destinoActual(ConsultaExpedienteResponseDetallado consultaresponse,
      ExpedienteElectronicoDTO expediente) throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("destinoActual(consultaresponse={}, expediente={}) - start", consultaresponse,
          expediente);
    }

    String destinoActual = this.traerUltimoHistorial(expediente);

    if (destinoActual != null && destinoActual.contains("-")) {
      consultaresponse.setEsSectorDestino(true);
      consultaresponse
          .setReparticionDestino(destinoActual.substring(0, destinoActual.indexOf("-")));
      consultaresponse.setSectorDestino(
          destinoActual.substring(destinoActual.indexOf("-") + 1, destinoActual.length()));
    } else {
      if (usuariosSADEService.getDatosUsuario(destinoActual) != null) {
        consultaresponse.setEsUsuarioDestino(true);
        consultaresponse.setUsuarioDestino(destinoActual);
      } else {
        consultaresponse.setEsReparticionDestino(true);
        consultaresponse.setReparticionDestino(destinoActual);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "destinoActual(ConsultaExpedienteResponseDetallado, ExpedienteElectronico) - end");
    }
  }

  private String traerUltimoHistorial(ExpedienteElectronicoDTO expediente)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("traerUltimoHistorial(expediente={}) - start", expediente);
    }

    String salida = null;
    List<HistorialOperacionDTO> historiales = null;
    try {

      historiales = this.historialOperacionService
          .buscarHistorialporExpediente(expediente.getId());
    } catch (SinPersistirException e) {
      logger.error(e.getMessage());
      throw new ProcesoFallidoException(e.getMessage(), e);
    }
    int a = 0;

    for (HistorialOperacionDTO aux : historiales) {
      if (aux.getId() >= a) {
        salida = aux.getDestinatarioDetalle();
        a = aux.getId();
      }
    }

    if (StringUtils.isEmpty(salida)) {
      throw new ProcesoFallidoException(
          "No es posible determinar el destino actual del expediente "
              + expediente.getCodigoCaratula(),
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("traerUltimoHistorial(ExpedienteElectronico) - end - return value={}", salida);
    }
    return salida;
  }

  private String formatoCodigo(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("formatoCodigo(expediente={}) - start", expediente);
    }

    String salida = "";
    salida += expediente.getTipoDocumento();
    salida += expediente.getAnio();
    salida += expediente.getNumero();

    if (!expediente.getSecuencia().trim().isEmpty()) {
      salida += expediente.getSecuencia();
    }

    salida += expediente.getCodigoCaratula().subSequence(21,
        expediente.getCodigoCaratula().length());

    if (logger.isDebugEnabled()) {
      logger.debug("formatoCodigo(ExpedienteElectronico) - end - return value={}", salida);
    }
    return salida;
  }

  private List<ExpedienteMetadataExternal> listDatoVariable(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listDatoVariable(expediente={}) - start", expediente);
    }

    ExpedienteMetadataExternal expedienteMetadataExternal;
    List<ExpedienteMetadataExternal> listaSalida = new ArrayList<>();

    for (ExpedienteMetadataDTO aux : expediente.getMetadatosDeTrata()) {
      expedienteMetadataExternal = new ExpedienteMetadataExternal();

      expedienteMetadataExternal.setNombreMetadata(aux.getNombre());
      expedienteMetadataExternal.setOrdenMetadata(aux.getOrden());
      expedienteMetadataExternal.setTipoMetadata(aux.getTipo());
      expedienteMetadataExternal.setValorMetadata(aux.getValor());
      listaSalida.add(expedienteMetadataExternal);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listDatoVariable(ExpedienteElectronico) - end - return value={}", listaSalida);
    }
    return listaSalida;
  }

  @Transactional
  public ConsultaExpedienteResponse consultarDatosExpedientePorCodigosDeTrata(
      List<String> listaDeCodigosTrata, String expedienteEstado, String expedienteUsuarioAsignado)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultarDatosExpedientePorCodigosDeTrata(listaDeCodigosTrata={}, expedienteEstado={}, expedienteUsuarioAsignado={}) - start",
          listaDeCodigosTrata, expedienteEstado, expedienteUsuarioAsignado);
    }

    if ((listaDeCodigosTrata == null) || (listaDeCodigosTrata.size() == 0)
        || (expedienteEstado == null) || "null".equalsIgnoreCase(expedienteEstado)
        || "".equalsIgnoreCase(expedienteEstado)) {
      throw new ProcesoFallidoException(
          "Debe ingresar al menos un código de trata validado ó el estado del expediente para realizar la consulta.",
          null);
    }

    ConsultaExpedienteResponse response = new ConsultaExpedienteResponse();

    try {
      List<DatosTareaBean> listadoDatosTarea = getDatosTareaBean(
          taskViewService.buscarTareaDistPorTrata(expedienteEstado, expedienteUsuarioAsignado,
              listaDeCodigosTrata)); 
      response.setListaDatosTarea(listadoDatosTarea);
    } catch (Exception e) {
      logger.error("consultarDatosExpedientePorCodigosDeTrata(List<String>, String, String)", e);

      throw new ProcesoFallidoException(
          "No existe un expediente electronico con alguna de las tratas ("
              + listaDeCodigosTrata.toString() + "), [error= " + e.getMessage() + "]",
          e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultarDatosExpedientePorCodigosDeTrata(List<String>, String, String) - end - return value={}",
          response);
    }
    return response;
  }

  private List<DatosTareaBean> getDatosTareaBean(List<Tarea> tareasList) {
    if (logger.isDebugEnabled()) {
      logger.debug("getDatosTareaBean(tareasList={}) - start", tareasList);
    }

    List<DatosTareaBean> datosTareaBeanList = new ArrayList<>();

    for (Tarea tarea : tareasList) {
      DatosTareaBean datosTareaBean = new DatosTareaBean(tarea.getEstado(),
          tarea.getCodigoExpediente(), tarea.getCodigoTrata(), tarea.getDescripcionTrata(),
          tarea.getMotivo(), tarea.getFechaModificacion(), tarea.getUsuarioAnterior());
      datosTareaBeanList.add(datosTareaBean);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getDatosTareaBean(List<Tarea>) - end - return value={}", datosTareaBeanList);
    }
    return datosTareaBeanList;
  }

  @Transactional
  public ConsultaExpedienteResponse obtenerCaratulaPorNumeroExpediente(String expedienteCodigo)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCaratulaPorNumeroExpediente(expedienteCodigo={}) - start",
          expedienteCodigo);
    }

    ConsultaExpedienteResponse response = new ConsultaExpedienteResponse();

    if ((expedienteCodigo == null) || "null".equalsIgnoreCase(expedienteCodigo)
        || "".equalsIgnoreCase(expedienteCodigo)) {
      throw new ProcesoFallidoException(
          "Debe ingresar un código de expediente electrónico validado para obtener la caratula.",
          null);
    }

    try {
      response.setCodigoDocCaratula(
          expedienteElectronicoService.obtenerCodigoCaratulaPorNumeroExpediente(expedienteCodigo));
    } catch (Exception e) {
      logger.error("obtenerCaratulaPorNumeroExpediente(String)", e);

      throw new ProcesoFallidoException(
          "No existe nínguna modificación de caratula para el expediente electronico ("
              + expedienteCodigo + ") que coincida, [error= " + e.getMessage() + "]",
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCaratulaPorNumeroExpediente(String) - end - return value={}", response);
    }
    return response;
  }

  /**
   * @param <code>java.lang.String</code>
   *          codigoEE
   *
   * @return <code>
  *                  <DTODatosCaratula>
  *                             <NombreSolicitante>PEPE</NombreSolicitante>
  *                             <ApellidoSolicitante>PEPE</ ApellidoSolicitante>
  *                             <RazonSocialSolicitante>PEPE</RazonSocialSolicitante>
  *                             <TipoDocumento>PEPE</TipoDocumento>
  *                             <NumeroDocumento>PEPE</NumeroDocumento>
  *                             <NumeroSadeDocumentoCaratula>PEPE</ NumeroSadeDocumentoCaratula >
  *                             <esPersonaJuridica>True/False</esPersonaJuridica>
  *                             </DTODatosCaratula></code> la estructura
   */
  @Override
  public ObtenerCaratulaPorCodigoEEResponse obtenerCaratulaPorCodigoEE(
      @WebParam(name = "request") ObtenerCaratulaPorCodigoEERequest request)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCaratulaPorCodigoEE(request={}) - start", request);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    ExpedienteElectronico2CaratulaDatosTransformer transformer;
    ObtenerCaratulaPorCodigoEEResponse response = new ObtenerCaratulaPorCodigoEEResponse();

    if ((request.getCodigoEE() == null) || "null".equalsIgnoreCase(request.getCodigoEE().trim())
        || "".equalsIgnoreCase(request.getCodigoEE().trim())) {
      throw new ProcesoFallidoException(
          "Debe ingresar un código de expediente electrónico validado para obtener la caratula.",
          null);
    }

    try {
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(request.getCodigoEE());
    } catch (ParametroIncorrectoException e) {
      logger.error("obtenerCaratulaPorCodigoEE(ObtenerCaratulaPorCodigoEERequest)", e);

      throw new ProcesoFallidoException(
          e.getMessage() + " expediente electronico (" + request.getCodigoEE() + ") que coincida.",
          e);
    }
    transformer = new ExpedienteElectronico2CaratulaDatosTransformer(consultaDocumentoService);
    response.setDtoDatosCaratula((DTODatosCaratula) transformer.transform(expedienteElectronico));

    if (transformer.getMsgError() != null) {
      throw new ProcesoFallidoException(
          transformer.getMsgError() + ", expediente electronico (" + request.getCodigoEE() + ")",
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerCaratulaPorCodigoEE(ObtenerCaratulaPorCodigoEERequest) - end - return value={}",
          response);
    }
    return response;
  }

  public boolean validarEE(String codigoEE) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarEE(codigoEE={}) - start", codigoEE);
    }

    try {
      validarCodigoEE(codigoEE);
    } catch (ParametroIncorrectoException e) {
      logger.error("validarEE(String)", e);

      if (logger.isDebugEnabled()) {
        logger.debug("validarEE(String) - end - return value={}", false);
      }
      return false;
    } catch (ProcesoFallidoException e) {
      logger.error("validarEE(String)", e);

      if (logger.isDebugEnabled()) {
        logger.debug("validarEE(String) - end - return value={}", false);
      }
      return false;
    } catch (NegocioException e) {
      logger.error("validarEE(String)", e);

      if (logger.isDebugEnabled()) {
        logger.debug("validarEE(String) - end - return value={}", false);
      }
      return false;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarEE(String) - end - return value={}", true);
    }
    return true;
  }

  private ExpedienteElectronicoDTO validarCodigoEE(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoEE(codigoEE={}) - start", codigoEE);
    }

    ExpedienteElectronicoDTO expediente;
    // Desglose del código recibido en formato "XX-0000-00000000- -XXXX-XXXX"
    if (codigoEE == null) {
      throw new ParametroIncorrectoException("El código de expediente dado es nulo.", null);
    } else {
      expediente = obtenerExpedienteElectronico(codigoEE);
      if (expediente == null) {
        throw new NegocioException("El expediente " + codigoEE + " no existe en EE.", null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoEE(String) - end - return value={}", expediente);
    }
    return expediente;

  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  @Override
  public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(
      String codExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(codExpediente={}) - start",
          codExpediente);
    }

    List<ExpedienteElectronicoDTO> expedientes = this.expedienteElectronicoService
        .obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(codExpediente);
    List<ExpedienteElectronicoResponse> resultado = new ArrayList<>();
    if (expedientes != null) {
      for (ExpedienteElectronicoDTO e : expedientes) {
        // creacion del expedienteElectronicoResponse
        ExpedienteElectronicoResponse eer = new ExpedienteElectronicoResponse();
        eer.setCodigoCaratula(e.getCodigoCaratula());
        eer.setCodigoTrata(e.getTrata().getCodigoTrata());
        eer.setDescripcionTrataElectronica(
            trataService.obtenerDescripcionTrataByCodigo(e.getTrata().getCodigoTrata()));
        eer.setEstado(e.getEstado());
        eer.setDescripcion(e.getDescripcion()); // motivo del expediente
        eer.setFechaCreacion(e.getFechaCreacion());
        eer.setFechaModificacion(e.getFechaModificacion());
        eer.setMotivoSolicitud(e.getSolicitudIniciadora().getMotivo());
        eer.setUsuarioCreador(e.getUsuarioCreador());
        eer.setMotivoSolicitudExterna(e.getSolicitudIniciadora().getMotivoExterno());

        // creacion de datos del solicitante
        SolicitanteDTO expSolicitante = e.getSolicitudIniciadora().getSolicitante();
        SolicitanteResponse s = new SolicitanteResponse();
        s.setDomicilio(expSolicitante.getDomicilio());
        s.setPrimerNombre(expSolicitante.getNombreSolicitante());
        s.setSegundoNombre(expSolicitante.getSegundoNombreSolicitante());
        s.setPrimerApellido(expSolicitante.getApellidoSolicitante());
        s.setSegundoApellido(expSolicitante.getSegundoApellidoSolicitante());
        if (e.getSolicitudIniciadora().isEsSolicitudInterna()) {
          s.setTipoDeIniciador("Interno");
        } else {
          s.setTipoDeIniciador("Externo");
        }
        s.setTipoDocumento(expSolicitante.getDocumento().getTipoDocumento());
        s.setNumeroDocumento(expSolicitante.getDocumento().getNumeroDocumento());
        s.setRazonSocial(expSolicitante.getRazonSocialSolicitante());
        s.setTelefono(expSolicitante.getTelefono());
        s.setCuitCuil(expSolicitante.getCuitCuil());
        s.setPiso(expSolicitante.getPiso());
        s.setDepartamento(expSolicitante.getDepartamento());
        s.setCodigoPostal(expSolicitante.getCodigoPostal());
        s.setEmail(expSolicitante.getEmail());
        s.setTelefono(expSolicitante.getTelefono());

        eer.setSolicitante(s);

        resultado.add(eer);
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(String) - end - return value={}",
            resultado);
      }
      return resultado;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(String) - end - return value={null}");
    }
    return null;
  }

  @Override
  public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalMayorA24Meses(
      Integer cantidadDeDias) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesEnGuardaTemporalMayorA24Meses(cantidadDeDias={}) - start",
          cantidadDeDias);
    }

    List<ExpedienteElectronicoDTO> expedientes = this.expedienteElectronicoService
        .obtenerExpedientesEnGuardaTemporalMayorA24Meses(cantidadDeDias);
    List<ExpedienteElectronicoResponse> resultado = new ArrayList<>();
    if (expedientes != null) {
      for (ExpedienteElectronicoDTO e : expedientes) {
        // creacion del expedienteElectronicoResponse
        ExpedienteElectronicoResponse eer = new ExpedienteElectronicoResponse();
        eer.setCodigoCaratula(e.getCodigoCaratula());
        eer.setCodigoTrata(e.getTrata().getCodigoTrata());
        eer.setDescripcionTrataElectronica(
            trataService.obtenerDescripcionTrataByCodigo(e.getTrata().getCodigoTrata()));
        eer.setEstado(e.getEstado());
        eer.setDescripcion(e.getDescripcion()); // motivo del expediente
        eer.setFechaCreacion(e.getFechaCreacion());
        eer.setFechaModificacion(e.getFechaModificacion());
        eer.setMotivoSolicitud(e.getSolicitudIniciadora().getMotivo());
        eer.setUsuarioCreador(e.getUsuarioCreador());
        eer.setMotivoSolicitudExterna(e.getSolicitudIniciadora().getMotivoExterno());

        // creacion de datos del solicitante
        SolicitanteDTO expSolicitante = e.getSolicitudIniciadora().getSolicitante();
        SolicitanteResponse s = new SolicitanteResponse();
        s.setDomicilio(expSolicitante.getDomicilio());
        s.setPrimerNombre(expSolicitante.getNombreSolicitante());
        s.setSegundoNombre(expSolicitante.getSegundoNombreSolicitante());
        s.setPrimerApellido(expSolicitante.getApellidoSolicitante());
        s.setSegundoApellido(expSolicitante.getSegundoApellidoSolicitante());
        if (e.getSolicitudIniciadora().isEsSolicitudInterna()) {
          s.setTipoDeIniciador("Interno");
        } else {
          s.setTipoDeIniciador("Externo");
        }
        s.setTipoDocumento(expSolicitante.getDocumento().getTipoDocumento());
        s.setNumeroDocumento(expSolicitante.getDocumento().getNumeroDocumento());
        s.setRazonSocial(expSolicitante.getRazonSocialSolicitante());
        s.setTelefono(expSolicitante.getTelefono());
        s.setCuitCuil(expSolicitante.getCuitCuil());
        s.setPiso(expSolicitante.getPiso());
        s.setDepartamento(expSolicitante.getDepartamento());
        s.setCodigoPostal(expSolicitante.getCodigoPostal());
        s.setEmail(expSolicitante.getEmail());
        s.setTelefono(expSolicitante.getTelefono());

        eer.setSolicitante(s);

        resultado.add(eer);
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerExpedientesEnGuardaTemporalMayorA24Meses(Integer) - end - return value={}",
            resultado);
      }
      return resultado;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesEnGuardaTemporalMayorA24Meses(Integer) - end - return value={null}");
    }
    return null;
  }

  private List<HistorialOperacionDTO> ordenarPases(final List<HistorialOperacionDTO> listaPases) {
    if (logger.isDebugEnabled()) {
      logger.debug("ordenarPases(listaPases={}) - start", listaPases);
    }

    Collections.sort(listaPases, new PaseComparator());

    if (logger.isDebugEnabled()) {
      logger.debug("ordenarPases(List<HistorialOperacion>) - end - return value={}", listaPases);
    }
    return listaPases;
  }

  private List<ExpedientesVinculadosResponse> ordenarVinculados(
      final List<ExpedientesVinculadosResponse> listaTc) {
    if (logger.isDebugEnabled()) {
      logger.debug("ordenarVinculados(listaTc={}) - start", listaTc);
    }

    Collections.sort(listaTc, new VinculadosComparator());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "ordenarVinculados(List<ExpedientesVinculadosResponse>) - end - return value={}",
          listaTc);
    }
    return listaTc;
  }

  private List<ExpedienteAsociadoResponse> ordenarAsociados(
      final List<ExpedienteAsociadoResponse> listaAs) {
    if (logger.isDebugEnabled()) {
      logger.debug("ordenarAsociados(listaAs={}) - start", listaAs);
    }

    Collections.sort(listaAs, new AsociadoComparator());

    if (logger.isDebugEnabled()) {
      logger.debug("ordenarAsociados(List<ExpedienteAsociadoResponse>) - end - return value={}",
          listaAs);
    }
    return listaAs;
  }

  private List<DocumentosVinculadosResponse> ordenarDocumentos(
      final List<DocumentosVinculadosResponse> listadvr) {
    if (logger.isDebugEnabled()) {
      logger.debug("ordenarDocumentos(listadvr={}) - start", listadvr);
    }

    Collections.sort(listadvr, new DocumentosComparator());

    if (logger.isDebugEnabled()) {
      logger.debug("ordenarDocumentos(List<DocumentosVinculadosResponse>) - end - return value={}",
          listadvr);
    }
    return listadvr;
  }

  public String getReparticionGT() {
    return reparticionGT;
  }

  public void setReparticionGT(String reparticionGT) {
    this.reparticionGT = reparticionGT;
  }

  @Override
  public HistorialDePasesResponse obtenerHistorialDePasesDeExpediente(String codigoEE)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerHistorialDePasesDeExpediente(codigoEE={}) - start", codigoEE);
    }

    try {
      ExpedienteElectronicoDTO expediente = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codigoEE);
      List<HistorialOperacionDTO> historial;

      if (expediente != null) {
        historial = this.historialOperacionService
            .buscarHistorialporExpediente(expediente.getId());

        historial = ordenarPases(historial);

        HistorialDePasesResponse resultado = new HistorialDePasesResponse();
        // inicio de listaVinculados en TC
        List<ExpedienteAsociadoEntDTO> etc;

        etc = expedienteElectronicoService.obtenerListaEnTramitacionConjunta(expediente.getId());

        List<ExpedientesVinculadosResponse> listaVinculados = new ArrayList<>();

        for (ExpedienteAsociadoEntDTO ea : etc) {
          ExpedienteElectronicoDTO eaux = expedienteElectronicoService
              .obtenerExpedienteElectronicoPorCodigo(ea.getAsNumeroSade());
          ExpedientesVinculadosResponse ev = new ExpedientesVinculadosResponse();
          if (eaux.getTrata() != null && eaux.getTrata().getCodigoTrata() != null) {
            ev.setTrataExpedienteVinculado((eaux.getTrata().getCodigoTrata()));

            ev.setDescTrataExVinculado(
                trataService.obtenerDescripcionTrataByCodigo(eaux.getTrata().getCodigoTrata()));
          }
          ev.setCodigoExpediente(ea.getAsNumeroSade());
          ev.setUsuarioVinculador(ea.getUsuarioAsociador());
          ev.setFechaVinculacion(ea.getFechaAsociacion());
          listaVinculados.add(ev);
        }
        ;
        listaVinculados = ordenarVinculados(listaVinculados);
        resultado.setExpedientesVinculados(listaVinculados);
        // Fin lista VinculadosEnTc

        // inicio de listaAsociados en ASociacion
        etc = expedienteElectronicoService.buscarExpedientesAsociados(expediente.getId(), "ARCH");
        List<ExpedienteAsociadoResponse> listaAsociados = new ArrayList<>();

        for (ExpedienteAsociadoEntDTO e : etc) {
          if (e.getEsExpedienteAsociadoTC() == null) {
            ExpedienteAsociadoResponse eas = new ExpedienteAsociadoResponse();
            eas.setCodigoExpediente(e.getAsNumeroSade());
            eas.setDescTrataExAsociado(trataService.obtenerDescripcionTrataByCodigo(e.getTrata()));
            eas.setTrataExpedienteASociado(e.getTrata());
            eas.setFechaAsociacion(e.getFechaAsociacion());
            eas.setUsuarioAsociador(e.getUsuarioAsociador());
            listaAsociados.add(eas);
          }

        }
        listaAsociados = ordenarAsociados(listaAsociados);
        resultado.setExpedientesAsociados(listaAsociados);
        // Fin de listaVinculados en ASOCIACION

        // inicio de listaDocumentos del Expediente
        List<DocumentosVinculadosResponse> lDocr = new ArrayList<>();
        List<DocumentoDTO> docv;
        docv = expediente.getDocumentos();

        for (DocumentoDTO d : docv) {
          DocumentosVinculadosResponse dvr = new DocumentosVinculadosResponse();
          dvr.setFechaCreacion(d.getFechaCreacion());
          dvr.setFechavinculacionDefinitiva(d.getFechaAsociacion());
          dvr.setUsuarioAsociacion(d.getUsuarioAsociador());
          dvr.setNumeroEspecialDocumento(d.getNumeroEspecial());
          dvr.setNumeroSadeDocumento(d.getNumeroSade());
          dvr.setTipodeDocumento(d.getTipoDocAcronimo());
          dvr.setReferencia(d.getMotivo());
          dvr.setUsuarioGenerador(d.getNombreUsuarioGenerador());
          lDocr.add(dvr);
        }
        lDocr = ordenarDocumentos(lDocr);
        resultado.setDocumentosVinculados(lDocr);

        // Fin de listaDocumentos del Expediente

        // inicio de listaAsociados en Fusion
        etc = expedienteElectronicoService.obtenerListaEnFusionados(expediente.getId());

        List<ExpedientesFusionResponse> exf = new ArrayList<>();

        for (ExpedienteAsociadoEntDTO e : etc) {
          ExpedientesFusionResponse exfr = new ExpedientesFusionResponse();
          exfr.setCodigoExpediente(e.getAsNumeroSade());
          exfr.setCodigoTrata(e.getTrata());
          exfr.setDescripcionTrata(trataService.obtenerDescripcionTrataByCodigo(e.getTrata()));
          exf.add(exfr);
        }
        resultado.setExpedientesFusionAsociados(exf);

        // Fin de listaAsociados en Fusion

        // Inicio historialOperacion
        List<HistorialDeOperacionResponse> list = new ArrayList<>();

        for (HistorialOperacionDTO h : historial) {
          HistorialDeOperacionResponse hdor = new HistorialDeOperacionResponse();
          if (destinatarioIgualAEstado(h.getDestinatario())) {
            hdor.setDestinatario(reparticionGT);
          } else {
            hdor.setDestinatario(h.getDestinatario());
          }
          hdor.setEstado(h.getEstado());
          hdor.setMotivo(h.getMotivo());
          hdor.setFechaOperacion(h.getFechaOperacion());
          hdor.setTipoOperacion(h.getTipoOperacion());
          hdor.setIdExpediente(h.getIdExpediente());
          hdor.setUsuario(h.getUsuario());
          hdor.setExpediente(h.getExpediente());

          list.add(hdor);
        }
        resultado.setHistorialDeOperacion(list);
        // Fin HistorialOperacion

        if (logger.isDebugEnabled()) {
          logger.debug("obtenerHistorialDePasesDeExpediente(String) - end - return value={}",
              resultado);
        }
        return resultado;
      }
    } catch (Exception e) {
      logger.error("error al obtener historial", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerHistorialDePasesDeExpediente(String) - end - return value={null}");
    }
    return null;
  }

  private boolean destinatarioIgualAEstado(String destinatario) {
    if (logger.isDebugEnabled()) {
      logger.debug("destinatarioIgualAEstado(destinatario={}) - start", destinatario);
    }

    boolean returnboolean = destinatario.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
        || destinatario.equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)
        || destinatario.equals(ConstantesWeb.ESTADO_ARCHIVO);
    if (logger.isDebugEnabled()) {
      logger.debug("destinatarioIgualAEstado(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  final class PaseComparator implements Comparator<HistorialOperacionDTO> {

    /**
     * Compara dos instancias de Documento y devuelve la comparación usando el
     * criterio de más reciente primero en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(HistorialOperacionDTO t1, HistorialOperacionDTO t2) {
      if (logger.isDebugEnabled()) {
        logger.debug("compare(t1={}, t2={}) - start", t1, t2);
      }

      int returnint = t2.getFechaOperacion().compareTo(t1.getFechaOperacion());
      if (logger.isDebugEnabled()) {
        logger.debug("compare(HistorialOperacion, HistorialOperacion) - end - return value={}",
            returnint);
      }
      return returnint;

    }
  }

  final class VinculadosComparator implements Comparator<ExpedientesVinculadosResponse> {

    /**
     * Compara dos instancias de Documento y devuelve la comparación usando el
     * criterio de más reciente primero en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(ExpedientesVinculadosResponse t1, ExpedientesVinculadosResponse t2) {
      if (logger.isDebugEnabled()) {
        logger.debug("compare(t1={}, t2={}) - start", t1, t2);
      }

      int returnint = t2.getFechaVinculacion().compareTo(t1.getFechaVinculacion());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "compare(ExpedientesVinculadosResponse, ExpedientesVinculadosResponse) - end - return value={}",
            returnint);
      }
      return returnint;

    }
  }

  final class AsociadoComparator implements Comparator<ExpedienteAsociadoResponse> {

    /**
     * Compara dos instancias de Documento y devuelve la comparación usando el
     * criterio de más reciente primero en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(ExpedienteAsociadoResponse t1, ExpedienteAsociadoResponse t2) {
      if (logger.isDebugEnabled()) {
        logger.debug("compare(t1={}, t2={}) - start", t1, t2);
      }

      int returnint = t2.getFechaAsociacion().compareTo(t1.getFechaAsociacion());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "compare(ExpedienteAsociadoResponse, ExpedienteAsociadoResponse) - end - return value={}",
            returnint);
      }
      return returnint;

    }
  }

  final class DocumentosComparator implements Comparator<DocumentosVinculadosResponse> {

    /**
     * Compara dos instancias de Documento y devuelve la comparación usando el
     * criterio de más reciente primero en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(DocumentosVinculadosResponse t1, DocumentosVinculadosResponse t2) {
      if (logger.isDebugEnabled()) {
        logger.debug("compare(t1={}, t2={}) - start", t1, t2);
      }

      int returnint = t2.getFechavinculacionDefinitiva()
          .compareTo(t1.getFechavinculacionDefinitiva());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "compare(DocumentosVinculadosResponse, DocumentosVinculadosResponse) - end - return value={}",
            returnint);
      }
      return returnint;

    }
  }

  public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen,
      String reparticion) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen={}, reparticion={}) - start",
          sistemaOrigen, reparticion);
    }

    try {
      validarParametroConsulta("Sistema Origen", sistemaOrigen);
      validarParametroConsulta("Reparticion", reparticion);
      List<String> returnList = expedienteElectronicoService
          .consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen, reparticion);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "consultaExpedientesPorSistemaOrigenReparticion(String, String) - end - return value={}",
            returnList);
      }
      return returnList;
    } catch (ParametroIncorrectoException e) {

      throw e;
    }
  }

  public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen,
      String usuario) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen={}, usuario={}) - start",
          sistemaOrigen, usuario);
    }

    try {
      validarParametroConsulta("Sistema Origen", sistemaOrigen);
      validarParametroConsulta("Usuario", usuario);
      List<String> returnList = expedienteElectronicoService
          .consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen, usuario);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "consultaExpedientesPorSistemaOrigenUsuario(String, String) - end - return value={}",
            returnList);
      }
      return returnList;
    } catch (ParametroIncorrectoException e) {

      throw e;
    }
  }

  public Long consultaIdFCPorExpediente(String codigoEE) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaIdFCPorExpediente(codigoEE={}) - start", codigoEE);
    }

    Long idTransacccionFC = 0l;
    ExpedienteElectronicoDTO ee = null;

    if (codigoEE != null) {
      try {
        ee = expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codigoEE);
      } catch (ParametroIncorrectoException e) {
        logger.error("No se pudo obtener el Expediente Electrónico", e);
      }
      if (ee != null) {
        List<DocumentoDTO> listaDocs = ee.getDocumentos();
        for (DocumentoDTO doc : listaDocs) {
          if (doc.getIdTransaccionFC() != null) {
            idTransacccionFC = doc.getIdTransaccionFC();
            break;
          }
        }
      }
    }
    if (ee == null) {
      throw new NegocioException("Debe ingresar un código de Expediente válido", null);
    }
    if (idTransacccionFC == 0) {
      throw new ParametroIncorrectoException("El expediente no tiene FC", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaIdFCPorExpediente(String) - end - return value={}", idTransacccionFC);
    }
    return idTransacccionFC;
  }

  private void validarParametroConsulta(String nombreParametro, String parametro)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametroConsulta(nombreParametro={}, parametro={}) - start",
          nombreParametro, parametro);
    }

    if (parametro == null || "".equals(parametro.trim())) {
      throw new ParametroIncorrectoException(
          "El parámetro " + nombreParametro + " ingresado no puede ser null o vacio", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarParametroConsulta(String, String) - end");
    }
  }

  public FusionService getFusionService() {
    return fusionService;
  }

  public void setFusionService(FusionService fusionService) {
    this.fusionService = fusionService;
  }

  public TramitacionConjuntaService getTramitacionConjuntaService() {
    return tramitacionConjuntaService;
  }

  public void setTramitacionConjuntaService(
      TramitacionConjuntaService tramitacionConjuntaService) {
    this.tramitacionConjuntaService = tramitacionConjuntaService;
  }

  public CaratulaVariableResponse obtenerDatosCaratulaVariable(String codigoEE, String usuario)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDatosCaratulaVariable(codigoEE={}, usuario={}) - start", codigoEE,
          usuario);
    }

    if (codigoEE == null || codigoEE.equals("")) {
      logger.error("El número de expediente es obligatorio.");
      throw new ParametroIncorrectoException("El número de expediente es obligatorio.", null);
    }
    if (usuario == null || usuario.equals("")) {
      logger.error("Debe ingresar el usuario.");
      throw new ParametroIncorrectoException("Debe ingresar el usuario.", null);
    }

    ExpedienteElectronicoDTO expediente = expedienteElectronicoService
        .obtenerExpedienteElectronicoPorCodigo(codigoEE);

    if (expediente == null) {
      logger.error("No se encontró el expediente: " + codigoEE);
      throw new ParametroIncorrectoException("No se encontró el expediente: " + codigoEE, null);

    }

    CaratulaVariableResponse returnCaratulaVariableResponse = getDatosCaratulaVariable(
        expediente.getDocumentos(), usuario, codigoEE);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDatosCaratulaVariable(String, String) - end - return value={}",
          returnCaratulaVariableResponse);
    }
    return returnCaratulaVariableResponse;
  }

  public CaratulaVariableResponse getDatosCaratulaVariable(List<DocumentoDTO> documentos,
      String usuario, String codigoEE) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("getDatosCaratulaVariable(documentos={}, usuario={}) - start", documentos,
          usuario);
    }

    CaratulaVariableResponse caratulaVariableResponse;
    for (DocumentoDTO doc : documentos) {
      if (doc.getMotivo().equalsIgnoreCase("carátula") && doc.getIdTransaccionFC() != null) {
        ResponseExternalConsultaDocumento response = null;
        try {
          response = documentoGedoService.consultarDocumentoPorNumero(doc.getNumeroSade(),
              usuario);
        } catch (Exception e) {
          logger.error("getDatosCaratulaVariable(List<Documento>, String)", e);

          throw new ParametroIncorrectoException("Ha ocurrido un error de Comunicación con GEDO",
              e);
        }
        
        ExpedienteElectronicoDTO ee = null;
        try {
            ee = expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codigoEE);
        } catch (BuscarExpedienteElectronicoException e) {
          logger.error("error al buscar el expediente electronico", e);
        }
        try {
          doc.getIdTransaccionFC();

          if (response.getIdTransaccion() != null) {
            TipoDocumentoDTO tp = tipoDocumentoService
                .obtenerTipoDocumento(response.getTipoDocumento());

            if (tp != null && tp.getIdFormulario() != null) {
              IFormManager<Component> manager = null;
              //TODO:idOperacion
              try {
                manager = formManagerFact.create(tp.getIdFormulario());
                if (manager != null) {
                  if(ee != null && ee.getIdOperacion() != null){
                      manager.fillCompValues(ee.getIdOperacion(), Integer.valueOf(response.getIdTransaccion()));
                    }else{
                      manager.fillCompValues(Integer.valueOf(response.getIdTransaccion()));
                    }
                  Map<String, Object> caratulaVariable = FormularioControladoUtils
                      .getFFCCValues(manager);
                  caratulaVariableResponse = new CaratulaVariableResponse();
                  caratulaVariableResponse.setNombre(doc.getNumeroSade());
                  ArrayList<CampoFFCCDTO> campos = new ArrayList<>();
                  for (String key : caratulaVariable.keySet()) {
                    CampoFFCCDTO campo = new CampoFFCCDTO();
                    campo.setCampo(key);
                    campo.setValor(String.valueOf(caratulaVariable.get(key)));
                    campos.add(campo);
                  }
                  caratulaVariableResponse.setCampos(campos);

                  if (logger.isDebugEnabled()) {
                    logger.debug(
                        "getDatosCaratulaVariable(List<Documento>, String) - end - return value={}",
                        caratulaVariableResponse);
                  }
                  return caratulaVariableResponse;
                }
              } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ParametroIncorrectoException(
                    "Ocurrió un error al obtener los datos de la carátula", e);
              }

            }
          }
        } catch (UsernameNotFoundException e) {
          logger.error(e.getMessage());
          throw new ParametroIncorrectoException("No se encontró el usuario: " + usuario, e);

        } catch (Exception e) {
          logger.error("error al caratular", e);
        }
      }
    }
    throw new ParametroIncorrectoException("No se encontraron datos para el expediente", null);
  }

  public byte[] obtenerDocumentoCaratulaVariable(String codigoEE, String usuario)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoCaratulaVariable(codigoEE={}, usuario={}) - start", codigoEE,
          usuario);
    }

    if (codigoEE == null || codigoEE.equals("")) {
      logger.error("El número de expediente es obligatorio.");
      throw new ParametroIncorrectoException("El número de expediente es obligatorio.", null);
    }
    if (usuario == null || usuario.equals("")) {
      logger.error("Debe ingresar el usuario.");
      throw new ParametroIncorrectoException("Debe ingresar el usuario.", null);
    }

    ExpedienteElectronicoDTO expediente = expedienteElectronicoService
        .obtenerExpedienteElectronicoPorCodigo(codigoEE);

    if (expediente == null) {
      logger.error("No se encontró el expediente: " + codigoEE);
      throw new ParametroIncorrectoException("No se encontró el expediente: " + codigoEE, null);

    }

    byte[] returnbyteArray = getDocumentoCaratulaVariable(expediente.getDocumentos(), usuario);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoCaratulaVariable(String, String) - end - return value={}",
          returnbyteArray);
    }
    return returnbyteArray;

  }

  @SuppressWarnings("unused")
  public byte[] getDocumentoCaratulaVariable(List<DocumentoDTO> documentos, String usuario)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("getDocumentoCaratulaVariable(documentos={}, usuario={}) - start", documentos,
          usuario);
    }

    for (DocumentoDTO doc : documentos) {
      if (doc.getMotivo().equalsIgnoreCase("carátula") && doc.getIdTransaccionFC() != null) {
        
        try {

          doc.getIdTransaccionFC();
          RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();

          request.setNumeroDocumento(doc.getNumeroSade());
          request.setUsuarioConsulta(usuario);

          byte[] result = consultaDocumentoService.consultarDocumentoPdf(request);

          if (result != null) {

            if (logger.isDebugEnabled()) {
              logger.debug(
                  "getDocumentoCaratulaVariable(List<Documento>, String) - end - return value={}",
                  result);
            }
            return result;
          }
        } catch (UsernameNotFoundException e) {
          logger.error(e.getMessage());
          throw new ParametroIncorrectoException("No se encontró el usuario: " + usuario, e);

        } catch (SinPrivilegiosException e) {
          logger.error(e.getMessage());
          throw new ParametroIncorrectoException(e.getMessage(), e);

        } catch (Exception e) {
          logger.error("error al caratular", e);
          logger.error(e.getMessage());
        }
      }
    }
    throw new ParametroIncorrectoException(
        "No se encontró la carátula variable para el expediente.", null);
  }

}
