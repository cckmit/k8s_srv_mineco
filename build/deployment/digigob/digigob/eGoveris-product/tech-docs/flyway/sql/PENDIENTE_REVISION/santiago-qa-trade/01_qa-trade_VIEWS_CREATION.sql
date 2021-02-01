-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_te_trade
-- ------------------------------------------------------
-- Server version	5.7.17-ndb-7.5.5-cluster-gpl

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

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
/*!50013 DEFINER=root@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_documento AS select qa_deo_trade.gedo_documento.ID AS ID,qa_deo_trade.gedo_documento.NUMERO AS NUMERO,qa_deo_trade.gedo_documento.NUMEROESPECIAL AS NUMEROESPECIAL,qa_deo_trade.gedo_documento.REPARTICION AS REPARTICION,qa_deo_trade.gedo_documento.ANIO AS ANIO,qa_deo_trade.gedo_documento.MOTIVO AS MOTIVO,qa_deo_trade.gedo_documento.USUARIOGENERADOR AS USUARIOGENERADOR,qa_deo_trade.gedo_documento.FECHACREACION AS FECHACREACION,qa_deo_trade.gedo_documento.WORKFLOWORIGEN AS WORKFLOWORIGEN,qa_deo_trade.gedo_documento.NUMERO_SADE_PAPEL AS NUMERO_SADE_PAPEL,qa_deo_trade.gedo_documento.TIPO AS TIPO,qa_deo_trade.gedo_documento.SISTEMAORIGEN AS SISTEMAORIGEN,qa_deo_trade.gedo_documento.SISTEMAINICIADOR AS SISTEMAINICIADOR,qa_deo_trade.gedo_documento.USUARIOINICIADOR AS USUARIOINICIADOR,qa_deo_trade.gedo_documento.TIPORESERVA AS TIPORESERVA,qa_deo_trade.gedo_documento.VERSION AS VERSION,qa_deo_trade.gedo_documento.CCOO_ID_DOC AS CCOO_ID_DOC,qa_deo_trade.gedo_documento.CCOO_FECHA_CREACION AS CCOO_FECHA_CREACION,qa_deo_trade.gedo_documento.APODERADO AS APODERADO,qa_deo_trade.gedo_documento.REPARTICION_ACTUAL AS REPARTICION_ACTUAL,qa_deo_trade.gedo_documento.FECHA_MODIFICACION AS FECHA_MODIFICACION,qa_deo_trade.gedo_documento.ID_GUARDA_DOCUMENTAL AS ID_GUARDA_DOCUMENTAL,qa_deo_trade.gedo_documento.PESO AS PESO,qa_deo_trade.gedo_documento.MOTIVO_DEPURACION AS MOTIVO_DEPURACION,qa_deo_trade.gedo_documento.FECHA_DEPURACION AS FECHA_DEPURACION from qa_deo_trade.gedo_documento */;
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
/*!50013 DEFINER=root@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_tipodocumento AS select qa_deo_trade.gedo_tipodocumento.ID AS ID,qa_deo_trade.gedo_tipodocumento.NOMBRE AS NOMBRE,qa_deo_trade.gedo_tipodocumento.ACRONIMO AS ACRONIMO,qa_deo_trade.gedo_tipodocumento.DESCRIPCION AS DESCRIPCION,qa_deo_trade.gedo_tipodocumento.ESESPECIAL AS ESESPECIAL,qa_deo_trade.gedo_tipodocumento.TIENETOKEN AS TIENETOKEN,qa_deo_trade.gedo_tipodocumento.TIENETEMPLATE AS TIENETEMPLATE,qa_deo_trade.gedo_tipodocumento.ESTADO AS ESTADO,qa_deo_trade.gedo_tipodocumento.IDTIPODOCUMENTOSADE AS IDTIPODOCUMENTOSADE,qa_deo_trade.gedo_tipodocumento.CODIGOTIPODOCUMENTOSADE AS CODIGOTIPODOCUMENTOSADE,qa_deo_trade.gedo_tipodocumento.ESCONFIDENCIAL AS ESCONFIDENCIAL,qa_deo_trade.gedo_tipodocumento.ESFIRMAEXTERNA AS ESFIRMAEXTERNA,qa_deo_trade.gedo_tipodocumento.ESMANUAL AS ESMANUAL,qa_deo_trade.gedo_tipodocumento.ESAUTOMATICA AS ESAUTOMATICA,qa_deo_trade.gedo_tipodocumento.ESFIRMACONJUNTA AS ESFIRMACONJUNTA,qa_deo_trade.gedo_tipodocumento.FAMILIA AS FAMILIA,qa_deo_trade.gedo_tipodocumento.TIENEAVISO AS TIENEAVISO,qa_deo_trade.gedo_tipodocumento.PERMITE_EMBEBIDOS AS PERMITE_EMBEBIDOS,qa_deo_trade.gedo_tipodocumento.TIPOPRODUCCION AS TIPOPRODUCCION,qa_deo_trade.gedo_tipodocumento.ES_NOTIFICABLE AS ES_NOTIFICABLE,qa_deo_trade.gedo_tipodocumento.VERSION AS VERSION,qa_deo_trade.gedo_tipodocumento.TAMANO AS TAMANO,qa_deo_trade.gedo_tipodocumento.ESOCULTO AS ESOCULTO,qa_deo_trade.gedo_tipodocumento.ES_COMUNICABLE AS ES_COMUNICABLE,qa_deo_trade.gedo_tipodocumento.USUARIO_CREADOR AS USUARIO_CREADOR,qa_deo_trade.gedo_tipodocumento.FECHA_CREACION AS FECHA_CREACION,qa_deo_trade.gedo_tipodocumento.ESFIRMAEXTERNACONENCABEZADO AS ESFIRMAEXTERNACONENCABEZADO from qa_deo_trade.gedo_tipodocumento */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 12:28:27
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_mnt_trade
-- ------------------------------------------------------
-- Server version	5.7.17-ndb-7.5.5-cluster-gpl

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 12:28:29
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_deo_trade
-- ------------------------------------------------------
-- Server version	5.7.17-ndb-7.5.5-cluster-gpl

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 12:28:37
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_edt_trade
-- ------------------------------------------------------
-- Server version	5.7.17-ndb-7.5.5-cluster-gpl

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 12:28:42
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_numerador_trade
-- ------------------------------------------------------
-- Server version	5.7.17-ndb-7.5.5-cluster-gpl

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 12:28:45
