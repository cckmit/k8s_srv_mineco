FROM centos:centos7

MAINTAINER gaguiler@everis.com

RUN yum install -y httpd
RUN yum install httpd mod_ssl -y
USER apache
COPY ./conf-files/ssl-EGOVERISGUATEMALA.conf /etc/httpd/conf.d
COPY ./entrypoint.sh /
USER root
RUN mkdir -p /etc/httpd/SSL.crt
COPY ./conf-files/apache-selfsigned.crt /etc/httpd/SSL.crt
COPY ./conf-files/apache-selfsigned.key /etc/httpd/SSL.crt
RUN chmod 775 /entrypoint.sh
RUN mkdir -p /var/www/html/tomcat
COPY ./conf-files/apache-selfsigned.crt /var/www/html/tomcat
COPY ./conf-files/favicon.ico /var/www/html/tomcat
COPY ./conf-files/db_label_en.properties /var/www/html/tomcat
COPY ./conf-files/db_label_es.properties /var/www/html/tomcat
COPY ./conf-files/logo.jpg /var/www/html/tomcat
COPY ./conf-files/terminosycondiciones.txt /var/www/html/tomcat


ENTRYPOINT [ "/entrypoint.sh" ]

