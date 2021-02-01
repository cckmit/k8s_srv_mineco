package com.egoveris.te.base.composer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkforge.fckez.FCKeditor;
//public class org.zkforge.fckez.FCKeditor extends org.zkoss.zk.ui.AbstractComponent {
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Window;

import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;

/**
 * The Class EnvioExpedienteComposer.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioExpedienteComposer extends EEGenericForwardComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7198135574812140884L;
	@Autowired
	protected Window envioWindow;
	@Autowired
	protected Window envioAdministracionWindow;
	@Autowired
	protected FCKeditor motivoExpediente;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	protected ExpedienteElectronicoService expedienteElectronicoService;
	@Autowired
	protected String motivoExpedienteStr = "";
	protected AnnotateDataBinder bBinder;
	@Autowired
	protected Hlayout hlay;
	protected String width;
	boolean editorVisible;

	public void definirMotivo() {
		if (this.motivoExpediente != null) {
			this.motivoExpedienteStr = motivoExpediente.getValue();
		}
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.bBinder = new AnnotateDataBinder(this.hlay);
		if (this.envioWindow != null) {
			this.width = this.envioWindow.getWidth();
		} else {
			this.width = this.envioAdministracionWindow.getWidth();
		}
		
		this.editorVisible = this.motivoExpediente.isVisible();
	}


	public void onCancelar() {
		this.envioWindow.detach();
	}

	public void validarMotivo() {
		if ((this.motivoExpedienteStr == null)	|| (this.motivoExpedienteStr.trim().equals(""))) {
				throw new WrongValueException(this.motivoExpediente,"Debe ingresar un motivo.");
		}else{
			if(expedienteElectronicoService.PasarFormatoStringSinHTML(this.motivoExpedienteStr).length()> 3500){
				throw new WrongValueException(this.motivoExpediente,"El motivo ingresado es demasiado largo.");
			}
			if(this.motivoExpedienteStr.contains("<img")){
				throw new WrongValueException(this.motivoExpediente,"El motivo no puede contener imágenes.");
			}
		}

	}

}
