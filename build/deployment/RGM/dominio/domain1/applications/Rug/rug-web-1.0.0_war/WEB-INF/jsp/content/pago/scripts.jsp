<script language="javascript">
function GetInformation () {
	alert ("Informacion Retornada");
	alert ("Certificado: " + document.firma.Base64Certificate);
	alert ("Error: " + document.firma.State);
	alert ("Descripcion: " + document.firma.Descript);
	alert ("Firma: " + document.firma.Base64Sign);
	alert ("Estado Sello: " + document.firma.StateTs);
	alert ("Sello tiempo: " + document.firma.TimeStamping);
}

function monitorFirma(){
   
  /*if(document.firma.Base64Certificate !='' && document.firma.Base64Sign != ''&& document.firma.Base64Certificate !=null && document.firma.Base64Sign != null){
	  getObject('btnFirma').disabled=true;
	  getObject('idBase64UserSign').value=document.firma.Base64Sign;
	  getObject('idBase64UserCertificate').value= document.firma.Base64Certificate;
	  getObject('b64Form').submit();
  }else{
	  alert('No ha Firmado');
  }*/
  
	getObject('b64Form').submit();

}

function daemonFirma(){
	alert('daemon up');
  while(true)
  {
    setTimeout("monitorFirma();",5000);
     
  }
}

</script>