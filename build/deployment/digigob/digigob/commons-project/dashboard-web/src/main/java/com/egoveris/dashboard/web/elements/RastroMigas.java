package com.egoveris.dashboard.web.elements;

public class RastroMigas {
	
	/**
	 * Si el elemento MenuElement dado posee un icono se
	 * muestra como icono del rastro de migas. Si no lo posee
	 * y tiene padres, lo coge del primero que tenga
	 * 
	 * @param menuElement
	 * @return
	 */
	public String getIconClass(MenuElement menuElement) {
		String icon = "";
		
		if (menuElement != null && menuElement.getIconSclass() != null && !menuElement.getIconSclass().isEmpty()) {
			icon = menuElement.getIconSclass();
		}
		else {
			if (menuElement != null && menuElement.getParent() != null) {
				icon = getIconClass(menuElement.getParent());
			}
		}
		
		return icon;
	}
	
	/**
	 * Si el elemento MenuElement dado posee un parent, se
	 * muestra el label de este. Con esto se arma una etiqueta
	 * de subseccion en el rastro de migas
	 * 
	 * @param menuElement
	 * @return
	 */
	public String getSubsection(MenuElement menuElement) {
		String subsection = "";
		
		if (menuElement != null && menuElement.getParent() != null) {
			subsection = menuElement.getParent().getLabel();
		}
		
		return subsection;
	}
	
}
