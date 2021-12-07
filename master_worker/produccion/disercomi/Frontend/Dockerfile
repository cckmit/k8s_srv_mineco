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
    && pecl install mcrypt-1.0.3  \
    && docker-php-ext-install -j$(nproc) iconv   \
    && docker-php-ext-install zip  \
    && docker-php-ext-install pdo_mysql   \
    && docker-php-ext-install bcmath \
    && docker-php-ext-configure ldap --with-libdir=lib/x86_64-linux-gnu/ \
    && docker-php-ext-install ldap \
    && docker-php-ext-enable mcrypt

RUN docker-php-ext-install mysqli pdo pdo_mysql

RUN chown -R www-data:www-data /var/www/html

WORKDIR /var/www/html

RUN a2enmod headers \
    && a2enmod rewrite \
   && service apache2 restart



ENV NODE_VERSION=12.6.0
RUN apt install -y curl
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"