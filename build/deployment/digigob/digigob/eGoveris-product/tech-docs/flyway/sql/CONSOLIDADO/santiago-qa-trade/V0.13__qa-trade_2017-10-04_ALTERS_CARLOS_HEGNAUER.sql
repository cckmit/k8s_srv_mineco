USE qa_deo_trade;

ALTER TABLE cc_product_operation
ADD ID_OPERATION INT(11)
COMMENT 'We should give a proper name for this field that indicates the role of this 2nd relation with operation.'
AFTER USO_PREVISTO;


USE qa_te_trade;

CREATE TABLE transaction_esb (
  ID int(11) NOT NULL AUTO_INCREMENT,
  DATE_CREATION datetime DEFAULT NULL,
  TRANSACTION varchar(400) DEFAULT NULL,
  PRIMARY KEY (ID)
)AUTO_INCREMENT=5;
