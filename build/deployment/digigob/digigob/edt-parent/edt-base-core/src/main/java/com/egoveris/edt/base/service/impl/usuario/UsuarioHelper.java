package com.egoveris.edt.base.service.impl.usuario;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service("usuarioHelper")
public class UsuarioHelper implements IUsuarioHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioHelper.class);

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private ISectorUsuarioService sectorUsuarioService;
  @Autowired
  private AppProperty appProperty;
  private List<UsuarioReducido> listaUsuarios;

  public AppProperty getAppProperty() {
    return appProperty;
  }

  public void setAppProperty(AppProperty appProperty) {
    this.appProperty = appProperty;
  }

  @Override
  @Scheduled(fixedRate = 30000)
  public void cachearListaUsuarios() {
    try {
      listaUsuarios = usuarioService.obtenerUsuariosDeSolr();
    } catch (SecurityNegocioException ex) {
      LOGGER.error("Error al obtener usuarios solr", ex);
    }
  }

  @Override
  public List<UsuarioReducido> obtenerTodosUsuarios() {
    if (listaUsuarios == null) {
      cachearListaUsuarios();
      return listaUsuarios;
    }

    return listaUsuarios;
  }

  public IUsuarioService getUsuarioService() {
    return usuarioService;
  }

  public void setUsuarioService(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  /**
   * Valida usuario asignador Si el usuario no es asignador: TRUE Si el usuario
   * es asignador pero tiene en su sector mas de un asignador: TRUE Si el
   * usuario es asignador y es el unico con ese perfil para su sector: False
   * 
   * @param codigoUsuario
   * @return
   */
  @Override
  public boolean esUsuarioAsignador(String codigoUsuario) throws SecurityNegocioException {

    // EE.ASIGNADOR
    List<UsuarioReducido> listaUsuariosReducidos = usuarioService
        .obtenerUsuariosPorRol(ConstantesSesion.ROL_ASIGNADOR);

    for (UsuarioReducido uR : listaUsuariosReducidos) {
      if (codigoUsuario.equals(uR.getUsername())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Valida usuario asignador Si el usuario no es asignador: TRUE Si el usuario
   * es asignador pero tiene en su sector mas de un asignador: TRUE Si el
   * usuario es asignador y es el unico con ese perfil para su sector: False
   * 
   * @param codigoUsuario
   * @return
   */
  @Override
  public boolean validarUsuarioAsignador(String codigoUsuario) throws SecurityNegocioException {
    int cantidadDeAsignadores = 0;
    List<UsuarioReducido> listaUsuariosReducidos = usuarioService
        .obtenerUsuariosPorRol(ConstantesSesion.ROL_ASIGNADOR);
    SectorUsuarioDTO su = sectorUsuarioService.getByUsername(codigoUsuario);
    if (su != null) {
      List<String> usuariosPorGrupo = sectorUsuarioService
          .obtenerUsernamePorSector(su.getSector().getId());
      for (String u : usuariosPorGrupo) {
        for (UsuarioReducido uR : listaUsuariosReducidos) {
          if (u.equals(uR.getUsername())) {
            cantidadDeAsignadores++;
            if (cantidadDeAsignadores > 1) {
              return true;
            }
          }
        }
      }
    }

    return cantidadDeAsignadores > 1;
  }

  @Override
  public boolean tieneUsuarioAsignador(Integer idSectorInterno) throws SecurityNegocioException {
    List<String> lista = sectorUsuarioService.obtenerUsernamePorSector(idSectorInterno);
    List<UsuarioReducido> listaUsuariosReducidos = usuarioService
        .obtenerUsuariosPorRol(ConstantesSesion.ROL_ASIGNADOR);

    for (String u : lista) {
      for (UsuarioReducido ur : listaUsuariosReducidos) {
        if (ur.getUsername().equals(u)) {
          return true;
        }
      }

    }

    return false;
  }

  @Override
  public List<String> obtenerUsuariosAsignadoresPorSector(Integer codigoSectorInterno) {
    List<String> lista = sectorUsuarioService.obtenerUsernamePorSector(codigoSectorInterno);
    List<UsuarioReducido> listaUsuariosReducidos = usuarioService
        .obtenerUsuariosPorRol(ConstantesSesion.ROL_ASIGNADOR);

    List<String> usuarios = new ArrayList<>();

    for (String u : lista) {
      for (UsuarioReducido ur : listaUsuariosReducidos) {
        if (ur.getUsername().equals(u)) {
          usuarios.add(ur.getUsername());
        }
      }
    }
    return usuarios;
  }
}
