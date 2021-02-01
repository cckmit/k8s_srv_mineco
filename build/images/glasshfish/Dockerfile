# FROM store/oracle/serverjre:8 



# # Set environment variables and default password for user 'admin'
# ENV GLASSFISH_PKG=glassfish-5.0-b25-09_23_2017.zip \
#     GLASSFISH_URL=http://download.oracle.com/glassfish/5.0/nightly \
#     GLASSFISH_HOME=/glassfish5 \
#     PATH=$PATH:/glassfish5/bin \
#     PASSWORD=glassfish

# # Install packages, download and extract GlassFish
# # Setup password file
# # Enable DAS
# RUN yum install -y wget unzip && \
#     wget --no-check-certificate ${GLASSFISH_URL}/${GLASSFISH_PKG} && \
#     unzip -o ${GLASSFISH_PKG} && \
#     rm -f ${GLASSFISH_PKG} && \
#     yum remove -y wget unzip && \
#     echo "----- CREATE PASSWORD FILE --------------------" && \
#     echo "AS_ADMIN_PASSWORD=" > /tmp/gfpassword && \
#     echo "AS_ADMIN_NEWPASSWORD=${PASSWORD}" >> /tmp/gfpassword  && \
#     echo "----- CHANGE ADMIN PASSWORD AND SECURE ADMIN ACCESS --------------------" && \
#     asadmin --user=admin --passwordfile=/tmp/gfpassword change-admin-password --domain_name domain1 && \
#     echo "----- ENABLE DOMAIN ADMINISTRATION SERVER --------------------" && \
#     asadmin start-domain && \
#     echo "AS_ADMIN_PASSWORD=${PASSWORD}" > /tmp/gfpassword && \
#     asadmin --user=admin --passwordfile=/tmp/gfpassword enable-secure-admin && \
#     asadmin --user=admin stop-domain 

# # Ports being exposed
# EXPOSE 4848 8080 8181

# # Start asadmin console and the domain
# CMD ["asadmin", "start-domain", "-v"]

# FROM        java:8-jdk
FROM        store/oracle/serverjre:8 

ENV         JAVA_HOME         /usr/lib/jvm/java-8-openjdk-amd64
ENV         GLASSFISH_HOME    /usr/local/glassfish5
ENV         PATH              $PATH:$JAVA_HOME/bin:$GLASSFISH_HOME/bin

RUN         apt-get update && \
            apt-get install -y curl unzip zip inotify-tools && \
            rm -rf /var/lib/apt/lists/*

# RUN         curl -L -o /tmp/glassfish-4.1.zip http://download.java.net/glassfish/4.1/release/glassfish-4.1.zip && \
RUN         curl -L -o /tmp/glassfish-5.0.zip http://download.java.net/glassfish/5.0/release/glassfish-5.0.zip && \
            unzip /tmp/glassfish-5.0.zip -d /usr/local && \
            rm -f /tmp/glassfish-5.0.zip

EXPOSE      8080 4848 8181

WORKDIR     /usr/local/glassfish5

# verbose causes the process to remain in the foreground so that docker can track it
CMD         asadmin start-domain --verbos