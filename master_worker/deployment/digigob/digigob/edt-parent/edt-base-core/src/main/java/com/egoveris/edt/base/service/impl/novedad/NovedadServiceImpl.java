package com.egoveris.edt.base.service.impl.novedad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.CategoriaDTO;
import com.egoveris.edt.base.model.eu.novedad.Novedad;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.repository.eu.novedad.NovedadRepository;
import com.egoveris.edt.base.service.novedad.INovedadService;
import com.egoveris.edt.base.util.EstadoNovedadEnum;
import com.egoveris.shared.map.ListMapper;

@Service("novedadService")
@Transactional
public class NovedadServiceImpl implements INovedadService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private NovedadRepository novedadRepository;

  @Override
  public NovedadDTO save(NovedadDTO novedad) {
    novedad.setFechaModificacion(new Date());
    Novedad savedNovedad = novedadRepository.save(mapper.map(novedad, Novedad.class));
    return mapper.map(savedNovedad, NovedadDTO.class);
  }

  @Override
  public NovedadDTO geybyId(Integer id) {
    NovedadDTO retorno = null;
    final Novedad resultado = novedadRepository.getOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, NovedadDTO.class);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> getActivas() {
    Date now = new Date();
    return ListMapper.mapList(novedadRepository
        .findByEstadoAndFechaFinAfterOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
            EstadoNovedadEnum.ACTIVO.name().toUpperCase(), now),
        mapper, NovedadDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> getPendientes() {
    Date now = new Date();
    List<String> listaEstados = new ArrayList<>();
    listaEstados.add(EstadoNovedadEnum.PENDIENTE.name());
    listaEstados.add(EstadoNovedadEnum.ACTIVO.name());

    return ListMapper.mapList(novedadRepository
        .findByEstadoInAndFechaFinAfterOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
            listaEstados, now),
        mapper, NovedadDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> getbyEstado(EstadoNovedadEnum estado) {
    return ListMapper.mapList(novedadRepository.findByEstado(estado.name()), mapper,
        NovedadDTO.class);
  }

  @Override
  public void delete(NovedadDTO novedad) {
    Novedad novedadToBeDeleted = mapper.map(novedad, Novedad.class);
    novedadToBeDeleted.setFechaModificacion(new Date());
    novedadToBeDeleted.setEstado(EstadoNovedadEnum.ELIMINADO.name());
    novedadRepository.save(novedadToBeDeleted);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> gethistorial() {
    return ListMapper.mapList(
        novedadRepository
            .findAllByOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(),
        mapper, NovedadDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> getByCategoria(CategoriaDTO categoria) {
    return ListMapper.mapList(
        novedadRepository.findByCategoria_CategoriaNombre(categoria.getCategoriaNombre()), mapper,
        NovedadDTO.class);
  }

  @Override
  public void cambiarEstadoJob() {
    // Change estado of novedades from pendiente to activo.
    Date now = new Date();
    List<Novedad> lstNovedadPendiente = novedadRepository
        .findByFechaInicioBeforeAndEstadoAndFechaFinAfter(now, EstadoNovedadEnum.PENDIENTE.name(),
            now);
    for (Novedad novedad : lstNovedadPendiente) {
      novedad.setEstado(EstadoNovedadEnum.ACTIVO.name());
    }
    novedadRepository.save(lstNovedadPendiente);

    // Change estado of novedades from activo to finalizado.
    List<Novedad> lstNovedadActivo = novedadRepository.findByFechaFinBeforeAndEstado(now,
        EstadoNovedadEnum.ACTIVO.name());
    for (Novedad novedad : lstNovedadActivo) {
      novedad.setEstado(EstadoNovedadEnum.FINALIZADO.name());
    }
    novedadRepository.save(lstNovedadActivo);

  }

}