package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;

/**
 * The Interface ObtenerReparticionServices.
 */
public interface ObtenerReparticionServices {
		
		/**
		 * Este metodo busca todas las reparticiones vigentes y activas.
		 *
		 * @return La lista de reparticiones vigentes y activas existentes
		 */
		public List<ReparticionBean> buscarReparticionesVigentesActivas();
		
		
		/**
		 * Valida si el string pasado como parámetro es un código de una reparticion válida.
		 *
		 * @param value Código de repartición a validar
		 * @return true, if successful
		 * @result <b>true</b> si existe una repartición con dicho código. <b>false</b> caso contrario
		 */
		public boolean validarCodigoReparticion(String value);

		/**
		 * Este metodo busca la reparticion asociada al
		 * usuario pasado como parametro.
		 *
		 * @param userName the user name
		 * @return la reparticion asociada al usuario o null
		 */
		public ReparticionBean buscarReparticionByUsuario(String userName);
		
		/**
		 * Este metodo busca una reparticion por su codigo.
		 *
		 * @param codigoReparticion the codigo reparticion
		 * @return devuelve el id de la reparticion o null
		 */
		public ReparticionBean getReparticionBycodigoReparticion(String codigoReparticion);
		
		/**
		 * Este metodo busca una reparticion por su id.
		 *
		 * @param idReparticion the id reparticion
		 * @return devuelve el codigo de la reparticion o null
		 */
		public ReparticionBean getReparticionById(Long idReparticion);
		
	}

	
	
	
	
	
	


