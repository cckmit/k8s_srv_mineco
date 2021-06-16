package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zkoss.util.resource.Labels;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.DatoPropio;
import com.egoveris.te.base.model.DatosVariablesComboGrupos;
import com.egoveris.te.base.model.DatosVariablesComboGruposDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.model.TipoComboDTO;
import com.egoveris.te.base.model.TipoDatoPropioDTO;
import com.egoveris.te.base.model.TrataAuditoriaDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataDiferencialDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.tipo.TipoCombo;
import com.egoveris.te.base.model.tipo.TipoDatoPropio;
import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.model.trata.TrataAuditoria;
import com.egoveris.te.base.model.trata.TrataDiferencial;
import com.egoveris.te.base.model.trata.TrataReparticion;
import com.egoveris.te.base.model.trata.TrataReparticionPK;
import com.egoveris.te.base.model.trata.TrataTipoResultado;
import com.egoveris.te.base.model.trata.TrataTipoResultadoPK;
import com.egoveris.te.base.model.trata.TrataTrack;
import com.egoveris.te.base.repository.DatosVariablesComboGruposRepository;
import com.egoveris.te.base.repository.tipo.ITipoComboRepository;
import com.egoveris.te.base.repository.tipo.TipoDatoPropioRepository;
import com.egoveris.te.base.repository.trata.ITrataDiferencialRepository;
import com.egoveris.te.base.repository.trata.TrataAuditoriaRepository;
import com.egoveris.te.base.repository.trata.TrataRepository;
import com.egoveris.te.base.repository.trata.TrataTipoResultadoRepository;
import com.egoveris.te.base.repository.trata.TrataTrackRepository;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;
import com.egoveris.te.base.util.ComparadorTrata;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.TipoTramiteEnum;


@Service
@Transactional
public class TrataServiceImpl implements TrataService {

  @Autowired
  private TrataRepository trataRepository;
  @Autowired
  private TrataTrackRepository trataTackRepo;
  @Autowired
  private TrataAuditoriaRepository trataAuditoriaRepository;
  @Autowired
  private TipoDatoPropioRepository tipoDatoPropioRepository;
  @Autowired
  private ITipoComboRepository tipoComboDAO;
  @Autowired
  private DatosVariablesComboGruposRepository datosVariablesComboGruposRepository;
  @Autowired
  private ITrataDiferencialRepository trataDiferencialDAO;
  @Autowired
  private ExpedienteSadeService expedienteSadeService;
  @Autowired
  private TrataTrackRepository trataTrackRepository;
  @Autowired
  private TrataTipoResultadoRepository trataTipoResultadoRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Value("${flag.crear.tramite}")
  private int FLAG_TRAMITE;

  public static final String ACTIVO = "ALTA";
  public static final String DIFERENCIAL = "DIFERENCIAL";
  public static final String NODIFERENCIAL = "NO DIFERENCIAL";

  private static final Logger logger = LoggerFactory.getLogger(TrataServiceImpl.class);
  private static final String TRATA_SIN_DESCRIPCION = "";

  @SuppressWarnings("unchecked")
  public List<TrataDTO> buscarTratasByEstado(String estado) {
    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.findByEstado(estado);
    
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
      Collections.<TrataDTO>sort(retorno, new ComparadorTrata());
    }

    return retorno;
  }

  @SuppressWarnings("unchecked")
  public List<TrataDTO> buscarTratasManuales(Boolean manual) {
    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.findByEsManualAndEstado(manual, ACTIVO);
    
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
      Collections.<TrataDTO>sort(retorno, new ComparadorTrata());
    }
    
    return retorno;
  }

  @Override
  public TrataDTO buscarTrataporId(Long idTrata) {

    TrataDTO retorno = null;
    Trata resultado = this.trataRepository.findOne(idTrata);

    if (resultado != null) {
      retorno = mapper.map(resultado, TrataDTO.class);
    }

    return retorno;
  }

  @SuppressWarnings("unchecked")
  public List<TrataDTO> buscarTratasManualesYTipoCaratulacion(Boolean manual, Boolean esInterno) {
    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.buscarTratasManualesYTipoCaratulacion(manual,
        ACTIVO, esInterno, !esInterno);
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
      Collections.<TrataDTO>sort(retorno, new ComparadorTrata());
    }

    return retorno;
  }

  public void eliminarTrata(TrataDTO trata, String userName) {
    this.trataRepository.delete(trata.getId());
    
    TrataAuditoriaDTO trataAuditoria = new TrataAuditoriaDTO(trata, userName);
    trataAuditoria.setTipoOperacion(TrataAuditoriaDTO.BAJA);
    TrataAuditoria trataAduEnti = mapper.map(trataAuditoria, TrataAuditoria.class);
    trataAuditoriaRepository.save(trataAduEnti);
  }

  public void modificarTrata(TrataDTO trata, String userName) {
    Trata trataEnty = mapper.map(trata, Trata.class);
    
    for (TrataReparticion trataReparticion : trataEnty.getReparticionesTrata()) {
  		trataReparticion.setPk(new TrataReparticionPK(trata.getId(), trataReparticion.getOrden()));
  	}
    
    for (TrataTipoResultado trataTipoResultado : trataEnty.getTipoResultadosTrata()) {
      trataTipoResultado.setPk(new TrataTipoResultadoPK(trata.getId(), trataTipoResultado.getClave()));
      trataTipoResultado.setProperty(null);
    }
    
    this.trataRepository.save(trataEnty);

    TrataAuditoriaDTO trataAuditoria = new TrataAuditoriaDTO(trata, userName);
    trataAuditoria.setTipoOperacion(TrataAuditoriaDTO.MOD);
    TrataAuditoria trataAduEnti = mapper.map(trataAuditoria, TrataAuditoria.class);
    trataAuditoriaRepository.save(trataAduEnti);

  }

  public List<MetadataDTO> buscarDatosVariablesPorTrata(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosVariablesPorTrata(trata={}) - start", trata);
    }
    Trata trataEnty = this.trataRepository.findOne(trata.getId());
    TrataDTO trataDTO = mapper.map(trataEnty, TrataDTO.class);
    List<MetadataDTO> returnList = trataDTO.getDatoVariable();
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosVariablesPorTrata(Trata) - end - return value={}", returnList);
    }
    return returnList;
  }

  public void guardarDatosVariablesDeTrata(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarDatosVariablesDeTrata(trata={}) - start", trata);
    }
    Trata trataEnti = mapper.map(trata, Trata.class);
    
    for (TrataReparticion trataReparticion : trataEnti.getReparticionesTrata()) {
		trataReparticion.setPk(new TrataReparticionPK(trataReparticion.getIdTrata(), trataReparticion.getOrden()));
	}
    
    this.trataRepository.save(trataEnti);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarDatosVariablesDeTrata(Trata) - end");
    }
  }

  public TrataDTO buscarTrataByCodigo(String codigoTrata) {
    TrataDTO retorno = null;
    Trata resultado = trataRepository.findByCodigoTrata(codigoTrata);
    
    if (resultado != null) {
      retorno = mapper.map(resultado, TrataDTO.class);
    }
    
    return retorno;
  }

  public Boolean esTrataUtilizadaEnEE(String trataSade) {
    Trata resultado = trataRepository.findByEstadoAndCodigoTrata(TrataDTO.ACTIVO, trataSade);
    if (resultado != null) {
      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  public void darAltaTrata(TrataDTO trataDTO, String userName) {
    if (logger.isDebugEnabled()) {
      logger.debug("darAltaTrata(trataSade={}, userName={}) - start", trataDTO, userName);
    }

    if (trataDTO.getCodigoTrata() == null) {
      throw new IllegalAccessError(Labels.getLabel("ee.nuevaTrata.codigoInvalido"));
    }

    if (this.buscarTrataByCodigo(trataDTO.getCodigoTrata()) != null) {
      throw new IllegalAccessError(Labels.getLabel("ee.nuevaTrata.trataYaExiste"));
    } else {
      TrataReparticionDTO todas = new TrataReparticionDTO();
      todas.setCodigoReparticion(TrataReparticionDTO.TODAS);
      todas.setHabilitacion(true);
      todas.setOrden(1);
      todas.setReserva(false);
      trataDTO.getReparticionesTrata().add(todas);
      trataDTO.setEstado(TrataDTO.ACTIVO);
      
      Trata trataEnty = mapper.map(trataDTO, Trata.class);
      
      for (TrataReparticion trataReparticion : trataEnty.getReparticionesTrata()) {
    	  trataReparticion.setPk(new TrataReparticionPK(trataReparticion.getIdTrata(), trataReparticion.getOrden()));
      }
      
      // Los tipos de resultados se guardaran en forma posterior al ingreso de la Trata
      // (se intento guardar con Cascade.ALL, pero no hubo caso)
      trataEnty.getTipoResultadosTrata().clear();
      
      this.trataRepository.save(trataEnty);
      trataDTO.setId(trataEnty.getId());
      
      // Se guardan los tipos de resultado
      List<TrataTipoResultado> listTrataTipoResultado = new ArrayList<>();
      listTrataTipoResultado.addAll(ListMapper.mapList(trataDTO.getTipoResultadosTrata(), mapper, TrataTipoResultado.class));
      
      for (TrataTipoResultado trataTipoResultado : listTrataTipoResultado) {
        trataTipoResultado.setPk(new TrataTipoResultadoPK(trataEnty.getId(), trataTipoResultado.getClave()));
      }
      
      trataTipoResultadoRepository.save(listTrataTipoResultado);
      
      // Auditoria
      TrataAuditoriaDTO trataAuditoria = new TrataAuditoriaDTO(trataDTO, userName);
      trataAuditoria.setTipoOperacion(TrataAuditoriaDTO.ALTA);

      TrataAuditoria trataAduEnti = mapper.map(trataAuditoria, TrataAuditoria.class);
      trataAuditoriaRepository.save(trataAduEnti);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("darAltaTrata(Trata, String) - end");
    }
  }

  /**
   * Este Metodo solo se debe Usar en el composer de Crear Trata Smuzychu
   */
  public List<TrataDTO> obtenerListaTodasLasTratas() {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerListaTodasLasTratas() - start");
    }
    List<TrataDTO> retorno = new ArrayList<>();
    List<TrataTrack> resultado = this.trataTackRepo
        .findByVigenciaDesdeAndVigenciaHastaAndEstadoRegistro(new Date(), new Date(), true);
    if (resultado != null) {
      for (TrataTrack t : resultado) {
        TrataDTO dub = new TrataDTO();
        dub.setCodigoTrata(t.getCodigoExtracto());
        dub.setDescripcion(t.getDescripcionExtracto());
        retorno.add(dub);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerListaTodasLasTratas() - end - return value={}", retorno);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  public List<TrataDTO> buscarTratasByTipoCaratulacion(Boolean esInterno, Boolean esExterno) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasByTipoCaratulacion(esInterno={}, esExterno={}) - start", esInterno,
          esExterno);
    }

    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.findByEsInternoAndEsExterno(esInterno, esExterno);
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
      Collections.<TrataDTO>sort(retorno, new ComparadorTrata());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasByTipoCaratulacion(Boolean, Boolean) - end - return value={}",
          retorno);
    }
    return retorno;
  }

  public TrataDTO obtenerTrataByIdTrataSugerida(Long idTrataSugerida) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTrataByIdTrataSugerida(idTrataSugerida={}) - start", idTrataSugerida);
    }
    TrataDTO retorno = null;
    Trata resultado = this.trataRepository.findOne(idTrataSugerida);
    if (resultado != null) {
      retorno = mapper.map(resultado, TrataDTO.class);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTrataByIdTrataSugerida(Integer) - end - return value={}", retorno);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  public List<TrataDTO> buscarTotalidadTratasEE() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTotalidadTratasEE() - start");
    }
    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.findAll();
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTotalidadTratasEE() - end - return value={}", retorno);
    }
    return retorno;
  }

  @Override
  public String obtenerDescripcionTrataByCodigo(String codigoTrata) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDescripcionTrataByCodigo(codigoTrata={}) - start", codigoTrata);
    }
    String retorno = TRATA_SIN_DESCRIPCION;
    TrataDTO resultado = this.buscarTrataByCodigo(codigoTrata);
    if (resultado != null) {
      retorno = resultado.getDescripcion();
    }
    return retorno;

  }

  @Override
  public String formatoToStringTrata(String codigo, String descrip) {
    if (logger.isDebugEnabled()) {
      logger.debug("formatoToStringTrata(codigo={}, descrip={}) - start", codigo, descrip);
    }

    String salida;

    if (codigo == null) {
      codigo = StringUtils.EMPTY;
    }
    if (descrip == null) {
      descrip = StringUtils.EMPTY;
    }
    if (StringUtils.EMPTY.equals(codigo.trim()) && StringUtils.EMPTY.equals(descrip.trim())) {
      salida = StringUtils.EMPTY;
    } else {
      salida = codigo + " - " + descrip;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("formatoToStringTrata(String, String) - end - return value={}", salida);
    }
    return salida;
  }

  @SuppressWarnings("unchecked")
  public List<TipoDatoPropioDTO> buscarTiposDatosPropios() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTiposDatosPropios() - start");
    }
    List<TipoDatoPropioDTO> retorno = new ArrayList<>();
    List<TipoDatoPropio> resultado = tipoDatoPropioRepository.findAll();
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TipoDatoPropioDTO.class));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTiposDatosPropios() - end - return value={}", retorno);
    }
    return retorno;
  }

  /**
   * Metodo de busqueda de datos propios de trata.
   */
  @SuppressWarnings("unchecked")
public List<DatoPropio> buscarDatosCombo(String nombre) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosCombo(nombre={}) - start", nombre);
    }

    List<DatosVariablesComboGrupos> datosEnti = datosVariablesComboGruposRepository
        .findByTipo(DIFERENCIAL);

    List<DatosVariablesComboGruposDTO> gruposTratasDif = ListMapper.mapList(datosEnti, mapper,
        DatosVariablesComboGruposDTO.class);

    for (int i = 0; i < gruposTratasDif.size(); i++) {

      if (nombre.equals(gruposTratasDif.get(i).getNombreGrupo())) {
        int idEstructura;
        if (nombre.equalsIgnoreCase(ConstantesWeb.JUSTICIA_CABA)) {
          idEstructura = ConstantesWeb.ID_ESTRUCTURA_JUSTICIA_CABA;
          List<DatoPropio> returnList = buscarDatosPropiosDeReparticiones(idEstructura);
          if (logger.isDebugEnabled()) {
            logger.debug("buscarDatosCombo(String) - end - return value={}", returnList);
          }
          return returnList;
        } else {
          idEstructura = ConstantesWeb.ID_ESTRUCTURA_JUSTICIA_NACIONAL_Y_PROVINCIAL;
          List<DatoPropio> returnList = buscarDatosPropiosDeReparticiones(idEstructura);
          if (logger.isDebugEnabled()) {
            logger.debug("buscarDatosCombo(String) - end - return value={}", returnList);
          }
          return returnList;
        }
      }
    }
    List<DatoPropio> returnList = buscarDatosPropios(nombre);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosCombo(String) - end - return value={}", returnList);
    }
    return returnList;
  }

  private List<DatoPropio> buscarDatosPropiosDeReparticiones(int idEstructura) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosPropiosDeReparticiones(idEstructura={}) - start", idEstructura);
    }

    List<DatoPropio> listDatoPropio = new ArrayList<DatoPropio>();
    Date date = new Date();
    List<ReparticionDTO> listReparticiones = (List<ReparticionDTO>) expedienteSadeService
        .buscarReparticiones(idEstructura);

    for (int i = 0; i < listReparticiones.size(); i++) {

      DatoPropio datoPropio = new DatoPropio();
      ReparticionDTO reparticion = (ReparticionDTO) listReparticiones.get(i);

      datoPropio.setId(reparticion.getId());

      datoPropio.setGrupoId(listReparticiones.get(i).getIdEstructura());

      String nombreReparticion = listReparticiones.get(i).getNombreReparticion();
      datoPropio.setNombre(nombreReparticion);

      Calendar calendarVigenciaDesde = Calendar.getInstance();
      calendarVigenciaDesde.setTime(listReparticiones.get(i).getVigenciaDesde());

      Calendar sysDate = Calendar.getInstance();
      sysDate.setTime(date);

      Calendar calendarVigenciaHasta = Calendar.getInstance();
      calendarVigenciaHasta.setTime(listReparticiones.get(i).getVigenciaHasta());

      if (calendarVigenciaDesde.before(sysDate) && calendarVigenciaHasta.after(sysDate)) {
        datoPropio.setEstado("Alta");
      } else {
        datoPropio.setEstado("Baja");
      }

      listDatoPropio.add(i, datoPropio);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosPropiosDeReparticiones(int) - end - return value={}",
          listDatoPropio);
    }
    return listDatoPropio;
  }

  @SuppressWarnings("unchecked")
  private List<DatoPropio> buscarDatosPropios(String nombre) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosPropios(nombre={}) - start", nombre);
    }

    List<DatoPropio> listDatoPropio = new ArrayList<DatoPropio>();

    List<TipoCombo> tipoComboEnt = tipoComboDAO.buscarDatosCombo(nombre);

    List<TipoComboDTO> listTipoCombo = ListMapper.mapList(tipoComboEnt, mapper,
        TipoComboDTO.class);

    for (int i = 0; i < listTipoCombo.size(); i++) {
      DatoPropio datoPropio = new DatoPropio();
      TipoComboDTO tipoCombo = listTipoCombo.get(i);
      datoPropio.setId(tipoCombo.getId().intValue());
      datoPropio.setGrupoId(tipoCombo.getGrupo());
      datoPropio.setNombre(tipoCombo.getValorOpcion());

      if (tipoCombo.getFechaBaja() == null) {
        datoPropio.setEstado("Alta");
      } else {
        datoPropio.setEstado("Baja");
      }
      listDatoPropio.add(i, datoPropio);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatosPropios(String) - end - return value={}", listDatoPropio);
    }
    return listDatoPropio;
  }

  /**
   * Búsqueda de los datos variables de trata.
   */
  @SuppressWarnings("unchecked")
  public List<DatosVariablesComboGruposDTO> buscarComboGruposPorTipo(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComboGruposPorTipo(trata={}) - start", trata);
    }

    if (esTrataDiferencial(trata.getCodigoTrata())) {

      List<DatosVariablesComboGrupos> datosEnti = datosVariablesComboGruposRepository
          .findByTipo(DIFERENCIAL);

      List<DatosVariablesComboGruposDTO> returnList = ListMapper.mapList(datosEnti, mapper,
          DatosVariablesComboGruposDTO.class);

      if (logger.isDebugEnabled()) {
        logger.debug("buscarComboGruposPorTipo(Trata) - end - return value={}", returnList);
      }
      return returnList;

    } else {

      List<DatosVariablesComboGrupos> datosEnti = datosVariablesComboGruposRepository
          .findByTipo(NODIFERENCIAL);

      List<DatosVariablesComboGruposDTO> returnList = ListMapper.mapList(datosEnti, mapper,
          DatosVariablesComboGruposDTO.class);

      if (logger.isDebugEnabled()) {
        logger.debug("buscarComboGruposPorTipo(Trata) - end - return value={}", returnList);
      }
      return returnList;
    }
  }

  /**
   * Valida que sea trata diferencial
   * 
   * @param codigoTrata
   * @return
   */
  private boolean esTrataDiferencial(String codigoTrata) {
    if (logger.isDebugEnabled()) {
      logger.debug("esTrataDiferencial(codigoTrata={}) - start", codigoTrata);
    }

    TrataDiferencial trasEnt = this.trataDiferencialDAO.findByCodigoTrata(codigoTrata);

    TrataDiferencialDTO trataDiferencial = mapper.map(trasEnt, TrataDiferencialDTO.class);

    boolean returnboolean = trataDiferencial != null;
    if (logger.isDebugEnabled()) {
      logger.debug("esTrataDiferencial(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TrataDTO> obtenerTodasLasTratasConDescripcion() {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodasLasTratasConDescripcion() - start");
    }

    List<TrataDTO> respuesta = new ArrayList<>();

    List<Trata> resultado = this.trataRepository.findAll();
    if (resultado != null) {
      for (Trata trata : resultado) {
        if (trata.getDescripcion() == null) {

          TrataTrack trataTrackEnti = this.trataTackRepo
              .findBycodigoExtracto(trata.getCodigoTrata());
          String descripcion = trataTrackEnti.getCodigoExtracto();
          trata.setDescripcion(descripcion != null ? descripcion : "");
        }
      }
    }

    respuesta.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodasLasTratasConDescripcion() - end - return value={}", respuesta);
    }
    return respuesta;

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TrataDTO> buscarTratasByEstadoYTipoCaratulacion(String estado, Boolean esInterno) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasByEstadoYTipoCaratulacion(estado={}, esInterno={}) - start",
          estado, esInterno);
    }

    List<TrataDTO> retorno = new ArrayList<>();
    List<Trata> resultado = this.trataRepository.findByEstadoAndEsInternoAndEsExterno(estado,
        esInterno, !esInterno);
    if (resultado != null) {
      retorno.addAll(ListMapper.mapList(resultado, mapper, TrataDTO.class));
      Collections.<TrataDTO>sort(retorno, new ComparadorTrata());
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarTratasByEstadoYTipoCaratulacion(String, Boolean) - end - return value={}",
          retorno);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void actualizarReparticionCaratuladoraYRectora(String codigoReparticionViejo,
      String codigoReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarReparticionCaratuladoraYRectora(codigoReparticionViejo={}, codigoReparticion={}) - start",
          codigoReparticionViejo, codigoReparticion);
    }

    List<Trata> resultado = trataRepository.findAll();
    /*
     * Se podría omitir esta lista temporal y trabajar solo con las entidades,
     * pero reemplazarReparticion() es un método del DTO. Podría dejarse aquí en
     * el método. REVISAR
     */

    List<TrataDTO> listaTemporal;
    if (resultado != null) {
      listaTemporal = ListMapper.mapList(resultado, mapper, TrataDTO.class);
      for (TrataDTO t : listaTemporal) {
        if (t.reemplazarReparticion(codigoReparticionViejo, codigoReparticion)
            || t.reemplazarReparticionRectora(codigoReparticionViejo, codigoReparticion)) {

          Trata trataEnti = mapper.map(t, Trata.class);
          for (TrataReparticion trataReparticion : trataEnti.getReparticionesTrata()) {
        	trataReparticion.setPk(new TrataReparticionPK(trataReparticion.getIdTrata(), trataReparticion.getOrden()));
          }
          
          trataRepository.save(trataEnti);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarReparticionCaratuladoraYRectora(String, String) - end");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean reparticionUsuarioTienePermisoDeReserva(String codigoReparticion) {
    List<Trata> resultado = trataRepository.findAll();
    /*
     * Se podría omitir esta lista temporal y trabajar solo con las entidades,
     * pero codigoReparticionTienePermisoDeReserva() es un método del DTO.
     * Podría dejarse aquí en el método. REVISAR
     */

    List<TrataDTO> listaTemporal;
    if (resultado != null) {
      listaTemporal = ListMapper.mapList(resultado, mapper, TrataDTO.class);
      for (TrataDTO t : listaTemporal) {
        if (t.codigoReparticionTienePermisoDeReserva(codigoReparticion)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public int buscarFlagCreacionTramite() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarFlagCreacionTramite() - start");
    }

    int resultado = FLAG_TRAMITE;

    if (logger.isDebugEnabled()) {
      logger.debug("buscarFlagCreacionTramite() - end - return value={}", resultado);
    }
    return resultado;
  }

  @Override
  public String obtenerDescripcionTrataSADE(String codigoExtracto) {
    String retorno = StringUtils.EMPTY;
    TrataTrack trataTrack = trataTrackRepository.findBycodigoExtracto(codigoExtracto);
    if (null != trataTrack) {
      retorno = trataTrack.getCodigoExtracto();
    }
    return retorno;
  }

  @Override
  public boolean isTipoResultadoEnUso(String propertyTipoResultado) {
    if (logger.isDebugEnabled()) {
      logger.debug("isTipoResultadoEnUso(propertyTipoResultado={}) - start", propertyTipoResultado);
    }
    
    List<TrataTipoResultado> result = trataTipoResultadoRepository.findByClave(propertyTipoResultado);
    boolean isEnUso = !result.isEmpty();
    
    if (logger.isDebugEnabled()) {
      logger.debug("isTipoResultadoEnUso() - end - return value={}", isEnUso);
    }
    
    return isEnUso;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TrataDTO> buscarTratasEEByTipoSubproceso() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasEEByTipoSubproceso() - start");
    }
    
    List<TrataDTO> listTrataDTO = new ArrayList<>();
    
    List<String> trataTypes = new ArrayList<>();
    trataTypes.add(TipoTramiteEnum.SUBPROCESO.getValue());
    trataTypes.add(TipoTramiteEnum.AMBOS.getValue());
    
    List<Trata> listTrataEntity = trataRepository.findByTipoTramiteIn(trataTypes);
    
    if (listTrataEntity != null && !listTrataEntity.isEmpty()) {
      listTrataDTO.addAll(ListMapper.mapList(listTrataEntity, mapper, TrataDTO.class));
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasEEByTipoSubproceso() - end - return value={}", listTrataDTO);
    }
    
    return listTrataDTO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TrataDTO> buscarTratasByTipoExpediente(boolean manual) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasByTipoExpediente(manual={}) - start", manual);
    }
    
    List<TrataDTO> listTrataDTO = new ArrayList<>();
    
    List<String> trataTypes = new ArrayList<>();
    trataTypes.add(TipoTramiteEnum.EXPEDIENTE.getValue());
    trataTypes.add(TipoTramiteEnum.AMBOS.getValue());
    
    List<Trata> listTrataEntity = trataRepository.findByTipoTramiteInAndEsManual(trataTypes, manual);
    
    if (listTrataEntity != null && !listTrataEntity.isEmpty()) {
      listTrataDTO.addAll(ListMapper.mapList(listTrataEntity, mapper, TrataDTO.class));
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasByTipoExpediente() - end - return value={}", listTrataDTO);
    }
    
    return listTrataDTO;
  }

}