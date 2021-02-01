package com.egoveris.ffdd.render.zk.util;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.util.FFDDConstants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CComplejosHelper {

	private static final String REP_NUM_END = "]";
	private static final String REP_NUM_BEGIN = "[";
	private static final Logger logger = LoggerFactory.getLogger(CComplejosHelper.class);

	private CComplejosHelper() {
	}

	/**
	 * Devuelve una lista de DTOs con los valores informados en la lista.
	 * 
	 * @param nombre
	 * @param valores
	 * @param clase
	 * @return
	 */
	public static final List<AbstractCComplejoDTO> crearComponentesComplejos(final String nombre,
			final List<ValorFormCompDTO> valores, final Class<?> clase, final Integer idOperacion) {
		final Map<String, AbstractCComplejoDTO> dtos = new HashMap<>();

		agregarValores(dtos, valores, nombre, clase, idOperacion);

		return new ArrayList<>(dtos.values());
	}

	/**
	 * Asocia cada valor a un campo de un dto dentro del mapa.
	 * 
	 * @param dtos
	 * @param valores
	 * @param nombre
	 * @param clase
	 */
	private static void agregarValores(final Map<String, AbstractCComplejoDTO> dtos,
			final List<ValorFormCompDTO> valores, final String nombre, final Class<?> clase,
			final Integer idOperacion) {

		for (final ValorFormCompDTO value : valores) {
			try {
				final String[] nameElements = value.getInputName().split(FFDDConstants.NAME_SEPARATOR);
				String repeticion = null;
				if (nameElements.length == 2) {
					repeticion = "R0";
				}
				String formFieldName = nameElements[1];
				if (nameElements.length == 3) {
					repeticion = nameElements[1];
					formFieldName = nameElements[2];
				}

				agregarRepeticion(dtos, repeticion, clase, nombre, idOperacion);
				String[] nameInnerElement = nameElements[0].split("\\.");
				AbstractCComplejoDTO abstractCComplejoDTO = findInnerDTO(dtos, idOperacion, repeticion,
						nameInnerElement);

				//agrega los valores
				if (null != value && null != formFieldName) {
					abstractCComplejoDTO.setFieldValue(formFieldName, value.getValor());
				}

			} catch (final ReflectiveOperationException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @param dtos
	 * @param idOperacion
	 * @param repeticion
	 * @param nameInnerElement
	 * @return
	 * @throws ReflectiveOperationException
	 * 
	 */
	private static AbstractCComplejoDTO findInnerDTO(final Map<String, AbstractCComplejoDTO> dtos,
			final Integer idOperacion, String repeticion, String[] nameInnerElement)
			throws ReflectiveOperationException {

		AbstractCComplejoDTO abstractCComplejoDTO = dtos.get(repeticion);
		
		if (nameInnerElement.length > 1) {
			for (int i = 1; i < nameInnerElement.length; i++) {
				String fieldName = nameInnerElement[i];
				Integer posicionRepet = null;
				if (fieldName.contains(REP_NUM_BEGIN)) {
					String posicionRepetStr = fieldName.substring(fieldName.indexOf(REP_NUM_BEGIN) + 1,
							fieldName.length()-1);
					if (StringUtils.isNotBlank(posicionRepetStr) && StringUtils.isNumeric(posicionRepetStr)) {
						posicionRepet = Integer.valueOf(posicionRepetStr);
					}
					fieldName= fieldName.substring(0, fieldName.indexOf(REP_NUM_BEGIN));
					
				}
				abstractCComplejoDTO = abstractCComplejoDTO.getInnerDTO(fieldName, idOperacion,
						nameInnerElement[i], posicionRepet);
			}
		}
		return abstractCComplejoDTO;
	}

	/**
	 * En el caso que no exista la repeticion informada en el mapa, crea una
	 * nueva instancia del dto y la agrega al mapa con el sufijo de repetición
	 * como clave.
	 * 
	 * @param dtos
	 * @param repeticion
	 * @param clase
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static void agregarRepeticion(final Map<String, AbstractCComplejoDTO> dtos, final String repeticion,
			final Class<?> clase, final String nombre, final Integer idOperacion)
			throws ReflectiveOperationException {
		if (!dtos.containsKey(repeticion)) {
			final AbstractCComplejoDTO dto = (AbstractCComplejoDTO) clase.newInstance();
			dto.setNombre(nombre);
			dto.setIdOperacion(idOperacion);
			dto.setOrden(dtos.size());
			dtos.put(repeticion, dto);
		}
	}

	/**
	 * Busca el prefijo asociado a un componente complejo en el nombre del
	 * valor.
	 * 
	 * @param name
	 * @param ccNames
	 * @return
	 */
	public static String cComplejoName(final String name, final Set<String> ccNames) {
		String esCComplejo=null;

		if(null!=name){
			final Iterator<String> iterator = ccNames.iterator();
			while(esCComplejo==null && iterator.hasNext()){
				final StringBuilder prefijo=new StringBuilder();
				final StringBuilder prefijoInner=new StringBuilder();
				final String cComplejoName = iterator.next();
				prefijo.append(cComplejoName).append(FFDDConstants.NAME_SEPARATOR);
				prefijoInner.append(cComplejoName).append(FFDDConstants.NAME_INNER_SEPARATOR);
				if (name.startsWith(prefijo.toString())||name.startsWith(prefijoInner.toString())) {
					esCComplejo=cComplejoName;
				}
			}
		}
		
		return esCComplejo;
	}

	/**
	 * 
	 * @param nombre
	 * @param listaDto
	 * @return
	 */
	public static List<ValorFormCompDTO> obtenerValoresComponente(final String nombre,
			final List<AbstractCComplejoDTO> listaDto) {
		final List<ValorFormCompDTO> valores = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(listaDto)) {
			for (int i = 0; i < listaDto.size(); i++) {
				valores.addAll(obtenerValores(nombre, listaDto.get(i), i));
			}
		}

		return valores;
	}

	/**
	 * Busca el valor informado para cada campo del dto y lo agrega a la lista
	 * de valores asociados a un inputName.
	 * 
	 * @param nombre
	 * @param dto
	 * @param repeticion
	 * @param tieneRep
	 * @return
	 */
	private static List<ValorFormCompDTO> obtenerValores(final String nombre, final AbstractCComplejoDTO dto,
			final Integer repeticion) {
		final List<ValorFormCompDTO> valores = new ArrayList<>();

		final Class<?> clase = dto.getClass();
		final Field[] declaredFields = clase.getDeclaredFields();

		for (Field field : declaredFields) {
			try {
				final String fieldName = field.getName();
				Object value = dto.getFieldValue(fieldName);
				if (null != value && !(value instanceof List && CollectionUtils.isEmpty((List<?>) value))) {
					addValorFormComp(nombre, repeticion, valores, fieldName, value);
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return valores;
	}

	/**
	 * @param nombre
	 * @param repeticion
	 * @param tieneRep
	 * @param valores
	 * @param field
	 * @param value
	 */
	private static void addValorFormComp(final String nombre, final Integer repeticion,
			final List<ValorFormCompDTO> valores, String field, Object value) {

		if (value instanceof AbstractCComplejoDTO) {
			StringBuilder inputName = new StringBuilder().append(nombre).append(".").append(field);
			valores.addAll(obtenerValores(inputName.toString(), (AbstractCComplejoDTO) value, repeticion));
		} else if (value instanceof List<?> && CollectionUtils.isNotEmpty((List<?>) value)) {
			List<?> list = (List<?>) value;
			for (int i = 0; i < list.size(); i++) {
				StringBuilder inputName = new StringBuilder().append(nombre).append(".").append(field).append(REP_NUM_BEGIN)
						.append(i).append(REP_NUM_END);
				valores.addAll(obtenerValores(inputName.toString(), (AbstractCComplejoDTO) list.get(i), repeticion));
			}
		} else {
			StringBuilder inputName = new StringBuilder().append(nombre);
			if (repeticion > 0) {
				inputName.append(FFDDConstants.NAME_SEPARATOR).append("R").append(repeticion);
			}
			inputName.append(FFDDConstants.NAME_SEPARATOR).append(field);

			final ValorFormCompDTO valor = new ValorFormCompDTO();
			valor.setInputName(inputName.toString());
			valor.setValor(value);
			valores.add(valor);
		}

	}
	

}
	
	

