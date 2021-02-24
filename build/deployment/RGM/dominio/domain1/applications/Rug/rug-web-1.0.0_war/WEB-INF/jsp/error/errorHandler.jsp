<%@ taglib prefix="s" uri="/struts-tags"%>
<br>
<br>
<table class="tablaComun" align="center">
	<thead>
		<tr>
			<td>
			<h2><s:text name="http.error.title" /></h2>
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td align="center"><br>
			<br>
			<table class="nota">
				<tbody>
					<tr>
						<td class="imgNota"><img
							src="<%=request.getContextPath()%>/resources/imgs/error.png" /></td>
						<td>&nbsp;&nbsp;</td>
						<td class="contenidoNota"><s:property value="messageKey" /></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center"><br>
			<br>
			<a href="javascript: window.history.back()"><s:text
				name="http.error.back" /></a></td>
		</tr>
	</tbody>
</table>
<div style="color: white;">
<s:property value="messageError"/>
</div>


