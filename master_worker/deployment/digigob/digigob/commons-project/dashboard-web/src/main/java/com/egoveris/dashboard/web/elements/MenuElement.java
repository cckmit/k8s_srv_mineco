package com.egoveris.dashboard.web.elements;

import java.util.ArrayList;
import java.util.List;

public class MenuElement {

	private String id;
	private String label;
	private String iconSclass;
	private String targetUrl;
	private String targetZul;
	private String badgeText;
	private boolean selected;
	private MenuElement parent;
	private List<MenuElement> children;

	/**
	 * Constructor con los atributos basicos
	 * 
	 * @param label Texto (etiqueta)
	 * @param iconSclass Icono
	 * @param targetUrl URL destino
	 */
	public MenuElement(String label, String iconSclass, String targetUrl) {
		this.label = label;
		this.iconSclass = iconSclass;
		this.targetUrl = targetUrl;
		this.targetZul = targetUrl;
	}
	
	/**
	 * Constructor con los atributos basicos
	 * 
	 * @param label Texto (etiqueta)
	 * @param iconSclass Icono
	 * @param targetUrl URL destino
	 * @param targetZul ZUL destino
	 */
	public MenuElement(String label, String iconSclass, String targetUrl, String targetZul) {
		this.label = label;
		this.iconSclass = iconSclass;
		this.targetUrl = targetUrl;
		this.targetZul = targetZul;
	}
	
	public void addSubMenuElement(MenuElement menuElement) {
		menuElement.setParent(this);
		getChildren().add(menuElement);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIconSclass() {
		return iconSclass;
	}

	public void setIconSclass(String iconSclass) {
		this.iconSclass = iconSclass;
	}
	
	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetZul() {
		return targetZul;
	}

	public void setTargetZul(String targetZul) {
		this.targetZul = targetZul;
	}

	public String getBadgeText() {
		return badgeText;
	}

	public void setBadgeText(String badgeText) {
		this.badgeText = badgeText;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public MenuElement getParent() {
		return parent;
	}

	public void setParent(MenuElement parent) {
		this.parent = parent;
	}
	
	public List<MenuElement> getChildren() {
		if (children == null) {
			children = new ArrayList<>();
		}
		
		return children;
	}

	public void setChildren(List<MenuElement> children) {
		this.children = children;
	}

}
