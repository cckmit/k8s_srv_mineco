package com.egoveris.ffdd.web.adm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.service.IConstraintService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.shared.collection.CollectionUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AdministradorExportFCComposer extends GenericForwardComposer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AdministradorExportFCComposer.class);

	public static String md5Spring(final String text) {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	// componentes zk
	private Window exportarFCWindow;
	private Listbox listboxFormulariosExistentes;
	private Listbox listboxFormulariosExportar;

	private AnnotateDataBinder exportarFCWindowBinder;
	// atributos
	private List<FormularioDTO> listaFormularios;
	private List<FormularioDTO> listaFormularioAux;

	private List<FormularioDTO> listaFormulariosExportar;
	// Beans
	@WireVariable("formularioService")
	private IFormularioService formularioService;
	@WireVariable("constraintService")
	private IConstraintService constraintService;

	private List<FormTriggerDTO> buscarTriggersFormulario(final int idFormulario) throws AccesoDatoException {
		return this.constraintService.obtenerConstraintsPorComponente(idFormulario);
	}

	public void crearXML(final List<FormularioDTO> listaFormularios)
			throws InterruptedException, ParseException, DynFormException, AccesoDatoException {

		final Element formularios = new Element("forms");
		final Document doc = new Document(formularios);
		doc.setRootElement(formularios);

		for (final FormularioDTO form : listaFormularios) {

			final Element formulario = new Element("form");
			formulario.setAttribute(new Attribute("id", form.getId().toString()));
			formulario.setAttribute(new Attribute("name", form.getNombre()));
			formulario.setAttribute(new Attribute("description", form.getDescripcion()));

			final Set<FormularioComponenteDTO> listaComponentes = form.getFormularioComponentes();
			final Element formcomponentes = new Element("formcomponents");
			final Element componentes = new Element("components");
			final Element triggers = new Element("triggers");

			for (final FormularioComponenteDTO comp : listaComponentes) {

				final Element formcomponente = new Element("formcomponent");
				formcomponente.addContent(new Element("id").setText(comp.getId().toString()));
				formcomponente.addContent(new Element("name").setText(comp.getNombre()));
				formcomponente.addContent(new Element("label").setText(comp.getEtiqueta()));
				formcomponente.addContent(new Element("order").setText(comp.getOrden().toString()));
				formcomponente.addContent(new Element("obligatory").setText(comp.getObligatorio().toString()));
				formcomponente
						.addContent(new Element("searchrelevancy").setText(comp.getRelevanciaBusqueda().toString()));
				formcomponente.addContent(new Element("hidden").setText(comp.getOculto().toString()));
				if (comp.isMultilinea()) {
					formcomponente.addContent(new Element("textoMultilinea")
							.setText(this.formularioService.buscarMultilinea(comp.getId())));
				}
				formcomponentes.addContent(formcomponente);

				final Element componente = new Element("component");
				componente.addContent(new Element("id").setText(comp.getComponente().getId().toString()));
				componente.addContent(new Element("name").setText(comp.getComponente().getNombre()));
				componente.addContent(new Element("description").setText(comp.getComponente().getDescripcion()));

				final Element tipoComponente = new Element("type");
				tipoComponente.addContent(
						new Element("id").setText(comp.getComponente().getTipoComponenteEnt().getId().toString()));
				tipoComponente.addContent(
						new Element("name").setText(comp.getComponente().getTipoComponenteEnt().getNombre()));
				tipoComponente.addContent(new Element("description"));

				componente.addContent(tipoComponente);

				final Map<String, AtributoComponenteDTO> listAtributos = comp.getComponente().getAtributos();
				final Set<String> keys = comp.getComponente().getAtributos().keySet();

				final Element atributos = new Element("atributtes");

				for (final String key : keys) {
					final Element atributo = new Element("atributte");
					atributo.addContent(new Element("id").setText(listAtributos.get(key).getId().toString()));
					atributo.addContent(new Element("key").setText(key));
					atributo.addContent(new Element("value").setText(listAtributos.get(key).toString()));
					atributos.addContent(atributo);
				}

				final Set<ItemDTO> items = comp.getComponente().getItems();
				final Element multivalues = new Element("multivalues");
				for (final ItemDTO item : items) {
					final Element valor = new Element("value");
					valor.addContent(new Element("id").setText(item.getId().toString()));
					valor.addContent(new Element("value").setText(item.getValor()));
					valor.addContent(new Element("description").setText(item.getDescripcion()));
					valor.addContent(new Element("order").setText(item.getOrden().toString()));
					multivalues.addContent(valor);
				}

				componente.addContent(atributos);
				componente.addContent(multivalues);
				componentes.addContent(componente);
			}

			final List<FormTriggerDTO> listaTriggers = buscarTriggersFormulario(form.getId());
			if (listaTriggers != null) {
				for (final FormTriggerDTO trigger : listaTriggers) {
					final Element formTrigger = new Element("trigger");
					formTrigger.addContent(new Element("id").setText(trigger.getId().toString()));
					formTrigger.addContent(new Element("formulario").setText(trigger.getIdForm().toString()));
					final String json = new String(constraintService.jsonToByte(trigger));
					formTrigger.addContent(new Element("json").setText(json));
					formTrigger.addContent(new Element("type").setText(trigger.getType()));
					final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					final String fecha = df.format(trigger.getFechaCreacion());
					formTrigger.addContent(new Element("fechaCreacion").setText(fecha));
					triggers.addContent(formTrigger);
				}
			}

			formulario.addContent(formcomponentes);
			formulario.addContent(componentes);
			formulario.addContent(triggers);
			doc.getRootElement().addContent(formulario);
		}

		final XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		final String docString = xmlOutput.outputString(doc);

		final Element md5 = new Element("md5");
		md5.addContent(md5Spring(docString));
		formularios.addContent(md5);

		Filedownload.save(xmlOutput.outputString(doc), null, nombreArchivo());

		final StringBuffer mensaje = new StringBuffer();
		mensaje.append(Labels.getLabel("admExpFcComposer.msjAppn.formExport") + " \n\n");

		for (final FormularioDTO form : listaFormularios) {
			mensaje.append("-").append(form.getNombre()).append("\n");
		}

		mensaje.append("\n");

		Messagebox.show(mensaje.toString(), Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	@Override
	public void doAfterCompose(final Component comp) throws DynFormException {
		try {
			super.doAfterCompose(comp);
			Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
			obtenerDatos();
			this.exportarFCWindowBinder = new AnnotateDataBinder(exportarFCWindow);
			this.exportarFCWindowBinder.loadAll();
		} catch (final Exception e) {
			throw new DynFormException(
					Labels.getLabel("admExpFcComposer.exception.erroExportador") + 
						Labels.getLabel("abmComboboxComposer.exception.intMinutos"), e);
		}
	}

	public Window getExportarFCWindow() {
		return exportarFCWindow;
	}

	public List<FormularioDTO> getListaFormularios() {
		return listaFormularios;
	}

	public List<FormularioDTO> getListaFormulariosExportar() {
		return listaFormulariosExportar;
	}

	public Listbox getListboxFormulariosExistentes() {
		return listboxFormulariosExistentes;
	}

	public Listbox getListboxFormulariosExportar() {
		return listboxFormulariosExportar;
	}

	public String nombreArchivo() {
		final Calendar fecha = new GregorianCalendar();
		final String date = new java.text.SimpleDateFormat("dd-MM-yyyy_HH-mm").format(fecha.getTime());
		return date + "FC" + ".xml";
	}

	public void obtenerDatos() throws DynFormException, AccesoDatoException {

		final List<FormularioDTO> primerListaFormularios = this.formularioService.obtenerTodosLosFormularios();
		this.listaFormularios = new ArrayList<FormularioDTO>();

		for (final FormularioDTO form : primerListaFormularios) {
			if (!form.getNombre().startsWith("--TEMP")) {
				listaFormularios.add(form);
			}
		}

		Collections.sort(listaFormularios, new Comparator<FormularioDTO>() {
			@Override
			public int compare(final FormularioDTO f1, final FormularioDTO f2) {
				return f1.getNombre().toUpperCase().compareTo(f2.getNombre().toUpperCase());
			}

		});
		this.listaFormularioAux = listaFormularios;
		this.listaFormulariosExportar = new ArrayList<FormularioDTO>();
	}

	public void onCancel() throws Exception {
		try {
			Messagebox.show(Labels.getLabel("fc.export.salirPreg.msg"), Labels.getLabel("fc.confirmacion"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onYes".equals(evt.getName())) {
								exportarFCWindow.onClose();
							}
						}
					});
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void onChanging$buscarFormulario(final InputEvent e) {
		final List<FormularioDTO> listaFiltrada = new ArrayList<FormularioDTO>();
		for (final FormularioDTO form : listaFormularioAux) {
			if (form.getNombre().toLowerCase().contains(e.getValue().toLowerCase())) {
				listaFiltrada.add(form);
			}
		}
		if (!e.getValue().equals("")) {
			setListaFormularios(listaFiltrada);
		} else {
			setListaFormularios(listaFormularioAux);
		}
		this.exportarFCWindowBinder.loadComponent(listboxFormulariosExistentes);
	}

	public void onDropItem(final ForwardEvent fe) {

		final DropEvent event = (DropEvent) fe.getOrigin();
		final Listitem draggedComp = (Listitem) event.getDragged();
		final ListModelList model = (ListModelList) listboxFormulariosExportar.getModel();
		try {
			if (draggedComp.getParent().getId().equals(this.listboxFormulariosExistentes.getId())) {

				if (event.getTarget() instanceof Listbox || event.getTarget() instanceof Listitem) {
					final FormularioDTO formulario = (FormularioDTO) draggedComp.getValue();
					if (verificarFormularioRepetido(formulario)) {
						model.add(model.size(), formulario);
					}
				}
			} else {

				if (event.getTarget() instanceof Button && draggedComp.getParent() == listboxFormulariosExportar) {
					model.remove(draggedComp.getIndex());
				}
			}

			setListaFormulariosExportar(CollectionUtils.asList(model.getInnerList(), FormularioDTO.class));

		} catch (final Exception e) {
			logger.error("mensaje de error", e);
		}
	}

	public void onExport() throws Exception {
		try {

			if (listaFormulariosExportar.size() != 0) {
				Messagebox.show(Labels.getLabel("fc.export.exportar.msg"), Labels.getLabel("fc.confirmacion"),
						Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
							@Override
							public void onEvent(final Event evt) throws InterruptedException, ParseException {
								if ("onYes".equals(evt.getName())) {
									try {
										crearXML(listaFormulariosExportar);
									} catch (final DynFormException e) {
										logger.error("Error al exportar el formulario:" + e.getMessage(), e);
									} catch (final AccesoDatoException e) {
										logger.error("Error al exportar el formulario:" + e.getMessage(), e);
									}
									exportarFCWindow.onClose();
								}
							}
						});
			} else {
				Messagebox.show(Labels.getLabel("fc.export.exportar.vali"), Labels.getLabel("fc.export.atencion.title"),
						Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void setExportarFCWindow(final Window exportarFCWindow) {
		this.exportarFCWindow = exportarFCWindow;
	}

	public void setListaFormularios(final List<FormularioDTO> listaFormularios) {
		this.listaFormularios = listaFormularios;
	}

	public void setListaFormulariosExportar(final List<FormularioDTO> listaFormulariosExportar) {
		this.listaFormulariosExportar = listaFormulariosExportar;
	}

	public void setListboxFormulariosExistentes(final Listbox listboxFormulariosExistentes) {
		this.listboxFormulariosExistentes = listboxFormulariosExistentes;
	}

	public void setListboxFormulariosExportar(final Listbox listboxFormulariosExportar) {
		this.listboxFormulariosExportar = listboxFormulariosExportar;
	}

	public boolean verificarFormularioRepetido(final FormularioDTO formulario) throws InterruptedException {
		if (listaFormulariosExportar.size() != 0) {
			for (final FormularioDTO item : listaFormulariosExportar) {
				if (item.getNombre().toUpperCase().equals(formulario.getNombre().toUpperCase())) {
					Messagebox.show(Labels.getLabel("fc.export.repetido.vali"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
					return false;
				}
			}
		}
		return true;
	}

}
