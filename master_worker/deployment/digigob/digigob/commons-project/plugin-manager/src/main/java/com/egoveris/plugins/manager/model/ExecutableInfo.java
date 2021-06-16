/**
 * 
 */
package com.egoveris.plugins.manager.model;

/**
 * @author difarias
 *
 */
public class ExecutableInfo {
	public String name;
	public String icon;
	public String tooltip;
	public boolean visible;

	public ExecutableInfo() {
	}
	
	public ExecutableInfo(String name, String icon, String tooltip, boolean isVisible) {
		this.name=name;
		this.icon=icon;		
		this.tooltip=tooltip;
		this.visible=isVisible;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}
	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String aux="Executable Info: (name: %s, icon: %s, tooltip: %s, visible: %b)"; 
		return String.format(aux, getName(),getIcon(),getTooltip(), isVisible());
	}
}
