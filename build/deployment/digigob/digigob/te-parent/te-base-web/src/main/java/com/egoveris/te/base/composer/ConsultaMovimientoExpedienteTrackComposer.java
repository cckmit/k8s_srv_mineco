package com.egoveris.te.base.composer;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.base.model.PaseDetalleResult;
import com.egoveris.te.base.service.PDFService;
import com.egoveris.te.base.service.PaseServiceSatra;
import com.egoveris.te.base.service.SolrServiceTrack;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaMovimientoExpedienteTrackComposer extends ExpedienteTrackActuacionComposer {

	private static final long serialVersionUID = 3355956850131607529L;

	@Autowired
	Window detalleHistorialExpedienteTrackWindow;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	Button volver;
	@Autowired
	Listbox listHistorial;
	@Autowired
	PDFService pdfService;
	@Autowired
	Textbox iniciador;
	@Autowired
	Textbox codTrata;
	@Autowired
	Textbox trata;
	@Autowired
	Textbox agnc;
	@Autowired
	Textbox remito;
	@Autowired
	Textbox trataDetalle;
	@Autowired
	Textbox extraEjecutiva;
	@Autowired
	Textbox cuerposAnexos;
	@Autowired
	Textbox observacionesDePase;
	@Autowired
	Longbox opAnio;
	@Autowired
	Longbox opNumero;
	@Autowired
	Textbox compTipo;
	@Autowired
	Longbox compAnio;
	@Autowired
	Longbox compNumero;
	@Autowired
	Longbox compImporte;
	@Autowired
	Longbox ALEAnio;
	@Autowired
	Textbox ALELeg;
	@Autowired
	Longbox ALEEst;
	@Autowired
	Button generarPDF;
	@Autowired
	private AnnotateDataBinder binder;
	private ExpedienteTrack model;
	@Autowired
	PaseServiceSatra paseServiceSatra;
	private List<PaseDetalleResult> paseDetalle;
	private PaseDetalleResult selectedPaseDetalle;
	@Autowired
	private String nombreEntorno;

	@WireVariable("appProperty")	
	private AppProperty appProperty;
		
	@WireVariable(ConstantesServicios.SOLR_TRACK_SERVICE)
	private SolrServiceTrack solrServiceTrack;
  
	private String reparticionUsuarioBusqueda;
	private String reparticionActuacionBusqueda;

	private String crearFilaHTML(final PaseDetalleResult p) {
		String r;
		r = "<tr>" + "<td>" + p.getEstado() + "</td>" + "<td>" + p.getOrigen() + "</td>" + "<td>" + p.getDestino()
				+ "</td>" + "<td>" + p.getFechaCreacion() + "</td>" + "<td>" + p.getFojas() + "</td>" + "<td>"
				+ model.getCodigoCaratula() + "</td>" + "</tr>";
		return r;
	}

	private void crearParteInferiorDeTabla(String resultado) {
		resultado += "</tbody></table>";
	}

	private String crearParteSuperiorDeTabla() {
		return "<table style=" + '"' + "width: 100%; text-align: center; margin-left: auto;"
				+ "margin-right: auto; width: 966px; height: 85px; font-family: Arial;" + '"' + "cellpadding=" + '"'
				+ "2" + '"' + "cellspacing=" + '"' + "2" + '"' + ">" + "<tbody>" + "<tr style=" + '"'
				+ "border: solid; background: gray;" + '"' + ">" + "<td><font color=" + '"' + "white" + '"'
				+ ">Estado</font></td>" + "<td><font color=" + '"' + "white" + '"' + ">Origen</font></td>"
				+ "<td><font color=" + '"' + "white" + '"' + ">Destino</font></td>" + "<td><font color=" + '"' + "white"
				+ '"' + ">Fecha</font></td>" + "<td><font color=" + '"' + "white" + '"' + ">Fojas</font></td>"
				+ "<td><font color=" + '"' + "white" + '"' + ">Actuaci&#243n Principal</font></td>" + "</tr>";
	}

	private String crearTablaDePases() {

		String resultado = crearParteSuperiorDeTabla();
		for (final PaseDetalleResult p : paseDetalle) {
			resultado += crearFilaHTML(p);
		}
		crearParteInferiorDeTabla(resultado);
		return resultado;

	}

	private String[] desglosarCodigoTrack(final String codigoTrack) {
		return codigoTrack.split("-");
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		binder = new AnnotateDataBinder(comp);
		model = (ExpedienteTrack) Executions.getCurrent().getArg().get("expedienteTrack");
		nombreEntorno = appProperty.getString("nombre.entorno");
		if (model == null) {
			final String codigoExpedienteTrack = Executions.getCurrent().getParameter("codigoExpedienteTrack");
			final String[] cod = desglosarCodigoTrack(codigoExpedienteTrack);
			model = solrServiceTrack.buscarExpedientePapel(cod[0], Integer.valueOf(cod[1]), Integer.valueOf(cod[2]),
					cod[4], cod[5]);

			volver.setDisabled(true);
			detalleHistorialExpedienteTrackWindow.setClosable(false);

		}

		reparticionActuacionBusqueda = (String) Executions.getCurrent().getArg().get("reparticionBusqueda");
		reparticionUsuarioBusqueda = (String) Executions.getCurrent().getArg().get("reparticionUsuarioBusqueda");
		if (reparticionActuacionBusqueda == null && reparticionUsuarioBusqueda == null) {
			reparticionActuacionBusqueda = model.getReparticionOrigenTrack();
			reparticionUsuarioBusqueda = model.getSectorOrigenTrack();
		}
		// this.anio.setValue(Long.valueOf(model.getAnio()));
		this.ex.setValue(model.getLetraTrack());
		this.codigoReparticion.setValue(model.getCodigoReparticionActuacion());
		this.codigoRepUsuario.setValue(model.getCodigoReparticionUsuario());
		this.sec.setValue(model.getSecuencia());
		this.numero.setValue(Long.valueOf(model.getNumero()));
		this.trata.setValue(model.getDescripcion());
		this.codTrata.setValue(model.getCodigoTrataTrack());

		paseDetalle = paseServiceSatra.consultaDetalleDePaseByIdExpediente(model.getId());

		logger.info("Cargando Datos de la ventana: " + detalleHistorialExpedienteTrackWindow.getTitle());
		binder.loadComponent(listHistorial);

	}

	private String eliminarEspacio(final String codigoCaratula) {
		return codigoCaratula.contains(" ") ? codigoCaratula.replaceAll("\\s", "") : codigoCaratula;
	}

	public AppProperty getAppProperty() {
		return appProperty;
	}

	public ExpedienteTrack getModel() {
		return model;
	}

	public List<PaseDetalleResult> getPaseDetalle() {
		return paseDetalle;
	}

	public PaseDetalleResult getSelectedPaseDetalle() {
		return selectedPaseDetalle;
	}

	public void onClick$generarPDF() throws InterruptedException {
		logger.info("Creando archivo PDF con la informacion de los pases del expediente: " + model.getCodigoCaratula());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = putParams();
		try {
			final byte[] pdf = pdfService.generarPDF(parametros);
			final String fileName = "Historial_de_pases_de_Expediente-" + eliminarEspacio(model.getCodigoCaratula())
					+ "-" + parametros.get("fechaHeader") + ".pdf";
			Filedownload.save(pdf, "aplication/pdf", fileName);
			logger.info("Se ha creado de manera exitosa el archivo con nombre: " + fileName);
		} catch (final Exception e) {
			logger.error("Ha ocurrido un error en la generacion del PDF ");
			Messagebox.show(
					Labels.getLabel(
							Labels.getLabel("ee.consultaExpTrackComp.errorCreacionPDF")),
					Labels.getLabel("ee.consultaExpTrackComp.errorPDF"), Messagebox.OK, Messagebox.ERROR);
			logger.error(e.getMessage());
		}
	}

	public void onClick$volver() {

		detalleHistorialExpedienteTrackWindow.detach();
	}

	public void onVerDetallePase() {
		if (selectedPaseDetalle.getTrataDescripcion() != null) {
			trataDetalle.setValue(model.getCodigoTrataTrack() + " - " + selectedPaseDetalle.getTrataDescripcion());
		}
		if (selectedPaseDetalle.getExtraEjecutiva() != null) {
			extraEjecutiva.setValue(selectedPaseDetalle.getExtraEjecutiva());
		}
		if (selectedPaseDetalle.getCuerpoAnexo() != null) {
			cuerposAnexos.setValue(selectedPaseDetalle.getCuerpoAnexo());
		}
		if (selectedPaseDetalle.getObservaciones() != null) {
			observacionesDePase.setValue(selectedPaseDetalle.getObservaciones());
		}
		if (selectedPaseDetalle.getOpAnio() != null) {
			opAnio.setValue(Long.valueOf(selectedPaseDetalle.getOpAnio()));
		}
		if (selectedPaseDetalle.getOpNro() != null) {
			opNumero.setValue(Long.valueOf(selectedPaseDetalle.getOpNro()));
		}
		if (selectedPaseDetalle.getCompTipo() != null) {
			compTipo.setValue(selectedPaseDetalle.getCompTipo());
		}
		if (selectedPaseDetalle.getCompAnio() != null) {
			compAnio.setValue(Long.valueOf(selectedPaseDetalle.getCompAnio()));
		}
		if (selectedPaseDetalle.getCompNro() != null) {
			compAnio.setValue(selectedPaseDetalle.getCompNro().longValue());
		}
		if (selectedPaseDetalle.getCompImporte() != null) {
			compImporte.setValue(selectedPaseDetalle.getCompImporte().longValue());
		}
		if (selectedPaseDetalle.getzEstante() != null) {
			ALEEst.setValue(selectedPaseDetalle.getzEstante().longValue());
		}
		if (selectedPaseDetalle.getLegajo() != null) {
			ALELeg.setValue(selectedPaseDetalle.getLegajo());
		}
		if (selectedPaseDetalle.getzAnio() != null) {
			ALEAnio.setValue(Long.valueOf(selectedPaseDetalle.getzAnio()));
		}
		if (selectedPaseDetalle.getIdRemito() != null) {
			remito.setValue(String.valueOf(selectedPaseDetalle.getIdRemito()));
		}
	}

	public void onVerHistorialPase() throws InterruptedException {
		logger.info("Creando ventana 'consultaHistorialDePasesTrack.zul'");
		try {
			final Map<String, Object> args = new HashMap<String, Object>();
			args.put("paseDetalle", selectedPaseDetalle);
			args.put("expedienteTrack", model);
			final Window wi = (Window) Executions.createComponents("/expediente/consultaHistorialPasesTrack.zul", self,
					args);
			wi.setPosition("center");
			wi.doModal();
			logger.info("Ejecucion exitosa!");
		} catch (final Exception e) {
			logger.error("ha ocurrido un error en la visualizacion de la pantalla consultaHistorialDePasesTrack");
			logger.error(e.getMessage());
			Messagebox.show(Labels.getLabel(Labels.getLabel("ee.consultaExpTrackComp.errorVisualHistorial")),
					Labels.getLabel("ee.consultaExpElecComp.msgbox.errorVisual"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	private Map<String, Object> putParams() {
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		final Map<String, Object> parametros = new HashMap<>();
		final String hoy = format.format(new Date());
		parametros.put("fechaHeader", hoy);
		parametros.put("letra", ex.getValue());
		parametros.put("anio", String.valueOf(anio.getValue().intValue()));
		parametros.put("numero", String.valueOf(numero.getValue().intValue()));
		parametros.put("reparticionActuacion",
				reparticionActuacionBusqueda != null && !reparticionActuacionBusqueda.equals("")
						? reparticionActuacionBusqueda : "---");
		parametros.put("reparticionUsuario",
				reparticionUsuarioBusqueda != null && !reparticionUsuarioBusqueda.equals("")
						? reparticionUsuarioBusqueda : "---");
		parametros.put("secuencia", sec.getValue() != null && !sec.getValue().equals("   ") ? sec.getValue() : "---");
		parametros.put("tablaDePasesHTML", crearTablaDePases());
		return parametros;
	}

	public void setAppProperty(final AppProperty appProperty) {
		this.appProperty = appProperty;
	}

	public void setPaseDetalle(final List<PaseDetalleResult> paseDetalle) {
		this.paseDetalle = paseDetalle;
	}

	public void setSelectedPaseDetalle(final PaseDetalleResult ps) {
		this.selectedPaseDetalle = ps;
	}

}
