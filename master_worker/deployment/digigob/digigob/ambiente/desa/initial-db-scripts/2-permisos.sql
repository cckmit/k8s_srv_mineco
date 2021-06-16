USE mysql;
GRANT ALL PRIVILEGES ON *.* TO 'temuco'@'%';
drop view qa_te_trade.gedo_documento;
drop view qa_te_trade.gedo_tipodocumento;
create view qa_te_trade.gedo_documento as select * from qa_deo_trade.gedo_documento;
create view qa_te_trade.gedo_tipodocumento as select * from qa_deo_trade.gedo_tipodocumento;
drop view qa_deo_trade.property_configuration;
create view qa_deo_trade.property_configuration as select * from qa_te_trade.property_configuration;
