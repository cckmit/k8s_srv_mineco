/**
 * 
 */
package com.egoveris.plugins.manager.deployers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.IDeployHandler;

/**
 * @author difarias
 *
 */
@Deprecated
public class FileDeployerOLD extends TimerTask implements IDeployer {
	final static Logger logger = LoggerFactory.getLogger(FileDeployerOLD.class);	
	
	@SuppressWarnings("serial")
	private class FileMetadata implements Serializable {
		private String filename;
		private long length;
		private long crc32;
		private long lastModified;
		
		public FileMetadata(File file) throws IOException {
			this.filename = file.getAbsolutePath();
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
	}
	
	private Timer timer;
	private long timeout=10;
	private boolean paused;
	private String urlToMonitoring;
	private File fileDirectory;

	private IDeployHandler deployHandler;
	
	private Map<String,FileMetadata> filesMap;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	/**
	 * @return the filesMap
	 */
	public Map<String, FileMetadata> getFilesMap() {
		if (filesMap==null) {
			filesMap = new HashMap<>();
		}
		
		return filesMap;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#setURLToMonitoring(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#getURIToMonitoring()
	 */
	public String getUrlToMonitoring() {
		if (urlToMonitoring==null) {
			urlToMonitoring = "/";
		}
		return urlToMonitoring;
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

	/**
	 * @return the paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Method to set the time out in seconds
	 * @param timeout the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		while (paused){try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			  Thread.currentThread().interrupt();
		}};
		
		try {
			fileExplore();
		} catch (IOException e) {
		  logger.error("run - IOException", e);
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#start()
	 */
	public void start() {
		if (timer==null) {
			logger.info("--------------------------------------------------------");
			logger.info("Starting file deployer : "+getUrlToMonitoring());
			logger.info("Timeout: "+getTimeout());
			logger.info("--------------------------------------------------------");
			
			timer = new Timer(true);
			timer.scheduleAtFixedRate(this, 0, getTimeout()*1000);
			paused=false;
		} else { // timer exists
			paused=false;
		}
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#pause()
	 */
	public void pause() {
		if (timer!=null) {
			logger.debug("FileDeployer.pause()");
			paused=true;
		}
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#stop()
	 */
	public void stop() {
		if (timer!=null) {
			logger.debug("FileDeployer.stop()");
			timer.cancel();
			timer.purge();
			timer=null;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#setDeployHandler(com.egoveris.plugins.manager.IDeployHandler)
	 */
	public void setDeployHandler(IDeployHandler deployHandler) {
		this.deployHandler = deployHandler;
	}
	
	/**
	 * @return the deployHandler
	 */
	public IDeployHandler getDeployHandler() {
		return this.deployHandler;
	}
	

	private void sendToDeploy(List<String> files) throws IOException {
		FileMetadata file;
		boolean doRedeploy = false;
		for (String filename: files) {
			if (getFilesMap().containsKey(filename)) {
				file = getFilesMap().get(filename);
				FileMetadata toProve = new FileMetadata(filename);
				
				boolean result = !file.isEqual(toProve);
				
				if (result) {
					getFilesMap().put(filename, toProve);
					//getDeployHandler().redeploy(filename);
					doRedeploy = true;
				}
			} else { // not in cache
				try {
					file = new FileMetadata(filename);
					getFilesMap().put(filename, file);
					
					//getDeployHandler().deploy(filename);
				} catch (Exception e) {
				  logger.error("sendToDeploy", e);
				}
			}
		}
		
		if (doRedeploy) {
			getDeployHandler().redeploy(files);
		} else {
			getDeployHandler().deploy(files);
		}
	}

	public void fileExplore() throws IOException {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(getFileDirectory());
		scanner.setIncludes(new String[]{"*.jar"}); // Antes iba con \\ al comienzo
		scanner.scan();
		
		if (scanner.getIncludedFilesCount()>0) {
			List<String> archivos = Arrays.asList(scanner.getIncludedFiles());
			List<String> destiny = new ArrayList<>();
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
