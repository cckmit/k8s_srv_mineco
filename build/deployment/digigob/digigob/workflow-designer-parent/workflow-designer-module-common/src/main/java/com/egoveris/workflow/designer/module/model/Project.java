/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

public class Project implements Serializable, Comparable<Project>{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5616707636646849784L;
	private long id;
	private String name;
	private String author;
	private String description;
	private int version;
	private String jsonModel;
	private boolean incrementVersion;

	private State startState;
	private State endState;
	private List<State> states;
	private List<Link> links;
	
	private List<Fork> forks;
	private List<Join> joins;
	private TypeWorkFlow typeWorkFlow;
	private String nameSubProcess;
	private String scriptFuseGeneric;
	private int versionProcedure;
	
	
			
	/**
	 * Default constructor
	 */
	public Project() {
		this.version=0;
	}
			
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the jsonModel
	 */
	public String getJsonModel() {
		return jsonModel;
	}

	/**
	 * @param jsonModel the jsonModel to set
	 */
	public void setJsonModel(String jsonModel) {
		this.jsonModel = jsonModel;
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the startState
	 */
	public State getStartState() {
		return startState;
	}

	/**
	 * @param startState the startState to set
	 */
	public void setStartState(State startState) {
		this.startState = startState;
	}

	/**
	 * @return the endState
	 */
	public State getEndState() {
		if (endState!=null) {
			endState.getProperties().setName("Cierre"); // por culpa de Expediente
		}
		return endState;
	}

	/**
	 * @param endState the endState to set
	 */
	public void setEndState(State endState) {
		this.endState = endState;
	}

	/**
	 * @return the states
	 */
	public List<State> getStates() {
		if (states==null) {
			states = new ArrayList<State>();
		}
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(List<State> states) {
		this.states = states;
	}

	/**
	 * @return the linnks
	 */
	public List<Link> getLinks() {
		if (links==null) {
			links = new ArrayList<Link>();
		}
		return links;
	}

	/**
	 * @param linnks the linnks to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasStartState() {
		return getStartState()!=null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasEndState() {		
		return getEndState()!=null;
	}

	/**
	 * @return the incrementVersion
	 */
	public boolean isIncrementVersion() {
		return incrementVersion;
	}

	/**
	 * @param incrementVersion the incrementVersion to set
	 */
	public void setIncrementVersion(boolean incrementVersion) {
		this.incrementVersion = incrementVersion;
	}
	
	public List<Fork> getForks() {
		return forks;
	}

	public void setForks(List<Fork> forks) {
		this.forks = forks;
	}

	public List<Join> getJoins() {
		return joins;
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}
	
	
	public TypeWorkFlow getTypeWorkFlow() {
		return typeWorkFlow;
	}


	public void setTypeWorkFlow(TypeWorkFlow typeWorkFlow) {
		this.typeWorkFlow = typeWorkFlow;
	}

	

	public String getNameSubProcess() {
		return nameSubProcess;
	}


	public void setNameSubProcess(String nameSubProcess) {
		this.nameSubProcess = nameSubProcess;
	}

	public String getScriptFuseGeneric() {
		return scriptFuseGeneric;
	}


	public void setScriptFuseGeneric(String scriptFuseGeneric) {
		this.scriptFuseGeneric = scriptFuseGeneric;
	}

	

	public int getVersionProcedure() {
		return versionProcedure;
	}


	public void setVersionProcedure(int versionProcedure) {
		this.versionProcedure = versionProcedure;
	}


	/**
	private String name;
	private String author;
	private String description;
	private int version;
	private String jsonModel;
	private boolean incrementVersion;
	

	private State startState;
	private State endState;
	private List<State> states;
	private List<Link> links;
	 * 
	 * Metodo para generar a mano el json correspondiente
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		String dataObj = "\"%s\":%s";
		int control=0;
		
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "id",this.id)).append(",");
		aux.append(String.format(dataStr, "name",this.name)).append(",");
		aux.append(String.format(dataStr, "author",this.author)).append(",");
		aux.append(String.format(dataStr, "description",this.description)).append(",");
		aux.append(String.format(dataStr, "version",this.version)).append(",");
		aux.append(String.format(dataStr, "jsonModel",this.jsonModel)).append(",");
		aux.append(String.format(dataObj, "startState",this.startState)).append(",");
		aux.append(String.format(dataObj, "endState",this.endState)).append(",");
		aux.append(String.format(dataStr, "scriptFuseGeneric",this.scriptFuseGeneric)).append(",");
		// -- states --
		aux.append("\"states\":[");
			if (this.states!=null) {
				for (control=0; control<this.states.size(); control++) {
					aux.append(this.states.get(control).toJSON());
					if (control<this.states.size()-1) aux.append(","); 
				}
			}
		aux.append("]").append(",");
		// -- links --
		aux.append("\"links\":[");
			if (this.links!=null) {
				for (control=0; control<this.links.size(); control++) {
					aux.append(this.links.get(control).toJSON());
					if (control<this.links.size()-1) aux.append(","); 
				}
			}
		aux.append("]").append(",");
		// -- forks --
		aux.append("\"forks\":[");
		if (this.forks!=null) {
			for (control=0; control<this.forks.size(); control++) {
				aux.append(this.forks.get(control).toJSON());
				if (control<this.forks.size()-1) aux.append(","); 
			}
		}
		aux.append("]").append(",");
		// -- joins --
		aux.append("\"joins\":[");
		if (this.joins!=null) {
			for (control=0; control<this.joins.size(); control++) {
				aux.append(this.joins.get(control).toJSON());
				if (control<this.joins.size()-1) aux.append(","); 
			}
		}
		aux.append("]").append(",");

		aux.append(String.format(dataStr, "incrementVersion",this.incrementVersion)).append(",");
		aux.append(String.format(dataStr, "jsonModel",this.jsonModel));		
		aux.append("}");
		
		return aux.toString();
	}
	@Override
	public int compareTo(Project p2) {
		return this.getName().compareTo(p2.getName());
	}
	
	
}

