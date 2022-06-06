# FROM tomcat:8.0-alpine
# FROM tomcat:8.5.75-jdk11-openjdk-slim
FROM tomcat:8.5-alpine

COPY mineco.pfx /etc/ssl

COPY context.xml /usr/local/tomcat/conf/
COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY server.xml /usr/local/tomcat/conf/

# RUN keytool -importcert -alias mineco -keystore  /etc/ssl/cacerts -storepass changeit -file ca.der 

# keytool -keystore "/etc/ssl/cacerts" -storepass changeit -list | grep mineco




# keytool -import -trustcacerts -alias mineco -keystore mineco.jks -file /etc/ssl/certs/mineco.cer
# keytool -import -trustcacerts -alias mineco -keystore mineco.jks -file /etc/ssl/certs/mineco.cer


EXPOSE 80 443

CMD ["catalina.sh", "run"]





