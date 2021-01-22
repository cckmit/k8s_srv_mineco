# FROM composer:1.6.5 as build 
# WORKDIR /app 
# COPY . /app 
# RUN apk update

# RUN apk add libpng libpng-dev libjpeg-turbo-dev libwebp-dev zlib-dev libxpm-dev gd && docker-php-ext-install gd
# RUN composer install

FROM php:7.2-apache 
EXPOSE 80 
# COPY --from=build /app /app 
COPY vhost.conf /etc/apache2/sites-available/000-default.conf 

RUN apt-get update && apt-get install -y \
        libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng-dev \
        git \
        zip \ 
        unzip \
        libzip-dev \
    && pecl install mcrypt-1.0.1  \
    && docker-php-ext-install -j$(nproc) iconv   \
    && docker-php-ext-install zip  \
    && docker-php-ext-install pdo_mysql   \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd \
    && docker-php-ext-install bcmath \
    && docker-php-ext-enable mcrypt

RUN docker-php-ext-install mysqli pdo pdo_mysql

## install driver sql server

ARG INSTALL_MSSQL=false
RUN set -eux; \
    if [ $\{INSTALL_MSSQL\} = true ]; then \
        if [ $(php -r "echo PHP_MAJOR_VERSION;") = "5" ]; then \
        apt-get -y install freetds-dev libsybdb5 \
        && ln -s /usr/lib/x86_64-linux-gnu/libsybdb.so /usr/lib/libsybdb.so \
        && docker-php-ext-install mssql pdo_dblib \
        && php -m | grep -q 'mssql' \
        && php -m | grep -q 'pdo_dblib' \
        ;else \
        ###########################################################################
        # Ref from https://github.com/Microsoft/msphpsql/wiki/Dockerfile-for-adding-pdo_sqlsrv-and-sqlsrv-to-official-php-image
        ###########################################################################
        # Add Microsoft repo for Microsoft ODBC Driver 13 for Linux
        apt-get install -y apt-transport-https gnupg \
        && curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - \
        && curl https://packages.microsoft.com/config/debian/9/prod.list > /etc/apt/sources.list.d/mssql-release.list \
        && apt-get update -yqq \
        # Install Dependencies
        && ACCEPT_EULA=Y apt-get install -y unixodbc unixodbc-dev libgss3 odbcinst msodbcsql17 locales \
        && echo "en_US.UTF-8 UTF-8" > /etc/locale.gen \
        # link local aliases
        && ln -sfn /etc/locale.alias /usr/share/locale/locale.alias \
        && locale-gen \
        # Install pdo_sqlsrv and sqlsrv from PECL. Replace pdo_sqlsrv-4.1.8preview with preferred version.
        && if [ $(php -r "echo PHP_MINOR_VERSION;") = "0" ]; then \
            pecl install pdo_sqlsrv-5.3.0 sqlsrv-5.3.0 \
        ;else \
            pecl install pdo_sqlsrv sqlsrv \
        ;fi \
        && docker-php-ext-enable pdo_sqlsrv sqlsrv \
        && php -m | grep -q 'pdo_sqlsrv' \
        && php -m | grep -q 'sqlsrv' \
        ;fi \
    ;fi



# RUN chown -R www-data:www-data /app \
RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite 


# WORKDIR /app
WORKDIR /var/www/html


# RUN chmod -R 777 /app/storage
# RUN chmod -R 777 /app/bootstrap/cache
# RUN chmod -R 777 /app/public

# install node and npm
ENV NODE_VERSION=12.6.0
RUN apt install -y curl
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"

# install composer
RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
RUN php -r "if (hash_file('sha384', 'composer-setup.php') === '756890a4488ce9024fc62c56153228907f1545c228516cbf63f885e036d37e9a59d27d63f46af1d4d07ee0f76181c7d3') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
RUN php composer-setup.php
RUN php -r "unlink('composer-setup.php');"
RUN mv composer.phar /usr/local/bin/composer

#################################################
# RUN npm install
# RUN npm run dev
# RUN cp .env.example .env