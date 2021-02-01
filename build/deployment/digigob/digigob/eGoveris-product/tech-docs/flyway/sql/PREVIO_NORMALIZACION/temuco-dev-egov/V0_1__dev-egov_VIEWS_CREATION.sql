CREATE DATABASE  IF NOT EXISTS `gedo_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `gedo_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: gedo_ged
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
-- Temporary view structure for view `property_configuration`
--

DROP TABLE IF EXISTS property_configuration;
/*!50001 DROP VIEW IF EXISTS property_configuration*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `property_configuration` AS SELECT 
 1 AS CLAVE,
 1 AS VALOR,
 1 AS CONFIGURACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS ID_REPARTICION,
 1 AS CODIGO_REPARTICION,
 1 AS CODIGO_REPAR_INTER,
 1 AS NOMBRE_REPARTICION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS ID_ESTRUCTURA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DGTAL,
 1 AS REP_PADRE,
 1 AS COD_DGTAL,
 1 AS ID_JURISDICCION,
 1 AS MINISTERIO,
 1 AS ADMINISTRADOR_PRESUPUESTO,
 1 AS calle*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_interno`
--

DROP TABLE IF EXISTS sade_sector_interno;
/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_interno` AS SELECT 
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS NOMBRE_SECTOR_INTERNO,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS CODIGO_REPARTICION,
 1 AS SECTOR_INTERNO_AGRUPACION_INDE,
 1 AS ID_AGRUPACION_SECTOR_MESA,
 1 AS MESA_VIRTUAL,
 1 AS ES_ARCHIVO,
 1 AS USUARIO_ASIGNADOR*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_usuario`
--

DROP TABLE IF EXISTS sade_sector_usuario;
/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_usuario` AS SELECT 
 1 AS ID_SECTOR_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS NOMBRE_USUARIO,
 1 AS PROCESO,
 1 AS ESTADO_REGISTRO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `property_configuration`
--

/*!50001 DROP VIEW IF EXISTS property_configuration*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW property_configuration AS select eu_ged.property_configuration.CLAVE AS CLAVE,eu_ged.property_configuration.VALOR AS VALOR,eu_ged.property_configuration.CONFIGURACION AS CONFIGURACION from eu_ged.property_configuration */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select track_ged.sade_reparticion.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,track_ged.sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,track_ged.sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_reparticion.NUMERO AS NUMERO,track_ged.sade_reparticion.PISO AS PISO,track_ged.sade_reparticion.OFICINA AS OFICINA,track_ged.sade_reparticion.TELEFONO AS TELEFONO,track_ged.sade_reparticion.FAX AS FAX,track_ged.sade_reparticion.EMAIL AS EMAIL,track_ged.sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_reparticion.EN_RED AS EN_RED,track_ged.sade_reparticion.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_reparticion.VERSION AS VERSION,track_ged.sade_reparticion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_reparticion.ES_DGTAL AS ES_DGTAL,track_ged.sade_reparticion.REP_PADRE AS REP_PADRE,track_ged.sade_reparticion.COD_DGTAL AS COD_DGTAL,track_ged.sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,track_ged.sade_reparticion.MINISTERIO AS MINISTERIO,track_ged.sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO,track_ged.sade_reparticion.calle AS calle from track_ged.sade_reparticion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_interno`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_interno AS select track_ged.sade_sector_interno.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_interno.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,track_ged.sade_sector_interno.NOMBRE_SECTOR_INTERNO AS NOMBRE_SECTOR_INTERNO,track_ged.sade_sector_interno.CALLE AS CALLE,track_ged.sade_sector_interno.NUMERO AS NUMERO,track_ged.sade_sector_interno.PISO AS PISO,track_ged.sade_sector_interno.OFICINA AS OFICINA,track_ged.sade_sector_interno.TELEFONO AS TELEFONO,track_ged.sade_sector_interno.FAX AS FAX,track_ged.sade_sector_interno.EMAIL AS EMAIL,track_ged.sade_sector_interno.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_sector_interno.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_sector_interno.EN_RED AS EN_RED,track_ged.sade_sector_interno.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_sector_interno.VERSION AS VERSION,track_ged.sade_sector_interno.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_interno.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_interno.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_interno.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_interno.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_interno.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_sector_interno.SECTOR_INTERNO_AGRUPACION_INDE AS SECTOR_INTERNO_AGRUPACION_INDE,track_ged.sade_sector_interno.ID_AGRUPACION_SECTOR_MESA AS ID_AGRUPACION_SECTOR_MESA,track_ged.sade_sector_interno.MESA_VIRTUAL AS MESA_VIRTUAL,track_ged.sade_sector_interno.ES_ARCHIVO AS ES_ARCHIVO,track_ged.sade_sector_interno.USUARIO_ASIGNADOR AS USUARIO_ASIGNADOR from track_ged.sade_sector_interno */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_usuario`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select track_ged.sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,track_ged.sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_sector_usuario.PROCESO AS PROCESO,track_ged.sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_usuario.VERSION AS VERSION,track_ged.sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_usuario.CARGO_ID AS CARGO_ID from track_ged.sade_sector_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:46
CREATE DATABASE  IF NOT EXISTS `eu_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `eu_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: eu_ged
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
-- Temporary view structure for view `cargos`
--

DROP TABLE IF EXISTS cargos;
/*!50001 DROP VIEW IF EXISTS cargos*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `cargos` AS SELECT 
 1 AS ID,
 1 AS CARGO,
 1 AS VISIBLE,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS FECHA_CREACION,
 1 AS VIGENTE,
 1 AS USUARIO_MODIFICACION,
 1 AS ID_REPARTICION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `datos_usuario`
--

DROP TABLE IF EXISTS datos_usuario;
/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `datos_usuario` AS SELECT 
 1 AS ID_DATO_USUARIO,
 1 AS MAIL,
 1 AS OCUPACION,
 1 AS USER_,
 1 AS USUARIO,
 1 AS USER_SUPERIOR,
 1 AS MAIL_SUPERIOR,
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS FECHA_CADUCIDAD_SECTOR_INTERNO,
 1 AS ES_SECRETARIO,
 1 AS SECRETARIO,
 1 AS APELLIDO_NOMBRE,
 1 AS ACEPTACION_TYC,
 1 AS NUMERO_CUIT,
 1 AS EXTERNALIZAR_FIRMA_EN_GEDO,
 1 AS EXTERNALIZAR_FIRMA_EN_SIGA,
 1 AS EXTERNALIZAR_FIRMA_EN_CCOO,
 1 AS EXTERNALIZAR_FIRMA_EN_LOYS,
 1 AS USUARIO_ASESOR,
 1 AS NOTIFICAR_SOLICITUD_PF,
 1 AS CARGO,
 1 AS CAMBIAR_MESA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `periodo_licencia`
--

DROP TABLE IF EXISTS periodo_licencia;
/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `periodo_licencia` AS SELECT 
 1 AS ID_PERIODO_LICENCIA,
 1 AS FECHA_HORA_DESDE,
 1 AS FECHA_HORA_HASTA,
 1 AS APODERADO,
 1 AS CONDICION_PERIODO,
 1 AS USUARIO,
 1 AS FECHA_CANCELACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_estructura`
--

DROP TABLE IF EXISTS sade_estructura;
/*!50001 DROP VIEW IF EXISTS sade_estructura*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_estructura` AS SELECT 
 1 AS ID_ESTRUCTURA,
 1 AS CODIGO_ESTRUCTURA,
 1 AS NOMBRE_ESTRUCTURA,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS GENERA_ALS,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ESTRUCTURA_PODER_EJECUTIVO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS ID_REPARTICION,
 1 AS CODIGO_REPARTICION,
 1 AS CODIGO_REPAR_INTER,
 1 AS NOMBRE_REPARTICION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS ID_ESTRUCTURA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DGTAL,
 1 AS REP_PADRE,
 1 AS COD_DGTAL,
 1 AS ID_JURISDICCION,
 1 AS MINISTERIO,
 1 AS ADMINISTRADOR_PRESUPUESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion_seleccionada`
--

DROP TABLE IF EXISTS sade_reparticion_seleccionada;
/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion_seleccionada` AS SELECT 
 1 AS ID_REPARTICION_SELECCIONADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_interno`
--

DROP TABLE IF EXISTS sade_sector_interno;
/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_interno` AS SELECT 
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS NOMBRE_SECTOR_INTERNO,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS CODIGO_REPARTICION,
 1 AS SECTOR_INTERNO_AGRUPACION_INDE,
 1 AS ID_AGRUPACION_SECTOR_MESA,
 1 AS MESA_VIRTUAL,
 1 AS ES_ARCHIVO,
 1 AS USUARIO_ASIGNADOR*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_usuario`
--

DROP TABLE IF EXISTS sade_sector_usuario;
/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_usuario` AS SELECT 
 1 AS ID_SECTOR_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS NOMBRE_USUARIO,
 1 AS PROCESO,
 1 AS ESTADO_REGISTRO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_usr_repa_habilitada`
--

DROP TABLE IF EXISTS sade_usr_repa_habilitada;
/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_usr_repa_habilitada` AS SELECT 
 1 AS ID_USR_REPA_HABILITADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `cargos`
--

/*!50001 DROP VIEW IF EXISTS cargos*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW cargos AS select co_ged.cargos.ID AS ID,co_ged.cargos.CARGO AS CARGO,co_ged.cargos.VISIBLE AS VISIBLE,co_ged.cargos.USUARIO_CREACION AS USUARIO_CREACION,co_ged.cargos.FECHA_MODIFICACION AS FECHA_MODIFICACION,co_ged.cargos.FECHA_CREACION AS FECHA_CREACION,co_ged.cargos.VIGENTE AS VIGENTE,co_ged.cargos.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,co_ged.cargos.ID_REPARTICION AS ID_REPARTICION from co_ged.cargos */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `datos_usuario`
--

/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW datos_usuario AS select co_ged.datos_usuario.ID_DATO_USUARIO AS ID_DATO_USUARIO,co_ged.datos_usuario.MAIL AS MAIL,co_ged.datos_usuario.OCUPACION AS OCUPACION,co_ged.datos_usuario.USER_ AS USER_,co_ged.datos_usuario.USUARIO AS USUARIO,co_ged.datos_usuario.USER_SUPERIOR AS USER_SUPERIOR,co_ged.datos_usuario.MAIL_SUPERIOR AS MAIL_SUPERIOR,co_ged.datos_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,co_ged.datos_usuario.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,co_ged.datos_usuario.FECHA_CADUCIDAD_SECTOR_INTERNO AS FECHA_CADUCIDAD_SECTOR_INTERNO,co_ged.datos_usuario.ES_SECRETARIO AS ES_SECRETARIO,co_ged.datos_usuario.SECRETARIO AS SECRETARIO,co_ged.datos_usuario.APELLIDO_NOMBRE AS APELLIDO_NOMBRE,co_ged.datos_usuario.ACEPTACION_TYC AS ACEPTACION_TYC,co_ged.datos_usuario.NUMERO_CUIT AS NUMERO_CUIT,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_GEDO AS EXTERNALIZAR_FIRMA_EN_GEDO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_SIGA AS EXTERNALIZAR_FIRMA_EN_SIGA,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_CCOO AS EXTERNALIZAR_FIRMA_EN_CCOO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_LOYS AS EXTERNALIZAR_FIRMA_EN_LOYS,co_ged.datos_usuario.USUARIO_ASESOR AS USUARIO_ASESOR,co_ged.datos_usuario.NOTIFICAR_SOLICITUD_PF AS NOTIFICAR_SOLICITUD_PF,co_ged.datos_usuario.CARGO AS CARGO,co_ged.datos_usuario.CAMBIAR_MESA AS CAMBIAR_MESA from co_ged.datos_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `periodo_licencia`
--

/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW periodo_licencia AS select co_ged.periodo_licencia.ID_PERIODO_LICENCIA AS ID_PERIODO_LICENCIA,co_ged.periodo_licencia.FECHA_HORA_DESDE AS FECHA_HORA_DESDE,co_ged.periodo_licencia.FECHA_HORA_HASTA AS FECHA_HORA_HASTA,co_ged.periodo_licencia.APODERADO AS APODERADO,co_ged.periodo_licencia.CONDICION_PERIODO AS CONDICION_PERIODO,co_ged.periodo_licencia.USUARIO AS USUARIO,co_ged.periodo_licencia.FECHA_CANCELACION AS FECHA_CANCELACION from co_ged.periodo_licencia */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_estructura`
--

/*!50001 DROP VIEW IF EXISTS sade_estructura*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_estructura AS select track_ged.sade_estructura.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_estructura.CODIGO_ESTRUCTURA AS CODIGO_ESTRUCTURA,track_ged.sade_estructura.NOMBRE_ESTRUCTURA AS NOMBRE_ESTRUCTURA,track_ged.sade_estructura.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_estructura.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_estructura.GENERA_ALS AS GENERA_ALS,track_ged.sade_estructura.VERSION AS VERSION,track_ged.sade_estructura.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_estructura.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_estructura.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_estructura.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_estructura.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_estructura.ESTRUCTURA_PODER_EJECUTIVO AS ESTRUCTURA_PODER_EJECUTIVO from track_ged.sade_estructura */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select track_ged.sade_reparticion.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,track_ged.sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,track_ged.sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_reparticion.calle AS CALLE,track_ged.sade_reparticion.NUMERO AS NUMERO,track_ged.sade_reparticion.PISO AS PISO,track_ged.sade_reparticion.OFICINA AS OFICINA,track_ged.sade_reparticion.TELEFONO AS TELEFONO,track_ged.sade_reparticion.FAX AS FAX,track_ged.sade_reparticion.EMAIL AS EMAIL,track_ged.sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_reparticion.EN_RED AS EN_RED,track_ged.sade_reparticion.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_reparticion.VERSION AS VERSION,track_ged.sade_reparticion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_reparticion.ES_DGTAL AS ES_DGTAL,track_ged.sade_reparticion.REP_PADRE AS REP_PADRE,track_ged.sade_reparticion.COD_DGTAL AS COD_DGTAL,track_ged.sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,track_ged.sade_reparticion.MINISTERIO AS MINISTERIO,track_ged.sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO from track_ged.sade_reparticion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion_seleccionada`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion_seleccionada AS select track_ged.sade_reparticion_seleccionada.ID_REPARTICION_SELECCIONADA AS ID_REPARTICION_SELECCIONADA,track_ged.sade_reparticion_seleccionada.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion_seleccionada.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_reparticion_seleccionada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO from track_ged.sade_reparticion_seleccionada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_interno`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_interno AS select track_ged.sade_sector_interno.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_interno.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,track_ged.sade_sector_interno.NOMBRE_SECTOR_INTERNO AS NOMBRE_SECTOR_INTERNO,track_ged.sade_sector_interno.CALLE AS CALLE,track_ged.sade_sector_interno.NUMERO AS NUMERO,track_ged.sade_sector_interno.PISO AS PISO,track_ged.sade_sector_interno.OFICINA AS OFICINA,track_ged.sade_sector_interno.TELEFONO AS TELEFONO,track_ged.sade_sector_interno.FAX AS FAX,track_ged.sade_sector_interno.EMAIL AS EMAIL,track_ged.sade_sector_interno.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_sector_interno.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_sector_interno.EN_RED AS EN_RED,track_ged.sade_sector_interno.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_sector_interno.VERSION AS VERSION,track_ged.sade_sector_interno.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_interno.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_interno.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_interno.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_interno.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_interno.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_sector_interno.SECTOR_INTERNO_AGRUPACION_INDE AS SECTOR_INTERNO_AGRUPACION_INDE,track_ged.sade_sector_interno.ID_AGRUPACION_SECTOR_MESA AS ID_AGRUPACION_SECTOR_MESA,track_ged.sade_sector_interno.MESA_VIRTUAL AS MESA_VIRTUAL,track_ged.sade_sector_interno.ES_ARCHIVO AS ES_ARCHIVO,track_ged.sade_sector_interno.USUARIO_ASIGNADOR AS USUARIO_ASIGNADOR from track_ged.sade_sector_interno */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_usuario`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select track_ged.sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,track_ged.sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_sector_usuario.PROCESO AS PROCESO,track_ged.sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_usuario.VERSION AS VERSION,track_ged.sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_usuario.CARGO_ID AS CARGO_ID from track_ged.sade_sector_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_usr_repa_habilitada`
--

/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_usr_repa_habilitada AS select sade_usr_repa_habilitada.ID_USR_REPA_HABILITADA AS ID_USR_REPA_HABILITADA,sade_usr_repa_habilitada.ID_REPARTICION AS ID_REPARTICION,sade_usr_repa_habilitada.NOMBRE_USUARIO AS NOMBRE_USUARIO,sade_usr_repa_habilitada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,sade_usr_repa_habilitada.CARGO_ID AS CARGO_ID from co_ged.sade_usr_repa_habilitada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:47
CREATE DATABASE  IF NOT EXISTS `co_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `co_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: co_ged
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
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS ID_REPARTICION,
 1 AS CODIGO_REPARTICION,
 1 AS CODIGO_REPAR_INTER,
 1 AS NOMBRE_REPARTICION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS ID_ESTRUCTURA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DGTAL,
 1 AS REP_PADRE,
 1 AS COD_DGTAL,
 1 AS ID_JURISDICCION,
 1 AS MINISTERIO,
 1 AS ADMINISTRADOR_PRESUPUESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion_seleccionada`
--

DROP TABLE IF EXISTS sade_reparticion_seleccionada;
/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion_seleccionada` AS SELECT 
 1 AS ID_REPARTICION_SELECCIONADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_interno`
--

DROP TABLE IF EXISTS sade_sector_interno;
/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_interno` AS SELECT 
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS NOMBRE_SECTOR_INTERNO,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS CODIGO_REPARTICION,
 1 AS SECTOR_INTERNO_AGRUPACION_INDE,
 1 AS ID_AGRUPACION_SECTOR_MESA,
 1 AS MESA_VIRTUAL,
 1 AS ES_ARCHIVO,
 1 AS USUARIO_ASIGNADOR*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_usuario`
--

DROP TABLE IF EXISTS sade_sector_usuario;
/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_usuario` AS SELECT 
 1 AS ID_SECTOR_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS NOMBRE_USUARIO,
 1 AS PROCESO,
 1 AS ESTADO_REGISTRO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_usr_repa_habilitada`
--

DROP TABLE IF EXISTS sade_usr_repa_habilitada;
/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_usr_repa_habilitada` AS SELECT 
 1 AS ID_USR_REPA_HABILITADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `sade_reparticion`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select track_ged.sade_reparticion.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,track_ged.sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,track_ged.sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_reparticion.calle AS CALLE,track_ged.sade_reparticion.NUMERO AS NUMERO,track_ged.sade_reparticion.PISO AS PISO,track_ged.sade_reparticion.OFICINA AS OFICINA,track_ged.sade_reparticion.TELEFONO AS TELEFONO,track_ged.sade_reparticion.FAX AS FAX,track_ged.sade_reparticion.EMAIL AS EMAIL,track_ged.sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_reparticion.EN_RED AS EN_RED,track_ged.sade_reparticion.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_reparticion.VERSION AS VERSION,track_ged.sade_reparticion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_reparticion.ES_DGTAL AS ES_DGTAL,track_ged.sade_reparticion.REP_PADRE AS REP_PADRE,track_ged.sade_reparticion.COD_DGTAL AS COD_DGTAL,track_ged.sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,track_ged.sade_reparticion.MINISTERIO AS MINISTERIO,track_ged.sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO from track_ged.sade_reparticion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion_seleccionada`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion_seleccionada AS select track_ged.sade_reparticion_seleccionada.ID_REPARTICION_SELECCIONADA AS ID_REPARTICION_SELECCIONADA,track_ged.sade_reparticion_seleccionada.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion_seleccionada.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_reparticion_seleccionada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO from track_ged.sade_reparticion_seleccionada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_interno`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_interno AS select track_ged.sade_sector_interno.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_interno.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,track_ged.sade_sector_interno.NOMBRE_SECTOR_INTERNO AS NOMBRE_SECTOR_INTERNO,track_ged.sade_sector_interno.CALLE AS CALLE,track_ged.sade_sector_interno.NUMERO AS NUMERO,track_ged.sade_sector_interno.PISO AS PISO,track_ged.sade_sector_interno.OFICINA AS OFICINA,track_ged.sade_sector_interno.TELEFONO AS TELEFONO,track_ged.sade_sector_interno.FAX AS FAX,track_ged.sade_sector_interno.EMAIL AS EMAIL,track_ged.sade_sector_interno.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_sector_interno.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_sector_interno.EN_RED AS EN_RED,track_ged.sade_sector_interno.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_sector_interno.VERSION AS VERSION,track_ged.sade_sector_interno.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_interno.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_interno.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_interno.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_interno.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_interno.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_sector_interno.SECTOR_INTERNO_AGRUPACION_INDE AS SECTOR_INTERNO_AGRUPACION_INDE,track_ged.sade_sector_interno.ID_AGRUPACION_SECTOR_MESA AS ID_AGRUPACION_SECTOR_MESA,track_ged.sade_sector_interno.MESA_VIRTUAL AS MESA_VIRTUAL,track_ged.sade_sector_interno.ES_ARCHIVO AS ES_ARCHIVO,track_ged.sade_sector_interno.USUARIO_ASIGNADOR AS USUARIO_ASIGNADOR from track_ged.sade_sector_interno */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_usuario`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select track_ged.sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,track_ged.sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_sector_usuario.PROCESO AS PROCESO,track_ged.sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_usuario.VERSION AS VERSION,track_ged.sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_usuario.CARGO_ID AS CARGO_ID from track_ged.sade_sector_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_usr_repa_habilitada`
--

/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_usr_repa_habilitada AS select track_ged.sade_usr_repa_habilitada.ID_USR_REPA_HABILITADA AS ID_USR_REPA_HABILITADA,track_ged.sade_usr_repa_habilitada.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_usr_repa_habilitada.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_usr_repa_habilitada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_usr_repa_habilitada.CARGO_ID AS CARGO_ID from track_ged.sade_usr_repa_habilitada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:48
CREATE DATABASE  IF NOT EXISTS `tad_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tad_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: tad_ged
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:49
CREATE DATABASE  IF NOT EXISTS `track_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `track_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: track_ged
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
-- Temporary view structure for view `cargos`
--

DROP TABLE IF EXISTS cargos;
/*!50001 DROP VIEW IF EXISTS cargos*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `cargos` AS SELECT 
 1 AS ID,
 1 AS CARGO,
 1 AS VISIBLE,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS FECHA_CREACION,
 1 AS VIGENTE,
 1 AS USUARIO_MODIFICACION,
 1 AS ID_REPARTICION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `datos_usuario`
--

DROP TABLE IF EXISTS datos_usuario;
/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `datos_usuario` AS SELECT 
 1 AS ID_DATO_USUARIO,
 1 AS MAIL,
 1 AS OCUPACION,
 1 AS USER_,
 1 AS USUARIO,
 1 AS USER_SUPERIOR,
 1 AS MAIL_SUPERIOR,
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS FECHA_CADUCIDAD_SECTOR_INTERNO,
 1 AS ES_SECRETARIO,
 1 AS SECRETARIO,
 1 AS APELLIDO_NOMBRE,
 1 AS ACEPTACION_TYC,
 1 AS NUMERO_CUIT,
 1 AS EXTERNALIZAR_FIRMA_EN_GEDO,
 1 AS EXTERNALIZAR_FIRMA_EN_SIGA,
 1 AS EXTERNALIZAR_FIRMA_EN_CCOO,
 1 AS EXTERNALIZAR_FIRMA_EN_LOYS,
 1 AS USUARIO_ASESOR,
 1 AS NOTIFICAR_SOLICITUD_PF,
 1 AS CARGO,
 1 AS CAMBIAR_MESA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `periodo_licencia`
--

DROP TABLE IF EXISTS periodo_licencia;
/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `periodo_licencia` AS SELECT 
 1 AS ID_PERIODO_LICENCIA,
 1 AS FECHA_HORA_DESDE,
 1 AS FECHA_HORA_HASTA,
 1 AS APODERADO,
 1 AS CONDICION_PERIODO,
 1 AS USUARIO,
 1 AS FECHA_CANCELACION*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `cargos`
--

/*!50001 DROP VIEW IF EXISTS cargos*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW cargos AS select co_ged.cargos.ID AS ID,co_ged.cargos.CARGO AS CARGO,co_ged.cargos.VISIBLE AS VISIBLE,co_ged.cargos.USUARIO_CREACION AS USUARIO_CREACION,co_ged.cargos.FECHA_MODIFICACION AS FECHA_MODIFICACION,co_ged.cargos.FECHA_CREACION AS FECHA_CREACION,co_ged.cargos.VIGENTE AS VIGENTE,co_ged.cargos.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,co_ged.cargos.ID_REPARTICION AS ID_REPARTICION from co_ged.cargos */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `datos_usuario`
--

/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW datos_usuario AS select co_ged.datos_usuario.ID_DATO_USUARIO AS ID_DATO_USUARIO,co_ged.datos_usuario.MAIL AS MAIL,co_ged.datos_usuario.OCUPACION AS OCUPACION,co_ged.datos_usuario.USER_ AS USER_,co_ged.datos_usuario.USUARIO AS USUARIO,co_ged.datos_usuario.USER_SUPERIOR AS USER_SUPERIOR,co_ged.datos_usuario.MAIL_SUPERIOR AS MAIL_SUPERIOR,co_ged.datos_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,co_ged.datos_usuario.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,co_ged.datos_usuario.FECHA_CADUCIDAD_SECTOR_INTERNO AS FECHA_CADUCIDAD_SECTOR_INTERNO,co_ged.datos_usuario.ES_SECRETARIO AS ES_SECRETARIO,co_ged.datos_usuario.SECRETARIO AS SECRETARIO,co_ged.datos_usuario.APELLIDO_NOMBRE AS APELLIDO_NOMBRE,co_ged.datos_usuario.ACEPTACION_TYC AS ACEPTACION_TYC,co_ged.datos_usuario.NUMERO_CUIT AS NUMERO_CUIT,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_GEDO AS EXTERNALIZAR_FIRMA_EN_GEDO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_SIGA AS EXTERNALIZAR_FIRMA_EN_SIGA,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_CCOO AS EXTERNALIZAR_FIRMA_EN_CCOO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_LOYS AS EXTERNALIZAR_FIRMA_EN_LOYS,co_ged.datos_usuario.USUARIO_ASESOR AS USUARIO_ASESOR,co_ged.datos_usuario.NOTIFICAR_SOLICITUD_PF AS NOTIFICAR_SOLICITUD_PF,co_ged.datos_usuario.CARGO AS CARGO,co_ged.datos_usuario.CAMBIAR_MESA AS CAMBIAR_MESA from co_ged.datos_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `periodo_licencia`
--

/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW periodo_licencia AS select co_ged.periodo_licencia.ID_PERIODO_LICENCIA AS ID_PERIODO_LICENCIA,co_ged.periodo_licencia.FECHA_HORA_DESDE AS FECHA_HORA_DESDE,co_ged.periodo_licencia.FECHA_HORA_HASTA AS FECHA_HORA_HASTA,co_ged.periodo_licencia.APODERADO AS APODERADO,co_ged.periodo_licencia.CONDICION_PERIODO AS CONDICION_PERIODO,co_ged.periodo_licencia.USUARIO AS USUARIO,co_ged.periodo_licencia.FECHA_CANCELACION AS FECHA_CANCELACION from co_ged.periodo_licencia */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:49
CREATE DATABASE  IF NOT EXISTS `numerador_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `numerador_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: numerador_ged
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
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS ID_REPARTICION,
 1 AS CODIGO_REPARTICION,
 1 AS CODIGO_REPAR_INTER,
 1 AS NOMBRE_REPARTICION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS ID_ESTRUCTURA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DGTAL,
 1 AS REP_PADRE,
 1 AS COD_DGTAL,
 1 AS ID_JURISDICCION,
 1 AS MINISTERIO,
 1 AS ADMINISTRADOR_PRESUPUESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_interno`
--

DROP TABLE IF EXISTS sade_sector_interno;
/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_interno` AS SELECT 
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS NOMBRE_SECTOR_INTERNO,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS CODIGO_REPARTICION,
 1 AS SECTOR_INTERNO_AGRUPACION_INDE,
 1 AS ID_AGRUPACION_SECTOR_MESA,
 1 AS MESA_VIRTUAL,
 1 AS ES_ARCHIVO,
 1 AS USUARIO_ASIGNADOR*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_usuario`
--

DROP TABLE IF EXISTS sade_sector_usuario;
/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_usuario` AS SELECT 
 1 AS ID_SECTOR_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS NOMBRE_USUARIO,
 1 AS PROCESO,
 1 AS ESTADO_REGISTRO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `sade_reparticion`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select track_ged.sade_reparticion.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,track_ged.sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,track_ged.sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_reparticion.calle AS CALLE,track_ged.sade_reparticion.NUMERO AS NUMERO,track_ged.sade_reparticion.PISO AS PISO,track_ged.sade_reparticion.OFICINA AS OFICINA,track_ged.sade_reparticion.TELEFONO AS TELEFONO,track_ged.sade_reparticion.FAX AS FAX,track_ged.sade_reparticion.EMAIL AS EMAIL,track_ged.sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_reparticion.EN_RED AS EN_RED,track_ged.sade_reparticion.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_reparticion.VERSION AS VERSION,track_ged.sade_reparticion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_reparticion.ES_DGTAL AS ES_DGTAL,track_ged.sade_reparticion.REP_PADRE AS REP_PADRE,track_ged.sade_reparticion.COD_DGTAL AS COD_DGTAL,track_ged.sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,track_ged.sade_reparticion.MINISTERIO AS MINISTERIO,track_ged.sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO from track_ged.sade_reparticion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_interno`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_interno AS select track_ged.sade_sector_interno.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_interno.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,track_ged.sade_sector_interno.NOMBRE_SECTOR_INTERNO AS NOMBRE_SECTOR_INTERNO,track_ged.sade_sector_interno.CALLE AS CALLE,track_ged.sade_sector_interno.NUMERO AS NUMERO,track_ged.sade_sector_interno.PISO AS PISO,track_ged.sade_sector_interno.OFICINA AS OFICINA,track_ged.sade_sector_interno.TELEFONO AS TELEFONO,track_ged.sade_sector_interno.FAX AS FAX,track_ged.sade_sector_interno.EMAIL AS EMAIL,track_ged.sade_sector_interno.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_sector_interno.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_sector_interno.EN_RED AS EN_RED,track_ged.sade_sector_interno.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_sector_interno.VERSION AS VERSION,track_ged.sade_sector_interno.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_interno.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_interno.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_interno.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_interno.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_interno.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_sector_interno.SECTOR_INTERNO_AGRUPACION_INDE AS SECTOR_INTERNO_AGRUPACION_INDE,track_ged.sade_sector_interno.ID_AGRUPACION_SECTOR_MESA AS ID_AGRUPACION_SECTOR_MESA,track_ged.sade_sector_interno.MESA_VIRTUAL AS MESA_VIRTUAL,track_ged.sade_sector_interno.ES_ARCHIVO AS ES_ARCHIVO,track_ged.sade_sector_interno.USUARIO_ASIGNADOR AS USUARIO_ASIGNADOR from track_ged.sade_sector_interno */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_usuario`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select track_ged.sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,track_ged.sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_sector_usuario.PROCESO AS PROCESO,track_ged.sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_usuario.VERSION AS VERSION,track_ged.sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_usuario.CARGO_ID AS CARGO_ID from track_ged.sade_sector_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:50
CREATE DATABASE  IF NOT EXISTS `ee_ged` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ee_ged`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: ee_ged
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
-- Temporary view structure for view `cargos`
--

DROP TABLE IF EXISTS cargos;
/*!50001 DROP VIEW IF EXISTS cargos*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `cargos` AS SELECT 
 1 AS ID,
 1 AS CARGO,
 1 AS VISIBLE,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS FECHA_CREACION,
 1 AS VIGENTE,
 1 AS USUARIO_MODIFICACION,
 1 AS ID_REPARTICION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `datos_usuario`
--

DROP TABLE IF EXISTS datos_usuario;
/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `datos_usuario` AS SELECT 
 1 AS ID_DATO_USUARIO,
 1 AS MAIL,
 1 AS OCUPACION,
 1 AS USER_,
 1 AS USUARIO,
 1 AS USER_SUPERIOR,
 1 AS MAIL_SUPERIOR,
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS FECHA_CADUCIDAD_SECTOR_INTERNO,
 1 AS ES_SECRETARIO,
 1 AS SECRETARIO,
 1 AS APELLIDO_NOMBRE,
 1 AS ACEPTACION_TYC,
 1 AS NUMERO_CUIT,
 1 AS EXTERNALIZAR_FIRMA_EN_GEDO,
 1 AS EXTERNALIZAR_FIRMA_EN_SIGA,
 1 AS EXTERNALIZAR_FIRMA_EN_CCOO,
 1 AS EXTERNALIZAR_FIRMA_EN_LOYS,
 1 AS USUARIO_ASESOR,
 1 AS NOTIFICAR_SOLICITUD_PF,
 1 AS CARGO,
 1 AS CAMBIAR_MESA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `gedo_documento`
--

DROP TABLE IF EXISTS gedo_documento;
/*!50001 DROP VIEW IF EXISTS gedo_documento*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `gedo_documento` AS SELECT 
 1 AS ID,
 1 AS NUMERO,
 1 AS NUMEROESPECIAL,
 1 AS REPARTICION,
 1 AS ANIO,
 1 AS MOTIVO,
 1 AS USUARIOGENERADOR,
 1 AS FECHACREACION,
 1 AS WORKFLOWORIGEN,
 1 AS NUMERO_SADE_PAPEL,
 1 AS TIPO,
 1 AS SISTEMAORIGEN,
 1 AS SISTEMAINICIADOR,
 1 AS USUARIOINICIADOR,
 1 AS TIPORESERVA,
 1 AS VERSION,
 1 AS CCOO_ID_DOC,
 1 AS CCOO_FECHA_CREACION,
 1 AS APODERADO,
 1 AS REPARTICION_ACTUAL,
 1 AS FECHA_MODIFICACION,
 1 AS ID_GUARDA_DOCUMENTAL,
 1 AS PESO,
 1 AS MOTIVO_DEPURACION,
 1 AS FECHA_DEPURACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `gedo_tipodocumento`
--

DROP TABLE IF EXISTS gedo_tipodocumento;
/*!50001 DROP VIEW IF EXISTS gedo_tipodocumento*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `gedo_tipodocumento` AS SELECT 
 1 AS ID,
 1 AS NOMBRE,
 1 AS ACRONIMO,
 1 AS DESCRIPCION,
 1 AS ESESPECIAL,
 1 AS TIENETOKEN,
 1 AS TIENETEMPLATE,
 1 AS ESTADO,
 1 AS IDTIPODOCUMENTOSADE,
 1 AS CODIGOTIPODOCUMENTOSADE,
 1 AS ESCONFIDENCIAL,
 1 AS ESFIRMAEXTERNA,
 1 AS ESMANUAL,
 1 AS ESAUTOMATICA,
 1 AS ESFIRMACONJUNTA,
 1 AS FAMILIA,
 1 AS TIENEAVISO,
 1 AS PERMITE_EMBEBIDOS,
 1 AS TIPOPRODUCCION,
 1 AS ES_NOTIFICABLE,
 1 AS VERSION,
 1 AS TAMANO,
 1 AS ESOCULTO,
 1 AS ES_COMUNICABLE,
 1 AS USUARIO_CREADOR,
 1 AS FECHA_CREACION,
 1 AS ESFIRMAEXTERNACONENCABEZADO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `periodo_licencia`
--

DROP TABLE IF EXISTS periodo_licencia;
/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `periodo_licencia` AS SELECT 
 1 AS ID_PERIODO_LICENCIA,
 1 AS FECHA_HORA_DESDE,
 1 AS FECHA_HORA_HASTA,
 1 AS APODERADO,
 1 AS CONDICION_PERIODO,
 1 AS USUARIO,
 1 AS FECHA_CANCELACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_actuacion`
--

DROP TABLE IF EXISTS sade_actuacion;
/*!50001 DROP VIEW IF EXISTS sade_actuacion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_actuacion` AS SELECT 
 1 AS ID_ACTUACION,
 1 AS CODIGO_ACTUACION,
 1 AS NOMBRE_ACTUACION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS INICIA_ACTUACION,
 1 AS JERARQUIA,
 1 AS INCORPORADO,
 1 AS AGREGADO,
 1 AS ANULADO,
 1 AS DESGLOSADO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DOCUMENTO,
 1 AS DESHABILITADO_PAPEL*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS ID_REPARTICION,
 1 AS CODIGO_REPARTICION,
 1 AS CODIGO_REPAR_INTER,
 1 AS NOMBRE_REPARTICION,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS ID_ESTRUCTURA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS ES_DGTAL,
 1 AS REP_PADRE,
 1 AS COD_DGTAL,
 1 AS ID_JURISDICCION,
 1 AS MINISTERIO,
 1 AS ADMINISTRADOR_PRESUPUESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_reparticion_seleccionada`
--

DROP TABLE IF EXISTS sade_reparticion_seleccionada;
/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion_seleccionada` AS SELECT 
 1 AS ID_REPARTICION_SELECCIONADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_interno`
--

DROP TABLE IF EXISTS sade_sector_interno;
/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_interno` AS SELECT 
 1 AS ID_SECTOR_INTERNO,
 1 AS CODIGO_SECTOR_INTERNO,
 1 AS NOMBRE_SECTOR_INTERNO,
 1 AS CALLE,
 1 AS NUMERO,
 1 AS PISO,
 1 AS OFICINA,
 1 AS TELEFONO,
 1 AS FAX,
 1 AS EMAIL,
 1 AS VIGENCIA_DESDE,
 1 AS VIGENCIA_HASTA,
 1 AS EN_RED,
 1 AS SECTOR_MESA,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS ESTADO_REGISTRO,
 1 AS CODIGO_REPARTICION,
 1 AS SECTOR_INTERNO_AGRUPACION_INDE,
 1 AS ID_AGRUPACION_SECTOR_MESA,
 1 AS MESA_VIRTUAL,
 1 AS ES_ARCHIVO,
 1 AS USUARIO_ASIGNADOR*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_sector_usuario`
--

DROP TABLE IF EXISTS sade_sector_usuario;
/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_sector_usuario` AS SELECT 
 1 AS ID_SECTOR_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS NOMBRE_USUARIO,
 1 AS PROCESO,
 1 AS ESTADO_REGISTRO,
 1 AS VERSION,
 1 AS FECHA_CREACION,
 1 AS USUARIO_CREACION,
 1 AS FECHA_MODIFICACION,
 1 AS USUARIO_MODIFICACION,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sade_usr_repa_habilitada`
--

DROP TABLE IF EXISTS sade_usr_repa_habilitada;
/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_usr_repa_habilitada` AS SELECT 
 1 AS ID_USR_REPA_HABILITADA,
 1 AS ID_REPARTICION,
 1 AS NOMBRE_USUARIO,
 1 AS ID_SECTOR_INTERNO,
 1 AS CARGO_ID*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `cargos`
--

/*!50001 DROP VIEW IF EXISTS cargos*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW cargos AS select co_ged.cargos.ID AS ID,co_ged.cargos.CARGO AS CARGO,co_ged.cargos.VISIBLE AS VISIBLE,co_ged.cargos.USUARIO_CREACION AS USUARIO_CREACION,co_ged.cargos.FECHA_MODIFICACION AS FECHA_MODIFICACION,co_ged.cargos.FECHA_CREACION AS FECHA_CREACION,co_ged.cargos.VIGENTE AS VIGENTE,co_ged.cargos.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,co_ged.cargos.ID_REPARTICION AS ID_REPARTICION from co_ged.cargos */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `datos_usuario`
--

/*!50001 DROP VIEW IF EXISTS datos_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW datos_usuario AS select co_ged.datos_usuario.ID_DATO_USUARIO AS ID_DATO_USUARIO,co_ged.datos_usuario.MAIL AS MAIL,co_ged.datos_usuario.OCUPACION AS OCUPACION,co_ged.datos_usuario.USER_ AS USER_,co_ged.datos_usuario.USUARIO AS USUARIO,co_ged.datos_usuario.USER_SUPERIOR AS USER_SUPERIOR,co_ged.datos_usuario.MAIL_SUPERIOR AS MAIL_SUPERIOR,co_ged.datos_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,co_ged.datos_usuario.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,co_ged.datos_usuario.FECHA_CADUCIDAD_SECTOR_INTERNO AS FECHA_CADUCIDAD_SECTOR_INTERNO,co_ged.datos_usuario.ES_SECRETARIO AS ES_SECRETARIO,co_ged.datos_usuario.SECRETARIO AS SECRETARIO,co_ged.datos_usuario.APELLIDO_NOMBRE AS APELLIDO_NOMBRE,co_ged.datos_usuario.ACEPTACION_TYC AS ACEPTACION_TYC,co_ged.datos_usuario.NUMERO_CUIT AS NUMERO_CUIT,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_GEDO AS EXTERNALIZAR_FIRMA_EN_GEDO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_SIGA AS EXTERNALIZAR_FIRMA_EN_SIGA,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_CCOO AS EXTERNALIZAR_FIRMA_EN_CCOO,co_ged.datos_usuario.EXTERNALIZAR_FIRMA_EN_LOYS AS EXTERNALIZAR_FIRMA_EN_LOYS,co_ged.datos_usuario.USUARIO_ASESOR AS USUARIO_ASESOR,co_ged.datos_usuario.NOTIFICAR_SOLICITUD_PF AS NOTIFICAR_SOLICITUD_PF,co_ged.datos_usuario.CARGO AS CARGO,co_ged.datos_usuario.CAMBIAR_MESA AS CAMBIAR_MESA from co_ged.datos_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `gedo_documento`
--

/*!50001 DROP VIEW IF EXISTS gedo_documento*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_documento AS select gedo_ged.gedo_documento.ID AS ID,gedo_ged.gedo_documento.NUMERO AS NUMERO,gedo_ged.gedo_documento.NUMEROESPECIAL AS NUMEROESPECIAL,gedo_ged.gedo_documento.REPARTICION AS REPARTICION,gedo_ged.gedo_documento.ANIO AS ANIO,gedo_ged.gedo_documento.MOTIVO AS MOTIVO,gedo_ged.gedo_documento.USUARIOGENERADOR AS USUARIOGENERADOR,gedo_ged.gedo_documento.FECHACREACION AS FECHACREACION,gedo_ged.gedo_documento.WORKFLOWORIGEN AS WORKFLOWORIGEN,gedo_ged.gedo_documento.NUMERO_SADE_PAPEL AS NUMERO_SADE_PAPEL,gedo_ged.gedo_documento.TIPO AS TIPO,gedo_ged.gedo_documento.SISTEMAORIGEN AS SISTEMAORIGEN,gedo_ged.gedo_documento.SISTEMAINICIADOR AS SISTEMAINICIADOR,gedo_ged.gedo_documento.USUARIOINICIADOR AS USUARIOINICIADOR,gedo_ged.gedo_documento.TIPORESERVA AS TIPORESERVA,gedo_ged.gedo_documento.VERSION AS VERSION,gedo_ged.gedo_documento.CCOO_ID_DOC AS CCOO_ID_DOC,gedo_ged.gedo_documento.CCOO_FECHA_CREACION AS CCOO_FECHA_CREACION,gedo_ged.gedo_documento.APODERADO AS APODERADO,gedo_ged.gedo_documento.REPARTICION_ACTUAL AS REPARTICION_ACTUAL,gedo_ged.gedo_documento.FECHA_MODIFICACION AS FECHA_MODIFICACION,gedo_ged.gedo_documento.ID_GUARDA_DOCUMENTAL AS ID_GUARDA_DOCUMENTAL,gedo_ged.gedo_documento.PESO AS PESO,gedo_ged.gedo_documento.MOTIVO_DEPURACION AS MOTIVO_DEPURACION,gedo_ged.gedo_documento.FECHA_DEPURACION AS FECHA_DEPURACION from gedo_ged.gedo_documento */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `gedo_tipodocumento`
--

/*!50001 DROP VIEW IF EXISTS gedo_tipodocumento*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_tipodocumento AS select gedo_ged.gedo_tipodocumento.ID AS ID,gedo_ged.gedo_tipodocumento.NOMBRE AS NOMBRE,gedo_ged.gedo_tipodocumento.ACRONIMO AS ACRONIMO,gedo_ged.gedo_tipodocumento.DESCRIPCION AS DESCRIPCION,gedo_ged.gedo_tipodocumento.ESESPECIAL AS ESESPECIAL,gedo_ged.gedo_tipodocumento.TIENETOKEN AS TIENETOKEN,gedo_ged.gedo_tipodocumento.TIENETEMPLATE AS TIENETEMPLATE,gedo_ged.gedo_tipodocumento.ESTADO AS ESTADO,gedo_ged.gedo_tipodocumento.IDTIPODOCUMENTOSADE AS IDTIPODOCUMENTOSADE,gedo_ged.gedo_tipodocumento.CODIGOTIPODOCUMENTOSADE AS CODIGOTIPODOCUMENTOSADE,gedo_ged.gedo_tipodocumento.ESCONFIDENCIAL AS ESCONFIDENCIAL,gedo_ged.gedo_tipodocumento.ESFIRMAEXTERNA AS ESFIRMAEXTERNA,gedo_ged.gedo_tipodocumento.ESMANUAL AS ESMANUAL,gedo_ged.gedo_tipodocumento.ESAUTOMATICA AS ESAUTOMATICA,gedo_ged.gedo_tipodocumento.ESFIRMACONJUNTA AS ESFIRMACONJUNTA,gedo_ged.gedo_tipodocumento.FAMILIA AS FAMILIA,gedo_ged.gedo_tipodocumento.TIENEAVISO AS TIENEAVISO,gedo_ged.gedo_tipodocumento.PERMITE_EMBEBIDOS AS PERMITE_EMBEBIDOS,gedo_ged.gedo_tipodocumento.TIPOPRODUCCION AS TIPOPRODUCCION,gedo_ged.gedo_tipodocumento.ES_NOTIFICABLE AS ES_NOTIFICABLE,gedo_ged.gedo_tipodocumento.VERSION AS VERSION,gedo_ged.gedo_tipodocumento.TAMANO AS TAMANO,gedo_ged.gedo_tipodocumento.ESOCULTO AS ESOCULTO,gedo_ged.gedo_tipodocumento.ES_COMUNICABLE AS ES_COMUNICABLE,gedo_ged.gedo_tipodocumento.USUARIO_CREADOR AS USUARIO_CREADOR,gedo_ged.gedo_tipodocumento.FECHA_CREACION AS FECHA_CREACION,gedo_ged.gedo_tipodocumento.ESFIRMAEXTERNACONENCABEZADO AS ESFIRMAEXTERNACONENCABEZADO from gedo_ged.gedo_tipodocumento */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `periodo_licencia`
--

/*!50001 DROP VIEW IF EXISTS periodo_licencia*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW periodo_licencia AS select co_ged.periodo_licencia.ID_PERIODO_LICENCIA AS ID_PERIODO_LICENCIA,co_ged.periodo_licencia.FECHA_HORA_DESDE AS FECHA_HORA_DESDE,co_ged.periodo_licencia.FECHA_HORA_HASTA AS FECHA_HORA_HASTA,co_ged.periodo_licencia.APODERADO AS APODERADO,co_ged.periodo_licencia.CONDICION_PERIODO AS CONDICION_PERIODO,co_ged.periodo_licencia.USUARIO AS USUARIO,co_ged.periodo_licencia.FECHA_CANCELACION AS FECHA_CANCELACION from co_ged.periodo_licencia */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_actuacion`
--

/*!50001 DROP VIEW IF EXISTS sade_actuacion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_actuacion AS select track_ged.sade_actuacion.ID_ACTUACION AS ID_ACTUACION,track_ged.sade_actuacion.CODIGO_ACTUACION AS CODIGO_ACTUACION,track_ged.sade_actuacion.NOMBRE_ACTUACION AS NOMBRE_ACTUACION,track_ged.sade_actuacion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_actuacion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_actuacion.INICIA_ACTUACION AS INICIA_ACTUACION,track_ged.sade_actuacion.JERARQUIA AS JERARQUIA,track_ged.sade_actuacion.INCORPORADO AS INCORPORADO,track_ged.sade_actuacion.AGREGADO AS AGREGADO,track_ged.sade_actuacion.ANULADO AS ANULADO,track_ged.sade_actuacion.DESGLOSADO AS DESGLOSADO,track_ged.sade_actuacion.VERSION AS VERSION,track_ged.sade_actuacion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_actuacion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_actuacion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_actuacion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_actuacion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_actuacion.ES_DOCUMENTO AS ES_DOCUMENTO,track_ged.sade_actuacion.DESHABILITADO_PAPEL AS DESHABILITADO_PAPEL from track_ged.sade_actuacion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select track_ged.sade_reparticion.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,track_ged.sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,track_ged.sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_reparticion.calle AS CALLE,track_ged.sade_reparticion.NUMERO AS NUMERO,track_ged.sade_reparticion.PISO AS PISO,track_ged.sade_reparticion.OFICINA AS OFICINA,track_ged.sade_reparticion.TELEFONO AS TELEFONO,track_ged.sade_reparticion.FAX AS FAX,track_ged.sade_reparticion.EMAIL AS EMAIL,track_ged.sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,track_ged.sade_reparticion.EN_RED AS EN_RED,track_ged.sade_reparticion.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_reparticion.VERSION AS VERSION,track_ged.sade_reparticion.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_reparticion.ES_DGTAL AS ES_DGTAL,track_ged.sade_reparticion.REP_PADRE AS REP_PADRE,track_ged.sade_reparticion.COD_DGTAL AS COD_DGTAL,track_ged.sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,track_ged.sade_reparticion.MINISTERIO AS MINISTERIO,track_ged.sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO from track_ged.sade_reparticion */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_reparticion_seleccionada`
--

/*!50001 DROP VIEW IF EXISTS sade_reparticion_seleccionada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion_seleccionada AS select track_ged.sade_reparticion_seleccionada.ID_REPARTICION_SELECCIONADA AS ID_REPARTICION_SELECCIONADA,track_ged.sade_reparticion_seleccionada.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_reparticion_seleccionada.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_reparticion_seleccionada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO from track_ged.sade_reparticion_seleccionada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_interno`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_interno*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_interno AS select track_ged.sade_sector_interno.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_interno.CODIGO_SECTOR_INTERNO AS CODIGO_SECTOR_INTERNO,track_ged.sade_sector_interno.NOMBRE_SECTOR_INTERNO AS NOMBRE_SECTOR_INTERNO,track_ged.sade_sector_interno.CALLE AS CALLE,track_ged.sade_sector_interno.NUMERO AS NUMERO,track_ged.sade_sector_interno.PISO AS PISO,track_ged.sade_sector_interno.OFICINA AS OFICINA,track_ged.sade_sector_interno.TELEFONO AS TELEFONO,track_ged.sade_sector_interno.FAX AS FAX,track_ged.sade_sector_interno.EMAIL AS EMAIL,track_ged.sade_sector_interno.VIGENCIA_DESDE AS VIGENCIA_DESDE,track_ged.sade_sector_interno.VIGENCIA_HASTA AS VIGENCIA_HASTA,track_ged.sade_sector_interno.EN_RED AS EN_RED,track_ged.sade_sector_interno.SECTOR_MESA AS SECTOR_MESA,track_ged.sade_sector_interno.VERSION AS VERSION,track_ged.sade_sector_interno.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_interno.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_interno.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_interno.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_interno.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_interno.CODIGO_REPARTICION AS CODIGO_REPARTICION,track_ged.sade_sector_interno.SECTOR_INTERNO_AGRUPACION_INDE AS SECTOR_INTERNO_AGRUPACION_INDE,track_ged.sade_sector_interno.ID_AGRUPACION_SECTOR_MESA AS ID_AGRUPACION_SECTOR_MESA,track_ged.sade_sector_interno.MESA_VIRTUAL AS MESA_VIRTUAL,track_ged.sade_sector_interno.ES_ARCHIVO AS ES_ARCHIVO,track_ged.sade_sector_interno.USUARIO_ASIGNADOR AS USUARIO_ASIGNADOR from track_ged.sade_sector_interno */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_sector_usuario`
--

/*!50001 DROP VIEW IF EXISTS sade_sector_usuario*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select track_ged.sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,track_ged.sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_sector_usuario.PROCESO AS PROCESO,track_ged.sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,track_ged.sade_sector_usuario.VERSION AS VERSION,track_ged.sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,track_ged.sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,track_ged.sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,track_ged.sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,track_ged.sade_sector_usuario.CARGO_ID AS CARGO_ID from track_ged.sade_sector_usuario */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sade_usr_repa_habilitada`
--

/*!50001 DROP VIEW IF EXISTS sade_usr_repa_habilitada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=egov@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_usr_repa_habilitada AS select track_ged.sade_usr_repa_habilitada.ID_USR_REPA_HABILITADA AS ID_USR_REPA_HABILITADA,track_ged.sade_usr_repa_habilitada.ID_REPARTICION AS ID_REPARTICION,track_ged.sade_usr_repa_habilitada.NOMBRE_USUARIO AS NOMBRE_USUARIO,track_ged.sade_usr_repa_habilitada.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,track_ged.sade_usr_repa_habilitada.CARGO_ID AS CARGO_ID from track_ged.sade_usr_repa_habilitada */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:25:51
