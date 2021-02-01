package com.egoveris.numerador.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.numerador.base.model.Numero;
import com.egoveris.numerador.base.model.NumeroCaratula;
import com.egoveris.numerador.base.model.NumeroCaratulaSector;
import com.egoveris.numerador.base.model.NumeroSecuencia;
import com.egoveris.numerador.base.model.NumeroTrabajo;
import com.egoveris.numerador.base.repository.NumeroCaratulaRepository;
import com.egoveris.numerador.base.repository.NumeroCaratulaSectorRepository;
import com.egoveris.numerador.base.repository.NumeroRepository;
import com.egoveris.numerador.base.repository.NumeroSecuenciaRepository;
import com.egoveris.numerador.base.repository.NumeroTrabajoRepository;
import com.egoveris.numerador.base.service.NumeroService;
import com.egoveris.numerador.base.service.SistemaService;
import com.egoveris.numerador.base.util.Utilitarios;
import com.egoveris.numerador.model.exception.SistemaInvalidoException;
import com.egoveris.numerador.model.exception.ValidacionDatosException;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.numerador.util.Constantes;
import com.egoveris.shared.map.ListMapper;

@Service
@Transactional
public class NumeroServiceImpl implements NumeroService {
	private static final Logger logger = LoggerFactory.getLogger(NumeroServiceImpl.class);
	
	@Autowired
	private NumeroRepository numeroRepository;
	@Autowired
	private NumeroTrabajoRepository numeroTrabajoRepository;
	@Autowired
	private NumeroSecuenciaRepository numeroSecuenciaRepository;
	@Autowired
	private NumeroCaratulaRepository numeroCaratulaRepository;
	@Autowired
	private NumeroCaratulaSectorRepository numeroCaratulaSectorRepository;
	@Autowired
	private SistemaService sistemaService;
	@Value("${app.numerador.rep.actu.GEDO}")
	private String reparticionActuacionGEDO;
	@Value("${numerador.proceso.reintento}")
	private String cantidadReintentos;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	@Override
	public NumeroDTO obtenerNumero(String usuario, String sistema, String codigoActuacion, String reparticionActuacion,
			String reparticionUsuario) {
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumero(usuario={}, sistema={}, codigoActuacion={}, reparticionActuacion={}, reparticionUsuario={}) - start", usuario, sistema, codigoActuacion, reparticionActuacion);
		}
		
		NumeroDTO numeroDTO =  this.obtenerNumeroReal(usuario, sistema, codigoActuacion, reparticionActuacion, reparticionUsuario, null);
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumero(NumeroDTO) - end", numeroDTO);
		}
		
		return numeroDTO;
	}

	@Override
	public NumeroDTO obtenerNumeroConSecuencia(String usuario, String sistema, String codigoActuacion,
			String reparticionActuacion, String reparticionUsuario, List<String> listaSecuencia) {
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumeroConSecuencia(usuario={}, sistema={}, codigoActuacion={}, reparticionActuacion={}, reparticionUsuario={}, listaSecuencia={}) - start", usuario, sistema, codigoActuacion, reparticionActuacion, listaSecuencia);
		}
		
		NumeroDTO numeroDTO = this.obtenerNumeroReal(usuario, sistema, codigoActuacion, reparticionActuacion, reparticionUsuario,
				listaSecuencia);
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumeroConSecuencia(NumeroDTO) - end", numeroDTO);
		}
		return numeroDTO;
	}

	@Override
	public void crearNumero(NumeroDTO numero) {
		if(logger.isDebugEnabled()){
			logger.debug("crearNumero(numero={}) - start", numero);
		}
		
		numeroRepository.save(this.mapper.map(numero, Numero.class));
		
		if(logger.isDebugEnabled()){
			logger.debug("crearNumero() - end");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void crearNumero(List<NumeroDTO> numeros) {
		if(logger.isDebugEnabled()){
			logger.debug("crearNumero(numeros={}) - start", numeros);
		}
		
		numeroRepository.save(ListMapper.mapList(numeros, this.mapper, Numero.class));
		
		if(logger.isDebugEnabled()){
			logger.debug("crearNumero() - end");
		}
	}

	@Override
	public List<NumeroDTO> buscarNumeroByEstado(int anio, int numero, String estado) {		
		if(logger.isDebugEnabled()){
			logger.debug("buscarNumeroByEstado(anio={}, numero={}, estado={}) - start", anio, numero, estado);
		}

		List<Numero> numeroList = numeroRepository.findByAnioAndNumeroAndEstado(anio, numero, estado);

		if (numeroList == null) {
			numeroList = new ArrayList<Numero>();
		}

		List<NumeroDTO> numResultList = new ArrayList<NumeroDTO>();
		NumeroDTO numResult;

		for (Numero numeroAux : numeroList) {
			numResult = new NumeroDTO();
			numResult.setAnio(numeroAux.getAnio());
			numResult.setNumero(numeroAux.getNumero());
			numResultList.add(numResult);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("buscarNumeroByEstado(List<NumeroDTO>) - end", numResultList);
		}
		
		return numResultList;
	}

	@Override
	public NumeroDTO buscarNumero(int anio, int numero, String secuencia) {
		if(logger.isDebugEnabled()){
			logger.debug("buscarNumero(anio={}, numero={}, secuencia={}) - start", anio, numero, secuencia);
		}		
		
		NumeroDTO numeroDTO = null;		
		
		Numero num = this.numeroRepository.findByAnioAndNumeroAndSecuencia(anio, numero, secuencia);
		if (num != null) {
			numeroDTO = this.mapper.map(num, NumeroDTO.class);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("buscarNumero(NumeroDTO) - end", numeroDTO);
		}
		
		return numeroDTO;
	}

	@Override
	public NumeroDTO obtenerProximoNumero(int anio) {
		if(logger.isDebugEnabled()){
			logger.debug("obtenerProximoNumero(anio={}) - start", anio);
		}	
		
		NumeroDTO numeroResult = new NumeroDTO();
		NumeroSecuencia numeroSecuencia;

		numeroSecuencia = numeroSecuenciaRepository.findByAnio(anio);

		if (numeroSecuencia == null) {
			numeroSecuencia = new NumeroSecuencia();
			numeroSecuencia.setAnio(anio);
			numeroSecuencia.setNumero(1);
			numeroSecuenciaRepository.save(numeroSecuencia);
		}
		numeroResult.setAnio(numeroSecuencia.getAnio());
		numeroResult.setNumero(numeroSecuencia.getNumero());
		
		// Se incrementa en 1 el numero de secuencia
		incrementNumeroByAnio(anio);

		if(logger.isDebugEnabled()){
			logger.debug("obtenerProximoNumero(NumeroDTO) - end", numeroResult);
		}	
		
		return numeroResult;
	}

	@Override
	public void anularNumero(int anio, int numero) throws ValidacionDatosException {
		if(logger.isDebugEnabled()){
			logger.debug("anularNumero(anio={}, numero={}) - start", anio, numero);
		}
		
		this.cambiarEstadoNumero(Constantes.ESTADO_BAJA, anio, numero);
		
		if(logger.isDebugEnabled()){
			logger.debug("anularNumero() - end");
		}
	}

	@Override
	public void confimarNumero(int anio, int numero) throws ValidacionDatosException {
		if(logger.isDebugEnabled()){
			logger.debug("confimarNumero(anio={}, numero={}) - start", anio, numero);
		}
		
		this.cambiarEstadoNumero(Constantes.ESTADO_USADO, anio, numero);
		
		if(logger.isDebugEnabled()){
			logger.debug("confimarNumero() - end");
		}
	}

	@Override
	public NumeroDTO getNumeroCaratulacion(String sistema, String usuario, int anio, String codigoActuacion,
			String reparticionUsuario, String reparticionActuacion, Date fechaCreacion) {
		if(logger.isDebugEnabled()){
			logger.debug("getNumeroCaratulacion() - start");
		}
		
		List<String> listaSistemas = sistemaService.buscarNombreSistemasByEstado(true);
		NumeroDTO proximoNumero;

		if (anio < Integer.parseInt(Utilitarios.obtenerAnioActual())) {
			if (listaSistemas.contains(sistema)) {
				proximoNumero = this.obtenerProximoNumero(anio);
				NumeroTrabajo numeroTrabajo = buildNumeroTrabajo(sistema, usuario, codigoActuacion, anio,
						proximoNumero.getNumero(), Constantes.CODIGO_SECUENCIA_ORIGINAL, reparticionActuacion,
						reparticionUsuario, Constantes.ESTADO_TRANSITORIO, fechaCreacion);
				numeroTrabajoRepository.save(numeroTrabajo);
			} else {
				throw new SistemaInvalidoException("El sistema '" + sistema + "' no es válido");
			}
		} else {
			throw new SistemaInvalidoException("Dato inválido: el año " + anio + " es mayor que el año en curso "
					+ Integer.parseInt(Utilitarios.obtenerAnioActual()));
		}
		if(logger.isDebugEnabled()){
			logger.debug("getNumeroCaratulacion() - end");
		}
		
		return proximoNumero;
	}

	@Override
	public void crearCaratulaConSecuencia(String usuario, String sistema, String codigoActuacion, int numero, int anio,
			String reparticionUsuario, String reparticionActuacion, String secuencia) {
		if(logger.isDebugEnabled()){
			logger.debug("crearCaratulaConSecuencia() - start");
		}
		
		String estado = Constantes.ESTADO_USADO;
		Numero newNumero = numeroRepository.findByAnioAndNumeroAndSecuencia(anio, numero, secuencia);
		if (newNumero == null) {
			newNumero = this.buildNumero(sistema, reparticionUsuario, anio, numero, secuencia, estado, new Date(),
					new Date());
		}
		NumeroCaratula numeroCaratula = this.buildNumeroCaratula(sistema, usuario, new Date(), reparticionActuacion,
				reparticionUsuario, codigoActuacion, newNumero);
		numeroCaratulaRepository.save(numeroCaratula);
		this.inyectarSectorCaratula(numeroCaratula);
		
		if(logger.isDebugEnabled()){
			logger.debug("crearCaratulaConSecuencia() - end");
		}
	}

	@Override
	public NumeroDTO incrementNumeroByAnio(int anio) {
		if(logger.isDebugEnabled()){
			logger.debug("incrementNumeroByAnio(anio={}) - end", anio);
		}
		
		NumeroSecuencia numSecuencia;
		numSecuencia = numeroSecuenciaRepository.findByAnio(anio);

		if (numSecuencia != null) {
			numSecuencia.setNumero(numSecuencia.getNumero() + 1);
		} else {
			numSecuencia = new NumeroSecuencia();
			numSecuencia.setAnio(anio);
			numSecuencia.setNumero(1);
		}
		
		numeroSecuenciaRepository.save(numSecuencia);
		
		NumeroDTO numeroDTO = new NumeroDTO();
		numeroDTO.setAnio(numSecuencia.getAnio());
		numeroDTO.setNumero(numSecuencia.getNumero());
		
		if(logger.isDebugEnabled()){
			logger.debug("incrementNumeroByAnio(NumeroDTO) - end", numSecuencia);
		}
		
		return numeroDTO;
	}

	private NumeroDTO obtenerNumeroReal(String usuario, String sistema, String codigoActuacion,
			String reparticionActuacion, String reparticionUsuario, List<String> listaSecuencia) {
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumeroReal() - start");
		}

		List<String> listaSistemas = sistemaService.buscarNombreSistemasByEstado(true);
		Integer proxNum;
		NumeroDTO proximoNro;
		Integer anioActual = Integer.parseInt(Utilitarios.obtenerAnioActual());
		Date hoy = new Date();
		List<NumeroTrabajo> listNumeroTrabajo = new ArrayList<NumeroTrabajo>();
		if (listaSistemas.contains(sistema)) {
			proximoNro = this.obtenerProximoNumero(anioActual.intValue());
			proxNum = proximoNro != null ? proximoNro.getNumero() : null;

			NumeroTrabajo numeroSadetrabajoElement = buildNumeroTrabajo(sistema, usuario, codigoActuacion, anioActual,
					proxNum, Constantes.CODIGO_SECUENCIA_ORIGINAL, reparticionActuacion, reparticionUsuario,
					Constantes.ESTADO_TRANSITORIO, hoy);

			listNumeroTrabajo.add(numeroSadetrabajoElement);

			if (listaSecuencia != null) {
				for (String secuencia : listaSecuencia) {
					NumeroTrabajo numeroSadetrabajoSecuencia = buildNumeroTrabajo(sistema, usuario, codigoActuacion,
							anioActual, proxNum, secuencia, reparticionActuacion, reparticionUsuario,
							Constantes.ESTADO_TRANSITORIO, hoy);
					listNumeroTrabajo.add(numeroSadetrabajoSecuencia);
				}
			}
			numeroTrabajoRepository.save(listNumeroTrabajo);

		} else {
			throw new SistemaInvalidoException("El sistema '" + sistema + "' no es válido");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerNumeroReal(NumeroDTO) - end",proximoNro);
		}
		return proximoNro;
	}

	private NumeroTrabajo buildNumeroTrabajo(String sistema, String usuario, String codigoActuacion, Integer anioActual,
			Integer numero, String secuencia, String reparticionActuacion, String reparticionUsuario, String estado,
			Date fechaCreacion) {
		if(logger.isDebugEnabled()){
			logger.debug("buildNumeroTrabajo() - start");
		}

		NumeroTrabajo numeroSadetrabajoElement = new NumeroTrabajo();
		numeroSadetrabajoElement.setSistema(sistema);
		numeroSadetrabajoElement.setUsuario(usuario.toUpperCase().trim());
		numeroSadetrabajoElement.setTipoActuacion(codigoActuacion.toUpperCase().trim());
		numeroSadetrabajoElement.setAnio(anioActual);
		numeroSadetrabajoElement.setNumero(numero);
		numeroSadetrabajoElement.setSecuencia(secuencia);
		numeroSadetrabajoElement.setReparticionActuacion(reparticionActuacion.toUpperCase().trim());
		numeroSadetrabajoElement.setReparticionUsuario(reparticionUsuario.toUpperCase().trim());
		numeroSadetrabajoElement.setFechaCreacion(fechaCreacion);
		
		if(logger.isDebugEnabled()){
			logger.debug("buildNumeroTrabajo(NumeroTrabajo) - end", numeroSadetrabajoElement);
		}
		return numeroSadetrabajoElement;
	}

	private void cambiarEstadoNumero(String estado, int anio, int numero) throws ValidacionDatosException {
		if(logger.isDebugEnabled()){
			logger.debug("cambiarEstadoNumero(estado={}, anio={}, numero={}) - start", estado, anio, numero);
		}
		
		List<NumeroTrabajo> listanumeroSadeTrabajoResult;
		List<Numero> listaNroscConfirmados = new ArrayList<Numero>();
		List<NumeroCaratula> listaCaratulas = new ArrayList<NumeroCaratula>();
		listanumeroSadeTrabajoResult = numeroTrabajoRepository.findByAnioAndNumero(anio, numero);
		if (listanumeroSadeTrabajoResult.isEmpty()) {
			throw new ValidacionDatosException("No se ha encontrado el número para el año " + anio + " ingresado");
		}
		for (NumeroTrabajo numeroSadeTrabajo : listanumeroSadeTrabajoResult) {
			Numero numeroSade = new Numero();
			NumeroCaratula numeroCaratula = new NumeroCaratula();
			BeanUtils.copyProperties(numeroSadeTrabajo, numeroSade);
			BeanUtils.copyProperties(numeroSadeTrabajo, numeroCaratula);
			numeroSade.setEstado(estado);
			numeroSade.setFechaModificacion(new Date());
			numeroCaratula.setFechaCreacionCaratula(numeroSade.getFechaCreacion());
			numeroCaratula.setNumeroSade(numeroSade);
			listaNroscConfirmados.add(numeroSade);
			if ("GEDO".equals(numeroCaratula.getSistema())) {
				numeroCaratula.setReparticionActuacion(reparticionActuacionGEDO);
			}
			listaCaratulas.add(numeroCaratula);
		}
		numeroRepository.save(listaNroscConfirmados);
		numeroCaratulaRepository.save(listaCaratulas);
		this.inyectarSectoresAListaDeCaratulas(listaCaratulas);
		numeroTrabajoRepository.delete(listanumeroSadeTrabajoResult);
		
		if(logger.isDebugEnabled()){
			logger.debug("cambiarEstadoNumero() - end");
		}
	}

	private void inyectarSectoresAListaDeCaratulas(List<NumeroCaratula> listaCaratulas) {
		if (listaCaratulas != null && !listaCaratulas.isEmpty()) {
			for (NumeroCaratula n : listaCaratulas) {
				this.inyectarSectorCaratula(n);
			}
		}
	}

	private void inyectarSectorCaratula(NumeroCaratula numeroCaratula) {
		NumeroCaratulaSector n = new NumeroCaratulaSector();
		if (numeroCaratula != null) {
			NumeroCaratulaSector numeroSector = this.numeroCaratulaSectorRepository
					.findByUsuario(numeroCaratula.getUsuario());
			if (numeroSector != null) {
				if (!numeroSector.getSectorInternoCaratula().isEmpty()) {
					n.setId(numeroCaratula.getId());
					n.setSectorInternoCaratula(numeroSector.getSectorInternoCaratula());
					n.setUsuario(numeroCaratula.getUsuario());
					this.numeroCaratulaSectorRepository.save(n);
				}
			}
		}
	}

	private Numero buildNumero(String sistema, String usuario, int anio, int numero, String secuencia, String estado,
			Date fechaCreacion, Date fechaModificacion) {
		Numero newNumero = new Numero();
		newNumero.setAnio(anio);
		newNumero.setNumero(numero);
		newNumero.setSecuencia(secuencia);
		newNumero.setEstado(estado);
		newNumero.setFechaCreacion(fechaCreacion);
		newNumero.setFechaModificacion(fechaModificacion);

		return newNumero;
	}

	private NumeroCaratula buildNumeroCaratula(String sistema, String usuario, Date fechaCreacionCaratula,
			String reparticionActuacion, String reparticionUsuario, String tipoActuacion, Numero numeroSade) {

		NumeroCaratula numeroCaratula = new NumeroCaratula();
		numeroCaratula.setSistema(sistema);
		numeroCaratula.setUsuario(usuario.trim());
		numeroCaratula.setFechaCreacionCaratula(fechaCreacionCaratula);
		numeroCaratula.setReparticionActuacion(reparticionActuacion.trim());
		numeroCaratula.setReparticionUsuario(reparticionUsuario.trim());
		numeroCaratula.setTipoActuacion(tipoActuacion.trim());
		numeroCaratula.setNumeroSade(numeroSade);

		return numeroCaratula;
	}

}
