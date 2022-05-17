FROM payara/server-full:latest  

LABEL maintainer = "jjolong@miumg.edu.gt"

USER root 

COPY /ssl $PAYARA_DIR/glassfish/domains/domain1/config/

WORKDIR $PAYARA_DIR/glassfish/domains/domain1/config/

ENV ADMIN_USER=admin\
    ADMIN_PASSWORD=admin \
    PAYARA_DIR=/opt/payara/appserver \
    PASSWORD_FILE=/opt/payara/passwordFile

# RUN echo 'AS_ADMIN_MASTERPASSWORD=changeit\nAS_ADMIN_NEWMASTERPASSWORD=mineco' >> /opt/masterpwdfile

# RUN $PAYARA_DIR/bin/./asadmin change-master-password --passwordfile=/opt/masterpwdfile domain1 

RUN openssl pkcs12 -export -in mineco.crt -inkey mineco.key -out mineco.p12 -name cert22 -password pass:changeit

# RUN keytool -importkeystore -destkeystore keystore.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias cert22 -storepass changeit

# RUN keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias cert22  -storepass changeit

# RUN keytool -importkeystore -destkeystore cacerts.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias cert22 -storepass changeit


# RUN ${PAYARA_DIR}/bin/asadmin start-domain && set configs.config.server-config.network-config.protocols.protocol.http-listener-2.security-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls11-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls12-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.cert-nickname=mineco_ssl --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} 
    
RUN mkdir -p /opt/flowable/data