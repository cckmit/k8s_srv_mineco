<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registro de Garant&iacute;as Mobiliarias </title>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/materialize.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/custom.css?v=1.4" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/footable.standalone.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome.min.css">
<!--     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"> -->
	  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/material-icons.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/rgm.css?v=1.4" />
    <tiles:insertAttribute name=".include-styles" />
	<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript"	src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/Utils/GeneralUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/custom.js"></script>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/moment.min.js"></script>

	
	<tiles:insertAttribute name=".header-scripts"/>
  </head>
<%
Constants.setContextPath(request.getContextPath());
%>
<body>
	<div class="se-pre-con"></div>
	<tiles:insertAttribute name=".header" />	
	<tiles:insertAttribute name=".sideMenu" />
	<tiles:insertAttribute name=".tabs" />
 	<tiles:insertAttribute name="working.region" />
	<tiles:insertAttribute name=".footer" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/modernizr.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/materialize.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/footable.min.js"></script>
	
	<script src="https://cdn.jsdelivr.net/npm/tableexport.jquery.plugin@1.10.21/libs/js-xlsx/xlsx.core.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/tableexport.jquery.plugin@1.10.21/tableExport.min.js"></script>
	<script>
	$(document).ready(function() {
		$(".btn-menu").sideNav();
		$('select').material_select();
		$('.modal').modal({
		    ready: function(modal, trigger) {
		        modal.scrollTop(0);		        
		        $('select').material_select('destroy');
		        $('select').material_select();
		      }			
		});		
		$(".button-collapse").sideNav();
		$('.table').footable({
			"filtering": {
				"placeholder": "Buscar",
				"position": "left",				
				"empty": "No se encontraron resultados"
			}
		});
		$('.datepicker').on('mousedown', function (event) {
	        event.preventDefault();
	    });
		$('.datepicker').pickadate({
	        selectMonths: true,
	        selectYears: 15, 
	        today: 'Hoy',
	        clear: 'Inicializar',
	        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
	        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
	        close: 'Ok',
	        closeOnSelect: false,
	        format: 'dd/mm/yyyy'
	    });
		$('.carousel.carousel-slider').carousel({
			fullWidth: true,
			indicators: true
		});
		$('.moveNextCarousel').click(function(e) {
			e.preventDefault();
			e.stopPropagation();
			$('.carousel').carousel('next');
	   });

	   // move prev carousel
	   $('.movePrevCarousel').click(function(e) {
			e.preventDefault();
			e.stopPropagation();
			$('.carousel').carousel('prev');
	   });
	   $(".dropdown-trigger").dropdown();
	});
	$('.content-modal').on('click', function(event) {
		$target = $(event.target);
		event.preventDefault();
		$('#contentModal .modal-content').empty();
		$.ajax({
			url: event.target.href,
			success: function(result) {
				//$('#contentModal .modal-content').append('<h4>' + $target.attr('data-title') + '</h4>');
				$('#contentModal .modal-content').append(result);
				$('#contentModal').modal('open');
			}
		});
	});
	/*$('.sidenav-trigger').on('click', function(event) {
		$('#slide-out').open();
	});*/
	
	$(window).on('load', function() {
		$(".se-pre-con").fadeOut("slow");
	});
	$(window).on('beforeunload', function () {
		$(".se-pre-con").fadeIn("slow");
	});
	$(document).ajaxStart(function() {
		$(".se-pre-con").fadeIn("slow");
	});
	$(document).ajaxComplete(function() {
		$(".se-pre-con").fadeOut("slow");
	});
	var oldXHR = window.XMLHttpRequest;

	function newXHR() {
	    var realXHR = new oldXHR();
	    realXHR.addEventListener("readystatechange", function() {
	        if(realXHR.readyState==1){
	            $(".se-pre-con").fadeIn("slow");
	        }
	        if(realXHR.readyState==4){
	        	$(".se-pre-con").fadeOut("slow");
	        }
	    }, false);
	    return realXHR;
	}
	window.XMLHttpRequest = newXHR;
	</script>
	<tiles:insertAttribute name=".include-scripts" />
</body>
</html>
