package com.egoveris.tica.base.util.convert;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.html.AbstractTagProcessor;
import com.itextpdf.tool.xml.html.table.Table;
import com.itextpdf.tool.xml.html.table.TableBorderEvent;

public class TextBoxTagProcessor extends AbstractTagProcessor {

	@Override
	public List<Element> start(final WorkerContext ctx, final Tag tag) {

		String label = (tag.getAttributes().get("label") != null) ? tag.getAttributes()
				.get("label") : null;
		String name = (tag.getAttributes().get("name") != null) ? tag.getAttributes()
				.get("name") : "";
		String value = (tag.getAttributes().get("value") != null) ? tag.getAttributes()
				.get("value") : "";
		String labelWidth = (tag.getAttributes().get("label-width") != null) ? tag
				.getAttributes().get("label-width") : "50";
		int alignLabel = alignment((tag.getAttributes().get("align-label") != null) ? tag
				.getAttributes().get("align-label").toUpperCase()
				: "LEFT");
		int alignValue = alignment((tag.getAttributes().get("align-value") != null) ? tag
				.getAttributes().get("align-value").toUpperCase()
				: "LEFT");
		String labelBold = (tag.getAttributes().get("label-bold") != null) ? tag
				.getAttributes().get("label-bold").toUpperCase() : "FALSE";
		float sizeFloatFont = Float
				.parseFloat((tag.getAttributes().get("size-font") != null) ? tag
						.getAttributes().get("size-font").toUpperCase() : "10");

		List<Element> l = new ArrayList<Element>(1);
		PdfPTable table;
		Phrase phrase = null;

		if (label != null) {
			phrase = new Phrase(label);
			phrase.getFont().setSize(sizeFloatFont);
			
			table = new PdfPTable(2);
			table.getDefaultCell().setBorder(0);
			table.getDefaultCell().setPadding(0);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setHorizontalAlignment(alignLabel);
			table.addCell(phrase);
			
			try {
				final int i = Integer.parseInt(labelWidth);
				table.setWidths(new int[] { i, 100 - i });
			} catch (DocumentException e) {
				throw new RuntimeWorkerException(e);
			}
		
		} else {
			table = new PdfPTable(1);
		}

		PdfPCell cell = new PdfPCell(new Phrase("  "));
		cell.setCellEvent(new TextboxEvent(name, value, alignValue, sizeFloatFont));
		cell.setBorder(0);
		table.addCell(cell);
		table.setWidthPercentage(100);
		table.setTableEvent(new TableBorderEvent(Table.setStyleValues(tag)));
		l.add(table);
		if (label != null && Boolean.parseBoolean(labelBold)) {
			phrase.getFont().setStyle(Font.BOLD);
		}
		return l;
	}

	private int alignment(String alignment) {
		if ("CENTER".equalsIgnoreCase(alignment)) {
			return Element.ALIGN_CENTER;
		} else if ("RIGHT".equalsIgnoreCase(alignment)) {
			return Element.ALIGN_RIGHT;
		} else {
			return Element.ALIGN_LEFT;
		}
	}

	public static class TextboxEvent implements PdfPCellEvent {
		private String name;
		private String value;
		private int align;
		private float fontSize;

		public TextboxEvent(String name, String value, int align, float fontSize) {
			this.name = name;
			this.value = value;
			this.align = align;
			this.fontSize = fontSize;
		}

		public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
			try {
				PdfWriter writer = canvases[0].getPdfWriter();
				TextField text = new TextField(writer,
						new Rectangle(position.getLeft(), position.getBottom() - 2,
								position.getRight(), position.getTop()), name);
				text.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
				text.setText(value);
				text.setBackgroundColor(BaseColor.WHITE);
				text.setFontSize(fontSize);
				text.setOptions(TextField.READ_ONLY);
				text.setAlignment(align);

				PdfFormField field = text.getTextField();
				writer.addAnnotation(field);
			} catch (Exception e) {
				throw new ExceptionConverter(e);
			}
		}
	}
}