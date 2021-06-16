<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
    <style>
        body {
            background-color: #f9fcb4;
            padding: 0;
            margin: 0;
        }

        .descargar-documento-header {
            background-image: url(/deo-web/zkau/web/db_images/banner_2.png);
            width: calc(100% - 20px);
            height: 60px;
            margin: 0;
            background-image: url(banner_2.png);
            border-bottom: 2px solid #158DBE;
            padding-left: 20px;
        }

        .descargar-documento-body {
            width: 100%;
            height: calc(100vh - 80px);
            text-align: center;
            line-height: calc(100vh - 80px);
        }

        .z-button {
            position: relative;
            padding: 3px 12px;
            -webkit-border-radius: 0;
            border-radius: 0;
            ;
            text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
            min-height: 0;
            color: #FFF !important;
            -webkit-transition: all ease 0.15s 0;
            transition: all ease 0.15s 0;
            height: 42px;
            background-color: #8a8a8a!important;
            border: 0;
            border-radius: 3px;

        }

        .z-button:active {
            top: 1px;
            left: 1px;
        }

        .z-button:hover {
            background-color: #9da30e !important;
        }

    </style>
</head>

<body>
    <div class="descargar-documento-header">
        <img src="/deo-web/zkau/web/d9bef093/db_images/logoSinFondo.png">
<!--         <img src="logoSinFondo.png"> -->
    </div>
    <div class="descargar-documento-body">
        <button class="z-button">Descargar documento</button>
    </div>
</body>

</html>