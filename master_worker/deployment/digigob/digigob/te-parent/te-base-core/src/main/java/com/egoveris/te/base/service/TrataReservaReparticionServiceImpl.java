package com.egoveris.te.base.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.service.iface.ITrataReservaReparticionService;

@Service
@Transactional
public class TrataReservaReparticionServiceImpl implements ITrataReservaReparticionService {
  private static final Logger logger = LoggerFactory
      .getLogger(TrataReservaReparticionServiceImpl.class);

  @Autowired
  private TrataService trataService;
  @Autowired
  private UsuariosSADEService usuarioService;

  @Override
  @WebMethod(operationName = "reparticionDeUsuarioTienePermisosDeReservaEnEE")
  public boolean reparticionDeUsuarioTienePermisosDeReservaEnEE(
      @WebParam(name = "usuario") String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("reparticionDeUsuarioTienePermisosDeReservaEnEE(usuario={}) - start", usuario);
    }

    boolean permiso = false;
    Usuario user = usuarioService.getDatosUsuario(usuario);

    if (user != null) {
      boolean returnboolean = trataService
          .reparticionUsuarioTienePermisoDeReserva(user.getCodigoReparticion())
          || trataService
              .reparticionUsuarioTienePermisoDeReserva(user.getCodigoReparticionOriginal());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "reparticionDeUsuarioTienePermisosDeReservaEnEE(String) - end - return value={}",
            returnboolean);
      }
      return returnboolean;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "reparticionDeUsuarioTienePermisosDeReservaEnEE(String) - end - return value={}",
          permiso);
    }
    return permiso;
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public UsuariosSADEService getUsuarioService() {
    return usuarioService;
  }

  public void setUsuarioService(UsuariosSADEService usuarioService) {
    this.usuarioService = usuarioService;
  }

}
