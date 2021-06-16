#!/bin/bash

sed -i "/^cas.securityContext.serviceProperties.service=/c\cas.securityContext.serviceProperties.service=$APPS_PROTOCOL://$HOST_NAME:$SERVER_CAS_HTTPS_PORT/cas/services/j_acegi_cas_security_check" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^cas.securityContext.casProcessingFilterEntryPoint.loginUrl=/c\cas.securityContext.casProcessingFilterEntryPoint.loginUrl=$APPS_PROTOCOL://$HOST_NAME:$SERVER_CAS_HTTPS_PORT/cas/login" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^cas.securityContext.ticketValidator.casServerUrlPrefix=/c\cas.securityContext.ticketValidator.casServerUrlPrefix=$APPS_PROTOCOL://$HOST_NAME:$SERVER_CAS_HTTPS_PORT/cas" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^eu.url=/c\eu.url=$APPS_PROTOCOL://$DEFAULT_APP_SERVER_NAME:$DEFAULT_APP_SERVER_PORT/$DEFAULT_APP_CONTEXT_ROOT" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^host.name=/c\host.name=$HOST_NAME" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^inpe.userValidation.url=/c\inpe.userValidation.url=$APPS_PROTOCOL://$VALIDATION_APP_SERVER_NAME:$VALIDATION_APP_SERVER_PORT/$VALIDATION_PATH" /usr/local/tomcat/common/classes/cas.properties
sed -i "/^inpe.disableValidation=/c\inpe.disableValidation=$VALIDATION_DISABLED" /usr/local/tomcat/common/classes/cas.properties

sed -i "\%value=\"ldap://%c\		<constructor-arg type=\"java.lang.String\" value=\"ldap://$LDAP_SERVER_NAME:$LDAP_PORT/\"/>" /usr/local/tomcat/common/classes/ldap-cfg.xml
sed -i "\%value=\"secret\"%c\		<constructor-arg type=\"java.lang.String\" value=\"$LDAP_PASSWORD\"/>" /usr/local/tomcat/common/classes/ldap-cfg.xml

sed -i "/^common.loader=/c\common.loader=\"\${catalina.base}/lib\",\"\${catalina.base}/lib/*.jar\",\"\${catalina.home}/lib\",\"\${catalina.home}/lib/*.jar\",\"\${catalina.home}/common/classes\"" /usr/local/tomcat/conf/catalina.properties

sed -i "/<Environment description=\"Nombre del Nodo\"/c\<Environment description=\"Nombre del Nodo\" name=\"nombreNodo\" override=\"false\" type=\"java.lang.String\" value=\"SERVER-$APP-$PRODUCT-$ENVIRONMENT\"/>" /usr/local/tomcat/conf/context.xml

sed -i "/<user username=\"deployer\" password=\"deployer\"/c\<user username=\"$TOMCAT_REMOTE_USER\" password=\"$TOMCAT_REMOTE_PASSWORD\" roles=\"manager-script,manager-gui,manager-jmx,manager-status\"/>" /usr/local/tomcat/conf/tomcat-users.xml

sed -i "\%<Connector protocol=\"org.apache.coyote.http11.Http11NioProtocol\"%c\	<Connector protocol=\"org.apache.coyote.http11.Http11NioProtocol\" port=\"8443\" maxThreads=\"200\" scheme=\"https\" secure=\"true\" SSLEnabled=\"true\" keystoreFile=\"keystore/$CERTIFICATE_KEYSTORE\" keystorePass=\"$CERTIFICATE_STOREPASS\" clientAuth=\"false\" sslProtocol=\"TLS\"/>" /usr/local/tomcat/conf/server.xml
sed -i "\%</Host>%c\	<Valve className=\"org.apache.catalina.valves.RemoteAddrValve\" addConnectorPort=\"true\" invalidAuthenticationWhenDeny=\"true\" allow=\".*\"/> </Host>" /usr/local/tomcat/conf/server.xml


cd /usr/local/tomcat/keystore

#We override the Certificate main Id with the real hostname obtained in the first run of the container.
CERTIFICATE_CN= app.egoveris.com

$JAVA_HOME/bin/keytool -genkey -noprompt \
	-alias $CERTIFICATE_ALIAS \
	-dname "CN=$CERTIFICATE_CN, OU=$CERTIFICATE_OU, O=$CERTIFICATE_O, L=$CERTIFICATE_L, S=$CERTIFICATE_S, C=$CERTIFICATE_C" \
	-keyalg $CERTIFICATE_ALGORITHM \
	-keysize $CERTIFICATE_KEYSIZE \
	-keystore $CERTIFICATE_KEYSTORE \
	-storepass $CERTIFICATE_STOREPASS \
	-validity $CERTIFICATE_VALIDITY

/usr/local/tomcat/bin/catalina.sh run
