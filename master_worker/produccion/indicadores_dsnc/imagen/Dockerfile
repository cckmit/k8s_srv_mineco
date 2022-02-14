FROM php:7.4.0-apache

COPY vhost.conf /etc/apache2/sites-available/000-default.conf
COPY /ssl /etc/ssl/certs/

RUN a2enmod ssl


RUN apt-get update && apt-get install -y \
        libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng-dev \
        git \
        zip \
        unzip \
        libzip-dev \
        nano \
    && pecl install mcrypt-1.0.4  \
    && docker-php-ext-install -j$(nproc) iconv   \
    && docker-php-ext-install zip  \
    && docker-php-ext-install pdo_mysql   \
    && docker-php-ext-install bcmath \
    && docker-php-ext-enable mcrypt

RUN docker-php-ext-install mysqli pdo pdo_mysql


## install driver sql server

RUN apt-get install -y apt-transport-https gnupg \
    && curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - \
    && curl https://packages.microsoft.com/config/debian/9/prod.list > /etc/apt/sources.list.d/mssql-release.list \
    && apt-get update -yqq \
    && ACCEPT_EULA=Y apt-get install -y unixodbc unixodbc-dev libgss3 odbcinst msodbcsql17 locales \
    && echo "en_US.UTF-8 UTF-8" > /etc/locale.gen \
    && ln -sfn /etc/locale.alias /usr/share/locale/locale.alias \
    && locale-gen \
    && pecl install pdo_sqlsrv sqlsrv \
    && docker-php-ext-enable pdo_sqlsrv sqlsrv \
    && php -m | grep -q 'pdo_sqlsrv' \
    && php -m | grep -q 'sqlsrv' \
    && sed -i 's,^\(MinProtocol[ ]*=\).*,\1'TLSv1.2',g' /etc/ssl/openssl.cnf \
    && sed -i 's,^\(CipherString[ ]*=\).*,\1'DEFAULT@SECLEVEL=1',g' /etc/ssl/openssl.cnf
    

RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite

RUN a2enmod headers \
   && service apache2 restart 

WORKDIR /var/www/html