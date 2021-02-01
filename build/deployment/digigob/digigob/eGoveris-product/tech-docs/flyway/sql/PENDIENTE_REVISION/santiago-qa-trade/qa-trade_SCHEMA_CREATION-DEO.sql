-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: qa_deo_trade
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
-- Table structure for table `cc_hoja_insumo_datpa`
--

DROP TABLE IF EXISTS cc_hoja_insumo_datpa;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_hoja_insumo_datpa (
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
  ESTADO_PRODUCTO varchar(20) DEFAULT NULL,
  NOMBRE_CIENTIFICO varchar(20) DEFAULT NULL,
  NOMBRE_COMUN_PRODUCTO varchar(20) DEFAULT NULL,
  NOMBRE_NEGOCIO_PRODUCTO_INGLES varchar(20) DEFAULT NULL,
  COMMON_NAME varchar(20) DEFAULT NULL,
  DESTINACION_ADUANERA varchar(20) DEFAULT NULL,
  NOMBRE_NEGOCIO_PRODUCTO varchar(20) DEFAULT NULL,
  DESC_PRODUCTO_INGLES varchar(20) DEFAULT NULL,
  PRODUCT_VERSION varchar(20) DEFAULT NULL,
  DESC_PRODUCTO varchar(20) DEFAULT NULL,
  ID_CANASTA_PRODUCTO varchar(20) DEFAULT NULL,
  NOMBRE_COMUN_PRODUCTO_INGLES varchar(20) DEFAULT NULL,
  PRODUCT_CODE varchar(20) DEFAULT NULL,
  ARANCELARIO_COD varchar(20) DEFAULT NULL,
  PROD_CODE_SSPP varchar(20) DEFAULT NULL,
  PRODUCTO_OPERACION_ID int(11) DEFAULT NULL,
  PRODUCT_ID int(11) DEFAULT NULL,
  SSPP_ENUM varchar(20) DEFAULT NULL,
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
-- Table structure for table `cc_viewvistosbuenos`
--

DROP TABLE IF EXISTS cc_viewvistosbuenos;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_viewvistosbuenos (
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
-- Table structure for table `cc_vista_declaracion_din`
--

DROP TABLE IF EXISTS cc_vista_declaracion_din;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cc_vista_declaracion_din (
  ID int(11) NOT NULL,
  ID_OPERACION int(11) NOT NULL,
  NOMBRE varchar(20) DEFAULT NULL,
  ORDEN int(11) NOT NULL,
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
  id int(19) NOT NULL,
  KEY_AT varchar(255) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  PRIMARY KEY (id)
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
  id int(19) NOT NULL,
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
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_component_multivalue`
--

DROP TABLE IF EXISTS df_component_multivalue;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_component_multivalue (
  id int(19) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  `VALUE` varchar(300) NOT NULL,
  DESCRIPTION varchar(300) NOT NULL,
  VALUE_ORDER decimal(19,0) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  TYPE_ORDER double DEFAULT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_component_type`
--

DROP TABLE IF EXISTS df_component_type;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_component_type (
  id int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  FACTORY varchar(64) DEFAULT NULL,
  PRIMARY KEY (id)
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
  id int(19) NOT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
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
  id int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  CREATION_DATE datetime DEFAULT NULL,
  CREATOR_USER varchar(255) DEFAULT NULL,
  MODIFICATION_DATE datetime DEFAULT NULL,
  MODIFYING_USER varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_form_comp_value`
--

DROP TABLE IF EXISTS df_form_comp_value;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_form_comp_value (
  id int(19) NOT NULL,
  ID_TRANSACTION decimal(19,0) NOT NULL,
  ID_FORM_COMPONENT decimal(19,0) NOT NULL,
  VALUE_STR varchar(255) DEFAULT NULL,
  VALUE_INT decimal(19,0) DEFAULT NULL,
  VALUE_DATE datetime DEFAULT NULL,
  VALUE_DOUBLE double DEFAULT NULL,
  VALUE_BOOLEAN int(11) DEFAULT NULL,
  INPUT_NAME varchar(255) DEFAULT NULL,
  VALUE_ARCHIVO varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_form_component`
--

DROP TABLE IF EXISTS df_form_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_form_component (
  id int(19) NOT NULL,
  ID_FORM decimal(19,0) NOT NULL,
  ID_COMPONENT decimal(19,0) NOT NULL,
  COMP_ORDER int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  LABEL varchar(400) DEFAULT NULL,
  OBLIGATORY int(11) DEFAULT NULL,
  SEARCH_RELEVANCY int(11) DEFAULT NULL,
  HIDDEN int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_group`
--

DROP TABLE IF EXISTS df_group;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_group (
  id int(19) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  DESCRIPTION varchar(255) NOT NULL,
  CREATION_DATE datetime NOT NULL,
  CREATOR_USER varchar(255) NOT NULL,
  MODIFYING_USER varchar(255) NOT NULL,
  MODIFICATION_DATE datetime NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_group_component`
--

DROP TABLE IF EXISTS df_group_component;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_group_component (
  id int(19) NOT NULL,
  ID_GROUP double NOT NULL,
  ID_COMPONENT double NOT NULL,
  COMP_ORDER double NOT NULL,
  `NAME` varchar(255) NOT NULL,
  LABEL varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `df_transaction`
--

DROP TABLE IF EXISTS df_transaction;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE df_transaction (
  id int(19) NOT NULL,
  CREATION_DATE datetime NOT NULL,
  FORM_NAME varchar(255) DEFAULT NULL,
  SYS_SOURCE varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-28 12:48:34
