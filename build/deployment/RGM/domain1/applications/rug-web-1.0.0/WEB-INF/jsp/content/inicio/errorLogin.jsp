<form name="frmLogin" action="j_security_check" method="POST">
	<table width="780" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="5">&nbsp;</td>
			<td width="770">
				<table border="0" width="100%" cellpadding="2" cellspacing="2">
					<tr>
						<td>
							<table width="70%" border="0" class="CajaDenominacionAzul" align="center">
								<tr>
									<td align="center">
										Usuario o contraseña no válidos.													
										<br />
									</td>
								</tr>																			
							</table>
						</td>
					</tr>								
	
					<tr>
						<td>
							<table width="100%" border="0">
								<tr>
									<td width="20%">&nbsp;</td>
									<td width="80%">
										<table width="100%" border="0">											
											<tr>
												<td class="texto_general" align="right" width="20%">
													Usuario:
												</td>
												<td class="texto_general" align="left" width="80"> 
													<input type="text" name="j_username" id="idUserFake" class="texto_general" tabindex="30" />
												</td>
											</tr>
											<tr>
												<td class="texto_general" align="right" width="20%">
													Contraseña: 
												</td>
												<td class="texto_general" align="left" width="80">
													<input type="password" name="j_password" class="texto_general" tabindex="31" />
												</td>
											</tr>
											<tr>
												<td align="center" colspan="2">
													<input type="button" name="Accesar" id="Accesar" value="Accesar" onClick="submit();" tabindex="32" />
												</td>
											</tr>
											<!--tr>
												<td><input type="submit" value="enviar" /></td>
											</tr-->
										</table>
									</td>
									<td width="20%">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>

<script language="JavaScript">
	document.getElementById('idUserFake').focus();	
</script>