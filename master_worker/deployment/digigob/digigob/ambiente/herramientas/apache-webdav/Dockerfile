FROM centos:centos7

MAINTAINER gaguiler@everis.com

RUN yum install -y httpd
ADD ./binary-files/conf.d.tar /etc/httpd/conf.d/
USER apache
RUN mkdir -p ./var/lib/dav
ADD ./binary-files/guarda-documental.tar /var/www/html/
COPY ./binary-files/*.jar /var/www/html/guarda-documental/PluginManager/ee/deployegovdocker/

COPY ./entrypoint.sh /
USER root
RUN chmod 775 /entrypoint.sh

ENTRYPOINT [ "/entrypoint.sh" ]

