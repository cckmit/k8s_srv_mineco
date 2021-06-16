-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: qa_mnt_trade
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `property_configuration`
--

INSERT INTO qa_mnt_trade.property_configuration (CLAVE, VALOR, CONFIGURACION) VALUES ('app.url','https://qa.trade.com/mantenedores-web/','MNT'),('cas.url','https://casqa.trade.com','SISTEMA'),('ldap.admin.password','password','SISTEMA'),('ldap.admin.password.ad','','SISTEMA'),('ldap.admin.user','cn=Manager,DC=egoveris,DC=com','SISTEMA'),('ldap.admin.user.ad','','SISTEMA'),('ldap.base','DC=egoveris,DC=com','SISTEMA'),('ldap.base.ad','','SISTEMA'),('ldap.entorno','SADE','SISTEMA'),('ldap.ou','funcionarios','SISTEMA'),('ldap.ou.base','ou=funcionarios','SISTEMA'),('ldap.url','ldap://egov3p1.trade.com:389','SISTEMA'),('ldap.url.ad','','SISTEMA'),('ldap.user.account.control.ad','cn=Manager,DC=egoveris,DC=com','SISTEMA'),('security.usuarios.refrescoMinutos','5','SISTEMA'),('solr.maxTotalConnections','100','SISTEMA'),('solr.track.url','http://egov3p1.egoveris.com:8092/bweb-solr/core-track','SISTEMA'),('solr.usr.url','http://egov3p1.egoveris.com:8092/bweb-solr/coreUSUARIOS','SISTEMA');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-27 17:58:20
