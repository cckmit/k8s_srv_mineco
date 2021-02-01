#!/bin/bash

#USE: run egoveris desa 1.0.0

#Expected params:
#	$1 product name: egoveris
#	$2 environment name: desa
#	$3 image or product version: 0.1.0
#	$4 server cas: 10.214.88.24
#	$5 server ldap: 10.214.88.24


SERVICE=cas
IMAGE_NAME=${SERVICE}-$1
CONTAINER_NAME=${IMAGE_NAME}-$2
PRODUCT_ENVIRONMENT=$1-$2
MACHINE_NAME=${CONTAINER_NAME}.egoveris.com
#NETWORK_DEVICE_NAME=eth0
#HOSTIP=`ip -4 addr show scope global dev ${NETWORK_DEVICE_NAME} | grep inet | awk '{print \$2}' | cut -d / -f 1`
HOSTIP=`hostname -I | cut -d" " -f 1`
HOSTNAME=`hostname`

EXPOSED_PORT=9000
EXPOSED_HTTPS_PORT=8443


#The --privileged=true flag will allow to run the container without problems when selinux is running, if not, it will have problems when accessing bindmounted volumes for example. This flag will  be mandatory in production environments
docker run -d \
	--memory="2048m" \
	--add-host=${HOSTNAME}:${HOSTIP} \
	--add-host=capa.digigob.gob.gt:$4 \
	--add-host=docker1-host.egoveris.com:$5 \
	-e HOST_NAME=${HOSTNAME} \
	--env-file ./docker-compose/${PRODUCT_ENVIRONMENT}.env \
	--name ${CONTAINER_NAME} \
	--privileged=true \
	-h ${MACHINE_NAME} \
	-p ${EXPOSED_PORT}:8080 \
	-p ${EXPOSED_HTTPS_PORT}:8443 \
	--mount type=volume,source=${CONTAINER_NAME}-keystore,target=/usr/local/tomcat/keystore \
	--mount type=volume,source=${CONTAINER_NAME}-logs,target=/usr/local/tomcat/logs \
	${IMAGE_NAME}:$3
