package com.egoveris.ffdd.model.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DynamicComponentFieldDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4817862823953475705L;

	private Long id;
	private String name;
	private String label;
	private String parent;
	private Boolean hidden;
	private Boolean required;
	private String defaultValue;
	private String style;
	private String width;
	private String height;
	private String tooltip;
	private Boolean disabled;
	private String dependence;

	private String type;
	private Boolean disableRequired;
	private Boolean disableHidden;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the hidden
	 */
	public Boolean getHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * @param required
	 *            the required to set
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @return the disabled
	 */
	public Boolean getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the disableRequired
	 */
	public Boolean getDisableRequired() {
		return disableRequired;
	}

	/**
	 * @param disableRequired
	 *            the disableRequired to set
	 */
	public void setDisableRequired(Boolean disableRequired) {
		this.disableRequired = disableRequired;
	}

	/**
	 * @return the disableHidden
	 */
	public Boolean getDisableHidden() {
		return disableHidden;
	}

	/**
	 * @param disableHidden
	 *            the disableHidden to set
	 */
	public void setDisableHidden(Boolean disableHidden) {
		this.disableHidden = disableHidden;
	}

	/**
	 * Shows DTO inner values
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getDependence() {
		return dependence;
	}

	public void setDependence(String dependence) {
		this.dependence = dependence;
	}
}
