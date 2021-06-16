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
VERSION=4.0.0
	echo  "*********************************************************** DETENIENDO CONTENEDOR WEB DAV $ENTORNO $VERSION *************************************************"
	sleep 2s
	docker stop apache-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA CONTENEDORES WEB DAV $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rm apache-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA IMAGENES WEB DAV $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rmi apache-$PRODUCT_NAME:$VERSION
	echo  "*********************************************************** LIMPIA DOCKER *************************************************"
	sleep 2s
	docker volume prune -f
	echo  "*********************************************************** INSTALANDO WEB DAV $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./build.sh $PRODUCT_NAME $VERSION
	echo  "*********************************************************** INICIANDO WEB DAV $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./run.sh $PRODUCT_NAME $ENTORNO $VERSION
	sleep 5s
	echo  "*********************************************************** INSTALACION FINALIZADA WEB DAV $ENTORNO $VERSION  *************************************************"
fi
