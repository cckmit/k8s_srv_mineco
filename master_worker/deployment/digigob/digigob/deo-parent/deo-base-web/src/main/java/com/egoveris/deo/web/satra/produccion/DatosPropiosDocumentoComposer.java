package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.renderers.DatosDocumentoRenderer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class DatosPropiosDocumentoComposer extends GEDOGenericForwardComposer {

	private static final String LABEL_SALIR = Labels.getLabel("gedo.usuarioInvalido.salir");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1397686110013747941L;

	public static final String METADATA = "metadata";
	public static final String HABILITAR_PANTALLA = "habilitarPantalla";
	public static final String HABILITAR_EDICION = "habilitarEdicion";
	public static final String HABILITAR_CIERRE = "habilitarcierre";

	public static final String TIPO_PROD = "tipoProd";
	
	private Window datosPropiosDelDocumentoWindow;
	private Grid grillaDatos;
	private Textbox campoNuevo;
	private Label idRequerido;
	private Label idSinMetadatos;

	private List<DocumentoMetadataDTO> datos;
	private List<DocumentoMetadataDTO> metadatosOriginal;
	private AnnotateDataBinder binder;
	private ListModelMap strset = new ListModelMap();

	private boolean metaDatosTrataCargados;

	private Boolean habilitarPantalla;
	private Boolean habilitarEdicion;
	private Boolean habilitarCierre;

	private Button guardar;
	private Button cancelar;

	private static Logger logger = LoggerFactory
			.getLogger(DatosPropiosDocumentoComposer.class);

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		metadatosOriginal = (List<DocumentoMetadataDTO>) Executions.getCurrent()
				.getArg().get(METADATA);
		habilitarPantalla = (Boolean) Executions.getCurrent().getArg()
				.get(HABILITAR_PANTALLA);
		habilitarEdicion = (Boolean) Executions.getCurrent().getArg()
				.get(HABILITAR_EDICION);
		habilitarCierre = (Boolean) Executions.getCurrent().getArg()
				.get(HABILITAR_CIERRE);

		if (habilitarPantalla == null) {
			habilitarPantalla = true;
			logger.debug("Valor habilitar no definido, se toma true por defecto");
		}

		if (habilitarEdicion == null) {
			habilitarEdicion = Boolean.TRUE;
			logger.debug("Valor Habilitar Edicion no definido, se toma FALSE por defecto");
		} else if (habilitarEdicion.equals(Boolean.FALSE)) {
			guardar.setVisible(false);
			cancelar.setVisible(true);
			cancelar.setLabel(LABEL_SALIR);
		}

		grillaDatos
				.setRowRenderer(new DatosDocumentoRenderer(habilitarEdicion));
		if (this.getMetadatosOriginal().size() > 0) {
			for (DocumentoMetadataDTO documentoMetadata : this
					.getMetadatosOriginal()) {
				if (documentoMetadata.isObligatoriedad()) {
					idRequerido.setVisible(true);
					break;
				}
			}
		} else {
			this.grillaDatos.setVisible(false);
			this.idSinMetadatos.setVisible(true);
		}
		if (this.datos == null) {
			this.datos = new ArrayList<DocumentoMetadataDTO>(
					this.getMetadatosOriginal());
		}
		
		String tipoProd = (String) Executions.getCurrent().getArg().get(DatosPropiosDocumentoComposer.TIPO_PROD);
		if(tipoProd != null && tipoProd.equals("IMPORTADO")){
			this.grillaDatos.setHeight("230px");
		}
	}

	public void onClick$guardar() throws InterruptedException {

		List<Component> rows = (List<Component>) grillaDatos.getRows().getChildren();

		int i = 0, datosCompletos = 0, datosOblig = 0, datosObligCompletos = 0;
		for (Component hijo : rows) {
			List lista = hijo.getChildren();
			for (Object object : lista) {
				if ((object.getClass().equals(org.zkoss.zul.Hlayout.class))) {
					Hlayout hlayout = (Hlayout) object;
					List hijosHlayout = hlayout.getChildren();

					for (Object hijoLo : hijosHlayout) {
						if ((hijoLo.getClass()
								.equals(org.zkoss.zul.Textbox.class))) {

							Textbox textbox = (Textbox) hijoLo;
							if (!textbox.getValue().equals("")) {
								datosCompletos++;
							}
							if (this.datos.get(i).isObligatoriedad()) {
								datosOblig++;
							}
							if (!textbox.getValue().equals("")
									&& this.datos.get(i).isObligatoriedad()) {
								datosObligCompletos++;
							}
							if ((this.datos.get(i).isObligatoriedad())
									&& (textbox.getValue() == null || textbox
											.getValue().equals(""))
									&& !isHabilitarPantalla())
								throw new WrongValueException(textbox,
										Labels.getLabel("gedo.datosPropiosDocumento.exception.noValorNulo"));

							this.datos.get(i).setValor(textbox.getValue());
							i++;

						}
					}
				}

			}

		}

		this.metadatosOriginal.clear();
		this.metadatosOriginal.addAll(this.datos);
		
		if(isHabilitarCierre()){			
			informarYCerrarVentana(i, datosCompletos, datosOblig,
					datosObligCompletos);
		}else{
			informarSinCerrarVentana(i, datosCompletos, datosOblig,
					datosObligCompletos);
		}

		 

	}

	private void informarSinCerrarVentana(int i, int datosCompletos,
			int datosOblig, int datosObligCompletos)
			throws InterruptedException {
		if (datosOblig == datosObligCompletos && i != datosCompletos) {
			Messagebox
					.show(Labels.getLabel("gedo.datosPropiosDocumento.exception.datosNoobligatoriosSinCompletar"),
							Labels.getLabel("ccoo.title.atencion"), Messagebox.OK, Messagebox.INFORMATION);
		}

		if (i == datosCompletos) {
			Messagebox.show(Labels.getLabel("gedo.datosPropiosDocumento.exception.datosCompletosTotalidad"),
					Labels.getLabel("ccoo.title.atencion"), Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	private void informarYCerrarVentana(int i, int datosCompletos,
			int datosOblig, int datosObligCompletos)
			throws InterruptedException {
		
		
		if (datosOblig == datosObligCompletos) {
			if(i != datosCompletos) {
				mostrarMensajeDatosIncompletos(Labels.getLabel("gedo.iniciarDocumento.datosPropios.sinCompletar.no.obligatorios"));
			} else {
				Messagebox.show(Labels.getLabel("gedo.datosPropiosDocumento.exception.datosCompletosTotalidad"),
						Labels.getLabel("ccoo.title.atencion"), Messagebox.OK, Messagebox.INFORMATION,
						new EventListener() {
							public void onEvent(Event evt) {
								cerrarVentana();
							}
						});
			}
		} else {
			mostrarMensajeDatosIncompletos(Labels.getLabel("gedo.iniciarDocumento.datosPropios.sinCompletar.obligatorios"));
		}
		
//		if (datosOblig == datosObligCompletos && i != datosCompletos) {
//				 mostrarMensajeDatosIncompletos(Labels.getLabel("gedo.iniciarDocumento.datosPropios.sinCompletar.no.obligatorios"));
//		} else if (i == datosCompletos) {
//			Messagebox.show("Datos propios completos en su totalidad.",
//					"Atencion", Messagebox.OK, Messagebox.INFORMATION,
//					new EventListener() {
//						public void onEvent(Event evt) {
//							cerrarVentana();
//						}
//					});
//		} else if(datosOblig != datosObligCompletos){
//			mostrarMensajeDatosIncompletos(Labels.getLabel("gedo.iniciarDocumento.datosPropios.sinCompletar.obligatorios"));
//		}
	}
	
	public void mostrarMensajeDatosIncompletos(String mensaje) throws InterruptedException{
		Messagebox.show(mensaje,
				Labels.getLabel("gedo.general.information"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
		 new EventListener() {
				public void onEvent(Event evt) {
					switch (((Integer) evt.getData()).intValue()) {
					case Messagebox.YES:
						return;
					case Messagebox.NO:
						cerrarVentana();
						break;
					}
				}
			});
	}
	
	public void cerrarVentana(){
		this.closeAndNotifyAssociatedWindow(null);		
	}
	public void onClick$cancelar() {
		this.datosPropiosDelDocumentoWindow.detach();
	}

	public Grid getGrillaDatos() {
		return grillaDatos;
	}

	public void setGrillaDatos(Grid grillaDatos) {
		this.grillaDatos = grillaDatos;
	}

	public Textbox getCampoNuevo() {
		return campoNuevo;
	}

	public void setCampoNuevo(Textbox campoNuevo) {
		this.campoNuevo = campoNuevo;
	}

	public Label getIdRequerido() {
		return idRequerido;
	}

	public void setIdRequerido(Label idRequerido) {
		this.idRequerido = idRequerido;
	}

	public Label getIdSinMetadatos() {
		return idSinMetadatos;
	}

	public void setIdSinMetadatos(Label idSinMetadatos) {
		this.idSinMetadatos = idSinMetadatos;
	}

	public List<DocumentoMetadataDTO> getDatos() {
		return datos;
	}

	public void setDatos(List<DocumentoMetadataDTO> datos) {
		this.datos = datos;
	}

	public List<DocumentoMetadataDTO> getMetadatosOriginal() {
		return metadatosOriginal;
	}

	public void setMetadatosOriginal(List<DocumentoMetadataDTO> metadatosOriginal) {
		this.metadatosOriginal = metadatosOriginal;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public ListModelMap getStrset() {
		return strset;
	}

	public void setStrset(ListModelMap strset) {
		this.strset = strset;
	}

	public boolean isMetaDatosTrataCargados() {
		return metaDatosTrataCargados;
	}

	public void setMetaDatosTrataCargados(boolean metaDatosTrataCargados) {
		this.metaDatosTrataCargados = metaDatosTrataCargados;
	}

	public Window getDatosPropiosDelDocumentoWindow() {
		return datosPropiosDelDocumentoWindow;
	}

	public void setDatosPropiosDelDocumentoWindow(
			Window datosPropiosDelDocumentoWindow) {
		this.datosPropiosDelDocumentoWindow = datosPropiosDelDocumentoWindow;
	}

	public boolean isHabilitarPantalla() {
		return habilitarPantalla;
	}

	public void setHabilitarPantalla(boolean habilitarPantalla) {
		this.habilitarPantalla = habilitarPantalla;
	}

	public boolean isHabilitarCierre() {
		return habilitarCierre;
	}

	public void setHabilitarCierre(Boolean habilitarCierre) {
		this.habilitarCierre = habilitarCierre;
	}

	public Button getGuardar() {
		return guardar;
	}

	public void setGuardar(Button guardar) {
		this.guardar = guardar;
	}

	public Button getCancelar() {
		return cancelar;
	}

	public void setCancelar(Button cancelar) {
		this.cancelar = cancelar;
	}
}
