
package com.egoveris.ffdd.render.zk.comp.cc;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class UsuarioLoginComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 98717143129329203L;

	Cell identificadorDiv;
	
	Cell primerNombreDiv;
	Cell segundoNombreDiv;
	Cell tercerNombreDiv;
	
	Cell primerApellidoDiv;
	Cell segundoApellidoDiv;
	Cell tercerApellidoDiv;

	Cell reparticionDiv;
	Cell sectorDiv;
	Cell cargoDiv;
	
	Cell nombreSuperiorJerarquicoDiv;

	InputElement primerNombre;
	InputElement segundoNombre;
	InputElement tercerNombre;

	InputElement primerApellido;
	InputElement segundoApellido;
	InputElement tercerApellido;
	
	InputElement identificador;
	InputElement reparticion;
	InputElement sector;
	InputElement cargo;
	
	InputElement nombreSuperiorJerarquico;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioLoginComposer.class);

	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Map<String, String> mapDatos = null;
		String nombreUsuario = (String) Sessions.getCurrent().getAttribute("userName");
		
		 try {
			 Object service = SpringUtil.getBean("externalDatosUsuarioService");
			 
			 Method method = service.getClass().getMethod("datosUsuarioParaFormulario", String.class);
			 
			 mapDatos = (Map<String, String>) method.invoke(service, nombreUsuario);
			
		} catch (Exception e) {
			LOGGER.error("Error al obtener la info del usuario logueado " 
		+ nombreUsuario + " " + e.getMessage(),e);
			throw new WrongValueException("No se pudo obtener la información del usuario " + nombreUsuario);
		}
		 
		cargarFornularioUsuario(mapDatos);
		
	}

	private void cargarFornularioUsuario(Map<String, String> mapDatos) {
		if(mapDatos !=null) {			
			this.primerNombre.setText(mapDatos.get("primerNombre"));
			this.primerNombre.setReadonly(true);
			
			this.segundoNombre.setText(mapDatos.get("segundoNombre"));
			this.segundoNombre.setReadonly(true);
			
			this.tercerNombre.setText(mapDatos.get("tercerNombre"));
			this.tercerNombre.setReadonly(true);

			this.primerApellido.setText(mapDatos.get("primerApellido"));
			this.primerApellido.setReadonly(true);

			this.segundoApellido.setText(mapDatos.get("segundoApellido"));
			this.segundoApellido.setReadonly(true);

			this.tercerApellido.setText(mapDatos.get("tercerApellido"));
			this.tercerApellido.setReadonly(true);
			
			
			this.identificador.setText(mapDatos.get("identificador"));
			this.identificador.setReadonly(true);
			
			this.reparticion.setText(mapDatos.get("reparticion"));
			this.reparticion.setReadonly(true);

			this.sector.setText(mapDatos.get("sector"));
			this.sector.setReadonly(true);
			
			this.cargo.setText(mapDatos.get("cargo"));
			this.cargo.setReadonly(true);
			
			if(mapDatos.get("nombreSuperiorJerarquico")!=null) {
				this.nombreSuperiorJerarquico.setText(mapDatos.get("nombreSuperiorJerarquico"));
			}
			this.nombreSuperiorJerarquico.setReadonly(true);
		}
	}

	@Override
	protected String getName() {
		return "loginUsuario";
	}

	@Override
	protected void setDefaultValues(final String name) {
	
	}

}
