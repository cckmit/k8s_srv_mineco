FROM  payara/server-full

# ENV ADMIN_USER=admin\
#     ADMIN_PASSWORD=admin

USER root

RUN mkdir -p /home/diaco/Documentos/FILESERVER/

RUN apt-get update && apt-get -y install wget libreoffice-base

RUN wget https://downloadarchive.documentfoundation.org/libreoffice/old/6.1.0.1/deb/x86_64/LibreOffice_6.1.0.1_Linux_x86-64_deb.tar.gz

RUN tar -xvf LibreOffice_6.1.0.1_Linux_x86-64_deb.tar.gz

WORKDIR /opt/payara/LibreOffice_6.1.0.1_Linux_x86-64_deb/DEBS

RUN dpkg -i *.deb

RUN apt-get install -y libglu1-mesa libxinerama1

WORKDIR ${HOME_DIR}




# RUN asadmin stop-domain domain1


# COPY mineco.p12 /opt/payara/appserver/glassfish/domains/domain1/config
# COPY mineco.crt /opt/payara/appserver/glassfish/domains/domain1/config
# COPY mineco.key /opt/payara/appserver/glassfish/domains/domain1/config

# RUN echo "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=${ADMIN_PASSWORD}" > /tmp/tmpfile && \
#     echo "AS_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${PASSWORD_FILE}

# WORKDIR /opt/payara/appserver/glassfish/domains/domain1/config
# # WORKDIR ${JAVA_HOME}/jre/bin

# RUN echo mineco | keytool -importkeystore -storepass changeit -destkeystore keystore.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate

# RUN echo changeit | keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias mineco_certificate | echo yes

# RUN echo mineco |  keytool -importkeystore -storepass changeit -destkeystore cacerts.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate 

# RUN echo changeit | keytool  -delete -alias mineco_certificate -keystore keystore.jks 

# RUN echo changeit |  keytool -delete -alias mineco_certificate -keystore cacerts.jks


# WORKDIR ${HOME_DIR}/appserver/bin

# RUN asadmin start-domain domain1 

# WORKDIR ${HOME_DIR}