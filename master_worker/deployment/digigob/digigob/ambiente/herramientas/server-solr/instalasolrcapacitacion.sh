#!/bin/bash

clear
echo "Este script de inicializacion de ambiente va a permitir definir todos los parametros configurables por el cliente para la aplicacion seleccionada. 	"
echo "En caso de continuar los ficheros preexistentes se sobreescribiran perdiendo la posibilidad de volver a generar contendores con la misma configuración que los actuales."
echo "¿Ha entendido las implicaciones y esta seguro de querer continuar? "
echo "Si no esta seguro de lo que va a responder contacte con el responsable de sistemas y pulse enter.[S/N]"
read CONTINUAR
if [[ "$CONTINUAR" == "S" || "$CONTINUAR" == "s" ]]; then
ENTORNO=capacitacion
PRODUCT_NAME=egoveris
VERSION=1.0.0
MYSQLIP=10.0.0.4

	echo "Introduzca la Ip del servidor MYSQL [por defecto: 10.0.0.4] "
	read MYSQLIP
	if [[ "$MYSQLIP" == "" ]] || [[ "$MYSQLIP" == "\r" ]]; then
		MYSQLIP=10.0.0.4
	fi

	echo  "*********************************************************** DETENIENDO CONTENEDOR solr $ENTORNO $VERSION *************************************************"
	sleep 2s
	docker stop solr-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA CONTENEDORES solr $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rm -f solr-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA IMAGENES solr $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rmi solr-$ENTORNO:$VERSION
	echo  "*********************************************************** LIMPIA DOCKER *************************************************"
	sleep 2s
	docker volume prune -f
	echo  "*********************************************************** INSTALANDO solr $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./build.sh $PRODUCT_NAME $VERSION
	echo  "*********************************************************** INICIANDO solr $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./run.sh $PRODUCT_NAME $ENTORNO $VERSION $MYSQLIP
	sleep 5s
	echo  "*********************************************************** INSTALACION FINALIZADA solr $ENTORNO $VERSION  *************************************************"
fi