var aFinMes;
var dias;
// Si no se setea la variable dias se queda con el default : dias
var label_dias="dias";

function setDias(label_dias){
	this.label_dias=label_dias;
}

function esBisiesto(anio){
	return ( (((anio % 4)==0) && (anio %100 !=0)) || ((anio % 4)==0) && (anio %100 ==0)&& (anio %400 ==0) );
}

function llenaDias(anio, mes, dia){
	aFinMes= new Array(0,31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	var _anio= document.getElementById(anio);
	var _mes= document.getElementById(mes);
	
	if((_anio.selectedIndex==0)||(_mes.selectedIndex==0)){
		xD(dia, 0);
	}else{
		if((esBisiesto(_anio.options[_anio.selectedIndex].value))&&(_mes.selectedIndex==2)){
			aFinMes[_mes.selectedIndex]=29;
		}
		xD(dia, _mes.selectedIndex);
	}
	
	function xD(dia, v){
		dias=null;
		dias= new Array(aFinMes[v]+1);
		dias[0]=label_dias;
		for(_i=1; _i<aFinMes[v]+1; _i++){
			if(_i<10){
				dias[_i]="0"+_i;
			}
			else{
				dias[_i]=""+_i;
			}
		}
		var campoDias=document.getElementById(dia);
		var oldDia=campoDias.options[campoDias.selectedIndex].value;
		dwr.util.removeAllOptions(dia);
		dwr.util.addOptions(dia,dias);
		
		BuscaSelectOpcion(campoDias,oldDia);
	}
}
