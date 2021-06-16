package com.egoveris.ffdd.render.service;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.CampoBusqueda;

/**
 * The Interface IFormManager.
 *
 * @param <T> the generic type
 */
public interface IFormManager<T> {

	/**
	 * Obtiene el componente "formulario". Este componente contiene la
	 * estructura del formulario.
	 * 
	 * @return componente
	 */
	public T getFormComponent();

	/**
	 * Obtiene el componente "formulario". Este componente contiene la
	 * estructura del formulario con los valores de la transaccion "uuid"
	 *
	 * @param uuid            de transaccion
	 * @return componente
	 * @throws DynFormException the dyn form exception
	 */
	public T getFormComponent(Integer uuid) throws DynFormException;

	/**
	 * Obtiene el componente "formulario". Este componente contiene la
	 * estructura del formulario con los valores de la transaccion "uuid" El
	 * formulario se presenta en modo lectura si se especifica en el argumento
	 * readOnly
	 *
	 * @param uuid            de transaccion
	 * @param disabled            true si se requieren ver todos los componentes en modo lectura
	 *            disabled
	 * @return componente
	 * @throws DynFormException the dyn form exception
	 */
	public T getFormComponent(Integer uuid, boolean disabled) throws DynFormException;

	/**
	 * Obtiene el componente solicitado del formulario.
	 *
	 * @param idOperacion the id operacion
	 * @param uuid the uuid
	 * @param disabled the disabled
	 * @return the form component
	 * @throws DynFormException             sino encuentra el componente
	 */
	public Component getFormComponent(Integer idOperacion, final Integer uuid, final boolean disabled) throws DynFormException;
	
	
	
	/**
	 * Gets the component.
	 *
	 * @param name the name
	 * @return the component
	 * @throws DynFormException the dyn form exception
	 */
	public T getComponent(String name) throws DynFormException;

	/**
	 * Persiste los valores del formulario. Retorna un uuid de transaccion para
	 * posibilitar la recuperacion.
	 *
	 * @param idOperacion the id operacion
	 * @return the integer
	 * @throws DynFormException the dyn form exception
	 */
	public Integer saveValues(Integer idOperacion) throws DynFormException;

	/**
	 * Save values.
	 *
	 * @return the integer
	 * @throws DynFormException the dyn form exception
	 */
	public Integer saveValues() throws DynFormException;

	/**
	 * Obtiene los valores de los componentes tomando el nombre del componente
	 * como clave.
	 *
	 * @return the values
	 */
	public Map<String, Object> getValues();

	/**
	 * Llena el componente con el valor proporcionado.
	 *
	 * @param name            nombre del componente
	 * @param value            valor del componente
	 * @throws DynFormException the dyn form exception
	 */
	void fillCompValue(String name, Object value) throws DynFormException;

	/**
	 * Llena los componentes con los valores de la transaccion.
	 *
	 * @param idOperacion the id operacion
	 * @param uuid            id de transaccion
	 * @throws DynFormException the dyn form exception
	 */
	public void fillCompValues(Integer idOperacion, Integer uuid) throws DynFormException;

	/**
	 * Fill comp values.
	 *
	 * @param uuid the uuid
	 * @throws DynFormException the dyn form exception
	 */
	public void fillCompValues(Integer uuid) throws DynFormException;

	/**
	 * Llena los componentes con los valores del mapa.
	 *
	 * @param presetValues the preset values
	 * @throws DynFormException             sino encuentra algun componente
	 */
	public void fillCompValues(Map<String, Object> presetValues) throws DynFormException;

	/**
	 * Modo solo lectura. Setea todos los componentes del formulario en readonly
	 * Como no existe el readOnly para Checkbox se setea disabled.
	 *
	 * @param bool the bool
	 * @throws DynFormException             sino encuentra algun componente (no deberia pasar)
	 */
	public void readOnlyMode(boolean bool) throws DynFormException;

	/**
	 * Modo solo lectura. Setea todos el componente del formulario en readonly
	 * Como no existe el readOnly para Checkbox se setea disabled.
	 *
	 * @param compName the comp name
	 * @param bool the bool
	 * @throws DynFormException             sino encuentra el componente
	 */
	public void readOnlyMode(String compName, boolean bool) throws DynFormException;

	/**
	 * Devuelve todos los campos de busqueda con etiqueta, name y relevancia de
	 * busqueda. Los que tienen valor 0 en relevanciaBusqueda deberian ser
	 * excluidas para una futura busqueda
	 *
	 * @return the list
	 */
	public List<CampoBusqueda> searchFields();
	
	/**
	 * Search all fields.
	 *
	 * @return the list
	 */
	public List<CampoBusqueda> searchAllFields();
	
	/**
	 * Metodo para actualizar un formulario existente a travez de su codigo.
	 *
	 * @param idOperacion the id operacion
	 * @param uuid the uuid
	 * @throws DynFormException the dyn form exception
	 */
	public void updateFormWeb(Integer idOperacion, Integer uuid) throws DynFormException;

	/**
	 * Update form web.
	 *
	 * @param uuid the uuid
	 * @throws DynFormException the dyn form exception
	 */
	public void updateFormWeb(Integer uuid) throws DynFormException;
	
	/**
	 * Delete form web.
	 *
	 * @param uuid the uuid
	 * @throws DynFormException the dyn form exception
	 */
	public void deleteFormWeb (Integer uuid) throws DynFormException;

	/**
	 * Forzar validacion.
	 *
	 * @param comp the comp
	 */
	public void forzarValidacion(final Component comp);
	
	
	/**
	 * Gets the component map types.
	 *
	 * @return the component map types
	 */
	public Map<String, String> getComponentMapTypes();
	
	/**
	 * Gets the component map (inputCompZkMap)
	 * 
	 * @return
	 */
	public Map<String, ?> getComponentsMap();
}
