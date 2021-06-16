#!/bin/bash


clear
echo "Este script de inicializacion de ambiente va a permitir definir todos los parametros configurables por el cliente para la aplicacion seleccionada. 	"
echo "En caso de continuar los ficheros preexistentes se sobreescribiran perdiendo la posibilidad de volver a generar contendores con la misma configuración que los actuales."
echo "¿Ha entendido las implicaciones y esta seguro de querer continuar? "
echo "Si no esta seguro de lo que va a responder contacte con el responsable de sistemas y pulse enter.[S/N]"
read CONTINUAR
if [[ "$CONTINUAR" == "S" || "$CONTINUAR" == "s" ]]; then
#Cambiar de acuerdo al entorno que corresponda, no olvidar copiar
ENTORNO=capacitacion
PRODUCT_NAME=egoveris
VERSION=1.0.0
CASIP=10.0.0.6
LDAPIP=10.0.0.5

#	echo "Introduzca la Ip del servidor CAS [por defecto: 10.0.0.5] "
#	read CASIP
#	if [[ "$CASIP" == "" ]] || [[ "$CASIP" == "\r" ]]; then
#		CASIP=10.0.0.5
#	fi
#	echo "Introduzca la Ip del servidor de LDAP [por defecto: 10.0.0.5] "
#	read LDAPIP
#	if [[ "$LDAPIP" == "" ]] || [[ "$LDAPIP" == "\r" ]]; then
#		LDAPIP=10.0.0.5
#	fi

	echo  "*********************************************************** DETENIENDO CONTENEDOR cas $ENTORNO $VERSION *************************************************"
	sleep 2s
	docker stop cas-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA CONTENEDORES cas $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rm -f cas-$PRODUCT_NAME-$ENTORNO
	echo  "*********************************************************** ELIMINA IMAGENES cas $ENTORNO $VERSION  *************************************************"
	sleep 2s
	docker rmi cas-$PRODUCT_NAME:$VERSION
	echo  "*********************************************************** LIMPIA DOCKER *************************************************"
	sleep 2s
	docker volume prune -f
	echo  "*********************************************************** INSTALANDO cas $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./build.sh $PRODUCT_NAME $VERSION
	echo  "*********************************************************** INICIANDO cas $ENTORNO $VERSION  *************************************************"
	sleep 2s
	./run.sh $PRODUCT_NAME $ENTORNO $VERSION $CASIP $LDAPIP
	sleep 5s
	echo  "*********************************************************** INSTALACION FINALIZADA cas $ENTORNO $VERSION  *************************************************"
fi
