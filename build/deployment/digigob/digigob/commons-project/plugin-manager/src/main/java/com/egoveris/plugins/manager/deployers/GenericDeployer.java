/**
 * 
 */
package com.egoveris.plugins.manager.deployers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.IDeployHandler;
import com.egoveris.plugins.manager.model.FileMetadata;

/**
 * @author difarias
 *
 */
public abstract class GenericDeployer extends TimerTask implements IDeployer {
	final static Logger logger = LoggerFactory.getLogger(GenericDeployer.class);
	
	protected IDeployHandler deployHandler;
	
	protected Timer timer;
	protected long timeout=10;
	protected boolean paused;
	protected String urlToMonitoring;
	
	protected Map<String,FileMetadata> filesMap;
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	/**
	 * @return the deployHandler
	 */
	public IDeployHandler getDeployHandler() {
		return deployHandler;
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#setDeployHandler(com.egoveris.plugins.manager.IDeployHandler)
	 */
	public void setDeployHandler(IDeployHandler deployHandler) {
		this.deployHandler = deployHandler;
	}
	
	
	/**
	 * @return the filesMap
	 */
	public Map<String, FileMetadata> getFilesMap() {
		if (filesMap==null) {
			filesMap = new HashMap<>();
		}
		
		return filesMap;
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
	
	public abstract void explore() throws IOException;
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		while (paused){try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		  Thread.currentThread().interrupt();
		}};
		
		try {
			explore();
		} catch (IOException e) {
		  logger.error("error run - IOException", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#setURLToMonitoring(java.lang.String)
	 */
	public void setUrlToMonitoring(String UrlToMonitoring) {
		this.urlToMonitoring = UrlToMonitoring;
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
	

	/* (non-Javadoc)
	 * @see java.util.TimerTask#cancel()
	 */
	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		return super.cancel();
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#scheduledExecutionTime()
	 */
	@Override
	public long scheduledExecutionTime() {
		// TODO Auto-generated method stub
		return super.scheduledExecutionTime();
	}

	
	protected void sendToDeploy(List<String> files) throws IOException {
		FileMetadata file;
		boolean doRedeploy = false;
		boolean existToRemoveFiles;
		
		for (String filename: files) {
			if (getFilesMap().containsKey(filename)) {
				file = getFilesMap().get(filename);
				FileMetadata toProve = new FileMetadata(filename);
				
				if (!file.isEqual(toProve)) {
					getFilesMap().put(filename, toProve);
					doRedeploy = true;
				}
			} else { // not in cache
				try {
					System.out.println("--> no se encuentra en cache");
					file = new FileMetadata(filename);
					getFilesMap().put(filename, file);
				} catch (Exception e) {
		    logger.error("error sendToDeploy", e);
				}
			}
		}
		
		if (doRedeploy) {
			getDeployHandler().redeploy(files);
		} else {
			getDeployHandler().deploy(files);
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
	 * @see com.egoveris.plugins.manager.deployers.IDeployer#start()
	 */
	public void start() {
		if (timer==null) {
			logger.info("--------------------------------------------------------");
			logger.info("Starting "+this.getClass().getSimpleName()+" : "+getUrlToMonitoring());
			logger.info("Timeout: "+getTimeout());
			logger.info("--------------------------------------------------------");
			
			timer = new Timer(true);
			timer.scheduleAtFixedRate(this, 0, getTimeout()*1000);
			paused=false;
		} else { // timer exists
			paused=false;
		}
	}
	
}
