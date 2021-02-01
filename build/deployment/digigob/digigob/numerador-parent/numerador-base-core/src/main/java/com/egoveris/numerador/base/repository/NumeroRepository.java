package com.egoveris.numerador.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.numerador.base.model.Numero;

/**
 * The Interface NumeroRepository.
 */
public interface NumeroRepository extends JpaRepository<Numero, Integer >{

	/*	
	 * 
	  	SAVE() LISTO
	  	public void crearNumeroSade(List<NumeroSade> listNumerosSade)
			throws AccesoDatosException;
			
		SAVE() LISTO
		public void crearNumeroSade(NumeroSade listNumerosSade)
		throws AccesoDatosException;		
	 	
	 	FIND()
	 	public NumeroSade buscarNumeroSade(int anio, int numero, String secuencia )
	 	
	 	FIND()
	 	public List<NumeroSadeResult> buscarNroByAnio(int anio, int numero,
			String estado)
	 */
	

	/**
	 * Buscar por anio y numero en la entidad Numero.
	 *
	 * @param anio the anio
	 * @param numero the numero
	 * @return the list
	 */
	List<Numero> findByAnioAndNumero(Integer anio, Integer numero);
	
	/**
	 * Find by anio and numero and estado.
	 *
	 * @param anio the anio
	 * @param numero the numero
	 * @param estado the estado
	 * @return the list
	 */
	List<Numero> findByAnioAndNumeroAndEstado(Integer anio, Integer numero, String estado);
	
	/**
	 * Find by anio and numero and secuencia.
	 *
	 * @param anio the anio
	 * @param numero the numero
	 * @param secuencia the secuencia
	 * @return the list
	 */
	Numero findByAnioAndNumeroAndSecuencia(Integer anio, Integer numero, String secuencia);
	
}
