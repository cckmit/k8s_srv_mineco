package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TrataAuditoriaDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.rendered.TrataHistoricotemRenderer;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HistorialTrataComposer extends EEGenericForwardComposer {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2781903961003275009L;
	
	@Autowired
    private Window historialTrataWindow;
    private TrataDTO trata;
    
    @WireVariable(ConstantesServicios.AUDITORIA_SERVICE)
    private IAuditoriaService auditoriaService;
    
    private AnnotateDataBinder binder;
    
    @Autowired
    private Window historialView;
    
    private TrataAuditoriaDTO selectedAuditoriaTrata;
    
    private List<TrataAuditoriaDTO> listaHistoricoFiltrado;
    private List<TrataAuditoriaDTO> listaHistorico;
    
    @Autowired
	private Listbox listaHistoricoList;
    
	private TrataHistoricotemRenderer trataHistoricotemRenderer;
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
        this.binder = new AnnotateDataBinder(comp);
         
        this.trata = (TrataDTO) Executions.getCurrent().getArg().get("trata");
        listaHistoricoFiltrado = this.auditoriaService.buscarHistorialTrata(trata);
        
        this.binder.loadAll();
    }

    public void onCancelar() throws InterruptedException {
        this.historialTrataWindow.detach();
    }

    public TrataDTO getTrata() {
        return trata;
    }

    public void setTrata(TrataDTO trata) {
        this.trata = trata;
    }

    public AnnotateDataBinder getBinder() {
        return binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }
    
    public Window getHistorialTrataWindow() {
		return historialTrataWindow;
	}

	public void setHistorialTrataWindow(Window historialTrataWindow) {
		this.historialTrataWindow = historialTrataWindow;
	}

	public Window getHistorialView() {
		return historialView;
	}

	public void setHistorialView(Window historialView) {
		this.historialView = historialView;
	}

	public void setSelectedAuditoriaTrata(TrataAuditoriaDTO selectedAuditoriaTrata) {
		this.selectedAuditoriaTrata = selectedAuditoriaTrata;
	}

	public TrataAuditoriaDTO getSelectedAuditoriaTrata() {
		return selectedAuditoriaTrata;
	}
	
	public List<TrataAuditoriaDTO> getListaHistoricoFiltrado() {
		return listaHistoricoFiltrado;
	}

	public void setListaHistoricoFiltrado(List<TrataAuditoriaDTO> listaHistoricoFiltrado) {
		this.listaHistoricoFiltrado = listaHistoricoFiltrado;
	}

	public List<TrataAuditoriaDTO> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<TrataAuditoriaDTO> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}
	
	public Listbox getListaHistoricoList() {
		return listaHistoricoList;
	}

	public void setListaHistoricoList(Listbox listaHistoricoList) {
		this.listaHistoricoList = listaHistoricoList;
	}

}
