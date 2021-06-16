package com.egoveris.ffdd.render.model;

import org.zkoss.zk.ui.Component;

public interface ComponentZkExt extends Component {
	

	/**
	 * Obtiene el id del origen de la representacion de BBDD del comp para el
	 * formulario. En resumen: DF_FORM_COMPONENT.ID
	 * 
	 * @return
	 */
	public Integer getIdComponentForm();
	
	/**
	 * Setea el id del origen de la representacion de BBDD del comp para el formulario.
	 * @param inte
	 */
	public void setIdComponentForm(Integer inte);

	/**
	 * Nombre del componente
	 */
	public String getName();

	/**
	 * Nombre del componente
	 */
	public void setName(String name);

}
