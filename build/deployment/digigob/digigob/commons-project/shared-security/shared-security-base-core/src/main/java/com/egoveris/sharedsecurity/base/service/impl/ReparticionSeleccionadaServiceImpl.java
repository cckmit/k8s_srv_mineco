package com.egoveris.sharedsecurity.base.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityAccesoDatosException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.ReparticionSeleccionada;
import com.egoveris.sharedsecurity.base.model.Sector;
import com.egoveris.sharedsecurity.base.model.UserConverter;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioSolr;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionSeleccionadaDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.repository.ReparticionSeleccionadaRepository;
import com.egoveris.sharedsecurity.base.service.IReparticionSeleccionadaService;
import com.egoveris.sharedsecurity.base.service.IUsuarioSolrService;

@Service("reparticionSeleccionaService")
@Transactional("jpaTransactionManagerSec")
public class ReparticionSeleccionadaServiceImpl implements IReparticionSeleccionadaService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ReparticionSeleccionadaRepository rptcnSeleccRepository;
  @Autowired
  private IUsuarioSolrService usuarioSolrService;
  @Autowired
  UserConverter userConverter;

  @Override
  public void establecerReparticionSeleccionada(ReparticionSeleccionadaDTO repHabSelec,
      Usuario user) throws SecurityNegocioException {

    try {
      UsuarioSolr userSolr = usuarioSolrService.searchByUsername(user.getUsername());
      // Si el usuario es multireparticion se otiene la reparticion desde
      // la tabla SADE_REPARTICION_SELECCIONADA
      if (repHabSelec != null) {
        ReparticionSeleccionada mappedRepHabSelec = mapper.map(repHabSelec,
            ReparticionSeleccionada.class);

        // En caso de que YA haya elegido una reparticion, se debe pisar
        ReparticionSeleccionada repSeleccionadaPrev = rptcnSeleccRepository
            .findByUsuario(repHabSelec.getUsuario());

        if (userSolr != null) {
          if (repSeleccionadaPrev == null) {
            repSeleccionadaPrev = new ReparticionSeleccionada();
          }
          repSeleccionadaPrev.setReparticion(mappedRepHabSelec.getReparticion());
          repSeleccionadaPrev.setSector(mappedRepHabSelec.getSector());
          repSeleccionadaPrev.setUsuario(user.getUsername());
          mappedRepHabSelec = repSeleccionadaPrev;
          userSolr.setIsMultireparticion(user.getIsMultireparticion());
          userSolr.setCodigoReparticion(repHabSelec.getReparticion().getCodigo());
          userSolr.setCodigoSectorInterno(repHabSelec.getSector().getCodigo());
          userSolr.setCargo(user.getCargo());
        } else {
          userSolr = userConverter.cargarDTO(user);
        }
        rptcnSeleccRepository.save(mappedRepHabSelec);
      } else {
        if (userSolr != null) {
          userSolr.setIsMultireparticion(user.getIsMultireparticion());
        } else {
          userSolr = userConverter.cargarDTO(user);
        }
      }
      usuarioSolrService.addToIndex(userSolr);
    } catch (SecurityAccesoDatosException e) {
      throw new SecurityNegocioException(e.getMessage(), e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionSeleccionadaDTO> obtenerReparticionPorRepa(ReparticionDTO reparticion) {
    return ListMapper.mapList(
        rptcnSeleccRepository.findByReparticion(mapper.map(reparticion, Reparticion.class)),
        mapper, ReparticionSeleccionadaDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionSeleccionadaDTO> obtenerReparticionPorRepaYSector(
      ReparticionDTO reparticion, SectorDTO sector) {
    return ListMapper.mapList(
        rptcnSeleccRepository.findByReparticionAndSector(
            mapper.map(reparticion, Reparticion.class), mapper.map(sector, Sector.class)),
        mapper, ReparticionSeleccionadaDTO.class);
  }
}