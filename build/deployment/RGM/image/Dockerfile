FROM  payara/server-full

COPY ojdbc7-12.1.0.jar /opt/payara/appserver/glassfish/domains/domain1/lib/
COPY bcpkix-jdk15on-155.jar /usr/lib/jvm/zulu8-ca-amd64/jre/lib/ext
COPY bcprov-jdk15on-155.jar /usr/lib/jvm/zulu8-ca-amd64/jre/lib/ext

RUN apt-get update && apt-get install -y \
        nano 

EXPOSE 4848 8080 8181 8282
