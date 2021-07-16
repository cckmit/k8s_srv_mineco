FROM  payara/server-full

ENV ADMIN_USER=admin\
    ADMIN_PASSWORD=admin

USER root

COPY mineco.p12 /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.crt /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.key /opt/payara/appserver/glassfish/domains/domain1/config

RUN echo "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=${ADMIN_PASSWORD}" > /tmp/tmpfile && \
    echo "AS_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${PASSWORD_FILE}

WORKDIR /opt/payara/appserver/glassfish/domains/domain1/config
# WORKDIR ${JAVA_HOME}/jre/bin

RUN echo mineco | keytool -importkeystore -storepass changeit -destkeystore keystore.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate

RUN echo changeit | keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias mineco_certificate | echo yes

RUN echo mineco |  keytool -importkeystore -storepass changeit -destkeystore cacerts.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate 

RUN echo changeit | keytool  -delete -alias mineco_certificate -keystore keystore.jks 

RUN echo changeit |  keytool -delete -alias mineco_certificate -keystore cacerts.jks


WORKDIR ${HOME_DIR}



RUN ${PAYARA_DIR}/bin/asadmin --user ${ADMIN_USER} --passwordfile=/tmp/tmpfile change-admin-password --domain_name=${DOMAIN_NAME} && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} start-domain ${DOMAIN_NAME} && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} enable-secure-admin 
