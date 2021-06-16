package com.egoveris.deo.web.satra.consulta;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

@SuppressWarnings({ "deprecation", "unchecked" })
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaPorNumeroSADEComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = -1569138975299328259L;
	private TipoDocumentoDTO selectedTipoDocumentoSADE;
	private Intbox numeroSADE;
	private Bandbox reparticionBusquedaSADE;
	private Intbox anioSADE;
	private AnnotateDataBinder binder;
	private Window consultaPorNumeroSADEWindow;
	private Bandbox familiaEstructuraTree;
	@WireVariable("ecosistemaServiceImpl")
	private IEcosistemaService ecosistemaService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		familiaEstructuraTree.addEventListener(Events.ON_NOTIFY, new ConsultaPorNumeroSADEComposerListener(this));
		if (this.ecosistemaService.obtenerEcosistema().trim().isEmpty()) {
			this.consultaPorNumeroSADEWindow
					.setTitle(Labels.getLabel("gedo.consultaDocumentos.busquedaPorSade", new String[] { "SADE" }));
		} else {
			this.consultaPorNumeroSADEWindow.setTitle(Labels.getLabel("gedo.consultaDocumentos.busquedaPorSade",
					new String[] { this.ecosistemaService.obtenerEcosistema() }));
		}

	}

	public void onClick$buscar() throws InterruptedException {
		this.checkConstraints();
		ConsultaSolrRequest consulta = new ConsultaSolrRequest();
		if (!this.selectedTipoDocumentoSADE.getEsOculto()) {
			String nroSade = armarNumeracion(this.selectedTipoDocumentoSADE.getCodigoTipoDocumentoSade(),
					this.anioSADE.getValue(), this.numeroSADE.getValue(), this.reparticionBusquedaSADE.getValue());
			consulta.setNroSade(nroSade);
			consulta.setTipoDocAcr(this.selectedTipoDocumentoSADE.getAcronimo());
		}
		this.closeAndNotifyAssociatedWindow(consulta);
	}

	private String armarNumeracion(String codigoTipoDocumentoSade, Integer anio, Integer numero, String reparticion) {
		if ((codigoTipoDocumentoSade == null) || (numero == null) || (anio == null)
				|| ((StringUtils.isEmpty(reparticion)) || (reparticion == null))) {
			throw new IllegalArgumentException(Labels.getLabel("gedo.consSadeComposer.exception.camposCompletados"));
		}

		return armarNumeracionEstandar(codigoTipoDocumentoSade, anio.toString(), numero.toString(), reparticion);
	}

	// TODO modificar si es multi ecosistema
	private String armarNumeracionEstandar(String codigoActuacionSade, String anio, String numero,
			String codigoReparticion) {
		String numeroFormateado = formatearNumero(numero);
		String codigoEcosistema = ecosistemaService.obtenerEcosistema();
		if (codigoEcosistema.trim().isEmpty()) {
			return codigoActuacionSade + "-" + anio + "-" + numeroFormateado + "-   -" + codigoReparticion.trim();
		} else {
			return codigoActuacionSade + "-" + anio + "-" + numeroFormateado + "-" + codigoEcosistema + "-"
					+ codigoReparticion.trim();
		}

	}

	private String formatearNumero(String numero) {
		DecimalFormat format = new DecimalFormat("00000000");
		Integer numeroAformatear = Integer.valueOf(numero);
		String numeroFormateado = format.format(numeroAformatear);

		return numeroFormateado;
	}

	protected void checkConstraints() {

		if (this.selectedTipoDocumentoSADE == null) {
			throw new WrongValueException(this.familiaEstructuraTree,
					Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
		}
		if (this.anioSADE.getValue() != null) {
			if (this.anioSADE.getValue() <= 1854) {
				throw new WrongValueException(this.anioSADE, Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto",
						new Object[] { String.valueOf(this.anioSADE.intValue()) }));
			}
		} else {
			throw new WrongValueException(this.anioSADE,
					Labels.getLabel("gedo.consultaDocumentos.anioIncorrecto", new Object[] { "" }));
		}

	}

	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}

	public Intbox getAnioSADE() {
		return anioSADE;
	}

	public void setAnioSADE(Intbox anioSADE) {
		this.anioSADE = anioSADE;
	}

	public Bandbox getFamiliaEstructuraTree() {
		return familiaEstructuraTree;
	}

	public void setFamiliaEstructuraTree(Bandbox familiaEstructuraTree) {
		this.familiaEstructuraTree = familiaEstructuraTree;
	}

	public void cargarTipoDocumento(TipoDocumentoDTO data) {
		this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
		this.familiaEstructuraTree.close();
		setSelectedTipoDocumentoSADE(data);
	}

	public TipoDocumentoDTO getSelectedTipoDocumentoSADE() {
		return selectedTipoDocumentoSADE;
	}

	public void setSelectedTipoDocumentoSADE(TipoDocumentoDTO selectedTipoDocumentoSADE) {
		this.selectedTipoDocumentoSADE = selectedTipoDocumentoSADE;
	}

	public void setEcosistemaService(IEcosistemaService ecosistemaService) {
		this.ecosistemaService = ecosistemaService;
	}

	public IEcosistemaService getEcosistemaService() {
		return ecosistemaService;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

}

@SuppressWarnings("rawtypes")
final class ConsultaPorNumeroSADEComposerListener implements EventListener {
	private ConsultaPorNumeroSADEComposer composer;

	public ConsultaPorNumeroSADEComposerListener(ConsultaPorNumeroSADEComposer comp) {
		this.composer = comp;
	}

	public void onEvent(Event event) throws Exception {

		if (event.getName().equals(Events.ON_NOTIFY)) {
			if (event.getData() != null) {
				TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
				this.composer.cargarTipoDocumento(data);
			}
		}
	}
}
