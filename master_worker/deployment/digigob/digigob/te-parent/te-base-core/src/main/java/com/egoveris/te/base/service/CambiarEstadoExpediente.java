package com.egoveris.te.base.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.util.ConstantesCore;

@Service
@Transactional
public class CambiarEstadoExpediente implements ICambiarEstadoExpediente {

  private static transient Logger logger = LoggerFactory.getLogger(CambiarEstadoExpediente.class);
  
  @Autowired
  private IActividadService actividadService;

  	@Override
  	public void cambiarEstadoExpediente(ActividadDTO obj) {
		if (null != obj	&& !(ConstantesCore.ESTADO_CERRADO).equalsIgnoreCase(obj.getEstado())) {
			obj.setFechaCierre(new Date()); 
			obj.setEstado(ConstantesCore.ESTADO_CERRADO);
			actividadService.actualizarActividad(obj);
		}
	
  	}

  
}