package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.web.utils.Utilitarios;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class InboxItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {
		Task task = (Task) data;
		if (task.getExecutionId() == null) {
			return;
		}
		// Acá no funciona Autowired ni de Spring ni de ZK entonces hay que
		// traer los beans a "manija"
		ExecutionService executionService = ((ProcessEngine) SpringUtil
					.getBean("processEngine", ProcessEngine.class))
					.getExecutionService();
		IUsuarioService usuarioService = (IUsuarioService) SpringUtil
			    	.getBean("usuarioServiceImpl");
		TipoDocumentoService tipoDocumentoService = (TipoDocumentoService) SpringUtil.getBean("tipoDocumentoServiceImpl");

		Listcell currentCell;
		new Listcell(task.getActivityName()).setParent(item);
		new Listcell(Utilitarios.fechaToString(task.getCreateTime())).setParent(item);
		Set<String> names = new HashSet<>();
		names.add("usuarioCreador");
		names.add("usuarioDerivador");
		names.add("motivo");
		names.add("tipoDocumento");
		Map<String, Object> listaVariables = executionService.getVariables(task
				.getExecutionId(), names);

		 Map<String, Usuario> mapaUsuarios = usuarioService.obtenerMapaUsuarios();
		
		String originator = (String) listaVariables.get(
				"usuarioCreador");
		String derivator = (String) listaVariables.get(
				"usuarioDerivador");
		String motivoDocumento = (String) listaVariables.get(
				"motivo");
		String idTipoDocumento = (String) listaVariables.get(
				"tipoDocumento");

		String NyAOriginator = "Desc.";
		if (StringUtils.isNotEmpty(originator)) {

			
			Usuario datosUsuarioOriginator = mapaUsuarios.get(originator);
			if (datosUsuarioOriginator != null) {
				NyAOriginator = datosUsuarioOriginator.getNombreApellido();
			}
			if (StringUtils.isNotEmpty(NyAOriginator)) {
				new Listcell(NyAOriginator).setParent(item);
			} else {
				new Listcell(originator).setParent(item);
			}
		} else {
			new Listcell("N/D").setParent(item);
		}
		String NyADerivator = "Desc.";
		if (StringUtils.isNotEmpty(derivator)) {
			Usuario datosUsuarioDerivator = mapaUsuarios.get(derivator);
			if (datosUsuarioDerivator != null) {
				NyADerivator = datosUsuarioDerivator.getNombreApellido();
			}
			if (StringUtils.isNotEmpty(NyADerivator)) {
				new Listcell(NyADerivator).setParent(item);
			} else {
				new Listcell(derivator).setParent(item);
			}
		} else {
			new Listcell("N/D").setParent(item);
		}
		Listcell motivoCell;
		motivoDocumento = motivoDocumento == null ? StringUtils.EMPTY
				: motivoDocumento;
		String motivo = Utilitarios.motivoParseado(motivoDocumento,30);
		motivoCell = new Listcell(motivo);
		motivoCell.setTooltiptext(motivoDocumento);

		motivoCell.setParent(item);
		
		TipoDocumentoDTO nuevoDoc;
			nuevoDoc = (TipoDocumentoDTO)tipoDocumentoService.buscarTipoDocumentoPorId(Integer.valueOf(idTipoDocumento));	
		
		new Listcell(nuevoDoc.getNombre()).setParent(item);
		currentCell = new Listcell();
		currentCell.setParent(item);
		Hbox hbox = new Hbox();
		Image runImage = new Image("/imagenes/play.png");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
				"onClick=inboxWindow$InboxComposer.onExecuteTask");
		runImage.setParent(hbox);
		Label runLabel = new Label(Labels.getLabel("gedo.inbox.ejecutar"));
		runLabel.setParent(hbox);
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabel,
				"onClick=inboxWindow$InboxComposer.onExecuteTask");
		hbox.setParent(currentCell);
	}
}
