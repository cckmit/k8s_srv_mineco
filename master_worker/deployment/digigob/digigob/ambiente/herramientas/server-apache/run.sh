#!/bin/bash

#USE: run egoveris desa

#Expected params:
#	$1 product name: egoveris
#	$2 environment name: desa
#	$3 image or product version: 1.0.0



APP=apache
IMAGE_NAME=${APP}-$1
CONTAINER_NAME=${IMAGE_NAME}-$2
PRODUCT_ENVIRONMENT=$1-$2

HOST_DOMAIN=.egoveris.com
MACHINE_NAME=${CONTAINER_NAME}${HOST_DOMAIN}
HOSTIP=`hostname -I | cut -d" " -f 1`
HOSTNAME=`hostname`

EXPOSED_PORT=8081

#The --privileged=true flag will allow to run the container without problems when selinux is running, if not, it will have problems when accessing bindmounted volumes for example. This flag will  be mandatory in production environments
docker run -d \
	--memory="2048m" \
	--add-host=${HOSTNAME}:${HOSTIP} \
	--name ${CONTAINER_NAME} \
	--privileged=true \
	-h ${MACHINE_NAME} \
	-e SERVER_NAME=${HOSTNAME} \
	-p ${EXPOSED_PORT}:80 \
	-p 443:443 \
	--mount type=volume,source=${CONTAINER_NAME},target=/var/www/html/ \
	${IMAGE_NAME}:$3