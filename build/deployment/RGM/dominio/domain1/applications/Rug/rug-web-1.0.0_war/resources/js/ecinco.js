
function resetField(campo, valor){
	if(campo.value==""){
		campo.value=valor;
	}
}

function resetIfClean(campo, valor){
	if(campo.value==valor){
		campo.value="";
	}
}

function tbShow(path){
	 tb_show('Información del pago', path+'/pago/ecinco/vencimiento.do?keepThis=true&TB_iframe=true&height=300&width=500&modal=true', '');
}