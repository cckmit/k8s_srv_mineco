package com.egoveris.te.base.service;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.GenerarCopiaBean;
import com.egoveris.te.base.model.GenerarCopiaBeanDTO;
import com.egoveris.te.base.repository.GenerarCopiaRepository;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class GenerarCopiaServiceImpl implements GenerarCopiaService {
  private static final Logger logger = LoggerFactory.getLogger(GenerarCopiaServiceImpl.class);

  @Autowired
  private GenerarCopiaRepository generarCopiaRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Override
  @Transactional
  public void save(GenerarCopiaBeanDTO bean) {
    if (logger.isDebugEnabled()) {
      logger.debug("save(bean={}) - start", bean);
    }

    GenerarCopiaBean generarCopiaBean = mapper.map(bean, GenerarCopiaBean.class);

    generarCopiaRepository.save(generarCopiaBean);

    if (logger.isDebugEnabled()) {
      logger.debug("save(GenerarCopiaBean) - end");
    }
  }

  @Override
  public List<GenerarCopiaBeanDTO> buscarBeansValidosParaReintento(Integer reintentos) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarBeansValidosParaReintento(reintentos={}) - start", reintentos);
    }

    List<GenerarCopiaBean> generarCopiaBean = generarCopiaRepository
        .findByEstadoDeReintentoAndReintentosLessThan(ConstantesWeb.GENERAR_COPIA_ESTADO_PENDIENTE,
            reintentos);
    List<GenerarCopiaBeanDTO> generarCopiaBeanDto = ListMapper.mapList(generarCopiaBean, mapper,
        GenerarCopiaBeanDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarBeansValidosParaReintento(Integer) - end - return value={}",
          generarCopiaBeanDto);
    }
    return generarCopiaBeanDto;
  }

}
