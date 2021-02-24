<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
  <center>
    <div class="section"></div>

    <h5 class="indigo-text">Inicie sesi&oacute;n</h5>
    <div class="section"></div>

    <div class="container">
      <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

        <form class="col s12" name="frmLogin" id="frmLogin" action="j_security_check" method="post">
          <div class='row'>
            <div class='col s12'>
            </div>
          </div>

          <div class='row'>
            <div class='input-field col s12'>
              <input class='validate' type='email' name='j_username' id='idUserFake' onkeypress="return submitenter(this,event)" onkeyup="this.value = this.value.toLowerCase()" required="true"/>
              <label for='idUserFake'>Usuario</label>
              <span class="helper-text red-text" id="usuario-error" style="display: none;">El campo usuario es requerido</span>
            </div>
          </div>

          <div class='row'>
            <div class='input-field col s12'>
              <input class='validate' type='password' name='j_password' id='j_password' onkeypress="return submitenter(this,event)" onblur="focoBoton()" required="true" />
              <label for='j_password'>Contrase&ntilde;a</label>
              <span class="helper-text red-text" id="password-error" style="display: none;">El campo Contrase�a es requerido</span>
            </div>
            <label style='float: right;'>
              <a class='aqua-text' href='<%= request.getContextPath() %>/usuario/recover.do'><b>&iquest;Ha olvidado su contrase&ntilde;a?</b></a>
            </label>
          </div>

          <br />
          <center>
            <div class='row'>
              <button type='button' name='Ingresar' id="Ingresar" class='col s12 btn btn-large waves-effect indigo' onClick="MuestraMjs();">Ingresar</button>
            </div>
          </center>
 		  <%if(request.getParameter("error")!=null){%> 
 		    <label  style="font-size: 15px; color: red;" > Usuario o Contrase&ntilde;a incorrecto(s) </label>
 		  <%}%>
        </form>
      </div>
    </div>
    <a href="<%= request.getContextPath() %>/usuario/add.do">Registrarse</a>
  </center>

  <div class="section"></div>
  <div class="section"></div>
</main>

<script language="JavaScript">

document.frmLogin.idUserFake.focus();

function focoBoton(){
	document.frmLogin.Ingresar.focus();
}

function MuestraMjs(){
	$('#usuario-error').hide();
	$('#password-error').hide();
	if ($('#idUserFake').val() && $('#j_password').val()) {
		validaConexionService.existeConexion(existeConexionBD);	 	
	} else {
		if (!$('#idUserFake').val()) {
			$('#usuario-error').show();
		}
		if (!$('#j_password').val()) {
			$('#password-error').show();
		}
	}
} 	

function submitenter(myfield,e){
	var keycode;
	if (window.event) keycode = window.event.keyCode;
	else if (e) keycode = e.which;
	else return true;	
	if (keycode == 13)
	{		
		MuestraMjs();
	return false;
	}
	else
	return true;	
}


</script> 

