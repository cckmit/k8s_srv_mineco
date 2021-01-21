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

ENV NODE_VERSION=12.6.0
RUN apt install -y curl
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"
RUN npm install
RUN npm run dev
RUN cp .env.example .env