package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionAuditoriaDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.trata.TrataReparticionAuditoria;
import com.egoveris.te.base.repository.ReparticionTrataAuditoriaRepository;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class ReparticionTrataAuditoriaServImpl implements ReparticionTrataAuditoriaServ {
  private static final Logger logger = LoggerFactory
      .getLogger(ReparticionTrataAuditoriaServImpl.class);
  private DozerBeanMapper mapper = new DozerBeanMapper();
  @Autowired
  private ReparticionTrataAuditoriaRepository reparticionTrataAuditoriaDAO;

  public void AuditoriaTrataReparticion(TrataDTO trata,
      List<TrataReparticionDTO> reparticionesHabilitadas, String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "AuditoriaTrataReparticion(trata={}, reparticionesHabilitadas={}, usuario={}) - start",
          trata, reparticionesHabilitadas, usuario);
    }

    List<TrataReparticionAuditoriaDTO> auditoria = new ArrayList<TrataReparticionAuditoriaDTO>();
    List<TrataReparticionDTO> reparticionesOriginales = trata.getReparticionesTrata();
    int i;
    int cantOri = reparticionesOriginales.size();
    int cantHab = reparticionesHabilitadas.size();
    for (i = 0; i < cantOri; i++) {
      if (reparticionesHabilitadas.get(i).getHabilitacion() != reparticionesOriginales.get(i)
          .getHabilitacion()
          || reparticionesHabilitadas.get(i).getReserva() != reparticionesOriginales.get(i)
              .getReserva()) {
        TrataReparticionAuditoriaDTO itemAuditoria = new TrataReparticionAuditoriaDTO();
        itemAuditoria.setId_trata(trata.getId());
        itemAuditoria.setUsuario(usuario);
        itemAuditoria.setCodigoReparticion(reparticionesHabilitadas.get(i).getCodigoReparticion());
        itemAuditoria.setHabilitacion(reparticionesHabilitadas.get(i).getHabilitacion());
        itemAuditoria.setFechaOperacion(new Date());
        itemAuditoria.setTipoOperacion(ConstantesWeb.MODIF_TRATA_REPARTICION);
        itemAuditoria.setReserva(reparticionesHabilitadas.get(i).getReserva());
        auditoria.add(itemAuditoria);
      }
    }
    for (i = cantOri; i < cantHab; i++) {
      TrataReparticionAuditoriaDTO itemAuditoria = new TrataReparticionAuditoriaDTO();
      itemAuditoria.setId_trata(trata.getId());
      itemAuditoria.setUsuario(usuario);
      itemAuditoria.setCodigoReparticion(reparticionesHabilitadas.get(i).getCodigoReparticion());
      itemAuditoria.setHabilitacion(reparticionesHabilitadas.get(i).getHabilitacion());
      itemAuditoria.setFechaOperacion(new Date());
      itemAuditoria.setTipoOperacion(ConstantesWeb.CREACION_TRATA_REPARTICION);
      itemAuditoria.setReserva(reparticionesHabilitadas.get(i).getReserva());
      auditoria.add(itemAuditoria);
    }
    if (auditoria.size() != 0) {

      List<TrataReparticionAuditoria> trataEnt = ListMapper.mapList(auditoria, mapper,
          TrataReparticionAuditoria.class);

      this.reparticionTrataAuditoriaDAO.save(trataEnt);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("AuditoriaTrataReparticion(Trata, List<TrataReparticion>, String) - end");
    }
  }
}
