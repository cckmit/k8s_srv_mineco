<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script>
function validaRfc() {
	var strCorrecta;
	var rfcStr =document.getElementById('rfc').value;
	strCorrecta = rfcStr;
	if (!noVacio(rfcStr)){
		displayAlert(true,'Error en el RFC','El campo RFC es obligatorio');
	}
	if (rfcStr.length == 13){
		var valid = '^(([A-Z_Ñ_&]|[a-z_ñ_&]|\s){1})(([A-Z_Ñ_&]|[a-z_ñ_&]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	}else{
	  displayAlert(true,'Error en el RFC','El campo RFC es incorrecto');
	}
	var validRfc=new RegExp(valid);
	var matchArray=strCorrecta.match(validRfc);
	if (matchArray==null) {
		displayAlert(true,'Error en el RFC','El campo RFC es incorrecto');
	}
	else
	{
		getObject('frmRfc').submit();
	}
}
</script>

<s:form name="frmRfc" namespace="/firma" action="guardaRfc" id="frmRfc">
<s:label label="Para poder realizar operaciones es necesario contar con su RFC"></s:label>
<s:textfield id="rfc" name="rfc" label="RFC" onkeyup="this.value = this.value.toUpperCase()"></s:textfield>
<input type="hidden" name="idTramite" value='<s:property value="idTramite" />' /> 
<table width="745px"><tr><td align="right">
<input type="button" value="Enviar" onclick="validaRfc();" class="tituloInteriorRojo"/>
</td></tr></table>
</s:form>