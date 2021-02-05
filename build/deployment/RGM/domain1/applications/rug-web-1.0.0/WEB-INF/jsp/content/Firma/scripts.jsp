<script type="text/javascript">


   function abrirCentrado(Url,NombreVentana,ancho,alto) {
		y=(screen.height-alto)/2;
		x=(screen.width-ancho)/2;
		ventanaHija = window.open(Url,"Poliza","left="+x+",top="+y+",width="+ancho+",height="+alto);
	}	   
    	
	function sendForm(){
		displayMessageAlert(false);	
		showBoleta();	
	}
	
	function showBoleta() {
		var URL="<%=request.getContextPath()%>/home/boleta.do";
		window.open(URL, "_blank");
		//abrirCentrado(URL,"Boleta","500","500"); 
	}
	
</script>