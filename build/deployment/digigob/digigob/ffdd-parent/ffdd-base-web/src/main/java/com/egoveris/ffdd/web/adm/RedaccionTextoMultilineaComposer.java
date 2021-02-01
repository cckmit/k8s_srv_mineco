package com.egoveris.ffdd.web.adm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.exception.DynFormException;


public class RedaccionTextoMultilineaComposer  extends GenericForwardComposer{

	private static final long serialVersionUID = 1L;
	
	private Window agregarTextoMultilineaWindow;
	private Textbox texto;

	public void doAfterCompose(Component comp) throws Exception {
		try{
			super.doAfterCompose(comp);
			this.texto.setText((String)Executions.getCurrent().getArg().get("TEXTOMULTILINEA"));
		
		}catch(DynFormException e){
			throw new DynFormException(Labels.getLabel("redacionTextoMulComposer.exception.errorPrevisualizar") +
					Labels.getLabel("abmComboboxComposer.exception.intMinutos"), e);
		}
	}

	public void onCancelar(){
			this.agregarTextoMultilineaWindow.onClose();
	}
	
	public void onAceptar() throws WrongValueException, SerialException, SQLException, IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("textoMultilinea", this.texto.getText());
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self
				.getParent(), map));
		this.agregarTextoMultilineaWindow.onClose();
	}
}
