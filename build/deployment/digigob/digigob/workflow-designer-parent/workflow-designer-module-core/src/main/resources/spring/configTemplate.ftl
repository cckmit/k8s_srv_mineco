<?xml version="1.0" encoding="UTF-8"?>

<#assign defaultZul="./expediente/macros/${projNameLower}/genericState.zul">
<config>
	<SubsanacionState config-class="com.egoveris.plugins.${projNameLower}.states.SubsanacionState">
		<name>Subsanacion</name>
		<windowsId>genericStateWindow</windowsId>
		<stateMacroURI>./expediente/macros/${projNameLower}/genericState.zul</stateMacroURI>
		<acronymPase></acronymPase>
		<workflowName>${projName}</workflowName>
		<tipoDocumentoFFCC></tipoDocumentoFFCC>
		<showPassInfo>false</showPassInfo>
		<forwardValidation>
		<![CDATA[
			return true;
		]]>			
		</forwardValidation>
		<forwardDesicion>
		<![CDATA[
			state.selectUsuario(windowPase, BPM['usuario_PrevioSubsanacion']);
			return BPM['estado_PrevioSubsanacion'];
		]]>			
		</forwardDesicion>
	</SubsanacionState>

	<#list lstStates as state>
	<${stringUtil.camelName(state.properties.name)}State config-class="com.egoveris.plugins.${projNameLower}.states.${stringUtil.camelName(state.properties.name)}State">
		<forward>${state.properties.forward}</forward>
		<#if (state.properties.backward)?? && (state.properties.backward?length>0)>
		<backward>${state.properties.backward}</backward>
		</#if>		
		<name>${state.properties.name}</name>
		<windowsId><#if (state.properties.windowsId)?? && (state.properties.windowsId?length>0)>${(state.properties.windowsId)}<#else>genericStateWindow</#if></windowsId>
		<stateMacroURI><#if (state.properties.stateMacroURI)?? && (state.properties.stateMacroURI?length>0)>${(state.properties.stateMacroURI)}<#else>${defaultZul}</#if></stateMacroURI>
		<acronymPase>${state.properties.acronymPase}</acronymPase>
		<workflowName>${state.properties.workflowName}</workflowName>
		<tipoDocumentoFFCC>${state.properties.tipoDocumentoFFCC}</tipoDocumentoFFCC>
		<acceptCierreExpediente>${state.properties.acceptCierreExpediente?string}</acceptCierreExpediente>
		<acceptReject>${state.properties.acceptReject?string}</acceptReject>
		<acceptTramitacionLibre>${state.properties.acceptTramitacionLibre?string}</acceptTramitacionLibre>
		<showPassInfo>${state.properties.showPassInfo?string}</showPassInfo>
	
		<hasGroup>${state.properties.hasGroup?string}</hasGroup>
		<groupName><#if (state.properties.groupName)?? && (state.properties.groupName?length>0)>${(state.properties.groupName)}</#if></groupName>
		<hasPreviousGroup>${state.properties.hasPreviousGroup?string}</hasPreviousGroup>
		<previousGroupName><#if (state.properties.previousGroupName)?? && (state.properties.previousGroupName?length>0)>${(state.properties.previousGroupName)}</#if></previousGroupName>		
		<branchName><#if (state.properties.branchName)?? && (state.properties.branchName?length>0)>${(state.properties.branchName)}</#if></branchName>
		<forkName><#if (state.properties.forkName)?? && (state.properties.forkName?length>0)>${(state.properties.forkName)}</#if></forkName>
		<forkOnlyLink>${state.properties.forkOnlyLink?string}</forkOnlyLink>
		<stateConnectedToFork><#if (state.properties.stateConnectedToFork)?? && (state.properties.stateConnectedToFork?length>0)>${(state.properties.stateConnectedToFork)}</#if></stateConnectedToFork>
		<stateConnectedToJoinNamed><#if (state.properties.stateConnectedToJoinNamed)?? && (state.properties.stateConnectedToJoinNamed?length>0)>${(state.properties.stateConnectedToJoinNamed)}</#if></stateConnectedToJoinNamed>
		<stateAfterJoinNamed><#if (state.properties.stateAfterJoinNamed)?? && (state.properties.stateAfterJoinNamed?length>0)>${(state.properties.stateAfterJoinNamed)}</#if></stateAfterJoinNamed>
		<pathToActualStateJSON><#if (state.properties.pathToActualStateJSON)?? && (state.properties.pathToActualStateJSON?length>0)>${(state.properties.pathToActualStateJSON)}</#if></pathToActualStateJSON>
		
		<startScript>
			<![CDATA[
				${(state.properties.startScript)!""}
			]]>
		</startScript>
		<initialize>
		<![CDATA[
			${(state.properties.initialize)!""}
		]]>
		</initialize>
		<forwardValidation>
		<![CDATA[
			${(state.properties.forwardValidation)!"return true;"}
		]]>
		</forwardValidation>
		<forwardDesicion>
		<![CDATA[
			<#if (state.properties.forwardDesicion)?? && (state.properties.forwardDesicion?length>0)>${(state.properties.forwardDesicion)}<#else>return "NO-STATE";</#if>
		]]>
		</forwardDesicion>
		<scriptFuseTask>
		<![CDATA[
			<#if (state.properties.scriptFuseTask)?? && (state.properties.scriptFuseTask?length>0)>${(state.properties.scriptFuseTask)}<#else>return "NO-STATE";</#if>
		]]>
		</scriptFuseTask>
		<scriptFuseGeneric>
		<![CDATA[
			<#if (projScriptFuseGeneric)?? && (projScriptFuseGeneric?length>0)>${(projScriptFuseGeneric)}<#else>return "NO-STATE";</#if>
		]]>
		</scriptFuseGeneric>
		<scriptStartState>
		<![CDATA[
			${(state.properties.scriptStartState)!""}
		]]>
		</scriptStartState>
		<scriptEndState>
		<![CDATA[
			${(state.properties.scriptEndState)!""}
		]]>
		</scriptEndState>
		
		
		<hint>
			${(state.hint)!""}
		</hint>
	</${stringUtil.camelName(state.properties.name)}State>
	</#list>
</config>