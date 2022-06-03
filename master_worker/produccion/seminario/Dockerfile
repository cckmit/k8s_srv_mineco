FROM jjolon/lara_vue:3 
EXPOSE 80 443


COPY vhost.conf /etc/apache2/sites-available/000-default.conf
COPY /ssl /etc/ssl/certs/

RUN a2enmod ssl


RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite

RUN a2enmod headers \
   && service apache2 restart 

WORKDIR /var/www/html