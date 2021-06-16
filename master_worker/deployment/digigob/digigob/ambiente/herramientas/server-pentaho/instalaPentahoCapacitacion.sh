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
APPSIP=10.0.0.6
MYSQLIP=10.0.0.4

	echo  "*********************************************************** INSTALANDO CERTIFICADO NUMERADOR $ENTORNO $VERSION *************************************************"
#	echo -n | openssl s_client -connect $APPIP:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > /home/dockeruser/docker/server-pentaho/conf-files/certificado.crt
	echo  "*********************************************************** DETENIENDO CONTENEDOR PENTAHO $ENTORNO $VERSION *************************************************"
	sleep 2s
	docker stop pentaho-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA CONTENEDORES PENTAHO $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rm pentaho-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA IMAGENES PENTAHO $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rmi pentaho-$PRODUCT_NAME:$VERSION
	echo  "*********************************************************** LIMPIA DOCKER *************************************************"
	sleep 2s
	docker volume prune -f
	echo  "*********************************************************** INSTALANDO PENTAHO $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./build.sh $PRODUCT_NAME $VERSION
	echo  "*********************************************************** INICIANDO PENTAHO $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./run.sh $PRODUCT_NAME $ENTORNO $VERSION $APPSIP $MYSQLIP
	sleep 5s
	echo  "*********************************************************** INSTALACION FINALIZADA PENTAHO $ENTORNO $VERSION  *************************************************"
fi
