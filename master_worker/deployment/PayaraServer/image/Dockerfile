FROM  payara/server-full

ENV ADMIN_USER=admin\
    ADMIN_PASSWORD=DTIdesa@2K21 

USER root

RUN mkdir -p certificates

COPY certificate/ /certificate

# WORKDIR $JAVA_HOME/jre/bin

# RUN ./keytool -importkeystore -keypass mineco -destkeystore keystore.jks -srckeystore /opt/payara/certificate/mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate -storepass changeit

# ./keytool -importkeystore -storepass mineco -destkeystore keystore.jks -srckeystore /opt/payara/mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate 


# ./keytool -importcert -trustcacerts -destkeystore cacerts.jks -file /opt/payara/mineco.crt -alias mineco_certificate

# COPY ojdbc7-12.1.0.jar /opt/payara/appserver/glassfish/domains/domain1/lib/
# RUN mkdir -p /opt/payara/appserver/glassfish/domains/diaco
# COPY diaco/ /opt/payara/appserver/glassfish/domains/diaco
# ENV DOMAIN_NAME=diaco
# COPY bcpkix-jdk15on-155.jar /usr/lib/jvm/zulu8-ca-amd64/jre/lib/ext
# COPY bcprov-jdk15on-155.jar /usr/lib/jvm/zulu8-ca-amd64/jre/lib/ext
# COPY java.security /usr/lib/jvm/zulu8-ca-amd64/jre/lib/security/java.security
# COPY domain.xml /opt/payara/appserver/glassfish/domains/domain1/config/domain.xml



# EXPOSE 4848 8080 8181 8282 80
