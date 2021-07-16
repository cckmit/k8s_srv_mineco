FROM  payara/server-full

# ENV ADMIN_USER=admin\
#     ADMIN_PASSWORD=DTIdesa@2K21 

USER root

COPY mineco.p12 /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.crt /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.key /opt/payara/appserver/glassfish/domains/domain1/config

WORKDIR /opt/payara/appserver/glassfish/domains/domain1/config
# WORKDIR ${JAVA_HOME}/jre/bin

RUN echo mineco | keytool -importkeystore -storepass changeit -destkeystore keystore.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate

RUN echo changeit | keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias mineco_certificate | echo yes

RUN echo mineco |  keytool -importkeystore -storepass changeit -destkeystore cacerts.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate 

RUN echo changeit | keytool  -delete -alias mineco_certificate -keystore keystore.jks 

RUN echo changeit |  keytool -delete -alias mineco_certificate -keystore cacerts.jks

RUN asadmin stop-domain domain1 \
    asadmin start-domain domain1 
    

