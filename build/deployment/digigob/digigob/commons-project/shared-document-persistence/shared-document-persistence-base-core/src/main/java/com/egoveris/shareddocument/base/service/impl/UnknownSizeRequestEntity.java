package com.egoveris.shareddocument.base.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.methods.RequestEntity;

public class UnknownSizeRequestEntity implements RequestEntity {
	private InputStream content = null;

	public UnknownSizeRequestEntity(InputStream content) {
		this.content = new BufferedInputStream(content);
	}

	public long getContentLength() {
		return -1;
	}

	public String getContentType() {
		return "content/octet-stream";
	}

	public boolean isRepeatable() {
		return false;
	}

	public void writeRequest(OutputStream out) throws IOException {
		int read = content.read();
		while (read != -1) {
			out.write(read);
			read=content.read();
			System.out.println("OPS");
		}
		out.flush();
	}

}
