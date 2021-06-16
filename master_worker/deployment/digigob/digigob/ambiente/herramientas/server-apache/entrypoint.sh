#!/bin/bash

sed -i "/^#ServerName/c\
ServerName http://app.egoveris.com:8081" /etc/httpd/conf/httpd.conf

chown -R apache:apache /var/www

#su -l apache
/usr/sbin/httpd -D FOREGROUND
