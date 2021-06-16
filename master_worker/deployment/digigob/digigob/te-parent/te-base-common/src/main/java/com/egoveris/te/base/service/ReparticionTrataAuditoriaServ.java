package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;

/**
 * The Interface ReparticionTrataAuditoriaServ.
 */
public interface ReparticionTrataAuditoriaServ {
	
	/**
	 * Metedo que audita los cambios realizados en las Reparticiones por Trata.
	 *
	 * @param trata the trata
	 * @param reparticionesHabilitadas the reparticiones habilitadas
	 * @param usuario the usuario
	 */
  public void AuditoriaTrataReparticion (TrataDTO trata, List<TrataReparticionDTO> reparticionesHabilitadas,String usuario);
}
