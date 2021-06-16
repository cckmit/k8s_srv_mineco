FROM tomcat:8.5.20-alpine
MAINTAINER gaguiler@everis.com

COPY ./binary-files/cas.war /usr/local/tomcat/webapps/
ADD binary-files/lib.tar /usr/local/tomcat/lib/
COPY conf-files/cas.properties /usr/local/tomcat/common/classes/
COPY conf-files/context.xml /usr/local/tomcat/conf/
COPY conf-files/server.xml /usr/local/tomcat/conf/
COPY conf-files/ldap-cfg.xml /usr/local/tomcat/common/classes/
COPY conf-files/manager.xml /usr/local/tomcat/conf/Catalina/localhost/
COPY conf-files/tomcat-users.xml /usr/local/tomcat/conf/
COPY entrypoint.sh /
RUN chmod 775 /entrypoint.sh

CMD ["/entrypoint.sh"]