package com.egoveris.sharedsecurity.base.jdbc.repository.impl;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;
import com.egoveris.sharedsecurity.base.jdbc.repository.InfoUsuarioLoginRepository;
import com.egoveris.sharedsecurity.base.jdbc.rowmaper.InfoUsuarioLoginMapper;

@Repository
public class InfoUsuarioLoginRepositoryImpl implements InfoUsuarioLoginRepository {

	JdbcTemplate jdbcTemplate;
	
	private static final String QUERY_USUARIO = 
			"select edu.USUARIO, edu.NUMERO_CUIT, esr.CODIGO_REPARTICION, esr.NOMBRE_REPARTICION, essi.CODIGO_SECTOR_INTERNO, " + 
			"essi.NOMBRE_SECTOR_INTERNO, edu.PRIMER_NOMBRE, edu.SEGUNDO_NOMBRE, edu.TERCER_NOMBRE, " + 
			"edu.PRIMER_APELLIDO, edu.SEGUNDO_APELLIDO, edu.TERCER_APELLIDO, ec.CARGO " + 
			"from qa_edt_trade.edt_datos_usuario edu " + 
			"left join qa_edt_trade.edt_sade_sector_usuario essu  on edu.USUARIO = essu.NOMBRE_USUARIO " + 
			"left join qa_edt_trade.edt_sade_sector_interno essi  on essi.ID_SECTOR_INTERNO = essu.ID_SECTOR_INTERNO " + 
			"left join qa_edt_trade.edt_sade_reparticion esr  on esr.ID_REPARTICION = essi.CODIGO_REPARTICION " + 
			"left join qa_edt_trade.edt_cargos ec on ec.ID = edu.CARGO " + 
			"where edu.USUARIO = ?";
	
	@Autowired
	public InfoUsuarioLoginRepositoryImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public InfoUsuarioLoginDTO getDatosUsuarioByNombre(String usuario) {
		
		List<InfoUsuarioLoginDTO> list = jdbcTemplate.query(QUERY_USUARIO, 
				new Object[] {usuario.toUpperCase()}, new InfoUsuarioLoginMapper());
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
