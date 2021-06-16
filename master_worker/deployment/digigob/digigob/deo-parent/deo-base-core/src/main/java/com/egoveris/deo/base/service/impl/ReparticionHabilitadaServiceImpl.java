package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.ReparticionHabilitada;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.base.repository.ReparticionHabilitadaRepository;
import com.egoveris.deo.base.service.NumeracionEspecialService;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReparticionHabilitadaServiceImpl implements ReparticionHabilitadaService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ReparticionHabilitadaServiceImpl.class);

  @Autowired
  private NumeracionEspecialService numeracionEspecialService;
  @Autowired
  private ReparticionHabilitadaRepository reparticionHabilitadaRepo;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private IUsuarioService usuarioService;
  
  private String codEcosistema = null;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;
  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.ReparticionHabilitadaService#
   * cargarReparticionesHabilitadas(com.egoveris.deo.core.api.satra.dominio.
   * TipoDocumento)
   */
  public Set<ReparticionHabilitadaDTO> cargarReparticionesHabilitadas(
      TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cargarReparticionesHabilitadas(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    Set<ReparticionHabilitadaDTO> reparticionesHabilitadas = new TreeSet<>();

    for (ReparticionHabilitadaDTO reparticion : tipoDocumento.getListaReparticiones()) {
      ReparticionHabilitadaDTO reparticionHabilitada = new ReparticionHabilitadaDTO();
      reparticionHabilitada.setId(reparticion.getId());
      reparticionHabilitada.setCodigoReparticion(reparticion.getCodigoReparticion());
      reparticionHabilitada.setPermisoFirmar(reparticion.getPermisoFirmar());
      reparticionHabilitada.setPermisoIniciar(reparticion.getPermisoIniciar());
      reparticionHabilitada.setEstado(reparticion.getEstado());
      reparticionHabilitada.setTipoDocumento(tipoDocumento);
      reparticionHabilitada.setNumeracionEspecial(new NumeracionEspecialDTO());
      reparticionHabilitada.setEdicionNumeracionEspecial(tipoDocumento.getEsEspecial());
      reparticionesHabilitadas.add(reparticionHabilitada);
    }
    if (tipoDocumento.getEsEspecial()) {
      this.obtenerInformacionReparticiones(reparticionesHabilitadas);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cargarReparticionesHabilitadas(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return reparticionesHabilitadas;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.TipoDocumentoService#
   * obtenerNumerosEspecialesReparticiones(java.util.List,
   * com.egoveris.deo.core.api.satra.dominio.TipoDocumento)
   */
  public void obtenerInformacionReparticiones(
      Set<ReparticionHabilitadaDTO> reparticionesHabilitadas) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerInformacionReparticiones(Set<ReparticionHabilitadaDTO>) - start"); //$NON-NLS-1$
    }

    for (ReparticionHabilitadaDTO reparticionHabilitada : reparticionesHabilitadas) {
      NumeracionEspecialDTO numeracionEspecial = numeracionEspecialService
          .buscarUltimoNumeroEspecial(reparticionHabilitada.getCodigoReparticion(),
              reparticionHabilitada.getTipoDocumento(), null);
      if (numeracionEspecial != null) {
        reparticionHabilitada.getNumeracionEspecial().setId(numeracionEspecial.getId());
        reparticionHabilitada.getNumeracionEspecial().setNumero(numeracionEspecial.getNumero());
        reparticionHabilitada.getNumeracionEspecial()
            .setNumeroInicial(numeracionEspecial.getNumeroInicial() != null
                ? numeracionEspecial.getNumeroInicial() : 0);
        reparticionHabilitada.getNumeracionEspecial().setAnio(numeracionEspecial.getAnio());
        reparticionHabilitada.setEdicionNumeracionEspecial(true);
//        if (!codEcosistema.trim().isEmpty()) {
//          reparticionHabilitada.getNumeracionEspecial().setCodigoEcosistema(codEcosistema);
//        }
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerInformacionReparticiones(Set<ReparticionHabilitadaDTO>) - end"); //$NON-NLS-1$
    }
  }

  public Boolean existeReparticion(Map<String, Object> parametrosConsulta)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeReparticion(Map<String,Object>) - start"); //$NON-NLS-1$
    }
    List<ReparticionHabilitada> result = reparticionHabilitadaRepo
        .findByCodigoReparticionAndTipoDocumento((String) parametrosConsulta.get("reparticion"),
            (TipoDocumento) parametrosConsulta.get("tipoDocumento"));
    Boolean returnBoolean = !result.isEmpty();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeReparticion(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnBoolean;
  }

  public List<ReparticionHabilitadaDTO> buscarReparticones(String reparticion)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticones(String) - start"); //$NON-NLS-1$
    }

    List<ReparticionHabilitadaDTO> returnList = ListMapper.mapList(
        reparticionHabilitadaRepo.findByCodigoReparticion(reparticion), this.mapper,
        ReparticionHabilitadaDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticones(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  // FIXME MULTIREPARTICION
  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.ReparticionHabilitadaService#
   * validarPermisoReparticion(com.egoveris.deo.core.api.satra.dominio.
   * TipoDocumento, java.lang.String, java.lang.String)
   */
  public boolean validarPermisoReparticion(TipoDocumentoDTO tipoDocumento, String user,
      String permiso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarPermisoReparticion(TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    Boolean tienePermiso = true;
    List<ReparticionHabilitadaDTO> listaReparticiones = null;
    TipoDocumento td = this.mapper.map(tipoDocumento, TipoDocumento.class);
    List<ReparticionHabilitada> reparticiones = reparticionHabilitadaRepo
        .findByTipoDocumentoAndEstado(td, true);
    if (reparticiones != null) {
      listaReparticiones = ListMapper.mapList(reparticiones, this.mapper,
          ReparticionHabilitadaDTO.class);
    }
    tienePermiso = tienePermisoReparticionUsuario(listaReparticiones, user, permiso);

    // Si la repartición no se encuentra entre las reparticiones habilitadas, se
    // validan los permisos de todas.
    if (tienePermiso == null) {
      tienePermiso = tienePermisoTodasReparticiones(listaReparticiones, permiso);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarPermisoReparticion(TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return tienePermiso;
  }

  public boolean validarPermisosUsuariosDeSuListaReparticiones(TipoDocumentoDTO tipoDocumento,
      String user, String permiso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "validarPermisosUsuariosDeSuListaReparticiones(TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    Boolean tienePermiso = true;
    List<ReparticionHabilitadaDTO> listaReparticiones = ListMapper.mapList(
        reparticionHabilitadaRepo.findByTipoDocumentoAndEstado(
            this.mapper.map(tipoDocumento, TipoDocumento.class), true),
        this.mapper, ReparticionHabilitadaDTO.class);

    tienePermiso = tienePermisoReparticionesDelUsuario(listaReparticiones, user, permiso);

    // Si la repartición no se encuentra entre las reparticiones habilitadas, se
    // validan los permisos de todas.
    if (tienePermiso == null) {
      tienePermiso = tienePermisoTodasReparticiones(listaReparticiones, permiso);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "validarPermisosUsuariosDeSuListaReparticiones(TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return tienePermiso;
  }

  /**
   * Valida los permisos que tiene una repartición determinada
   * 
   * @param reparticiones:
   *          Lista de reparticiones habilitada para un tipo de documento
   *          determinado
   * @param user:
   *          Usuario que tiene una reparticion seleccionada que quiere validar
   *          con la lista de reparticiones del tipo de docuemento
   * @param permiso:
   *          Permiso que se requiere validar.
   * @return true si la repartición del usuario está en la lista y tiene el
   *         permiso indicado, false, si la repartición está en la lista y no
   *         tiene el permiso indicado, null si la repartición no se encuentra
   *         en la lista.
   */
  // FIXME MULTIREPARTICION
  private Boolean tienePermisoReparticionUsuario(List<ReparticionHabilitadaDTO> reparticiones,
      String user, String permiso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "tienePermisoReparticionUsuario(Set<ReparticionHabilitadaDTO>, String, String) - start"); //$NON-NLS-1$
    }

    Boolean tienePermiso = null;
    Usuario usuario = null;
    try {
      usuario = usuarioService.obtenerUsuario(user);
      if (usuario != null) {
        for (ReparticionHabilitadaDTO reparticionHabilitada : reparticiones) {
          if (reparticionHabilitada.getCodigoReparticion().trim()
              .compareTo(usuario.getCodigoReparticion().trim()) == 0) {
            if (permiso.compareTo(Constantes.REPARTICION_PERMISO_INICIAR) == 0) {
              tienePermiso = reparticionHabilitada.getPermisoIniciar();
            }
            if (permiso.compareTo(Constantes.REPARTICION_PERMISO_FIRMAR) == 0) {
              tienePermiso = reparticionHabilitada.getPermisoFirmar();
            }
            break;
          }
        }
      }
    } catch (SecurityNegocioException e) {
      LOGGER.error("Mensaje de error", e);
    }

    /// escribir preg firma conjunta y todos con todos

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "tienePermisoReparticionUsuario(Set<ReparticionHabilitadaDTO>, String, String) - end"); //$NON-NLS-1$
    }
    return tienePermiso;
  }

  private Boolean tienePermisoReparticionesDelUsuario(List<ReparticionHabilitadaDTO> reparticiones,
      String user, String permiso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "tienePermisoReparticionesDelUsuario(Set<ReparticionHabilitadaDTO>, String, String) - start"); //$NON-NLS-1$
    }

    Boolean tienePermiso = null;
    Boolean permite = false;
    List<String> reparticionesUsuario = null;
    Usuario usuario;
    try {
      usuario = usuarioService.obtenerUsuario(user);
      if (usuario.getIsMultireparticion() != null && usuario.getIsMultireparticion() != false) {
        reparticionesUsuario = usuarioService.obtenerReparticionesHabilitadasPorUsuario(user);
        reparticionesUsuario.add(usuario.getCodigoReparticionOriginal());
        for (String repUsuario : reparticionesUsuario) {
          if (!permite) {
            for (ReparticionHabilitadaDTO reparticionHabilitada : reparticiones) {
              if (reparticionHabilitada.getCodigoReparticion().trim()
                  .compareTo(repUsuario.trim()) == 0) {
                if (permiso.compareTo(Constantes.REPARTICION_PERMISO_INICIAR) == 0) {
                  tienePermiso = reparticionHabilitada.getPermisoIniciar();
                  permite = tienePermiso;
                }
                if (permiso.compareTo(Constantes.REPARTICION_PERMISO_FIRMAR) == 0) {
                  tienePermiso = reparticionHabilitada.getPermisoFirmar();
                  permite = tienePermiso;
                }
                break;
              }
            }
          }
        }
      } else {

        for (ReparticionHabilitadaDTO reparticionHabilitada : reparticiones) {
          if (null != usuario.getCodigoReparticionOriginal() && reparticionHabilitada.getCodigoReparticion().trim()
              .compareTo(usuario.getCodigoReparticionOriginal().trim()) == 0) {
            if (permiso.compareTo(Constantes.REPARTICION_PERMISO_INICIAR) == 0) {
              tienePermiso = reparticionHabilitada.getPermisoIniciar();
            }
            if (permiso.compareTo(Constantes.REPARTICION_PERMISO_FIRMAR) == 0) {
              tienePermiso = reparticionHabilitada.getPermisoFirmar();
            }
            break;
          }
        }

      }
    } catch (SecurityNegocioException e) {
      LOGGER.error("Mensaje de error", e);
    }

    /// escribir preg firma conjunta y todos con todos

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "tienePermisoReparticionesDelUsuario(Set<ReparticionHabilitadaDTO>, String, String) - end"); //$NON-NLS-1$
    }
    return tienePermiso;
  }

  /**
   * Valida los permisos de la repartición identificado con el código "TODAS",
   * que representa los permisos sobre todas las reparticiones.
   * 
   * @param reparticiones:
   *          Lista de reparticiones habilitada para un tipo de documento
   *          determinado
   * @param permiso:
   *          Permiso que se requiere validar.
   * @return true si la repartición "TODAS" tiene permiso, false en caso
   *         contrario.
   */
  private Boolean tienePermisoTodasReparticiones(List<ReparticionHabilitadaDTO> reparticiones,
      String permiso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("tienePermisoTodasReparticiones(Set<ReparticionHabilitadaDTO>, String) - start"); //$NON-NLS-1$
    }

    Boolean tienePermiso = false;
    for (ReparticionHabilitadaDTO reparticionHabilitada : reparticiones) {
      if (reparticionHabilitada.getCodigoReparticion()
          .compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        if (permiso.compareTo(Constantes.REPARTICION_PERMISO_INICIAR) == 0) {
          tienePermiso = reparticionHabilitada.getPermisoIniciar();
        }
        if (permiso.compareTo(Constantes.REPARTICION_PERMISO_FIRMAR) == 0) {
          tienePermiso = reparticionHabilitada.getPermisoFirmar();
        }
        break;
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("tienePermisoTodasReparticiones(Set<ReparticionHabilitadaDTO>, String) - end"); //$NON-NLS-1$
    }
    return tienePermiso;
  }

  public boolean validacionParaIniciar(TipoDocumentoDTO tipoDocumento, String user) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validacionParaIniciar(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    List<ReparticionHabilitadaDTO> listaReparticiones = ListMapper.mapList(
        reparticionHabilitadaRepo.findByTipoDocumentoAndEstado(
            this.mapper.map(tipoDocumento, TipoDocumento.class), true),
        this.mapper, ReparticionHabilitadaDTO.class);
    List<String> reparticionesUsuario = null;
    try {
      reparticionesUsuario = usuarioService.obtenerReparticionesHabilitadasPorUsuario(user);
    } catch (SecurityNegocioException e) {
      LOGGER.error("Mensaje de error", e);
    }

    Boolean permiteIniciarLaReparticion = null;
    int contadorDePermisoInicio = 0;

    for (String codigoReparticionUser : reparticionesUsuario) {

      for (ReparticionHabilitadaDTO b : listaReparticiones) {
        if (b.getCodigoReparticion().equals(codigoReparticionUser)) {
          permiteIniciarLaReparticion = b.getPermisoIniciar();
        }
        if (b.getPermisoIniciar() && !b.getCodigoReparticion().equals(codigoReparticionUser)) {
          contadorDePermisoInicio = 1;
        }
      }
    }

    if (permiteIniciarLaReparticion == null || permiteIniciarLaReparticion) {
      // todas las reparticiones para ese tipo de documento tienen permiso de
      // inicio.

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParaIniciar(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
      }
      return true;
    }
    if (contadorDePermisoInicio != 1) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParaIniciar(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
      }
      return true;
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParaIniciar(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
      }
      return false;
    }
  }

  public boolean validacionParafirmar(String acronimoTipoDocumento, String user) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validacionParafirmar(String, String) - start"); //$NON-NLS-1$
    }

    List<String> reparticionesUsuario = null;
    try {
      reparticionesUsuario = usuarioService.obtenerReparticionesHabilitadasPorUsuario(user);
    } catch (SecurityNegocioException e) {
      LOGGER.error("Mensaje de error", e);
    }

    TipoDocumentoDTO tipoDocumento = (TipoDocumentoDTO) tipoDocumentoService
        .buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
    List<ReparticionHabilitadaDTO> listaReparticiones = ListMapper.mapList(
        reparticionHabilitadaRepo.findByTipoDocumentoAndEstado(
            this.mapper.map(tipoDocumento, TipoDocumento.class), true),
        this.mapper, ReparticionHabilitadaDTO.class);
    Boolean permiteFirmaLaReparticion = null;
    int contadorDePermisoFirma = 0;

    for (String codigoReparticionUser : reparticionesUsuario) {

      for (ReparticionHabilitadaDTO b : listaReparticiones) {

        if (b.getCodigoReparticion().compareTo(codigoReparticionUser) == 0) {
          permiteFirmaLaReparticion = b.getPermisoFirmar();
        }
        if (b.getPermisoIniciar()
            && b.getCodigoReparticion().compareTo(codigoReparticionUser) != 0) {
          contadorDePermisoFirma = 1;
        }
      }
    }
    if (permiteFirmaLaReparticion == null || permiteFirmaLaReparticion) {
      // todas las reparticiones para ese tipo de documento tienen permiso de
      // inicio.

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParafirmar(String, String) - end"); //$NON-NLS-1$
      }
      return true;
    }
    if (contadorDePermisoFirma != 1) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParafirmar(String, String) - end"); //$NON-NLS-1$
      }
      return true;
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validacionParafirmar(String, String) - end"); //$NON-NLS-1$
      }
      return false;
    }
  }

  public void eliminarReparticionesTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarReparticionesTipoDocumento(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    reparticionHabilitadaRepo.delete(
        this.mapper.map(tipoDocumento.getListaReparticiones(), ReparticionHabilitada.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarReparticionesTipoDocumento(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

  public void eliminarReparticiones(List<ReparticionHabilitadaDTO> listaReparticiones)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarReparticiones(List<ReparticionHabilitadaDTO>) - start"); //$NON-NLS-1$
    }

    reparticionHabilitadaRepo
        .delete(ListMapper.mapList(listaReparticiones, this.mapper, ReparticionHabilitada.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarReparticiones(List<ReparticionHabilitadaDTO>) - end"); //$NON-NLS-1$
    }
  }

  public void actualizarReparticionHabilitadaTipoDocumento(String reparticionOrigen,
      String reparticionDestino) throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionHabilitadaTipoDocumento(String, String) - start"); //$NON-NLS-1$
    }

    reparticionHabilitadaRepo.actualizarReparticionHabilitadaTipoDocumento(reparticionOrigen,
        reparticionDestino);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionHabilitadaTipoDocumento(String, String) - end"); //$NON-NLS-1$
    }
  }

  public void actualizarReparticionesHabilitadas(List<ReparticionHabilitadaDTO> listaReparticiones)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionesHabilitadas(List<ReparticionHabilitadaDTO>) - start"); //$NON-NLS-1$
    }

    reparticionHabilitadaRepo
        .save(ListMapper.mapList(listaReparticiones, this.mapper, ReparticionHabilitada.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionesHabilitadas(List<ReparticionHabilitadaDTO>) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public List<ReparticionHabilitadaDTO> findByTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    return ListMapper.mapList(
        this.reparticionHabilitadaRepo
            .findByTipoDocumento(this.mapper.map(tipoDocumento, TipoDocumento.class)),
        this.mapper, ReparticionHabilitadaDTO.class);
  }
}