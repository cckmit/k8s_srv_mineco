package com.egoveris.edt.base.service.impl.feriado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.feriado.Feriado;
import com.egoveris.edt.base.model.eu.feriado.FeriadoAuditoria;
import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;
import com.egoveris.edt.base.repository.eu.feriado.FeriadoAudiRepository;
import com.egoveris.edt.base.repository.eu.feriado.FeriadoRepository;
import com.egoveris.edt.base.service.feriado.FeriadoService;
import com.egoveris.shared.map.ListMapper;

@Service("feriadoService")
public class FeriadoServiceImpl implements FeriadoService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private FeriadoRepository feriadoRepository;
  @Autowired
  private FeriadoAudiRepository feriadoAudiRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<FeriadoDTO> obtenerFeriados() {
    List<Feriado> feriados = this.feriadoRepository.findAllByOrderByFecha();
    if (feriados == null) {
      feriados = new ArrayList<>();
    }
    return ListMapper.mapList(feriados, mapper, FeriadoDTO.class);
  }

  @Override
  public void guardarOModificarFeriado(FeriadoDTO feriado, String usuario) {
    FeriadoAuditoria audi = new FeriadoAuditoria();
    audi.setId(feriado.getId());
    audi.setMotivo(feriado.getMotivo());
    audi.setFechaFeriado(feriado.getFecha());
    audi.setFechaAuditoria(new Date());
    audi.setUsuario(usuario);

    if (audi.getIdFeriado() != null) {
      audi.setOperacion("MODIFICACION");
    }

    Feriado feriadoToBeSaved = mapper.map(feriado, Feriado.class);
    this.feriadoRepository.save(feriadoToBeSaved);
    audi.setIdFeriado(feriadoToBeSaved.getId());
    this.feriadoAudiRepository.save(audi);
  }

  @Override
  public void eliminarFeriado(FeriadoDTO feriado, String usuario) {
    FeriadoAuditoria audi = new FeriadoAuditoria();
    audi.setId(feriado.getId());
    audi.setMotivo(feriado.getMotivo());
    audi.setFechaFeriado(feriado.getFecha());
    audi.setFechaAuditoria(new Date());
    audi.setUsuario(usuario);
    audi.setOperacion("BAJA");

    this.feriadoRepository.delete(mapper.map(feriado, Feriado.class));
    this.feriadoAudiRepository.save(audi);
  }

}