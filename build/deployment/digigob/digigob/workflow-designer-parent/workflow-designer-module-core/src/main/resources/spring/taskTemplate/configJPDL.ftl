<?xml version="1.0" encoding="UTF-8"?>

<!--
	Workflow: ${projName}
	Autor: ${projAuthor}
	Descripcion:
		${projDescription}
-->
<#setting number_format="0">
<process key="${projName}" name="workflow-${projName}" version="${projVersion}" xmlns="http://jbpm.org/4.4/jpdl">
	<start g="${start.x?string.computer},${start.y?string.computer},150,51" name="${start.name}">
		<#list start.transitions as trans>
		<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
		</#list>
	</start>

	<#list lstTask as task>
		<task form="expediente/tramitacion.zul" g="${task.x?string.computer},${task.y?string.computer},150,51" name="${task.name}">
			<assignment-handler class="com.egoveris.te.core.api.ee.asignacion.AsignarTarea"/>
			<#list task.transitions as trans>
			<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
			</#list>		
			<#if (task.name)?? && (task.name!='Tramitación Libre' && task.name!='Guarda Temporal')>
			<transition g="" name="Subsanacion" to="Subsanacion"/>
			</#if>
			<#if (task.type)?? && (task.type =='TEMP')>
				<variable name="tasktemp" type="string" init-expr="task wait ESB" />
			</#if>
		</task>
	</#list>

	<task form="expediente/tramitacion.zul" g="66,39,92,52" name="Subsanacion">
		<assignment-handler class="com.egoveris.te.core.api.ee.asignacion.AsignarTarea"/>
		<transition name="Subsanacion" to="Subsanacion"/>
		<#list lstTask as task>
		<#if (task.name)?? && (task.name!='Tramitación Libre')>
		<transition  name="${task.name}" to="${task.name}"/>
		</#if>	
		</#list>		
	</task>

	<#list lstFork as fork>
	<fork g="${fork.x?string.computer},${fork.y?string.computer},150,51" name="${fork.name}">
		<#list fork.transitions as trans>
		<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
		</#list>		
	</fork>
	</#list>
	
	<#list lstJoin as join>
	<join g="${join.x?string.computer},${join.y?string.computer},150,51" name="${join.name}">
		<#list join.transitions as trans>
		<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
		</#list>		
	</join>
	</#list>

	<end g="${end.x},${end.y},150,51" name="${end.name}"/>

	<migrate-instances versions="*">
	</migrate-instances>
</process>
