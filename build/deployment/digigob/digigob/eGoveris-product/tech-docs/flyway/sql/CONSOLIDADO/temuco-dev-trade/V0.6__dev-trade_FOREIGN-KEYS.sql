USE dev_mnt_trade;

ALTER TABLE trd_acuerdo_comercial ADD CONSTRAINT FK_ACRDCOM_TPOACCOM FOREIGN KEY (ID_TIPO_ACUERDO_COM) 
REFERENCES trd_tipo_acuerdo_comercial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_documento_aprobacion ADD CONSTRAINT FK_DOCAPR_SSPP FOREIGN KEY (ID_SSPP) 
REFERENCES trd_sspp (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_hscode ADD CONSTRAINT FK_HSDCODE_CAPITULO FOREIGN KEY (ID_CAPITULO) REFERENCES trd_capitulo (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_hscode ADD CONSTRAINT FK_HSDCODE_PARTIDA FOREIGN KEY (ID_PARTIDA) REFERENCES trd_partida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_hscode ADD CONSTRAINT FK_HSDCODE_SUBPRTD FOREIGN KEY (ID_SUBPARTIDA) REFERENCES trd_subpartida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_hscode ADD CONSTRAINT FK_HSDCODE_SUBPRTDNC FOREIGN KEY (ID_SUBPARTIDA_NC) REFERENCES trd_subpartida_nc (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_hscode_acuerdo ADD CONSTRAINT FK_HSCACDR_HSCODE FOREIGN KEY (ID_HSCODDE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_hscode_acuerdo ADD CONSTRAINT FK_HSCACRD_ACRDCOM FOREIGN KEY (ID_ACUERDO_COMERCIAL) REFERENCES trd_acuerdo_comercial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_hscode_matriz ADD CONSTRAINT FK_HSCDMTZ_HSCODE FOREIGN KEY (ID_HSCODE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_hscode_matriz ADD CONSTRAINT FK_HSCDMTZ_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_matriz_documento ADD CONSTRAINT FK_MTZDOC_DOCAPR FOREIGN KEY (ID_DOC_APROBACION) REFERENCES trd_documento_aprobacion (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_matriz_documento ADD CONSTRAINT FK_MTZDOC_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_matriz_producto ADD CONSTRAINT FK_MTZPROD_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_matriz_producto ADD CONSTRAINT FK_MTZPROD_PROD FOREIGN KEY (ID_PRODUCTO) REFERENCES trd_producto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_matriz_vb ADD CONSTRAINT FK_MTZVB_CARESPEC FOREIGN KEY (ID_CAR_ESPECIAL) REFERENCES trd_caracteristica_especial (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_matriz_vb ADD CONSTRAINT FK_MTZVB_USOPREV FOREIGN KEY (ID_USO_PREVISTO) REFERENCES trd_uso_previsto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_pais_matriz ADD CONSTRAINT FK_MTZPAIS_MTZVB FOREIGN KEY (ID_MATRIZ_VB) REFERENCES trd_matriz_vb (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE trd_pais_matriz ADD CONSTRAINT FK_MTZPAIS_PAIS FOREIGN KEY (ID_PAIS) REFERENCES trd_pais (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_partida ADD CONSTRAINT FK_PARTIDA_CAPITULO FOREIGN KEY (ID_CAPITULO) REFERENCES trd_capitulo (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_producto ADD CONSTRAINT FK_PRODUCTO_HSCODE FOREIGN KEY (ID_HSCODE) REFERENCES trd_hscode (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_producto_atributo ADD CONSTRAINT FK_ATRPROD_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES trd_producto (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_subpartida ADD CONSTRAINT FK_SUBPRTD_PARTIDA FOREIGN KEY (ID_PARTIDA) REFERENCES trd_partida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE trd_subpartida_nc ADD CONSTRAINT FK_SUBPRTDNC_SUBPRTD FOREIGN KEY (ID_SUBPARTIDA) REFERENCES trd_subpartida (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;


USE dev_vuc_trade;

ALTER TABLE tad_t_tramite_t_documento ADD CONSTRAINT FK_tad_t_tramite_t_documento_tad_tipo_documento FOREIGN KEY (ID_TIPO_DOCUMENTO) REFERENCES tad_tipo_documento (ID);