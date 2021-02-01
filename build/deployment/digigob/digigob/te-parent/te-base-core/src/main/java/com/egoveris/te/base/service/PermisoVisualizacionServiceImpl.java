package com.egoveris.te.base.service;

import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ArchivoDeTrabajoVisualizacion;
import com.egoveris.te.base.model.ArchivoDeTrabajoVisualizacionDTO;
import com.egoveris.te.base.repository.ArchivoDeTrabajoVisualizacionRepository;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class PermisoVisualizacionServiceImpl implements PermisoVisualizacionService {
  @Autowired
  private ArchivoDeTrabajoVisualizacionRepository permisoVisualizacionRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  private boolean verReparticionSector(ArchivoDeTrabajoDTO archivo, String reparticion,
      String usuario) {
    if (!buscarPorReparticion(archivo, reparticion)) {
      if (buscarPorUsuario(usuario, archivo)) {
        return true;
      }
    } else {
      return true;
    }
    return false;
  }

  private boolean verRectoraSector(String usuario, ArchivoDeTrabajoDTO archivo, String reparticion,
      String sector) {
    if (!buscarPorRectora(reparticion, archivo)) {
      if (!buscarPorSector(sector, archivo, reparticion)) {
        if (buscarPorUsuario(usuario, archivo)) {
          // lo encontro por usuario
          return true;
        }
      } else {
        // lo encontro por sector
        return true;
      }
    } else {
      // lo encontro por rectora
      return true;
    }
    return false;
  }

  private boolean verRectoraReparticion(String usuario, ArchivoDeTrabajoDTO archivo,
      String reparticion) {
    if (!buscarPorRectora(reparticion, archivo)) {
      if (!buscarPorReparticion(archivo, reparticion)) {
        if (buscarPorUsuario(usuario, archivo)) {
          return true;
        }
      } else {
        return true;
      }
    } else {
      return true;
    }
    return false;
  }

  public boolean buscarPorRectora(String reparticion, ArchivoDeTrabajoDTO archivo) {

    boolean returnboolean = false;
    int count = this.permisoVisualizacionRepository.buscarPorRectora(archivo.getId(), reparticion);

    if (count > 0) {
      returnboolean = true;
    }
    return returnboolean;
  }

  public boolean buscarPorReparticion(ArchivoDeTrabajoDTO archivo, String reparticion) {

    boolean returnboolean = false;
    int count = this.permisoVisualizacionRepository.buscarPorReparticion(archivo.getId(),
        reparticion);

    if (count > 0) {
      returnboolean = true;
    }
    return returnboolean;

  }

  public boolean buscarPorUsuario(String usuario, ArchivoDeTrabajoDTO archivo) {

    boolean returnboolean = false;
    int count = this.permisoVisualizacionRepository.buscarPorUsuario(archivo.getId(), usuario);

    if (count > 0) {
      returnboolean = true;
    }

    return returnboolean;
  }

  public boolean buscarPorSector(String sector, ArchivoDeTrabajoDTO archivo, String reparticion) {

    boolean returnboolean = false;

    int count = this.permisoVisualizacionRepository.buscarPorSector(archivo.getId(), sector,
        reparticion);

    if (count > 0) {
      returnboolean = true;
    }

    return returnboolean;
  }

  public boolean buscarPorRectoraTodas(ArchivoDeTrabajoDTO archivo) {

    boolean returnboolean = false;

    int count = this.permisoVisualizacionRepository.buscarPorRectora(archivo.getId(),
        ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS);

    if (count > 0) {
      returnboolean = true;
    }
    return returnboolean;
  }

  private boolean buscarTodosLosPermisos(String reparticion, ArchivoDeTrabajoDTO archivo,
      String usuario) {
    boolean puedeVer = false;

    if (!buscarPorRectoraTodas(archivo)) {
      if (!buscarPorRectora(reparticion, archivo)) {
        // no esta por rectora
        if (!buscarPorReparticion(archivo, reparticion)) {
          // no esta por reparticion - no se podria buscar por sector,
          // porque usa la reparticion, entonces se busca por usuario
          if (buscarPorUsuario(usuario, archivo)) {
            puedeVer = true;
          }
        } else {
          puedeVer = true;
        }
      } else {
        puedeVer = true;
      }
    } else {
      puedeVer = true;
    }
    return puedeVer;
  }

  @Override
  public boolean tienePermisoVisualizacion(Usuario usuario, ArchivoDeTrabajoDTO archivo) {
    if (archivo.getTipoReserva() == null || (archivo.getTipoReserva() == null
        && !archivo.getTipoReserva().equals(ConstantesWeb.ARCHIVO_TRABAJO_RESERVADO))) {
      return true;
    }

    Collection<GrantedAuthority> lstRoles = usuario.getAuthorities();
    boolean permisoRectora = false;
    boolean permisoReparticion = false;
    boolean permisoSector = false;
    for (GrantedAuthority rol : lstRoles) {
      if (rol.getAuthority().compareTo(ConstantesWeb.RECTORA_CONFIDENCIAL) == 0) {
        permisoRectora = true;
      }
      if (rol.getAuthority().compareTo(ConstantesWeb.REPARTICION_CONFIDENCIAL) == 0) {
        permisoReparticion = true;
      }
      if (rol.getAuthority().compareTo(ConstantesWeb.SECTOR_CONFIDENCIAL) == 0) {
        permisoSector = true;
      }
    }

    boolean puedeVer;
    if (permisoRectora) {
      if (permisoReparticion) {
        if (permisoSector) {
          // rectora - reparticion - sector
          puedeVer = buscarTodosLosPermisos(usuario.getCodigoReparticion(), archivo,
              usuario.getUsername());
        } else {
          // rectora - reparticion
          puedeVer = verRectoraReparticion(usuario.getUsername(), archivo,
              usuario.getCodigoReparticion());
        }
      } else if (permisoSector) {
        // rectora - sector
        puedeVer = verRectoraSector(usuario.getUsername(), archivo, usuario.getCodigoReparticion(),
            usuario.getCodigoSectorInterno());
      } else {
        // rectora
        if (!buscarPorRectoraTodas(archivo)) {
          if (!buscarPorRectora(usuario.getCodigoReparticion(), archivo)) {
            puedeVer = buscarPorUsuario(usuario.getUsername(), archivo);
          } else {
            puedeVer = true;
          }
        } else {
          puedeVer = true;
        }

      }
    } else if (permisoReparticion) {
      if (permisoSector) {
        // reparticion - sector
        puedeVer = verReparticionSector(archivo, usuario.getCodigoReparticion(),
            usuario.getUsername());
      } else {
        // reparticion
        if (!buscarPorReparticion(archivo, usuario.getCodigoReparticion())) {
          puedeVer = buscarPorUsuario(usuario.getUsername(), archivo);
        } else {
          puedeVer = true;
        }
      }
    } else if (permisoSector) {
      // sector
      if (!buscarPorSector(usuario.getCodigoSectorInterno(), archivo,
          usuario.getCodigoReparticion())) {
        puedeVer = buscarPorUsuario(usuario.getUsername(), archivo);
      } else {
        puedeVer = true;
      }
    } else {
      puedeVer = buscarPorUsuario(usuario.getUsername(), archivo);
    }
    return puedeVer;
  }

  public ArchivoDeTrabajoVisualizacionRepository getPermisoVisualizacionRepository() {
    return permisoVisualizacionRepository;
  }

  public void setPermisoVisualizacionRepository(
      ArchivoDeTrabajoVisualizacionRepository permisoVisualizacionRepository) {
    this.permisoVisualizacionRepository = permisoVisualizacionRepository;
  }

  @Override
  public boolean buscarPorRectoraTodas(String reparticion) {
    return false;
  }

  @Override
  @Transactional()
  public void guardarPermisos(List<ArchivoDeTrabajoVisualizacionDTO> permisos) {
    for (ArchivoDeTrabajoVisualizacionDTO a : permisos) {

      List<ArchivoDeTrabajoVisualizacion> archiEnty = ListMapper.mapList(permisos, mapper,
          ArchivoDeTrabajoVisualizacion.class);

      permisoVisualizacionRepository.save(archiEnty);
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void guardarPermisoDeVisualizacion(ArchivoDeTrabajoVisualizacionDTO arch) {

    permisoVisualizacionRepository.save(mapper.map(arch, ArchivoDeTrabajoVisualizacion.class));
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ArchivoDeTrabajoVisualizacionDTO> buscarPermisosByIdArchivo(
      Long idArchivoDeTrabajo) {

    return ListMapper.mapList(
        permisoVisualizacionRepository.findByIdArchivoDeTrabajo(idArchivoDeTrabajo), mapper,
        ArchivoDeTrabajoDTO.class);
  }

}