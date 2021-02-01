package com.egoveris.te.base.service.tarjetausuario;

import com.egoveris.te.base.model.TarjetaDatosUsuarioBean;

public interface TarjetaDatosUsuarioService {

	/**
	 * Obtiene información sobre un usuario para ser presentada en forma de
	 * tarjeta.
	 * 
	 * @param username
	 * @return
	 */
	public TarjetaDatosUsuarioBean getTarjetaDatosUsuarioBean(String username);

	/**
	 * Retorna el mensaje a mostrar en caso que el usuario no se encuentre o no
	 * tenga detalles disponibles. Opcional, de lo contrario mostrará un valor
	 * por defecto TarjetaDatosUsuarioComposer.MSG_SIN_DETALLE_DEFECTO
	 * 
	 * @return
	 */
	public String getMensajeSinDetalle();

}
