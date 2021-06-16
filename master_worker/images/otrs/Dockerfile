# FROM composer:1.6.5 as build 
# WORKDIR /app 
# COPY . /app 
# RUN apk update

# RUN apk add libpng libpng-dev libjpeg-turbo-dev libwebp-dev zlib-dev libxpm-dev gd && docker-php-ext-install gd
# RUN composer install


FROM php:7.2-apache 
EXPOSE 80 443 587 25


# ENV APP_HOST='localhost'
# COPY --from=build /app /app 
COPY vhost.conf /etc/apache2/sites-available/000-default.conf 
# RUN sed -i 's/ServerName APP_HOST/ServerName '$APP_HOST'/g' /etc/apache2/sites-available/000-default.conf 
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
        python2  \
        iputils-ping  \
        libldap2-dev   \
    && pecl install mcrypt-1.0.1  \
    && docker-php-ext-install -j$(nproc) iconv   \
    && docker-php-ext-install zip  \
    && docker-php-ext-install pdo_mysql   \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd \
    && docker-php-ext-install bcmath \
    && docker-php-ext-configure ldap --with-libdir=lib/x86_64-linux-gnu/ \
    && docker-php-ext-install ldap \
    && docker-php-ext-enable mcrypt

RUN docker-php-ext-install mysqli pdo pdo_mysql

RUN apt-get -y install supervisor


# RUN sed -i 's/^\(\[supervisord\]\)$/\1\nnodaemon=true/' /etc/supervisor/supervisord.conf
# RUN echo "supervisord -c /etc/supervisord.conf" >> /root/.bashrc
COPY supervisord-laravel.conf /etc/supervisor/conf.d/supervisor-laravel.conf
# RUN echo user=root >> /etc/supervisor/supervisord.conf
# CMD ["/usr/bin/supervisord","-c","/etc/supervisor/supervisord.conf"]
# CMD ["supervisord", "-c", "/etc/supervisor/supervisord.conf"]
# /usr/bin/supervisord -c /etc/supervisor/supervisord.conf



# CMD ["/usr/bin/supervisord"]
# RUN supervisord -c /etc/supervisor/supervisor.conf
# RUN service supervisor start \
#     supervisorctl reread \ 
#     supervisorctl update \
#     supervisorctl start supervisord-laravel:*

# ENTRYPOINT ["/usr/bin/supervisord", "-n", "-c",  "/etc/supervisord.conf"]

# RUN chown -R www-data:www-data /app \
RUN chown -R www-data:www-data /var/www/html \
    && a2enmod rewrite 


#     /usr/local/apache/bin/apachectl restart
# WORKDIR /app
WORKDIR /var/www/html

RUN a2enmod headers \
   && service apache2 restart 
# RUN chmod -R 777 /app/storage
# RUN chmod -R 777 /app/bootstrap/cache
# RUN chmod -R 777 /app/public

RUN echo "\
SMTP = correo.mineco.gob.gt\n\
smtp_port = 25\n\
bcmath.scale = 0\n\
max_execution_time = 6000\n\
memory_limit = 3G\n\
upload_max_filesize = 200M\n\
max_file_uploads = 200\n\
default_charset = \"UTF-8\"\n\
post_max_size = 2G\n\
short_open_tag = On" > /usr/local/etc/php/php.ini

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
# RUN composer install
# RUN npm install
# RUN npm run dev
# RUN cp .env.example .env