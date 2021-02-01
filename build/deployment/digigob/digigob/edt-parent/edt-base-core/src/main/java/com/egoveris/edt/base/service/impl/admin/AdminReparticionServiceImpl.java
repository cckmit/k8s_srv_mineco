package com.egoveris.edt.base.service.impl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.admin.AdminReparticion;
import com.egoveris.sharedsecurity.base.model.admin.AdminReparticionHistorico;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.repository.admin.AdminReparticionHistRepository;
import com.egoveris.sharedsecurity.base.repository.admin.AdminReparticionRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorUsuarioEDTRepository;

@Service("adminReparticionService")
@Transactional
public class AdminReparticionServiceImpl implements IAdminReparticionService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  AdminReparticionHistRepository adminReparticionHRepository;
  @Autowired
  AdminReparticionRepository adminReparticionRepository;
  @Autowired
  SectorUsuarioEDTRepository sectorUsuarioRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionDTO> obtenerReparticionesRelacionadasByUsername(String userAdmin) {
    // The two lists are merged.
    // A Set is defined to avoid duplications.
    Date now = new Date();
    List<AdminReparticion> lista = adminReparticionRepository
        .findByNombreUsuarioAndReparticion_VigenciaDesdeBeforeAndReparticion_VigenciaHastaAfter(
            userAdmin.toUpperCase(), now, now);
    List<Reparticion> listaResult = new ArrayList<>();
    for (AdminReparticion admin : lista) {
      listaResult.add(admin.getReparticion());
    }
    Set<Reparticion> set = new HashSet<>(listaResult);
    set.add(sectorUsuarioRepository.findByNombreUsuarioAndEstadoTrue(userAdmin).getSector()
        .getReparticion());

    return ListMapper.mapList(new ArrayList<>(set), mapper, ReparticionDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AdminReparticionDTO> obtenerReparticionesAdministradasByUsername(String userAdmin) {
    Date now = new Date();
    return ListMapper.mapList(adminReparticionRepository
        .findByNombreUsuarioAndReparticion_VigenciaDesdeBeforeAndReparticion_VigenciaHastaAfter(
            userAdmin.toUpperCase(), now, now),
        mapper, AdminReparticionDTO.class);
  }

  @Override
  public void agregarAdminReparticion(AdminReparticionDTO adminReparticion) {
    adminReparticionRepository.save(mapper.map(adminReparticion, AdminReparticion.class));

  }

  @Override
  public boolean usuarioPerteneceAlasReparticiones(String usuario,
      List<ReparticionDTO> listaRepartcionesDelUsuario) throws NegocioException {

    Reparticion reparticionDelUsuario = sectorUsuarioRepository
        .findByNombreUsuarioAndEstadoTrue(usuario).getSector().getReparticion();

    if (reparticionDelUsuario != null) {
      for (ReparticionDTO reparticionActual : listaRepartcionesDelUsuario) {
        if (reparticionActual.getId().equals(reparticionDelUsuario.getId())) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public void eliminarAdminReparticion(AdminReparticionDTO adminReparticionAEliminar) {
    adminReparticionRepository
        .delete(mapper.map(adminReparticionAEliminar, AdminReparticion.class));
  }


  @Override
  public List<AdminReparticionDTO> getHistorico(String nombreUsuario) {
    List<AdminReparticionDTO> retorno = null;
    List<AdminReparticionHistorico> resultados = adminReparticionHRepository
        .findByNombreUsuarioOrderByFechaRevisionDesc(nombreUsuario);
    if (resultados != null && !resultados.isEmpty()) {
      retorno = new ArrayList<>();
      for (AdminReparticionHistorico data : resultados) {
        AdminReparticionDTO objectToBeAdded = mapper.map(data, AdminReparticionDTO.class);
        objectToBeAdded.setFechaRevision(new Date(data.getFechaRevision()));
        retorno.add(objectToBeAdded);
      }
    }
    return retorno;
  }

}