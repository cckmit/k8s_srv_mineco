CREATE DATABASE  IF NOT EXISTS `qa_mnt_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_mnt_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: mnt_egov_docker
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
  KEY FK_ACRDCOM_TPOACCOM (ID_TIPO_ACUERDO_COM)
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
  KEY ID_SSPP_idx (ID_SSPP)
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
  KEY ID_SUBPARTIDA_NC_idx (ID_SUBPARTIDA_NC)
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
  KEY FK_HSCACRD_ACRDCOM (ID_ACUERDO_COMERCIAL)
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
  KEY ID_HSCODE_idx (ID_HSCODE)
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
  KEY ID_DOC_APROBACION_idx (ID_DOC_APROBACION)
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
  KEY ID_PRODUCTO_idx (ID_PRODUCTO)
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
  KEY ID_CAR_ESPECIAL_idx (ID_CAR_ESPECIAL)
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
  KEY ID_PAIS_idx (ID_PAIS)
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
  KEY CAPITULO_idx (ID_CAPITULO)
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
  KEY ID_HSCODE_idx (ID_HSCODE)
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
  KEY ID_PRODUCTO_idx (ID_PRODUCTO)
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
  KEY FK_SUBPRTD_PARTIDA (ID_PARTIDA)
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
  KEY FK_SUBPRTDNC_SUBPRTD (ID_SUBPARTIDA)
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

-- Dump completed on 2017-08-30  9:58:49
CREATE DATABASE  IF NOT EXISTS `qa_edt_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_edt_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_edt_egov_docker
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
-- Table structure for table `adminsade_permisos`
--

DROP TABLE IF EXISTS adminsade_permisos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE adminsade_permisos (
  ID decimal(10,0) NOT NULL,
  PERMISO varchar(255) NOT NULL,
  ROL varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  SISTEMA varchar(45) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_cargo_rol`
--

DROP TABLE IF EXISTS edt_cargo_rol;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_cargo_rol (
  EDTRL_ID decimal(10,0) DEFAULT NULL,
  CARGO_ID decimal(10,0) DEFAULT NULL,
  KEY EDT_CARGO_ROL_R01 (CARGO_ID),
  KEY EDT_CARGO_ROL_R02 (EDTRL_ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_cargos`
--

DROP TABLE IF EXISTS edt_cargos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_cargos (
  ID int(10) NOT NULL,
  CARGO varchar(255) DEFAULT NULL,
  RESTRINGIDO tinyint(1) DEFAULT NULL,
  USUARIO_CREACION varchar(50) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  VIGENTE int(11) DEFAULT NULL,
  USUARIO_MODIFICACION varchar(50) DEFAULT NULL,
  ID_REPARTICION decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_cargos_hist`
--

DROP TABLE IF EXISTS edt_cargos_hist;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_cargos_hist (
  ID int(10) DEFAULT NULL,
  REVISION int(10) DEFAULT NULL,
  CARGO varchar(255) DEFAULT NULL,
  VISIBLE int(11) DEFAULT NULL,
  USUARIO_CREACION varchar(50) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  VIGENTE int(11) DEFAULT NULL,
  USUARIO_MODIFICACION varchar(50) DEFAULT NULL,
  TIPO_REVISION int(11) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_datos_usuario`
--

DROP TABLE IF EXISTS edt_datos_usuario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_datos_usuario (
  ID_DATO_USUARIO int(10) NOT NULL,
  MAIL varchar(255) DEFAULT NULL,
  OCUPACION varchar(255) DEFAULT NULL,
  USER_ varchar(255) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  USER_SUPERIOR varchar(255) DEFAULT NULL,
  MAIL_SUPERIOR varchar(255) DEFAULT NULL,
  ID_SECTOR_INTERNO varchar(255) DEFAULT NULL,
  CODIGO_SECTOR_INTERNO varchar(255) DEFAULT NULL,
  FECHA_CADUCIDAD_SECTOR_INTERNO datetime DEFAULT NULL,
  ES_SECRETARIO varchar(255) DEFAULT NULL,
  SECRETARIO varchar(255) DEFAULT NULL,
  APELLIDO_NOMBRE varchar(255) DEFAULT NULL,
  ACEPTACION_TYC int(11) DEFAULT '0',
  NUMERO_CUIT varchar(11) DEFAULT NULL,
  EXTERNALIZAR_FIRMA_EN_GEDO int(10) DEFAULT NULL,
  EXTERNALIZAR_FIRMA_EN_SIGA int(10) DEFAULT NULL,
  EXTERNALIZAR_FIRMA_EN_CCOO int(10) DEFAULT NULL,
  EXTERNALIZAR_FIRMA_EN_LOYS int(10) DEFAULT NULL,
  USUARIO_ASESOR varchar(255) DEFAULT NULL,
  NOTIFICAR_SOLICITUD_PF varchar(1) DEFAULT NULL,
  CARGO int(10) DEFAULT NULL,
  CAMBIAR_MESA varchar(1) DEFAULT NULL,
  PRIMARY KEY (ID_DATO_USUARIO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_periodo_licencia`
--

DROP TABLE IF EXISTS edt_periodo_licencia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_periodo_licencia (
  ID_PERIODO_LICENCIA int(10) NOT NULL,
  FECHA_HORA_DESDE datetime DEFAULT NULL,
  FECHA_HORA_HASTA datetime DEFAULT NULL,
  APODERADO varchar(255) DEFAULT NULL,
  CONDICION_PERIODO varchar(255) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  FECHA_CANCELACION datetime DEFAULT NULL,
  PRIMARY KEY (ID_PERIODO_LICENCIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_rol`
--

DROP TABLE IF EXISTS edt_rol;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_rol (
  EDTRL_ID int(10) NOT NULL,
  EDTRL_ROL varchar(255) DEFAULT NULL,
  EDTRL_VISIBLE int(11) DEFAULT NULL,
  EDTRL_USUARIO_CREACION varchar(50) DEFAULT NULL,
  EDTRL_FECHA_CREACION datetime DEFAULT NULL,
  EDTRL_VIGENTE int(11) DEFAULT NULL,
  EDTRL_USUARIO_MODIFICACION varchar(50) DEFAULT NULL,
  EDTRL_FECHA_MODIFICACION datetime DEFAULT NULL,
  PRIMARY KEY (EDTRL_ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_rol_permisos`
--

DROP TABLE IF EXISTS edt_rol_permisos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_rol_permisos (
  EDTRL_ID decimal(10,0) NOT NULL,
  SD_PERMISO_ID decimal(10,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_actuacion`
--

DROP TABLE IF EXISTS edt_sade_actuacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_actuacion (
  ID_ACTUACION int(10) NOT NULL,
  CODIGO_ACTUACION varchar(5) NOT NULL,
  NOMBRE_ACTUACION varchar(30) NOT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  INICIA_ACTUACION varchar(1) DEFAULT NULL,
  JERARQUIA int(11) NOT NULL,
  INCORPORADO varchar(1) DEFAULT NULL,
  AGREGADO varchar(1) DEFAULT NULL,
  ANULADO varchar(1) DEFAULT NULL,
  DESGLOSADO varchar(1) DEFAULT NULL,
  VERSION decimal(10,0) NOT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  ES_DOCUMENTO int(11) DEFAULT NULL,
  DESHABILITADO_PAPEL varchar(1) NOT NULL,
  PRIMARY KEY (ID_ACTUACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_actuacion_audi`
--

DROP TABLE IF EXISTS edt_sade_actuacion_audi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_actuacion_audi (
  ID_ACTUACION_AUDI int(11) NOT NULL,
  ID_ACTUACION decimal(10,0) NOT NULL,
  CODIGO_ACTUACION varchar(5) NOT NULL,
  NOMBRE_ACTUACION varchar(30) NOT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  INICIA_ACTUACION varchar(1) DEFAULT NULL,
  JERARQUIA int(11) NOT NULL,
  INCORPORADO varchar(1) DEFAULT NULL,
  AGREGADO varchar(1) DEFAULT NULL,
  ANULADO varchar(1) DEFAULT NULL,
  DESGLOSADO varchar(1) DEFAULT NULL,
  VERSION decimal(10,0) NOT NULL,
  FECHA_A datetime NOT NULL,
  FUNCION_A varchar(1) NOT NULL,
  USUARIO_A varchar(20) NOT NULL,
  ID_SECTOR_INTERNO_A decimal(10,0) NOT NULL,
  PRIMARY KEY (ID_ACTUACION_AUDI)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_admin_reparticion`
--

DROP TABLE IF EXISTS edt_sade_admin_reparticion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_admin_reparticion (
  ID_ADMIN_REPARTICION decimal(10,0) NOT NULL,
  ID_REPARTICION decimal(10,0) NOT NULL,
  NOMBRE_USUARIO varchar(30) NOT NULL,
  PRIMARY KEY (ID_ADMIN_REPARTICION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_admin_reparticion_hist`
--

DROP TABLE IF EXISTS edt_sade_admin_reparticion_hist;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_admin_reparticion_hist (
  ID_ADMIN_REPARTICION decimal(10,0) NOT NULL,
  ID_REPARTICION decimal(10,0) NOT NULL,
  NOMBRE_USUARIO varchar(30) NOT NULL,
  REVISION decimal(10,0) NOT NULL,
  TIPO_REVISION int(11) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_estructura`
--

DROP TABLE IF EXISTS edt_sade_estructura;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_estructura (
  ID_ESTRUCTURA int(10) NOT NULL,
  CODIGO_ESTRUCTURA decimal(10,0) NOT NULL,
  NOMBRE_ESTRUCTURA varchar(120) NOT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  GENERA_ALS varchar(1) DEFAULT NULL,
  VERSION decimal(10,0) NOT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  ESTRUCTURA_PODER_EJECUTIVO varchar(1) DEFAULT NULL,
  PRIMARY KEY (ID_ESTRUCTURA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_jurisdiccion`
--

DROP TABLE IF EXISTS edt_sade_jurisdiccion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_jurisdiccion (
  ID int(10) NOT NULL,
  DESCRIPCION varchar(500) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_reparticion`
--

DROP TABLE IF EXISTS edt_sade_reparticion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_reparticion (
  ID_REPARTICION int(10) NOT NULL,
  CODIGO_REPARTICION varchar(100) NOT NULL,
  CODIGO_REPAR_INTER varchar(100) DEFAULT NULL,
  NOMBRE_REPARTICION varchar(120) NOT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  NUMERO varchar(10) NOT NULL,
  PISO varchar(10) DEFAULT NULL,
  OFICINA varchar(10) DEFAULT NULL,
  TELEFONO varchar(30) DEFAULT NULL,
  FAX varchar(30) DEFAULT NULL,
  EMAIL varchar(30) DEFAULT NULL,
  ID_ESTRUCTURA int(10) NOT NULL,
  EN_RED varchar(1) DEFAULT NULL,
  SECTOR_MESA varchar(1) DEFAULT NULL,
  VERSION int(10) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  ES_DGTAL int(11) NOT NULL,
  REP_PADRE int(10) DEFAULT NULL,
  COD_DGTAL varchar(4000) DEFAULT NULL,
  ID_JURISDICCION int(11) DEFAULT NULL,
  MINISTERIO int(10) DEFAULT NULL,
  ADMINISTRADOR_PRESUPUESTO int(10) DEFAULT NULL,
  calle varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID_REPARTICION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_reparticion_seleccionada`
--

DROP TABLE IF EXISTS edt_sade_reparticion_seleccionada;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_reparticion_seleccionada (
  ID_REPARTICION_SELECCIONADA int(10) NOT NULL,
  ID_REPARTICION int(10) NOT NULL,
  NOMBRE_USUARIO varchar(30) NOT NULL,
  ID_SECTOR_INTERNO int(10) NOT NULL,
  PRIMARY KEY (ID_REPARTICION_SELECCIONADA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_sector_interno`
--

DROP TABLE IF EXISTS edt_sade_sector_interno;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_sector_interno (
  ID_SECTOR_INTERNO int(10) NOT NULL,
  CODIGO_SECTOR_INTERNO varchar(100) NOT NULL,
  NOMBRE_SECTOR_INTERNO varchar(120) NOT NULL,
  CALLE varchar(255) DEFAULT NULL,
  NUMERO varchar(10) DEFAULT NULL,
  PISO varchar(40) DEFAULT NULL,
  OFICINA varchar(255) DEFAULT NULL,
  TELEFONO varchar(40) DEFAULT NULL,
  FAX varchar(40) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  EN_RED varchar(45) DEFAULT NULL,
  SECTOR_MESA varchar(1) DEFAULT NULL,
  VERSION int(10) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  CODIGO_REPARTICION int(10) NOT NULL,
  SECTOR_INTERNO_AGRUPACION_INDE int(10) DEFAULT NULL,
  ID_AGRUPACION_SECTOR_MESA int(10) DEFAULT NULL,
  MESA_VIRTUAL int(11) DEFAULT NULL,
  ES_ARCHIVO int(11) DEFAULT NULL,
  USUARIO_ASIGNADOR varchar(30) DEFAULT NULL,
  PRIMARY KEY (ID_SECTOR_INTERNO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_sector_usuario`
--

DROP TABLE IF EXISTS edt_sade_sector_usuario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_sector_usuario (
  ID_SECTOR_USUARIO int(10) NOT NULL,
  ID_SECTOR_INTERNO int(10) NOT NULL,
  NOMBRE_USUARIO varchar(255) NOT NULL,
  PROCESO varchar(40) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  VERSION int(10) DEFAULT NULL,
  FECHA_CREACION datetime NOT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  CARGO_ID int(10) DEFAULT NULL,
  PRIMARY KEY (ID_SECTOR_USUARIO),
  KEY SECTORES_ACTIVOS_USUARIOS (NOMBRE_USUARIO,ESTADO_REGISTRO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_uai`
--

DROP TABLE IF EXISTS edt_sade_uai;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_uai (
  ID_UAI int(10) NOT NULL,
  CODIGO varchar(9) NOT NULL,
  DESCRIPCION varchar(70) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  USUARIO_CREACION varchar(30) NOT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) NOT NULL,
  VERSION decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID_UAI)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_sade_usr_repa_habilitada`
--

DROP TABLE IF EXISTS edt_sade_usr_repa_habilitada;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_sade_usr_repa_habilitada (
  ID_USR_REPA_HABILITADA int(10) NOT NULL,
  ID_REPARTICION int(10) DEFAULT NULL,
  NOMBRE_USUARIO varchar(255) DEFAULT NULL,
  ID_SECTOR_INTERNO int(10) DEFAULT NULL,
  CARGO_ID int(10) DEFAULT NULL,
  PRIMARY KEY (ID_USR_REPA_HABILITADA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edt_usuario_cargo`
--

DROP TABLE IF EXISTS edt_usuario_cargo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE edt_usuario_cargo (
  CARGO_ID decimal(10,0) DEFAULT NULL,
  USUARIO varchar(20) DEFAULT NULL,
  KEY EDT_USUARIO_CARGO_R01 (CARGO_ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_alertas_avisos`
--

DROP TABLE IF EXISTS eu_alertas_avisos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_alertas_avisos (
  ID_ALERTA_AVISO decimal(10,0) NOT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  NUMERO_GEDO varchar(255) DEFAULT NULL,
  REDIRIGIBLE int(11) DEFAULT NULL,
  REFERENCIA varchar(255) DEFAULT NULL,
  NOMBRE_USUARIO varchar(255) DEFAULT NULL,
  FK_ID_MODULO decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_aplicacion`
--

DROP TABLE IF EXISTS eu_aplicacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_aplicacion (
  ID int(10) NOT NULL,
  NOMBRE varchar(255) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  URLAPLICACION varchar(255) NOT NULL,
  URLAPLICACIONVALIDACION varchar(255) NOT NULL,
  URLAPLICACIONINBOX varchar(255) DEFAULT NULL,
  URLAPLICACIONINBOXSUPERVISADO varchar(255) DEFAULT NULL,
  URLAPLICACIONBUZON varchar(255) DEFAULT NULL,
  VISIBLEMISTAREAS int(11) NOT NULL,
  VISIBLEMISSISTEMAS int(11) NOT NULL,
  VISIBLEMISSUPERVISADOS int(11) NOT NULL,
  VISIBLEBUZONGRUPAL int(11) NOT NULL,
  VISIBLE int(11) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_categoria`
--

DROP TABLE IF EXISTS eu_categoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_categoria (
  ID decimal(10,0) NOT NULL,
  CATEGORIA varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_departamento`
--

DROP TABLE IF EXISTS eu_departamento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_departamento (
  NOMBRE varchar(255) NOT NULL,
  ID double NOT NULL,
  ID_PROVINCIA double NOT NULL,
  ORDEN double DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_estado_novedad`
--

DROP TABLE IF EXISTS eu_estado_novedad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_estado_novedad (
  ID double NOT NULL,
  ESTADO varchar(50) NOT NULL,
  UNIQUE KEY EU_ESTADO_NOVEDAD_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_feriados`
--

DROP TABLE IF EXISTS eu_feriados;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_feriados (
  ID int(10) NOT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  FECHA datetime DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_feriados_audi`
--

DROP TABLE IF EXISTS eu_feriados_audi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_feriados_audi (
  ID int(10) NOT NULL,
  IDFERIADO decimal(10,0) DEFAULT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  FECHAFERIADO datetime DEFAULT NULL,
  FECHAAUDITORIA datetime DEFAULT NULL,
  OPERACION varchar(255) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_grupo_permisos`
--

DROP TABLE IF EXISTS eu_grupo_permisos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_grupo_permisos (
  ID decimal(10,0) NOT NULL,
  FK_ADMINSADE_PERMISOS decimal(10,0) NOT NULL,
  GRUPO_USUARIO varchar(30) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_localidad`
--

DROP TABLE IF EXISTS eu_localidad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_localidad (
  ID double DEFAULT NULL,
  NOMBRE varchar(255) DEFAULT NULL,
  ID_PROVINCIA double DEFAULT NULL,
  ID_DEPARTAMENTO double DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_novedad`
--

DROP TABLE IF EXISTS eu_novedad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_novedad (
  ID int(10) NOT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  FECHA_FIN datetime DEFAULT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  NOVEDAD varchar(550) NOT NULL,
  USUARIO varchar(255) NOT NULL,
  CATEGORIA_ID decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_novedad_aplicacion`
--

DROP TABLE IF EXISTS eu_novedad_aplicacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_novedad_aplicacion (
  NOVEDAD_ID decimal(10,0) DEFAULT NULL,
  APLICACION_ID decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_novedad_hist`
--

DROP TABLE IF EXISTS eu_novedad_hist;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_novedad_hist (
  ID int(10) NOT NULL,
  REVISION decimal(10,0) NOT NULL,
  TIPO_REVISION int(11) DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  FECHA_FIN datetime DEFAULT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  NOVEDAD varchar(550) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  APLICACIONES varchar(255) DEFAULT NULL,
  CATEGORIA_ID decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_provincia`
--

DROP TABLE IF EXISTS eu_provincia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_provincia (
  NOMBRE varchar(255) NOT NULL,
  ID double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_usuario_buzongrupal`
--

DROP TABLE IF EXISTS eu_usuario_buzongrupal;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_usuario_buzongrupal (
  ID int(10) NOT NULL,
  APLICACIONID decimal(10,0) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_usuario_frecuencias`
--

DROP TABLE IF EXISTS eu_usuario_frecuencias;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_usuario_frecuencias (
  ID int(10) NOT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  FRECUENCIAMAYOR decimal(10,0) NOT NULL,
  FRECUENCIAMEDIA decimal(10,0) NOT NULL,
  FRECUENCIAMENOR decimal(10,0) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_usuario_missistemas`
--

DROP TABLE IF EXISTS eu_usuario_missistemas;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_usuario_missistemas (
  ID int(10) NOT NULL,
  APLICACIONID decimal(10,0) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_usuario_missupervisados`
--

DROP TABLE IF EXISTS eu_usuario_missupervisados;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_usuario_missupervisados (
  ID int(10) NOT NULL,
  APLICACIONID decimal(10,0) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eu_usuario_mistareas`
--

DROP TABLE IF EXISTS eu_usuario_mistareas;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eu_usuario_mistareas (
  ID int(10) NOT NULL,
  APLICACIONID decimal(10,0) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mig_dato_consulta`
--

DROP TABLE IF EXISTS mig_dato_consulta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mig_dato_consulta (
  TOKEN varchar(50) NOT NULL,
  CODIGO_USUARIO varchar(50) DEFAULT NULL,
  CODIGO_SECTOR varchar(50) DEFAULT NULL,
  CODIGO_REPARTICION varchar(50) NOT NULL,
  DISCRIMINATOR varchar(20) NOT NULL,
  ID double NOT NULL,
  CODIGO_SECTOR_DESTINO varchar(50) DEFAULT NULL,
  CODIGO_SECTOR_DESTINO_HOLA varchar(50) DEFAULT NULL,
  UNIQUE KEY MIG_DATO_CONSULTA_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mig_logueo_error`
--

DROP TABLE IF EXISTS mig_logueo_error;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mig_logueo_error (
  TOKEN varchar(255) NOT NULL,
  MODULO varchar(15) NOT NULL,
  TIPO_ERROR_ID int(11) NOT NULL,
  FECHA datetime NOT NULL,
  DESCRIPCION varchar(4000) NOT NULL,
  MENSAJE varchar(4000) NOT NULL,
  UNIQUE KEY MIG_LOGUEO_ERROR_PK (TOKEN,MODULO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mig_tarea`
--

DROP TABLE IF EXISTS mig_tarea;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mig_tarea (
  TOKEN varchar(50) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  TIPO_MIGRACION varchar(50) NOT NULL,
  USUARIO varchar(100) DEFAULT NULL,
  DESTINO_SECTOR varchar(50) DEFAULT NULL,
  DESTINO_REPARTICION varchar(50) DEFAULT NULL,
  ORIGEN_SECTOR varchar(50) DEFAULT NULL,
  ORIGEN_REPARTICION varchar(50) DEFAULT NULL,
  ESTADO varchar(50) NOT NULL,
  USUARIO_SOLICITANTE varchar(100) NOT NULL,
  MODULO varchar(50) NOT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  FECHA_ULTIMA_MODIFICACION datetime DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mig_tarea_hist`
--

DROP TABLE IF EXISTS mig_tarea_hist;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mig_tarea_hist (
  REVISION decimal(10,0) NOT NULL,
  TIPO_REVISION int(11) DEFAULT NULL,
  TOKEN varchar(50) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  TIPO_MIGRACION varchar(50) NOT NULL,
  USUARIO varchar(100) DEFAULT NULL,
  DESTINO_SECTOR varchar(50) DEFAULT NULL,
  DESTINO_REPARTICION varchar(50) DEFAULT NULL,
  ORIGEN_SECTOR varchar(50) DEFAULT NULL,
  ORIGEN_REPARTICION varchar(50) DEFAULT NULL,
  ESTADO varchar(50) NOT NULL,
  USUARIO_SOLICITANTE varchar(100) NOT NULL,
  MODULO varchar(50) NOT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  FECHA_ULTIMA_MODIFICACION datetime DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mig_tipo_error`
--

DROP TABLE IF EXISTS mig_tipo_error;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mig_tipo_error (
  ID int(11) NOT NULL,
  DESCRIPCION varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_configuration`
--

DROP TABLE IF EXISTS property_configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE property_configuration (
  CLAVE varchar(100) NOT NULL,
  VALOR varchar(400) NOT NULL,
  CONFIGURACION varchar(100) NOT NULL,
  PRIMARY KEY (CLAVE,CONFIGURACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `revinfo`
--

DROP TABLE IF EXISTS revinfo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE revinfo (
  REV decimal(10,0) NOT NULL,
  REVTSTMP decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sade_administrador_sistema`
--

DROP TABLE IF EXISTS sade_administrador_sistema;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE sade_administrador_sistema (
  ID decimal(10,0) NOT NULL,
  NOMBRE_USUARIO varchar(255) DEFAULT NULL,
  SISTEMA varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sade_app_alive_logger`
--

DROP TABLE IF EXISTS sade_app_alive_logger;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE sade_app_alive_logger (
  ID int(20) NOT NULL,
  `HOST` varchar(4000) NOT NULL,
  START_TIME datetime NOT NULL,
  CHECK_TIME datetime DEFAULT NULL,
  SHUTDOWN_TIME datetime DEFAULT NULL,
  IPADDR varchar(4000) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY INDICE_HOSTS (`HOST`(333))
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_busqueda_auditoria`
--

DROP TABLE IF EXISTS tad_busqueda_auditoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_busqueda_auditoria (
  ID_BUSQUEDA_AUDITORIA int(10) NOT NULL,
  CODIGO_EXPEDIENTE varchar(99) DEFAULT NULL,
  DURACION_CONSULTA decimal(10,0) DEFAULT NULL,
  SISTEMA_IMPACTADO varchar(45) DEFAULT NULL,
  FECHA_CONSULTA datetime DEFAULT NULL,
  IP_CONSULTA varchar(45) DEFAULT NULL,
  APELLIDO_INTERESADO varchar(99) DEFAULT NULL,
  NOMBRE_INTERESADO varchar(99) DEFAULT NULL,
  DNI_INTERESADO varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID_BUSQUEDA_AUDITORIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tr_facturas`
--

DROP TABLE IF EXISTS tr_facturas;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tr_facturas (
  ID varchar(32) DEFAULT NULL,
  VERSION decimal(10,0) DEFAULT NULL,
  DESCRIPCIONFACTURA varchar(255) DEFAULT NULL,
  NUMIDENTBENEFICIARIO varchar(20) DEFAULT NULL,
  DESCRIPCIONBENEFICIARIO varchar(255) DEFAULT NULL,
  IMPORTE double DEFAULT NULL,
  ID_CONTRATACION varchar(32) DEFAULT NULL,
  FECHACREACION datetime DEFAULT NULL,
  USUARIOCREACION varchar(50) DEFAULT NULL,
  FECHAMODIFICACION datetime DEFAULT NULL,
  USUARIOMODIFICACION varchar(50) DEFAULT NULL,
  ESTADOREGISTRO varchar(1) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30  9:58:44
CREATE DATABASE  IF NOT EXISTS `qa_te_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_te_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_te_egov_docker
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
-- Table structure for table `actividad`
--

DROP TABLE IF EXISTS actividad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE actividad (
  ID int(19) NOT NULL,
  ID_TIPO_ACTIVIDAD int(19) DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  FECHA_ALTA datetime DEFAULT NULL,
  FECHA_CIERRE datetime DEFAULT NULL,
  USUARIO_ALTA varchar(255) DEFAULT NULL,
  USUARIO_CIERRE varchar(255) DEFAULT NULL,
  PARENT_ID int(19) DEFAULT NULL,
  ID_OBJETIVO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FKB24A401D7DDD5049 (ID_TIPO_ACTIVIDAD),
  KEY FKB24A401DD5886906 (ID_TIPO_ACTIVIDAD)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actividad_param`
--

DROP TABLE IF EXISTS actividad_param;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE actividad_param (
  ID int(19) NOT NULL,
  ID_ACTIVIDAD int(19) DEFAULT NULL,
  CAMPO varchar(255) DEFAULT NULL,
  VALOR varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY ACTIVIDAD_PARAM_PK (ID),
  KEY FK865179CB2827ADD (ID_ACTIVIDAD),
  KEY FK865179CB549B7BA0 (ID_ACTIVIDAD),
  KEY ID_ACTIVIDAD (ID_ACTIVIDAD)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dato_variable`
--

DROP TABLE IF EXISTS dato_variable;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE dato_variable (
  ID_TRATA int(19) NOT NULL,
  OBLIGATORIEDAD char(1) DEFAULT NULL,
  NOMBRE_METADATO varchar(255) DEFAULT NULL,
  TIPO decimal(10,0) DEFAULT NULL,
  ORDEN decimal(10,0) NOT NULL,
  KEY FK762D3B235BC32B44 (ID_TRATA),
  KEY FK762D3B23C5BD8F30 (ID_TRATA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datos_variables_combo_grupos`
--

DROP TABLE IF EXISTS datos_variables_combo_grupos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE datos_variables_combo_grupos (
  ID int(19) NOT NULL,
  NOMBRE_GRUPO varchar(255) DEFAULT NULL,
  FECHA_BAJA datetime DEFAULT NULL,
  TIPO varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datos_variables_combos`
--

DROP TABLE IF EXISTS datos_variables_combos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE datos_variables_combos (
  ID int(19) NOT NULL,
  ID_GRUPO_COMBO int(19) NOT NULL,
  VALOR_OPCION_COMBO varchar(255) DEFAULT NULL,
  FECHA_BAJA datetime DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documento (
  ID int(19) NOT NULL,
  NUMERO_SADE varchar(255) DEFAULT NULL,
  NUMERO_ESPECIAL varchar(255) DEFAULT NULL,
  NOMBRE_USUARIO_GENERADOR varchar(255) DEFAULT NULL,
  MOTIVO varchar(333) DEFAULT NULL,
  NOMBRE_ARCHIVO varchar(255) DEFAULT NULL,
  NUMERO_FOLIADO decimal(10,0) DEFAULT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_ASOCIACION datetime DEFAULT NULL,
  ID_TIPO_DOCUMENTO int(19) DEFAULT NULL,
  ID_TIPO_DOCUMENTO_VINCULADO int(19) DEFAULT NULL,
  USUARIO_ASOCIADOR varchar(255) DEFAULT NULL,
  ID_TASK varchar(255) DEFAULT NULL,
  ID_EXP_CABECERA_TC int(19) DEFAULT NULL,
  TIPO_DOC_ACRONIMO varchar(11) DEFAULT NULL,
  SUBSANADO int(11) DEFAULT NULL,
  SUBSANADO_LIMITADO int(11) DEFAULT NULL,
  USUARIO_SUBSANADOR varchar(255) DEFAULT NULL,
  FECHA_SUBSANACION datetime DEFAULT NULL,
  ID_TRANSACCION_FC int(19) DEFAULT NULL,
  ENTIDAD varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_2 (ID),
  KEY DOCUMENTOMOTIVOIDX (MOTIVO),
  KEY FKDE556294398E95A1 (ID_TIPO_DOCUMENTO),
  KEY FKDE5562944390DDB5 (ID_TIPO_DOCUMENTO),
  KEY IDX_NRODOC (NUMERO_SADE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento_de_identidad`
--

DROP TABLE IF EXISTS documento_de_identidad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documento_de_identidad (
  ID int(19) NOT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  NUMERO_DOCUMENTO varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO_POSIBLE varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_39 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_arch_trab_repa_part`
--

DROP TABLE IF EXISTS ee_arch_trab_repa_part;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_arch_trab_repa_part (
  ID int(19) NOT NULL,
  ID_REPARTICION int(19) NOT NULL,
  POSICION decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_arch_trabajo_visu_test`
--

DROP TABLE IF EXISTS ee_arch_trabajo_visu_test;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_arch_trabajo_visu_test (
  ID int(19) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  REPARTICION varchar(45) DEFAULT NULL,
  SECTOR varchar(45) DEFAULT NULL,
  RECTORA varchar(45) DEFAULT NULL,
  FECHA_ALTA datetime DEFAULT NULL,
  ID_ARCHIVO_TRABAJO int(19) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_arch_trabajo_visualizacion`
--

DROP TABLE IF EXISTS ee_arch_trabajo_visualizacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_arch_trabajo_visualizacion (
  ID int(19) NOT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  REPARTICION varchar(45) DEFAULT NULL,
  SECTOR varchar(45) DEFAULT NULL,
  RECTORA varchar(45) DEFAULT NULL,
  FECHA_ALTA datetime DEFAULT NULL,
  ID_ARCHIVO_TRABAJO int(19) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_archivo_de_trabajo`
--

DROP TABLE IF EXISTS ee_archivo_de_trabajo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_archivo_de_trabajo (
  ID int(10) NOT NULL,
  NOMBRE_ARCHIVO varchar(255) DEFAULT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  USUARIOASOCIADOR varchar(255) DEFAULT NULL,
  FECHAASOCIACION datetime DEFAULT NULL,
  ID_TASK varchar(255) DEFAULT NULL,
  ID_EXP_CABECERA_TC decimal(10,0) DEFAULT NULL,
  ID_TIPO_ARCHIVO_TRABAJO decimal(10,0) DEFAULT NULL,
  TIPO_RESERVA decimal(10,0) DEFAULT NULL,
  ID_GUARDA_DOCUMENTAL varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY PRIMARY_3 (ID),
  KEY FKAF0C277B4DD2B314 (ID_TIPO_ARCHIVO_TRABAJO),
  KEY FKAF0C277B67870EA8 (ID_TIPO_ARCHIVO_TRABAJO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_archivo_remision`
--

DROP TABLE IF EXISTS ee_archivo_remision;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_archivo_remision (
  FECHA_DE_SOLICITUD datetime NOT NULL,
  ID int(19) NOT NULL,
  ID_EXPEDIENTE int(19) NOT NULL,
  ID_EXPEDIENTE_REMISION int(19) NOT NULL,
  UNIQUE KEY EE_ARCHIVO_REMISION_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_aud_tarea_migracion`
--

DROP TABLE IF EXISTS ee_aud_tarea_migracion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_aud_tarea_migracion (
  ID_TAREA int(19) NOT NULL,
  TAREA varchar(255) NOT NULL,
  CODIGO_REPARTICION_ORIGEN varchar(255) DEFAULT NULL,
  CODIGO_REPARTICION_DESTINO varchar(255) DEFAULT NULL,
  CODIGO_SECTOR_ORIGEN varchar(255) DEFAULT NULL,
  CODIGO_SECTOR_DESTINO varchar(255) DEFAULT NULL,
  FECHA datetime NOT NULL,
  UNIQUE KEY EE_AUD_TAREA_MIGRACION_PK (ID_TAREA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_aud_tarea_migracion_error`
--

DROP TABLE IF EXISTS ee_aud_tarea_migracion_error;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_aud_tarea_migracion_error (
  ID int(19) NOT NULL,
  ID_TAREA int(19) DEFAULT NULL,
  `ERROR` varchar(255) NOT NULL,
  FECHA datetime NOT NULL,
  UNIQUE KEY EE_AUD_MIGRACION_ERROR_PK (ID),
  KEY FKCAD6425C8C638808 (ID_TAREA),
  KEY FKCAD6425CE95D609C (ID_TAREA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_auditoria_de_consulta`
--

DROP TABLE IF EXISTS ee_auditoria_de_consulta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_auditoria_de_consulta (
  ID_AUDITORIA int(19) NOT NULL,
  USUARIO varchar(45) NOT NULL,
  TIPO_ACTUACION varchar(45) DEFAULT NULL,
  ANIO_ACTUACION decimal(10,0) DEFAULT NULL,
  NUMERO_ACTUACION decimal(10,0) DEFAULT NULL,
  REPARTICION_ACTUACION varchar(45) DEFAULT NULL,
  REPARTICION_USUARIO varchar(45) DEFAULT NULL,
  TRATA varchar(255) DEFAULT NULL,
  METADATO_1 varchar(255) DEFAULT NULL,
  VALOR_METADATO_1 varchar(255) DEFAULT NULL,
  METADATO_2 varchar(255) DEFAULT NULL,
  VALOR_METADATO_2 varchar(255) DEFAULT NULL,
  METADATO_3 varchar(255) DEFAULT NULL,
  VALOR_METADATO_3 varchar(255) DEFAULT NULL,
  FECHA_DESDE datetime DEFAULT NULL,
  FECHA_HASTA datetime DEFAULT NULL,
  FECHA_CONSULTA datetime NOT NULL,
  TIPODOCUMENTO varchar(45) DEFAULT NULL,
  NUMERODOCUMENTO varchar(45) DEFAULT NULL,
  CUIT_CUIL varchar(11) DEFAULT NULL,
  DOMICILIO varchar(50) DEFAULT NULL,
  DEPARTAMENTO varchar(10) DEFAULT NULL,
  CODIGO_POSTAL varchar(10) DEFAULT NULL,
  PISO varchar(10) DEFAULT NULL,
  UNIQUE KEY PRIMARY_4 (ID_AUDITORIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_auditoria_notificacion`
--

DROP TABLE IF EXISTS ee_auditoria_notificacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_auditoria_notificacion (
  ID_AUDITORIA int(19) NOT NULL,
  USUARIO varchar(45) DEFAULT NULL,
  ANIO decimal(10,0) DEFAULT NULL,
  NUMERO decimal(10,0) DEFAULT NULL,
  ACTUACION varchar(45) DEFAULT NULL,
  FECHA_OPERACION datetime DEFAULT NULL,
  ID_DOCUMENTO int(19) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(45) DEFAULT NULL,
  REPARTICION varchar(45) DEFAULT NULL,
  UNIQUE KEY EE_AUDITORIA_NOTIFICACION_PK (ID_AUDITORIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_con_suspension`
--

DROP TABLE IF EXISTS ee_con_suspension;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_con_suspension (
  ID_EE int(19) NOT NULL,
  USUARIO_SUSP varchar(255) DEFAULT NULL,
  FECHA_SUSP datetime DEFAULT NULL,
  COD_CARATULA varchar(50) DEFAULT NULL,
  UNIQUE KEY EE_CON_SUSPENSION_PK (ID_EE),
  KEY FK51AC8C597753DDDD (ID_EE),
  KEY FK51AC8C59AE21D949 (ID_EE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_expediente_archivostrabajo`
--

DROP TABLE IF EXISTS ee_expediente_archivostrabajo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_expediente_archivostrabajo (
  ID int(19) NOT NULL,
  ID_ARCHIVOTRABAJO int(19) NOT NULL,
  POSICION decimal(10,0) NOT NULL,
  UNIQUE KEY PRIMARY_5 (ID,POSICION),
  KEY FKFEA278477753DDDD (ID),
  KEY FKFEA27847AE21D949 (ID),
  KEY FKFEA27847B29F2BA3 (ID_ARCHIVOTRABAJO),
  KEY FKFEA27847FBC4BCB7 (ID_ARCHIVOTRABAJO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_expediente_asociado`
--

DROP TABLE IF EXISTS ee_expediente_asociado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_expediente_asociado (
  ID decimal(10,0) NOT NULL,
  ID_EXPEDIENTEASOCIADO decimal(10,0) NOT NULL,
  POSICION decimal(10,0) NOT NULL,
  UNIQUE KEY PRIMARY_6 (ID,POSICION),
  KEY FK50723E82228A0DDC (ID_EXPEDIENTEASOCIADO),
  KEY FK50723E823C3E6970 (ID_EXPEDIENTEASOCIADO),
  KEY FK50723E827753DDDD (ID),
  KEY FK50723E82AE21D949 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_expediente_documentos`
--

DROP TABLE IF EXISTS ee_expediente_documentos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_expediente_documentos (
  ID int(19) NOT NULL,
  ID_DOCUMENTO int(19) NOT NULL,
  POSICION decimal(10,0) NOT NULL,
  ID_EE_DOC int(19) DEFAULT NULL,
  KEY FK6959B6584D2F80C (ID_DOCUMENTO),
  KEY FK6959B65861E185F8 (ID_DOCUMENTO),
  KEY FK6959B6587753DDDD (ID),
  KEY FK6959B658AE21D949 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_expediente_electronico`
--

DROP TABLE IF EXISTS ee_expediente_electronico;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_expediente_electronico (
  ID int(19) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  ID_TRATA int(19) DEFAULT NULL,
  USUARIO_CREADOR varchar(255) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(255) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  ANIO decimal(10,0) DEFAULT NULL,
  NUMERO decimal(10,0) DEFAULT NULL,
  SECUENCIA varchar(255) DEFAULT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  CODIGO_REPARTICION_ACTUACION varchar(255) DEFAULT NULL,
  CODIGO_REPARTICION_USUARIO varchar(255) DEFAULT NULL,
  ES_ELECTRONICO char(1) DEFAULT NULL,
  SOLICITUD_INICIADORA decimal(10,0) DEFAULT NULL,
  ID_WORKFLOW varchar(60) DEFAULT NULL,
  ESTADO varchar(45) DEFAULT NULL,
  ES_CABECERA_TC char(1) DEFAULT NULL,
  SISTEMA_CREADOR varchar(255) DEFAULT NULL,
  SISTEMA_APODERADO varchar(255) DEFAULT NULL,
  BLOQUEADO int(11) DEFAULT NULL,
  TRAMITACION_LIBRE int(11) DEFAULT NULL,
  ES_RESERVADO int(11) DEFAULT NULL,
  USUARIO_RESERVA varchar(255) DEFAULT NULL,
  FECHA_RESERVA datetime DEFAULT NULL,
  FECHA_ARCHIVO datetime DEFAULT NULL,
  FECHA_SOLICITUD_ARCHIVO datetime DEFAULT NULL,
  FECHA_ENVIO_DEPURACION datetime DEFAULT NULL,
  ID_OPERACION int(19) DEFAULT NULL,
  RESULTADO varchar(40) DEFAULT NULL,
  BLOCKED int(1) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY PRIMARY_8 (ID),
  KEY FKDD1F0E5A494A58B (SOLICITUD_INICIADORA),
  KEY FKDD1F0E5A5BC32B44 (ID_TRATA),
  KEY FKDD1F0E5AC5BD8F30 (ID_TRATA),
  KEY FKDD1F0E5AE7BD8E9F (SOLICITUD_INICIADORA),
  KEY IDX_EE_EXP_ELEC_ESTADO (ESTADO),
  KEY IDX_EE_EXP_ELEC_FECHA_CREACION (FECHA_CREACION),
  KEY IDX_EE_EXP_ELEC_FECHA_MODIFIC (FECHA_MODIFICACION),
  KEY IDX_NUMERO_ANIO (ANIO,NUMERO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_reparticion_participante`
--

DROP TABLE IF EXISTS ee_reparticion_participante;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_reparticion_participante (
  ID_REPARTICION int(19) NOT NULL,
  REPARTICION varchar(255) DEFAULT NULL,
  TIPO_OPERACION varchar(255) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID_REPARTICION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_sade_extracto`
--

DROP TABLE IF EXISTS ee_sade_extracto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_sade_extracto (
  ID_EXTRACTO int(19) NOT NULL,
  CODIGO_EXTRACTO varchar(11) NOT NULL,
  GRUPO_EXTRACTO varchar(30) NOT NULL,
  DESCRIPCION_EXTRAC varchar(70) NOT NULL,
  VIGENCIA_DESDE datetime NOT NULL,
  VIGENCIA_HASTA datetime NOT NULL,
  REQUERIMIENTOS varchar(1) DEFAULT NULL,
  TIPO_ACTUACION varchar(1) DEFAULT NULL,
  VERSION decimal(10,0) NOT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(30) DEFAULT NULL,
  ESTADO_REGISTRO varchar(1) DEFAULT NULL,
  DESHABILITADO_PAPEL varchar(1) NOT NULL,
  ID_PERMANENCIA decimal(10,0) DEFAULT NULL,
  REPARTICION_GUARDA decimal(10,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_reparticion_enlace`
--

DROP TABLE IF EXISTS ee_reparticion_enlace;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_reparticion_enlace (
  ID_REPARTICION decimal(10,0) DEFAULT NULL,
  ID_ENLACE decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_sistema_externo`
--

DROP TABLE IF EXISTS ee_sistema_externo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_sistema_externo (
  CODIGO varchar(45) DEFAULT NULL,
  URL varchar(500) DEFAULT NULL,
  PARAMETROS varchar(500) DEFAULT NULL,
  ESTADO char(1) DEFAULT NULL,
  ID int(19) NOT NULL,
  TRATA int(19) DEFAULT NULL,
  SARASA double DEFAULT NULL,
  UNIQUE KEY EE_SISTEMA_EXTERNO_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_subprocess`
--

DROP TABLE IF EXISTS ee_subprocess;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_subprocess (
  ID bigint(20) NOT NULL,
  STATEFLOW varchar(200) NOT NULL,
  LOCK_TYPE varchar(200) NOT NULL,
  STATENAME varchar(200) NOT NULL,
  ID_PROCEDURE int(11) DEFAULT NULL COMMENT 'ID TRATA',
  VERSION int(11) DEFAULT NULL,
  PROCEDURE_NAME varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FKEBFF6D2E8EEAD0B3 (ID_PROCEDURE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_subprocess_production`
--

DROP TABLE IF EXISTS ee_subprocess_production;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_subprocess_production (
  ID bigint(20) NOT NULL,
  STATEFLOW varchar(200) NOT NULL,
  LOCK_TYPE varchar(200) NOT NULL,
  STATENAME varchar(200) NOT NULL,
  ID_PROCEDURE int(11) DEFAULT NULL COMMENT 'ID TRATA',
  VERSION int(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_TRATA (ID_PROCEDURE),
  KEY FKBA3B5C6A8EEAD0B3 (ID_PROCEDURE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_tarea_paralelo`
--

DROP TABLE IF EXISTS ee_tarea_paralelo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_tarea_paralelo (
  ID int(19) NOT NULL,
  ID_TASK varchar(255) DEFAULT NULL,
  ID_EXP int(19) NOT NULL,
  FECHA_PASE date DEFAULT NULL,
  USUARIO_ORIGEN varchar(255) NOT NULL,
  USUARIO_GRUPO_DESTINO varchar(255) NOT NULL,
  MOTIVO mediumtext,
  ESTADO varchar(255) NOT NULL,
  ESTADO_ANTERIOR varchar(255) NOT NULL,
  MOTIVO_RESPUESTA mediumtext,
  USUARIO_GRUPO_ANTERIOR varchar(255) NOT NULL,
  TAREA_GRUPAL int(3) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_tipos_archivo_trabajo`
--

DROP TABLE IF EXISTS ee_tipos_archivo_trabajo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_tipos_archivo_trabajo (
  ID int(19) NOT NULL,
  NOMBRE varchar(50) NOT NULL,
  DESCRIPCION varchar(200) DEFAULT NULL,
  REPETIBLE char(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ee_tramitacion_conjunta`
--

DROP TABLE IF EXISTS ee_tramitacion_conjunta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ee_tramitacion_conjunta (
  ID int(19) NOT NULL,
  ID_EXPEDIENTEASOCIADO int(19) NOT NULL,
  FECHA_MODIFICACION_TC datetime NOT NULL,
  USUARIO_MODIFICACION_TC varchar(255) NOT NULL,
  UNIQUE KEY PRIMARY_10 (ID),
  KEY FK_EE_TRAMITACION_CONJUNTA_1 (ID_EXPEDIENTEASOCIADO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expedienteasociado`
--

DROP TABLE IF EXISTS expedienteasociado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE expedienteasociado (
  ID int(19) NOT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  ANIO varchar(255) DEFAULT NULL,
  NUMERO varchar(255) DEFAULT NULL,
  SECUENCIA varchar(255) NOT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  CODIGO_REPARTICION_ACTUACION varchar(255) DEFAULT NULL,
  CODIGO_REPARTICION_USUARIO varchar(255) DEFAULT NULL,
  ES_ELECTRONICO char(1) DEFAULT NULL,
  ID_CODIGO_CARATULA int(19) DEFAULT NULL,
  FECHA_ASOCIACION datetime DEFAULT NULL,
  USUARIO_ASOCIADOR varchar(255) DEFAULT NULL,
  ID_TASK varchar(255) DEFAULT NULL,
  ES_EXP_ASOC_TC char(1) DEFAULT NULL,
  ES_EXP_ASOC_FUSION char(1) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_MODIFICACION varchar(255) DEFAULT NULL,
  ID_EXP_CABECERA_TC int(19) DEFAULT NULL,
  UNIQUE KEY PRIMARY_11 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `generar_copia`
--

DROP TABLE IF EXISTS generar_copia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE generar_copia (
  ID int(19) NOT NULL,
  USERNAME varchar(255) NOT NULL,
  ID_WORKFLOW varchar(255) NOT NULL,
  CODIGO_EXPEDIENTE varchar(255) NOT NULL,
  REINTENTOS double NOT NULL,
  ID_ACTIVIDAD int(19) NOT NULL,
  ESTADO_DE_REINTENTO varchar(255) NOT NULL,
  UNIQUE KEY GENERAR_COPIA_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `historialoperacion`
--

DROP TABLE IF EXISTS historialoperacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE historialoperacion (
  ID int(19) NOT NULL,
  TIPO_OPERACION varchar(255) DEFAULT NULL,
  FECHA_OPERACION datetime DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  EXPEDIENTE varchar(255) DEFAULT NULL,
  ID_EXPEDIENTE int(19) DEFAULT NULL,
  ID_SOLICITUD int(19) DEFAULT NULL,
  GRUPO_SELECCIONADO varchar(255) DEFAULT NULL,
  ES_SECTOR_DESTINO varchar(255) DEFAULT NULL,
  USUARIO_DESTINO varchar(255) DEFAULT NULL,
  ID_LIST_DESTINATARIOS varchar(255) DEFAULT NULL,
  DESTINATARIO varchar(255) DEFAULT NULL,
  ID_EXPEDIENTE_ELECTRONICO int(19) DEFAULT NULL,
  REPARTICION_DESTINO varchar(255) DEFAULT NULL,
  USUARIO_ANTERIOR varchar(255) DEFAULT NULL,
  ESTADO_SELECCIONADO varchar(255) DEFAULT NULL,
  ES_USUARIODESTINO varchar(255) DEFAULT NULL,
  GRUPO_ANTERIOR varchar(255) DEFAULT NULL,
  USUARIO_PRODUCTOR_INFO varchar(255) DEFAULT NULL,
  ESTADO_SELECCIONADO_4M varchar(255) DEFAULT NULL,
  SECTOR_DESTINO varchar(255) DEFAULT NULL,
  USUARIO_ORIGEN varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  SISTEMA_APODERADO varchar(255) DEFAULT NULL,
  REPARTICION_USUARIO varchar(255) DEFAULT NULL,
  DESTINO varchar(255) DEFAULT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  ESTADO_ANTERIOR varchar(255) DEFAULT NULL,
  LOGGEDUSERNAME varchar(255) DEFAULT NULL,
  ES_REPARTICIONDESTINO varchar(255) DEFAULT NULL,
  FECHA date DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  USUARIO_SELECCIONADO varchar(255) DEFAULT NULL,
  TIPO_OPERACION_DETALLE varchar(255) DEFAULT NULL,
  TAREA_GRUPAL varchar(255) DEFAULT NULL,
  SECTOR_USUARIO_ORIGEN varchar(255) DEFAULT NULL,
  RESULTADO varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_deployment`
--

DROP TABLE IF EXISTS jbpm4_deployment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_deployment (
  DBID_ bigint(20) NOT NULL,
  NAME_ longtext,
  TIMESTAMP_ bigint(20) DEFAULT NULL,
  STATE_ varchar(255) DEFAULT NULL,
  PRIMARY KEY (DBID_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_deployprop`
--

DROP TABLE IF EXISTS jbpm4_deployprop;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_deployprop (
  DBID_ decimal(24,0) NOT NULL,
  DEPLOYMENT_ decimal(24,0) DEFAULT NULL,
  OBJNAME_ varchar(255) DEFAULT NULL,
  KEY_ varchar(255) DEFAULT NULL,
  STRINGVAL_ varchar(255) DEFAULT NULL,
  LONGVAL_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_15 (DBID_),
  KEY FK_DEPLPROP_DEPL (DEPLOYMENT_),
  KEY IDX_DEPLPROP_DEPL (DEPLOYMENT_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_execution`
--

DROP TABLE IF EXISTS jbpm4_execution;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_execution (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(255) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  ACTIVITYNAME_ varchar(255) DEFAULT NULL,
  PROCDEFID_ varchar(255) DEFAULT NULL,
  HASVARS_ char(1) DEFAULT NULL,
  NAME_ varchar(255) DEFAULT NULL,
  KEY_ varchar(255) DEFAULT NULL,
  ID_ varchar(255) DEFAULT NULL,
  STATE_ varchar(255) DEFAULT NULL,
  SUSPHISTSTATE_ varchar(255) DEFAULT NULL,
  PRIORITY_ decimal(10,0) DEFAULT NULL,
  HISACTINST_ decimal(24,0) DEFAULT NULL,
  PARENT_ decimal(24,0) DEFAULT NULL,
  INSTANCE_ decimal(24,0) DEFAULT NULL,
  SUPEREXEC_ decimal(24,0) DEFAULT NULL,
  SUBPROCINST_ decimal(24,0) DEFAULT NULL,
  PARENT_IDX_ decimal(10,0) DEFAULT NULL,
  INITIATOR_ varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_16 (DBID_),
  UNIQUE KEY ID_ (ID_),
  KEY FK_EXEC_INSTANCE (INSTANCE_),
  KEY FK_EXEC_PARENT (PARENT_),
  KEY FK_EXEC_SUBPI (SUBPROCINST_),
  KEY FK_EXEC_SUPEREXEC (SUPEREXEC_),
  KEY IDX_EXEC_INSTANCE (INSTANCE_),
  KEY IDX_EXEC_PARENT (PARENT_),
  KEY IDX_EXEC_SUBPI (SUBPROCINST_),
  KEY IDX_EXEC_SUPEREXEC (SUPEREXEC_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_actinst`
--

DROP TABLE IF EXISTS jbpm4_hist_actinst;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_actinst (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(255) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  HPROCI_ decimal(24,0) DEFAULT NULL,
  TYPE_ varchar(255) DEFAULT NULL,
  EXECUTION_ varchar(255) DEFAULT NULL,
  ACTIVITY_NAME_ varchar(255) DEFAULT NULL,
  START_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  TRANSITION_ varchar(255) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL,
  HTASK_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_17 (DBID_),
  KEY FK_HACTI_HPROCI (HPROCI_),
  KEY FK_HTI_HTASK (HTASK_),
  KEY IDX_HACTI_HPROCI (HPROCI_),
  KEY IDX_HTI_HTASK (HTASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_detail`
--

DROP TABLE IF EXISTS jbpm4_hist_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_detail (
  DBID_ bigint(20) NOT NULL,
  CLASS_ varchar(255) NOT NULL,
  DBVERSION_ int(11) NOT NULL,
  USERID_ varchar(255) DEFAULT NULL,
  TIME_ datetime DEFAULT NULL,
  HPROCI_ bigint(20) DEFAULT NULL,
  HPROCIIDX_ int(11) DEFAULT NULL,
  HACTI_ bigint(20) DEFAULT NULL,
  HACTIIDX_ int(11) DEFAULT NULL,
  HTASK_ bigint(20) DEFAULT NULL,
  HTASKIDX_ int(11) DEFAULT NULL,
  HVAR_ bigint(20) DEFAULT NULL,
  HVARIDX_ int(11) DEFAULT NULL,
  MESSAGE_ longtext,
  OLD_STR_ varchar(255) DEFAULT NULL,
  NEW_STR_ varchar(255) DEFAULT NULL,
  OLD_INT_ int(11) DEFAULT NULL,
  NEW_INT_ int(11) DEFAULT NULL,
  OLD_TIME_ datetime DEFAULT NULL,
  NEW_TIME_ datetime DEFAULT NULL,
  PARENT_ bigint(20) DEFAULT NULL,
  PARENT_IDX_ int(11) DEFAULT NULL,
  PRIMARY KEY (DBID_),
  KEY FK_HDETAIL_HVAR (HVAR_),
  KEY FK_HDETAIL_HPROCI (HPROCI_),
  KEY FK_HDETAIL_HTASK (HTASK_),
  KEY FK_HDETAIL_HACTI (HACTI_),
  KEY IDX_HDET_HVAR (HVAR_),
  KEY IDX_HDET_HACTI (HACTI_),
  KEY IDX_HDET_HTASK (HTASK_),
  KEY IDX_HDET_HPROCI (HPROCI_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_procinst`
--

DROP TABLE IF EXISTS jbpm4_hist_procinst;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_procinst (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  ID_ varchar(255) DEFAULT NULL,
  PROCDEFID_ varchar(255) DEFAULT NULL,
  KEY_ varchar(255) DEFAULT NULL,
  START_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  STATE_ varchar(255) DEFAULT NULL,
  ENDACTIVITY_ varchar(255) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_19 (DBID_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_task`
--

DROP TABLE IF EXISTS jbpm4_hist_task;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_task (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  EXECUTION_ varchar(255) DEFAULT NULL,
  OUTCOME_ varchar(255) DEFAULT NULL,
  ASSIGNEE_ varchar(255) DEFAULT NULL,
  PRIORITY_ decimal(10,0) DEFAULT NULL,
  STATE_ varchar(255) DEFAULT NULL,
  CREATE_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL,
  SUPERTASK_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_20 (DBID_),
  KEY FK_HSUPERT_SUB (SUPERTASK_),
  KEY IDX_HSUPERT_SUB (SUPERTASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_var`
--

DROP TABLE IF EXISTS jbpm4_hist_var;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_var (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  PROCINSTID_ varchar(255) DEFAULT NULL,
  EXECUTIONID_ varchar(255) DEFAULT NULL,
  VARNAME_ varchar(255) DEFAULT NULL,
  VALUE_ varchar(255) DEFAULT NULL,
  HPROCI_ decimal(24,0) DEFAULT NULL,
  HTASK_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_21 (DBID_),
  KEY FK_HVAR_HPROCI (HPROCI_),
  KEY FK_HVAR_HTASK (HTASK_),
  KEY IDX_HVAR_HPROCI (HPROCI_),
  KEY IDX_HVAR_HTASK (HTASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_job`
--

DROP TABLE IF EXISTS jbpm4_job;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_job (
  DBID_ bigint(20) NOT NULL,
  CLASS_ varchar(255) NOT NULL,
  DBVERSION_ int(11) NOT NULL,
  DUEDATE_ datetime DEFAULT NULL,
  STATE_ varchar(255) DEFAULT NULL,
  ISEXCLUSIVE_ bit(1) DEFAULT NULL,
  LOCKOWNER_ varchar(255) DEFAULT NULL,
  LOCKEXPTIME_ datetime DEFAULT NULL,
  EXCEPTION_ longtext,
  RETRIES_ int(11) DEFAULT NULL,
  PROCESSINSTANCE_ bigint(20) DEFAULT NULL,
  EXECUTION_ bigint(20) DEFAULT NULL,
  CFG_ bigint(20) DEFAULT NULL,
  SIGNAL_ varchar(255) DEFAULT NULL,
  EVENT_ varchar(255) DEFAULT NULL,
  REPEAT_ varchar(255) DEFAULT NULL,
  PRIMARY KEY (DBID_),
  KEY FK_JOB_CFG (CFG_),
  KEY IDX_JOB_CFG (CFG_),
  KEY IDX_JOB_EXE (EXECUTION_),
  KEY IDX_JOB_PRINST (PROCESSINSTANCE_),
  KEY IDX_JOBDUEDATE (DUEDATE_),
  KEY IDX_JOBLOCKEXP (LOCKEXPTIME_),
  KEY IDX_JOBRETRIES (RETRIES_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_lob`
--

DROP TABLE IF EXISTS jbpm4_lob;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_lob (
  DBID_ bigint(20) NOT NULL,
  DBVERSION_ int(11) NOT NULL,
  BLOB_VALUE_ longblob,
  DEPLOYMENT_ bigint(20) DEFAULT NULL,
  NAME_ longtext,
  PRIMARY KEY (DBID_),
  KEY FK_LOB_DEPLOYMENT (DEPLOYMENT_),
  KEY IDX_LOB_DEPLOYMENT (DEPLOYMENT_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_participation`
--

DROP TABLE IF EXISTS jbpm4_participation;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_participation (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  GROUPID_ varchar(255) DEFAULT NULL,
  USERID_ varchar(255) DEFAULT NULL,
  TYPE_ varchar(255) DEFAULT NULL,
  TASK_ decimal(24,0) DEFAULT NULL,
  SWIMLANE_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_24 (DBID_),
  KEY FK_PART_SWIMLANE (SWIMLANE_),
  KEY FK_PART_TASK (TASK_),
  KEY IDX_PART_TASK (TASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_property`
--

DROP TABLE IF EXISTS jbpm4_property;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_property (
  KEY_ varchar(255) NOT NULL,
  VERSION_ decimal(10,0) NOT NULL,
  VALUE_ varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_25 (KEY_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_swimlane`
--

DROP TABLE IF EXISTS jbpm4_swimlane;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_swimlane (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  NAME_ varchar(255) DEFAULT NULL,
  ASSIGNEE_ varchar(255) DEFAULT NULL,
  EXECUTION_ decimal(24,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_26 (DBID_),
  KEY FK_SWIMLANE_EXEC (EXECUTION_),
  KEY IDX_SWIMLANE_EXEC (EXECUTION_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_task`
--

DROP TABLE IF EXISTS jbpm4_task;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_task (
  DBID_ bigint(20) NOT NULL,
  CLASS_ char(1) NOT NULL,
  DBVERSION_ int(11) NOT NULL,
  NAME_ varchar(255) DEFAULT NULL,
  DESCR_ longtext,
  STATE_ varchar(255) DEFAULT NULL,
  SUSPHISTSTATE_ varchar(255) DEFAULT NULL,
  ASSIGNEE_ varchar(255) DEFAULT NULL,
  FORM_ varchar(255) DEFAULT NULL,
  PRIORITY_ int(11) DEFAULT NULL,
  CREATE_ datetime DEFAULT NULL,
  DUEDATE_ datetime DEFAULT NULL,
  PROGRESS_ int(11) DEFAULT NULL,
  SIGNALLING_ bit(1) DEFAULT NULL,
  EXECUTION_ID_ varchar(255) DEFAULT NULL,
  ACTIVITY_NAME_ varchar(255) DEFAULT NULL,
  HASVARS_ bit(1) DEFAULT NULL,
  SUPERTASK_ bigint(20) DEFAULT NULL,
  EXECUTION_ bigint(20) DEFAULT NULL,
  PROCINST_ bigint(20) DEFAULT NULL,
  SWIMLANE_ bigint(20) DEFAULT NULL,
  TASKDEFNAME_ varchar(255) DEFAULT NULL,
  PRIMARY KEY (DBID_),
  KEY FK_TASK_SUPERTASK (SUPERTASK_),
  KEY FK_TASK_SWIML (SWIMLANE_),
  KEY IDX_TASK_SUPERTASK (SUPERTASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_variable`
--

DROP TABLE IF EXISTS jbpm4_variable;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_variable (
  DBID_ bigint(20) NOT NULL,
  CLASS_ varchar(255) NOT NULL,
  DBVERSION_ int(11) NOT NULL,
  KEY_ varchar(255) DEFAULT NULL,
  CONVERTER_ varchar(255) DEFAULT NULL,
  HIST_ char(1) DEFAULT NULL,
  EXECUTION_ bigint(20) DEFAULT NULL,
  TASK_ bigint(20) DEFAULT NULL,
  LOB_ bigint(20) DEFAULT NULL,
  DATE_VALUE_ datetime DEFAULT NULL,
  DOUBLE_VALUE_ double DEFAULT NULL,
  CLASSNAME_ varchar(255) DEFAULT NULL,
  LONG_VALUE_ bigint(20) DEFAULT NULL,
  STRING_VALUE_ longtext,
  TEXT_VALUE_ longtext,
  EXESYS_ bigint(20) DEFAULT NULL,
  PRIMARY KEY (DBID_),
  KEY FK_VAR_EXECUTION (EXECUTION_),
  KEY FK_VAR_EXESYS (EXESYS_),
  KEY FK_VAR_LOB (LOB_),
  KEY FK_VAR_TASK (TASK_),
  KEY IDX_VAR_EXECUTION (EXECUTION_),
  KEY IDX_VAR_EXESYS (EXESYS_),
  KEY IDX_VAR_LOB (LOB_),
  KEY IDX_VAR_TASK (TASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm_task_temp`
--

DROP TABLE IF EXISTS jbpm_task_temp;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm_task_temp (
  EXECUTION_ID_ varchar(255) DEFAULT NULL,
  NAME_ varchar(255) DEFAULT NULL,
  CREATE_ datetime DEFAULT NULL,
  CODIGO_EXPEDIENTE varchar(599) DEFAULT NULL,
  KEY_ varchar(255) DEFAULT NULL,
  STRING_VALUE_ longtext
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metadatos_trata`
--

DROP TABLE IF EXISTS metadatos_trata;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE metadatos_trata (
  ID_EXPEDIENTE int(19) NOT NULL,
  OBLIGATORIEDAD char(1) DEFAULT NULL,
  NOMBRE_METADATO varchar(255) DEFAULT NULL,
  VALOR_METADATO varchar(255) DEFAULT NULL,
  ORDEN decimal(10,0) NOT NULL,
  TIPO decimal(10,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_29 (ID_EXPEDIENTE,ORDEN),
  KEY FK34BD7A677056ED2D (ID_EXPEDIENTE),
  KEY FK34BD7A67A724E899 (ID_EXPEDIENTE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `migr_datatype_transform_map`
--

DROP TABLE IF EXISTS migr_datatype_transform_map;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE migr_datatype_transform_map (
  ID double NOT NULL,
  PROJECT_ID_FK double NOT NULL,
  MAP_NAME varchar(300) DEFAULT NULL,
  SECURITY_GROUP_ID double NOT NULL,
  CREATED_ON datetime NOT NULL,
  CREATED_BY varchar(255) DEFAULT NULL,
  LAST_UPDATED_ON datetime DEFAULT NULL,
  LAST_UPDATED_BY varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY MIGR_DATATYPE_TRANSFORM_M_FK1 (PROJECT_ID_FK)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `migr_datatype_transform_rule`
--

DROP TABLE IF EXISTS migr_datatype_transform_rule;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE migr_datatype_transform_rule (
  ID double NOT NULL,
  MAP_ID_FK double NOT NULL,
  SOURCE_DATA_TYPE_NAME varchar(300) NOT NULL,
  SOURCE_PRECISION double DEFAULT NULL,
  SOURCE_SCALE double DEFAULT NULL,
  TARGET_DATA_TYPE_NAME varchar(300) NOT NULL,
  TARGET_PRECISION double DEFAULT NULL,
  TARGET_SCALE double DEFAULT NULL,
  SECURITY_GROUP_ID double NOT NULL,
  CREATED_ON datetime NOT NULL,
  CREATED_BY varchar(255) DEFAULT NULL,
  LAST_UPDATED_ON datetime DEFAULT NULL,
  LAST_UPDATED_BY varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY MIGR_DATATYPE_TRANSFORM_R_FK1 (MAP_ID_FK)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `migr_generation_order`
--

DROP TABLE IF EXISTS migr_generation_order;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE migr_generation_order (
  ID double NOT NULL,
  CONNECTION_ID_FK double NOT NULL,
  OBJECT_ID double NOT NULL,
  OBJECT_TYPE varchar(300) NOT NULL,
  GENERATION_ORDER double NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY MIGR_GENERATION_ORDER_UK (OBJECT_ID),
  KEY MIGR_GENERATION_ORDER_MD__FK1 (CONNECTION_ID_FK)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `migrlog`
--

DROP TABLE IF EXISTS migrlog;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE migrlog (
  ID double NOT NULL,
  PARENT_LOG_ID double DEFAULT NULL,
  LOG_DATE datetime NOT NULL,
  SEVERITY int(11) NOT NULL,
  LOGTEXT varchar(300) DEFAULT NULL,
  `PHASE` varchar(100) DEFAULT NULL,
  REF_OBJECT_ID double DEFAULT NULL,
  REF_OBJECT_TYPE varchar(300) DEFAULT NULL,
  CONNECTION_ID_FK double DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY MIGR_MIGRLOG_FK (PARENT_LOG_ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_configuration`
--

DROP TABLE IF EXISTS property_configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE property_configuration (
  CLAVE varchar(50) NOT NULL,
  VALOR varchar(1024) NOT NULL,
  CONFIGURACION varchar(100) NOT NULL,
  PRIMARY KEY (CLAVE,CONFIGURACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sade_extracto`
--

DROP TABLE IF EXISTS sade_extracto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE sade_extracto (
  ID_EXTRACTO int(19) NOT NULL,
  CODIGO_EXTRACTO varchar(255) DEFAULT NULL,
  DESCRIPCION_EXTRAC varchar(255) DEFAULT NULL,
  VIGENCIA_HASTA datetime DEFAULT NULL,
  VIGENCIA_DESDE datetime DEFAULT NULL,
  ESTADO_REGISTRO bit(1) DEFAULT NULL,
  PRIMARY KEY (ID_EXTRACTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `solicitante`
--

DROP TABLE IF EXISTS solicitante;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE solicitante (
  ID_SOLICITANTE int(19) NOT NULL,
  ID_DOCUMENTO int(19) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  TELEFONO varchar(255) DEFAULT NULL,
  NOMBRE_SOLICITANTE varchar(255) DEFAULT NULL,
  APELLIDO_SOLICITANTE varchar(255) DEFAULT NULL,
  RAZON_SOCIAL_SOLICITANTE varchar(255) DEFAULT NULL,
  SEGUNDO_APELLIDO_SOLICITANTE varchar(255) DEFAULT NULL,
  TERCER_APELLIDO_SOLICITANTE varchar(255) DEFAULT NULL,
  SEGUNDO_NOMBRE_SOLICITANTE varchar(255) DEFAULT NULL,
  TERCER_NOMBRE_SOLICITANTE varchar(255) DEFAULT NULL,
  CUIT_CUIL varchar(11) DEFAULT NULL,
  DOMICILIO varchar(255) DEFAULT NULL,
  PISO varchar(20) DEFAULT NULL,
  DEPARTAMENTO varchar(255) DEFAULT NULL,
  CODIGO_POSTAL varchar(20) DEFAULT NULL,
  BARRIO varchar(255) DEFAULT NULL,
  COMUNA varchar(255) DEFAULT NULL,
  ALTURA varchar(255) DEFAULT NULL,
  PROVINCIA varchar(255) DEFAULT NULL,
  LOCALIDAD varchar(255) DEFAULT NULL,
  OBSERVACIONES varchar(300) DEFAULT NULL,
  DPTO varchar(20) DEFAULT NULL,
  UNIQUE KEY PRIMARY_31 (ID_SOLICITANTE),
  KEY FKA5E856135F6788CD (ID_DOCUMENTO),
  KEY FKA5E85613F56D24E1 (ID_DOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `solicitud_expediente`
--

DROP TABLE IF EXISTS solicitud_expediente;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE solicitud_expediente (
  ID int(19) NOT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  MOTIVO_DE_RECHAZO varchar(255) DEFAULT NULL,
  SOLICITUD_INTERNA char(1) DEFAULT NULL,
  SOLICITANTE decimal(10,0) DEFAULT NULL,
  USUARIO_CREACION varchar(255) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  ID_TRATA_SUGERIDA int(19) DEFAULT NULL,
  MOTIVO_EXTERNO varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_32 (ID),
  KEY FK2FCC7B6280A5232E (SOLICITANTE),
  KEY FK2FCC7B62D447E61A (SOLICITANTE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento`
--

DROP TABLE IF EXISTS tad_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento (
  ID int(19) NOT NULL,
  ANIO int(10) DEFAULT NULL,
  BAJA_LOGICA int(11) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  NUMERO int(10) DEFAULT NULL,
  REFERENCIA varchar(255) DEFAULT NULL,
  REPARTICION varchar(255) DEFAULT NULL,
  TIPO varchar(255) DEFAULT NULL,
  PERSONA_ID int(19) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_documento`
--

DROP TABLE IF EXISTS tad_expediente_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_documento (
  ID_EXPEDIENTE int(19) NOT NULL,
  ID_DOCUMENTO int(19) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_persona`
--

DROP TABLE IF EXISTS tad_persona;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_persona (
  ID int(19) NOT NULL,
  CUIT varchar(255) DEFAULT NULL,
  DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  NOMBRE_APELLIDO varchar(255) DEFAULT NULL,
  RAZON_SOCIAL varchar(255) DEFAULT NULL,
  TELEFONO varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  ID_TERMINOS int(19) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_terminos_condiciones`
--

DROP TABLE IF EXISTS tad_terminos_condiciones;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_terminos_condiciones (
  ID int(19) NOT NULL,
  TERMINOS_CONDICIONES varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipo_tramite`
--

DROP TABLE IF EXISTS tad_tipo_tramite;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipo_tramite (
  ID int(19) NOT NULL,
  REPARTICION_INICIADORA varchar(255) DEFAULT NULL,
  REQUISITOS_TRAMITE varchar(255) DEFAULT NULL,
  TIPO_TRAMITE varchar(255) DEFAULT NULL,
  TRATA int(19) NOT NULL,
  USUARIO_INICIADOR varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_expedient_transaction`
--

DROP TABLE IF EXISTS te_expedient_transaction;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_expedient_transaction (
  ID int(19) NOT NULL,
  ID_EXPEDIENT int(19) NOT NULL,
  ID_TRANSACTION varchar(200) NOT NULL,
  DATE_CREATION datetime DEFAULT NULL,
  `CODE` varchar(200) DEFAULT NULL,
  MESSAGE varchar(200) DEFAULT NULL,
  `STATUS` int(1) NOT NULL,
  ID_OPERATION int(19) DEFAULT NULL,
  PRIMARY KEY (ID,ID_TRANSACTION),
  KEY ID_EXPEDIENT (ID_EXPEDIENT)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_expediente_formulario`
--

DROP TABLE IF EXISTS te_expediente_formulario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_expediente_formulario (
  ID_DF_TRANSACTION int(19) NOT NULL,
  ID_EE_EXPEDIENT int(19) NOT NULL,
  ID_DEF_FORM int(19) NOT NULL,
  DATE_CREATION datetime DEFAULT NULL,
  USER_CREATION varchar(20) NOT NULL DEFAULT '0',
  FORM_NAME varchar(50) NOT NULL DEFAULT '0',
  OBSERVATION varchar(200) DEFAULT '0',
  ORGANISM varchar(100) NOT NULL DEFAULT '0',
  ISDEFINITIVE double NOT NULL DEFAULT '0',
  PRIMARY KEY (ID_DF_TRANSACTION),
  KEY FROM_NAME_IDX (FORM_NAME),
  KEY ID_DF_TRANSACTION_IDX (ID_DF_TRANSACTION),
  KEY ID_EE_EXPEDIENT_IDX (ID_EE_EXPEDIENT)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_log_esb`
--

DROP TABLE IF EXISTS te_log_esb;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_log_esb (
  ID int(19) NOT NULL,
  ID_MENSAJE varchar(400) DEFAULT NULL,
  ID_TRANSACCION varchar(400) DEFAULT NULL,
  CODIGO_RECEPCION varchar(400) DEFAULT NULL,
  DESCRIPCION_RECEPCION varchar(400) DEFAULT NULL,
  ID_EXPEDIENTE int(19) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_operacion`
--

DROP TABLE IF EXISTS te_operacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_operacion (
  ID int(19) NOT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  FECHA_FIN datetime DEFAULT NULL,
  NUM_OFICIAL varchar(45) DEFAULT NULL,
  ESTADO_BLOQ varchar(10) DEFAULT NULL,
  TIPO_OPERACION_ID decimal(10,0) NOT NULL DEFAULT '0',
  EXECUTION_DBID_ varchar(50) NOT NULL DEFAULT '',
  ESTADO_OPERACION varchar(255) DEFAULT NULL,
  ID_SECTOR_INTERNO int(19) NOT NULL,
  VERSION_PROCEDURE int(10) DEFAULT NULL,
  USUARIO_CREADOR varchar(255) DEFAULT NULL,
  ID_REPARTICION int(19) DEFAULT NULL,
  PRIMARY KEY (ID,TIPO_OPERACION_ID,EXECUTION_DBID_),
  KEY fk_TE_OPERACION_TE_TIPO_OPERACION1_idx (TIPO_OPERACION_ID),
  KEY FK585A96AA875FE9A5 (TIPO_OPERACION_ID),
  KEY ID_SECTOR_INTERNO (ID_SECTOR_INTERNO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_operacion_doc`
--

DROP TABLE IF EXISTS te_operacion_doc;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_operacion_doc (
  ID_OPERACION int(19) NOT NULL,
  ID_GEDO_TIPODOCUMENTO int(19) NOT NULL,
  NUMERO_DOCUMENTO varchar(60) NOT NULL COMMENT 'Numero de documento generado en DEO (la relación se hace a través de este campo)',
  NOMBRE_ARCHIVO varchar(100) NOT NULL,
  PRIMARY KEY (ID_OPERACION,ID_GEDO_TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_operacion_ee`
--

DROP TABLE IF EXISTS te_operacion_ee;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_operacion_ee (
  ID_OPERACION int(19) DEFAULT NULL,
  ID_SUBPROCESO int(19) DEFAULT NULL,
  ID_EXPEDIENTE_ELECTRONICO int(19) NOT NULL,
  KEY FKD5E45E551DB28061 (ID_EXPEDIENTE_ELECTRONICO),
  KEY FKD5E45E5531A898B8 (ID_SUBPROCESO),
  KEY FKD5E45E55B16B8723 (ID_OPERACION),
  KEY FKD5E45E55C152CD6E (ID_OPERACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_project`
--

DROP TABLE IF EXISTS te_project;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_project (
  `NAME` varchar(200) NOT NULL,
  AUTHOR varchar(200) DEFAULT NULL,
  DESCRIPTION varchar(300) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  JSON_MODEL blob,
  TYPE_WORKFLOW varchar(20) DEFAULT NULL,
  VERSION_PROCEDURE int(10) DEFAULT NULL,
  PRIMARY KEY (`NAME`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_registry_transaction`
--

DROP TABLE IF EXISTS te_registry_transaction;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_registry_transaction (
  ID int(19) NOT NULL,
  ID_TRANSACTION varchar(200) NOT NULL,
  REQUEST_DATE datetime DEFAULT NULL,
  RESPONSE_DATE datetime DEFAULT NULL,
  MESSAGE varchar(200) DEFAULT NULL,
  ID_MENSSAGE varchar(400) DEFAULT NULL,
  RECEPTION_CODE varchar(400) DEFAULT NULL,
  RECEPTION_DESCRIPTION varchar(400) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY ID_TRANSACTION (ID_TRANSACTION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_subprocess`
--

DROP TABLE IF EXISTS te_subprocess;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_subprocess (
  ID int(19) NOT NULL,
  STATEFLOW varchar(200) NOT NULL,
  LOCK_TYPE varchar(200) NOT NULL,
  STATENAME varchar(200) NOT NULL,
  ID_PROCEDURE int(19) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  PROCEDURE_NAME varchar(20) DEFAULT NULL,
  START_TYPE varchar(20) DEFAULT NULL,
  SCRIPT_END blob,
  SCRIPT_START blob,
  VERSION_PROCEDURE int(10) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FKEBFF6D2E8EEAD0B3 (ID_PROCEDURE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_subprocess_production`
--

DROP TABLE IF EXISTS te_subprocess_production;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_subprocess_production (
  ID int(19) NOT NULL,
  STATEFLOW varchar(200) NOT NULL,
  LOCK_TYPE varchar(200) NOT NULL,
  STATENAME varchar(200) NOT NULL,
  ID_PROCEDURE int(19) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  START_TYPE varchar(20) DEFAULT NULL,
  SCRIPT_START blob,
  SCRIPT_END blob,
  PROCEDURE_NAME varchar(20) DEFAULT NULL,
  VERSION_PROCEDURE int(10) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FKBA3B5C6A8EEAD0B3 (ID_PROCEDURE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_tipo_oper_documento`
--

DROP TABLE IF EXISTS te_tipo_oper_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_tipo_oper_documento (
  ID_TIPO_OPERACION int(19) NOT NULL,
  ID_GEDO_TIPODOCUMENTO int(19) NOT NULL,
  OPCIONAL binary(1) NOT NULL DEFAULT '0',
  OBLIGATORIO binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (ID_TIPO_OPERACION,ID_GEDO_TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_tipo_oper_form`
--

DROP TABLE IF EXISTS te_tipo_oper_form;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_tipo_oper_form (
  ID_FORMULARIO int(19) NOT NULL,
  ID_TIPO_OPERACION int(19) NOT NULL,
  KEY FK_TE_TIPO_OPER_ID_FORM (ID_FORMULARIO),
  KEY FK_TE_TIPO_OPER_ID_OPER (ID_TIPO_OPERACION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_tipo_oper_organismo`
--

DROP TABLE IF EXISTS te_tipo_oper_organismo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_tipo_oper_organismo (
  ID_TIPO_OPERACION int(19) NOT NULL,
  ID_ORGANISMO int(19) NOT NULL,
  PRIMARY KEY (ID_TIPO_OPERACION,ID_ORGANISMO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `te_tipo_operacion`
--

DROP TABLE IF EXISTS te_tipo_operacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE te_tipo_operacion (
  ID int(19) NOT NULL,
  CODIGO varchar(45) NOT NULL,
  NOMBRE varchar(250) NOT NULL,
  DESCRIPCION varchar(2000) DEFAULT NULL,
  DBID_ decimal(24,0) NOT NULL,
  ESTADO binary(1) NOT NULL,
  PRIMARY KEY (ID,DBID_),
  KEY fk_TIPO_OPERACION_JBPM_DEPLYPROP1_idx (DBID_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_actividad`
--

DROP TABLE IF EXISTS tipo_actividad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipo_actividad (
  ID int(19) NOT NULL,
  NOMBRE varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  FORM varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_documento`
--

DROP TABLE IF EXISTS tipo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipo_documento (
  ID int(19) NOT NULL,
  NOMBRE varchar(255) DEFAULT NULL,
  ACRONIMO varchar(11) DEFAULT NULL,
  ESESPECIAL char(1) DEFAULT NULL,
  ID_TIPO_DOCUMENTOSADE int(19) DEFAULT NULL,
  CODIGO_TIPO_DOCUMENTOSADE varchar(10) DEFAULT NULL,
  USO_EE varchar(255) DEFAULT NULL,
  UNIQUE KEY PRIMARY_33 (ID),
  KEY IDX_USO_EE (USO_EE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_documento_depuracion`
--

DROP TABLE IF EXISTS tipo_documento_depuracion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipo_documento_depuracion (
  ID int(19) NOT NULL,
  TIPO_DOC_ACRONIMO varchar(11) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_documento_vinculado`
--

DROP TABLE IF EXISTS tipo_documento_vinculado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipo_documento_vinculado (
  ID int(19) DEFAULT NULL,
  NOMBRE varchar(20) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_reserva`
--

DROP TABLE IF EXISTS tipo_reserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipo_reserva (
  ID int(19) DEFAULT NULL,
  TIPO_RESERVA varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipos_datos_propios`
--

DROP TABLE IF EXISTS tipos_datos_propios;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipos_datos_propios (
  ID int(19) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  ABREVIACION varchar(45) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata`
--

DROP TABLE IF EXISTS trata;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata (
  ID int(19) NOT NULL,
  CODIGO_TRATA varchar(255) DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  ID_RESERVA int(19) DEFAULT NULL,
  ES_AUTOMATICA int(11) DEFAULT NULL,
  ES_MANUAL int(11) DEFAULT NULL,
  ACRONIMO_DOC_GEDO varchar(255) DEFAULT NULL,
  NOMBRE_WORKFLOW varchar(255) DEFAULT NULL,
  TIEMPO_RESOLUCION double DEFAULT NULL,
  TIPO_ACTUACION varchar(10) DEFAULT NULL,
  ES_INTERNO int(11) DEFAULT NULL,
  ES_EXTERNO int(11) DEFAULT NULL,
  CLAVE_TAD varchar(11) DEFAULT NULL,
  ES_NOTIFICABLE_TAD int(11) DEFAULT NULL,
  ENVIO_AUTOMATICO_GT int(11) DEFAULT NULL,
  NOTIFICABLE_JMS int(11) DEFAULT NULL,
  INTEGRA_SIS_EXT int(11) DEFAULT NULL,
  INTEGRACION int(11) DEFAULT NULL,
  INTEGRACION_AFJG int(11) DEFAULT NULL,
  descripcion varchar(255) DEFAULT NULL,
  TIPO_TRAMITE varchar(100) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY PRIMARY_34 (ID),
  KEY FK4C5FB30573A2340 (ID_RESERVA),
  KEY FK4C5FB30AADCE62C (ID_RESERVA),
  KEY IDX_ENVIO_AUTO_GT (ENVIO_AUTOMATICO_GT)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_auditoria`
--

DROP TABLE IF EXISTS trata_auditoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_auditoria (
  ID int(19) NOT NULL,
  CODIGO_TRATA varchar(255) DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL,
  ID_RESERVA int(19) DEFAULT NULL,
  ES_AUTOMATICA int(11) DEFAULT NULL,
  ES_MANUAL int(11) DEFAULT NULL,
  TIPOOPERACION varchar(255) DEFAULT NULL,
  FECHAOPERACION datetime DEFAULT NULL,
  USERNAME varchar(255) DEFAULT NULL,
  WORKFLOW varchar(255) DEFAULT NULL,
  TIEMPO_ESTIMADO decimal(10,0) DEFAULT NULL,
  TIPO_ACTUACION varchar(10) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  ES_INTERNO int(11) DEFAULT NULL,
  ES_EXTERNO int(11) DEFAULT NULL,
  CLAVE_TAD varchar(11) DEFAULT NULL,
  ES_NOTIFICABLE_TAD int(11) DEFAULT NULL,
  ENVIO_AUTOMATICO_GT int(11) DEFAULT NULL,
  NOTIFICABLE_JMS int(11) NOT NULL,
  INTEGRA_SIS_EXT int(11) DEFAULT NULL,
  INTEGRACION int(11) DEFAULT NULL,
  NOTIFICABLE_JMS_2 int(11) DEFAULT NULL,
  INTEGRACION_AFJG int(11) DEFAULT NULL,
  UNIQUE KEY PRIMARY_35 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_integracion_reparticion`
--

DROP TABLE IF EXISTS trata_integracion_reparticion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_integracion_reparticion (
  ID int(19) NOT NULL,
  ID_TRATA int(19) DEFAULT NULL,
  CODIGO_REPARTICION varchar(255) DEFAULT NULL,
  ID_PARAMETRO_SISTEMA_EXTERNO double DEFAULT NULL,
  HABILITADA double DEFAULT NULL,
  ORDEN double DEFAULT NULL,
  UNIQUE KEY TRATA_INTEGRACION_REPARTI_PK (ID),
  KEY FKC79555BF26F5FA34 (ID_PARAMETRO_SISTEMA_EXTERNO),
  KEY FKC79555BFB9868D48 (ID_PARAMETRO_SISTEMA_EXTERNO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_reparticion`
--

DROP TABLE IF EXISTS trata_reparticion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_reparticion (
  ID_TRATA int(19) NOT NULL,
  CODIGOREPARTICION varchar(255) NOT NULL,
  HABILITACION int(11) NOT NULL,
  ORDEN decimal(10,0) NOT NULL,
  RESERVA decimal(10,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_36 (ID_TRATA,ORDEN),
  KEY FKD3F939195BC32B44 (ID_TRATA),
  KEY FKD3F93919C5BD8F30 (ID_TRATA),
  KEY INDEX2 (ID_TRATA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_reparticion_auditoria`
--

DROP TABLE IF EXISTS trata_reparticion_auditoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_reparticion_auditoria (
  ID int(19) NOT NULL,
  ID_TRATA int(19) DEFAULT NULL,
  FECHAOPERACION datetime DEFAULT NULL,
  USUARIO varchar(45) DEFAULT NULL,
  TIPOOPERACION varchar(45) DEFAULT NULL,
  HABILITACION int(11) DEFAULT NULL,
  CODIGOREPARTICION varchar(45) DEFAULT NULL,
  RESERVA decimal(10,0) DEFAULT NULL,
  UNIQUE KEY PRIMARY_37 (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_tipo_documento`
--

DROP TABLE IF EXISTS trata_tipo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_tipo_documento (
  ID_TRATA_TIPO_DOCUMENTO int(19) NOT NULL,
  ACRONIMO_GEDO varchar(255) DEFAULT NULL,
  TRATA int(19) DEFAULT NULL,
  UNIQUE KEY PRIMARY_38 (ID_TRATA_TIPO_DOCUMENTO),
  KEY FKBEA7F91F745C3B68_1 (TRATA),
  KEY FKE0BAE438745C3B68 (TRATA),
  KEY FKE0BAE438DE569F54 (TRATA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_tipo_documento_auditoria`
--

DROP TABLE IF EXISTS trata_tipo_documento_auditoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_tipo_documento_auditoria (
  ID_TRATA_TIPO_DOCUMENTO int(19) NOT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  NOMBRE varchar(45) DEFAULT NULL,
  ACRONIMO_GEDO varchar(45) DEFAULT NULL,
  ESTADO varchar(45) DEFAULT NULL,
  TRATA int(19) DEFAULT NULL,
  UNIQUE KEY PRIMARY_1 (ID_TRATA_TIPO_DOCUMENTO),
  KEY FK5A586D2F745C3B68 (TRATA),
  KEY FK5A586D2FDE569F54 (TRATA),
  KEY FKBEA7F91F745C3B68 (TRATA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trata_tipo_resultado`
--

DROP TABLE IF EXISTS trata_tipo_resultado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE trata_tipo_resultado (
  ID_TRATA int(19) NOT NULL,
  CLAVE_TIPO_RESULTADO varchar(50) NOT NULL,
  PRIMARY KEY (ID_TRATA,CLAVE_TIPO_RESULTADO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tratas_diferenciales`
--

DROP TABLE IF EXISTS tratas_diferenciales;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tratas_diferenciales (
  ID int(19) DEFAULT NULL,
  CODIGO_TRATA varchar(255) DEFAULT NULL,
  ESTADO varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_migracion`
--

DROP TABLE IF EXISTS usuario_migracion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE usuario_migracion (
  NOMBRE_USUARIO varchar(50) NOT NULL,
  ID int(19) NOT NULL,
  CODIGO_SECTOR_INTERNO varchar(20) DEFAULT NULL,
  CODIGO_REPARTICION varchar(20) DEFAULT NULL,
  UNIQUE KEY USUARIO_MIGRACION_PK (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wd_bitacora`
--

DROP TABLE IF EXISTS wd_bitacora;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE wd_bitacora (
  ID int(19) NOT NULL,
  PROYECTO varchar(200) DEFAULT NULL,
  VERSION varchar(20) DEFAULT NULL,
  USUARIO varchar(100) DEFAULT NULL,
  FECHA datetime DEFAULT NULL,
  DESCRIPCION varchar(500) DEFAULT NULL,
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

-- Dump completed on 2017-08-30  9:58:45
CREATE DATABASE  IF NOT EXISTS `qa_numerador_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_numerador_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_numerador_egov_docker
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
-- Table structure for table `num_caratula_sector_usu`
--

DROP TABLE IF EXISTS num_caratula_sector_usu;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE num_caratula_sector_usu (
  ID_NUMERO_SADE_CARATULA double NOT NULL,
  USUARIO varchar(4000) NOT NULL,
  CODIGO_SECTOR_INTERNO varchar(4000) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_proceso_batch`
--

DROP TABLE IF EXISTS numero_proceso_batch;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_proceso_batch (
  ID_PROCESO_BATCH int(10) NOT NULL,
  ANIO decimal(10,0) NOT NULL,
  SISTEMA varchar(45) NOT NULL,
  CANTIDAD_REGISTROS_ACTUALIZADO decimal(10,0) NOT NULL,
  ESTADO varchar(500) NOT NULL,
  FECHA_INICIO_PROCESO datetime NOT NULL,
  FECHA_FIN_PROCESO datetime NOT NULL,
  PRIMARY KEY (ID_PROCESO_BATCH)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_sade`
--

DROP TABLE IF EXISTS numero_sade;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_sade (
  ID_NUMERO_SADE int(10) NOT NULL,
  ANIO decimal(10,0) NOT NULL,
  NUMERO decimal(10,0) NOT NULL,
  SECUENCIA varchar(9) NOT NULL,
  ESTADO varchar(15) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  FECHA_MODIFICACION datetime NOT NULL,
  PRIMARY KEY (ID_NUMERO_SADE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_sade_caratula`
--

DROP TABLE IF EXISTS numero_sade_caratula;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_sade_caratula (
  ID_NUMERO_SADE_CARATULA int(10) NOT NULL,
  ID_NUMERO_SADE int(10) NOT NULL,
  SISTEMA varchar(45) NOT NULL,
  USUARIO varchar(45) NOT NULL,
  FECHA_CREACION_CARATULA datetime NOT NULL,
  REPARTICION_ACTUACION varchar(45) NOT NULL,
  REPARTICION_USUARIO varchar(45) NOT NULL,
  TIPO_ACTUACION varchar(45) NOT NULL,
  PRIMARY KEY (ID_NUMERO_SADE_CARATULA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_sade_secuencia`
--

DROP TABLE IF EXISTS numero_sade_secuencia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_sade_secuencia (
  ID_SECUENCIA int(10) NOT NULL,
  ANIO decimal(10,0) NOT NULL,
  NUMERO decimal(10,0) NOT NULL,
  PRIMARY KEY (ID_SECUENCIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_sade_trabajo`
--

DROP TABLE IF EXISTS numero_sade_trabajo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_sade_trabajo (
  ID_SECUENCIA_USADA_DIARIA int(10) NOT NULL,
  SISTEMA varchar(20) NOT NULL,
  USUARIO varchar(30) NOT NULL,
  TIPO_ACTUACION varchar(5) NOT NULL,
  ANIO decimal(10,0) NOT NULL,
  NUMERO decimal(10,0) NOT NULL,
  SECUENCIA varchar(3) NOT NULL,
  REPARTICION_ACTUACION varchar(4000) NOT NULL,
  REPARTICION_USUARIO varchar(4000) DEFAULT NULL,
  FECHA_CREACION datetime NOT NULL,
  PRIMARY KEY (ID_SECUENCIA_USADA_DIARIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numero_sistemas_sade`
--

DROP TABLE IF EXISTS numero_sistemas_sade;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE numero_sistemas_sade (
  ID_SISTEMA int(10) NOT NULL,
  NOMBRE_SISTEMA varchar(30) NOT NULL,
  LINK_SISTEMA varchar(100) NOT NULL,
  ACTIVO char(1) DEFAULT NULL,
  PRIMARY KEY (ID_SISTEMA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_configuration`
--

DROP TABLE IF EXISTS property_configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE property_configuration (
  CLAVE varchar(50) NOT NULL,
  VALOR varchar(1024) NOT NULL,
  CONFIGURACION varchar(1024) DEFAULT NULL,
  PRIMARY KEY (CLAVE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS qrtz_blob_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_blob_triggers (
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  BLOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS qrtz_calendars;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_calendars (
  CALENDAR_NAME varchar(80) DEFAULT NULL,
  CALENDAR longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS qrtz_cron_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_cron_triggers (
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  CRON_EXPRESSION varchar(80) DEFAULT NULL,
  TIME_ZONE_ID varchar(80) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS qrtz_fired_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_fired_triggers (
  ENTRY_ID varchar(95) DEFAULT NULL,
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  IS_VOLATILE varchar(1) DEFAULT NULL,
  INSTANCE_NAME varchar(80) DEFAULT NULL,
  FIRED_TIME decimal(24,0) DEFAULT NULL,
  PRIORITY decimal(10,0) DEFAULT NULL,
  STATE varchar(16) DEFAULT NULL,
  JOB_NAME varchar(80) DEFAULT NULL,
  JOB_GROUP varchar(80) DEFAULT NULL,
  IS_STATEFUL varchar(1) DEFAULT NULL,
  REQUESTS_RECOVERY varchar(1) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS qrtz_job_details;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_job_details (
  JOB_NAME varchar(80) DEFAULT NULL,
  JOB_GROUP varchar(80) DEFAULT NULL,
  DESCRIPTION varchar(120) DEFAULT NULL,
  JOB_CLASS_NAME varchar(128) DEFAULT NULL,
  IS_DURABLE varchar(1) DEFAULT NULL,
  IS_VOLATILE varchar(1) DEFAULT NULL,
  IS_STATEFUL varchar(1) DEFAULT NULL,
  REQUESTS_RECOVERY varchar(1) DEFAULT NULL,
  JOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_listeners`
--

DROP TABLE IF EXISTS qrtz_job_listeners;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_job_listeners (
  JOB_NAME varchar(80) DEFAULT NULL,
  JOB_GROUP varchar(80) DEFAULT NULL,
  JOB_LISTENER varchar(80) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS qrtz_locks;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_locks (
  LOCK_NAME varchar(40) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_paused_trigger_grps (
  TRIGGER_GROUP varchar(80) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS qrtz_scheduler_state;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_scheduler_state (
  INSTANCE_NAME varchar(80) DEFAULT NULL,
  LAST_CHECKIN_TIME decimal(24,0) DEFAULT NULL,
  CHECKIN_INTERVAL decimal(24,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS qrtz_simple_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_simple_triggers (
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  REPEAT_COUNT decimal(24,0) DEFAULT NULL,
  REPEAT_INTERVAL decimal(24,0) DEFAULT NULL,
  TIMES_TRIGGERED decimal(24,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_trigger_listeners`
--

DROP TABLE IF EXISTS qrtz_trigger_listeners;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_trigger_listeners (
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  TRIGGER_LISTENER varchar(80) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS qrtz_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_triggers (
  TRIGGER_NAME varchar(80) DEFAULT NULL,
  TRIGGER_GROUP varchar(80) DEFAULT NULL,
  JOB_NAME varchar(80) DEFAULT NULL,
  JOB_GROUP varchar(80) DEFAULT NULL,
  IS_VOLATILE varchar(1) DEFAULT NULL,
  DESCRIPTION varchar(120) DEFAULT NULL,
  NEXT_FIRE_TIME decimal(24,0) DEFAULT NULL,
  PREV_FIRE_TIME decimal(24,0) DEFAULT NULL,
  PRIORITY decimal(10,0) DEFAULT NULL,
  TRIGGER_STATE varchar(16) DEFAULT NULL,
  TRIGGER_TYPE varchar(8) DEFAULT NULL,
  START_TIME decimal(24,0) DEFAULT NULL,
  END_TIME decimal(24,0) DEFAULT NULL,
  CALENDAR_NAME varchar(80) DEFAULT NULL,
  MISFIRE_INSTR int(11) DEFAULT NULL,
  JOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30  9:58:43
CREATE DATABASE  IF NOT EXISTS `qa_vuc_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_vuc_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_vuc_egov_docker
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
-- Table structure for table `estado_tarea`
--

DROP TABLE IF EXISTS estado_tarea;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE estado_tarea (
  ID int(11) NOT NULL,
  DESCRIPCION varchar(25) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estado_tramite`
--

DROP TABLE IF EXISTS estado_tramite;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE estado_tramite (
  ID int(11) NOT NULL,
  DESCRIPCION varchar(25) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `revinfo`
--

DROP TABLE IF EXISTS revinfo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE revinfo (
  REV double NOT NULL,
  REVTSTMP double DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_apoderado_persona`
--

DROP TABLE IF EXISTS tad_apoderado_persona;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_apoderado_persona (
  ID int(19) NOT NULL,
  ID_TITULAR int(19) NOT NULL,
  ID_APODERADO int(19) NOT NULL,
  ESTADO tinyint(1) NOT NULL,
  FECHA_VENCIMIENTO datetime DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  TIPO_REPRESENTACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_campo`
--

DROP TABLE IF EXISTS tad_campo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_campo (
  ID int(19) NOT NULL,
  LABEL varchar(255) DEFAULT NULL,
  COMPONENTE varchar(255) DEFAULT NULL,
  ID_TIPO_TRAMITE decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_contenido`
--

DROP TABLE IF EXISTS tad_contenido;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_contenido (
  ID int(19) NOT NULL,
  CODIGO varchar(30) DEFAULT NULL,
  VALOR_STRING longtext,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_MODI datetime DEFAULT NULL,
  USUARIO_CREACION varchar(30) DEFAULT NULL,
  USUARIO_MODI varchar(30) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_denuncia`
--

DROP TABLE IF EXISTS tad_dc_denuncia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_denuncia (
  ID decimal(19,0) DEFAULT NULL,
  DETALLE_DENUNCIA varchar(3500) DEFAULT NULL,
  ID_TIPO_MOTIVO decimal(19,0) DEFAULT NULL,
  ID_TIPO_DENUNCIA decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  RESUMEN varchar(1000) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_denunciado`
--

DROP TABLE IF EXISTS tad_dc_denunciado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_denunciado (
  ID decimal(19,0) DEFAULT NULL,
  CUIT varchar(255) DEFAULT NULL,
  RAZON_SOCIAL varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  COD_EMPRESA double DEFAULT NULL,
  ID_DOMICILIO decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_denunciante`
--

DROP TABLE IF EXISTS tad_dc_denunciante;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_denunciante (
  ID decimal(19,0) NOT NULL,
  ID_PERSONA decimal(19,0) NOT NULL,
  COD_PERSONA double NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_problematica`
--

DROP TABLE IF EXISTS tad_dc_problematica;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_problematica (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_PROBLEMATICA varchar(255) DEFAULT NULL,
  ID_SUB_RUBRO decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_reenvio`
--

DROP TABLE IF EXISTS tad_dc_reenvio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_reenvio (
  ID decimal(19,0) NOT NULL,
  ID_EXPEDIENTE_DEF_CONSUMIDOR decimal(19,0) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_rubro`
--

DROP TABLE IF EXISTS tad_dc_rubro;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_rubro (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_RUBRO varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_servicio_denunciado`
--

DROP TABLE IF EXISTS tad_dc_servicio_denunciado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_servicio_denunciado (
  ID decimal(19,0) NOT NULL,
  ID_PROBLEMATICA decimal(19,0) DEFAULT NULL,
  ID_TIPO_PAGO decimal(19,0) DEFAULT NULL,
  ID_TIPO_ADQUISICION decimal(19,0) DEFAULT NULL,
  FECHA_ADQUISICION datetime DEFAULT NULL,
  DESCRIPCION varchar(300) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_sub_rubro`
--

DROP TABLE IF EXISTS tad_dc_sub_rubro;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_sub_rubro (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_SUB_RUBRO varchar(255) DEFAULT NULL,
  ID_RUBRO decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_t_den_t_doc_t_prueba`
--

DROP TABLE IF EXISTS tad_dc_t_den_t_doc_t_prueba;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_t_den_t_doc_t_prueba (
  ID_DC_TIPO_DENUNCIA decimal(19,0) NOT NULL,
  ID_TIPO_DOCUMENTO decimal(19,9) NOT NULL,
  ID_DC_TIPO_PRUEBA decimal(19,0) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_tipo_adquisicion`
--

DROP TABLE IF EXISTS tad_dc_tipo_adquisicion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_tipo_adquisicion (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_TIPO_ADQUISICION varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_tipo_denuncia`
--

DROP TABLE IF EXISTS tad_dc_tipo_denuncia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_tipo_denuncia (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_TIPO_DENUNCIA varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_tipo_motivo`
--

DROP TABLE IF EXISTS tad_dc_tipo_motivo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_tipo_motivo (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_TIPO_MOTIVO varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_tipo_pago`
--

DROP TABLE IF EXISTS tad_dc_tipo_pago;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_tipo_pago (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_TIPO_PAGO varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_dc_tipo_prueba`
--

DROP TABLE IF EXISTS tad_dc_tipo_prueba;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_dc_tipo_prueba (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento`
--

DROP TABLE IF EXISTS tad_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento (
  ID int(19) NOT NULL,
  ANIO int(10) DEFAULT NULL,
  BAJA_LOGICA tinyint(1) DEFAULT NULL,
  NUMERO int(10) DEFAULT NULL,
  REFERENCIA varchar(255) DEFAULT NULL,
  REPARTICION varchar(255) DEFAULT NULL,
  ACTUACION varchar(255) DEFAULT NULL,
  ID_PERSONA int(19) DEFAULT NULL,
  ID_TIPO_DOCUMENTO int(19) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(255) DEFAULT NULL,
  VERSION int(19) DEFAULT NULL,
  ID_TRANSACCION double DEFAULT NULL,
  URL_TEMPORAL varchar(255) DEFAULT NULL,
  NOMBRE_ORIGINAL varchar(255) DEFAULT NULL COMMENT 'Original file name of the uploaded document.',
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento_externo`
--

DROP TABLE IF EXISTS tad_documento_externo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento_externo (
  ID double NOT NULL,
  MOTIVO varchar(500) DEFAULT NULL,
  ID_PERSONA double DEFAULT NULL,
  FECHA_ENVIO datetime NOT NULL,
  CODIGO_EE varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento_externo_detalle`
--

DROP TABLE IF EXISTS tad_documento_externo_detalle;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento_externo_detalle (
  ID double NOT NULL,
  DOCUMENTO varchar(50) NOT NULL,
  ID_DOCUMENTO_EXTERNO double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento_fc`
--

DROP TABLE IF EXISTS tad_documento_fc;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento_fc (
  ID decimal(19,0) NOT NULL,
  ID_EXPEDIENTE_BASE decimal(19,0) NOT NULL,
  ID_TRANSACCION decimal(19,0) DEFAULT NULL,
  ID_TIPO_DOCUMENTO decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  URL_TEMPORAL varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_documento_tvs`
--

DROP TABLE IF EXISTS tad_documento_tvs;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_documento_tvs (
  ID double NOT NULL,
  ID_EXPEDIENTE_BASE double NOT NULL,
  DOCUMENTO_SADE varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_domicilio`
--

DROP TABLE IF EXISTS tad_domicilio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_domicilio (
  ID decimal(19,0) NOT NULL,
  DIRECCION varchar(255) DEFAULT NULL,
  PISO varchar(255) DEFAULT NULL,
  DEPTO varchar(255) DEFAULT NULL,
  LOCALIDAD varchar(255) DEFAULT NULL,
  COD_POSTAL varchar(255) DEFAULT NULL,
  TELEFONO varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  ALTURA varchar(6) DEFAULT NULL,
  `LOCAL` varchar(6) DEFAULT NULL,
  CGPC varchar(100) DEFAULT NULL,
  COMISARIA varchar(100) DEFAULT NULL,
  PROVINCIA varchar(255) DEFAULT NULL,
  DEPARTAMENTO varchar(255) DEFAULT NULL,
  OBSERVACIONES varchar(500) DEFAULT NULL,
  EMAIL varchar(100) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_domicilio_aud`
--

DROP TABLE IF EXISTS tad_domicilio_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_domicilio_aud (
  ID decimal(19,0) NOT NULL,
  REVISION decimal(19,0) NOT NULL,
  TIPO_REVISION int(11) DEFAULT NULL,
  DIRECCION varchar(255) DEFAULT NULL,
  PISO varchar(255) DEFAULT NULL,
  DEPTO varchar(255) DEFAULT NULL,
  LOCALIDAD varchar(255) DEFAULT NULL,
  COD_POSTAL varchar(255) DEFAULT NULL,
  TELEFONO varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  ALTURA varchar(6) DEFAULT NULL,
  `LOCAL` varchar(6) DEFAULT NULL,
  CGPC varchar(100) DEFAULT NULL,
  COMISARIA varchar(100) DEFAULT NULL,
  PROVINCIA varchar(255) DEFAULT NULL,
  DEPARTAMENTO varchar(255) DEFAULT NULL,
  OBSERVACIONES varchar(500) DEFAULT NULL,
  EMAIL varchar(100) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_equivalencia_nom`
--

DROP TABLE IF EXISTS tad_equivalencia_nom;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_equivalencia_nom (
  ID decimal(19,0) NOT NULL,
  TRATA_CONCURSO varchar(255) NOT NULL,
  ID_TRATA_DESIGNACION decimal(10,0) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_error_log`
--

DROP TABLE IF EXISTS tad_error_log;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_error_log (
  ID double NOT NULL,
  TIPO_ERROR varchar(255) DEFAULT NULL,
  DETALLE_ERROR varchar(1000) DEFAULT NULL,
  ID_PERSONA double DEFAULT NULL,
  FECHA datetime DEFAULT NULL,
  ID_EXPEDIENTE double DEFAULT NULL,
  ID_TIPO_TRAMITE double DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_error_patron`
--

DROP TABLE IF EXISTS tad_error_patron;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_error_patron (
  ID double NOT NULL,
  PATRON varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_error_sade`
--

DROP TABLE IF EXISTS tad_error_sade;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_error_sade (
  ID double NOT NULL,
  SISTEMA_ORIGEN varchar(255) DEFAULT NULL,
  TIPO_EXCEPCION_SADE varchar(255) DEFAULT NULL,
  DETALLE_SADE varchar(4000) DEFAULT NULL,
  ID_ERROR_USUARIO double DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_error_usuario`
--

DROP TABLE IF EXISTS tad_error_usuario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_error_usuario (
  ID double NOT NULL,
  DESCRIPCION varchar(4000) DEFAULT NULL,
  NOTIFICABLE char(1) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_alta_dominio`
--

DROP TABLE IF EXISTS tad_expediente_alta_dominio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_alta_dominio (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  ID_TRANSACCION decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  IP_SOLICITUD varchar(100) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_base`
--

DROP TABLE IF EXISTS tad_expediente_base;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_base (
  ID int(19) NOT NULL,
  ANIO int(10) DEFAULT NULL,
  DESCRIPCION_ADICIONAL varchar(255) DEFAULT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  NUMERO int(10) DEFAULT NULL,
  REPARTICION_ACTUACION varchar(255) DEFAULT NULL,
  REPARTICION_USUARIO varchar(255) DEFAULT NULL,
  TIPO varchar(255) DEFAULT NULL,
  ULTIMO_INGRESO datetime DEFAULT NULL,
  ID_PERSONA int(19) DEFAULT NULL,
  TIPO_TRAMITE_ID int(19) DEFAULT NULL,
  BAJA_LOGICA int(11) DEFAULT NULL,
  PASO_ACTUAL int(10) DEFAULT NULL,
  VERSION int(19) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  TURNO varchar(255) DEFAULT NULL,
  ID_APODERADO int(19) DEFAULT NULL,
  ID_INTERVINIENTE int(19) DEFAULT NULL,
  TOMANDO_VISTA int(11) DEFAULT NULL,
  ID_EXP_PADRE int(19) DEFAULT NULL,
  SE_VA_A_REINTENTAR tinyint(1) DEFAULT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  ID_ESTADO_TRAMITE int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_def_consumidor`
--

DROP TABLE IF EXISTS tad_expediente_def_consumidor;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_def_consumidor (
  ID_DENUNCIA decimal(19,0) DEFAULT NULL,
  ID_DENUNCIADO decimal(19,0) DEFAULT NULL,
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  ID_SERVICIO_DENUNCIADO decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  ID_DENUNCIANTE decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_denunciado`
--

DROP TABLE IF EXISTS tad_expediente_denunciado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_denunciado (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  ID_DENUNCIADO decimal(19,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_documento`
--

DROP TABLE IF EXISTS tad_expediente_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_documento (
  ID_EXPEDIENTE int(19) NOT NULL,
  ID_DOCUMENTO int(19) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_fam_fc`
--

DROP TABLE IF EXISTS tad_expediente_fam_fc;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_fam_fc (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  ID_TRANSACCION varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_fam_nomb`
--

DROP TABLE IF EXISTS tad_expediente_fam_nomb;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_fam_nomb (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  FECHA_NOTIFICACION datetime DEFAULT NULL,
  FECHA_TURNERA datetime DEFAULT NULL,
  COD_SADE_EXPEDIENTE_ORIGEN varchar(255) DEFAULT NULL,
  TRATA varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_fam_solicitud`
--

DROP TABLE IF EXISTS tad_expediente_fam_solicitud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_fam_solicitud (
  DETALLE_SOLICITUD varchar(1500) DEFAULT NULL,
  ID_EXPEDIENTE int(19) NOT NULL,
  VERSION int(19) DEFAULT NULL,
  PRIMARY KEY (ID_EXPEDIENTE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_fam_super_trata`
--

DROP TABLE IF EXISTS tad_expediente_fam_super_trata;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_fam_super_trata (
  ID_EXPEDIENTE double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_reintento`
--

DROP TABLE IF EXISTS tad_expediente_reintento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_reintento (
  ID int(11) NOT NULL,
  ID_EXPEDIENTE_BASE double NOT NULL,
  CV_SADE varchar(255) DEFAULT NULL,
  CV_ID_TRANSACCION varchar(20) DEFAULT NULL,
  DATOS_PASO_1 varchar(4000) DEFAULT NULL,
  FECHA_ERROR datetime NOT NULL,
  FECHA_REININTENTO datetime DEFAULT NULL,
  ESTADO varchar(255) NOT NULL,
  PASO double NOT NULL,
  INTENTO double NOT NULL,
  `ERROR` varchar(500) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_rpa_adm`
--

DROP TABLE IF EXISTS tad_expediente_rpa_adm;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_rpa_adm (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  MATRICULA decimal(19,0) DEFAULT NULL,
  CONSORCIOS int(11) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  ANIO_MATRICULA int(11) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_expediente_rpa_consorcio`
--

DROP TABLE IF EXISTS tad_expediente_rpa_consorcio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_expediente_rpa_consorcio (
  ID_EXPEDIENTE decimal(19,0) NOT NULL,
  ANIO_ADMINISTRACION varchar(15) DEFAULT NULL,
  MATRICULA decimal(19,0) DEFAULT NULL,
  ID_DOMICILIO decimal(19,0) DEFAULT NULL,
  CUIT varchar(15) DEFAULT NULL,
  ASAMBLEA_ORDINARIA int(11) DEFAULT NULL,
  NUMERO_OBLEA int(11) DEFAULT NULL,
  CERTIFICACION int(11) DEFAULT NULL,
  FECHA_EMISION datetime DEFAULT NULL,
  FECHA_NO_CONFORMIDAD datetime DEFAULT NULL,
  MOTIVO_NO_CONFORMIDAD varchar(1000) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_formulario_controlado`
--

DROP TABLE IF EXISTS tad_formulario_controlado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_formulario_controlado (
  ID_FORMULARIO_CONTROLADO decimal(19,0) NOT NULL,
  ID_ACRONIMO varchar(255) DEFAULT NULL,
  NOMBRE varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_grupo`
--

DROP TABLE IF EXISTS tad_grupo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_grupo (
  ID decimal(19,0) NOT NULL,
  NOMBRE varchar(100) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_matricula`
--

DROP TABLE IF EXISTS tad_matricula;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_matricula (
  ID double NOT NULL,
  MATRICULA decimal(19,0) DEFAULT NULL,
  FECHA_VENCIMIENTO datetime DEFAULT NULL,
  CUIT decimal(19,0) DEFAULT NULL,
  ANIO_PENDIENTE1 int(11) DEFAULT NULL,
  ANIO_PENDIENTE2 int(11) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_metadato`
--

DROP TABLE IF EXISTS tad_metadato;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_metadato (
  ID decimal(19,0) NOT NULL,
  NOMBRE varchar(255) DEFAULT NULL,
  OBLIGATORIO int(11) DEFAULT NULL,
  TIPO int(11) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_TIPO_TRAMITE decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_metadatos_reintento`
--

DROP TABLE IF EXISTS tad_metadatos_reintento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_metadatos_reintento (
  ID double NOT NULL,
  ID_EXPEDIENTE_REINTENTO double NOT NULL,
  CLAVE varchar(255) NOT NULL,
  VALOR varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_notificacion`
--

DROP TABLE IF EXISTS tad_notificacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_notificacion (
  ID int(19) NOT NULL,
  ID_EXPEDIENTE_BASE int(19) DEFAULT NULL,
  CODSADE varchar(255) NOT NULL,
  MOTIVO varchar(500) NOT NULL,
  FECHA_NOTIFICACION datetime NOT NULL,
  USUARIO_CREACION varchar(255) NOT NULL,
  NOTIFICADO tinyint(1) DEFAULT NULL,
  BAJA_LOGICA tinyint(1) DEFAULT NULL,
  VERSION int(19) DEFAULT NULL,
  ID_PERSONA double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_paso`
--

DROP TABLE IF EXISTS tad_paso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_paso (
  ID decimal(19,0) NOT NULL,
  DESCRIPCION_PASO varchar(20) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_persona`
--

DROP TABLE IF EXISTS tad_persona;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_persona (
  ID int(19) NOT NULL,
  DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  RAZON_SOCIAL varchar(255) DEFAULT NULL,
  TELEFONO_CONTACTO varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  CUIT varchar(255) DEFAULT NULL,
  APELLIDO1 varchar(255) DEFAULT NULL,
  NOMBRE1 varchar(255) DEFAULT NULL,
  ID_DOMICILIO_CONSTITUIDO decimal(19,0) DEFAULT NULL,
  ID_DOMICILIO_REAL decimal(19,0) DEFAULT NULL,
  ID_TERMINOS decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  SEXO varchar(10) DEFAULT NULL,
  NOMBRE2 varchar(255) DEFAULT NULL,
  NOMBRE3 varchar(255) DEFAULT NULL,
  APELLIDO2 varchar(255) DEFAULT NULL,
  APELLIDO3 varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  NUMERO_DOCUMENTO varchar(255) DEFAULT NULL,
  estado varchar(20) NOT NULL DEFAULT 'ACTIVO',
  `password` varchar(150) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_persona_aud`
--

DROP TABLE IF EXISTS tad_persona_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_persona_aud (
  ID decimal(19,0) NOT NULL,
  REVISION decimal(19,0) NOT NULL,
  TIPO_REVISION int(11) DEFAULT NULL,
  DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  NOMBRE_APELLIDO varchar(255) DEFAULT NULL,
  RAZON_SOCIAL varchar(255) DEFAULT NULL,
  TELEFONO_CONTACTO varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO_IDENTIDAD varchar(255) DEFAULT NULL,
  CUIT varchar(255) DEFAULT NULL,
  APELLIDO1 varchar(255) DEFAULT NULL,
  NOMBRE1 varchar(255) DEFAULT NULL,
  ID_DOMICILIO_CONSTITUIDO decimal(19,0) DEFAULT NULL,
  ID_DOMICILIO_REAL decimal(19,0) DEFAULT NULL,
  ID_TERMINOS decimal(19,0) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  SEXO varchar(10) DEFAULT NULL,
  NOMBRE2 varchar(255) DEFAULT NULL,
  NOMBRE3 varchar(255) DEFAULT NULL,
  APELLIDO2 varchar(255) DEFAULT NULL,
  APELLIDO3 varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  NUMERO_DOCUMENTO varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_pre_validacion_tramite`
--

DROP TABLE IF EXISTS tad_pre_validacion_tramite;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_pre_validacion_tramite (
  ID double NOT NULL,
  CUIT varchar(50) NOT NULL,
  ID_TIPO_TRAMITE double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_reserva_dominio`
--

DROP TABLE IF EXISTS tad_reserva_dominio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_reserva_dominio (
  ID double NOT NULL,
  ID_ZONA double NOT NULL,
  NOMBRE_DOMINIO varchar(500) NOT NULL,
  ID_EXPEDIENTE double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_sexo`
--

DROP TABLE IF EXISTS tad_sexo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_sexo (
  ID decimal(19,0) NOT NULL,
  SEXO varchar(255) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_super_trata`
--

DROP TABLE IF EXISTS tad_super_trata;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_super_trata (
  ID double NOT NULL,
  ID_TRAMITE_PADRE double NOT NULL,
  ID_TRAMITE_HIJO double NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_t_tramite_t_documento`
--

DROP TABLE IF EXISTS tad_t_tramite_t_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_t_tramite_t_documento (
  ID int(19) NOT NULL AUTO_INCREMENT,
  ID_TIPO_TRAMITE decimal(19,0) NOT NULL,
  ID_TIPO_DOCUMENTO int(19) NOT NULL,
  OBLIGATORIO tinyint(1) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  CANTIDAD int(11) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_tad_t_tramite_t_documento_tad_tipo_documento (ID_TIPO_DOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tarea`
--

DROP TABLE IF EXISTS tad_tarea;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tarea (
  ID int(19) NOT NULL,
  FECHA datetime DEFAULT NULL,
  ID_EXPEDIENTE decimal(19,0) DEFAULT NULL,
  MOTIVO varchar(250) DEFAULT NULL,
  ENVIADO_POR varchar(150) DEFAULT NULL,
  BAJA_LOGICA int(11) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  ID_INTERVINIENTE decimal(19,0) DEFAULT NULL COMMENT 'Id de la tabla id_persona',
  TIPO varchar(100) DEFAULT NULL,
  CUIT_DESTINO varchar(255) DEFAULT NULL,
  ID_ESTADO int(11) NOT NULL COMMENT 'Estado del tramite',
  NOTIFICADO tinyint(1) NOT NULL COMMENT 'Usuario ha visto la tarea.',
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tarea_paso`
--

DROP TABLE IF EXISTS tad_tarea_paso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tarea_paso (
  ID_TAREA decimal(19,0) NOT NULL,
  ID_PASO decimal(19,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tarea_tipo_documento`
--

DROP TABLE IF EXISTS tad_tarea_tipo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tarea_tipo_documento (
  ID_TAREA decimal(19,0) NOT NULL,
  ID_TIPO_DOCUMENTO decimal(19,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_terminos_condiciones`
--

DROP TABLE IF EXISTS tad_terminos_condiciones;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_terminos_condiciones (
  ID decimal(19,0) NOT NULL,
  ID_TIPO_DOCUMENTO decimal(19,0) DEFAULT NULL,
  ESTADO tinyint(1) DEFAULT NULL,
  FECHA datetime DEFAULT NULL,
  CODIGO_CONTENIDO varchar(30) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipado_documento`
--

DROP TABLE IF EXISTS tad_tipado_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipado_documento (
  ID decimal(19,0) NOT NULL,
  TIPADO varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipo_doc_form_ctrol`
--

DROP TABLE IF EXISTS tad_tipo_doc_form_ctrol;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipo_doc_form_ctrol (
  FK_TIPO_DOCUMENTO int(18) NOT NULL,
  FK_FORMULARIO_CONTROLADO int(18) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipo_documento`
--

DROP TABLE IF EXISTS tad_tipo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipo_documento (
  ID int(19) NOT NULL,
  ACRONIMO_GEDO varchar(11) DEFAULT NULL,
  ACRONIMO_TAD varchar(50) DEFAULT NULL,
  NOMBRE varchar(255) DEFAULT NULL,
  USUARIO_INICIADOR varchar(255) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  NOMBRE_FORMULARIO_CONTROLADO varchar(255) DEFAULT NULL,
  ID_TIPADO_DOCUMENTO decimal(19,0) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  ESTADO tinyint(1) DEFAULT NULL,
  DETALLE_FC varchar(255) DEFAULT NULL,
  INCLUIDO_EN_SUPERTRATA tinyint(1) DEFAULT NULL,
  FIRMA_CON_TOKEN tinyint(1) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipo_documento_identidad`
--

DROP TABLE IF EXISTS tad_tipo_documento_identidad;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipo_documento_identidad (
  ID int(19) NOT NULL,
  CODIGO varchar(255) NOT NULL,
  DESCRIPCION varchar(255) NOT NULL,
  VERSION decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_tipo_tramite`
--

DROP TABLE IF EXISTS tad_tipo_tramite;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_tipo_tramite (
  ID int(19) NOT NULL,
  REPARTICION_INICIADORA varchar(255) DEFAULT NULL,
  TRATA varchar(255) DEFAULT NULL,
  USUARIO_INICIADOR varchar(255) DEFAULT NULL,
  NOMBRE varchar(500) DEFAULT NULL,
  NOMBRE_FORMULARIO varchar(255) DEFAULT NULL,
  PATH_INICIAL varchar(255) DEFAULT NULL,
  REQUISITOS_COD_CONTENIDO varchar(30) DEFAULT NULL,
  ESTADO varchar(20) NOT NULL,
  ID_TIPO_DOC_FORMULARIO int(19) DEFAULT NULL,
  VERSION int(19) DEFAULT NULL,
  SECTOR_INICIADOR varchar(255) DEFAULT NULL,
  ID_GRUPO int(19) DEFAULT NULL,
  MOTIVO varchar(250) DEFAULT NULL,
  MENSAJE_EXITO varchar(4000) DEFAULT NULL,
  APODERABLE int(11) DEFAULT NULL,
  ANEXO1 varchar(4000) DEFAULT NULL,
  ANEXO3 varchar(4000) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  TURNO tinyint(1) NOT NULL,
  PAGO tinyint(1) DEFAULT NULL,
  ID_TRAMITE_TURNO int(11) DEFAULT NULL,
  ARCHIVO_TRABAJO int(11) NOT NULL,
  INTERVINIENTE tinyint(1) NOT NULL,
  PREVALIDACION tinyint(1) NOT NULL,
  SUPER_TRATA tinyint(1) DEFAULT NULL,
  DIAS_GUARDADO int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_zona_cuit`
--

DROP TABLE IF EXISTS tad_zona_cuit;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_zona_cuit (
  ID double NOT NULL,
  ID_ZONA double NOT NULL,
  CUIT varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tad_zona_dominio`
--

DROP TABLE IF EXISTS tad_zona_dominio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tad_zona_dominio (
  ID double NOT NULL,
  ZONA varchar(20) NOT NULL,
  TRATA varchar(65) NOT NULL,
  TRATA_BANQUINADO varchar(65) NOT NULL,
  ESPECIAL char(1) NOT NULL,
  TRATA_ACTUALIZACION varchar(65) DEFAULT NULL,
  TRATA_ACTUALIZACION_BANQUINADO varchar(65) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vuc_expediente_formulario`
--

DROP TABLE IF EXISTS vuc_expediente_formulario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE vuc_expediente_formulario (
  ID_DF_TRANSACTION int(10) NOT NULL COMMENT 'ID DE LA TABLA GEDO_GED.DF_TRANSACTION PK',
  VUC_ID_EXPEDIENT int(10) NOT NULL COMMENT 'ID TABLA TAD_EXPEDIENTE_BASE',
  FORM_NAME varchar(50) DEFAULT NULL COMMENT 'NOMBRE DEL FORMULARIO ASOCIADO AL EXPEDIENTE, RELACION CON GEDO_GED.DF_FORM.NAME',
  DATE_CREATION datetime DEFAULT NULL COMMENT 'FECHA DE CREACION DEL REGISTRO DE RELACION FORMULARIO-EXPEDIENTE',
  ID_TYPE_FORM int(10) NOT NULL COMMENT 'ID DE TABLA VUC_FORM_TYPE',
  PRIMARY KEY (ID_DF_TRANSACTION)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vuc_form_type`
--

DROP TABLE IF EXISTS vuc_form_type;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE vuc_form_type (
  ID_TYPE_FORM int(10) NOT NULL,
  FFDD_ACRONYM varchar(50) DEFAULT NULL,
  VUC_ACRONYM varchar(50) DEFAULT NULL,
  FORM_NAME varchar(50) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  DATE_CREATION datetime DEFAULT NULL,
  USER_CREATION varchar(20) DEFAULT NULL,
  VERSION int(19) DEFAULT NULL,
  PRIMARY KEY (ID_TYPE_FORM)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vuc_t_tramite_t_form`
--

DROP TABLE IF EXISTS vuc_t_tramite_t_form;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE vuc_t_tramite_t_form (
  ID int(19) NOT NULL,
  ID_TYPE_TRAMITE int(19) NOT NULL,
  ID_TYPE_FORM int(19) NOT NULL,
  MANDATORY int(11) DEFAULT NULL,
  DATE_CREATION datetime DEFAULT NULL,
  DATE_MODIFY datetime DEFAULT NULL,
  QUANTITY int(11) DEFAULT NULL,
  ORDER_ADD int(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY VUC_T_TRAMITE_T_FORM_TRATA_FK (ID_TYPE_TRAMITE)
);
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30  9:58:46
CREATE DATABASE  IF NOT EXISTS `qa_deo_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_deo_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_deo_egov_docker
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
-- Table structure for table `cc_acknowledge`
--

DROP TABLE IF EXISTS cc_acknowledge;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_acknowledge (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_MENSAJE varchar(20) DEFAULT NULL,
  ID_TRANSACCION varchar(20) DEFAULT NULL,
  CODIGO_RECEPCION varchar(20) DEFAULT NULL,
  DESCRIPCION_RECEPCION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_address`
--

DROP TABLE IF EXISTS cc_address;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_address (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(256) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  DIRECCION varchar(60) DEFAULT NULL,
  COMUNA varchar(60) DEFAULT NULL,
  REGION varchar(60) DEFAULT NULL,
  PAIS varchar(60) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_almacenista`
--

DROP TABLE IF EXISTS cc_almacenista;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_almacenista (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ALMACENISTA varchar(50) DEFAULT NULL,
  FECHA_RECEPCION_MERCANCIAS date DEFAULT NULL,
  FECHA_RETIRO_MERCANCIAS date DEFAULT NULL,
  NUMERO_REGISTRO_RECONOCIMIENTO varchar(20) DEFAULT NULL,
  ANO_REGISTRO_RECONOCIMIENTO varchar(20) DEFAULT NULL,
  CODIGO_REGLA_UNO_PROCEDIMIENTO_AFORO varchar(20) DEFAULT NULL,
  NUMERO_RESOLUCION_REGLA_UNO varchar(20) DEFAULT NULL,
  ANO_RESOLUCION_REGLA_UNO varchar(20) DEFAULT NULL,
  CODIGO_ULTIMA_RESOLUCION_REGLA_UNO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_audit`
--

DROP TABLE IF EXISTS cc_audit;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_audit (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  UPDATE_BY varchar(20) DEFAULT NULL,
  UPDATE_DATE date DEFAULT NULL,
  CREATE_BY varchar(20) DEFAULT NULL,
  CREATE_DATE date DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_autorizacion`
--

DROP TABLE IF EXISTS cc_autorizacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_autorizacion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_AUTORIZACION int(11) DEFAULT NULL,
  OPERACION_ID varchar(20) DEFAULT NULL,
  SSPP varchar(20) DEFAULT NULL,
  SUBMIT_BY varchar(20) DEFAULT NULL,
  SUBMIT_DATE date DEFAULT NULL,
  RESPUESTA_DATE date DEFAULT NULL,
  RESPONSE_STATUS varchar(20) DEFAULT NULL,
  RESPONSE_CODE varchar(20) DEFAULT NULL,
  VIGENCIA_DATE date DEFAULT NULL,
  OBSERVACIONES varchar(20) DEFAULT NULL,
  URL varchar(20) DEFAULT NULL,
  ID_DOCUMENTO int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_bulto`
--

DROP TABLE IF EXISTS cc_bulto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_bulto (
  ID int(11) NOT NULL,
  ID_BULTO bigint(20) DEFAULT NULL,
  SECUENCIA_BULTO int(11) DEFAULT NULL,
  TIPO_BULTO varchar(20) DEFAULT NULL,
  CANTIDAD_PAQUETES int(11) DEFAULT NULL,
  IDENTIFICADOR_BULTO varchar(20) DEFAULT NULL,
  SUB_CONTINENTE varchar(20) DEFAULT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_chapter`
--

DROP TABLE IF EXISTS cc_chapter;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_chapter (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_CHAPTER int(11) DEFAULT NULL,
  CODE_CHAPTER varchar(20) DEFAULT NULL,
  DESC_CHAPTER varchar(20) DEFAULT NULL,
  DESC_SP_CHAPTER varchar(20) DEFAULT NULL,
  STATUS_CHAPTER varchar(20) DEFAULT NULL,
  `YEAR` int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_commoneda`
--

DROP TABLE IF EXISTS cc_commoneda;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_commoneda (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  OTHER_TAXES varchar(20) DEFAULT NULL,
  DECLARATION_NUMBER varchar(20) DEFAULT NULL,
  TYPE_EXPORT varchar(20) DEFAULT NULL,
  CARGO_CATEGORY varchar(20) DEFAULT NULL,
  TRANSACTION_NATURE varchar(20) DEFAULT NULL,
  EXPORTER_TYPE varchar(20) DEFAULT NULL,
  TRANSPORT_MODE varchar(20) DEFAULT NULL,
  CUSTOMS_STATION varchar(20) DEFAULT NULL,
  EXPORTER_CODE varchar(20) DEFAULT NULL,
  EXPORTER_DESC varchar(255) DEFAULT NULL,
  EXPORTER_ADDRESS varchar(255) DEFAULT NULL,
  EXPORTER_ZIP varchar(20) DEFAULT NULL,
  EXPORTER_PHONE varchar(20) DEFAULT NULL,
  CUSTOMS_WAREHOUSE varchar(20) DEFAULT NULL,
  PLANNED_DECLARANT varchar(20) DEFAULT NULL,
  CONSIGNEE_CODE varchar(20) DEFAULT NULL,
  CONSIGNEE_ADDRESS varchar(255) DEFAULT NULL,
  CONSIGNEE_ZIP varchar(20) DEFAULT NULL,
  CONSIGNEE_COUNTRY varchar(20) DEFAULT NULL,
  AWB varchar(20) DEFAULT NULL,
  PACKAGES_NUMBER varchar(20) DEFAULT NULL,
  GROSS_WEIGHT varchar(20) DEFAULT NULL,
  CONVEYANCE varchar(20) DEFAULT NULL,
  DEPARTURE_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LOADING_LOCATION varchar(20) DEFAULT NULL,
  FINAL_DESTINATION varchar(20) DEFAULT NULL,
  INVOICE_PRICE varchar(20) DEFAULT NULL,
  INCOTERMS varchar(20) DEFAULT NULL,
  INVOICE_CURRENCY varchar(20) DEFAULT NULL,
  INVOICE_TOTAL varchar(20) DEFAULT NULL,
  LC varchar(20) DEFAULT NULL,
  LCN varchar(64) DEFAULT NULL,
  LCD varchar(20) DEFAULT NULL,
  BAN varchar(64) DEFAULT NULL,
  AUTHENTICATION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_cuenta`
--

DROP TABLE IF EXISTS cc_cuenta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_cuenta (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  MONTO_CUENTA_OTR varchar(20) DEFAULT NULL,
  CODIGO varchar(20) DEFAULT NULL,
  MONTO decimal(10,0) DEFAULT NULL,
  CODIGO_CUENTA_OTR varchar(20) DEFAULT NULL,
  PORCENTAJE int(11) DEFAULT NULL,
  SIGNO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_declaracion`
--

DROP TABLE IF EXISTS cc_declaracion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_declaracion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  DECLARATION_ID int(11) DEFAULT NULL,
  DECLARACION_ENUM varchar(20) DEFAULT NULL,
  SUBMITTED_BY varchar(20) DEFAULT NULL,
  SUBMITTED_DATE date DEFAULT NULL,
  RESP_DATE date DEFAULT NULL,
  RESPONSE_STATUS varchar(20) DEFAULT NULL,
  RESPONSE_CODE varchar(20) DEFAULT NULL,
  URL varchar(60) DEFAULT NULL,
  FECHA_VENCIMIENTO date DEFAULT NULL,
  TIPO_FORMULARIO varchar(50) DEFAULT NULL,
  CODIGO_DECLARACION varchar(50) DEFAULT NULL,
  NUMERO_IDENTIFICACION varchar(20) DEFAULT NULL,
  DOCUMENTO_PARCIAL varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_detaileda`
--

DROP TABLE IF EXISTS cc_detaileda;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_detaileda (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  HS_CODE varchar(20) DEFAULT NULL,
  ITEM_CODE varchar(20) DEFAULT NULL,
  ITEM_NAME varchar(20) DEFAULT NULL,
  ORIGIN_CODE varchar(20) DEFAULT NULL,
  QUANTITY1 varchar(20) DEFAULT NULL,
  UNIT_CODE1 varchar(20) DEFAULT NULL,
  QUANTITY2 varchar(20) DEFAULT NULL,
  UNIT_CODE2 varchar(20) DEFAULT NULL,
  CMP varchar(20) DEFAULT NULL,
  BP_CURRENCY varchar(20) DEFAULT NULL,
  BASIC_PRICE varchar(20) DEFAULT NULL,
  TAX_ER varchar(20) DEFAULT NULL,
  TAX_ER_AMOUNT varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_detalle_cuota`
--

DROP TABLE IF EXISTS cc_detalle_cuota;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_detalle_cuota (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGO_UNO varchar(20) DEFAULT NULL,
  CODIGO_DOS varchar(20) DEFAULT NULL,
  FECHA varchar(20) DEFAULT NULL,
  MONTO decimal(10,0) DEFAULT NULL,
  FINANCIERO_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_detalles_documento_instalacion`
--

DROP TABLE IF EXISTS cc_detalles_documento_instalacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_detalles_documento_instalacion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  NOMBNRE_SSPP varchar(20) DEFAULT NULL,
  CODIGO_DOCUMENTO varchar(20) DEFAULT NULL,
  NOMBRE_DOCUMENTO varchar(20) DEFAULT NULL,
  ID_SOLICITUD varchar(20) DEFAULT NULL,
  NOMBRE_INSTALACION varchar(20) DEFAULT NULL,
  REGION_INSTALACION varchar(20) DEFAULT NULL,
  COMUNA_INSTALACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_detalles_operacion_impo`
--

DROP TABLE IF EXISTS cc_detalles_operacion_impo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_detalles_operacion_impo (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ESTADO_OP varchar(20) DEFAULT NULL,
  NUMERO_OP varchar(20) DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  TIPO_OPERACION varchar(20) DEFAULT NULL,
  TIPO_INGRESO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_detallespuerto`
--

DROP TABLE IF EXISTS cc_detallespuerto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_detallespuerto (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_DETALLESPUERTO int(11) DEFAULT NULL,
  PAIS varchar(20) DEFAULT NULL,
  COD_PUERTO varchar(50) DEFAULT NULL,
  TIPO_PUERTO varchar(20) DEFAULT NULL,
  NOMBRE_PUERTO varchar(50) DEFAULT NULL,
  LOCACION_PUERTO varchar(50) DEFAULT NULL,
  FECHA date DEFAULT NULL,
  TIPO_PUERTOENTRADA varchar(20) DEFAULT NULL,
  REGION varchar(20) DEFAULT NULL,
  FECHA_ESTIMADA date DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_documento`
--

DROP TABLE IF EXISTS cc_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_documento (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(256) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_DOCUMENTO int(11) DEFAULT NULL,
  COD_DOCUMENTO varchar(20) DEFAULT NULL,
  NOMBRE_DOCUMENTO varchar(20) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(20) DEFAULT NULL,
  ID_PARTICIPANTE int(11) DEFAULT NULL,
  FECHA_DOCUMENTO timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PAIS_DOCUMENTO varchar(20) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_documento_apoyo`
--

DROP TABLE IF EXISTS cc_documento_apoyo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_documento_apoyo (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(256) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_DOCUMENTO_APOYO int(19) DEFAULT NULL,
  SECUENCIA_NRO int(11) DEFAULT NULL,
  IDDOCUMENTO varchar(20) DEFAULT NULL,
  FECHA_DOCUMENTO date DEFAULT NULL,
  COMENTARIOS varchar(50) DEFAULT NULL,
  ADJUNTO varchar(50) DEFAULT NULL,
  NUMERO_DOCUMENTO varchar(50) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(50) DEFAULT NULL,
  EMISOR_DOCUMENTO varchar(50) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_documento_transporte`
--

DROP TABLE IF EXISTS cc_documento_transporte;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_documento_transporte (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(20) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_DOC_TRANSPORTE int(11) DEFAULT NULL,
  SEQ_DOC_TRANSPORTE int(11) DEFAULT NULL,
  NUM_DOC_TRANSPORTE int(11) DEFAULT NULL,
  FECHA_DOC_TRANSPORTE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  TIPO_DOC_TRANSPORTE varchar(20) DEFAULT NULL,
  NOMBRE_NAVE varchar(20) DEFAULT NULL,
  NUM_VIAJE int(11) DEFAULT NULL,
  NUM_MANIFIESTO varchar(20) DEFAULT NULL,
  ID_NAVE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_error`
--

DROP TABLE IF EXISTS cc_error;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_error (
  CODIGO varchar(20) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  PRIMARY KEY (CODIGO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_financiero`
--

DROP TABLE IF EXISTS cc_financiero;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_financiero (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  VALOR_LIQUIDO_RETORNO decimal(10,0) DEFAULT NULL,
  VALOR_FLETE decimal(10,0) DEFAULT NULL,
  VALOR_EXFABRICA decimal(10,0) DEFAULT NULL,
  VALOR_CLAUSULA_VENTA decimal(10,0) DEFAULT NULL,
  VALOR_CIF decimal(10,0) DEFAULT NULL,
  TOTAL_VALOR_FOB decimal(10,0) DEFAULT NULL,
  TOTAL_DIFERIDO int(11) DEFAULT NULL,
  TASA_INTERES int(11) DEFAULT NULL,
  TASA_CAMBIO varchar(20) DEFAULT NULL,
  SEGURO_TEORICO varchar(20) DEFAULT NULL,
  REGIMEN_IMPORTACION varchar(20) DEFAULT NULL,
  PROVEEDOR_O_FABRICANTE varchar(20) DEFAULT NULL,
  PLAZO_PAGO varchar(20) DEFAULT NULL,
  PAIS_ADQUISICION varchar(20) DEFAULT NULL,
  OTROS_GASTOS_DEDUCIBLES decimal(10,0) DEFAULT NULL,
  ORIGEN_DIVISAS varchar(20) DEFAULT NULL,
  OBS_BANCOS_NA varchar(20) DEFAULT NULL,
  NUM_FACTURA varchar(20) DEFAULT NULL,
  NUMERO_CUOTAS_PAGO_DIFERIDO varchar(20) DEFAULT NULL,
  MONTO_TOTAL_ADVALOREM decimal(10,0) DEFAULT NULL,
  MONTO_TOTAL decimal(10,0) DEFAULT NULL,
  MONTO_SEGURO decimal(10,0) DEFAULT NULL,
  MONEDA varchar(20) DEFAULT NULL,
  MODALIDAD_VENTA varchar(20) DEFAULT NULL,
  IVA varchar(20) DEFAULT NULL,
  ID_PAGO_DIFERIDO varchar(20) DEFAULT NULL,
  GASTOS_HASTA_FOB decimal(10,0) DEFAULT NULL,
  FORMA_PAGO_GRAVAMENES varchar(20) DEFAULT NULL,
  FORMA_PAGO varchar(20) DEFAULT NULL,
  FLETE_TEORICO varchar(20) DEFAULT NULL,
  FECHA_PAGO_DIFERIDO date DEFAULT NULL,
  FECHA_PAGO date DEFAULT NULL,
  FEC_FACTURA date DEFAULT NULL,
  FACTURA_ID bigint(20) DEFAULT NULL,
  FACTURA_COMERCIAL_DEFINITIVA varchar(20) DEFAULT NULL,
  DESCUENTO decimal(10,0) DEFAULT NULL,
  DECLARACION_ID bigint(20) DEFAULT NULL,
  CUOTAS varchar(20) DEFAULT NULL,
  CUOTA_CONTADO int(11) DEFAULT NULL,
  COMISIONES_EXTERIOR decimal(10,0) DEFAULT NULL,
  CODIGO_TOTAL_ADVALOREM varchar(20) DEFAULT NULL,
  CODIGO_SEGURO varchar(20) DEFAULT NULL,
  CODIGO_FLETE varchar(20) DEFAULT NULL,
  CODIGO_BANCO_COMERCIAL varchar(20) DEFAULT NULL,
  CLAUSULA_VENTA varchar(20) DEFAULT NULL,
  CLAUSULA_COMPRA varchar(20) DEFAULT NULL,
  ADUANA_DIPAGODIF varchar(20) DEFAULT NULL,
  CODIGO_UNO int(11) DEFAULT NULL,
  PARTICIPANTE_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_header`
--

DROP TABLE IF EXISTS cc_header;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_header (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_HEADER bigint(20) DEFAULT NULL,
  DESTINACION_ADUANERA varchar(20) DEFAULT NULL,
  TIPO_OPERACION varchar(20) DEFAULT NULL,
  TIPO_TRAMITE varchar(20) DEFAULT NULL,
  ADUANA_TRAMITACION varchar(20) DEFAULT NULL,
  NUMERO_INTERNO_DESPACHO varchar(20) DEFAULT NULL,
  COMENTARIO varchar(20) DEFAULT NULL,
  TIPO_INGRESO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_heading`
--

DROP TABLE IF EXISTS cc_heading;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_heading (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_HEADING int(11) DEFAULT NULL,
  CHAPTER int(11) DEFAULT NULL,
  ID_CHAPTER int(11) DEFAULT NULL,
  CODE_HEADING varchar(20) DEFAULT NULL,
  DESC_HEADING varchar(20) DEFAULT NULL,
  DESC_SP_HEADING varchar(20) DEFAULT NULL,
  STATUS_HEADING varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_hoja_anexa`
--

DROP TABLE IF EXISTS cc_hoja_anexa;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_hoja_anexa (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NRO_SECUENCIA int(11) NOT NULL,
  NRO_DAPEX int(11) DEFAULT NULL,
  FECHA_DAPEX datetime DEFAULT NULL,
  ADUANA_TRAMITACION varchar(45) DEFAULT NULL,
  NRO_ITEM int(11) DEFAULT NULL,
  NRO_INSUMO int(11) DEFAULT NULL,
  NOMBRE_INSUMO varchar(45) DEFAULT NULL,
  COD_UNIDAD_MEDIDA varchar(45) DEFAULT NULL,
  NOMBRE_MERCANCIA int(11) DEFAULT NULL,
  CANTIDAD varchar(45) DEFAULT NULL,
  UNIDAD_MEDIDA_CANT varchar(45) DEFAULT NULL,
  FACTOR_CONSUMO varchar(45) DEFAULT NULL,
  INSUMOS_UTILIZADOS varchar(45) DEFAULT NULL,
  NRO_HOJA int(11) DEFAULT NULL,
  TOTAL_INSUMOS_HOJA int(11) DEFAULT NULL,
  TOTAL_INSUMOS_HOJAS_ANT int(11) DEFAULT NULL,
  TOTAL_FINAL_INSUMOS int(11) DEFAULT NULL,
  FECHA_CONTROL_VB datetime DEFAULT NULL,
  FECHA_FIRMA_DESPACHADOR datetime DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_hoja_insumos_datpa`
--

DROP TABLE IF EXISTS cc_hoja_insumos_datpa;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_hoja_insumos_datpa (
  RUT_IMPORTADOR varchar(15) DEFAULT NULL,
  CONSIGNATORIO varchar(45) DEFAULT NULL,
  CODIGO_DESPACHADOR varchar(45) DEFAULT NULL,
  ADUANA varchar(45) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_hs_code_matriz_cc`
--

DROP TABLE IF EXISTS cc_hs_code_matriz_cc;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_hs_code_matriz_cc (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  id_HSCode_Matriz int(11) DEFAULT NULL,
  id_Matriz_VB int(11) DEFAULT NULL,
  id_HS_Code int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_hscode`
--

DROP TABLE IF EXISTS cc_hscode;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_hscode (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  SUBHEADING_2 bigint(20) DEFAULT NULL,
  DESCRIPTION varchar(20) DEFAULT NULL,
  DESCRIPTION_OTHER varchar(20) DEFAULT NULL,
  CREATED_BY varchar(20) DEFAULT NULL,
  MODIFIED_BY varchar(20) DEFAULT NULL,
  MODIFIED_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONTROLLED_INDICATOR varchar(20) DEFAULT NULL,
  HS_CODE_ID bigint(20) DEFAULT NULL,
  HS_CODE varchar(20) DEFAULT NULL,
  DUTIABLE varchar(20) DEFAULT NULL,
  UOM_CODE varchar(20) DEFAULT NULL,
  SUBHEADING_ID bigint(20) DEFAULT NULL,
  CHAPTER int(11) DEFAULT NULL,
  `YEAR` bigint(4) DEFAULT NULL,
  EFFECTIVE_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CREATED_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATUS` varchar(20) DEFAULT NULL,
  HEADING int(11) DEFAULT NULL,
  SUBHEADING int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_instalacion`
--

DROP TABLE IF EXISTS cc_instalacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_instalacion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(256) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  OIG_PROPIE varchar(20) DEFAULT NULL,
  NOMBRE_INST varchar(20) DEFAULT NULL,
  DIRECCION_INST int(11) DEFAULT NULL,
  NUM_RESOL_AUT int(11) DEFAULT NULL,
  FECHA_EMI_RESOL timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ENTIDAD_EMI varchar(20) DEFAULT NULL,
  DIRECTOR_TECNICO int(11) DEFAULT NULL,
  TIPO_INST_DEST varchar(20) DEFAULT NULL,
  COD_INST_DEST varchar(20) DEFAULT NULL,
  TELEF_INST_DEST varchar(20) DEFAULT NULL,
  CONT_INST_DEST varchar(20) DEFAULT NULL,
  RAZON_SOCIAL varchar(20) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_insumos_datpa`
--

DROP TABLE IF EXISTS cc_insumos_datpa;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_insumos_datpa (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NUMERO varchar(30) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  CANTIDAD bigint(20) DEFAULT NULL,
  UNIDAD_MEDIDA_CANT varchar(30) DEFAULT NULL,
  NRO_HOJA int(11) DEFAULT NULL,
  NROI_ITEM int(11) DEFAULT NULL,
  VALOR_CIF_UNITARIO varchar(45) DEFAULT NULL,
  UNIDAD_MEDIDA_CIF varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_interop_header`
--

DROP TABLE IF EXISTS cc_interop_header;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_interop_header (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_TRANSACCION varchar(20) DEFAULT NULL,
  ID_MENSAJE varchar(20) DEFAULT NULL,
  DESTINATARIO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_item`
--

DROP TABLE IF EXISTS cc_item;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_item (
  ID int(11) NOT NULL,
  NOMBRE varchar(256) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  ID_PRODUCTO int(11) DEFAULT NULL,
  VALOR_MINIMO int(11) DEFAULT NULL,
  ACUERDO_COMERCIAL varchar(20) DEFAULT NULL,
  MONTO_UNITARIO_FOB int(11) DEFAULT NULL,
  MONTO_FOB int(11) DEFAULT NULL,
  PESO_BRUTO int(11) DEFAULT NULL,
  UNIDAD_PESO_BRUTO varchar(20) DEFAULT NULL,
  PESO_NETO_EMBARQUE int(11) DEFAULT NULL,
  UNIDAD_PN_EMBARQUE varchar(20) DEFAULT NULL,
  VOLUMEN_TOTAL int(11) DEFAULT NULL,
  UNIDAD_VOLUMEN varchar(20) DEFAULT NULL,
  CANT_MERCANCIAS int(11) DEFAULT NULL,
  UNIDAD_CANT_MERCANCIAS varchar(20) DEFAULT NULL,
  MONTO_AJUSTE varchar(20) DEFAULT NULL,
  SIGNO_AJUSTE varchar(20) DEFAULT NULL,
  SUJETO_CUPO varchar(20) DEFAULT NULL,
  COD_TRATADO_ARANCEL varchar(20) DEFAULT NULL,
  NUM_CORR_ARANCEL varchar(20) DEFAULT NULL,
  VALOR_CIF decimal(10,0) DEFAULT NULL,
  PORC_ADVALOREM int(11) DEFAULT NULL,
  COD_CUENTA_ADVALOREM varchar(20) DEFAULT NULL,
  MONTO_CUENTA_ADVALOREM decimal(10,0) DEFAULT NULL,
  PAIS_PRODUCCION varchar(20) DEFAULT NULL,
  ID_PRODUCTOR int(11) DEFAULT NULL,
  CUARENTENA_PF varchar(20) DEFAULT NULL,
  NUM_RESOLUCION varchar(20) DEFAULT NULL,
  MERC_DESTINO varchar(20) DEFAULT NULL,
  ID_BULTO int(11) DEFAULT NULL,
  ID_OPERATION int(11) DEFAULT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_lista_documento_ingreso`
--

DROP TABLE IF EXISTS cc_lista_documento_ingreso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_lista_documento_ingreso (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_SOLICITUD varchar(20) DEFAULT NULL,
  NOMBRE_SSPP varchar(20) DEFAULT NULL,
  NOMBRE_DOCUMENTO varchar(20) DEFAULT NULL,
  NOMBRE_INSTALACION_DESTINO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_lista_productos_di`
--

DROP TABLE IF EXISTS cc_lista_productos_di;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_lista_productos_di (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  `NUMBER` varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGO_PRODUCTO varchar(20) DEFAULT NULL,
  DESCRIPCION_PRODUCTO varchar(20) DEFAULT NULL,
  CANTIDAD_TOTAL varchar(20) DEFAULT NULL,
  CANTIDAD_UOM varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_lote`
--

DROP TABLE IF EXISTS cc_lote;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_lote (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(256) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  NUM_LOTE int(11) DEFAULT NULL,
  VALOR_LOTE varchar(20) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_manifiesto`
--

DROP TABLE IF EXISTS cc_manifiesto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_manifiesto (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  FECHA_MANIFIESTO date DEFAULT NULL,
  NUMERO_MANIFIESTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_medio_transporte`
--

DROP TABLE IF EXISTS cc_medio_transporte;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_medio_transporte (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  FECHA_ESTIMADA_LLEGADA date DEFAULT NULL,
  RUTA varchar(20) DEFAULT NULL,
  PATENTE varchar(20) DEFAULT NULL,
  ID_TRANSPORTISTA int(11) DEFAULT NULL,
  TIPO_MEDIO_TRANSPORTE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_mercancia`
--

DROP TABLE IF EXISTS cc_mercancia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_mercancia (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_MERCANCIA int(20) DEFAULT NULL,
  MERCANCIA_NACIONALIZADA int(2) DEFAULT NULL,
  REGION_ORIGEN varchar(45) DEFAULT NULL,
  PROPIOS_MEDIOS int(2) DEFAULT NULL,
  MERCANCIA_ZONA_PRIMARIA int(2) DEFAULT NULL,
  ADQUIRIDAS_CIF int(2) DEFAULT NULL,
  ORIGEN_CIF int(2) DEFAULT NULL,
  PAIS_ORIGEN varchar(45) DEFAULT NULL,
  PAIS_ADQUISICION varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_observacion`
--

DROP TABLE IF EXISTS cc_observacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_observacion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(256) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  OBSERVACION_CODE varchar(20) DEFAULT NULL,
  OBSERVACION_DESC varchar(20) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  ID_AUTORIZACION int(11) DEFAULT NULL,
  ID_DECLARACION int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_operation`
--

DROP TABLE IF EXISTS cc_operation;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_operation (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGO_OFICINA_ADUANA varchar(20) DEFAULT NULL,
  COD_OPERACION varchar(20) DEFAULT NULL,
  NOMBRE_AGENCIA_ADUANA varchar(20) DEFAULT NULL,
  PROCESSING_STATUS varchar(20) DEFAULT NULL,
  CODIGO_DESTINACION varchar(20) DEFAULT NULL,
  CODIGO_AGENCIA_ADUANAS varchar(20) DEFAULT NULL,
  CODIGO_TRANSBORDO varchar(20) DEFAULT NULL,
  ACTUALIZADO_POR varchar(20) DEFAULT NULL,
  CREADO_POR varchar(20) DEFAULT NULL,
  ID_TRANSPORT int(11) DEFAULT NULL,
  ID_CONTACTO int(11) DEFAULT NULL,
  ID_IMPORTADOR int(11) DEFAULT NULL,
  ID_CONSIGNATARIO int(11) DEFAULT NULL,
  ID_INTERMEDIARIO int(11) DEFAULT NULL,
  ID_EXPORTADOR_PRINCIPAL int(11) DEFAULT NULL,
  ID_REPRESENTANTE int(11) DEFAULT NULL,
  ID_EXPORTADOR_SECUNDARIO int(11) DEFAULT NULL,
  ID_INTEROP_HEADER int(11) DEFAULT NULL,
  ID_HEADER int(11) DEFAULT NULL,
  ID_FINANCIERO int(11) DEFAULT NULL,
  ID_DETALLES_PUERTO int(11) DEFAULT NULL,
  ID_DIN int(11) DEFAULT NULL,
  ID_DUS_AT int(11) DEFAULT NULL,
  ID_DUS_LEG int(11) DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  FECHA_ACTUALIZACION date DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_participante_secundario`
--

DROP TABLE IF EXISTS cc_participante_secundario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_participante_secundario (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(256) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_PARTI_SEC varchar(20) DEFAULT NULL,
  TIPO_PARTI_SEC varchar(20) DEFAULT NULL,
  DOC_TIPO_PARTI_SEC varchar(20) DEFAULT NULL,
  DOC_NUM_PARTI_SEC varchar(20) DEFAULT NULL,
  NOMBRE_PARTI_SEC varchar(20) DEFAULT NULL,
  ADDRESS_PARTI_SEC int(11) DEFAULT NULL,
  EMAIL_PARTI_SEC varchar(64) DEFAULT NULL,
  TEL_FIJO_PARTI_SEC varchar(20) DEFAULT NULL,
  TEL_MOVIL_PARTI_SEC varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_participantes`
--

DROP TABLE IF EXISTS cc_participantes;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_participantes (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(256) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PARTICIPANTE_ID int(20) DEFAULT NULL,
  PARTICIPANTE_TYPE varchar(50) DEFAULT NULL,
  DISPATCHER_CODE varchar(50) DEFAULT NULL,
  PERSONA_TYPE_ENUM varchar(50) DEFAULT NULL,
  DOC_PERSONA_TYPE varchar(50) DEFAULT NULL,
  DOC_PERSONA_NUM varchar(50) DEFAULT NULL,
  PARTICIPANTE_NOMBRE varchar(50) DEFAULT NULL,
  PARTICIPANTE_APELLIDO varchar(50) DEFAULT NULL,
  PARTICIPANTE_PORCENTAJE double DEFAULT NULL,
  PARTICIPANTE_ADDRESS varchar(50) DEFAULT NULL,
  PARTICIPANTE_EMAIL varchar(50) DEFAULT NULL,
  PARTICIPANTE_NUMERO_TELEFONO_FIJO varchar(50) DEFAULT NULL,
  PARTICIPANTE_NUMERO_TELEFONO_MOVIL varchar(50) DEFAULT NULL,
  CONTACTO varchar(50) DEFAULT NULL,
  ID_ITEM int(11) DEFAULT NULL,
  PERSONA_TYPE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_product`
--

DROP TABLE IF EXISTS cc_product;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_product (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRODUCT_ID bigint(20) DEFAULT NULL,
  PRODUCT_CODE varchar(20) DEFAULT NULL,
  HS_CODE varchar(20) DEFAULT NULL,
  SSPP varchar(20) DEFAULT NULL,
  COMMON_REG_NAME varchar(20) DEFAULT NULL,
  COMON_REG_NAME_SPA varchar(20) DEFAULT NULL,
  PROD_DESC varchar(20) DEFAULT NULL,
  PROD_DESC_SPA varchar(20) DEFAULT NULL,
  SCIENTIFIC_NAME varchar(20) DEFAULT NULL,
  BUSINESS_NAME varchar(20) DEFAULT NULL,
  BUSINESS_NAME_SPA varchar(20) DEFAULT NULL,
  PROD_CODE_SSPP varchar(20) DEFAULT NULL,
  PHYSICAL_STATE varchar(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  ALERT_MESSAGE varchar(20) DEFAULT NULL,
  DESTINACION varchar(20) DEFAULT NULL,
  PRODUCT_VERSION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_product_attributes`
--

DROP TABLE IF EXISTS cc_product_attributes;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_product_attributes (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(256) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRODUCT_ATTRIBUTE_ID bigint(20) DEFAULT NULL,
  PRODUCT_ID bigint(20) DEFAULT NULL,
  PRODUCT_CODE varchar(20) DEFAULT NULL,
  ATTRIBUTE_SEQUENCE bigint(20) DEFAULT NULL,
  ATTRIBUTE_CODE_NAME varchar(20) DEFAULT NULL,
  ATTRIBUTE_CODE_NAME_ESP varchar(20) DEFAULT NULL,
  ATTRIBUTE_VALUE varchar(20) DEFAULT NULL,
  ATTRIBUTE_DATA_TYPE varchar(20) DEFAULT NULL,
  ATTRIBUTE_DATA_SIZE varchar(20) DEFAULT NULL,
  MASTER_CODE_TYPE varchar(20) DEFAULT NULL,
  IS_FIXED varchar(20) DEFAULT NULL,
  IS_MANDATORY varchar(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  DESTINACION varchar(20) DEFAULT NULL,
  ATTRIBUTE_UNIQUE_CODE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_product_attributes_operation`
--

DROP TABLE IF EXISTS cc_product_attributes_operation;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_product_attributes_operation (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRODUCT_ATTRIBUTE_OPERACION_ID bigint(20) DEFAULT NULL,
  PRODUCTO_OPERACION_ID bigint(20) DEFAULT NULL,
  PRODUCT_ATTRIBUTE_ID bigint(20) DEFAULT NULL,
  PRODUCT_ID bigint(20) DEFAULT NULL,
  PRODUCT_CODE varchar(20) DEFAULT NULL,
  ATTRIBUTE_SEQ int(11) DEFAULT NULL,
  ATTRIBUTE_CODE_NAME varchar(20) DEFAULT NULL,
  ATTRIBUTE_CODE_NAME_ESP varchar(20) DEFAULT NULL,
  ATTRIBUTE_VALUE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_product_component`
--

DROP TABLE IF EXISTS cc_product_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_product_component (
  PRODUCT_COMPONENT_ID int(11) DEFAULT NULL,
  CODIGO varchar(50) DEFAULT NULL,
  NOMBRE varchar(20) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  PRINCIPIO_ACTIVO varchar(50) DEFAULT NULL,
  PORCENTAJE_CONSITUYENTE varchar(50) DEFAULT NULL,
  CONSTITUYENTE_SUOM varchar(255) DEFAULT NULL,
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE_PRODUCT_COMPONENT varchar(20) DEFAULT NULL,
  ORDEN int(11) NOT NULL,
  PRODUCT_OPERATION int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_product_operation`
--

DROP TABLE IF EXISTS cc_product_operation;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_product_operation (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PRODUCT_OPERACION_ID bigint(11) DEFAULT NULL,
  PRODUCT_ID bigint(11) DEFAULT NULL,
  PRODUCT_VERSION varchar(20) DEFAULT NULL,
  PRODUCT_CODE varchar(20) DEFAULT NULL,
  COMMON_NAME varchar(20) DEFAULT NULL,
  PROD_CODE_SSPP varchar(20) DEFAULT NULL,
  ARANCELARIO_COD varchar(20) DEFAULT NULL,
  PAIS_ORIGEN varchar(20) DEFAULT NULL,
  SSPP_ENUM varchar(20) DEFAULT NULL,
  ARANCELARIO_PROD varchar(20) DEFAULT NULL,
  CODIGO_COCHILCO varchar(20) DEFAULT NULL,
  CARACTERISTICA_ESPECIAL varchar(20) DEFAULT NULL,
  DESCRIPCION_PROD varchar(20) DEFAULT NULL,
  DESCRIPCION_ADIC varchar(20) DEFAULT NULL,
  CUOTA_CONTRACTUAL varchar(20) DEFAULT NULL,
  USO_PREVISTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_regimen_susp`
--

DROP TABLE IF EXISTS cc_regimen_susp;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_regimen_susp (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  COD_ADUANA_CTRL varchar(45) DEFAULT NULL,
  COD_COMUNA_ALMACEN varchar(45) DEFAULT NULL,
  PLAZO_VIGENCIA varchar(45) DEFAULT NULL,
  INDICADOR_PARCIALIDAD varchar(45) DEFAULT NULL,
  NRO_REGIMEN_SUS int(11) NOT NULL,
  FECHA_REGIMEN_SUSP datetime DEFAULT NULL,
  ADUANA_REGIMEN_SUSP varchar(45) DEFAULT NULL,
  NRO_HOJAS_ANEXAS int(11) DEFAULT NULL,
  PLAZO varchar(45) DEFAULT NULL,
  GLOSA varchar(45) DEFAULT NULL,
  DIR_ALMACENAMIENTO varchar(45) DEFAULT NULL,
  NRO_PASAPORTE varchar(45) DEFAULT NULL,
  NRO_HOJAS_INSUMO varchar(45) DEFAULT NULL,
  TOTAL_INSUMOS varchar(45) DEFAULT NULL,
  CANT_SECUENCIA int(11) DEFAULT NULL,
  NRO_TITV varchar(45) DEFAULT NULL,
  TIPO_REINGRESO varchar(45) DEFAULT NULL,
  RAZON_REINGRESO varchar(100) DEFAULT NULL,
  INDICADOR_BOLETA_POLIZA varchar(30) DEFAULT NULL,
  FECHA_BOLETA_POLIZA datetime DEFAULT NULL,
  COD_ALMACEN_PARTICULAR varchar(45) DEFAULT NULL,
  NRO_EMISION_BOLETA varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_reserva`
--

DROP TABLE IF EXISTS cc_reserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_reserva (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_RESERVA int(11) DEFAULT NULL,
  NUMERO_RESERVA_EMBARQUE varchar(20) DEFAULT NULL,
  NUMERO_REF_ENVIO varchar(20) DEFAULT NULL,
  NOBRE_PRINCIPAL varchar(255) DEFAULT NULL,
  NUMERO_ROTACION varchar(20) DEFAULT NULL,
  UCT varchar(20) DEFAULT NULL,
  BUQUE_REG_MOMENTO_LLEGADA varchar(255) DEFAULT NULL,
  BUQUE_REG_FRONTERA varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_resumen_declaracion_ingreso`
--

DROP TABLE IF EXISTS cc_resumen_declaracion_ingreso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_resumen_declaracion_ingreso (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  NUMERO_OP varchar(20) DEFAULT NULL,
  DESTINACION_ADUANERA varchar(20) DEFAULT NULL,
  TIPO_OPERACION varchar(20) DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  ESTADO_OP varchar(20) DEFAULT NULL,
  TOTAL_GIRO_US varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_resumen_op_dus`
--

DROP TABLE IF EXISTS cc_resumen_op_dus;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_resumen_op_dus (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  COD_OPERACION varchar(20) DEFAULT NULL,
  DESTINACION_ADUANERA varchar(20) DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  PROCESSING_STATUS varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_sequences`
--

DROP TABLE IF EXISTS cc_sequences;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_sequences (
  sequence_name varchar(20) NOT NULL,
  sequence_next_hi_value int(11) DEFAULT NULL,
  PRIMARY KEY (sequence_name)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_subheading`
--

DROP TABLE IF EXISTS cc_subheading;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_subheading (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  SUBHEADING_ID bigint(20) DEFAULT NULL,
  HEADING int(11) DEFAULT NULL,
  HEADING_ID bigint(20) DEFAULT NULL,
  SUBHEADING_CODE varchar(20) DEFAULT NULL,
  SUBHEADING_DESC varchar(20) DEFAULT NULL,
  SUBHEADING_DESC_SP varchar(20) DEFAULT NULL,
  SUBHEADING_STATUS varchar(20) DEFAULT NULL,
  SUBHEADING_TEXT varchar(20) DEFAULT NULL,
  `YEAR` bigint(4) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_transport`
--

DROP TABLE IF EXISTS cc_transport;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_transport (
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  ID_TRANSPORTE bigint(20) DEFAULT NULL,
  ID_DECLARACION bigint(20) DEFAULT NULL,
  TRANSPORTISTA int(11) DEFAULT NULL,
  EMISOR_DOCUMENTO_TRANSPORTE int(11) DEFAULT NULL,
  AGENCIA_TRANSPORTISTA int(11) DEFAULT NULL,
  MEDIO_DE_TRANSPORTE int(11) DEFAULT NULL,
  DOCUMENTO int(11) DEFAULT NULL,
  ID int(11) NOT NULL,
  EMISOR_DOCUMENTO_TRANS_ID int(11) DEFAULT NULL,
  TRANSPORTISTA_ID int(11) DEFAULT NULL,
  AGENCIA_TRANSPORTISTA_ID int(11) DEFAULT NULL,
  ID_DOC_TRANSPORTE int(11) DEFAULT NULL,
  ID_MERCANCIA int(11) DEFAULT NULL,
  ID_RESERVA int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_view_destinos_asociados`
--

DROP TABLE IF EXISTS cc_view_destinos_asociados;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_view_destinos_asociados (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGO_PRODUCTO varchar(20) DEFAULT NULL,
  USO_PREVISTO varchar(20) DEFAULT NULL,
  NOMBRE_INSTALACION varchar(20) DEFAULT NULL,
  CANTIDAD decimal(10,0) DEFAULT NULL,
  NOMBRE_PRODUCTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_view_resumen`
--

DROP TABLE IF EXISTS cc_view_resumen;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_view_resumen (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  COD_OPERACION varchar(20) DEFAULT NULL,
  TIPO_TRAMITE varchar(20) DEFAULT NULL,
  CREADO_POR varchar(20) DEFAULT NULL,
  ACTUALIZADO_POR varchar(20) DEFAULT NULL,
  BULTO_TOTAL int(11) DEFAULT NULL,
  PESO_NETO_EMBARQUE decimal(10,0) DEFAULT NULL,
  PROCESSING_STATUS varchar(20) DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  FECHA_ACTUALIZACION date DEFAULT NULL,
  ITEM_TOTAL int(11) DEFAULT NULL,
  PESO_BRUTO_ITEM decimal(10,0) DEFAULT NULL,
  VOLUMEN_TOTAL decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_view_resumen_impo`
--

DROP TABLE IF EXISTS cc_view_resumen_impo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_view_resumen_impo (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PROCESSING_STATUS varchar(20) DEFAULT NULL,
  CREADO_POR varchar(20) DEFAULT NULL,
  TIPO_TRAMITE varchar(20) DEFAULT NULL,
  COD_OPERACION varchar(20) DEFAULT NULL,
  TOTAL_ITEMS int(11) DEFAULT NULL,
  TOTAL_BULTOS int(11) DEFAULT NULL,
  FECHA_ACTUALIZACION date DEFAULT NULL,
  FECHA_CREACION date DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_view_vistos_buenos`
--

DROP TABLE IF EXISTS cc_view_vistos_buenos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_view_vistos_buenos (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) DEFAULT NULL,
  NOMBRE varchar(20) DEFAULT NULL,
  ORDEN int(11) DEFAULT NULL,
  DESTINACIONADUANERA varchar(50) DEFAULT NULL,
  PAIS varchar(50) DEFAULT NULL,
  CODOPERACION varchar(50) DEFAULT NULL,
  CREATEDDATE date DEFAULT NULL,
  PROCESSINGSTATUS varchar(50) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_almacenista_din`
--

DROP TABLE IF EXISTS cc_vista_almacenista_din;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_almacenista_din (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGO_ALMACENISTA varchar(20) DEFAULT NULL,
  FECHA_RECEPCION_MERCANCIAS date DEFAULT NULL,
  FECHA_RETIRO_MERCANCIAS date DEFAULT NULL,
  NUMERO_REG_RECONOCIMIENTO varchar(20) DEFAULT NULL,
  ANIO_REG_RECONOCIMIENTO varchar(20) DEFAULT NULL,
  CODIGO_DECLARACION_REGLA1 int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_bulto_da`
--

DROP TABLE IF EXISTS cc_vista_bulto_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_bulto_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CANTIDAD_ENVASE int(11) DEFAULT NULL,
  CANTIDAD_BULTOS int(11) DEFAULT NULL,
  TIPO_ENVASE varchar(20) DEFAULT NULL,
  TIPO_BULTO varchar(20) DEFAULT NULL,
  TIPO_SUB_CONTINENTE varchar(20) DEFAULT NULL,
  SECUENCIAL_BULTO int(11) DEFAULT NULL,
  IDENTIFICACION_BULTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_com_transp_dus`
--

DROP TABLE IF EXISTS cc_vista_com_transp_dus;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_com_transp_dus (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  RUT_CIA_TRANSPORTADORA varchar(11) NOT NULL,
  NOMBRE_CIA_TRANSPORTADORA varchar(45) DEFAULT NULL,
  PAIS_CIA_TRANSPORTADORA varchar(45) DEFAULT NULL,
  EMISOR_DOC_TRASNPORTE varchar(45) DEFAULT NULL,
  RUT_EMISOR_DOC_TRASNPORTE varchar(45) DEFAULT NULL,
  RUT_AGEN_CIA_TRASNP varchar(45) DEFAULT NULL,
  RUT_REP_LEGAL_DOC_TRANSP varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_dec_doc_ing_env`
--

DROP TABLE IF EXISTS cc_vista_dec_doc_ing_env;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_dec_doc_ing_env (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  COD_ADUANA varchar(30) DEFAULT NULL,
  COD_TIPO_OPER varchar(45) DEFAULT NULL,
  TIPO_DESTINACION varchar(45) DEFAULT NULL,
  REGION_INGRESO varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_declaracion_din`
--

DROP TABLE IF EXISTS cc_vista_declaracion_din;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_declaracion_din (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  NUMERO_IDENTIFICACION varchar(20) DEFAULT NULL,
  CODIGO_ADUANA varchar(20) DEFAULT NULL,
  CAMPO_FORM varchar(20) DEFAULT NULL,
  FECHA_VENCIMIENTO varchar(20) DEFAULT NULL,
  CODIGO_TIPO_OPERACION varchar(20) DEFAULT NULL,
  TIPO_INGRESO varchar(20) DEFAULT NULL,
  URL_DIN varchar(20) DEFAULT NULL,
  TIPO_DESTINACION varchar(20) DEFAULT NULL,
  REGION_INGRESO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_destino_doc_ing_envio`
--

DROP TABLE IF EXISTS cc_vista_destino_doc_ing_envio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_destino_doc_ing_envio (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PATENTE varchar(20) DEFAULT NULL,
  RUTA varchar(20) DEFAULT NULL,
  TIPO_MEDIO_TRANSPORTE varchar(20) DEFAULT NULL,
  FECHA_ESTIMADA_LLEGADA date DEFAULT NULL,
  NOMBRE_TRANSPORTISTA varchar(20) DEFAULT NULL,
  TIPO_NOMBRE varchar(20) DEFAULT NULL,
  RUT_TRANSPORTISTA varchar(20) DEFAULT NULL,
  PASAPORTE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_det_puertos_da`
--

DROP TABLE IF EXISTS cc_vista_det_puertos_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_det_puertos_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  SECUENCIAL int(11) NOT NULL,
  REGION varchar(45) DEFAULT NULL,
  COD_PAIS varchar(45) DEFAULT NULL,
  FECHA varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_documento`
--

DROP TABLE IF EXISTS cc_vista_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_documento (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  EMISOR_DOCUMENTO_COMERCIAL varchar(20) DEFAULT NULL,
  NUMERO_DOCUMENTO_COMERCIAL varchar(20) DEFAULT NULL,
  CODIGO_PAIS_EMISOR_DOCUMENTO_COMERCIAL varchar(20) DEFAULT NULL,
  TIPO_DOCUMENTO_COMERCIAL varchar(20) DEFAULT NULL,
  FECHA_DOCUMENTO_COMERCIAL varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_documento_apoyo_da`
--

DROP TABLE IF EXISTS cc_vista_documento_apoyo_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_documento_apoyo_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  DESCRIPCION varchar(20) DEFAULT NULL,
  NOMBRE_DOCUMENTO varchar(20) DEFAULT NULL,
  NOMBRE_EMISOR varchar(20) DEFAULT NULL,
  TIPO_DOCUMENTO_ADJUNTO varchar(20) DEFAULT NULL,
  SECUENCIA_DOCUMENTO int(11) DEFAULT NULL,
  NUMERO_DOCUMENTO int(11) DEFAULT NULL,
  FECHA_DOCUMENTO date DEFAULT NULL,
  ADJUNTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_documento_ingreso_envio`
--

DROP TABLE IF EXISTS cc_vista_documento_ingreso_envio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_documento_ingreso_envio (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  NUMERO_DOCUMENTO_INGRESO varchar(20) DEFAULT NULL,
  TIPO_SOLICITUD varchar(20) DEFAULT NULL,
  FECHA_SOLICITUD date DEFAULT NULL,
  FECHA_DOC_INGRESO date DEFAULT NULL,
  ID_SOLICITUD varchar(20) DEFAULT NULL,
  CODI_SERVICIOSPUBLICOS_RELACIONADOS varchar(20) DEFAULT NULL,
  TOTAL_BULTOS int(11) DEFAULT NULL,
  ID_BULTOS varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_documento_ingreso_oig`
--

DROP TABLE IF EXISTS cc_vista_documento_ingreso_oig;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_documento_ingreso_oig (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  OIG_DOCUMENTO varchar(20) DEFAULT NULL,
  NOMBRE_DOCUMENTO varchar(20) DEFAULT NULL,
  BODEGA_DESTINO varchar(20) DEFAULT NULL,
  NUMERO_DOCUMENTO varchar(20) DEFAULT NULL,
  FECHA_DOCUMENTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_financieroda`
--

DROP TABLE IF EXISTS cc_vista_financieroda;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_financieroda (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  DESCUENTO varchar(20) DEFAULT NULL,
  CLAUSULA_VENTA_TRANSACCION varchar(20) DEFAULT NULL,
  COMISIONES_AL_EXTERIOR varchar(20) DEFAULT NULL,
  OTROS_GASTOS varchar(20) DEFAULT NULL,
  MODALIDAD_VENTA_TRANSACCION varchar(20) DEFAULT NULL,
  NUMERO_FACTURA varchar(20) DEFAULT NULL,
  FORMA_PAGO_TRANSACCION varchar(20) DEFAULT NULL,
  TIPO_MONEDA_TRANSACCION varchar(20) DEFAULT NULL,
  INDICA_FACTURA_COMERCIAL_DEFINITIVA varchar(20) DEFAULT NULL,
  CODIGO_PAIS_ADQUISICION varchar(20) DEFAULT NULL,
  TASA_SEGURO varchar(20) DEFAULT NULL,
  NUMEROCUOTAS_PAGO_DIFERIDO date DEFAULT NULL,
  FECHA_EMISION decimal(10,0) DEFAULT NULL,
  MONTO_TOTAL_FACTURA decimal(10,0) DEFAULT NULL,
  MONTO_TOTAL_CIF decimal(10,0) DEFAULT NULL,
  VALOR_LIQUIDO_RETORNO decimal(10,0) DEFAULT NULL,
  VALOR_FLETE decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_instalacion_doc_ing_envio`
--

DROP TABLE IF EXISTS cc_vista_instalacion_doc_ing_envio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_instalacion_doc_ing_envio (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  TIPO_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  CODIGO_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  NOMBRE_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  ESTABLECIMIENTO_AUTORIZADO int(11) DEFAULT NULL,
  DIRECCION_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  RAZON_SOCIAL_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  NOMBRE_CONTACTO_ESTABLECIMIENTO varchar(20) DEFAULT NULL,
  REGION_INSTALACION varchar(20) DEFAULT NULL,
  COMUNA_INSTALACION varchar(20) DEFAULT NULL,
  NUMERO_RESOLUCION_AUTORIZACION varchar(20) DEFAULT NULL,
  FECHA_EMISION_RESOLUCION date DEFAULT NULL,
  ENTIDAD_EMISORA varchar(20) DEFAULT NULL,
  NOMBRE_DIRECTOR_TECNICO varchar(20) DEFAULT NULL,
  TELEFONO_INSTALACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_mercancia_da`
--

DROP TABLE IF EXISTS cc_vista_mercancia_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_mercancia_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  ID_ITEM int(11) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_observacion_da`
--

DROP TABLE IF EXISTS cc_vista_observacion_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_observacion_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  VALOR_OBSERVACION varchar(20) DEFAULT NULL,
  CODIGO_OBSERVACION varchar(20) DEFAULT NULL,
  GLOSA_OBSERVACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_observacion_doc_ingreso_envio`
--

DROP TABLE IF EXISTS cc_vista_observacion_doc_ingreso_envio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_observacion_doc_ingreso_envio (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  SECUENCIAL_OBSERVACION int(11) DEFAULT NULL,
  CODIGO_OBSERVACION varchar(20) DEFAULT NULL,
  DESCRIPCION_OBSERVACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_origen_doc_ing`
--

DROP TABLE IF EXISTS cc_vista_origen_doc_ing;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_origen_doc_ing (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  COD_PAIS_ORIGEN varchar(45) DEFAULT NULL,
  VIA_TRANSPORTE varchar(45) DEFAULT NULL,
  COD_PUERTO_EMBARQUE varchar(45) DEFAULT NULL,
  NRO_DOC_TRANSPORTE varchar(45) DEFAULT NULL,
  FECHA_DOC_TRANSPORTE datetime DEFAULT NULL,
  COD_PAIS_PROCEDENCIA varchar(45) DEFAULT NULL,
  TRANSBORDO varchar(45) DEFAULT NULL,
  COD_PUERTO_DESEMBARQUE varchar(45) DEFAULT NULL,
  NOMBRE_CIA_TRANSPORTE varchar(45) DEFAULT NULL,
  FECHA_EMBARQUE datetime DEFAULT NULL,
  FECHA_DESEMBARQUE datetime DEFAULT NULL,
  NOMBRE_NAVE varchar(45) DEFAULT NULL,
  NUMERO_MANIFIESTO varchar(45) DEFAULT NULL,
  EMISOR varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_otro_impuesto_din`
--

DROP TABLE IF EXISTS cc_vista_otro_impuesto_din;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_otro_impuesto_din (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  SECUENCIAL_OTRO_IMPUESTO int(11) DEFAULT NULL,
  PORCENTAJE varchar(20) DEFAULT NULL,
  CODIGO_CUENTA varchar(20) DEFAULT NULL,
  SIGNO_CUENTA varchar(20) DEFAULT NULL,
  MONTO_IMPUESTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_participante_secundario`
--

DROP TABLE IF EXISTS cc_vista_participante_secundario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_participante_secundario (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE_CONTACTO varchar(45) DEFAULT NULL,
  EMAIL_CONTACTO varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_participantes_da`
--

DROP TABLE IF EXISTS cc_vista_participantes_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_participantes_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  NOMBRE_PARTICIPANTE varchar(20) DEFAULT NULL,
  CODIGO_DESPACHO varchar(20) DEFAULT NULL,
  DESPACHADOR_CODIGO varchar(20) DEFAULT NULL,
  INDICA_TIPO_DOCUMENTO varchar(20) DEFAULT NULL,
  RUT varchar(20) DEFAULT NULL,
  PORCENTAJE varchar(20) DEFAULT NULL,
  EMAIL varchar(20) DEFAULT NULL,
  DIRECCION varchar(20) DEFAULT NULL,
  COMUNA varchar(20) DEFAULT NULL,
  PARTICIPANTE int(11) DEFAULT NULL,
  REGION varchar(20) DEFAULT NULL,
  NUMERO_FIJO varchar(20) DEFAULT NULL,
  NUMERO_MOVIL varchar(20) DEFAULT NULL,
  PAIS varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_prod_doc_ing_env`
--

DROP TABLE IF EXISTS cc_vista_prod_doc_ing_env;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_prod_doc_ing_env (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  SECUENCIA_ATRIBUTO int(11) NOT NULL,
  NOMBRE_ATRIBUTO varchar(45) DEFAULT NULL,
  VALOR_ATRIBUTO varchar(45) DEFAULT NULL,
  ES_FIJO int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_product_attribute_da`
--

DROP TABLE IF EXISTS cc_vista_product_attribute_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_product_attribute_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  SECUENCIA_ATRIBUTO int(11) DEFAULT NULL,
  NOMBRE_ATRIBUTO varchar(20) DEFAULT NULL,
  ES_FIJO varchar(20) DEFAULT NULL,
  VALOR_ATRIBUTO varchar(20) DEFAULT NULL,
  VISTAPRODUCTDA_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_product_da`
--

DROP TABLE IF EXISTS cc_vista_product_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_product_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  CODIGOPRODUCTO varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_reg_suspensivo_da`
--

DROP TABLE IF EXISTS cc_vista_reg_suspensivo_da;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_reg_suspensivo_da (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  TIPO_REINGRESO varchar(45) DEFAULT NULL,
  RAZON_REINGRESO varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_vista_totales_declaracion`
--

DROP TABLE IF EXISTS cc_vista_totales_declaracion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_totales_declaracion (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) NOT NULL,
  ORDEN int(11) DEFAULT NULL,
  PESO_BRUTO_EMBARQUE varchar(20) DEFAULT NULL,
  TOTAL_BULTO varchar(20) DEFAULT NULL,
  PESO_NETO_EMBARQUE varchar(20) DEFAULT NULL,
  TOTAL_ITEM varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ccoo_aviso`
--

DROP TABLE IF EXISTS ccoo_aviso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ccoo_aviso (
  ID double NOT NULL DEFAULT '0',
  NOMBRE varchar(255) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  USUARIO_CREACION varchar(255) DEFAULT NULL,
  FECHA_LEIDO datetime DEFAULT NULL,
  FECHA_ELIMINACION datetime DEFAULT NULL,
  MENSAJE varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ccoo_comunicacion`
--

DROP TABLE IF EXISTS ccoo_comunicacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ccoo_comunicacion (
  ID int(10) NOT NULL,
  ID_DOCUMENTO int(10) NOT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  MENSAJE varchar(300) DEFAULT NULL,
  ID_COMUNICACION double DEFAULT NULL,
  USUARIO_CREADOR varchar(300) DEFAULT NULL,
  FECHA_ELIMINACION_BANDEJA datetime DEFAULT NULL,
  CCOO_ID_DOC decimal(10,0) DEFAULT NULL,
  CCOO_FECHA_CREACION datetime DEFAULT NULL,
  NOMBRE_COMPLETO_USUARIO varchar(300) DEFAULT NULL,
  TIENE_ADJUNTOS char(1) DEFAULT NULL,
  NRO_COMUNICACION_RESPONDIDA varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_blyhndf0yg4gw8hltv2i4ks7s (ID_DOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ccoo_comunicacion_destino`
--

DROP TABLE IF EXISTS ccoo_comunicacion_destino;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ccoo_comunicacion_destino (
  ID_COMUNICACION int(10) NOT NULL,
  NOMBRE_USUARIO varchar(300) DEFAULT NULL,
  COPIA char(1) DEFAULT NULL,
  COPIA_OCULTA char(1) DEFAULT NULL,
  ID int(10) NOT NULL,
  REPARTICION_EXTERNO varchar(300) DEFAULT NULL,
  LEIDO datetime DEFAULT NULL,
  MESA_EXTERNO varchar(300) DEFAULT NULL,
  FECHA_ELIMINACION_BANDEJA datetime DEFAULT NULL,
  MESA_DESTINO varchar(300) DEFAULT NULL,
  USUARIO_CON_LICENCIA varchar(300) DEFAULT NULL,
  REENVIO_POR varchar(300) DEFAULT NULL,
  USUARIO_IMPRESOR varchar(300) DEFAULT NULL,
  FECHA_OPERACION datetime DEFAULT NULL,
  CCOO_ID_DOC int(10) DEFAULT NULL,
  CCOO_FECHA_CREACION datetime DEFAULT NULL,
  ID_COMUNICACION_RESPONDIDA double DEFAULT NULL,
  NOMBRE_COMPLETO_USUARIO varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datos_variables_tipo_doc_aud`
--

DROP TABLE IF EXISTS datos_variables_tipo_doc_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE datos_variables_tipo_doc_aud (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  OBLIGATORIEDAD char(1) DEFAULT NULL,
  NOMBRE_METADATO varchar(300) DEFAULT NULL,
  TIPO varchar(300) DEFAULT NULL,
  ORDEN decimal(10,0) NOT NULL,
  listaDatosVariables tinyblob,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datos_variables_tipo_documento`
--

DROP TABLE IF EXISTS datos_variables_tipo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE datos_variables_tipo_documento (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  OBLIGATORIEDAD char(1) DEFAULT NULL,
  NOMBRE_METADATO varchar(300) DEFAULT NULL,
  TIPO varchar(300) DEFAULT NULL,
  ORDEN decimal(10,0) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_attribute`
--

DROP TABLE IF EXISTS df_attribute;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_attribute (
  ID int(19) NOT NULL,
  KEY_AT varchar(255) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_clob_text`
--

DROP TABLE IF EXISTS df_clob_text;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_clob_text (
  id int(19) NOT NULL,
  ID_FORM_COMPONENT double NOT NULL,
  TEXTO longtext NOT NULL,
  ID_FORM double NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_component`
--

DROP TABLE IF EXISTS df_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_component (
  ID int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  ID_COMPONENT_TYPE decimal(19,0) NOT NULL,
  CREATION_DATE datetime DEFAULT NULL,
  CREATOR_USER varchar(255) DEFAULT NULL,
  MODIFICATION_DATE datetime DEFAULT NULL,
  MODIFYING_USER varchar(255) DEFAULT NULL,
  MASCARA varchar(20) DEFAULT NULL,
  MENSAJE varchar(255) DEFAULT NULL,
  ABM_COMPONENT char(1) DEFAULT NULL,
  TIPO_VISIBLE varchar(255) DEFAULT NULL,
  NOMBREXML varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_component_multivalue`
--

DROP TABLE IF EXISTS df_component_multivalue;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_component_multivalue (
  ID int(19) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  `VALUE` varchar(300) NOT NULL,
  DESCRIPTION varchar(300) NOT NULL,
  VALUE_ORDER decimal(19,0) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  TYPE_ORDER double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_component_type`
--

DROP TABLE IF EXISTS df_component_type;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_component_type (
  ID int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  FACTORY varchar(64) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_dynamic_component`
--

DROP TABLE IF EXISTS df_dynamic_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_dynamic_component (
  ID int(19) NOT NULL,
  ID_COMPONENTE int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  LABEL varchar(255) DEFAULT NULL,
  HIDDEN tinyint(4) DEFAULT NULL,
  REQUIRED tinyint(4) DEFAULT NULL,
  VISIBILITY_RULE blob,
  CONSTRAINT_RULE blob,
  PARENT varchar(255) DEFAULT NULL,
  DEFAULT_VALUE varchar(255) DEFAULT NULL,
  STYLE varchar(255) DEFAULT NULL,
  WIDTH varchar(32) DEFAULT NULL,
  HEIGHT varchar(32) DEFAULT NULL,
  TOOLTIP varchar(255) DEFAULT NULL,
  DISABLED tinyint(4) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_dynamic_constraint`
--

DROP TABLE IF EXISTS df_dynamic_constraint;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_dynamic_constraint (
  ID_FORM_COMP decimal(10,0) NOT NULL,
  `JSON` longblob,
  ID int(10) NOT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_dynamic_form`
--

DROP TABLE IF EXISTS df_dynamic_form;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_dynamic_form (
  ID_FORM int(10) NOT NULL,
  `JSON` blob,
  id int(19) NOT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  FECHA_ALTA timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_form`
--

DROP TABLE IF EXISTS df_form;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_form (
  ID int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  CREATION_DATE datetime DEFAULT NULL,
  CREATOR_USER varchar(255) DEFAULT NULL,
  MODIFICATION_DATE datetime DEFAULT NULL,
  MODIFYING_USER varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_form_comp_value`
--

DROP TABLE IF EXISTS df_form_comp_value;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_form_comp_value (
  ID int(19) NOT NULL,
  ID_TRANSACTION decimal(19,0) NOT NULL,
  ID_FORM_COMPONENT decimal(19,0) NOT NULL,
  VALUE_STR varchar(255) DEFAULT NULL,
  VALUE_INT decimal(19,0) DEFAULT NULL,
  VALUE_DATE datetime DEFAULT NULL,
  VALUE_DOUBLE double DEFAULT NULL,
  VALUE_BOOLEAN int(11) DEFAULT NULL,
  INPUT_NAME varchar(255) DEFAULT NULL,
  VALUE_ARCHIVO varchar(50) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_form_component`
--

DROP TABLE IF EXISTS df_form_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_form_component (
  ID int(19) NOT NULL,
  ID_FORM decimal(19,0) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  COMP_ORDER int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  LABEL varchar(400) DEFAULT NULL,
  OBLIGATORY int(11) DEFAULT NULL,
  SEARCH_RELEVANCY int(11) DEFAULT NULL,
  HIDDEN int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_group`
--

DROP TABLE IF EXISTS df_group;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_group (
  ID int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) NOT NULL,
  CREATION_DATE datetime NOT NULL,
  CREATOR_USER varchar(255) NOT NULL,
  MODIFYING_USER varchar(255) NOT NULL,
  MODIFICATION_DATE datetime NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_group_component`
--

DROP TABLE IF EXISTS df_group_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_group_component (
  ID int(19) NOT NULL,
  ID_GROUP double NOT NULL,
  ID_COMPONENT double NOT NULL,
  COMP_ORDER double NOT NULL,
  `NAME` varchar(255) NOT NULL,
  LABEL varchar(255) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_transaction`
--

DROP TABLE IF EXISTS df_transaction;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_transaction (
  ID int(19) NOT NULL,
  CREATION_DATE datetime NOT NULL,
  FORM_NAME varchar(255) DEFAULT NULL,
  SYS_SOURCE varchar(100) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento_datos_variables`
--

DROP TABLE IF EXISTS documento_datos_variables;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documento_datos_variables (
  ID double NOT NULL DEFAULT '0',
  OBLIGATORIEDAD char(1) DEFAULT NULL,
  NOMBRE_METADATO varchar(300) DEFAULT NULL,
  VALOR_METADATO varchar(300) DEFAULT NULL,
  ORDEN decimal(10,0) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_archivo_de_trabajo`
--

DROP TABLE IF EXISTS gedo_archivo_de_trabajo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_archivo_de_trabajo (
  ID int(11) NOT NULL,
  NOMBRE_ARCHIVO varchar(300) DEFAULT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  USUARIOASOCIADOR varchar(300) DEFAULT NULL,
  PATHRELATIVO varchar(300) DEFAULT NULL,
  FECHAASOCIACION datetime DEFAULT NULL,
  IDTASK varchar(300) DEFAULT NULL,
  ID_GUARDA_DOCUMENTAL varchar(300) DEFAULT NULL,
  PESO double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_archivo_embebido`
--

DROP TABLE IF EXISTS gedo_archivo_embebido;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_archivo_embebido (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  NOMBRE_ARCHIVO varchar(255) DEFAULT NULL,
  USUARIOASOCIADOR varchar(255) DEFAULT NULL,
  PATHRELATIVO varchar(255) DEFAULT NULL,
  FECHAASOCIACION datetime DEFAULT NULL,
  IDTASK varchar(255) DEFAULT NULL,
  MIMETYPE varchar(255) DEFAULT NULL,
  ID_GUARDA_DOCUMENTAL varchar(300) DEFAULT NULL,
  PESO double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_aud_error_cambio_sigla`
--

DROP TABLE IF EXISTS gedo_aud_error_cambio_sigla;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_aud_error_cambio_sigla (
  ID double NOT NULL DEFAULT '0',
  ID_TAREA decimal(10,0) NOT NULL,
  `ERROR` varchar(255) NOT NULL,
  FECHA datetime NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_aud_tarea_cambio_sigla`
--

DROP TABLE IF EXISTS gedo_aud_tarea_cambio_sigla;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_aud_tarea_cambio_sigla (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  TAREA varchar(255) NOT NULL,
  CODIGO_REPARTICION_ORIGEN varchar(255) DEFAULT NULL,
  CODIGO_REPARTICION_DESTINO varchar(255) DEFAULT NULL,
  CODIGO_SECTOR_ORIGEN varchar(255) DEFAULT NULL,
  CODIGO_SECTOR_DESTINO varchar(255) DEFAULT NULL,
  FECHA datetime NOT NULL,
  USUARIO_BAJA varchar(255) DEFAULT NULL,
  TOKEN varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_aviso`
--

DROP TABLE IF EXISTS gedo_aviso;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_aviso (
  ID int(10) NOT NULL,
  USUARIORECEPTOR varchar(300) NOT NULL,
  USUARIOACCION varchar(300) NOT NULL,
  FECHAACCION datetime NOT NULL,
  FECHAENVIO datetime NOT NULL,
  REDIRIGIDOPOR varchar(300) DEFAULT NULL,
  MOTIVO varchar(300) DEFAULT NULL,
  MOTIVORECHAZO varchar(300) DEFAULT NULL,
  DOCUMENTO decimal(10,0) DEFAULT NULL,
  REFERENCIADOCUMENTO varchar(300) DEFAULT NULL,
  NUMEROSADEPAPEL varchar(300) DEFAULT NULL,
  NUMEROESPECIAL varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_qobbyu40s45kcb6bt5x25i8fu (DOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_comentarios`
--

DROP TABLE IF EXISTS gedo_comentarios;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_comentarios (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  ID_TASK varchar(300) NOT NULL,
  USUARIO varchar(300) NOT NULL,
  FECHA datetime NOT NULL,
  WORKFLOWORIGEN varchar(300) NOT NULL,
  COMENTARIO varchar(300) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_documento`
--

DROP TABLE IF EXISTS gedo_documento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_documento (
  ID int(10) NOT NULL,
  NUMERO varchar(300) DEFAULT NULL,
  NUMEROESPECIAL varchar(300) DEFAULT NULL,
  REPARTICION varchar(300) DEFAULT NULL,
  ANIO varchar(300) DEFAULT NULL,
  MOTIVO varchar(300) DEFAULT NULL,
  USUARIOGENERADOR varchar(300) DEFAULT NULL,
  FECHACREACION datetime DEFAULT NULL,
  WORKFLOWORIGEN varchar(300) DEFAULT NULL,
  NUMERO_SADE_PAPEL varchar(300) DEFAULT NULL,
  TIPO decimal(10,0) DEFAULT NULL,
  SISTEMAORIGEN varchar(300) DEFAULT NULL,
  SISTEMAINICIADOR varchar(300) DEFAULT NULL,
  USUARIOINICIADOR varchar(300) DEFAULT NULL,
  TIPORESERVA decimal(10,0) DEFAULT NULL,
  VERSION varchar(24) DEFAULT NULL,
  CCOO_ID_DOC decimal(10,0) DEFAULT NULL,
  CCOO_FECHA_CREACION datetime DEFAULT NULL,
  APODERADO varchar(300) DEFAULT NULL,
  REPARTICION_ACTUAL varchar(300) DEFAULT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  ID_GUARDA_DOCUMENTAL varchar(300) DEFAULT NULL,
  PESO double DEFAULT NULL,
  MOTIVO_DEPURACION varchar(300) DEFAULT NULL,
  FECHA_DEPURACION datetime DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_1ypd1baej6b9jooaexcaxvhwv (TIPO),
  KEY FK_ck6qibg1hlfn007feqlj0p4bi (TIPORESERVA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_documento_adjunto`
--

DROP TABLE IF EXISTS gedo_documento_adjunto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_documento_adjunto (
  ID int(10) NOT NULL,
  NOMBRE_ARCHIVO varchar(300) DEFAULT NULL,
  DEFINITIVO char(1) DEFAULT NULL,
  USUARIOASOCIADOR varchar(300) DEFAULT NULL,
  PATHRELATIVO varchar(300) DEFAULT NULL,
  FECHAASOCIACION datetime DEFAULT NULL,
  IDTASK varchar(300) DEFAULT NULL,
  ID_GUARDA_DOCUMENTAL varchar(300) DEFAULT NULL,
  PESO double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_documento_solicitud`
--

DROP TABLE IF EXISTS gedo_documento_solicitud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_documento_solicitud (
  ID int(11) NOT NULL,
  NUMERO_SADE varchar(60) DEFAULT NULL,
  WORKFLOWID varchar(60) DEFAULT NULL,
  ID_TRANSACCION double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_documento_template`
--

DROP TABLE IF EXISTS gedo_documento_template;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_documento_template (
  ID_TIPODOCUMENTO double NOT NULL,
  VERSION double NOT NULL,
  WORKFLOWID varchar(30) NOT NULL,
  ID_TRANSACCION double DEFAULT NULL,
  PRIMARY KEY (ID_TIPODOCUMENTO,VERSION,WORKFLOWID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_documento_usuariosreserva`
--

DROP TABLE IF EXISTS gedo_documento_usuariosreserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_documento_usuariosreserva (
  ID_DOCUMENTO decimal(10,0) NOT NULL,
  USERNAME varchar(255) NOT NULL,
  RESER_POR_SCRIPT varchar(4) DEFAULT NULL,
  KEY FK_ret2pf44bdj8eoo71ytkdargd (ID_DOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_error_reintento`
--

DROP TABLE IF EXISTS gedo_error_reintento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_error_reintento (
  ID double NOT NULL DEFAULT '0',
  NOMBRE varchar(50) NOT NULL,
  DESCRIPCION varchar(200) DEFAULT NULL,
  ES_REINTENTO char(1) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_ALTA varchar(50) NOT NULL,
  USUARIO_MODIFICACION varchar(200) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_extension_mimetype`
--

DROP TABLE IF EXISTS gedo_extension_mimetype;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_extension_mimetype (
  ID_FORMATO_TAMANO_ARCHIVO decimal(10,0) NOT NULL,
  MIMETYPE varchar(255) NOT NULL,
  KEY FK_fgxodt5eo3hki249gbq2db2y (ID_FORMATO_TAMANO_ARCHIVO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_firmantes`
--

DROP TABLE IF EXISTS gedo_firmantes;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_firmantes (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  WORKFLOWID varchar(300) DEFAULT NULL,
  ORDEN decimal(10,0) DEFAULT NULL,
  USUARIOFIRMANTE varchar(300) DEFAULT NULL,
  ESTADOFIRMA char(1) DEFAULT NULL,
  USUARIOREVISOR varchar(255) DEFAULT NULL,
  ESTADOREVISION char(1) DEFAULT NULL,
  APODERADO varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_formato_tamano_archivo`
--

DROP TABLE IF EXISTS gedo_formato_tamano_archivo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_formato_tamano_archivo (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  FORMATO varchar(255) DEFAULT NULL,
  TAMANO decimal(10,0) DEFAULT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  obligatoriedad bit(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_hist_visualizacion`
--

DROP TABLE IF EXISTS gedo_hist_visualizacion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_hist_visualizacion (
  DOCUMENTO varchar(255) DEFAULT '0',
  USUARIO varchar(255) NOT NULL,
  SECTOR varchar(255) DEFAULT NULL,
  REPARTICION varchar(255) NOT NULL,
  REPARTICION_RECTORA varchar(255) DEFAULT NULL,
  ID int(12) NOT NULL,
  ID_DOCUMENTO double DEFAULT NULL,
  FECHA_ALTA datetime DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_hist_visualizacion_back`
--

DROP TABLE IF EXISTS gedo_hist_visualizacion_back;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_hist_visualizacion_back (
  DOCUMENTO varchar(255) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  SECTOR varchar(255) DEFAULT NULL,
  REPARTICION varchar(255) DEFAULT NULL,
  REPARTICION_RECTORA varchar(255) DEFAULT NULL,
  ID double NOT NULL DEFAULT '0',
  ID_DOCUMENTO double DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_historial`
--

DROP TABLE IF EXISTS gedo_historial;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_historial (
  ID int(10) NOT NULL,
  USUARIO varchar(300) NOT NULL,
  ACTIVIDAD varchar(300) NOT NULL,
  FECHA_FIN datetime DEFAULT NULL,
  MENSAJE varchar(300) DEFAULT NULL,
  WORKFLOWORIGEN varchar(300) NOT NULL,
  FECHA_INICIO datetime DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_numeracionespecial`
--

DROP TABLE IF EXISTS gedo_numeracionespecial;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_numeracionespecial (
  ID int(10) NOT NULL,
  CODIGOREPARTICION varchar(300) DEFAULT NULL,
  ANIO varchar(300) DEFAULT NULL,
  NUMERO decimal(10,0) DEFAULT NULL,
  TIPODOCUMENTO decimal(10,0) DEFAULT NULL,
  LOCKED decimal(24,0) DEFAULT NULL,
  NUMEROINICIAL decimal(10,0) DEFAULT NULL,
  VERSION varchar(24) DEFAULT NULL,
  CODIGOECOSISTEMA varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_numeracionespecial_aud`
--

DROP TABLE IF EXISTS gedo_numeracionespecial_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_numeracionespecial_aud (
  ID bigint(10) NOT NULL,
  TIPOOPERACION varchar(300) DEFAULT NULL,
  FECHAMODIFICACION datetime DEFAULT NULL,
  USERNAME varchar(300) DEFAULT NULL,
  CODIGOREPARTICION varchar(300) DEFAULT NULL,
  ANIO varchar(300) DEFAULT NULL,
  NUMERO decimal(10,0) DEFAULT NULL,
  TIPODOCUMENTO decimal(10,0) DEFAULT NULL,
  VERSION varchar(24) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_numerosusados`
--

DROP TABLE IF EXISTS gedo_numerosusados;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_numerosusados (
  ID bigint(10) NOT NULL,
  TIPODOCUMENTO decimal(10,0) DEFAULT NULL,
  CODIGOREPARTICION varchar(300) DEFAULT NULL,
  ANIO varchar(300) DEFAULT NULL,
  NUMEROSADE varchar(300) DEFAULT NULL,
  NUMEROESPECIAL varchar(300) DEFAULT NULL,
  ESTADO varchar(300) DEFAULT NULL,
  VERSION varchar(24) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_316ne28ytlespevpamn2yma6n (TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_perfilesconversion`
--

DROP TABLE IF EXISTS gedo_perfilesconversion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_perfilesconversion (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  NOMBRE varchar(300) NOT NULL,
  TIPO varchar(300) NOT NULL,
  HABILITADO char(1) NOT NULL,
  DEFAULTSETTING char(1) NOT NULL,
  cambioDeEstado bit(1) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_plantilla`
--

DROP TABLE IF EXISTS gedo_plantilla;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_plantilla (
  ID int(10) NOT NULL,
  NOMBRE varchar(300) NOT NULL,
  DESCRIPCION varchar(300) DEFAULT NULL,
  CONTENIDO longblob NOT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_proceso_log`
--

DROP TABLE IF EXISTS gedo_proceso_log;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_proceso_log (
  ID bigint(20) NOT NULL,
  PROCESO varchar(50) NOT NULL,
  WORKFLOWID varchar(50) DEFAULT NULL,
  SISTEMA_ORIGEN varchar(50) DEFAULT NULL,
  ESTADO varchar(20) DEFAULT NULL,
  DESCRIPCION varchar(512) DEFAULT NULL,
  FECHA_CREACION datetime NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_reparticion_acumulada`
--

DROP TABLE IF EXISTS gedo_reparticion_acumulada;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_reparticion_acumulada (
  IDDOCUMENTO decimal(10,0) NOT NULL,
  REPARTICION varchar(300) DEFAULT NULL,
  TIPOOPERACION char(4) DEFAULT NULL,
  FECHAMODIFICACION datetime DEFAULT NULL,
  USERNAME varchar(300) DEFAULT NULL,
  MIGRADO decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_sistema_origen`
--

DROP TABLE IF EXISTS gedo_sistema_origen;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_sistema_origen (
  ID double NOT NULL DEFAULT '0',
  NOMBRE varchar(50) NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  MECANISMO_RESPUESTA varchar(20) NOT NULL,
  DIRECCION_RESPUESTA varchar(250) NOT NULL,
  CAMPO varchar(20) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_suscripcion`
--

DROP TABLE IF EXISTS gedo_suscripcion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_suscripcion (
  WORKFLOWID varchar(50) NOT NULL,
  SISTEMA_ORIGEN double NOT NULL,
  ESTADO varchar(50) NOT NULL,
  REINTENTO double NOT NULL,
  FECHA_CREACION datetime NOT NULL,
  USUARIO_ALTA varchar(50) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_task_by_user`
--

DROP TABLE IF EXISTS gedo_task_by_user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_task_by_user (
  WORKFLOWORIGEN varchar(255) NOT NULL,
  ES_COMUNICABLE bit(1) DEFAULT NULL,
  FECHA_ALTA_TAREA datetime DEFAULT NULL,
  FECHA_PARTICIPACION datetime DEFAULT NULL,
  MOTIVO varchar(255) DEFAULT NULL,
  TIPO_DOCUMENTO varchar(255) DEFAULT NULL,
  TIPO_TAREA varchar(255) DEFAULT NULL,
  USUARIO varchar(255) DEFAULT NULL,
  ULTIMO_USUARIO varchar(255) DEFAULT NULL,
  PRIMER_USUARIO varchar(255) DEFAULT NULL,
  PRIMARY KEY (WORKFLOWORIGEN)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipo_produccion`
--

DROP TABLE IF EXISTS gedo_tipo_produccion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipo_produccion (
  ID double NOT NULL DEFAULT '0',
  NOMBRE varchar(60) NOT NULL,
  DESCRIPCION varchar(320) DEFAULT NULL,
  FECHA_CREACION datetime NOT NULL,
  FECHA_MODIFICACION datetime DEFAULT NULL,
  USUARIO_ALTA varchar(20) NOT NULL,
  USUARIO_MODIFICACION varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipo_reserva`
--

DROP TABLE IF EXISTS gedo_tipo_reserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipo_reserva (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  RESERVA varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodoc_perfil`
--

DROP TABLE IF EXISTS gedo_tipodoc_perfil;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodoc_perfil (
  IDTIPODOCUMENTO decimal(10,0) NOT NULL,
  IDADOBEPDFSETTING decimal(10,0) NOT NULL,
  IDFILETYPESETTING decimal(10,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento`
--

DROP TABLE IF EXISTS gedo_tipodocumento;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento (
  ID int(11) NOT NULL,
  NOMBRE varchar(300) DEFAULT NULL,
  ACRONIMO varchar(20) DEFAULT NULL,
  DESCRIPCION varchar(300) DEFAULT NULL,
  ESESPECIAL char(1) DEFAULT NULL,
  TIENETOKEN char(1) DEFAULT NULL,
  TIENETEMPLATE char(1) DEFAULT NULL,
  ESTADO varchar(30) DEFAULT NULL,
  IDTIPODOCUMENTOSADE decimal(10,0) DEFAULT NULL,
  CODIGOTIPODOCUMENTOSADE varchar(100) DEFAULT NULL,
  ESCONFIDENCIAL char(1) DEFAULT NULL,
  ESFIRMAEXTERNA char(1) NOT NULL,
  ESMANUAL char(1) DEFAULT NULL,
  ESAUTOMATICA char(1) DEFAULT NULL,
  ESFIRMACONJUNTA char(1) DEFAULT NULL,
  FAMILIA decimal(10,0) DEFAULT NULL,
  TIENEAVISO char(1) DEFAULT NULL,
  PERMITE_EMBEBIDOS char(1) DEFAULT NULL,
  TIPOPRODUCCION double NOT NULL,
  ES_NOTIFICABLE char(1) NOT NULL,
  VERSION varchar(24) DEFAULT NULL,
  TAMANO decimal(10,0) DEFAULT NULL,
  ESOCULTO char(1) DEFAULT NULL,
  ES_COMUNICABLE char(1) NOT NULL,
  USUARIO_CREADOR varchar(255) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  ESFIRMAEXTERNACONENCABEZADO char(1) NOT NULL,
  PRIMARY KEY (ID),
  KEY DOCS_ACRONIMO_VERSION (ACRONIMO,VERSION),
  KEY FK_fxmv04yk4scdd8md2h6v7sbn6 (FAMILIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_aud`
--

DROP TABLE IF EXISTS gedo_tipodocumento_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_aud (
  ID int(10) NOT NULL,
  TIPOOPERACION varchar(300) DEFAULT NULL,
  FECHAOPERACION datetime DEFAULT NULL,
  USERNAME varchar(300) DEFAULT NULL,
  NOMBRE varchar(300) DEFAULT NULL,
  ACRONIMO varchar(300) DEFAULT NULL,
  DESCRIPCION varchar(300) DEFAULT NULL,
  ESESPECIAL char(1) DEFAULT NULL,
  ESMANUAL char(1) DEFAULT NULL,
  ESAUTOMATICA char(1) DEFAULT NULL,
  TIENETOKEN char(1) DEFAULT NULL,
  TIENETEMPLATE char(1) DEFAULT NULL,
  ESTADO varchar(300) DEFAULT NULL,
  IDTIPODOCUMENTOSADE decimal(10,0) DEFAULT NULL,
  CODIGOTIPODOCUMENTOSADE varchar(300) DEFAULT NULL,
  ESCONFIDENCIAL char(1) DEFAULT NULL,
  ESFIRMAEXTERNA char(1) DEFAULT NULL,
  ESFIRMACONJUNTA char(1) DEFAULT NULL,
  FAMILIA decimal(10,0) DEFAULT NULL,
  TIPOPRODUCCION double NOT NULL,
  VERSION varchar(24) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_kuh0quxxes5omgnmpdehnxrme (FAMILIA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_embebido`
--

DROP TABLE IF EXISTS gedo_tipodocumento_embebido;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_embebido (
  ID_TIPODOCUMENTO decimal(10,0) NOT NULL,
  ID_FORMATO decimal(10,0) NOT NULL,
  DESCRIPCION varchar(255) DEFAULT NULL,
  OBLIGATORIO char(1) DEFAULT NULL,
  FECHACREACION datetime DEFAULT NULL,
  USERNAME varchar(300) DEFAULT NULL,
  TAMANO double DEFAULT NULL,
  KEY FK_8wfqn69lur7v8pr12bftfxseg (ID_TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_familia`
--

DROP TABLE IF EXISTS gedo_tipodocumento_familia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_familia (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  NOMBRE varchar(300) NOT NULL,
  DESCRIPCION varchar(300) NOT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_familia_aud`
--

DROP TABLE IF EXISTS gedo_tipodocumento_familia_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_familia_aud (
  ID decimal(10,0) NOT NULL DEFAULT '0',
  FECHACREACION datetime DEFAULT NULL,
  NOMBREUSUARIO varchar(300) DEFAULT NULL,
  ID_FAMILIA decimal(10,0) DEFAULT NULL,
  NOMBREFAMILIA varchar(300) DEFAULT NULL,
  DESCRIPCION varchar(300) DEFAULT NULL,
  ESTADO varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_rep_aud`
--

DROP TABLE IF EXISTS gedo_tipodocumento_rep_aud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_rep_aud (
  TIPODOCUMENTO decimal(10,0) NOT NULL,
  CODIGOREPARTICION varchar(300) DEFAULT NULL,
  PERMISOINICIAR char(1) DEFAULT NULL,
  PERMISOFIRMAR char(1) DEFAULT NULL,
  ESTADO char(1) DEFAULT NULL,
  listaReparticiones_id int(11) NOT NULL,
  KEY FK_a8g87h1yfdqv09n71pqxutjvl (listaReparticiones_id),
  KEY FK_kskcde6dvjp2vp1ru3pp89qdj (TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_reparticion`
--

DROP TABLE IF EXISTS gedo_tipodocumento_reparticion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_reparticion (
  ID int(10) NOT NULL,
  CODIGOREPARTICION varchar(300) DEFAULT NULL,
  PERMISOINICIAR char(1) DEFAULT NULL,
  PERMISOFIRMAR char(1) DEFAULT NULL,
  ESTADO char(1) DEFAULT NULL,
  TIPODOCUMENTO decimal(10,0) NOT NULL,
  PRIMARY KEY (ID),
  KEY FK_gy6q08d7yaeibaijjtxfahogf (TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_tipodocumento_template`
--

DROP TABLE IF EXISTS gedo_tipodocumento_template;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_tipodocumento_template (
  ID_TIPODOCUMENTO double NOT NULL,
  VERSION double NOT NULL,
  TEMPLATE longblob NOT NULL,
  DESCRIPCION varchar(1024) DEFAULT NULL,
  FECHA_CREACION datetime NOT NULL,
  USUARIO_ALTA varchar(60) NOT NULL,
  ID_FORMULARIO varchar(255) DEFAULT NULL,
  KEY FK_cab85l4ya6nkaq848rgsbgtx1 (ID_TIPODOCUMENTO)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gedo_usuario_plantilla`
--

DROP TABLE IF EXISTS gedo_usuario_plantilla;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gedo_usuario_plantilla (
  ID int(10) NOT NULL,
  USUARIO varchar(300) NOT NULL,
  IDPLANTILLA decimal(10,0) NOT NULL,
  PRIMARY KEY (ID),
  KEY FK_jqb91saorpfe212bgogvdodac (IDPLANTILLA)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_deployment`
--

DROP TABLE IF EXISTS jbpm4_deployment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_deployment (
  DBID_ decimal(24,0) NOT NULL,
  NAME_ longtext,
  TIMESTAMP_ decimal(24,0) DEFAULT NULL,
  STATE_ varchar(300) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_deployprop`
--

DROP TABLE IF EXISTS jbpm4_deployprop;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_deployprop (
  DBID_ decimal(24,0) NOT NULL,
  DEPLOYMENT_ decimal(24,0) DEFAULT NULL,
  OBJNAME_ varchar(300) DEFAULT NULL,
  KEY_ varchar(300) DEFAULT NULL,
  STRINGVAL_ varchar(300) DEFAULT NULL,
  LONGVAL_ decimal(24,0) DEFAULT NULL,
  KEY IDX_DEPLPROP_DEPL (DEPLOYMENT_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_execution`
--

DROP TABLE IF EXISTS jbpm4_execution;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_execution (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(300) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  ACTIVITYNAME_ varchar(300) DEFAULT NULL,
  PROCDEFID_ varchar(300) DEFAULT NULL,
  HASVARS_ char(1) DEFAULT NULL,
  NAME_ varchar(300) DEFAULT NULL,
  KEY_ varchar(300) DEFAULT NULL,
  ID_ varchar(300) DEFAULT NULL,
  STATE_ varchar(300) DEFAULT NULL,
  SUSPHISTSTATE_ varchar(300) DEFAULT NULL,
  PRIORITY_ decimal(10,0) DEFAULT NULL,
  HISACTINST_ decimal(24,0) DEFAULT NULL,
  PARENT_ decimal(24,0) DEFAULT NULL,
  INSTANCE_ decimal(24,0) DEFAULT NULL,
  SUPEREXEC_ decimal(24,0) DEFAULT NULL,
  SUBPROCINST_ decimal(24,0) DEFAULT NULL,
  PARENT_IDX_ decimal(10,0) DEFAULT NULL,
  INITIATOR_ varchar(255) DEFAULT NULL,
  KEY IDX_EXEC_PARENT (PARENT_),
  KEY IDX_EXEC_INSTANCE (INSTANCE_),
  KEY IDX_EXEC_SUPEREXEC (SUPEREXEC_),
  KEY IDX_EXEC_SUBPI (SUBPROCINST_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_actinst`
--

DROP TABLE IF EXISTS jbpm4_hist_actinst;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_actinst (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(300) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  HPROCI_ decimal(24,0) DEFAULT NULL,
  TYPE_ varchar(300) DEFAULT NULL,
  EXECUTION_ varchar(300) DEFAULT NULL,
  ACTIVITY_NAME_ varchar(300) DEFAULT NULL,
  START_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  TRANSITION_ varchar(300) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL,
  HTASK_ decimal(24,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_detail`
--

DROP TABLE IF EXISTS jbpm4_hist_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_detail (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(300) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  USERID_ varchar(300) DEFAULT NULL,
  TIME_ datetime DEFAULT NULL,
  HPROCI_ decimal(24,0) DEFAULT NULL,
  HPROCIIDX_ decimal(10,0) DEFAULT NULL,
  HACTI_ decimal(24,0) DEFAULT NULL,
  HACTIIDX_ decimal(10,0) DEFAULT NULL,
  HTASK_ decimal(24,0) DEFAULT NULL,
  HTASKIDX_ decimal(10,0) DEFAULT NULL,
  HVAR_ decimal(24,0) DEFAULT NULL,
  HVARIDX_ decimal(10,0) DEFAULT NULL,
  MESSAGE_ longtext,
  OLD_STR_ varchar(300) DEFAULT NULL,
  NEW_STR_ varchar(300) DEFAULT NULL,
  OLD_INT_ decimal(10,0) DEFAULT NULL,
  NEW_INT_ decimal(10,0) DEFAULT NULL,
  OLD_TIME_ datetime DEFAULT NULL,
  NEW_TIME_ datetime DEFAULT NULL,
  PARENT_ decimal(24,0) DEFAULT NULL,
  PARENT_IDX_ decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_procinst`
--

DROP TABLE IF EXISTS jbpm4_hist_procinst;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_procinst (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  ID_ varchar(300) DEFAULT NULL,
  PROCDEFID_ varchar(300) DEFAULT NULL,
  KEY_ varchar(300) DEFAULT NULL,
  START_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  STATE_ varchar(300) DEFAULT NULL,
  ENDACTIVITY_ varchar(300) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_task`
--

DROP TABLE IF EXISTS jbpm4_hist_task;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_task (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  EXECUTION_ varchar(300) DEFAULT NULL,
  OUTCOME_ varchar(300) DEFAULT NULL,
  ASSIGNEE_ varchar(300) DEFAULT NULL,
  PRIORITY_ decimal(10,0) DEFAULT NULL,
  STATE_ varchar(300) DEFAULT NULL,
  CREATE_ datetime DEFAULT NULL,
  END_ datetime DEFAULT NULL,
  DURATION_ decimal(24,0) DEFAULT NULL,
  NEXTIDX_ decimal(10,0) DEFAULT NULL,
  SUPERTASK_ decimal(24,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_hist_var`
--

DROP TABLE IF EXISTS jbpm4_hist_var;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_hist_var (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  PROCINSTID_ varchar(300) DEFAULT NULL,
  EXECUTIONID_ varchar(300) DEFAULT NULL,
  VARNAME_ varchar(300) DEFAULT NULL,
  VALUE_ varchar(300) DEFAULT NULL,
  HPROCI_ decimal(24,0) DEFAULT NULL,
  HTASK_ decimal(24,0) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_job`
--

DROP TABLE IF EXISTS jbpm4_job;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_job (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(300) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  DUEDATE_ datetime DEFAULT NULL,
  STATE_ varchar(300) DEFAULT NULL,
  ISEXCLUSIVE_ char(1) DEFAULT NULL,
  LOCKOWNER_ varchar(300) DEFAULT NULL,
  LOCKEXPTIME_ datetime DEFAULT NULL,
  EXCEPTION_ longtext,
  RETRIES_ decimal(10,0) DEFAULT NULL,
  PROCESSINSTANCE_ decimal(24,0) DEFAULT NULL,
  EXECUTION_ decimal(24,0) DEFAULT NULL,
  CFG_ decimal(24,0) DEFAULT NULL,
  SIGNAL_ varchar(300) DEFAULT NULL,
  EVENT_ varchar(300) DEFAULT NULL,
  REPEAT_ varchar(300) DEFAULT NULL,
  KEY IDX_JOBDUEDATE (DUEDATE_),
  KEY IDX_JOBLOCKEXP (LOCKEXPTIME_),
  KEY IDX_JOBRETRIES (RETRIES_),
  KEY IDX_JOB_PRINST (PROCESSINSTANCE_),
  KEY IDX_JOB_EXE (EXECUTION_),
  KEY IDX_JOB_CFG (CFG_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_lob`
--

DROP TABLE IF EXISTS jbpm4_lob;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_lob (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  BLOB_VALUE_ longblob,
  DEPLOYMENT_ decimal(24,0) DEFAULT NULL,
  NAME_ longtext,
  PRIMARY KEY (DBID_),
  KEY IDX_LOB_DEPLOYMENT (DEPLOYMENT_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_participation`
--

DROP TABLE IF EXISTS jbpm4_participation;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_participation (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  GROUPID_ varchar(300) DEFAULT NULL,
  USERID_ varchar(300) DEFAULT NULL,
  TYPE_ varchar(300) DEFAULT NULL,
  TASK_ decimal(24,0) DEFAULT NULL,
  SWIMLANE_ decimal(24,0) DEFAULT NULL,
  KEY IDX_PART_TASK (TASK_),
  KEY FK_PART_SWIMLANE (SWIMLANE_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_property`
--

DROP TABLE IF EXISTS jbpm4_property;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_property (
  KEY_ varchar(300) NOT NULL,
  VERSION_ decimal(10,0) NOT NULL,
  VALUE_ varchar(300) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_swimlane`
--

DROP TABLE IF EXISTS jbpm4_swimlane;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_swimlane (
  DBID_ decimal(24,0) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  NAME_ varchar(300) DEFAULT NULL,
  ASSIGNEE_ varchar(300) DEFAULT NULL,
  EXECUTION_ decimal(24,0) DEFAULT NULL,
  KEY IDX_SWIMLANE_EXEC (EXECUTION_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_task`
--

DROP TABLE IF EXISTS jbpm4_task;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_task (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ char(1) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  NAME_ varchar(300) DEFAULT NULL,
  DESCR_ longtext,
  STATE_ varchar(300) DEFAULT NULL,
  SUSPHISTSTATE_ varchar(300) DEFAULT NULL,
  ASSIGNEE_ varchar(300) DEFAULT NULL,
  FORM_ varchar(300) DEFAULT NULL,
  PRIORITY_ decimal(10,0) DEFAULT NULL,
  CREATE_ datetime DEFAULT NULL,
  DUEDATE_ datetime DEFAULT NULL,
  PROGRESS_ decimal(10,0) DEFAULT NULL,
  SIGNALLING_ char(1) DEFAULT NULL,
  EXECUTION_ID_ varchar(300) DEFAULT NULL,
  ACTIVITY_NAME_ varchar(300) DEFAULT NULL,
  HASVARS_ char(1) DEFAULT NULL,
  SUPERTASK_ decimal(24,0) DEFAULT NULL,
  EXECUTION_ decimal(24,0) DEFAULT NULL,
  PROCINST_ decimal(24,0) DEFAULT NULL,
  SWIMLANE_ decimal(24,0) DEFAULT NULL,
  TASKDEFNAME_ varchar(300) DEFAULT NULL,
  KEY IDX_TASK_SUPERTASK (SUPERTASK_),
  KEY FK_TASK_SWIML (SWIMLANE_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbpm4_variable`
--

DROP TABLE IF EXISTS jbpm4_variable;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE jbpm4_variable (
  DBID_ decimal(24,0) NOT NULL,
  CLASS_ varchar(300) NOT NULL,
  DBVERSION_ decimal(10,0) NOT NULL,
  KEY_ varchar(300) DEFAULT NULL,
  CONVERTER_ varchar(300) DEFAULT NULL,
  HIST_ char(1) DEFAULT NULL,
  EXECUTION_ decimal(24,0) DEFAULT NULL,
  TASK_ decimal(24,0) DEFAULT NULL,
  LOB_ decimal(24,0) DEFAULT NULL,
  DATE_VALUE_ datetime DEFAULT NULL,
  DOUBLE_VALUE_ double DEFAULT NULL,
  CLASSNAME_ varchar(300) DEFAULT NULL,
  LONG_VALUE_ decimal(24,0) DEFAULT NULL,
  STRING_VALUE_ varchar(300) DEFAULT NULL,
  TEXT_VALUE_ longtext,
  EXESYS_ decimal(24,0) DEFAULT NULL,
  KEY IDX_VAR_LOB (LOB_),
  KEY IDX_VAR_EXECUTION (EXECUTION_),
  KEY IDX_VAR_EXESYS (EXESYS_),
  KEY IDX_VAR_TASK (TASK_)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messagerequest`
--

DROP TABLE IF EXISTS messagerequest;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE messagerequest (
  ID decimal(24,0) NOT NULL DEFAULT '0',
  MESSAGE_ID varchar(300) DEFAULT NULL,
  EXECUTION_ID varchar(300) DEFAULT NULL,
  FECHA_CREACION datetime DEFAULT NULL,
  FECHA_RESPUESTA datetime DEFAULT NULL,
  ESTADO varchar(300) DEFAULT NULL,
  PRIMARY KEY (ID)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS qrtz_blob_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_blob_triggers (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  BLOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS qrtz_calendars;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_calendars (
  CALENDAR_NAME varchar(200) NOT NULL,
  CALENDAR longblob NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS qrtz_cron_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_cron_triggers (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  CRON_EXPRESSION varchar(120) NOT NULL,
  TIME_ZONE_ID varchar(80) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS qrtz_fired_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_fired_triggers (
  ENTRY_ID varchar(95) NOT NULL,
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  IS_VOLATILE varchar(1) NOT NULL,
  INSTANCE_NAME varchar(200) NOT NULL,
  FIRED_TIME decimal(13,0) NOT NULL,
  PRIORITY decimal(13,0) NOT NULL,
  STATE varchar(16) NOT NULL,
  JOB_NAME varchar(200) DEFAULT NULL,
  JOB_GROUP varchar(200) DEFAULT NULL,
  IS_STATEFUL varchar(1) DEFAULT NULL,
  REQUESTS_RECOVERY varchar(1) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS qrtz_job_details;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_job_details (
  JOB_NAME varchar(200) NOT NULL,
  JOB_GROUP varchar(200) NOT NULL,
  DESCRIPTION varchar(250) DEFAULT NULL,
  JOB_CLASS_NAME varchar(250) NOT NULL,
  IS_DURABLE varchar(1) NOT NULL,
  IS_VOLATILE varchar(1) NOT NULL,
  IS_STATEFUL varchar(1) NOT NULL,
  REQUESTS_RECOVERY varchar(1) NOT NULL,
  JOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_listeners`
--

DROP TABLE IF EXISTS qrtz_job_listeners;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_job_listeners (
  JOB_NAME varchar(200) NOT NULL,
  JOB_GROUP varchar(200) NOT NULL,
  JOB_LISTENER varchar(200) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS qrtz_locks;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_locks (
  LOCK_NAME varchar(40) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_paused_trigger_grps (
  TRIGGER_GROUP varchar(200) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS qrtz_scheduler_state;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_scheduler_state (
  INSTANCE_NAME varchar(200) NOT NULL,
  LAST_CHECKIN_TIME decimal(13,0) NOT NULL,
  CHECKIN_INTERVAL decimal(13,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS qrtz_simple_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_simple_triggers (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  REPEAT_COUNT int(11) NOT NULL,
  REPEAT_INTERVAL decimal(12,0) NOT NULL,
  TIMES_TRIGGERED decimal(10,0) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_trigger_listeners`
--

DROP TABLE IF EXISTS qrtz_trigger_listeners;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_trigger_listeners (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  TRIGGER_LISTENER varchar(200) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS qrtz_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtz_triggers (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  JOB_NAME varchar(200) NOT NULL,
  JOB_GROUP varchar(200) NOT NULL,
  IS_VOLATILE varchar(1) NOT NULL,
  DESCRIPTION varchar(250) DEFAULT NULL,
  NEXT_FIRE_TIME decimal(13,0) DEFAULT NULL,
  PREV_FIRE_TIME decimal(13,0) DEFAULT NULL,
  PRIORITY decimal(13,0) DEFAULT NULL,
  TRIGGER_STATE varchar(16) NOT NULL,
  TRIGGER_TYPE varchar(8) NOT NULL,
  START_TIME decimal(13,0) NOT NULL,
  END_TIME decimal(13,0) DEFAULT NULL,
  CALENDAR_NAME varchar(200) DEFAULT NULL,
  MISFIRE_INSTR int(11) DEFAULT NULL,
  JOB_DATA longblob
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtzco_blob_triggers`
--

DROP TABLE IF EXISTS qrtzco_blob_triggers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE qrtzco_blob_triggers (
  TRIGGER_NAME varchar(200) NOT NULL,
  TRIGGER_GROUP varchar(200) NOT NULL,
  BLOB_DATA longblob
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

-- Dump completed on 2017-08-30  9:58:48