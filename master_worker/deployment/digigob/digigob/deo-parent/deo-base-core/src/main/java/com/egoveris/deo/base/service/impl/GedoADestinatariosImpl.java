package com.egoveris.deo.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.base.service.GedoADestinatarios;
import com.egoveris.deo.base.service.SuscripcionUtils;
import com.egoveris.deo.util.Constantes;
import com.egoveris.te.model.exception.ActividadException;
import com.egoveris.te.ws.service.IActividadExternalService; 
@Service
@Transactional
public class GedoADestinatariosImpl implements GedoADestinatarios {

  @Autowired
  private SuscripcionUtils suscripcionUtils;
  
  @Autowired
  private IActividadExternalService actividadesExtService;

  private static final Logger LOGGER = LoggerFactory.getLogger(GedoADestinatariosImpl.class);

  /**
   * Metodo que cambiar el estado de los expedietes.
   * 
   * @param workflowId
   * @param estado
   */  
	@Override
	public void notificarADestinatarios(String indicador, String numero, String executionId, String usuario) 
			throws ActividadException {
		try {
		if (Constantes.NAM_TE.equalsIgnoreCase(indicador) || "EE".equalsIgnoreCase(indicador)) {
			actividadesExtService.notificarDocumentoGenerado(indicador, numero, executionId, usuario);
			} 
		} catch (final ActividadException e) {
	      throw e;
		}
	}
 
}
