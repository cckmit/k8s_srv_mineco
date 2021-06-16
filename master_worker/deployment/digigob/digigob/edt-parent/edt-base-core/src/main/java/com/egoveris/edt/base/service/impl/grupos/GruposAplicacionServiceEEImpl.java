package com.egoveris.edt.base.service.impl.grupos;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.service.grupos.IGruposAplicacionService;
import com.egoveris.edt.base.service.impl.ArchivoTrabajoServiceImpl;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Group;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

/**
 * Implementación de búsqueda de grupos específica para EE, donde se recorren la
 * repartición y los sectores del usuario para listar los distintos grupos.
 * 
 * @author alelarre
 * 
 */
@Service
public class GruposAplicacionServiceEEImpl implements IGruposAplicacionService {

  private static Logger log = LoggerFactory.getLogger(ArchivoTrabajoServiceImpl.class);

  @Autowired
  private ILdapAccessor ldapAccessor;
  
  @Autowired
  private IUsuarioService usuarioService;

  @Override
  public List<String> buscarGruposUsuarioAplicacion(String user) {
    Usuario usuario = null;
    try {
      usuario = usuarioService.obtenerUsuario(user);
    } catch (SecurityNegocioException e) {
      log.error(e.getMessage(), e);
    }

    if (usuario == null) {
      try {
        usuario = usuarioService.obtenerUsuarioSinFiltro(user);
      } catch (SecurityNegocioException e) {
        log.error(e.getMessage(), e);
      }
    }

    List<String> sectores = new ArrayList<>();
    sectores.add(usuario.getCodigoSectorInterno());

    List<String> listaGrupos = new ArrayList<>();
    listaGrupos.add(usuario.getCodigoReparticion());

    for (String grupo : sectores) {

      String reparticionSector = null;
      if (usuario.getCodigoReparticion() != null) {
        reparticionSector = usuario.getCodigoReparticion().trim() + "-" + grupo.trim();
      }
      listaGrupos.add(reparticionSector);
    }

    List<Group> listaPerfilesLdap;
    /**
     * Se cargan los grupos de reparticiones caratuladoras a las que pertenece
     * el usuario.
     */
    List<Group> listaGruposReparticionConCaratulador = new ArrayList<>();

    listaPerfilesLdap = this.ldapAccessor.buscarPerfilesDeUsuarioLdap(user);

    for (Group group : listaPerfilesLdap) {
      if ("sade.externos".equalsIgnoreCase(group.getName())) {
        String reparticionCaratulador = null;
        if (usuario.getCodigoReparticion() != null) {
          reparticionCaratulador = usuario.getCodigoReparticion().trim() + "-"
              + group.getName().trim();
        }
        Group grupo = new Group();
        grupo.setId(reparticionCaratulador);
        grupo.setName(reparticionCaratulador);
        listaGruposReparticionConCaratulador.add(grupo);
        break;
      }
      if ("sade.internos".equalsIgnoreCase(group.getName())) {

        String reparticionCaratulador = null;

        if (usuario.getCodigoReparticion() != null) {
          reparticionCaratulador = usuario.getCodigoReparticion().trim() + "-"
              + group.getName().trim();
        }
        Group grupo = new Group();
        grupo.setId(reparticionCaratulador);
        grupo.setName(reparticionCaratulador);
        listaGruposReparticionConCaratulador.add(grupo);
        break;
      }
    }
    for (Group group : listaGruposReparticionConCaratulador) {
      listaGrupos.add(group.getName());
    }
    /**
     * Se cargan los grupos de sectores caratuladores a las que pertenece el
     * usuario.
     */
    List<Group> listaGruposSectorConCaratulador = new ArrayList<>();
    List<Group> listaGruposSector = new ArrayList<>();
    Group auxi = new Group();
    auxi.setName(sectores.get(0));
    listaGruposSector.add(auxi);
    for (Group group : listaPerfilesLdap) {
      if ("sade.externos".equalsIgnoreCase(group.getName())) {
        for (Group grupoSector : listaGruposSector) {
          String reparticionCaratulador = usuario.getCodigoReparticion().trim() + "-"
              + grupoSector.getName().trim() + "-" + group.getName().trim();
          Group grupo = new Group();
          grupo.setId(reparticionCaratulador);
          grupo.setName(reparticionCaratulador);
          listaGruposSectorConCaratulador.add(grupo);
          break;
        }
      }
      if ("sade.internos".equalsIgnoreCase(group.getName())) {
        for (Group grupoSector : listaGruposSector) {
          String reparticionCaratulador = usuario.getCodigoReparticion().trim() + "-"
              + grupoSector.getName().trim() + "-" + group.getName().trim();
          Group grupo = new Group();
          grupo.setId(reparticionCaratulador);
          grupo.setName(reparticionCaratulador);
          listaGruposSectorConCaratulador.add(grupo);
          break;
        }
      }
    }
    for (Group group : listaGruposSectorConCaratulador) {
      listaGrupos.add(group.getName());
    }
    return listaGrupos;
  }
}
