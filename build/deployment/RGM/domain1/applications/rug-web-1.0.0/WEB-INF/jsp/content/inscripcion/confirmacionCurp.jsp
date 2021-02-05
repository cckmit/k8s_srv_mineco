<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/template_css.css" type="text/css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/default.css" type="text/css" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/campos.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/thickbox.js"></script>   
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/ThickBox.css" type="text/css" media="screen" />
</head>
	<script language="JavaScript">
	function addCurp(valorPaso){
			alert("valor: "+ valorPaso);
	//		top.document.forms.AddCurpForm.valorPaso.value=true;
			return true;
			self.parent.tb_remove();
	}

	function exitCurp(valorPaso){
		alert("valor: "+ valorPaso);
		top.tb_remove();
	//	top.document.forms.AddCurpForm.valorPaso.value=false;
		return false;
}

	</script>
<body>
	<table cellpadding ="0" cellspacing="0"  width="100%">

			<tr valign="top">
				        <td align="center">
				        	<table width="90%" cellpadding ="0" cellspacing="0">
				            	<tr>
				                	<table class="nota">
										<tr>
											<td class="imgNota">
												<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
												<s:text name="common.nota" />
											</td>
											<td class="contenidoNota"><b></>ADVERTENCIA:</b>En la base de datos del RUG no existe un Otorgante con los atributos que ha ingresado. Asegúrese de la veracidad y exactitud de dicha información, ya que el sistema matriculará al Otorgante con base en la información ingresada por usted.La CURP que ingresó corresponde al siguiente Otorgante:</td>
										</tr>
									</table>
				                 </tr>
								
								<tr>
				                	<table>
										 <tr>
						                	<td  align="center">
						                    	<br><a id="btn_ac" href="javascript:exitCurp('false');">Regresar</a>
						                    </td>
						                	<td  align="center">
						                    	<br><a id="btn_ac" href="javascript:addCurp('true');">Continuar</a>
						                    </td>
											
		
						                 </tr>
					                 </table>
								 </tr>
								 
							</table>
				        </td>
					</tr>
	</table>
</body>
</html>