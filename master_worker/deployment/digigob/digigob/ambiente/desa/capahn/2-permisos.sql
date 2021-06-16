USE mysql;
GRANT ALL PRIVILEGES ON *.* TO 'digigob'@'%';
FLUSH PRIVILEGES;
drop view te_digigob.gedo_documento;
drop view te_digigob.gedo_tipodocumento;
create view te_digigob.gedo_documento as select * from deo_digigob.gedo_documento;
create view te_digigob.gedo_tipodocumento as select * from deo_digigob.gedo_tipodocumento;
drop view deo_digigob.property_configuration;
create view deo_digigob.property_configuration as select * from te_digigob.property_configuration;
