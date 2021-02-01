package com.egoveris.dashboard.web.composer;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Image;
import org.zkoss.zul.impl.XulElement;

import com.egoveris.dashboard.web.config.EstilosConfig;
import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;
import com.egoveris.sharedsecurity.base.jdbc.service.InfoUsuarioService;

public class HeaderComposer extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderComposer.class);
	
	@Wire("#notificaciones #atask")
	A atask;
	
	@Wire("#notificaciones #anoti")
	A anoti;
	
	@Wire("#notificaciones #amsg")
	A amsg;

	@Wire
	private Image logo;

	@Wire
	private XulElement headerDiv;
	
	private EstilosConfig estilos;
	
	@Override
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		if (this.estilos == null) {
			this.estilos = new EstilosConfig();
		}
		this.logo.setSrc("data:image/png;base64," + estilos.getLogo());
		this.headerDiv.setStyle("background-color: " + this.estilos.getColorHeader() + "; border-bottom: 2px solid " + this.estilos.getColorHeaderBorder() + ";");
	}
	
	@Listen("onOpen = #notificaciones #taskpp")
	public void toggleTaskPopup(OpenEvent event) {
		toggleOpenClass(event.isOpen(), atask);
	}
	
	@Listen("onOpen = #notificaciones #notipp")
	public void toggleNotiPopup(OpenEvent event) {
		toggleOpenClass(event.isOpen(), anoti);
	}
	
	@Listen("onOpen = #notificaciones #msgpp")
	public void toggleMsgPopup(OpenEvent event) {
		toggleOpenClass(event.isOpen(), amsg);
	}

	// Toggle open class to the component
	public void toggleOpenClass(Boolean open, Component component) {
		HtmlBasedComponent comp = (HtmlBasedComponent) component;
		String scls = comp.getSclass();
		
		if (open) {
			comp.setSclass(scls + " open");
		} else {
			comp.setSclass(scls.replace(" open", ""));
		}
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}

	public void setHeaderDiv(XulElement headerDiv) {
		this.headerDiv = headerDiv;
	}
		
}
