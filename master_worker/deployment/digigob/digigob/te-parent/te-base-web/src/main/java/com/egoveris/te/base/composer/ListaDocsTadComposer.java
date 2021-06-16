package com.egoveris.te.base.composer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.vucfront.model.model.TipoTramiteTipoDocDTO;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ListaDocsTadComposer extends GenericForwardComposer {

  private static final long serialVersionUID = -604736600612270418L;

  public static final String LISTA_DOCS_TAD = "listaDocs";
  public static final String LISTA_EDITABLE = "listaEditable";
  public static final String ES_SUBSANAR = "esSubsanar";
  public static final Pattern DIACRITICS_AND_FRIENDS = Pattern
      .compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

  private List<ExternalDocumentoVucDTO> listaDocs;
  private List<String> selectedDocs;
  private Button guardar;
  private Boolean listaEditable;
  
  private Listfooter lisTotalSize;
  private Listbox tipoDocListbox;
  private List<ExternalDocumentoVucDTO> tiposDocumentosFiltrados;
  private List<TipoTramiteTipoDocDTO> listDoc;

  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;
  
  @Autowired
  private AnnotateDataBinder binder;

	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		this.selectedDocs = new ArrayList<>();
		tipoDocumentoService = (TipoDocumentoService) Executions.getCurrent().getDesktop().getAttribute("tipoDocService");
		obtenerSubsanacion();
		tipoDocListbox.setModel(new ListModelList<>(listaDocs));		
		lisTotalSize.setLabel(String.valueOf(listaDocs.size()));
		guardar.setDisabled(!this.listaEditable);
	}

	@SuppressWarnings("unchecked")
	public void obtenerSubsanacion() {
		listDoc = (List<TipoTramiteTipoDocDTO>) Executions.getCurrent().getDesktop().getAttribute("docVuc");
	}
	
	public void onSelectTD(Event event) throws InterruptedException {
		ExternalDocumentoVucDTO doc = (ExternalDocumentoVucDTO) event.getData();
		doc.setSeleccionado(!doc.isSeleccionado());
	}

  /**
   * Carga los tipos de documentos que matchean con el string ingresado
   * 
   * @param e
   */
  public void onChanging$busquedaTipoDocumento(InputEvent e) {
    this.tiposDocumentosFiltrados = new ArrayList<>();
    String matchingText = normalizarString(e.getValue());
    this.cargarTiposDocumentos(matchingText);
  }

  /**
   * @param matchingText
   *          carga en tiposDocumentosFiltrados los que coinciden con
   *          matchingText
   */
  public void cargarTiposDocumentos(String matchingText) {

    this.tiposDocumentosFiltrados.clear();
    if (!matchingText.equals("") && (matchingText.length() >= 2)) {
      if (this.tiposDocumentosFiltrados != null) {
        matchingText = matchingText.toUpperCase();
        for (ExternalDocumentoVucDTO tipoDocumento : this.listaDocs) {

          Pattern pat = Pattern.compile(".*" + matchingText.trim() + ".*");
          Matcher matNombre = pat.matcher(normalizarString(tipoDocumento.getTipoDocumento().getNombre()));
          Matcher matAcronimo = pat.matcher(normalizarString(tipoDocumento.getTipoDocumento().getAcronimoTad()));
          if (matNombre.matches() || matAcronimo.matches()) {
            this.tiposDocumentosFiltrados.add(tipoDocumento);
          }
         	if (tipoDocumento.isSeleccionado()) {
         		System.out.println("SELECCIONADO: " + tipoDocumento.getTipoDocumento().getNombre());
         	}
        }
      }
			this.tipoDocListbox.setModel(new ListModelList<>(this.tiposDocumentosFiltrados));
    } else{
		this.tipoDocListbox.setModel(new ListModelList<>(listaDocs));
    }
//    this.binder.loadComponent(busquedaTipoDocumento);
  }
  
  /**
   * Quita acentos y caracteres especiales y pasa a Mayusculas el string
   * 
   * @param string
   * @return
   */
  public static String normalizarString(String string) {
    if (string == null) {
      return null;
    }
    string = string.trim().toUpperCase();
    string = Normalizer.normalize(string, Normalizer.Form.NFD);
    string = DIACRITICS_AND_FRIENDS.matcher(string).replaceAll("");
    return string;
  }

  /**
   * Abre o cierra la Familia del Arbol
   * 
   * @param event
   * @throws InterruptedException
   */
  public void onSelectFamily(Event event) throws InterruptedException {
    Treeitem parent = (Treeitem) event.getData();
    parent.setOpen(!parent.isOpen());
  }
  
	private void recorrerLista(List<Component> children) {

		for (Component child : children) {
			if (child instanceof Checkbox) {
				Checkbox check = (Checkbox) child;
				if (check.isChecked()) {
					this.selectedDocs.add(check.getId());
				}
			} else {
				recorrerLista(child.getChildren());
			}
		}
	}


	public void onClick$guardar() {
		recorrerLista(this.tipoDocListbox.getChildren());
		Executions.getCurrent().getDesktop().setAttribute("docsAgregar", this.selectedDocs);
		((Window) this.self).onClose();
	}

  public void onClick$salir() {
    ((Window) this.self).onClose();
  }

  public List<ExternalDocumentoVucDTO> getListaDocs() {
    return listaDocs;
  }

  public void setListaDocs(List<ExternalDocumentoVucDTO> listaDocs) {
    this.listaDocs = listaDocs;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public List<TipoTramiteTipoDocDTO> getListDoc() {
    return listDoc;
  }

  public void setListDoc(List<TipoTramiteTipoDocDTO> listDoc) {
    this.listDoc = listDoc;
  }

}