
var IdElement;
function getCarrito(elementId){
	IdElement=elementId;
	CarroComprasDwrAction.getCarroCompra(showCarrito);
}

function showCarrito(message){
	fillObject(IdElement,message.message);	
}