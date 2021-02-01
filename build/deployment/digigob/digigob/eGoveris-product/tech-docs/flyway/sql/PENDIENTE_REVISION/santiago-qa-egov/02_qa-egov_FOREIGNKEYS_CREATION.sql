USE qa_te_egov;

ALTER TABLE tad_expediente_documento DROP FOREIGN KEY FK_tad_expediente_documento_tad_documento;
ALTER TABLE tad_expediente_documento ADD CONSTRAINT FK_tad_expediente_documento_tad_documento FOREIGN KEY (ID_DOCUMENTO) REFERENCES tad_documento (ID);

ALTER TABLE tad_expediente_documento DROP FOREIGN KEY FK_tad_expediente_documento_tad_expediente_base;
ALTER TABLE tad_expediente_documento ADD CONSTRAINT FK_tad_expediente_documento_tad_expediente_base FOREIGN KEY (ID_EXPEDIENTE) REFERENCES tad_expediente_base (ID);

USE qa_vuc_egov;

ALTER TABLE tad_expediente_documento DROP FOREIGN KEY FK_tad_expediente_documento_tad_documento;
ALTER TABLE tad_expediente_documento ADD CONSTRAINT FK_tad_expediente_documento_tad_documento FOREIGN KEY (ID_DOCUMENTO) REFERENCES tad_documento (ID);

ALTER TABLE tad_expediente_documento DROP FOREIGN KEY FK_tad_expediente_documento_tad_expediente_base;
ALTER TABLE tad_expediente_documento ADD CONSTRAINT FK_tad_expediente_documento_tad_expediente_base FOREIGN KEY (ID_EXPEDIENTE) REFERENCES tad_expediente_base (ID);

ALTER TABLE tad_notificacion DROP FOREIGN KEY FK_tad_notificacion_tad_expediente_base;
ALTER TABLE tad_notificacion ADD CONSTRAINT FK_tad_notificacion_tad_expediente_base FOREIGN KEY (ID_EXPEDIENTE_BASE) REFERENCES tad_expediente_base (ID);

ALTER TABLE tad_t_tramite_t_documento DROP FK_tad_t_tramite_t_documento_tad_tipo_documento;
ALTER TABLE tad_t_tramite_t_documento ADD CONSTRAINT FK_tad_t_tramite_t_documento_tad_tipo_documento FOREIGN KEY (ID_TIPO_DOCUMENTO) REFERENCES tad_tipo_documento (ID);