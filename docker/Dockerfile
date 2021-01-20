FROM composer:1.6.5 as build 
WORKDIR /app 
COPY . /app 
RUN composer install

FROM php:7.2-apache 
EXPOSE 80 
COPY --from=build /app /app 
COPY vhost.conf /etc/apache2/sites-available/000-default.conf 
RUN chown -R www-data:www-data /app \
    && a2enmod rewrite

WORKDIR /app

RUN chmod -R 777 /app/storage
RUN chmod -R 777 /app/bootstrap/cache
RUN chmod -R 777 /app/public