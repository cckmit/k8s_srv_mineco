package com.egoveris.edt.base.service.impl.usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioRolDTO;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Cargo;
import com.egoveris.sharedsecurity.base.model.DatosUsuario;
import com.egoveris.sharedsecurity.base.model.DatosUsuarioRol;
import com.egoveris.sharedsecurity.base.model.SectorUsuario;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.repository.impl.DatosUsuarioRepository;
import com.egoveris.sharedsecurity.base.repository.impl.DatosUsuarioRolRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorUsuarioEDTRepository;

@Service("datosUsuarioService")
@Transactional
public class DatosUsuarioServiceImpl implements IDatosUsuarioService {

  private DozerBeanMapper mapper = new DozerBeanMapper();
  
  private Logger LOGGER = LoggerFactory.getLogger(DatosUsuarioServiceImpl.class);

  @Autowired
  private DatosUsuarioRolRepository datosUsuarioRolRepository;
  
  @Autowired
  private DatosUsuarioRepository datosUsuarioRepository;
  @Autowired
  private SectorUsuarioEDTRepository sectorUsrRepository;
  @Autowired
  private ISectorService sectorService;
  
  @Override
  public DatosUsuarioDTO getDatosUsuarioByUsername(String userName) {
    DatosUsuario result = datosUsuarioRepository.findByUsuario(userName);
    if (result != null) {
      return mapper.map(result, DatosUsuarioDTO.class);
    } else {
      return null;
    }
  }

  @Override
  public void guardarDatosUsuario(DatosUsuarioDTO datosUsuario) {
  	
  	DatosUsuario usuario = mapper.map(datosUsuario, DatosUsuario.class);
  	
  	
    datosUsuarioRepository.save(usuario);
  }
  
  @Override
  public void guardarDatosUsuarioRol(DatosUsuarioDTO datosUsuario) {

  	List<DatosUsuarioRol> listaPrevia = datosUsuarioRolRepository
  			.findByUsername(datosUsuario.getUsuario());
  	
  	agregarRoles(datosUsuario, listaPrevia);
  	
  	borrarRoles(datosUsuario, listaPrevia);

  }

	private void borrarRoles(DatosUsuarioDTO datosUsuario, List<DatosUsuarioRol> listaPrevia) {
		for(DatosUsuarioRol usuExiste: listaPrevia) {
  		boolean delete = true;
  		for(DatosUsuarioRolDTO datosRol : datosUsuario.getRoles()) {
  			if(existeDatosUsuarioRol(usuExiste, datosRol)) {
  				delete = false;
  			}
  		}
  		if(delete) {
  			datosUsuarioRolRepository.delete(usuExiste);
  		}
  	}
	}

	private void agregarRoles(DatosUsuarioDTO datosUsuario, List<DatosUsuarioRol> listaPrevia) {
		for(DatosUsuarioRolDTO datosRol : datosUsuario.getRoles()) {
  		boolean add = true;
  		for(DatosUsuarioRol usuExiste: listaPrevia) {
  			if(existeDatosUsuarioRol(usuExiste, datosRol)) {
  				add =false;
  			}
  			
  		}
  		if(add) {
  			DatosUsuarioRol usuarioRol = mapper.map(datosRol,DatosUsuarioRol.class);
  			datosUsuarioRolRepository.save(usuarioRol);
  		
  		}
  	}
	}
  
	private boolean existeDatosUsuarioRol(DatosUsuarioRol rolUsuario, DatosUsuarioRolDTO rolUsuarioAsignado) {
		return rolUsuarioAsignado.getId().getDatosUsuario() != null 
				 && rolUsuario.getId().getRol()!=null 
				 && rolUsuarioAsignado.getDatosUsuario().getUsuario()
				 .equals(rolUsuario.getDatosUsuario().getUsuario())
				 && rolUsuarioAsignado.getRol().getId().equals(rolUsuario.getRol().getId());
	}

  @Override
  public void modificarDatosUsuario(String username, String nombre, String email) {
    DatosUsuario datosUsuario = datosUsuarioRepository.findByUsuario(username);
    if (datosUsuario != null && datosUsuario.getIdSectorInterno() != null) {
      SectorDTO mesaAnterior = sectorService.getSectorbyId(datosUsuario.getIdSectorInterno());
      SectorUsuario su = sectorUsrRepository.findByNombreUsuarioAndEstadoTrue(username);
			if (!mesaAnterior.getReparticion().getId().equals(su.getSector().getReparticion().getId())) {
				datosUsuario.setCambiarMesa(true);
			}
      datosUsuario.setApellidoYNombre(nombre);
      datosUsuario.setMail(email);
      datosUsuarioRepository.save(datosUsuario);
    }
  }

  @Override
  public void modificarDatosUsuario(DatosUsuarioDTO datosUsuario) {
    DatosUsuario datosUsrToBeModified = mapper.map(datosUsuario, DatosUsuario.class);
    datosUsrToBeModified.setCambiarMesa(false);
    datosUsuarioRepository.save(datosUsrToBeModified);
  }

  @Override
  public void updateCargoUsuario(String userName, CargoDTO cargo) {
    DatosUsuario datosUsuario = datosUsuarioRepository.findByUsuario(userName);
    if (datosUsuario != null) {
      datosUsuario.setCargoAsignado(mapper.map(cargo, com.egoveris.sharedsecurity.base.model.Cargo.class));
      datosUsuarioRepository.save(datosUsuario);
    }
  }

  @Override
  public Boolean existeCuit(String cuit, String usuario) {
    List<DatosUsuario> busqueda = datosUsuarioRepository.findByNumeroCuit(cuit);
    if (!busqueda.isEmpty() && busqueda.get(0).getUsuario().equals(usuario)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void eliminarDatosUsuario(DatosUsuarioDTO datosUsuario) {
    // actualizo todos los usuarios en los que aparezca con atributos
    // "especiales"
    List<DatosUsuario> datosUsuarios;

    // traigo todos los usuarios en los que yo aparezca como asesor
    datosUsuarios = datosUsuarioRepository.findByUsuarioAsesor(datosUsuario.getUsuario());
    if (!CollectionUtils.isEmpty(datosUsuarios)) {
      for (DatosUsuario usuario : datosUsuarios) {
        usuario.setUsuarioAsesor(null);
        this.modificarDatosUsuario(mapper.map(usuario, DatosUsuarioDTO.class));
      }
    }
    // traigo todos los usuarios en los que aparezca como secretario
    datosUsuarios = datosUsuarioRepository.findBySecretario(datosUsuario.getUsuario());
    if (!CollectionUtils.isEmpty(datosUsuarios)) {
      for (DatosUsuario usuario : datosUsuarios) {
        usuario.setSecretario(null);
        this.modificarDatosUsuario(mapper.map(usuario, DatosUsuarioDTO.class));
      }
    }
    // traigo todos los usuarios en los que yo aparezca como superior
    datosUsuarios = datosUsuarioRepository.findByUserSuperior(datosUsuario.getUsuario());
    if (!CollectionUtils.isEmpty(datosUsuarios)) {
      for (DatosUsuario usuario : datosUsuarios) {
        usuario.setUserSuperior(null);
        this.modificarDatosUsuario(mapper.map(usuario, DatosUsuarioDTO.class));
      }
    }

    // guardo en Envers
    this.persistirUltimoMovimiento(datosUsuario);
    datosUsuarioRepository.delete(mapper.map(datosUsuario, DatosUsuario.class));
  }

  @Override
  @Transactional(value = "transactionManagerCCOO")
  public void persistirUltimoMovimiento(DatosUsuarioDTO datosUsuario) {
    /* Se agrega este update para que quede un registro en envers */
    datosUsuarioRepository.save(mapper.map(datosUsuario, DatosUsuario.class));
  }

  @Override
  @Transactional(readOnly = true)
	public Boolean existeDatosUsuario(String userName) {
		DatosUsuario du = datosUsuarioRepository.findByUsuario(userName);
		if (du != null) {
			// email y numero identificación
			boolean isEmailCuitNotEmpty = !StringUtils.isBlank(du.getMail()) && !StringUtils.isBlank(du.getNumeroCuit());
			// Sector interno asignado
			boolean isSectorNotEmpty = du.getIdSectorInterno() != null && !StringUtils.isBlank(du.getCodigoSectorInterno());
			// Usuario superior y aceptación
			boolean isUserNotEmpty = !StringUtils.isBlank(du.getUserSuperior())
					&& BooleanUtils.isTrue(du.getAceptacionTyC());
			if (isEmailCuitNotEmpty && isUserNotEmpty && isSectorNotEmpty && du.getCargoAsignado() != null) {
				return true;
			}
		}
		return false;
	}

  @Override
  public void actualizarbanderaSectorMesa(Integer idSectorInterno, Boolean igualReparticion) {
    if (!igualReparticion) {
      List<DatosUsuario> lista1 = datosUsuarioRepository.buscarPorSector(idSectorInterno);
      if (!lista1.isEmpty()) {
        for (DatosUsuario dato : lista1) {
          dato.setCambiarMesa(true);
        }
        datosUsuarioRepository.save(lista1);
      }
      List<DatosUsuario> lista2 = datosUsuarioRepository.findByIdSectorInterno(idSectorInterno);
      if (!lista2.isEmpty()) {
        for (DatosUsuario dato : lista2) {
          dato.setCambiarMesa(true);
        }
        datosUsuarioRepository.save(lista2);
      }
    }

  }

  @Override
  public List<DatosUsuarioDTO> getHistorico(DatosUsuarioDTO datosUsuario) {
    // if (datosUsuario == null) {
    // return new ArrayList<DatosUsuario>();
    // }
    // return datosUsuarioRepository.getHistorico(datosUsuario);
    return null;
  }

	/**
	 * Gets the datos usuarios by cargo.
	 *
	 * @param cargo the cargo
	 * @return the datos usuarios by cargo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DatosUsuarioDTO> getDatosUsuariosByCargo(CargoDTO cargo) {
		return ListMapper.mapList(this.datosUsuarioRepository.findByCargoAsignado(this.mapper.map(cargo, Cargo.class)),
				this.mapper, DatosUsuarioDTO.class);
	}
	
}