#!/bin/bash
export JAVA_OPTS="-Dfile.encoding=UTF-8 -DDB_HOST=${DB_HOST} -DDB_PORT=${DB_PORT} -DDB_NAME_PREFIX=${DB_NAME_PREFIX} -DDB_NAME_SUFIX=${DB_NAME_SUFIX} -DDB_USER=${DB_USER} -DDB_PASS=${DB_PASS} -DNOMBRE_NODO=${NOMBRE_NODO}"
if [[ -z "${DEBUG_PORT}" ]]; then
	echo "Se ejecuta sin debug"
else
	echo "Se configura el puerto ${DEBUG_PORT} como puerto de debug."
	export JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"
fi
