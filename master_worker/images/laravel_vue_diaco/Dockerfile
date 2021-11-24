FROM php:7.3-apache

COPY vhost.conf /etc/apache2/sites-available/000-default.conf

RUN apt-get update && apt-get install -y \
    libfreetype6-dev \
            gcc make autoconf libc-dev pkg-config \
            libjpeg62-turbo-dev \
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
            libgmp-dev  \
        && pecl install mcrypt-1.0.2  \
        && docker-php-ext-install -j$(nproc) iconv   \
        && docker-php-ext-install zip  \
        && docker-php-ext-install pdo_mysql   \
        && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
        && docker-php-ext-install -j$(nproc) gd \
        && docker-php-ext-install bcmath \
        && docker-php-ext-configure ldap --with-libdir=lib/x86_64-linux-gnu/ \
        && docker-php-ext-install ldap \
        && docker-php-ext-enable mcrypt \
        && docker-php-ext-install mysqli pdo pdo_mysql \
        && docker-php-ext-install gmp


RUN apt-get update && apt-get install -y libmagickwand-dev --no-install-recommends && rm -rf /var/lib/apt/lists/*
RUN printf "\n" | pecl install imagick
RUN docker-php-ext-enable imagick

RUN chown -R www-data:www-data /var/www/html

WORKDIR /var/www/html

RUN a2enmod headers \
    && a2enmod rewrite \
    && service apache2 restart

ENV NODE_VERSION=14.18.1
RUN apt install -y curl
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"

RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
RUN php -r "if (hash_file('sha384', 'composer-setup.php') === '906a84df04cea2aa72f40b5f787e49f22d4c2f19492ac310e8cba5b96ac8b64115ac402c8cd292b8a03482574915d1a8') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
RUN php composer-setup.php
RUN php -r "unlink('composer-setup.php');"
RUN mv composer.phar /usr/local/bin/composer