FROM payara/server-full:latest  

LABEL maintainer = "jjolong@miumg.edu.gt"

USER root 

COPY /ssl $PAYARA_DIR/glassfish/domains/domain1/config/

RUN openssl pkcs12 -export -in $PAYARA_DIR/glassfish/domains/domain1/config/mineco.crt -inkey $PAYARA_DIR/glassfish/domains/domain1/config/mineco.key -out $PAYARA_DIR/glassfish/domains/domain1/config/mineco.p12 -name mineco_ssl -password pass:changeit
RUN keytool -importkeystore -destkeystore keystore.jks -srckeystore $PAYARA_DIR/glassfish/domains/domain1/config/mineco.p12 -srcstoretype PKCS12 -alias mineco_ssl
RUN keytool -importcert -trustcacerts -destkeystore cacerts.jks -file $PAYARA_DIR/glassfish/domains/domain1/config/mineco.crt -alias mineco_ssl  

RUN mkdir -p /opt/flowable/data