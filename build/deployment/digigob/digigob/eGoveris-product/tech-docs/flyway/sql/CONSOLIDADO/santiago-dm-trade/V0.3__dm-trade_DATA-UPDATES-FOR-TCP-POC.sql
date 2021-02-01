update dm_edt_trade.edt_sade_reparticion set nombre_reparticion='Tribunal Cuentas Publico', CODIGO_REPARTICION='TCP' where CODIGO_REPARTICION='REGCIVIL';
update dm_vuc_trade.tad_grupo  set nombre='Tribunal de Cuentas Publico' where nombre='Registro Civil';
update dm_vuc_trade.tad_tipo_tramite set REPARTICION_INICIADORA='TCP', NOMBRE='Solicitud Inicio Demanda' where REPARTICION_INICIADORA='REGCIVIL';
