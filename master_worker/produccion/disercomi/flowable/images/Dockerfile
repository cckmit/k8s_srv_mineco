# FROM tomcat:8.0-alpine
# FROM tomcat:8.5.75-jdk11-openjdk-slim
FROM tomcat:8.5-alpine

COPY ca.der /usr/local/tomcat

COPY context.xml /usr/local/tomcat/conf/
COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY server.xml /usr/local/tomcat/conf/

# RUN keytool -importcert -alias mineco -keystore  /etc/ssl/cacerts -storepass changeit -file ca.der 

# keytool -keystore "/etc/ssl/cacerts" -storepass changeit -list | grep mineco


EXPOSE 8080

CMD ["catalina.sh", "run"]





