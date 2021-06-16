package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.model.model.ArchivoDTO;

import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Fileupload;

public class FileUploadExt extends Fileupload implements ConstrInputComponent {

	private static final long serialVersionUID = -3957958607146719943L;

	private Integer idComponentForm;
	private Constraint constraint;
	private MultiConstrData multiConstrData;
	private Media media;
	private String name;

	public FileUploadExt(final String name, final String label) {
		this.setLabel(label);
		this.name = name;
	}

	@Override
	public boolean addEventListener(final String evtnm, final EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}

	@Override
	public void clearErrorMessage(final boolean revalidateRequired) {
	}

	@Override
	public Constraint getConstraint() {
		return this.constraint;
	}

	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	public Media getMedia() {
		return media;
	}

	@Override
	public MultiConstrData getMultiConstrData() {
		return this.multiConstrData;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getRawValue() {
		return this.media;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public boolean isReadonly() {
		return isDisabled();
	}

	@Override
	public void setConstraint(final Constraint constraint) {
		this.constraint = constraint;

	}

	@Override
	public void setIdComponentForm(final Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}

	public void setMedia(final Media media) {
		this.media = media;
	}

	@Override
	public void setMultiConstrData(final MultiConstrData multiConstrStruct) {
		this.multiConstrData = multiConstrStruct;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setRawValue(final Object obj) {
		final ArchivoDTO archivo = (ArchivoDTO) obj;
		final AMedia media = new AMedia(archivo.getNombre(), "", "", archivo.getData());
		this.setMedia(media);
		Events.sendEvent(Events.ON_USER, this, null);
	}

	@Override
	public void setReadonly(final boolean disable) {
		setDisabled(disable);
	}
}
