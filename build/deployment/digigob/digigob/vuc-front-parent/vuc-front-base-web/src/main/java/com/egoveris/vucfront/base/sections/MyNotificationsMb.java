package com.egoveris.vucfront.base.sections;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;

import com.egoveris.vucfront.base.exception.DownloadDocumentException;
import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.exception.ValidacionException;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;

@ManagedBean
@ViewScoped
public class MyNotificationsMb extends AbstractMb {
	private static final long serialVersionUID = 3464577970455713571L;

	private static final Logger LOG = LoggerFactory.getLogger(MyNotificationsMb.class);

	@ManagedProperty("#{documentoServiceImpl}")
	private DocumentoService documentoService;
	@ManagedProperty("#{loginMb}")
	private LoginMb login;
	@ManagedProperty("#{notificacionServiceImpl}")
	private NotificacionService notificacionService;
	@ManagedProperty("#{msg}")
	private DefaultLocaleMessageSource bundle;

	private List<NotificacionDTO> notificacionesList;
	private Map<String, List<NotificacionDTO>> mapaExpedienteListaNotificaciones;
	private Entry<String, List<NotificacionDTO>> selectedExpedienteMap;

	public void init() {
		// Login
		if (login.getPersona() == null) {
			redirect(ConstantsUrl.LOGIN);
		}

		if (notificacionesList == null) {
			notificacionesList = notificacionService.getNotificacionesByPersona(login.getPersona());
		}
		if (mapaExpedienteListaNotificaciones == null) {
			mapaExpedienteListaNotificaciones = new LinkedHashMap<>();
			fillExpedienteNotificacionesMap(notificacionesList);
		}
	}

	/**
	 * Set the current Expediente and it's Notifications for the dialog display.
	 * 
	 * @param expedienteEntry
	 */
	public void cmdViewNotification(Entry<String, List<NotificacionDTO>> expedienteEntry) {
		selectedExpedienteMap = expedienteEntry;
		// Set's the Notificaciones as viewed
		for (NotificacionDTO aux : expedienteEntry.getValue()) {
			notificacionService.setNotificacionAsViewed(aux);
		}
	}

	/**
	 * Checks if an Expediente has any unseen Notification.
	 * 
	 * @param expedienteEntry
	 * @return
	 */
	public boolean hasExpedienteAnyUnseenNotificacion(Entry<String, List<NotificacionDTO>> expedienteEntry) {
		boolean unseenNotification = false;
		for (NotificacionDTO aux : expedienteEntry.getValue()) {
			if (!aux.getNotificado()) {
				unseenNotification = true;
				break;
			}
		}
		return unseenNotification;
	}
	
	/**
	 * Checks if an Notification is seen.
	 * 
	 * @param expedienteEntry
	 * @return
	 */
	public boolean isSeenNotificacion(NotificacionDTO notificacion) {
		return notificacion.getNotificado();
	}

	public StreamedContent cmdDownloadNotificacion(NotificacionDTO notificacion) {
		
		DocumentoDTO dto = new DocumentoDTO();
		dto.setNumeroDocumento(notificacion.getCodSade());
		dto.setUsuarioCreacion(notificacion.getExpediente().getTipoTramite().getUsuarioIniciador());
		dto.setNombreOriginal(notificacion.getCodSade());
		String contentType = "application/octet-stream";
		try {
			return new DefaultStreamedContent(documentoService.downloadDocument(dto),
					contentType, notificacion.getCodSade()+".pdf");
		} catch (DownloadDocumentException e) {
			showDialogMessage(e.getMessage(), MessageType.ERROR);
		} catch (ValidacionException e2) {
			showDialogMessage(bundle.getMessage("myDocumentsErrorSistema", null), MessageType.ERROR);
		}

		return null;
	}
	
	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder("?");

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	/**
	 * Map Expedientes with a list of notificaciones ordered by date.
	 */
	private void fillExpedienteNotificacionesMap(List<NotificacionDTO> notificacionesList) {
		for (NotificacionDTO aux : notificacionesList) {
			if (mapaExpedienteListaNotificaciones.get(aux.getExpediente().getCodigoSade()) == null) {
				mapaExpedienteListaNotificaciones.put(aux.getExpediente().getCodigoSade(),
						new ArrayList<NotificacionDTO>());
			}
			mapaExpedienteListaNotificaciones.get(aux.getExpediente().getCodigoSade()).add(aux);
		}
	}

	public Map<String, List<NotificacionDTO>> getMapaExpedienteListaNotificaciones() {
		return mapaExpedienteListaNotificaciones;
	}

	public Entry<String, List<NotificacionDTO>> getSelectedExpedienteMap() {
		return selectedExpedienteMap;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public void setLogin(LoginMb login) {
		this.login = login;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
	
	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}

}