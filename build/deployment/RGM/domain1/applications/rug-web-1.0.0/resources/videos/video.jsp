<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String videoUrl=request.getParameter("videoUrl");
String height=request.getParameter("heightV");
String width=request.getParameter("widthV");
%>

<html> 

<head> </head> 

<body> 
	<div> </div>
	<table align="center">
		<tr align="center"> 
			<td align="center">	
	   			<object align="middle" type="application/x-shockwave-flash" data="player_flv_mini.swf" width="502" height="465">
					<param name="movie" value="player_flv_mini.swf" />
					<param name="FlashVars" value="flv=/RugResources/resources/videos/<%=videoUrl%>&width=501&height=464" />
				</object>
			</td>
		</tr>		
	</table>		
</body> 


</html>