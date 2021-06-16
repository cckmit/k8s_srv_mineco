package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.zk.comp.cc.ComplexComponentComposer;

import java.io.ByteArrayInputStream;

import javax.activation.MimetypesFileTypeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;

public class FileUploadComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6878994527781528849L;

	public Cell fileUploadDiv;

	public FileUploadExt fileUpload;
	
	public Cell fileLabelDiv;

	public Textbox fileLabel;

	public Button download;

	public Button clean;

	@Override
	public void doAfterCompose(final org.zkoss.zk.ui.Component comp) throws Exception {
		super.doAfterCompose(comp);
		fileUpload.addEventListener(Events.ON_UPLOAD, new FileUploadListener(this));
		fileUpload.addEventListener(Events.ON_USER, new FileUploadListener(this));
		download.addEventListener(Events.ON_CLICK, new DownloadListener(this));
		clean.addEventListener(Events.ON_CLICK, new CleanListener(this));
		download.setDisabled(true);
		clean.setDisabled(true);
		fileUpload.setUpload("true,maxsize=500");
		fileLabel.setDisabled(true);
		fileLabel.setHflex("true");
	}

	@Override
	protected void bindComp(final Component comp, final String nombre)
			throws IllegalArgumentException, IllegalAccessException {
		super.bindComp(comp, nombre);
		this.download = (Button) comp.getFellow(nombre + "_download");
		this.clean = (Button) comp.getFellow(nombre + "_clean");
	}

	public void clean() {
		this.download.setDisabled(true);
		this.fileLabel.setValue(null);
		this.fileUpload.setMedia(null);
		this.clean.setDisabled(true);
	}

	public void download() {
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(fileUpload.getMedia().getByteData());
		Filedownload.save(inputStream,
				new MimetypesFileTypeMap().getContentType(fileUpload.getMedia().getContentType()),
				fileUpload.getMedia().getName());
	}

	@Override
	public void generateComponents(final FormularioComponenteDTO formComp)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		super.generateComponents(formComp);
		fileUpload = new FileUploadExt(formComp.getNombre() + "file", "Upload");
		fileUpload.setIdComponentForm(formComp.getId());
		fileUpload.setParent(fileUploadDiv);
		fileLabel = new Textbox();
		fileLabel.setParent(fileLabelDiv);
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

	public void setFile() {
		if (fileUpload.getMedia() == null) {
			download.setDisabled(true);
			clean.setDisabled(true);
		} else {
			fileLabel.setValue(fileUpload.getMedia().getName());
			download.setDisabled(false);
			fileUpload.setDisabled(true);
			clean.setDisabled(false);
		}
	}

	public void upload(final Event event) {
		final UploadEvent ev = (UploadEvent) event;
		this.fileUpload.setMedia(ev.getMedia());
		fileLabel.setValue(fileUpload.getMedia().getName());
		download.setDisabled(false);
		clean.setDisabled(false);
	}

	final class FileUploadListener implements EventListener {
		FileUploadComposer composer;

		public FileUploadListener(final FileUploadComposer composer) {
			this.composer = composer;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_UPLOAD)) {
				this.composer.upload(event);
			}
			if (event.getName().equals(Events.ON_CLICK)) {
				this.composer.download();
			}
			if (event.getName().equals(Events.ON_USER)) {
				this.composer.setFile();
			}
		}
	}

	final class DownloadListener implements EventListener {
		FileUploadComposer composer;

		public DownloadListener(final FileUploadComposer composer) {
			this.composer = composer;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_CLICK)) {
				this.composer.download();
			}
		}
	}

	final class CleanListener implements EventListener {
		FileUploadComposer composer;

		public CleanListener(final FileUploadComposer composer) {
			this.composer = composer;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_CLICK)) {
				this.composer.clean();
			}
		}
	}
}
