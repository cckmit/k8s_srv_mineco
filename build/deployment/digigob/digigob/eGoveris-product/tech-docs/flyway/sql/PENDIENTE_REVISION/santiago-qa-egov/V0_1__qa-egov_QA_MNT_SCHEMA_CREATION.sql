CREATE DATABASE  IF NOT EXISTS `qa_mnt_egov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_mnt_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: mnt_trade
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `property_configuration`
--

DROP TABLE IF EXISTS property_configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE property_configuration (
  CLAVE varchar(50) NOT NULL,
  VALOR varchar(1024) NOT NULL,
  CONFIGURACION varchar(45) NOT NULL,
  PRIMARY KEY (CLAVE,CONFIGURACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_acuerdo_comercial`
--

DROP TABLE IF EXISTS trd_acuerdo_comercial;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_acuerdo_comercial (
  ID bigint(20) NOT NULL,
  REGIME varchar(45) NOT NULL,
  NOMBRE varchar(45) NOT NULL,
  VALOR bigint(20) NOT NULL,
  ID_TIPO_ACUERDO_COM bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY FK_ACRDCOM_TPOACCOM (ID_TIPO_ACUERDO_COM),
  CONSTRAINT FK_ACRDCOM_TPOACCOM FOREIGN KEY (ID_TIPO_ACUERDO_COM) REFERENCES trd_tipo_acuerdo_comercial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_capitulo`
--

DROP TABLE IF EXISTS trd_capitulo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_capitulo (
  ID bigint(20) NOT NULL,
  CODIGO char(2) NOT NULL,
  DESCRIPCION varchar(250) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(250) DEFAULT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_capitulo2`
--

DROP TABLE IF EXISTS trd_capitulo2;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_capitulo2 (
  ID_CAPITULO bigint(20) NOT NULL,
  CODIGO char(2) NOT NULL,
  DESCRIPCION varchar(250) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(250) DEFAULT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  PRIMARY KEY (ID_CAPITULO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_caracteristica_especial`
--

DROP TABLE IF EXISTS trd_caracteristica_especial;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_caracteristica_especial (
  ID bigint(20) NOT NULL,
  CODIGO varchar(45) NOT NULL,
  DESCRIPCION varchar(200) NOT NULL,
  DESCRIPCION_ING varchar(200) NOT NULL,
  ESTADO char(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_documento_aprobacion`
--

DROP TABLE IF EXISTS trd_documento_aprobacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_documento_aprobacion (
  ID bigint(20) NOT NULL,
  ID_SSPP bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_SSPP_idx (ID_SSPP),
  CONSTRAINT FK_DOCAPR_SSPP FOREIGN KEY (ID_SSPP) REFERENCES trd_sspp (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_hscode`
--

DROP TABLE IF EXISTS trd_hscode;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_hscode (
  ID bigint(20) NOT NULL,
  ID_CAPITULO bigint(20) NOT NULL,
  ID_PARTIDA bigint(20) NOT NULL,
  ID_SUBPARTIDA bigint(20) NOT NULL,
  ID_SUBPARTIDA_NC bigint(20) NOT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  ANHO char(4) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_CAPITULO_idx (ID_CAPITULO),
  KEY ID_PARTIDA_idx (ID_PARTIDA),
  KEY ID_SUBPARTIDA_idx (ID_SUBPARTIDA),
  KEY ID_SUBPARTIDA_NC_idx (ID_SUBPARTIDA_NC),
  CONSTRAINT FK_HSDCODE_CAPITULO FOREIGN KEY (ID_CAPITULO) REFERENCES trd_capitulo (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HSDCODE_PARTIDA FOREIGN KEY (ID_PARTIDA) REFERENCES trd_partida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HSDCODE_SUBPRTD FOREIGN KEY (ID_SUBPARTIDA) REFERENCES trd_subpartida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HSDCODE_SUBPRTDNC FOREIGN KEY (ID_SUBPARTIDA_NC) REFERENCES trd_subpartida_nc (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_hscode_acuerdo`
--

DROP TABLE IF EXISTS trd_hscode_acuerdo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_hscode_acuerdo (
  ID bigint(20) NOT NULL,
  ID_HSCODDE bigint(20) NOT NULL,
  ID_ACUERDO_COMERCIAL bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY FK_HSCACDR_HSCODE (ID_HSCODDE),
  KEY FK_HSCACRD_ACRDCOM (ID_ACUERDO_COMERCIAL),
  CONSTRAINT FK_HSCACDR_HSCODE FOREIGN KEY (ID_HSCODDE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HSCACRD_ACRDCOM FOREIGN KEY (ID_ACUERDO_COMERCIAL) REFERENCES trd_acuerdo_comercial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_hscode_matriz`
--

DROP TABLE IF EXISTS trd_hscode_matriz;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_hscode_matriz (
  ID bigint(20) NOT NULL,
  ID_MATRIZ_VB bigint(20) NOT NULL,
  ID_HSCODE bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_MATRIZ_VB_idx (ID_MATRIZ_VB),
  KEY ID_HSCODE_idx (ID_HSCODE),
  CONSTRAINT FK_HSCDMTZ_HSCODE FOREIGN KEY (ID_HSCODE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HSCDMTZ_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_matriz_documento`
--

DROP TABLE IF EXISTS trd_matriz_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_matriz_documento (
  ID bigint(20) NOT NULL,
  ID_MATRIZ_VB bigint(20) NOT NULL,
  ID_DOC_APROBACION bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_MATRIZ_VB_idx (ID_MATRIZ_VB),
  KEY ID_DOC_APROBACION_idx (ID_DOC_APROBACION),
  CONSTRAINT FK_MTZDOC_DOCAPR FOREIGN KEY (ID_DOC_APROBACION) REFERENCES trd_documento_aprobacion (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_MTZDOC_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_matriz_producto`
--

DROP TABLE IF EXISTS trd_matriz_producto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_matriz_producto (
  ID bigint(20) NOT NULL,
  ID_MATRIZ_VB bigint(20) NOT NULL,
  ID_PRODUCTO bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_MATRIZ_VB_idx (ID_MATRIZ_VB),
  KEY ID_PRODUCTO_idx (ID_PRODUCTO),
  CONSTRAINT FK_MTZPROD_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_MTZPROD_PROD FOREIGN KEY (ID_PRODUCTO) REFERENCES trd_producto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_matriz_vb`
--

DROP TABLE IF EXISTS trd_matriz_vb;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_matriz_vb (
  ID bigint(20) NOT NULL,
  CODIGO varchar(10) NOT NULL,
  REGIMEN_MATRIZ_VB varchar(3) NOT NULL,
  NOMBRE varchar(45) NOT NULL,
  NOMBRE_ING varchar(45) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  DESCRIPCION_ING varchar(255) DEFAULT NULL,
  VALIDA_DESDE datetime NOT NULL,
  VALIDA_HASTA datetime NOT NULL,
  TODO_HSCODE char(1) NOT NULL,
  TODO_PAIS char(1) NOT NULL,
  TODO_CAR_ESPECIALES char(1) NOT NULL,
  CONTROL_ANTICIPADO char(1) NOT NULL,
  ESTADO char(1) NOT NULL,
  ID_USO_PREVISTO bigint(20) DEFAULT NULL,
  ID_CAR_ESPECIAL bigint(20) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY ID_USO_PREVISTO_idx (ID_USO_PREVISTO),
  KEY ID_CAR_ESPECIAL_idx (ID_CAR_ESPECIAL),
  CONSTRAINT FK_MTZVB_CARESPEC FOREIGN KEY (ID_CAR_ESPECIAL) REFERENCES trd_caracteristica_especial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_MTZVB_USOPREV FOREIGN KEY (ID_USO_PREVISTO) REFERENCES trd_uso_previsto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_pais`
--

DROP TABLE IF EXISTS trd_pais;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_pais (
  ID bigint(20) NOT NULL,
  CODIGO varchar(3) NOT NULL,
  NOMBRE varchar(45) NOT NULL,
  NOMBRE_ING varchar(45) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  DESCRIPCION_ING varchar(255) DEFAULT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_pais_matriz`
--

DROP TABLE IF EXISTS trd_pais_matriz;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_pais_matriz (
  ID bigint(20) NOT NULL,
  ID_MATRIZ_VB bigint(20) NOT NULL,
  ID_PAIS bigint(20) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_MATRIZ_VB_idx (ID_MATRIZ_VB),
  KEY ID_PAIS_idx (ID_PAIS),
  CONSTRAINT FK_MTZPAIS_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_MTZPAIS_PAIS FOREIGN KEY (ID_PAIS) REFERENCES trd_pais (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_partida`
--

DROP TABLE IF EXISTS trd_partida;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_partida (
  ID bigint(20) NOT NULL,
  ID_CAPITULO bigint(20) NOT NULL,
  CODIGO char(2) NOT NULL,
  DESCRIPCION varchar(250) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(250) DEFAULT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  PRIMARY KEY (ID),
  KEY CAPITULO_idx (ID_CAPITULO),
  CONSTRAINT FK_PARTIDA_CAPITULO FOREIGN KEY (ID_CAPITULO) REFERENCES trd_capitulo (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_producto`
--

DROP TABLE IF EXISTS trd_producto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_producto (
  ID bigint(20) NOT NULL,
  NOMBRE varchar(250) NOT NULL,
  ID_HSCODE bigint(20) NOT NULL,
  CODIGO char(11) NOT NULL,
  NOMBRE_COMUN varchar(250) NOT NULL,
  NOMBRE_COMUN_ING varchar(250) DEFAULT NULL,
  DESCRIPCION varchar(250) DEFAULT NULL,
  DESCRIPCION_ING varchar(250) DEFAULT NULL,
  NOMBRE_CIENTIFICO varchar(250) NOT NULL,
  NOMBRE_NEGOCIO varchar(250) NOT NULL,
  NOMBRE_NEGOCIO_ING varchar(250) DEFAULT NULL,
  DESTINO_ADUANA varchar(5) NOT NULL,
  VERSION varchar(30) NOT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  ID_CANASTA varchar(30) NOT NULL,
  PRIMARY KEY (ID),
  KEY ID_HSCODE_idx (ID_HSCODE),
  CONSTRAINT FK_PRODUCTO_HSCODE FOREIGN KEY (ID_HSCODE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_producto_atributo`
--

DROP TABLE IF EXISTS trd_producto_atributo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_producto_atributo (
  ID bigint(20) NOT NULL,
  ID_PRODUCTO bigint(20) NOT NULL,
  SECUENCIAL bigint(20) NOT NULL,
  NOMBRE varchar(250) NOT NULL,
  NOMBRE_ING varchar(250) DEFAULT NULL,
  VALOR varchar(250) NOT NULL,
  TIPO_DATO varchar(6) NOT NULL,
  TAMANO_DATO int(4) NOT NULL,
  ES_FIJO char(1) NOT NULL,
  ES_OBLIGATORIO char(1) NOT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A' COMMENT 'A:Activo',
  PRIMARY KEY (ID),
  KEY ID_PRODUCTO_idx (ID_PRODUCTO),
  CONSTRAINT FK_ATRPROD_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES trd_producto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_sspp`
--

DROP TABLE IF EXISTS trd_sspp;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_sspp (
  ID bigint(20) NOT NULL,
  CODIGO varchar(10) NOT NULL,
  NOMBRE varchar(45) NOT NULL,
  NOMBRE_ING varchar(45) DEFAULT NULL,
  ESTADO char(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_subpartida`
--

DROP TABLE IF EXISTS trd_subpartida;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_subpartida (
  ID bigint(20) NOT NULL,
  ID_PARTIDA bigint(20) NOT NULL,
  CODIGO char(2) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(255) DEFAULT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (ID),
  KEY FK_SUBPRTD_PARTIDA (ID_PARTIDA),
  CONSTRAINT FK_SUBPRTD_PARTIDA FOREIGN KEY (ID_PARTIDA) REFERENCES trd_partida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_subpartida_nc`
--

DROP TABLE IF EXISTS trd_subpartida_nc;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_subpartida_nc (
  ID bigint(20) NOT NULL,
  CODIGO char(2) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(255) DEFAULT NULL,
  ID_SUBPARTIDA bigint(20) NOT NULL,
  ESTADO char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (ID),
  KEY FK_SUBPRTDNC_SUBPRTD (ID_SUBPARTIDA),
  CONSTRAINT FK_SUBPRTDNC_SUBPRTD FOREIGN KEY (ID_SUBPARTIDA) REFERENCES trd_subpartida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_tipo_acuerdo_comercial`
--

DROP TABLE IF EXISTS trd_tipo_acuerdo_comercial;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_tipo_acuerdo_comercial (
  ID bigint(20) NOT NULL,
  CODIGO varchar(30) NOT NULL,
  DESCRIPCION varchar(255) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trd_uso_previsto`
--

DROP TABLE IF EXISTS trd_uso_previsto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trd_uso_previsto (
  ID bigint(20) NOT NULL,
  CODIGO varchar(45) NOT NULL,
  DESCRIPCION varchar(255) NOT NULL,
  DESCRIPCION_ING varchar(255) NOT NULL,
  ESTADO char(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
