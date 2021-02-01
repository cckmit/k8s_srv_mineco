#!/bin/bash

sed -i "/^#ServerName/c\
ServerName http://docker1-host.egoveris.com:80" /etc/httpd/conf/httpd.conf

chown -R apache:apache /var/www

#su -l apache
/usr/sbin/httpd -D FOREGROUND
