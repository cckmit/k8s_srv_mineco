#!/bin/bash

#USE: run egoveris desa 1.0.0

#Expected params:
#	$1 product name: egoveris
#	$2 environment name: desa
#	$3 image or product version: 1.0.0

SERVICE=pentaho
IMAGE_NAME=${SERVICE}-$1
CONTAINER_NAME=${IMAGE_NAME}-$2
PRODUCT_ENVIRONMENT=$1-$2

HOST_DOMAIN=.egoveris.com
MACHINE_NAME=${CONTAINER_NAME}${HOST_DOMAIN}
#NETWORK_DEVICE_NAME=eth0
#HOSTIP=`ip -4 addr show scope global dev ${NETWORK_DEVICE_NAME} | grep inet | awk '{print \$2}' | cut -d / -f 1`
HOSTIP=`hostname -I | cut -d" " -f 1`
HOSTNAME=`hostname`

EXPOSED_PORT=8080
EXPOSED_AJP_PORT=8009

#The --privileged=true flag will allow to run the container without problems when selinux is running, if not, it will have problems when accessing bindmounted volumes for example. This flag will  be mandatory in production environments
docker run -d \
	--memory="5120m" \
	--add-host=${HOSTNAME}:${HOSTIP} \
	--add-host=capa.digigob.gob.gt:$4 \
	--add-host=mysql.egoveris.com:$5 \
	--name ${CONTAINER_NAME} \
	--privileged=true \
	-e SERVER_NAME=${HOSTNAME} \
	-h ${MACHINE_NAME} \
	-p ${EXPOSED_AJP_PORT}:8009 \
	-p ${EXPOSED_PORT}:8080 \
	--mount type=volume,source=${CONTAINER_NAME}-logs,target=/usr/usr/local/tomcat/logs \
	${IMAGE_NAME}:$3
