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

public class ReparticionComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell organismoDiv;
	InputElement organismo;
	Listbox listboxDiv = new Listbox();

	private static final Logger LOGGER = LoggerFactory.getLogger(ReparticionComposer.class);

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		try {

			Object service = SpringUtil.getBean("externalReparticionService");
			Method method = service.getClass().getMethod("obtenerReparticiones");
			List<String> lista = (List<String>) method.invoke(service);

			buildBandbox();
			listboxDiv.setModel(new ListModelList<String>(lista));
			this.organismo.addEventListener(Events.ON_CHANGING, e -> filtrarOrganismos((InputEvent) e, listboxDiv, lista));
		} catch (Exception e) {
			LOGGER.error("Error al cargar los organismos: " + e.getMessage(), e);
			throw new WrongValueException("No se pudo cargar los organismos");

		}
	}

	private void buildBandbox() {
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
		listboxDiv.setParent(vbox);
		pag.setParent(vbox);
		listhead.setParent(listboxDiv);
		listheader.setParent(listhead);
		listboxDiv.setItemRenderer(new OrganismoRenderer());
		pag.setPageSize(10);
		listboxDiv.setMold("paging");
		listboxDiv.setPaginal(pag);
		listboxDiv.setWidth("300px");
		listheader.setLabel(Labels.getLabel("list.organismo"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void filtrarOrganismos(InputEvent e, Listbox listbox, List<String> listaCompleta) {
		((BandboxExt) this.organismo).open();

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

	public void onElegirOrganismo(Listitem item) {
		((BandboxExt) this.organismo).setValue(item.getValue());
		((BandboxExt) this.organismo).close();
	}

	@Override
	protected String getName() {
		return "reparticion";
	}

	@Override
	protected void setDefaultValues(final String name) {
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

}