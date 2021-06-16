package com.egoveris.sharedsecurity.base.jdbc.rowmaper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;

public class InfoUsuarioLoginMapper implements RowMapper<InfoUsuarioLoginDTO> {

	@Override
	public InfoUsuarioLoginDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		InfoUsuarioLoginDTO usuario = new InfoUsuarioLoginDTO();
		
		usuario.setUsuario(rs.getString("USUARIO"));
		usuario.setIdentificador(rs.getString("NUMERO_CUIT"));
		usuario.setCargo(rs.getString("CARGO"));
		
		usuario.setPrimerNombre(rs.getString("PRIMER_NOMBRE"));
		usuario.setSegundoNombre(rs.getString("SEGUNDO_NOMBRE"));
		usuario.setTercerNombre(rs.getString("TERCER_NOMBRE"));
		
		usuario.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
		usuario.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
		usuario.setTercerApellido(rs.getString("TERCER_APELLIDO"));
		
		String nombreReparticion = rs.getString("NOMBRE_REPARTICION");
		String codigoReparticion = rs.getString("CODIGO_REPARTICION");
		String nombreSector = rs.getString("NOMBRE_SECTOR_INTERNO");
		String codigoSector = rs.getString("CODIGO_SECTOR_INTERNO");
		
		
		
		if(nombreReparticion !=null && codigoReparticion !=null ) {
			usuario.setReparticion(nombreReparticion + "( "+codigoReparticion  +" )");
		}
		if(nombreSector!=null && codigoSector!=null) {
			usuario.setSector(nombreSector + "( "+codigoSector+" )");
		}
		
		return usuario;
	}

}
