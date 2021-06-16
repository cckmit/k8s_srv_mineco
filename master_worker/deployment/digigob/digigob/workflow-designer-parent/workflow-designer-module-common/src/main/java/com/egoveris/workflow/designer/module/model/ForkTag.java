/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edifranc
 *
 */
public class ForkTag {
	private int x;
	private int y;
	private String name;
	private List<Transition> transitions;
	

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
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
	 * @return the transitions
	 */
	public List<Transition> getTransitions() {
		if (transitions==null) {
			transitions = new ArrayList<Transition>();
		}
		return transitions;
	}

	/**
	 * @param transitions the transitions to set
	 */
	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append("    <fork g=\""+this.x+","+this.y+",150,51\" name=\""+this.name+"\">").append("\n");
		
		for (Transition trans : getTransitions()) {
			sb.append(trans.toString());
		}
		
		sb.append("    </fork>");
		
		return sb.toString();
	}
}
