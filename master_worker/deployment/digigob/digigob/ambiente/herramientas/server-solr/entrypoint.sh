#!/bin/bash


sed -i "\%master.core.url%c\master.core.url=$SOLR_SERVER:$SOLR_PORT/bweb-solr" /solr-home/coreUSUARIOS/conf/solrcore.properties
sed -i "\%core0.database.url%c\core0.database.url=$DB_JDBC_DRIVER_CONECTION//$DB_SERVER_NAME:$DB_PORT/$DB_NAME$DB_SCHEMA_EU_SUFIX" /solr-home/coreUSUARIOS/conf/solrcore.properties
sed -i "\%core0.database.user%c\core0.database.user=$DB_USER" /solr-home/coreUSUARIOS/conf/solrcore.properties
sed -i "\%core0.database.password%c\core0.database.password=$DB_PASSWORD" /solr-home/coreUSUARIOS/conf/solrcore.properties

/usr/local/tomcat/bin/catalina.sh run