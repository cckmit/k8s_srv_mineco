#!/bin/bash


#cd /biserver-ce 
#./import-export.sh --restore --url=http://docker-host.egoveris.com:8090/pentaho --username=admin --password=password --file-path=/backup.zip --overwrite=true --logfile=/logfile.log

cd /biserver-ce/tomcat

keytool -importcert -keystore /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.144-1.b01.x86_64/jre/lib/security/cacerts -storepass changeit -alias capa.digigob.gob.gt -file /biserver-ce/tomcat/certificado.crt -trustcacerts -noprompt

keytool -importcert -keystore /etc/pki/java/cacerts -storepass changeit -alias capa.digigob.gob.gt -file /biserver-ce/tomcat/certificado.crt -trustcacerts -noprompt


