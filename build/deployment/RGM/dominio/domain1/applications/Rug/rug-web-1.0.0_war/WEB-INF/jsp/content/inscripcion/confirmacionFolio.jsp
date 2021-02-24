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
	function addResiduo(){
			top.document.forms.AddFolioForm.submit();
			self.parent.tb_remove();
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
											<td class="contenidoNota">La CURP que ingresó corresponde al siguiente Otorgante:</td>
										</tr>
										<tr>
											<td class="contenidoNota"></td>
											<td class="contenidoNota"> Folio Electrónico:____________ Nombre:____________</td>
										</tr>
										
										<tr>
											<td class="contenidoNota"></td>
											<td class="contenidoNota">¿Desea realizar la operación en el Folio Electrónico de este Otorgante?</td>
										</tr>
									</table>
				                 </tr>
								
								<tr>
				                	<table>
										 <tr>
						                	<td  align="center">
						                    	<br><a id="btn_ac" href="javascript:addResiduo();"><img border=0 src="<%= request.getContextPath() %>/resources/imgs/botonGuardar.png"></a>
						                    </td>
											<td  align="center">
						                    	<br><a id="btn_ac" href="javascript:top.tb_remove();"><img border=0 src="<%= request.getContextPath() %>/resources/imgs/botonCancelar.png"></a>
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