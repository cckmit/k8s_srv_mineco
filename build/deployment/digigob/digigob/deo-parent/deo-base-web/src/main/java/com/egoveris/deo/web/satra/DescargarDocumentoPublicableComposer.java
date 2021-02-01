package com.egoveris.deo.web.satra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DescargarDocumentoPublicableComposer extends GenericForwardComposer {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 8120487614299873053L;
	
		private static final Logger logger = LoggerFactory.getLogger(DescargarDocumentoPublicableComposer.class);

		private Label titulo;
		private Window descargaDocumentoPublicableWindow;

		public void doAfterCompose(Component component) throws Exception {
			super.doAfterCompose(component);

			Executions.getCurrent().getDesktop().setAttribute("soloLectura", true);

			this.titulo.setValue("Descargar documento");
		}

		public Label getTitulo() {
			return titulo;
		}

		public void setTitulo(Label titulo) {
			this.titulo = titulo;
		}
		
}
