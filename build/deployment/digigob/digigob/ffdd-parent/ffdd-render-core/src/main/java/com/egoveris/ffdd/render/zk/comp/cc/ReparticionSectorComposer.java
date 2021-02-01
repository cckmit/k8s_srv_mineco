
package com.egoveris.ffdd.render.zk.comp.cc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.BandboxExt;

public class ReparticionSectorComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell organismoDiv;
	Cell sectorDiv;
	InputElement organismo;
	InputElement sector;
	Listbox listboxRepaDiv = new Listbox();
	Listbox listboxSectorDiv = new Listbox();

	transient Object service;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReparticionSectorComposer.class);

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		try {
			service = SpringUtil.getBean("externalReparticionService");
			Method method = service.getClass().getMethod("obtenerReparticiones");
			List<String> lista = (List<String>) method.invoke(service);
			buildBandboxRepa();
			buildBandboxSector();
			listboxRepaDiv.setModel(new ListModelList<String>(lista));

			this.organismo.addEventListener(Events.ON_CHANGING,
					e -> filtrar((InputEvent) e, this.organismo, listboxRepaDiv, lista));
		} catch (Exception e) {
			LOGGER.error("Error al cargar los organismos: " + e.getMessage(), e);
			throw new WrongValueException("No se pudo cargar los organismos");
		}
	}

	private void buildBandboxRepa() {
		Bandpopup pop = new Bandpopup();
		Groupbox group = new Groupbox();
		Vbox vbox = new Vbox();
		Paging pag = new Paging();
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader();
		pop.setParent(this.organismo);
		group.setParent(pop);
		group.setClosable(false);
		vbox.setParent(group);
		listboxRepaDiv.setParent(vbox);
		pag.setParent(vbox);
		listhead.setParent(listboxRepaDiv);
		listheader.setParent(listhead);
		listboxRepaDiv.setItemRenderer(new OrganismoRenderer());
		pag.setPageSize(10);
		listboxRepaDiv.setMold("paging");
		listboxRepaDiv.setPaginal(pag);
		listboxRepaDiv.setWidth("225px");
		listheader.setLabel(Labels.getLabel("list.organismo"));
	}

	private void buildBandboxSector() {
		Bandpopup pop = new Bandpopup();
		Groupbox group = new Groupbox();
		Vbox vbox = new Vbox();
		Paging pag = new Paging();
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader();
		pop.setParent(this.sector);
		group.setParent(pop);
		group.setClosable(false);
		vbox.setParent(group);
		listboxSectorDiv.setParent(vbox);
		pag.setParent(vbox);
		listhead.setParent(listboxSectorDiv);
		listheader.setParent(listhead);
		listboxSectorDiv.setItemRenderer(new SectorRenderer());
		pag.setPageSize(10);
		listboxSectorDiv.setMold("paging");
		listboxSectorDiv.setPaginal(pag);
		listboxSectorDiv.setWidth("225px");
		listheader.setLabel(Labels.getLabel("list.sector"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buscarSectores() {
		try {
			Method method = service.getClass().getMethod("obtenerSectores", String.class);
			String reparticion = ((BandboxExt) this.organismo).getValue();
			List<String> lista = (List<String>) method.invoke(service,
					reparticion.substring(reparticion.indexOf('(') + 1, reparticion.indexOf(')')));
			listboxSectorDiv.setModel(new ListModelList<String>(lista));
			this.sector.addEventListener(Events.ON_CHANGING,
					e -> filtrar((InputEvent) e, this.sector, listboxSectorDiv, lista));
		} catch (Exception e) {
			LOGGER.error("Error al cargar los sectores: " + e.getMessage(), e);
			listboxSectorDiv.setModel(new ListModelList(new ArrayList<String>()));
			throw new WrongValueException("No se pudo cargar los sectores");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void filtrar(InputEvent e, InputElement input, Listbox listbox, List<String> listaCompleta) {
		((BandboxExt) input).open();

		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText) && (matchingText.length() >= 1)) {
			List<String> listaFiltrada = new ArrayList<>();
			if (listaCompleta != null) {
				matchingText = matchingText.toUpperCase();

				for (String user : listaCompleta) {

					Pattern pat = Pattern.compile(".*" + matchingText.trim() + ".*");
					Matcher mat = pat.matcher(user.toUpperCase());

					if (mat.matches()) {
						listaFiltrada.add(user);
					}
				}
			}
			listbox.setModel(new ListModelList(listaFiltrada));
		} else {
			listbox.setModel(new ListModelList(listaCompleta));
		}
	}

	@Override
	protected String getName() {
		return "reparticionsector";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

	public void onElegirOrganismo(Listitem item) {
		((BandboxExt) this.organismo).setValue(item.getValue());
		((BandboxExt) this.organismo).close();
		buscarSectores();
	}

	public void onElegirSector(Listitem item) {
		((BandboxExt) this.sector).setValue(item.getValue());
		((BandboxExt) this.sector).close();
	}

	@SuppressWarnings("rawtypes")
	public class OrganismoRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data, int arg2) throws Exception {
			String org = (String) data;

			Listcell listcell = new Listcell(org);
			listcell.setParent(item);
			item.setValue(org);
			item.addEventListener(Events.ON_CLICK, e -> onElegirOrganismo(item));
		}
	}

	@SuppressWarnings("rawtypes")
	public class SectorRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data, int arg2) throws Exception {
			String org = (String) data;

			Listcell listcell = new Listcell(org);
			listcell.setParent(item);
			item.setValue(org);
			item.addEventListener(Events.ON_CLICK, e -> onElegirSector(item));
		}
	}
}