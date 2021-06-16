package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class SupervisadosInboxItemRenderer implements ListitemRenderer {
  
 @Override
	public void render(Listitem item, Object data, int arg2) throws Exception {
		Task task = (Task) data;
		// Acá no funciona Autowired ni de Spring ni de ZK entonces hay que
		// traer los beans a "manija"
		ExecutionService executionService = ((ProcessEngine) SpringUtil
				.getBean("processEngine")).getExecutionService();
		IUsuarioService usuarioService = ((IUsuarioService) SpringUtil
				.getBean("usuarioServiceImpl"));
		TipoDocumentoService tipoDocumentoService = ((TipoDocumentoService) SpringUtil.getBean("tipoDocumentoServiceImpl"));
		//codigo para formatear la fecha
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String cadenaFecha = formato.format(task.getCreateTime());
		//----------------

		Set<String> names = new HashSet<>();
		names.add("usuarioCreador");
		names.add("usuarioDerivador");
		names.add("motivo");
		names.add("tipoDocumento");
		Map<String, Object> listaVariables = executionService.getVariables(task
				.getExecutionId(), names);

		Listcell currentCell;
		new Listcell("").setParent(item);	
		new Listcell(task.getActivityName()).setParent(item);
		new Listcell(cadenaFecha).setParent(item);
		String originator = (String) listaVariables.get("usuarioCreador");
		if (StringUtils.isNotEmpty(originator)) {
			if (usuarioService.obtenerUsuario(originator) != null) {
				String NyAOriginator = usuarioService.obtenerUsuario(originator)
						.getNombreApellido();
				if (StringUtils.isNotEmpty(NyAOriginator)) {
					new Listcell(NyAOriginator).setParent(item);
				} else {
					new Listcell(originator).setParent(item);
				}
			} else {
				new Listcell("N/D").setParent(item);
			}
		} else {
			new Listcell("N/D").setParent(item);
		}
		String derivator = (String) listaVariables.get("usuarioDerivador");
		if (StringUtils.isNotEmpty(derivator)) {
			if (usuarioService.obtenerUsuario(derivator) != null) {
				String NyADerivator = usuarioService.obtenerUsuario(derivator)
						.getNombreApellido();
				if (StringUtils.isNotEmpty(NyADerivator)) {
					new Listcell(NyADerivator).setParent(item);
				} else {
					new Listcell(derivator).setParent(item);
				}
			} else {
				new Listcell("N/D").setParent(item);
			}
		} else {
			new Listcell("N/D").setParent(item);
		}
		String motivoDocumento = (String) listaVariables.get("motivo");

		Listcell motivoCell;
		motivoDocumento = motivoDocumento == null ? StringUtils.EMPTY
				: motivoDocumento;
		String motivo = motivoParseado(motivoDocumento);
		motivoCell = new Listcell(motivo);
		motivoCell.setTooltiptext(motivoDocumento);
		motivoCell.setParent(item);
		
		String idTipoDocumento = (String) listaVariables.get("tipoDocumento");
		TipoDocumentoDTO objetoDocumento;
		if(null != idTipoDocumento){
			objetoDocumento = (TipoDocumentoDTO)tipoDocumentoService.buscarTipoDocumentoPorId(Integer.valueOf(idTipoDocumento));
			new Listcell(objetoDocumento.getNombre()).setParent(item);
			
				if(objetoDocumento.getEsFirmaExternaConEncabezado() && task.getActivityName().equals(Constantes.TASK_FIRMAR_DOCUMENTO)){
					item.setDisabled(true);
			}
		}
		
		currentCell = new Listcell();
		currentCell.setParent(item);
		
	}

	private String motivoParseado(String motivo) {
		int cantidadCaracteres = 10;
		String substringMotivo;
		if (motivo.length() > cantidadCaracteres) {
			substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
		} else {
			substringMotivo = motivo.substring(0, motivo.length());
		}
		return substringMotivo;
	}

}
