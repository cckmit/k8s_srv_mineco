package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DatosUsuarioBean;

@Service
@Transactional
public class UsuariosSADEServiceImpl implements UsuariosSADEService {

  private static final Logger logger = LoggerFactory.getLogger(UsuariosSADEServiceImpl.class);

  @Autowired
  private IUsuarioService usuarioService;

  public List<Usuario> getTodosLosUsuarios() throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuarios() - start");
    }

    List<Usuario> lista = new ArrayList<Usuario>(usuarioService.obtenerUsuarios());

    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuarios() - end - return value={}", lista);
    }
    return lista;
  }

  public List<Usuario> getUsuarios(String reparticion) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("getUsuarios(reparticion={}) - start", reparticion);
    }

    List<Usuario> listaADepurar = usuarioService.obtenerUsuariosPorReparticion(reparticion);

    if (logger.isDebugEnabled()) {
      logger.debug("getUsuarios(String) - end - return value={}", listaADepurar);
    }
    return listaADepurar;
  }

  public boolean usuarioTieneRol(String username, String rol) {
    if (logger.isDebugEnabled()) {
      logger.debug("usuarioTieneRol(username={}, rol={}) - start", username, rol);
    }

    try {
      boolean returnboolean = usuarioService.usuarioTieneRol(username, rol);
      if (logger.isDebugEnabled()) {
        logger.debug("usuarioTieneRol(String, String) - end - return value={}", returnboolean);
      }
      return returnboolean;
    } catch (Exception e) {
      logger.error("usuarioTieneRol(String, String)", e);

      throw new TeRuntimeException("Ha ocurrido un error de comunicacion con el servidor", e);
    }
  }

  public Usuario obtenerUsuarioMigracion(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioMigracion(username={}) - start", username);
    }

    try {
      Usuario returnUsuario = usuarioService.obtenerUsuarioMigracion(username);
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerUsuarioMigracion(String) - end - return value={}", returnUsuario);
      }
      return returnUsuario;
    } catch (Exception e) {
      logger.error("obtenerUsuarioMigracion(String)", e);

      throw new TeRuntimeException(
          "Ha ocurrido un error al buscar los datos del usuario: " + username, e);
    }
  }

  public List<Usuario> getUsuarios(String reparticion, String modulo) {
    if (logger.isDebugEnabled()) {
      logger.debug("getUsuarios(reparticion={}, modulo={}) - start", reparticion, modulo);
    }

    throw new UnsupportedOperationException();
  }

  public Usuario obtenerUsuarioActual() {
    logger.info("Obteniendo usuario actual");
    Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioActual() - end - return value={}", user);
    }
    return user;
  }

  public Usuario getDatosUsuario(String nombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("getDatosUsuario(nombreUsuario={}) - start", nombreUsuario);
    }

    try {
      Usuario returnUsuario = usuarioService.obtenerUsuario(nombreUsuario);
      if (logger.isDebugEnabled()) {
        logger.debug("getDatosUsuario(String) - end - return value={}", returnUsuario);
      }
      return returnUsuario;
    } catch (Exception e) {
      logger.warn("getDatosUsuario(String)", e);

      if (logger.isDebugEnabled()) {
        logger.debug("getDatosUsuario(String) - end - return value={null}");
      }
      return null;
    }
  }

  public List<Usuario> obtenerUsuariosSupervisados(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuariosSupervisados(username={}) - start", username);
    }

    try {
      List<Usuario> returnList = usuarioService.obtenerUsuariosPorSupervisor(username);
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerUsuariosSupervisados(String) - end - return value={}", returnList);
      }
      return returnList;
    } catch (Exception e) {
      logger.error("obtenerUsuariosSupervisados(String)", e);

      throw new TeRuntimeException("Ha ocurrido un error de comunicacion con el servidor", e);
    }
  }

  	public List<Usuario> obtenerUsuariosPorSector(String sector) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("obtenerUsuariosPorSector(sector={}) - start", sector);
	    }
	    try {
	    	List<Usuario> returnList =  usuarioService.obtenerUsuariosPorSector(sector);
	    	if (logger.isDebugEnabled()) {
	        logger.debug("obtenerUsuariosPorSector(String) - end - return value={}", returnList);
	      }
	      return returnList;
	    } catch (Exception e) {
	      logger.error("obtenerUsuariosPorSector(String)", e);

	      throw new TeRuntimeException("Ha ocurrido un error de comunicacion con el servidor", e);
	    }
	  }
  
  /**
   * Se buscan todos los usuarios con permiso de caratulación interna
   */
  public List<DatosUsuarioBean> getTodosLosUsuariosCaratuladoresInternos() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuariosCaratuladoresInternos() - start");
    }

    try {
      List<UsuarioReducido> listaUsuarios = usuarioService.obtenerUsuariosPorRol("SADE.INTERNOS");
      List<DatosUsuarioBean> listaUsuariosCaratuladoresInternos = new ArrayList<DatosUsuarioBean>();

      for (UsuarioReducido datosUsuarioBean : listaUsuarios) {
        listaUsuariosCaratuladoresInternos.add(
            toDatosUsuarioBean(usuarioService.obtenerUsuario(datosUsuarioBean.getUsername())));
      }

      if (logger.isDebugEnabled()) {
        logger.debug("getTodosLosUsuariosCaratuladoresInternos() - end - return value={}",
            listaUsuariosCaratuladoresInternos);
      }
      return listaUsuariosCaratuladoresInternos;
    } catch (Exception e) {
      logger.error("getTodosLosUsuariosCaratuladoresInternos()", e);

      throw new TeRuntimeException(e.getMessage(), e);
    }
  }

  public List<Usuario> obtenerUsuarioPorCriterio(String criterio) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioPorCriterio(criterio={}) - start", criterio);
    }

    try {
      List<Usuario> returnList = usuarioService.obtenerUsuarios(criterio);
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerUsuarioPorCriterio(String) - end - return value={}", returnList);
      }
      return returnList;
    } catch (SecurityNegocioException e) {
      logger.error("obtenerUsuarioPorCriterio SecurityNegocioException ", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioPorCriterio(String) - end - return value={null}");
    }
    return null;
  }

  /**
   * Se buscan todos los usuarios con permiso de caratulación externa
   */
  public List<DatosUsuarioBean> getTodosLosUsuariosCaratuladoresExternos() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuariosCaratuladoresExternos() - start");
    }

    try {
      List<UsuarioReducido> listaUsuarios = usuarioService.obtenerUsuariosPorRol("SADE.EXTERNOS");
      List<DatosUsuarioBean> listaUsuariosCaratuladoresExternos = new ArrayList<DatosUsuarioBean>();

      for (UsuarioReducido datosUsuarioBean : listaUsuarios) {
        listaUsuariosCaratuladoresExternos.add(
            toDatosUsuarioBean(usuarioService.obtenerUsuario(datosUsuarioBean.getUsername())));
      }

      if (logger.isDebugEnabled()) {
        logger.debug("getTodosLosUsuariosCaratuladoresExternos() - end - return value={}",
            listaUsuariosCaratuladoresExternos);
      }
      return listaUsuariosCaratuladoresExternos;
    } catch (Exception e) {
      logger.error("getTodosLosUsuariosCaratuladoresExternos()", e);

      throw new TeRuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Se buscan todos los usuarios con permiso de caratulación interna
   */
  public boolean hasUsuariosCaratuladoresInternosXReparticionYSector(String reparticion,
      String sectorInterno) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "hasUsuariosCaratuladoresInternosXReparticionYSector(reparticion={}, sectorInterno={}) - start",
          reparticion, sectorInterno);
    }

    List<Usuario> listaUsuariosXReparticionSector = getTodosLosUsuariosXReparticionYSectorEE(
        reparticion, sectorInterno);
    for (Usuario datosUsuarioBean : listaUsuariosXReparticionSector) {
      if (usuarioTieneRol(datosUsuarioBean.getUsername(), "sade.internos")) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "hasUsuariosCaratuladoresInternosXReparticionYSector(String, String) - end - return value={}",
              true);
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "hasUsuariosCaratuladoresInternosXReparticionYSector(String, String) - end - return value={}",
          false);
    }
    return false;
  }

  /**
   * Se buscan todos los usuarios con permiso de caratulación externa
   */
  public boolean hasUsuariosCaratuladoresExternosXReparticionYSector(String reparticion,
      String sectorInterno) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "hasUsuariosCaratuladoresExternosXReparticionYSector(reparticion={}, sectorInterno={}) - start",
          reparticion, sectorInterno);
    }

    List<Usuario> listaUsuariosXReparticionSector = getTodosLosUsuariosXReparticionYSectorEE(
        reparticion, sectorInterno);
    for (Usuario datosUsuarioBean : listaUsuariosXReparticionSector) {
      if (usuarioTieneRol(datosUsuarioBean.getUsername(), "sade.externos")) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "hasUsuariosCaratuladoresExternosXReparticionYSector(String, String) - end - return value={}",
              true);
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "hasUsuariosCaratuladoresExternosXReparticionYSector(String, String) - end - return value={}",
          false);
    }
    return false;
  }

  public boolean licenciaActiva(String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("licenciaActiva(usuario={}) - start", usuario);
    }

    try {
      boolean returnboolean = usuarioService.licenciaActiva(usuario, new Date());
      
      if (logger.isDebugEnabled()) {
        logger.debug("licenciaActiva(String) - end - return value={}", returnboolean);
      }
      
      return returnboolean;
    } catch (SecurityNegocioException e) {
      logger.error("licenciaActiva(String)", e);

      throw new TeRuntimeException(e);
    }
  }

  // SE AGREGA
  // StringUtils.isNotEmpty(datosUsuarioBean.getSectorInterno().getCodigo())
  // PARA VER SI EL ERROR PASA POR EL SECTOR INTERNO DEL USUARIO
  public List<Usuario> getTodosLosUsuariosXReparticionYSectorEE(String reparticion,
      String sectorInterno) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "getTodosLosUsuariosXReparticionYSectorEE(reparticion={}, sectorInterno={}) - start",
          reparticion, sectorInterno);
    }

    try {
      List<Usuario> listaUsuarios = usuarioService.obtenerUsuariosPorGrupo(reparticion,
          sectorInterno);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "getTodosLosUsuariosXReparticionYSectorEE(String, String) - end - return value={}",
            listaUsuarios);
      }
      return listaUsuarios;
    } catch (Exception e) {
      logger.error("getTodosLosUsuariosXReparticionYSectorEE(String, String)", e);

      throw new TeRuntimeException(e.getMessage(), e);
    }
  }

  public List<DatosUsuarioBean> getTodosLosUsuariosXReparticionYSector(String reparticion,
      String sectorInterno) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "getTodosLosUsuariosXReparticionYSector(reparticion={}, sectorInterno={}) - start",
          reparticion, sectorInterno);
    }

    try {
      List<Usuario> listaUsuarios = usuarioService.obtenerUsuariosPorGrupo(reparticion,
          sectorInterno);
      List<DatosUsuarioBean> users = new ArrayList<DatosUsuarioBean>();
      logger.info(
          "Obteniendo usuarios de la reparticion: " + reparticion + " y sector: " + sectorInterno);
      for (Usuario datosUsuarioBean : listaUsuarios) {
        users.add(toDatosUsuarioBean(datosUsuarioBean));
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "getTodosLosUsuariosXReparticionYSector(String, String) - end - return value={}",
            users);
      }
      return users;
    } catch (Exception e) {
      logger.error("Ha ocurrido un error al comunicarse con el jar security");
      logger.error(e.getMessage());
      throw new TeRuntimeException(e);
    }
  }

  public DatosUsuarioBean getDatos(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("getDatos(username={}) - start", username);
    }

    Usuario u;
    try {
      u = usuarioService.obtenerUsuario(username);
      DatosUsuarioBean du = new DatosUsuarioBean();
      du.setApellidoYNombre(u.getNombreApellido());
      du.setUserApoderado(u.getApoderado());
      du.setMail(u.getEmail());
      du.setCargo(u.getOcupacion());
      du.setCodigoReparticion(u.getCodigoReparticion());
      du.setCodigoSectorInterno(u.getCodigoSectorInterno());
      du.setUsuario(u.getUsername());
      du.setUsuarioSuperior(u.getSupervisor());

      if (logger.isDebugEnabled()) {
        logger.debug("getDatos(String) - end - return value={}", du);
      }
      return du;
    } catch (Exception e) {
      logger.error("getDatos(String)", e);

      throw new TeRuntimeException(e.getMessage(), e);
    }
  }

  public DatosUsuarioBean toDatosUsuarioBean(Usuario u) {
    if (logger.isDebugEnabled()) {
      logger.debug("toDatosUsuarioBean(u={}) - start", u);
    }

    DatosUsuarioBean du = new DatosUsuarioBean();
    du.setApellidoYNombre(u.getNombreApellido());
    du.setUserApoderado(u.getApoderado());
    du.setMail(u.getEmail());
    du.setCargo(u.getOcupacion());
    du.setCodigoReparticion(u.getCodigoReparticion());
    du.setCodigoSectorInterno(u.getCodigoSectorInterno());
    du.setUsuario(u.getUsername());
    du.setUsuarioSuperior(u.getSupervisor());

    if (logger.isDebugEnabled()) {
      logger.debug("toDatosUsuarioBean(Usuario) - end - return value={}", du);
    }
    return du;
  }


  @Override
  public List<Usuario> getUsuariosSegunRol(String rol) {
    if (logger.isDebugEnabled()) {
      logger.debug("getUsuariosSegunRol(rol={}) - start", rol);
    }

    List<UsuarioReducido> listaRoles = usuarioService.obtenerUsuariosPorRol(rol);
    ArrayList<Usuario> toThrowAway = new ArrayList<>();

    for (UsuarioReducido usuarioR : listaRoles) {
      Usuario user = null;
      try {
        user = usuarioService.obtenerUsuario(usuarioR.getUsername());
      } catch (Exception e) {
        logger.error("Usuario: " + usuarioR.getUsername(), e);
      }
      if (user != null) {
        toThrowAway.add(user);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getUsuariosSegunRol(String) - end - return value={}", toThrowAway);
    }
    return toThrowAway;
  }

  @Override
  public String buscarAsignadorDeSectorDesestimandoUsuarios(String codigoReparticionOriginal,
      String codigoSectorInterno, List<String> usuariosQueNoQuiero) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarAsignadorDeSectorDesestimandoUsuarios(codigoReparticionOriginal={}, codigoSectorInterno={}, usuariosQueNoQuiero={}) - start",
          codigoReparticionOriginal, codigoSectorInterno, usuariosQueNoQuiero);
    }

    List<Usuario> users = this.getTodosLosUsuariosXReparticionYSectorEE(codigoReparticionOriginal,
        codigoSectorInterno);
    for (Usuario u : users) {
      if (this.usuarioTieneRol(u.getUsername(), "EE.ASIGNADOR")) {
        String username = u.getUsername();
        if (!usuariosQueNoQuiero.contains(username)) {
          if (logger.isDebugEnabled()) {
            logger.debug(
                "buscarAsignadorDeSectorDesestimandoUsuarios(String, String, List<String>) - end - return value={}",
                username);
          }
          return username;
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarAsignadorDeSectorDesestimandoUsuarios(String, String, List<String>) - end - return value={null}");
    }
    return null;
  }

}
