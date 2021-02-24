function consultaDomicilio(idDomicilio, idTipoDomicilio){
	document.getElementById("idDomicilioEdit").value=idDomicilio;
	document.getElementById("idTipoDomicilioEdit").value=idTipoDomicilio;
	document.frmDomicilio.action="edit.do";
	document.frmDomicilio.submit();
	
}


function guardar(idDomicilio){
	var idDomi=document.getElementById(idDomicilio).value;
	
	if(idDomi=="" || idDomi==null){
		guardaDomicilio();
	}else{
		actualizaDomicilio();
	}
	
}

function guardaDomicilio(){
	document.frmDomicilio.action="save.do";
	document.frmDomicilio.submit();
	
}

function actualizaDomicilio(){
	document.frmDomicilio.action="update.do";
	document.frmDomicilio.submit();
}

function borrar(idDomicilio ,idTipoDomicilio ){
	document.getElementById('idDomicilioEdit').value=idDomicilio;
	document.getElementById('idTipoDomicilioEdit').value=idTipoDomicilio;
	document.frmDomicilio.action="remove.do";
	document.frmDomicilio.submit();
}