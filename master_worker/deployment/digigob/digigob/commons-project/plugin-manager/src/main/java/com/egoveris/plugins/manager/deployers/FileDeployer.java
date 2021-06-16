/**
 * 
 */
package com.egoveris.plugins.manager.deployers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author difarias
 *
 */
public class FileDeployer extends GenericDeployer {
	final static Logger logger = LoggerFactory.getLogger(FileDeployer.class);

	private File fileDirectory;
	
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.GenericDeployer#setUrlToMonitoring(java.lang.String)
	 */
	@Override
	public void setUrlToMonitoring(String UrlToMonitoring) {
		try {
			URL url = new URL(UrlToMonitoring);
			
			if (url.getProtocol().equalsIgnoreCase("file")) {
				this.urlToMonitoring = url.getFile();
				this.fileDirectory=null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @return the fileDirectory
	 */
	public File getFileDirectory() {
		if (fileDirectory==null) {
			fileDirectory = new File(getUrlToMonitoring());
		}
		
		return fileDirectory;
	}


	@Override
	public void explore() throws IOException {
		fileExplore();
	}
	

	public void fileExplore() throws IOException {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(getFileDirectory());
		scanner.setIncludes(new String[]{"*.jar"}); // Antes iba con \\ al comienzo
		scanner.scan();
		
		if (scanner.getIncludedFilesCount()>0) {
			List<String> archivos = Arrays.asList(scanner.getIncludedFiles());
			List<String> destiny = new ArrayList<String>();
			for (String filename : archivos) {
				String directoryName = getUrlToMonitoring().endsWith("\\")?getUrlToMonitoring().substring(0, getUrlToMonitoring().length()-1):getUrlToMonitoring();
				String completeFilename = directoryName+File.separator+filename;
				destiny.add(completeFilename);
			}
			
			if (!destiny.isEmpty()) {
				sendToDeploy(destiny);
			}
		}
	}
	
}
