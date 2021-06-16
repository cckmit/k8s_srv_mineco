#!/bin/bash
if [[ -z "${CERTIFICADOS}" ]]; then
	echo "No hay certificados para instalar."
else
	IFS=' '
	echo "Instalando certificados..."
	read -a certs <<< "${CERTIFICADOS}"
	for val in "${certs[@]}";
	do
		echo "Instalando ${val}..."
		ALIAS=${val%.*}
		CERT="/etc/certs/${val}"
		echo "Alias ${ALIAS}"
		keytool -importcert -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -alias ${ALIAS} -file ${CERT} -trustcacerts -noprompt
		
		echo "${val} instalado."
	done
fi
/usr/local/tomcat/bin/catalina.sh run
