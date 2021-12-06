FROM php:5.6-apache
EXPOSE 80 
# COPY --from=build /app /app 
COPY vhost.conf /etc/apache2/sites-available/000-default.conf 


RUN apt-get update && apt-get install -y libmcrypt-dev mariadb-client \
    libgd-dev \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install mcrypt pdo_mysql gd

    
RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite 


# WORKDIR /app
WORKDIR /var/www/html


