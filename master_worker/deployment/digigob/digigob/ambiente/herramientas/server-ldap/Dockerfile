FROM openshift/openldap-2441-centos7:latest
MAINTAINER gaguiler@everis.com

COPY /conf-files/egoveris.ldif /
COPY entrypoint.sh /
COPY addUser.sh /
RUN chmod 775 /entrypoint.sh
RUN chmod 775 /addUser.sh
