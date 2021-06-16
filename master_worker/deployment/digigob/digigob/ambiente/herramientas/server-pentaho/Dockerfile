FROM flexberry/pentaho.8.2
MAINTAINER gaguiler@everis.com

COPY ./binary-files/backup.zip /
COPY ./binary-files/cors-filter-2.4.jar /biserver-ce/tomcat/lib
COPY ./conf-files/pentaho.xml /biserver-ce/pentaho-solutions/system/
COPY ./conf-files/certificado.crt /biserver-ce/tomcat
COPY ./conf-files/cda/settings.xml /biserver-ce/pentaho-solutions/system/cda/
COPY ./conf-files/cdf/settings.xml /biserver-ce/pentaho-solutions/system/pentaho-cdf/
COPY ./conf-files/cdf-dd/settings.xml /biserver-ce/pentaho-solutions/system/pentaho-cdf-dd/
COPY ./conf-files/web.xml /biserver-ce/tomcat/conf
COPY ./conf-files/security.properties /biserver-ce/pentaho-solutions/system/

COPY entrypoint.sh /
RUN chmod 775 /entrypoint.sh

#CMD ["/entrypoint.sh"]
