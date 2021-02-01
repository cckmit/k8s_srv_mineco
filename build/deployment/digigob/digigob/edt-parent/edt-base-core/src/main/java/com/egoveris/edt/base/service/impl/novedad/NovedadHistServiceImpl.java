package com.egoveris.edt.base.service.impl.novedad;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadHist;
import com.egoveris.edt.base.model.eu.novedad.NovedadHistDTO;
import com.egoveris.edt.base.repository.eu.novedad.NovedadHistRepository;
import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.shared.map.ListMapper;

@Service("novedadHistService")
@Transactional
public class NovedadHistServiceImpl implements INovedadHistService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private NovedadHistRepository novedadHistRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadHistDTO> getHistorial(Integer id) {
    return ListMapper.mapList(novedadHistRepository
        .findByRevisionOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
            id),
        mapper, NovedadHistDTO.class);
  }

  @Override
  public void save(NovedadHistDTO novedadHist) {
    NovedadHist nvdadToBeSaved = mapper.map(novedadHist, NovedadHist.class);
    nvdadToBeSaved.setFechaModificacion(new Date());
    novedadHistRepository.save(nvdadToBeSaved);
  }

  @Override
  public void guardarHistorico(NovedadDTO novedad, int tipo) {
    NovedadHistDTO novedadHist = new NovedadHistDTO();

    novedadHist.setCategoria(novedad.getCategoria());

    StringBuilder aplicaciones = new StringBuilder("");
    for (AplicacionDTO a : novedad.getAplicaciones()) {
      aplicaciones.append(a.getNombreAplicacion());
      aplicaciones.append(" ");
    }

    novedadHist.setAplicaciones(aplicaciones.toString());
    novedadHist.setEstado(novedad.getEstado());
    novedadHist.setFechaFin(novedad.getFechaFin());
    novedadHist.setFechaInicio(novedad.getFechaInicio());
    novedadHist.setNovedad(novedad.getNovedad());
    novedadHist.setRevision(novedad.getId());
    novedadHist.setTipoRevision(tipo);
    novedadHist.setUsuario(novedad.getUsuario());

    this.save(novedadHist);
  }

}