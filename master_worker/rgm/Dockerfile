FROM payara/server-full:latest  

LABEL maintainer = "jjolong@miumg.edu.gt"

USER root 

COPY /certificado $PAYARA_DIR/glassfish/domains/domain1/config/

WORKDIR $PAYARA_DIR/glassfish/domains/domain1/config/

RUN openssl pkcs12 -export -in rgm.crt -inkey rgm.key -out rgm.p12 -name cert2022 -password pass:changeit





# RUN keytool -importkeystore -destkeystore keystore.jks -srckeystore rgm.p12 -srcstoretype PKCS12 -alias cert2022 -storepass changeit

# RUN keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias cert22  -storepass changeit

# RUN keytool -importkeystore -destkeystore cacerts.jks -srckeystore rgm.p12 -srcstoretype PKCS12 -alias cert2022 -storepass changeit


# RUN ${PAYARA_DIR}/bin/asadmin start-domain && set configs.config.server-config.network-config.protocols.protocol.http-listener-2.security-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls11-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.tls12-enabled=true --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} && \
#     set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.cert-nickname=mineco_ssl --user=${ADMIN_USER} --password=${ADMIN_PASSWORD} 
    
# RUN mkdir -p /opt/flowable/data