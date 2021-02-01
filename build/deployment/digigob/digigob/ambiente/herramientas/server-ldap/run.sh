#!/bin/bash

#USE: run egoveris desa 1.0.0

#Expected params:
#	$1 product name: egoveris
#	$2 environment name: desa
#	$3 image or product version: 1.0.0


SERVICE=ldap
IMAGE_NAME=${SERVICE}-$1
CONTAINER_NAME=${IMAGE_NAME}-$2
PRODUCT_ENVIRONMENT=$1-$2
MACHINE_NAME=${CONTAINER_NAME}.egoveris.com
CONTAINER_ENVIRONMENT=${PRODUCT_ENVIRONMENT}
HOSTIP=`hostname -I | cut -d" " -f 1`
HOSTNAME=`hostname`

EXPOSED_PORT=389

#The --privileged=true flag will allow to run the container without problems when selinux is running, if not, it will have problems when accessing bindmounted volumes for example. This flag will  be mandatory in production environments
docker run -d \
	--memory="2048m" \
	--add-host=${HOSTNAME}:${HOSTIP} \
	-e HOST_NAME=${HOSTNAME} \
	-e DOMAIN=egoveris.com \
	-e PASSWORD=secret \
	--name ${CONTAINER_NAME} \
	--privileged=true \
	-h ${MACHINE_NAME} \
	-p ${EXPOSED_PORT}:389 \
	${IMAGE_NAME}:$3

sleep 10s

./modifica.sh $1 $2
