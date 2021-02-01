<?xml version="1.0" encoding="UTF-8"?>
<!--
	Workflow: ${projName}
	Autor: ${projAuthor}
	Descripcion:
		${projDescription}
-->
<#setting number_format="0">
<process key="${projName}" name="stateflow-${projName}" version="${projVersion}" xmlns="http://jbpm.org/4.4/jpdl">
	<start g="${start.x?string.computer},${start.y?string.computer},150,51" name="${start.name}">
		<#list start.transitions as trans>
		<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
		</#list>
	</start>
	<#list lstTask as task>
		<state name="${task.name}">
			<#list task.transitions as trans>
				<transition g="${trans.g}" name="${trans.to}" to="${trans.to}"/>
			</#list>
	  	</state>
	</#list>

	<end g="${end.x},${end.y},150,51" name="${end.name}"/>
	<migrate-instances versions="*">
	</migrate-instances>
</process>
