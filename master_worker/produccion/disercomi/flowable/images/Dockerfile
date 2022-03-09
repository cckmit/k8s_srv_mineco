# FROM tomcat:8.0-alpine
# FROM tomcat:8.5.75-jdk11-openjdk-slim
FROM tomcat:8.5-alpine

LABEL maintainer="deepak@softwareyoga.com"

ADD flowable-ui.war /usr/local/tomcat/webapps/
ADD flowable-rest.war /usr/local/tomcat/webapps/
ADD tomcat-users.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/
ADD server.xml /usr/local/tomcat/conf/
ADD mineco.pfx /usr/local/tomcat/

EXPOSE 8080

CMD ["catalina.sh", "run"]
