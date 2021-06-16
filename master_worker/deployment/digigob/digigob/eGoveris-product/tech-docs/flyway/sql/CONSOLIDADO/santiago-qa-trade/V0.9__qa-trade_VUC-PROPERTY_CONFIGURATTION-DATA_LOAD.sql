-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: qa_vuc_trade
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

DELETE FROM qa_vuc_trade.property_configuration;
INSERT INTO qa_vuc_trade.property_configuration (CLAVE, VALOR, CONFIGURACION) VALUES ('app.deo.url','https://qa.trade.com/deo-web','SISTEMA'),('app.ee.url','https://qa.trade.com/te-web','SISTEMA'),('app.ffdd.url','http://desa.egoveris.com:10000/ffdd-web','SISTEMA'),('app.vuc.url','https://qa.trade.com/vuc-front-web','SISTEMA'),('cambioMisDatos.descAdicional','- ','TAD'),('cambioMisDatos.motivoPase','Actualizacion de informacion propia','TAD'),('cambioMisDatos.trata','MGOB0123A','TAD'),('cargoGEDO',' ','TAD'),('codigoReparticionGEDO','codigoReparticionGEDO','TAD'),('cronExpression','0 0 1 1/1 * ? *','TAD'),('cronExpressionReintentos','0 0 1 1/1 * ? 2099','TAD'),('dias.altaDC','1','TAD'),('diasBorradoArchivos','1','TAD'),('dynform.url','https://qa.trade.com/ffdd-web','SISTEMA'),('envioCorreoActivo','false','TAD'),('id.tramites.escribanos','1,177,300,305','TAD'),('ley104.motivoPase','Motivo pase a funcionario público','TAD'),('limiteDiasExpedienteReintentos','0','TAD'),('mailsReintentos','guillermo.gefaell.valcarce@everis.com','TAD'),('mensajeEmbebido','Documento Embebido TAD','TAD'),('password.citas','ZDNmNGg2ajc=','TAD'),('repository.connectionTimeout','15000','SISTEMA'),('repository.defaultPassword','admin','SISTEMA'),('repository.defaultUsername','admin','SISTEMA'),('repository.documentRoot','/guarda-documental','SISTEMA'),('repository.hostname','egov3p1.egoveris.com','SISTEMA'),('repository.port','80','SISTEMA'),('RPA.aniosMatricula','2010','TAD'),('rpaadm.desccarat','ADMINISTRADOR INSCRIPTO MATRICULA N° ','TAD'),('rpacon.desccarat','INFORMACIÓN CORRESPONDIENTE A CADA CONSORCIO ADMINISTRADO','TAD'),('rutaTemporales','temporalDocuments','TAD'),('tad.mailMesaAyuda','darkkonum@gmail.com','TAD'),('tipoDocumentoApoderado','GEAP','TAD'),('tituloDocumentoCaratulaVariable','Datos Adicionales para la Caratula','TAD'),('url.tad','https://desa.agip.gob.ar/claveciudad/','TAD'),('user.citas','tad','TAD'),('wsdl.agip','https://desa.agip.gob.ar/claveciudad/claveCiudadWS','TAD'),('wsdl.citas','http://localhost:8880/apps/api-citas-hml/ws.citas.php','TAD'),('wsdl.DC','http://localhost:8088/mock','TAD'),('wsdl.DC.decodingXml','UTF-8','TAD'),('wsdl.DC.encodingXml','ISO-8859-1','TAD'),('wsdl.DC.externalURL','http://localhost/wsDGDyPC/','TAD'),('wsdl.escribanos','http://testing.colegio-escribanos.org.ar:1010/BoraWS/ValidadorMatricula','TAD'),('wsdl.escribanos','https://servicios.colegio-escribanos.org.ar:8444/BoraWS/ValidadorMatricula','TAD.MM_yaNo');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-21 13:01:42
