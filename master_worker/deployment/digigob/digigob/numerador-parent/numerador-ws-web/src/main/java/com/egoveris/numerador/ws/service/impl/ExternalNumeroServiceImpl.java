package com.egoveris.numerador.ws.service.impl;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.numerador.base.service.NumeroGeneradoService;
import com.egoveris.numerador.base.service.NumeroSecuenciaService;
import com.egoveris.numerador.base.service.NumeroService;
import com.egoveris.numerador.model.exception.SistemaInvalidoException;
import com.egoveris.numerador.model.exception.ValidacionDatosException;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.numerador.model.model.NumeroGeneradoDTO;
import com.egoveris.numerador.model.model.NumeroSecuenciaDTO;
import com.egoveris.numerador.ws.service.ExternalNumeroService;
import com.egoveris.shared.map.ListMapper;
 
@Service
public class ExternalNumeroServiceImpl implements ExternalNumeroService {

	@Autowired
	private NumeroService numeroService;
	@Autowired
	private NumeroSecuenciaService numeroSecuenciaService;
	@Autowired
	private NumeroGeneradoService numeroGeneradoService;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;
	
	@Override
	public NumeroDTO obtenerNumeroSade(String usuario, String sistema,
			String codigoActuacion, String reparticionActuacion,
			String reparticionUsuario){

		return this.mapper.map(this.numeroService.obtenerNumero(usuario, sistema,
				codigoActuacion, reparticionActuacion, reparticionUsuario), NumeroDTO.class);
	}

	@Override
	public void anularNumeroSade(int anio, int numero) throws ValidacionDatosException{
		this.numeroService.anularNumero(anio, numero);

	}

	@Override
	public void confimarNumeroSade(int anio, int numero)
			throws ValidacionDatosException {
		this.numeroService.confimarNumero(anio, numero);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroSecuenciaDTO> obtenerCaratulas(int anio, int numero)
			 {

		return ListMapper.mapList(this.numeroSecuenciaService.obtenerCaratulas(anio, numero), this.mapper, NumeroSecuenciaDTO.class);
	}

	@Override
	public void registrarCaratulaConSecuencia(String usuario, String sistema,
			String codigoActuacion, int numero, int anio,
			String reparticionUsuario, String reparticionActuacion,
			String secuencia) {
		this.numeroService.crearCaratulaConSecuencia(usuario, sistema,
				codigoActuacion, numero, anio, reparticionUsuario,
				reparticionActuacion, secuencia);

	}

	@Override
	public NumeroDTO obtenerNroSadeCaratulacionII(String sistema,
			String usuario, int anio, String codigoActuacion,
			String reparticionUsuario, String reparticionActuacion,
			java.util.Date fechaCreacion) throws	SistemaInvalidoException {
		
		return this.mapper.map(this.numeroService.getNumeroCaratulacion(sistema, usuario, anio, 
				codigoActuacion, reparticionUsuario, reparticionActuacion, fechaCreacion), NumeroDTO.class);
	}

	@Override
	public NumeroDTO obtenerNumeroSadeConSecuencia(String usuario,
			String sistema, String codigoActuacion,
			String reparticionActuacion, String reparticionUsuario,
			List<String> listaSecuencia) throws SistemaInvalidoException {
				return this.mapper.map(this.numeroService.obtenerNumeroConSecuencia(usuario, sistema, codigoActuacion, 
				reparticionActuacion, reparticionUsuario, listaSecuencia), NumeroDTO.class);
	}
	
	@Override
	public NumeroGeneradoDTO consultarNumero(int anio, int numero,String secuencia){
		return this.mapper.map(this.numeroGeneradoService.consultarNumero(anio, numero, secuencia), NumeroGeneradoDTO.class);
	}	
	
}
