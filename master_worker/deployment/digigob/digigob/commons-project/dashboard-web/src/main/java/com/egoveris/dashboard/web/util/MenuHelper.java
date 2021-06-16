package com.egoveris.dashboard.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;

import com.egoveris.dashboard.web.elements.MenuElement;

public class MenuHelper {
	
	private List<String> permisos;
	
	/**
	 * Arma una lista con los elementos de navegacion
	 * para el menu lateral
	 * 
	 * @return Lista con elementos de navegacion
	 */
	public static List<MenuElement> loadMenuNavegacion() {
		List<MenuElement> menuNavegacion = new ArrayList<>();
		
		if (isAnyGranted(PermisosConstants.EDT)) {
			menuNavegacion.add(getMenuDeskTop());
		}
		
		if (isAnyGranted(PermisosConstants.GRUPO_TE) || isAnyGranted(PermisosConstants.GRUPO_TAREAS)) {
			menuNavegacion.add(loadMenuExpedientes());
		}
		
		if (isAnyGranted(PermisosConstants.GRUPO_DEO)) {
			menuNavegacion.add(loadMenuDocumentos());
		}
		
		if (isAnyGranted(PermisosConstants.GRUPO_ADMINISTRACION)) {
			menuNavegacion.add(loadMenuAdministracion());
		}
		
		if (isAnyGranted(PermisosConstants.GRUPO_HERRAMIENTAS)) {
      menuNavegacion.add(loadMenuHerramientas());
    }
		
		if(isAnyGranted(PermisosConstants.CONSULTA_CONSOLIDACION)) {
			menuNavegacion.add(loadMenuConsultaConsolidacion());
		}
		
		return menuNavegacion;
	}

	private static MenuElement loadMenuConsultaConsolidacion() {
	  return new MenuElement(Labels.getLabel("ee.navegacion.consulta.consolidacion"),
	  		"z-icon-search", 
	  		DashboardConstants.getHostTE() + "/consultas/consultaConsolidacion.zul");


	}

	/**
	 * Gets the menu desk top.
	 *
	 * @return the menu desk top
	 */
	public static MenuElement getMenuDeskTop() {
		MenuElement  menuElement = new MenuElement(Labels.getLabel("ee.navegacion.escritorio"), "z-icon-desktop", null);
		
		menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.escritorio.inicio"), null, DashboardConstants.getHostEDT() + "/panelUsuario.zul"));
		return menuElement;
	}

	/**
	 * Arma la lista de navegacion para el menu de expedientes
	 * 
	 * @return Menu de expedientes
	 */
	public static MenuElement loadMenuExpedientes() {
		MenuElement  menuElement = new MenuElement(Labels.getLabel("ee.navegacion.expedientes"), "z-icon-tasks", null);
		
//		if (isAnyGranted(PermisosConstants.OPERACIONES)) {
//			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.operaciones"), null, DashboardConstants.getHostTE() + "/operaciones/misOperaciones.zul"));
//		}
		
		if (isAnyGranted(PermisosConstants.GRUPO_TAREAS)) {
			menuElement.addSubMenuElement(loadMenuTareas());
		}
		
		if (isAnyGranted(PermisosConstants.CONSULTAS)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.consultas"), null, DashboardConstants.getHostTE() +  "/consultas/consultaExpedientes.zul"));
		}
		
		if (isAnyGranted(PermisosConstants.REHABILITAR)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.rehabilitarExpedientes"), null, DashboardConstants.getHostTE() + "/expediente/admExpedientes.zul"));
		}
		
		return menuElement;
	}
	
	/**
	 * Arma la lista de navegacion para el menu de tareas
	 * 
	 * @return Menu de tareas
	 */
	public static MenuElement loadMenuTareas() {
		MenuElement menuElement = new MenuElement(Labels.getLabel("ee.navegacion.tareas"), null , null);
		
		if (isAnyGranted(PermisosConstants.MIS_TAREAS)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.tareas.misTareas"), null,  DashboardConstants.getHostTE() + "/inbox/inbox.zul"));
		}
		
		if (isAnyGranted(PermisosConstants.BUZON_GRUPAL)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.tareas.buzonGrupal"), null, DashboardConstants.getHostTE() + "/inbox/inboxGrupal.zul"));
		}
		
		if (isAnyGranted(PermisosConstants.MIS_SUPERVISADOS)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.tareas.supervisados"), null, DashboardConstants.getHostTE() +  "/supervisados/misSupervisados.zul"));
		}
		
		return menuElement;
	}
	
	/**
   * Arma la lista de navegacion para el menu de documentos
   * 
   * @return Menu de documentos
   */
  public static MenuElement loadMenuDocumentos(){
    MenuElement  menuElement = new MenuElement(Labels.getLabel("ee.navegacion.documentos"), "z-icon-clipboard", null);
    
    if (isAnyGranted(PermisosConstants.DEO_MIS_TAREAS)) {
      menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.documentos.misTareas"), null,  DashboardConstants.getHostDEO() + "/inbox.zul"));
    }
    
    if (isAnyGranted(PermisosConstants.DEO_TAREAS_SUPERVISADAS)) {
      menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.documentos.tareasSupervisadas"), null, DashboardConstants.getHostDEO() + "/misSupervisados.zul"));
    }
    
    if (isAnyGranted(PermisosConstants.DEO_CONSULTAS)) {
      menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.documentos.consultas"), null, DashboardConstants.getHostDEO() + "/consultas/consultaDocumentos.zul"));
    }
    
    if (isAnyGranted(PermisosConstants.DEO_PLANTILLAS)) {
      menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.documentos.plantillas"), null, DashboardConstants.getHostDEO() + "/inbox/plantillas.zul"));
    }
    
    return menuElement;
  }
	
	/**
	 * Arma la lista de navegacion para el menu de administracion
	 * 
	 * @return Menu de administracion
	 */
	public static MenuElement loadMenuAdministracion() {
		MenuElement menuElement = new MenuElement(Labels.getLabel("ee.navegacion.administracion"), "z-icon-cog", null);
		
		if (isAnyGranted(PermisosConstants.TRAMITES)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.tramites"), null, DashboardConstants.getHostTE() + "/inbox/abmTrata.zul"));
		}
		
		if (isAnyGranted(PermisosConstants.ADM_DOCUMENTOS)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.admDocumentos"), null, DashboardConstants.getHostDEO() + "/inbox/abmTipoDocumento.zul"));
		}
		
//		if (isAnyGranted(PermisosConstants.TIPO_OPERACIONES)) {
//			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.tipoOperaciones"), null,  DashboardConstants.getHostTE() + "/tipoOperaciones/tipoOperaciones.zul"));
//		}
		
		loadMenuEDT(menuElement);
		
		return menuElement;
	}
	
	/**
	 * Arma la lista de navegacion para el menu de herramientas
	 * 
	 * @return Menu de Herramientas
	 */
	public static MenuElement loadMenuHerramientas() {
	  MenuElement menuElement = new MenuElement(Labels.getLabel("ee.navegacion.herramientas"), "z-icon-wrench", null);
	  
	  if (isAnyGranted(PermisosConstants.FFDD)) {
      menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.herramientas.formularios"),  null, DashboardConstants.getHostFFDD() + "/administradorFC/administradorFC.zul"));
    }
	  
	  if (isAnyGranted(PermisosConstants.WD_STATEFLOW) || isAnyGranted(PermisosConstants.WD_TASKFLOW)) {
	    menuElement.addSubMenuElement(loadMenuWorkflow());
    }
	  
	  if (isAnyGranted(PermisosConstants.HERRAMIENTAS)) {
	    menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.herramientas.altaWorkflow"), null, DashboardConstants.getHostTE() + "/expediente/herramientas.zul"));
	  }
	 
	  return menuElement;	  
	}
	
	/**
	 * Arma la lista de navegacion para el menu de workflow
	 * 
	 * @return Menu de administracion
	 */
	public static MenuElement loadMenuWorkflow() {
		MenuElement menuElement = new MenuElement(Labels.getLabel("ee.navegacion.herramientas.workflow"),  null, null);
		
//		if (isAnyGranted(PermisosConstants.WD_STATEFLOW)) {
//			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.herramientas.stateflow"), null,  DashboardConstants.getHostWD() + "/stateflow.zul"));
//		}
		
		if (isAnyGranted(PermisosConstants.WD_TASKFLOW)) {
			menuElement.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.herramientas.taskflow"), null,  DashboardConstants.getHostWD() + "/taskflow.zul"));
		}
		
		return menuElement;
	}
	
	/**
	 * Arma Menu de EDT (por el momento no esta perfilado)
	 * @param menuAdministracion
	 * @return
	 */
	public static MenuElement loadMenuEDT(MenuElement menuAdministracion) {
	  // Usuarios y Perfilamiento
		
		if (isAnyGranted(PermisosConstants.EDT_USUARIO)) {
			  MenuElement usuariosPerf = new MenuElement(Labels.getLabel("ee.navegacion.administracion.usuariosPerf"), null, null);
			  
			  usuariosPerf.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.usuariosPerf.usuarios"), null, DashboardConstants.getHostEDT() + "/administrator/busquedaUsuario.zul"));
			  usuariosPerf.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.usuariosPerf.roles"), null, DashboardConstants.getHostEDT() + "/administrator/tabsRoles/admRoles.zul"));
			  usuariosPerf.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.usuariosPerf.cargos"), null, DashboardConstants.getHostEDT() + "/administrator/tabsCargos/admCargos.zul"));
			  menuAdministracion.addSubMenuElement(usuariosPerf);
		}
	 
		// Organizacion
		if (isAnyGranted(PermisosConstants.EDT_ORGANISMO)) {
			  MenuElement organizacion = new MenuElement(Labels.getLabel("ee.navegacion.administracion.org"), null, null);
			  
			  organizacion.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.org.organismos"), null, DashboardConstants.getHostEDT() + "/administrator/busquedaReparticion.zul"));
			  organizacion.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.org.sectores"), null, DashboardConstants.getHostEDT() + "/administrator/busquedaSector.zul"));
			  organizacion.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.org.estructura"), null, DashboardConstants.getHostEDT() + "/administrator/admEstructura.zul"));
			  
			  menuAdministracion.addSubMenuElement(organizacion);
		}
		
		// Mantenedores
		if (isAnyGranted(PermisosConstants.EDT_MANTENEDORES)) {
			  
			  MenuElement mantenedor = new MenuElement(Labels.getLabel("ee.navegacion.administracion.mant"), null, null);
			  mantenedor.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.mant.actuacion"), null, DashboardConstants.getHostEDT() + "/administrator/admActuacion.zul"));
			  mantenedor.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.mant.tipoResultado"), null,DashboardConstants.getHostTE() + "/tipoResultados/tipoResultados.zul"));
			  
			  menuAdministracion.addSubMenuElement(mantenedor);
		}
		
		// Feriados y Novedades
		if (isAnyGranted(PermisosConstants.EDT_FERIADOS)) {
			menuAdministracion.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.feriados"), null, DashboardConstants.getHostEDT() + "/administrator/admCalendario.zul"));
		}
		if (isAnyGranted(PermisosConstants.EDT_NOVEDADES)) {
			menuAdministracion.addSubMenuElement(new MenuElement(Labels.getLabel("ee.navegacion.administracion.novedades"), null, DashboardConstants.getHostEDT() + "/administrator/novedadesTab.zul"));
		}
	  
		return menuAdministracion;
	}

	public List<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<String> permisos) {
		this.permisos = permisos;
	}
	
	private static boolean isAnyGranted(String authoritie) {
		boolean contains = false;
		String[] authorities = authoritie.split(",");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
  		for (int i = 0; i < authorities.length; i++) {
  			for (GrantedAuthority g : auth.getAuthorities()) {
  				// Parche para sprint4
  				String role = g.getAuthority().replace("ROLE_", "");
  				
  				if (role.equalsIgnoreCase(authorities[i])) {
  					contains = true;
  					break;
  				}
  			}
  		}
		}
		
		return contains;
	}
	
}
