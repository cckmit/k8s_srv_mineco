FROM tomcat:8.5-jre8-alpine
ENV DB_HOST=docker1-host.egoveris.com
ENV DB_PORT=3306
ENV DB_USER=temuco
ENV DB_PASS=temuco
ENV DB_NAME_PREFIX=QA_
ENV DB_NAME_SUFIX=_TRADE
ADD ./workflow-designer-webapp/target/wd-web.war /usr/local/tomcat/webapps/
RUN apk --no-cache add curl
RUN curl -L -o /usr/local/tomcat/lib/mysql-connector-java-5.1.39.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.39/mysql-connector-java-5.1.39.jar
ADD ./conf/context.xml /usr/local/tomcat/conf/
ADD ./conf/setenv.sh /usr/local/tomcat/bin/
COPY ./conf/entrypoint.sh /
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 8080
