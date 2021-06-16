ALTER TABLE qa_deo_snrsa_g.cc_product_operation ADD ID_OPERATION bigint;
COMMENT ON COLUMN qa_deo_snrsa_g.cc_product_operation.ID_OPERATION IS 'We should give a proper name for this field that indicates the role of this 2nd relation with operation.';


CREATE TABLE qa_te_snrsa_g.transaction_esb (
  ID bigserial NOT NULL,
  DATE_CREATION timestamp DEFAULT NULL,
  TRANSACTION varchar(400) DEFAULT NULL,
  PRIMARY KEY (ID)
);
ALTER SEQUENCE qa_te_snrsa_g.transaction_esb_id_seq RESTART WITH 1 INCREMENT BY 5;
