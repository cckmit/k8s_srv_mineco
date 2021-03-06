CREATE DATABASE  IF NOT EXISTS `qa_edt_egov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_edt_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_edt_egov
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

-- Dump completed on 2017-08-25 10:28:26
CREATE DATABASE  IF NOT EXISTS `qa_te_egov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_te_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_te_egov
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
/*!50001 VIEW gedo_tipodocumento AS select 1 AS ID,1 AS NOMBRE,1 AS ACRONIMO,1 AS DESCRIPCION,1 AS ESESPECIAL,1 AS TIENETOKEN,1 AS TIENETEMPLATE,1 AS ESTADO,1 AS IDTIPODOCUMENTOSADE,1 AS CODIGOTIPODOCUMENTOSADE,1 AS ESCONFIDENCIAL,1 AS ESFIRMAEXTERNA,1 AS ESMANUAL,1 AS ESAUTOMATICA,1 AS ESFIRMACONJUNTA,1 AS FAMILIA,1 AS TIENEAVISO,1 AS PERMITE_EMBEBIDOS,1 AS TIPOPRODUCCION,1 AS ES_NOTIFICABLE,1 AS VERSION,1 AS TAMANO,1 AS ESOCULTO,1 AS ES_COMUNICABLE,1 AS USUARIO_CREADOR,1 AS FECHA_CREACION,1 AS ESFIRMAEXTERNACONENCABEZADO */;
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
/*!50013 DEFINER=root@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select 1 AS ID_REPARTICION,1 AS CODIGO_REPARTICION,1 AS CODIGO_REPAR_INTER,1 AS NOMBRE_REPARTICION,1 AS VIGENCIA_DESDE,1 AS VIGENCIA_HASTA,1 AS CALLE,1 AS NUMERO,1 AS PISO,1 AS OFICINA,1 AS TELEFONO,1 AS FAX,1 AS EMAIL,1 AS ID_ESTRUCTURA,1 AS EN_RED,1 AS SECTOR_MESA,1 AS VERSION,1 AS FECHA_CREACION,1 AS USUARIO_CREACION,1 AS FECHA_MODIFICACION,1 AS USUARIO_MODIFICACION,1 AS ESTADO_REGISTRO,1 AS ES_DGTAL,1 AS REP_PADRE,1 AS COD_DGTAL,1 AS ID_JURISDICCION,1 AS MINISTERIO,1 AS ADMINISTRADOR_PRESUPUESTO */;
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

-- Dump completed on 2017-08-25 10:28:27
CREATE DATABASE  IF NOT EXISTS `qa_numerador_egov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_numerador_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_numerador_egov
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

-- Dump completed on 2017-08-25 10:28:29
CREATE DATABASE  IF NOT EXISTS `qa_vuc_egov` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `qa_vuc_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_vuc_egov
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

-- Dump completed on 2017-08-25 10:28:36
CREATE DATABASE  IF NOT EXISTS `qa_deo_egov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_deo_egov`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.212.8.103    Database: qa_deo_egov
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

-- Dump completed on 2017-08-25 10:28:43
