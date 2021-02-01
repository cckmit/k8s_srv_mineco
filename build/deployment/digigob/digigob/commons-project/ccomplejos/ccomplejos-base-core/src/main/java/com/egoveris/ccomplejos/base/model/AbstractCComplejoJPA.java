package com.egoveris.ccomplejos.base.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.apache.commons.collections4.CollectionUtils;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractCComplejoJPA {


	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "cc_sequences")
	@TableGenerator(name = "cc_sequences", table = "cc_sequences")
	protected Integer id;

	@Column(name = "ID_OPERACION")
	protected Integer idOperacion;

	@Column(name = "NOMBRE")
	protected String nombre;

	@Column(name = "ORDEN")
	protected Integer orden;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idOperacion
	 */
	public Integer getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion
	 *            the idOperacion to set
	 */
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * 
	 * @throws ReflectiveOperationException
	 */
	public void fillListParent() throws ReflectiveOperationException {

		final Field[] declaredFields = this.getClass().getDeclaredFields();


		for (int i = 0; i < declaredFields.length; i++) {
			final Field field = declaredFields[i];
			// bucar campo tipo lista que no esté vacio
			final Object fieldValue = field.get(this);
			if (List.class.equals(field.getType()) && CollectionUtils.isNotEmpty((List<?>) fieldValue)) {
				
				// se crea una nueva instacia de la clase y se le setea el id
				// final AbstractCComplejoJPA parent =
				// this.getClass().newInstance();
				// parent.setId(id);
				
				// setear parent a cada elemento de la lista
				fillListParent(this, field);
			}

			// bucar campo tipo jpa
			if (null != fieldValue && fieldValue instanceof AbstractCComplejoJPA) {

				// llamar a su propio fillListParent
				((AbstractCComplejoJPA) fieldValue).fillListParent();
			}
		}

	}

	/**
	 * 
	 * @param parent
	 * @param field
	 * @throws ReflectiveOperationException
	 */
	private void fillListParent(final AbstractCComplejoJPA parent, Field field) throws ReflectiveOperationException {

		// buscar el nombre del campo parent dentro de las anotaciones mappedBy
		final OneToMany annotation = field.getAnnotation(OneToMany.class);
		final String parentName = annotation.mappedBy();
		final List<AbstractCComplejoJPA> list = (List) field.get(this);

		// para cada elemento
		for (AbstractCComplejoJPA jpa : list) {

			// llamar a su propio fillListParent
			jpa.fillListParent();

			// setear el campo parent
			jpa.setParent(parentName, parent);
		}
	}

	/**
	 * 
	 * @param parentName
	 * @param parent
	 * @throws ReflectiveOperationException
	 */
	protected void setParent(String parentName, AbstractCComplejoJPA parent) throws ReflectiveOperationException {

		final Field parentField = getClassFieldByName(parentName);
		parentField.set(this, parent);

	}
		
		/**
		 * Busca un campo de la clase por nombre.
		 *
		 * @param name the name
		 * @return the class field by name
		 * @throws NoSuchFieldException the no such field exception
		 */
		private Field getClassFieldByName(final String name) throws ReflectiveOperationException {
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
