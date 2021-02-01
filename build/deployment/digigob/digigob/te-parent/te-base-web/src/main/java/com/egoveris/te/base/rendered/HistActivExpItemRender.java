package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.tarjetausuario.TarjetaDatosUsuario;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class HistActivExpItemRender extends ActivExpItemRender {

	private boolean permisoParaModificar = false;

	public HistActivExpItemRender(final boolean b) {
		this.permisoParaModificar = b;
	}

	@Override
	protected void cellsFechas(final Listitem item, final ActividadInbox data1) {
		// fecha alta
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Listcell currentCell = new Listcell(sdf.format(data1.getFechaAlta()));
		currentCell.setParent(item);

		// fecha baja
		if (data1.getFechaBaja() != null) {
			currentCell = new Listcell(sdf.format(data1.getFechaBaja()));
		} else {
			currentCell = new Listcell();
		}
		currentCell.setParent(item);
	}

	@Override
	protected void cellsExtra(final Listitem item, final ActividadInbox data1) {
		Listcell currentCell;
		if (data1.getUsuarioCierre() != null) {
			currentCell = new Listcell(data1.getUsuarioCierre());
			TarjetaDatosUsuario.setPopup(currentCell, data1.getUsuarioCierre());
		} else {
			currentCell = new Listcell();
		}
		currentCell.setParent(item);
	}

	@Override
	protected boolean permisoParaModificar() {
		return permisoParaModificar;
	}

 
}
