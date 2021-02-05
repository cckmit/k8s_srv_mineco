<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
	<table style="width:100%">  
  		<tr>
    		<td class="tituloEncabezado2">AVISO</td>
    	</tr>  
    		<tr><td><s:property escape="false" value="contenidoHTML"/></td></tr>
    		<tr><td align="right"><form><input type="button" value=" &lt;-- ATRAS " onclick="history.back(1);" /></form></td></tr>  
  		
	</table>

	
	  
	
	<style type="text/css">
		.tituloEncabezado{
    font-family: Calibri;
    font-size: 20px;
    font-weight: bold;
    text-align : center;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;

}
.tituloEncabezado2{
    font-family: Calibri;
    font-size: 18px;
    font-weight: bold;
    text-align : center;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;

}
.tituloEncabezado2Left{
    font-family: Calibri;
    font-size: 18px;
    font-weight: bold;
    text-align : left;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;

}
.parrafo{
    font-family: Calibri;
    font-size: 15px;
    text-align : justify;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;
}
.subTilteTD{
    background:silver;
}
.textTD{
}
.title1TD{
background:gray;
}
.title2TD{
background:silver;
}


.tituloSeccion{
    font-family: Calibri;
    font-size: 14px;
    font-weight: bold;
    text-align : center;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;
        background-color:gray;
}
.tituloCampo{
    font-family: Calibri;
    font-size: 13px;
    font-weight: bold;
    text-align : left;
    text-decoration: none;
    padding-top:10px;
    padding-bottom:10px;
    background-color:silver;
     width:50%;
}

.seccionTableClass{
        width:100%;
        border-width: 1px;
    border-spacing: 0px;
    border-style: solid;
    border-color: gray;
    border-collapse: collapse;
}
.camposTableClass{
         width:100%;
}
	</style>
