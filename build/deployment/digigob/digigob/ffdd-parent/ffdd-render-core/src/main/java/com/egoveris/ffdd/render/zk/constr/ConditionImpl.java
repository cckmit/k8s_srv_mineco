package com.egoveris.ffdd.render.zk.constr;

import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.CondicionDTO.Condicion;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ComboboxDecorator;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;

import org.zkoss.zk.ui.Component;

public class ConditionImpl implements Condition {

	private CondicionDTO condicionDTO;
	private InputComponent inputNombreComponente;
	private InputComponent inputNombreCompComparacion;
	private String sufijoRep;

	public ConditionImpl(CondicionDTO condicionDTO, Component grid, String sufijoRep) {
		this.condicionDTO = condicionDTO;
		this.sufijoRep = sufijoRep;
		setInputNombreComponente(findComponent(grid, condicionDTO.getNombreComponente()));
		if (condicionDTO.getNombreCompComparacion() != null) {
			setInputNombreCompComparacion(findComponent(grid, condicionDTO.getNombreCompComparacion()));
		}
	}

	/**
	 * Busca los valores y los compara
	 */
	@Override
	public boolean validar(InputComponent comp, Object value) {

		Comparable<?> valorA = obtenerValorComp(comp, getInputNombreComponente(), value);

		Comparable<?> valorB = null;
		if (getInputNombreCompComparacion() != null) {
			valorB = obtenerValorComp(comp, getInputNombreCompComparacion(), value);
		} else if (condicionDTO.getValorComparacion() != null && valorA != null) {
			valorB = ComponentUtils.castToObjType(condicionDTO.getValorComparacion(), valorA);
		}

		return comparar(condicionDTO.getCondicion(), valorA, valorB);
	}

	private InputComponent findComponent(Component grid, String nombreComp) {
		String nombre = sufijoRep != null ? nombreComp + sufijoRep : nombreComp;
		InputComponent inputComp = (InputComponent) ComponentUtils.obtenerComponentePorName(grid, nombre);
		if (inputComp == null) {
			throw new DynFormValorComponente("No se encuentra el componente: " + nombreComp);
		}
		return inputComp;
	}

	private Comparable<?> obtenerValorComp(Component comp, InputComponent inputComp, Object value) {
		if (inputComp == comp) {
			return (Comparable<?>) value;
		} else {
			return (Comparable<?>) inputComp.getRawValue();
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private boolean comparar(Condicion condicion, Comparable valorA, Comparable valorB) {
		switch (condicion) {
		case DISTINTO:
			return isDistinto(valorA, valorB);
		case IGUAL:
			return isIgual(valorA, valorB);
		case MAYOR:
			return isMayor(valorA, valorB);
		case MENOR:
			return isMenor(valorA, valorB);
		case NO_VACIO:
			return !isVacio(valorA);
		case VACIO:
			return isVacio(valorA);
		default:
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private boolean isIgual(Comparable valorA, Comparable valorB) {
		return conValores(valorA, valorB) && valorA.equals(valorB);
	}

	@SuppressWarnings({ "rawtypes" })
	private boolean isDistinto(Comparable valorA, Comparable valorB) {
		return conValores(valorA, valorB) && !valorA.equals(valorB);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isMenor(Comparable valorA, Comparable valorB) {
		return conValores(valorA, valorB) && valorA.compareTo(valorB) < 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isMayor(Comparable valorA, Comparable valorB) {
		return conValores(valorA, valorB) && valorA.compareTo(valorB) > 0;
	}

	private boolean isVacio(Comparable<?> valorA) {
		return valorA == null || valorA.equals("") || valorA.equals(ComboboxDecorator.VACIO);
	}

	private boolean algunoVacio(Comparable<?> valorA, Comparable<?> valorB) {
		return isVacio(valorA) || isVacio(valorB);
	}

	private boolean conValores(Comparable<?> valorA, Comparable<?> valorB) {
		return !algunoVacio(valorA, valorB);
	}

	public InputComponent getInputNombreComponente() {
		return inputNombreComponente;
	}

	private void setInputNombreComponente(InputComponent inputNombreComponente) {
		this.inputNombreComponente = inputNombreComponente;
	}

	public InputComponent getInputNombreCompComparacion() {
		return inputNombreCompComparacion;
	}

	private void setInputNombreCompComparacion(InputComponent inputNombreCompComparacion) {
		this.inputNombreCompComparacion = inputNombreCompComparacion;
	}
}