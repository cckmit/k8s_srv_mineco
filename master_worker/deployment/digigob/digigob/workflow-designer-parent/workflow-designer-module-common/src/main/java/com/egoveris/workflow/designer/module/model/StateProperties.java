/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;


public class StateProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6779671648683427981L;
	private String forward=""; 
	private String backward=null;
	private String name="";
	private String typeState = "";
	private String windowsId="genericStateWindow";
	private String stateMacroURI=String.format("/expediente/macros/%s/genericState.zul",name);
	private String acronymPase="";
	private String workflowName=null;
	private String tipoDocumentoFFCC="";
	
	private String forwardDesicion="";	
	private String forwardValidation=null;
	private String initialize=null;
	private String startScript=null;
	
	private boolean acceptReject=false;
	private boolean acceptCierreExpediente=false;
	private boolean acceptTramitacionLibre=true;
	private boolean showPassInfo=false;
	
	private boolean hasGroup = false;
	private String groupName = "";
	private String branchName= "";
	private boolean hasPreviousGroup = false;
	private String previousGroupName = "";
	
	private String stateConnectedToJoinNamed = "";
	private String stateAfterJoinNamed = "";
	
	private String stateConnectedToFork = "";
	
	private String[] pathToActualState;
	private String   pathToActualStateJSON;
	
	private boolean forkOnlyLink = false;
	private String  forkName = "";
	private String scriptFuseTask ="";
	private String scriptStartState ="";
	private String scriptEndState ="";
	
	/**
	 * @return the forward
	 */
	public String getForward() {
		return forward;
	}
	/**
	 * @param forward the forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}
	/**
	 * @return the backward
	 */
	public String getBackward() {
		return backward;
	}
	/**
	 * @param backward the backward to set
	 */
	public void setBackward(String backward) {
		this.backward = backward;
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
	 * @return the windowsId
	 */
	public String getWindowsId() {
		return windowsId;
	}
	/**
	 * @param windowsId the windowsId to set
	 */
	public void setWindowsId(String windowsId) {
		this.windowsId = windowsId;
	}
	/**
	 * @return the stateMacroURI
	 */
	public String getStateMacroURI() {
		return stateMacroURI;
	}
	/**
	 * @param stateMacroURI the stateMacroURI to set
	 */
	public void setStateMacroURI(String stateMacroURI) {
		this.stateMacroURI = stateMacroURI;
	}
	/**
	 * @return the acronymPase
	 */
	public String getAcronymPase() {
		return acronymPase;
	}
	/**
	 * @param acronymPase the acronymPase to set
	 */
	public void setAcronymPase(String acronymPase) {
		this.acronymPase = acronymPase;
	}
	/**
	 * @return the workflowName
	 */
	public String getWorkflowName() {
		return workflowName;
	}
	/**
	 * @param workflowName the workflowName to set
	 */
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	/**
	 * @return the tipoDocumentoFFCC
	 */
	public String getTipoDocumentoFFCC() {
		return tipoDocumentoFFCC;
	}
	/**
	 * @param tipoDocumentoFFCC the tipoDocumentoFFCC to set
	 */
	public void setTipoDocumentoFFCC(String tipoDocumentoFFCC) {
		this.tipoDocumentoFFCC = tipoDocumentoFFCC;
	}
	/**
	 * @return the forwardDesicion
	 */
	public String getForwardDesicion() {
		return forwardDesicion;
	}
	/**
	 * @param forwardDesicion the forwardDesicion to set
	 */
	public void setForwardDesicion(String forwardDesicion) {
		this.forwardDesicion = forwardDesicion;
	}
	/**
	 * @return the forwardValidation
	 */
	public String getForwardValidation() {
		return forwardValidation;
	}
	/**
	 * @param forwardValidation the forwardValidation to set
	 */
	public void setForwardValidation(String forwardValidation) {
		this.forwardValidation = forwardValidation;
	}
	/**
	 * @return the initialize
	 */
	public String getInitialize() {
		return initialize;
	}
	/**
	 * @param initialize the initialize to set
	 */
	public void setInitialize(String initialize) {
		this.initialize = initialize;
	}
	/**
	 * @return the acceptReject
	 */
	public boolean isAcceptReject() {
		return acceptReject;
	}
	/**
	 * @param acceptReject the acceptReject to set
	 */
	public void setAcceptReject(boolean acceptReject) {
		this.acceptReject = acceptReject;
	}
	public boolean isAcceptCierreExpediente() {
		return acceptCierreExpediente;
	}
	public void setAcceptCierreExpediente(boolean acceptCierreExpediente) {
		this.acceptCierreExpediente = acceptCierreExpediente;
	}
	/**
	 * @return the acceptTramitacionLibre
	 */
	public boolean isAcceptTramitacionLibre() {
		return acceptTramitacionLibre;
	}
	/**
	 * @param acceptTramitacionLibre the acceptTramitacionLibre to set
	 */
	public void setAcceptTramitacionLibre(boolean acceptTramitacionLibre) {
		this.acceptTramitacionLibre = acceptTramitacionLibre;
	}
	/**
	 * @return the showPassInfo
	 */
	public boolean isShowPassInfo() {
		return showPassInfo;
	}
	/**
	 * @param showPassInfo the showPassInfo to set
	 */
	public void setShowPassInfo(boolean showPassInfo) {
		this.showPassInfo = showPassInfo;
	}

	public boolean isHasGroup() {
		return hasGroup;
	}
	public void setHasGroup(boolean hasGroup) {
		this.hasGroup = hasGroup;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public boolean isHasPreviousGroup() {
		return hasPreviousGroup;
	}
	public void setHasPreviousGroup(boolean hasPreviousGroup) {
		this.hasPreviousGroup = hasPreviousGroup;
	}
	public String getPreviousGroupName() {
		return previousGroupName;
	}
	public void setPreviousGroupName(String previousGroupName) {
		this.previousGroupName = previousGroupName;
	}
	public String getStateConnectedToJoinNamed() {
		return stateConnectedToJoinNamed;
	}
	public void setStateConnectedToJoinNamed(String stateConnectedToJoinNamed) {
		this.stateConnectedToJoinNamed = stateConnectedToJoinNamed;
	}
	public boolean isForkOnlyLink() {
		return forkOnlyLink;
	}
	public void setForkOnlyLink(boolean forkOnlyLink) {
		this.forkOnlyLink = forkOnlyLink;
	}
	public String getForkName() {
		return forkName;
	}
	public void setForkName(String forkName) {
		this.forkName = forkName;
	}
	public String getStateConnectedToFork() {
		return stateConnectedToFork;
	}
	public void setStateConnectedToFork(String stateConnectedToFork) {
		this.stateConnectedToFork = stateConnectedToFork;
	}
	public String[] getPathToActualState() {
		return pathToActualState;
	}
	public void setPathToActualState(String[] pathToActualState) {
		this.pathToActualState = pathToActualState;
	}
	public String getPathToActualStateJSON() {
		return pathToActualStateJSON;
	}
	public void setPathToActualStateJSON(String pathToActualStateJSON) {
		this.pathToActualStateJSON = pathToActualStateJSON;
	}
	public String getStateAfterJoinNamed() {
		return stateAfterJoinNamed;
	}
	public void setStateAfterJoinNamed(String stateAfterJoinNamed) {
		this.stateAfterJoinNamed = stateAfterJoinNamed;
	}
	
	public String getStartScript() {
		return startScript;
	}
	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}
	
	public String getTypeState() {
		return typeState;
	}
	
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	
	public String getScriptFuseTask() {
		return scriptFuseTask;
	}
	
	public void setScriptFuseTask(String scriptFuseTask) {
		this.scriptFuseTask = scriptFuseTask;
	}
	
	public String getScriptStartState() {
		return scriptStartState;
	}
	
	public void setScriptStartState(String scriptStartState) {
		this.scriptStartState = scriptStartState;
	}
	
	public String getScriptEndState() {
		return scriptEndState;
	}
	
	public void setScriptEndState(String scriptEndState) {
		this.scriptEndState = scriptEndState;
	}
	/**
	 * Metodo para generar a mano el json correspondiente
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "forward",this.forward)).append(",");
		aux.append(String.format(dataStr, "backward",this.backward)).append(",");
		aux.append(String.format(dataStr, "name",this.name)).append(",");
		aux.append(String.format(dataStr, "typeState",this.typeState)).append(",");
		aux.append(String.format(dataStr, "windowsId",this.windowsId)).append(",");
		aux.append(String.format(dataStr, "stateMacroURI",this.stateMacroURI)).append(",");
		aux.append(String.format(dataStr, "acronymPase",this.acronymPase)).append(",");
		aux.append(String.format(dataStr, "workflowName",this.workflowName)).append(",");
		aux.append(String.format(dataStr, "tipoDocumentoFFCC",this.tipoDocumentoFFCC)).append(",");
		aux.append(String.format(dataStr, "forwardDesicion",this.forwardDesicion)).append(",");
		aux.append(String.format(dataStr, "forwardValidation",this.forwardValidation)).append(",");
		aux.append(String.format(dataStr, "initialize",this.initialize)).append(",");
		aux.append(String.format(dataStr, "startScript",this.startScript)).append(",");
		aux.append(String.format(dataStr, "acceptReject",this.acceptReject)).append(",");
		aux.append(String.format(dataStr, "acceptCierreExpediente",this.acceptCierreExpediente)).append(",");
		aux.append(String.format(dataStr, "acceptTramitacionLibre",this.acceptTramitacionLibre)).append(",");
		aux.append(String.format(dataStr, "showPassInfo",this.showPassInfo)).append(",");
		aux.append(String.format(dataStr, "hasGroup",this.hasGroup)).append(",");
		aux.append(String.format(dataStr, "groupName",this.groupName)).append(",");
		aux.append(String.format(dataStr, "branchName",this.branchName)).append(",");
		aux.append(String.format(dataStr, "hasPreviousGroup",this.hasPreviousGroup)).append(",");
		aux.append(String.format(dataStr, "previousGroupName",this.previousGroupName)).append(",");
		aux.append(String.format(dataStr, "forkOnlyLink",this.forkOnlyLink)).append(",");
		aux.append(String.format(dataStr, "stateConnectedToJoinNamed",this.stateConnectedToJoinNamed)).append(",");
		aux.append(String.format(dataStr, "stateAfterJoinNamed",this.stateAfterJoinNamed)).append(",");
		aux.append(String.format(dataStr, "stateConnectedToFork",this.stateConnectedToFork)).append(",");
		aux.append(String.format(dataStr, "pathToActualState",this.pathToActualState)).append(",");
		aux.append(String.format(dataStr, "pathToActualStateJSON",this.pathToActualStateJSON)).append(",");
		aux.append(String.format(dataStr, "forkName",this.forkName)).append(",");
		aux.append(String.format(dataStr, "scriptFuseTask",this.scriptFuseTask)).append(",");
		aux.append(String.format(dataStr, "scriptStartState",this.scriptStartState)).append(",");
		aux.append(String.format(dataStr, "scriptEndState",this.scriptEndState)).append(",");
		aux.append("}");
		return aux.toString();
	}
}