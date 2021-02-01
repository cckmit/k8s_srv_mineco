package com.egoveris.te.base.composer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.egoveris.te.base.util.Utils;
import com.egoveris.vucfront.ws.model.ConsolidacionDTO;

@SuppressWarnings("deprecation")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaConsolidacionComposer extends EEGenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4800334034612114686L;
	
	
	private Paging consultaPaginator;
	
	private Menuitem descargarCSV;
	
	private Listbox consultaConsolidacionList;
	
	private List<ConsolidacionDTO> listaConsolidacion;
	
	private AnnotateDataBinder binder;
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(ConsultaConsolidacionComposer.class);
	
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.addEventListener(Events.ON_NOTIFY, new ConsultaConsolidacionListener());
    this.binder = new AnnotateDataBinder(comp);
	}
	
	public void onClick$buscarConsolidacionPorFecha() {
    Window popupConsultaPorFecha = (Window) Executions
        .createComponents(Utils.formatZulPath("consultas/popupConsultaConsolidacionFecha.zul"), this.self,null);
	
    popupConsultaPorFecha.setParent(this.self);
    popupConsultaPorFecha.setPosition("center");
    popupConsultaPorFecha.setVisible(true);
    popupConsultaPorFecha.setClosable(true);
    popupConsultaPorFecha.doModal();
	
	}
	
	public void onClick$descargarCSV() {
		
		String[] headersCSV = Labels.getLabel("ee.consulta.consolidacion.headers.csv").split(";");
		DateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);		
	
		 
    Object[][] tablaResultado = cargarInfoExportar(headersCSV);
    ByteArrayOutputStream csv = null;
    try {
  	
		csv = new ByteArrayOutputStream();
		PrintStream printer = new PrintStream(csv, true, "UTF-8");
	DateFormat dfPago = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");		
		for(int row = 0; row < tablaResultado.length; row++) {
			String tupla = "";
			for(int col= 0; col < tablaResultado[0].length; col ++) {
				if(tablaResultado[row][col] instanceof Date) {
					tupla = tupla + dfPago.format((Date) tablaResultado[row][col])+";";
				}else {	
					Object valor = tablaResultado[row][col];
					if(valor!=null) {						
						tupla = tupla + valor + ";";
					}else {
						tupla = tupla + " " + ";";
					}
				}			
			}
			printer.println(tupla.substring(0, tupla.length() - 1));
			
		}
		printer.close();
		
      
		String text = new String(csv.toByteArray(), StandardCharsets.UTF_8);
		byte[] csvUTF8 = text.getBytes(StandardCharsets.UTF_8);
		
			Filedownload.save(csvUTF8, "text/csv;charset=UTF-8", "consolidaciones_"+ reportDate + ".csv");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}finally {
			if(csv!=null) {
				try {
					csv.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(),e);
				}
			}
		}
    
	}
		
	private Object[][] cargarInfoExportar(String[] headersCSV) {
		Object[][] tablaResultado = new Object[listaConsolidacion.size() + 1][ headersCSV.length];
    
    cargarEncabezados(headersCSV, tablaResultado);
    int index = 0;
    for (int row = 1; row < listaConsolidacion.size() + 1; row ++) {
    	
    	tablaResultado[row][0] = listaConsolidacion.get(index).getCodigoTrata();
    	tablaResultado[row][1] = listaConsolidacion.get(index).getDescripcionTrata();
    	tablaResultado[row][2] = listaConsolidacion.get(index).getNumeroExpediente();
    	tablaResultado[row][3] = listaConsolidacion.get(index).getNumeroTransaccionTad();
    	tablaResultado[row][4] = listaConsolidacion.get(index).getFechaPago();
    	tablaResultado[row][5] = listaConsolidacion.get(index).getNumeroAutorizacion();
    	tablaResultado[row][6] = listaConsolidacion.get(index).getTransaccionPago();
    	tablaResultado[row][7] = listaConsolidacion.get(index).getApiKey();
    	tablaResultado[row][8] = listaConsolidacion.get(index).getMonto();
    	tablaResultado[row][9] = listaConsolidacion.get(index).getTitularTarjeta();
    	tablaResultado[row][10] = listaConsolidacion.get(index).getNumeroDUI();
    	tablaResultado[row][11] = listaConsolidacion.get(index).getOrganismoIniciador();
    	    	
    	index++;
    }
		return tablaResultado;
	}

	private void cargarEncabezados(String[] headersCSV, Object[][] tablaResultado) {
		for(int i = 0; i < headersCSV.length; i++) {
      tablaResultado[0][i] = headersCSV[i];
    }
	}
	
	public Paging getConsultaPaginator() {
		return consultaPaginator;
	}

	public void setConsultaPaginator(Paging consultaPaginator) {
		this.consultaPaginator = consultaPaginator;
	}

	public Listbox getConsultaConsolidacionList() {
		return consultaConsolidacionList;
	}

	public void setConsultaConsolidacionList(Listbox consultaConsolidacionList) {
		this.consultaConsolidacionList = consultaConsolidacionList;
	}

	public List<ConsolidacionDTO> getListaConsolidacion() {
		return listaConsolidacion;
	}

	public void setListaConsolidacion(List<ConsolidacionDTO> listaConsolidacion) {
		this.listaConsolidacion = listaConsolidacion;
	}
	
	@SuppressWarnings("rawtypes")
	public class ConsultaConsolidacionListener implements EventListener{

		@SuppressWarnings({ "unchecked" })
		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(Events.ON_NOTIFY)) {
				listaConsolidacion = (List<ConsolidacionDTO>) event.getData();
				
				if(listaConsolidacion.isEmpty()) {
					consultaConsolidacionList
					.setEmptyMessage(Labels.getLabel("ee.consulta.consolidacion.list.empty"));
					descargarCSV.setDisabled(true);
				}else {
					descargarCSV.setDisabled(false);
				}
				
				binder.loadAll();
			}
		}
		
	}
}
