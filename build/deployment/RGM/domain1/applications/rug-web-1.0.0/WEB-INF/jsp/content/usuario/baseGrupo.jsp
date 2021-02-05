<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>Secretaría de Economía - Registro &Uacute;nico de Garant&iacute;as Mobiliarias</title>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<%
    Constants constants= new Constants();
	boolean caheOn = Boolean.valueOf(constants.getParamValue(Constants.CACHE_STATUS)).booleanValue(); // consulta a la base de datos
	if (caheOn){
		%>
			<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
			<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
		<%
		
	}
%>
  
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.js"></script>

  <!-- CSS  -->
  <link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/template_css.css" type="text/css" />
  <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/alt_template_css.js"></script>

  <link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/default.css" type="text/css" />
  <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/alt_template_css.js"></script>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/rug.css" media="screen" type="text/css" />
  <link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/tuempresa.css" type="text/css" />
  <!--Calendar -->
  <link type="text/css" href="<%=request.getContextPath()%>/resources/css/demos.css" rel="stylesheet" />
  <!-- /Calendar-->

  <!-- alt -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ThickBox.css" type="text/css" media="screen" />

  <!--/ CSS  -->
 
<!-- JS -->
<script>
function getInternetExplorerVersion(){
	var rv = -1; // Return value assumes failure.
	if (navigator.appName == 'Microsoft Internet Explorer'){ 
		var ua = navigator.userAgent;
 		var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
 		if (re.exec(ua) != null)
 	 	 	rv = parseFloat( RegExp.$1 );
		}
	return rv;
}
var ver = getInternetExplorerVersion();

if ( ver > -1 ){
	if ( ver == 7 ) 
		document.write(" <link rel='stylesheet' href='<%=request.getContextPath()%>/resources/css/loaderIE7.css' type='text/css' />")
	else
		document.write(" <link rel='stylesheet' href='<%=request.getContextPath()%>/resources/css/loader.css' type='text/css' />");
}else{
	document.write(" <link rel='stylesheet' href='<%=request.getContextPath()%>/resources/css/loader.css' type='text/css' />");
}
</script>
<!--Calendar-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.ui.datepicker.js"></script>
<!--/Calendar-->


	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/Utils/GeneralUtil.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/thickbox.js"></script>
	


<!--/ JS -->
</head>
<body>

<div id="divCargador" class="loaderCotGral" ></div>
<div id="divImgLoader" class="loaderImage" align="center">
	<img src="<%=request.getContextPath()%>/resources/imgs/loader/loadingAnimation.gif" style="position: relative;top:10px;" alt="loader" />
	<br/><br/>
	<h3 style="margin-top: 10px">Espere un momento...</h3>
</div>
<div id="layoutContainer">
<!-- div id="headerContainer"><tiles:insertAttribute name=".header" />
</div-->

<div id="tabsContainer"><tiles:insertAttribute name=".tabs" />
</div>
<div id="workingContainer">

<table class="workArea">
<tr>
	<td class="sideMenu" >
		<div id="sideMenuContainer"><tiles:insertAttribute name=".sideMenu" /></div>
	</td>
	<td>
		<table>
		<tr>
			<td>
				<div id="topNavContainer"><tiles:insertAttribute name=".topNav" /></div>
			</td>
		</tr>
		<tr>
			<td>
				<tiles:insertAttribute name="working.region" />
			</td>
		</tr>
		<tr>
			<td>
				<div id="topNavContainer"><tiles:insertAttribute name=".bottomNav" /></div>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

</div>
<div id="spacer" ><br/></div>
<div id="footerContainer"><tiles:insertAttribute name=".footer" />
</div>
</div>
<div id="smothboxDiv" class="smothboxCSS" >
</div>
<div id="messageAlertDiv" class="messageAlert" align="center">
<div class="clear"></div>
	<h3 align="left">Confirmaci&oacute;n</h3>
	<div class="clear"></div><br />
	<center >Confirmo que la informaci&oacute;n proporcionada es v&aacute;lida.</center>
	<div class="clear"></div>
			    <input id="siButtonMessage" type="button" style="width: 60px;" onclick="sendForm();" value="Si" />  
			    <input type="button" style="width: 60px;" onclick="displayMessage(false);confirmaChek();" value="No" />  
</div>
<div id="mensajealert">
</div>

</body>
</html>
