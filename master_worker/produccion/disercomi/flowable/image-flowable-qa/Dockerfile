FROM payara/server-full:latest  

LABEL maintainer = "jjolong@miumg.edu.gt"

ADD mineco.pem /opt/payara

ADD mineco.crt /opt/payara

ADD mineco.key /opt/payara

RUN openssl pkcs12 -export -in mineco.crt -inkey mineco.key -out mycert.p12 -name mineco -password pass:mineco

RUN keytool -importkeystore -destkeystore keystore.jks -srckeystore mycert.p12 -srcstoretype PKCS12 -alias mineco

RUN keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias mineco

RUN asadmin start-domain & set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.cert-nickname=mineco


# RUN openssl pkcs12 -export -in /opt/payara/mineco.pem -inkey /opt/payara/mineco.pem -out pkcs.p12 -name mineco-ssl -password pass:mineco

# RUN keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore keystore.jks -srckeystore pkcs.p12 -srcstoretype PKCS12 -alias mineco -srcstorepass mineco 

# RUN keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mycert.crt -alias mydomain_certificate

# RUN keytool -importkeystore -srckeystore /opt/payara/mineco-ssl.jks -destkeystore keystore.jks

# RUN asadmin start-domain && set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.cert-nickname=mineco-ssl


