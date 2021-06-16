package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.base.service.ObtenerNumeracionEspecialService;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.Utilitarios;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.text.DateFormats;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaSecuenciaNumerosComposer extends
		GEDOGenericForwardComposer {

	private static final long serialVersionUID = -1569138975299328259L;
	private Datebox fechaDesde;
	private Datebox fechaHasta;
	@WireVariable("obtenerNumeracionEspecialServiceImpl")
	private ObtenerNumeracionEspecialService obtenerNumeracionEspecialService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

  }
	public void onClick$buscar() throws InterruptedException, SecurityNegocioException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Usuario us = null;
		//			us = usuarioService.obtenerUsuario(username);
		//cacheUser
		us = Utilitarios.obtenerUsuarioActual();
		List<NumerosUsadosDTO> result = obtenerNumeracionEspecialService
				.getNumerosUsados(year, us.getCodigoReparticion());
		if (result.size() == 0) {
			Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"));
		}
		byte[] excel = ExcelConverter.convert(result);
		if (excel != null) {
			String fnlm = "NumerosUsados "
					+ us.getCodigoReparticion().trim() + "_"
					+ (DateFormats.format(new Date(), true)) + ".xls";
			Filedownload.save(excel, "application/excel", fnlm);
		} else {
			Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"));
		}
		this.closeAndNotifyAssociatedWindow(null);
	}

	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}

	public Datebox getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Datebox fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Datebox getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Datebox fechaHasta) {
    this.fechaHasta = fechaHasta;
  }
}
