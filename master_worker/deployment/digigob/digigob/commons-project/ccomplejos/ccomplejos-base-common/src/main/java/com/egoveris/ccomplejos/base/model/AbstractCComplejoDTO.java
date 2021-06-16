package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


// TODO: Auto-generated Javadoc
/**
 * All complex components created in FFDD that must be sent as concret java
 * classes must extends this class. All attributes of child classes must have
 * package access for the constructor sets their fields with reflection
 *
 * @author everis
 *
 */
public abstract class AbstractCComplejoDTO implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5161089980637289077L;

	/** The id. */
	private Integer id;
	
	/** The id operacion. */
	private Integer idOperacion;

	/** The nombre. */
	private String nombre;

	/** The orden. */
	private Integer orden;
	 
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the id operacion.
	 *
	 * @return the idOperacion
	 */
	public Integer getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Sets the id operacion.
	 *
	 * @param idOperacion            the idOperacion to set
	 */
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the orden.
	 *
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * Sets the orden.
	 *
	 * @param orden            the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Setea el valor indicado en el campo al que corresponde el nombre.
	 *
	 * @param name the name
	 * @param value the value
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	public void setFieldValue(String name, Object value) throws IllegalAccessException, NoSuchFieldException {
		final Field field = this.getClassFieldByName(name); 
		if (null != field) { 
			field.set(this, value);
		}
	}

	/**
	 * Obtiene el valor de un campo de la clase por nombre.
	 *
	 * @param name the name
	 * @return the field value
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	public Object getFieldValue(String name)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException {
		Object object = null;
		final Field field = getClassFieldByName(name);
		if (null != field) {
			object = field.get(this);
		}
		return object;
	}

	/**
	 * Instancia DTOs en caso de no encontralos.
	 *
	 * @param name the name
	 * @param idOperacion the id operacion
	 * @param nombre the nombre
	 * @param posicionRepeat the posicion repeat
	 * @return the inner DTO
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	public AbstractCComplejoDTO getInnerDTO(String name, Integer idOperacion, String nombre, Integer posicionRepeat)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException {
		Object object = null;
		final Field field = getClassFieldByName(name);
		
		if (null != field) {
			object = field.get(this);
			if (null == object) {
				
				if (field.getType().equals(List.class)) {
					List arr = new ArrayList<>();
					field.set(this, arr);
				} else {
					object = field.getType().newInstance();
					((AbstractCComplejoDTO) object).setIdOperacion(idOperacion);
					((AbstractCComplejoDTO) object).setNombre(nombre);
					field.set(this, object);
				}

			}
			if(null!=posicionRepeat){
				List arr =(List) field.get(this);
				ParameterizedType listType = (ParameterizedType) field.getGenericType();
				Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
				if(posicionRepeat >= arr.size()){
					for(int i=arr.size(); i<=posicionRepeat; i++){
						createListElement(idOperacion, nombre, i, arr, listClass);
					}
				}
				object=arr.get(posicionRepeat);
			}
			
		}
		return (AbstractCComplejoDTO) object;
	}

	private void createListElement(Integer idOperacion, String nombre, Integer posicionRepeat, List arr,
			Class<?> listClass) throws InstantiationException, IllegalAccessException {
		Object instance = listClass.newInstance();
		((AbstractCComplejoDTO) instance).setIdOperacion(idOperacion);
		((AbstractCComplejoDTO) instance).setNombre(nombre);
		((AbstractCComplejoDTO) instance).setOrden(posicionRepeat);
		arr.add(instance);
	}
	
	/**
	 * Busca un campo de la clase por nombre.
	 *
	 * @param name the name
	 * @return the class field by name
	 * @throws NoSuchFieldException the no such field exception
	 */
	private Field getClassFieldByName(final String name) throws NoSuchFieldException {
		Field[] fields = this.getClass().getDeclaredFields();
		Field field = null;
		int i = 0;
		while (i < fields.length && null == field) {
			if (fields[i].getName().equals(name) && !Modifier.isPrivate(fields[i].getModifiers())) {
				field = fields[i];
			}
			i++;
		}
		return field;
	}
}