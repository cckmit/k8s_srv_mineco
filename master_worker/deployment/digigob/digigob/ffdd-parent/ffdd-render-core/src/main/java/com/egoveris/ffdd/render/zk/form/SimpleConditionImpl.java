package com.egoveris.ffdd.render.zk.form;

import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.CondicionDTO.Condicion;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ComboboxDecorator;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;

import org.zkoss.zk.ui.Component;

public class SimpleConditionImpl implements Condition {

	private CondicionDTO condicionDTO;
	private Component inputNombreComponente;
	private Component inputNombreCompComparacion;

	public SimpleConditionImpl(CondicionDTO condicionDTO, Component vlayout, String sufijoRep) {
		this.condicionDTO = condicionDTO;
		setInputNombreComponente(ComponentUtils.findComponent(vlayout, condicionDTO.getNombreComponente(), sufijoRep));
		if (condicionDTO.getNombreCompComparacion() != null) {
			setInputNombreCompComparacion(ComponentUtils.findComponent(vlayout, condicionDTO.getNombreCompComparacion(), sufijoRep));
		}
	}

	
	@Override
	public boolean validar() {
		return validar(null, null);
	}
	
	/**
	 * Busca los valores y los compara
	 */
	@Override
	public boolean validar(InputComponent comp, Object value) {

		// si algun componente esta no visible retorna false
		if (!getInputNombreComponente().isVisible() || (getInputNombreCompComparacion() != null && !getInputNombreCompComparacion().isVisible())) {
				return false;
		}
		if(getInputNombreComponente() instanceof InputComponent) {
			
			Comparable<?> valorA = obtenerValorComp(comp, (InputComponent) getInputNombreComponente(), value);

			Comparable<?> valorB = null;
			if (getInputNombreCompComparacion() instanceof InputComponent) {
				valorB = obtenerValorComp(comp, (InputComponent) getInputNombreCompComparacion(), value);
			} else if (condicionDTO.getValorComparacion() != null && valorA != null) {
				valorB = ComponentUtils.castToObjType(condicionDTO.getValorComparacion(), valorA);
			}

			return comparar(condicionDTO.getCondicion(), valorA, valorB);
		}
		return false;


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

	public Component getInputNombreComponente() {
		return inputNombreComponente;
	}

	private void setInputNombreComponente(Component inputNombreComponente) {
		this.inputNombreComponente = inputNombreComponente;
	}

	public Component getInputNombreCompComparacion() {
		return inputNombreCompComparacion;
	}

	private void setInputNombreCompComparacion(Component inputNombreCompComparacion) {
		this.inputNombreCompComparacion = inputNombreCompComparacion;
	}
}