package com.egoveris.edt.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.edt.ws.service.IExternalDatosUsuarioService;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;

@Service
public class ExternalDatosUsuarioServiceImpl implements IExternalDatosUsuarioService {

	@Autowired
	private IDatosUsuarioService datosUsuarioService;
	
	@Autowired
	private IUsuarioHelper usuarioHelper;
	
	@Autowired
	private ISectorUsuarioService sectorUsuarioService;
	
	
	@Override
	public Map<String, String> datosUsuarioParaFormulario(String usuario) {
		

		
		DatosUsuarioDTO datosUsuario = datosUsuarioService.getDatosUsuarioByUsername(usuario);
		
		
		
		if(datosUsuario!=null) {
			
			Map<String, String> mapDatosUsuario = new HashedMap<>();
			
			mapDatosUsuario.put("usuario",datosUsuario.getUsuario());
			mapDatosUsuario.put("identificador", datosUsuario.getNumeroCuit());
			mapDatosUsuario.put("primerApellido", datosUsuario.getPrimerApellido());
			mapDatosUsuario.put("segundoApellido", datosUsuario.getSegundoApellido());
			mapDatosUsuario.put("tercerApellido", datosUsuario.getTercerApellido());
			mapDatosUsuario.put("primerNombre", datosUsuario.getPrimerNombre());
			mapDatosUsuario.put("segundoNombre", datosUsuario.getSegundoNombre());
			mapDatosUsuario.put("tercerNombre", datosUsuario.getTercerNombre());
			mapDatosUsuario.put("cargo", datosUsuario.getCargoAsignado().getCargoNombre());			
			cargarDatosDeReparticionSector(usuario, mapDatosUsuario);
			cargarDatosSuperiorJerarquico(datosUsuario, mapDatosUsuario);
			
			return mapDatosUsuario;
		}
		
		
		return null;
	}


	private void cargarDatosDeReparticionSector(String usuario, Map<String, String> mapDatosUsuario) {
		SectorUsuarioDTO sectorUsuario = sectorUsuarioService.getByUsername(usuario);

		if(sectorUsuario!=null) {
			
			String nombreReparticion = sectorUsuario.getSector()
					.getReparticion().getNombre() + " (" + sectorUsuario.getSector()
					.getReparticion().getCodigo() + ") ";
			
			String nombreSector = sectorUsuario.getSector().getNombre() 
					+ " (" + sectorUsuario.getSector().getCodigo() + ") ";
			
			mapDatosUsuario.put("reparticion", nombreReparticion);
			mapDatosUsuario.put("sector", nombreSector);
			
			}
	}


	private void cargarDatosSuperiorJerarquico(DatosUsuarioDTO datosUsuario, Map<String, String> mapDatosUsuario) {
		if(datosUsuario.getUserSuperior()!=null) {				
			DatosUsuarioDTO superiorJerarquico = datosUsuarioService
					.getDatosUsuarioByUsername(datosUsuario.getUserSuperior());
			if(superiorJerarquico!=null) {
				String nombreSuperiorJerarquico = superiorJerarquico.getApellidoYNombre();
				if(superiorJerarquico.getNumeroCuit()!=null) {
					nombreSuperiorJerarquico = nombreSuperiorJerarquico 
							+ " (" + superiorJerarquico.getUsuario() + ") ";
				}
				mapDatosUsuario.put("nombreSuperiorJerarquico", nombreSuperiorJerarquico);
			}
		}
	}

	@Override
	public List<String> obtenerUsuarios() {		
		List<UsuarioReducido> lista = usuarioHelper.obtenerTodosUsuarios();
		List<String> listaRet = new ArrayList<>();
		
		if (lista == null) {
			return listaRet;
		}
		
		for (int i = 0; i < lista.size(); i++) {
			UsuarioReducido us = lista.get(i);
			String val = us.getNombreApellido() + " (" + us.getUsername() + ")";
			listaRet.add(val);
		}
		return listaRet;
	}
}
