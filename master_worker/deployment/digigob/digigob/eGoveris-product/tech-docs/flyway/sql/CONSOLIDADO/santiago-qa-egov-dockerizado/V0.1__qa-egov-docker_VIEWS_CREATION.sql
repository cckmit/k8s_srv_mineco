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
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_sector_usuario AS select qa_edt_egov_docker.edt_sade_sector_usuario.ID_SECTOR_USUARIO AS ID_SECTOR_USUARIO,qa_edt_egov_docker.edt_sade_sector_usuario.ID_SECTOR_INTERNO AS ID_SECTOR_INTERNO,qa_edt_egov_docker.edt_sade_sector_usuario.NOMBRE_USUARIO AS NOMBRE_USUARIO,qa_edt_egov_docker.edt_sade_sector_usuario.PROCESO AS PROCESO,qa_edt_egov_docker.edt_sade_sector_usuario.ESTADO_REGISTRO AS ESTADO_REGISTRO,qa_edt_egov_docker.edt_sade_sector_usuario.VERSION AS VERSION,qa_edt_egov_docker.edt_sade_sector_usuario.FECHA_CREACION AS FECHA_CREACION,qa_edt_egov_docker.edt_sade_sector_usuario.USUARIO_CREACION AS USUARIO_CREACION,qa_edt_egov_docker.edt_sade_sector_usuario.FECHA_MODIFICACION AS FECHA_MODIFICACION,qa_edt_egov_docker.edt_sade_sector_usuario.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,qa_edt_egov_docker.edt_sade_sector_usuario.CARGO_ID AS CARGO_ID from qa_edt_egov_docker.edt_sade_sector_usuario */;
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

-- Dump completed on 2017-08-30 10:00:25
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:00:25
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
-- Temporary view structure for view `new_view`
--

DROP TABLE IF EXISTS new_view;
/*!50001 DROP VIEW IF EXISTS new_view*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `new_view` AS SELECT 
 1 AS NOMBRE_CIA_TRANSPORTADORA,
 1 AS RUT_REP_LEGAL_DOC_TRANSP,
 1 AS EMISOR_DOC_TRASNPORTE,
 1 AS RUT_EMISOR_DOC_TRASNPORTE,
 1 AS RUT_AGEN_CIA_TRASNP,
 1 AS RUT_CIA_TRANSPORTADORA,
 1 AS PAIS_CIA_TRANSPORTADORA*/;
SET character_set_client = @saved_cs_client;

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
-- Temporary view structure for view `view_almacenistadin`
--

DROP TABLE IF EXISTS view_almacenistadin;
/*!50001 DROP VIEW IF EXISTS view_almacenistadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_almacenistadin` AS SELECT 
 1 AS CODIGO_ALMACENISTA,
 1 AS FECHA_RECEPCION_MERCANCIAS,
 1 AS FECHA_RETIRO_MERCANCIAS,
 1 AS NUMERO_REG_RECONOCIMIENTO,
 1 AS ANIO_REG_RECONOCIMIENTO,
 1 AS CODIGO_DECLARACION_REGLA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_ausuariodin`
--

DROP TABLE IF EXISTS view_ausuariodin;
/*!50001 DROP VIEW IF EXISTS view_ausuariodin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_ausuariodin` AS SELECT 
 1 AS LOGIN_USUARIO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_bultoda`
--

DROP TABLE IF EXISTS view_bultoda;
/*!50001 DROP VIEW IF EXISTS view_bultoda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_bultoda` AS SELECT 
 1 AS SECUENCIAL_BULTO,
 1 AS TIPO_BULTO,
 1 AS CANTIDAD_BULTOS,
 1 AS IDENTIFICACION_BULTO,
 1 AS TIPO_SUB_CONTINENTE,
 1 AS TIPO_ENVASE,
 1 AS CANTIDAD_ENVASE*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_codigooperacion`
--

DROP TABLE IF EXISTS view_codigooperacion;
/*!50001 DROP VIEW IF EXISTS view_codigooperacion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_codigooperacion` AS SELECT 
 1 AS COD_OPERACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_comtransportadoradus`
--

DROP TABLE IF EXISTS view_comtransportadoradus;
/*!50001 DROP VIEW IF EXISTS view_comtransportadoradus*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_comtransportadoradus` AS SELECT 
 1 AS NOMBRE_CIA_TRANSPORTADORA,
 1 AS RUT_REP_LEGAL_DOC_TRANSP,
 1 AS EMISOR_DOC_TRASNPORTE,
 1 AS RUT_EMISOR_DOC_TRASNPORTE,
 1 AS RUT_AGEN_CIA_TRASNP,
 1 AS RUT_CIA_TRANSPORTADORA,
 1 AS PAIS_CIA_TRANSPORTADORA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_decdocingresoenvio`
--

DROP TABLE IF EXISTS view_decdocingresoenvio;
/*!50001 DROP VIEW IF EXISTS view_decdocingresoenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_decdocingresoenvio` AS SELECT 
 1 AS COD_ADUANA,
 1 AS COD_TIPO_OPER,
 1 AS TIPO_DESTINACION,
 1 AS REGION_INGRESO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_declaraciondin`
--

DROP TABLE IF EXISTS view_declaraciondin;
/*!50001 DROP VIEW IF EXISTS view_declaraciondin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_declaraciondin` AS SELECT 
 1 AS TIPO_INGRESO,
 1 AS CODIGO_ADUANA,
 1 AS CODIGO_TIPO_OPERACION,
 1 AS REGION_INGRESO,
 1 AS NUMERO_IDENTIFICACION,
 1 AS CAMPO_FORM,
 1 AS FECHA_VENCIMIENTO,
 1 AS URL_DIN,
 1 AS TIPO_DESTINACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_destinodocingenvio`
--

DROP TABLE IF EXISTS view_destinodocingenvio;
/*!50001 DROP VIEW IF EXISTS view_destinodocingenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_destinodocingenvio` AS SELECT 
 1 AS PATENTE,
 1 AS RUTA,
 1 AS TIPO_MEDIO_TRANSPORTE,
 1 AS FECHA_ESTIMADA_LLEGADA,
 1 AS NOMBRE_TRANSPORTISTA,
 1 AS TIPO_NOMBRE,
 1 AS RUT_TRANSPORTISTA,
 1 AS PASAPORTE*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_destinosasociados`
--

DROP TABLE IF EXISTS view_destinosasociados;
/*!50001 DROP VIEW IF EXISTS view_destinosasociados*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_destinosasociados` AS SELECT 
 1 AS NOMBRE_INST,
 1 AS CANT_MERCANCIAS,
 1 AS PRODUCT_CODE,
 1 AS NOMBRE,
 1 AS USO_PREVISTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_detallescuotadin`
--

DROP TABLE IF EXISTS view_detallescuotadin;
/*!50001 DROP VIEW IF EXISTS view_detallescuotadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_detallescuotadin` AS SELECT 
 1 AS CODIGO_MONTO,
 1 AS FECHA_VENCIMIENTO,
 1 AS MONTO_CUOTA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_detallesoperacionimpo`
--

DROP TABLE IF EXISTS view_detallesoperacionimpo;
/*!50001 DROP VIEW IF EXISTS view_detallesoperacionimpo*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_detallesoperacionimpo` AS SELECT 
 1 AS COD_OPERACION,
 1 AS FECHA_CREACION,
 1 AS PROCESSING_STATUS,
 1 AS TIPO_INGRESO,
 1 AS TIPO_OPERACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_detallespuertoda`
--

DROP TABLE IF EXISTS view_detallespuertoda;
/*!50001 DROP VIEW IF EXISTS view_detallespuertoda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_detallespuertoda` AS SELECT 
 1 AS COD_PUERTO,
 1 AS REGION,
 1 AS PAIS,
 1 AS FECHA,
 1 AS SECUENCIAL*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_detdocinstalacion`
--

DROP TABLE IF EXISTS view_detdocinstalacion;
/*!50001 DROP VIEW IF EXISTS view_detdocinstalacion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_detdocinstalacion` AS SELECT 
 1 AS SSPP,
 1 AS COD_DOCUMENTO,
 1 AS NOMBRE_DOCUMENTO,
 1 AS ID_OPERACION,
 1 AS NOMBRE_INST,
 1 AS REGION,
 1 AS COMUNA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_docingresoenvio`
--

DROP TABLE IF EXISTS view_docingresoenvio;
/*!50001 DROP VIEW IF EXISTS view_docingresoenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_docingresoenvio` AS SELECT 
 1 AS NUMERO_ITEM,
 1 AS CODIGO_TRATA_ARAN,
 1 AS USE_PREVISTO,
 1 AS CODIGO_PAIS_PROD,
 1 AS VALOR_CIF,
 1 AS MONTO_AJUSTE,
 1 AS SIGNO_AJUSTE,
 1 AS CANTIDAD,
 1 AS CODIGO_UNIDAD_MED,
 1 AS CANTIDAD_DE_MERCANCIA,
 1 AS PRECIO_FOB,
 1 AS CODIGO_ARANCEL,
 1 AS NUMERO_CORRELATIVO,
 1 AS CODIGO_ACUERDO_COMERCIAL,
 1 AS SUJETO_CUPO,
 1 AS PORCENTAJE_VALOREM,
 1 AS CODIGO_CUENTALOREM,
 1 AS NOMTO_CUENTALOREM,
 1 AS CARAC_ESP,
 1 AS PESO_UNITARIO,
 1 AS OTRA_DESC,
 1 AS OB_OIG,
 1 AS NOMBRE_PRODUCTOR,
 1 AS CODIGO_ARANCEL_FINAL,
 1 AS MERCADO_DESTINO,
 1 AS PESO_NETO,
 1 AS PESO_BRUTO,
 1 AS DIR_PARTICIPANTES,
 1 AS CUARENTENA_POST_FRONT,
 1 AS NOMBRE_PRODC,
 1 AS CODIGO_PRODUCTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_documento`
--

DROP TABLE IF EXISTS view_documento;
/*!50001 DROP VIEW IF EXISTS view_documento*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_documento` AS SELECT 
 1 AS EMISOR_DOCUMENTO_COMERCIAL,
 1 AS NUMERO_DOCUMENTO_COMERCIAL,
 1 AS CODIGO_PAIS_EMISOR_DOCUMENTO_COMERCIAL,
 1 AS TIPO_DOCUMENTO_COMERCIAL,
 1 AS FECHA_DOCUMENTO_COMERCIAL*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_documentoingresodin`
--

DROP TABLE IF EXISTS view_documentoingresodin;
/*!50001 DROP VIEW IF EXISTS view_documentoingresodin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_documentoingresodin` AS SELECT 
 1 AS NUMERO_DOCUMENTO,
 1 AS FECHA_RESOLUCION,
 1 AS NUMERO_ITEM*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_documentoingresoenvio`
--

DROP TABLE IF EXISTS view_documentoingresoenvio;
/*!50001 DROP VIEW IF EXISTS view_documentoingresoenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_documentoingresoenvio` AS SELECT 
 1 AS NUMERO_DOCUMENTO_INGRESO,
 1 AS TIPO_SOLICITUD,
 1 AS FECHA_SOLICITUD,
 1 AS CODI_SERVICIOSPUBLICOS_RELACIONADOS,
 1 AS ID_SOLICITUD,
 1 AS TOTAL_BULTOS,
 1 AS ID_BULTOS,
 1 AS FECHA_DOC_INGRESO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_documentoingresooig`
--

DROP TABLE IF EXISTS view_documentoingresooig;
/*!50001 DROP VIEW IF EXISTS view_documentoingresooig*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_documentoingresooig` AS SELECT 
 1 AS OIG_DOCUMENTO,
 1 AS NOMBRE_DOCUMENTO,
 1 AS BODEGA_DESTINO,
 1 AS NUMERO_DOCUMENTO,
 1 AS FECHA_DOCUMENTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_dusda`
--

DROP TABLE IF EXISTS view_dusda;
/*!50001 DROP VIEW IF EXISTS view_dusda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_dusda` AS SELECT 
 1 AS TIPO_ENVIO,
 1 AS TOTAL_ITEM,
 1 AS TOTAL_BULTOS,
 1 AS CODIGO_DESPACHO,
 1 AS INDICA_MERC_NAC,
 1 AS INDICA_MERC_CONS_ZP,
 1 AS VIA_TRANSPORTE,
 1 AS TIPO_OPERACION_ADUANA,
 1 AS CODIGO_FLETE,
 1 AS DOCUMENTO_PARCIAL,
 1 AS ESTADO_DUS,
 1 AS NUMERO_DESPACHO,
 1 AS REGION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_hojaanexadin`
--

DROP TABLE IF EXISTS view_hojaanexadin;
/*!50001 DROP VIEW IF EXISTS view_hojaanexadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_hojaanexadin` AS SELECT 
 1 AS NUMERO_SECUENCIA,
 1 AS NUMERO_INSUMO,
 1 AS NOMBRE_INSUMO,
 1 AS NUMERO_ITEM,
 1 AS NOMBRE_MERCANCIA,
 1 AS CANTIDAD,
 1 AS CODIGO_MEDIDA_ABONA,
 1 AS FACTOR_CONSUMO,
 1 AS INSUMOS_UTILIZADOS*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_hojainsumosdatpa`
--

DROP TABLE IF EXISTS view_hojainsumosdatpa;
/*!50001 DROP VIEW IF EXISTS view_hojainsumosdatpa*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_hojainsumosdatpa` AS SELECT 
 1 AS ADUANA_TRAMITACION,
 1 AS DISPATCHER_CODE,
 1 AS CONTACTO,
 1 AS DOC_PERSONA_NUM*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_insumosdatpadin`
--

DROP TABLE IF EXISTS view_insumosdatpadin;
/*!50001 DROP VIEW IF EXISTS view_insumosdatpadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_insumosdatpadin` AS SELECT 
 1 AS NUMERO_ITEM,
 1 AS NUMERO_INSUMO,
 1 AS DESCRIPCION,
 1 AS CANTIDAD,
 1 AS CODIGO_UNIDAD,
 1 AS VALOR_CIF*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_intalaciondocingenvio`
--

DROP TABLE IF EXISTS view_intalaciondocingenvio;
/*!50001 DROP VIEW IF EXISTS view_intalaciondocingenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_intalaciondocingenvio` AS SELECT 
 1 AS NOMBRE_CONTACTO_ESTABLECIMIENTO,
 1 AS NOMBRE_DIRECTOR,
 1 AS TIPO_ESTABLECIMIENTO,
 1 AS CODIGO_DESTINO,
 1 AS NOMBRE_ESTABLECIMIENTO,
 1 AS AUTORIZADO,
 1 AS RAZON_SOCIAL,
 1 AS NUMERO_RESOLUCION,
 1 AS FECHA_EMISION,
 1 AS ENTIDAD_EMISORA,
 1 AS TELEFONO_INSTALACION,
 1 AS DIRECCION,
 1 AS REGION,
 1 AS COMUNA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_itemda`
--

DROP TABLE IF EXISTS view_itemda;
/*!50001 DROP VIEW IF EXISTS view_itemda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_itemda` AS SELECT 
 1 AS PESO_BRUTO,
 1 AS COD_TRATADO_ARANCEL,
 1 AS UNIDAD_VOLUMEN,
 1 AS ID_ITEM,
 1 AS MONTO_FOB,
 1 AS VALOR_MINIMO,
 1 AS NUM_RESOLUCION,
 1 AS MONTO_UNITARIO_FOB,
 1 AS fobUSD,
 1 AS PESO_NETO_EMBARQUE,
 1 AS UNIDAD_PESO_BRUTO,
 1 AS estadoItem,
 1 AS VOLUMEN_TOTAL,
 1 AS UNIDAD_PN_EMBARQUE,
 1 AS CANT_MERCANCIAS,
 1 AS ID_PRODUCTO,
 1 AS OPERACION_ID,
 1 AS ID_OPERACION,
 1 AS NUMERO_DOCUMENTO,
 1 AS OBSERVACION_CODE*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_listadocumentoingreso`
--

DROP TABLE IF EXISTS view_listadocumentoingreso;
/*!50001 DROP VIEW IF EXISTS view_listadocumentoingreso*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_listadocumentoingreso` AS SELECT 
 1 AS ID_AUTORIZACION,
 1 AS NOMBRE_DOCUMENTO,
 1 AS ENTIDAD_EMI,
 1 AS NOMBRE_INST*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_listaproductosdi`
--

DROP TABLE IF EXISTS view_listaproductosdi;
/*!50001 DROP VIEW IF EXISTS view_listaproductosdi*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_listaproductosdi` AS SELECT 
 1 AS PRODUCT_CODE,
 1 AS DESCRIPCION_PROD,
 1 AS CANT_MERCANCIAS,
 1 AS CANTIDAD_UOM*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_lotedocingresoenvio`
--

DROP TABLE IF EXISTS view_lotedocingresoenvio;
/*!50001 DROP VIEW IF EXISTS view_lotedocingresoenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_lotedocingresoenvio` AS SELECT 
 1 AS TIPO_REINGRESO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_manifiestodin`
--

DROP TABLE IF EXISTS view_manifiestodin;
/*!50001 DROP VIEW IF EXISTS view_manifiestodin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_manifiestodin` AS SELECT 
 1 AS SECUENCIA_MANIFIESTO,
 1 AS NUMERO_MANIFIESTO,
 1 AS FECHA_MANIFIESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_mercanciada`
--

DROP TABLE IF EXISTS view_mercanciada;
/*!50001 DROP VIEW IF EXISTS view_mercanciada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_mercanciada` AS SELECT 
 1 AS ID,
 1 AS NOMBRE,
 1 AS ORDEN,
 1 AS ID_ITEM,
 1 AS ID_PRODUCTO,
 1 AS VALOR_MINIMO,
 1 AS ACUERDO_COMERCIAL,
 1 AS MONTO_UNITARIO_FOB,
 1 AS MONTO_FOB,
 1 AS PESO_BRUTO,
 1 AS UNIDAD_PESO_BRUTO,
 1 AS PESO_NETO_EMBARQUE,
 1 AS UNIDAD_PN_EMBARQUE,
 1 AS VOLUMEN_TOTAL,
 1 AS UNIDAD_VOLUMEN,
 1 AS CANT_MERCANCIAS,
 1 AS UNIDAD_CANT_MERCANCIAS,
 1 AS MONTO_AJUSTE,
 1 AS SIGNO_AJUSTE,
 1 AS SUJETO_CUPO,
 1 AS COD_TRATADO_ARANCEL,
 1 AS NUM_CORR_ARANCEL,
 1 AS VALOR_CIF,
 1 AS PORC_ADVALOREM,
 1 AS COD_CUENTA_ADVALOREM,
 1 AS MONTO_CUENTA_ADVALOREM,
 1 AS PAIS_PRODUCCION,
 1 AS ID_PRODUCTOR,
 1 AS CUARENTENA_PF,
 1 AS NUM_RESOLUCION,
 1 AS MERC_DESTINO,
 1 AS ID_BULTO,
 1 AS ID_OPERATION,
 1 AS ID_OPERACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_mercanciadin`
--

DROP TABLE IF EXISTS view_mercanciadin;
/*!50001 DROP VIEW IF EXISTS view_mercanciadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_mercanciadin` AS SELECT 
 1 AS CANTIDAD_PAQUETES,
 1 AS IDENTIFICADOR_BULTO,
 1 AS PESO_BRUTO,
 1 AS VOLUMEN_TOTAL*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_observacionda`
--

DROP TABLE IF EXISTS view_observacionda;
/*!50001 DROP VIEW IF EXISTS view_observacionda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_observacionda` AS SELECT 
 1 AS CODIGO_OBSERVACION,
 1 AS VALOR_OBSERVACION,
 1 AS GLOSA_OBSERVACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_observaciondocingresoenvio`
--

DROP TABLE IF EXISTS view_observaciondocingresoenvio;
/*!50001 DROP VIEW IF EXISTS view_observaciondocingresoenvio*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_observaciondocingresoenvio` AS SELECT 
 1 AS SECUENCIAL_OBSERVACION,
 1 AS CODIGO_OBSERVACION,
 1 AS DESCRIPCION_OBSERVACION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_operacionda`
--

DROP TABLE IF EXISTS view_operacionda;
/*!50001 DROP VIEW IF EXISTS view_operacionda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_operacionda` AS SELECT 
 1 AS ID_OPERACION,
 1 AS COMENTARIO,
 1 AS CODIGO_OFICINA_ADUANA,
 1 AS NOMBRE_AGENCIA_ADUANA,
 1 AS CODIGO_AGENCIA_ADUANAS,
 1 AS VALOR_SEGURO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_operacionrespuestadin`
--

DROP TABLE IF EXISTS view_operacionrespuestadin;
/*!50001 DROP VIEW IF EXISTS view_operacionrespuestadin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_operacionrespuestadin` AS SELECT 
 1 AS CODIGO_OPERACION,
 1 AS CREADOR,
 1 AS FECHA_CREACION,
 1 AS FECHA_ESTADO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_origendocing`
--

DROP TABLE IF EXISTS view_origendocing;
/*!50001 DROP VIEW IF EXISTS view_origendocing*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_origendocing` AS SELECT 
 1 AS VIA_TRANSPORTE,
 1 AS CODIGO_PUERTO,
 1 AS CODIGO_PAIS,
 1 AS CODIGO_DES,
 1 AS FECHA_EMBARQUE,
 1 AS FECHA_DESEMBARQUE,
 1 AS NOMBRE_NAVE,
 1 AS CODIGO_PAIS_ORIGEN,
 1 AS NOMBRE_COMPANIA,
 1 AS NUMERO_DOC_TRASPORTE,
 1 AS FECHA_DOC_TRASPORTE*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_otrdin`
--

DROP TABLE IF EXISTS view_otrdin;
/*!50001 DROP VIEW IF EXISTS view_otrdin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_otrdin` AS SELECT 
 1 AS SECUENCIA_CUENTA,
 1 AS CODIGO_CUENTA,
 1 AS MONTO_CUENTA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_otroimpuestodin`
--

DROP TABLE IF EXISTS view_otroimpuestodin;
/*!50001 DROP VIEW IF EXISTS view_otroimpuestodin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_otroimpuestodin` AS SELECT 
 1 AS SECUENCIAL_OTRO_IMPUESTO,
 1 AS PORCENTAJE,
 1 AS CODIGO_CUENTA,
 1 AS SIGNO_CUENTA,
 1 AS MONTO_IMPUESTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_participantesda`
--

DROP TABLE IF EXISTS view_participantesda;
/*!50001 DROP VIEW IF EXISTS view_participantesda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_participantesda` AS SELECT 
 1 AS NOMBRE_CONTACTO_ESTABLECIMIENTO,
 1 AS NOMBRE_DIRECTOR,
 1 AS TIPO_ESTABLECIMIENTO,
 1 AS CODIGO_DESTINO,
 1 AS NOMBRE_ESTABLECIMIENTO,
 1 AS AUTORIZADO,
 1 AS RAZON_SOCIAL,
 1 AS NUMERO_RESOLUCION,
 1 AS FECHA_EMISION,
 1 AS ENTIDAD_EMISORA,
 1 AS TELEFONO_INSTALACION,
 1 AS DIRECCION,
 1 AS REGION,
 1 AS COMUNA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_participantessecundarios`
--

DROP TABLE IF EXISTS view_participantessecundarios;
/*!50001 DROP VIEW IF EXISTS view_participantessecundarios*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_participantessecundarios` AS SELECT 
 1 AS NOMBRE_CONTACTO,
 1 AS EMAIL_CONTACTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_prodattrdocingenv`
--

DROP TABLE IF EXISTS view_prodattrdocingenv;
/*!50001 DROP VIEW IF EXISTS view_prodattrdocingenv*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_prodattrdocingenv` AS SELECT 
 1 AS SECUENCIA_ATRIBUTO,
 1 AS NOMBRE_ATRIBUTO,
 1 AS VALOR_ATRIBUTO,
 1 AS NUMERO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_productattributeda`
--

DROP TABLE IF EXISTS view_productattributeda;
/*!50001 DROP VIEW IF EXISTS view_productattributeda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_productattributeda` AS SELECT 
 1 AS SECUENCIA_ATRIBUTO,
 1 AS NOMBRE_ATRIBUTO,
 1 AS VALOR_ATRIBUTO,
 1 AS ES_FIJO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_productda`
--

DROP TABLE IF EXISTS view_productda;
/*!50001 DROP VIEW IF EXISTS view_productda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_productda` AS SELECT 
 1 AS PRODUCT_ID,
 1 AS SECUENCIA_ATRIBUTO,
 1 AS NOMBRE_ATRIBUTO,
 1 AS VALOR_ATRIBUTO,
 1 AS ES_FIJO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_regimensuspensivocda`
--

DROP TABLE IF EXISTS view_regimensuspensivocda;
/*!50001 DROP VIEW IF EXISTS view_regimensuspensivocda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_regimensuspensivocda` AS SELECT 
 1 AS TIPO_REINGRESO,
 1 AS RAZON_REINGRESO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_regimensusupensivodin`
--

DROP TABLE IF EXISTS view_regimensusupensivodin;
/*!50001 DROP VIEW IF EXISTS view_regimensusupensivodin*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_regimensusupensivodin` AS SELECT 
 1 AS DIRECCION_ALMACENAMIENTO,
 1 AS CODIGO_COMUNA,
 1 AS CODIGO_ADUANA,
 1 AS PLAZO_VIGENCIA,
 1 AS NUMERO_INSUMOS,
 1 AS TOTAL_INSUMOS,
 1 AS NUMERO_REGIMEN,
 1 AS FECHA_REGIMEN,
 1 AS ADUANA_REGIMEN,
 1 AS NUMERO_HOJAS_ANEXAS,
 1 AS CANTIDAD_SECUENCIAS,
 1 AS PLAZO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_resumen`
--

DROP TABLE IF EXISTS view_resumen;
/*!50001 DROP VIEW IF EXISTS view_resumen*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_resumen` AS SELECT 
 1 AS COD_OPERACION,
 1 AS FECHA_CREACION,
 1 AS PROCESSING_STATUS,
 1 AS CREADO_POR,
 1 AS FECHA_ACTUALIZACION,
 1 AS ACTUALIZADO_POR,
 1 AS PESO_BRUTO,
 1 AS PESO_NETO_EMBARQUE,
 1 AS VOLUMEN_TOTAL,
 1 AS TIPO_TRAMITE,
 1 AS CANTIDAD_PAQUETES*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_resumendecingreso`
--

DROP TABLE IF EXISTS view_resumendecingreso;
/*!50001 DROP VIEW IF EXISTS view_resumendecingreso*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_resumendecingreso` AS SELECT 
 1 AS ID,
 1 AS DESTINACION_ADUANERA,
 1 AS TIPO_OPERACION,
 1 AS FECHA_CREACION,
 1 AS PROCESSING_STATUS,
 1 AS MONTO_FOB*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_resumenimpo`
--

DROP TABLE IF EXISTS view_resumenimpo;
/*!50001 DROP VIEW IF EXISTS view_resumenimpo*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_resumenimpo` AS SELECT 
 1 AS COD_OPERACION,
 1 AS PROCESSING_STATUS,
 1 AS FECHA_CREACION,
 1 AS CREADO_POR,
 1 AS FECHA_ACTUALIZACION,
 1 AS TIPO_TRAMITE,
 1 AS VOLUMEN_TOTAL,
 1 AS CANTIDAD_PAQUETES*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_resumenopdus`
--

DROP TABLE IF EXISTS view_resumenopdus;
/*!50001 DROP VIEW IF EXISTS view_resumenopdus*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_resumenopdus` AS SELECT 
 1 AS COD_OPERACION,
 1 AS FECHA_CREACION,
 1 AS PROCESSING_STATUS,
 1 AS DESTINACION_ADUANERA*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_totalesdeclaracion`
--

DROP TABLE IF EXISTS view_totalesdeclaracion;
/*!50001 DROP VIEW IF EXISTS view_totalesdeclaracion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_totalesdeclaracion` AS SELECT 
 1 AS PESO_BRUTO,
 1 AS PESO_NETO,
 1 AS TOTAL_ITEM,
 1 AS TOTAL_BULTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_vistadocumentoapoyoda`
--

DROP TABLE IF EXISTS view_vistadocumentoapoyoda;
/*!50001 DROP VIEW IF EXISTS view_vistadocumentoapoyoda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_vistadocumentoapoyoda` AS SELECT 
 1 AS TIPO_DOCUMENTO,
 1 AS NOMBRE_DOCUMENTO,
 1 AS NOMBRE_EMISOR,
 1 AS FECHA_DOCUMENTO,
 1 AS DESCRIPCION,
 1 AS ADJUNTO,
 1 AS NUMERO_DOCUMENTO,
 1 AS SECUENCIA_DOCUMENTO*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_vistafinancieroda`
--

DROP TABLE IF EXISTS view_vistafinancieroda;
/*!50001 DROP VIEW IF EXISTS view_vistafinancieroda*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_vistafinancieroda` AS SELECT 
 1 AS TASA_SEGURO,
 1 AS OTROS_GASTOS,
 1 AS VALOR_LIQUIDO_RETORNO,
 1 AS INDICA_FACTURA_COMERCIAL_DEFINITIVA,
 1 AS NUMEROCUOTAS_PAGO_DIFERIDO,
 1 AS NUMERO_FACTURA,
 1 AS FECHA_EMISION,
 1 AS COMISIONES_AL_EXTERIOR,
 1 AS MONTO_TOTAL_CIF,
 1 AS TIPO_MONEDA_TRANSACCION,
 1 AS CLAUSULA_VENTA_TRANSACCION,
 1 AS MODALIDAD_VENTA_TRANSACCION,
 1 AS FORMA_PAGO_TRANSACCION,
 1 AS DESCUENTO,
 1 AS MONTO_TOTAL_FACTURA,
 1 AS VALOR_FLETE,
 1 AS CODIGO_PAIS_ADQUISICION*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_vistosbuenos`
--

DROP TABLE IF EXISTS view_vistosbuenos;
/*!50001 DROP VIEW IF EXISTS view_vistosbuenos*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_vistosbuenos` AS SELECT 
 1 AS COD_OPERACION,
 1 AS FECHA_CREACION,
 1 AS PROCESSING_STATUS,
 1 AS DESTINACION_ADUANERA,
 1 AS PAIS*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vistamercanciada`
--

DROP TABLE IF EXISTS vistamercanciada;
/*!50001 DROP VIEW IF EXISTS vistamercanciada*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vistamercanciada` AS SELECT 
 1 AS ID,
 1 AS NOMBRE,
 1 AS ORDEN,
 1 AS ID_ITEM,
 1 AS ID_PRODUCTO,
 1 AS VALOR_MINIMO,
 1 AS ACUERDO_COMERCIAL,
 1 AS MONTO_UNITARIO_FOB,
 1 AS MONTO_FOB,
 1 AS PESO_BRUTO,
 1 AS UNIDAD_PESO_BRUTO,
 1 AS PESO_NETO_EMBARQUE,
 1 AS UNIDAD_PN_EMBARQUE,
 1 AS VOLUMEN_TOTAL,
 1 AS UNIDAD_VOLUMEN,
 1 AS CANT_MERCANCIAS,
 1 AS UNIDAD_CANT_MERCANCIAS,
 1 AS MONTO_AJUSTE,
 1 AS SIGNO_AJUSTE,
 1 AS SUJETO_CUPO,
 1 AS COD_TRATADO_ARANCEL,
 1 AS NUM_CORR_ARANCEL,
 1 AS VALOR_CIF,
 1 AS PORC_ADVALOREM,
 1 AS COD_CUENTA_ADVALOREM,
 1 AS MONTO_CUENTA_ADVALOREM,
 1 AS PAIS_PRODUCCION,
 1 AS ID_PRODUCTOR,
 1 AS CUARENTENA_PF,
 1 AS NUM_RESOLUCION,
 1 AS MERC_DESTINO,
 1 AS ID_BULTO,
 1 AS ID_OPERATION,
 1 AS ID_OPERACION*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `new_view`
--

/*!50001 DROP VIEW IF EXISTS new_view*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW new_view AS select cpd.NOMBRE AS NOMBRE_CIA_TRANSPORTADORA,cpd.PERSONA_TYPE AS RUT_REP_LEGAL_DOC_TRANSP,cpd.DOC_PERSONA_NUM AS EMISOR_DOC_TRASNPORTE,cpd.DOC_PERSONA_TYPE AS RUT_EMISOR_DOC_TRASNPORTE,cpd.PERSONA_TYPE_ENUM AS RUT_AGEN_CIA_TRASNP,cpd.PARTICIPANTE_TYPE AS RUT_CIA_TRANSPORTADORA,cad.PAIS AS PAIS_CIA_TRANSPORTADORA from (cc_participantes cpd join cc_address cad) where (cpd.ID_OPERACION = cad.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

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
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW property_configuration AS select qa_edt_egov_docker.property_configuration.CLAVE AS CLAVE,qa_edt_egov_docker.property_configuration.VALOR AS VALOR,qa_edt_egov_docker.property_configuration.CONFIGURACION AS CONFIGURACION from qa_edt_egov_docker.property_configuration */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_almacenistadin`
--

/*!50001 DROP VIEW IF EXISTS view_almacenistadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_almacenistadin AS select cad.ALMACENISTA AS CODIGO_ALMACENISTA,cad.FECHA_RECEPCION_MERCANCIAS AS FECHA_RECEPCION_MERCANCIAS,cad.FECHA_RETIRO_MERCANCIAS AS FECHA_RETIRO_MERCANCIAS,cad.NUMERO_REGISTRO_RECONOCIMIENTO AS NUMERO_REG_RECONOCIMIENTO,cad.ANO_REGISTRO_RECONOCIMIENTO AS ANIO_REG_RECONOCIMIENTO,cad.CODIGO_REGLA_UNO_PROCEDIMIENTO_AFORO AS CODIGO_DECLARACION_REGLA from cc_almacenista cad */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_ausuariodin`
--

/*!50001 DROP VIEW IF EXISTS view_ausuariodin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_ausuariodin AS select ca.NOMBRE AS LOGIN_USUARIO from cc_autorizacion ca */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_bultoda`
--

/*!50001 DROP VIEW IF EXISTS view_bultoda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_bultoda AS select vbd.SECUENCIAL_BULTO AS SECUENCIAL_BULTO,vbd.TIPO_BULTO AS TIPO_BULTO,vbd.CANTIDAD_BULTOS AS CANTIDAD_BULTOS,vbd.IDENTIFICACION_BULTO AS IDENTIFICACION_BULTO,vbd.TIPO_SUB_CONTINENTE AS TIPO_SUB_CONTINENTE,vbd.TIPO_ENVASE AS TIPO_ENVASE,vbd.CANTIDAD_ENVASE AS CANTIDAD_ENVASE from cc_vista_bulto_da vbd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_codigooperacion`
--

/*!50001 DROP VIEW IF EXISTS view_codigooperacion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_codigooperacion AS select opd.COD_OPERACION AS COD_OPERACION from cc_operation opd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_comtransportadoradus`
--

/*!50001 DROP VIEW IF EXISTS view_comtransportadoradus*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_comtransportadoradus AS select cpd.NOMBRE AS NOMBRE_CIA_TRANSPORTADORA,cpd.PERSONA_TYPE AS RUT_REP_LEGAL_DOC_TRANSP,cpd.DOC_PERSONA_NUM AS EMISOR_DOC_TRASNPORTE,cpd.DOC_PERSONA_TYPE AS RUT_EMISOR_DOC_TRASNPORTE,cpd.PERSONA_TYPE_ENUM AS RUT_AGEN_CIA_TRASNP,cpd.PARTICIPANTE_TYPE AS RUT_CIA_TRANSPORTADORA,cad.PAIS AS PAIS_CIA_TRANSPORTADORA from (cc_participantes cpd join cc_address cad) where (cpd.ID_OPERACION = cad.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_decdocingresoenvio`
--

/*!50001 DROP VIEW IF EXISTS view_decdocingresoenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_decdocingresoenvio AS select cod.CODIGO_AGENCIA_ADUANAS AS COD_ADUANA,cod.COD_OPERACION AS COD_TIPO_OPER,cod.CODIGO_DESTINACION AS TIPO_DESTINACION,cad.REGION AS REGION_INGRESO from (cc_operation cod join cc_address cad) where (cod.ID_OPERACION = cad.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_declaraciondin`
--

/*!50001 DROP VIEW IF EXISTS view_declaraciondin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_declaraciondin AS select 'TipoIngreso' AS TIPO_INGRESO,cod.CODIGO_OFICINA_ADUANA AS CODIGO_ADUANA,cod.COD_OPERACION AS CODIGO_TIPO_OPERACION,cdp.REGION AS REGION_INGRESO,cdd.NUMERO_IDENTIFICACION AS NUMERO_IDENTIFICACION,cdd.TIPO_FORMULARIO AS CAMPO_FORM,cdd.FECHA_VENCIMIENTO AS FECHA_VENCIMIENTO,cdd.URL AS URL_DIN,cdd.RESPONSE_CODE AS TIPO_DESTINACION from ((cc_operation cod join cc_detallespuerto cdp) join cc_declaracion cdd) where ((cod.ID_OPERACION = cdp.ID_OPERACION) and (cod.ID_OPERACION = cdd.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_destinodocingenvio`
--

/*!50001 DROP VIEW IF EXISTS view_destinodocingenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_destinodocingenvio AS select cmt.PATENTE AS PATENTE,cmt.RUTA AS RUTA,cmt.TIPO_MEDIO_TRANSPORTE AS TIPO_MEDIO_TRANSPORTE,cmt.FECHA_ESTIMADA_LLEGADA AS FECHA_ESTIMADA_LLEGADA,cpd.PARTICIPANTE_NOMBRE AS NOMBRE_TRANSPORTISTA,cpd.PERSONA_TYPE AS TIPO_NOMBRE,cpd.PARTICIPANTE_ID AS RUT_TRANSPORTISTA,cpd.DOC_PERSONA_NUM AS PASAPORTE from (cc_medio_transporte cmt join cc_participantes cpd) where (cmt.ID_OPERACION = cpd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_destinosasociados`
--

/*!50001 DROP VIEW IF EXISTS view_destinosasociados*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_destinosasociados AS select ins.NOMBRE_INST AS NOMBRE_INST,it.CANT_MERCANCIAS AS CANT_MERCANCIAS,po.PRODUCT_CODE AS PRODUCT_CODE,po.NOMBRE AS NOMBRE,po.USO_PREVISTO AS USO_PREVISTO from ((cc_instalacion ins join cc_item it) join cc_product_operation po) where ((ins.ID_OPERACION = it.ID_OPERACION) and (it.ID_OPERACION = po.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_detallescuotadin`
--

/*!50001 DROP VIEW IF EXISTS view_detallescuotadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_detallescuotadin AS select cdc.CODIGO_UNO AS CODIGO_MONTO,cdc.FECHA AS FECHA_VENCIMIENTO,cdc.MONTO AS MONTO_CUOTA from cc_detalle_cuota cdc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_detallesoperacionimpo`
--

/*!50001 DROP VIEW IF EXISTS view_detallesoperacionimpo*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_detallesoperacionimpo AS select op.COD_OPERACION AS COD_OPERACION,op.FECHA_CREACION AS FECHA_CREACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,hd.TIPO_INGRESO AS TIPO_INGRESO,hd.TIPO_OPERACION AS TIPO_OPERACION from (cc_operation op join cc_header hd) where (op.ID_OPERACION = hd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_detallespuertoda`
--

/*!50001 DROP VIEW IF EXISTS view_detallespuertoda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_detallespuertoda AS select dtp.COD_PUERTO AS COD_PUERTO,dtp.REGION AS REGION,dtp.PAIS AS PAIS,dtp.FECHA AS FECHA,'Secuencial' AS SECUENCIAL from cc_detallespuerto dtp */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_detdocinstalacion`
--

/*!50001 DROP VIEW IF EXISTS view_detdocinstalacion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_detdocinstalacion AS select au.SSPP AS SSPP,doc.COD_DOCUMENTO AS COD_DOCUMENTO,doc.NOMBRE_DOCUMENTO AS NOMBRE_DOCUMENTO,doc.ID_OPERACION AS ID_OPERACION,ins.NOMBRE_INST AS NOMBRE_INST,adr.REGION AS REGION,adr.COMUNA AS COMUNA from (((cc_autorizacion au join cc_documento doc) join cc_instalacion ins) join cc_address adr) where ((au.ID_OPERACION = doc.ID_OPERACION) and (doc.ID_OPERACION = ins.ID_OPERACION) and (au.ID_DOCUMENTO = doc.ID_DOCUMENTO) and (ins.ID_OPERACION = adr.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_docingresoenvio`
--

/*!50001 DROP VIEW IF EXISTS view_docingresoenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_docingresoenvio AS select cid.ID_ITEM AS NUMERO_ITEM,cid.COD_TRATADO_ARANCEL AS CODIGO_TRATA_ARAN,cpo.USO_PREVISTO AS USE_PREVISTO,cid.PAIS_PRODUCCION AS CODIGO_PAIS_PROD,cid.VALOR_CIF AS VALOR_CIF,cid.MONTO_AJUSTE AS MONTO_AJUSTE,cid.SIGNO_AJUSTE AS SIGNO_AJUSTE,cid.CANT_MERCANCIAS AS CANTIDAD,cid.UNIDAD_PN_EMBARQUE AS CODIGO_UNIDAD_MED,cid.UNIDAD_CANT_MERCANCIAS AS CANTIDAD_DE_MERCANCIA,cid.MONTO_UNITARIO_FOB AS PRECIO_FOB,cid.COD_TRATADO_ARANCEL AS CODIGO_ARANCEL,cid.NUM_CORR_ARANCEL AS NUMERO_CORRELATIVO,cid.ACUERDO_COMERCIAL AS CODIGO_ACUERDO_COMERCIAL,cid.SUJETO_CUPO AS SUJETO_CUPO,cid.PORC_ADVALOREM AS PORCENTAJE_VALOREM,cid.COD_CUENTA_ADVALOREM AS CODIGO_CUENTALOREM,cid.MONTO_CUENTA_ADVALOREM AS NOMTO_CUENTALOREM,cpo.CARACTERISTICA_ESPECIAL AS CARAC_ESP,cid.PESO_NETO_EMBARQUE AS PESO_UNITARIO,cpo.DESCRIPCION_ADIC AS OTRA_DESC,cpo.DESCRIPCION_PROD AS OB_OIG,cps.NOMBRE AS NOMBRE_PRODUCTOR,cid.NUM_RESOLUCION AS CODIGO_ARANCEL_FINAL,cid.MERC_DESTINO AS MERCADO_DESTINO,cid.PESO_NETO_EMBARQUE AS PESO_NETO,cid.UNIDAD_PESO_BRUTO AS PESO_BRUTO,cpd.PARTICIPANTE_ADDRESS AS DIR_PARTICIPANTES,cid.CUARENTENA_PF AS CUARENTENA_POST_FRONT,cpo.NOMBRE AS NOMBRE_PRODC,cpo.PRODUCT_CODE AS CODIGO_PRODUCTO from (((cc_item cid join cc_product_operation cpo) join cc_participante_secundario cps) join cc_participantes cpd) where ((cid.ID_OPERACION = cpo.ID_OPERACION) and (cid.ID_OPERACION = cps.ID_OPERACION) and (cid.ID_OPERACION = cpd.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_documento`
--

/*!50001 DROP VIEW IF EXISTS view_documento*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_documento AS select cvp.NOMBRE_PARTI_SEC AS EMISOR_DOCUMENTO_COMERCIAL,cdd.COD_DOCUMENTO AS NUMERO_DOCUMENTO_COMERCIAL,cdd.PAIS_DOCUMENTO AS CODIGO_PAIS_EMISOR_DOCUMENTO_COMERCIAL,cdd.TIPO_DOCUMENTO AS TIPO_DOCUMENTO_COMERCIAL,cdd.FECHA_DOCUMENTO AS FECHA_DOCUMENTO_COMERCIAL from (cc_participante_secundario cvp join cc_documento cdd) where (cvp.ID_OPERACION = cdd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_documentoingresodin`
--

/*!50001 DROP VIEW IF EXISTS view_documentoingresodin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_documentoingresodin AS select cdd.COD_DOCUMENTO AS NUMERO_DOCUMENTO,cdd.FECHA_DOCUMENTO AS FECHA_RESOLUCION,cid.NUM_RESOLUCION AS NUMERO_ITEM from (cc_documento cdd join cc_item cid) where (cdd.ID_OPERACION = cid.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_documentoingresoenvio`
--

/*!50001 DROP VIEW IF EXISTS view_documentoingresoenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_documentoingresoenvio AS select cad.ID_DOCUMENTO AS NUMERO_DOCUMENTO_INGRESO,'tipo_solicitud' AS TIPO_SOLICITUD,cad.SUBMIT_DATE AS FECHA_SOLICITUD,cad.SUBMIT_BY AS CODI_SERVICIOSPUBLICOS_RELACIONADOS,cad.RESPONSE_CODE AS ID_SOLICITUD,cbd.CANTIDAD_PAQUETES AS TOTAL_BULTOS,cbd.ID_BULTO AS ID_BULTOS,cdd.FECHA_DOCUMENTO AS FECHA_DOC_INGRESO from ((cc_autorizacion cad join cc_bulto cbd) join cc_documento cdd) where ((cad.ID_OPERACION = cbd.ID_OPERACION) and (cad.ID_OPERACION = cdd.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_documentoingresooig`
--

/*!50001 DROP VIEW IF EXISTS view_documentoingresooig*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_documentoingresooig AS select 'oigDocumento' AS OIG_DOCUMENTO,dod.NOMBRE_DOCUMENTO AS NOMBRE_DOCUMENTO,'bodegaDestino' AS BODEGA_DESTINO,'numeroDocumento' AS NUMERO_DOCUMENTO,dod.FECHA_DOCUMENTO AS FECHA_DOCUMENTO from ((cc_autorizacion aud join cc_documento dod) join cc_instalacion ind) where ((aud.ID_OPERACION = dod.ID_OPERACION) and (aud.ID_OPERACION = ind.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_dusda`
--

/*!50001 DROP VIEW IF EXISTS view_dusda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_dusda AS select 'TIPO_ENVIO' AS TIPO_ENVIO,cid.CANT_MERCANCIAS AS TOTAL_ITEM,cbd.CANTIDAD_PAQUETES AS TOTAL_BULTOS,cpd.DISPATCHER_CODE AS CODIGO_DESPACHO,cmd.MERCANCIA_NACIONALIZADA AS INDICA_MERC_NAC,cmd.MERCANCIA_ZONA_PRIMARIA AS INDICA_MERC_CONS_ZP,cmt.TIPO_MEDIO_TRANSPORTE AS VIA_TRANSPORTE,chd.TIPO_OPERACION AS TIPO_OPERACION_ADUANA,cfd.CODIGO_FLETE AS CODIGO_FLETE,cdd.DOCUMENTO_PARCIAL AS DOCUMENTO_PARCIAL,cdd.RESPONSE_STATUS AS ESTADO_DUS,cdd.RESPONSE_CODE AS NUMERO_DESPACHO,cad.REGION AS REGION from ((((((((cc_item cid join cc_bulto cbd) join cc_participantes cpd) join cc_mercancia cmd) join cc_medio_transporte cmt) join cc_header chd) join cc_financiero cfd) join cc_declaracion cdd) join cc_address cad) where ((cid.ID_OPERACION = cbd.ID_OPERACION) and (cid.ID_OPERACION = cpd.ID_OPERACION) and (cid.ID_OPERACION = cmd.ID_OPERACION) and (cid.ID_OPERACION = cmt.ID_OPERACION) and (cid.ID_OPERACION = chd.ID_OPERACION) and (cid.ID_OPERACION = cfd.ID_OPERACION) and (cid.ID_OPERACION = cdd.ID_OPERACION) and (cid.ID_OPERACION = cad.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_hojaanexadin`
--

/*!50001 DROP VIEW IF EXISTS view_hojaanexadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_hojaanexadin AS select cha.NRO_SECUENCIA AS NUMERO_SECUENCIA,cha.NRO_INSUMO AS NUMERO_INSUMO,cha.NOMBRE_INSUMO AS NOMBRE_INSUMO,cha.NRO_ITEM AS NUMERO_ITEM,cha.NOMBRE_MERCANCIA AS NOMBRE_MERCANCIA,cha.CANTIDAD AS CANTIDAD,cha.COD_UNIDAD_MEDIDA AS CODIGO_MEDIDA_ABONA,cha.FACTOR_CONSUMO AS FACTOR_CONSUMO,cha.INSUMOS_UTILIZADOS AS INSUMOS_UTILIZADOS from cc_hoja_anexa cha */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_hojainsumosdatpa`
--

/*!50001 DROP VIEW IF EXISTS view_hojainsumosdatpa*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_hojainsumosdatpa AS select hd.ADUANA_TRAMITACION AS ADUANA_TRAMITACION,pa.DISPATCHER_CODE AS DISPATCHER_CODE,pa.CONTACTO AS CONTACTO,pa.DOC_PERSONA_NUM AS DOC_PERSONA_NUM from (cc_header hd join cc_participantes pa) where (hd.ID_OPERACION = pa.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_insumosdatpadin`
--

/*!50001 DROP VIEW IF EXISTS view_insumosdatpadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_insumosdatpadin AS select cid.NROI_ITEM AS NUMERO_ITEM,cid.NRO_HOJA AS NUMERO_INSUMO,cid.DESCRIPCION AS DESCRIPCION,cid.CANTIDAD AS CANTIDAD,cid.UNIDAD_MEDIDA_CIF AS CODIGO_UNIDAD,cid.VALOR_CIF_UNITARIO AS VALOR_CIF from cc_insumos_datpa cid */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_intalaciondocingenvio`
--

/*!50001 DROP VIEW IF EXISTS view_intalaciondocingenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_intalaciondocingenvio AS select cps.NOMBRE AS NOMBRE_CONTACTO_ESTABLECIMIENTO,cid.DIRECTOR_TECNICO AS NOMBRE_DIRECTOR,cid.TIPO_INST_DEST AS TIPO_ESTABLECIMIENTO,cid.COD_INST_DEST AS CODIGO_DESTINO,cid.NOMBRE_INST AS NOMBRE_ESTABLECIMIENTO,cid.OIG_PROPIE AS AUTORIZADO,cid.RAZON_SOCIAL AS RAZON_SOCIAL,cid.NUM_RESOL_AUT AS NUMERO_RESOLUCION,cid.FECHA_EMI_RESOL AS FECHA_EMISION,cid.ENTIDAD_EMI AS ENTIDAD_EMISORA,cid.TELEF_INST_DEST AS TELEFONO_INSTALACION,cad.DIRECCION AS DIRECCION,cad.REGION AS REGION,cad.COMUNA AS COMUNA from (((cc_participante_secundario cps join cc_participantes cpd) join cc_instalacion cid) join cc_address cad) where ((cps.ID_OPERACION = cpd.ID_OPERACION) and (cps.ID_OPERACION = cid.ID_OPERACION) and (cps.ID_OPERACION = cad.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_itemda`
--

/*!50001 DROP VIEW IF EXISTS view_itemda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_itemda AS select cid.PESO_BRUTO AS PESO_BRUTO,cid.COD_TRATADO_ARANCEL AS COD_TRATADO_ARANCEL,cid.UNIDAD_VOLUMEN AS UNIDAD_VOLUMEN,cid.ID_ITEM AS ID_ITEM,cid.MONTO_FOB AS MONTO_FOB,cid.VALOR_MINIMO AS VALOR_MINIMO,cid.NUM_RESOLUCION AS NUM_RESOLUCION,cid.MONTO_UNITARIO_FOB AS MONTO_UNITARIO_FOB,'fobUSD' AS fobUSD,cid.PESO_NETO_EMBARQUE AS PESO_NETO_EMBARQUE,cid.UNIDAD_PESO_BRUTO AS UNIDAD_PESO_BRUTO,'estadoItem' AS estadoItem,cid.VOLUMEN_TOTAL AS VOLUMEN_TOTAL,cid.UNIDAD_PN_EMBARQUE AS UNIDAD_PN_EMBARQUE,cid.CANT_MERCANCIAS AS CANT_MERCANCIAS,cid.ID_PRODUCTO AS ID_PRODUCTO,cad.OPERACION_ID AS OPERACION_ID,cld.ID_OPERACION AS ID_OPERACION,cda.NUMERO_DOCUMENTO AS NUMERO_DOCUMENTO,cod.OBSERVACION_CODE AS OBSERVACION_CODE from ((((cc_item cid join cc_autorizacion cad) join cc_lote cld) join cc_documento_apoyo cda) join cc_observacion cod) where ((cid.ID_OPERACION = cad.ID_OPERACION) and (cid.ID_OPERACION = cld.ID_OPERACION) and (cid.ID_OPERACION = cda.ID_OPERACION) and (cid.ID_OPERACION = cod.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_listadocumentoingreso`
--

/*!50001 DROP VIEW IF EXISTS view_listadocumentoingreso*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_listadocumentoingreso AS select au.ID_AUTORIZACION AS ID_AUTORIZACION,doc.NOMBRE_DOCUMENTO AS NOMBRE_DOCUMENTO,ins.ENTIDAD_EMI AS ENTIDAD_EMI,ins.NOMBRE_INST AS NOMBRE_INST from ((cc_autorizacion au join cc_documento doc) join cc_instalacion ins) where ((au.ID_OPERACION = doc.ID_OPERACION) and (doc.ID_OPERACION = ins.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_listaproductosdi`
--

/*!50001 DROP VIEW IF EXISTS view_listaproductosdi*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_listaproductosdi AS select po.PRODUCT_CODE AS PRODUCT_CODE,po.DESCRIPCION_PROD AS DESCRIPCION_PROD,it.CANT_MERCANCIAS AS CANT_MERCANCIAS,'CANTIDAD_UOM' AS CANTIDAD_UOM from (cc_product_operation po join cc_item it) where (po.ID_OPERACION = it.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_lotedocingresoenvio`
--

/*!50001 DROP VIEW IF EXISTS view_lotedocingresoenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_lotedocingresoenvio AS select cld.VALOR_LOTE AS TIPO_REINGRESO from cc_lote cld */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_manifiestodin`
--

/*!50001 DROP VIEW IF EXISTS view_manifiestodin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_manifiestodin AS select 'SecuenciaManifiesto' AS SECUENCIA_MANIFIESTO,cmd.NUMERO_MANIFIESTO AS NUMERO_MANIFIESTO,cmd.FECHA_MANIFIESTO AS FECHA_MANIFIESTO from cc_manifiesto cmd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_mercanciada`
--

/*!50001 DROP VIEW IF EXISTS view_mercanciada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_mercanciada AS select cid.ID AS ID,cid.NOMBRE AS NOMBRE,cid.ORDEN AS ORDEN,cid.ID_ITEM AS ID_ITEM,cid.ID_PRODUCTO AS ID_PRODUCTO,cid.VALOR_MINIMO AS VALOR_MINIMO,cid.ACUERDO_COMERCIAL AS ACUERDO_COMERCIAL,cid.MONTO_UNITARIO_FOB AS MONTO_UNITARIO_FOB,cid.MONTO_FOB AS MONTO_FOB,cid.PESO_BRUTO AS PESO_BRUTO,cid.UNIDAD_PESO_BRUTO AS UNIDAD_PESO_BRUTO,cid.PESO_NETO_EMBARQUE AS PESO_NETO_EMBARQUE,cid.UNIDAD_PN_EMBARQUE AS UNIDAD_PN_EMBARQUE,cid.VOLUMEN_TOTAL AS VOLUMEN_TOTAL,cid.UNIDAD_VOLUMEN AS UNIDAD_VOLUMEN,cid.CANT_MERCANCIAS AS CANT_MERCANCIAS,cid.UNIDAD_CANT_MERCANCIAS AS UNIDAD_CANT_MERCANCIAS,cid.MONTO_AJUSTE AS MONTO_AJUSTE,cid.SIGNO_AJUSTE AS SIGNO_AJUSTE,cid.SUJETO_CUPO AS SUJETO_CUPO,cid.COD_TRATADO_ARANCEL AS COD_TRATADO_ARANCEL,cid.NUM_CORR_ARANCEL AS NUM_CORR_ARANCEL,cid.VALOR_CIF AS VALOR_CIF,cid.PORC_ADVALOREM AS PORC_ADVALOREM,cid.COD_CUENTA_ADVALOREM AS COD_CUENTA_ADVALOREM,cid.MONTO_CUENTA_ADVALOREM AS MONTO_CUENTA_ADVALOREM,cid.PAIS_PRODUCCION AS PAIS_PRODUCCION,cid.ID_PRODUCTOR AS ID_PRODUCTOR,cid.CUARENTENA_PF AS CUARENTENA_PF,cid.NUM_RESOLUCION AS NUM_RESOLUCION,cid.MERC_DESTINO AS MERC_DESTINO,cid.ID_BULTO AS ID_BULTO,cid.ID_OPERATION AS ID_OPERATION,cid.ID_OPERACION AS ID_OPERACION from ((cc_product_operation cpo join cc_product_attributes cpa) join cc_item cid) where ((cpo.ID_OPERACION = cpa.PRODUCT_ID) and (cpa.PRODUCT_ID = cid.ID_PRODUCTO)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_mercanciadin`
--

/*!50001 DROP VIEW IF EXISTS view_mercanciadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_mercanciadin AS select cbd.CANTIDAD_PAQUETES AS CANTIDAD_PAQUETES,cbd.IDENTIFICADOR_BULTO AS IDENTIFICADOR_BULTO,cid.PESO_BRUTO AS PESO_BRUTO,cid.VOLUMEN_TOTAL AS VOLUMEN_TOTAL from (cc_bulto cbd join cc_item cid) where (cid.ID_OPERACION = cbd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_observacionda`
--

/*!50001 DROP VIEW IF EXISTS view_observacionda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_observacionda AS select cod.OBSERVACION_CODE AS CODIGO_OBSERVACION,cod.NOMBRE AS VALOR_OBSERVACION,cod.OBSERVACION_DESC AS GLOSA_OBSERVACION from cc_observacion cod */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_observaciondocingresoenvio`
--

/*!50001 DROP VIEW IF EXISTS view_observaciondocingresoenvio*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_observaciondocingresoenvio AS select cpao.ATTRIBUTE_SEQ AS SECUENCIAL_OBSERVACION,cpao.ATTRIBUTE_CODE_NAME AS CODIGO_OBSERVACION,cpao.PRODUCT_CODE AS DESCRIPCION_OBSERVACION from cc_product_attributes_operation cpao */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_operacionda`
--

/*!50001 DROP VIEW IF EXISTS view_operacionda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_operacionda AS select cod.ID_OPERACION AS ID_OPERACION,chd.COMENTARIO AS COMENTARIO,cod.CODIGO_OFICINA_ADUANA AS CODIGO_OFICINA_ADUANA,cod.NOMBRE_AGENCIA_ADUANA AS NOMBRE_AGENCIA_ADUANA,cod.CODIGO_AGENCIA_ADUANAS AS CODIGO_AGENCIA_ADUANAS,'VALOR_SEGURO' AS VALOR_SEGURO from ((cc_header chd join cc_operation cod) join cc_documento_transporte cdt) where (cod.ID_OPERACION = cdt.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_operacionrespuestadin`
--

/*!50001 DROP VIEW IF EXISTS view_operacionrespuestadin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_operacionrespuestadin AS select cod.COD_OPERACION AS CODIGO_OPERACION,cod.CREADO_POR AS CREADOR,cod.FECHA_CREACION AS FECHA_CREACION,cod.FECHA_ACTUALIZACION AS FECHA_ESTADO from cc_operation cod */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_origendocing`
--

/*!50001 DROP VIEW IF EXISTS view_origendocing*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_origendocing AS select cmt.TIPO_MEDIO_TRANSPORTE AS VIA_TRANSPORTE,cdd.COD_PUERTO AS CODIGO_PUERTO,cdd.PAIS AS CODIGO_PAIS,cdd.LOCACION_PUERTO AS CODIGO_DES,cdd.FECHA AS FECHA_EMBARQUE,cdd.FECHA_ESTIMADA AS FECHA_DESEMBARQUE,cmt.NOMBRE AS NOMBRE_NAVE,cpo.PAIS_ORIGEN AS CODIGO_PAIS_ORIGEN,ctd.NOMBRE AS NOMBRE_COMPANIA,cdt.NUM_DOC_TRANSPORTE AS NUMERO_DOC_TRASPORTE,cdt.FECHA_DOC_TRANSPORTE AS FECHA_DOC_TRASPORTE from (((((cc_detallespuerto cdd join cc_manifiesto cmd) join cc_medio_transporte cmt) join cc_product_operation cpo) join cc_transport ctd) join cc_documento_transporte cdt) where ((cdd.ID_OPERACION = cmd.ID_OPERACION) and (cdd.ID_OPERACION = cmt.ID_OPERACION) and (cdd.ID_OPERACION = cpo.ID_OPERACION) and (cdd.ID_OPERACION = ctd.ID_OPERACION) and (cdd.ID_OPERACION = cdt.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_otrdin`
--

/*!50001 DROP VIEW IF EXISTS view_otrdin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_otrdin AS select 'secuenciaCuenta' AS SECUENCIA_CUENTA,ccd.CODIGO_CUENTA_OTR AS CODIGO_CUENTA,ccd.MONTO_CUENTA_OTR AS MONTO_CUENTA from cc_cuenta ccd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_otroimpuestodin`
--

/*!50001 DROP VIEW IF EXISTS view_otroimpuestodin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_otroimpuestodin AS select 'SecuenciaOtroImpuesto' AS SECUENCIAL_OTRO_IMPUESTO,ccd.PORCENTAJE AS PORCENTAJE,ccd.CODIGO AS CODIGO_CUENTA,ccd.SIGNO AS SIGNO_CUENTA,ccd.MONTO AS MONTO_IMPUESTO from cc_cuenta ccd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_participantesda`
--

/*!50001 DROP VIEW IF EXISTS view_participantesda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_participantesda AS select cps.NOMBRE AS NOMBRE_CONTACTO_ESTABLECIMIENTO,cid.DIRECTOR_TECNICO AS NOMBRE_DIRECTOR,cid.TIPO_INST_DEST AS TIPO_ESTABLECIMIENTO,cid.COD_INST_DEST AS CODIGO_DESTINO,cid.NOMBRE_INST AS NOMBRE_ESTABLECIMIENTO,cid.OIG_PROPIE AS AUTORIZADO,cid.RAZON_SOCIAL AS RAZON_SOCIAL,cid.NUM_RESOL_AUT AS NUMERO_RESOLUCION,cid.FECHA_EMI_RESOL AS FECHA_EMISION,cid.ENTIDAD_EMI AS ENTIDAD_EMISORA,cid.TELEF_INST_DEST AS TELEFONO_INSTALACION,cad.DIRECCION AS DIRECCION,cad.REGION AS REGION,cad.COMUNA AS COMUNA from (((cc_participante_secundario cps join cc_participantes cpd) join cc_instalacion cid) join cc_address cad) where ((cps.ID_OPERACION = cpd.ID_OPERACION) and (cps.ID_OPERACION = cid.ID_OPERACION) and (cps.ID_OPERACION = cad.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_participantessecundarios`
--

/*!50001 DROP VIEW IF EXISTS view_participantessecundarios*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_participantessecundarios AS select cps.NOMBRE_PARTI_SEC AS NOMBRE_CONTACTO,cps.ADDRESS_PARTI_SEC AS EMAIL_CONTACTO from cc_participante_secundario cps */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_prodattrdocingenv`
--

/*!50001 DROP VIEW IF EXISTS view_prodattrdocingenv*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_prodattrdocingenv AS select cpao.ATTRIBUTE_SEQ AS SECUENCIA_ATRIBUTO,cpao.ATTRIBUTE_CODE_NAME AS NOMBRE_ATRIBUTO,cpao.ATTRIBUTE_VALUE AS VALOR_ATRIBUTO,'esFijo' AS NUMERO from cc_product_attributes_operation cpao */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_productattributeda`
--

/*!50001 DROP VIEW IF EXISTS view_productattributeda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_productattributeda AS select cpao.ATTRIBUTE_SEQ AS SECUENCIA_ATRIBUTO,cpao.ATTRIBUTE_CODE_NAME AS NOMBRE_ATRIBUTO,cpao.ATTRIBUTE_VALUE AS VALOR_ATRIBUTO,'esFIjo' AS ES_FIJO from cc_product_attributes_operation cpao */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_productda`
--

/*!50001 DROP VIEW IF EXISTS view_productda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_productda AS select cpo.PRODUCT_ID AS PRODUCT_ID,vpat.SECUENCIA_ATRIBUTO AS SECUENCIA_ATRIBUTO,vpat.NOMBRE_ATRIBUTO AS NOMBRE_ATRIBUTO,vpat.VALOR_ATRIBUTO AS VALOR_ATRIBUTO,vpat.ES_FIJO AS ES_FIJO from ((cc_product_operation cpo join cc_product_attributes cpa) join view_productattributeda vpat) where ((cpo.PRODUCT_ID = cpa.PRODUCT_ID) and (cpa.ATTRIBUTE_SEQUENCE = vpat.SECUENCIA_ATRIBUTO)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_regimensuspensivocda`
--

/*!50001 DROP VIEW IF EXISTS view_regimensuspensivocda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_regimensuspensivocda AS select cvrs.TIPO_REINGRESO AS TIPO_REINGRESO,cvrs.RAZON_REINGRESO AS RAZON_REINGRESO from cc_vista_reg_suspensivo_da cvrs */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_regimensusupensivodin`
--

/*!50001 DROP VIEW IF EXISTS view_regimensusupensivodin*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_regimensusupensivodin AS select crd.DIR_ALMACENAMIENTO AS DIRECCION_ALMACENAMIENTO,crd.COD_COMUNA_ALMACEN AS CODIGO_COMUNA,crd.COD_ADUANA_CTRL AS CODIGO_ADUANA,crd.PLAZO_VIGENCIA AS PLAZO_VIGENCIA,crd.NRO_HOJAS_INSUMO AS NUMERO_INSUMOS,crd.TOTAL_INSUMOS AS TOTAL_INSUMOS,crd.NRO_REGIMEN_SUS AS NUMERO_REGIMEN,crd.FECHA_REGIMEN_SUSP AS FECHA_REGIMEN,crd.ADUANA_REGIMEN_SUSP AS ADUANA_REGIMEN,crd.NRO_HOJAS_ANEXAS AS NUMERO_HOJAS_ANEXAS,crd.CANT_SECUENCIA AS CANTIDAD_SECUENCIAS,crd.PLAZO AS PLAZO from cc_regimen_susp crd */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_resumen`
--

/*!50001 DROP VIEW IF EXISTS view_resumen*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_resumen AS select op.COD_OPERACION AS COD_OPERACION,op.FECHA_CREACION AS FECHA_CREACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,op.CREADO_POR AS CREADO_POR,op.FECHA_ACTUALIZACION AS FECHA_ACTUALIZACION,op.ACTUALIZADO_POR AS ACTUALIZADO_POR,it.PESO_BRUTO AS PESO_BRUTO,it.PESO_NETO_EMBARQUE AS PESO_NETO_EMBARQUE,it.VOLUMEN_TOTAL AS VOLUMEN_TOTAL,hd.TIPO_TRAMITE AS TIPO_TRAMITE,bu.CANTIDAD_PAQUETES AS CANTIDAD_PAQUETES from (((cc_operation op join cc_header hd) join cc_item it) join cc_bulto bu) where ((op.ID_OPERACION = hd.ID_OPERACION) and (hd.ID_OPERACION = it.ID_OPERACION) and (it.ID_OPERACION = bu.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_resumendecingreso`
--

/*!50001 DROP VIEW IF EXISTS view_resumendecingreso*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_resumendecingreso AS select op.ID AS ID,hd.DESTINACION_ADUANERA AS DESTINACION_ADUANERA,hd.TIPO_OPERACION AS TIPO_OPERACION,op.FECHA_CREACION AS FECHA_CREACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,it.MONTO_FOB AS MONTO_FOB from ((cc_operation op join cc_header hd) join cc_item it) where ((op.ID = hd.ID_OPERACION) and (op.ID_HEADER = hd.ID_HEADER) and (hd.ID_OPERACION = it.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_resumenimpo`
--

/*!50001 DROP VIEW IF EXISTS view_resumenimpo*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_resumenimpo AS select op.COD_OPERACION AS COD_OPERACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,op.FECHA_CREACION AS FECHA_CREACION,op.CREADO_POR AS CREADO_POR,op.FECHA_ACTUALIZACION AS FECHA_ACTUALIZACION,hd.TIPO_TRAMITE AS TIPO_TRAMITE,it.VOLUMEN_TOTAL AS VOLUMEN_TOTAL,bu.CANTIDAD_PAQUETES AS CANTIDAD_PAQUETES from (((cc_operation op join cc_header hd) join cc_item it) join cc_bulto bu) where ((op.ID_OPERACION = hd.ID_OPERACION) and (hd.ID_OPERACION = it.ID_OPERACION) and (it.ID_OPERACION = bu.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_resumenopdus`
--

/*!50001 DROP VIEW IF EXISTS view_resumenopdus*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_resumenopdus AS select op.COD_OPERACION AS COD_OPERACION,op.FECHA_CREACION AS FECHA_CREACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,hd.DESTINACION_ADUANERA AS DESTINACION_ADUANERA from (cc_operation op join cc_header hd) where (op.ID_OPERACION = hd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_totalesdeclaracion`
--

/*!50001 DROP VIEW IF EXISTS view_totalesdeclaracion*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_totalesdeclaracion AS select cid.PESO_BRUTO AS PESO_BRUTO,cid.PESO_NETO_EMBARQUE AS PESO_NETO,cid.VOLUMEN_TOTAL AS TOTAL_ITEM,cbd.CANTIDAD_PAQUETES AS TOTAL_BULTO from (cc_item cid join cc_bulto cbd) where (cid.ID_OPERACION = cbd.ID_OPERACION) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_vistadocumentoapoyoda`
--

/*!50001 DROP VIEW IF EXISTS view_vistadocumentoapoyoda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_vistadocumentoapoyoda AS select dca.TIPO_DOCUMENTO AS TIPO_DOCUMENTO,dca.NOMBRE AS NOMBRE_DOCUMENTO,dca.EMISOR_DOCUMENTO AS NOMBRE_EMISOR,dca.FECHA_DOCUMENTO AS FECHA_DOCUMENTO,dca.COMENTARIOS AS DESCRIPCION,dca.ADJUNTO AS ADJUNTO,dca.NUMERO_DOCUMENTO AS NUMERO_DOCUMENTO,dca.ID_OPERACION AS SECUENCIA_DOCUMENTO from cc_documento_apoyo dca */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_vistafinancieroda`
--

/*!50001 DROP VIEW IF EXISTS view_vistafinancieroda*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_vistafinancieroda AS select fc.TASA_CAMBIO AS TASA_SEGURO,fc.OTROS_GASTOS_DEDUCIBLES AS OTROS_GASTOS,fc.VALOR_LIQUIDO_RETORNO AS VALOR_LIQUIDO_RETORNO,fc.FACTURA_COMERCIAL_DEFINITIVA AS INDICA_FACTURA_COMERCIAL_DEFINITIVA,fc.CUOTAS AS NUMEROCUOTAS_PAGO_DIFERIDO,fc.NUM_FACTURA AS NUMERO_FACTURA,fc.FEC_FACTURA AS FECHA_EMISION,fc.COMISIONES_EXTERIOR AS COMISIONES_AL_EXTERIOR,fc.VALOR_CIF AS MONTO_TOTAL_CIF,fc.MONEDA AS TIPO_MONEDA_TRANSACCION,fc.CLAUSULA_VENTA AS CLAUSULA_VENTA_TRANSACCION,fc.MODALIDAD_VENTA AS MODALIDAD_VENTA_TRANSACCION,fc.FORMA_PAGO AS FORMA_PAGO_TRANSACCION,fc.DESCUENTO AS DESCUENTO,fc.MONTO_TOTAL AS MONTO_TOTAL_FACTURA,fc.VALOR_FLETE AS VALOR_FLETE,fc.PAIS_ADQUISICION AS CODIGO_PAIS_ADQUISICION from cc_financiero fc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_vistosbuenos`
--

/*!50001 DROP VIEW IF EXISTS view_vistosbuenos*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW view_vistosbuenos AS select op.COD_OPERACION AS COD_OPERACION,op.FECHA_CREACION AS FECHA_CREACION,op.PROCESSING_STATUS AS PROCESSING_STATUS,hd.DESTINACION_ADUANERA AS DESTINACION_ADUANERA,dp.PAIS AS PAIS from ((cc_operation op join cc_header hd) join cc_detallespuerto dp) where ((op.ID_OPERACION = hd.ID_OPERACION) and (hd.ID_OPERACION = dp.ID_OPERACION)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vistamercanciada`
--

/*!50001 DROP VIEW IF EXISTS vistamercanciada*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW vistamercanciada AS select cc_item.ID AS ID,cc_item.NOMBRE AS NOMBRE,cc_item.ORDEN AS ORDEN,cc_item.ID_ITEM AS ID_ITEM,cc_item.ID_PRODUCTO AS ID_PRODUCTO,cc_item.VALOR_MINIMO AS VALOR_MINIMO,cc_item.ACUERDO_COMERCIAL AS ACUERDO_COMERCIAL,cc_item.MONTO_UNITARIO_FOB AS MONTO_UNITARIO_FOB,cc_item.MONTO_FOB AS MONTO_FOB,cc_item.PESO_BRUTO AS PESO_BRUTO,cc_item.UNIDAD_PESO_BRUTO AS UNIDAD_PESO_BRUTO,cc_item.PESO_NETO_EMBARQUE AS PESO_NETO_EMBARQUE,cc_item.UNIDAD_PN_EMBARQUE AS UNIDAD_PN_EMBARQUE,cc_item.VOLUMEN_TOTAL AS VOLUMEN_TOTAL,cc_item.UNIDAD_VOLUMEN AS UNIDAD_VOLUMEN,cc_item.CANT_MERCANCIAS AS CANT_MERCANCIAS,cc_item.UNIDAD_CANT_MERCANCIAS AS UNIDAD_CANT_MERCANCIAS,cc_item.MONTO_AJUSTE AS MONTO_AJUSTE,cc_item.SIGNO_AJUSTE AS SIGNO_AJUSTE,cc_item.SUJETO_CUPO AS SUJETO_CUPO,cc_item.COD_TRATADO_ARANCEL AS COD_TRATADO_ARANCEL,cc_item.NUM_CORR_ARANCEL AS NUM_CORR_ARANCEL,cc_item.VALOR_CIF AS VALOR_CIF,cc_item.PORC_ADVALOREM AS PORC_ADVALOREM,cc_item.COD_CUENTA_ADVALOREM AS COD_CUENTA_ADVALOREM,cc_item.MONTO_CUENTA_ADVALOREM AS MONTO_CUENTA_ADVALOREM,cc_item.PAIS_PRODUCCION AS PAIS_PRODUCCION,cc_item.ID_PRODUCTOR AS ID_PRODUCTOR,cc_item.CUARENTENA_PF AS CUARENTENA_PF,cc_item.NUM_RESOLUCION AS NUM_RESOLUCION,cc_item.MERC_DESTINO AS MERC_DESTINO,cc_item.ID_BULTO AS ID_BULTO,cc_item.ID_OPERATION AS ID_OPERATION,cc_item.ID_OPERACION AS ID_OPERACION from cc_item */;
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

-- Dump completed on 2017-08-30 10:00:27
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
-- Temporary view structure for view `sade_reparticion`
--

DROP TABLE IF EXISTS sade_reparticion;
/*!50001 DROP VIEW IF EXISTS sade_reparticion*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sade_reparticion` AS SELECT 
 1 AS id_reparticion,
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
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_documento AS select qa_deo_egov_docker.gedo_documento.ID AS ID,qa_deo_egov_docker.gedo_documento.NUMERO AS NUMERO,qa_deo_egov_docker.gedo_documento.NUMEROESPECIAL AS NUMEROESPECIAL,qa_deo_egov_docker.gedo_documento.REPARTICION AS REPARTICION,qa_deo_egov_docker.gedo_documento.ANIO AS ANIO,qa_deo_egov_docker.gedo_documento.MOTIVO AS MOTIVO,qa_deo_egov_docker.gedo_documento.USUARIOGENERADOR AS USUARIOGENERADOR,qa_deo_egov_docker.gedo_documento.FECHACREACION AS FECHACREACION,qa_deo_egov_docker.gedo_documento.WORKFLOWORIGEN AS WORKFLOWORIGEN,qa_deo_egov_docker.gedo_documento.NUMERO_SADE_PAPEL AS NUMERO_SADE_PAPEL,qa_deo_egov_docker.gedo_documento.TIPO AS TIPO,qa_deo_egov_docker.gedo_documento.SISTEMAORIGEN AS SISTEMAORIGEN,qa_deo_egov_docker.gedo_documento.SISTEMAINICIADOR AS SISTEMAINICIADOR,qa_deo_egov_docker.gedo_documento.USUARIOINICIADOR AS USUARIOINICIADOR,qa_deo_egov_docker.gedo_documento.TIPORESERVA AS TIPORESERVA,qa_deo_egov_docker.gedo_documento.VERSION AS VERSION,qa_deo_egov_docker.gedo_documento.CCOO_ID_DOC AS CCOO_ID_DOC,qa_deo_egov_docker.gedo_documento.CCOO_FECHA_CREACION AS CCOO_FECHA_CREACION,qa_deo_egov_docker.gedo_documento.APODERADO AS APODERADO,qa_deo_egov_docker.gedo_documento.REPARTICION_ACTUAL AS REPARTICION_ACTUAL,qa_deo_egov_docker.gedo_documento.FECHA_MODIFICACION AS FECHA_MODIFICACION,qa_deo_egov_docker.gedo_documento.ID_GUARDA_DOCUMENTAL AS ID_GUARDA_DOCUMENTAL,qa_deo_egov_docker.gedo_documento.PESO AS PESO,qa_deo_egov_docker.gedo_documento.MOTIVO_DEPURACION AS MOTIVO_DEPURACION,qa_deo_egov_docker.gedo_documento.FECHA_DEPURACION AS FECHA_DEPURACION from qa_deo_egov_docker.gedo_documento */;
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
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW gedo_tipodocumento AS select qa_deo_egov_docker.gedo_tipodocumento.ID AS ID,qa_deo_egov_docker.gedo_tipodocumento.NOMBRE AS NOMBRE,qa_deo_egov_docker.gedo_tipodocumento.ACRONIMO AS ACRONIMO,qa_deo_egov_docker.gedo_tipodocumento.DESCRIPCION AS DESCRIPCION,qa_deo_egov_docker.gedo_tipodocumento.ESESPECIAL AS ESESPECIAL,qa_deo_egov_docker.gedo_tipodocumento.TIENETOKEN AS TIENETOKEN,qa_deo_egov_docker.gedo_tipodocumento.TIENETEMPLATE AS TIENETEMPLATE,qa_deo_egov_docker.gedo_tipodocumento.ESTADO AS ESTADO,qa_deo_egov_docker.gedo_tipodocumento.IDTIPODOCUMENTOSADE AS IDTIPODOCUMENTOSADE,qa_deo_egov_docker.gedo_tipodocumento.CODIGOTIPODOCUMENTOSADE AS CODIGOTIPODOCUMENTOSADE,qa_deo_egov_docker.gedo_tipodocumento.ESCONFIDENCIAL AS ESCONFIDENCIAL,qa_deo_egov_docker.gedo_tipodocumento.ESFIRMAEXTERNA AS ESFIRMAEXTERNA,qa_deo_egov_docker.gedo_tipodocumento.ESMANUAL AS ESMANUAL,qa_deo_egov_docker.gedo_tipodocumento.ESAUTOMATICA AS ESAUTOMATICA,qa_deo_egov_docker.gedo_tipodocumento.ESFIRMACONJUNTA AS ESFIRMACONJUNTA,qa_deo_egov_docker.gedo_tipodocumento.FAMILIA AS FAMILIA,qa_deo_egov_docker.gedo_tipodocumento.TIENEAVISO AS TIENEAVISO,qa_deo_egov_docker.gedo_tipodocumento.PERMITE_EMBEBIDOS AS PERMITE_EMBEBIDOS,qa_deo_egov_docker.gedo_tipodocumento.TIPOPRODUCCION AS TIPOPRODUCCION,qa_deo_egov_docker.gedo_tipodocumento.ES_NOTIFICABLE AS ES_NOTIFICABLE,qa_deo_egov_docker.gedo_tipodocumento.VERSION AS VERSION,qa_deo_egov_docker.gedo_tipodocumento.TAMANO AS TAMANO,qa_deo_egov_docker.gedo_tipodocumento.ESOCULTO AS ESOCULTO,qa_deo_egov_docker.gedo_tipodocumento.ES_COMUNICABLE AS ES_COMUNICABLE,qa_deo_egov_docker.gedo_tipodocumento.USUARIO_CREADOR AS USUARIO_CREADOR,qa_deo_egov_docker.gedo_tipodocumento.FECHA_CREACION AS FECHA_CREACION,qa_deo_egov_docker.gedo_tipodocumento.ESFIRMAEXTERNACONENCABEZADO AS ESFIRMAEXTERNACONENCABEZADO from qa_deo_egov_docker.gedo_tipodocumento */;
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
/*!50013 DEFINER=temuco@`%` SQL SECURITY DEFINER */
/*!50001 VIEW sade_reparticion AS select qa_edt_egov_docker.edt_sade_reparticion.id_reparticion AS id_reparticion,qa_edt_egov_docker.edt_sade_reparticion.CODIGO_REPARTICION AS CODIGO_REPARTICION,qa_edt_egov_docker.edt_sade_reparticion.CODIGO_REPAR_INTER AS CODIGO_REPAR_INTER,qa_edt_egov_docker.edt_sade_reparticion.NOMBRE_REPARTICION AS NOMBRE_REPARTICION,qa_edt_egov_docker.edt_sade_reparticion.VIGENCIA_DESDE AS VIGENCIA_DESDE,qa_edt_egov_docker.edt_sade_reparticion.VIGENCIA_HASTA AS VIGENCIA_HASTA,qa_edt_egov_docker.edt_sade_reparticion.NUMERO AS NUMERO,qa_edt_egov_docker.edt_sade_reparticion.PISO AS PISO,qa_edt_egov_docker.edt_sade_reparticion.OFICINA AS OFICINA,qa_edt_egov_docker.edt_sade_reparticion.TELEFONO AS TELEFONO,qa_edt_egov_docker.edt_sade_reparticion.FAX AS FAX,qa_edt_egov_docker.edt_sade_reparticion.EMAIL AS EMAIL,qa_edt_egov_docker.edt_sade_reparticion.ID_ESTRUCTURA AS ID_ESTRUCTURA,qa_edt_egov_docker.edt_sade_reparticion.EN_RED AS EN_RED,qa_edt_egov_docker.edt_sade_reparticion.SECTOR_MESA AS SECTOR_MESA,qa_edt_egov_docker.edt_sade_reparticion.VERSION AS VERSION,qa_edt_egov_docker.edt_sade_reparticion.FECHA_CREACION AS FECHA_CREACION,qa_edt_egov_docker.edt_sade_reparticion.USUARIO_CREACION AS USUARIO_CREACION,qa_edt_egov_docker.edt_sade_reparticion.FECHA_MODIFICACION AS FECHA_MODIFICACION,qa_edt_egov_docker.edt_sade_reparticion.USUARIO_MODIFICACION AS USUARIO_MODIFICACION,qa_edt_egov_docker.edt_sade_reparticion.ESTADO_REGISTRO AS ESTADO_REGISTRO,qa_edt_egov_docker.edt_sade_reparticion.ES_DGTAL AS ES_DGTAL,qa_edt_egov_docker.edt_sade_reparticion.REP_PADRE AS REP_PADRE,qa_edt_egov_docker.edt_sade_reparticion.COD_DGTAL AS COD_DGTAL,qa_edt_egov_docker.edt_sade_reparticion.ID_JURISDICCION AS ID_JURISDICCION,qa_edt_egov_docker.edt_sade_reparticion.MINISTERIO AS MINISTERIO,qa_edt_egov_docker.edt_sade_reparticion.ADMINISTRADOR_PRESUPUESTO AS ADMINISTRADOR_PRESUPUESTO,qa_edt_egov_docker.edt_sade_reparticion.calle AS calle from qa_edt_egov_docker.edt_sade_reparticion */;
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

-- Dump completed on 2017-08-30 10:00:27
CREATE DATABASE  IF NOT EXISTS `qa_mnt_egov_docker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `qa_mnt_egov_docker`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 7.214.8.14    Database: qa_mnt_egov_docker
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:00:28
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-30 10:00:29
