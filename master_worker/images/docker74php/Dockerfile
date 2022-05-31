FROM php:7.4-apache 
EXPOSE 80 443

COPY vhost.conf /etc/apache2/sites-available/000-default.conf 

RUN apt-get update && apt-get install -y \
        libfreetype6-dev \
        gcc make autoconf libc-dev pkg-config \
        libmcrypt-dev \
        libpng-dev \
        git \
        zip \ 
        unzip \
        libzip-dev \
        nano \
        python2  \
        iputils-ping  \
        libldap2-dev   \
        libxml2-dev  \
    && pecl install mcrypt-1.0.3  \
    && docker-php-ext-install -j$(nproc) iconv   \
    && docker-php-ext-install zip  \
    && docker-php-ext-install pdo_mysql   \
    && docker-php-ext-install bcmath \
    && docker-php-ext-configure ldap --with-libdir=lib/x86_64-linux-gnu/ \
    && docker-php-ext-install ldap \
    && docker-php-ext-enable mcrypt \
    && docker-php-ext-install pcntl \
    && docker-php-ext-install soap

RUN docker-php-ext-install mysqli pdo pdo_mysql

RUN apt-get -y install supervisor
COPY horizon.conf /etc/supervisor/conf.d

RUN chown -R www-data:www-data /var/www/html 

WORKDIR /var/www/html

RUN a2enmod headers \
    && a2enmod rewrite \
   && service apache2 restart 

# RUN echo "\
# SMTP = correo.mineco.gob.gt\n\
# smtp_port = 25\n\
# bcmath.scale = 0\n\
# max_execution_time = 6000\n\
# memory_limit = 3G\n\
# upload_max_filesize = 200M\n\
# max_file_uploads = 200M\n\
# default_charset = \"UTF-8\"\n\
# post_max_size = 2G\n\
# short_open_tag = On" > /usr/local/etc/php/php.ini

ENV NODE_VERSION=14.18.1
RUN apt install -y curl
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"

RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
RUN php -r "if (hash_file('sha384', 'composer-setup.php') === '55ce33d7678c5a611085589f1f3ddf8b3c52d662cd01d4ba75c0ee0459970c2200a51f492d557530c71c15d8dba01eae') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
RUN php composer-setup.php
RUN php -r "unlink('composer-setup.php');"
RUN mv composer.phar /usr/local/bin/composer