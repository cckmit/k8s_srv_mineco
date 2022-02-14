# FROM tomcat:8.0-alpine
FROM tomcat:8.5-alpine

LABEL maintainer="deepak@softwareyoga.com"

# COPY vhost.conf /etc/apache2/sites-available/000-default.conf 
# COPY /ssl /etc/ssl/certs/

# RUN a2enmod ssl

# ADD flowable-ui.war /usr/local/tomcat/webapps/
ADD tomcat-users.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/
ADD server.xml /usr/local/tomcat/conf/
ADD server.xml /usr/local/tomcat/conf/
ADD web.xml /usr/local/tomcat/


# RUN a2enmod rewrite 

# RUN a2enmod headers \
#    && service apache2 restart 

EXPOSE 8080

CMD ["catalina.sh", "run"]
