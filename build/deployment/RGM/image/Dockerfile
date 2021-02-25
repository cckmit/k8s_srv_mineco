FROM shinyay/docker-glassfish5:latest

ENV PASSWORD=glassfish

RUN echo "----- CREATE PASSWORD FILE --------------------" && \
    echo "AS_ADMIN_PASSWORD=" > /tmp/gfpassword && \
    echo "AS_ADMIN_NEWPASSWORD=${PASSWORD}" >> /tmp/gfpassword  && \
    echo "----- CHANGE ADMIN PASSWORD AND SECURE ADMIN ACCESS --------------------" && \
    asadmin --user=admin --passwordfile=/tmp/gfpassword change-admin-password --domain_name domain1 && \
    echo "----- ENABLE DOMAIN ADMINISTRATION SERVER --------------------" && \
    asadmin start-domain && \
    echo "AS_ADMIN_PASSWORD=${PASSWORD}" > /tmp/gfpassword && \
    asadmin --user=admin --passwordfile=/tmp/gfpassword enable-secure-admin && \
    asadmin --user=admin stop-domain 

EXPOSE 4848 8080 8181 8282