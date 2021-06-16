#!/bin/bash

#USE: run egoveris desa 1.0.0

#Expected params:
#	$1 product name: egoveris
#	$2 environment name: desa
#	$3 image or product version: 1.0.0
#	$4 server Mysql: 10.214.88.24


SERVICE=solr
IMAGE_NAME=${SERVICE}-$1
CONTAINER_NAME=${IMAGE_NAME}-$2
PRODUCT_ENVIRONMENT=$1-$2

HOST_DOMAIN=.egoveris.com
MACHINE_NAME=${CONTAINER_NAME}${HOST_DOMAIN}
#NETWORK_DEVICE_NAME=eth0
#HOSTIP=`ip -4 addr show scope global dev ${NETWORK_DEVICE_NAME} | grep inet | awk '{print \$2}' | cut -d / -f 1`
HOSTIP=`hostname -I | cut -d" " -f 1`
HOSTNAME=`hostname`

EXPOSED_PORT=8090

#The --privileged=true flag will allow to run the container without problems when selinux is running, if not, it will have problems when accessing bindmounted volumes for example. This flag will  be mandatory in production environments
docker run -d \
	--memory="2048m" \
	--add-host=${HOSTNAME}:${HOSTIP} \
	--env-file ./docker-compose/${PRODUCT_ENVIRONMENT}.env \
	--add-host=mysql.egoveris.com:$4 \
	--name ${CONTAINER_NAME} \
	--privileged=true \
	-e SERVER_NAME=${HOSTNAME} \
	-h ${MACHINE_NAME} \
	-p ${EXPOSED_PORT}:8080 \
	--mount type=volume,source=${CONTAINER_NAME}-logs,target=/usr/usr/local/tomcat/logs \
	${IMAGE_NAME}:$3