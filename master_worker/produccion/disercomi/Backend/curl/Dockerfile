FROM jjolon/disercomi_backend:latest

COPY /ca-bundle.crt /etc/ssl/certs
# COPY /php.ini /usr/local/etc/php/
RUN docker-php-ext-install gd

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

