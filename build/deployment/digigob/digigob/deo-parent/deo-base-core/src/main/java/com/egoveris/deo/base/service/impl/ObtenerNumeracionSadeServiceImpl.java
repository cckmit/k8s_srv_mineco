package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.ObtenerNumeracionSadeService;
import com.egoveris.deo.model.model.NroSADEProcesoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.numerador.ws.service.ExternalNumeroService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class ObtenerNumeracionSadeServiceImpl implements ObtenerNumeracionSadeService {
  private static final Logger logger = LoggerFactory
      .getLogger(ObtenerNumeracionSadeServiceImpl.class);

  @Autowired
  private IUsuarioService usuarioService;

  // numerador*************************************************
  public static final String ESTADO_TRANSITORIO = "transitorio";
  public static final String ESTADO_USADO = "usado";
  public static final String ESTADO_BAJA = "baja";
  public static final String CODIGO_SECUENCIA_ORIGINAL = "   ";

  @Autowired
  private ExternalNumeroService numeradorService;

  @Autowired
  private DocumentoRepository documentoRepo;

  @Override
  public void anularNumeroSADE(int anio, int numero) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("anularNumeroSADE(int, int) - start"); //$NON-NLS-1$
    }

    try {
      this.numeradorService.anularNumeroSade(anio, numero);
    } catch (ApplicationException e) {
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("anularNumeroSADE(int, int) - end"); //$NON-NLS-1$
    }
  }

  // ***********************numerador***********************************************

  @Override
  public NumeroDTO buscarNumeroSecuenciaSade(String userName, String tipoActuacionSade)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeroSecuenciaSade(String, String) - start"); //$NON-NLS-1$
    }

    NumeroDTO numero = null;

    try {
      Usuario datosUsuario = this.usuarioService.obtenerUsuario(userName);
      if (datosUsuario.getCodigoReparticion() != null) {
        numero = numeradorService.obtenerNumeroSade(userName.trim(), Constantes.NOMBRE_APLICACION,
            tipoActuacionSade.trim(), datosUsuario.getCodigoReparticion().trim(),
            datosUsuario.getCodigoReparticion().trim());
      }

    } catch (Exception e) {
      logger.warn("buscarNumeroSecuenciaSade(String, String) - exception ignored", e); //$NON-NLS-1$
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeroSecuenciaSade(String, String) - end"); //$NON-NLS-1$
    }
    return numero;
  }

  @Override
  public void confirmarNumeroSADE(int anio, int numero) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("confirmarNumeroSADE(int, int) - start"); //$NON-NLS-1$
    }

    try {
      this.numeradorService.confimarNumeroSade(anio, numero);
    } catch (ApplicationException e) {
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("confirmarNumeroSADE(int, int) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public List<NroSADEProcesoDTO> buscarNumeros(int anio, List<Integer> numerosTransitorios) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeros(int, List<Integer>) - start"); //$NON-NLS-1$
    }

    List<NroSADEProcesoDTO> nrosEncontrados = new ArrayList<>();
    List<Integer> estadoNros = new ArrayList<>();

    for (Integer numero : numerosTransitorios) {
      StringBuilder codigo = new StringBuilder();
      codigo.append("%-");
      codigo.append(anio);
      codigo.append("-");
      codigo.append(StringUtils.leftPad(Integer.toString(numero), 8, "0"));
      codigo.append("-");
      codigo.append(CODIGO_SECUENCIA_ORIGINAL);
      codigo.append("-%");

      Documento documento = this.documentoRepo.findByNumeroLike(codigo.toString());
      if (documento != null && documento.getNumero() != null) {
        String vec[] = documento.getNumero().split("-");
        estadoNros.add(Integer.valueOf(vec[2]));
      }

      for (Integer numeroTransitorio : numerosTransitorios) {
        String estado;
        if (estadoNros != null && estadoNros.contains(numeroTransitorio)) {
          estado = ESTADO_USADO;

        } else {
          estado = ESTADO_BAJA;
        }
        nrosEncontrados.add(new NroSADEProcesoDTO(anio, numeroTransitorio, estado));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeros(int, List<Integer>) - end"); //$NON-NLS-1$
    }
      return nrosEncontrados;
  }
}
