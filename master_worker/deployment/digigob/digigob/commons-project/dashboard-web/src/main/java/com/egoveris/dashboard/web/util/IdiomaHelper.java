package com.egoveris.dashboard.web.util;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listitem;

public class IdiomaHelper {
	
	private IdiomaHelper() {
		// Constructor
	}
	
	/**
	 * Arma una lista con los idiomas disponibles de la aplicacion
	 * 
	 * @return Lista con los idiomas disponibles
	 */
	public static List<Listitem> loadIdiomas() {
		List<Listitem> listaIdiomas = new ArrayList<>();
		
		listaIdiomas.add(new Listitem(Labels.getLabel("dbweb.language.es"), DashboardConstants.IDIOMA_ES));
		listaIdiomas.add(new Listitem(Labels.getLabel("dbweb.language.en"), DashboardConstants.IDIOMA_EN));
		
		return listaIdiomas;
	}
	
	/**
	 * Devuelve el idioma seleccionado a partir de la lista
	 * segun el idioma de sesion
	 * 
	 * @param idiomas Lista de idiomas disponibles
	 * @return Idioma segun sesion
	 */
	public static Listitem getSelectedLang(List<Listitem> idiomas) {
		Listitem itemIdioma = null;
		
		for (Listitem item : idiomas) {
			if (item.getValue() != null && item.getValue().toString().equals(Locales.getCurrent().toString())) {
				itemIdioma = item;
				break;
			}
		}
		
		return itemIdioma;
	}
	
}
