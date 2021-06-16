/**
 * 
 */
package com.egoveris.plugins.manager.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * @author difarias
 *
 */
public class FileMetadata implements Serializable {
	private String filename;
	private String fullFilename;
	private long length;
	private long crc32;
	private long lastModified;
	
	public FileMetadata(File file) throws IOException {
		this.fullFilename = file.getAbsolutePath();
		this.filename = FilenameUtils.getName(this.fullFilename);
		this.length = file.length();
		this.lastModified = file.lastModified();
		this.crc32 = FileUtils.checksumCRC32(file);
	}

	public FileMetadata(String filename) throws IOException {
		this(new File(filename));
	}
	
	public boolean isEqual(FileMetadata metadata) {
		boolean result = this.getLength()==metadata.getLength();
			result &= this.getFilename().equals(metadata.getFilename());
			result &= this.getCrc32()==metadata.getCrc32();
			result &= this.getLastModified()==metadata.getLastModified();
			
		return result;
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}
	/**
	 * @return the crc32
	 */
	public long getCrc32() {
		return crc32;
	}
	/**
	 * @return the lastModified
	 */
	public long getLastModified() {
		return lastModified;
	}

	/**
	 * @return the fullFilename
	 */
	public String getFullFilename() {
		return fullFilename;
	}
}
