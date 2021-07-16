FROM  payara/server-full

ENV ADMIN_USER=admin\
    ADMIN_PASSWORD=DTIdesa@2K21 

USER root

COPY mineco.p12 /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.crt /opt/payara/appserver/glassfish/domains/domain1/config
COPY mineco.key /opt/payara/appserver/glassfish/domains/domain1/config

RUN echo "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=${ADMIN_PASSWORD}" > /tmp/tmpfile && \
    echo "AS_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${PASSWORD_FILE}

WORKDIR /opt/payara/appserver/glassfish/domains/domain1/config
# WORKDIR ${JAVA_HOME}/jre/bin

RUN echo mineco | keytool -importkeystore -storepass changeit -destkeystore keystore.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate

RUN echo changeit | keytool -importcert -trustcacerts -destkeystore cacerts.jks -file mineco.crt -alias mineco_certificate | echo yes

RUN echo mineco |  keytool -importkeystore -storepass changeit -destkeystore cacerts.jks -srckeystore mineco.p12 -srcstoretype PKCS12 -alias mineco_certificate 

RUN echo changeit | keytool  -delete -alias mineco_certificate -keystore keystore.jks 

RUN echo changeit |  keytool -delete -alias mineco_certificate -keystore cacerts.jks


WORKDIR ${HOME_DIR}



RUN ${PAYARA_DIR}/bin/asadmin --user ${ADMIN_USER} --passwordfile=/tmp/tmpfile change-admin-password --domain_name=${DOMAIN_NAME} && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} start-domain ${DOMAIN_NAME} && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} enable-secure-admin && \
    for MEMORY_JVM_OPTION in $(${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} list-jvm-options | grep "Xm[sx]"); do\
        ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} delete-jvm-options $MEMORY_JVM_OPTION;\
    done && \
    # FIXME: when upgrading this container to Java 10+, this needs to be changed to '-XX:+UseContainerSupport' and '-XX:MaxRAMPercentage'
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} create-jvm-options '-XX\:+UnlockExperimentalVMOptions:-XX\:+UseCGroupMemoryLimitForHeap:-XX\:MaxRAMFraction=1' && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} set-log-attributes com.sun.enterprise.server.logging.GFFileHandler.logtoFile=false && \
    ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} stop-domain ${DOMAIN_NAME}