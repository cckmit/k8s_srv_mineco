FROM php:5.6-apache
EXPOSE 80 
# COPY --from=build /app /app 
COPY vhost.conf /etc/apache2/sites-available/000-default.conf 


RUN apt-get update && apt-get install -y \
        libbz2-dev \
        libfreetype6-dev \
        libgd-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng16-16 \
        libxml2-dev \
        zlib1g-dev \
    && docker-php-ext-install iconv mbstring mcrypt soap sockets zip \
    && docker-php-ext-configure gd --enable-gd-native-ttf --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install gd \
    && docker-php-ext-configure mysql --with-mysql=mysqlnd \
    && docker-php-ext-install mysql
    
RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite 


# WORKDIR /app
WORKDIR /var/www/html
