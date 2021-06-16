FROM tomcat:6
MAINTAINER gaguiler@everis.com

COPY ./binary-files/bweb-solr.war /usr/local/tomcat/webapps/
ADD ./binary-files/lib.tar /usr/local/tomcat/
ADD ./binary-files/solr-home.tar /
COPY ./conf-files/catalina.sh /usr/local/tomcat/bin/
RUN chmod 775 /usr/local/tomcat/bin/catalina.sh
COPY ./conf-files/context.xml /usr/local/tomcat/conf/
COPY ./conf-files/manager.xml /usr/local/tomcat/conf/Catalina/localhost/
COPY ./conf-files/tomcat-users.xml /usr/local/tomcat/conf/
COPY entrypoint.sh /
RUN chmod 775 /entrypoint.sh

CMD [ "/entrypoint.sh" ]