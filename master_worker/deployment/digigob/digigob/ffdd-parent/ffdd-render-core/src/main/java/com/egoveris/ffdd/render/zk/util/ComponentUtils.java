package com.egoveris.ffdd.render.zk.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Vlayout;

import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.zk.comp.ext.BandboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.CheckboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.ConstrInputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorExt;

public class ComponentUtils {

	static Logger LOGGER = LoggerFactory.getLogger(ComponentUtils.class);

	public static final String SEPARATOR = "SEPARATOR";
	public static final String SEPARATOR_INTERNO = "Separator - Interno";
	public static final String SEPARATOR_REPETIDOR = "Separator - Repetidor";

	/**
	 * Devuelve todos los campos. Los que tienen valor 0 en relevanciaBusqueda
	 * deberian ser excluidas para una futura busqueda
	 *
	 * @param formDTO
	 * @return
	 */
	public static List<CampoBusqueda> getCamposBusqueda(final FormularioDTO formDTO) {
		Boolean repetidorCreado = false;
		final List<CampoBusqueda> listCampo = new ArrayList<CampoBusqueda>();
		for (final FormularioComponenteDTO formCompDTO : formDTO.getFormularioComponentes()) {
			if (!formCompDTO.getComponente().getTipoComponente().equals(SEPARATOR) && !formCompDTO.isMultilinea() 
					&& !formCompDTO.isMultilineaEditable()) {
				addField(listCampo, formCompDTO);
			} else {
				if (formCompDTO.getComponente().getNombre().equals(SEPARATOR_REPETIDOR) && !repetidorCreado
						|| formCompDTO.isMultilinea() || formCompDTO.isMultilineaEditable()) {
					repetidorCreado = formCompDTO.getComponente().getNombre().equals(SEPARATOR_REPETIDOR)
							&& !repetidorCreado ? true : repetidorCreado;
					listCampo.add(nuevoCampoBusqueda(formCompDTO));
				}
			}
		}
		return listCampo;
	}

	public static List<CampoBusqueda> getTodosCamposBusqueda(final FormularioDTO formDTO) {

		final List<CampoBusqueda> listCampo = new ArrayList<CampoBusqueda>();
		for (final FormularioComponenteDTO formCompDTO : formDTO.getFormularioComponentes()) {
			if (!formCompDTO.getComponente().getTipoComponente().equals(SEPARATOR) 
				&& !formCompDTO.isMultilinea() && !formCompDTO.isMultilineaEditable()) {
				addField(listCampo, formCompDTO);
			} else {
				if (formCompDTO.isMultilinea() || formCompDTO.isMultilineaEditable()) {
					listCampo.add(nuevoCampoBusqueda(formCompDTO));
				}
			}
		}
		return listCampo;
	}

	public static SeparatorExt generarSeparatorExt(final FormularioComponenteDTO fcompDB) {
		return new SeparatorExt(fcompDB.getId(), fcompDB.getNombre(), fcompDB.getEtiqueta(),
				fcompDB.getComponente().getNombre().equals(SEPARATOR_REPETIDOR),
				fcompDB.getComponente().getNombre().equals(SEPARATOR_INTERNO));
	}
	
	public static SeparatorExt generarSeparatorRepetidor(final FormularioComponenteDTO fcompDB) {
	  return new SeparatorExt(fcompDB.getId(), fcompDB.getNombre(), fcompDB.getEtiqueta(), true, false);
 }

	public static Component generarHeaderInterno(final FormularioComponenteDTO fcompDB) {
		final Grid gridInterno = new Grid();
		final SeparatorExt result = generarSeparatorExt(fcompDB);
		result.setParent(gridInterno);
		final Columns cols = new Columns();
		final Column col = new Column();
		col.setParent(cols);
		cols.setVisible(false);
		cols.setParent(gridInterno);

		final Cell celda = new Cell();
		final Row rowFinal = new Row();
		celda.setColspan(2);
		gridInterno.setParent(celda);
		celda.setParent(rowFinal);
		return rowFinal;
	}

	/**
	 * crea una grilla vacía
	 * @return
	 */
	public static Rows nuevaGrilla() {
		final Grid grid = new Grid();
		Columns columns = new Columns();
		new Column().setParent(columns);
		new Column().setParent(columns);
		columns.setParent(grid);
		final Rows rows = new Rows();
		rows.setParent(grid);
		return rows;
	}

	private static void addField(final List<CampoBusqueda> listCampo, final FormularioComponenteDTO formCompDTO) {

		if (StringUtils.isEmpty(formCompDTO.getComponente().getNombreXml())) {
			addGenericField(listCampo, formCompDTO);
		} else {
			addComplexField(listCampo, formCompDTO);
		}
	}

	private static boolean addComplexField(final List<CampoBusqueda> listCampo,
			final FormularioComponenteDTO formCompDTO) {
		final IComplexComponentService serv = (IComplexComponentService) SpringUtil.getBean("complexComponentService");
		final ComplexComponent comp = serv.getComponent(formCompDTO.getComponente().getNombreXml());
		if (comp != null) {
			try {
				final Class clazz = Class.forName(comp.getComposer());
				for (final Field field : clazz.getDeclaredFields()) {
					if (Component.class.isAssignableFrom(field.getType())  &&
							!field.getName().contains("Div")) {
						listCampo.add(
								campoBusquedaUsigSade(formCompDTO, " " + field.getName(), "_".concat(field.getName())));
					}
				}
				return true;
			} catch (final ClassNotFoundException e) {
				LOGGER.info("No se ha encontrado la clase para el composer" + comp.getComposer(), e);
			}
		}
		return false;
	}

	private static CampoBusqueda nuevoCampoBusqueda(final FormularioComponenteDTO formCompDTO) {
		final CampoBusqueda campo = new CampoBusqueda();
		campo.setEtiqueta(formCompDTO.getEtiqueta());
		String multilinea = formCompDTO.isMultilinea() ? " MULTILINEA"
				: null;
		multilinea = formCompDTO.isMultilineaEditable() ? " MULTILINEA EDITABLE"
				: multilinea;
		campo.setNombre(multilinea != null ? formCompDTO.getNombre() + multilinea
				: formCompDTO.getComponente().getNombre());
		campo.setRelevanciaBusqueda(formCompDTO.getRelevanciaBusqueda());
		campo.setOrden(formCompDTO.getOrden());
		campo.setMascara(formCompDTO.getComponente().getMascara());
		campo.setMensaje(formCompDTO.getComponente().getMensaje());
		return campo;
	}

	private static void addNroSadeField(final List<CampoBusqueda> listCampo,
			final FormularioComponenteDTO formCompDTO) {

		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (ActuaciÃ³n)", "_act"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (AÃ±o)", "_anio"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (NÃºmero)", "_nro"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (ReparticiÃ³n)", "_rep"));
	}

	private static void addUsigField(final List<CampoBusqueda> listCampo, final FormularioComponenteDTO formCompDTO) {

		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (Calle y altura)", ""));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (Barrio)", "_barrio"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (Comuna)", "_comuna"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (SecciÃ³n)", "_seccion"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (Manzana)", "_manzana"));
		listCampo.add(campoBusquedaUsigSade(formCompDTO, " (Parcela)", "_parcela"));
	}

	private static CampoBusqueda campoBusquedaUsigSade(final FormularioComponenteDTO formCompDTO,
			final String sufEtiqueta, final String sufNombre) {
		final CampoBusqueda cbusq = new CampoBusqueda();
		cbusq.setEtiqueta(formCompDTO.getEtiqueta() + sufEtiqueta);
		cbusq.setNombre(formCompDTO.getNombre() + sufNombre);
		cbusq.setRelevanciaBusqueda(formCompDTO.getRelevanciaBusqueda());
		cbusq.setOrden(formCompDTO.getOrden());
		cbusq.setMascara(formCompDTO.getComponente().getMascara());
		cbusq.setMensaje(formCompDTO.getComponente().getMensaje());
		return cbusq;
	}

	private static void addGenericField(final List<CampoBusqueda> listCampo,
			final FormularioComponenteDTO formCompDTO) {
		final CampoBusqueda campo = new CampoBusqueda();
		campo.setEtiqueta(formCompDTO.getEtiqueta());
		campo.setNombre(formCompDTO.getNombre());
		campo.setRelevanciaBusqueda(formCompDTO.getRelevanciaBusqueda());
		campo.setOrden(formCompDTO.getOrden());
		campo.setMascara(formCompDTO.getComponente().getMascara());
		campo.setMensaje(formCompDTO.getComponente().getMensaje());
		listCampo.add(campo);
	}

	/**
	 * Retorna el grid que contiene al componente o nulo si no lo encuentra
	 * Recursivo
	 *
	 * @param comp
	 *            componente del formulario controlado
	 * @return grid
	 */
	public static Vlayout obtenerVlayoutRoot(final Component comp) {
		if (comp instanceof Vlayout) {
			return (Vlayout) comp;
		} else {
			if (comp.getParent() != null) {
				return obtenerVlayoutRoot(comp.getParent());
			} else {
				return null;
			}
		}
	}

	public static void addEventListener(final Component inputComp, final EventListener evnt) {
		if (inputComp instanceof BandboxExt && ((BandboxExt) inputComp).isReadonly()) {
			((BandboxExt) inputComp).getBusquedaListbox().addEventListener(Events.ON_SELECT, evnt);
		} else {
			inputComp.addEventListener(ComponentUtils.eventForComp(inputComp), evnt);
		}
	}
	
	public static void dispararValidacion(final Component inputComp) {
		if (inputComp instanceof BandboxExt && ((BandboxExt) inputComp).isReadonly()) {
			
			Component listBox = ((BandboxExt) inputComp).getBusquedaListbox();
						
			Events.sendEvent(new Event(Events.ON_SELECT, listBox));
		} else {
			Events.sendEvent(new Event(ComponentUtils.eventForComp(inputComp), inputComp));			
		}
	}


	public static String eventForComp(final Component input) {
		if (input instanceof CheckboxExt) {
			return Events.ON_CHECK;
		} else {
			return Events.ON_CHANGE;
		}
	}

	/**
	 * Obtiene el componente hijo que contenga el name suministrado
	 *
	 * Recursivo
	 *
	 * @param compon
	 * @param name
	 * @return
	 */
	public static Component obtenerComponentePorName(final Component compon, final String name) {
		if (compon != null) {
			if (compon instanceof InputComponent && ((InputComponent) compon).getName().equals(name)) {
				return compon;
			}
			if(compon instanceof Row && ((Row) compon).getValue()!=null &&  ((Row) compon).getValue().equals(name)) {				
				return compon;
			}
			
			else {
				if (compon.getChildren() != null && !compon.getChildren().isEmpty()) {
					for (final Object compObj : compon.getChildren()) {
						final Component foundComp = obtenerComponentePorName((Component) compObj, name);
						if (foundComp != null) {
							return foundComp;
						}
					}
				}
			}
		}
		return null;
	}

	public static Component findComponent(final Component rootComp, final String nombreComp,
			final String sufijoRep) {
		final String nombre = sufijoRep != null ? nombreComp + sufijoRep : nombreComp;
		final Component inputComp =  ComponentUtils.obtenerComponentePorName(rootComp, nombre);
		if (inputComp == null) {
			throw new DynFormValorComponente("No se encuentra el componente: " + nombre);
		}
		return inputComp;
	}	

	public static Comparable<?> castToObjType(final String valor, final Object objType) {
		if (objType instanceof String) {
			return valor;
		} else if (objType instanceof Integer) {
			return Integer.valueOf(valor);
		} else if (objType instanceof Long) {
			return Long.valueOf(valor);
		} else if (objType instanceof Date) {
			final DateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return dateTimeFormatter.parse(valor);
			} catch (final ParseException e) {
				throw new DynFormValorComponente("Error al convertir objeto String a Date");
			}
		} else if (objType instanceof Double) {
			return Double.valueOf(valor);
		} else if (objType instanceof Boolean) {
			return Boolean.valueOf(valor);
		} else {
			throw new DynFormValorComponente("El tipo de objeto " + objType.toString() + " no es compatible");
		}
	}

	public static void buildConstraint(final ComponentZkExt input) {
		buildConstraint(input, null);
	}

	public static void buildConstraint(final ComponentZkExt input, final String suffix) {
		if (input instanceof ConstrInputComponent) {
			final ConstrInputComponent c = (ConstrInputComponent) input;
			if (c.getMultiConstrData() != null && c.getConstraint() == null) {
				c.setConstraint(c.getMultiConstrData().buildConstraint(c, suffix));
			}
		}
	}
	
	public static void buildConstraint(final ConstrInputComponent input, final String suffix) {
			if (input.getMultiConstrData() != null && input.getConstraint() == null) {
				input.setConstraint(input.getMultiConstrData().buildConstraint(input, suffix));
			}
	}

	public static Rows nuevaGrilla(final int columns) {
		final Grid grid = new Grid();
		// Si se quiere usar AUXHEADER hay que definir las cols
		final Columns cols = new Columns();
		Column col = new Column();
		col.setWidth("15%");
		col.setParent(cols);
		col = new Column();
		col.setWidth("85%");
		col.setParent(cols);
		final Rows rows = new Rows();
		cols.setVisible(false);
		cols.setParent(grid);
		rows.setParent(grid);
		return rows;
	}

}