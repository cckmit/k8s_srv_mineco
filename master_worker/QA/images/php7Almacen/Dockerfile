FROM gjuniioor/php-sqlsrv:7.0

# COPY vhost.conf /etc/apache2/sites-available/000-default.conf 

RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite 
WORKDIR /var/www/html