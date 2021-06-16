package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.NumeracionEspecialNoInicializadaException;
import com.egoveris.deo.base.model.NumerosUsados;
import com.egoveris.deo.base.repository.NumerosUsadoRepository;
import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.base.service.NumeracionEspecialService;
import com.egoveris.deo.base.service.ObtenerNumeracionEspecialService;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerNumeracionEspecialServiceImpl implements ObtenerNumeracionEspecialService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ObtenerNumeracionEspecialServiceImpl.class);

  @Autowired
  private NumeracionEspecialService numeracionEspecialService;
  @Autowired
  private NumerosUsadoRepository numeroUsadoRepo;
  @Autowired
  private ReparticionHabilitadaService reparticionesHabilitadaService;
  @Autowired
  private IEcosistemaService ecosistemaService;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public NumeracionEspecialDTO bloquearNumeroEspecial(RequestGenerarDocumento request) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("bloquearNumeroEspecial(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    String anio = UtilsDate.obtenerAnioActual();
    String codigoEcosistema = ecosistemaService.obtenerEcosistema();
    NumeracionEspecialDTO numeracionEspecial;
    if (codigoEcosistema.trim().isEmpty()) {
      bloquearNumero(request, anio);
      numeracionEspecial = this.numeracionEspecialService.buscarNumeracionEspecial(
          request.getCodigoReparticion(), request.getTipoDocumentoGedo(), anio);
    } else {
      bloquearNumeroEcosistema(request, anio, codigoEcosistema);
      numeracionEspecial = this.numeracionEspecialService.buscarNumeracionEspecialEcosistema(
          request.getCodigoReparticion(), request.getTipoDocumentoGedo(), anio, codigoEcosistema);
    }
    LOGGER.debug("Se obtiene lock para " + numeracionEspecial.getNumero() + " usuario: "
        + request.getUsuario());
    /*
     * Se crea un nuevo objeto con los valores del objeto cargado desde la base
     * de datos, por un comportamiento inesperado en el manejo de transacciones,
     * la modificación sobre el objeto numeracionEspecial estaba ocasionando
     * actualización en la base de datos, sin ninguna instrucción explícita.
     * TO_DO: Validar manejo de transacciones.
     */
    return crearProximoNumero(numeracionEspecial);
  }

  private void bloquearNumeroEcosistema(RequestGenerarDocumento request, String anio,
      String codigoEcosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("bloquearNumeroEcosistema(RequestGenerarDocumento, String, String) - start"); //$NON-NLS-1$
    }

    // consultar numero usando ecosistema
    if (!this.numeracionEspecialService.existeNumeradorEcosistema(request.getCodigoReparticion(),
        request.getTipoDocumentoGedo(), anio, codigoEcosistema)) {
      guardarNuevaNumeracionEspecial(request, anio, codigoEcosistema);
    }
    this.numeracionEspecialService.lockNumeroEspecialEcosistema(request.getCodigoReparticion(),
        request.getTipoDocumentoGedo(), anio, codigoEcosistema);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("bloquearNumeroEcosistema(RequestGenerarDocumento, String, String) - end"); //$NON-NLS-1$
    }
  }

  private void bloquearNumero(RequestGenerarDocumento request, String anio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("bloquearNumero(RequestGenerarDocumento, String) - start"); //$NON-NLS-1$
    }

    if (!this.numeracionEspecialService.existeNumerador(request.getCodigoReparticion(),
        request.getTipoDocumentoGedo(), anio)) {
      guardarNuevaNumeracionEspecial(request, anio, null);
    }
    this.numeracionEspecialService.lockNumeroEspecial(request.getCodigoReparticion(),
        request.getTipoDocumentoGedo(), anio);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("bloquearNumero(RequestGenerarDocumento, String) - end"); //$NON-NLS-1$
    }
  }

  private void guardarNuevaNumeracionEspecial(RequestGenerarDocumento request, String anio,
      String ecosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarNuevaNumeracionEspecial(RequestGenerarDocumento, String, String) - start"); //$NON-NLS-1$
    }

    // validar que el tipo de documento tenga firma para ese codigo de
    // reparticion
    if (reparticionesHabilitadaService.validarPermisoReparticion(request.getTipoDocumentoGedo(),
        request.getUsuario(), Constantes.REPARTICION_PERMISO_FIRMAR)) {
      NumeracionEspecialDTO numeroEspecial = new NumeracionEspecialDTO();
      numeroEspecial.setAnio(anio);
      numeroEspecial.setCodigoReparticion(request.getCodigoReparticion());
      numeroEspecial.setNumero(0);
      numeroEspecial.setNumeroInicial(0);
      numeroEspecial.setIdTipoDocumento(request.getTipoDocumentoGedo().getId());
      numeroEspecial.setCodigoEcosistema(ecosistema);
      this.numeracionEspecialService.guardar(numeroEspecial);
    } else {
      throw new NumeracionEspecialNoInicializadaException(request.getCodigoReparticion() + "/"
          + request.getTipoDocumentoGedo().getAcronimo() + "/" + anio
          + " no existe en el registro de nros. especiales. Por favor comunicarse con el Adm. Central");
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarNuevaNumeracionEspecial(RequestGenerarDocumento, String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void actualizarNumerosEspeciales(NumeracionEspecialDTO numeracionEspecial,
      NumerosUsadosDTO numerosUsados) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarNumerosEspeciales(NumeracionEspecialDTO, NumerosUsadosDTO) - start"); //$NON-NLS-1$
    }

    NumerosUsados nUsado = this.numeroUsadoRepo.findByNumeroSADE(numerosUsados.getNumeroSADE());
    nUsado.setEstado("ACTIVO");
    this.numeroUsadoRepo.save(nUsado);
    String codEcosistema = this.ecosistemaService.obtenerEcosistema();
    if (codEcosistema.trim().isEmpty()) {
      this.numeracionEspecialService.grabarNumeracionEspecial(numeracionEspecial);
    } else {
      this.numeracionEspecialService.grabarNumeracionEspecialEcosistema(numeracionEspecial,
          codEcosistema);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarNumerosEspeciales(NumeracionEspecialDTO, NumerosUsadosDTO) - end"); //$NON-NLS-1$
    }
  }

  public void rollbackNumeroEspecial(ResponseGenerarDocumento response) {
    this.numeracionEspecialService.unlockNumeroEspecial(response.getNumeroEspecial());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("rollbackNumeroEspecial(ResponseGenerarDocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void rollbackNumeroEspecial(NumeracionEspecialDTO numeracionEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("rollbackNumeroEspecial(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialService.unlockNumeroEspecial(numeracionEspecial);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("rollbackNumeroEspecial(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void rollbackNumeroEspecialEcosistema(NumeracionEspecialDTO numeracionEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("rollbackNumeroEspecialEcosistema(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialService.unlockNumeroEspecialEcosistema(numeracionEspecial);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("rollbackNumeroEspecialEcosistema(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public List<NumerosUsadosDTO> getNumerosUsados(Integer anio, String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getNumerosUsados(Integer, String) - start"); //$NON-NLS-1$
    }

    List<NumerosUsadosDTO> returnList = ListMapper.mapList(this.numeroUsadoRepo.findByAnioAndCodigoReparticionAndNumeroEspecialIsNotNull(String.valueOf(anio), reparticion), this.mapper, NumerosUsadosDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getNumerosUsados(Integer, String) - end"); //$NON-NLS-1$
    }
    return returnList;

  }

  @Override
  public void guardar(NumerosUsadosDTO numerosUsados) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardar(NumerosUsadosDTO) - start"); //$NON-NLS-1$
    }

    this.numeroUsadoRepo.save(this.mapper.map(numerosUsados, NumerosUsados.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardar(NumerosUsadosDTO) - end"); //$NON-NLS-1$
    }
  }

  /**
   * A partir del último número especial registrado en la base, crea un objeto
   * que contendrá la información necesaria para la creación del próximo número.
   * 
   * @param numeracionEspecial
   * @return Objeto con la información del próximo número especial.
   */
  private NumeracionEspecialDTO crearProximoNumero(NumeracionEspecialDTO numeracionEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearProximoNumero(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO proximoNumeroEspecial = new NumeracionEspecialDTO();
    proximoNumeroEspecial.setAnio(numeracionEspecial.getAnio());
    proximoNumeroEspecial.setCodigoReparticion(numeracionEspecial.getCodigoReparticion());
    proximoNumeroEspecial.setId(numeracionEspecial.getId());
    proximoNumeroEspecial.setNumero(numeracionEspecial.getNumero() + 1);
    proximoNumeroEspecial.setIdTipoDocumento(numeracionEspecial.getIdTipoDocumento());
    String codEcosistema = this.ecosistemaService.obtenerEcosistema();
    if (!codEcosistema.trim().isEmpty()) {
      proximoNumeroEspecial.setCodigoEcosistema(codEcosistema);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearProximoNumero(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
    return proximoNumeroEspecial;
  }

  @Override
  public NumeracionEspecialDTO buscarUltimoNumeroEspecial(String codigoReparticion,
      TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUltimoNumeroEspecial(String, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO returnNumeracionEspecialDTO = this.numeracionEspecialService.buscarUltimoNumeroEspecial(codigoReparticion, tipoDocumento, null);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUltimoNumeroEspecial(String, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return returnNumeracionEspecialDTO;
  }

}
