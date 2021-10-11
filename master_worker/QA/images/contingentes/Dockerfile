FROM php:5.5.29-apache

RUN apt-get update && apt-get install -y --no-install-recommends \
    libcurl4-openssl-dev \
    libedit-dev \
    libsqlite3-dev \
    libssl-dev \
    libxml2-dev \
    zlib1g-dev \
    freetds-dev \
    freetds-bin \
    freetds-common \
    libdbd-freetds \
    libsybdb5 \
    libqt4-sql-tds \
    libqt5sql5-tds \
    libqxmlrpc-dev \
    && ln -s /usr/lib/x86_64-linux-gnu/libsybdb.so /usr/lib/libsybdb.so \
    && ln -s /usr/lib/x86_64-linux-gnu/libsybdb.a /usr/lib/libsybdb.a \
    && docker-php-ext-install   mssql \
    && docker-php-ext-configure mssql \
    && chmod 755 /var/www/html -R \
    && chown www-data:www-data /var/www/html



RUN apt-get update && apt-get install -y libmcrypt-dev mariadb-client \
    libgd-dev \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install mcrypt pdo_mysql gd

COPY conf/php.ini /usr/local/etc/php/
COPY conf.d/ /usr/local/etc/php/conf.d/

# install composer
RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
RUN php -r "if (hash_file('sha384', 'composer-setup.php') === '756890a4488ce9024fc62c56153228907f1545c228516cbf63f885e036d37e9a59d27d63f46af1d4d07ee0f76181c7d3') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
RUN php composer-setup.php
RUN php -r "unlink('composer-setup.php');"
RUN mv composer.phar /usr/local/bin/composer