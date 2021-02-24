<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--
	Lic. Abraham Stalin Aguilar Valencia
	Realizado para elegir el acreedor con quien se trabajara. 
 -->
<s:form action="mosubusuarios.do" onsubmit="return checkSubmit();" namespace="inscripcion" name="frmSeleccionaAcreedor" id="frmSeleccionaAcreedor">
<span id="inscripcionSeleccione" class="ayuda">Seleccione al Acreedor del cual usted es Administrador :</span>
<s:select list="listaAcreedores" listKey="idPersona" listValue="nombreCompleto" name="idAcreedorTO" >
</s:select>	
<s:submit value="Aceptar" id="baceptar" /> 
</s:form>
