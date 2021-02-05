<%@page import="mx.gob.se.rug.inscripcion.dao.AltaParteDAO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<main>

<%

PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(12));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();

	if ((priv.get(new Integer(35))!=null )) {
%>
<table>
	<tr>
		<td style="cursor: pointer;"><a
			href="<%=request.getContextPath()%>/masiva/inicia.do">Carga
				Masiva </a>|</td>
		 <td style="cursor: pointer;"><a	href="<%=request.getContextPath()%>/masiva/autoridad/inicia.do">Autoridad</a>
		</td>  
	</tr>
</table>
<%
	}
%>
<s:if test="mensaje!=null">
	<table>
		<tr>
			<td>Sucedio el siguiente error:</td>
			<td> <s:property value="mensaje.mensaje"/> </td>
		</tr>
	</table>
</s:if>

<div class="container-fluid">
	<div class="row">
		<div class="col s12">
			<div class="card">
				<div class="col s1"></div>					
				<div class="col s10">					
					<s:form action="cargaMasiva.do" method="post" enctype="multipart/form-data" theme="simple" id="idCargaMasivaForm">						
					   	<div class="row">
					   		<div id="dwrId"></div>
					   	</div>
					
				 	</s:form>
				 </div>
				 <div class="col s1"></div>		
			</div>
		</div>
	</div>
</div>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
 <script>
//setActiveTab('seisMenu');
//$("#seisMenu").attr("class","linkSelected");
function cargaMasiva(){	
	var idUsuario = <s:property value="idUsuario" />;
	var idAcreedor = '';	
	displayLoader(true);
	MasivaDwrAction.getCargaMasiva(idUsuario, idUsuario, showMasiva);	
}

function cambiaAction(){
	idListaTramite = document.getElementById("idListaTramite").value; 
	if (idListaTramite == 32){
		document.getElementById("idCargaMasivaForm").action='<%= request.getContextPath() %>/masiva/autoridad/carga.do';
	}else{
		document.getElementById("idCargaMasivaForm").action='cargaMasiva.do';
	}  
}

$(document).ready()
{
    $('select').material_select();    
}

cargaMasiva();
</script>
</main>