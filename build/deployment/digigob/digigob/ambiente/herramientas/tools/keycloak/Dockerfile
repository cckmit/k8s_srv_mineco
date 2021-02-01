FROM jboss/keycloak:10.0.1
ENV KEYCLOAK_USER=admin
ENV KEYCLOAK_PASSWORD=admin
ENV DB_ADDR=mariadb
ENV DB_USER=keycloak
ENV DB_PASSWORD=keycloak
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-11.0.7.10-1.el8_1.x86_64
COPY themes /opt/jboss/keycloak/themes
COPY vuc-event-listener.jar /opt/jboss/keycloak/standalone/deployments
COPY vuc-registration-validator.jar /opt/jboss/keycloak/standalone/deployments
EXPOSE 8080
EXPOSE 8443
