package com.egoveris.deo.base.service.impl;


import com.egoveris.deo.base.exception.TipoReservaNoExisteException;
import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.repository.TipoReservaRepository;
import com.egoveris.deo.base.service.TipoReservaService;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.ReparticionAcumuladaDTO;
import com.egoveris.deo.model.model.TipoReservaDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class TipoReservaServiceImpl implements TipoReservaService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TipoReservaServiceImpl.class);

	@Autowired
	private TipoReservaRepository tipoReservaRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
 @Qualifier("deoCoreMapper")
 private Mapper mapper;
 	
	public void actualizarReservaReparticionDocumento(DocumentoDTO documento,
			TipoReservaDTO tipoReserva, String reparticion, List<String> reparticionesRectoras, String usuarioOReparticionAlta) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReservaReparticionDocumento(DocumentoDTO, TipoReservaDTO, String, List<String>, String) - start"); //$NON-NLS-1$
    }
		
		boolean huboCambios = false;

		// Si la reserva es de mayor jerarquia, realizo el cambio.
		if (documento.getTipoReserva().getId() < tipoReserva.getId()){
			documento.setTipoReserva(tipoReserva);
			huboCambios = true;
		}
		
		// Comparo las Reparticiones Rectoras para agregar las que hagan falta o ninguna.
		if(reparticionesRectoras.isEmpty()){
			huboCambios = true;
		}else if (compararReparticionesRectorasConReparticionesAcumuladas(reparticionesRectoras, documento.getReparticionesAcumuladas(), usuarioOReparticionAlta)){
			huboCambios = true;
		}
		
		// Comparo la reparticion enviada para agregarla si falta o no.
		if (compararReparticionConReparticionesAcumuladas(reparticion, documento.getReparticionesAcumuladas(), usuarioOReparticionAlta)){
			huboCambios = true;
		}
		
		// Guardo en caso de se hayan producido cambios.
		try{
			if(huboCambios){
				documentoRepository.save(mapper.map(documento, Documento.class));
			}
		}catch(Exception e){
			LOGGER.error("Error al guardar el documento", e);
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReservaReparticionDocumento(DocumentoDTO, TipoReservaDTO, String, List<String>, String) - end"); //$NON-NLS-1$
    }
 	}

	/**
	 * Comparo las reparticiones rectoras con las acumuladas. En caso de no encontrar alguna, la agrego.
	 * @param reparticionesRectoras
	 * @param reparticionesAcumuladas
	 * @param usuarioOReparticionAlta
	 * @return
	 */
	private boolean compararReparticionesRectorasConReparticionesAcumuladas(List<String> reparticionesRectoras, 
			Set<ReparticionAcumuladaDTO> reparticionesAcumuladas,	String usuarioOReparticionAlta) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("compararReparticionesRectorasConReparticionesAcumuladas(List<String>, Set<ReparticionAcumuladaDTO>, String) - start"); //$NON-NLS-1$
    }

		boolean huboCambios = false;
		
		if (reparticionesRectoras != null && !reparticionesRectoras.isEmpty()){
			for (String rr : reparticionesRectoras){
				if (compararReparticionConReparticionesAcumuladas(rr, reparticionesAcumuladas, usuarioOReparticionAlta)){
					huboCambios = true;
				}
			}
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("compararReparticionesRectorasConReparticionesAcumuladas(List<String>, Set<ReparticionAcumuladaDTO>, String) - end"); //$NON-NLS-1$
    }
  		return huboCambios;
	}
	
	/**
	 * Comparo una reparticion determinada con las reparticiones acumuladas. En caso de no encontrar alguna, la agrego.
	 * @param reparticion
	 * @param reparticionesAcumuladas
	 * @param usuarioOReparticionAlta
	 * @return
	 */
	private boolean compararReparticionConReparticionesAcumuladas (String reparticion, 
			Set<ReparticionAcumuladaDTO> reparticionesAcumuladas,	String usuarioOReparticionAlta){
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("compararReparticionConReparticionesAcumuladas(String, Set<ReparticionAcumuladaDTO>, String) - start"); //$NON-NLS-1$
    }
		
		boolean huboCambios = false;
		boolean existeReparticion = false;
		
		for (ReparticionAcumuladaDTO ra : reparticionesAcumuladas){
			if (ra.getReparticion().trim().equals(reparticion.trim())){
				existeReparticion = true;
			}
		}
		
		if(!existeReparticion){
			ReparticionAcumuladaDTO reparticionAcumulada = crearReparticionAcumulada(reparticion, usuarioOReparticionAlta);
			reparticionesAcumuladas.add(reparticionAcumulada);
			huboCambios = true;
		}
		
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("compararReparticionConReparticionesAcumuladas(String, Set<ReparticionAcumuladaDTO>, String) - end"); //$NON-NLS-1$
    }
  		return huboCambios;
	}

	/**
	 * Creacion de la ReparticionAcumulada que se agregara a un documento determinado.
	 * @param reparticion
	 * @param documento
	 * @param usuarioOReparticionAlta
	 * @return
	 */
	private ReparticionAcumuladaDTO crearReparticionAcumulada(String reparticion, String usuarioOReparticionAlta) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearReparticionAcumulada(String, String) - start"); //$NON-NLS-1$
    }

		ReparticionAcumuladaDTO reparticionAcumulada = new ReparticionAcumuladaDTO();
		reparticionAcumulada.setFechaModificacion(new Date());
		reparticionAcumulada.setReparticion(reparticion);
		reparticionAcumulada.setTipoOperacion("ALTA");
		reparticionAcumulada.setUserName(usuarioOReparticionAlta);
		
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearReparticionAcumulada(String, String) - end"); //$NON-NLS-1$
    }
  		return reparticionAcumulada;
	}
	
	/**
	 * Obtiene una reserva de la base de datos, segun su descrpcion.
	 * @param reserva
	 */
	public TipoReservaDTO obtenerReserva(String reserva) throws TipoReservaNoExisteException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerReserva(String) - start"); //$NON-NLS-1$
    }
		
		TipoReservaDTO tipoReserva = null;
		
		try{
			tipoReserva = mapper.map(tipoReservaRepository.findByReserva(reserva),TipoReservaDTO.class);
			    
		}catch(Exception e){
			LOGGER.error("Error en la búsqueda del tipo de reserva", e);
		}
		
		if (tipoReserva == null){
			throw new TipoReservaNoExisteException(reserva);
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerReserva(String) - end"); //$NON-NLS-1$
    }
  		return tipoReserva;
	}

}
